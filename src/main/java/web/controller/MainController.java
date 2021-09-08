package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class MainController {

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;


    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(User user) {
        Role roleUser = roleService.getRoleByName("USER");

        if (roleUser == null) {
            roleUser = new Role();
            roleUser.setRole("USER");
        }
        user.getRoles().add(roleUser);

//        Role roleAdmin= roleService.getRoleByName("ADMIN");
//
//        if (roleAdmin == null) {
//            roleAdmin = new Role();
//            roleAdmin.setRole("ADMIN");
//        }
//
//        user.getRoles().add(roleAdmin);

        userService.saveUser(user);
        return "redirect:/registration";
    }

    @GetMapping(value = "/admin")
    public String allUsers(Model modelUsers) {
        List<User> users = userService.getUsersList();
        modelUsers.addAttribute("users", users);
        return "admin";
    }





    @GetMapping(value = "/user")
    public String currentUser(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Object principal = authentication.getPrincipal();
//        Field field = principal.getClass().getDeclaredField("name");
//        field.setAccessible(true);
//        Object o = field.get(principal);
//        if (o != null) {
//            System.out.println(o.toString());
//        }
        return "user";
    }

    @GetMapping("/user_edit/{id}")
    public String editUserForm(@PathVariable("id") Long id, ModelMap model) {
        User user = userService.getUser(id);
        Set<Role> roles = user.getRoles();
        model.addAllAttributes(Map.of("user",user,"roles",roles));
        return "user_edit";
    }

    @PostMapping("/user_update/{id}")
    public String updateUser(@PathVariable Long id, User user) {
        User hiberUser = userService.getUser(id);
        hiberUser.update(user);
        userService.updateUser(hiberUser);
        return "redirect:/admin";
    }

    @GetMapping("/user_delete/{id}")
    public String removeUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/do_admin/{id}")
    public String doEdit(@PathVariable("id") Long id) {
        User userWithoutAdminRole = userService.getUser(id);
        Role roleAdmin= roleService.getRoleByName("ADMIN");
        if (roleAdmin == null) {
            roleAdmin = new Role();
            roleAdmin.setRole("ADMIN");
        }
        userWithoutAdminRole.getRoles().add(roleAdmin);
        userService.updateUser(userWithoutAdminRole);
        return "redirect:/admin";
    }
}


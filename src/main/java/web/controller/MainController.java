package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
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
        List<Role> allRoles = roleService.getAllRoles();
        Set<Role> userRoles = user.getRoles();
        model.addAllAttributes(Map.of("user", user, "roles", userRoles, "allRoles", allRoles));
        return "user_edit";
    }

    @PostMapping("/user_update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") User user, @RequestParam(value = "selectedRole", required = false) String selectedRole) {
        userService.updateUser(user,selectedRole, id);
        return "redirect:/admin";
    }

    @GetMapping("/user_delete/{id}")
    public String removeUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}


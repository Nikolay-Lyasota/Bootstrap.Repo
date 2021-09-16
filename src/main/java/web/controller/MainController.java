package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;


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
        modelUsers.addAttribute("users", userService.getUsersList());
        return "admin";
    }

    @GetMapping(value = "/user")
    public String currentUser(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "user";
    }


    @GetMapping("/user_edit")
    public String editUserForm(@RequestParam Long id, ModelMap model) {
        User user = userService.getUser(id);
        model.addAllAttributes(Map.of("user", user, "roles", user.getRoles(), "allRoles", roleService.getAllRoles()));
        return "user_edit";
    }

    @PostMapping("/user_update")
    public String updateUser(@RequestParam(value = "userId") Long id,
                             @ModelAttribute("user") User user,
                             @RequestParam(value = "selectedRole", required = false) String selectedRole) {
        userService.updateUser(user, selectedRole, id);
        return "redirect:/admin";
    }

    @PostMapping("/user_delete")
    public String removeUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}


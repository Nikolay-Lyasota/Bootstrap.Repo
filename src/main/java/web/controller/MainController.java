package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.converter.UserConverter;
import web.dto.PrincipalDto;
import web.dto.UserDto;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class MainController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "login";
    }

//    @GetMapping("/registration")
//    public String registration(Model model) {
//        model.addAttribute("user", new User());
//        return "registration";
//    }
//
//    @PostMapping("/registration")
//    public String registerUser(User user) {
//        userService.saveUser(user);
//        return "redirect:/registration";
//    }

    @GetMapping(value = "/admin")
    public String allUsers(@AuthenticationPrincipal User principal, Model model, User newUser) {
        model.addAttribute("dto",new PrincipalDto(userService.findByUsername(principal.getUsername())));
        model.addAttribute("users", userService.getUsersList());
        model.addAttribute("newUser",newUser);
        model.addAttribute("rolesList",List.of("USER","ADMIN"));
        return "admin";
    }

    @PostMapping("/add")
    public String newUser(UserDto userDto) {
        System.out.println(userDto);
        User newUser = userConverter.convertDtoToUser(userDto);
        userService.saveUser(newUser);
        return "redirect:/admin";
    }

    @GetMapping(value = "/user")
    public String currentUser(@AuthenticationPrincipal User principal, Model model) {
        model.addAttribute("dto",new PrincipalDto(userService.findByUsername(principal.getUsername())));
        model.addAttribute("user", principal);
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


package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.converter.UserConverter;
import web.dto.UserDto;
import web.dto.UserWithIdDto;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
public class RestControllers {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserConverter userConverter;


    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody UserDto dtoUser) {
        userService.saveUser(userConverter.convertDtoToUser(dtoUser));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> get() {
        return new ResponseEntity<>(userService.getUsersList(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> all() {
        return ResponseEntity.ok(userService.getUsersList());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("get/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PostMapping("/patch")
    public ResponseEntity<Void> patch(@RequestBody UserWithIdDto user) {
        userService.updateUser(userConverter.toUser(user));
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/oauth")
    public ResponseEntity<Void> savePrincipal(Principal principal) {
        for (int i = 0; i < 30; i++) {
            System.out.println(principal.getName());
        }
        return ResponseEntity.ok().build();
    }
//
//    @GetMapping(value = "/user")
//    public ResponseEntity<?> usersPage(@AuthenticationPrincipal User principal, Model model) {
//        System.out.println("USER!");
//        return ResponseEntity.ok().build();
//    }

}

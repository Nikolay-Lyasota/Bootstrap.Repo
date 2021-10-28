package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.converter.UserConverter;
import web.dto.UserDto;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

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

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        return new ResponseEntity<>(userService.getUsersList(),HttpStatus.ACCEPTED);
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



//    @PostMapping("/new")
//    public User create(@RequestBody User user) {
//       return userService.saveUser(user);
//
//    }
}

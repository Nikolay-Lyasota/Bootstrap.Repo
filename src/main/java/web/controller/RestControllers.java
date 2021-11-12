package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.converter.UserConverter;
import web.dto.UserDto;
import web.dto.UserWithIdDto;
import web.model.User;
import web.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/")
public class RestControllers {

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;


    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody UserDto dtoUser) {
        userService.saveUser(userConverter.convertDtoToUser(dtoUser));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> get() {
        return ResponseEntity.ok(userService.getUsersList());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/get")
    public ResponseEntity<User> getUser(@RequestParam("id") Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PostMapping("/patch")
    public ResponseEntity<Void> patch(@RequestBody UserWithIdDto user) {
        userService.updateUser(userConverter.toUser(user));
        return ResponseEntity.ok().build();
    }
}

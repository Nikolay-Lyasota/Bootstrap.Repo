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
@RequestMapping("/api/users")
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/get/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PutMapping("/put")
    public ResponseEntity<Void> patch(@RequestBody UserWithIdDto user) {
        userService.updateUser(userConverter.toUser(user));
        return ResponseEntity.ok().build();
    }
}

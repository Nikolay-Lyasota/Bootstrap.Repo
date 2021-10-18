package web.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import web.converter.UserConverter;
import web.dto.UserDto;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import javax.validation.Valid;
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
    public ResponseEntity<Void> create(UserDto dtoUser) {
        userService.saveUser(userConverter.convertDtoToUser(dtoUser));
        return ResponseEntity.ok().build();
    }


    @GetMapping("/get")
    public ResponseEntity<?> get() {
        return new ResponseEntity<>(userService.getUsersList(),HttpStatus.ACCEPTED);
    }

//    @PostMapping("/new")
//    public User create(@RequestBody User user) {
//       return userService.saveUser(user);
//
//    }
}

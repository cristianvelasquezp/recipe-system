package org.cristianvelasquezp.recipes.controllers;

import jakarta.validation.Valid;
import org.cristianvelasquezp.recipes.entities.User;
import org.cristianvelasquezp.recipes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.OK)
    public void registerUser(@Valid @RequestBody User user) {
        userService.saveUser(user);
    }
}

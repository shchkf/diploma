package org.manager.controllers;

import org.manager.managers.UserManager;
import org.manager.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserManager userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PutMapping("/{id}/block")
    public ResponseEntity<User> blockUser(@PathVariable Long id) {
        User blockedUser = userService.blockUser(id);
        return new ResponseEntity<>(blockedUser, HttpStatus.OK);
    }

    @PutMapping("/{id}/move-to-resgroup/{rgId}")
    public ResponseEntity<User> moveUserToResGroup(@PathVariable Long id, @PathVariable Long rgId) {
        User movedUser = userService.moveUserToResGroup(id, rgId);
        return new ResponseEntity<>(movedUser, HttpStatus.OK);
    }
}

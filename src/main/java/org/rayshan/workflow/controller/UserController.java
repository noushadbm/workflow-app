package org.rayshan.workflow.controller;

import lombok.extern.log4j.Log4j2;
import org.rayshan.workflow.entity.User;
import org.rayshan.workflow.exception.AppException;
import org.rayshan.workflow.modal.ApiResponse;
import org.rayshan.workflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Log4j2
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ApiResponse<List<User>> getAllUsers() {
        log.info("Fetching all users");
        List<User> users = userService.getAllUsers();
        ApiResponse<List<User>> response = new ApiResponse<>(HttpStatus.OK.value(), "Success");
        response.setData(users);
        log.info("Returning {} users", users.size());
        return response;
    }

    @GetMapping("/{id}")
    public ApiResponse<User> getUserById(@PathVariable Long id) throws AppException {
        User user = userService.getUserById(id);
        ApiResponse<User> response = new ApiResponse<>(HttpStatus.OK.value(), "Success");
        response.setData(user);
        return response;
    }

    @GetMapping("/email/{email}")
    public ApiResponse<User> getUserByEmail(@PathVariable String email) throws AppException {
        User user = userService.getUserByEmail(email);
        ApiResponse<User> response = new ApiResponse<>(HttpStatus.OK.value(), "Success");
        response.setData(user);
        return response;
    }

    @PostMapping
    public ApiResponse<User> createUser(@RequestBody User user) throws AppException {
        User createdUser = userService.createUser(user);
        ApiResponse<User> response = new ApiResponse<>(HttpStatus.OK.value(), "Success");
        response.setData(createdUser);
        return response;
    }

    @PutMapping("/{id}")
    public ApiResponse<User> updateUser(@PathVariable Long id, @RequestBody User user) throws AppException {
        User updatedUser = userService.updateUser(id, user);
        ApiResponse<User> response = new ApiResponse<>(HttpStatus.OK.value(), "Success");
        response.setData(updatedUser);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

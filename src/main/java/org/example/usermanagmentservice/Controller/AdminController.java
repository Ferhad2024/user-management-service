package org.example.usermanagmentservice.Controller;

import lombok.RequiredArgsConstructor;
import org.example.usermanagmentservice.Service.ServiceImpl.UserServiceImpl;
import org.example.usermanagmentservice.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/dashboard")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"}, allowCredentials = "true")
public class AdminController {

    private final UserServiceImpl userService;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
     return ResponseEntity.ok(userService.getUserById(id));
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponse>>getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}/deactivate")
    public ResponseEntity<Void>deleteUser(@PathVariable Long id){
     userService.deleteUserById(id);
     return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.aptech.Controller;

import java.util.concurrent.ExecutionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.aptech.Entity.User;
import vn.aptech.service.UserService;

/**
 *
 * @author Administrator
 */
@RestController
public class UserController {

    public UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public String createUser(@RequestBody User u) throws InterruptedException, ExecutionException {
        return service.createUser(u);
    }

    @GetMapping("/get")
    public User getUser(@RequestParam int id) throws InterruptedException, ExecutionException {
        return service.getUser(id);
    }

    @PutMapping("/update")
    public String updateUser(@RequestBody User u) throws InterruptedException, ExecutionException {
        return service.updateUser(u);
    }

    @PutMapping("/delete")
    public String deleteUser(@RequestParam int id) throws InterruptedException, ExecutionException {
        return service.deleteUser(id);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testGetEndpoint() {
        return ResponseEntity.ok("Test get endpoint is working");
    }
}

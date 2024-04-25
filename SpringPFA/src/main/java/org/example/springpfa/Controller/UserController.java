package org.example.springpfa.Controller;

import org.example.springpfa.Repository.UserRepository;
import org.example.springpfa.Services.JWTUtils;
import org.example.springpfa.Services.PasswordEncoderService;
import org.example.springpfa.Services.UserService;
import org.example.springpfa.entities.Demande;
import org.example.springpfa.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService ;
    @Autowired
    JWTUtils jwtUtils;



    @GetMapping("/users")
    public List<User> GetUsers(){
        List<User> arrayList = new ArrayList<>();
        arrayList =  userService.FindUsers();
        return  arrayList;
    }
    //Get a specific user
    @GetMapping("/users/{id}")
    public User GetUser(@PathVariable long id){
        return userService.findUser(id);
    }
    //add new User
    @PostMapping("/user/add")
    public void PostUser(@RequestBody User user){
        userService.addUser(user);
    }
    //Edit an existing user :
    @PutMapping("/user/edit/{id}")
    public void PutUser(@RequestBody User user , @PathVariable long id ){
        userService.editUser(user,id);
    }
    @DeleteMapping("/users/delete/{id}")
    public void DeleteUser(@PathVariable long id){
        userService.dropUser(id);
    }

    // Authentication
    @PostMapping("/users/authenticate")
    public Map<String, String> authenticateUser(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        Map<String, String> response = new HashMap<>();
        // Retrieve user from database
        User user = userRepository.findByEmail(email);
        if (user != null && password.equals(user.getPassword())) {
            // Generate JWT token
            String token = jwtUtils.generateToken(user);
            // Return the token to the client
            response.put("token", token);
            response.put("response","200");
            response.put("role",user.getUserRole().getNomRole());
        } else {
            response.put("response","503");
            response.put("role","err");
        }
        return response;
    }


}

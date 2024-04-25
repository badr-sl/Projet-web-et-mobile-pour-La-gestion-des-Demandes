package org.example.springpfa.Services;

import org.example.springpfa.Repository.UserRepository;
import org.example.springpfa.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> FindUsers(){
        List<User> arrayList = new ArrayList<>();
        arrayList =  userRepository.findAll();
        return  arrayList;
    }
    public User findUser(long id){
        User user ;
        if(userRepository.findById(id).isPresent()){
            user =  userRepository.findById(id).get();
            return  user;
        }else {
            return null;
        }
    }
    public void addUser(User user){
        userRepository.save(user);
    }
    public void editUser(User user,long iduser){
        if (userRepository.findById(iduser).isPresent()){
            User editUser = new User();
            editUser.setUserRole(user.getUserRole());
            editUser.setTel(user.getTel());
            editUser.setDemandes(user.getDemandes());
            editUser.setEmail(user.getEmail());
            editUser.setPassword(user.getPassword());
            editUser.setUsername(user.getUsername());
            userRepository.save(editUser);
        }
    }
    public void dropUser(long id){
        if(userRepository.findById(id).isPresent()){
            User user = userRepository.findById(id).get();
            userRepository.delete(user);
        }
    }

}

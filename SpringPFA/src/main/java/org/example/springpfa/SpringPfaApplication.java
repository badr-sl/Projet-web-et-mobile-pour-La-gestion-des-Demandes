package org.example.springpfa;

import org.example.springpfa.Repository.DemandeRepository;
import org.example.springpfa.Repository.UserRepository;
import org.example.springpfa.Repository.UserRoleRepository;
import org.example.springpfa.entities.Demande;
import org.example.springpfa.entities.User;
import org.example.springpfa.entities.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class SpringPfaApplication implements CommandLineRunner {
    @Autowired
    DemandeRepository demandeRepository ;
    @Autowired
    UserRepository userRepository ;
    @Autowired
    UserRoleRepository userRoleRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringPfaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*ArrayList<User> arrayList = new ArrayList<>();
        ArrayList<Demande> arrayList2 = new ArrayList<>();
        Demande demande = new Demande(null,"title","subject",new Date(),"Pending",null);
        arrayList2.add(demande);
        User user = new User(null,"user1","0000000","123","emal@gmail.com",null);
        arrayList.add(user);
        UserRole userRole = new UserRole(null,"Admin",null);

        demandeRepository.save(demande);
        userRepository.save(user);
        userRoleRepository.save(userRole);

         */
    }
}

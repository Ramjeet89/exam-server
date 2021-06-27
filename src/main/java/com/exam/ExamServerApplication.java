package com.exam;

import com.exam.helper.UserFoundException;
import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;


@SpringBootApplication
public class ExamServerApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(ExamServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            System.out.println("starting code");
            User user = new User();
            user.setFirstName("Ramjeet89");
            user.setLastName("Mahto");
            user.setUsername("Ramjeet");
            user.setPassword(this.bCryptPasswordEncoder.encode("abc123"));
            user.setEmail("ramjeet.mahto@gmail.com");
            user.setPhone("9798463413");
            user.setProfile("default.png");

            Role role = new Role();
            role.setRoleId(44L);
            role.setRoleName("ADMIN");

            Set<UserRole> userRoleSet = new HashSet<>();
            UserRole userRole = new UserRole();
            userRole.setRole(role);
            userRole.setUser(user);

            userRoleSet.add(userRole);

            User user1 = this.userService.createUser(user, userRoleSet);
            System.out.println(user1.getUsername());
        } catch (UserFoundException e) {
            e.printStackTrace();
        }
    }
}

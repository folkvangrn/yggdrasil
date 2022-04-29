package ovh.nixenos.tab.server.services;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ovh.nixenos.tab.server.repositories.UserRepository;
import ovh.nixenos.tab.server.users.User;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User findById(final int id) {
        User result = userRepository.findById(id);
        return result;
    }

    public User findByUsername(final String username) {
        User result = userRepository.findByUsername(username);
        return result;
    }

    public void save(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void delete(User user) {
        try {
            userRepository.delete(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void deleteById(int id) {
        try {
            userRepository.deleteById(id);
            ;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @PostConstruct
    public void createDefaultAdmin() {
        /*
         * @Value("${app.admin.username}")
         * String defaultAdminUsernameInput;
         * 
         * @Value("${app.admin.password}")
         * String defaultAdminPasswordInput;
         * 
         * @Value("${app.admin.name}")
         * String defaultAdminNameInput;
         * 
         * @Value("${app.admin.surname}")
         * 
         * String defaultAdminSurnameInput;
         */
        String adminUsername = "admin";
        String adminPassword = "admin";
        String adminName = "admin";
        String adminSurname = "admin";
        String adminRole = "admin";
        /*
         * if (defaultAdminNameInput != null) {
         * adminName = defaultAdminNameInput;
         * }
         * if (defaultAdminSurnameInput != null) {
         * adminSurname = defaultAdminSurnameInput;
         * }
         * if (defaultAdminUsernameInput != null) {
         * adminUsername = defaultAdminUsernameInput;
         * }
         * if (defaultAdminPasswordInput != null) {
         * adminPassword = defaultAdminPasswordInput;
         * }
         */
        User admin = new User(adminUsername, adminName, adminSurname, adminRole, adminPassword);
        User oldAdmin = this.findByUsername("admin");
        if (oldAdmin != null) {
            try {
                oldAdmin.setPassword(passwordEncoder.encode(adminPassword));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else {
            try {
                admin.setPassword(passwordEncoder.encode(adminPassword));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            this.save(admin);
        }
    }
}

package ovh.nixenos.tab.server.services;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ovh.nixenos.tab.server.repositories.UserRepository;
import ovh.nixenos.tab.server.users.AdminGenerator;
import ovh.nixenos.tab.server.users.User;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminGenerator adminGenerator;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User findById(final Long id) {
        Optional<User> result = userRepository.findById(id);
        if (result.isPresent())
            return result.get();
        else
            return null;
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

    /*
     * public void deleteById(int id) {
     * try {
     * userRepository.deleteById(id);
     * ;
     * } catch (Exception e) {
     * System.err.println(e.getMessage());
     * }
     * }
     */

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public boolean existsById(final Long id) {
        return this.userRepository.existsById(id);
    }

    public List<User> findAllByRole(final String role) {
        return this.userRepository.findAllByRole(role);
    }

    @PostConstruct
    public void createDefaultAdmin() {
        String defaultAdminUsernameInput = adminGenerator.getUsername();
        String defaultAdminPasswordInput = adminGenerator.getPassword();
        String defaultAdminNameInput = adminGenerator.getFirstName();
        String defaultAdminSurnameInput = adminGenerator.getLastName();
        String adminUsername = "admin";
        String adminPassword = "admin";
        String adminName = "admin";
        String adminSurname = "admin";
        String adminRole = "admin";

        if (defaultAdminNameInput != null) {
            adminName = defaultAdminNameInput;
        }
        if (defaultAdminSurnameInput != null) {
            adminSurname = defaultAdminSurnameInput;
        }
        if (defaultAdminUsernameInput != null) {
            adminUsername = defaultAdminUsernameInput;
        }
        if (defaultAdminPasswordInput != null) {
            adminPassword = defaultAdminPasswordInput;
        }

        User admin = new User(adminUsername, adminName, adminSurname, adminRole, adminPassword);
        User oldAdmin = this.findByUsername(adminUsername);
        if (oldAdmin != null) {
            try {
                this.delete(oldAdmin);
                admin.setPassword(passwordEncoder.encode(adminPassword));
                this.save(admin);
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

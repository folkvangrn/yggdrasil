package ovh.nixenos.tab.server.repositories;

import org.springframework.data.repository.CrudRepository;
import ovh.nixenos.tab.server.users.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    List<User> findAllByRole(final String role);
}

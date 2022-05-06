package ovh.nixenos.tab.server.repositories;

import org.springframework.data.repository.CrudRepository;
import ovh.nixenos.tab.server.users.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
    User findById(Long id);

}

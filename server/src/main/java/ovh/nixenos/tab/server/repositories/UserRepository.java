package ovh.nixenos.tab.server.repositories;

import org.springframework.data.repository.CrudRepository;
import ovh.nixenos.tab.server.users.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

}

package ovh.nixenos.tab.server.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ovh.nixenos.tab.server.entities.Request;

import java.util.Optional;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {

    Optional<Request> findById(Long requestId);

}

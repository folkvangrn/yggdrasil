package ovh.nixenos.tab.server.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ovh.nixenos.tab.server.entities.Request;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {

    Optional<Request> findById(Long requestId);

    Optional<List<Request>> findByStatus(String status);

    Optional<List<Request>> findByManagerIdAndStatus(Long id, String status);

}

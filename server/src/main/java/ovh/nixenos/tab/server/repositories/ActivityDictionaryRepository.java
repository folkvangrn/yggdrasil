package ovh.nixenos.tab.server.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ovh.nixenos.tab.server.entities.ActivityDictionary;

import java.util.Optional;

@Repository
public interface ActivityDictionaryRepository extends CrudRepository<ActivityDictionary, String> {

    Optional<ActivityDictionary> findById(String type);

    Iterable<ActivityDictionary> findAll();

}

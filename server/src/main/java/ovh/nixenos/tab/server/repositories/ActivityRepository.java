package ovh.nixenos.tab.server.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ovh.nixenos.tab.server.entities.Activity;
import ovh.nixenos.tab.server.entities.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends CrudRepository<Activity, Long> {

    Optional<Activity> findById(final Long activityId);

    List<Activity> findByWorkerIdAndStatusOrderBySequenceNumberAsc(final Long id, final Status status);

    List<Activity> findByWorkerIdOrderBySequenceNumberAsc(final Long id);

}

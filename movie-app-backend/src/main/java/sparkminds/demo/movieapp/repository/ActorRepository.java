package sparkminds.demo.movieapp.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sparkminds.demo.movieapp.entity.Actor;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActorRepository extends MongoRepository<Actor, UUID> {
    public Optional<Actor> findByFullName(String fullName);

}

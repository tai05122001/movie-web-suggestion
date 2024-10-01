package sparkminds.demo.movieapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sparkminds.demo.movieapp.entity.Genre;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GenreRepository extends MongoRepository<Genre, UUID> {
    public Optional<Genre> findByName(String name);
}

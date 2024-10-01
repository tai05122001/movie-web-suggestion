package sparkminds.demo.movieapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sparkminds.demo.movieapp.entity.Genre;
import sparkminds.demo.movieapp.entity.Movie;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MovieRepository extends MongoRepository<Movie, UUID> {
    Optional<Movie> findByTitle(String title);
    List<Movie> findByGenreListContaining(Genre genre);

}

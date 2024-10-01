package sparkminds.demo.movieapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sparkminds.demo.movieapp.entity.History;
import sparkminds.demo.movieapp.entity.Movie;
import sparkminds.demo.movieapp.entity.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface HistoryRepository extends MongoRepository<History, UUID> {
    List<History> findByUser(User user);
    History findFirstByMovie (Movie movie);
}

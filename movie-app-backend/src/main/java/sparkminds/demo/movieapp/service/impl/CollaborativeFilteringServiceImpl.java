package sparkminds.demo.movieapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparkminds.demo.movieapp.entity.History;
import sparkminds.demo.movieapp.entity.Movie;
import sparkminds.demo.movieapp.entity.User;
import sparkminds.demo.movieapp.exception.BadRequestException;
import sparkminds.demo.movieapp.repository.HistoryRepository;
import sparkminds.demo.movieapp.repository.MovieRepository;
import sparkminds.demo.movieapp.repository.UserRepository;
import sparkminds.demo.movieapp.service.CollaborativeFilteringService;
import sparkminds.demo.movieapp.service.ContentBasedFilteringService;
import sparkminds.demo.movieapp.service.MovieService;
import sparkminds.demo.movieapp.service.dto.response.MovieResponseDto;
import sparkminds.demo.movieapp.service.mapper.MovieMapper;

import java.util.*;
import java.util.stream.Collectors;

import static sparkminds.demo.movieapp.constant.MessageContant.CommonMessange.MOVIE_IS_NOT_FOUND;
import static sparkminds.demo.movieapp.constant.MessageContant.CommonMessange.USER_IS_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class CollaborativeFilteringServiceImpl implements CollaborativeFilteringService {
    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final HistoryRepository historyRepository;
    private final ContentBasedFilteringService contentBasedFilteringService;
    private final MovieMapper movieMapper;


    private double consineSimilarity(List<Integer> userRating1, List<Integer> userRating2) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < userRating1.size(); i++) {
            dotProduct += userRating1.get(i) * userRating2.get(i);
            normA += Math.pow(userRating1.get(i), 2);
            normB += Math.pow(userRating2.get(i), 2);
        }
        return (Objects.equals(normA, 0) || Objects.equals(normB, 0)) ?
                0 :
                dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }


    private List<Integer> getRatingsUserForMovies(User user, List<Movie> movies) {
        List<History> historyList = historyRepository.findByUser(user).stream()
                .sorted((o1, o2) -> o2.getViewAt().compareTo(o1.getViewAt())).toList();
        List<Movie> movieList = historyList.stream().map(History::getMovie).toList();
        Set<Movie> movieSet = new HashSet<>(movieList);
        List<Movie> listUnique = new ArrayList<>(movieSet);

        Map<UUID, Integer> movieRatingMap = listUnique.stream()
                .collect(Collectors.toMap(
                        Movie::getIdMovie,
                        movie -> {
                            History history = historyRepository.findFirstByMovie(movie);
                            return Objects.isNull(history) ? 0 : history.getRating();
                        }
                ));

        return movies
                .stream()
                .map(movie -> movieRatingMap.getOrDefault(movie.getIdMovie(), 0))
                .collect(Collectors.toList());
    }


    private List<User> getListUserSimilar(Integer numUsersSimilar, User currentUser) {
        List<User> listAllUserNonCurrentUser = userRepository.findAll()
                .stream().filter(user -> !user.equals(currentUser)).toList();
        List<Movie> movies = movieRepository.findAll();
        List<Integer> getRatingsListCurrentUser = getRatingsUserForMovies(currentUser, movies);
        Map<User, Double> userSimilarMap = new HashMap<>();
        listAllUserNonCurrentUser.forEach(user -> {
            List<Integer> getRatingUser = getRatingsUserForMovies(user, movies);
            userSimilarMap.put(user, consineSimilarity(getRatingUser, getRatingsListCurrentUser));
        });

        return userSimilarMap
                .entrySet()
                .stream()
                .filter(userDoubleEntry -> Objects.equals(Boolean.FALSE, userDoubleEntry.getValue().isNaN()))
                .sorted(Map.Entry.<User, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .limit(Objects.nonNull(numUsersSimilar) ? numUsersSimilar : 2)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieResponseDto> getRecommendedMovies(String username, Integer numUsersSimilar, UUID idMovie) {
        User curentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException(USER_IS_NOT_FOUND));
        Movie curentMovie = movieRepository.findById(idMovie)
                .orElseThrow(() -> new BadRequestException(MOVIE_IS_NOT_FOUND));
        //Get history of current user
        List<History> historyList = historyRepository.findByUser(curentUser);
        List<Movie> listMovieCurrentUser = historyList.stream().map(History::getMovie).toList();
        Set<Movie> setMovieCurrentUser = new HashSet<>(listMovieCurrentUser);
        //Get list unique movie
        List<Movie> listUniqueCurrentUser = new ArrayList<>(setMovieCurrentUser);
        //Get number user have high similar
        List<User> userSimilarList = getListUserSimilar(numUsersSimilar, curentUser);
        List<History> historyUserSimilarList = new ArrayList<>();
        userSimilarList.forEach(user ->
                historyUserSimilarList.addAll(historyRepository.findByUser(user).stream().
                        filter(history -> !listUniqueCurrentUser.contains(history.getMovie())).toList())
        );
        List<Movie> listMovieRecommended = historyUserSimilarList.stream().map(History::getMovie).toList();
        Map<Movie, Double> result = contentBasedFilteringService.contentBasedFiltering(curentMovie, listMovieRecommended);
        List<Movie> resultList = result.entrySet().
                stream().
                sorted(Map.Entry.<Movie, Double>comparingByValue().reversed()).
                map(Map.Entry::getKey).limit(10).toList();
        List<MovieResponseDto> movieResponseDtoList = new ArrayList<>();
        resultList.forEach(movie -> movieResponseDtoList.add(movieMapper.toMovieResponseDto(movie)));
        return movieResponseDtoList;
    }


}

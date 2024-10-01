package sparkminds.demo.movieapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparkminds.demo.movieapp.entity.Actor;
import sparkminds.demo.movieapp.entity.Genre;
import sparkminds.demo.movieapp.entity.History;
import sparkminds.demo.movieapp.entity.Movie;
import sparkminds.demo.movieapp.exception.NotFoundException;
import sparkminds.demo.movieapp.repository.ActorRepository;
import sparkminds.demo.movieapp.repository.HistoryRepository;
import sparkminds.demo.movieapp.repository.MovieRepository;
import sparkminds.demo.movieapp.repository.UserRepository;
import sparkminds.demo.movieapp.service.ContentBasedFilteringService;
import sparkminds.demo.movieapp.service.HuggingFaceService;
import sparkminds.demo.movieapp.service.dto.request.GetRelatedMovieLoginRequestDto;
import sparkminds.demo.movieapp.service.dto.response.MovieResponseDto;
import sparkminds.demo.movieapp.service.mapper.MovieMapper;

import java.util.*;

import static sparkminds.demo.movieapp.constant.MessageContant.CommonMessange.MOVIE_IS_NOT_FOUND;
import static sparkminds.demo.movieapp.constant.MessageContant.CommonMessange.USER_IS_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class ContentBasedFilteringServiceImpl implements ContentBasedFilteringService {

    private final HuggingFaceService huggingFaceService;

    private final MovieRepository movieRepository;

    private final HistoryRepository historyRepository;

    private final MovieMapper movieMapper;
    private final UserRepository userRepository;

    @Override
    public Map<Movie, Double> contentBasedFiltering(Movie currentMovie, List<Movie> movies) {
        List<String> listContent = movies.stream().map(Movie::getDescription).toList();
        String s = huggingFaceService.calculateSimilarStringsLocal(currentMovie.getDescription(), listContent);
        String[] response = s.replace("[", "").replace("]", "").split(",");
        Map<Movie, Double> result = new HashMap<>();
        for (int i = 0; i < response.length; i++) {
            result.put(movies.get(i), Double.parseDouble(response[i]));
        }
        return result;
    }

    @Override
    public List<MovieResponseDto> getMovieContentBasedFilteringById(UUID idMovie) {
        Movie currentMovie = movieRepository.findById(idMovie).orElseThrow(() -> new NotFoundException(MOVIE_IS_NOT_FOUND));
        List<Movie> listMovie = new ArrayList<>();
        movieRepository
                .findAll()
                .stream()
                .filter(movie -> Objects.equals(Boolean.FALSE, Objects.equals(movie.getIdMovie(), currentMovie.getIdMovie())))
                .sorted(Comparator
                        .comparingInt((Movie movie) -> countGenre(currentMovie, movie))
                        .reversed()
                        .thenComparingInt((Movie movie) -> countActor(currentMovie, movie))
                        .reversed()
                        .thenComparingInt(Movie::getViews)
                        .reversed()
                )
                .limit(20)
                .forEach(listMovie::add);

        Set<Movie> movieSet = new HashSet<>(listMovie);
        List<Movie> movieUnique = new ArrayList<>(movieSet);
        List<String> listDescription = movieUnique.stream().map(Movie::getDescription).toList();

        String result = huggingFaceService.calculateSimilarStringsLocal(currentMovie.getDescription(), listDescription);
        String[] response = result.replace("[", "").replace("]", "").split(",");
        Map<Movie, Double> mapSimilar = new HashMap<>();
        for (int i = 0; i < response.length; i++) {
            mapSimilar.put(listMovie.get(i), Double.parseDouble(response[i]));
        }
        List<MovieResponseDto> responseDtoList = new ArrayList<>();
        List<Movie> movieListResult  =  mapSimilar
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Movie, Double>comparingByValue().reversed())
                .limit(10).map(Map.Entry::getKey).toList();
        movieListResult.forEach(movie -> responseDtoList.add(movieMapper.toMovieResponseDto(movie)));
        return responseDtoList;
    }

    @Override
    public List<MovieResponseDto> getMovieContentBasedFilteringLogin(GetRelatedMovieLoginRequestDto requestDto) {
        List<Movie> historyMovieList = historyRepository
                .findByUser(userRepository
                        .findByUsername(requestDto.getUsername())
                        .orElseThrow(()-> new NotFoundException(USER_IS_NOT_FOUND))
                ).stream().map(History::getMovie).toList();
        Movie currentMovie = movieRepository.findById(requestDto.getIdMovie())
                .orElseThrow(() -> new NotFoundException(MOVIE_IS_NOT_FOUND));
        List<Movie> listMovie = new ArrayList<>();
        movieRepository
                .findAll()
                .stream()
                .filter(movie -> Objects.equals(Boolean.FALSE, Objects.equals(movie.getIdMovie(), currentMovie.getIdMovie())))
                .filter(movie -> !historyMovieList.contains(movie))
                .sorted(Comparator
                        .comparingInt((Movie movie) -> countGenre(currentMovie, movie))
                        .reversed()
                        .thenComparingInt((Movie movie) -> countActor(currentMovie, movie))
                        .reversed()
                        .thenComparingInt(Movie::getViews)
                        .reversed()
                )
                .limit(20)
                .forEach(listMovie::add);


        List<String> listDescription = listMovie.stream().map(Movie::getDescription).toList();

        String result = huggingFaceService.calculateSimilarStrings(currentMovie.getDescription(), listDescription);
        String[] response = result.replace("[", "").replace("]", "").split(",");
        Map<Movie, Double> mapSimilar = new HashMap<>();
        for (int i = 0; i < response.length; i++) {
            mapSimilar.put(listMovie.get(i), Double.parseDouble(response[i]));
        }
        List<MovieResponseDto> responseDtoList = new ArrayList<>();
        List<Movie> movieListResult  =  mapSimilar
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Movie, Double>comparingByValue().reversed())
                .limit(10).map(Map.Entry::getKey).toList();
        movieListResult.forEach(movie -> responseDtoList.add(movieMapper.toMovieResponseDto(movie)));
        return responseDtoList;
    }


    public Integer countGenre(Movie currentMovie, Movie movie) {
        Set<Genre> genreSetCurrentFilm = new HashSet<>(currentMovie.getGenreList());
        genreSetCurrentFilm.retainAll(movie.getGenreList());
        return genreSetCurrentFilm.size();


    }

    public Integer countActor(Movie currentMovie, Movie movie) {
        Set<Actor> actorHashSet = new HashSet<>(currentMovie.getActorList());
        actorHashSet.retainAll(movie.getActorList());
        return actorHashSet.size();


    }


}

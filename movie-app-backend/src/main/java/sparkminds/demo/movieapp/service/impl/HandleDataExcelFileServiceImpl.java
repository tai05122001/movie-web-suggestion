package sparkminds.demo.movieapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparkminds.demo.movieapp.entity.*;
import sparkminds.demo.movieapp.enumeration.TypeEntity;
import sparkminds.demo.movieapp.exception.BadRequestException;
import sparkminds.demo.movieapp.repository.*;
import sparkminds.demo.movieapp.service.HandleDataExcelFileService;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static sparkminds.demo.movieapp.constant.MessageContant.CommonMessange.*;

@Service
@RequiredArgsConstructor
@Transactional
public class HandleDataExcelFileServiceImpl implements HandleDataExcelFileService {

    private final GenreRepository genreRepository;
    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final HistoryRepository historyRepository;

    @Override
    @SneakyThrows
    @Transactional(readOnly = true)
    public boolean handleDataExcelFile(String fileName) {
        ClassPathResource resource = new ClassPathResource(fileName);
        if (resource.exists()) {
            try (InputStream inputStream = resource.getInputStream(); Workbook workbook = new XSSFWorkbook(inputStream)) {
                for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                    Sheet sheet = workbook.getSheetAt(i);
                    switch (sheet.getSheetName()) {
                        case "genres" -> handleGenres(sheet);
                        case "actors" -> handleActor(sheet);
                        case "movie" -> handleMovie(sheet);
                        case "user" -> handleUser(sheet);
                        case "history" -> handleHistory(sheet);
                    }
                }
            }
            return true;
        }

        return false;
    }

    private void handleHistory(Sheet sheet) {
        for (Row row : sheet) {
            User user = User.builder().build();
            Movie movie = Movie.builder().build();
            LocalDateTime viewAt = LocalDateTime.now();
            double watchDuration = 0;
            int rating = 0;
            for (Cell cell : row) {
                if (Objects.equals(Objects.equals(cell.getRowIndex(), 0), Boolean.FALSE)) {
                    if (Objects.equals(cell.getColumnIndex(), 0)) {
                        user = userRepository.findByEmail(cell.getStringCellValue()).
                                orElseThrow(() -> new BadRequestException(USER_IS_NOT_FOUND));

                    }
                    if (Objects.equals(cell.getColumnIndex(), 1)) {
                        movie = movieRepository.findByTitle(cell.getStringCellValue()).
                                orElseThrow(() -> new BadRequestException(MOVIE_IS_NOT_FOUND));
                    }
                    if (Objects.equals(cell.getColumnIndex(), 2)) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
                        viewAt = LocalDateTime.parse(cell.getStringCellValue(), formatter);
                    }
                    if (Objects.equals(cell.getColumnIndex(), 3)) {
                        watchDuration = cell.getNumericCellValue();
                    }
                    if (Objects.equals(cell.getColumnIndex(), 4)) {
                        rating = (int) cell.getNumericCellValue();
                    }
                }
            }
            if (Objects.equals(Objects.equals(row.getRowNum(), 0), Boolean.FALSE)) {
                historyRepository.save(
                        History.builder().
                                viewAt(viewAt).
                                watchDuration((int) watchDuration).
                                user(user).
                                movie(movie).
                                status(TypeEntity.ACTIVE).
                                idHistory(UUID.randomUUID()).
                                rating(rating).
                                build());
                movie.setViews(movie.getViews() + 1);
                movieRepository.save(movie);
            }
        }
    }

    private void handleUser(Sheet sheet) {
        for (Row row : sheet) {
            String email = "";
            String username = "";
            String password = "";
            for (Cell cell : row) {
                if (Objects.equals(Objects.equals(cell.getRowIndex(), 0), Boolean.FALSE)) {
                    if (Objects.equals(cell.getColumnIndex(), 0)) {
                        email = cell.getStringCellValue();
                    }
                    if (Objects.equals(cell.getColumnIndex(), 1)) {
                        username = cell.getStringCellValue();
                    }

                    if (Objects.equals(cell.getColumnIndex(), 2)) {
                        password = cell.getStringCellValue();
                    }
                }

            }
            if (Objects.equals(Objects.equals(row.getRowNum(), 0), Boolean.FALSE)) {
                userRepository.save(User.builder().
                        username(username).
                        password(password).
                        email(email).
                        idUser(UUID.randomUUID()).
                        status(TypeEntity.ACTIVE).
                        build());
            }
        }
    }

    private void handleMovie(Sheet sheet) {
        for (Row row : sheet) {
            String title = "", description = "", pathImageBrand = "", pathImagePoster = "", urlMovie = "";
            int year = 0;
            int runtime = 0;
            float rating = 0.0f;
            int numVotes = 0;
            int metaScore = 0;
            float revenue = 0.0f;
            List<Genre> genres = new ArrayList<>();
            List<Actor> actors = new ArrayList<>();
            for (Cell cell : row) {
                if (Objects.equals(Objects.equals(cell.getRowIndex(), 0), Boolean.FALSE)) {
                    if (Objects.equals(cell.getColumnIndex(), 0)) {
                        title = cell.getStringCellValue();
                    }
                    if (Objects.equals(cell.getColumnIndex(), 1)) {
                        String[] genresArray = cell.getStringCellValue().split(",");
                        for (String name : genresArray) {
                            genres.add(genreRepository.findByName(name.trim()).
                                    orElseThrow(() -> new BadRequestException(GENRE_IS_NOT_FOUND)));
                        }
                    }
                    if (Objects.equals(cell.getColumnIndex(), 2)) {
                        description = cell.getStringCellValue();
                    }

                    if (Objects.equals(cell.getColumnIndex(), 3)) {
                        String[] actorsArray = cell.getStringCellValue().split(",");
                        for (String name : actorsArray) {
                            actors.add(actorRepository.findByFullName(name.trim())
                                    .orElseThrow(() -> new BadRequestException(ACTOR_IS_NOT_FOUND)));
                        }
                    }
                    if (Objects.equals(cell.getColumnIndex(), 4)) {
                        year = (int) cell.getNumericCellValue();
                    }

                    if (Objects.equals(cell.getColumnIndex(), 5)) {
                        runtime = (int) cell.getNumericCellValue();
                    }
                    if (Objects.equals(cell.getColumnIndex(), 6)) {
                        rating = (float) cell.getNumericCellValue();
                    }

                    if (Objects.equals(cell.getColumnIndex(), 7)) {
                        numVotes = (int) cell.getNumericCellValue();
                    }
                    if (Objects.equals(cell.getColumnIndex(), 8)) {
                        revenue = (float) cell.getNumericCellValue();
                    }
                    if (Objects.equals(cell.getColumnIndex(), 9)) {
                        metaScore = (int) cell.getNumericCellValue();
                    }
                    if (Objects.equals(cell.getColumnIndex(), 10)) {
                        pathImagePoster = cell.getStringCellValue();
                    }

                    if (Objects.equals(cell.getColumnIndex(), 11)) {
                        pathImageBrand = cell.getStringCellValue();
                    }
                    if (Objects.equals(cell.getColumnIndex(), 12)) {
                        urlMovie = cell.getStringCellValue();
                    }

                }
            }
            if (Objects.equals(Objects.equals(row.getRowNum(), 0), Boolean.FALSE)) {
                movieRepository.save(Movie.builder().
                        title(title).
                        description(description).
                        idMovie(UUID.randomUUID()).
                        rating(rating).
                        revenue(revenue).
                        actorList(actors).
                        genreList(genres).
                        metaScore(metaScore).
                        year(year).
                        numVotes(numVotes).
                        runtime(runtime).
                        status(TypeEntity.ACTIVE).
                        views(0).
                        urlMovie(urlMovie).
                        pathImagePoster(pathImagePoster).
                        pathImageBrand(pathImageBrand).
                        build());
            }


        }
    }

    private void handleActor(Sheet sheet) {
        for (Row row : sheet) {
            String actorName = "";
            LocalDate dateBirth = LocalDate.now();
            for (Cell cell : row) {
                if (Objects.equals(Objects.equals(cell.getRowIndex(), 0), Boolean.FALSE)) {
                    if (Objects.equals(cell.getColumnIndex(), 0)) {
                        actorName = cell.getStringCellValue();
                    }
                    if (Objects.equals(cell.getColumnIndex(), 1)) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        dateBirth = LocalDate.parse(cell.getStringCellValue(), formatter);
                    }
                }
            }
            if (Objects.equals(Objects.equals(row.getRowNum(), 0), Boolean.FALSE)) {
                actorRepository.save(Actor.builder().
                        idActor(UUID.randomUUID()).
                        status(TypeEntity.ACTIVE).
                        fullName(actorName).
                        birthDate(dateBirth).
                        build());
            }
        }
    }

    public void handleGenres(Sheet sheet) {
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (Objects.equals(Objects.equals(cell.getRowIndex(), 0), Boolean.FALSE)) {
                    if (Objects.equals(Objects.equals(cell.getCellType(), CellType.STRING), Boolean.FALSE)) {
                        throw new BadRequestException("Genre name is invalid");
                    }
                    genreRepository.save(Genre.builder().
                            idType(UUID.randomUUID()).
                            name(cell.getStringCellValue()).
                            status(TypeEntity.ACTIVE).
                            build());
                }
            }

        }

    }

}

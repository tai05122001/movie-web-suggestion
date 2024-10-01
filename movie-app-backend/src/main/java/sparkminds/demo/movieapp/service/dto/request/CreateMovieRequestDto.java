package sparkminds.demo.movieapp.service.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import sparkminds.demo.movieapp.entity.Actor;
import sparkminds.demo.movieapp.entity.Genre;

import java.util.List;

import static sparkminds.demo.movieapp.constant.MessageContant.*;

@Builder
@Data
public class CreateMovieRequestDto {
    @NotNull(message = TITLE_IS_NULL)
    private String title;
    @NotNull(message = DESCRIPTION_IS_NULL)
    private String description;
    @NotNull(message = LIST_GENRES_IS_NULL)
    private List<Genre> genreList;
    @NotNull(message = LIST_ACTORS_IS_NULL)
    private List<Actor> actorList;
    private int year;
    private int runtime;
    @DecimalMin(value = "0.1", message = RATING_MIN_IS_INVALID)
    @DecimalMax(value = "10.0", message = RATING_MAX_IS_INVALID)
    private float rating;
    private int numVotes;
    @DecimalMin(value = "0.1", message = RATING_MIN_IS_INVALID)
    private float revenue;
    @Min(value = 0, message = MIN_META_SCORE_IS_INVALID)
    @Min(value = 100, message = MAX_META_SCORE_IS_INVALID)
    private int metaScore;
}

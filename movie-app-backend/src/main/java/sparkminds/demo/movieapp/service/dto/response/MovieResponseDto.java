package sparkminds.demo.movieapp.service.dto.response;

import lombok.Builder;
import lombok.Data;
import sparkminds.demo.movieapp.entity.Actor;
import sparkminds.demo.movieapp.entity.Genre;
import sparkminds.demo.movieapp.enumeration.TypeEntity;

import java.util.List;
import java.util.UUID;

@Builder
@Data
public class MovieResponseDto {
    private UUID idMovie;
    private String title;
    private String description;
    private List<Genre> genreList;
    private List<Actor> actorList;
    private int year;
    private int runtime;
    private float rating;
    private int numVotes;
    private float revenue;
    private int metaScore;
    private TypeEntity status;
    private Integer views;
    private String pathImagePoster;
    private String pathImageBrand;
    private String urlMovie;
}

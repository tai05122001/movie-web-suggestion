package sparkminds.demo.movieapp.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import sparkminds.demo.movieapp.enumeration.TypeEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
@Document(collection = "movie")
public class Movie {
    @Id
    private UUID idMovie;
    private String title;
    private String description;
    @DBRef
    private List<Genre> genreList;
    @DBRef
    private List<Actor> actorList;
    private Integer year;
    private Integer runtime;
    private Float rating;
    private Integer numVotes;
    private Float revenue;
    private Integer metaScore;
    private TypeEntity status;
    private Integer views;
    private String pathImagePoster;
    private String pathImageBrand;
    private String urlMovie;

}

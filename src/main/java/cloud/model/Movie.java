package cloud.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movies", schema = "public")
public class Movie{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int releaseYear;
    private int runtime;
    private double rating;
    private String director;
    private String description;
    private String ageRating;
    private String imgIdentification;
    @ManyToMany
    private List<User> users;
    @ManyToMany
    private List<Genre> categories;
}

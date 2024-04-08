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
    private String movieName;
    private String ageRating;
    private int duration;
    private String imgIdentification;
    private String director;
    private double imdbRating;
    private String description;
    @ManyToMany
    private List<User> users;
    @ManyToMany
    private List<Category> categories;
}

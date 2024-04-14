package cloud.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "genre", schema = "public")
@JsonIgnoreProperties("movies")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String genreName;
    @ManyToMany(mappedBy = "genres")
    private List<Movie> movies;

    @Override
    public String toString() {
        return genreName;
    }
}

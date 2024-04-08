package cloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cloud.model.Movie;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByMovieTitle(String title);
}


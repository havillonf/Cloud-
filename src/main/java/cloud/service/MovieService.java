package cloud.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import cloud.model.Movie;
import cloud.repository.MovieRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
    public Optional<Movie> findByTitle(String title){
        return movieRepository.findByTitle(title);
    }
    public Long create(Movie Movie){
        return movieRepository.save(Movie).getId();
    }
    public Optional<Movie> findById(Long id){
        return movieRepository.findById(id);
    }
    public void update(Movie Movie){
        movieRepository.save(Movie);
    }
    public void delete(Long id){
        movieRepository.delete(this.findById(id).orElseThrow());
    }

}

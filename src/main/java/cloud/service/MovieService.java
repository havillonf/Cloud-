package cloud.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import cloud.model.Movie;
import cloud.repository.MovieRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;


    public Optional<Movie> findByName(String title){
        return movieRepository.findByMovieTitle(title);
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

package cloud.service;

import cloud.util.S3Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import cloud.model.Movie;
import cloud.repository.MovieRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {
    private final MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> findByTitle(String title){
        return movieRepository.findByTitle(title);
    }

    public Long create(Movie movie, MultipartFile img){
        try {
            S3Utils.uploadFile(movie.getImgIdentification(), img.getInputStream());
            log.info("File uploaded : {}", movie.getTitle());
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return movieRepository.save(movie).getId();
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

    private File convertMultiPartFileToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}

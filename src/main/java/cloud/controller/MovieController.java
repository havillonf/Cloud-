package cloud.controller;

import cloud.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cloud.model.Movie;

import java.util.List;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("movies")
@RequiredArgsConstructor
public class MovieController {
    @Autowired
    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<Movie>> getMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        var movieOpt = movieService.findById(id);
        if(movieOpt.isPresent()){
            return ResponseEntity.ok(movieOpt.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<?> findByTitle(@PathVariable String title){
        var movieOpt = movieService.findByTitle(title);
        if(movieOpt.isPresent()){
            return ResponseEntity.ok(movieOpt.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Movie movie){
        try {
            return created(fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(movieService.create(movie)).toUri())
                    .build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<?> update(@RequestBody Movie movie){
        if (movieService.findById(movie.getId()).isPresent()){
            movieService.update(movie);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try{
            movieService.delete(id);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}

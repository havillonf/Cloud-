package cloud.controller;

import cloud.model.Genre;
import cloud.service.GenreService;
import cloud.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import cloud.model.Movie;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("movies")
@RequiredArgsConstructor
public class MovieController {
    @Autowired
    private final MovieService movieService;
    private final GenreService genreService;

    @GetMapping
    public String index(){
        return "index";
    }

    @GetMapping("list")
    public String listMovies(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "list";
    }

    @GetMapping("/info")
    public String showMovieInfo(@RequestParam("id") Long id, Model model){
        var movieOpt = movieService.findById(id);
        if(movieOpt.isPresent()){
            model.addAttribute("movie", movieOpt.get());
            return "movie_info";
        }
        return "redirect:list";
    }

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("id") Long id, Model model){
        var movieOpt = movieService.findById(id);
        if(movieOpt.isPresent()){
            List<Genre> genres = genreService.getAllGenres();

            model.addAttribute("genres", genres);
            model.addAttribute("movie", movieOpt.get());
            return "movie_form";
        }
        return "redirect:list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model){

        Movie movie = new Movie();

        List<Genre> genres = genreService.getAllGenres();

        model.addAttribute("movie", movie);
        model.addAttribute("genres", genres);
        return "movie_form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id){

        movieService.delete(id);

        return "redirect:list";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("movie") Movie movie, @RequestParam("file") MultipartFile file, @RequestParam("file") Genre genre){

        List<Genre> movie_genres = movie.getGenres();
        movie_genres.clear();
        movie_genres.add(genre);

        movie.setGenres(movie_genres);

        movieService.create(movie, file);

        return "redirect:list";
    }
}
package cloud.controller;

import cloud.model.AgeRatingEnum;
import cloud.model.Genre;
import cloud.repository.GenreRepository;
import cloud.service.GenreService;
import cloud.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import cloud.model.Movie;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("movies")
@RequiredArgsConstructor
@Slf4j
public class MovieController {
    @Autowired
    private final MovieService movieService;
    private final GenreService genreService;
    private final GenreRepository genreRepository;

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

            List<AgeRatingEnum> ratings = Arrays.stream(AgeRatingEnum.values()).toList();
            List<String> stringRatings = new ArrayList<>();
            ratings.forEach(rating -> stringRatings.add(rating.getRating()));

            model.addAttribute("genres", genres);
            model.addAttribute("age_ratings", stringRatings);
            model.addAttribute("movie", movieOpt.get());
            return "movie_form";
        }
        return "redirect:list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model){

        Movie movie = new Movie();

        List<Genre> genres = genreService.getAllGenres();

        List<AgeRatingEnum> ratings = Arrays.stream(AgeRatingEnum.values()).toList();
        List<String> stringRatings = new ArrayList<>();
        ratings.forEach(rating -> stringRatings.add(rating.getRating()));

        //log.info(stringRatings.toString());

        model.addAttribute("movie", movie);
        model.addAttribute("genres", genres);
        model.addAttribute("age_ratings", stringRatings);
        return "movie_form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id){

        movieService.delete(id);

        return "redirect:list";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("movie") Movie movie, @RequestParam("file") MultipartFile file, @RequestParam("genre") Long id){

        List<Genre> movie_genres = new ArrayList<Genre>();

        Optional<Genre> genre_opt = genreRepository.findById(id);

        Genre genre = genre_opt.get();

        movie_genres.add(genre);

        movie.setGenres(movie_genres);

        String fileName = file.getOriginalFilename();

        log.info(fileName);

        int dotIndex = fileName.lastIndexOf('.');

        String fileNameWithoutExtension = (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);

        log.info(fileNameWithoutExtension);

        movie.setImgIdentification(fileNameWithoutExtension);

        movieService.create(movie, file);

        return "redirect:list";
    }
}
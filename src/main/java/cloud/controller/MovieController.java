package cloud.controller;

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
            model.addAttribute("movie", movieOpt.get());
            return "movie_form";
        }
        return "redirect:list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model){

        Movie movie = new Movie();

        model.addAttribute("movie", movie);
        return "movie_form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id){

        movieService.delete(id);

        return "redirect:list";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("movie") Movie movie, @RequestParam("file") MultipartFile file){

        movieService.create(movie, file);

        return "redirect:list";
    }
}
package helton.spring.jwt.api.controllers;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import helton.spring.jwt.api.model.Movie;
import helton.spring.jwt.api.repositories.MovieRepository;
import helton.spring.jwt.api.utils.Authenticator;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class MovieController
{

	@Autowired
	private MovieRepository rep;

	@Autowired
	private Authenticator auth;

	@RequestMapping(path = "/movie", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String createMovie(@RequestBody Movie movie, HttpServletRequest req, HttpServletResponse resp)
	{

		try {
			String token = req.getHeader("Authorization").replace("Bearer ", "");

			if (auth.isAdmin(token)) {

				rep.createMovie(movie);
				return "{\"success\": true}";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
		
		return "{\"success\": false}";
	}

	@RequestMapping(path = "/movie/single/{movieId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Movie getSingleMovie(@PathVariable(name = "movieId", required = true) Long movieId)
	{

		return rep.getMovie(movieId);
	}

	@RequestMapping(path = "/movie/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Movie> getMovieList(@RequestParam(required = false) Integer offset,
			@RequestParam(required = false) Integer limit, @RequestParam(required = false) String director,
			@RequestParam(required = false) String genre)
	{

		return rep.getMovieList(offset, limit, director, genre);

	}

	@RequestMapping(path = "/movie/vote/{movieId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String addVote(@PathVariable("movieId") Long movieId, @RequestParam Integer vote, HttpServletRequest req, HttpServletResponse resp)
	{

		try {
			String token = req.getHeader("Authorization").replace("Bearer ", "");

			if (auth.isDefault(token)) {

				rep.voteInAMovie(movieId, vote);

				return "{\"success\": true}";
			}
			
		} catch (Exception ex) {
			System.out.println(ex);			
		}
		
		resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
		
		return "{\"success\": false}";
	}
}

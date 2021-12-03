package helton.spring.jwt.api.repositories;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import helton.spring.jwt.api.model.Movie;

@Repository
public class MovieRepository
{

	@Autowired
	private SessionFactory sFactory;

	public void createMovie(Movie movie)
	{
		Session session = sFactory.openSession();
		session.save(movie);

	}

	public Movie getMovie(Long movieId)
	{
		Session session = sFactory.openSession();
		Movie movie = session.get(Movie.class, movieId);
		
		return movie;
	}

	public List<Movie> getMovieList(Integer offset, Integer limit, String director, String genre)
	{
		Session session = sFactory.openSession();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Movie> criteria = criteriaBuilder.createQuery(Movie.class);

		Root<Movie> root = criteria.from(Movie.class);

		criteria.select(root);
		criteria.orderBy(criteriaBuilder.desc(root.get("vote")), criteriaBuilder.asc(root.get("title")));

		if (director != null)
			criteria.where(criteriaBuilder.equal(root.get("director"), director));

		if (genre != null)
			criteria.where(criteriaBuilder.equal(root.get("genre"), genre));

		Query<Movie> query = session.createQuery(criteria);
		
		query.setFirstResult(offset != null ? offset : 0);
		
		if(limit != null)
			query.setMaxResults(limit);

		return query.getResultList();
	}

	public void voteInAMovie(Long movieId, Integer vote)
	{
		Session session = sFactory.openSession();
		
		Transaction transaction = session.beginTransaction();
		
		Movie movie = session.load(Movie.class, movieId);
		movie.setVote(movie.getVote() + vote);

		transaction.commit();

	}

}

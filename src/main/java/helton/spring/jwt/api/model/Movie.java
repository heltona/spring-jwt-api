package helton.spring.jwt.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name="MOVIE")
public class Movie
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    
    private String title;
    private String director;
    private String genre;
    private int vote;
    
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getDirector()
	{
		return director;
	}
	public void setDirector(String director)
	{
		this.director = director;
	}
	public String getGenre()
	{
		return genre;
	}
	public void setGenre(String genre)
	{
		this.genre = genre;
	}
	
	public int getVote()
	{
		return vote;
	}
	public void setVote(int vote)
	{
		this.vote = vote;
	}
    
    
}

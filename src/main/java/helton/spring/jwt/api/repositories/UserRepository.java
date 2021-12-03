package helton.spring.jwt.api.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import helton.spring.jwt.api.model.User;

@Repository
public class UserRepository
{
	@Autowired
	private SessionFactory sFactory;
	
	public User getUserById(long id)
	{
		
		Session session = sFactory.openSession();
		User user = session.get(User.class, id);
		
		return user;
	}

	public User createUser(User user)
	{
		Session session = sFactory.openSession();
		Long id = (Long) session.save(user);
		user.setId(id);

		return user;

	}

	public User updateUser(User user)
	{
		Session session = sFactory.openSession();

		Transaction transaction = session.beginTransaction();
		session.update(user);
		transaction.commit();

		return user;

	}

	public User disableUser(long id)
	{
		Session session = sFactory.openSession();

		Transaction transaction = session.beginTransaction();

		User user = session.get(User.class, id);
		user.setEnabled(0);
		session.flush();

		transaction.commit();

		return user;

	}

}

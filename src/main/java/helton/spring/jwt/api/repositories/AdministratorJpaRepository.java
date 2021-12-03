package helton.spring.jwt.api.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import helton.spring.jwt.api.model.User;

@Transactional
@Repository
public class AdministratorJpaRepository
{
	@Autowired
	private EntityManager entityManager;

	public List<User> getAllActiveNotAdministratorUsers(Integer offset, Integer limit)
	{
		
		TypedQuery<User> query = entityManager
				.createQuery("SELECT u FROM User u WHERE u.role = 'DEFAULT' AND u.enabled = 1  ORDER BY u.name", User.class);

		query.setFirstResult(offset != null ? offset : 0);

		if (limit != null) {
			query.setMaxResults(limit);
		}

		return query.getResultList();
	}

	public User createAdministrator(User user)
	{
		entityManager.persist(user);
		entityManager.flush();

		return user;

	}

	public User updateAdministrator(User user)
	{
		entityManager.merge(user);
		entityManager.flush();

		return user;

	}

	public User disableAdministrator(long id)
	{
		User user = entityManager.find(User.class, id);
		user.setEnabled(0);

		entityManager.flush();
		return user;
	}

}

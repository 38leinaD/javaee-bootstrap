package de.dplatz.app.records.control;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.dplatz.app.records.entity.Record;

/**
 * DAO for Record
 */
@Stateless
public class RecordDao {
	@PersistenceContext(unitName = "persistence-unit")
	EntityManager em;

	public void create(Record entity) {
		em.persist(entity);
	}

	public void deleteById(Long id) {
		Record entity = em.find(Record.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public Record findById(Long id) {
		return em.find(Record.class, id);
	}

	public Record update(Record entity) {
		return em.merge(entity);
	}

	public List<Record> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Record> findAllQuery = em.createQuery(
				"SELECT DISTINCT r FROM Record r ORDER BY r.id", Record.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
}

package de.dplatz.app.records.control;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;

import de.dplatz.app.records.control.RecordDao;
import de.dplatz.app.records.entity.Record;

public class RecordDaoTest {
	RecordDao cut;
	EntityManager em;
	
	@Before
	public void init() {
		cut = new RecordDao();
		em = mock(EntityManager.class);
		cut.em = em;
	}
	
	@Test
	public void persistEntity() {
		Record record = new Record();
		record.setId(1L);
		record.setName("duke");
		
		cut.create(record);
		verify(em).persist(record);		
	}
}

package de.dplatz.app.records.control;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.dplatz.app.records.entity.Record;

@RunWith(Arquillian.class)
public class RecordDaoIT {

	@Inject
	private RecordDao recordDao;

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap
				.create(JavaArchive.class)
				.addClasses(RecordDao.class, Record.class)
				.addAsManifestResource("META-INF/persistence.xml",
						"persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Test
	public void createAndGetEntity() {
		assertNotNull(recordDao);
		Record record = new Record();
		record.setName("bob");
		recordDao.create(record);
		Record foundRecord = recordDao.findById(record.getId());
		assertThat(record, is(foundRecord));
	}
}

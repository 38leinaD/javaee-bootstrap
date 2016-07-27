package de.dplatz.app.records.view;

import java.io.File;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import de.dplatz.app.records.entity.Record;

@RunWith(Arquillian.class)
public class CreateRecordUITest {
    private static final String WEBAPP_SRC = "src/main/webapp";
    
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "login.war")
            .addClasses(RecordBean.class, Record.class, ViewUtils.class)
            .addAsWebResource(new File(WEBAPP_SRC + "/record", "create.xhtml"), "record/create.xhtml")
            .addAsWebResource(new File(WEBAPP_SRC + "/record", "search.xhtml"), "record/search.xhtml")
            .merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
                    .importDirectory(WEBAPP_SRC + "/resources").as(GenericArchive.class),
                    "/resources")
            .addAsWebInfResource("META-INF/persistence.xml",
					"classes/META-INF/persistence.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsWebInfResource(
                new StringAsset("<faces-config version=\"2.0\"/>"),
                "faces-config.xml")
            .addAsManifestResource(new File("src/main/webapp/WEB-INF/classes/META-INF/forge.taglib.xml"), "forge.taglib.xml");
    }
    
    @Drone
    private WebDriver browser;
    
    @ArquillianResource
    private URL deploymentUrl;
    
    @Page
    private SearchPage searchPage;
    
    @Test
    @RunAsClient
    public void createSuccesfully(@InitialPage CreatePage createPage) {
        //browser.get(deploymentUrl.toExternalForm() + "record/create.xhtml");
    	final String NAME_CREATED = "billy";
    	createPage.create(NAME_CREATED);
    	searchPage.assertOnSearchPage();
    	searchPage.assertNameListed(NAME_CREATED);
    }
}

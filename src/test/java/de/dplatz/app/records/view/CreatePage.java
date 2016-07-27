package de.dplatz.app.records.view;

import static org.jboss.arquillian.graphene.Graphene.guardHttp;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Location("record/create.xhtml")
public class CreatePage {
    @FindBy(id = "create:recordBeanRecordName")
    private WebElement nameField;

    @FindBy(id = "create:save")
    private WebElement saveButton;
    
    public void create(String name) {
    	nameField.sendKeys(name);
        guardHttp(saveButton).click();
    }
}

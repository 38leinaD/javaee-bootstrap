package de.dplatz.app.records.view;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchPage {
    @FindBy(id = "search:recordBeanPageItems")
    private WebElement table;
    
	public void assertOnSearchPage() {
		Assert.assertNotNull(table);
    }
	
	public void assertNameListed(String name) {
		System.out.println("++TEXT: " +table.getText());
		Assert.assertTrue(table.getText().contains(name));
	}
}

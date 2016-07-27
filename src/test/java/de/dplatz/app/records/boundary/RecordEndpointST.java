package de.dplatz.app.records.boundary;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

public class RecordEndpointST {
	Client client;
	final String RECORDS_URI = "http://localhost:8080/bootstrap/resources/records";

	@Before
	public void init() {
		client = ClientBuilder.newClient();
	}

	@Test
	public void crud() {
		WebTarget tut = client.target(RECORDS_URI);

		JsonObjectBuilder builder = Json.createObjectBuilder();
		JsonObject obj = builder.add("name", "duke")
								.build();
		
		Response createResponse = tut.request().post(Entity.json(obj));
		
		assertThat(createResponse.getStatus(), is(201));
		assertThat(createResponse.getHeaderString("Location"), is(notNullValue()));
		
		tut = client.target(createResponse.getHeaderString("Location"));
		
		Response getResponse = tut.request().get();
		assertThat(getResponse.getStatus(), is(200));
		JsonObject getObject = getResponse.readEntity(JsonObject.class);
		assertThat(getObject.getString("name"), equalTo("duke"));
	}
}

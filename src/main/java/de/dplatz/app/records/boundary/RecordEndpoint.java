package de.dplatz.app.records.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import de.dplatz.app.records.control.RecordDao;
import de.dplatz.app.records.entity.Record;

/**
 * 
 */
@Stateless
@Path("/records")
public class RecordEndpoint {
	@Inject
	RecordDao records;

	@POST
	@Consumes("application/json")
	public Response create(Record entity) {
		records.create(entity);
		return Response.created(
				UriBuilder.fromResource(RecordEndpoint.class)
						.path(String.valueOf(entity.getId())).build()).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id) {
		Record entity = records.findById(id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		records.deleteById(id);
		return Response.noContent().build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Response findById(@PathParam("id") Long id) {
		Record entity = records.findById(id);

		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(entity).build();
	}

	@GET
	@Produces("application/json")
	public List<Record> listAll(@QueryParam("start") Integer startPosition,
			@QueryParam("max") Integer maxResult) {
		if (startPosition == null) {
			startPosition = 0;
		}
		if (maxResult == null) {
			maxResult = 10;
		}
		final List<Record> results = records.listAll(startPosition, maxResult);
		return results;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public Response update(@PathParam("id") Long id, Record entity) {
		if (entity == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (id == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (!id.equals(entity.getId())) {
			return Response.status(Status.CONFLICT).entity(entity).build();
		}
		Record record = records.findById(id);
		if (record == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		try {
			records.update(entity);
		} catch (OptimisticLockException e) {
			return Response.status(Response.Status.CONFLICT)
					.entity(e.getEntity()).build();
		}

		return Response.noContent().build();
	}
}

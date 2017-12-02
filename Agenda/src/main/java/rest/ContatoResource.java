package rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import domain.Contato;
import domain.ContatoService;
import domain.Response;

@Path("/contatos")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ContatoResource {
	private ContatoService contatoService = new ContatoService();

	@GET
	public List<Contato> get() {
		List<Contato> contatos = contatoService.listar();
		return contatos;
	}

	@GET
	@Path("{id}")
	public Contato get(@PathParam("id") long id) {
		Contato c = contatoService.buscarPorId(id);
		return c;
	}

	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") long id) {
		contatoService.excluir(id);
		return Response.Ok("Contato deletado com sucesso");
	}

	@POST
	public Response post(Contato c) {
		contatoService.salvar(c);
		return Response.Ok("Contato salvo com sucesso");
	}

	@PUT
	public Response put(Contato c) {
		contatoService.salvar(c);
		return Response.Ok("Contato atualizado com sucesso");
	}
}

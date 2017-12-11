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

import domain.Denuncia;
import domain.DenunciaService;
import domain.Response;

@Path("/denuncias")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class DenunciaResource {
	private DenunciaService denunciaService = new DenunciaService();

	@GET
	public List<Denuncia> get() {
		List<Denuncia> denuncias = denunciaService.listar();
		return denuncias;
	}

	@GET
	@Path("{id}")
	public Denuncia get(@PathParam("id") int id) {
		Denuncia d = denunciaService.buscarPorId(id);
		return d;
	}

	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") int id) {
		denunciaService.excluir(id);
		return Response.Ok("Denuncia deletado com sucesso");
	}

	@POST
	public Response post(Denuncia d) {
		if (d.getId() != null && d.getId() > 0 && denunciaService.buscarPorId(d.getId()) != null) {
			denunciaService.atualizar(d);
			return Response.Ok("Denuncia atualizado com sucesso");
		}

		denunciaService.salvar(d);
		return Response.Ok("Denuncia salvo com sucesso");
	}

	@PUT
	public Response put(Denuncia d) {
		denunciaService.salvar(d);
		return Response.Ok("Denuncia atualizado com sucesso");
	}
}

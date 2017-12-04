package rest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Base64;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
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
import domain.UploadService;

@Path("/arquivos")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class FileResource {
	private ContatoService contatoService = new ContatoService();

	@GET
	@Path("{id}")
	public String getFotoBase64(@PathParam("id") long id) {
		String imagemBase64 = "";
		try {
			Contato c = contatoService.buscarPorId(id);
			String SAVE_DIR = "C:" + File.separator + "upload";
			String filePath = SAVE_DIR + File.separator + c.getUriFoto();
			imagemBase64 = UploadService.encodeFileToBase64Binary(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imagemBase64;
	}

	@POST
	@Path("/postFotoBase64")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response postFotoBase64(@FormParam("fileName") String fileName, @FormParam("base64") String base64) {
		if (fileName != null && base64 != null) {
			try {
				// Decode: Converte o Base64 para array de bytes
				byte[] bytes = Base64.getMimeDecoder().decode(base64);
				InputStream in = new ByteArrayInputStream(bytes);
				// Faz o upload (salva o arquivo em uma pasta)
				String path = UploadService.upload(fileName, in);
				System.out.println("Arquivo: " + path);
				// OK
				return Response.Ok("Arquivo recebido com sucesso");
			} catch (Exception e) {
				e.printStackTrace();
				return Response.Error("Erro ao enviar o arquivo.");
			}
		}
		return Response.Error("Requisição inválida.");
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

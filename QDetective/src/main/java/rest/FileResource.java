package rest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Base64;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import domain.Denuncia;
import domain.DenunciaService;
import domain.Response;
import domain.UploadService;

@Path("/arquivos")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class FileResource {
	private DenunciaService denunciaService = new DenunciaService();

	@GET
	@Path("{id}")
	public String getFotoBase64(@PathParam("id") int id) {
		String imagemBase64 = "";
		try {
			Denuncia d = denunciaService.buscarPorId(id);
			String SAVE_DIR = "C:" + File.separator + "upload";
			String filePath = SAVE_DIR + File.separator + d.getUriMidia();
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
				byte[] bytes = Base64.getMimeDecoder().decode(base64);
				InputStream in = new ByteArrayInputStream(bytes);
				String path = UploadService.upload(fileName, in);
				System.out.println("Arquivo: " + path);
				return Response.Ok("Arquivo recebido com sucesso");
			} catch (Exception e) {
				e.printStackTrace();
				return Response.Error("Erro ao enviar o arquivo.");
			}
		}
		return Response.Error("Requisição inválida.");
	}
}

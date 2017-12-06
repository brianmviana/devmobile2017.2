package rest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import domain.Denuncia;
import domain.DenunciaService;
import domain.Response;
import domain.UploadService;

@Path("/arquivos")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class FileResource {
	@Context
	private ServletContext context;

	private DenunciaService denunciaService = new DenunciaService();

	@GET
	@Path("{id}")
	public String getFotoBase64(@PathParam("id") int id) {
		String imagemBase64 = "";
		try {
			Denuncia d = denunciaService.buscarPorId(id);
			String SAVE_DIR = context.getInitParameter("diretorioUpload");
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
				String path = UploadService.upload(context.getInitParameter("diretorioUpload"), fileName, in);
				System.out.println("Arquivo: " + path);
				return Response.Ok("Arquivo recebido com sucesso");
			} catch (Exception e) {
				e.printStackTrace();
				return Response.Error("Erro ao enviar o arquivo.");
			}
		}
		return Response.Error("Requisição inválida.");
	}

	@POST
	@Path("/toBase64")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String toBase64(final FormDataMultiPart multiPart) {
		if (multiPart != null) {
			Set<String> keys = multiPart.getFields().keySet();
			for (String key : keys) {
				try {
					// Obtem a InputStream para ler o arquivo
					FormDataBodyPart field = multiPart.getField(key);
					InputStream in = field.getValueAs(InputStream.class);
					byte[] bytes = IOUtils.toByteArray(in);
					String base64 = Base64.getEncoder().encodeToString(bytes);
					return base64;
				} catch (Exception e) {
					e.printStackTrace();
					return "Erro: " + e.getMessage();
				}
			}
		}
		return "Requisição inválida.";
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response postFoto(final FormDataMultiPart multiPart) {
		if (multiPart != null && multiPart.getFields() != null) {
			Set<String> keys = multiPart.getFields().keySet();
			for (String key : keys) {
				// Obtem a InputStream para ler o arquivo
				FormDataBodyPart field = multiPart.getField(key);
				InputStream in = field.getValueAs(InputStream.class);
				try {
					// Salva o arquivo
					String fileName = field.getFormDataContentDisposition().getFileName();

					String path = UploadService.upload(context.getInitParameter("diretorioUpload"), fileName, in);
					System.out.println("Arquivo: " + path);

					return Response.Ok("Arquivo recebido com sucesso");
				} catch (IOException e) {
					e.printStackTrace();
					return Response.Error("Erro ao enviar o arquivo.");
				}
			}
		}
		return Response.Ok("Requisição inválida.");
	}
}

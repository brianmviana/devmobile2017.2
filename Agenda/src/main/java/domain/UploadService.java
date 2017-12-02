package domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.sun.jersey.core.util.Base64;

public class UploadService {
	public static String upload(String fileName, InputStream in) throws IOException {
		if (fileName == null || in == null) {
			throw new IllegalArgumentException("Parâmetros inválidos");
		}
		File tmpDir = new File("C:" + File.separator + "upload");
		if (!tmpDir.exists()) {
			// Cria a pasta carros se não existe
			tmpDir.mkdir();
		}
		// Cria o arquivo
		File file = new File(tmpDir, fileName);
		// Abre a OutputStream para escrever no arquivo
		FileOutputStream out = new FileOutputStream(file);
		// Escreve os dados no arquivo
		byte[] buffer = new byte[4096];
		int bytesRead = -1;

		while ((bytesRead = in.read(buffer)) != -1) {
			out.write(buffer, 0, bytesRead);
		}
		in.close();
		out.close();
		// Retorna o caminho do arquivo
		String path = file.getAbsolutePath();
		return path;
	}

	public static String encodeFileToBase64Binary(String fileName) throws IOException {
		File file = new File(fileName);
		byte[] bytes = loadFile(file);
		byte[] encoded = Base64.encode(bytes);
		String encodedString = new String(encoded);
		return encodedString;
	}

	private static byte[] loadFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		long length = file.length();
		byte[] bytes = new byte[(int) length];

		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}
		is.close();
		return bytes;
	}
}

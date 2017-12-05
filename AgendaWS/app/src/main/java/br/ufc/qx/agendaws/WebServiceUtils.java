package br.ufc.qx.agendaws;

import android.graphics.*;
import android.util.Base64;

import com.google.gson.*;


import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.text.*;
import java.util.*;


public class WebServiceUtils {

    private ArrayList<Contato> contatos;
    private String respostaServidor;

    public WebServiceUtils() {
        this.contatos = new ArrayList<>();
        this.respostaServidor = "";
    }

    public ArrayList<Contato> getContatos() {
        return contatos;
    }

    public String getRespostaServidor() {
        return respostaServidor;
    }

    public List<Contato> getListaContatosJson(String url, String path, String id) {

        String uri = url + path + "/" + id;
        String json = getJSONFromAPI(uri);
        if (!json.isEmpty()) {
            GsonBuilder b = new GsonBuilder().registerTypeAdapter(Date.class, new DateTypeAdapter());
            Gson gson = b.create();
            Contato[] contato = gson.fromJson(json, Contato[].class);
            contatos = new ArrayList<>(Arrays.asList(contato));
            respostaServidor = "Download realizado com sucesso.";
        }
        return contatos;
    }

    private String getJSONFromAPI(String url) {
        String retorno = "";
        try {
            URL apiUrl = new URL(url);
            HttpURLConnection conexao = (HttpURLConnection) apiUrl.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setReadTimeout(15000);
            conexao.setConnectTimeout(15000);
            conexao.connect();

            int codigoResposta = conexao.getResponseCode();
            InputStream is = null;
            if (codigoResposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                is = conexao.getInputStream();
            } else {
                is = conexao.getErrorStream();
            }
            retorno = converterInputStreamToString(is);
            is.close();
            conexao.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            respostaServidor = "Erro ao estabelecer conexão com o servidor.";
        }
        return retorno;
    }

    private String converterInputStreamToString(InputStream is) throws IOException {
        StringBuffer buffer = new StringBuffer();
        String linha = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((linha = br.readLine()) != null) {
            buffer.append(linha);
        }
        br.close();
        return buffer.toString();
    }

    public String downloadImagemBase64(String url, String pathSalvamento, long id) {
        try {
            String imageBase64 = getJSONFromAPI(url + "/" + id);
            byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
            Bitmap imagem = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            File file = new File(pathSalvamento);
            OutputStream fOut = new FileOutputStream(file);
            imagem.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.close();
        } catch (Exception fne) {
            fne.printStackTrace();
            respostaServidor = "Erro ao realizar o download da imagem do servidor.";
        }
        return null;
    }

    public boolean sendContatoJson(String url, Contato contato) {
        String retorno = null;
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Date.class, new DateTypeAdapter());
            Gson gson = builder.setPrettyPrinting().create();
            String jsonObject = gson.toJson(contato);

            URL apiUrl = new URL(url);
            HttpURLConnection conexao = (HttpURLConnection) apiUrl.openConnection();
            conexao.setDoOutput(true);
            conexao.setRequestMethod("POST");
            conexao.setRequestProperty("Content-Type", "application/json");
            conexao.connect();

            DataOutputStream wr = new DataOutputStream(conexao.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
            writer.write(jsonObject.toString());
            writer.close();
            wr.flush();
            wr.close();
            int codigoResposta = conexao.getResponseCode();
            InputStream is = null;
            if (codigoResposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                is = conexao.getInputStream();
            } else {
                is = conexao.getErrorStream();
                respostaServidor = "Resposta inválida do servidor.";
            }
            respostaServidor = converterInputStreamToString(is);
            is.close();
            conexao.disconnect();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            respostaServidor = "Erro ao estabelecer conexão com o servidor.";
        }
        return false;
    }

    public boolean uploadImagemBase64(String url, File foto) {
        try {
            byte[] byteArray = loadFile(foto);
            String encoded = Base64.encodeToString(loadFile(foto), Base64.DEFAULT);
            HashMap<String, String> parametros = new HashMap<>();

            String nomeArquivo = foto.getName();
            if (foto.getName().contains("/")) {
                int beginIndex = foto.getName().lastIndexOf("/") + 1;
                nomeArquivo = foto.getName().substring(beginIndex);
            }
            parametros.put("fileName", nomeArquivo);
            parametros.put("base64", encoded);


            URL apiUrl = new URL(url);
            HttpURLConnection conexao = (HttpURLConnection) apiUrl.openConnection();
            conexao.setDoOutput(true);
            conexao.setInstanceFollowRedirects(false);
            conexao.setRequestMethod("POST");
            conexao.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conexao.setRequestProperty("charset", "UTF-8");
            conexao.setRequestProperty("Content-Length", Integer.toString(getPostDataString(parametros).length()));
            conexao.setUseCaches(false);
            conexao.connect();

            try (DataOutputStream wr = new DataOutputStream(conexao.getOutputStream())) {
                wr.write(getPostDataString(parametros).getBytes());
                wr.flush();
                wr.close();
            }
            int codigoResposta = conexao.getResponseCode();
            InputStream is = null;
            if (codigoResposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                is = conexao.getInputStream();
            } else {
                is = conexao.getErrorStream();
                respostaServidor = "Resposta inválida do servidor.";
            }
            String resposta = converterInputStreamToString(is);
            if (resposta.contains("OK")) {
                respostaServidor = "Arquivo enviado com sucesso.";
            } else {
                respostaServidor = "Erro ao enviar arquivo para servidor.";
            }
            is.close();
            conexao.disconnect();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            respostaServidor = "Erro ao estabelecer conexão com o servidor.";
        }
        return false;
    }

    private byte[] loadFile(File file) throws IOException {
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

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    private class DateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

        private final DateFormat dateFormat;

        public DateTypeAdapter() {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        }

        @Override
        public synchronized JsonElement serialize(Date date, Type type,
                                                  JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(dateFormat.format(date).toString());
        }

        @Override
        public synchronized Date deserialize(JsonElement jsonElement, Type type,
                                             JsonDeserializationContext jsonDeserializationContext) {
            Date data = new Date(Long.parseLong(jsonElement.getAsString()));
            return data;
        }
    }
}

package br.ufc.qx.agendaws;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

class Utils {

    public static Contato getContatoJson(String url, String path, long id) {
        Contato contato = null;
        try {
            String json = getJSONFromAPI(url + path + "/" + id);
            Gson gson = new Gson();
            contato = gson.fromJson(json, Contato.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contato;
    }

    public static List<Contato> getListaContatosJson(String url, String path) {
        ArrayList<Contato> contatos = null;
        try {
            String json = getJSONFromAPI(url + path);
            GsonBuilder b = new GsonBuilder().registerTypeAdapter(Date.class, new DateTypeAdapter());
            Gson gson = b.create();
            Contato[] contato = gson.fromJson(json, Contato[].class);
            contatos = new ArrayList<>(Arrays.asList(contato));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contatos;
    }

    private static String getJSONFromAPI(String url) throws IOException {
        String retorno = "";
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
        return retorno;
    }

    private static String converterInputStreamToString(InputStream is) throws IOException {
        StringBuffer buffer = new StringBuffer();
        String linha = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((linha = br.readLine()) != null) {
            buffer.append(linha);
        }
        br.close();
        return buffer.toString();
    }

    public static String resolverNomeArquivo(String nomeArquivo) {
        int beginIndex = nomeArquivo.lastIndexOf("/") + 1;
        String nome = nomeArquivo.substring(beginIndex);
        return nome;
    }

    public static String downloadImagemBase64(String url, String path, long id) {
        try {
            String imageBase64 = getJSONFromAPI(url + "/" + id);
            byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
            Bitmap imagem = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            File file = new File(path);
            OutputStream fOut = new FileOutputStream(file);
            imagem.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class DateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

        private final DateFormat dateFormat;

        public DateTypeAdapter() {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        }

        @Override
        public synchronized JsonElement serialize(Date date, Type type,
                                                  JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(dateFormat.format(date));
        }

        @Override
        public synchronized Date deserialize(JsonElement jsonElement, Type type,
                                             JsonDeserializationContext jsonDeserializationContext) {
            Date data = new Date(Long.parseLong(jsonElement.getAsString()));
            return data;
        }
    }
}

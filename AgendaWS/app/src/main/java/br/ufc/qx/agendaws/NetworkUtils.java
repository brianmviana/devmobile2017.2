package br.ufc.qx.agendaws;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class NetworkUtils {


    public static HttpURLConnection conectarAPI(String urlString, String metodoHttp) throws IOException {
        HttpURLConnection urlConnection = null;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod(metodoHttp);
        urlConnection.connect();
        return urlConnection;
    }

    private String downloadMidia(String url, String path) {
        try {
            URL enderecoNaWeb = new URL(uriFoto);
            InputStream inputStream = enderecoNaWeb.openStream();
            Bitmap imagem = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            String nomeArquivo = resolverNomeArquivo(uriFoto);
            File file = new File(path, nomeArquivo);
            OutputStream fOut = new FileOutputStream(file);
            imagem.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.close();
            return Uri.fromFile(file).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String resolverNomeArquivo(String nomeArquivo) {
        int beginIndex = nomeArquivo.lastIndexOf("=") + 1;
        String nome = nomeArquivo.substring(beginIndex);
        return nome;
    }
}

package br.ufc.qx.agendaws;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Anibal on 24/11/2017.
 */

public class Utils {

    public Contato getInformacao(String end, String path) {
        String json;
        Contato contato = null;
        json = NetworkUtils.getJSONFromAPI(end);
        contato = parseJson(json, path);
        return contato;
    }

    private Contato parseJson(String json, String path) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Contato contato = new Contato();

            JSONObject jsonObj = new JSONObject(json);
            JSONArray array = jsonObj.getJSONArray("results");

            Date data;

            JSONObject objArray = array.getJSONObject(0);

            contato.setEmail(objArray.getString("email"));
            contato.setCelular(objArray.getString("cell"));
            data = new Date(format.parse(objArray.getString("dob")).getTime());
            contato.setData_aniversario(data);

            JSONObject nome = objArray.getJSONObject("name");
            contato.setNome(nome.getString("first") + nome.getString("last"));

            JSONObject foto = objArray.getJSONObject("picture");
            contato.setUriFoto(baixarImagem(foto.getString("large"), path));

            return contato;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String baixarImagem(String uriFoto, String path) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String resolverNomeArquivo(String nomeArquivo) {
        int beginIndex = nomeArquivo.lastIndexOf("/") + 1;
        String nome = nomeArquivo.substring(beginIndex);
        return nome;
    }
}

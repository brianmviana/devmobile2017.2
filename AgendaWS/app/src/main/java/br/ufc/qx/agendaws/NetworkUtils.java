package br.ufc.qx.agendaws;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Anibal on 24/11/2017.
 */

public class NetworkUtils {


    public static String getJSONFromAPI(String url) {
        String retorno = "";
        try {
            URL apiEnd = new URL(url);
            HttpURLConnection conexao = (HttpURLConnection) apiEnd.openConnection();
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
        }
        return retorno;
    }

    private static String converterInputStreamToString(InputStream is) {
        StringBuffer buffer = new StringBuffer();
        try {
            String linha = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((linha = br.readLine()) != null) {
                buffer.append(linha);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}

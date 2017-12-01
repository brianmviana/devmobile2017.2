package br.ufc.qx.agendaws;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class Utils {

    public List<Contato> do(Long id, String urlString, String path) {
         contatoList = null;
        String json = getDadosAPI(urlString);

        for (int i = 0; i < contatoList.size(); i++) {
            String uri = baixarImagem(urlString + "?filename=" + contatoList.get(i).getUriFoto(), path);
            contatoList.get(i).setUriFoto(uri);
        }
        return contatoList;
    }







    public void enviarContatos(String url, ContatoDAO dao) {
        List<Contato> contatoList = dao.listar();
        for (Contato contato : contatoList) {
            NetworkUtils.sendJSONtoAPI(url, contato);
        }
    }


}

package br.ufc.quixada.alertdialogmenudecontexto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgendaDAO {

    public ArrayList<Map<String, Object>> listaContatos = new ArrayList<Map<String, Object>>();

    private static AgendaDAO uniqueInstance;

    public static AgendaDAO getInstance() {
        if (uniqueInstance == null)
            uniqueInstance = new AgendaDAO();
        return uniqueInstance;
    }

    private AgendaDAO() {
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("id", 1L);
        item.put("foto", R.drawable.foto1);
        item.put("nome", "Charles Jacob Bean");
        item.put("celular", "(88)99991-9999");
        item.put("email", "misterbean@brisanet.com.br");
        listaContatos.add(item);

        item = new HashMap<String, Object>();
        item.put("id", 2L);
        item.put("foto", R.drawable.foto2);
        item.put("nome", "William Henry Gates III");
        item.put("celular", "(88)99992-9999");
        item.put("email", "billgates@brisanet.com.br");
        listaContatos.add(item);
    }

    public void remover(int posicao) {

        listaContatos.remove(posicao);
    }

    public void atualizar(int posicao, String nome, String cel, String email) {
        Map<String, Object> item = listaContatos.get(posicao);
        item.put("nome", nome);
        item.put("celular", cel);
        item.put("email", email);
    }
}

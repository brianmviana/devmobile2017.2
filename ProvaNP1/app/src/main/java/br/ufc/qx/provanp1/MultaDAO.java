package br.ufc.qx.provanp1;

import java.util.*;
import java.text.*;


/**
 * Created by Anibal on 22/10/2017.
 */

class MultaDAO {

    private static MultaDAO instance;
    private final ArrayList<Map<String, Object>> listaMultas;

    public static MultaDAO getInstance() {
        if (instance == null)
            instance = new MultaDAO();
        return instance;
    }

    private MultaDAO() {
        listaMultas = new ArrayList<Map<String, Object>>();
    }

    public void salvar(int imagem, String veiculo, String multa, Date data, String placa) {
        Map<String, Object> item = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        item.put("imagem", imagem);
        item.put("veiculo", veiculo);
        item.put("multa", multa);
        item.put("data", dateFormat.format(data));
        item.put("date", data);
        item.put("placa", placa);
        listaMultas.add(item);
    }


    public void apagar(int posicao) {
        this.listaMultas.remove(posicao);
    }

    public void atualizar(int posicao, int imagem, String veiculo, String multa, Date data, String placa) {
        Map<String, Object> item = listaMultas.get(posicao);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        item.put("imagem", imagem);
        item.put("veiculo", veiculo);
        item.put("multa", multa);
        item.put("data", dateFormat.format(data));
        item.put("date", data);
        item.put("placa", placa);
    }

    public ArrayList<Map<String, Object>> listarTodos() {
        return this.listaMultas;
    }

    public Map<String, Object> buscarPorPosicao(int posicao) {
        return this.listaMultas.get(posicao);
    }
}

package br.ufc.quixada.boaviagem;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufc.quixada.boaviagem.dao.BoaViagemDAO;
import br.ufc.quixada.boaviagem.dominio.Viagem;
import br.ufc.quixada.boaviagem.enums.TipoViagem;

/**
 * Created by Anibal on 21/09/2017.
 */
public class ViagemListActivity extends ListActivity implements ViewBinder {

    private AlertDialog alertDialog;
    private List<Map<String, Object>> viagens;
    private BoaViagemDAO dao;
    private Integer idViagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new BoaViagemDAO();
        String[] chave = {"imagem", "destino", "data", "orcamento"};
        int[] valor = {R.id.tipoViagem, R.id.destino, R.id.data, R.id.valor};

        SimpleAdapter adapter = new SimpleAdapter(this, listarViagens(),
                R.layout.activity_viagem_list, chave, valor);

        adapter.setViewBinder(this);
        setListAdapter(adapter);
    }

    private List<Map<String, Object>> listarViagens() {
        viagens = new ArrayList<Map<String, Object>>();

        List<Viagem> listaViagens = dao.listarViagens();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (Viagem viagem : listaViagens) {
            Map<String, Object> item = new HashMap<String, Object>();
            if (viagem.getTipoViagem() == TipoViagem.VIAGEM_LAZER) {
                item.put("imagem", R.drawable.lazer);
            } else {
                item.put("imagem", R.drawable.negocios);
            }
            item.put("idViagem", viagem.getId());
            item.put("destino", viagem.getDestino());
            item.put("data", dateFormat.format(viagem.getDataSaida()) + " a " + dateFormat.format(viagem.getDataChegada()));
            item.put("orcamento", "Orçamento R$ " + viagem.getOrcamento());
            viagens.add(item);
        }
        return viagens;
    }

    @Override
    public boolean setViewValue(View view, Object o, String s) {
        return false;
    }
}

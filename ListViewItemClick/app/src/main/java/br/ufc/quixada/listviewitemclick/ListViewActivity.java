package br.ufc.quixada.listviewitemclick;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Aníbal on 21/09/2017.
 */

public class ListViewActivity extends ListActivity implements SimpleAdapter.ViewBinder, AdapterView.OnItemClickListener {

    private List<Map<String, Object>> agenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] chave = {"foto", "nome", "celular", "email"};
        int[] valor = {R.id.foto, R.id.nome, R.id.celular, R.id.email};

        SimpleAdapter adapter = new SimpleAdapter(this, listarContatos(), R.layout.activity_list_view, chave, valor);
        adapter.setViewBinder(this);
        setListAdapter(adapter);
        ListView listView = getListView();
        listView.setOnItemClickListener(this);
    }

    private List<? extends Map<String, ?>> listarContatos() {
        agenda = new ArrayList<Map<String, Object>>();
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("id", 1L);
        item.put("foto", R.drawable.foto1);
        item.put("nome", "Charles Jacob Bean");
        item.put("celular", "(88)99991-9999");
        item.put("email", "misterbean@brisanet.com.br");
        agenda.add(item);

        item = new HashMap<String, Object>();
        item.put("id", 2L);
        item.put("foto", R.drawable.foto2);
        item.put("nome", "William Henry Gates III");
        item.put("celular", "(88)99992-9999");
        item.put("email", "billgates@brisanet.com.br");
        agenda.add(item);

        return agenda;

    }


    @Override
    public boolean setViewValue(View view, Object o, String s) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long l) {
        Long idContato = (Long) agenda.get(posicao).get("id");
        int foto = (int) agenda.get(posicao).get("foto");
        String nome = (String) agenda.get(posicao).get("nome");
        String celular = (String) agenda.get(posicao).get("celular");
        String email = (String) agenda.get(posicao).get("email");

        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, ContatoActivity.class);
        bundle.putString("ID", idContato.toString());
        bundle.putString("FOTO", foto+"");
        bundle.putString("NOME", nome);
        bundle.putString("CEL", celular);
        bundle.putString("EMAIL", email);

        intent.putExtras(bundle);
        startActivity(intent);
    }
}

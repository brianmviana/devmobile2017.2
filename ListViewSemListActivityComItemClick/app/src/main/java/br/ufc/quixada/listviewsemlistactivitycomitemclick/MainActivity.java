package br.ufc.quixada.alertdialogokcancel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements SimpleAdapter.ViewBinder, AdapterView.OnItemClickListener {

    private List<Map<String, Object>> agenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] chave = {"foto", "nome", "celular", "email"};
        int[] valor = {R.id.foto, R.id.nome, R.id.celular, R.id.email};

        SimpleAdapter adapter = new SimpleAdapter(this, listarContatos(), R.layout.layout_item_lista, chave, valor);
        ListView listView = findViewById(R.id.lista);
        adapter.setViewBinder(this);
        listView.setAdapter(adapter);
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
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        Long idContato = (Long) agenda.get(pos).get("id");
        int foto = (int) agenda.get(pos).get("foto");
        String nome = (String) agenda.get(pos).get("nome");
        String celular = (String) agenda.get(pos).get("celular");
        String email = (String) agenda.get(pos).get("email");

        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, ContatoActivity.class);
        bundle.putString("ID", idContato.toString());
        bundle.putString("FOTO", foto + "");
        bundle.putString("NOME", nome);
        bundle.putString("CEL", celular);
        bundle.putString("EMAIL", email);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}

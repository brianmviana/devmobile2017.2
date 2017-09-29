package br.ufc.quixada.alertdialogmenudecontexto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity implements SimpleAdapter.ViewBinder,
        AdapterView.OnItemClickListener, MenuDialogFragment.NotificarEscutadorDoDialog {

    private AgendaDAO agenda;
    private SimpleAdapter adapter;
    private ListView listView;

    @Override
    protected void onResume() {
        super.onResume();
        listView.refreshDrawableState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        agenda = AgendaDAO.getInstance();

        String[] chave = {"foto", "nome", "celular", "email"};
        int[] valor = {R.id.foto, R.id.nome, R.id.celular, R.id.email};

        adapter = new SimpleAdapter(this, agenda.listaContatos, R.layout.layout_item_lista, chave, valor);
        listView = findViewById(R.id.lista);
        adapter.setViewBinder(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onDialogExcluiClick(int posicao) {
        agenda.remover(posicao);
        listView.setAdapter(adapter);
        listView.refreshDrawableState();
    }

    @Override
    public void onDialogEditarClick(int posicao) {
        Intent intent = new Intent(this, ContatoActivity.class);
        intent.putExtra("pos", posicao);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        MenuDialogFragment fragmento = new MenuDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        fragmento.setArguments(bundle);
        fragmento.show(this.getFragmentManager(), "confirma");
    }

    @Override
    public boolean setViewValue(View view, Object o, String s) {
        return false;
    }
}

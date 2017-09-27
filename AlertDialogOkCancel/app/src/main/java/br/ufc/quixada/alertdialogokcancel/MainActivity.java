package br.ufc.quixada.alertdialogokcancel;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity implements
                                  SimpleAdapter.ViewBinder,
                                  AdapterView.OnItemClickListener,
                                  ConfirmaDialogFragment.NotificarEscutadorDoDialog{

    private AgendaDAO agenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        agenda = AgendaDAO.getInstance();

        String[] chave = {"foto", "nome", "celular", "email"};
        int[] valor = {R.id.foto, R.id.nome, R.id.celular, R.id.email};

        SimpleAdapter adapter = new SimpleAdapter(this, agenda.listaContatos, R.layout.layout_item_lista, chave, valor);
        ListView listView = findViewById(R.id.lista);
        adapter.setViewBinder(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }



    @Override
    public boolean setViewValue(View view, Object o, String s) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        DialogFragment fragmento = new ConfirmaDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        fragmento.setArguments(bundle);
        fragmento.show(this.getFragmentManager(), "confirma");
    }

    @Override
    public void onDialogSimClick(DialogFragment dialog) {
        int posicao = dialog.getArguments().getInt("pos");
        agenda.remover(posicao);
        finish();
        startActivity(this.getIntent());
    }

    @Override
    public void onDialogNaoClick(DialogFragment dialog) {

    }
}

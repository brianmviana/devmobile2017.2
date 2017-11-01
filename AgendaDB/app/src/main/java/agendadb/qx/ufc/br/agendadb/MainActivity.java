package agendadb.qx.ufc.br.agendadb;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener, MenuDialogFragment.NotificarEscutadorDoDialog {

    private SimpleAdapter adapter;
    private ListView listView;
    private ContatoDAO contatoDAO;
    private List<Map<String, Object>> mapList;

    @Override
    protected void onResume() {
        super.onResume();
        carregarDados();
    }

    @Override
    protected void onDestroy() {
        contatoDAO.close();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contatoDAO = new ContatoDAO(this);
        setContentView(R.layout.activity_main);
        carregarDados();
    }

    public void carregarDados() {
        mapList = contatoDAO.listarContatos();
        String[] chave = {
                DatabaseHelper.Contato.FOTO,
                DatabaseHelper.Contato.NOME,
                DatabaseHelper.Contato.CELULAR,
                DatabaseHelper.Contato.EMAIL,
                DatabaseHelper.Contato.DATA_ANIVERSARIO
        };
        int[] valor = {R.id.foto, R.id.nome, R.id.celular, R.id.email, R.id.aniversario};

        adapter = new SimpleAdapter(this,
                mapList,
                R.layout.layout_item_contato,
                chave,
                valor);
        listView = findViewById(R.id.listaContatos);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    public void criarContato(View view) {
        Intent intent = new Intent(this, ContatoActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDialogExcluiClick(int id) {
        if (contatoDAO.removerContato(id)) {
            carregarDados();
        }
    }

    @Override
    public void onDialogEditarClick(int id) {
        Intent intent = new Intent(this, ContatoActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MenuDialogFragment fragmento = new MenuDialogFragment();
        Map<String, Object> item = mapList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", (int) item.get(DatabaseHelper.Contato._ID));
        fragmento.setArguments(bundle);
        fragmento.show(this.getFragmentManager(), "confirma");
    }
}

package br.ufc.qx.provanp1;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity implements SimpleAdapter.ViewBinder,
        AdapterView.OnItemClickListener, MenuDialogFragment.NotificarEscutadorDoMenuDialog {

    private ListView listView;
    private SimpleAdapter adapter;
    private MultaDAO multaDao;


    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int[] valor = {R.id.veiculoImageView, R.id.veiculoTextView, R.id.multaTtextView, R.id.dataTextView, R.id.placaTextView};
        String[] chave = {"imagem", "veiculo", "multa", "data", "placa"};
        multaDao = MultaDAO.getInstance();
        listView = findViewById(R.id.lista_multas);
        adapter = new SimpleAdapter(this, multaDao.listarTodos(), R.layout.item_lista, chave, valor);
        listView.setAdapter(adapter);
        adapter.setViewBinder(this);
        listView.setOnItemClickListener(this);
    }

    public void multar(View v) {
        Intent intent = new Intent(this, CadastroMultaActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean setViewValue(View view, Object data, String textRepresentation) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MenuDialogFragment fragmento = new MenuDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("posicao", position);
        fragmento.setArguments(bundle);
        fragmento.show(this.getFragmentManager(), "menuDialog");
    }

    @Override
    public void onDialogExcluiClick(int posicao) {
        multaDao.apagar(posicao);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDialogEditarClick(int posicao) {
        Intent intent = new Intent(this, CadastroMultaActivity.class);
        intent.putExtra("posicao", posicao);
        startActivity(intent);
    }
}

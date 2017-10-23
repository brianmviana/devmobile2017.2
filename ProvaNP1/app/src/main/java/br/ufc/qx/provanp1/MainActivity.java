package br.ufc.qx.provanp1;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity implements SimpleAdapter.ViewBinder {

    private ListView listaViewDeMultas;
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
        listaViewDeMultas = findViewById(R.id.lista_multas);
        adapter = new SimpleAdapter(this, multaDao.list(), R.layout.item_lista, chave, valor);
        listaViewDeMultas.setAdapter(adapter);
        adapter.setViewBinder(this);
    }

    public void multar(View v) {
        Intent intent = new Intent(this, CadastroMultaActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean setViewValue(View view, Object data, String textRepresentation) {
        return false;
    }
}

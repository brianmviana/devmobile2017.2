package br.ufc.qx.multadb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

import br.ufc.qx.multadb.dao.MultaDAO;

public class MainActivity extends Activity {

    private ListView listView;
    private MultaDAO multaDAO;
    private SimpleAdapter adapter;
    private List<Map<String, Object>> listaMultas;

    @Override
    protected void onResume(){
        super.onResume();
        carregarDados();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        multaDAO = new MultaDAO(this);
        listView = findViewById(R.id.lista);
    }

    private void carregarDados() {
        listaMultas = multaDAO.listar();
        String fonteDeDados[] = {DataBaseHelper.Multa.TIPO_VEICULO,
                DataBaseHelper.Multa.TIPO_MULTA,
                DataBaseHelper.Multa.PLACA_VEICULO,
                DataBaseHelper.Multa.IMAGEM_VEICULO,
                DataBaseHelper.Multa.DATA_MULTA};

        int itensDoLayout[] = {R.id.tipoVeiculoTextView,
                R.id.tipoMultaTextView,
                R.id.palcaTextView,
                R.id.veiculoImageView,
                R.id.dataTextView};

        adapter = new SimpleAdapter(this, listaMultas, R.layout.layout_item_lista, fonteDeDados, itensDoLayout);
        listView.setAdapter(adapter);
    }

    public void multar(View view) {
        Intent intent = new Intent(this, CadastroMultaActivity.class);
        startActivity(intent);
    }
}

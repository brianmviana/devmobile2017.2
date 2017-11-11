package br.ufc.qx.multadb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import br.ufc.qx.multadb.dao.MultaDAO;

public class MainActivity extends Activity implements MenuDialogFragment.NotificarEscutadorDoDialog, AdapterView.OnItemClickListener {

    private ListView listView;
    private MultaDAO multaDAO;
    private SimpleAdapter adapter;
    private List<Map<String, Object>> listaMultas;

    @Override
    protected void onDestroy() {
        multaDAO.close();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
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

        listaMultas = multaDAO.listar();
        adapter = new SimpleAdapter(this, listaMultas, R.layout.layout_item_lista, fonteDeDados, itensDoLayout);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    public void multar(View view) {
        Intent intent = new Intent(this, CadastroMultaActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDialogExcluiClick(int posicao) {
        Map<String, Object> item = listaMultas.get(posicao);
        int resultado = multaDAO.excluirPorId((long) item.get(DataBaseHelper.Multa._ID));
        if (resultado > 0) {
            mostrarMensagem("Multa excluída com sucesso!");
        } else {
            mostrarMensagem("Erro ao excluir multa.");
        }
        onResume();
    }

    @Override
    public void onDialogEditarClick(int posicao) {
        Map<String, Object> item = listaMultas.get(posicao);
        long id = (long) item.get(DataBaseHelper.Multa._ID);

        Intent intent = new Intent(this, CadastroMultaActivity.class);
        intent.putExtra("id", id);
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

    private void mostrarMensagem(String mensagem) {
        Context contexto = getApplicationContext();
        int duracao = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(contexto, mensagem, duracao);
        toast.show();
    }

}

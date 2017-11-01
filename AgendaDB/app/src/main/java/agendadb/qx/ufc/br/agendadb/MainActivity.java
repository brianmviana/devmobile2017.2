package agendadb.qx.ufc.br.agendadb;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.Calendar;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener, MenuDialogFragment.NotificarEscutadorDoDialog {

    private SimpleCursorAdapter adapter;
    private ListView listView;
    private ContatoDAO contatoDAO;
    private Cursor cursor;


    @Override
    protected void onRestart() {
        super.onRestart();

        String[] chave = {
                DatabaseHelper.Contato.FOTO,
                DatabaseHelper.Contato.NOME,
                DatabaseHelper.Contato.CELULAR,
                DatabaseHelper.Contato.EMAIL,
                DatabaseHelper.Contato.DATA_ANIVERSARIO
        };
        int[] valor = {R.id.foto, R.id.nome, R.id.celular, R.id.email, R.id.aniversario};

        cursor = contatoDAO.listarContatosCursor();
        adapter = new SimpleCursorAdapter(this,
                R.layout.layout_item_contato,
                cursor,
                chave,
                valor,
                0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contatoDAO.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contatoDAO = new ContatoDAO(this);
        cursor = contatoDAO.listarContatosCursor();

        String[] chave = {
                DatabaseHelper.Contato.FOTO,
                DatabaseHelper.Contato.NOME,
                DatabaseHelper.Contato.CELULAR,
                DatabaseHelper.Contato.EMAIL,
                DatabaseHelper.Contato.DATA_ANIVERSARIO
        };
        int[] valor = {R.id.foto, R.id.nome, R.id.celular, R.id.email, R.id.aniversario};

        adapter = new SimpleCursorAdapter(this,
                R.layout.layout_item_contato,
                cursor,
                chave,
                valor,
                0);
        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (columnIndex == 5) {
                    Long date = cursor.getLong(columnIndex);
                    TextView data = (TextView) view;
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(date);
                    data.setText(cal.DAY_OF_MONTH + "/" + cal.MONTH + 1 + "/" + cal.YEAR);
                    return true;
                }
                return false;
            }
        });
        listView = findViewById(R.id.listaContatos);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    public void criarContato(View view) {
        Intent intent = new Intent(this, ContatoActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDialogExcluiClick(long id) {
        contatoDAO.removerContato((int) id);
    }

    @Override
    public void onDialogEditarClick(long id) {
        Intent intent = new Intent(this, ContatoActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MenuDialogFragment fragmento = new MenuDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        fragmento.setArguments(bundle);
        fragmento.show(this.getFragmentManager(), "confirma");
    }
}

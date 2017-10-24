package br.ufc.qx.adaptercervejas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CervejaDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = CervejaDAO.getInstance();
        List<Cerveja> listaDeCerveja = dao.todasAsCervejas();
        ListView listViewDeCerveja = (ListView) findViewById(R.id.lista_de_cervejas);
        AdapterCervejas adapter = new AdapterCervejas(listaDeCerveja, this);
        listViewDeCerveja.setAdapter(adapter);
    }
}
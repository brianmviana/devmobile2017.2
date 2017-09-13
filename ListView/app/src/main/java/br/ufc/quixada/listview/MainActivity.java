package br.ufc.quixada.listview;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] teams = getResources().getStringArray(R.array.paises);

        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_main, R.id.textview, teams));

    }
}

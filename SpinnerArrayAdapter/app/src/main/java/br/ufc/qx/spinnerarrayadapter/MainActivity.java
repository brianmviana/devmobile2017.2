package br.ufc.qx.spinnerarrayadapter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ArrayList<String> cores = new ArrayList<String>();
    private Button button;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cores.add("Vermelho");
        cores.add("Laranja");
        cores.add("Amarelo");
        cores.add("Verde");
        cores.add("Azul");

        spinner = findViewById(R.id.spinner);
        button = findViewById(R.id.button);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cores);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(itemClickListener);
    }

    private AdapterView.OnItemSelectedListener itemClickListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int color = Color.TRANSPARENT;
            switch (position) {
                case 0:
                    color = Color.RED;
                    break;
                case 1:
                    color = Color.rgb(255, 140, 0);
                    break;
                case 2:
                    color = Color.YELLOW;
                    break;
                case 3:
                    color = Color.GREEN;
                    break;
                case 4:
                    color = Color.BLUE;
                    break;
                default:
                    color = Color.TRANSPARENT;
            }
            button.setBackgroundColor(color);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
}

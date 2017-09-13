package br.ufc.qx.linearlayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void vertical(View view) {
        Intent intent = new Intent(MainActivity.this, LinearLayoutVerticalActivity.class);
        startActivity(intent);
    }

    public void horizontal(View view) {
        Intent intent = new Intent(MainActivity.this, LinearLayoutHorizontalActivity.class);
        startActivity(intent);
    }

    public void pesos(View view) {
        Intent intent = new Intent(MainActivity.this, PesosActivity.class);
        startActivity(intent);
    }
}

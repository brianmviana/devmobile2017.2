package br.ufc.qx.helloworld_30;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView saudacaoTextView = findViewById(R.id.saudacaoTextView);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String txt = bundle.getString("txt");
        saudacaoTextView.setText(txt);
    }
}

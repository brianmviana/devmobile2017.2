package br.ufc.qx.helloworld_30;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;


public class MainActivity extends Activity {

    private EditText nomeEditText;
    private TextView saudacaoTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.nomeEditText = findViewById(R.id.nomeEditText);
        this.saudacaoTextView = findViewById(R.id.saudacaoTextView);
    }

    public void surpreenderUsuario(View view) {
        String txt = this.nomeEditText.getText().toString();
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        Bundle bundle = new Bundle();
        bundle.putString("txt", txt);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

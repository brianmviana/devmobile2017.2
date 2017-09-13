package br.ufc.quixada.boaviagem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
    }

    public void selecionarOpcao(View view) {
        if (view.getId() == R.id.nova_viagem) {
            startActivity(new Intent(this, ViagemActivity.class));
        }
    }
}

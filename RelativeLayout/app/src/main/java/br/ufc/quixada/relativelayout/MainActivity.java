package br.ufc.quixada.relativelayout;

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

    public void navegar(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.button1:
                intent = new Intent(this, RelativeLayout1Activity.class);
                break;
            case R.id.button2:
                intent = new Intent(this, RelativeLayout2Activity.class);
                break;
        }
        startActivity(intent);
    }
}

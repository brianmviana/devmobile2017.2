package qx.ufc.br.photoapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends Activity {
    private static final int CAPTURAR_IMAGEM = 1;
    private Uri uri;
    private Boolean possuiCartaoSD = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        possuiCartaoSD = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    private void getPermissoes() {
        String CAMERA = Manifest.permission.CAMERA;
        String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
        int PERMISSION_GRANTED = PackageManager.PERMISSION_GRANTED;

        boolean permissaoCamera = ActivityCompat.checkSelfPermission(this, CAMERA) == PERMISSION_GRANTED;
        boolean permissaoEscrita = ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED;
        boolean permissaoLeitura = ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED;

        if (permissaoCamera && permissaoEscrita && permissaoLeitura) {
            iniciarCapturaDeFotos();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{CAMERA, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 1);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    iniciarCapturaDeFotos();
                } else {
                    Toast.makeText(this, "Sem permissão para uso de câmera.", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    public void capturarImagem(View v) {
        getPermissoes();
    }

    private void iniciarCapturaDeFotos() {
        try {
            setArquivoImagem();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, CAPTURAR_IMAGEM);
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao iniciar a câmera.", Toast.LENGTH_LONG).show();
        }
    }

    private void setArquivoImagem() {
        File diretorio = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        if (!possuiCartaoSD) {
            diretorio = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        }

        File pathImagem = new File(diretorio + "/" + System.currentTimeMillis() + ".jpg");

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            String authority = this.getApplicationContext().getPackageName() + ".fileprovider";
            uri = FileProvider.getUriForFile(this, authority, pathImagem);
        } else {
            uri = Uri.fromFile(pathImagem);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURAR_IMAGEM) {
            if (resultCode == RESULT_OK) {
                ImageView imagem = findViewById(R.id.imagem);
                try {
                    Bitmap bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                    imagem.setImageBitmap(bmp);
                } catch (Exception e) {
                    Toast.makeText(this, "Imagem não encontrada!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Imagem não capturada!",
                        Toast.LENGTH_LONG)
                        .show();
            }
        }
    }
}
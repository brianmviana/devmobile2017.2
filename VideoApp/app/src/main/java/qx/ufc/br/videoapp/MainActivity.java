package qx.ufc.br.videoapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final int CAPTURAR_VIDEO = 1;
    private Uri uri;
    private Boolean possuiCartaoSD = false;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        videoView = findViewById(R.id.videoView);
        possuiCartaoSD = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURAR_VIDEO) {
            if (resultCode == RESULT_OK) {
                String msg = "Vídeo gravado em " + data.getDataString();
                uri = data.getData();
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Video não gravado!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void visualizarVideo(View v) {
        videoView.setVideoURI(uri);
        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc);
        videoView.start();
    }

    public void capturarVideo(View v) {
        getPermissoes();
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
            iniciarGravacaoDeVideo();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{CAMERA, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 1);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    iniciarGravacaoDeVideo();
                } else {
                    Toast.makeText(this, "Sem permissão para uso de câmera.", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }


    private void iniciarGravacaoDeVideo() {
        try {
            setArquivoVideo();
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
            startActivityForResult(intent, CAPTURAR_VIDEO);
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao iniciar a câmera.", Toast.LENGTH_LONG).show();
        }
    }


    private void setArquivoVideo() {
        File diretorio = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        if (!possuiCartaoSD) {
            diretorio = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        }

        File pathVideo = new File(diretorio + "/" + System.currentTimeMillis() + ".mp4");

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            String authority = this.getApplicationContext().getPackageName() + ".fileprovider";
            uri = FileProvider.getUriForFile(this, authority, pathVideo);
        } else {
            uri = Uri.fromFile(pathVideo);
        }
    }
}

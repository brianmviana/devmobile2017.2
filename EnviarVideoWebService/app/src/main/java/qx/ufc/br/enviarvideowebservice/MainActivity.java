package qx.ufc.br.enviarvideowebservice;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;
import android.text.TextUtils;

import java.io.File;
import java.util.Calendar;

public class MainActivity extends Activity {

    private final String url = "http://35.226.50.35/QDetective/rest/";
    //private final String url = "http://10.0.2.2:8080/QDetective/rest/";
    private boolean permisaoInternet = false;
    private static final int CAPTURAR_VIDEO = 1;
    private Uri uri;
    private Boolean possuiCartaoSD = false;
    private VideoView videoView;
    private Spinner categoriaSpinner;
    private EditText descricaoEditText, usuarioEditText;
    private ProgressDialog load;
    private Button enviarButton, capturarButon, visualizarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        videoView = findViewById(R.id.videoView);
        descricaoEditText = findViewById(R.id.descricao);
        usuarioEditText = findViewById(R.id.usuario);
        categoriaSpinner = findViewById(R.id.categoria);
        enviarButton = findViewById(R.id.enviarButton);
        capturarButon = findViewById(R.id.capturar);
        visualizarButton = findViewById(R.id.visualizar);

        categoriaSpinner.setAdapter(new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_item, Categoria.values()));
        categoriaSpinner.setSelection(0, true);
        possuiCartaoSD = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURAR_VIDEO) {
            if (resultCode == RESULT_OK) {
                String msg = "Vídeo gravado em " + data.getDataString();
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
            case 2: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    if (isOnline()) {
                        permisaoInternet = true;
                        return;
                    } else {
                        permisaoInternet = false;
                        Toast.makeText(this, "Sem conexão de Internet.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    permisaoInternet = false;
                    Toast.makeText(this, "Sem permissão para uso de Internet.", Toast.LENGTH_LONG).show();
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
        File diretorio = this.getExternalFilesDir(Environment.DIRECTORY_MOVIES);

        File pathVideo = new File(diretorio + "/" + System.currentTimeMillis() + ".mp4");

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            String authority = this.getApplicationContext().getPackageName() + ".fileprovider";
            uri = FileProvider.getUriForFile(this, authority, pathVideo);
        } else {
            uri = Uri.fromFile(pathVideo);
        }
    }

    private File getDiretorioDeSalvamento(String nomeArquivo) {
        if (nomeArquivo.contains("/")) {
            int beginIndex = nomeArquivo.lastIndexOf("/") + 1;
            nomeArquivo = nomeArquivo.substring(beginIndex);
        }
        File diretorio = this.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        File pathDaImagem = new File(diretorio, nomeArquivo);
        return pathDaImagem;
    }

    private void getPermissaoDaInternet() {
        boolean internet = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
        boolean redeStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED;
        if (internet && redeStatus) {
            if (isOnline()) {
                permisaoInternet = true;
                return;
            } else {
                permisaoInternet = false;
                Toast.makeText(this, "Sem conexão de Internet.", Toast.LENGTH_LONG).show();
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.INTERNET,
                            Manifest.permission.ACCESS_NETWORK_STATE},
                    1);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    class UploadDenuncia extends AsyncTask<Denuncia, Void, WebServiceUtils> {

        @Override
        protected void onPreExecute() {
            load = ProgressDialog.show(MainActivity.this, "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected WebServiceUtils doInBackground(Denuncia... denuncias) {
            WebServiceUtils webService = new WebServiceUtils();
            Denuncia denuncia = denuncias[0];
            String urlDados = url + "denuncias";
            if (webService.sendDenunciaJson(urlDados, denuncia)) {
                urlDados = url + "arquivos/postFotoBase64";
                webService.uploadImagemBase64(urlDados, new File(denuncia.getUriMidia()));
            }
            return webService;
        }

        @Override
        protected void onPostExecute(WebServiceUtils webService) {
            Toast.makeText(getApplicationContext(),
                    webService.getRespostaServidor(),
                    Toast.LENGTH_LONG).show();
            load.dismiss();
        }
    }

    public void realizarUpload(View view) {

        if (TextUtils.isEmpty(descricaoEditText.getText().toString().trim())) {
            descricaoEditText.setError("Campo obrigatório.");
            return;
        }

        if (TextUtils.isEmpty(usuarioEditText.getText().toString().trim())) {
            usuarioEditText.setError("Campo obrigatório.");
            return;
        }

        getPermissaoDaInternet();
        if (permisaoInternet) {
            Denuncia denuncia = new Denuncia();
            denuncia.setData(Calendar.getInstance().getTime());
            denuncia.setDescricao(descricaoEditText.getText().toString());
            denuncia.setUsuario(usuarioEditText.getText().toString());
            denuncia.setLatitude(-4.979501);
            denuncia.setLongitude(-39.0650278);
            denuncia.setUriMidia(getDiretorioDeSalvamento(uri.getPath()).getPath());
            switch (categoriaSpinner.getSelectedItemPosition()) {
                case 0:
                    denuncia.setCategoria(Categoria.VIAS_PUBLICAS);
                    break;
                case 1:
                    denuncia.setCategoria(Categoria.EQUIPAMENTOS_COMUNICATARIOS);
                    break;
                case 2:
                    denuncia.setCategoria(Categoria.LIMPEZA_URBANA);
                    break;
            }
            UploadDenuncia upload = new UploadDenuncia();
            upload.execute(denuncia);
        }
    }
}
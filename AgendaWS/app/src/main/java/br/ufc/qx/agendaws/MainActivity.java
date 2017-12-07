package br.ufc.qx.agendaws;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity implements
        AdapterView.OnItemClickListener,
        MenuDialogFragment.NotificarEscutadorDoDialog, SimpleAdapter.ViewBinder {

    private final String url = "http://107.170.33.111:8080/Agenda/rest/";
    private boolean permisaoInternet = false;
    private SimpleAdapter adapter;
    private ListView listView;
    private ContatoDAO contatoDAO;
    private List<Map<String, Object>> mapList;
    private ImageView fotoAgenda;
    private ProgressDialog load;

    @Override
    protected void onResume() {
        super.onResume();
        carregarDados();
    }

    @Override
    protected void onDestroy() {
        contatoDAO.close();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contatoDAO = new ContatoDAO(this);
        setContentView(R.layout.activity_main);
        carregarDados();
    }

    public void carregarDados() {
        mapList = contatoDAO.listarContatos();
        String[] chave = {
                DatabaseHelper.Contato.FOTO,
                DatabaseHelper.Contato.NOME,
                DatabaseHelper.Contato.CELULAR,
                DatabaseHelper.Contato.EMAIL,
                DatabaseHelper.Contato.DATA_ANIVERSARIO
        };
        int[] valor = {R.id.foto, R.id.nome, R.id.celular, R.id.email, R.id.aniversario};

        adapter = new SimpleAdapter(this,
                mapList,
                R.layout.layout_item_contato,
                chave,
                valor);
        listView = findViewById(R.id.listaContatos);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        fotoAgenda = findViewById(R.id.foto);
        adapter.setViewBinder(this);
    }

    @Override
    public boolean setViewValue(View view, Object data, String textRepresentation) {
        if (view.getId() == R.id.foto) {
            try {
                if (data != null) {
                    String nomeArquivo = data.toString();
                    Uri imgUri = Uri.fromFile(new File(nomeArquivo));
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imgUri));
                    fotoAgenda.setImageBitmap(bitmap);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void criarContato(View view) {
        try {
            Intent intent = new Intent(this, ContatoActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDialogEnviarParaNuvemClick(int id) {
        getPermissaoDaInternet();
        if (permisaoInternet) {
            Contato contato = contatoDAO.buscarContatoPorId(id);
            UploadJson uploadJson = new UploadJson();
            uploadJson.execute(contato);
        }
    }

    @Override
    public void onDialogExcluiClick(int id) {
        Contato contato = contatoDAO.buscarContatoPorId(id);
        String path = contato.getUriFoto();
        if (contatoDAO.removerContato(id)) {
            File file = new File(path);
            file.delete();
            carregarDados();
        }
    }

    @Override
    public void onDialogEditarClick(int id) {
        Intent intent = new Intent(this, ContatoActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MenuDialogFragment fragmento = new MenuDialogFragment();
        Map<String, Object> item = mapList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", (int) item.get(DatabaseHelper.Contato._ID));
        fragmento.setArguments(bundle);
        fragmento.show(this.getFragmentManager(), "confirma");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
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

    private File getDiretorioDeSalvamento(String nomeArquivo) {
        if (nomeArquivo.contains("/")) {
            int beginIndex = nomeArquivo.lastIndexOf("/") + 1;
            nomeArquivo = nomeArquivo.substring(beginIndex);
        }
        File diretorio = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File pathDaImagem = new File(diretorio, nomeArquivo);
        return pathDaImagem;
    }

    public void iniciarDownload(View view) {
        getPermissaoDaInternet();
        if (permisaoInternet) {
            DownloadContatos downloadContatos = new DownloadContatos();
            downloadContatos.execute();
        }
    }

    private class DownloadContatos extends AsyncTask<Long, Void, WebServiceUtils> {
        @Override
        protected void onPreExecute() {
            load = ProgressDialog.show(MainActivity.this, "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected WebServiceUtils doInBackground(Long... ids) {
            WebServiceUtils webService = new WebServiceUtils();
            String id = (ids != null && ids.length == 1) ? ids[0].toString() : "";
            List<Contato> contatos = webService.getListaContatosJson(url, "contatos", id);
            for (Contato contato : contatos) {
                String path = getDiretorioDeSalvamento(contato.getUriFoto()).getPath();
                webService.downloadImagemBase64(url + "arquivos", path, contato.getId());
                contato.setUriFoto(path);
            }
            return webService;
        }

        @Override
        protected void onPostExecute(WebServiceUtils webService) {
            for (Contato contato : webService.getContatos()) {
                Contato c = contatoDAO.buscarContatoPorId(contato.getId());
                if (c != null) {
                    contatoDAO.atualizarContato(contato);
                } else {
                    contatoDAO.inserirContato(contato);
                }
            }
            load.dismiss();
            Toast.makeText(getApplicationContext(), webService.getRespostaServidor(), Toast.LENGTH_LONG).show();
            carregarDados();
        }
    }

    private class UploadJson extends AsyncTask<Contato, Void, WebServiceUtils> {
        @Override
        protected void onPreExecute() {
            load = ProgressDialog.show(MainActivity.this, "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected WebServiceUtils doInBackground(Contato... contatos) {
            WebServiceUtils webService = new WebServiceUtils();
            Contato contato = contatos[0];
            String urlDados = url + "contatos";
            if (webService.sendContatoJson(urlDados, contato)){
                urlDados = url + "arquivos/postFotoBase64";
                webService.uploadImagemBase64(urlDados, getDiretorioDeSalvamento(contato.getUriFoto()));
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
}
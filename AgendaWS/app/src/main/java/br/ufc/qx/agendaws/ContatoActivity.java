package br.ufc.qx.agendaws;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ContatoActivity extends Activity implements DatePickerFragment.EscutadorDoDatePickerDialog {

    private static final int CAPTURAR_IMAGEM = 1;
    private EditText nomeEditText, celularEditText, emailEditText;
    private Button salvarButton, aniversarioButton;
    private ContatoDAO contatoDAO;
    private Contato contato;
    private Date date;
    private Calendar cal;
    private Bundle bundle = null;
    private int id;

    private ImageView fotoAgenda;
    private Uri uri;

    @Override
    protected void onResume() {
        super.onResume();
        bundle = this.getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getInt("id", 0);
            if (id > 0) {
                salvarButton.setText("Atualizar Contato");
                carregarDados(id);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);
        contatoDAO = new ContatoDAO(this);
        contato = new Contato();
        id = 0;
        nomeEditText = findViewById(R.id.nomeEditText);
        celularEditText = findViewById(R.id.celularEditText);
        emailEditText = findViewById(R.id.emailEditText);
        aniversarioButton = findViewById(R.id.aniversarioButton);
        fotoAgenda = findViewById(R.id.fotoAgenda);
        salvarButton = findViewById(R.id.buttonSalvar);

        cal = Calendar.getInstance();
        aniversarioButton.setHint(new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime()));
        fotoAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturarImagem(v);
            }
        });
    }


    public void carregarDados(int id) {
        contato = contatoDAO.buscarContatoPorId(id);
        String foto = contato.getUriFoto();
        String nome = contato.getNome();
        String email = contato.getEmail();
        String celular = contato.getCelular();
        date = contato.getData_aniversario();
        nomeEditText.setText(nome);
        celularEditText.setText(celular);
        emailEditText.setText(email);
        aniversarioButton.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));

        if (foto != null) {
            try {
                uri = Uri.fromFile(getDiretorioDeSalvamento(foto));
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                fotoAgenda.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void salvarContato(View view) {
        if (TextUtils.isEmpty(nomeEditText.getText().toString().trim())) {
            nomeEditText.setError("Campo obrigatório.");
            return;
        }

        if (TextUtils.isEmpty(celularEditText.getText().toString().trim())) {
            celularEditText.setError("Campo obrigatório.");
            return;
        }

        if (TextUtils.isEmpty(emailEditText.getText().toString().trim())) {
            emailEditText.setError("Campo obrigatório.");
            return;
        }
        String nome = nomeEditText.getText().toString();
        String celular = celularEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String foto = null;
        if (uri != null)
            foto = getDiretorioDeSalvamento(uri.toString()).toString();

        contato = new Contato(id, nome, email, celular, foto, date);
        if (id > 0) {
            contatoDAO.atualizarContato(contato);
        } else {
            contatoDAO.inserirContato(contato);
        }
        finish();
    }

    @Override
    public void onDateSelectedClick(Date date) {
        aniversarioButton.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
        this.date = date;
    }

    public void cadastrarData(View view) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(this.getFragmentManager(), "cadastroData");
    }

    private void iniciarCapturaDeFotos() {
        try {
            String nomeArquivo = (contato.getUriFoto() == null) ? System.currentTimeMillis() + ".jpg" : contato.getUriFoto();
            uri = setArquivoImagem(nomeArquivo);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, CAPTURAR_IMAGEM);
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao iniciar a câmera.", Toast.LENGTH_LONG).show();
        }
    }

    private Uri setArquivoImagem(String nomeArquivo) {
        File pathDaImagem = getDiretorioDeSalvamento(nomeArquivo);
        Uri uri = null;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                String authority = "br.ufc.qx.agendaws.fileprovider";
                uri = FileProvider.getUriForFile(this.getApplicationContext(), authority, pathDaImagem);
            } catch (Exception e) {
                Toast.makeText(this, "Erro a acessar o FileProvider.", Toast.LENGTH_LONG).show();
            }
        } else {
            uri = Uri.fromFile(pathDaImagem);
        }
        return uri;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURAR_IMAGEM) {
            if (resultCode == RESULT_OK) {
                RecarregarImagem recarregarImagem = new RecarregarImagem();
                recarregarImagem.execute();
            } else {
                Toast.makeText(this, "Imagem não capturada!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void capturarImagem(View v) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            getPermissoes();
        } else {
            iniciarCapturaDeFotos();
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
                    iniciarCapturaDeFotos();
                } else {
                    Toast.makeText(this, "Sem permissão para uso de câmera.", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private void getPermissoes() {
        boolean camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean leitura = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        boolean escrita = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (camera && leitura && escrita) {
            iniciarCapturaDeFotos();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
    }

    class RecarregarImagem extends AsyncTask<Void, Integer, Bitmap> {
        @Override
        protected Bitmap doInBackground(Void... voids) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                int bmpWidth = bitmap.getWidth();
                int bmpHeight = bitmap.getHeight();
                Matrix matrix = new Matrix();
                matrix.postRotate(0);
                Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bmpWidth, bmpHeight, matrix, true);
                contato.setUriFoto(getDiretorioDeSalvamento(uri.toString()).getPath());
                return resizedBitmap;
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Imagem não encontrada!", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            fotoAgenda.setImageBitmap(bitmap);
        }
    }

}

package br.ufc.quixada.boaviagem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import br.ufc.quixada.boaviagem.dao.BoaViagemDAO;
import br.ufc.quixada.boaviagem.dominio.Viagem;
import br.ufc.quixada.boaviagem.enums.TipoViagem;

/**
 * Created by Anibal on 21/09/2017.
 */
public class ViagemActivity extends Activity {

    private Long idViagem;
    private int ano, mes, dia;
    private Date dataRetorno, dataPartida;
    private Button dataRetornoButton;
    private Button dataPartidaButton;
    private EditText destinoEditText, orcamentoEditText, qtdPessoasEditText;
    private BoaViagemDAO dao;
    private RadioGroup tipoViagemRadioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viagem);
        Calendar calendar = Calendar.getInstance();
        ano = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);

        dataRetornoButton = findViewById(R.id.data_retorno);
        dataPartidaButton = findViewById(R.id.data_partida);
        destinoEditText = findViewById(R.id.destino);
        orcamentoEditText = findViewById(R.id.orcamento);
        qtdPessoasEditText = findViewById(R.id.quantidadePessoas);
        tipoViagemRadioGroup = findViewById(R.id.tipoViagem);
        dao = new BoaViagemDAO();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        idViagem = bundle.getLong("VIAGEM_SELECIONADA", -1);
        if (idViagem != -1) {
            prepararEdicao();
        }
    }

    public void selecionarData(View view) {
        showDialog(view.getId());
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case R.id.data_retorno:
                return new DatePickerDialog(this, dataRetornoListener, ano, mes, dia);

            case R.id.data_partida:
                return new DatePickerDialog(this, dataPartidaListener, ano, mes, dia);
        }
        return null;
    }

    private void prepararEdicao() {
        Viagem viagem = dao.buscarViagemPorId(idViagem);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        if (viagem.getTipoViagem() == TipoViagem.VIAGEM_LAZER) {
            tipoViagemRadioGroup.check(R.id.lazer);
        } else {
            tipoViagemRadioGroup.check(R.id.negocios);
        }

        destinoEditText.setText(viagem.getDestino());
        dataRetorno = viagem.getDataChegada();
        dataPartida = viagem.getDataSaida();
        dataRetornoButton.setText(dateFormat.format(dataRetorno));
        dataPartidaButton.setText(dateFormat.format(dataPartida));
        qtdPessoasEditText.setText(viagem.getQuantidadePessoas().toString());
        orcamentoEditText.setText(viagem.getOrcamento().toString());
    }

    private OnDateSetListener dataRetornoListener = new OnDateSetListener() {
        public void onDateSet(DatePicker view, int anoSelecionado, int mesSelecionado, int diaSelecionado) {
            dataRetorno = criarData(anoSelecionado, mesSelecionado, diaSelecionado);
            dataRetornoButton.setText(diaSelecionado + "/" + (mesSelecionado + 1) + "/" + anoSelecionado);
        }
    };

    private OnDateSetListener dataPartidaListener = new OnDateSetListener() {
        public void onDateSet(DatePicker view, int anoSelecionado, int mesSelecionado, int diaSelecionado) {
            dataPartida = criarData(anoSelecionado, mesSelecionado, diaSelecionado);
            dataPartidaButton.setText(diaSelecionado + "/" + (mesSelecionado + 1) + "/" + anoSelecionado);
        }
    };

    private Date criarData(int anoSelecionado, int mesSelecionado, int diaSelecionado) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(anoSelecionado, mesSelecionado, diaSelecionado);
        return calendar.getTime();
    }

    public void criarViagem(View view) {
        Viagem viagem = new Viagem();
        viagem.setDestino(destinoEditText.getText().toString());
        viagem.setDataChegada(dataRetorno);
        viagem.setDataSaida(dataPartida);
        viagem.setOrcamento(
                Double.valueOf(orcamentoEditText.getText().toString()));
        viagem.setQuantidadePessoas(
                Integer.valueOf(qtdPessoasEditText.getText().toString()));

        int tipo = tipoViagemRadioGroup.getCheckedRadioButtonId();

        if (tipo == R.id.lazer) {
            viagem.setTipoViagem(TipoViagem.VIAGEM_LAZER);
        } else {
            viagem.setTipoViagem(TipoViagem.VIAGEM_NEGOCIOS);
        }

        dao.inserirViagem(viagem);
        Toast.makeText(this, "Viagem Salva!", Toast.LENGTH_SHORT).show();
        finish();
    }
}

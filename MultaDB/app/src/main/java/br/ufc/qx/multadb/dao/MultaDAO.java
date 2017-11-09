package br.ufc.qx.multadb.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufc.qx.multadb.DataBaseHelper;
import br.ufc.qx.multadb.dominio.Multa;

public class MultaDAO {

    private DataBaseHelper helper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public MultaDAO(Context context) {
        helper = DataBaseHelper.getInstance(context);
    }

    public List<Map<String, Object>> listar() {
        db = helper.getReadableDatabase();
        cursor = db.query(DataBaseHelper.Multa.NOME_TABELA,
                DataBaseHelper.Multa.COLUNAS,
                null, null, null, null,
                DataBaseHelper.Multa.DATA_MULTA + " DESC");

        List<Map<String, Object>> lista = new ArrayList<>();

        while (cursor.moveToNext()) {
            Long id = cursor.getLong(0);
            String tipoVeiculo = cursor.getString(1);
            String tipoMulta = cursor.getString(2);
            String placa = cursor.getString(3);
            String imagemVeiculo = cursor.getString(4);
            Date dataMulta = new Date(cursor.getLong(5));

            Map<String, Object> multa = new HashMap<>();
            multa.put(DataBaseHelper.Multa._ID, id);
            multa.put(DataBaseHelper.Multa.TIPO_VEICULO, tipoVeiculo);
            multa.put(DataBaseHelper.Multa.TIPO_MULTA, tipoMulta);
            multa.put(DataBaseHelper.Multa.PLACA_VEICULO, placa);
            multa.put(DataBaseHelper.Multa.IMAGEM_VEICULO, imagemVeiculo);
            multa.put(DataBaseHelper.Multa.DATA_MULTA, fmt.format(dataMulta));
            lista.add(multa);
        }
        db.close();
        return lista;
    }

    public Multa buscarPorId(long id) {
        db = helper.getReadableDatabase();
        String selecao = DataBaseHelper.Multa._ID + " = ?";
        String[] argumentos = {String.valueOf(id)};

        cursor = db.query(DataBaseHelper.Multa.NOME_TABELA,
                DataBaseHelper.Multa.COLUNAS,
                selecao,
                argumentos,
                null, null, null);

        cursor.moveToFirst();
        Multa multa = criarMulta(cursor);
        db.close();
        return multa;
    }

    public long salvar(Multa multa) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.Multa.TIPO_VEICULO, multa.getTipoVeiculo());
        values.put(DataBaseHelper.Multa.TIPO_MULTA, multa.getTipoMulta());
        values.put(DataBaseHelper.Multa.PLACA_VEICULO, multa.getPlaca());
        values.put(DataBaseHelper.Multa.IMAGEM_VEICULO, multa.getImagemVeiculo());
        values.put(DataBaseHelper.Multa.DATA_MULTA, multa.getDataMulta().getTime());

        db = helper.getWritableDatabase();
        long idNovoRegistro = db.insert(DataBaseHelper.Multa.NOME_TABELA, null, values);
        db.close();
        return idNovoRegistro;
    }

    public int atualizar(Multa multa) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.Multa.TIPO_VEICULO, multa.getTipoVeiculo());
        values.put(DataBaseHelper.Multa.TIPO_MULTA, multa.getTipoMulta());
        values.put(DataBaseHelper.Multa.PLACA_VEICULO, multa.getPlaca());
        values.put(DataBaseHelper.Multa.IMAGEM_VEICULO, multa.getImagemVeiculo());
        values.put(DataBaseHelper.Multa.DATA_MULTA, multa.getDataMulta().getTime());

        String selecao = DataBaseHelper.Multa._ID + " = ?";
        String[] argumentos = {String.valueOf(multa.getId())};

        db = helper.getWritableDatabase();

        int linhasAfetadas = db.update(DataBaseHelper.Multa.NOME_TABELA, values, selecao, argumentos);
        db.close();
        return linhasAfetadas;

    }

    public int excluirPorId(long id) {
        String selecao = DataBaseHelper.Multa._ID + " = ?";
        String[] argumentos = {String.valueOf(id)};

        db = helper.getWritableDatabase();
        int linhasApagadas  = db.delete(DataBaseHelper.Multa.NOME_TABELA, selecao, argumentos);
        db.close();
        return linhasApagadas;
    }

    public int excluir(Multa multa) {
        return this.excluirPorId(multa.getId());
    }


    private Multa criarMulta(Cursor cursor) {
        Long _id = cursor.getLong(0);
        String tipoVeiculo = cursor.getString(1);
        String tipoMulta = cursor.getString(2);
        String placa = cursor.getString(3);
        String imagemVeiculo = cursor.getString(4);
        Date dataMulta = new Date(cursor.getLong(5));
        return new Multa(_id, tipoVeiculo, tipoMulta, placa, imagemVeiculo, dataMulta);
    }

    public void close() {
        helper.close();
        db = null;
    }
}

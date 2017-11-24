package agendadb.qx.ufc.br.agendadb;


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

public class ContatoDAO {

    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

    public ContatoDAO(Context context) {
        this.helper = new DatabaseHelper(context);
    }

    public List<Map<String, Object>> listarContatos() {
        db = helper.getReadableDatabase();
        cursor = db.query(DatabaseHelper.Contato.TABELA,
                DatabaseHelper.Contato.COLUNAS,
                null, null, null, null, null);

        List<Map<String, Object>> contatos = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Contato._ID));
            String nome = cursor.getString(cursor.getColumnIndex(DatabaseHelper.Contato.NOME));
            String celular = cursor.getString(cursor.getColumnIndex(DatabaseHelper.Contato.CELULAR));
            String email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.Contato.EMAIL));
            String foto = cursor.getString(cursor.getColumnIndex(DatabaseHelper.Contato.FOTO));
            Date aniversario = new Date(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.Contato.DATA_ANIVERSARIO)));
            Map<String, Object> contato = new HashMap<>();
            contato.put(DatabaseHelper.Contato._ID, id);
            contato.put(DatabaseHelper.Contato.NOME, nome);
            contato.put(DatabaseHelper.Contato.CELULAR, celular);
            contato.put(DatabaseHelper.Contato.EMAIL, email);
            contato.put(DatabaseHelper.Contato.FOTO, Integer.parseInt(foto));
            contato.put(DatabaseHelper.Contato.DATA_ANIVERSARIO, fmt.format(aniversario));
            contatos.add(contato);
        }
        cursor.close();
        return contatos;
    }


    public Contato buscarContatoPorId(Integer id) {
        db = helper.getReadableDatabase();
        cursor = db.query(DatabaseHelper.Contato.TABELA,
                DatabaseHelper.Contato.COLUNAS,
                DatabaseHelper.Contato._ID + " = ?",
                new String[]{id.toString()}, null, null, null);

        if (cursor.moveToNext()) {
            Contato contato = criarContato(cursor);
            cursor.close();
            return contato;
        }
        return null;
    }

    public long inserirContato(Contato contato) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Contato.NOME, contato.getNome());
        values.put(DatabaseHelper.Contato.CELULAR, contato.getCelular());
        values.put(DatabaseHelper.Contato.EMAIL, contato.getEmail());
        values.put(DatabaseHelper.Contato.FOTO, contato.getFoto());
        values.put(DatabaseHelper.Contato.DATA_ANIVERSARIO, contato.getData_aniversario().getTime());

        db = helper.getWritableDatabase();
        long qtdInseridos = db.insert(DatabaseHelper.Contato.TABELA, null, values);
        return qtdInseridos;
    }

    public long atualizarContato(Contato contato) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Contato.NOME, contato.getNome());
        values.put(DatabaseHelper.Contato.CELULAR, contato.getCelular());
        values.put(DatabaseHelper.Contato.EMAIL, contato.getEmail());
        values.put(DatabaseHelper.Contato.FOTO, contato.getFoto());
        values.put(DatabaseHelper.Contato.DATA_ANIVERSARIO, contato.getData_aniversario().getTime());

        db = helper.getWritableDatabase();
        long qtdAtualizados = db.update(DatabaseHelper.Contato.TABELA,
                values,
                DatabaseHelper.Contato._ID + " = ?",
                new String[]{contato.getId().toString()});
        return qtdAtualizados;
    }

    public boolean removerContato(Integer id) {
        db = helper.getWritableDatabase();
        String where[] = new String[]{id.toString()};
        int removidos = db.delete(DatabaseHelper.Contato.TABELA, "_id = ?", where);
        return removidos > 0;
    }

    private Contato criarContato(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Contato._ID));
        String nome = cursor.getString(cursor.getColumnIndex(DatabaseHelper.Contato.NOME));
        String celular = cursor.getString(cursor.getColumnIndex(DatabaseHelper.Contato.CELULAR));
        String email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.Contato.EMAIL));
        String foto = cursor.getString(cursor.getColumnIndex(DatabaseHelper.Contato.FOTO));
        Date aniversario = new Date(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.Contato.DATA_ANIVERSARIO)));
        Contato contato = new Contato(id, nome, email, celular, uriFoto, aniversario);
        return contato;
    }

    public void close() {
        helper.close();
        db = null;
    }
}

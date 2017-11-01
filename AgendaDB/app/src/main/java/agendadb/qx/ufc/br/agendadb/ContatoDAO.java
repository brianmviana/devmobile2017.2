package agendadb.qx.ufc.br.agendadb;


import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.*;

public class ContatoDAO {

    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private Cursor cursor;

    public ContatoDAO(Context context) {
        this.helper = new DatabaseHelper(context);
    }

    public Cursor listarContatosCursor() {
        db = getSqLiteDatabase();
        cursor = db.query(DatabaseHelper.Contato.TABELA,
                DatabaseHelper.Contato.COLUNAS,
                null, null, null, null, "nome ASC");
        return cursor;
    }

    private SQLiteDatabase getSqLiteDatabase() {
        return helper.getWritableDatabase();
    }

    public List<Contato> listarContatos() {
        db = getSqLiteDatabase();
        cursor = db.query(DatabaseHelper.Contato.TABELA,
                DatabaseHelper.Contato.COLUNAS,
                null, null, null, null, null);

        cursor.moveToFirst();
        List<Contato> contatos = new ArrayList<>();

        while (cursor.moveToNext()) {
            Contato contato = criarContato(cursor);
            contatos.add(contato);
        }
        this.close();
        return contatos;
    }


    public Contato buscarContatoPorId(Integer id) {
        db = getSqLiteDatabase();
        cursor = db.query(DatabaseHelper.Contato.TABELA,
                DatabaseHelper.Contato.COLUNAS,
                DatabaseHelper.Contato._ID + " = ?",
                new String[]{id.toString()}, null, null, null);

        if (cursor.moveToNext()) {
            Contato contato = criarContato(cursor);
            this.close();
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
        return db.insert(DatabaseHelper.Contato.TABELA, null, values);
    }

    public long atualizarContato(Contato contato) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Contato.NOME, contato.getNome());
        values.put(DatabaseHelper.Contato.CELULAR, contato.getCelular());
        values.put(DatabaseHelper.Contato.EMAIL, contato.getEmail());
        values.put(DatabaseHelper.Contato.FOTO, contato.getFoto());
        values.put(DatabaseHelper.Contato.DATA_ANIVERSARIO, contato.getData_aniversario().getTime());

        db = helper.getWritableDatabase();
        return db.update(DatabaseHelper.Contato.TABELA,
                values,
                DatabaseHelper.Contato._ID + " = ?",
                new String[]{contato.getId().toString()});
    }

    public boolean removerContato(Integer id) {
        String whereClause = DatabaseHelper.Contato._ID + " = ?";
        String[] whereArgs = new String[]{id.toString()};
        SQLiteDatabase db = helper.getWritableDatabase();
        int removidos =
                db.delete(DatabaseHelper.Contato.TABELA, whereClause, whereArgs);
        return removidos > 0;
    }

    private Contato criarContato(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Contato._ID));
        String nome = cursor.getString(cursor.getColumnIndex(DatabaseHelper.Contato.NOME));
        String celular = cursor.getString(cursor.getColumnIndex(DatabaseHelper.Contato.CELULAR));
        String email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.Contato.EMAIL));
        String foto = cursor.getString(cursor.getColumnIndex(DatabaseHelper.Contato.FOTO));
        Date aniversario = new Date(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.Contato.DATA_ANIVERSARIO)));
        Contato contato = new Contato(id, nome, celular, email, foto, aniversario);
        return contato;
    }

    public void close() {
        if (cursor != null) {
            cursor.close();
        }
        if (db != null) {
            db.close();
            db = null;
        }
    }
}

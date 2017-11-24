package br.ufc.qx.agendaws;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String BANCO_DADOS = "agenda";
    private static final int VERSAO = 1;

    public static class Contato {
        public static final String TABELA = "contato";
        public static final String _ID = "_id";
        public static final String NOME = "nome";
        public static final String EMAIL = "email";
        public static final String CELULAR = "celular";
        public static final String FOTO = "foto";
        public static final String DATA_ANIVERSARIO = "data_aniversario";

        public static final String[] COLUNAS =
                new String[]{_ID, NOME, CELULAR, EMAIL, FOTO, DATA_ANIVERSARIO};
    }

    public DatabaseHelper(Context context) {
        super(context, BANCO_DADOS, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE contato (_id INTEGER PRIMARY KEY," +
                "nome TEXT, email TEXT, celular TEXT," +
                "foto TEXT, data_aniversario DATE);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE contato;");
        onCreate(db);
    }
}

package br.ufc.qx.multadb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String NOME_BD = "multaDB";
    private static final int VERSAO_BD = 1;
    private static DataBaseHelper instance;

    public static DataBaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DataBaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DataBaseHelper(Context context) {
        super(context, NOME_BD, null, VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Multa.CRIAR_TABELA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Multa.EXCLUIR_TABELA);
        onCreate(db);
    }

    public static class Multa {

        public static final String NOME_TABELA = "multa";
        public static final String _ID = "_id";
        public static final String TIPO_VEICULO = "tipo_veiculo";
        public static final String TIPO_MULTA = "tipo_multa";
        public static final String PLACA_VEICULO = "placa_veiculo";
        public static final String IMAGEM_VEICULO = "imagem_veiculo";
        public static final String DATA_MULTA = "data_multa";

        public static final String[] COLUNAS =
                new String[]{_ID, TIPO_VEICULO, TIPO_MULTA, PLACA_VEICULO, IMAGEM_VEICULO, DATA_MULTA};

        public static final String CRIAR_TABELA =
                "CREATE TABLE IF NOT EXISTS " + NOME_TABELA + "(" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        TIPO_VEICULO + " TEXT, " +
                        TIPO_MULTA + " TEXT, " +
                        PLACA_VEICULO + " TEXT, " +
                        IMAGEM_VEICULO + " TEXT, " +
                        DATA_MULTA + " DATE);";

        public static final String EXCLUIR_TABELA = "DROP TABLE " + NOME_TABELA + ";";
    }

}

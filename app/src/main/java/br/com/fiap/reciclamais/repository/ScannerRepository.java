package br.com.fiap.reciclamais.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.reciclamais.model.Registro;

public class ScannerRepository extends SQLiteOpenHelper {


    public static final String NOME_DB = "registros";
    public static final int VERSAO_DB = 1;
    public static final String TB_REGISTROS = "TB_REGISTROS";

    public ScannerRepository(Context context){
        super(context, NOME_DB, null, VERSAO_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TB_REGISTROS + " (" +
                " uid TEXT NOT NULL," +
                " data TEXT NOT NULL )";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void inserir(String uid){
        SQLiteDatabase db = getWritableDatabase();

        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String data = formatter.format(localDate);

        ContentValues cv = new ContentValues();
        cv.put("uid", uid);
        cv.put("data", data);

        db.insert(TB_REGISTROS, null, cv);
    }

    public List<Registro> listar(){
        List<Registro> registros = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TB_REGISTROS,
                new String[]{"uid", "data"},
                null,
                null,
                null,
                null,
                "data"
        );

        if (cursor.moveToFirst()){
            do {
                Registro registro = new Registro();

                registro.setUuid(cursor.getString(0));
                registro.setData(cursor.getString(1));

                registros.add(registro);
            } while (cursor.moveToNext());
        }

        return registros;
    }

    public void limpar(){
        SQLiteDatabase db = getWritableDatabase();

        db.delete(TB_REGISTROS, null, null);
    }

}

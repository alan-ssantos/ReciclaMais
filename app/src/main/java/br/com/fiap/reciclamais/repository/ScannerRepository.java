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

    public List<String> listar(){
        List<String> registros = new ArrayList<>();

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
                String registro = cursor.getString(0) + " / " + cursor.getString(1);

                registros.add(registro);
            } while (cursor.moveToNext());
        }

        return registros;
    }


}

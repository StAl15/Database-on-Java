package com.example.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

//создание бд
class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "moneta.db";
    private static final int DATABASE_VERSION = 4;

    private static final String TABLE_NAME = "my_coins";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NOMINAL = "nominal";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_PRICE = "price";

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMINAL + " TEXT, " +
                COLUMN_YEAR + " TEXT, " +
                COLUMN_PRICE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void add_data(String nominal, String year, String price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NOMINAL, nominal);
        cv.put(COLUMN_YEAR, year);
        cv.put(COLUMN_PRICE, price);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1){
            Toast.makeText(context, "Ошибка", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context, "Успешно сохранено", Toast.LENGTH_LONG).show();

        }
    }
    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;


    }
    void updateData(String row_id, String nominal, String year, String price){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NOMINAL, nominal);
        cv.put(COLUMN_YEAR, year);
        cv.put(COLUMN_PRICE, price);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1){
            Toast.makeText(context, "Ошибка", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Успешно обновлено", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1){
            Toast.makeText(context, "Не удалось удалить", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Успешно удалено", Toast.LENGTH_SHORT).show();
            db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE name='my_coins'"); //обнуление автоинкремента

        }
    }
    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE name='my_coins'"); //обнуление автоинкремента
    }
}

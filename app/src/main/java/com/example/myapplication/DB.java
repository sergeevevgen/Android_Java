package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DB {
    private static final String DB_NAME = "AppDB_3";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "Element";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TXT = "txt";
    public static final String COLUMN_CH = "sel";

    private static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_TXT + " text, " +
                    COLUMN_CH + " integer" +
                    ");";

    private final Context mCtx;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
        mCtx = ctx;
    }

    // открыть подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    // закрыть подключение
    public void close() {
        if (mDBHelper != null)
            mDBHelper.close();
    }

    // получить все данные из таблицы DB_TABLE
    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }

    // добавить запись в DB_TABLE
    public void addElement(@NonNull Element element) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TXT, element.getText());
        if (element.isSelected())
            cv.put(COLUMN_CH, 1);
        else
            cv.put(COLUMN_CH, 0);
        mDB.insert(DB_TABLE, null, cv);
    }

    //Получение всех элементов
    public List<Element> getElements(){
        ArrayList<Element> elements = new ArrayList<>();
        Cursor cursor = getAllData();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String text = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TXT));
            int k = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CH));
            if (k == 0)
                elements.add(new Element(id, text, false));
            else
                elements.add(new Element(id, text, true));
        }
        cursor.close();
        return elements;
    }

    //Получение элемента по айди
    public Element getElement(long id){
        Element element = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?", DB_TABLE, COLUMN_ID);
        Cursor cursor = mDB.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            String text = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TXT));
            int k = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CH));
            if (k == 0)
                element = new Element(id, text, false);
            else
                element = new Element(id, text, true);
        }
        cursor.close();
        return element;
    }

    //Получение числа записей
    public long getCount(){
        return DatabaseUtils.queryNumEntries(mDB, DB_TABLE);
    }

    // удалить запись из DB_TABLE
    public void deleteElement(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }

    public void updateElement(@NonNull Element element){
        String whereClause = COLUMN_ID + "=" + element.getId();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TXT, element.getText());
        if (element.isSelected())
            cv.put(COLUMN_CH, 1);
        else
            cv.put(COLUMN_CH, 0);

        mDB.update(DB_TABLE, cv, whereClause, null);
    }

    // класс по созданию и управлению БД
    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        // создаем и заполняем БД
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }

        @Override
        public synchronized void close() {
            super.close();
        }
    }
}

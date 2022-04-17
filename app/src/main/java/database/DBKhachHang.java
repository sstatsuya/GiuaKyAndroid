package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;

import database.model.DonHang;
import database.model.KhachHang;

public class DBKhachHang {
    public static final String TABLE_NAME = "KHACHHANG";
    public static final String COLUMN_ID = "MAKH";
    public static final String COLUMN_AVATAR = "HINHANH";
    public static final String  COLUMN_NAME = "TENKH";
    public static final String  COLUMN_ADDRESS = "DIACHI";
    public static final String  COLUMN_PHONE = "DIENTHOAI";

    private Context context;
    DBSQLiteOpenHelper dbHelper;

    public DBKhachHang(@Nullable Context contex) {
        this.dbHelper = new DBSQLiteOpenHelper(contex);
        this.context = contex;
    }

    public ArrayList<KhachHang> GetData() {
        ArrayList<KhachHang> data = new ArrayList<>();
        String sql = "select * from " + TABLE_NAME;
        SQLiteDatabase database = this.dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                KhachHang khachHang = new KhachHang();
                khachHang.setMAKH(cursor.getInt(0));
                khachHang.setHINHANH(cursor.getBlob(1));
                khachHang.setTENKH(cursor.getString(2));
                khachHang.setDIACHI(cursor.getString(3));
                khachHang.setDIENTHOAI(cursor.getString(4));
                data.add(khachHang);
            }
            while (cursor.moveToNext());
        }
        return data;
    }
  
    public void insertData(KhachHang khachHang) {
        Log.i("Database", "DBKhachHang.insertData " + khachHang.getMAKH() + " - " + khachHang.getTENKH());

        String sql = "insert into " + TABLE_NAME +"(" + COLUMN_AVATAR + ", " + COLUMN_NAME + ", " + COLUMN_ADDRESS
                + ", " + COLUMN_PHONE + ") values(?,?,?,?)";
        SQLiteDatabase database = this.dbHelper.getWritableDatabase();
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindBlob(1,khachHang.getHINHANH());
        statement.bindString(2, khachHang.getTENKH());
        statement.bindString(3, khachHang.getDIACHI());
        statement.bindString(4, khachHang.getDIENTHOAI());
        statement.executeInsert();
        database.close();
    }


    public void updateData(KhachHang khachHang) { //Hàm Update
        Log.i("Database", "DBKhachHang.updateData " + khachHang.getMAKH() + " - " + khachHang.getTENKH() + " - " + khachHang.getDIENTHOAI() + " - " + khachHang.getDIACHI());

        String sql = "update " + TABLE_NAME +
                " set " + COLUMN_AVATAR + "=?, " + COLUMN_NAME+ "=?, " +COLUMN_ADDRESS + "=?, " + COLUMN_PHONE+ "=? " +
                "where " +COLUMN_ID + "="  + khachHang.getMAKH() ;

        SQLiteDatabase database = this.dbHelper.getWritableDatabase();
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindBlob(1, khachHang.getHINHANH());
        statement.bindString(2, khachHang.getTENKH());
        statement.bindString(3, khachHang.getDIACHI());
        statement.bindString(4, khachHang.getDIENTHOAI());
        statement.executeUpdateDelete();
        database.close();
    }

    public void deleteData(int MAKH) { //Hàm Delete
        Log.i("Database", "DBKhachHang.deleteData " + MAKH);

        String sql = "delete from " + TABLE_NAME + " where " +COLUMN_ID + "="  + MAKH ;
        SQLiteDatabase database = this.dbHelper.getWritableDatabase();
        SQLiteStatement statement = database.compileStatement(sql);
        statement.executeUpdateDelete();
        database.close();
    }

    public String getTenKhachHangByID(int id) {
        DonHang donHang;

        SQLiteDatabase db = this.dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_ID, COLUMN_NAME},
                COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getString(1);
    }
}

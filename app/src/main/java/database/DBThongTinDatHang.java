package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import database.model.DonHang;
import database.model.SanPham;
import database.model.SanPhamDonHang;

public class DBThongTinDatHang {
    private static final String TAG = "Database";

    private static final String TABLE_NAME = "THONGTINDATHANG";
    private static final String COLUMN_ID = "MADH";
    private static final String COLUMN_ID2 = "MASP";
    private static final String COLUMN_QUANTITY = "SOLUONGDAT";

    DBSQLiteOpenHelper dbHelper;

    public DBThongTinDatHang(@Nullable Context contex) {
        this.dbHelper = new DBSQLiteOpenHelper(contex);
    }

    //Customize Database DAO control
    public void getAll() { //Hàm Read
        String sql = "select * from " + TABLE_NAME;
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                System.out.println(cursor.getInt(0) + " - " + cursor.getInt(1) + " - " + cursor.getInt(2));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
    }

    public ArrayList<SanPhamDonHang> getAllByMaDH(int id) {
        ArrayList<SanPhamDonHang> sanPhams = new ArrayList<>();
        SanPhamDonHang sanPhamTemp;
        //query
        String sql = "SELECT SP.MASP, SP.TENSP, SP.XUATXU, SP.DONGIA, SP.HINHANH, DH.SOLUONGDAT\n" +
                    "FROM SANPHAM SP, THONGTINDATHANG DH\n" +
                    "WHERE SP.MASP = DH.MASP AND DH.MADH=" + String.valueOf(id);

        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                sanPhamTemp = new SanPhamDonHang();
                sanPhamTemp.setMaSP(cursor.getInt(0));
                sanPhamTemp.setTenSP(cursor.getString(1));
                sanPhamTemp.setXuatXu(cursor.getString(2));
                sanPhamTemp.setDonGia(cursor.getDouble(3));
                sanPhamTemp.setHinh(cursor.getBlob(4));
                sanPhamTemp.setSoLuong(cursor.getInt(5));

                sanPhams.add(sanPhamTemp);
            }
            while (cursor.moveToNext());
        }
        return sanPhams;
    }

    public int insert(int maDH, int maSP, int soLuong) {
        Log.i(TAG, "DHThongTinDatHang.insert " + maDH + " - " + maSP + " - " + soLuong);

        long rowId = 0;

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_ID, maDH);
        initialValues.put(COLUMN_ID2, maSP);
        initialValues.put(COLUMN_QUANTITY, soLuong);
        rowId = database.insert(TABLE_NAME, null, initialValues);

        database.close();
        return (int)rowId;
    }

    public int update(int maDH, int maSP, int soLuong) {
        Log.i(TAG, "DHThongTinDatHang.update " + maDH + " - " + maSP + " - " + soLuong);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, maDH);
        values.put(COLUMN_ID2, maSP);
        values.put(COLUMN_QUANTITY, soLuong);

        // updating row
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(maDH), String.valueOf(maSP)});
    }

    public void delete(int maDH) {
        Log.i(TAG, "DHThongTinDatHang.insert " + maDH);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[] { String.valueOf(maDH) });
        db.close();
    }

}

package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

import database.model.SanPhamDonHang;
import database.model.ThongTinDonHang;

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

    //Get all data from ThongTinDonHang table
    public ArrayList<ThongTinDonHang> getAll() { //Hàm Read
        ArrayList<ThongTinDonHang> data = new ArrayList<>();
        String sql = "select * from " + TABLE_NAME;
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                ThongTinDonHang thongTinDonHang = new ThongTinDonHang();
                thongTinDonHang.setMaHD(cursor.getInt(0));
                thongTinDonHang.setMaSP(cursor.getInt(1));
                thongTinDonHang.setSoLuongDat(cursor.getInt(2));
                data.add(thongTinDonHang);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return data;
    }

    //Get ThongTinDonHang base on maDH
    public ArrayList<ThongTinDonHang> getAllThongTinDonHangByMaDH(Integer maDH) {
        ArrayList<ThongTinDonHang> data = new ArrayList<>();
        String sql = "select * from " + TABLE_NAME +
        " WHERE MADH = " + String.valueOf(maDH);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                ThongTinDonHang thongTinDonHang = new ThongTinDonHang();
                thongTinDonHang.setMaHD(cursor.getInt(0));
                thongTinDonHang.setMaSP(cursor.getInt(1));
                thongTinDonHang.setSoLuongDat(cursor.getInt(2));
                data.add(thongTinDonHang);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return data;
    }


    public ArrayList<SanPhamDonHang> getAllByMaDH(int id) {
        ArrayList<SanPhamDonHang> sanPhams = new ArrayList<>();
        SanPhamDonHang sanPhamTemp;
        //query
        String sql = "SELECT SP.MASP, SP.TENSP, SP.XUATXU, SP.DONGIA, SP.HINHANH, DH.SOLUONGDAT\n" +
                    "FROM SANPHAM SP, THONGTINDATHANG DH\n" +
                    "WHERE SP.MASP = DH.MASP AND DH.MADH = " + String.valueOf(id);

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

    public void delete(int maDH, int maSP) {
        Log.i(TAG, "DHThongTinDatHang.insert " + maDH + " - " + maSP);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[] { String.valueOf(maDH), String.valueOf(maSP) });
        db.close();
    }
}

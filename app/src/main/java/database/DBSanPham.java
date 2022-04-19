package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import database.model.SanPham;

public class DBSanPham {
    private Context context;
    DBSQLiteOpenHelper dbHelper;

    public DBSanPham(@Nullable Context contex) {
        this.dbHelper = new DBSQLiteOpenHelper(contex);
        this.context = contex;
    }

    public void themDL(SanPham sanPham) { //Hàm Create
        Log.i("Database", "DBSanPham.themDL " + sanPham.getMaSP() + " - " + sanPham.getTenSP());
        String sql = "insert into SANPHAM values(null,?,?,?,?)";
        SQLiteDatabase database = this.dbHelper.getWritableDatabase();
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(1, sanPham.getTenSP());
        statement.bindString(2, sanPham.getXuatXu());
        statement.bindDouble(3, sanPham.getDonGia());
        statement.bindBlob(4, sanPham.getHinh());
        statement.executeInsert();
        database.close();
    }

    public ArrayList<SanPham> docDL() { //Hàm Read
        ArrayList<SanPham> data = new ArrayList<>();
        String sql = "select * from SANPHAM";
        SQLiteDatabase database = this.dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                SanPham sanPham = new SanPham();
                sanPham.setMaSP(cursor.getInt(0));
                sanPham.setTenSP(cursor.getString(1));
                sanPham.setXuatXu(cursor.getString(2));
                sanPham.setDonGia(cursor.getDouble(3));
                sanPham.setHinh(cursor.getBlob(4));
                data.add(sanPham);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return data;
    }

    public void suaDL(SanPham sanPham) { //Hàm Update
        Log.i("Database", "DBSanPham.suaDL " + sanPham.getMaSP() + " - " + sanPham.getTenSP() + " - " + sanPham.getXuatXu() + " - " + sanPham.getDonGia());
        String sql = "update SANPHAM set TENSP=?, XUATXU=?, DONGIA=?, HINHANH=? where MASP="+sanPham.getMaSP();
        SQLiteDatabase database = this.dbHelper.getWritableDatabase();
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(1, sanPham.getTenSP());
        statement.bindString(2, sanPham.getXuatXu());
        statement.bindDouble(3, sanPham.getDonGia());
        statement.bindBlob(4, sanPham.getHinh());
        statement.executeUpdateDelete();
        database.close();
    }

    public void xoaDL(SanPham sanPham) { //Hàm Delete
        Log.i("Database", "DBSanPham.delete " + sanPham.getMaSP());
        String sql = "delete from SANPHAM where MASP="+sanPham.getMaSP();
        SQLiteDatabase database = this.dbHelper.getWritableDatabase();
        SQLiteStatement statement = database.compileStatement(sql);
        statement.executeUpdateDelete();
        database.close();
    }

    //Get all SanPham base on maSP
    public SanPham getSanPhamByMaSP(int maSP) { //Hàm Read
        Log.i("Database", "DBSanPham.getSanphamByMaSP " + maSP);
        String sql = "select * from SANPHAM" +
        " WHERE MASP = " + String.valueOf(maSP);
        SQLiteDatabase database = this.dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                SanPham sanPham = new SanPham();
                sanPham.setMaSP(cursor.getInt(0));
                sanPham.setTenSP(cursor.getString(1));
                sanPham.setXuatXu(cursor.getString(2));
                sanPham.setDonGia(cursor.getDouble(3));
                sanPham.setHinh(cursor.getBlob(4));
                return sanPham;
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return null;
    }
}

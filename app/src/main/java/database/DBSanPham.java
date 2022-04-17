package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import database.model.SanPham;

public class DBSanPham extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "GIUAKYANDROID";
    private static final int DATABASE_VERSION = 1;

    // SQL CREATE TABLE SANPHAM
    private static final String DBCREATE_SQLSANPHAM = "CREATE TABLE SANPHAM (" +
            "    MASP    INTEGER PRIMARY KEY AUTOINCREMENT," +
            "    TENSP   TEXT," +
            "    XUATXU  TEXT," +
            "    DONGIA  DOUBLE," +
            "    HINHANH BLOB );" ;

    // SQL CREATE TABLE KHACHHANG
    private static final String DBCREATE_SQLKHACHHANG = "CREATE TABLE KHACHHANG (" +
            "    MAKH    INTEGER PRIMARY KEY AUTOINCREMENT," +
            "    HINHANH BLOB," +
            "    TENKH   TEXT," +
            "    DIACHI  TEXT," +
            "    DIENTHOAI  TEXT" +
            ");";
    // SQL CREATE TABLE DONHANG
    private static final String DBCREATE_SQLDONHANG = "CREATE TABLE DONHANG (" +
            "    MADH   INTEGER PRIMARY KEY AUTOINCREMENT," +
            "    NGAYDH DATE," +
            "    MAKH           REFERENCES KHACHHANG (MAKH) ON DELETE CASCADE" +
            "                                               ON UPDATE CASCADE" +
            "                                               MATCH SIMPLE" +
            ");";
    // SQL CREATE TABLE THONGTINDATHANG
    private static final String DBCREATE_SQLTHONGTINDATHANG = "CREATE TABLE THONGTINDATHANG (" +
            "    MADH       INTEGER REFERENCES DONHANG (MADH) ON DELETE CASCADE" +
            "                                                 ON UPDATE CASCADE" +
            "                                                 MATCH SIMPLE," +
            "    MASP       INTEGER REFERENCES SANPHAM (MASP) ON DELETE CASCADE" +
            "                                                 ON UPDATE CASCADE," +
            "    SOLUONGDAT INTEGER," +
            "    PRIMARY KEY (MADH, MASP)" +
            ");";

    public DBSanPham(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DBCREATE_SQLKHACHHANG);
        sqLiteDatabase.execSQL(DBCREATE_SQLSANPHAM);
        sqLiteDatabase.execSQL(DBCREATE_SQLDONHANG);
        sqLiteDatabase.execSQL(DBCREATE_SQLTHONGTINDATHANG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists KHACHHANG" );
        sqLiteDatabase.execSQL("Drop table if exists SANPHAM" );
        sqLiteDatabase.execSQL("Drop table if exists DONHANG" );
        sqLiteDatabase.execSQL("Drop table if exists THONGTINDATHANG" );
    }

    public void themDL(SanPham sanPham) { //Hàm Create
        String sql = "insert into SANPHAM values(null,?,?,?,?)";
        SQLiteDatabase database = getWritableDatabase();
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
        SQLiteDatabase database = getReadableDatabase();
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
        String sql = "update SANPHAM set TENSP=?, XUATXU=?, DONGIA=?, HINHANH=? where MASP="+sanPham.getMaSP();
        SQLiteDatabase database = getWritableDatabase();
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(1, sanPham.getTenSP());
        statement.bindString(2, sanPham.getXuatXu());
        statement.bindDouble(3, sanPham.getDonGia());
        statement.bindBlob(4, sanPham.getHinh());
        statement.executeUpdateDelete();
        database.close();
    }

    public void xoaDL(SanPham sanPham) { //Hàm Delete
        String sql = "delete from SANPHAM where MASP="+sanPham.getMaSP();
        SQLiteDatabase database = getWritableDatabase();
        SQLiteStatement statement = database.compileStatement(sql);
        statement.executeUpdateDelete();
        database.close();
    }

    //Get all SanPham base on maSP
    public SanPham getSanPhamByMaSP(int maSP) { //Hàm Read
        String sql = "select * from SANPHAM" +
        " WHERE MASP = " + String.valueOf(maSP);
        SQLiteDatabase database = getReadableDatabase();
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

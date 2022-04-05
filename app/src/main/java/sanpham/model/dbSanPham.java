package sanpham.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class dbSanPham extends SQLiteOpenHelper {
    public dbSanPham(@Nullable Context context) {
        super(context, "GIUAKYANDROID", null, 1); //dbMonHoc = tên database
    }

    // 2 hàm Override dưới đây sẽ tự động chạy mà không cần gọi
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE SANPHAM (MASP INTEGER PRIMARY KEY AUTOINCREMENT, TENSP TEXT, XUATXU TEXT, DONGIA DOUBLE, HINHANH BLOB);"; //Lệnh này để tạo bảng
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

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

}

package khachhang.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBKhachHang extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "KHACHHANG";
    public static final String COLUMN_ID = "MAKH";
    public static final String COLUMN_AVATAR = "HINHANH";
    public static final String  COLUMN_NAME = "TENKH";
    public static final String  COLUMN_ADDRESS = "DIACHI";
    public static final String  COLUMN_PHONE = "DIENTHOAI";

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
            "    MADH   INTEGER PRIMARY KEY," +
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

    public DBKhachHang(@Nullable Context context) {
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

    public ArrayList<KhachHang> GetData() {
        ArrayList<KhachHang> data = new ArrayList<>();
        String sql = "select * from " + TABLE_NAME;
        SQLiteDatabase database = getReadableDatabase();
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

    public void InsertData(KhachHang khachHang) {
        String sql = "insert into " + TABLE_NAME +"(" + COLUMN_AVATAR + ", " + COLUMN_NAME + ", " + COLUMN_ADDRESS
                + ", " + COLUMN_PHONE + ") values(?,?,?,?)";
        SQLiteDatabase database = getWritableDatabase();
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindBlob(1,khachHang.getHINHANH());
        statement.bindString(2, khachHang.getTENKH());
        statement.bindString(3, khachHang.getDIACHI());
        statement.bindString(4, khachHang.getDIENTHOAI());
        statement.executeInsert();
        database.close();
    }


    public void updateData(KhachHang khachHang) { //Hàm Update
        String sql = "update " + TABLE_NAME +
                " set " + COLUMN_AVATAR + "=?, " + COLUMN_NAME+ "=?, " +COLUMN_ADDRESS + "=?, " + COLUMN_PHONE+ "=? " +
                "where " +COLUMN_ID + "="  + khachHang.getMAKH() ;

        SQLiteDatabase database = getWritableDatabase();
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindBlob(1, khachHang.getHINHANH());
        statement.bindString(2, khachHang.getTENKH());
        statement.bindString(3, khachHang.getDIACHI());
        statement.bindString(4, khachHang.getDIENTHOAI());
        statement.executeUpdateDelete();
        database.close();
    }

    public void DeleteData(int MAKH) { //Hàm Delete
        String sql = "delete from " + TABLE_NAME + " where " +COLUMN_ID + "="  + MAKH ;
        SQLiteDatabase database = getWritableDatabase();
        SQLiteStatement statement = database.compileStatement(sql);
        statement.executeUpdateDelete();
        database.close();
    }
}

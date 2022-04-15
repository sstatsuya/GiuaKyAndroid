package database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBSQLiteOpenHelper extends SQLiteOpenHelper {

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

    public DBSQLiteOpenHelper(@Nullable Context context) {
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
}

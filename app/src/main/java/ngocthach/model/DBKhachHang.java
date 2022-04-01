package ngocthach.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBKhachHang extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "KhachHang";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_AVATAR = "avatar";
    public static final String  COLUMN_NAME = "name";
    public static final String  COLUMN_ADDRESS = "address";
    public static final String  COLUMN_PHONE = "phone";

    private static final String DATABASE_NAME = "DBKhachHang";
    private static final int DATABASE_VERSION = 1;

    private static final String DBCREATE_SQL = "Create table " +
            TABLE_NAME + "( " + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_AVATAR + " text not null, " +  COLUMN_NAME + " text not null, " +
            COLUMN_ADDRESS + " text not null, " +  COLUMN_PHONE + " text not null );";

    public DBKhachHang(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DBCREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

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
                khachHang.setId(cursor.getInt(0));
                khachHang.setAvatar(cursor.getString(1));
                khachHang.setName(cursor.getString(2));
                khachHang.setAddress(cursor.getString(3));
                khachHang.setPhone(cursor.getString(4));
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
        database.execSQL (sql,new String[]{ khachHang.getAvatar(), khachHang.getName(), khachHang.getAddress(), khachHang.getPhone()});
        database.close();
    }
}

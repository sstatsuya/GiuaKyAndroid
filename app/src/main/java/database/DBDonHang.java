package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import database.model.DonHang;
import database.model.KhachHang;
import database.model.SanPhamDonHang;

public class DBDonHang {
    private static final String TAG = "Database";

    private static final String TABLE_NAME = "DONHANG";
    private static final String COLUMN_ID = "MADH";
    private static final String COLUMN_TIME = "NGAYDH";
    private static final String COLUMN_CUSTOMER = "MAKH";

    private Context context;


    DBSQLiteOpenHelper dbHelper;

    public DBDonHang(@Nullable Context contex) {
        this.dbHelper = new DBSQLiteOpenHelper(contex);
        this.context = contex;
    }

    //Customize Database DAO control
    public ArrayList<DonHang> getAll() {
        ArrayList<DonHang> donHangs = new ArrayList<>();
        DonHang donHangTemp;
        ArrayList<SanPhamDonHang> danhSachSanPham;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String sql = "SELECT DH.MADH, DH.MAKH, KH.TENKH, DH.NGAYDH\n" +
                "FROM DONHANG DH, KHACHHANG KH\n" +
                "WHERE DH.MAKH = KH.MAKH ORDER BY DH.MADH DESC";
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                donHangTemp = new DonHang();
                donHangTemp.setMaDH(cursor.getInt(0));
                donHangTemp.setMaKH(cursor.getInt(1));
                donHangTemp.setTenKH(cursor.getString(2));
                try {
                    donHangTemp.setNgayDatHang(dateFormat.parse(cursor.getString(3)));
                } catch (ParseException e) {
                    Log.i(TAG, "DBDonHang.getAll format date errors " + donHangTemp.getMaDH() + " - " + e.toString());
                    e.printStackTrace();
                }

                try {
                    donHangTemp.setSanPhamDonHangs(new DBThongTinDatHang(this.context).getAllByMaDH(donHangTemp.getMaDH()));
                } catch (Exception e) {
                    Log.i(TAG, "DBDonHang.getAll get all product list by orderID " + donHangTemp.getMaDH() + " - " + e.toString());
                    e.printStackTrace();
                }

                donHangs.add(donHangTemp);
            }
            while (cursor.moveToNext());
        }
        return donHangs;
    }

    public int insert(DonHang donHangInput) {
        Log.i(TAG, "DBDonHang.insert " + donHangInput.getMaKH() + donHangInput.getNgayDatHang());

        long rowId = 0;
//        String sql = "insert into " + TABLE_NAME +"(" + COLUMN_TIME + ", " + COLUMN_CUSTOMER + ") values(?,?)";

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        // set the format to sql date time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //create order
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_CUSTOMER, donHangInput.getMaKH());
        initialValues.put(COLUMN_TIME, dateFormat.format(donHangInput.getNgayDatHang()));
        rowId = database.insert(TABLE_NAME, null, initialValues);
        //update product list
        for(SanPhamDonHang i: donHangInput.getSanPhamDonHangs()){
            new DBThongTinDatHang(this.context).insert((int)rowId, i.getMaSP(), i.getSoLuong());
        }

        database.close();
        return (int)rowId;
    }

    public int update(DonHang donHangInput) {
        Log.i(TAG, "DBDonHang.update "  + donHangInput.getMaDH());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        ContentValues values = new ContentValues();
        values.put(COLUMN_CUSTOMER, donHangInput.getMaKH());
        values.put(COLUMN_TIME, dateFormat.format(donHangInput.getNgayDatHang()));

        // updating row
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(donHangInput.getMaDH())});
    }

    public void delete(int id) {
        Log.i(TAG, "DBDonHang.delete " + id);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public DonHang get(int id) {
        Log.i(TAG, "DBDonHang.get " + String.valueOf(id));
        DonHang donHang = new DonHang();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "SELECT DH.MADH, DH.MAKH, KH.TENKH, DH.NGAYDH\n" +
                "FROM DONHANG DH, KHACHHANG KH\n" +
                "WHERE DH.MAKH = KH.MAKH AND DH.MADH=" + String.valueOf(id);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null)
            cursor.moveToFirst();

        donHang.setMaDH(cursor.getInt(0));
        donHang.setMaKH(cursor.getInt(1));
        donHang.setTenKH(cursor.getString(2));
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            donHang.setNgayDatHang(df.parse(cursor.getString(3)));
        } catch (ParseException e) {
            Log.i(TAG, "DBDonHang.get format date errors " + donHang.getMaDH() + " - " + e.toString());
            e.printStackTrace();
        }

        donHang.setSanPhamDonHangs(new DBThongTinDatHang(context).getAllByMaDH(id));

        return donHang;
    }



    public void updateData(KhachHang khachHang) { //Hàm Update
//        String sql = "update " + TABLE_NAME +
//                " set " + COLUMN_AVATAR + "=?, " + COLUMN_NAME+ "=?, " +COLUMN_ADDRESS + "=?, " + COLUMN_PHONE+ "=? " +
//                "where " +COLUMN_ID + "="  + khachHang.getMAKH() ;
//
//        SQLiteDatabase database = getWritableDatabase();
//        SQLiteStatement statement = database.compileStatement(sql);
//        statement.bindBlob(1, khachHang.getHINHANH());
//        statement.bindString(2, khachHang.getTENKH());
//        statement.bindString(3, khachHang.getDIACHI());
//        statement.bindString(4, khachHang.getDIENTHOAI());
//        statement.executeUpdateDelete();
//        database.close();
    }

    public void DeleteData(int MAKH) { //Hàm Delete
//        String sql = "delete from " + TABLE_NAME + " where " +COLUMN_ID + "="  + MAKH ;
//        SQLiteDatabase database = getWritableDatabase();
//        SQLiteStatement statement = database.compileStatement(sql);
//        statement.executeUpdateDelete();
//        database.close();
    }
    public ArrayList<DonHang> getAllByYear(int year) {
        ArrayList<DonHang> donHangs = new ArrayList<>();
        DonHang donHangTemp;
        ArrayList<SanPhamDonHang> danhSachSanPham;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String sql = "SELECT DH.MADH, DH.MAKH, KH.TENKH, DH.NGAYDH\n" +
                "FROM DONHANG DH, KHACHHANG KH\n" +
                "WHERE DH.MAKH = KH.MAKH AND substr(DH.NGAYDH, 1, 4) = '" + String.valueOf(year) + "';";
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                donHangTemp = new DonHang();
                donHangTemp.setMaDH(cursor.getInt(0));
                donHangTemp.setMaKH(cursor.getInt(1));
                donHangTemp.setTenKH(cursor.getString(2));
                try {
                    donHangTemp.setNgayDatHang(dateFormat.parse(cursor.getString(3)));
                } catch (ParseException e) {
                    Log.i(TAG, "DBDonHang.getAll format date errors " + donHangTemp.getMaDH() + " - " + e.toString());
                    e.printStackTrace();
                }

                try {
                    donHangTemp.setSanPhamDonHangs(new DBThongTinDatHang(this.context).getAllByMaDH(donHangTemp.getMaDH()));
                } catch (Exception e) {
                    Log.i(TAG, "DBDonHang.getAll get all product list by orderID " + donHangTemp.getMaDH() + " - " + e.toString());
                    e.printStackTrace();
                }

                donHangs.add(donHangTemp);
            }
            while (cursor.moveToNext());
        }
        return donHangs;
    }
}

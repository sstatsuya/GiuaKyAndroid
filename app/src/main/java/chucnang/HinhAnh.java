package chucnang;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class HinhAnh {
    public static byte[] luuHinh(ImageView ivHinh){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) ivHinh.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] hinhSP = byteArrayOutputStream.toByteArray();
        return hinhSP;
    }
}

package others;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giuakyandroid.R;

import java.io.ByteArrayOutputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import java.text.DecimalFormat;


public class Others {
    public Others() {
    }

    public Dialog openConfirmDialog(Context context, String content) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_confirm);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        TextView txtConfirmNoiDung;
        txtConfirmNoiDung = dialog.findViewById(R.id.txt_confirm_noi_dung);
        txtConfirmNoiDung.setText(content);

        return dialog;
    }

    public Dialog openSuccessDialog(Context context, String noiDung) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_success);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);
        TextView txtSuccessNoiDung = dialog.findViewById(R.id.txt_success_noi_dung);
        txtSuccessNoiDung.setText(noiDung);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public byte[] luuHinh(ImageView ivHinh) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) ivHinh.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] hinhSP = byteArrayOutputStream.toByteArray();
        return hinhSP;
    }

    public boolean checkName(String name) {
        if (!name.matches("^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+"))
            return false;
        else return true;
    }

    public boolean checkPhone(String phone) {
        if (!phone.matches("^[0]\\d{9}$"))
            return false;
        else return true;
    }

    public boolean checkAddress(String hoTen) {
        if (!hoTen.matches("^[A-Za-z0-9 ///|/,/.ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]+"))
            return false;
        else return true;
    }

    public static LinkedHashMap<Integer, Integer> sortHashMapByValues(
            HashMap<Integer, Integer> passedMap) {
        List<Integer> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Integer> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues, Collections.reverseOrder());
        Collections.sort(mapKeys, Collections.reverseOrder());

        LinkedHashMap<Integer, Integer> sortedMap = new LinkedHashMap<>();
        Iterator<Integer> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            int val = valueIt.next();
            Iterator<Integer> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Integer key = keyIt.next();
                int comp1 = passedMap.get(key);
                int comp2 = val;

                if (comp1 == comp2) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }

    public String numberToVND(Double number) {
        DecimalFormat df = new DecimalFormat("###");
        String tam = df.format(number);
        String res = "";

        int dem = 0;
        for (int i = tam.length() - 1; i >= 0; i--) {
            res = tam.charAt(i) + res;
            dem += 1;
            if (dem == 3 && i != 0) {
                dem = 0;
                res = "." + res;
            }
        }
        return (res + "đ");
    }


}

package chucnang;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.giuakyandroid.R;

public class Dialog {

    public static android.app.Dialog openConfirmDialog(Context context, String content) {
        android.app.Dialog dialog = new android.app.Dialog(context);
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

    public static android.app.Dialog openSuccessDialog(Context context, String noiDung) {
        android.app.Dialog dialog = new android.app.Dialog(context);
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
}

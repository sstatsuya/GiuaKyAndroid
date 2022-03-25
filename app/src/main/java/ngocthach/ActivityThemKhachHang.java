package ngocthach;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giuakyandroid.R;

public class ActivityThemKhachHang extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_khach_hang);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));


    }
}
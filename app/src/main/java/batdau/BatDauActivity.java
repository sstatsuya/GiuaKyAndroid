package batdau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.giuakyandroid.R;

public class BatDauActivity extends AppCompatActivity {
    Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bat_dau);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BatDauActivity.this, TrangChuActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        btnStart = findViewById(R.id.btnStart);
    }
    public void scheduleJob() {
//        ComponentName componentName = new ComponentName(this, SendMailSchedule.class);
//        JobInfo info = new JobInfo.Builder(123, componentName)
//                //just run this job when have wifi connection
//                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
//                .setPeriodic()

    }
}
package batdau;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.giuakyandroid.R;

import java.util.concurrent.TimeUnit;

import schedulemail.ScheduleWorker;

public class BatDauActivity extends AppCompatActivity {
    Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bat_dau);
        scheduleJob();
        System.out.println("sau scedu");
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
        System.out.println("Go to this");
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(
                ScheduleWorker.class, 1, TimeUnit.MINUTES
        ).build();
        WorkManager.getInstance().enqueue(periodicWorkRequest);
    }
}
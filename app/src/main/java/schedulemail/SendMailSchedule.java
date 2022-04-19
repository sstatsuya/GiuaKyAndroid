package schedulemail;

import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Context;
import android.util.Log;

/**
 * Project: GiuaKyAndroid
 * Author: Tran Van Tu
 * Date: 4/18/2022 10:35 AM
 * Desc:
 */
public class SendMailSchedule extends JobService {
    private static final String TAG = "SendMailSchedule";

    final String mail = "tutranvan156@gmail.com";
    final String subject = "test";
    final String message = "Hello world";
    Context context;

    public SendMailSchedule() {
    }

    public SendMailSchedule(Context context) {
        this.context = context;
    }
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "Job started");
        System.out.println("into this hsit");
        sendMail(jobParameters);
        return true;
    }
    private void sendMail(JobParameters jobParameters) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("into sendMail");
                JavaMailAPI javaMailAPI = new JavaMailAPI(getApplicationContext(), mail, subject, message);
                javaMailAPI.execute();
                Log.d(TAG, "Job finished");
            }
        }).start();
    }
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
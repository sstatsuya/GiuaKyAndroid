package sendmail;

import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;

/**
 * Project: GiuaKyAndroid
 * Author: Tran Van Tu
 * Date: 4/18/2022 10:35 AM
 * Desc:
 */
public class SendMailSchedule extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        JobScheduler jobScheduler = (JobScheduler)getApplicationContext().getSystemService(JOB_SCHEDULER_SERVICE);


        return false;
    }
    private void sendMail(final JobParameters parameters) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("run this");
                }
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}

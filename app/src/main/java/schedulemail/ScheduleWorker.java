package schedulemail;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * Project: GiuaKyAndroid
 * Author: Tran Van Tu
 * Date: 4/19/2022 9:47 PM
 * Desc:
 */
public class ScheduleWorker extends Worker{

    public ScheduleWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }
        return null;
    }
}

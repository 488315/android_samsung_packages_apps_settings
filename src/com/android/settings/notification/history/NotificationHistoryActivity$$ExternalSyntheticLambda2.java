package com.android.settings.notification.history;

import android.util.Slog;

import androidx.appcompat.widget.SeslSwitchBar;

import com.android.settings.R;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;

import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationHistoryActivity$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationHistoryActivity f$0;

    public /* synthetic */ NotificationHistoryActivity$$ExternalSyntheticLambda2(
            NotificationHistoryActivity notificationHistoryActivity, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationHistoryActivity;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        NotificationHistoryActivity notificationHistoryActivity = this.f$0;
        switch (i) {
            case 0:
                StatusLogger$StatusLoggingProvider statusLogger$StatusLoggingProvider =
                        NotificationHistoryActivity.STATUS_LOGGING_PROVIDER;
                notificationHistoryActivity.getClass();
                try {
                    notificationHistoryActivity.mCountdownLatch.await(2L, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    Slog.e("NotifHistory", "timed out waiting for loading", e);
                }
                ThreadUtils.postOnMainThread(
                        new NotificationHistoryActivity$$ExternalSyntheticLambda2(
                                notificationHistoryActivity, 1));
                break;
            default:
                if (((SeslSwitchBar) notificationHistoryActivity.mSwitchBar).mSwitch.isChecked()
                        && notificationHistoryActivity.findViewById(R.id.today_list).getVisibility()
                                == 8
                        && notificationHistoryActivity.mSnoozeView.getVisibility() == 8
                        && notificationHistoryActivity.mDismissView.getVisibility() == 8) {
                    notificationHistoryActivity.mHistoryOn.setVisibility(8);
                    notificationHistoryActivity.mHistoryEmpty.setVisibility(0);
                    break;
                }
                break;
        }
    }
}

package com.samsung.android.settings.voiceinput.offline;

import android.os.Handler;

import com.samsung.android.util.SemLog;

import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class OfflinePackageChecker {
    private static final String TAG = "@VoiceIn: OfflinePackageChecker";

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class CustomAppUpdateData {
        private AppUpdateData appUpdateData;
        private String langPackName;
        private Locale locale;

        public CustomAppUpdateData(String str, Locale locale, AppUpdateData appUpdateData) {
            this.langPackName = str;
            this.locale = locale;
            this.appUpdateData = appUpdateData;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class HandlerExecutor implements Executor {
        private final Handler mHandler;

        public HandlerExecutor(Handler handler) {
            SemLog.i(OfflinePackageChecker.TAG, "handler not null");
            this.mHandler = handler;
        }

        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            if (this.mHandler.post(runnable)) {
                return;
            }
            throw new RejectedExecutionException(this.mHandler + " is shutting down");
        }
    }
}

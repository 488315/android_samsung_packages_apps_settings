package com.android.settings.connecteddevice.threadnetwork;

import android.net.thread.ThreadNetworkController;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;

import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0000\n"
                + "\u0000\bg\u0018\u00002\u00020\u0001ø\u0001\u0000\u0082\u0002\u0006\n"
                + "\u0004\b!0\u0001¨\u0006\u0002À\u0006\u0001"
        },
        d2 = {
            "Lcom/android/settings/connecteddevice/threadnetwork/BaseThreadNetworkController;",
            ApnSettings.MVNO_NONE,
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public interface BaseThreadNetworkController {
    void registerStateCallback(
            Executor executor, ThreadNetworkController.StateCallback stateCallback);

    void setEnabled(
            boolean z,
            Executor executor,
            ThreadNetworkToggleController$setChecked$1 threadNetworkToggleController$setChecked$1);

    void unregisterStateCallback(ThreadNetworkController.StateCallback stateCallback);
}

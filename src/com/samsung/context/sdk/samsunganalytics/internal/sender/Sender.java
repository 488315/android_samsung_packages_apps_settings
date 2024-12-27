package com.samsung.context.sdk.samsunganalytics.internal.sender;

import android.content.Context;

import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.sender.DLC.DLCLogSender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.DLS.DLSLogSender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.DMA.DMALogSender;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class Sender {
    public static BaseLogSender logSender;

    public static BaseLogSender get(Context context, int i, Configuration configuration) {
        if (logSender == null) {
            synchronized (Sender.class) {
                try {
                    if (i == 0) {
                        logSender = new DLSLogSender(context, configuration);
                    } else if (i == 1) {
                        logSender = new DLCLogSender(context, configuration);
                    } else if (i == 2 || i == 3) {
                        logSender = new DMALogSender(context, configuration);
                    }
                } finally {
                }
            }
        }
        return logSender;
    }
}

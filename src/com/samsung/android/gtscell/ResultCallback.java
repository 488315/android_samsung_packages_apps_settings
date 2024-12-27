package com.samsung.android.gtscell;

import com.samsung.android.gtscell.data.result.GtsItemResult;
import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000\u0016\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0000\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/ResultCallback;",
            ApnSettings.MVNO_NONE,
            "onResult",
            ApnSettings.MVNO_NONE,
            "result",
            "Lcom/samsung/android/gtscell/data/result/GtsItemResult;",
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public interface ResultCallback {
    void onResult(GtsItemResult result);
}

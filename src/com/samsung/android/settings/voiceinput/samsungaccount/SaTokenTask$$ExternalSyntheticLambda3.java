package com.samsung.android.settings.voiceinput.samsungaccount;

import com.samsung.android.settings.voiceinput.samsungaccount.listener.SaTokenResultListener;
import com.samsung.android.util.SemLog;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class SaTokenTask$$ExternalSyntheticLambda3 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        SaTokenResultListener saTokenResultListener = (SaTokenResultListener) obj;
        int i = SaTokenTask.$r8$clinit;
        try {
            saTokenResultListener.onResult();
        } catch (Exception e) {
            SemLog.e("SaTokenTask", "Exception happens, " + e);
        }
    }
}

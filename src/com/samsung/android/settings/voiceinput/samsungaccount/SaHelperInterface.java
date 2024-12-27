package com.samsung.android.settings.voiceinput.samsungaccount;

import com.samsung.android.settings.voiceinput.samsungaccount.listener.SaTokenResultListener;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public interface SaHelperInterface {
    boolean isSamsungAccountSigned();

    void requestToken(SaTokenResultListener saTokenResultListener);
}

package com.samsung.android.knox.zt.devicetrust.cert;

import android.os.Bundle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface ICertProvisionListener {
    void onError(int i, String str);

    void onStatusChange(String str, String str2);

    void onSuccess(Bundle bundle);
}

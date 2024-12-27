package com.samsung.android.knox.container;

import android.os.RemoteException;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract /* synthetic */ class RCPPolicy$$ExternalSyntheticOutline0 {
    public static void m(RemoteException remoteException, StringBuilder sb, String str) {
        sb.append(Log.getStackTraceString(remoteException));
        Log.e(str, sb.toString());
    }
}

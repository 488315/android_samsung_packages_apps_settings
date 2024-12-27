package com.samsung.android.settings.biometrics;

import android.accounts.AccountManager;
import android.content.Context;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class BiometricsConfig {
    public static boolean isSamsungAccountSignedIn(Context context) {
        if (AccountManager.get(context).getAccountsByType("com.osp.app.signin").length > 0) {
            Log.d("BiometricsConfig", "isSamsungAccountSignedIn() - true");
            return true;
        }
        Log.d("BiometricsConfig", "isSamsungAccountSignedIn() - false");
        return false;
    }
}

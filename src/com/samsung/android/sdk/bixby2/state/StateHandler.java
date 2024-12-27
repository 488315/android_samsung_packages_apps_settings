package com.samsung.android.sdk.bixby2.state;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;

import com.samsung.android.sdk.bixby2.AppMetaInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class StateHandler {
    public static StateHandler mInstance;
    public Callback mCallback;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class Callback {}

    public static AppMetaInfo getDefaultAppMetaInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            String packageName = context.getPackageName();
            Bundle bundle = packageManager.getApplicationInfo(packageName, 128).metaData;
            if (bundle != null && bundle.containsKey("com.samsung.android.bixby.capsuleId")) {
                return new AppMetaInfo(
                        bundle.getString("com.samsung.android.bixby.capsuleId"),
                        packageManager.getPackageInfo(packageName, 0).versionCode);
            }
            Log.e("StateHandler", "Can't get Capsule ID from Meta data:" + packageName);
            return null;
        } catch (PackageManager.NameNotFoundException | NullPointerException e) {
            CloneBackend$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Failed to get Meta data info: "), "StateHandler");
            return null;
        }
    }
}

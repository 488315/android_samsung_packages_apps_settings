package com.samsung.android.scloud.lib.setting;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SyncSettingProviderClient {
    public static String TAG;
    public int applicationType;
    public String authority;
    public Context context;
    public Uri rpcUri;
    public String settingProvider;
    public AnonymousClass1 syncStatusObserver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SyncSettingIntent implements Function {
        public int flags;

        @Override // java.util.function.Function
        public final Intent apply(String str) {
            Log.d(SyncSettingProviderClient.TAG, "Intent - authority: " + str);
            Intent intent =
                    "media".equals(str)
                            ? new Intent("com.samsung.android.scloud.GALLERY_SETTING")
                            : "setting".equals(str)
                                    ? new Intent(
                                            "com.samsung.android.scloud.app.activity.LAUNCH_SYNC_SETTING_DASHBOARD")
                                    : new Intent(
                                            "com.samsung.android.scloud.app.activity.LAUNCH_OTHER_PHONE_DATA");
            intent.setPackage("com.samsung.android.scloud");
            intent.putExtra("authority", str);
            intent.addFlags(this.flags);
            return intent;
        }
    }

    public final Bundle getProfile() {
        try {
            Log.i(TAG, "getProfile");
            Bundle bundle = new Bundle();
            bundle.putString("authority", this.authority);
            bundle.putInt("application_type", this.applicationType);
            bundle.putLong("library_version", 20170L);
            return this.context
                    .getContentResolver()
                    .call(this.rpcUri, "get_profile", (String) null, bundle);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }
}

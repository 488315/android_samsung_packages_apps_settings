package com.samsung.android.sdk.scs.ai.asr;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface Repository {
    public static final SharedPrefRepository settings = new SharedPrefRepository();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SharedPrefRepository implements Repository {
        public final SharedPreferences sharedPrefSupplier;
        public final String prefName = "scs_asr_settings_1";
        public final String TAG = "SharedPrefRepository@scs_asr_settings";

        public SharedPrefRepository() {
            ExpiringData expiringData = Environment.langpackConfig;
            Application application = null;
            try {
                Class[] clsArr = new Class[0];
                application =
                        (Application)
                                Class.forName("android.app.ActivityThread")
                                        .getMethod("currentApplication", null)
                                        .invoke(null, null);
            } catch (ClassNotFoundException
                    | IllegalAccessException
                    | IllegalArgumentException
                    | NoSuchMethodException
                    | InvocationTargetException unused) {
            }
            this.sharedPrefSupplier = application.getSharedPreferences("scs_asr_settings", 0);
            Log.i(
                    com.samsung.android.sdk.scs.base.utils.Log.concatPrefixTag(this.TAG),
                    "created  " + this.prefName);
        }
    }
}

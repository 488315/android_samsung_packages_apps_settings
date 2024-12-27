package com.samsung.android.settings.logging.status;

import android.util.Log;

import com.android.settings.Utils;

import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class StatusLoggingUtils {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long A_WEEK_TO_MILLISECOND = TimeUnit.DAYS.toMillis(7);

    public static StatusLogger$StatusLoggingProvider getStatusLoggingProvider(Class cls) {
        if (Utils.DBG) {
            Log.i("Settings_SA_Utils", " find field 'STATUS_LOGGING_PROVIDER in " + cls.toString());
        }
        try {
            return (StatusLogger$StatusLoggingProvider)
                    cls.getField("STATUS_LOGGING_PROVIDER").get(null);
        } catch (IllegalAccessException unused) {
            Log.i("Settings_SA_Utils", "Illegal access to field 'STATUS_LOGGING_PROVIDER'");
            return null;
        } catch (IllegalArgumentException unused2) {
            Log.i(
                    "Settings_SA_Utils",
                    "Illegal argument when accessing field 'STATUS_LOGGING_PROVIDER'");
            return null;
        } catch (NoSuchFieldException unused3) {
            Log.i("Settings_SA_Utils", "Cannot find field 'STATUS_LOGGING_PROVIDER'");
            return null;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        } catch (SecurityException unused4) {
            Log.i("Settings_SA_Utils", "Security exception for field 'STATUS_LOGGING_PROVIDER'");
            return null;
        }
    }
}

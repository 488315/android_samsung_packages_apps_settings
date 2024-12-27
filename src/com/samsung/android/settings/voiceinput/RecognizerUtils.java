package com.samsung.android.settings.voiceinput;

import android.content.Context;
import android.util.Log;

import com.samsung.android.sdk.scs.base.feature.Feature;
import com.samsung.android.util.SemLog;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class RecognizerUtils {
    private static final String TAG = "@VoiceIn: RecognizerUtils";

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class SCS {
        private static final /* synthetic */ SCS[] $VALUES;
        public static final SCS AVAILABLE;
        public static final SCS NOT_AVAILABLE;

        static {
            SCS scs = new SCS("AVAILABLE", 0);
            AVAILABLE = scs;
            SCS scs2 = new SCS("NOT_AVAILABLE", 1);
            NOT_AVAILABLE = scs2;
            $VALUES = new SCS[] {scs, scs2};
        }

        public static SCS valueOf(String str) {
            return (SCS) Enum.valueOf(SCS.class, str);
        }

        public static SCS[] values() {
            return (SCS[]) $VALUES.clone();
        }
    }

    public static String getLocaleCode(Locale locale) {
        return locale.getLanguage() + "-" + locale.getCountry();
    }

    public static SCS isSCSFeatureEnabled(Context context) {
        int checkFeature = Feature.checkFeature(context);
        int versionCode = PackageUtils.getVersionCode(context, Constants.SCS_PACKAGE_NAME);
        SemLog.i(TAG, "scsApkVersion " + versionCode);
        boolean z = checkFeature == 0 && versionCode >= Constants.SCS_STABLE_VERSION.intValue();
        Log.d(TAG, "scsAvailable : " + checkFeature);
        Log.d(TAG, "scsApkVersion : " + versionCode);
        Log.d(TAG, "enable scs ? : " + z);
        return z ? SCS.AVAILABLE : SCS.NOT_AVAILABLE;
    }
}

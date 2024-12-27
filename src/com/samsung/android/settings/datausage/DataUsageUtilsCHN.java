package com.samsung.android.settings.datausage;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.BidiFormatter;
import android.text.format.Formatter;
import android.util.Log;

import com.android.settings.DisplaySettings$$ExternalSyntheticOutline0;
import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.general.BaseResetSettingsData;
import com.samsung.android.settings.general.OnResetSettingsDataListener;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class DataUsageUtilsCHN {
    public static final OnResetSettingsDataListener RESET_SETTINGS_DATA = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.datausage.DataUsageUtilsCHN$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseResetSettingsData {
        @Override // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void resetSettings(Context context) {
            Log.i("DataUsageUtilsCHN", "reset settings called");
            if (Rune.SUPPORT_SMARTMANAGER_CN) {
                try {
                    context.getContentResolver()
                            .call(
                                    "com.samsung.android.sm.dcapi",
                                    "resetDataUsageChn",
                                    (String) null,
                                    (Bundle) null);
                } catch (Exception e) {
                    Log.d("DataUsageUtilsCHN", "reset data usage chn error: " + e.toString());
                }
            }
        }
    }

    public static CharSequence formatDataUsage(Context context, long j) {
        int i;
        long j2;
        Resources resources = context.getResources();
        boolean z = j < 0;
        if (z) {
            j = -j;
        }
        float f = j;
        if (f > 900.0f) {
            j2 = 1024;
            f /= 1024;
            i = R.string.kilobyteShort;
        } else {
            i = R.string.byteShort;
            j2 = 1;
        }
        if (f > 900.0f) {
            j2 *= 1024;
            f /= 1024;
            i = R.string.megabyteShort;
        }
        if (f > 900.0f) {
            j2 *= 1024;
            f /= 1024;
            i = R.string.gigabyteShort;
        }
        if (f > 900.0f) {
            j2 *= 1024;
            f /= 1024;
            i = R.string.terabyteShort;
        }
        if (f > 900.0f) {
            j2 *= 1024;
            f /= 1024;
            i = R.string.petabyteShort;
        }
        String str = (j2 == 1 || f >= 100.0f) ? "%.0f" : "%.2f";
        if (z) {
            f = -f;
        }
        String format = String.format(str, Float.valueOf(f));
        String string = resources.getString(i);
        Formatter.BytesResult bytesResult = new Formatter.BytesResult(format, string, string, 0L);
        return BidiFormatter.getInstance()
                .unicodeWrap(
                        context.getString(
                                R.string.fileSizeSuffix,
                                ApnSettings.MVNO_NONE,
                                bytesResult.value,
                                bytesResult.units));
    }

    public static Intent getSMDataUsageSummaryIntent(boolean z) {
        Intent m =
                DisplaySettings$$ExternalSyntheticOutline0.m(
                        "com.samsung.android.sm_cn",
                        "com.samsung.android.sm.datausage.ui.datausagesummary.DataUsageSummaryActivity");
        if (z) {
            m.addFlags(67108864);
        }
        return m;
    }

    public static void sendSmartManagerEventWithValueLog(Context context, int i, int i2, int i3) {
        try {
            Log.d(
                    "DataUsageUtilsCHN",
                    "sendSmartManagerEventWithValueLog() START screenId : "
                            + i
                            + " eventId : "
                            + i2
                            + " eventValue : "
                            + i3);
            Uri build =
                    Uri.parse("content://com.samsung.android.sm/SamsungAnalysis")
                            .buildUpon()
                            .appendQueryParameter("screenId", Integer.toString(i))
                            .appendQueryParameter("eventId", Integer.toString(i2))
                            .appendQueryParameter("eventValue", Integer.toString(i3))
                            .build();
            if (Rune.SUPPORT_SMARTMANAGER_CN) {
                build =
                        Uri.parse("content://com.samsung.android.sa/SamsungAnalysis")
                                .buildUpon()
                                .appendQueryParameter("screenId", Integer.toString(i))
                                .appendQueryParameter("eventId", Integer.toString(i2))
                                .appendQueryParameter("eventValue", Integer.toString(i3))
                                .build();
            }
            Cursor query = context.getContentResolver().query(build, null, null, null, null);
            if (query != null) {
                query.close();
            }
        } catch (IllegalArgumentException unused) {
            Log.e(
                    "DataUsageUtilsCHN",
                    "sendSmartManagerEventWithValueLog() has occured IllegalArgumentException with"
                        + " SMLogging");
        }
    }
}

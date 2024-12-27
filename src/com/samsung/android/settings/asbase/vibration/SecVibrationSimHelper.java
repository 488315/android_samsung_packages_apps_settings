package com.samsung.android.settings.asbase.vibration;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.Settings;

import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.asbase.rune.VibRune;
import com.samsung.android.settings.asbase.utils.SimUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecVibrationSimHelper {
    public final Context mContext;
    public final IVibSimType mSimType;
    public final int mType;

    /* JADX WARN: Multi-variable type inference failed */
    public SecVibrationSimHelper(Context context, int i) {
        SecVibrationSingleSimType secVibrationSingleSimType;
        this.mContext = context;
        this.mType = i;
        if (SimUtils.isMultiSimEnabled(context)) {
            SecVibrationMultiSimType secVibrationMultiSimType = new SecVibrationMultiSimType();
            secVibrationMultiSimType.mType = i;
            secVibrationSingleSimType = secVibrationMultiSimType;
        } else {
            SecVibrationSingleSimType secVibrationSingleSimType2 = new SecVibrationSingleSimType();
            secVibrationSingleSimType2.mType = i;
            secVibrationSingleSimType = secVibrationSingleSimType2;
        }
        this.mSimType = secVibrationSingleSimType;
    }

    public final String getVibrationPatternSummary() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int i = this.mType;
        String concat =
                "content://com.android.settings.personalvibration.PersonalVibrationProvider/"
                        .concat(i == 0 ? "registerinfo" : "notification");
        String[] strArr = new String[2];
        String str = ApnSettings.MVNO_NONE;
        int i2 = 0;
        while (true) {
            IVibSimType iVibSimType = this.mSimType;
            if (i2 >= iVibSimType.getVibrationSimCount()) {
                return str;
            }
            try {
                Cursor query =
                        contentResolver.query(
                                Uri.parse(concat),
                                null,
                                "vibration_pattern=?",
                                new String[] {
                                    Integer.toString(
                                            Settings.System.getInt(
                                                    contentResolver,
                                                    (String)
                                                            iVibSimType.getSepIndexDbName().get(i2),
                                                    i == 0 ? 50035 : 50034))
                                },
                                null);
                if (query != null) {
                    try {
                        if (query.moveToFirst()) {
                            strArr[i2] = query.getString(query.getColumnIndex("vibration_name"));
                        }
                    } catch (Throwable th) {
                        try {
                            query.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
                if (query != null) {
                    query.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (VibRune.SUPPORT_SYNC_WITH_HAPTIC
                    && Settings.System.getInt(
                                    contentResolver,
                                    (String) iVibSimType.getSyncDbName().get(i2),
                                    0)
                            == 1) {
                Uri actualDefaultRingtoneUri =
                        RingtoneManager.getActualDefaultRingtoneUri(
                                this.mContext,
                                ((Integer) iVibSimType.getSoundType().get(i2)).intValue());
                if (actualDefaultRingtoneUri != null
                        ? RingtoneManager.hasHapticChannels(actualDefaultRingtoneUri)
                        : false) {
                    strArr[i2] =
                            this.mContext.getString(
                                    i == 0
                                            ? R.string.sec_vib_picker_sync_with_ringtone
                                            : R.string.sec_vib_picker_sync_with_notification);
                }
            }
            if (i2 == 1) {
                str = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "\n");
            }
            if (SimUtils.isMultiSimEnabled(this.mContext) && i != 1) {
                StringBuilder m =
                        EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(
                                str);
                m.append(SimUtils.getSimName(this.mContext, i2));
                m.append(" : ");
                str = m.toString();
            }
            StringBuilder m2 =
                    EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(str);
            m2.append(strArr[i2]);
            str = m2.toString();
            i2++;
        }
    }
}

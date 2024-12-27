package com.samsung.android.settings.asbase.vibration;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.util.Log;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class PatternRestoreHelper {
    public static final HashMap sPatternLookupTable;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PatternLookupInfo {
        public final int mSepIndex;

        public PatternLookupInfo(int i) {
            this.mSepIndex = i;
        }
    }

    static {
        HashMap hashMap = new HashMap();
        sPatternLookupTable = hashMap;
        hashMap.put(1, new PatternLookupInfo(50035));
        hashMap.put(2, new PatternLookupInfo(50033));
        hashMap.put(3, new PatternLookupInfo(50034));
        hashMap.put(4, new PatternLookupInfo(50037));
        hashMap.put(5, new PatternLookupInfo(50036));
        hashMap.put(6, new PatternLookupInfo(50041));
        hashMap.put(7, new PatternLookupInfo(50042));
        hashMap.put(8, new PatternLookupInfo(50043));
        hashMap.put(9, new PatternLookupInfo(50044));
        hashMap.put(10, new PatternLookupInfo(50045));
        hashMap.put(11, new PatternLookupInfo(50125));
        hashMap.put(12, new PatternLookupInfo(50126));
        hashMap.put(13, new PatternLookupInfo(50127));
        hashMap.put(14, new PatternLookupInfo(50128));
        hashMap.put(15, new PatternLookupInfo(50129));
        hashMap.put(16, new PatternLookupInfo(50130));
        hashMap.put(17, new PatternLookupInfo(50131));
    }

    public static int findIdByQuery(Context context, int i, int i2) {
        try {
            Cursor query =
                    context.getContentResolver()
                            .query(
                                    Uri.parse(
                                            i == 0
                                                    ? "content://com.android.settings.personalvibration.PersonalVibrationProvider/registerinfo"
                                                    : "content://com.android.settings.personalvibration.PersonalVibrationProvider/notification"),
                                    null,
                                    "vibration_pattern=?",
                                    new String[] {Integer.toString(i2)},
                                    null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        int i3 = query.getInt(query.getColumnIndex("_id"));
                        Log.d("PatternRestoreHelper", "findIdByQuery() id=" + i3);
                        query.close();
                        return i3;
                    }
                } finally {
                }
            }
            if (query == null) {
                return -1;
            }
            query.close();
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int getIdByLegacyId(Context context, int i, int i2) {
        HashMap hashMap = sPatternLookupTable;
        if (!hashMap.containsKey(Integer.valueOf(i2))) {
            i2 = i == 0 ? 1 : 3;
        }
        return findIdByQuery(
                context, i, ((PatternLookupInfo) hashMap.get(Integer.valueOf(i2))).mSepIndex);
    }

    public static int getSepIndexById(Context context, int i, int i2) {
        String str;
        int i3;
        if (i == 0) {
            str =
                    "content://com.android.settings.personalvibration.PersonalVibrationProvider/registerinfo";
            i3 = 50035;
        } else {
            str =
                    "content://com.android.settings.personalvibration.PersonalVibrationProvider/notification";
            i3 = 50034;
        }
        try {
            Cursor query =
                    context.getContentResolver()
                            .query(
                                    Uri.parse(str),
                                    null,
                                    "_id=?",
                                    new String[] {Integer.toString(i2)},
                                    null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        i3 = query.getInt(query.getColumnIndex("vibration_pattern"));
                        Log.d("PatternRestoreHelper", "findSepIndexByQuery() retSepIndex=" + i3);
                    }
                } finally {
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i3;
    }

    public static boolean supportsColorfulPattern(Context context) {
        Vibrator defaultVibrator =
                ((VibratorManager) context.getSystemService("vibrator_manager"))
                        .getDefaultVibrator();
        return defaultVibrator != null && defaultVibrator.semGetSupportedVibrationType() >= 3;
    }

    public static boolean supportsLivePattern(Context context) {
        Vibrator defaultVibrator =
                ((VibratorManager) context.getSystemService("vibrator_manager"))
                        .getDefaultVibrator();
        return defaultVibrator != null && defaultVibrator.semGetSupportedVibrationType() >= 4;
    }
}

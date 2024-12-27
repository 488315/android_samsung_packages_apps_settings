package com.sec.ims.extensions;

import com.samsung.android.emergencymode.SemEmergencyConstants;

import java.lang.reflect.Field;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SemEmergencyConstantsExt {
    public static final String EMERGENCY_CHECK_ABNORMAL_STATE =
            getStringFromField(
                    "EMERGENCY_CHECK_ABNORMAL_STATE",
                    "com.samsung.intent.action.EMERGENCY_CHECK_ABNORMAL_STATE");

    public static String getStringFromField(String str, String str2) {
        try {
            Field field = ReflectionUtils.getField(SemEmergencyConstants.class, str);
            if (field != null) {
                return (String) field.get(null);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return str2;
    }
}

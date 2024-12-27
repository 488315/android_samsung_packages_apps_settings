package com.samsung.android.settings.voiceinput.samsungaccount.data;

import android.text.TextUtils;

import com.samsung.android.util.SemLog;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SaError {
    private static final String TAG = "@VoiceIn: SaError";
    private static final Map<String, Integer> sErrorType =
            new HashMap<
                    String,
                    Integer>() { // from class:
                                 // com.samsung.android.settings.voiceinput.samsungaccount.data.SaError.1
                {
                    put("SAC_0102", -1001);
                    put("SAC_0203", -1002);
                    put("SAC_0204", -1003);
                    put("SAC_0205", -1009);
                    put("SAC_0206", -1010);
                    put("SAC_0207", -1006);
                    put("SAC_0301", -1004);
                    put("SAC_0302", -1013);
                    put("SAC_0401", -1005);
                    put("SAC_0402", -1008);
                    put("SAC_0501", -1014);
                    put("SAC_0502", -1015);
                    put("USR_1312", -1007);
                }
            };
    private int mType;

    public SaError(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mType = -1016;
            return;
        }
        SemLog.i(TAG, "Error code, " + str);
        Map<String, Integer> map = sErrorType;
        this.mType = map.containsKey(str) ? map.get(str).intValue() : -1016;
    }

    public final int getType() {
        return this.mType;
    }
}

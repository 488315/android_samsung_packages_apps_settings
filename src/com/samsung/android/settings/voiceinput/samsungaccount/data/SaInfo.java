package com.samsung.android.settings.voiceinput.samsungaccount.data;

import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SaInfo implements Cloneable {
    private static final String TAG = "@VoiceIn: SaInfo";
    private String mCC;

    public final Object clone() {
        try {
            return (SaInfo) super.clone();
        } catch (Exception unused) {
            String str = this.mCC;
            SaInfo saInfo = new SaInfo();
            SemLog.d(TAG, "initialized" + str);
            saInfo.mCC = str;
            return saInfo;
        }
    }

    public final String getCC() {
        return this.mCC;
    }

    public final void setCC(String str) {
        SemLog.i(TAG, "setCC " + str);
        this.mCC = str;
    }
}

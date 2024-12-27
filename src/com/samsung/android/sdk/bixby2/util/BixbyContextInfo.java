package com.samsung.android.sdk.bixby2.util;

import android.os.Bundle;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.sivs.ai.sdkcommon.asr.SpeechRecognitionConst;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BixbyContextInfo {
    public final Integer bixbyClientTaskId;
    public final boolean isMediaControlActive;
    public final boolean isMusicActive;
    public final String locale;

    public BixbyContextInfo(Bundle bundle) {
        this.locale = bundle.getString(SpeechRecognitionConst.Key.LOCALE, ApnSettings.MVNO_NONE);
        this.isMusicActive = bundle.getBoolean("isMusicActive", false);
        this.isMediaControlActive = bundle.getBoolean("isMediaControlActive", false);
        if (bundle.containsKey("bixbyClient_taskId")) {
            this.bixbyClientTaskId = Integer.valueOf(bundle.getInt("bixbyClient_taskId"));
        }
    }

    public BixbyContextInfo() {
        this.locale = ApnSettings.MVNO_NONE;
        this.isMusicActive = false;
        this.isMediaControlActive = false;
    }
}

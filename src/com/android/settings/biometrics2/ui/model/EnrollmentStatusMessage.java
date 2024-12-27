package com.android.settings.biometrics2.ui.model;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class EnrollmentStatusMessage {
    public final int msgId;
    public final CharSequence str;

    public EnrollmentStatusMessage(int i, CharSequence charSequence) {
        this.msgId = i;
        this.str = charSequence == null ? ApnSettings.MVNO_NONE : charSequence;
    }

    public final String toString() {
        String hexString = Integer.toHexString(hashCode());
        CharSequence charSequence = this.str;
        StringBuilder m =
                ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                        "EnrollmentStatusMessage@", hexString, "{id:");
        m.append(this.msgId);
        m.append(", str:");
        m.append((Object) charSequence);
        m.append("}");
        return m.toString();
    }
}

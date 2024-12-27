package com.samsung.android.settings.analyzestorage.external.bnr;

import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;

import com.samsung.android.settings.analyzestorage.domain.log.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BnrParam {
    public String destinationPath;
    public int detailAction;
    public String encryptionKey;
    public String exportSessionTime;
    public String requestFrom;
    public int securityLevel;

    public final String toString() {
        String encodedMsg = Log.getEncodedMsg(this.destinationPath);
        int i = this.detailAction;
        String str = this.encryptionKey;
        String str2 = this.requestFrom;
        String str3 = this.exportSessionTime;
        int i2 = this.securityLevel;
        StringBuilder sb = new StringBuilder();
        sb.append(encodedMsg);
        sb.append(":");
        sb.append(i);
        sb.append(":");
        sb.append(str);
        ConstraintWidget$$ExternalSyntheticOutline0.m(sb, ":", str2, ":", str3);
        sb.append(":");
        sb.append(i2);
        return sb.toString();
    }
}

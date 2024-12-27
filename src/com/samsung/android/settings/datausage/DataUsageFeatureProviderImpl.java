package com.samsung.android.settings.datausage;

import android.content.Context;
import android.text.BidiFormatter;
import android.text.TextUtils;
import android.text.format.Formatter;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DataUsageFeatureProviderImpl {
    /* JADX WARN: Code restructure failed: missing block: B:34:0x006e, code lost:

       if ((r14 & 1) != 0) goto L39;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.text.format.Formatter.BytesResult formatBytes(
            android.content.res.Resources r11, long r12, int r14) {
        /*
            r10 = this;
            r0 = 0
            int r10 = (r12 > r0 ? 1 : (r12 == r0 ? 0 : -1))
            r0 = 1
            if (r10 >= 0) goto L9
            r10 = r0
            goto La
        L9:
            r10 = 0
        La:
            if (r10 == 0) goto Ld
            long r12 = -r12
        Ld:
            float r12 = (float) r12
            r13 = 1147207680(0x44610000, float:900.0)
            int r1 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
            r2 = 1
            r4 = 1148846080(0x447a0000, float:1000.0)
            r5 = 1000(0x3e8, double:4.94E-321)
            if (r1 <= 0) goto L20
            float r12 = r12 / r4
            r1 = 2132022328(0x7f141438, float:1.9683073E38)
            r7 = r5
            goto L24
        L20:
            r1 = 2132019328(0x7f140880, float:1.9676988E38)
            r7 = r2
        L24:
            int r9 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
            if (r9 <= 0) goto L2d
            long r7 = r7 * r5
            float r12 = r12 / r4
            r1 = 2132023149(0x7f14176d, float:1.9684738E38)
        L2d:
            int r9 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
            if (r9 <= 0) goto L36
            long r7 = r7 * r5
            float r12 = r12 / r4
            r1 = 2132021411(0x7f1410a3, float:1.9681213E38)
        L36:
            int r9 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
            if (r9 <= 0) goto L3f
            long r7 = r7 * r5
            float r12 = r12 / r4
            r1 = 2132029035(0x7f142e6b, float:1.9696676E38)
        L3f:
            int r13 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
            if (r13 <= 0) goto L48
            long r7 = r7 * r5
            float r12 = r12 / r4
            r1 = 2132024308(0x7f141bf4, float:1.9687088E38)
        L48:
            int r13 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            java.lang.String r2 = "%.0f"
            if (r13 == 0) goto L70
            r13 = 1120403456(0x42c80000, float:100.0)
            int r13 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
            if (r13 < 0) goto L55
            goto L70
        L55:
            r13 = 1065353216(0x3f800000, float:1.0)
            int r13 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
            java.lang.String r3 = "%.2f"
            if (r13 >= 0) goto L5f
        L5d:
            r2 = r3
            goto L70
        L5f:
            r13 = 1092616192(0x41200000, float:10.0)
            int r13 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
            if (r13 >= 0) goto L6c
            r13 = r14 & 1
            if (r13 == 0) goto L5d
            java.lang.String r2 = "%.1f"
            goto L70
        L6c:
            r13 = r14 & 1
            if (r13 == 0) goto L5d
        L70:
            if (r10 == 0) goto L73
            float r12 = -r12
        L73:
            java.lang.Float r10 = java.lang.Float.valueOf(r12)
            java.lang.Object[] r10 = new java.lang.Object[]{r10}
            java.lang.String r4 = java.lang.String.format(r2, r10)
            java.lang.String r6 = r11.getString(r1)
            android.text.format.Formatter$BytesResult r10 = new android.text.format.Formatter$BytesResult
            r7 = 0
            r3 = r10
            r5 = r6
            r3.<init>(r4, r5, r6, r7)
            return r10
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.datausage.DataUsageFeatureProviderImpl.formatBytes(android.content.res.Resources,"
                    + " long, int):android.text.format.Formatter$BytesResult");
    }

    public final String formatFileSize(Context context, long j) {
        if (context == null) {
            return ApnSettings.MVNO_NONE;
        }
        Formatter.BytesResult formatBytes = formatBytes(context.getResources(), j, 4);
        String string =
                context.getString(
                        R.string.fileSizeSuffix,
                        formatBytes.value,
                        ApnSettings.MVNO_NONE,
                        formatBytes.units);
        return TextUtils.getLayoutDirectionFromLocale(
                                context.getResources().getConfiguration().locale)
                        == 1
                ? BidiFormatter.getInstance(true).unicodeWrap(string)
                : string;
    }
}

package com.samsung.android.settings.analyzestorage.presenter.utils;

import android.content.Context;
import android.util.LruCache;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class StringConverter {
    public static final LruCache sCachedTime = new LruCache(128);
    public static final LruCache sCachedSize = new LruCache(128);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SizeString {
        public final String mUnit;
        public final String mValue;

        public SizeString(String str, String str2) {
            this.mValue = str;
            this.mUnit = str2;
        }

        public final String toString() {
            return this.mValue + this.mUnit;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x0083, code lost:

       if ((r14 & 1) != 0) goto L47;
    */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0088, code lost:

       if ((r14 & 1) != 0) goto L46;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.samsung.android.settings.analyzestorage.presenter.utils.StringConverter
                    .SizeString
            formatBytes(android.content.res.Resources r11, long r12, int r14) {
        /*
            r0 = 0
            int r0 = (r12 > r0 ? 1 : (r12 == r0 ? 0 : -1))
            r1 = 1
            if (r0 >= 0) goto L9
            r0 = r1
            goto La
        L9:
            r0 = 0
        La:
            if (r0 == 0) goto Ld
            long r12 = -r12
        Ld:
            float r12 = (float) r12
            r13 = 1147207680(0x44610000, float:900.0)
            int r2 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
            r3 = 1
            r5 = 1148846080(0x447a0000, float:1000.0)
            if (r2 <= 0) goto L1f
            float r12 = r12 / r5
            r2 = 2132022328(0x7f141438, float:1.9683073E38)
            r6 = 1000(0x3e8, double:4.94E-321)
            goto L23
        L1f:
            r2 = 2132019328(0x7f140880, float:1.9676988E38)
            r6 = r3
        L23:
            int r8 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
            if (r8 <= 0) goto L2e
            float r12 = r12 / r5
            r2 = 2132023149(0x7f14176d, float:1.9684738E38)
            r6 = 1000000(0xf4240, double:4.940656E-318)
        L2e:
            int r8 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
            if (r8 <= 0) goto L39
            float r12 = r12 / r5
            r2 = 2132021411(0x7f1410a3, float:1.9681213E38)
            r6 = 1000000000(0x3b9aca00, double:4.94065646E-315)
        L39:
            int r8 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
            r9 = 1099511627776(0x10000000000, double:5.43230922487E-312)
            if (r8 <= 0) goto L49
            r2 = 1149239296(0x44800000, float:1024.0)
            float r12 = r12 / r2
            r2 = 2132029035(0x7f142e6b, float:1.9696676E38)
            r6 = r9
        L49:
            int r13 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
            if (r13 <= 0) goto L56
            float r12 = r12 / r5
            r2 = 2132024308(0x7f141bf4, float:1.9687088E38)
            r6 = 1000000000000000(0x38d7ea4c68000, double:4.940656458412465E-309)
        L56:
            r13 = 2
            java.lang.String r5 = "%.1f"
            if (r14 != r13) goto L5c
            goto L8b
        L5c:
            int r13 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            java.lang.String r3 = "%.0f"
            if (r13 == 0) goto L8a
            r13 = 1120403456(0x42c80000, float:100.0)
            int r13 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
            if (r13 >= 0) goto L8a
            r13 = 1065353216(0x3f800000, float:1.0)
            int r4 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
            if (r4 != 0) goto L73
            int r4 = (r6 > r9 ? 1 : (r6 == r9 ? 0 : -1))
            if (r4 != 0) goto L73
            goto L8a
        L73:
            int r13 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
            java.lang.String r4 = "%.2f"
            if (r13 >= 0) goto L7b
        L79:
            r5 = r4
            goto L8b
        L7b:
            r13 = 1092616192(0x41200000, float:10.0)
            int r13 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
            if (r13 >= 0) goto L86
            r13 = r14 & 1
            if (r13 == 0) goto L79
            goto L8b
        L86:
            r13 = r14 & 1
            if (r13 == 0) goto L79
        L8a:
            r5 = r3
        L8b:
            if (r0 == 0) goto L8e
            float r12 = -r12
        L8e:
            java.lang.Float r12 = java.lang.Float.valueOf(r12)
            java.lang.Object[] r12 = new java.lang.Object[]{r12}
            java.lang.String r12 = java.lang.String.format(r5, r12)
            java.lang.String r11 = r11.getString(r2)     // Catch: android.content.res.Resources.NotFoundException -> La4
            com.samsung.android.settings.analyzestorage.presenter.utils.StringConverter$SizeString r13 = new com.samsung.android.settings.analyzestorage.presenter.utils.StringConverter$SizeString     // Catch: android.content.res.Resources.NotFoundException -> La4
            r13.<init>(r12, r11)     // Catch: android.content.res.Resources.NotFoundException -> La4
            return r13
        La4:
            r11 = move-exception
            r11.printStackTrace()
            r11 = 0
            return r11
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.analyzestorage.presenter.utils.StringConverter.formatBytes(android.content.res.Resources,"
                    + " long,"
                    + " int):com.samsung.android.settings.analyzestorage.presenter.utils.StringConverter$SizeString");
    }

    public static String formatFileSize(int i, long j, Context context) {
        SizeString formatBytes;
        if (context == null || (formatBytes = formatBytes(context.getResources(), j, i)) == null) {
            return ApnSettings.MVNO_NONE;
        }
        Locale.getDefault();
        return formatBytes.mValue + " " + formatBytes.mUnit;
    }
}

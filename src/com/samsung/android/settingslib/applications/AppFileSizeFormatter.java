package com.samsung.android.settingslib.applications;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.icu.text.DecimalFormat;
import android.icu.text.MeasureFormat;
import android.icu.text.NumberFormat;
import android.icu.text.UnicodeSet;
import android.icu.text.UnicodeSetSpanner;
import android.icu.util.Measure;
import android.icu.util.MeasureUnit;
import android.text.BidiFormatter;
import android.text.TextUtils;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.lang.reflect.Constructor;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class AppFileSizeFormatter {
    public static final MeasureUnit PETABYTE;
    public static final UnicodeSetSpanner SPACES_AND_CONTROLS =
            new UnicodeSetSpanner(new UnicodeSet("[[:Zs:][:Cf:]]").freeze());

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RoundedBytesResult {
        public final int fractionDigits;
        public final MeasureUnit units;
        public final float value;

        public RoundedBytesResult(float f, MeasureUnit measureUnit, int i, long j) {
            this.value = f;
            this.units = measureUnit;
            this.fractionDigits = i;
        }

        /* JADX WARN: Code restructure failed: missing block: B:39:0x0064, code lost:

           if ((r13 & 1) != 0) goto L39;
        */
        /* JADX WARN: Removed duplicated region for block: B:29:0x0069  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static com.samsung.android.settingslib.applications.AppFileSizeFormatter
                        .RoundedBytesResult
                roundBytes(int r13, long r14) {
            /*
                r0 = 0
                int r0 = (r14 > r0 ? 1 : (r14 == r0 ? 0 : -1))
                r1 = 0
                r2 = 1
                if (r0 >= 0) goto La
                r0 = r2
                goto Lb
            La:
                r0 = r1
            Lb:
                if (r0 == 0) goto Le
                long r14 = -r14
            Le:
                float r14 = (float) r14
                android.icu.util.MeasureUnit r15 = android.icu.util.MeasureUnit.BYTE
                r3 = 1147207680(0x44610000, float:900.0)
                int r4 = (r14 > r3 ? 1 : (r14 == r3 ? 0 : -1))
                r5 = 1
                r7 = 1148846080(0x447a0000, float:1000.0)
                r8 = 1000(0x3e8, double:4.94E-321)
                if (r4 <= 0) goto L22
                android.icu.util.MeasureUnit r15 = android.icu.util.MeasureUnit.KILOBYTE
                float r14 = r14 / r7
                r10 = r8
                goto L23
            L22:
                r10 = r5
            L23:
                int r4 = (r14 > r3 ? 1 : (r14 == r3 ? 0 : -1))
                if (r4 <= 0) goto L2b
                android.icu.util.MeasureUnit r15 = android.icu.util.MeasureUnit.MEGABYTE
                long r10 = r10 * r8
                float r14 = r14 / r7
            L2b:
                int r4 = (r14 > r3 ? 1 : (r14 == r3 ? 0 : -1))
                if (r4 <= 0) goto L33
                android.icu.util.MeasureUnit r15 = android.icu.util.MeasureUnit.GIGABYTE
                long r10 = r10 * r8
                float r14 = r14 / r7
            L33:
                int r4 = (r14 > r3 ? 1 : (r14 == r3 ? 0 : -1))
                if (r4 <= 0) goto L3b
                android.icu.util.MeasureUnit r15 = android.icu.util.MeasureUnit.TERABYTE
                long r10 = r10 * r8
                float r14 = r14 / r7
            L3b:
                int r3 = (r14 > r3 ? 1 : (r14 == r3 ? 0 : -1))
                if (r3 <= 0) goto L43
                android.icu.util.MeasureUnit r15 = com.samsung.android.settingslib.applications.AppFileSizeFormatter.PETABYTE
                long r10 = r10 * r8
                float r14 = r14 / r7
            L43:
                r9 = r15
                int r15 = (r10 > r5 ? 1 : (r10 == r5 ? 0 : -1))
                if (r15 == 0) goto L66
                r15 = 1120403456(0x42c80000, float:100.0)
                int r15 = (r14 > r15 ? 1 : (r14 == r15 ? 0 : -1))
                if (r15 < 0) goto L4f
                goto L66
            L4f:
                r15 = 1065353216(0x3f800000, float:1.0)
                int r15 = (r14 > r15 ? 1 : (r14 == r15 ? 0 : -1))
                r3 = 2
                if (r15 >= 0) goto L58
            L56:
                r10 = r3
                goto L67
            L58:
                r15 = 1092616192(0x41200000, float:10.0)
                int r15 = (r14 > r15 ? 1 : (r14 == r15 ? 0 : -1))
                if (r15 >= 0) goto L63
                r13 = r13 & r2
                if (r13 == 0) goto L56
                r10 = r2
                goto L67
            L63:
                r13 = r13 & r2
                if (r13 == 0) goto L56
            L66:
                r10 = r1
            L67:
                if (r0 == 0) goto L6a
                float r14 = -r14
            L6a:
                r8 = r14
                com.samsung.android.settingslib.applications.AppFileSizeFormatter$RoundedBytesResult r13 = new com.samsung.android.settingslib.applications.AppFileSizeFormatter$RoundedBytesResult
                r11 = 0
                r7 = r13
                r7.<init>(r8, r9, r10, r11)
                return r13
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.settingslib.applications.AppFileSizeFormatter.RoundedBytesResult.roundBytes(int,"
                        + " long):com.samsung.android.settingslib.applications.AppFileSizeFormatter$RoundedBytesResult");
        }
    }

    static {
        try {
            Constructor declaredConstructor =
                    MeasureUnit.class.getDeclaredConstructor(String.class, String.class);
            declaredConstructor.setAccessible(true);
            PETABYTE = (MeasureUnit) declaredConstructor.newInstance("digital", "petabyte");
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to create petabyte MeasureUnit", e);
        }
    }

    public static String formatFileSize(int i, long j, Context context) {
        if (context == null) {
            return ApnSettings.MVNO_NONE;
        }
        RoundedBytesResult roundBytes = RoundedBytesResult.roundBytes(i, j);
        Locale locale = context.getResources().getConfiguration().getLocales().get(0);
        NumberFormat numberFormat = NumberFormat.getInstance(locale);
        int i2 = roundBytes.fractionDigits;
        numberFormat.setMinimumFractionDigits(i2);
        numberFormat.setMaximumFractionDigits(i2);
        numberFormat.setGroupingUsed(false);
        if (numberFormat instanceof DecimalFormat) {
            numberFormat.setRoundingMode(4);
        }
        boolean isFileSize = isFileSize(roundBytes.units);
        float f = roundBytes.value;
        String string =
                isFileSize
                        ? context.getString(
                                R.string.lockscreen_glogin_invalid_input,
                                numberFormat.format(f),
                                getSuffixOverride(context.getResources(), roundBytes.units))
                        : MeasureFormat.getInstance(
                                        locale, MeasureFormat.FormatWidth.SHORT, numberFormat)
                                .format(new Measure(Float.valueOf(f), roundBytes.units));
        return TextUtils.getLayoutDirectionFromLocale(
                                context.getResources().getConfiguration().getLocales().get(0))
                        == 1
                ? BidiFormatter.getInstance(true).unicodeWrap(string)
                : string;
    }

    public static String getSuffixOverride(Resources resources, MeasureUnit measureUnit) {
        return measureUnit == MeasureUnit.BYTE
                ? resources.getString(com.android.settings.R.string.byteShort)
                : measureUnit == MeasureUnit.KILOBYTE
                        ? resources.getString(com.android.settings.R.string.kilobyteShort)
                        : measureUnit == MeasureUnit.MEGABYTE
                                ? resources.getString(com.android.settings.R.string.megabyteShort)
                                : measureUnit == MeasureUnit.GIGABYTE
                                        ? resources.getString(
                                                com.android.settings.R.string.gigabyteShort)
                                        : measureUnit == MeasureUnit.TERABYTE
                                                ? resources.getString(
                                                        com.android.settings.R.string.terabyteShort)
                                                : resources.getString(
                                                        com.android.settings.R.string
                                                                .petabyteShort);
    }

    public static boolean isFileSize(MeasureUnit measureUnit) {
        return measureUnit == MeasureUnit.BYTE
                || measureUnit == MeasureUnit.KILOBYTE
                || measureUnit == MeasureUnit.MEGABYTE
                || measureUnit == MeasureUnit.GIGABYTE
                || measureUnit == MeasureUnit.TERABYTE
                || measureUnit == PETABYTE;
    }
}

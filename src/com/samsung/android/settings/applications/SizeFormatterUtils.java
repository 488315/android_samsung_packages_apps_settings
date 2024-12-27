package com.samsung.android.settings.applications;

import android.content.Context;

import com.android.settings.R;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SizeFormatterUtils {
    public static String getGbFormattedSizeString(Context context, long j) {
        String numberFormattedString = getNumberFormattedString(j, RoundingMode.HALF_UP);
        return j == 0
                ? context.getString(R.string.string_mb, numberFormattedString)
                : j < 1024
                        ? String.format(
                                context.getString(R.string.string_byte), numberFormattedString)
                        : j < 1000000
                                ? context.getString(R.string.string_kb, numberFormattedString)
                                : j < 1000000000
                                        ? context.getString(
                                                R.string.string_mb, numberFormattedString)
                                        : context.getString(
                                                R.string.string_gb, numberFormattedString);
    }

    public static String getNumberFormattedString(long j, RoundingMode roundingMode) {
        if (j == 0) {
            return new DecimalFormat().format(0L);
        }
        if (j < 1024) {
            return new DecimalFormat().format(j);
        }
        if (j < 1000000) {
            return new DecimalFormat().format(j / 1024);
        }
        if (j >= 1000000000) {
            DecimalFormat decimalFormat = new DecimalFormat("##0.#");
            decimalFormat.setRoundingMode(roundingMode);
            return decimalFormat.format(j / 1.073741824E9d);
        }
        int i = (int) (j / 1048576);
        DecimalFormat decimalFormat2 =
                i < 10
                        ? new DecimalFormat("0.##")
                        : i < 100 ? new DecimalFormat("#0.#") : new DecimalFormat("##0");
        decimalFormat2.setRoundingMode(RoundingMode.DOWN);
        return decimalFormat2.format(j / 1048576.0d);
    }
}

package com.samsung.android.settings.applications;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class RamUiUtil {
    public static double getDecimalValueFormatted(long j) {
        double formatLocaleDouble;
        double d;
        String numberFormattedString =
                SizeFormatterUtils.getNumberFormattedString(j, RoundingMode.HALF_UP);
        if (j == 0) {
            return 0.0d;
        }
        if (j < 1024.0d) {
            return (j * 1000) / 1024.0d;
        }
        if (j < 1000000) {
            formatLocaleDouble = getFormatLocaleDouble(numberFormattedString);
            d = 1000.0d;
        } else if (j < 1000000000) {
            formatLocaleDouble = getFormatLocaleDouble(numberFormattedString);
            d = 1000000.0d;
        } else {
            formatLocaleDouble = getFormatLocaleDouble(numberFormattedString);
            d = 1.0E9d;
        }
        return formatLocaleDouble * d;
    }

    public static double getFormatLocaleDouble(String str) {
        Number number;
        try {
            number = NumberFormat.getInstance().parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            number = null;
        }
        if (number != null) {
            return number.doubleValue();
        }
        return 0.0d;
    }
}

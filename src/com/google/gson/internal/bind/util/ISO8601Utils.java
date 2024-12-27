package com.google.gson.internal.bind.util;

import androidx.compose.ui.platform.AndroidUriHandler$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ISO8601Utils {
    public static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone("UTC");

    public static boolean checkOffset(String str, int i, char c) {
        return i < str.length() && str.charAt(i) == c;
    }

    public static Date parse(String str, ParsePosition parsePosition) {
        int i;
        int i2;
        int i3;
        int i4;
        int length;
        TimeZone timeZone;
        char charAt;
        int length2;
        try {
            int index = parsePosition.getIndex();
            int i5 = index + 4;
            int parseInt = parseInt(index, i5, str);
            if (checkOffset(str, i5, '-')) {
                i5 = index + 5;
            }
            int i6 = i5 + 2;
            int parseInt2 = parseInt(i5, i6, str);
            if (checkOffset(str, i6, '-')) {
                i6 = i5 + 3;
            }
            int i7 = i6 + 2;
            int parseInt3 = parseInt(i6, i7, str);
            boolean checkOffset = checkOffset(str, i7, 'T');
            if (!checkOffset && str.length() <= i7) {
                GregorianCalendar gregorianCalendar =
                        new GregorianCalendar(parseInt, parseInt2 - 1, parseInt3);
                gregorianCalendar.setLenient(false);
                parsePosition.setIndex(i7);
                return gregorianCalendar.getTime();
            }
            if (checkOffset) {
                int i8 = i6 + 5;
                int parseInt4 = parseInt(i6 + 3, i8, str);
                if (checkOffset(str, i8, ':')) {
                    i8 = i6 + 6;
                }
                int i9 = i8 + 2;
                int parseInt5 = parseInt(i8, i9, str);
                if (checkOffset(str, i9, ':')) {
                    i9 = i8 + 3;
                }
                if (str.length() <= i9
                        || (charAt = str.charAt(i9)) == 'Z'
                        || charAt == '+'
                        || charAt == '-') {
                    i2 = parseInt5;
                    i3 = 0;
                    i4 = 0;
                    i7 = i9;
                    i = parseInt4;
                } else {
                    int i10 = i9 + 2;
                    i4 = parseInt(i9, i10, str);
                    if (i4 > 59 && i4 < 63) {
                        i4 = 59;
                    }
                    if (checkOffset(str, i10, '.')) {
                        int i11 = i9 + 3;
                        for (int i12 = i9 + 4; i12 < str.length(); i12++) {
                            char charAt2 = str.charAt(i12);
                            if (charAt2 >= '0' && charAt2 <= '9') {}
                            length2 = i12;
                        }
                        length2 = str.length();
                        int min = Math.min(length2, i9 + 6);
                        i3 = parseInt(i11, min, str);
                        int i13 = min - i11;
                        if (i13 == 1) {
                            i3 *= 100;
                        } else if (i13 == 2) {
                            i3 *= 10;
                        }
                        i = parseInt4;
                        i7 = length2;
                        i2 = parseInt5;
                    } else {
                        i = parseInt4;
                        i7 = i10;
                        i2 = parseInt5;
                        i3 = 0;
                    }
                }
            } else {
                i = 0;
                i2 = 0;
                i3 = 0;
                i4 = 0;
            }
            if (str.length() <= i7) {
                throw new IllegalArgumentException("No time zone indicator");
            }
            char charAt3 = str.charAt(i7);
            if (charAt3 == 'Z') {
                timeZone = TIMEZONE_UTC;
                length = i7 + 1;
            } else {
                if (charAt3 != '+' && charAt3 != '-') {
                    throw new IndexOutOfBoundsException(
                            "Invalid time zone indicator '" + charAt3 + "'");
                }
                String substring = str.substring(i7);
                if (substring.length() < 5) {
                    substring = substring + "00";
                }
                length = i7 + substring.length();
                if (!"+0000".equals(substring) && !"+00:00".equals(substring)) {
                    String str2 = "GMT" + substring;
                    TimeZone timeZone2 = TimeZone.getTimeZone(str2);
                    String id = timeZone2.getID();
                    if (!id.equals(str2) && !id.replace(":", ApnSettings.MVNO_NONE).equals(str2)) {
                        throw new IndexOutOfBoundsException(
                                "Mismatching time zone indicator: "
                                        + str2
                                        + " given, resolves to "
                                        + timeZone2.getID());
                    }
                    timeZone = timeZone2;
                }
                timeZone = TIMEZONE_UTC;
            }
            GregorianCalendar gregorianCalendar2 = new GregorianCalendar(timeZone);
            gregorianCalendar2.setLenient(false);
            gregorianCalendar2.set(1, parseInt);
            gregorianCalendar2.set(2, parseInt2 - 1);
            gregorianCalendar2.set(5, parseInt3);
            gregorianCalendar2.set(11, i);
            gregorianCalendar2.set(12, i2);
            gregorianCalendar2.set(13, i4);
            gregorianCalendar2.set(14, i3);
            parsePosition.setIndex(length);
            return gregorianCalendar2.getTime();
        } catch (IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException e) {
            String m =
                    str == null
                            ? null
                            : AndroidUriHandler$$ExternalSyntheticOutline0.m('\"', "\"", str);
            String message = e.getMessage();
            if (message == null || message.isEmpty()) {
                message = "(" + e.getClass().getName() + ")";
            }
            ParseException parseException =
                    new ParseException(
                            "Failed to parse date [" + m + "]: " + message,
                            parsePosition.getIndex());
            parseException.initCause(e);
            throw parseException;
        }
    }

    public static int parseInt(int i, int i2, String str) {
        int i3;
        int i4;
        if (i < 0 || i2 > str.length() || i > i2) {
            throw new NumberFormatException(str);
        }
        if (i < i2) {
            i4 = i + 1;
            int digit = Character.digit(str.charAt(i), 10);
            if (digit < 0) {
                throw new NumberFormatException("Invalid number: " + str.substring(i, i2));
            }
            i3 = -digit;
        } else {
            i3 = 0;
            i4 = i;
        }
        while (i4 < i2) {
            int i5 = i4 + 1;
            int digit2 = Character.digit(str.charAt(i4), 10);
            if (digit2 < 0) {
                throw new NumberFormatException("Invalid number: " + str.substring(i, i2));
            }
            i3 = (i3 * 10) - digit2;
            i4 = i5;
        }
        return -i3;
    }
}

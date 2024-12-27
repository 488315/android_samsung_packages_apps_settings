package com.android.settingslib.datetime;

import android.icu.text.TimeZoneFormat;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TtsSpan;

import androidx.core.text.BidiFormatter;
import androidx.core.text.TextDirectionHeuristicsCompat;

import com.android.i18n.timezone.CountryTimeZones;
import com.android.i18n.timezone.TimeZoneFinder;
import com.android.internal.app.LocaleHelper;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ZoneGetter {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ZoneGetterData {
        public List<String> lookupTimeZoneIdsByCountry(String str) {
            CountryTimeZones lookupCountryTimeZones =
                    TimeZoneFinder.getInstance().lookupCountryTimeZones(str);
            if (lookupCountryTimeZones == null) {
                return null;
            }
            List timeZoneMappings = lookupCountryTimeZones.getTimeZoneMappings();
            ArrayList arrayList = new ArrayList(timeZoneMappings.size());
            Iterator it = timeZoneMappings.iterator();
            while (it.hasNext()) {
                arrayList.add(((CountryTimeZones.TimeZoneMapping) it.next()).getTimeZoneId());
            }
            return Collections.unmodifiableList(arrayList);
        }
    }

    public static void appendWithTtsSpan(
            SpannableStringBuilder spannableStringBuilder,
            CharSequence charSequence,
            TtsSpan ttsSpan) {
        int length = spannableStringBuilder.length();
        spannableStringBuilder.append(charSequence);
        spannableStringBuilder.setSpan(ttsSpan, length, spannableStringBuilder.length(), 0);
    }

    public static String capitalizeForStandaloneDisplay(Locale locale, String str) {
        return TextUtils.isEmpty(str) ? str : LocaleHelper.toSentenceCase(str, locale);
    }

    public static CharSequence getGmtOffsetText(
            TimeZoneFormat timeZoneFormat, Locale locale, TimeZone timeZone, Date date) {
        String substring;
        String str;
        TimeZoneFormat.GMTOffsetPatternType gMTOffsetPatternType;
        int i;
        String str2;
        int i2;
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        String gMTPattern = timeZoneFormat.getGMTPattern();
        int indexOf = gMTPattern.indexOf("{0}");
        int i3 = 0;
        if (indexOf == -1) {
            str = "GMT";
            substring = ApnSettings.MVNO_NONE;
        } else {
            String substring2 = gMTPattern.substring(0, indexOf);
            substring = gMTPattern.substring(indexOf + 3);
            str = substring2;
        }
        if (!str.isEmpty()) {
            appendWithTtsSpan(spannableStringBuilder, str, new TtsSpan.TextBuilder(str).build());
        }
        int offset = timeZone.getOffset(date.getTime());
        if (offset < 0) {
            offset = -offset;
            gMTOffsetPatternType = TimeZoneFormat.GMTOffsetPatternType.NEGATIVE_HM;
        } else {
            gMTOffsetPatternType = TimeZoneFormat.GMTOffsetPatternType.POSITIVE_HM;
        }
        String gMTOffsetPattern = timeZoneFormat.getGMTOffsetPattern(gMTOffsetPatternType);
        String gMTOffsetDigits = timeZoneFormat.getGMTOffsetDigits();
        long j = offset;
        int i4 = (int) (j / 3600000);
        int abs = Math.abs((int) (j / 60000)) % 60;
        while (i3 < gMTOffsetPattern.length()) {
            char charAt = gMTOffsetPattern.charAt(i3);
            if (charAt == '+' || charAt == '-' || charAt == 8722) {
                String valueOf = String.valueOf(charAt);
                appendWithTtsSpan(
                        spannableStringBuilder,
                        valueOf,
                        new TtsSpan.VerbatimBuilder(valueOf).build());
            } else if (charAt == 'H' || charAt == 'm') {
                int i5 = i3 + 1;
                if (i5 >= gMTOffsetPattern.length() || gMTOffsetPattern.charAt(i5) != charAt) {
                    i = 1;
                } else {
                    i3 = i5;
                    i = 2;
                }
                if (charAt == 'H') {
                    str2 = "hour";
                    i2 = i4;
                } else {
                    str2 = "minute";
                    i2 = abs;
                }
                int i6 = i2 / 10;
                int i7 = i2 % 10;
                StringBuilder sb = new StringBuilder(i);
                if (i2 >= 10 || i == 2) {
                    sb.append(gMTOffsetDigits.charAt(i6));
                }
                sb.append(gMTOffsetDigits.charAt(i7));
                appendWithTtsSpan(
                        spannableStringBuilder,
                        sb.toString(),
                        new TtsSpan.MeasureBuilder().setNumber(i2).setUnit(str2).build());
            } else {
                spannableStringBuilder.append(charAt);
            }
            i3++;
        }
        if (!substring.isEmpty()) {
            appendWithTtsSpan(
                    spannableStringBuilder, substring, new TtsSpan.TextBuilder(substring).build());
        }
        return BidiFormatter.getInstance()
                .unicodeWrap(
                        new SpannableString(spannableStringBuilder),
                        TextUtils.getLayoutDirectionFromLocale(locale) == 1
                                ? TextDirectionHeuristicsCompat.RTL
                                : TextDirectionHeuristicsCompat.LTR);
    }
}

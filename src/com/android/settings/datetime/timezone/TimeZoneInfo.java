package com.android.settings.datetime.timezone;

import android.icu.text.TimeZoneFormat;
import android.icu.text.TimeZoneNames;
import android.icu.util.TimeZone;
import android.text.TextUtils;

import com.android.settingslib.datetime.ZoneGetter;

import java.util.Date;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class TimeZoneInfo {
    public final String mDaylightName;
    public final String mExemplarLocation;
    public final String mGenericName;
    public final CharSequence mGmtOffset;
    public final String mId;
    public final String mStandardName;
    public final TimeZone mTimeZone;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder {
        public String mDaylightName;
        public String mExemplarLocation;
        public String mGenericName;
        public CharSequence mGmtOffset;
        public String mStandardName;
        public final TimeZone mTimeZone;

        public Builder(TimeZone timeZone) {
            if (timeZone == null) {
                throw new IllegalArgumentException("TimeZone must not be null!");
            }
            this.mTimeZone = timeZone;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Formatter {
        public final Locale mLocale;
        public final Date mNow;
        public final TimeZoneFormat mTimeZoneFormat;

        public Formatter(Locale locale, Date date) {
            this.mLocale = locale;
            this.mNow = date;
            this.mTimeZoneFormat = TimeZoneFormat.getInstance(locale);
        }

        public final TimeZoneInfo format(TimeZone timeZone) {
            String id = timeZone.getID();
            String canonicalID = TimeZone.getCanonicalID(id);
            if (canonicalID != null) {
                id = canonicalID;
            }
            TimeZoneNames timeZoneNames = this.mTimeZoneFormat.getTimeZoneNames();
            CharSequence gmtOffsetText =
                    ZoneGetter.getGmtOffsetText(
                            this.mTimeZoneFormat,
                            this.mLocale,
                            java.util.TimeZone.getTimeZone(id),
                            this.mNow);
            Builder builder = new Builder(timeZone);
            builder.mGenericName =
                    ZoneGetter.capitalizeForStandaloneDisplay(
                            this.mLocale,
                            timeZoneNames.getDisplayName(
                                    id, TimeZoneNames.NameType.LONG_GENERIC, this.mNow.getTime()));
            builder.mStandardName =
                    ZoneGetter.capitalizeForStandaloneDisplay(
                            this.mLocale,
                            timeZoneNames.getDisplayName(
                                    id, TimeZoneNames.NameType.LONG_STANDARD, this.mNow.getTime()));
            builder.mDaylightName =
                    ZoneGetter.capitalizeForStandaloneDisplay(
                            this.mLocale,
                            timeZoneNames.getDisplayName(
                                    id, TimeZoneNames.NameType.LONG_DAYLIGHT, this.mNow.getTime()));
            builder.mExemplarLocation =
                    ZoneGetter.capitalizeForStandaloneDisplay(
                            this.mLocale, timeZoneNames.getExemplarLocationName(id));
            builder.mGmtOffset = gmtOffsetText;
            if (TextUtils.isEmpty(gmtOffsetText)) {
                throw new IllegalStateException("gmtOffset must not be empty!");
            }
            return new TimeZoneInfo(builder);
        }
    }

    public TimeZoneInfo(Builder builder) {
        TimeZone timeZone = builder.mTimeZone;
        this.mTimeZone = timeZone;
        this.mId = timeZone.getID();
        this.mGenericName = builder.mGenericName;
        this.mStandardName = builder.mStandardName;
        this.mDaylightName = builder.mDaylightName;
        this.mExemplarLocation = builder.mExemplarLocation;
        this.mGmtOffset = builder.mGmtOffset;
    }
}

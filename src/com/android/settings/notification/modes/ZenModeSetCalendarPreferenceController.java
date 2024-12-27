package com.android.settings.notification.modes;

import android.app.Flags;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.CalendarContract;
import android.service.notification.SystemZenRules;
import android.service.notification.ZenModeConfig;

import androidx.preference.DropDownPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeSetCalendarPreferenceController
        extends AbstractZenModePreferenceController {

    @VisibleForTesting
    protected static final Comparator<CalendarInfo> CALENDAR_NAME =
            Comparator.comparing(
                    new ZenModeSetCalendarPreferenceController$$ExternalSyntheticLambda0());

    @VisibleForTesting protected static final String KEY_CALENDAR = "calendar";

    @VisibleForTesting protected static final String KEY_REPLY = "reply";
    public DropDownPreference mCalendar;
    public AnonymousClass1 mCalendarChangeListener;
    public ZenModeConfig.EventInfo mEvent;
    public DropDownPreference mReply;
    public AnonymousClass1 mReplyChangeListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CalendarInfo {
        public Long calendarId;
        public String name;
        public int userId;

        public final boolean equals(Object obj) {
            if (!(obj instanceof CalendarInfo)) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            CalendarInfo calendarInfo = (CalendarInfo) obj;
            return Objects.equals(calendarInfo.name, this.name)
                    && Objects.equals(calendarInfo.calendarId, this.calendarId);
        }

        public final int hashCode() {
            return Objects.hash(this.name, this.calendarId);
        }
    }

    public static void $r8$lambda$7Fr17Z0c81MmfwMQw4K0SwYsCpM(
            ZenModeSetCalendarPreferenceController zenModeSetCalendarPreferenceController,
            ZenModeConfig.EventInfo eventInfo,
            ZenMode zenMode) {
        zenModeSetCalendarPreferenceController.getClass();
        zenMode.mRule.setConditionId(ZenModeConfig.toEventConditionId(eventInfo));
        if (Flags.modesApi() && Flags.modesUi()) {
            zenMode.mRule.setTriggerDescription(
                    SystemZenRules.getTriggerDescriptionForScheduleEvent(
                            zenModeSetCalendarPreferenceController.mContext, eventInfo));
        }
    }

    @VisibleForTesting
    public static void addCalendar(long j, String str, int i, List<CalendarInfo> list) {
        CalendarInfo calendarInfo = new CalendarInfo();
        calendarInfo.calendarId = Long.valueOf(j);
        calendarInfo.name = str;
        calendarInfo.userId = i;
        if (list.contains(calendarInfo)) {
            return;
        }
        list.add(calendarInfo);
    }

    @VisibleForTesting
    public static String key(int i, Long l, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(ZenModeConfig.EventInfo.resolveUserId(i));
        sb.append(":");
        Object obj = l;
        if (l == null) {
            obj = ApnSettings.MVNO_NONE;
        }
        sb.append(obj);
        sb.append(":");
        if (str == null) {
            str = ApnSettings.MVNO_NONE;
        }
        sb.append(str);
        return sb.toString();
    }

    @VisibleForTesting
    public Function<ZenMode, ZenMode> updateEventMode(final ZenModeConfig.EventInfo eventInfo) {
        return new Function() { // from class:
                                // com.android.settings.notification.modes.ZenModeSetCalendarPreferenceController$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                ZenMode zenMode = (ZenMode) obj;
                ZenModeSetCalendarPreferenceController.$r8$lambda$7Fr17Z0c81MmfwMQw4K0SwYsCpM(
                        ZenModeSetCalendarPreferenceController.this, eventInfo, zenMode);
                return zenMode;
            }
        };
    }

    @Override // com.android.settings.notification.modes.AbstractZenModePreferenceController
    public final void updateState(Preference preference, ZenMode zenMode) {
        Context context;
        PreferenceCategory preferenceCategory = (PreferenceCategory) preference;
        this.mCalendar = (DropDownPreference) preferenceCategory.findPreference(KEY_CALENDAR);
        DropDownPreference dropDownPreference =
                (DropDownPreference) preferenceCategory.findPreference(KEY_REPLY);
        this.mReply = dropDownPreference;
        DropDownPreference dropDownPreference2 = this.mCalendar;
        if (dropDownPreference2 == null || dropDownPreference == null) {
            return;
        }
        dropDownPreference2.setOnPreferenceChangeListener(this.mCalendarChangeListener);
        this.mReply.setEntries(
                new CharSequence[] {
                    this.mContext.getString(R.string.zen_mode_event_rule_reply_any_except_no),
                    this.mContext.getString(R.string.zen_mode_event_rule_reply_yes_or_maybe),
                    this.mContext.getString(R.string.zen_mode_event_rule_reply_yes)
                });
        this.mReply.mEntryValues =
                new CharSequence[] {Integer.toString(0), Integer.toString(1), Integer.toString(2)};
        this.mReply.setOnPreferenceChangeListener(this.mReplyChangeListener);
        ZenModeConfig.EventInfo tryParseEventConditionId =
                ZenModeConfig.tryParseEventConditionId(zenMode.mRule.getConditionId());
        this.mEvent = tryParseEventConditionId;
        if (tryParseEventConditionId != null) {
            Context context2 = this.mContext;
            ArrayList arrayList = new ArrayList();
            Iterator<UserHandle> it = UserManager.get(context2).getUserProfiles().iterator();
            while (true) {
                byte b = 0;
                if (!it.hasNext()) {
                    break;
                }
                try {
                    context =
                            context2.createPackageContextAsUser(
                                    context2.getPackageName(), 0, it.next());
                } catch (PackageManager.NameNotFoundException unused) {
                    context = null;
                }
                if (context != null) {
                    try {
                        Cursor query =
                                context.getContentResolver()
                                        .query(
                                                CalendarContract.Calendars.CONTENT_URI,
                                                new String[] {"_id", "calendar_displayName"},
                                                "calendar_access_level >= 500 AND sync_events = 1",
                                                null,
                                                null);
                        if (query != null) {
                            while (query.moveToNext()) {
                                addCalendar(
                                        query.getLong(0),
                                        query.getString(1),
                                        context.getUserId(),
                                        arrayList);
                            }
                        } else if (query != null) {
                        }
                        query.close();
                    } catch (Throwable th) {
                        if (0 != 0) {
                            (b == true ? 1 : 0).close();
                        }
                        throw th;
                    }
                }
            }
            Collections.sort(arrayList, CALENDAR_NAME);
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            arrayList2.add(this.mContext.getString(R.string.zen_mode_event_rule_calendar_any));
            arrayList3.add(key(0, null, ApnSettings.MVNO_NONE));
            ZenModeConfig.EventInfo eventInfo = this.mEvent;
            String str = eventInfo != null ? eventInfo.calName : null;
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                CalendarInfo calendarInfo = (CalendarInfo) it2.next();
                arrayList2.add(calendarInfo.name);
                arrayList3.add(
                        key(calendarInfo.userId, calendarInfo.calendarId, calendarInfo.name));
                if (str != null
                        && this.mEvent.calendarId == null
                        && str.equals(calendarInfo.name)) {
                    this.mEvent.calendarId = calendarInfo.calendarId;
                }
            }
            CharSequence[] charSequenceArr =
                    (CharSequence[]) arrayList2.toArray(new CharSequence[arrayList2.size()]);
            CharSequence[] charSequenceArr2 =
                    (CharSequence[]) arrayList3.toArray(new CharSequence[arrayList3.size()]);
            if (!Arrays.equals(this.mCalendar.mEntries, charSequenceArr)) {
                this.mCalendar.setEntries(charSequenceArr);
            }
            if (!Arrays.equals(this.mCalendar.mEntryValues, charSequenceArr2)) {
                this.mCalendar.mEntryValues = charSequenceArr2;
            }
            String str2 = this.mCalendar.mValue;
            ZenModeConfig.EventInfo eventInfo2 = this.mEvent;
            if (!Objects.equals(
                    str2, key(eventInfo2.userId, eventInfo2.calendarId, eventInfo2.calName))) {
                DropDownPreference dropDownPreference3 = this.mCalendar;
                ZenModeConfig.EventInfo eventInfo3 = this.mEvent;
                dropDownPreference3.setValue(
                        key(eventInfo3.userId, eventInfo3.calendarId, eventInfo3.calName));
            }
            if (Objects.equals(this.mReply.mValue, Integer.toString(this.mEvent.reply))) {
                return;
            }
            this.mReply.setValue(Integer.toString(this.mEvent.reply));
        }
    }
}

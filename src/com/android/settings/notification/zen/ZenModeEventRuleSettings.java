package com.android.settings.notification.zen;

import android.app.AutomaticZenRule;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.CalendarContract;
import android.service.notification.ZenModeConfig;

import androidx.preference.DropDownPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

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

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenModeEventRuleSettings extends ZenModeRuleSettingsBase {
    public static final AnonymousClass3 CALENDAR_NAME = new AnonymousClass3();
    public DropDownPreference mCalendar;
    public boolean mCreate;
    public ZenModeConfig.EventInfo mEvent;
    public DropDownPreference mReply;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.zen.ZenModeEventRuleSettings$3, reason: invalid class name */
    public final class AnonymousClass3 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ((CalendarInfo) obj).name.compareTo(((CalendarInfo) obj2).name);
        }
    }

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

    public static String key$1(int i, Long l, String str) {
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
    public void addCalendar(long j, String str, int i, List<CalendarInfo> list) {
        CalendarInfo calendarInfo = new CalendarInfo();
        calendarInfo.calendarId = Long.valueOf(j);
        calendarInfo.name = str;
        calendarInfo.userId = i;
        if (list.contains(calendarInfo)) {
            return;
        }
        list.add(calendarInfo);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return new ArrayList();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 146;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.zen_mode_event_rule_settings;
    }

    @Override // com.android.settings.notification.zen.ZenModeRuleSettingsBase
    public final void onCreateInternal() {
        this.mCreate = true;
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        DropDownPreference dropDownPreference =
                (DropDownPreference) preferenceScreen.findPreference("calendar");
        this.mCalendar = dropDownPreference;
        final int i = 0;
        dropDownPreference.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener(
                        this) { // from class:
                                // com.android.settings.notification.zen.ZenModeEventRuleSettings.1
                    public final /* synthetic */ ZenModeEventRuleSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference, Object obj) {
                        switch (i) {
                            case 0:
                                String str = (String) obj;
                                ZenModeEventRuleSettings zenModeEventRuleSettings = this.this$0;
                                ZenModeConfig.EventInfo eventInfo = zenModeEventRuleSettings.mEvent;
                                boolean z = false;
                                if (!str.equals(
                                        ZenModeEventRuleSettings.key$1(
                                                eventInfo.userId,
                                                eventInfo.calendarId,
                                                eventInfo.calName))) {
                                    String[] split = str.split(":", 3);
                                    zenModeEventRuleSettings.mEvent.userId =
                                            Integer.parseInt(split[0]);
                                    z = true;
                                    zenModeEventRuleSettings.mEvent.calendarId =
                                            split[1].equals(ApnSettings.MVNO_NONE)
                                                    ? null
                                                    : Long.valueOf(Long.parseLong(split[1]));
                                    zenModeEventRuleSettings.mEvent.calName =
                                            split[2].equals(ApnSettings.MVNO_NONE)
                                                    ? null
                                                    : split[2];
                                    zenModeEventRuleSettings.updateEventRule(
                                            zenModeEventRuleSettings.mEvent);
                                }
                                return z;
                            default:
                                int parseInt = Integer.parseInt((String) obj);
                                ZenModeEventRuleSettings zenModeEventRuleSettings2 = this.this$0;
                                ZenModeConfig.EventInfo eventInfo2 =
                                        zenModeEventRuleSettings2.mEvent;
                                if (parseInt == eventInfo2.reply) {
                                    return false;
                                }
                                eventInfo2.reply = parseInt;
                                zenModeEventRuleSettings2.updateEventRule(eventInfo2);
                                return true;
                        }
                    }
                });
        DropDownPreference dropDownPreference2 =
                (DropDownPreference) preferenceScreen.findPreference("reply");
        this.mReply = dropDownPreference2;
        dropDownPreference2.setEntries(
                new CharSequence[] {
                    getString(R.string.zen_mode_event_rule_reply_any_except_no),
                    getString(R.string.zen_mode_event_rule_reply_yes_or_maybe),
                    getString(R.string.zen_mode_event_rule_reply_yes)
                });
        this.mReply.mEntryValues =
                new CharSequence[] {Integer.toString(0), Integer.toString(1), Integer.toString(2)};
        final int i2 = 1;
        this.mReply.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener(
                        this) { // from class:
                                // com.android.settings.notification.zen.ZenModeEventRuleSettings.1
                    public final /* synthetic */ ZenModeEventRuleSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference, Object obj) {
                        switch (i2) {
                            case 0:
                                String str = (String) obj;
                                ZenModeEventRuleSettings zenModeEventRuleSettings = this.this$0;
                                ZenModeConfig.EventInfo eventInfo = zenModeEventRuleSettings.mEvent;
                                boolean z = false;
                                if (!str.equals(
                                        ZenModeEventRuleSettings.key$1(
                                                eventInfo.userId,
                                                eventInfo.calendarId,
                                                eventInfo.calName))) {
                                    String[] split = str.split(":", 3);
                                    zenModeEventRuleSettings.mEvent.userId =
                                            Integer.parseInt(split[0]);
                                    z = true;
                                    zenModeEventRuleSettings.mEvent.calendarId =
                                            split[1].equals(ApnSettings.MVNO_NONE)
                                                    ? null
                                                    : Long.valueOf(Long.parseLong(split[1]));
                                    zenModeEventRuleSettings.mEvent.calName =
                                            split[2].equals(ApnSettings.MVNO_NONE)
                                                    ? null
                                                    : split[2];
                                    zenModeEventRuleSettings.updateEventRule(
                                            zenModeEventRuleSettings.mEvent);
                                }
                                return z;
                            default:
                                int parseInt = Integer.parseInt((String) obj);
                                ZenModeEventRuleSettings zenModeEventRuleSettings2 = this.this$0;
                                ZenModeConfig.EventInfo eventInfo2 =
                                        zenModeEventRuleSettings2.mEvent;
                                if (parseInt == eventInfo2.reply) {
                                    return false;
                                }
                                eventInfo2.reply = parseInt;
                                zenModeEventRuleSettings2.updateEventRule(eventInfo2);
                                return true;
                        }
                    }
                });
        reloadCalendar();
        updateControlsInternal();
    }

    @Override // com.android.settings.notification.zen.ZenModeRuleSettingsBase,
              // com.android.settings.notification.zen.ZenModeSettingsBase,
              // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (isUiRestricted()) {
            return;
        }
        if (!this.mCreate) {
            reloadCalendar();
        }
        this.mCreate = false;
    }

    public final void reloadCalendar() {
        Context context;
        Context context2 = ((ZenModeSettingsBase) this).mContext;
        ArrayList arrayList = new ArrayList();
        Iterator<UserHandle> it = UserManager.get(context2).getUserProfiles().iterator();
        while (true) {
            Cursor cursor = null;
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
                            try {
                                addCalendar(
                                        query.getLong(0),
                                        query.getString(1),
                                        context.getUserId(),
                                        arrayList);
                            } catch (Throwable th) {
                                th = th;
                                cursor = query;
                                if (cursor != null) {
                                    cursor.close();
                                }
                                throw th;
                            }
                        }
                    } else if (query != null) {
                    }
                    query.close();
                } catch (Throwable th2) {
                    th = th2;
                }
            }
        }
        Collections.sort(arrayList, CALENDAR_NAME);
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        arrayList2.add(getString(R.string.zen_mode_event_rule_calendar_any));
        arrayList3.add(key$1(0, null, ApnSettings.MVNO_NONE));
        ZenModeConfig.EventInfo eventInfo = this.mEvent;
        String str = eventInfo != null ? eventInfo.calName : null;
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            CalendarInfo calendarInfo = (CalendarInfo) it2.next();
            arrayList2.add(calendarInfo.name);
            arrayList3.add(key$1(calendarInfo.userId, calendarInfo.calendarId, calendarInfo.name));
            if (str != null && this.mEvent.calendarId == null && str.equals(calendarInfo.name)) {
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
        if (Arrays.equals(this.mCalendar.mEntryValues, charSequenceArr2)) {
            return;
        }
        this.mCalendar.mEntryValues = charSequenceArr2;
    }

    @Override // com.android.settings.notification.zen.ZenModeRuleSettingsBase
    public final boolean setRule(AutomaticZenRule automaticZenRule) {
        ZenModeConfig.EventInfo tryParseEventConditionId =
                automaticZenRule != null
                        ? ZenModeConfig.tryParseEventConditionId(automaticZenRule.getConditionId())
                        : null;
        this.mEvent = tryParseEventConditionId;
        return tryParseEventConditionId != null;
    }

    @Override // com.android.settings.notification.zen.ZenModeRuleSettingsBase
    public final void updateControlsInternal() {
        String str = this.mCalendar.mValue;
        ZenModeConfig.EventInfo eventInfo = this.mEvent;
        if (!Objects.equals(
                str, key$1(eventInfo.userId, eventInfo.calendarId, eventInfo.calName))) {
            DropDownPreference dropDownPreference = this.mCalendar;
            ZenModeConfig.EventInfo eventInfo2 = this.mEvent;
            dropDownPreference.setValue(
                    key$1(eventInfo2.userId, eventInfo2.calendarId, eventInfo2.calName));
        }
        if (Objects.equals(this.mReply.mValue, Integer.toString(this.mEvent.reply))) {
            return;
        }
        this.mReply.setValue(Integer.toString(this.mEvent.reply));
    }
}

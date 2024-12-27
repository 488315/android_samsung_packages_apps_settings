package com.android.settings.notification.modes;

import android.content.Context;
import android.service.notification.ZenModeConfig;

import androidx.preference.Preference;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenModeSetCalendarFragment extends ZenModeFragmentBase {
    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        ZenModeSetCalendarPreferenceController zenModeSetCalendarPreferenceController =
                new ZenModeSetCalendarPreferenceController(
                        context, "zen_mode_event_category", this.mBackend);
        zenModeSetCalendarPreferenceController.mCalendarChangeListener =
                new Preference
                        .OnPreferenceChangeListener() { // from class:
                                                        // com.android.settings.notification.modes.ZenModeSetCalendarPreferenceController.1
                    public final /* synthetic */ int $r8$classId;
                    public final /* synthetic */ ZenModeSetCalendarPreferenceController this$0;

                    public /* synthetic */ AnonymousClass1(
                            ZenModeSetCalendarPreferenceController
                                    zenModeSetCalendarPreferenceController2,
                            int i) {
                        r2 = i;
                        r1 = zenModeSetCalendarPreferenceController2;
                    }

                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference, Object obj) {
                        switch (r2) {
                            case 0:
                                String str = (String) obj;
                                ZenModeSetCalendarPreferenceController
                                        zenModeSetCalendarPreferenceController2 = r1;
                                ZenModeConfig.EventInfo eventInfo =
                                        zenModeSetCalendarPreferenceController2.mEvent;
                                boolean z = false;
                                if (!str.equals(
                                        ZenModeSetCalendarPreferenceController.key(
                                                eventInfo.userId,
                                                eventInfo.calendarId,
                                                eventInfo.calName))) {
                                    String[] split = str.split(":", 3);
                                    zenModeSetCalendarPreferenceController2.mEvent.userId =
                                            Integer.parseInt(split[0]);
                                    z = true;
                                    zenModeSetCalendarPreferenceController2.mEvent.calendarId =
                                            split[1].equals(ApnSettings.MVNO_NONE)
                                                    ? null
                                                    : Long.valueOf(Long.parseLong(split[1]));
                                    zenModeSetCalendarPreferenceController2.mEvent.calName =
                                            split[2].equals(ApnSettings.MVNO_NONE)
                                                    ? null
                                                    : split[2];
                                    zenModeSetCalendarPreferenceController2.saveMode(
                                            zenModeSetCalendarPreferenceController2.updateEventMode(
                                                    zenModeSetCalendarPreferenceController2
                                                            .mEvent));
                                }
                                return z;
                            default:
                                int parseInt = Integer.parseInt((String) obj);
                                ZenModeSetCalendarPreferenceController
                                        zenModeSetCalendarPreferenceController3 = r1;
                                ZenModeConfig.EventInfo eventInfo2 =
                                        zenModeSetCalendarPreferenceController3.mEvent;
                                if (parseInt == eventInfo2.reply) {
                                    return false;
                                }
                                eventInfo2.reply = parseInt;
                                zenModeSetCalendarPreferenceController3.saveMode(
                                        zenModeSetCalendarPreferenceController3.updateEventMode(
                                                eventInfo2));
                                return true;
                        }
                    }
                };
        zenModeSetCalendarPreferenceController2.mReplyChangeListener =
                new Preference
                        .OnPreferenceChangeListener() { // from class:
                                                        // com.android.settings.notification.modes.ZenModeSetCalendarPreferenceController.1
                    public final /* synthetic */ int $r8$classId;
                    public final /* synthetic */ ZenModeSetCalendarPreferenceController this$0;

                    public /* synthetic */ AnonymousClass1(
                            ZenModeSetCalendarPreferenceController
                                    zenModeSetCalendarPreferenceController2,
                            int i) {
                        r2 = i;
                        r1 = zenModeSetCalendarPreferenceController2;
                    }

                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference, Object obj) {
                        switch (r2) {
                            case 0:
                                String str = (String) obj;
                                ZenModeSetCalendarPreferenceController
                                        zenModeSetCalendarPreferenceController2 = r1;
                                ZenModeConfig.EventInfo eventInfo =
                                        zenModeSetCalendarPreferenceController2.mEvent;
                                boolean z = false;
                                if (!str.equals(
                                        ZenModeSetCalendarPreferenceController.key(
                                                eventInfo.userId,
                                                eventInfo.calendarId,
                                                eventInfo.calName))) {
                                    String[] split = str.split(":", 3);
                                    zenModeSetCalendarPreferenceController2.mEvent.userId =
                                            Integer.parseInt(split[0]);
                                    z = true;
                                    zenModeSetCalendarPreferenceController2.mEvent.calendarId =
                                            split[1].equals(ApnSettings.MVNO_NONE)
                                                    ? null
                                                    : Long.valueOf(Long.parseLong(split[1]));
                                    zenModeSetCalendarPreferenceController2.mEvent.calName =
                                            split[2].equals(ApnSettings.MVNO_NONE)
                                                    ? null
                                                    : split[2];
                                    zenModeSetCalendarPreferenceController2.saveMode(
                                            zenModeSetCalendarPreferenceController2.updateEventMode(
                                                    zenModeSetCalendarPreferenceController2
                                                            .mEvent));
                                }
                                return z;
                            default:
                                int parseInt = Integer.parseInt((String) obj);
                                ZenModeSetCalendarPreferenceController
                                        zenModeSetCalendarPreferenceController3 = r1;
                                ZenModeConfig.EventInfo eventInfo2 =
                                        zenModeSetCalendarPreferenceController3.mEvent;
                                if (parseInt == eventInfo2.reply) {
                                    return false;
                                }
                                eventInfo2.reply = parseInt;
                                zenModeSetCalendarPreferenceController3.saveMode(
                                        zenModeSetCalendarPreferenceController3.updateEventMode(
                                                eventInfo2));
                                return true;
                        }
                    }
                };
        arrayList.add(zenModeSetCalendarPreferenceController2);
        return arrayList;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 146;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.modes_set_calendar;
    }
}

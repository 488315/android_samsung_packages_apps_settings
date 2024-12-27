package com.android.settings.notification.zen;

import android.content.Context;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.lifecycle.Lifecycle;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenModeSoundVibrationSettings extends ZenModeSettingsBase {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.zen_mode_sound_vibration_settings);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.zen.ZenModeSoundVibrationSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return ZenModeSoundVibrationSettings.buildPreferenceControllers$1(context, null);
        }
    }

    public static List buildPreferenceControllers$1(Context context, Lifecycle lifecycle) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ZenModeAlarmsPreferenceController(context, lifecycle));
        arrayList.add(new ZenModeMediaPreferenceController(context, lifecycle));
        arrayList.add(new ZenModeSystemPreferenceController(context, "zen_mode_system", lifecycle));
        arrayList.add(
                new ZenModeRemindersPreferenceController(context, "zen_mode_reminders", lifecycle));
        arrayList.add(new ZenModeEventsPreferenceController(context, "zen_mode_events", lifecycle));
        arrayList.add(
                new ZenModeBehaviorFooterPreferenceController(
                        context, lifecycle, R.string.zen_sound_footer));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return buildPreferenceControllers$1(context, getSettingsLifecycle());
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 141;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.zen_mode_sound_vibration_settings;
    }
}

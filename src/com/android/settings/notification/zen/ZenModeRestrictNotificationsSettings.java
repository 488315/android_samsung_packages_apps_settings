package com.android.settings.notification.zen;

import android.content.Context;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.lifecycle.Lifecycle;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenModeRestrictNotificationsSettings extends ZenModeSettingsBase {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.zen_mode_restrict_notifications_settings);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.zen.ZenModeRestrictNotificationsSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return ZenModeRestrictNotificationsSettings.buildPreferenceControllers$1(context, null);
        }
    }

    public static List buildPreferenceControllers$1(Context context, Lifecycle lifecycle) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                new ZenModeVisEffectsNonePreferenceController(
                        context, "zen_mute_notifications", lifecycle));
        arrayList.add(
                new ZenModeVisEffectsAllPreferenceController(
                        context, "zen_hide_notifications", lifecycle));
        arrayList.add(
                new ZenModeVisEffectsCustomPreferenceController(context, "zen_custom", lifecycle));
        arrayList.add(new ZenFooterPreferenceController(context, "footer_preference", lifecycle));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return buildPreferenceControllers$1(context, getSettingsLifecycle());
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1400;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.zen_mode_restrict_notifications_settings;
    }
}

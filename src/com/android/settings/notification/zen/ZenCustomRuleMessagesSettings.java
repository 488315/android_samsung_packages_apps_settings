package com.android.settings.notification.zen;

import android.content.Context;

import com.android.settings.R;
import com.android.settings.notification.NotificationBackend;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenCustomRuleMessagesSettings extends ZenCustomRuleSettingsBase {
    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        ((ZenCustomRuleSettingsBase) this).mControllers = arrayList;
        arrayList.add(
                new ZenRulePrioritySendersPreferenceController(
                        context, getSettingsLifecycle(), new NotificationBackend()));
        return ((ZenCustomRuleSettingsBase) this).mControllers;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1610;
    }

    @Override // com.android.settings.notification.zen.ZenCustomRuleSettingsBase
    public final String getPreferenceCategoryKey() {
        return "zen_mode_settings_category_messages";
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.zen_mode_custom_rule_messages_settings;
    }

    @Override // com.android.settings.notification.zen.ZenCustomRuleSettingsBase
    public final void updatePreferences$1() {
        super.updatePreferences$1();
        getPreferenceScreen()
                .findPreference("footer_preference")
                .setTitle(
                        ((ZenModeSettingsBase) this)
                                .mContext
                                .getResources()
                                .getString(
                                        R.string.zen_mode_custom_messages_footer,
                                        this.mRule.getName()));
    }
}

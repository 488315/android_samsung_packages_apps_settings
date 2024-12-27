package com.android.settings.notification.zen;

import android.R;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenCustomRuleCallsSettings extends ZenCustomRuleSettingsBase {
    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        ((ZenCustomRuleSettingsBase) this).mControllers = arrayList;
        arrayList.add(new ZenRuleCallsPreferenceController(context, getSettingsLifecycle()));
        ((ArrayList) ((ZenCustomRuleSettingsBase) this).mControllers)
                .add(
                        new ZenRuleRepeatCallersPreferenceController(
                                context,
                                getSettingsLifecycle(),
                                context.getResources()
                                        .getInteger(
                                                R.integer
                                                        .leanback_setup_alpha_backward_in_content_delay)));
        ((ArrayList) ((ZenCustomRuleSettingsBase) this).mControllers)
                .add(
                        new ZenRuleStarredContactsPreferenceController(
                                context, getSettingsLifecycle()));
        return ((ZenCustomRuleSettingsBase) this).mControllers;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1611;
    }

    @Override // com.android.settings.notification.zen.ZenCustomRuleSettingsBase
    public final String getPreferenceCategoryKey() {
        return "zen_mode_settings_category_calls";
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return com.android.settings.R.xml.zen_mode_custom_rule_calls_settings;
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
                                        com.android.settings.R.string.zen_mode_custom_calls_footer,
                                        this.mRule.getName()));
    }
}

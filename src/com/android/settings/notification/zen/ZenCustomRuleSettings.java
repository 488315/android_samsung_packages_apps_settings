package com.android.settings.notification.zen;

import android.content.Context;

import com.android.settings.R;

import com.sec.ims.volte2.data.VolteConstants;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenCustomRuleSettings extends ZenCustomRuleSettingsBase {
    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        ((ZenCustomRuleSettingsBase) this).mControllers = arrayList;
        arrayList.add(
                new ZenRuleDefaultPolicyPreferenceController(
                        context, "zen_custom_rule_setting_default", getSettingsLifecycle()));
        ((ZenCustomRuleSettingsBase) this)
                .mControllers.add(
                        new ZenRuleCustomPolicyPreferenceController(
                                context, "zen_custom_rule_setting", getSettingsLifecycle()));
        return ((ZenCustomRuleSettingsBase) this).mControllers;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return VolteConstants.ErrorCode.DEREG_SUCCEEDED;
    }

    @Override // com.android.settings.notification.zen.ZenCustomRuleSettingsBase
    public final String getPreferenceCategoryKey() {
        return "zen_custom_rule_category";
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.zen_mode_custom_rule_settings;
    }
}

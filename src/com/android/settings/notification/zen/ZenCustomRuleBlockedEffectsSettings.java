package com.android.settings.notification.zen;

import android.content.Context;

import com.android.settings.R;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenCustomRuleBlockedEffectsSettings extends ZenCustomRuleSettingsBase {
    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        ((ZenCustomRuleSettingsBase) this).mControllers = arrayList;
        arrayList.add(
                new ZenRuleVisEffectPreferenceController(
                        context, getSettingsLifecycle(), "zen_effect_intent", 0, 1332, null));
        ((ZenCustomRuleSettingsBase) this)
                .mControllers.add(
                        new ZenRuleVisEffectPreferenceController(
                                context,
                                getSettingsLifecycle(),
                                "zen_effect_light",
                                1,
                                1333,
                                null));
        ((ZenCustomRuleSettingsBase) this)
                .mControllers.add(
                        new ZenRuleVisEffectPreferenceController(
                                context, getSettingsLifecycle(), "zen_effect_peek", 2, 1334, null));
        ((ZenCustomRuleSettingsBase) this)
                .mControllers.add(
                        new ZenRuleVisEffectPreferenceController(
                                context,
                                getSettingsLifecycle(),
                                "zen_effect_status",
                                3,
                                1335,
                                new int[] {6}));
        ((ZenCustomRuleSettingsBase) this)
                .mControllers.add(
                        new ZenRuleVisEffectPreferenceController(
                                context,
                                getSettingsLifecycle(),
                                "zen_effect_badge",
                                4,
                                1336,
                                null));
        ((ZenCustomRuleSettingsBase) this)
                .mControllers.add(
                        new ZenRuleVisEffectPreferenceController(
                                context,
                                getSettingsLifecycle(),
                                "zen_effect_ambient",
                                5,
                                1337,
                                null));
        ((ZenCustomRuleSettingsBase) this)
                .mControllers.add(
                        new ZenRuleVisEffectPreferenceController(
                                context, getSettingsLifecycle(), "zen_effect_list", 6, 1338, null));
        return ((ZenCustomRuleSettingsBase) this).mControllers;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1609;
    }

    @Override // com.android.settings.notification.zen.ZenCustomRuleSettingsBase
    public final String getPreferenceCategoryKey() {
        return null;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.zen_mode_block_settings;
    }
}

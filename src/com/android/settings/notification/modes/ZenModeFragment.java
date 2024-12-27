package com.android.settings.notification.modes;

import android.app.Application;
import android.app.AutomaticZenRule;
import android.content.Context;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settingslib.applications.ApplicationsState;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenModeFragment extends ZenModeFragmentBase {
    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ZenModeHeaderController(context, this, this.mBackend));
        arrayList.add(new ZenModeButtonPreferenceController(context, "activate", this.mBackend));
        arrayList.add(new ZenModeActionsPreferenceController(context, "actions", this.mBackend));
        arrayList.add(new ZenModePeopleLinkPreferenceController(context, this.mBackend));
        arrayList.add(
                new ZenModeAppsLinkPreferenceController(
                        context,
                        this,
                        ApplicationsState.getInstance(
                                (Application) context.getApplicationContext()),
                        this.mBackend));
        arrayList.add(new ZenModeOtherLinkPreferenceController(context, this.mBackend));
        arrayList.add(new ZenModeDisplayLinkPreferenceController(context, this.mBackend));
        final ZenModeSetTriggerLinkPreferenceController zenModeSetTriggerLinkPreferenceController =
                new ZenModeSetTriggerLinkPreferenceController(
                        context, "zen_automatic_trigger_category", this.mBackend);
        zenModeSetTriggerLinkPreferenceController.mSwitchChangeListener =
                new Preference
                        .OnPreferenceChangeListener() { // from class:
                                                        // com.android.settings.notification.modes.ZenModeSetTriggerLinkPreferenceController$$ExternalSyntheticLambda0
                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference, Object obj) {
                        ZenModeSetTriggerLinkPreferenceController
                                zenModeSetTriggerLinkPreferenceController2 =
                                        ZenModeSetTriggerLinkPreferenceController.this;
                        zenModeSetTriggerLinkPreferenceController2.getClass();
                        final boolean booleanValue = ((Boolean) obj).booleanValue();
                        return zenModeSetTriggerLinkPreferenceController2.saveMode(
                                new Function() { // from class:
                                                 // com.android.settings.notification.modes.ZenModeSetTriggerLinkPreferenceController$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj2) {
                                        boolean z = booleanValue;
                                        ZenMode zenMode = (ZenMode) obj2;
                                        if (z != zenMode.mRule.isEnabled()) {
                                            zenMode.mRule.setEnabled(z);
                                        }
                                        return zenMode;
                                    }
                                });
                    }
                };
        arrayList.add(zenModeSetTriggerLinkPreferenceController);
        return arrayList;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 142;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.modes_rule_settings;
    }

    @Override // com.android.settings.notification.modes.ZenModesFragmentBase,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        ZenMode zenMode = this.mZenMode;
        AutomaticZenRule automaticZenRule = zenMode == null ? null : zenMode.mRule;
        if (zenMode == null || automaticZenRule == null) {
            return;
        }
        getActivity().setTitle(automaticZenRule.getName());
    }
}

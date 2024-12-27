package com.android.settings.notification.zen;

import android.app.AutomaticZenRule;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ZenCustomRuleSettingsBase extends ZenModeSettingsBase {
    public String mId;
    public AutomaticZenRule mRule;
    public List mControllers = new ArrayList();
    public boolean mIsFirstLaunch = true;

    public abstract String getPreferenceCategoryKey();

    @Override // com.android.settings.notification.zen.ZenModeSettingsBase,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        if (arguments == null || !arguments.containsKey("RULE_ID")) {
            Log.d(
                    "ZenCustomRuleSettings",
                    "Rule id required to set custom dnd rule config settings");
            finish();
        } else {
            this.mId = arguments.getString("RULE_ID");
            updateRule();
        }
    }

    @Override // com.android.settings.notification.zen.ZenModeSettingsBase,
              // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onResume() {
        if (!this.mIsFirstLaunch) {
            updateRule();
        }
        super.onResume();
        updatePreferences$1();
    }

    @Override // com.android.settings.notification.zen.ZenModeSettingsBase
    public void onZenModeConfigChanged() {
        updateRule();
        updatePreferences$1();
        updatePreferenceStates();
    }

    public void updatePreferences$1() {
        Preference findPreference;
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        String preferenceCategoryKey = getPreferenceCategoryKey();
        if (preferenceCategoryKey != null
                && (findPreference = preferenceScreen.findPreference(preferenceCategoryKey))
                        != null) {
            findPreference.setTitle(
                    ((ZenModeSettingsBase) this)
                            .mContext
                            .getResources()
                            .getString(
                                    R.string.zen_mode_custom_behavior_category_title,
                                    this.mRule.getName()));
        }
        Iterator it = ((ArrayList) this.mControllers).iterator();
        while (it.hasNext()) {
            AbstractZenCustomRulePreferenceController abstractZenCustomRulePreferenceController =
                    (AbstractZenCustomRulePreferenceController)
                            ((AbstractPreferenceController) it.next());
            abstractZenCustomRulePreferenceController.onResume();
            if (!this.mIsFirstLaunch) {
                abstractZenCustomRulePreferenceController.displayPreference(preferenceScreen);
            }
        }
        this.mIsFirstLaunch = false;
    }

    public final void updateRule() {
        ZenModeBackend zenModeBackend = this.mBackend;
        this.mRule =
                NotificationManager.from(zenModeBackend.mContext).getAutomaticZenRule(this.mId);
        Iterator it = this.mControllers.iterator();
        while (it.hasNext()) {
            AbstractZenCustomRulePreferenceController abstractZenCustomRulePreferenceController =
                    (AbstractZenCustomRulePreferenceController)
                            ((AbstractPreferenceController) it.next());
            String str = this.mId;
            AutomaticZenRule automaticZenRule = this.mRule;
            abstractZenCustomRulePreferenceController.mId = str;
            abstractZenCustomRulePreferenceController.mRule = automaticZenRule;
        }
    }
}

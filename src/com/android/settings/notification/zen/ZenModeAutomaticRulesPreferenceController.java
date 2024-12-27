package com.android.settings.notification.zen;

import android.app.AutomaticZenRule;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.service.notification.ZenModeConfig;
import android.util.ArraySet;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceCategory;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.utils.ManagedServiceSettings;
import com.android.settings.utils.ZenServiceListing;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeAutomaticRulesPreferenceController
        extends AbstractZenModeAutomaticRulePreferenceController {
    public static final ManagedServiceSettings.Config CONFIG;
    protected SecPreferenceCategory mPreferenceCategory;
    public final ZenRulePreference.OnPreferenceLongClickListener mPreferenceLongClickListner;
    public final ZenServiceListing mServiceListing;

    static {
        BaseSearchIndexProvider baseSearchIndexProvider =
                ZenModeAutomationSettings.SEARCH_INDEX_DATA_PROVIDER;
        CONFIG =
                new ManagedServiceSettings.Config(
                        "ZenModeSettings",
                        null,
                        "android.service.notification.ConditionProviderService",
                        "android.app.action.AUTOMATIC_ZEN_RULE",
                        "android.permission.BIND_CONDITION_PROVIDER_SERVICE",
                        "condition provider",
                        0,
                        0,
                        0);
    }

    public ZenModeAutomaticRulesPreferenceController(
            Context context,
            Fragment fragment,
            Lifecycle lifecycle,
            ZenRulePreference.OnPreferenceLongClickListener onPreferenceLongClickListener) {
        super(context, "zen_mode_automatic_rules", lifecycle);
        ((AbstractZenModeAutomaticRulePreferenceController) this).mBackend =
                ZenModeBackend.getInstance(context);
        this.mPm = this.mContext.getPackageManager();
        ZenServiceListing zenServiceListing = new ZenServiceListing(this.mContext, CONFIG);
        this.mServiceListing = zenServiceListing;
        zenServiceListing.reloadApprovedServices();
        this.mPreferenceLongClickListner = onPreferenceLongClickListener;
    }

    public ZenRulePreference createZenRulePreference(Map.Entry<String, AutomaticZenRule> entry) {
        return new ZenRulePreference(
                this.mPreferenceCategory.getContext(),
                entry,
                ((AbstractZenModeAutomaticRulePreferenceController) this).mBackend,
                this.mPreferenceLongClickListner);
    }

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecPreferenceCategory secPreferenceCategory =
                (SecPreferenceCategory) preferenceScreen.findPreference("zen_mode_automatic_rules");
        this.mPreferenceCategory = secPreferenceCategory;
        secPreferenceCategory.setPersistent();
    }

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "zen_mode_automatic_rules";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    public void reloadAllRules(Map.Entry<String, AutomaticZenRule>[] entryArr) {
        this.mPreferenceCategory.removeAll();
        for (Map.Entry<String, AutomaticZenRule> entry : entryArr) {
            ZenRulePreference createZenRulePreference = createZenRulePreference(entry);
            createZenRulePreference.getClass();
            SecPreferenceUtils.applySummaryColor(createZenRulePreference, true);
            AutomaticZenRule value = entry.getValue();
            ComponentName owner = value.getOwner();
            ZenServiceListing zenServiceListing = this.mServiceListing;
            ComponentInfo componentInfo = null;
            if (owner != null) {
                Iterator it = ((ArraySet) zenServiceListing.mApprovedComponents).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    ComponentInfo componentInfo2 = (ComponentInfo) it.next();
                    if (new ComponentName(componentInfo2.packageName, componentInfo2.name)
                            .equals(owner)) {
                        componentInfo = componentInfo2;
                        break;
                    }
                }
            } else {
                zenServiceListing.getClass();
            }
            ComponentName settingsActivity =
                    AbstractZenModeAutomaticRulePreferenceController.getSettingsActivity(
                            this.mPm, value, componentInfo);
            String key = entry.getKey();
            Intent putExtra =
                    new Intent()
                            .addFlags(67108864)
                            .putExtra("android.service.notification.extra.RULE_ID", key)
                            .putExtra("android.app.extra.AUTOMATIC_RULE_ID", key);
            if (settingsActivity != null) {
                putExtra.setComponent(settingsActivity);
            } else {
                putExtra.setAction("android.settings.ZEN_MODE_SCHEDULE_RULE_SETTINGS");
                putExtra.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
            }
            if (ZenModeConfig.isValidScheduleConditionId(entry.getValue().getConditionId())) {
                createZenRulePreference.setIntent(putExtra);
            } else if (KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG.equals(value.getPackageName())) {
                createZenRulePreference.setIntent(
                        new Intent("com.samsung.android.app.routines.action.LAUNCH_MODE_TAB"));
            } else if ("com.samsung.android.app.routines".equals(value.getPackageName())) {
                createZenRulePreference.setIntent(
                        new Intent("com.samsung.android.app.routines.action.LAUNCH_ROUTINE_TAB"));
            } else {
                createZenRulePreference.setIntent(putExtra);
            }
            createZenRulePreference.setOnPreferenceChangeListener(
                    new Preference
                            .OnPreferenceChangeListener() { // from class:
                                                            // com.android.settings.notification.zen.ZenModeAutomaticRulesPreferenceController.1
                        @Override // androidx.preference.Preference.OnPreferenceChangeListener
                        public final boolean onPreferenceChange(Preference preference, Object obj) {
                            ZenRulePreference zenRulePreference = (ZenRulePreference) preference;
                            boolean booleanValue = ((Boolean) obj).booleanValue();
                            Map.Entry[] automaticZenRules =
                                    ((AbstractZenModeAutomaticRulePreferenceController)
                                                    ZenModeAutomaticRulesPreferenceController.this)
                                            .mBackend.getAutomaticZenRules();
                            for (int i = 0; i < automaticZenRules.length; i++) {
                                if (Objects.equals(
                                        zenRulePreference.mId, automaticZenRules[i].getKey())) {
                                    AutomaticZenRule automaticZenRule =
                                            (AutomaticZenRule) automaticZenRules[i].getValue();
                                    automaticZenRule.setEnabled(booleanValue);
                                    zenRulePreference.mBackend.updateZenRule(
                                            zenRulePreference.mId, automaticZenRule);
                                    return true;
                                }
                            }
                            return true;
                        }
                    });
            this.mPreferenceCategory.addPreference(createZenRulePreference);
        }
    }

    @Override // com.android.settings.notification.zen.AbstractZenModeAutomaticRulePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        Map.Entry[] automaticZenRules =
                ((AbstractZenModeAutomaticRulePreferenceController) this)
                        .mBackend.getAutomaticZenRules();
        if (this.mPreferenceCategory.getPreferenceCount() != automaticZenRules.length) {
            reloadAllRules(automaticZenRules);
            return;
        }
        for (int i = 0; i < automaticZenRules.length; i++) {
            ZenRulePreference zenRulePreference =
                    (ZenRulePreference) this.mPreferenceCategory.getPreference(i);
            if (!Objects.equals(zenRulePreference.mId, automaticZenRules[i].getKey())) {
                reloadAllRules(automaticZenRules);
                return;
            }
            AutomaticZenRule value = automaticZenRules[i].getValue();
            zenRulePreference.setChecked(zenRulePreference.mRule.isEnabled());
            if (!zenRulePreference.mRule.getName().equals(value.getName())) {
                String name = value.getName();
                zenRulePreference.mName = name;
                zenRulePreference.setTitle(name);
            }
            if (zenRulePreference.mRule.isEnabled() != value.isEnabled()) {
                zenRulePreference.setChecked(value.isEnabled());
            }
            zenRulePreference.setSummary(zenRulePreference.computeRuleSummary(value));
            zenRulePreference.mRule = value;
        }
    }
}

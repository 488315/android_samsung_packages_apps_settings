package com.android.settings.notification.zen;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.service.notification.ZenModeConfig;
import android.service.notification.ZenPolicy;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenCustomRuleConfigSettings extends ZenCustomRuleSettingsBase {
    public Preference mCallsPreference;
    public Preference mMessagesPreference;
    public Preference mNotificationsPreference;
    public ZenModeSettings.SummaryBuilder mSummaryBuilder;

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        ((ZenCustomRuleSettingsBase) this).mControllers = arrayList;
        arrayList.add(
                new ZenRuleCustomSwitchPreferenceController(
                        context, getSettingsLifecycle(), "zen_rule_alarms", 5, 1226));
        ((ZenCustomRuleSettingsBase) this)
                .mControllers.add(
                        new ZenRuleCustomSwitchPreferenceController(
                                context, getSettingsLifecycle(), "zen_rule_media", 6, 1227));
        ((ZenCustomRuleSettingsBase) this)
                .mControllers.add(
                        new ZenRuleCustomSwitchPreferenceController(
                                context, getSettingsLifecycle(), "zen_rule_system", 7, 1340));
        ((ZenCustomRuleSettingsBase) this)
                .mControllers.add(
                        new ZenRuleCustomSwitchPreferenceController(
                                context, getSettingsLifecycle(), "zen_rule_reminders", 0, 167));
        ((ZenCustomRuleSettingsBase) this)
                .mControllers.add(
                        new ZenRuleCustomSwitchPreferenceController(
                                context, getSettingsLifecycle(), "zen_rule_events", 1, 168));
        return ((ZenCustomRuleSettingsBase) this).mControllers;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1605;
    }

    @Override // com.android.settings.notification.zen.ZenCustomRuleSettingsBase
    public final String getPreferenceCategoryKey() {
        return "zen_custom_rule_configuration_category";
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.zen_mode_custom_rule_configuration;
    }

    @Override // com.android.settings.notification.zen.ZenModeSettingsBase,
              // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mSummaryBuilder =
                new ZenModeSettings.SummaryBuilder(((ZenModeSettingsBase) this).mContext);
        Preference findPreference = getPreferenceScreen().findPreference("zen_rule_calls_settings");
        this.mCallsPreference = findPreference;
        final int i = 0;
        findPreference.setOnPreferenceClickListener(
                new Preference.OnPreferenceClickListener(
                        this) { // from class:
                                // com.android.settings.notification.zen.ZenCustomRuleConfigSettings.1
                    public final /* synthetic */ ZenCustomRuleConfigSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public final boolean onPreferenceClick(Preference preference) {
                        switch (i) {
                            case 0:
                                ZenCustomRuleConfigSettings zenCustomRuleConfigSettings =
                                        this.this$0;
                                SubSettingLauncher subSettingLauncher =
                                        new SubSettingLauncher(
                                                ((ZenModeSettingsBase) zenCustomRuleConfigSettings)
                                                        .mContext);
                                String name = ZenCustomRuleCallsSettings.class.getName();
                                SubSettingLauncher.LaunchRequest launchRequest =
                                        subSettingLauncher.mLaunchRequest;
                                launchRequest.mDestinationName = name;
                                Bundle bundle2 = new Bundle();
                                bundle2.putString("RULE_ID", zenCustomRuleConfigSettings.mId);
                                launchRequest.mArguments = bundle2;
                                launchRequest.mSourceMetricsCategory = 1611;
                                subSettingLauncher.launch();
                                break;
                            case 1:
                                ZenCustomRuleConfigSettings zenCustomRuleConfigSettings2 =
                                        this.this$0;
                                SubSettingLauncher subSettingLauncher2 =
                                        new SubSettingLauncher(
                                                ((ZenModeSettingsBase) zenCustomRuleConfigSettings2)
                                                        .mContext);
                                String name2 = ZenCustomRuleMessagesSettings.class.getName();
                                SubSettingLauncher.LaunchRequest launchRequest2 =
                                        subSettingLauncher2.mLaunchRequest;
                                launchRequest2.mDestinationName = name2;
                                Bundle bundle3 = new Bundle();
                                bundle3.putString("RULE_ID", zenCustomRuleConfigSettings2.mId);
                                launchRequest2.mArguments = bundle3;
                                launchRequest2.mSourceMetricsCategory = 1610;
                                subSettingLauncher2.launch();
                                break;
                            default:
                                ZenCustomRuleConfigSettings zenCustomRuleConfigSettings3 =
                                        this.this$0;
                                SubSettingLauncher subSettingLauncher3 =
                                        new SubSettingLauncher(
                                                ((ZenModeSettingsBase) zenCustomRuleConfigSettings3)
                                                        .mContext);
                                String name3 = ZenCustomRuleNotificationsSettings.class.getName();
                                SubSettingLauncher.LaunchRequest launchRequest3 =
                                        subSettingLauncher3.mLaunchRequest;
                                launchRequest3.mDestinationName = name3;
                                Bundle bundle4 = new Bundle();
                                bundle4.putString("RULE_ID", zenCustomRuleConfigSettings3.mId);
                                launchRequest3.mArguments = bundle4;
                                launchRequest3.mSourceMetricsCategory = 1608;
                                subSettingLauncher3.launch();
                                break;
                        }
                        return true;
                    }
                });
        Preference findPreference2 =
                getPreferenceScreen().findPreference("zen_rule_messages_settings");
        this.mMessagesPreference = findPreference2;
        final int i2 = 1;
        findPreference2.setOnPreferenceClickListener(
                new Preference.OnPreferenceClickListener(
                        this) { // from class:
                                // com.android.settings.notification.zen.ZenCustomRuleConfigSettings.1
                    public final /* synthetic */ ZenCustomRuleConfigSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public final boolean onPreferenceClick(Preference preference) {
                        switch (i2) {
                            case 0:
                                ZenCustomRuleConfigSettings zenCustomRuleConfigSettings =
                                        this.this$0;
                                SubSettingLauncher subSettingLauncher =
                                        new SubSettingLauncher(
                                                ((ZenModeSettingsBase) zenCustomRuleConfigSettings)
                                                        .mContext);
                                String name = ZenCustomRuleCallsSettings.class.getName();
                                SubSettingLauncher.LaunchRequest launchRequest =
                                        subSettingLauncher.mLaunchRequest;
                                launchRequest.mDestinationName = name;
                                Bundle bundle2 = new Bundle();
                                bundle2.putString("RULE_ID", zenCustomRuleConfigSettings.mId);
                                launchRequest.mArguments = bundle2;
                                launchRequest.mSourceMetricsCategory = 1611;
                                subSettingLauncher.launch();
                                break;
                            case 1:
                                ZenCustomRuleConfigSettings zenCustomRuleConfigSettings2 =
                                        this.this$0;
                                SubSettingLauncher subSettingLauncher2 =
                                        new SubSettingLauncher(
                                                ((ZenModeSettingsBase) zenCustomRuleConfigSettings2)
                                                        .mContext);
                                String name2 = ZenCustomRuleMessagesSettings.class.getName();
                                SubSettingLauncher.LaunchRequest launchRequest2 =
                                        subSettingLauncher2.mLaunchRequest;
                                launchRequest2.mDestinationName = name2;
                                Bundle bundle3 = new Bundle();
                                bundle3.putString("RULE_ID", zenCustomRuleConfigSettings2.mId);
                                launchRequest2.mArguments = bundle3;
                                launchRequest2.mSourceMetricsCategory = 1610;
                                subSettingLauncher2.launch();
                                break;
                            default:
                                ZenCustomRuleConfigSettings zenCustomRuleConfigSettings3 =
                                        this.this$0;
                                SubSettingLauncher subSettingLauncher3 =
                                        new SubSettingLauncher(
                                                ((ZenModeSettingsBase) zenCustomRuleConfigSettings3)
                                                        .mContext);
                                String name3 = ZenCustomRuleNotificationsSettings.class.getName();
                                SubSettingLauncher.LaunchRequest launchRequest3 =
                                        subSettingLauncher3.mLaunchRequest;
                                launchRequest3.mDestinationName = name3;
                                Bundle bundle4 = new Bundle();
                                bundle4.putString("RULE_ID", zenCustomRuleConfigSettings3.mId);
                                launchRequest3.mArguments = bundle4;
                                launchRequest3.mSourceMetricsCategory = 1608;
                                subSettingLauncher3.launch();
                                break;
                        }
                        return true;
                    }
                });
        Preference findPreference3 = getPreferenceScreen().findPreference("zen_rule_notifications");
        this.mNotificationsPreference = findPreference3;
        final int i3 = 2;
        findPreference3.setOnPreferenceClickListener(
                new Preference.OnPreferenceClickListener(
                        this) { // from class:
                                // com.android.settings.notification.zen.ZenCustomRuleConfigSettings.1
                    public final /* synthetic */ ZenCustomRuleConfigSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public final boolean onPreferenceClick(Preference preference) {
                        switch (i3) {
                            case 0:
                                ZenCustomRuleConfigSettings zenCustomRuleConfigSettings =
                                        this.this$0;
                                SubSettingLauncher subSettingLauncher =
                                        new SubSettingLauncher(
                                                ((ZenModeSettingsBase) zenCustomRuleConfigSettings)
                                                        .mContext);
                                String name = ZenCustomRuleCallsSettings.class.getName();
                                SubSettingLauncher.LaunchRequest launchRequest =
                                        subSettingLauncher.mLaunchRequest;
                                launchRequest.mDestinationName = name;
                                Bundle bundle2 = new Bundle();
                                bundle2.putString("RULE_ID", zenCustomRuleConfigSettings.mId);
                                launchRequest.mArguments = bundle2;
                                launchRequest.mSourceMetricsCategory = 1611;
                                subSettingLauncher.launch();
                                break;
                            case 1:
                                ZenCustomRuleConfigSettings zenCustomRuleConfigSettings2 =
                                        this.this$0;
                                SubSettingLauncher subSettingLauncher2 =
                                        new SubSettingLauncher(
                                                ((ZenModeSettingsBase) zenCustomRuleConfigSettings2)
                                                        .mContext);
                                String name2 = ZenCustomRuleMessagesSettings.class.getName();
                                SubSettingLauncher.LaunchRequest launchRequest2 =
                                        subSettingLauncher2.mLaunchRequest;
                                launchRequest2.mDestinationName = name2;
                                Bundle bundle3 = new Bundle();
                                bundle3.putString("RULE_ID", zenCustomRuleConfigSettings2.mId);
                                launchRequest2.mArguments = bundle3;
                                launchRequest2.mSourceMetricsCategory = 1610;
                                subSettingLauncher2.launch();
                                break;
                            default:
                                ZenCustomRuleConfigSettings zenCustomRuleConfigSettings3 =
                                        this.this$0;
                                SubSettingLauncher subSettingLauncher3 =
                                        new SubSettingLauncher(
                                                ((ZenModeSettingsBase) zenCustomRuleConfigSettings3)
                                                        .mContext);
                                String name3 = ZenCustomRuleNotificationsSettings.class.getName();
                                SubSettingLauncher.LaunchRequest launchRequest3 =
                                        subSettingLauncher3.mLaunchRequest;
                                launchRequest3.mDestinationName = name3;
                                Bundle bundle4 = new Bundle();
                                bundle4.putString("RULE_ID", zenCustomRuleConfigSettings3.mId);
                                launchRequest3.mArguments = bundle4;
                                launchRequest3.mSourceMetricsCategory = 1608;
                                subSettingLauncher3.launch();
                                break;
                        }
                        return true;
                    }
                });
        updateSummaries();
    }

    @Override // com.android.settings.notification.zen.ZenCustomRuleSettingsBase,
              // com.android.settings.notification.zen.ZenModeSettingsBase,
              // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        updateSummaries();
    }

    @Override // com.android.settings.notification.zen.ZenCustomRuleSettingsBase,
              // com.android.settings.notification.zen.ZenModeSettingsBase
    public final void onZenModeConfigChanged() {
        super.onZenModeConfigChanged();
        updateSummaries();
    }

    public final void updateSummaries() {
        ZenModeBackend zenModeBackend = this.mBackend;
        ZenPolicy zenPolicy = this.mRule.getZenPolicy();
        zenModeBackend.getClass();
        NotificationManager.Policy notificationPolicy =
                new ZenModeConfig().toNotificationPolicy(zenPolicy);
        this.mCallsPreference.setSummary(
                this.mSummaryBuilder.getCallsSettingSummary(notificationPolicy));
        this.mMessagesPreference.setSummary(
                this.mSummaryBuilder.getMessagesSettingSummary(notificationPolicy));
        this.mNotificationsPreference.setSummary(
                this.mSummaryBuilder
                        .mContext
                        .getResources()
                        .getString(R.string.zen_mode_restrict_notifications_hide_summary));
    }
}

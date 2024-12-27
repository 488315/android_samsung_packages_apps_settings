package com.samsung.android.settings.wifi.intelligent;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.os.UserManager;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.widget.CompoundButton;

import androidx.preference.Preference;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.wifitrackerlib.SemWifiUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SwitchToBetterWifiSettings extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass2(R.xml.sec_switch_to_better_wifi_settings);
    public Context mContext;
    public SettingsMainSwitchBar mSwitchBar;
    public SecSwitchPreference mSwitchWifiScreenOn;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.intelligent.SwitchToBetterWifiSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            if (context.getResources().getBoolean(R.bool.config_show_wifi_settings)) {
                SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
                if (!((UserManager) context.getSystemService("user")).isGuestUser()) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return FileType.XLSB;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.show();
        SecSwitchPreference secSwitchPreference =
                (SecSwitchPreference) findPreference("switch_to_better_wifi_with_screen_on");
        this.mSwitchWifiScreenOn = secSwitchPreference;
        if (secSwitchPreference != null) {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            secSwitchPreference.setChecked(
                    Settings.Global.getInt(
                                    contentResolver,
                                    "sem_wifi_switch_to_better_wifi_on_screen_enabled",
                                    !"VZW".equals(Utils.getSalesCode()) ? 1 : 0)
                            == 1);
            this.mSwitchWifiScreenOn.setOnPreferenceChangeListener(
                    new Preference
                            .OnPreferenceChangeListener() { // from class:
                                                            // com.samsung.android.settings.wifi.intelligent.SwitchToBetterWifiSettings.1
                        @Override // androidx.preference.Preference.OnPreferenceChangeListener
                        public final boolean onPreferenceChange(Preference preference, Object obj) {
                            Boolean bool = (Boolean) obj;
                            SALogging.insertSALog(
                                    bool.booleanValue() ? 1L : 0L, "WIFI_210", "1223");
                            return Settings.Global.putInt(
                                    SwitchToBetterWifiSettings.this.mContext.getContentResolver(),
                                    "sem_wifi_switch_to_better_wifi_on_screen_enabled",
                                    bool.booleanValue() ? 1 : 0);
                        }
                    });
        }
        super.onActivityCreated(bundle);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        SALogging.insertSALog(z ? 1L : 0L, "WIFI_210", "1221");
        Settings.Global.putInt(
                this.mContext.getContentResolver(),
                "sem_wifi_switch_to_better_wifi_enabled",
                z ? 1 : 0);
        this.mSwitchWifiScreenOn.setEnabled(z);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.sec_switch_to_better_wifi_settings);
        this.mContext = getContext();
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        this.mSwitchBar.hide();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mSwitchBar.removeOnSwitchChangeListener(this);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mSwitchBar.addOnSwitchChangeListener(this);
        ContentResolver contentResolver = this.mContext.getContentResolver();
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        boolean z =
                Settings.Global.getInt(
                                contentResolver,
                                "sem_wifi_switch_to_better_wifi_enabled",
                                !"VZW".equals(Utils.getSalesCode()) ? 1 : 0)
                        == 1;
        this.mSwitchBar.setChecked(z);
        this.mSwitchWifiScreenOn.setEnabled(z);
    }
}

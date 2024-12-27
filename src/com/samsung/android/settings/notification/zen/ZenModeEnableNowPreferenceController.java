package com.samsung.android.settings.notification.zen;

import android.provider.Settings;
import android.service.notification.ZenModeConfig;

import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settings.notification.SettingsEnableZenModeDialog;
import com.android.settings.notification.zen.AbstractZenModePreferenceController;
import com.android.settings.notification.zen.ZenModeBackend;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.SALogging;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ZenModeEnableNowPreferenceController extends AbstractZenModePreferenceController
        implements Preference.OnPreferenceChangeListener {
    public FragmentManager mFragment;

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "enable_now";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        ZenModeBackend zenModeBackend = this.mBackend;
        if (booleanValue) {
            int zenDuration = getZenDuration();
            if (zenDuration == -1) {
                new SettingsEnableZenModeDialog().show(this.mFragment, "PrefControllerMixin");
            } else if (zenDuration != 0) {
                zenModeBackend.setZenModeForDuration(zenDuration);
            } else {
                zenModeBackend.setZenMode(1);
            }
        } else {
            zenModeBackend.setZenMode(0);
        }
        if (getZenMode() != 1) {
            ZenModeConfig zenModeConfig = this.mNotificationManager.getZenModeConfig();
            HashMap hashMap = new HashMap();
            if (zenModeConfig.manualRule != null) {
                int zenDuration2 = getZenDuration();
                hashMap.put("type", "manual");
                String str = zenModeConfig.manualRule.enabler;
                if (str != null) {
                    hashMap.put("app", str);
                }
                hashMap.put("timeset", String.valueOf(zenDuration2));
            }
            for (ZenModeConfig.ZenRule zenRule : zenModeConfig.automaticRules.values()) {
                if (zenRule.isAutomaticActive()) {
                    hashMap.put("type", "byRule");
                    String str2 = zenRule.enabler;
                    if (str2 == null) {
                        str2 = ApnSettings.MVNO_NONE;
                    }
                    hashMap.put("app", str2);
                }
            }
            SALogging.insertSALog(Integer.toString(36031), "NSTE0300", hashMap, 0);
        }
        return false;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        ((SwitchPreferenceCompat) preference)
                .setChecked(
                        Settings.Global.getInt(this.mContext.getContentResolver(), "zen_mode", 0)
                                != 0);
    }
}

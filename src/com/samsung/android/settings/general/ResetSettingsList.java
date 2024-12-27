package com.samsung.android.settings.general;

import com.android.settings.DisplaySettings;
import com.android.settings.accounts.AccountDashboardFragment;
import com.android.settings.datetime.DateTimeSettings;
import com.android.settings.notification.zen.ZenModeSettings;

import com.samsung.android.settings.asbase.reset.ResetDefaultRingtonePreference;
import com.samsung.android.settings.asbase.reset.ResetDualSoundRingtoneSettings;
import com.samsung.android.settings.asbase.reset.ResetSoundSettings;
import com.samsung.android.settings.asbase.reset.ResetSoundSystemFeedbackSettings;
import com.samsung.android.settings.asbase.reset.ResetVibrationIntensitySettings;
import com.samsung.android.settings.asbase.reset.ResetVolumeLimiterSettings;
import com.samsung.android.settings.asbase.reset.ResetVolumeSettings;
import com.samsung.android.settings.autopoweronoff.AutoPowerOnOffSettings;
import com.samsung.android.settings.datausage.DataUsageUtilsCHN;
import com.samsung.android.settings.deviceinfo.aboutphone.DeviceNameEditor;
import com.samsung.android.settings.inputmethod.ChangeLanguageShortcutOptionFragment;
import com.samsung.android.settings.inputmethod.MouseAdditionalFirstButtonFragment;
import com.samsung.android.settings.inputmethod.MouseAdditionalSecondButtonFragment;
import com.samsung.android.settings.inputmethod.MouseAndTrackpadSettings;
import com.samsung.android.settings.inputmethod.MouseMiddleButtonFragment;
import com.samsung.android.settings.inputmethod.MouseSecondaryButtonFragment;
import com.samsung.android.settings.lockscreen.LockScreenDataReset;
import com.samsung.android.settings.lockscreen.LockScreenNotificationSettings;
import com.samsung.android.settings.notification.ConfigureNotificationMoreSettings;
import com.samsung.android.settings.usefulfeature.UsefulfeatureReset;
import com.samsung.android.settings.wifi.WifiReset;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ResetSettingsList {
    public static final ArrayList RESET_SETTING_LIST;

    static {
        ArrayList arrayList = new ArrayList();
        RESET_SETTING_LIST = arrayList;
        arrayList.add(LockScreenDataReset.class.getName());
        arrayList.add(ResetSoundSettings.class.getName());
        arrayList.add(ResetDualSoundRingtoneSettings.class.getName());
        arrayList.add(ResetDefaultRingtonePreference.class.getName());
        arrayList.add(ResetVolumeSettings.class.getName());
        arrayList.add(ResetVolumeLimiterSettings.class.getName());
        arrayList.add(ResetSoundSystemFeedbackSettings.class.getName());
        arrayList.add(ResetVibrationIntensitySettings.class.getName());
        arrayList.add(WifiReset.class.getName());
        arrayList.add(DeviceNameEditor.class.getName());
        arrayList.add(AutoPowerOnOffSettings.class.getName());
        arrayList.add(DataUsageUtilsCHN.class.getName());
        arrayList.add(AccountDashboardFragment.class.getName());
        arrayList.add(DisplaySettings.class.getName());
        arrayList.add(UsefulfeatureReset.class.getName());
        arrayList.add(DateTimeSettings.class.getName());
        arrayList.add(ChangeLanguageShortcutOptionFragment.class.getName());
        arrayList.add(MouseAdditionalFirstButtonFragment.class.getName());
        arrayList.add(MouseAdditionalSecondButtonFragment.class.getName());
        arrayList.add(MouseAndTrackpadSettings.class.getName());
        arrayList.add(MouseMiddleButtonFragment.class.getName());
        arrayList.add(MouseSecondaryButtonFragment.class.getName());
        arrayList.add(ConfigureNotificationMoreSettings.class.getName());
        arrayList.add(ZenModeSettings.class.getName());
        arrayList.add(LockScreenNotificationSettings.class.getName());
    }
}

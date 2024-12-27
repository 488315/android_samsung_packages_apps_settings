package com.android.settings.connecteddevice.usb;

import android.app.ActivityManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UsbDetailsTranscodeMtpController extends UsbDetailsController
        implements Preference.OnPreferenceClickListener {
    public PreferenceCategory mPreferenceCategory;
    public SwitchPreferenceCompat mSwitchPreference;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) preferenceScreen.findPreference("usb_transcode_mtp");
        this.mPreferenceCategory = preferenceCategory;
        SwitchPreferenceCompat switchPreferenceCompat =
                new SwitchPreferenceCompat(preferenceCategory.getContext());
        this.mSwitchPreference = switchPreferenceCompat;
        switchPreferenceCompat.setTitle(R.string.usb_transcode_files);
        this.mSwitchPreference.setKey("usb_transcode_files");
        this.mSwitchPreference.setOnPreferenceClickListener(this);
        this.mSwitchPreference.setSummary(R.string.sec_usb_transcode_files_summary);
        this.mPreferenceCategory.addPreference(this.mSwitchPreference);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "usb_transcode_mtp";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (SemFloatingFeature.getInstance()
                .getBoolean("SEC_FLOATING_FEATURE_SETTINGS_DISABLE_TRANSCODE_MTP_MENU")) {
            return false;
        }
        StringBuilder sb = Utils.sBuilder;
        return !ActivityManager.isUserAMonkey();
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        requireAuthAndExecute(
                new Runnable() { // from class:
                                 // com.android.settings.connecteddevice.usb.UsbDetailsTranscodeMtpController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        UsbDetailsTranscodeMtpController usbDetailsTranscodeMtpController =
                                UsbDetailsTranscodeMtpController.this;
                        SystemProperties.set(
                                "sys.fuse.transcode_mtp",
                                Boolean.toString(
                                        usbDetailsTranscodeMtpController
                                                .mSwitchPreference
                                                .mChecked));
                        SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                                new StringBuilder("onClick() - "),
                                usbDetailsTranscodeMtpController.mSwitchPreference.mChecked,
                                "UsbDetailsCtrlMtp");
                        UsbBackend usbBackend = usbDetailsTranscodeMtpController.mUsbBackend;
                        long currentFunctions = usbBackend.mUsbManager.getCurrentFunctions() & (-2);
                        usbBackend.mUsbManager.setCurrentFunctions(0L);
                        Settings.System.putInt(
                                ((UsbDetailsController) usbDetailsTranscodeMtpController)
                                        .mContext.getContentResolver(),
                                "enable_mtp_settings",
                                1);
                        usbBackend.mUsbManager.setCurrentFunctions(currentFunctions);
                    }
                });
        return true;
    }

    @Override // com.android.settings.connecteddevice.usb.UsbDetailsController
    public final void refresh(boolean z, long j, int i, int i2) {
        boolean areFunctionsSupported = this.mUsbBackend.areFunctionsSupported(20L);
        UsbDetailsFragment usbDetailsFragment = this.mFragment;
        boolean z2 = false;
        if (areFunctionsSupported) {
            usbDetailsFragment.getPreferenceScreen().addPreference(this.mPreferenceCategory);
            Log.i(
                    "UsbDetailsCtrlMtp",
                    "refresh() " + SystemProperties.getBoolean("sys.fuse.transcode_mtp", false));
        } else {
            usbDetailsFragment.getPreferenceScreen().removePreference(this.mPreferenceCategory);
        }
        this.mSwitchPreference.setChecked(
                SystemProperties.getBoolean("sys.fuse.transcode_mtp", false));
        PreferenceCategory preferenceCategory = this.mPreferenceCategory;
        if (z && i2 == 2 && ((4 & j) != 0 || (j & 16) != 0)) {
            z2 = true;
        }
        preferenceCategory.setEnabled(z2);
    }
}

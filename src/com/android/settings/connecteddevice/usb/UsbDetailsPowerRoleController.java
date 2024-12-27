package com.android.settings.connecteddevice.usb;

import android.app.ActivityManager;
import android.content.Context;
import android.hardware.usb.UsbPort;
import android.hardware.usb.UsbPortStatus;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.emergencymode.SemEmergencyManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UsbDetailsPowerRoleController extends UsbDetailsController
        implements Preference.OnPreferenceChangeListener {
    public final UsbDetailsPowerRoleController$$ExternalSyntheticLambda0 mFailureCallback;
    public int mNextPowerRole;
    public PreferenceCategory mPreferenceCategory;
    public SwitchPreferenceCompat mSwitchPreference;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.settings.connecteddevice.usb.UsbDetailsPowerRoleController$$ExternalSyntheticLambda0] */
    public UsbDetailsPowerRoleController(
            Context context, UsbBackend usbBackend, UsbDetailsFragment usbDetailsFragment) {
        super(context, usbBackend, usbDetailsFragment);
        this.mFailureCallback =
                new Runnable() { // from class:
                                 // com.android.settings.connecteddevice.usb.UsbDetailsPowerRoleController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        UsbDetailsPowerRoleController usbDetailsPowerRoleController =
                                UsbDetailsPowerRoleController.this;
                        if (usbDetailsPowerRoleController.mNextPowerRole != 0) {
                            Log.i("UsbDetailsCtrlPowerRole", "FailureCallback, switch failed");
                            usbDetailsPowerRoleController.mNextPowerRole = 0;
                        }
                    }
                };
        this.mNextPowerRole = 0;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) preferenceScreen.findPreference("usb_details_power_role");
        this.mPreferenceCategory = preferenceCategory;
        SwitchPreferenceCompat switchPreferenceCompat =
                new SwitchPreferenceCompat(preferenceCategory.getContext());
        this.mSwitchPreference = switchPreferenceCompat;
        switchPreferenceCompat.setTitle(R.string.usb_use_power_only);
        this.mSwitchPreference.setKey("usb_use_power_only");
        this.mSwitchPreference.setOnPreferenceChangeListener(this);
        this.mSwitchPreference.setSummary(R.string.usb_use_power_description);
        this.mPreferenceCategory.addPreference(this.mSwitchPreference);
        this.mFragment.getPreferenceScreen().removePreference(this.mPreferenceCategory);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "usb_details_power_role";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        StringBuilder sb = Utils.sBuilder;
        return !ActivityManager.isUserAMonkey();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        int i = 2;
        int i2 = booleanValue ? 1 : 2;
        UsbBackend usbBackend = this.mUsbBackend;
        usbBackend.updatePorts();
        UsbPortStatus usbPortStatus = usbBackend.mPortStatus;
        if ((usbPortStatus == null ? 0 : usbPortStatus.getCurrentPowerRole()) != i2
                && this.mNextPowerRole == 0) {
            StringBuilder sb = Utils.sBuilder;
            if (!ActivityManager.isUserAMonkey()) {
                Log.i("UsbDetailsCtrlPowerRole", "user set : " + i2);
                int dataRole = usbBackend.getDataRole();
                if (usbBackend.areAllRolesSupported()) {
                    i = dataRole;
                } else if (i2 == 1) {
                    i = 1;
                } else if (i2 != 2) {
                    i = 0;
                }
                UsbPort usbPort = usbBackend.mPort;
                if (usbPort != null) {
                    usbPort.setRoles(i2, i);
                }
                this.mNextPowerRole = i2;
                this.mHandler.postDelayed(
                        this.mFailureCallback, usbBackend.areAllRolesSupported() ? 4000L : 15000L);
            }
        }
        return false;
    }

    @Override // com.android.settings.connecteddevice.usb.UsbDetailsController
    public final void refresh(boolean z, long j, int i, int i2) {
        Log.i(
                "UsbDetailsCtrlPowerRole",
                "refresh() connected : "
                        + z
                        + ", functions : "
                        + j
                        + ", powerRole : "
                        + i
                        + ", dataRole : "
                        + i2);
        SemEmergencyManager.getInstance(((UsbDetailsController) this).mContext);
        StringBuilder sb = new StringBuilder("1: ");
        sb.append(SemEmergencyManager.isEmergencyMode(((UsbDetailsController) this).mContext));
        sb.append(", 2: ");
        UsbBackend usbBackend = this.mUsbBackend;
        sb.append(usbBackend.areAllRolesSupported());
        Log.d("UsbDetailsCtrlPowerRole", sb.toString());
        UsbDetailsFragment usbDetailsFragment = this.mFragment;
        if (z
                && (!usbBackend.areAllRolesSupported()
                        || SemEmergencyManager.isEmergencyMode(
                                ((UsbDetailsController) this).mContext))) {
            usbDetailsFragment.getPreferenceScreen().removePreference(this.mPreferenceCategory);
        } else if (z
                && usbBackend.areAllRolesSupported()
                && !SemEmergencyManager.isEmergencyMode(((UsbDetailsController) this).mContext)) {
            usbDetailsFragment.getPreferenceScreen().addPreference(this.mPreferenceCategory);
        }
        if (i == 1) {
            this.mSwitchPreference.setChecked(true);
            this.mPreferenceCategory.setEnabled(true);
        } else if (i == 2) {
            this.mSwitchPreference.setChecked(false);
            this.mPreferenceCategory.setEnabled(true);
        } else if (!z || i == 0) {
            this.mPreferenceCategory.setEnabled(false);
        }
        int i3 = this.mNextPowerRole;
        if (i3 == 0 || i == 0) {
            return;
        }
        if (i3 != i) {
            Log.i("UsbDetailsCtrlPowerRole", "refresh, switch failed");
        }
        this.mNextPowerRole = 0;
        this.mHandler.removeCallbacks(this.mFailureCallback);
    }
}

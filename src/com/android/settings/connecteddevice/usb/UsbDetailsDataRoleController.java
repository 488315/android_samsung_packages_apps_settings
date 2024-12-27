package com.android.settings.connecteddevice.usb;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UsbDetailsDataRoleController extends UsbDetailsController
        implements SelectorWithWidgetPreference.OnClickListener {
    public final Context mContext;
    public SelectorWithWidgetPreference mDevicePref;
    public final UsbDetailsDataRoleController$$ExternalSyntheticLambda1 mFailureCallback;
    public SelectorWithWidgetPreference mHostPref;
    public SelectorWithWidgetPreference mNextRolePref;
    public PreferenceCategory mPreferenceCategory;

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.settings.connecteddevice.usb.UsbDetailsDataRoleController$$ExternalSyntheticLambda1] */
    public UsbDetailsDataRoleController(
            Context context, UsbBackend usbBackend, UsbDetailsFragment usbDetailsFragment) {
        super(context, usbBackend, usbDetailsFragment);
        this.mFailureCallback =
                new Runnable() { // from class:
                                 // com.android.settings.connecteddevice.usb.UsbDetailsDataRoleController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        UsbDetailsDataRoleController usbDetailsDataRoleController =
                                UsbDetailsDataRoleController.this;
                        SelectorWithWidgetPreference selectorWithWidgetPreference =
                                usbDetailsDataRoleController.mNextRolePref;
                        if (selectorWithWidgetPreference != null) {
                            selectorWithWidgetPreference.setSummary(R.string.usb_switching_failed);
                            usbDetailsDataRoleController.mNextRolePref = null;
                        }
                    }
                };
        this.mContext = context;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreferenceCategory =
                (PreferenceCategory) preferenceScreen.findPreference("usb_details_data_role");
        String num = Integer.toString(1);
        SelectorWithWidgetPreference selectorWithWidgetPreference =
                new SelectorWithWidgetPreference(this.mPreferenceCategory.getContext());
        selectorWithWidgetPreference.setKey(num);
        selectorWithWidgetPreference.setTitle(R.string.usb_control_host);
        selectorWithWidgetPreference.mListener = this;
        this.mPreferenceCategory.addPreference(selectorWithWidgetPreference);
        this.mHostPref = selectorWithWidgetPreference;
        String num2 = Integer.toString(2);
        SelectorWithWidgetPreference selectorWithWidgetPreference2 =
                new SelectorWithWidgetPreference(this.mPreferenceCategory.getContext());
        selectorWithWidgetPreference2.setKey(num2);
        selectorWithWidgetPreference2.setTitle(R.string.usb_control_device);
        selectorWithWidgetPreference2.mListener = this;
        this.mPreferenceCategory.addPreference(selectorWithWidgetPreference2);
        this.mDevicePref = selectorWithWidgetPreference2;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "usb_details_data_role";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (Rune.supportDataRole()) {
            return !ActivityManager.isUserAMonkey();
        }
        return false;
    }

    @Override // com.android.settingslib.widget.SelectorWithWidgetPreference.OnClickListener
    public final void onRadioButtonClicked(
            final SelectorWithWidgetPreference selectorWithWidgetPreference) {
        requireAuthAndExecute(
                new Runnable() { // from class:
                    // com.android.settings.connecteddevice.usb.UsbDetailsDataRoleController$$ExternalSyntheticLambda0
                    /* JADX WARN: Code restructure failed: missing block: B:16:0x007f, code lost:

                       if (r1 != 2) goto L27;
                    */
                    /* JADX WARN: Removed duplicated region for block: B:19:0x0089  */
                    /* JADX WARN: Removed duplicated region for block: B:22:0x009c  */
                    /* JADX WARN: Removed duplicated region for block: B:26:0x009f  */
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void run() {
                        /*
                            r8 = this;
                            com.android.settings.connecteddevice.usb.UsbDetailsDataRoleController r0 = com.android.settings.connecteddevice.usb.UsbDetailsDataRoleController.this
                            com.android.settingslib.widget.SelectorWithWidgetPreference r8 = r2
                            r0.getClass()
                            java.lang.String r1 = r8.getKey()
                            int r1 = java.lang.Integer.parseInt(r1)
                            java.lang.String r2 = "onClick() - click current mode : "
                            java.lang.String r3 = "UsbDetailsCtrlDataRole"
                            com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0.m(r1, r2, r3)
                            com.android.settings.connecteddevice.usb.UsbBackend r2 = r0.mUsbBackend
                            int r4 = r2.getDataRole()
                            if (r1 == r4) goto La6
                            com.android.settingslib.widget.SelectorWithWidgetPreference r4 = r0.mNextRolePref
                            if (r4 != 0) goto La6
                            java.lang.StringBuilder r4 = com.android.settings.Utils.sBuilder
                            boolean r4 = android.app.ActivityManager.isUserAMonkey()
                            if (r4 != 0) goto La6
                            int r4 = r2.getDataRole()
                            r5 = 0
                            r6 = 1
                            if (r4 != r6) goto L69
                            android.content.Context r4 = r0.mContext     // Catch: java.lang.Exception -> L64
                            android.content.ContentResolver r4 = r4.getContentResolver()     // Catch: java.lang.Exception -> L64
                            java.lang.String r7 = "content://com.sec.android.easyMover.statusProvider/isOtgTransferring"
                            android.net.Uri r7 = android.net.Uri.parse(r7)     // Catch: java.lang.Exception -> L64
                            java.lang.String r4 = r4.getType(r7)     // Catch: java.lang.Exception -> L64
                            java.lang.String r7 = "TRUE"
                            boolean r4 = r7.equals(r4)     // Catch: java.lang.Exception -> L64
                            if (r4 == 0) goto L64
                            java.lang.String r4 = "checkSmartSwitchTransfer - return true"
                            android.util.Log.i(r3, r4)     // Catch: java.lang.Exception -> L64
                            android.content.Context r8 = r0.mContext
                            android.content.res.Resources r0 = r8.getResources()
                            r1 = 2132029399(0x7f142fd7, float:1.9697414E38)
                            java.lang.String r0 = r0.getString(r1)
                            android.widget.Toast r8 = android.widget.Toast.makeText(r8, r0, r5)
                            r8.show()
                            goto La6
                        L64:
                            java.lang.String r4 = "checkSmartSwitchTransfer - return false"
                            android.util.Log.i(r3, r4)
                        L69:
                            r2.updatePorts()
                            android.hardware.usb.UsbPortStatus r3 = r2.mPortStatus
                            if (r3 != 0) goto L72
                            r3 = r5
                            goto L76
                        L72:
                            int r3 = r3.getCurrentPowerRole()
                        L76:
                            boolean r4 = r2.areAllRolesSupported()
                            if (r4 != 0) goto L82
                            if (r1 == r6) goto L84
                            r3 = 2
                            if (r1 == r3) goto L82
                            goto L85
                        L82:
                            r5 = r3
                            goto L85
                        L84:
                            r5 = r6
                        L85:
                            android.hardware.usb.UsbPort r3 = r2.mPort
                            if (r3 == 0) goto L8c
                            r3.setRoles(r5, r1)
                        L8c:
                            r0.mNextRolePref = r8
                            r1 = 2132029525(0x7f143055, float:1.969767E38)
                            r8.setSummary(r1)
                            android.os.Handler r8 = r0.mHandler
                            boolean r1 = r2.areAllRolesSupported()
                            if (r1 == 0) goto L9f
                            r1 = 4000(0xfa0, double:1.9763E-320)
                            goto La1
                        L9f:
                            r1 = 15000(0x3a98, double:7.411E-320)
                        La1:
                            com.android.settings.connecteddevice.usb.UsbDetailsDataRoleController$$ExternalSyntheticLambda1 r0 = r0.mFailureCallback
                            r8.postDelayed(r0, r1)
                        La6:
                            return
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.android.settings.connecteddevice.usb.UsbDetailsDataRoleController$$ExternalSyntheticLambda0.run():void");
                    }
                });
    }

    @Override // com.android.settings.connecteddevice.usb.UsbDetailsController
    public final void refresh(boolean z, long j, int i, int i2) {
        if (Rune.supportDataRole()) {
            Log.i(
                    "UsbDetailsCtrlDataRole",
                    "refresh() connected : "
                            + z
                            + ", functions : "
                            + j
                            + ", powerRole : "
                            + i
                            + ", dataRole : "
                            + i2);
            if (i2 == 2) {
                this.mDevicePref.setChecked(true);
                this.mHostPref.setChecked(false);
                this.mPreferenceCategory.setEnabled(true);
            } else if (i2 == 1) {
                this.mDevicePref.setChecked(false);
                this.mHostPref.setChecked(true);
                this.mPreferenceCategory.setEnabled(true);
            } else if (!z || i2 == 0) {
                this.mPreferenceCategory.setEnabled(false);
                if (this.mNextRolePref == null) {
                    this.mHostPref.setSummary(ApnSettings.MVNO_NONE);
                    this.mDevicePref.setSummary(ApnSettings.MVNO_NONE);
                }
            }
            SelectorWithWidgetPreference selectorWithWidgetPreference = this.mNextRolePref;
            if (selectorWithWidgetPreference == null || i2 == 0) {
                return;
            }
            if (Integer.parseInt(selectorWithWidgetPreference.getKey()) == i2) {
                this.mNextRolePref.setSummary(ApnSettings.MVNO_NONE);
            } else {
                this.mNextRolePref.setSummary(R.string.usb_switching_failed);
            }
            this.mNextRolePref = null;
            this.mHandler.removeCallbacks(this.mFailureCallback);
        }
    }
}

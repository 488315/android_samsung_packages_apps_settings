package com.android.settings.connecteddevice.usb;

import android.app.ActivityManager;
import android.hardware.usb.UsbManager;
import android.net.TetheringManager;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.provider.Settings;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.picker.widget.SeslNumberPickerSpinnerDelegate$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.connection.ConnectionsUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UsbDetailsFunctionsController extends UsbDetailsController
        implements SelectorWithWidgetPreference.OnClickListener {
    public static final boolean DEBUG = Log.isLoggable("UsbDetailsCtrlFunction", 3);
    public static final Map FUNCTIONS_MAP;
    public Handler mHandler;
    OnStartTetheringCallback mOnStartTetheringCallback;
    long mPreviousFunction;
    public PreferenceCategory mProfilesContainer;
    public TetheringManager mTetheringManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    final class OnStartTetheringCallback implements TetheringManager.StartTetheringCallback {
        public OnStartTetheringCallback() {}

        public final void onTetheringFailed(int i) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(
                    i, "onTetheringFailed() error : ", "UsbDetailsCtrlFunction");
            UsbDetailsFunctionsController usbDetailsFunctionsController =
                    UsbDetailsFunctionsController.this;
            UsbBackend usbBackend = usbDetailsFunctionsController.mUsbBackend;
            usbBackend.mUsbManager.setCurrentFunctions(
                    usbDetailsFunctionsController.mPreviousFunction);
        }
    }

    static {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        FUNCTIONS_MAP = linkedHashMap;
        linkedHashMap.put(4L, Integer.valueOf(R.string.usb_use_file_transfers));
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        if ("VZW".equals(Utils.getSalesCode()) || "VPP".equals(Utils.getSalesCode())) {
            linkedHashMap.put(524288L, Integer.valueOf(R.string.usb_use_system_update));
        } else {
            linkedHashMap.put(32L, Integer.valueOf(R.string.usb_use_tethering));
        }
        linkedHashMap.put(8L, Integer.valueOf(R.string.usb_use_MIDI));
        linkedHashMap.put(16L, Integer.valueOf(R.string.usb_use_photo_transfers));
        linkedHashMap.put(128L, Integer.valueOf(R.string.usb_use_uvc_webcam));
        linkedHashMap.put(262144L, Integer.valueOf(R.string.usb_use_charging_only));
        linkedHashMap.put(0L, Integer.valueOf(R.string.usb_use_debugging_only));
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mProfilesContainer =
                (PreferenceCategory) preferenceScreen.findPreference("usb_details_functions");
        refresh(false, this.mUsbBackend.mUsbManager.getScreenUnlockedFunctions(), 0, 0);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "usb_details_functions";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        StringBuilder sb = Utils.sBuilder;
        return !ActivityManager.isUserAMonkey();
    }

    @Override // com.android.settingslib.widget.SelectorWithWidgetPreference.OnClickListener
    public final void onRadioButtonClicked(
            final SelectorWithWidgetPreference selectorWithWidgetPreference) {
        requireAuthAndExecute(
                new Runnable() { // from class:
                    // com.android.settings.connecteddevice.usb.UsbDetailsFunctionsController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        UsbDetailsFunctionsController usbDetailsFunctionsController =
                                UsbDetailsFunctionsController.this;
                        SelectorWithWidgetPreference selectorWithWidgetPreference2 =
                                selectorWithWidgetPreference;
                        usbDetailsFunctionsController.getClass();
                        long parseLong = Long.parseLong(selectorWithWidgetPreference2.getKey(), 2);
                        UsbBackend usbBackend = usbDetailsFunctionsController.mUsbBackend;
                        long currentFunctions = usbBackend.mUsbManager.getCurrentFunctions();
                        if (UsbDetailsFunctionsController.DEBUG) {
                            StringBuilder m =
                                    SnapshotStateObserver$$ExternalSyntheticOutline0.m(
                                            parseLong,
                                            "onRadioButtonClicked() function : ",
                                            ", toString() : ");
                            m.append(UsbManager.usbFunctionsToString(parseLong));
                            m.append(", previousFunction : ");
                            m.append(currentFunctions);
                            m.append(", toString() : ");
                            m.append(UsbManager.usbFunctionsToString(currentFunctions));
                            Log.d("UsbDetailsCtrlFunction", m.toString());
                        }
                        Log.i(
                                "UsbDetailsCtrlFunction",
                                "onClick() - UsbBackend.usbFunctionsFromString() - click current"
                                    + " mode : "
                                        + parseLong);
                        Log.i(
                                "UsbDetailsCtrlFunction",
                                "onClick() - mUsbBackend.getCurrentFunctions() - previousFunction :"
                                    + " "
                                        + currentFunctions);
                        if (parseLong == currentFunctions || ActivityManager.isUserAMonkey()) {
                            return;
                        }
                        if (((2 & currentFunctions) != 0) && parseLong == 4) {
                            return;
                        }
                        usbDetailsFunctionsController.mPreviousFunction = currentFunctions;
                        int preferenceCount =
                                usbDetailsFunctionsController.mProfilesContainer
                                        .getPreferenceCount();
                        for (int i = 0; i < preferenceCount; i++) {
                            ((SelectorWithWidgetPreference)
                                            usbDetailsFunctionsController.mProfilesContainer
                                                    .getPreference(i))
                                    .setChecked(false);
                        }
                        selectorWithWidgetPreference2.setChecked(true);
                        if (parseLong == 32 || parseLong == 1024) {
                            usbDetailsFunctionsController.mTetheringManager.startTethering(
                                    1,
                                    new HandlerExecutor(usbDetailsFunctionsController.mHandler),
                                    usbDetailsFunctionsController.mOnStartTetheringCallback);
                            return;
                        }
                        if (parseLong == 4) {
                            Settings.System.putInt(
                                    ((UsbDetailsController) usbDetailsFunctionsController)
                                            .mContext.getContentResolver(),
                                    "enable_mtp_settings",
                                    1);
                        }
                        if (parseLong == 262144 && Rune.isChinaModel()) {
                            Settings.Global.putInt(
                                    ((UsbDetailsController) usbDetailsFunctionsController)
                                            .mContext.getContentResolver(),
                                    "adb_enabled",
                                    0);
                        }
                        usbBackend.mUsbManager.setCurrentFunctions(parseLong);
                    }
                });
    }

    @Override // com.android.settings.connecteddevice.usb.UsbDetailsController
    public final void refresh(boolean z, long j, int i, int i2) {
        Log.i(
                "UsbDetailsCtrlFunction",
                "refresh() connected : "
                        + z
                        + ", functions : "
                        + j
                        + ", powerRole : "
                        + i
                        + ", dataRole : "
                        + i2);
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        int i3 = ConnectionsUtils.$r8$clinit;
        boolean m =
                SeslNumberPickerSpinnerDelegate$$ExternalSyntheticOutline0.m(
                        "/sys/class/dual_role_usb");
        boolean m2 =
                SeslNumberPickerSpinnerDelegate$$ExternalSyntheticOutline0.m("/sys/class/typec");
        Utils$$ExternalSyntheticOutline0.m653m("support USBC : ", m, ", ", m2, "ConnectionsUtils");
        boolean z2 = false;
        if (m || m2) {
            Log.d("UsbDetailsCtrlFunction", "support USBC()");
            if (z && i2 == 2) {
                this.mProfilesContainer.setEnabled(true);
            } else {
                this.mProfilesContainer.setEnabled(false);
            }
        } else if (z) {
            this.mProfilesContainer.setEnabled(true);
        } else {
            this.mProfilesContainer.setEnabled(false);
        }
        boolean z3 =
                Utils.getEnterprisePolicyEnabled(
                                ((UsbDetailsController) this).mContext,
                                "content://com.sec.knox.provider/RestrictionPolicy4",
                                "isUsbTetheringEnabled")
                        == 1;
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "isUsbTetheringEnabled = ", "UsbDetailsCtrlFunction", z3);
        for (Long l : ((LinkedHashMap) FUNCTIONS_MAP).keySet()) {
            long longValue = l.longValue();
            int intValue = ((Integer) ((LinkedHashMap) FUNCTIONS_MAP).get(l)).intValue();
            String binaryString = Long.toBinaryString(longValue);
            SelectorWithWidgetPreference selectorWithWidgetPreference =
                    (SelectorWithWidgetPreference)
                            this.mProfilesContainer.findPreference(binaryString);
            if (selectorWithWidgetPreference == null) {
                selectorWithWidgetPreference =
                        new SelectorWithWidgetPreference(this.mProfilesContainer.getContext());
                selectorWithWidgetPreference.setKey(binaryString);
                selectorWithWidgetPreference.setTitle(intValue);
                selectorWithWidgetPreference.setSingleLineTitle(z2);
                selectorWithWidgetPreference.mListener = this;
                this.mProfilesContainer.addPreference(selectorWithWidgetPreference);
            }
            UsbBackend usbBackend = this.mUsbBackend;
            if (usbBackend.areFunctionsSupported(longValue)) {
                if ((j & 2) != 0 ? true : z2) {
                    selectorWithWidgetPreference.setChecked(4 == longValue ? true : z2);
                } else if (j == 1024) {
                    selectorWithWidgetPreference.setChecked(32 == longValue ? true : z2);
                } else {
                    selectorWithWidgetPreference.setChecked(j == longValue ? true : z2);
                }
                if (longValue == 4
                        && Utils.hasPackage(
                                ((UsbDetailsController) this).mContext,
                                "com.google.android.projection.gearhead")) {
                    selectorWithWidgetPreference.setTitle(
                            R.string.usb_use_file_transfers_android_auto);
                }
            } else {
                this.mProfilesContainer.removePreference(selectorWithWidgetPreference);
            }
            if (longValue == 32 && !z3) {
                this.mProfilesContainer.removePreference(selectorWithWidgetPreference);
            } else if (longValue == 0
                    && !usbBackend.supportUSBDebuggingMenu(
                            ((UsbDetailsController) this).mContext)) {
                Log.d("UsbDetailsCtrlFunction", "remove Debugging only");
                this.mProfilesContainer.removePreference(selectorWithWidgetPreference);
            }
            z2 = false;
        }
    }
}

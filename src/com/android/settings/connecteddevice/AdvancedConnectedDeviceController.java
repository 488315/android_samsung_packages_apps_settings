package com.android.settings.connecteddevice;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.nfc.NfcPreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AdvancedConnectedDeviceController extends BasePreferenceController {
    private static final String DRIVING_MODE_SETTINGS_ENABLED =
            "gearhead:driving_mode_settings_enabled";
    private static final String GEARHEAD_PACKAGE = "com.google.android.projection.gearhead";

    public AdvancedConnectedDeviceController(Context context, String str) {
        super(context, str);
    }

    public static int getConnectedDevicesSummaryResourceId(Context context) {
        return getConnectedDevicesSummaryResourceId(
                new NfcPreferenceController(context, NfcPreferenceController.KEY_TOGGLE_NFC),
                isDrivingModeAvailable(context),
                isAndroidAutoSettingAvailable(context));
    }

    public static boolean isAndroidAutoSettingAvailable(Context context) {
        Intent intent = new Intent("com.android.settings.action.IA_SETTINGS");
        intent.setPackage(GEARHEAD_PACKAGE);
        return intent.resolveActivity(context.getPackageManager()) != null;
    }

    public static boolean isDrivingModeAvailable(Context context) {
        return Settings.System.getInt(
                        context.getContentResolver(), DRIVING_MODE_SETTINGS_ENABLED, 0)
                == 1;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        Context context = this.mContext;
        return context.getText(getConnectedDevicesSummaryResourceId(context));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public static int getConnectedDevicesSummaryResourceId(
            NfcPreferenceController nfcPreferenceController, boolean z, boolean z2) {
        return z2
                ? nfcPreferenceController.isAvailable()
                        ? z
                                ? R.string.connected_devices_dashboard_android_auto_summary
                                : R.string
                                        .connected_devices_dashboard_android_auto_no_driving_mode_summary
                        : z
                                ? R.string.connected_devices_dashboard_android_auto_no_nfc_summary
                                : R.string
                                        .connected_devices_dashboard_android_auto_no_nfc_no_driving_mode
                : nfcPreferenceController.isAvailable()
                        ? z
                                ? R.string.connected_devices_dashboard_summary
                                : R.string.connected_devices_dashboard_no_driving_mode_summary
                        : z
                                ? R.string.connected_devices_dashboard_no_nfc_summary
                                : R.string
                                        .connected_devices_dashboard_no_driving_mode_no_nfc_summary;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}

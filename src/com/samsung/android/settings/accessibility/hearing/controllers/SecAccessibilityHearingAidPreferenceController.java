package com.samsung.android.settings.accessibility.hearing.controllers;

import android.bluetooth.BluetoothDevice;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.accessibility.AccessibilityHearingAidPreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;

import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils$$ExternalSyntheticLambda0;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingUtil;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecAccessibilityHearingAidPreferenceController
        extends AccessibilityHearingAidPreferenceController implements A11yStatusLoggingProvider {
    private static final String TAG = "AccessibilityHearingAidPreferenceController";

    public SecAccessibilityHearingAidPreferenceController(Context context, String str) {
        super(context, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handlePreferenceTreeClick$0(
            DialogInterface dialogInterface, int i) {
        launchBluetoothAddDeviceSetting();
    }

    private void launchBluetoothAddDeviceSetting() {
        Intent intent = new Intent("android.bluetooth.devicepicker.action.LAUNCH");
        intent.putExtra("android.bluetooth.devicepicker.extra.NEED_AUTH", true);
        intent.putExtra("android.bluetooth.devicepicker.extra.FILTER_TYPE", 8);
        intent.setFlags(268468224);
        try {
            this.mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "BluetoothSettings not found", e);
        }
    }

    private void launchBluetoothDeviceDetailSetting(BluetoothDevice bluetoothDevice) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("device", bluetoothDevice);
        Intent intent = new Intent("com.samsung.settings.DEVICE_PROFILES_SETTINGS");
        intent.putExtra("device", bundle);
        intent.setFlags(402653184);
        try {
            this.mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "com.samsung.settings.DEVICE_PROFILES_SETTINGS not found", e);
        }
    }

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider
    public Map<String, String> getStatusLoggingData(Context context) {
        if (getAvailabilityStatus() == 3) {
            return Map.of();
        }
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl
                .getA11ySettingsMetricsFeatureProvider()
                .actionBackground(
                        4006,
                        "A11YSE4006",
                        A11yStatusLoggingUtil.getShortcutStatusMap(
                                context,
                                AccessibilityConstant.COMPONENT_NAME_HEARING_AID_SUPPORT_SHORTCUT));
        return Map.of();
    }

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        CachedBluetoothDevice connectedHearingAidDevice =
                this.mHelper.getConnectedHearingAidDevice();
        return connectedHearingAidDevice == null
                ? this.mContext.getString(R.string.bluetooth_hearing_aid_summary)
                : connectedHearingAidDevice.mDevice.semGetAliasName();
    }

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!getPreferenceKey().equals(preference.getKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        CachedBluetoothDevice connectedHearingAidDevice =
                this.mHelper.getConnectedHearingAidDevice();
        if (connectedHearingAidDevice != null) {
            launchBluetoothDeviceDetailSetting(connectedHearingAidDevice.mDevice);
            return true;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setTitle(R.string.accessibility_hearingaid_pair_instructions_first_message);
        builder.setMessage(R.string.accessibility_hearingaid_pair_instructions_second_message);
        builder.setPositiveButton(
                R.string.common_continue,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.accessibility.hearing.controllers.SecAccessibilityHearingAidPreferenceController$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        SecAccessibilityHearingAidPreferenceController.this
                                .lambda$handlePreferenceTreeClick$0(dialogInterface, i);
                    }
                });
        builder.setNegativeButton(
                android.R.string.cancel,
                new SecAccessibilityHearingAidPreferenceController$$ExternalSyntheticLambda1());
        AlertDialog create = builder.create();
        HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
        Rect rect = new Rect();
        preference.seslGetPreferenceBounds(rect);
        create.semSetAnchor((rect.left + rect.right) / 2, rect.bottom);
        View rootView = create.findViewById(android.R.id.content).getRootView();
        if (rootView != null) {
            rootView.addOnLayoutChangeListener(
                    new SecAccessibilityUtils$$ExternalSyntheticLambda0(create, preference));
        }
        create.show();
        return true;
    }

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (preference != null) {
            preference.seslSetSummaryColor(
                    this.mHelper.getConnectedHearingAidDevice() != null
                            ? this.mContext.getColorStateList(R.color.text_color_primary_dark)
                            : this.mContext.getColorStateList(R.color.text_color_secondary));
        }
    }

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController,
              // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onAudioModeChanged() {}

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController,
              // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onAutoOnStateChanged(int i) {}

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController,
              // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onBluetoothStateChanged(int i) {}

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController,
              // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onDeviceAdded(
            CachedBluetoothDevice cachedBluetoothDevice) {}

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController,
              // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onDeviceDeleted(
            CachedBluetoothDevice cachedBluetoothDevice) {}

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController,
              // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onScanningStateChanged(boolean z) {}

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$handlePreferenceTreeClick$1(
            DialogInterface dialogInterface, int i) {}

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController,
              // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onAclConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController,
              // com.android.settingslib.bluetooth.BluetoothCallback
    public void onActiveDeviceChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController,
              // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController,
              // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onDeviceBondStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController,
              // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onProfileConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {}
}

package com.android.settings.bluetooth;

import android.view.View;
import android.widget.Button;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.applications.SpacePreference;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.HearingAidInfo;
import com.android.settingslib.widget.ButtonPreference;

import com.samsung.android.knox.EnterpriseContainerCallback;

import java.util.HashSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothDetailsPairOtherController extends BluetoothDetailsController {
    static final String KEY_SPACE = "hearing_aid_space_layout";
    public ButtonPreference mPreference;
    public SpacePreference mSpacePreference;

    public static boolean getButtonPreferenceVisibility(
            CachedBluetoothDevice cachedBluetoothDevice) {
        if (!cachedBluetoothDevice.isConnectedAshaHearingAidDevice()
                && (!cachedBluetoothDevice.isConnectedHapClientDevice()
                        || !cachedBluetoothDevice.isConnectedLeAudioDevice())) {
            return false;
        }
        HearingAidInfo hearingAidInfo = cachedBluetoothDevice.mHearingAidInfo;
        if ((hearingAidInfo != null ? hearingAidInfo.mMode : -1) != 1) {
            return false;
        }
        CachedBluetoothDevice cachedBluetoothDevice2 = cachedBluetoothDevice.mSubDevice;
        boolean z = cachedBluetoothDevice2 != null && cachedBluetoothDevice2.mBondState == 12;
        HashSet hashSet = (HashSet) cachedBluetoothDevice.mMemberDevices;
        return (z
                        || (!hashSet.isEmpty()
                                && hashSet.stream()
                                        .allMatch(
                                                new BluetoothDetailsPairOtherController$$ExternalSyntheticLambda0())))
                ? false
                : true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "hearing_aid_pair_other_button";
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void init(PreferenceScreen preferenceScreen) {
        this.mPreference =
                (ButtonPreference) preferenceScreen.findPreference("hearing_aid_pair_other_button");
        this.mSpacePreference = (SpacePreference) preferenceScreen.findPreference(KEY_SPACE);
        ButtonPreference buttonPreference = this.mPreference;
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        buttonPreference.setTitle(
                cachedBluetoothDevice.getDeviceSide() == 0
                        ? R.string.bluetooth_pair_right_ear_button
                        : R.string.bluetooth_pair_left_ear_button);
        boolean buttonPreferenceVisibility = getButtonPreferenceVisibility(cachedBluetoothDevice);
        this.mPreference.setVisible(buttonPreferenceVisibility);
        this.mSpacePreference.setVisible(buttonPreferenceVisibility);
        ButtonPreference buttonPreference2 = this.mPreference;
        View.OnClickListener onClickListener =
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.bluetooth.BluetoothDetailsPairOtherController$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        BluetoothDetailsPairOtherController bluetoothDetailsPairOtherController =
                                BluetoothDetailsPairOtherController.this;
                        SubSettingLauncher subSettingLauncher =
                                new SubSettingLauncher(
                                        ((BluetoothDetailsController)
                                                        bluetoothDetailsPairOtherController)
                                                .mContext);
                        String name = BluetoothPairingDetail.class.getName();
                        SubSettingLauncher.LaunchRequest launchRequest =
                                subSettingLauncher.mLaunchRequest;
                        launchRequest.mDestinationName = name;
                        ((BluetoothDeviceDetailsFragment)
                                        bluetoothDetailsPairOtherController.mFragment)
                                .getClass();
                        launchRequest.mSourceMetricsCategory =
                                EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_FAILURE;
                        subSettingLauncher.launch();
                    }
                };
        buttonPreference2.mClickListener = onClickListener;
        Button button = buttonPreference2.mButton;
        if (button != null) {
            button.setOnClickListener(onClickListener);
        }
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return getButtonPreferenceVisibility(this.mCachedDevice);
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void refresh() {
        ButtonPreference buttonPreference = this.mPreference;
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        buttonPreference.setTitle(
                cachedBluetoothDevice.getDeviceSide() == 0
                        ? R.string.bluetooth_pair_right_ear_button
                        : R.string.bluetooth_pair_left_ear_button);
        boolean buttonPreferenceVisibility = getButtonPreferenceVisibility(cachedBluetoothDevice);
        this.mPreference.setVisible(buttonPreferenceVisibility);
        this.mSpacePreference.setVisible(buttonPreferenceVisibility);
    }
}

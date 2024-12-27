package com.android.settings.inputmethod;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcelable;

import androidx.preference.Preference;

import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class KeyboardSettingsPreferenceController extends BasePreferenceController {
    private CachedBluetoothDevice mCachedDevice;

    public KeyboardSettingsPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        List<PhysicalKeyboardFragment.HardKeyboardDeviceInfo> hardKeyboardList =
                getHardKeyboardList();
        if (hardKeyboardList.isEmpty()) {
            return 2;
        }
        for (PhysicalKeyboardFragment.HardKeyboardDeviceInfo hardKeyboardDeviceInfo :
                hardKeyboardList) {
            if (this.mCachedDevice.mDevice.getAddress() != null
                    && hardKeyboardDeviceInfo.mBluetoothAddress != null
                    && this.mCachedDevice
                            .mDevice
                            .getAddress()
                            .equals(hardKeyboardDeviceInfo.mBluetoothAddress)) {
                return 0;
            }
        }
        return 2;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public List<PhysicalKeyboardFragment.HardKeyboardDeviceInfo> getHardKeyboardList() {
        return PhysicalKeyboardFragment.getHardKeyboards(this.mContext);
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!getPreferenceKey().equals(preference.getKey())) {
            return false;
        }
        for (PhysicalKeyboardFragment.HardKeyboardDeviceInfo hardKeyboardDeviceInfo :
                getHardKeyboardList()) {
            if (this.mCachedDevice
                    .mDevice
                    .getAddress()
                    .equals(hardKeyboardDeviceInfo.mBluetoothAddress)) {
                Intent intent = new Intent("android.settings.HARD_KEYBOARD_SETTINGS");
                intent.setPackage(this.mContext.getPackageName());
                intent.putExtra("com.android.settings.inputmethod.EXTRA_ENTRYPOINT", 2);
                intent.putExtra(
                        "input_device_identifier",
                        (Parcelable) hardKeyboardDeviceInfo.mDeviceIdentifier);
                intent.addFlags(1073741824);
                this.mContext.startActivity(intent);
                return true;
            }
        }
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public void init(CachedBluetoothDevice cachedBluetoothDevice) {
        this.mCachedDevice = cachedBluetoothDevice;
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

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}

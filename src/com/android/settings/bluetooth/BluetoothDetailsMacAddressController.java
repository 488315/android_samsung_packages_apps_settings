package com.android.settings.bluetooth;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.widget.FooterPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothDetailsMacAddressController extends BluetoothDetailsController {
    public FooterPreference mFooterPreference;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "device_details_footer";
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void init(PreferenceScreen preferenceScreen) {
        FooterPreference footerPreference =
                (FooterPreference) preferenceScreen.findPreference("device_details_footer");
        this.mFooterPreference = footerPreference;
        footerPreference.setTitle(
                ((BluetoothDetailsController) this)
                        .mContext.getString(
                                R.string.bluetooth_device_mac_address,
                                this.mCachedDevice.getIdentityAddress()));
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void refresh() {
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        if (cachedBluetoothDevice.mGroupId != -1) {
            StringBuilder sb =
                    new StringBuilder(
                            ((BluetoothDetailsController) this)
                                    .mContext.getString(
                                            R.string.bluetooth_multuple_devices_mac_address,
                                            cachedBluetoothDevice.getIdentityAddress()));
            for (CachedBluetoothDevice cachedBluetoothDevice2 :
                    cachedBluetoothDevice.mMemberDevices) {
                sb.append("\n");
                sb.append(cachedBluetoothDevice2.getIdentityAddress());
            }
            this.mFooterPreference.setTitle(sb);
            return;
        }
        if (cachedBluetoothDevice.mSubDevice == null) {
            this.mFooterPreference.setTitle(
                    ((BluetoothDetailsController) this)
                            .mContext.getString(
                                    R.string.bluetooth_device_mac_address,
                                    cachedBluetoothDevice.getIdentityAddress()));
            return;
        }
        StringBuilder sb2 =
                new StringBuilder(
                        ((BluetoothDetailsController) this)
                                .mContext.getString(
                                        R.string.bluetooth_multuple_devices_mac_address,
                                        cachedBluetoothDevice.getIdentityAddress()));
        sb2.append("\n");
        sb2.append(cachedBluetoothDevice.mSubDevice.getIdentityAddress());
        this.mFooterPreference.setTitle(sb2);
    }
}

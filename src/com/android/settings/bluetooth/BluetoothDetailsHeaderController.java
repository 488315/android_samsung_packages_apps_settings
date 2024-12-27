package com.android.settings.bluetooth;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Pair;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.widget.EntityHeaderController;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.widget.LayoutPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothDetailsHeaderController extends BluetoothDetailsController {
    public EntityHeaderController mHeaderController;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_device_header";
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void init(PreferenceScreen preferenceScreen) {
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference("bluetooth_device_header");
        PreferenceFragmentCompat preferenceFragmentCompat = this.mFragment;
        this.mHeaderController =
                new EntityHeaderController(
                        preferenceFragmentCompat.getActivity(),
                        preferenceFragmentCompat,
                        layoutPreference.mRootView.findViewById(R.id.entity_header));
        preferenceScreen.addPreference(layoutPreference);
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        return (BluetoothUtils.isAdvancedDetailsHeader(cachedBluetoothDevice.mDevice)
                        || cachedBluetoothDevice.getConnectableProfiles().stream()
                                .anyMatch(
                                        new BluetoothDetailsHeaderController$$ExternalSyntheticLambda0()))
                ? false
                : true;
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void refresh() {
        if (isAvailable()) {
            Context context = ((BluetoothDetailsController) this).mContext;
            CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
            Pair btRainbowDrawableWithDescription =
                    BluetoothUtils.getBtRainbowDrawableWithDescription(
                            context, cachedBluetoothDevice);
            String connectionSummary = cachedBluetoothDevice.getConnectionSummary();
            if (TextUtils.isEmpty(connectionSummary)) {
                this.mHeaderController.mSecondSummary = null;
            }
            this.mHeaderController.mLabel = cachedBluetoothDevice.getName();
            this.mHeaderController.setIcon((Drawable) btRainbowDrawableWithDescription.first);
            EntityHeaderController entityHeaderController = this.mHeaderController;
            entityHeaderController.mIconContentDescription =
                    (String) btRainbowDrawableWithDescription.second;
            entityHeaderController.mSummary = connectionSummary;
            entityHeaderController.done(true);
        }
    }
}

package com.android.settings.bluetooth;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.widget.ActionButtonsPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothDetailsButtonsController extends BluetoothDetailsController {
    public ActionButtonsPreference mActionButtons;
    public boolean mConnectButtonInitialized;
    public boolean mIsConnected;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "action_buttons";
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void init(PreferenceScreen preferenceScreen) {
        ActionButtonsPreference actionButtonsPreference =
                (ActionButtonsPreference) preferenceScreen.findPreference("action_buttons");
        actionButtonsPreference.setButton1Text(R.string.forget);
        actionButtonsPreference.setButton1Icon(R.drawable.ic_settings_delete);
        actionButtonsPreference.setButton1OnClickListener(
                new BluetoothDetailsButtonsController$$ExternalSyntheticLambda0(this, 2));
        actionButtonsPreference.setButton1Enabled(true);
        this.mActionButtons = actionButtonsPreference;
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void refresh() {
        ActionButtonsPreference actionButtonsPreference = this.mActionButtons;
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        boolean z = !cachedBluetoothDevice.isBusy();
        ActionButtonsPreference.ButtonInfo buttonInfo = actionButtonsPreference.mButton2Info;
        if (z != buttonInfo.mIsEnabled) {
            buttonInfo.mIsEnabled = z;
            actionButtonsPreference.notifyChanged();
        }
        boolean z2 = this.mIsConnected;
        boolean isConnected = cachedBluetoothDevice.isConnected();
        this.mIsConnected = isConnected;
        if (isConnected) {
            if (this.mConnectButtonInitialized && z2) {
                return;
            }
            ActionButtonsPreference actionButtonsPreference2 = this.mActionButtons;
            actionButtonsPreference2.setButton2Text(R.string.bluetooth_device_context_disconnect);
            actionButtonsPreference2.setButton2Icon(R.drawable.ic_settings_close);
            actionButtonsPreference2.setButton2OnClickListener(
                    new BluetoothDetailsButtonsController$$ExternalSyntheticLambda0(this, 0));
            this.mConnectButtonInitialized = true;
            return;
        }
        if (!this.mConnectButtonInitialized || z2) {
            ActionButtonsPreference actionButtonsPreference3 = this.mActionButtons;
            actionButtonsPreference3.setButton2Text(R.string.bluetooth_device_context_connect);
            actionButtonsPreference3.setButton2Icon(R.drawable.ic_add_24dp);
            actionButtonsPreference3.setButton2OnClickListener(
                    new BluetoothDetailsButtonsController$$ExternalSyntheticLambda0(this, 1));
            this.mConnectButtonInitialized = true;
        }
    }
}

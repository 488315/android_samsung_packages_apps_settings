package com.samsung.android.settings.bluetooth.tethering;

import android.content.Context;

import com.android.settings.Utils;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.connection.tether.SecTetherUtils;

import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class BluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BluetoothTetheringSwitchEnabler f$0;

    public /* synthetic */ BluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda0(
            BluetoothTetheringSwitchEnabler bluetoothTetheringSwitchEnabler, int i) {
        this.$r8$classId = i;
        this.f$0 = bluetoothTetheringSwitchEnabler;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        BluetoothTetheringSwitchEnabler bluetoothTetheringSwitchEnabler = this.f$0;
        switch (i) {
            case 0:
                Context context = bluetoothTetheringSwitchEnabler.mContext;
                AtomicReference atomicReference = BluetoothTetheringUtils.mDataSaverBackend;
                if (!(context != null ? SecTetherUtils.isProvisioningNeeded(context) : false)) {
                    bluetoothTetheringSwitchEnabler.setTethering(true);
                    break;
                } else {
                    BluetoothTetheringUtils.startProvisioning(
                            bluetoothTetheringSwitchEnabler.mActivity,
                            bluetoothTetheringSwitchEnabler.mContext);
                    break;
                }
            case 1:
                bluetoothTetheringSwitchEnabler.setSwitchChecked(false);
                break;
            case 2:
                if (!bluetoothTetheringSwitchEnabler.mIsEnabledForTethering.compareAndSet(
                        true, false)) {
                    bluetoothTetheringSwitchEnabler.setScanMode();
                    bluetoothTetheringSwitchEnabler.updateSwitch$1();
                    break;
                } else {
                    bluetoothTetheringSwitchEnabler.setTethering(true);
                    break;
                }
            case 3:
                bluetoothTetheringSwitchEnabler.getClass();
                AtomicReference atomicReference2 = BluetoothTetheringUtils.mDataSaverBackend;
                String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                if ("VZW".equals(Utils.getSalesCode())) {
                    bluetoothTetheringSwitchEnabler.setTethering(false);
                    break;
                }
                break;
            case 4:
                bluetoothTetheringSwitchEnabler.mIsTetheringRequested.set(false);
                bluetoothTetheringSwitchEnabler.showState(bluetoothTetheringSwitchEnabler.mContext);
                bluetoothTetheringSwitchEnabler.setScanMode();
                bluetoothTetheringSwitchEnabler.updateSwitch$1();
                break;
            case 5:
                bluetoothTetheringSwitchEnabler.mIsTetheringRequested.set(false);
                bluetoothTetheringSwitchEnabler.showState(bluetoothTetheringSwitchEnabler.mContext);
                bluetoothTetheringSwitchEnabler.setScanMode();
                bluetoothTetheringSwitchEnabler.updateSwitch$1();
                break;
            case 6:
                bluetoothTetheringSwitchEnabler.setTethering(false);
                break;
            default:
                bluetoothTetheringSwitchEnabler.updateSwitch$1();
                break;
        }
    }
}

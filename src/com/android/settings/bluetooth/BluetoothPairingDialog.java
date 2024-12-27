package com.android.settings.bluetooth;

import android.app.StatusBarManager;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.os.UserHandle;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.accessibility.PreferredShortcuts$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.logging.SALogging;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BluetoothPairingDialog extends AppCompatActivity {
    public BluetoothPairingController mBluetoothPairingController;
    public PowerManager.WakeLock mPartialWakeLock;
    public PowerManager.WakeLock mScreenWakeLock;
    public PowerManager.WakeLock mWakeLock;
    public boolean mReceiverRegistered = false;
    public boolean mEmergencyCallbackMode = false;
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.settings.bluetooth.BluetoothPairingDialog.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    BluetoothPairingController bluetoothPairingController;
                    String action = intent.getAction();
                    if (action != null) {
                        Log.d("BTPairingDialog", "onReceive :: action = ".concat(action));
                    }
                    if ("android.bluetooth.device.action.BOND_STATE_CHANGED".equals(action)) {
                        int intExtra =
                                intent.getIntExtra(
                                        "android.bluetooth.device.extra.BOND_STATE",
                                        Integer.MIN_VALUE);
                        if (intExtra == 12 || intExtra == 10) {
                            BluetoothPairingDialog.this.dismiss();
                            return;
                        }
                        return;
                    }
                    if ("android.bluetooth.device.action.PAIRING_CANCEL".equals(action)) {
                        BluetoothDevice bluetoothDevice =
                                (BluetoothDevice)
                                        intent.getParcelableExtra(
                                                "android.bluetooth.device.extra.DEVICE");
                        if (bluetoothDevice == null
                                || ((bluetoothPairingController =
                                                        BluetoothPairingDialog.this
                                                                .mBluetoothPairingController)
                                                != null
                                        && bluetoothDevice.equals(
                                                bluetoothPairingController.mDevice))) {
                            BluetoothPairingController bluetoothPairingController2 =
                                    BluetoothPairingDialog.this.mBluetoothPairingController;
                            if (bluetoothPairingController2 != null
                                    && !bluetoothPairingController2.mIsBondedInitiatedLocally
                                    && !bluetoothPairingController2.mIsSettingsForeground) {
                                bluetoothPairingController2.updateBlockingDevice(false);
                            }
                            BluetoothPairingDialog.this.dismiss();
                            return;
                        }
                        return;
                    }
                    if ("android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED".equals(action)) {
                        BluetoothPairingDialog.this.mEmergencyCallbackMode =
                                intent.getBooleanExtra(
                                        "android.telephony.extra.PHONE_IN_ECM_STATE", false);
                        BluetoothPairingDialog bluetoothPairingDialog = BluetoothPairingDialog.this;
                        if (bluetoothPairingDialog.mEmergencyCallbackMode) {
                            bluetoothPairingDialog.dismiss();
                            return;
                        }
                        return;
                    }
                    if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(action)) {
                        if (intent.getIntExtra(
                                        "android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE)
                                == 10) {
                            BluetoothPairingController bluetoothPairingController3 =
                                    BluetoothPairingDialog.this.mBluetoothPairingController;
                            if (bluetoothPairingController3 != null) {
                                bluetoothPairingController3.onCancel();
                            }
                            BluetoothPairingDialog.this.dismiss();
                            return;
                        }
                        return;
                    }
                    if ("com.samsung.bluetooth.device.action.BOND_STATE_CHANGED_FROM_NEARBY_DEVICE"
                            .equals(action)) {
                        int intExtra2 =
                                intent.getIntExtra(
                                        "android.bluetooth.device.extra.BOND_STATE",
                                        Integer.MIN_VALUE);
                        if (intExtra2 == 12 || intExtra2 == 10) {
                            BluetoothPairingDialog.this.dismiss();
                        }
                    }
                }
            };
    public final Handler mHandler =
            new Handler(
                    new Handler
                            .Callback() { // from class:
                                          // com.android.settings.bluetooth.BluetoothPairingDialog.2
                        @Override // android.os.Handler.Callback
                        public final boolean handleMessage(Message message) {
                            if (message.what == 1 && !BluetoothPairingDialog.this.isFinishing()) {
                                Log.d("BTPairingDialog", "setTimeout :: pairing time is exceeded.");
                                BluetoothPairingController bluetoothPairingController =
                                        BluetoothPairingDialog.this.mBluetoothPairingController;
                                if (bluetoothPairingController != null
                                        && !bluetoothPairingController.mIsBondedInitiatedLocally
                                        && !bluetoothPairingController.mIsSettingsForeground) {
                                    bluetoothPairingController.updateBlockingDevice(false);
                                }
                                BluetoothPairingDialog.this.dismiss();
                            }
                            return true;
                        }
                    });

    public void dismiss() {
        if (isFinishing()) {
            return;
        }
        BluetoothPairingDialogFragment bluetoothPairingDialogFragment =
                (BluetoothPairingDialogFragment)
                        getSupportFragmentManager().findFragmentByTag("bluetooth.pairing.fragment");
        if (bluetoothPairingDialogFragment != null) {
            bluetoothPairingDialogFragment.hideSoftInput();
            bluetoothPairingDialogFragment.dismissAllowingStateLoss();
        }
        finish();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        Intent registerReceiver;
        super.onCreate(bundle);
        getWindow().addSystemFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        setFinishOnTouchOutside(true);
        requestWindowFeature(1);
        Intent intent = getIntent();
        BluetoothDevice bluetoothDevice =
                (BluetoothDevice)
                        intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
        if (bluetoothDevice == null || bluetoothDevice.getBondState() == 10) {
            dismiss();
            return;
        }
        try {
            this.mBluetoothPairingController = new BluetoothPairingController(this, intent);
        } catch (IllegalStateException unused) {
            Log.e(
                    "BTPairingDialog",
                    "intent has not BluetoothDevice info. IllegalStateException occur");
        }
        BluetoothPairingController bluetoothPairingController = this.mBluetoothPairingController;
        if (bluetoothPairingController == null) {
            dismiss();
            return;
        }
        int i = bluetoothPairingController.mType;
        if ((i == 1 || i == 2) && bluetoothPairingController.mPasskey == Integer.MIN_VALUE) {
            bluetoothPairingController.onCancel();
            dismiss();
            return;
        }
        boolean z = false;
        if (!bluetoothPairingController.mIsBondedInitiatedLocally
                && !bluetoothPairingController.mIsSettingsForeground) {
            Preference$$ExternalSyntheticOutline0.m(
                    new StringBuilder(
                            "Bonding from remote and setting is not foreground, blockState = "),
                    this.mBluetoothPairingController.mBlockState,
                    "BTPairingDialog");
            BluetoothPairingController bluetoothPairingController2 =
                    this.mBluetoothPairingController;
            String string =
                    bluetoothPairingController2
                            .mContext
                            .getSharedPreferences("bluetooth_blocking_device", 0)
                            .getString("blocking_device_list", ApnSettings.MVNO_NONE);
            if (!string.equals(ApnSettings.MVNO_NONE)) {
                int currentTimeMillis = (int) (System.currentTimeMillis() / 3600000);
                String replace =
                        bluetoothPairingController2
                                .mDevice
                                .getAddress()
                                .replace(":", ApnSettings.MVNO_NONE);
                if (BluetoothPairingController.DBG) {
                    MainClearConfirm$$ExternalSyntheticOutline0.m(
                            PreferredShortcuts$$ExternalSyntheticOutline0.m(
                                    currentTimeMillis,
                                    "Blocking device loaded: ",
                                    string,
                                    " curTime:",
                                    " remote:"),
                            replace,
                            "BTPairingController");
                }
                String[] split = string.split(";");
                bluetoothPairingController2.mBlockingDevices = split;
                int length = split.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    String[] split2 = split[i2].split(",");
                    if (split2.length == 5 && replace.equals(split2[0])) {
                        try {
                            int parseInt = Integer.parseInt(split2[3]);
                            int parseInt2 = Integer.parseInt(split2[4]);
                            if (currentTimeMillis - parseInt < 12 || parseInt2 == 2) {
                                bluetoothPairingController2.mBlockState = parseInt2;
                            }
                        } catch (NumberFormatException unused2) {
                            continue;
                        }
                    }
                    i2++;
                }
            }
            BluetoothPairingController bluetoothPairingController3 =
                    this.mBluetoothPairingController;
            if (bluetoothPairingController3.mBlockState == 2) {
                bluetoothPairingController3.onCancel();
                dismiss();
                return;
            }
        }
        PowerManager powerManager = (PowerManager) getSystemService("power");
        this.mWakeLock = powerManager.newWakeLock(805306394, "BTPairingDialog");
        this.mPartialWakeLock = powerManager.newWakeLock(1, "BTPairingDialog");
        this.mScreenWakeLock = powerManager.newWakeLock(10, "BTPairingDialog");
        this.mWakeLock.acquire();
        if (this.mWakeLock.isHeld()) {
            this.mPartialWakeLock.acquire();
            this.mScreenWakeLock.acquire();
        }
        String salesCode = com.android.settings.Utils.getSalesCode();
        boolean equals = "VZW".equals(salesCode);
        AnonymousClass1 anonymousClass1 = this.mReceiver;
        if ((equals || "VPP".equals(salesCode))
                && (registerReceiver =
                                registerReceiver(
                                        anonymousClass1,
                                        new IntentFilter(
                                                "android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED")))
                        != null) {
            boolean booleanExtra =
                    registerReceiver.getBooleanExtra(
                            "android.telephony.extra.PHONE_IN_ECM_STATE", false);
            this.mEmergencyCallbackMode = booleanExtra;
            if (booleanExtra) {
                this.mBluetoothPairingController.onCancel();
                dismiss();
                return;
            }
        }
        if (((TelephonyManager) getSystemService("phone")).getCallState() == 0) {
            Utils.makeNotiSound(this);
        }
        BluetoothPairingDialogFragment bluetoothPairingDialogFragment =
                (BluetoothPairingDialogFragment)
                        getSupportFragmentManager().findFragmentByTag("bluetooth.pairing.fragment");
        if (bluetoothPairingDialogFragment != null
                && (bluetoothPairingDialogFragment.mPairingController != null
                        || bluetoothPairingDialogFragment.mPairingDialogActivity != null)) {
            bluetoothPairingDialogFragment.dismissInternal(false, false);
            bluetoothPairingDialogFragment = null;
        }
        if (bluetoothPairingDialogFragment == null) {
            bluetoothPairingDialogFragment = new BluetoothPairingDialogFragment();
        } else {
            z = true;
        }
        BluetoothPairingController bluetoothPairingController4 = this.mBluetoothPairingController;
        if (bluetoothPairingDialogFragment.mPairingController != null) {
            throw new IllegalStateException(
                    "The controller can only be set once. Forcibly replacing it will lead to"
                        + " undefined behavior");
        }
        bluetoothPairingDialogFragment.mPairingController = bluetoothPairingController4;
        if (bluetoothPairingDialogFragment.mPairingDialogActivity != null) {
            throw new IllegalStateException("The pairing dialog activity can only be set once");
        }
        bluetoothPairingDialogFragment.mPairingDialogActivity = this;
        if (!z) {
            bluetoothPairingDialogFragment.show(
                    getSupportFragmentManager(), "bluetooth.pairing.fragment");
        }
        Message message = new Message();
        message.what = 1;
        this.mHandler.sendMessageDelayed(message, 60000L);
        registerReceiver(
                anonymousClass1,
                new IntentFilter("android.bluetooth.device.action.PAIRING_CANCEL"));
        registerReceiver(
                anonymousClass1,
                new IntentFilter("android.bluetooth.device.action.BOND_STATE_CHANGED"));
        registerReceiver(
                anonymousClass1,
                new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        registerReceiver(
                anonymousClass1,
                new IntentFilter(
                        "com.samsung.bluetooth.device.action.BOND_STATE_CHANGED_FROM_NEARBY_DEVICE"));
        this.mReceiverRegistered = true;
        closeSystemDialogs();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onDestroy() {
        BluetoothPairingController bluetoothPairingController = this.mBluetoothPairingController;
        if (bluetoothPairingController != null) {
            if (bluetoothPairingController.mIsAcceptPair) {
                Utils.insertMDMLog(
                        getApplicationContext(),
                        true,
                        Process.myPid(),
                        getClass().getSimpleName(),
                        ComposerKt$$ExternalSyntheticOutline0.m(
                                "User Interaction: User action pairing bluetooth device ",
                                this.mBluetoothPairingController.mDevice.getAddress(),
                                " succeeded"),
                        UserHandle.getCallingUserId());
            } else {
                Utils.insertMDMLog(
                        getApplicationContext(),
                        false,
                        Process.myPid(),
                        getClass().getSimpleName(),
                        ComposerKt$$ExternalSyntheticOutline0.m(
                                "User Interaction: User action pairing bluetooth device ",
                                this.mBluetoothPairingController.mDevice.getAddress(),
                                " failed. Reason: User canceled"),
                        UserHandle.getCallingUserId());
            }
        }
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null && wakeLock.isHeld()) {
            this.mWakeLock.release();
        }
        PowerManager.WakeLock wakeLock2 = this.mPartialWakeLock;
        if (wakeLock2 != null && wakeLock2.isHeld()) {
            this.mPartialWakeLock.release();
        }
        PowerManager.WakeLock wakeLock3 = this.mScreenWakeLock;
        if (wakeLock3 != null && wakeLock3.isHeld()) {
            this.mScreenWakeLock.release();
        }
        super.onDestroy();
        AnonymousClass1 anonymousClass1 = this.mReceiver;
        if (anonymousClass1 != null && this.mReceiverRegistered) {
            this.mReceiverRegistered = false;
            unregisterReceiver(anonymousClass1);
        }
        this.mHandler.removeMessages(1);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        StatusBarManager statusBarManager = (StatusBarManager) getSystemService("statusbar");
        if (statusBarManager != null) {
            statusBarManager.collapsePanels();
        }
        SALogging.insertSALog(getString(R.string.screen_request_dialog));
    }
}

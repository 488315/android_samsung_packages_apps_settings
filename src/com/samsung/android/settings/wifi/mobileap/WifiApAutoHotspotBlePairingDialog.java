package com.samsung.android.settings.wifi.mobileap;

import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.os.UserHandle;
import android.util.Log;
import android.view.KeyEvent;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.internal.app.AlertActivity;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.datetime.timezone.TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0;

import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApAutoHotspotBlePairingDialog extends AlertActivity
        implements DialogInterface.OnKeyListener {
    public static final IntentFilter BLE_INTENT_FILTER =
            new IntentFilter("android.bluetooth.device.action.BOND_STATE_CHANGED");
    public BluetoothDevice device;
    public AlertDialog mBTPairingCloseDialog;
    public boolean mBleBroacastRegistered;
    public Context mContext;
    public CountDownTimer mCountdownTimer;
    public PowerManager.WakeLock mPartialWakeLock;
    public PowerManager.WakeLock mScreenWakeLock;
    public PowerManager.WakeLock mWakeLock;
    public boolean onBackKeyPressed = false;
    public final AnonymousClass1 mBLEReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.wifi.mobileap.WifiApAutoHotspotBlePairingDialog.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    AlertDialog alertDialog;
                    String action = intent.getAction();
                    int semGetMyUserId = UserHandle.semGetMyUserId();
                    IntentFilter intentFilter = WifiApAutoHotspotBlePairingDialog.BLE_INTENT_FILTER;
                    Log.i(
                            "WifiApAutoHotspotBlePairingDialog",
                            "mBLEReceiver onReceive: action "
                                    + action
                                    + " userID : "
                                    + semGetMyUserId);
                    if (action == null || semGetMyUserId != 0) {
                        MainClearConfirm$$ExternalSyntheticOutline0.m(
                                semGetMyUserId,
                                "mBLEReceiver do nothing, action is null or Knox userID: ",
                                "WifiApAutoHotspotBlePairingDialog");
                        return;
                    }
                    if ("android.bluetooth.device.action.BOND_STATE_CHANGED".equals(action)) {
                        int intExtra =
                                intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", 10);
                        int intExtra2 =
                                intent.getIntExtra(
                                        "android.bluetooth.device.extra.PREVIOUS_BOND_STATE", 10);
                        StringBuilder sb =
                                new StringBuilder("mBLEReceiver ACTION_BOND_STATE_CHANGED");
                        sb.append(WifiApAutoHotspotBlePairingDialog.this.mBTPairingCloseDialog);
                        sb.append(",bondState:");
                        sb.append(intExtra);
                        sb.append(",previousBondState:");
                        Preference$$ExternalSyntheticOutline0.m(
                                sb, intExtra2, "WifiApAutoHotspotBlePairingDialog");
                        if (intExtra == 10
                                && intExtra2 == 11
                                && (alertDialog =
                                                WifiApAutoHotspotBlePairingDialog.this
                                                        .mBTPairingCloseDialog)
                                        != null
                                && alertDialog.isShowing()) {
                            WifiApAutoHotspotBlePairingDialog.this.mBTPairingCloseDialog.dismiss();
                            WifiApAutoHotspotBlePairingDialog wifiApAutoHotspotBlePairingDialog =
                                    WifiApAutoHotspotBlePairingDialog.this;
                            wifiApAutoHotspotBlePairingDialog.mBTPairingCloseDialog = null;
                            CountDownTimer countDownTimer =
                                    wifiApAutoHotspotBlePairingDialog.mCountdownTimer;
                            if (countDownTimer != null) {
                                countDownTimer.cancel();
                                WifiApAutoHotspotBlePairingDialog.this.mCountdownTimer = null;
                            }
                            WifiApAutoHotspotBlePairingDialog.this.dismiss();
                        }
                    }
                }
            };

    /* JADX WARN: Multi-variable type inference failed */
    public final void onCreate(Bundle bundle) {
        final int i = 1;
        final int i2 = 0;
        super.onCreate(bundle);
        Log.i("WifiApAutoHotspotBlePairingDialog", "onCreate()");
        this.mContext = getApplicationContext();
        Intent intent = getIntent();
        Context context = this.mContext;
        boolean z = WifiApSettingsUtils.DBG;
        int i3 = context.getResources().getConfiguration().uiMode;
        setTheme(R.style.WifiApTheme);
        getWindow().setLayout(0, 0);
        AnonymousClass1 anonymousClass1 = this.mBLEReceiver;
        if (anonymousClass1 != null && !this.mBleBroacastRegistered) {
            this.mContext.registerReceiver(anonymousClass1, BLE_INTENT_FILTER, 2);
            this.mBleBroacastRegistered = true;
        }
        Log.i("WifiApAutoHotspotBlePairingDialog", "DIALOG_SMART_TETHERING_PAIR");
        this.device =
                (BluetoothDevice)
                        intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
        intent.getIntExtra("android.bluetooth.device.extra.PAIRING_VARIANT", Integer.MIN_VALUE);
        String format =
                String.format(
                        Locale.US,
                        "%06d",
                        Integer.valueOf(
                                intent.getIntExtra(
                                        "android.bluetooth.device.extra.PAIRING_KEY",
                                        Integer.MIN_VALUE)));
        Log.d("WifiApAutoHotspotBlePairingDialog", "mPasskeyFormat:".concat(format));
        PowerManager powerManager = (PowerManager) getSystemService("power");
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService("keyguard");
        this.mWakeLock = powerManager.newWakeLock(805306394, "WifiApAutoHotspotBlePairingDialog");
        this.mPartialWakeLock = powerManager.newWakeLock(1, "WifiApAutoHotspotBlePairingDialog");
        this.mScreenWakeLock = powerManager.newWakeLock(10, "WifiApAutoHotspotBlePairingDialog");
        this.mWakeLock.acquire();
        if (this.mWakeLock.isHeld()) {
            this.mPartialWakeLock.acquire();
            this.mScreenWakeLock.acquire();
        }
        Log.i(
                "WifiApAutoHotspotBlePairingDialog",
                "keypad locked:" + keyguardManager.isKeyguardLocked());
        getWindow().addFlags(6815872);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 0);
        AlertDialog.Builder title =
                builder.setTitle(R.string.smart_tethering_ble_connection_dialog_title);
        StringBuilder sb = new StringBuilder();
        TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0.m(
                this.mContext, R.string.smart_tethering_ble_connection_dialog_summary, sb, "\n\n");
        sb.append(this.mContext.getString(R.string.smart_tethering_ble_connection_dialog_passkey));
        sb.append(" ");
        sb.append(format);
        title.setMessage(sb.toString())
                .setNegativeButton(
                        R.string.cancel,
                        new DialogInterface.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.wifi.mobileap.WifiApAutoHotspotBlePairingDialog.4
                            public final /* synthetic */ WifiApAutoHotspotBlePairingDialog this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i4) {
                                switch (i) {
                                    case 0:
                                        IntentFilter intentFilter =
                                                WifiApAutoHotspotBlePairingDialog.BLE_INTENT_FILTER;
                                        Log.d("WifiApAutoHotspotBlePairingDialog", "onOK");
                                        BluetoothDevice bluetoothDevice = this.this$0.device;
                                        if (bluetoothDevice != null) {
                                            bluetoothDevice.setPairingConfirmation(true);
                                        }
                                        dialogInterface.dismiss();
                                        CountDownTimer countDownTimer = this.this$0.mCountdownTimer;
                                        if (countDownTimer != null) {
                                            countDownTimer.cancel();
                                            this.this$0.mCountdownTimer = null;
                                        }
                                        this.this$0.dismiss();
                                        break;
                                    default:
                                        IntentFilter intentFilter2 =
                                                WifiApAutoHotspotBlePairingDialog.BLE_INTENT_FILTER;
                                        Log.d("WifiApAutoHotspotBlePairingDialog", "onCancel");
                                        BluetoothDevice bluetoothDevice2 = this.this$0.device;
                                        if (bluetoothDevice2 != null) {
                                            bluetoothDevice2.cancelBondProcess();
                                        }
                                        dialogInterface.dismiss();
                                        CountDownTimer countDownTimer2 =
                                                this.this$0.mCountdownTimer;
                                        if (countDownTimer2 != null) {
                                            countDownTimer2.cancel();
                                            this.this$0.mCountdownTimer = null;
                                        }
                                        this.this$0.dismiss();
                                        break;
                                }
                            }
                        })
                .setPositiveButton(
                        R.string.okay,
                        new DialogInterface.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.wifi.mobileap.WifiApAutoHotspotBlePairingDialog.4
                            public final /* synthetic */ WifiApAutoHotspotBlePairingDialog this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i4) {
                                switch (i2) {
                                    case 0:
                                        IntentFilter intentFilter =
                                                WifiApAutoHotspotBlePairingDialog.BLE_INTENT_FILTER;
                                        Log.d("WifiApAutoHotspotBlePairingDialog", "onOK");
                                        BluetoothDevice bluetoothDevice = this.this$0.device;
                                        if (bluetoothDevice != null) {
                                            bluetoothDevice.setPairingConfirmation(true);
                                        }
                                        dialogInterface.dismiss();
                                        CountDownTimer countDownTimer = this.this$0.mCountdownTimer;
                                        if (countDownTimer != null) {
                                            countDownTimer.cancel();
                                            this.this$0.mCountdownTimer = null;
                                        }
                                        this.this$0.dismiss();
                                        break;
                                    default:
                                        IntentFilter intentFilter2 =
                                                WifiApAutoHotspotBlePairingDialog.BLE_INTENT_FILTER;
                                        Log.d("WifiApAutoHotspotBlePairingDialog", "onCancel");
                                        BluetoothDevice bluetoothDevice2 = this.this$0.device;
                                        if (bluetoothDevice2 != null) {
                                            bluetoothDevice2.cancelBondProcess();
                                        }
                                        dialogInterface.dismiss();
                                        CountDownTimer countDownTimer2 =
                                                this.this$0.mCountdownTimer;
                                        if (countDownTimer2 != null) {
                                            countDownTimer2.cancel();
                                            this.this$0.mCountdownTimer = null;
                                        }
                                        this.this$0.dismiss();
                                        break;
                                }
                            }
                        })
                .setOnCancelListener(
                        new DialogInterface
                                .OnCancelListener() { // from class:
                                                      // com.samsung.android.settings.wifi.mobileap.WifiApAutoHotspotBlePairingDialog.3
                            @Override // android.content.DialogInterface.OnCancelListener
                            public final void onCancel(DialogInterface dialogInterface) {
                                IntentFilter intentFilter =
                                        WifiApAutoHotspotBlePairingDialog.BLE_INTENT_FILTER;
                                Log.d("WifiApAutoHotspotBlePairingDialog", "onDialog Cancelled");
                                BluetoothDevice bluetoothDevice =
                                        WifiApAutoHotspotBlePairingDialog.this.device;
                                if (bluetoothDevice != null) {
                                    bluetoothDevice.cancelBondProcess();
                                }
                                CountDownTimer countDownTimer =
                                        WifiApAutoHotspotBlePairingDialog.this.mCountdownTimer;
                                if (countDownTimer != null) {
                                    countDownTimer.cancel();
                                    WifiApAutoHotspotBlePairingDialog.this.mCountdownTimer = null;
                                }
                                WifiApAutoHotspotBlePairingDialog.this.dismiss();
                            }
                        })
                .setOnDismissListener(
                        new DialogInterface
                                .OnDismissListener() { // from class:
                                                       // com.samsung.android.settings.wifi.mobileap.WifiApAutoHotspotBlePairingDialog.2
                            @Override // android.content.DialogInterface.OnDismissListener
                            public final void onDismiss(DialogInterface dialogInterface) {
                                IntentFilter intentFilter =
                                        WifiApAutoHotspotBlePairingDialog.BLE_INTENT_FILTER;
                                Log.d("WifiApAutoHotspotBlePairingDialog", "onDismiss");
                                if (WifiApAutoHotspotBlePairingDialog.this.device != null) {
                                    StringBuilder sb2 = new StringBuilder("device.getBondState():");
                                    sb2.append(
                                            WifiApAutoHotspotBlePairingDialog.this.device
                                                    .getBondState());
                                    sb2.append(",onBackKeyPressed:");
                                    ActionBarContextView$$ExternalSyntheticOutline0.m(
                                            sb2,
                                            WifiApAutoHotspotBlePairingDialog.this.onBackKeyPressed,
                                            "WifiApAutoHotspotBlePairingDialog");
                                    WifiApAutoHotspotBlePairingDialog
                                            wifiApAutoHotspotBlePairingDialog =
                                                    WifiApAutoHotspotBlePairingDialog.this;
                                    if (wifiApAutoHotspotBlePairingDialog.onBackKeyPressed
                                            && wifiApAutoHotspotBlePairingDialog.device
                                                            .getBondState()
                                                    == 11) {
                                        WifiApAutoHotspotBlePairingDialog.this.device
                                                .cancelBondProcess();
                                    }
                                }
                                WifiApAutoHotspotBlePairingDialog.this
                                        .getWindow()
                                        .clearFlags(6815872);
                                PowerManager.WakeLock wakeLock =
                                        WifiApAutoHotspotBlePairingDialog.this.mWakeLock;
                                if (wakeLock != null && wakeLock.isHeld()) {
                                    WifiApAutoHotspotBlePairingDialog.this.mWakeLock.release();
                                }
                                PowerManager.WakeLock wakeLock2 =
                                        WifiApAutoHotspotBlePairingDialog.this.mPartialWakeLock;
                                if (wakeLock2 != null && wakeLock2.isHeld()) {
                                    WifiApAutoHotspotBlePairingDialog.this.mPartialWakeLock
                                            .release();
                                }
                                PowerManager.WakeLock wakeLock3 =
                                        WifiApAutoHotspotBlePairingDialog.this.mScreenWakeLock;
                                if (wakeLock3 != null && wakeLock3.isHeld()) {
                                    WifiApAutoHotspotBlePairingDialog.this.mScreenWakeLock
                                            .release();
                                }
                                CountDownTimer countDownTimer =
                                        WifiApAutoHotspotBlePairingDialog.this.mCountdownTimer;
                                if (countDownTimer != null) {
                                    countDownTimer.cancel();
                                    WifiApAutoHotspotBlePairingDialog.this.mCountdownTimer = null;
                                }
                                WifiApAutoHotspotBlePairingDialog.this.dismiss();
                            }
                        });
        this.mCountdownTimer =
                new CountDownTimer() { // from class:
                                       // com.samsung.android.settings.wifi.mobileap.WifiApAutoHotspotBlePairingDialog.6
                    @Override // android.os.CountDownTimer
                    public final void onFinish() {
                        IntentFilter intentFilter =
                                WifiApAutoHotspotBlePairingDialog.BLE_INTENT_FILTER;
                        Log.d("WifiApAutoHotspotBlePairingDialog", "onFinish");
                        BluetoothDevice bluetoothDevice =
                                WifiApAutoHotspotBlePairingDialog.this.device;
                        if (bluetoothDevice != null && bluetoothDevice.getBondState() == 11) {
                            WifiApAutoHotspotBlePairingDialog.this.device.cancelBondProcess();
                        }
                        WifiApAutoHotspotBlePairingDialog.this.dismiss();
                    }

                    @Override // android.os.CountDownTimer
                    public final void onTick(long j) {}
                }.start();
        AlertDialog create = builder.create();
        this.mBTPairingCloseDialog = create;
        create.setCanceledOnTouchOutside(true);
        this.mBTPairingCloseDialog.setOnKeyListener(this);
        this.mBTPairingCloseDialog.show();
    }

    public final void onDestroy() {
        super.onDestroy();
        Log.i("WifiApAutoHotspotBlePairingDialog", "onDestroy");
        AnonymousClass1 anonymousClass1 = this.mBLEReceiver;
        if (anonymousClass1 != null && this.mBleBroacastRegistered) {
            this.mContext.unregisterReceiver(anonymousClass1);
            this.mBleBroacastRegistered = false;
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
        if (wakeLock3 == null || !wakeLock3.isHeld()) {
            return;
        }
        this.mScreenWakeLock.release();
    }

    @Override // android.content.DialogInterface.OnKeyListener
    public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        StringBuilder m =
                ListPopupWindow$$ExternalSyntheticOutline0.m(
                        i, "onKey :: keyCode = ", ", event = ");
        m.append(keyEvent.describeContents());
        Log.d("WifiApAutoHotspotBlePairingDialog", m.toString());
        if (i != 4) {
            return false;
        }
        this.onBackKeyPressed = true;
        AlertDialog alertDialog = this.mBTPairingCloseDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return false;
        }
        this.mBTPairingCloseDialog.dismiss();
        this.mBTPairingCloseDialog = null;
        CountDownTimer countDownTimer = this.mCountdownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.mCountdownTimer = null;
        }
        dismiss();
        return false;
    }

    public final void onStop() {
        AlertDialog alertDialog;
        super.onStop();
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService("keyguard");
        Log.i(
                "WifiApAutoHotspotBlePairingDialog",
                "onStop(), is keypad locked:" + keyguardManager.isKeyguardLocked());
        if (keyguardManager.isKeyguardLocked()
                || (alertDialog = this.mBTPairingCloseDialog) == null
                || !alertDialog.isShowing()) {
            return;
        }
        BluetoothDevice bluetoothDevice = this.device;
        if (bluetoothDevice != null && bluetoothDevice.getBondState() == 11) {
            Log.i("WifiApAutoHotspotBlePairingDialog", "cancelPairing()");
            this.device.cancelBondProcess();
        }
        this.mBTPairingCloseDialog.dismiss();
        this.mBTPairingCloseDialog = null;
        dismiss();
    }
}

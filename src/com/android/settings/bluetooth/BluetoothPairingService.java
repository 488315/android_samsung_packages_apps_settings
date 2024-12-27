package com.android.settings.bluetooth;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.core.app.NotificationCompat$Action;
import androidx.core.app.NotificationCompat$Builder;
import androidx.core.app.RemoteInput;

import com.android.settings.R;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothPairingService extends Service {
    static final String ACTION_DISMISS_PAIRING =
            "com.android.settings.bluetooth.ACTION_DISMISS_PAIRING";
    static final String ACTION_PAIRING_DIALOG =
            "com.android.settings.bluetooth.ACTION_PAIRING_DIALOG";
    static final int NOTIFICATION_ID = 17301632;
    public BluetoothDevice mDevice;
    NotificationManager mNm;
    public boolean mRegistered = false;
    public final AnonymousClass1 mCancelReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.settings.bluetooth.BluetoothPairingService.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (action.equals("android.bluetooth.device.action.BOND_STATE_CHANGED")) {
                        int intExtra =
                                intent.getIntExtra(
                                        "android.bluetooth.device.extra.BOND_STATE",
                                        Integer.MIN_VALUE);
                        StringBuilder m =
                                ListPopupWindow$$ExternalSyntheticOutline0.m(
                                        intExtra,
                                        "onReceive() Bond state change : ",
                                        ", device name : ");
                        m.append(BluetoothPairingService.this.mDevice.getName());
                        Log.d("BluetoothPairingService", m.toString());
                        if (intExtra != 10 && intExtra != 12) {
                            return;
                        }
                    } else if (action.equals(BluetoothPairingService.ACTION_DISMISS_PAIRING)) {
                        Log.d(
                                "BluetoothPairingService",
                                "Notification cancel  ("
                                        + BluetoothPairingService.this.mDevice.getName()
                                        + ")");
                        BluetoothPairingService.this.mDevice.cancelBondProcess();
                    } else {
                        Log.d(
                                "BluetoothPairingService",
                                "Dismiss pairing for  ("
                                        + BluetoothPairingService.this.mDevice.getName()
                                        + ")");
                    }
                    BluetoothPairingService.this.mNm.cancel(17301632);
                    BluetoothPairingService.this.stopSelf();
                }
            };

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public final void onCreate() {
        this.mNm = (NotificationManager) getSystemService(NotificationManager.class);
        this.mNm.createNotificationChannel(
                new NotificationChannel(
                        "bluetooth_notification_channel", getString(R.string.bluetooth), 4));
    }

    @Override // android.app.Service
    public final void onDestroy() {
        if (this.mRegistered) {
            unregisterReceiver(this.mCancelReceiver);
            this.mRegistered = false;
        }
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i, int i2) {
        if (intent == null) {
            Log.e("BluetoothPairingService", "Can't start: null intent!");
            stopSelf();
            return 2;
        }
        String action = intent.getAction();
        Log.d("BluetoothPairingService", "onStartCommand() action : " + action);
        BluetoothDevice bluetoothDevice =
                (BluetoothDevice)
                        intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
        this.mDevice = bluetoothDevice;
        if (bluetoothDevice == null) {
            Log.w("BluetoothPairingService", "mDevice is null");
            stopSelf();
            return 2;
        }
        if (bluetoothDevice.getBondState() != 11) {
            Log.w(
                    "BluetoothPairingService",
                    "Device "
                            + this.mDevice.getName()
                            + " not bonding: "
                            + this.mDevice.getBondState());
            this.mNm.cancel(17301632);
            stopSelf();
            return 2;
        }
        if (TextUtils.equals(action, "android.bluetooth.device.action.PAIRING_REQUEST")) {
            Resources resources = getResources();
            NotificationCompat$Builder notificationCompat$Builder =
                    new NotificationCompat$Builder(this, "bluetooth_notification_channel");
            notificationCompat$Builder.mNotification.icon = 17301632;
            notificationCompat$Builder.mNotification.tickerText =
                    NotificationCompat$Builder.limitCharSequenceLength(
                            resources.getString(R.string.bluetooth_notif_ticker));
            notificationCompat$Builder.mLocalOnly = true;
            int intExtra =
                    intent.getIntExtra(
                            "android.bluetooth.device.extra.PAIRING_VARIANT", Integer.MIN_VALUE);
            Intent intent2 = new Intent(ACTION_PAIRING_DIALOG);
            intent2.setClass(this, BluetoothPairingService.class);
            intent2.putExtra("android.bluetooth.device.extra.DEVICE", this.mDevice);
            intent2.putExtra("android.bluetooth.device.extra.PAIRING_VARIANT", intExtra);
            if (intExtra == 2 || intExtra == 4 || intExtra == 5) {
                intent2.putExtra(
                        "android.bluetooth.device.extra.PAIRING_KEY",
                        intent.getIntExtra(
                                "android.bluetooth.device.extra.PAIRING_KEY", Integer.MIN_VALUE));
            }
            PendingIntent service = PendingIntent.getService(this, 0, intent2, 1275068416);
            Intent intent3 = new Intent(ACTION_DISMISS_PAIRING);
            intent3.setClass(this, BluetoothPairingService.class);
            intent3.putExtra("android.bluetooth.device.extra.DEVICE", this.mDevice);
            PendingIntent service2 = PendingIntent.getService(this, 0, intent3, 1140850688);
            String stringExtra = intent.getStringExtra("android.bluetooth.device.extra.NAME");
            if (TextUtils.isEmpty(stringExtra)) {
                BluetoothDevice bluetoothDevice2 =
                        (BluetoothDevice)
                                intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                stringExtra =
                        bluetoothDevice2 != null
                                ? bluetoothDevice2.getAlias()
                                : resources.getString(android.R.string.unknownName);
            }
            Log.d(
                    "BluetoothPairingService",
                    "Show pairing notification for  (" + stringExtra + ")");
            String string = resources.getString(R.string.bluetooth_device_context_pair_connect);
            Bundle bundle = new Bundle();
            CharSequence limitCharSequenceLength =
                    NotificationCompat$Builder.limitCharSequenceLength(string);
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            NotificationCompat$Action notificationCompat$Action =
                    new NotificationCompat$Action(
                            null,
                            limitCharSequenceLength,
                            service,
                            bundle,
                            arrayList2.isEmpty()
                                    ? null
                                    : (RemoteInput[])
                                            arrayList2.toArray(new RemoteInput[arrayList2.size()]),
                            arrayList.isEmpty()
                                    ? null
                                    : (RemoteInput[])
                                            arrayList.toArray(new RemoteInput[arrayList.size()]),
                            true,
                            0,
                            true,
                            false,
                            false);
            String string2 = resources.getString(android.R.string.cancel);
            Bundle bundle2 = new Bundle();
            CharSequence limitCharSequenceLength2 =
                    NotificationCompat$Builder.limitCharSequenceLength(string2);
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            NotificationCompat$Action notificationCompat$Action2 =
                    new NotificationCompat$Action(
                            null,
                            limitCharSequenceLength2,
                            service2,
                            bundle2,
                            arrayList4.isEmpty()
                                    ? null
                                    : (RemoteInput[])
                                            arrayList4.toArray(new RemoteInput[arrayList4.size()]),
                            arrayList3.isEmpty()
                                    ? null
                                    : (RemoteInput[])
                                            arrayList3.toArray(new RemoteInput[arrayList3.size()]),
                            true,
                            0,
                            true,
                            false,
                            false);
            notificationCompat$Builder.mContentTitle =
                    NotificationCompat$Builder.limitCharSequenceLength(
                            resources.getString(R.string.bluetooth_notif_title));
            notificationCompat$Builder.mContentText =
                    NotificationCompat$Builder.limitCharSequenceLength(
                            resources.getString(R.string.bluetooth_notif_message, stringExtra));
            notificationCompat$Builder.mContentIntent = service;
            notificationCompat$Builder.mNotification.defaults = 1;
            notificationCompat$Builder.setFlag(2);
            notificationCompat$Builder.mColor =
                    getColor(android.R.color.system_notification_accent_color);
            notificationCompat$Builder.mActions.add(notificationCompat$Action);
            notificationCompat$Builder.mActions.add(notificationCompat$Action2);
            this.mNm.notify(17301632, notificationCompat$Builder.build());
        } else if (TextUtils.equals(action, ACTION_DISMISS_PAIRING)) {
            Log.d(
                    "BluetoothPairingService",
                    "Notification cancel  (" + this.mDevice.getName() + ")");
            this.mDevice.cancelBondProcess();
            this.mNm.cancel(17301632);
            stopSelf();
        } else if (TextUtils.equals(action, ACTION_PAIRING_DIALOG)) {
            BluetoothDevice bluetoothDevice3 =
                    (BluetoothDevice)
                            intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            int intExtra2 =
                    intent.getIntExtra(
                            "android.bluetooth.device.extra.PAIRING_VARIANT", Integer.MIN_VALUE);
            Intent intent4 = new Intent();
            intent4.setClass(this, BluetoothPairingDialog.class);
            intent4.putExtra("android.bluetooth.device.extra.DEVICE", bluetoothDevice3);
            intent4.putExtra("android.bluetooth.device.extra.PAIRING_VARIANT", intExtra2);
            if (intExtra2 == 2 || intExtra2 == 4 || intExtra2 == 5) {
                intent4.putExtra(
                        "android.bluetooth.device.extra.PAIRING_KEY",
                        intent.getIntExtra(
                                "android.bluetooth.device.extra.PAIRING_KEY", Integer.MIN_VALUE));
                intent4.putExtra("android.bluetooth.device.extra.PAIRING_INITIATOR", 2);
            }
            intent4.setAction("android.bluetooth.device.action.PAIRING_REQUEST");
            intent4.setFlags(268435456);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.bluetooth.device.action.BOND_STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.device.action.PAIRING_CANCEL");
            intentFilter.addAction(ACTION_DISMISS_PAIRING);
            registerReceiver(this.mCancelReceiver, intentFilter);
            this.mRegistered = true;
            startActivity(intent4);
        }
        return 1;
    }
}

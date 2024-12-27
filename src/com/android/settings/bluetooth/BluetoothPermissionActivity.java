package com.android.settings.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.IWindowManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.settings.R;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.logging.SALogging;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BluetoothPermissionActivity extends AlertActivity
        implements DialogInterface.OnClickListener, Preference.OnPreferenceChangeListener {
    public BluetoothDevice mDevice;
    public String mEventId;
    public TextView mMessageView;
    public String mScreenId;
    public View mView;
    public PowerManager.WakeLock mWakeLock;
    public int mRequestType = 0;
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.settings.bluetooth.BluetoothPermissionActivity.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (action == null) {
                        Log.d(
                                "BluetoothPermissionActivity",
                                "onReceive :: Intent.getAction() is return null");
                        return;
                    }
                    Log.d(
                            "BluetoothPermissionActivity",
                            "onReceive :: getAction = ".concat(action));
                    if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(action)) {
                        if (intent.getIntExtra(
                                        "android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE)
                                == 13) {
                            BluetoothPermissionActivity.this.finish();
                        }
                    } else if ("android.bluetooth.device.action.CONNECTION_ACCESS_CANCEL"
                                    .equals(action)
                            && intent.getIntExtra(
                                            "android.bluetooth.device.extra.ACCESS_REQUEST_TYPE", 2)
                                    == BluetoothPermissionActivity.this.mRequestType) {
                        if (BluetoothPermissionActivity.this.mDevice.equals(
                                (BluetoothDevice)
                                        intent.getParcelableExtra(
                                                "android.bluetooth.device.extra.DEVICE"))) {
                            BluetoothPermissionActivity.this.dismiss();
                        }
                    }
                }
            };
    public boolean mReceiverRegistered = false;

    public final void onBackPressed() {
        Log.i("BluetoothPermissionActivity", "Back button pressed! onCancel");
        onCancel$1();
        super.onBackPressed();
    }

    public final void onCancel$1() {
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("onCancel : request Type : "),
                this.mRequestType,
                "BluetoothPermissionActivity");
        sendReplyIntentToReceiver(false, false);
        finish();
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        String string;
        if (i == -2) {
            Preference$$ExternalSyntheticOutline0.m(
                    new StringBuilder("onNegative : request Type : "),
                    this.mRequestType,
                    "BluetoothPermissionActivity");
            sendReplyIntentToReceiver(false, true);
            finish();
            string = getString(R.string.detail_deny);
        } else if (i != -1) {
            string = null;
        } else {
            Preference$$ExternalSyntheticOutline0.m(
                    new StringBuilder("onPositive :: request Type : "),
                    this.mRequestType,
                    "BluetoothPermissionActivity");
            sendReplyIntentToReceiver(true, true);
            finish();
            string = getString(R.string.detail_allow);
        }
        SALogging.insertSALog(this.mScreenId, this.mEventId, string);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addPrivateFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        setFinishOnTouchOutside(true);
        PowerManager.WakeLock newWakeLock =
                ((PowerManager) getSystemService("power"))
                        .newWakeLock(805306394, "BluetoothPermissionActivity");
        this.mWakeLock = newWakeLock;
        newWakeLock.acquire();
        Intent intent = getIntent();
        if (!"android.bluetooth.device.action.CONNECTION_ACCESS_REQUEST"
                .equals(intent.getAction())) {
            Log.e(
                    "BluetoothPermissionActivity",
                    "Error: this activity may be started only with intent"
                        + " ACTION_CONNECTION_ACCESS_REQUEST");
            finish();
            return;
        }
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(this, Utils.mOnInitCallback);
        if (localBluetoothManager == null) {
            Log.e(
                    "BluetoothPermissionActivity",
                    "onCreate :: BluetoothAdapter not supported by system");
            finish();
            return;
        }
        LocalBluetoothAdapter localBluetoothAdapter = localBluetoothManager.mLocalAdapter;
        if (localBluetoothAdapter.mAdapter.isDiscovering()) {
            Log.e(
                    "BluetoothPermissionActivity",
                    "onCreate :: stop scanning before launch pairingdialog");
            localBluetoothAdapter.cancelDiscovery();
        }
        this.mDevice =
                (BluetoothDevice)
                        intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
        this.mRequestType =
                intent.getIntExtra("android.bluetooth.device.extra.ACCESS_REQUEST_TYPE", 2);
        TooltipPopup$$ExternalSyntheticOutline0.m(
                new StringBuilder("onCreate() Request type: "),
                this.mRequestType,
                "BluetoothPermissionActivity");
        int i = this.mRequestType;
        if (i == 1) {
            showDialog(
                    this.mRequestType, getString(R.string.bluetooth_connection_permission_request));
        } else if (i == 2) {
            showDialog(this.mRequestType, getString(R.string.bluetooth_phonebook_request));
        } else if (i == 3) {
            showDialog(this.mRequestType, getString(R.string.bluetooth_map_request));
        } else {
            if (i != 4) {
                Log.e(
                        "BluetoothPermissionActivity",
                        "Error: bad request type: " + this.mRequestType);
                finish();
                return;
            }
            showDialog(this.mRequestType, getString(R.string.bluetooth_sap_request));
        }
        getWindow().setGravity(80);
        try {
            IWindowManager.Stub.asInterface(ServiceManager.getService("window"))
                    .requestSystemKeyEvent(3, getComponentName(), true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        IntentFilter intentFilter =
                new IntentFilter("android.bluetooth.device.action.CONNECTION_ACCESS_CANCEL");
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        registerReceiver(this.mReceiver, intentFilter);
        this.mReceiverRegistered = true;
        this.mScreenId = getString(R.string.screen_request_dialog);
        if (this.mWakeLock.isHeld()) {
            this.mWakeLock.release();
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        if (this.mReceiverRegistered) {
            unregisterReceiver(this.mReceiver);
            this.mReceiverRegistered = false;
        }
        if (this.mWakeLock.isHeld()) {
            this.mWakeLock.release();
        }
        if (SystemProperties.get("net.mirrorlink.on").equals("1")) {
            Log.i(
                    "BluetoothPermissionActivity",
                    "dismissMirrorLinkBlackScreen isDismissBlackScreen= false");
            Intent intent = new Intent();
            intent.setAction("com.samsung.android.mirrorlink.action.SHOW_BLACK_SCREEN_NOIMAGE");
            intent.setPackage("com.samsung.android.app.mirrorlink");
            sendBroadcast(intent);
        }
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 3) {
            try {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.HOME");
                intent.addFlags(329252864);
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
            onCancel$1();
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        return true;
    }

    public final void onResume() {
        super.onResume();
        SALogging.insertSALog(this.mScreenId);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            Window window = getWindow();
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            int scaledWindowTouchSlop = ViewConfiguration.get(this).getScaledWindowTouchSlop();
            View decorView = window.getDecorView();
            int i = -scaledWindowTouchSlop;
            if ((x < i
                            || y < i
                            || x > decorView.getWidth() + scaledWindowTouchSlop
                            || y > decorView.getHeight() + scaledWindowTouchSlop)
                    && getWindow().peekDecorView() != null) {
                onCancel$1();
                return true;
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @VisibleForTesting
    public void sendReplyIntentToReceiver(boolean z, boolean z2) {
        try {
            String findBluetoothPackageName = Utils.findBluetoothPackageName(this);
            Intent intent = new Intent("android.bluetooth.device.action.CONNECTION_ACCESS_REPLY");
            Log.i(
                    "BluetoothPermissionActivity",
                    "sendReplyIntentToReceiver() Request type: "
                            + this.mRequestType
                            + " mReturnPackage");
            intent.setPackage(findBluetoothPackageName);
            intent.putExtra("android.bluetooth.device.extra.CONNECTION_ACCESS_RESULT", z ? 1 : 2);
            intent.putExtra("android.bluetooth.device.extra.ALWAYS_ALLOWED", z2);
            intent.putExtra("android.bluetooth.device.extra.DEVICE", this.mDevice);
            intent.putExtra(
                    "android.bluetooth.device.extra.ACCESS_REQUEST_TYPE", this.mRequestType);
            intent.addFlags(268435456);
            sendBroadcast(intent, "android.permission.BLUETOOTH_CONNECT");
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("BluetoothPermissionActivity", "Failed to find bluetooth package name", e);
        }
    }

    public final void showDialog(int i, String str) {
        String string;
        Log.i(
                "BluetoothPermissionActivity",
                "showDialog :: Request type: " + this.mRequestType + " this: " + this);
        BluetoothDevice bluetoothDevice = this.mDevice;
        String semGetAliasName = bluetoothDevice != null ? bluetoothDevice.semGetAliasName() : null;
        if (semGetAliasName == null) {
            semGetAliasName = getString(R.string.unknown);
        }
        if (i == 1) {
            string =
                    getString(
                            R.string.bluetooth_connection_dialog_text,
                            new Object[] {semGetAliasName});
        } else if (i == 2) {
            string =
                    getString(
                            R.string.sec_bluetooth_pb_acceptance_dialog_text,
                            new Object[] {semGetAliasName, semGetAliasName});
            this.mEventId = getString(R.string.event_request_dialog_pbap);
        } else if (i == 3) {
            string =
                    getString(
                            R.string.sec_bluetooth_map_acceptance_dialog_text,
                            new Object[] {semGetAliasName, semGetAliasName});
            this.mEventId = getString(R.string.event_request_dialog_map);
        } else if (i != 4) {
            string = ApnSettings.MVNO_NONE;
        } else {
            string =
                    getString(
                            R.string.sec_bluetooth_sap_acceptance_dialog_text,
                            new Object[] {
                                semGetAliasName,
                                semGetAliasName,
                                ((TelephonyManager) getSystemService(TelephonyManager.class))
                                        .getLine1Number()
                            });
            this.mEventId = getString(R.string.event_request_dialog_sap);
        }
        View inflate = getLayoutInflater().inflate(R.layout.bluetooth_access, (ViewGroup) null);
        this.mView = inflate;
        TextView textView = (TextView) inflate.findViewById(R.id.message);
        this.mMessageView = textView;
        boolean z = Utils.DEBUG;
        String language = Locale.getDefault().getLanguage();
        textView.setText(
                AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                        ("ar".equals(language)
                                        || "fa".equals(language)
                                        || "he".equals(language)
                                        || "ur".equals(language)
                                        || "yi".equals(language)
                                        || "iw".equals(language)
                                        || "ji".equals(language))
                                ? "\u200f"
                                : "\u200e",
                        string));
        if (com.android.settings.Utils.isTablet()
                && SystemProperties.get("ro.build.scafe.size").equals("tall")) {
            this.mMessageView.setTextSize(
                    getResources()
                            .getInteger(R.integer.bluetooth_message_view_text_size_tablet_tall));
        }
        AlertController.AlertParams alertParams = ((AlertActivity) this).mAlertParams;
        alertParams.mTitle = str;
        Log.i(
                "BluetoothPermissionActivity",
                "showDialog() Request type: " + this.mRequestType + " this: " + this);
        alertParams.mView = this.mView;
        alertParams.mPositiveButtonText = getString(R.string.allow);
        alertParams.mPositiveButtonListener = this;
        alertParams.mNegativeButtonText =
                getString(R.string.bluetooth_permission_dialog_button_deny);
        alertParams.mNegativeButtonListener = this;
        setupAlert();
    }
}

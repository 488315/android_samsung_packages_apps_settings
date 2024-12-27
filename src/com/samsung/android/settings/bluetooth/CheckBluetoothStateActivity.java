package com.samsung.android.settings.bluetooth;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDump;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class CheckBluetoothStateActivity extends Activity {
    public static boolean mDialogExist = false;
    public static boolean mExpectingBluetoothOn = false;
    public static boolean mIsDestroyed = false;
    public static boolean mIsRegisterKeyGuardReceiver = false;
    public final AnonymousClass1 mBluetoothReceiver;
    public String mCallingAppPackageName;
    public final AnonymousClass4 mKeyGuardHandler;
    public final AnonymousClass1 mKeyGuardReceiver;
    public final AnonymousClass1 mKeyGuardUnlockReceiver;
    public LocalBluetoothAdapter mLocalAdapter;
    public final AnonymousClass4 mTimeoutHandler;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.CheckBluetoothStateActivity$5, reason: invalid class name */
    public final class AnonymousClass5 implements Runnable {
        @Override // java.lang.Runnable
        public final void run() {
            try {
                Thread.sleep(250L);
            } catch (InterruptedException e) {
                Log.d("CheckBluetoothStateActivity", "InterruptedException" + e);
                Thread.currentThread().interrupt();
            }
            Log.e("CheckBluetoothStateActivity", "onCreate mDialogExist to false");
            CheckBluetoothStateActivity.mDialogExist = false;
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.samsung.android.settings.bluetooth.CheckBluetoothStateActivity$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.bluetooth.CheckBluetoothStateActivity$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.settings.bluetooth.CheckBluetoothStateActivity$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.samsung.android.settings.bluetooth.CheckBluetoothStateActivity$4] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.samsung.android.settings.bluetooth.CheckBluetoothStateActivity$4] */
    public CheckBluetoothStateActivity() {
        final int i = 0;
        this.mBluetoothReceiver =
                new BroadcastReceiver(this) { // from class:
                    // com.samsung.android.settings.bluetooth.CheckBluetoothStateActivity.1
                    public final /* synthetic */ CheckBluetoothStateActivity this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        switch (i) {
                            case 0:
                                String action = intent.getAction();
                                Log.e(
                                        "CheckBluetoothStateActivity",
                                        "onReceive :: action = " + action);
                                if (!"android.bluetooth.adapter.action.STATE_CHANGED"
                                        .equals(action)) {
                                    if ("com.android.launcher.action.EASY_MODE_CHANGE"
                                            .equals(action)) {
                                        Log.e("CheckBluetoothStateActivity", "EASY_MODE_CHANGE");
                                        this.this$0.finish();
                                        break;
                                    }
                                } else {
                                    int intExtra =
                                            intent.getIntExtra(
                                                    "android.bluetooth.adapter.extra.STATE",
                                                    Integer.MIN_VALUE);
                                    StringBuilder m =
                                            ListPopupWindow$$ExternalSyntheticOutline0.m(
                                                    intExtra,
                                                    "onReceive :: ACTION_STATE_CHANGED change to ="
                                                        + " ",
                                                    ", mExpectingBluetoothOn = ");
                                    m.append(CheckBluetoothStateActivity.mExpectingBluetoothOn);
                                    m.append(", mIsDestroyed = ");
                                    ActionBarContextView$$ExternalSyntheticOutline0.m(
                                            m,
                                            CheckBluetoothStateActivity.mIsDestroyed,
                                            "CheckBluetoothStateActivity");
                                    if (intExtra != 12) {
                                        if (intExtra == 10
                                                && CheckBluetoothStateActivity.mExpectingBluetoothOn
                                                && !CheckBluetoothStateActivity.mIsDestroyed) {
                                            this.this$0.finish();
                                            break;
                                        }
                                    } else {
                                        this.this$0.launchDevicePicker();
                                        break;
                                    }
                                }
                                break;
                            case 1:
                                String action2 = intent.getAction();
                                Log.e(
                                        "CheckBluetoothStateActivity",
                                        "onReceive :: action = " + action2);
                                if ("com.samsung.intent.action.BLUETOOTH_KEYGUARD_UNLOCK"
                                        .equals(action2)) {
                                    CheckBluetoothStateActivity checkBluetoothStateActivity =
                                            this.this$0;
                                    boolean z =
                                            CheckBluetoothStateActivity.mIsRegisterKeyGuardReceiver;
                                    checkBluetoothStateActivity.checkPickerProcess();
                                    break;
                                }
                                break;
                            default:
                                String action3 = intent.getAction();
                                Log.e(
                                        "CheckBluetoothStateActivity",
                                        "mKeyGuardReceiver onReceive :: action = " + action3);
                                if ("com.samsung.keyguard.KEYGUARD_STATE_UPDATE".equals(action3)) {
                                    boolean booleanExtra = intent.getBooleanExtra("showing", false);
                                    boolean booleanExtra2 =
                                            intent.getBooleanExtra("bouncerShowing", false);
                                    boolean booleanExtra3 =
                                            intent.getBooleanExtra("occluded", false);
                                    StringBuilder m2 =
                                            Utils$$ExternalSyntheticOutline0.m(
                                                    "keyguard onReceive :: showing = ",
                                                    booleanExtra,
                                                    ", bouncerShowing = ",
                                                    booleanExtra2,
                                                    ", occluded = ");
                                    m2.append(booleanExtra3);
                                    Log.e("CheckBluetoothStateActivity", m2.toString());
                                    if (!booleanExtra2 && booleanExtra) {
                                        CheckBluetoothStateActivity checkBluetoothStateActivity2 =
                                                this.this$0;
                                        AnonymousClass1 anonymousClass1 =
                                                checkBluetoothStateActivity2.mKeyGuardReceiver;
                                        if (anonymousClass1 != null
                                                && CheckBluetoothStateActivity
                                                        .mIsRegisterKeyGuardReceiver) {
                                            CheckBluetoothStateActivity
                                                            .mIsRegisterKeyGuardReceiver =
                                                    false;
                                            checkBluetoothStateActivity2.unregisterReceiver(
                                                    anonymousClass1);
                                        }
                                        this.this$0.finish();
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mKeyGuardUnlockReceiver =
                new BroadcastReceiver(this) { // from class:
                    // com.samsung.android.settings.bluetooth.CheckBluetoothStateActivity.1
                    public final /* synthetic */ CheckBluetoothStateActivity this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        switch (i2) {
                            case 0:
                                String action = intent.getAction();
                                Log.e(
                                        "CheckBluetoothStateActivity",
                                        "onReceive :: action = " + action);
                                if (!"android.bluetooth.adapter.action.STATE_CHANGED"
                                        .equals(action)) {
                                    if ("com.android.launcher.action.EASY_MODE_CHANGE"
                                            .equals(action)) {
                                        Log.e("CheckBluetoothStateActivity", "EASY_MODE_CHANGE");
                                        this.this$0.finish();
                                        break;
                                    }
                                } else {
                                    int intExtra =
                                            intent.getIntExtra(
                                                    "android.bluetooth.adapter.extra.STATE",
                                                    Integer.MIN_VALUE);
                                    StringBuilder m =
                                            ListPopupWindow$$ExternalSyntheticOutline0.m(
                                                    intExtra,
                                                    "onReceive :: ACTION_STATE_CHANGED change to ="
                                                        + " ",
                                                    ", mExpectingBluetoothOn = ");
                                    m.append(CheckBluetoothStateActivity.mExpectingBluetoothOn);
                                    m.append(", mIsDestroyed = ");
                                    ActionBarContextView$$ExternalSyntheticOutline0.m(
                                            m,
                                            CheckBluetoothStateActivity.mIsDestroyed,
                                            "CheckBluetoothStateActivity");
                                    if (intExtra != 12) {
                                        if (intExtra == 10
                                                && CheckBluetoothStateActivity.mExpectingBluetoothOn
                                                && !CheckBluetoothStateActivity.mIsDestroyed) {
                                            this.this$0.finish();
                                            break;
                                        }
                                    } else {
                                        this.this$0.launchDevicePicker();
                                        break;
                                    }
                                }
                                break;
                            case 1:
                                String action2 = intent.getAction();
                                Log.e(
                                        "CheckBluetoothStateActivity",
                                        "onReceive :: action = " + action2);
                                if ("com.samsung.intent.action.BLUETOOTH_KEYGUARD_UNLOCK"
                                        .equals(action2)) {
                                    CheckBluetoothStateActivity checkBluetoothStateActivity =
                                            this.this$0;
                                    boolean z =
                                            CheckBluetoothStateActivity.mIsRegisterKeyGuardReceiver;
                                    checkBluetoothStateActivity.checkPickerProcess();
                                    break;
                                }
                                break;
                            default:
                                String action3 = intent.getAction();
                                Log.e(
                                        "CheckBluetoothStateActivity",
                                        "mKeyGuardReceiver onReceive :: action = " + action3);
                                if ("com.samsung.keyguard.KEYGUARD_STATE_UPDATE".equals(action3)) {
                                    boolean booleanExtra = intent.getBooleanExtra("showing", false);
                                    boolean booleanExtra2 =
                                            intent.getBooleanExtra("bouncerShowing", false);
                                    boolean booleanExtra3 =
                                            intent.getBooleanExtra("occluded", false);
                                    StringBuilder m2 =
                                            Utils$$ExternalSyntheticOutline0.m(
                                                    "keyguard onReceive :: showing = ",
                                                    booleanExtra,
                                                    ", bouncerShowing = ",
                                                    booleanExtra2,
                                                    ", occluded = ");
                                    m2.append(booleanExtra3);
                                    Log.e("CheckBluetoothStateActivity", m2.toString());
                                    if (!booleanExtra2 && booleanExtra) {
                                        CheckBluetoothStateActivity checkBluetoothStateActivity2 =
                                                this.this$0;
                                        AnonymousClass1 anonymousClass1 =
                                                checkBluetoothStateActivity2.mKeyGuardReceiver;
                                        if (anonymousClass1 != null
                                                && CheckBluetoothStateActivity
                                                        .mIsRegisterKeyGuardReceiver) {
                                            CheckBluetoothStateActivity
                                                            .mIsRegisterKeyGuardReceiver =
                                                    false;
                                            checkBluetoothStateActivity2.unregisterReceiver(
                                                    anonymousClass1);
                                        }
                                        this.this$0.finish();
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i3 = 2;
        this.mKeyGuardReceiver =
                new BroadcastReceiver(this) { // from class:
                    // com.samsung.android.settings.bluetooth.CheckBluetoothStateActivity.1
                    public final /* synthetic */ CheckBluetoothStateActivity this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        switch (i3) {
                            case 0:
                                String action = intent.getAction();
                                Log.e(
                                        "CheckBluetoothStateActivity",
                                        "onReceive :: action = " + action);
                                if (!"android.bluetooth.adapter.action.STATE_CHANGED"
                                        .equals(action)) {
                                    if ("com.android.launcher.action.EASY_MODE_CHANGE"
                                            .equals(action)) {
                                        Log.e("CheckBluetoothStateActivity", "EASY_MODE_CHANGE");
                                        this.this$0.finish();
                                        break;
                                    }
                                } else {
                                    int intExtra =
                                            intent.getIntExtra(
                                                    "android.bluetooth.adapter.extra.STATE",
                                                    Integer.MIN_VALUE);
                                    StringBuilder m =
                                            ListPopupWindow$$ExternalSyntheticOutline0.m(
                                                    intExtra,
                                                    "onReceive :: ACTION_STATE_CHANGED change to ="
                                                        + " ",
                                                    ", mExpectingBluetoothOn = ");
                                    m.append(CheckBluetoothStateActivity.mExpectingBluetoothOn);
                                    m.append(", mIsDestroyed = ");
                                    ActionBarContextView$$ExternalSyntheticOutline0.m(
                                            m,
                                            CheckBluetoothStateActivity.mIsDestroyed,
                                            "CheckBluetoothStateActivity");
                                    if (intExtra != 12) {
                                        if (intExtra == 10
                                                && CheckBluetoothStateActivity.mExpectingBluetoothOn
                                                && !CheckBluetoothStateActivity.mIsDestroyed) {
                                            this.this$0.finish();
                                            break;
                                        }
                                    } else {
                                        this.this$0.launchDevicePicker();
                                        break;
                                    }
                                }
                                break;
                            case 1:
                                String action2 = intent.getAction();
                                Log.e(
                                        "CheckBluetoothStateActivity",
                                        "onReceive :: action = " + action2);
                                if ("com.samsung.intent.action.BLUETOOTH_KEYGUARD_UNLOCK"
                                        .equals(action2)) {
                                    CheckBluetoothStateActivity checkBluetoothStateActivity =
                                            this.this$0;
                                    boolean z =
                                            CheckBluetoothStateActivity.mIsRegisterKeyGuardReceiver;
                                    checkBluetoothStateActivity.checkPickerProcess();
                                    break;
                                }
                                break;
                            default:
                                String action3 = intent.getAction();
                                Log.e(
                                        "CheckBluetoothStateActivity",
                                        "mKeyGuardReceiver onReceive :: action = " + action3);
                                if ("com.samsung.keyguard.KEYGUARD_STATE_UPDATE".equals(action3)) {
                                    boolean booleanExtra = intent.getBooleanExtra("showing", false);
                                    boolean booleanExtra2 =
                                            intent.getBooleanExtra("bouncerShowing", false);
                                    boolean booleanExtra3 =
                                            intent.getBooleanExtra("occluded", false);
                                    StringBuilder m2 =
                                            Utils$$ExternalSyntheticOutline0.m(
                                                    "keyguard onReceive :: showing = ",
                                                    booleanExtra,
                                                    ", bouncerShowing = ",
                                                    booleanExtra2,
                                                    ", occluded = ");
                                    m2.append(booleanExtra3);
                                    Log.e("CheckBluetoothStateActivity", m2.toString());
                                    if (!booleanExtra2 && booleanExtra) {
                                        CheckBluetoothStateActivity checkBluetoothStateActivity2 =
                                                this.this$0;
                                        AnonymousClass1 anonymousClass1 =
                                                checkBluetoothStateActivity2.mKeyGuardReceiver;
                                        if (anonymousClass1 != null
                                                && CheckBluetoothStateActivity
                                                        .mIsRegisterKeyGuardReceiver) {
                                            CheckBluetoothStateActivity
                                                            .mIsRegisterKeyGuardReceiver =
                                                    false;
                                            checkBluetoothStateActivity2.unregisterReceiver(
                                                    anonymousClass1);
                                        }
                                        this.this$0.finish();
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i4 = 0;
        this.mTimeoutHandler =
                new Handler(this) { // from class:
                    // com.samsung.android.settings.bluetooth.CheckBluetoothStateActivity.4
                    public final /* synthetic */ CheckBluetoothStateActivity this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        CheckBluetoothStateActivity checkBluetoothStateActivity = this.this$0;
                        switch (i4) {
                            case 0:
                                if (message.what == 1) {
                                    Log.d(
                                            "CheckBluetoothStateActivity",
                                            "Received BT_ENABLE_TIMEOUT");
                                    checkBluetoothStateActivity.mTimeoutHandler.removeMessages(1);
                                    StringBuilder sb = new StringBuilder("BluetoothState = ");
                                    sb.append(
                                            checkBluetoothStateActivity.mLocalAdapter
                                                    .getBluetoothState());
                                    sb.append(", mExpectingBluetoothOn = ");
                                    sb.append(CheckBluetoothStateActivity.mExpectingBluetoothOn);
                                    sb.append(", mIsDestroyed = ");
                                    ActionBarContextView$$ExternalSyntheticOutline0.m(
                                            sb,
                                            CheckBluetoothStateActivity.mIsDestroyed,
                                            "CheckBluetoothStateActivity");
                                    if (checkBluetoothStateActivity.mLocalAdapter
                                                            .getBluetoothState()
                                                    == 10
                                            && CheckBluetoothStateActivity.mExpectingBluetoothOn
                                            && !CheckBluetoothStateActivity.mIsDestroyed) {
                                        checkBluetoothStateActivity.finish();
                                        break;
                                    }
                                }
                                break;
                            default:
                                Preference$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("mKeyGuardHandler - "),
                                        message.what,
                                        "CheckBluetoothStateActivity");
                                int i5 = message.what;
                                if (i5 == 1) {
                                    boolean z =
                                            CheckBluetoothStateActivity.mIsRegisterKeyGuardReceiver;
                                    KeyguardManager keyguardManager =
                                            (KeyguardManager)
                                                    checkBluetoothStateActivity.getSystemService(
                                                            "keyguard");
                                    if (!keyguardManager.isKeyguardLocked()) {
                                        checkBluetoothStateActivity.checkPickerProcess();
                                        break;
                                    } else {
                                        PendingIntent broadcast =
                                                PendingIntent.getBroadcast(
                                                        checkBluetoothStateActivity,
                                                        0,
                                                        new Intent(
                                                                "com.samsung.intent.action.BLUETOOTH_KEYGUARD_UNLOCK"),
                                                        201326592);
                                        Intent intent = new Intent();
                                        intent.putExtra("afterKeyguardGone", false);
                                        intent.putExtra("dismissIfInsecure", true);
                                        Log.d(
                                                "CheckBluetoothStateActivity",
                                                "sendBendedPendingIntent");
                                        keyguardManager.semSetPendingIntentAfterUnlock(
                                                broadcast, intent);
                                        if (!keyguardManager.isKeyguardSecure()) {
                                            checkBluetoothStateActivity.checkPickerProcess();
                                            break;
                                        } else {
                                            Message message2 = new Message();
                                            message2.what = 2;
                                            checkBluetoothStateActivity.mKeyGuardHandler
                                                    .sendMessageDelayed(message2, 150L);
                                            break;
                                        }
                                    }
                                } else if (i5 == 2) {
                                    IntentFilter m =
                                            AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(
                                                    "com.samsung.keyguard.KEYGUARD_STATE_UPDATE");
                                    if (!CheckBluetoothStateActivity.mIsRegisterKeyGuardReceiver) {
                                        checkBluetoothStateActivity.registerReceiver(
                                                checkBluetoothStateActivity.mKeyGuardReceiver,
                                                m,
                                                2);
                                        CheckBluetoothStateActivity.mIsRegisterKeyGuardReceiver =
                                                true;
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i5 = 1;
        this.mKeyGuardHandler =
                new Handler(this) { // from class:
                    // com.samsung.android.settings.bluetooth.CheckBluetoothStateActivity.4
                    public final /* synthetic */ CheckBluetoothStateActivity this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        CheckBluetoothStateActivity checkBluetoothStateActivity = this.this$0;
                        switch (i5) {
                            case 0:
                                if (message.what == 1) {
                                    Log.d(
                                            "CheckBluetoothStateActivity",
                                            "Received BT_ENABLE_TIMEOUT");
                                    checkBluetoothStateActivity.mTimeoutHandler.removeMessages(1);
                                    StringBuilder sb = new StringBuilder("BluetoothState = ");
                                    sb.append(
                                            checkBluetoothStateActivity.mLocalAdapter
                                                    .getBluetoothState());
                                    sb.append(", mExpectingBluetoothOn = ");
                                    sb.append(CheckBluetoothStateActivity.mExpectingBluetoothOn);
                                    sb.append(", mIsDestroyed = ");
                                    ActionBarContextView$$ExternalSyntheticOutline0.m(
                                            sb,
                                            CheckBluetoothStateActivity.mIsDestroyed,
                                            "CheckBluetoothStateActivity");
                                    if (checkBluetoothStateActivity.mLocalAdapter
                                                            .getBluetoothState()
                                                    == 10
                                            && CheckBluetoothStateActivity.mExpectingBluetoothOn
                                            && !CheckBluetoothStateActivity.mIsDestroyed) {
                                        checkBluetoothStateActivity.finish();
                                        break;
                                    }
                                }
                                break;
                            default:
                                Preference$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("mKeyGuardHandler - "),
                                        message.what,
                                        "CheckBluetoothStateActivity");
                                int i52 = message.what;
                                if (i52 == 1) {
                                    boolean z =
                                            CheckBluetoothStateActivity.mIsRegisterKeyGuardReceiver;
                                    KeyguardManager keyguardManager =
                                            (KeyguardManager)
                                                    checkBluetoothStateActivity.getSystemService(
                                                            "keyguard");
                                    if (!keyguardManager.isKeyguardLocked()) {
                                        checkBluetoothStateActivity.checkPickerProcess();
                                        break;
                                    } else {
                                        PendingIntent broadcast =
                                                PendingIntent.getBroadcast(
                                                        checkBluetoothStateActivity,
                                                        0,
                                                        new Intent(
                                                                "com.samsung.intent.action.BLUETOOTH_KEYGUARD_UNLOCK"),
                                                        201326592);
                                        Intent intent = new Intent();
                                        intent.putExtra("afterKeyguardGone", false);
                                        intent.putExtra("dismissIfInsecure", true);
                                        Log.d(
                                                "CheckBluetoothStateActivity",
                                                "sendBendedPendingIntent");
                                        keyguardManager.semSetPendingIntentAfterUnlock(
                                                broadcast, intent);
                                        if (!keyguardManager.isKeyguardSecure()) {
                                            checkBluetoothStateActivity.checkPickerProcess();
                                            break;
                                        } else {
                                            Message message2 = new Message();
                                            message2.what = 2;
                                            checkBluetoothStateActivity.mKeyGuardHandler
                                                    .sendMessageDelayed(message2, 150L);
                                            break;
                                        }
                                    }
                                } else if (i52 == 2) {
                                    IntentFilter m =
                                            AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(
                                                    "com.samsung.keyguard.KEYGUARD_STATE_UPDATE");
                                    if (!CheckBluetoothStateActivity.mIsRegisterKeyGuardReceiver) {
                                        checkBluetoothStateActivity.registerReceiver(
                                                checkBluetoothStateActivity.mKeyGuardReceiver,
                                                m,
                                                2);
                                        CheckBluetoothStateActivity.mIsRegisterKeyGuardReceiver =
                                                true;
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
    }

    public final void checkPickerProcess() {
        int bluetoothState = this.mLocalAdapter.getBluetoothState();
        if (bluetoothState == 10) {
            processEnableResult();
        } else if (bluetoothState == 12) {
            new Thread(
                            new Runnable() { // from class:
                                             // com.samsung.android.settings.bluetooth.CheckBluetoothStateActivity.6
                                @Override // java.lang.Runnable
                                public final void run() {
                                    try {
                                        Thread.sleep(400L);
                                        CheckBluetoothStateActivity checkBluetoothStateActivity =
                                                CheckBluetoothStateActivity.this;
                                        boolean z =
                                                CheckBluetoothStateActivity
                                                        .mIsRegisterKeyGuardReceiver;
                                        checkBluetoothStateActivity.launchDevicePicker();
                                    } catch (InterruptedException e) {
                                        Log.d(
                                                "CheckBluetoothStateActivity",
                                                "InterruptedException" + e);
                                        Thread.currentThread().interrupt();
                                    }
                                }
                            })
                    .start();
        }
    }

    public final void launchDevicePicker() {
        Intent intent;
        Log.e("CheckBluetoothStateActivity", "launchDevicePicker " + mDialogExist);
        Intent intent2 = getIntent();
        int intExtra = intent2.getIntExtra("android.bluetooth.devicepicker.extra.FILTER_TYPE", 0);
        if (intExtra == 5 || intExtra == 105) {
            intent = new Intent("com.samsung.settings.bluetooth.pickerdialog.LAUNCH");
            intent.setFlags(276824064);
        } else {
            intent = new Intent("com.samsung.settings.bluetooth.CheckBluetoothStateActivity");
            intent.setFlags(335544320);
            intent.putExtra(
                    "android.bluetooth.devicepicker.extra.NEED_AUTH",
                    intent2.getBooleanExtra(
                            "android.bluetooth.devicepicker.extra.NEED_AUTH", false));
            intent.putExtra(
                    "android.bluetooth.FromHeadsetActivity",
                    intent2.getBooleanExtra("android.bluetooth.FromHeadsetActivity", false));
        }
        intent.putExtra("android.bluetooth.devicepicker.extra.FILTER_TYPE", intExtra);
        String stringExtra =
                intent2.getStringExtra("android.bluetooth.devicepicker.extra.LAUNCH_PACKAGE");
        if (stringExtra != null) {
            intent.putExtra("android.bluetooth.devicepicker.extra.LAUNCH_PACKAGE", stringExtra);
        }
        String stringExtra2 =
                intent2.getStringExtra(
                        "android.bluetooth.devicepicker.extra.DEVICE_PICKER_LAUNCH_CLASS");
        if (stringExtra2 != null) {
            intent.putExtra(
                    "android.bluetooth.devicepicker.extra.DEVICE_PICKER_LAUNCH_CLASS",
                    stringExtra2);
        }
        if (!TextUtils.isEmpty(this.mCallingAppPackageName)) {
            intent.putExtra(
                    "android.bluetooth.devicepicker.extra.CALLING_APP_PACKAGE",
                    this.mCallingAppPackageName);
        }
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.e("CheckBluetoothStateActivity", "startActivity() failed: " + e);
        }
        finish();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x00fa A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00b5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreate(android.os.Bundle r14) {
        /*
            Method dump skipped, instructions count: 426
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.bluetooth.CheckBluetoothStateActivity.onCreate(android.os.Bundle):void");
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        Log.d("CheckBluetoothStateActivity", "onDestroy :: ");
        mIsDestroyed = true;
        try {
            AnonymousClass1 anonymousClass1 = this.mBluetoothReceiver;
            if (anonymousClass1 != null) {
                unregisterReceiver(anonymousClass1);
            }
            AnonymousClass1 anonymousClass12 = this.mKeyGuardUnlockReceiver;
            if (anonymousClass12 != null) {
                unregisterReceiver(anonymousClass12);
            }
            AnonymousClass1 anonymousClass13 = this.mKeyGuardReceiver;
            if (anonymousClass13 != null && mIsRegisterKeyGuardReceiver) {
                mIsRegisterKeyGuardReceiver = false;
                unregisterReceiver(anonymousClass13);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        removeMessages(1);
    }

    @Override // android.app.Activity
    public final void onResume() {
        Log.d("CheckBluetoothStateActivity", "onResume :: ");
        super.onResume();
    }

    public final void processEnableResult() {
        Log.d("CheckBluetoothStateActivity", "processEnableResult ::");
        if (TextUtils.isEmpty(this.mCallingAppPackageName)) {
            BluetoothDump.BtLog(
                    "CheckBluetoothStateActivity -- processEnableResult: Package name is null");
        } else {
            BluetoothDump.BtLog(
                    "CheckBluetoothStateActivity -- processEnableResult: called by "
                            + this.mCallingAppPackageName);
        }
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null || !defaultAdapter.enable()) {
            finish();
            return;
        }
        mExpectingBluetoothOn = true;
        AnonymousClass4 anonymousClass4 = this.mTimeoutHandler;
        anonymousClass4.sendMessageDelayed(anonymousClass4.obtainMessage(1), 20000L);
        startActivity(new Intent(this, (Class<?>) BluetoothEnablingActivity.class));
    }
}

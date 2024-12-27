package com.samsung.android.settings.bluetooth.tethering;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothPan;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.TetheringManager;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.widget.SeslSwitchBar;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.lifecycle.LifecycleObserver;
import androidx.preference.Preference;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.datausage.DataSaverBackend;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.BluetoothUtils$$ExternalSyntheticLambda6;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import com.samsung.android.settings.connection.tether.SecTetherUtils;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BluetoothTetheringSwitchEnabler
        implements CompoundButton.OnCheckedChangeListener,
                Preference.OnPreferenceChangeListener,
                LifecycleObserver {
    public static final boolean DBG = !SystemProperties.getBoolean("ro.product_ship", true);
    public Activity mActivity;
    public ConnectivityManager mConnectivityManager;
    public Context mContext;
    public SettingsMainSwitchBar mSettingsMainSwitchBar;
    public SecSwitchPreferenceScreen mSwitchPreferenceScreen;
    public BluetoothTetheringUtils.NetworkCallback mNetworkCallback = null;
    public BluetoothTetheringUtils.AnonymousClass1 mTetheringCallback = null;
    public BluetoothTetheringUtils$$ExternalSyntheticLambda0 mDataSaverListener = null;
    public BluetoothTetherReceiver mBroadcastReceiver = null;
    public final AtomicReference mProfileServiceListener = new AtomicReference(null);
    public final AtomicReference mBluetoothPan = new AtomicReference(null);
    public final AtomicBoolean mIsProxyRequested = new AtomicBoolean(false);
    public final AtomicBoolean mIsEnabledForTethering = new AtomicBoolean(false);
    public final AtomicBoolean mIsTetheringRequested = new AtomicBoolean(false);
    public final Handler mHandler = new Handler(Looper.getMainLooper());

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class BluetoothTetherReceiver extends BroadcastReceiver {
        public BluetoothTetherReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String str;
            String str2;
            String action = intent.getAction();
            boolean z = BluetoothTetheringSwitchEnabler.DBG;
            if (z) {
                Log.v("BluetoothTetheringSwitchEnabler", "onReceive: " + action);
            }
            if (action == null) {}
            str = "on";
            str2 = "unknown";
            switch (action) {
                case "android.bluetooth.adapter.action.STATE_CHANGED":
                    int intExtra =
                            intent.getIntExtra(
                                    "android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
                    if (z) {
                        AtomicReference atomicReference = BluetoothTetheringUtils.mDataSaverBackend;
                        switch (intExtra) {
                            case 10:
                                str = "off";
                                break;
                            case 11:
                                str = "turning on";
                                break;
                            case 12:
                                break;
                            case 13:
                                str = "turning off";
                                break;
                            default:
                                str = "unknown";
                                break;
                        }
                        Log.d("BluetoothTetheringSwitchEnabler", "BT state: ".concat(str));
                    }
                    if (intExtra != 12) {
                        if (intExtra == 10) {
                            BluetoothTetheringSwitchEnabler.this.closeProfileProxy();
                            BluetoothTetheringSwitchEnabler.this.setSwitchChecked(false);
                            BluetoothTetheringSwitchEnabler.this.setTethering(false);
                            break;
                        }
                    } else {
                        BluetoothTetheringSwitchEnabler.this.getProfileProxy();
                        break;
                    }
                    break;
                case "android.intent.action.AIRPLANE_MODE":
                    AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                            "Airplane mode: ",
                            "BluetoothTetheringSwitchEnabler",
                            Settings.Global.getInt(
                                            BluetoothTetheringSwitchEnabler.this.mContext
                                                    .getContentResolver(),
                                            "airplane_mode_on",
                                            0)
                                    != 0);
                    BluetoothTetheringSwitchEnabler.this.updateSwitch$1();
                    break;
                case "android.bluetooth.pan.profile.action.CONNECTION_STATE_CHANGED":
                    Log.d(
                            "BluetoothTetheringSwitchEnabler",
                            "PAN connection state: "
                                    .concat(
                                            BluetoothTetheringUtils.getPanStateMessage(
                                                    intent.getIntExtra(
                                                            "android.bluetooth.profile.extra.STATE",
                                                            0))));
                    BluetoothTetheringSwitchEnabler.this.updateSwitch$1();
                    break;
                case "android.bluetooth.action.TETHERING_STATE_CHANGED":
                    int intExtra2 =
                            intent.getIntExtra("android.bluetooth.extra.TETHERING_STATE", 1);
                    Log.d(
                            "BluetoothTetheringSwitchEnabler",
                            "BT tethering state: ".concat(intExtra2 == 1 ? "off" : "on"));
                    if (intExtra2 == 1) {
                        BluetoothTetheringSwitchEnabler.this.getClass();
                        BluetoothTetheringSwitchEnabler.setScanMode(21);
                    }
                    BluetoothTetheringSwitchEnabler.this.updateSwitch$1();
                    break;
                case "android.bluetooth.adapter.action.SCAN_MODE_CHANGED":
                    if (z) {
                        int intExtra3 =
                                intent.getIntExtra("android.bluetooth.adapter.extra.SCAN_MODE", 20);
                        AtomicReference atomicReference2 =
                                BluetoothTetheringUtils.mDataSaverBackend;
                        if (intExtra3 == 20) {
                            str2 = SignalSeverity.NONE;
                        } else if (intExtra3 == 21) {
                            str2 = "connectable";
                        } else if (intExtra3 == 23) {
                            str2 = "discoverable";
                        }
                        Log.v("BluetoothTetheringSwitchEnabler", "BT scan mode: ".concat(str2));
                    }
                    if (BluetoothTetheringSwitchEnabler.this.mActivity.isResumed()
                            && BluetoothTetheringSwitchEnabler.this.isTetheringEnabled()) {
                        BluetoothTetheringSwitchEnabler.this.getClass();
                        BluetoothTetheringSwitchEnabler.setScanMode(23);
                        break;
                    }
                    break;
            }
        }
    }

    public BluetoothTetheringSwitchEnabler(Activity activity, Object obj) {
        this.mSettingsMainSwitchBar = null;
        this.mSwitchPreferenceScreen = null;
        this.mContext = activity;
        this.mActivity = activity;
        this.mConnectivityManager = (ConnectivityManager) activity.getSystemService("connectivity");
        if (obj instanceof SecSwitchPreferenceScreen) {
            if (DBG) {
                Log.v(
                        "BluetoothTetheringSwitchEnabler",
                        "BluetoothTetheringSwitchEnabler: Mobile Hotspot and Tethering");
            }
            this.mSwitchPreferenceScreen = (SecSwitchPreferenceScreen) obj;
        } else {
            if (!(obj instanceof SettingsMainSwitchBar)) {
                Log.d(
                        "BluetoothTetheringSwitchEnabler",
                        "BluetoothTetheringSwitchEnabler: Unknown type");
                return;
            }
            if (DBG) {
                Log.v(
                        "BluetoothTetheringSwitchEnabler",
                        "BluetoothTetheringSwitchEnabler: Bluetooth tethering");
            }
            this.mSettingsMainSwitchBar = (SettingsMainSwitchBar) obj;
        }
    }

    public final void closeProfileProxy() {
        BluetoothAdapter defaultAdapter;
        BluetoothProfile bluetoothProfile;
        if (DBG) {
            StringBuilder sb = new StringBuilder("closeProfileProxy: Requested: ");
            sb.append(this.mIsProxyRequested);
            sb.append(", Profile: ");
            sb.append(this.mBluetoothPan.get() != null);
            Log.v("BluetoothTetheringSwitchEnabler", sb.toString());
        }
        AtomicReference atomicReference = this.mBluetoothPan;
        AtomicReference atomicReference2 = BluetoothTetheringUtils.mDataSaverBackend;
        if (atomicReference == null
                || (defaultAdapter = BluetoothAdapter.getDefaultAdapter()) == null
                || (bluetoothProfile = (BluetoothProfile) atomicReference.getAndSet(null))
                        == null) {
            return;
        }
        defaultAdapter.closeProfileProxy(5, bluetoothProfile);
        this.mIsProxyRequested.set(false);
    }

    public final void getProfileProxy() {
        BluetoothAdapter defaultAdapter;
        boolean z = false;
        if (DBG) {
            StringBuilder sb = new StringBuilder("getProfileProxy: Requested: ");
            sb.append(this.mIsProxyRequested);
            sb.append(", Profile: ");
            sb.append(this.mBluetoothPan.get() != null);
            Log.v("BluetoothTetheringSwitchEnabler", sb.toString());
        }
        if (this.mIsProxyRequested.get()) {
            return;
        }
        AtomicBoolean atomicBoolean = this.mIsProxyRequested;
        Context context = this.mContext;
        BluetoothProfile.ServiceListener serviceListener =
                (BluetoothProfile.ServiceListener) this.mProfileServiceListener.get();
        AtomicReference atomicReference = BluetoothTetheringUtils.mDataSaverBackend;
        if (context != null
                && serviceListener != null
                && (defaultAdapter = BluetoothAdapter.getDefaultAdapter()) != null) {
            z = defaultAdapter.getProfileProxy(context, serviceListener, 5);
        }
        atomicBoolean.set(z);
    }

    public final boolean isTetheringEnabled() {
        if (this.mBluetoothPan.get() == null) {
            return LocalBluetoothManager.getInstance(this.mContext, BluetoothUtils.mOnInitCallback)
                    .mProfileManager
                    .mPanProfile
                    .isNapEnabled();
        }
        AtomicReference atomicReference = BluetoothTetheringUtils.mDataSaverBackend;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!(defaultAdapter == null ? false : defaultAdapter.isEnabled())) {
            return false;
        }
        BluetoothPan bluetoothPan = (BluetoothPan) this.mBluetoothPan.get();
        return bluetoothPan == null ? false : bluetoothPan.isTetheringOn();
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        boolean isTetheringEnabled = isTetheringEnabled();
        Utils$$ExternalSyntheticOutline0.m653m(
                "onCheckedChanged: Tethering: ",
                isTetheringEnabled,
                ", isChecked: ",
                z,
                "BluetoothTetheringSwitchEnabler");
        if (!compoundButton.isPressed()) {
            if (DBG) {
                Log.d("BluetoothTetheringSwitchEnabler", "onCheckedChanged: Ignored local updates");
            }
        } else {
            if (z == isTetheringEnabled) {
                return;
            }
            if (z) {
                Log.i("BluetoothTetheringSwitchEnabler", "onCheckedChanged: Enabling");
                startTethering();
            } else {
                Log.i("BluetoothTetheringSwitchEnabler", "onCheckedChanged: Disabling");
                setTethering(false);
            }
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "onPreferenceChange: ", "BluetoothTetheringSwitchEnabler", booleanValue);
        LoggingHelper.insertEventLogging(90, 3430, booleanValue);
        return false;
    }

    /* JADX WARN: Type inference failed for: r4v3, types: [com.samsung.android.settings.bluetooth.tethering.BluetoothTetheringUtils$1] */
    public final void onStart() {
        boolean z = DBG;
        if (z) {
            Log.v("BluetoothTetheringSwitchEnabler", "onStart");
        }
        AtomicReference atomicReference = this.mProfileServiceListener;
        final AtomicReference atomicReference2 = this.mBluetoothPan;
        final BluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda0
                bluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda0 =
                        new BluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda0(this, 2);
        AtomicReference atomicReference3 = BluetoothTetheringUtils.mDataSaverBackend;
        BluetoothTetheringUtils$$ExternalSyntheticLambda0
                bluetoothTetheringUtils$$ExternalSyntheticLambda0 = null;
        atomicReference.compareAndSet(
                null,
                atomicReference2 == null
                        ? null
                        : new BluetoothProfile
                                .ServiceListener() { // from class:
                                                     // com.samsung.android.settings.bluetooth.tethering.BluetoothTetheringUtils.2
                            public final /* synthetic */ AtomicReference val$bluetoothPan;
                            public final /* synthetic */ Runnable val$runnableConnected;
                            public final /* synthetic */ Runnable val$runnableDisconnected = null;

                            public AnonymousClass2(
                                    final AtomicReference atomicReference22,
                                    final BluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda0
                                            bluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda02) {
                                r1 = atomicReference22;
                                r2 = bluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda02;
                            }

                            @Override // android.bluetooth.BluetoothProfile.ServiceListener
                            public final void onServiceConnected(
                                    int i, BluetoothProfile bluetoothProfile) {
                                r1.set((BluetoothPan) bluetoothProfile);
                                Runnable runnable = r2;
                                if (runnable != null) {
                                    runnable.run();
                                }
                            }

                            @Override // android.bluetooth.BluetoothProfile.ServiceListener
                            public final void onServiceDisconnected(int i) {
                                Runnable runnable = this.val$runnableDisconnected;
                                if (runnable != null) {
                                    runnable.run();
                                }
                            }
                        });
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null ? false : defaultAdapter.isEnabled()) {
            getProfileProxy();
        }
        this.mNetworkCallback =
                BluetoothTetheringUtils.registerNetworkCallback(
                        this.mContext,
                        new BluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda0(this, 3),
                        null);
        final BluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda0
                bluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda02 =
                        new BluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda0(this, 4);
        final BluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda0
                bluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda03 =
                        new BluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda0(this, 5);
        this.mTetheringCallback =
                new ConnectivityManager
                        .OnStartTetheringCallback() { // from class:
                                                      // com.samsung.android.settings.bluetooth.tethering.BluetoothTetheringUtils.1
                    public final /* synthetic */ Runnable val$runnableFailed;
                    public final /* synthetic */ Runnable val$runnableStarted;

                    public AnonymousClass1(
                            final BluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda0
                                    bluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda022,
                            final BluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda0
                                    bluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda032) {
                        r1 = bluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda022;
                        r2 = bluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda032;
                    }

                    public final void onTetheringFailed() {
                        Runnable runnable = r2;
                        if (runnable != null) {
                            runnable.run();
                        }
                    }

                    public final void onTetheringStarted() {
                        Runnable runnable = r1;
                        if (runnable != null) {
                            runnable.run();
                        }
                    }
                };
        Context context = this.mContext;
        BluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda0
                bluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda04 =
                        new BluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda0(this, 6);
        BluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda0
                bluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda05 =
                        new BluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda0(this, 7);
        if (context != null) {
            AtomicReference atomicReference4 = BluetoothTetheringUtils.mDataSaverBackend;
            atomicReference4.compareAndSet(null, new DataSaverBackend(context));
            bluetoothTetheringUtils$$ExternalSyntheticLambda0 =
                    new BluetoothTetheringUtils$$ExternalSyntheticLambda0(
                            bluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda04,
                            bluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda05);
            ((DataSaverBackend) atomicReference4.get())
                    .addListener(bluetoothTetheringUtils$$ExternalSyntheticLambda0);
        }
        this.mDataSaverListener = bluetoothTetheringUtils$$ExternalSyntheticLambda0;
        BluetoothTetherReceiver bluetoothTetherReceiver = new BluetoothTetherReceiver();
        this.mBroadcastReceiver = bluetoothTetherReceiver;
        Context context2 = this.mContext;
        IntentFilter intentFilter =
                new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.adapter.action.SCAN_MODE_CHANGED");
        intentFilter.addAction("android.bluetooth.pan.profile.action.CONNECTION_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.action.TETHERING_STATE_CHANGED");
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        context2.registerReceiver(bluetoothTetherReceiver, intentFilter);
        if (this.mSettingsMainSwitchBar != null) {
            if (z) {
                Log.v("BluetoothTetheringSwitchEnabler", "addSwitchChangeListener");
            }
            this.mSettingsMainSwitchBar.addOnSwitchChangeListener(this);
        }
    }

    public final void onStop() {
        ConnectivityManager connectivityManager;
        boolean z = DBG;
        if (z) {
            Log.v("BluetoothTetheringSwitchEnabler", "onStop");
        }
        if (this.mSettingsMainSwitchBar != null) {
            if (z) {
                Log.v("BluetoothTetheringSwitchEnabler", "removeSwitchChangeListener");
            }
            this.mSettingsMainSwitchBar.removeOnSwitchChangeListener(this);
        }
        Context context = this.mContext;
        BluetoothTetheringUtils$$ExternalSyntheticLambda0
                bluetoothTetheringUtils$$ExternalSyntheticLambda0 = this.mDataSaverListener;
        AtomicReference atomicReference = BluetoothTetheringUtils.mDataSaverBackend;
        if (context != null && bluetoothTetheringUtils$$ExternalSyntheticLambda0 != null) {
            AtomicReference atomicReference2 = BluetoothTetheringUtils.mDataSaverBackend;
            atomicReference2.compareAndSet(null, new DataSaverBackend(context));
            ((DataSaverBackend) atomicReference2.get())
                    .remListener(bluetoothTetheringUtils$$ExternalSyntheticLambda0);
        }
        this.mDataSaverListener = null;
        this.mTetheringCallback = null;
        Context context2 = this.mContext;
        BluetoothTetheringUtils.NetworkCallback networkCallback = this.mNetworkCallback;
        if (context2 != null
                && networkCallback != null
                && (connectivityManager =
                                (ConnectivityManager) context2.getSystemService("connectivity"))
                        != null) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
        this.mNetworkCallback = null;
        Context context3 = this.mContext;
        BluetoothTetherReceiver bluetoothTetherReceiver = this.mBroadcastReceiver;
        if (bluetoothTetherReceiver != null) {
            context3.unregisterReceiver(bluetoothTetherReceiver);
        }
        this.mBroadcastReceiver = null;
        closeProfileProxy();
        this.mProfileServiceListener.set(null);
    }

    public final void setScanMode() {
        if (DBG) {
            Log.v("BluetoothTetheringSwitchEnabler", "setScanMode");
        }
        AtomicReference atomicReference = BluetoothTetheringUtils.mDataSaverBackend;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!(defaultAdapter == null ? false : defaultAdapter.isEnabled())
                || !isTetheringEnabled()) {
            setScanMode(21);
        } else {
            if (BluetoothTetheringUtils.isBluetoothErrored(this.mContext)) {
                return;
            }
            setScanMode(23);
        }
    }

    public final void setSwitchChecked(boolean z) {
        if (DBG) {
            Log.v("BluetoothTetheringSwitchEnabler", "setSwitchChecked: " + z);
        }
        SecSwitchPreferenceScreen secSwitchPreferenceScreen = this.mSwitchPreferenceScreen;
        if (secSwitchPreferenceScreen != null && secSwitchPreferenceScreen.mChecked != z) {
            secSwitchPreferenceScreen.setChecked(z);
        }
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSettingsMainSwitchBar;
        if (settingsMainSwitchBar == null
                || ((SeslSwitchBar) settingsMainSwitchBar).mSwitch.isChecked() == z) {
            return;
        }
        this.mSettingsMainSwitchBar.setChecked(z);
    }

    public final void setTethering(boolean z) {
        boolean isTetheringEnabled = isTetheringEnabled();
        Log.i(
                "BluetoothTetheringSwitchEnabler",
                "setTethering: " + isTetheringEnabled + " -> " + z);
        if (isTetheringEnabled == z) {
            return;
        }
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            Log.w("BluetoothTetheringSwitchEnabler", "setTethering: Adapter is null");
            return;
        }
        if (this.mConnectivityManager == null) {
            Log.w(
                    "BluetoothTetheringSwitchEnabler",
                    "setTethering: ConnectivityManager is null, activity already destroyed");
            return;
        }
        if (z && !defaultAdapter.isEnabled()) {
            Log.i(
                    "BluetoothTetheringSwitchEnabler",
                    "setTethering: Turn on BT for enabling tethering");
            this.mIsEnabledForTethering.set(true);
            defaultAdapter.enable();
        } else if (!z) {
            this.mConnectivityManager.stopTethering(2);
        } else {
            this.mIsTetheringRequested.set(true);
            this.mConnectivityManager.startTethering(
                    2, true, this.mTetheringCallback, this.mHandler);
        }
    }

    public final void showState(Context context) {
        String str;
        TetheringManager tetheringManager;
        AtomicReference atomicReference = BluetoothTetheringUtils.mDataSaverBackend;
        if (context != null
                && (tetheringManager = (TetheringManager) context.getSystemService("tethering"))
                        != null) {
            Log.v(
                    "BluetoothTetheringUtils",
                    "showState: available: "
                            + Arrays.toString(tetheringManager.getTetherableIfaces())
                            + ", tethered: "
                            + Arrays.toString(tetheringManager.getTetheredIfaces())
                            + ", errored: "
                            + Arrays.toString(tetheringManager.getTetheringErroredIfaces()));
        }
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            StringBuilder sb = new StringBuilder("btState: ");
            switch (defaultAdapter.getState()) {
                case 10:
                    str = "off";
                    break;
                case 11:
                    str = "turning on";
                    break;
                case 12:
                    str = "on";
                    break;
                case 13:
                    str = "turning off";
                    break;
                default:
                    str = "unknown";
                    break;
            }
            sb.append(str);
            sb.append(", tethering: ");
            sb.append(isTetheringEnabled());
            sb.append(", error: ");
            sb.append(BluetoothTetheringUtils.isBluetoothErrored(context));
            Log.v("BluetoothTetheringSwitchEnabler", sb.toString());
        }
    }

    public final void startTethering() {
        boolean isTetheringAllowed;
        final Context context;
        final String string;
        TelephonyManager telephonyManager;
        final Context context2;
        final String tetheringErrorWithWifi;
        if (this.mIsTetheringRequested.get()) {
            Log.i("BluetoothTetheringSwitchEnabler", "startTethering: Already requested");
            return;
        }
        Context context3 = this.mContext;
        if (context3 == null) {
            AtomicReference atomicReference = BluetoothTetheringUtils.mDataSaverBackend;
            isTetheringAllowed = false;
        } else {
            isTetheringAllowed = BluetoothTetheringUtils.isTetheringAllowed(context3, false);
        }
        if (isTetheringAllowed) {
            if (isTetheringEnabled()
                    || BluetoothTetheringUtils.getConnectedDevices(this.mContext).isEmpty()) {
                Context context4 = this.mContext;
                if (context4 != null ? SecTetherUtils.isProvisioningNeeded(context4) : false) {
                    BluetoothTetheringUtils.startProvisioning(this.mActivity, this.mContext);
                    return;
                } else {
                    setTethering(true);
                    return;
                }
            }
            Log.i("BluetoothTetheringSwitchEnabler", "startTethering: PANU is enabled");
            Context context5 = this.mContext;
            BluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda0
                    bluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda0 =
                            new BluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda0(this, 0);
            BluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda0
                    bluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda02 =
                            new BluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda0(this, 1);
            boolean z = BluetoothUtils.DEBUG;
            new Handler(Looper.getMainLooper())
                    .post(
                            new BluetoothUtils$$ExternalSyntheticLambda6(
                                    context5,
                                    R.string
                                            .bluetooth_tethering_disconnect_from_bluetooth_tethering_device_title,
                                    R.string
                                            .bluetooth_tethering_disconnect_from_bluetooth_tethering_device_message,
                                    R.string.bluetooth_tethering_turn_on,
                                    bluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda0,
                                    bluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda02));
            return;
        }
        Log.i("BluetoothTetheringSwitchEnabler", "startTethering: Tethering is not allowed");
        if (BluetoothTetheringUtils.isOperatorBlocked(this.mContext)) {
            Context context6 = this.mContext;
            if ((context6 == null
                            || (telephonyManager =
                                            (TelephonyManager) context6.getSystemService("phone"))
                                    == null
                            || telephonyManager.getSimState() == 5)
                    ? false
                    : true) {
                Context context7 = this.mContext;
                if (DBG) {
                    Log.v("BluetoothTetheringSwitchEnabler", "showDialog");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context7);
                builder.setMessage(context7.getString(R.string.wifi_tether_dialog_nosim_warning));
                builder.setPositiveButton(
                        R.string.dlg_ok,
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.bluetooth.tethering.BluetoothTetheringSwitchEnabler.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                BluetoothTetheringSwitchEnabler.this.updateSwitch$1();
                            }
                        });
                builder.setOnCancelListener(
                        new DialogInterface
                                .OnCancelListener() { // from class:
                                                      // com.samsung.android.settings.bluetooth.tethering.BluetoothTetheringSwitchEnabler.2
                            @Override // android.content.DialogInterface.OnCancelListener
                            public final void onCancel(DialogInterface dialogInterface) {
                                BluetoothTetheringSwitchEnabler.this.updateSwitch$1();
                            }
                        });
                builder.setTitle(R.string.tether_settings_title_bluetooth);
                builder.create().show();
            } else if (BluetoothTetheringUtils.isWiFiConnected(this.mContext)
                    && (tetheringErrorWithWifi =
                                    BluetoothUtils.getTetheringErrorWithWifi(
                                            (context2 = this.mContext)))
                            != null) {
                new Handler(Looper.getMainLooper())
                        .post(
                                new Runnable() { // from class:
                                                 // com.samsung.android.settings.bluetooth.tethering.BluetoothTetheringUtils$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        Toast.makeText(context2, tetheringErrorWithWifi, 0).show();
                                    }
                                });
            }
        } else if (BluetoothTetheringUtils.isTetheringBlockedByMdm(this.mContext)
                && (string =
                                (context = this.mContext)
                                        .getString(android.R.string.ime_action_search))
                        != null) {
            new Handler(Looper.getMainLooper())
                    .post(
                            new Runnable() { // from class:
                                             // com.samsung.android.settings.bluetooth.tethering.BluetoothTetheringUtils$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Toast.makeText(context, string, 0).show();
                                }
                            });
        }
        setSwitchChecked(false);
    }

    public final void updateSwitch$1() {
        boolean isTetheringAllowed;
        boolean isTetheringEnabled = isTetheringEnabled();
        if (DBG) {
            Log.v(
                    "BluetoothTetheringSwitchEnabler",
                    "updateSwitch: Tethering: " + isTetheringEnabled);
        }
        Context context = this.mContext;
        if (context == null) {
            AtomicReference atomicReference = BluetoothTetheringUtils.mDataSaverBackend;
            isTetheringAllowed = false;
        } else {
            isTetheringAllowed = BluetoothTetheringUtils.isTetheringAllowed(context, false);
        }
        if (isTetheringAllowed) {
            setSwitchChecked(isTetheringEnabled);
            return;
        }
        Log.i("BluetoothTetheringSwitchEnabler", "updateSwitch: Tethering is not allowed");
        setTethering(false);
        setSwitchChecked(false);
    }

    public static void setScanMode(int i) {
        String str;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null && defaultAdapter.isEnabled()) {
            if (defaultAdapter.getScanMode() != i) {
                defaultAdapter.setScanMode(i);
                if (DBG) {
                    AtomicReference atomicReference = BluetoothTetheringUtils.mDataSaverBackend;
                    if (i == 20) {
                        str = SignalSeverity.NONE;
                    } else if (i == 21) {
                        str = "connectable";
                    } else if (i != 23) {
                        str = "unknown";
                    } else {
                        str = "discoverable";
                    }
                    Log.v("BluetoothTetheringSwitchEnabler", "setScanMode: ".concat(str));
                    return;
                }
                return;
            }
            return;
        }
        if (DBG) {
            Log.v("BluetoothTetheringSwitchEnabler", "setScanMode: Adapter is null or BT is off");
        }
    }
}

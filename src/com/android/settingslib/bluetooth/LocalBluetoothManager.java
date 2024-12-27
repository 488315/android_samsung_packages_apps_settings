package com.android.settingslib.bluetooth;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.CachedBluetoothCastDeviceManager;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastAdapter;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastProfileManager;
import com.samsung.android.settingslib.bluetooth.detector.BluetoothRetryDetector;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LocalBluetoothManager {
    public static int mForegroundCount = 0;
    public static boolean mSystemUiInstance = false;
    public static LocalBluetoothManager sInstance;
    public final CachedBluetoothCastDeviceManager mCachedCastDeviceManager;
    public final CachedBluetoothDeviceManager mCachedDeviceManager;
    public final BluetoothCastEventManager mCastEventManager;
    public final Context mContext;
    public final BluetoothEventManager mEventManager;
    public WeakReference mForegroundActivity;
    public final LocalBluetoothAdapter mLocalAdapter;
    public final LocalBluetoothCastAdapter mLocalCastAdapter;
    public final LocalBluetoothCastProfileManager mLocalCastProfileManager;
    public final LocalBluetoothProfileManager mProfileManager;
    public final BluetoothRetryDetector mRestoredRetryDetector;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface BluetoothManagerCallback {
        void onBluetoothManagerInitialized();
    }

    public LocalBluetoothManager(LocalBluetoothAdapter localBluetoothAdapter, Context context) {
        LocalBluetoothCastAdapter localBluetoothCastAdapter;
        Log.d("LocalBluetoothManager", "LocalBluetoothManager :: constructor");
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mLocalAdapter = localBluetoothAdapter;
        CachedBluetoothDeviceManager cachedBluetoothDeviceManager =
                new CachedBluetoothDeviceManager(applicationContext, this);
        this.mCachedDeviceManager = cachedBluetoothDeviceManager;
        BluetoothEventManager bluetoothEventManager =
                new BluetoothEventManager(
                        localBluetoothAdapter,
                        this,
                        cachedBluetoothDeviceManager,
                        applicationContext);
        this.mEventManager = bluetoothEventManager;
        LocalBluetoothProfileManager localBluetoothProfileManager =
                new LocalBluetoothProfileManager(
                        applicationContext,
                        localBluetoothAdapter,
                        cachedBluetoothDeviceManager,
                        bluetoothEventManager);
        this.mProfileManager = localBluetoothProfileManager;
        if ("com.android.systemui".equals(context.getPackageName().toLowerCase())) {
            mSystemUiInstance = true;
        }
        mForegroundCount = 0;
        BluetoothRetryDetector bluetoothRetryDetector = new BluetoothRetryDetector();
        Intent intent = new Intent();
        bluetoothRetryDetector.mTipsIntent = intent;
        intent.setClassName(
                "com.samsung.android.net.wifi.wifiguider",
                "com.samsung.android.net.wifi.wifiguider.activity.bluetooth.BluetoothGuideActivity");
        bluetoothRetryDetector.mIsForRestored = true;
        bluetoothRetryDetector.mRestoredDeviceList = new HashMap();
        this.mRestoredRetryDetector = bluetoothRetryDetector;
        localBluetoothProfileManager.updateLocalProfiles();
        bluetoothEventManager.readPairedDevices();
        bluetoothEventManager.readRestoredDevices();
        if (SemBluetoothCastAdapter.isBluetoothCastSupported()) {
            synchronized (LocalBluetoothCastAdapter.class) {
                try {
                    if (LocalBluetoothCastAdapter.sInstance == null) {
                        LocalBluetoothCastAdapter.sInstance =
                                new LocalBluetoothCastAdapter(applicationContext);
                    }
                    localBluetoothCastAdapter = LocalBluetoothCastAdapter.sInstance;
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.mLocalCastAdapter = localBluetoothCastAdapter;
            CachedBluetoothCastDeviceManager cachedBluetoothCastDeviceManager =
                    new CachedBluetoothCastDeviceManager(applicationContext, this);
            this.mCachedCastDeviceManager = cachedBluetoothCastDeviceManager;
            BluetoothCastEventManager bluetoothCastEventManager =
                    new BluetoothCastEventManager(
                            localBluetoothCastAdapter,
                            cachedBluetoothCastDeviceManager,
                            applicationContext);
            this.mCastEventManager = bluetoothCastEventManager;
            this.mLocalCastProfileManager =
                    new LocalBluetoothCastProfileManager(
                            applicationContext,
                            localBluetoothCastAdapter,
                            cachedBluetoothCastDeviceManager,
                            bluetoothCastEventManager);
        }
    }

    public static synchronized LocalBluetoothManager getInstance(
            Context context, BluetoothManagerCallback bluetoothManagerCallback) {
        synchronized (LocalBluetoothManager.class) {
            if (sInstance == null) {
                Log.d("LocalBluetoothManager", "LocalBluetoothManager :: sInstance == null");
                LocalBluetoothAdapter localBluetoothAdapter = LocalBluetoothAdapter.getInstance();
                if (localBluetoothAdapter == null) {
                    Log.d("LocalBluetoothManager", "LocalBluetoothManager :: adapter == null");
                    return null;
                }
                sInstance = new LocalBluetoothManager(localBluetoothAdapter, context);
                if (bluetoothManagerCallback != null) {
                    Log.d(
                            "LocalBluetoothManager",
                            "LocalBluetoothManager :: onInitCallback != null");
                    context.getApplicationContext();
                    bluetoothManagerCallback.onBluetoothManagerInitialized();
                }
            }
            return sInstance;
        }
    }

    public final boolean isForegroundActivity() {
        WeakReference weakReference = this.mForegroundActivity;
        return (weakReference == null || weakReference.get() == null) ? false : true;
    }

    public final boolean isForegroundActivityQP() {
        return Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(),
                        "bluetooth_settings_foreground_qp",
                        0,
                        -2)
                == 1;
    }

    public final boolean isTetheredSettings() {
        String string =
                Settings.Secure.getString(
                        this.mContext.getContentResolver(),
                        "bluetooth_tethering_settings_foreground");
        Log.d("LocalBluetoothManager", "isTetheredSettings : " + string);
        return "true".equals(string);
    }

    public final boolean semIsForegroundActivity() {
        return Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(), "bluetooth_settings_foreground", 0, -2)
                != 0;
    }

    public final synchronized void setForegroundActivity(Context context) {
        try {
            if (context != null) {
                Log.d("LocalBluetoothManager", "setting foreground activity to non-null context");
                this.mForegroundActivity = new WeakReference(context);
                mForegroundCount++;
            } else {
                if (this.mForegroundActivity != null) {
                    Log.d("LocalBluetoothManager", "setting foreground activity to null");
                    this.mForegroundActivity = null;
                }
                int i = mForegroundCount;
                if (i > 0) {
                    mForegroundCount = i - 1;
                } else {
                    Log.d(
                            "LocalBluetoothManager",
                            "setForegroundActivity :: mForegroundCount is smaller than 0 = ");
                }
            }
            Log.d(
                    "LocalBluetoothManager",
                    "setForegroundActivity :: mForegroundCount = " + mForegroundCount);
            Settings.Secure.putIntForUser(
                    this.mContext.getContentResolver(),
                    "bluetooth_settings_foreground",
                    mForegroundCount,
                    -2);
        } catch (Throwable th) {
            throw th;
        }
    }
}

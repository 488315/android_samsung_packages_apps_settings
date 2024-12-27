package com.android.settingslib.devicestate;

import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;

import androidx.compose.animation.ChangeSize$$ExternalSyntheticOutline0;

import com.android.internal.annotations.VisibleForTesting;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.collections.ArraysKt___ArraysKt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DeviceStateRotationLockSettingsManager {
    public static DeviceStateRotationLockSettingsManager sSingleton;
    public final Set mListeners;
    public SparseIntArray mPostureDefaultRotationLockSettings;
    public String[] mPostureRotationLockDefaults;
    public SparseIntArray mPostureRotationLockFallbackSettings;
    public SparseIntArray mPostureRotationLockSettings;
    public final PosturesHelper mPosturesHelper;
    public final SecureSettings mSecureSettings;
    public List mSettableDeviceStates;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface DeviceStateRotationLockSettingsListener {
        void onSettingsChanged();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettableDeviceState {
        public final int mDeviceState;
        public final boolean mIsSettable;

        public SettableDeviceState(int i, boolean z) {
            this.mDeviceState = i;
            this.mIsSettable = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SettableDeviceState)) {
                return false;
            }
            SettableDeviceState settableDeviceState = (SettableDeviceState) obj;
            return this.mDeviceState == settableDeviceState.mDeviceState
                    && this.mIsSettable == settableDeviceState.mIsSettable;
        }

        public final int hashCode() {
            return Objects.hash(
                    Integer.valueOf(this.mDeviceState), Boolean.valueOf(this.mIsSettable));
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("SettableDeviceState{mDeviceState=");
            sb.append(this.mDeviceState);
            sb.append(", mIsSettable=");
            return ChangeSize$$ExternalSyntheticOutline0.m(sb, this.mIsSettable, '}');
        }
    }

    @VisibleForTesting
    public DeviceStateRotationLockSettingsManager(Context context, SecureSettings secureSettings) {
        Handler handler = new Handler(Looper.getMainLooper());
        this.mListeners = new HashSet();
        this.mSecureSettings = secureSettings;
        this.mPosturesHelper = new PosturesHelper(context);
        this.mPostureRotationLockDefaults = context.getResources().getStringArray(17236282);
        loadDefaults();
        initializeInMemoryMap();
        ((AndroidSecureSettings) secureSettings)
                .mContentResolver.registerContentObserver(
                        Settings.Secure.getUriFor("device_state_rotation_lock"),
                        false,
                        new ContentObserver(
                                handler) { // from class:
                                           // com.android.settingslib.devicestate.DeviceStateRotationLockSettingsManager.1
                            @Override // android.database.ContentObserver
                            public final void onChange(boolean z) {
                                DeviceStateRotationLockSettingsManager.this
                                        .onPersistedSettingsChanged();
                            }
                        },
                        -2);
    }

    public static synchronized DeviceStateRotationLockSettingsManager getInstance(Context context) {
        DeviceStateRotationLockSettingsManager deviceStateRotationLockSettingsManager;
        synchronized (DeviceStateRotationLockSettingsManager.class) {
            try {
                if (sSingleton == null) {
                    Context applicationContext = context.getApplicationContext();
                    sSingleton =
                            new DeviceStateRotationLockSettingsManager(
                                    applicationContext,
                                    new AndroidSecureSettings(
                                            applicationContext.getContentResolver()));
                }
                deviceStateRotationLockSettingsManager = sSingleton;
            } catch (Throwable th) {
                throw th;
            }
        }
        return deviceStateRotationLockSettingsManager;
    }

    @VisibleForTesting
    public static synchronized void resetInstance() {
        synchronized (DeviceStateRotationLockSettingsManager.class) {
            sSingleton = null;
        }
    }

    public final void initializeInMemoryMap() {
        String stringForUser =
                Settings.Secure.getStringForUser(
                        ((AndroidSecureSettings) this.mSecureSettings).mContentResolver,
                        "device_state_rotation_lock",
                        -2);
        if (TextUtils.isEmpty(stringForUser)) {
            loadDefaults();
            persistSettings();
            return;
        }
        String[] split = stringForUser.split(":");
        if (split.length % 2 != 0) {
            Log.wtf(
                    "DSRotLockSettingsMngr",
                    "Can't deserialize saved settings, falling back on defaults");
            loadDefaults();
            persistSettings();
            return;
        }
        this.mPostureRotationLockSettings = new SparseIntArray(split.length / 2);
        int i = 0;
        while (true) {
            boolean z = true;
            if (i >= split.length - 1) {
                return;
            }
            int i2 = i + 1;
            try {
                int parseInt = Integer.parseInt(split[i]);
                i += 2;
                int parseInt2 = Integer.parseInt(split[i2]);
                boolean z2 = parseInt2 == 0;
                if (this.mPostureDefaultRotationLockSettings.get(parseInt) != 0) {
                    z = false;
                }
                if (z2 != z) {
                    Log.w(
                            "DSRotLockSettingsMngr",
                            "Conflict for ignored device state "
                                    + parseInt
                                    + ". Falling back on defaults");
                    loadDefaults();
                    persistSettings();
                    return;
                }
                this.mPostureRotationLockSettings.put(parseInt, parseInt2);
            } catch (NumberFormatException e) {
                Log.wtf(
                        "DSRotLockSettingsMngr",
                        "Error deserializing one of the saved settings",
                        e);
                loadDefaults();
                persistSettings();
                return;
            }
        }
    }

    public final void loadDefaults() {
        Integer firstOrNull;
        this.mSettableDeviceStates = new ArrayList(this.mPostureRotationLockDefaults.length);
        this.mPostureDefaultRotationLockSettings =
                new SparseIntArray(this.mPostureRotationLockDefaults.length);
        this.mPostureRotationLockSettings =
                new SparseIntArray(this.mPostureRotationLockDefaults.length);
        this.mPostureRotationLockFallbackSettings = new SparseIntArray(1);
        for (String str : this.mPostureRotationLockDefaults) {
            String[] split = str.split(":");
            try {
                int parseInt = Integer.parseInt(split[0]);
                int parseInt2 = Integer.parseInt(split[1]);
                if (parseInt2 == 0) {
                    if (split.length == 3) {
                        this.mPostureRotationLockFallbackSettings.put(
                                parseInt, Integer.parseInt(split[2]));
                    } else {
                        Log.w(
                                "DSRotLockSettingsMngr",
                                "Rotation lock setting is IGNORED, but values have unexpected size"
                                    + " of "
                                        + split.length);
                    }
                }
                boolean z = parseInt2 != 0;
                PosturesHelper posturesHelper = this.mPosturesHelper;
                if (parseInt == 0) {
                    firstOrNull =
                            ArraysKt___ArraysKt.firstOrNull(posturesHelper.foldedDeviceStates);
                } else if (parseInt == 1) {
                    firstOrNull =
                            ArraysKt___ArraysKt.firstOrNull(posturesHelper.halfFoldedDeviceStates);
                } else if (parseInt == 2) {
                    firstOrNull =
                            ArraysKt___ArraysKt.firstOrNull(posturesHelper.unfoldedDeviceStates);
                } else if (parseInt != 3) {
                    posturesHelper.getClass();
                    firstOrNull = null;
                } else {
                    firstOrNull =
                            ArraysKt___ArraysKt.firstOrNull(posturesHelper.rearDisplayDeviceStates);
                }
                if (firstOrNull != null) {
                    this.mSettableDeviceStates.add(
                            new SettableDeviceState(firstOrNull.intValue(), z));
                } else {
                    Log.wtf(
                            "DSRotLockSettingsMngr",
                            "No matching device state for posture: " + parseInt);
                }
                this.mPostureRotationLockSettings.put(parseInt, parseInt2);
                this.mPostureDefaultRotationLockSettings.put(parseInt, parseInt2);
            } catch (NumberFormatException e) {
                Log.wtf(
                        "DSRotLockSettingsMngr",
                        "Error parsing settings entry. Entry was: ".concat(str),
                        e);
                return;
            }
        }
    }

    @VisibleForTesting
    public void onPersistedSettingsChanged() {
        initializeInMemoryMap();
        Iterator it = ((HashSet) this.mListeners).iterator();
        while (it.hasNext()) {
            ((DeviceStateRotationLockSettingsListener) it.next()).onSettingsChanged();
        }
    }

    public final void persistSettingIfChanged(String str) {
        SecureSettings secureSettings = this.mSecureSettings;
        String stringForUser =
                Settings.Secure.getStringForUser(
                        ((AndroidSecureSettings) secureSettings).mContentResolver,
                        "device_state_rotation_lock",
                        -2);
        Log.v(
                "DSRotLockSettingsMngr",
                "persistSettingIfChanged: last=" + stringForUser + ", new=" + str);
        if (TextUtils.equals(stringForUser, str)) {
            return;
        }
        Settings.Secure.putStringForUser(
                ((AndroidSecureSettings) secureSettings).mContentResolver,
                "device_state_rotation_lock",
                str,
                -2);
    }

    public final void persistSettings() {
        if (this.mPostureRotationLockSettings.size() == 0) {
            persistSettingIfChanged(ApnSettings.MVNO_NONE);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.mPostureRotationLockSettings.keyAt(0));
        sb.append(":");
        sb.append(this.mPostureRotationLockSettings.valueAt(0));
        for (int i = 1; i < this.mPostureRotationLockSettings.size(); i++) {
            sb.append(":");
            sb.append(this.mPostureRotationLockSettings.keyAt(i));
            sb.append(":");
            sb.append(this.mPostureRotationLockSettings.valueAt(i));
        }
        persistSettingIfChanged(sb.toString());
    }

    @VisibleForTesting
    public void resetStateForTesting(Resources resources) {
        this.mPostureRotationLockDefaults = resources.getStringArray(17236282);
        loadDefaults();
        persistSettings();
    }
}

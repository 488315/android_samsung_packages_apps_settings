package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeAudioContentMetadata;
import android.bluetooth.BluetoothLeBroadcast;
import android.bluetooth.BluetoothLeBroadcastAssistant;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothLeBroadcastReceiveState;
import android.bluetooth.BluetoothLeBroadcastSubgroup;
import android.bluetooth.BluetoothProfile;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.sec.ims.configuration.DATA;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LocalBluetoothLeBroadcast implements LocalBluetoothProfile {
    public static final Uri[] SETTINGS_URIS = {
        Settings.Secure.getUriFor("bluetooth_le_broadcast_name"),
        Settings.Secure.getUriFor("bluetooth_le_broadcast_program_info"),
        Settings.Secure.getUriFor("bluetooth_le_broadcast_code"),
        Settings.Secure.getUriFor("bluetooth_le_broadcast_app_source_name"),
        Settings.Secure.getUriFor("bluetooth_le_broadcast_improve_compatibility")
    };
    public BluetoothLeAudioContentMetadata mBluetoothLeAudioContentMetadata;
    public BluetoothLeBroadcastMetadata mBluetoothLeBroadcastMetadata;
    public final AnonymousClass3 mBroadcastAssistantCallback;
    public final AnonymousClass2 mBroadcastCallback;
    public byte[] mBroadcastCode;
    public String mBroadcastName;
    public final BluetoothLeAudioContentMetadata.Builder mBuilder;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public final Executor mExecutor;
    public final LocalBluetoothProfileManager mProfileManager;
    public String mProgramInfo;
    public BluetoothLeBroadcast mServiceBroadcast;
    public BluetoothLeBroadcastAssistant mServiceBroadcastAssistant;
    public final AnonymousClass1 mServiceListener;
    public final BroadcastSettingsObserver mSettingsObserver;
    public int mBroadcastId = -1;
    public String mAppSourceName = ApnSettings.MVNO_NONE;
    public String mNewAppSourceName = ApnSettings.MVNO_NONE;
    public boolean mIsBroadcastProfileReady = false;
    public boolean mIsBroadcastAssistantProfileReady = false;
    public boolean mImproveCompatibility = false;
    public final Map mCachedBroadcastCallbackExecutorMap = new ConcurrentHashMap();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast$1, reason: invalid class name */
    public final class AnonymousClass1 implements BluetoothProfile.ServiceListener {
        public AnonymousClass1() {}

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            List allBroadcastMetadata;
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i, "Bluetooth service connected: ", "LocalBluetoothLeBroadcast");
            if (i == 26) {
                LocalBluetoothLeBroadcast localBluetoothLeBroadcast =
                        LocalBluetoothLeBroadcast.this;
                if (!localBluetoothLeBroadcast.mIsBroadcastProfileReady) {
                    localBluetoothLeBroadcast.mServiceBroadcast =
                            (BluetoothLeBroadcast) bluetoothProfile;
                    localBluetoothLeBroadcast.mIsBroadcastProfileReady = true;
                    localBluetoothLeBroadcast.registerServiceCallBack(
                            localBluetoothLeBroadcast.mExecutor,
                            localBluetoothLeBroadcast.mBroadcastCallback);
                    BluetoothLeBroadcast bluetoothLeBroadcast =
                            LocalBluetoothLeBroadcast.this.mServiceBroadcast;
                    if (bluetoothLeBroadcast == null) {
                        Log.d("LocalBluetoothLeBroadcast", "The BluetoothLeBroadcast is null.");
                        allBroadcastMetadata = Collections.emptyList();
                    } else {
                        allBroadcastMetadata = bluetoothLeBroadcast.getAllBroadcastMetadata();
                    }
                    if (!allBroadcastMetadata.isEmpty()) {
                        LocalBluetoothLeBroadcast.this.updateBroadcastInfoFromBroadcastMetadata(
                                (BluetoothLeBroadcastMetadata) allBroadcastMetadata.get(0));
                    }
                    LocalBluetoothLeBroadcast localBluetoothLeBroadcast2 =
                            LocalBluetoothLeBroadcast.this;
                    if (localBluetoothLeBroadcast2.mContentResolver == null) {
                        Log.d("LocalBluetoothLeBroadcast", "mContentResolver is null");
                    } else {
                        for (Uri uri : LocalBluetoothLeBroadcast.SETTINGS_URIS) {
                            localBluetoothLeBroadcast2.mContentResolver.registerContentObserver(
                                    uri, false, localBluetoothLeBroadcast2.mSettingsObserver);
                        }
                    }
                    Log.d(
                            "LocalBluetoothLeBroadcast",
                            "onServiceConnected: register mCachedBroadcastCallbackExecutorMap = "
                                    + LocalBluetoothLeBroadcast.this
                                            .mCachedBroadcastCallbackExecutorMap);
                    ((ConcurrentHashMap)
                                    LocalBluetoothLeBroadcast.this
                                            .mCachedBroadcastCallbackExecutorMap)
                            .forEach(
                                    new BiConsumer() { // from class:
                                                       // com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast$1$$ExternalSyntheticLambda0
                                        @Override // java.util.function.BiConsumer
                                        public final void accept(Object obj, Object obj2) {
                                            LocalBluetoothLeBroadcast localBluetoothLeBroadcast3 =
                                                    LocalBluetoothLeBroadcast.this;
                                            localBluetoothLeBroadcast3.registerServiceCallBack(
                                                    (Executor) obj2,
                                                    (BluetoothLeBroadcast.Callback) obj);
                                        }
                                    });
                    LocalBluetoothLeBroadcast.this.mProfileManager.callServiceConnectedListeners();
                    return;
                }
            }
            if (i == 29) {
                LocalBluetoothLeBroadcast localBluetoothLeBroadcast3 =
                        LocalBluetoothLeBroadcast.this;
                if (localBluetoothLeBroadcast3.mIsBroadcastAssistantProfileReady) {
                    return;
                }
                localBluetoothLeBroadcast3.mIsBroadcastAssistantProfileReady = true;
                BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant =
                        (BluetoothLeBroadcastAssistant) bluetoothProfile;
                localBluetoothLeBroadcast3.mServiceBroadcastAssistant =
                        bluetoothLeBroadcastAssistant;
                Executor executor = localBluetoothLeBroadcast3.mExecutor;
                AnonymousClass3 anonymousClass3 =
                        localBluetoothLeBroadcast3.mBroadcastAssistantCallback;
                if (bluetoothLeBroadcastAssistant == null) {
                    Log.d(
                            "LocalBluetoothLeBroadcast",
                            "registerBroadcastAssistantCallback failed, the"
                                + " BluetoothLeBroadcastAssistant is null.");
                    return;
                }
                try {
                    bluetoothLeBroadcastAssistant.registerCallback(executor, anonymousClass3);
                } catch (IllegalArgumentException e) {
                    Log.w(
                            "LocalBluetoothLeBroadcast",
                            "registerBroadcastAssistantCallback failed. " + e.getMessage());
                }
            }
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i, "Bluetooth service disconnected: ", "LocalBluetoothLeBroadcast");
            if (i == 26) {
                LocalBluetoothLeBroadcast localBluetoothLeBroadcast =
                        LocalBluetoothLeBroadcast.this;
                if (localBluetoothLeBroadcast.mIsBroadcastProfileReady) {
                    localBluetoothLeBroadcast.mProfileManager.callServiceDisconnectedListeners();
                    LocalBluetoothLeBroadcast localBluetoothLeBroadcast2 =
                            LocalBluetoothLeBroadcast.this;
                    localBluetoothLeBroadcast2.mIsBroadcastProfileReady = false;
                    LocalBluetoothLeBroadcast.m1030$$Nest$mnotifyBroadcastStateChange(
                            localBluetoothLeBroadcast2, 2);
                    LocalBluetoothLeBroadcast localBluetoothLeBroadcast3 =
                            LocalBluetoothLeBroadcast.this;
                    localBluetoothLeBroadcast3.unregisterServiceCallBack(
                            localBluetoothLeBroadcast3.mBroadcastCallback);
                    ((ConcurrentHashMap)
                                    LocalBluetoothLeBroadcast.this
                                            .mCachedBroadcastCallbackExecutorMap)
                            .clear();
                }
            }
            if (i == 29) {
                LocalBluetoothLeBroadcast localBluetoothLeBroadcast4 =
                        LocalBluetoothLeBroadcast.this;
                if (localBluetoothLeBroadcast4.mIsBroadcastAssistantProfileReady) {
                    localBluetoothLeBroadcast4.mIsBroadcastAssistantProfileReady = false;
                    AnonymousClass3 anonymousClass3 =
                            localBluetoothLeBroadcast4.mBroadcastAssistantCallback;
                    BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant =
                            localBluetoothLeBroadcast4.mServiceBroadcastAssistant;
                    if (bluetoothLeBroadcastAssistant == null) {
                        Log.d(
                                "LocalBluetoothLeBroadcast",
                                "unregisterBroadcastAssistantCallback, the"
                                    + " BluetoothLeBroadcastAssistant is null.");
                    } else {
                        try {
                            bluetoothLeBroadcastAssistant.unregisterCallback(anonymousClass3);
                        } catch (IllegalArgumentException e) {
                            Log.w(
                                    "LocalBluetoothLeBroadcast",
                                    "unregisterBroadcastAssistantCallback failed. "
                                            + e.getMessage());
                        }
                    }
                }
            }
            LocalBluetoothLeBroadcast localBluetoothLeBroadcast5 = LocalBluetoothLeBroadcast.this;
            if (localBluetoothLeBroadcast5.mIsBroadcastAssistantProfileReady
                    || localBluetoothLeBroadcast5.mIsBroadcastProfileReady) {
                return;
            }
            ContentResolver contentResolver = localBluetoothLeBroadcast5.mContentResolver;
            if (contentResolver == null) {
                Log.d("LocalBluetoothLeBroadcast", "mContentResolver is null");
            } else {
                contentResolver.unregisterContentObserver(
                        localBluetoothLeBroadcast5.mSettingsObserver);
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class BroadcastSettingsObserver extends ContentObserver {
        public BroadcastSettingsObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            Log.d("LocalBluetoothLeBroadcast", "BroadcastSettingsObserver: onChange");
            LocalBluetoothLeBroadcast.this.updateBroadcastInfoFromContentProvider();
        }
    }

    /* renamed from: -$$Nest$mnotifyBroadcastStateChange, reason: not valid java name */
    public static void m1030$$Nest$mnotifyBroadcastStateChange(
            LocalBluetoothLeBroadcast localBluetoothLeBroadcast, int i) {
        if (!localBluetoothLeBroadcast
                .mContext
                .getPackageName()
                .equals(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG)) {
            Log.d(
                    "LocalBluetoothLeBroadcast",
                    "Skip notifyBroadcastStateChange, not triggered by Settings.");
            return;
        }
        UserManager userManager =
                (UserManager)
                        localBluetoothLeBroadcast.mContext.getSystemService(UserManager.class);
        if (userManager != null && userManager.isManagedProfile()) {
            Log.d(
                    "LocalBluetoothLeBroadcast",
                    "Skip notifyBroadcastStateChange, not triggered for work profile.");
            return;
        }
        Intent intent =
                new Intent("com.android.settings.action.BLUETOOTH_LE_AUDIO_SHARING_STATE_CHANGE");
        intent.putExtra("BLUETOOTH_LE_AUDIO_SHARING_STATE", i);
        intent.setPackage(localBluetoothLeBroadcast.mContext.getPackageName());
        Log.e("LocalBluetoothLeBroadcast", "notifyBroadcastStateChange for state = " + i);
        localBluetoothLeBroadcast.mContext.sendBroadcast(intent);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast$2] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast$3] */
    public LocalBluetoothLeBroadcast(
            Context context,
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager,
            LocalBluetoothProfileManager localBluetoothProfileManager) {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mBroadcastCallback =
                new BluetoothLeBroadcast
                        .Callback() { // from class:
                                      // com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast.2
                    public final void onBroadcastMetadataChanged(
                            int i, BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                                i,
                                "onBroadcastMetadataChanged(), broadcastId = ",
                                "LocalBluetoothLeBroadcast");
                        LocalBluetoothLeBroadcast localBluetoothLeBroadcast =
                                LocalBluetoothLeBroadcast.this;
                        localBluetoothLeBroadcast.getClass();
                        if (bluetoothLeBroadcastMetadata != null
                                && bluetoothLeBroadcastMetadata.getBroadcastId()
                                        == localBluetoothLeBroadcast.mBroadcastId) {
                            localBluetoothLeBroadcast.mBluetoothLeBroadcastMetadata =
                                    bluetoothLeBroadcastMetadata;
                            localBluetoothLeBroadcast.updateBroadcastInfoFromBroadcastMetadata(
                                    bluetoothLeBroadcastMetadata);
                        }
                        LocalBluetoothLeBroadcast.m1030$$Nest$mnotifyBroadcastStateChange(
                                LocalBluetoothLeBroadcast.this, 1);
                    }

                    public final void onBroadcastStartFailed(int i) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                                i,
                                "onBroadcastStartFailed(), reason = ",
                                "LocalBluetoothLeBroadcast");
                    }

                    public final void onBroadcastStarted(int i, int i2) {
                        MainClearConfirm$$ExternalSyntheticOutline0.m(
                                "onBroadcastStarted(), reason = ",
                                ", broadcastId = ",
                                i,
                                i2,
                                "LocalBluetoothLeBroadcast");
                        LocalBluetoothLeBroadcast.this.setLatestBroadcastId(i2);
                        LocalBluetoothLeBroadcast localBluetoothLeBroadcast =
                                LocalBluetoothLeBroadcast.this;
                        localBluetoothLeBroadcast.setAppSourceName(
                                localBluetoothLeBroadcast.mNewAppSourceName, true);
                    }

                    public final void onBroadcastStopFailed(int i) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                                i,
                                "onBroadcastStopFailed(), reason = ",
                                "LocalBluetoothLeBroadcast");
                    }

                    public final void onBroadcastStopped(int i, int i2) {
                        MainClearConfirm$$ExternalSyntheticOutline0.m(
                                "onBroadcastStopped(), reason = ",
                                ", broadcastId = ",
                                i,
                                i2,
                                "LocalBluetoothLeBroadcast");
                        LocalBluetoothLeBroadcast.m1030$$Nest$mnotifyBroadcastStateChange(
                                LocalBluetoothLeBroadcast.this, 2);
                        LocalBluetoothLeBroadcast localBluetoothLeBroadcast =
                                LocalBluetoothLeBroadcast.this;
                        localBluetoothLeBroadcast.getClass();
                        Log.d("LocalBluetoothLeBroadcast", "resetCacheInfo:");
                        localBluetoothLeBroadcast.setAppSourceName(ApnSettings.MVNO_NONE, true);
                        localBluetoothLeBroadcast.mBluetoothLeBroadcastMetadata = null;
                        localBluetoothLeBroadcast.mBroadcastId = -1;
                    }

                    public final void onBroadcastUpdateFailed(int i, int i2) {
                        MainClearConfirm$$ExternalSyntheticOutline0.m(
                                "onBroadcastUpdateFailed(), reason = ",
                                ", broadcastId = ",
                                i,
                                i2,
                                "LocalBluetoothLeBroadcast");
                    }

                    public final void onBroadcastUpdated(int i, int i2) {
                        MainClearConfirm$$ExternalSyntheticOutline0.m(
                                "onBroadcastUpdated(), reason = ",
                                ", broadcastId = ",
                                i,
                                i2,
                                "LocalBluetoothLeBroadcast");
                        LocalBluetoothLeBroadcast.this.setLatestBroadcastId(i2);
                        LocalBluetoothLeBroadcast localBluetoothLeBroadcast =
                                LocalBluetoothLeBroadcast.this;
                        localBluetoothLeBroadcast.setAppSourceName(
                                localBluetoothLeBroadcast.mNewAppSourceName, true);
                    }

                    public final void onPlaybackStarted(int i, int i2) {}

                    public final void onPlaybackStopped(int i, int i2) {}
                };
        this.mBroadcastAssistantCallback =
                new BluetoothLeBroadcastAssistant
                        .Callback() { // from class:
                                      // com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast.3
                    public final void onReceiveStateChanged(
                            BluetoothDevice bluetoothDevice,
                            int i,
                            BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
                        Log.d(
                                "LocalBluetoothLeBroadcast",
                                "onReceiveStateChanged(), sink = "
                                        + bluetoothDevice
                                        + ", sourceId = "
                                        + i
                                        + ", state = "
                                        + bluetoothLeBroadcastReceiveState);
                    }

                    public final void onSourceAddFailed(
                            BluetoothDevice bluetoothDevice,
                            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata,
                            int i) {
                        Log.d(
                                "LocalBluetoothLeBroadcast",
                                "onSourceAddFailed(), sink = "
                                        + bluetoothDevice
                                        + ", reason = "
                                        + i
                                        + ", source = "
                                        + bluetoothLeBroadcastMetadata);
                    }

                    public final void onSourceAdded(
                            BluetoothDevice bluetoothDevice, int i, int i2) {
                        StringBuilder sb = new StringBuilder("onSourceAdded(), sink = ");
                        sb.append(bluetoothDevice);
                        sb.append(", reason = ");
                        sb.append(i2);
                        sb.append(", sourceId = ");
                        Preference$$ExternalSyntheticOutline0.m(sb, i, "LocalBluetoothLeBroadcast");
                    }

                    public final void onSourceRemoveFailed(
                            BluetoothDevice bluetoothDevice, int i, int i2) {
                        StringBuilder sb = new StringBuilder("onSourceRemoveFailed(), sink = ");
                        sb.append(bluetoothDevice);
                        sb.append(", reason = ");
                        sb.append(i2);
                        sb.append(", sourceId = ");
                        Preference$$ExternalSyntheticOutline0.m(sb, i, "LocalBluetoothLeBroadcast");
                    }

                    public final void onSourceRemoved(
                            BluetoothDevice bluetoothDevice, int i, int i2) {
                        StringBuilder sb = new StringBuilder("onSourceRemoved(), sink = ");
                        sb.append(bluetoothDevice);
                        sb.append(", reason = ");
                        sb.append(i2);
                        sb.append(", sourceId = ");
                        Preference$$ExternalSyntheticOutline0.m(sb, i, "LocalBluetoothLeBroadcast");
                    }

                    public final void onSearchStartFailed(int i) {}

                    public final void onSearchStarted(int i) {}

                    public final void onSearchStopFailed(int i) {}

                    public final void onSearchStopped(int i) {}

                    public final void onSourceFound(
                            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {}

                    public final void onSourceModified(
                            BluetoothDevice bluetoothDevice, int i, int i2) {}

                    public final void onSourceModifyFailed(
                            BluetoothDevice bluetoothDevice, int i, int i2) {}
                };
        this.mProfileManager = localBluetoothProfileManager;
        this.mContext = context;
        this.mExecutor = Executors.newSingleThreadExecutor();
        this.mBuilder = new BluetoothLeAudioContentMetadata.Builder();
        this.mContentResolver = context.getContentResolver();
        this.mSettingsObserver = new BroadcastSettingsObserver(new Handler(Looper.getMainLooper()));
        updateBroadcastInfoFromContentProvider();
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, anonymousClass1, 26);
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, anonymousClass1, 29);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return false;
    }

    public final void finalize() {
        Log.d("LocalBluetoothLeBroadcast", "finalize()");
        if (this.mServiceBroadcast != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(26, this.mServiceBroadcast);
                this.mServiceBroadcast = null;
            } catch (Throwable th) {
                Log.w("LocalBluetoothLeBroadcast", "Error cleaning up LeAudio proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothLeBroadcast bluetoothLeBroadcast = this.mServiceBroadcast;
        if (bluetoothLeBroadcast == null) {
            return 0;
        }
        return bluetoothLeBroadcast.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return 0;
    }

    public final BluetoothLeBroadcastMetadata getLatestBluetoothLeBroadcastMetadata() {
        BluetoothLeBroadcast bluetoothLeBroadcast = this.mServiceBroadcast;
        if (bluetoothLeBroadcast == null) {
            Log.d("LocalBluetoothLeBroadcast", "The BluetoothLeBroadcast is null");
            return null;
        }
        if (this.mBluetoothLeBroadcastMetadata == null) {
            this.mBluetoothLeBroadcastMetadata =
                    (BluetoothLeBroadcastMetadata)
                            bluetoothLeBroadcast.getAllBroadcastMetadata().stream()
                                    .filter(
                                            new Predicate() { // from class:
                                                              // com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast$$ExternalSyntheticLambda0
                                                @Override // java.util.function.Predicate
                                                public final boolean test(Object obj) {
                                                    LocalBluetoothLeBroadcast
                                                            localBluetoothLeBroadcast =
                                                                    LocalBluetoothLeBroadcast.this;
                                                    localBluetoothLeBroadcast.getClass();
                                                    return ((BluetoothLeBroadcastMetadata) obj)
                                                                    .getBroadcastId()
                                                            == localBluetoothLeBroadcast
                                                                    .mBroadcastId;
                                                }
                                            })
                                    .findFirst()
                                    .orElse(null);
        }
        return this.mBluetoothLeBroadcastMetadata;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getNameResource(BluetoothDevice bluetoothDevice) {
        return R.string.summary_empty;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getOrdinal() {
        return 1;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 26;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isEnabled(BluetoothDevice bluetoothDevice) {
        if (this.mServiceBroadcast == null) {
            return false;
        }
        return !r0.getAllBroadcastMetadata().isEmpty();
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return this.mIsBroadcastProfileReady;
    }

    public final void registerServiceCallBack(
            Executor executor, BluetoothLeBroadcast.Callback callback) {
        BluetoothLeBroadcast bluetoothLeBroadcast = this.mServiceBroadcast;
        if (bluetoothLeBroadcast == null) {
            Log.d(
                    "LocalBluetoothLeBroadcast",
                    "registerServiceCallBack failed, the BluetoothLeBroadcast is null.");
            ((ConcurrentHashMap) this.mCachedBroadcastCallbackExecutorMap)
                    .putIfAbsent(callback, executor);
            return;
        }
        try {
            bluetoothLeBroadcast.registerCallback(executor, callback);
        } catch (IllegalArgumentException e) {
            Log.w("LocalBluetoothLeBroadcast", "registerServiceCallBack failed. " + e.getMessage());
        }
    }

    public final void setAppSourceName(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            str = ApnSettings.MVNO_NONE;
        }
        String str2 = this.mAppSourceName;
        if (str2 != null && TextUtils.equals(str2, str)) {
            Log.d("LocalBluetoothLeBroadcast", "setAppSourceName: appSourceName is not changed");
            return;
        }
        this.mAppSourceName = str;
        this.mNewAppSourceName = ApnSettings.MVNO_NONE;
        if (z) {
            ContentResolver contentResolver = this.mContentResolver;
            if (contentResolver == null) {
                Log.d("LocalBluetoothLeBroadcast", "mContentResolver is null");
            } else {
                Settings.Secure.putString(
                        contentResolver, "bluetooth_le_broadcast_app_source_name", str);
            }
        }
    }

    public final void setBroadcastCode(byte[] bArr, boolean z) {
        if (bArr == null) {
            Log.d("LocalBluetoothLeBroadcast", "setBroadcastCode: broadcastCode is null");
            return;
        }
        byte[] bArr2 = this.mBroadcastCode;
        if (bArr2 != null && Arrays.equals(bArr, bArr2)) {
            Log.d("LocalBluetoothLeBroadcast", "setBroadcastCode: broadcastCode is not changed");
            return;
        }
        this.mBroadcastCode = bArr;
        if (z) {
            ContentResolver contentResolver = this.mContentResolver;
            if (contentResolver == null) {
                Log.d("LocalBluetoothLeBroadcast", "mContentResolver is null");
            } else {
                Settings.Secure.putString(
                        contentResolver,
                        "bluetooth_le_broadcast_code",
                        new String(bArr, StandardCharsets.UTF_8)
                                .replaceAll("\u0000", ApnSettings.MVNO_NONE));
            }
        }
    }

    public final void setBroadcastName(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            Log.d("LocalBluetoothLeBroadcast", "setBroadcastName: broadcastName is null or empty");
            return;
        }
        String str2 = this.mBroadcastName;
        if (str2 != null && TextUtils.equals(str2, str)) {
            Log.d("LocalBluetoothLeBroadcast", "setBroadcastName: broadcastName is not changed");
            return;
        }
        DialogFragment$$ExternalSyntheticOutline0.m(
                "setBroadcastName: ", str, "LocalBluetoothLeBroadcast");
        this.mBroadcastName = str;
        if (z) {
            ContentResolver contentResolver = this.mContentResolver;
            if (contentResolver == null) {
                Log.d("LocalBluetoothLeBroadcast", "mContentResolver is null");
            } else {
                Settings.Secure.putString(contentResolver, "bluetooth_le_broadcast_name", str);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        return false;
    }

    public final void setImproveCompatibility(boolean z, boolean z2) {
        if (this.mImproveCompatibility == z) {
            Log.d(
                    "LocalBluetoothLeBroadcast",
                    "setImproveCompatibility: improveCompatibility is not changed");
            return;
        }
        this.mImproveCompatibility = z;
        if (z2) {
            if (this.mContentResolver == null) {
                Log.d("LocalBluetoothLeBroadcast", "mContentResolver is null");
            } else {
                AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                        "Set improveCompatibility to: ", "LocalBluetoothLeBroadcast", z);
                Settings.Secure.putString(
                        this.mContentResolver,
                        "bluetooth_le_broadcast_improve_compatibility",
                        z ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
            }
        }
    }

    public final void setLatestBroadcastId(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "setLatestBroadcastId: mBroadcastId is ", "LocalBluetoothLeBroadcast");
        this.mBroadcastId = i;
    }

    public final void setProgramInfo(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            Log.d("LocalBluetoothLeBroadcast", "setProgramInfo: programInfo is null or empty");
            return;
        }
        String str2 = this.mProgramInfo;
        if (str2 != null && TextUtils.equals(str2, str)) {
            Log.d("LocalBluetoothLeBroadcast", "setProgramInfo: programInfo is not changed");
            return;
        }
        DialogFragment$$ExternalSyntheticOutline0.m(
                "setProgramInfo: ", str, "LocalBluetoothLeBroadcast");
        this.mProgramInfo = str;
        if (z) {
            ContentResolver contentResolver = this.mContentResolver;
            if (contentResolver == null) {
                Log.d("LocalBluetoothLeBroadcast", "mContentResolver is null");
            } else {
                Settings.Secure.putString(
                        contentResolver, "bluetooth_le_broadcast_program_info", str);
            }
        }
    }

    public final void stopBroadcast(int i) {
        if (this.mServiceBroadcast == null) {
            Log.d(
                    "LocalBluetoothLeBroadcast",
                    "The BluetoothLeBroadcast is null when stopping the broadcast.");
        } else {
            Log.d("LocalBluetoothLeBroadcast", "stopBroadcast()");
            this.mServiceBroadcast.stopBroadcast(i);
        }
    }

    public final String toString() {
        return "LE_AUDIO_BROADCAST";
    }

    public final void unregisterServiceCallBack(BluetoothLeBroadcast.Callback callback) {
        ((ConcurrentHashMap) this.mCachedBroadcastCallbackExecutorMap).remove(callback);
        BluetoothLeBroadcast bluetoothLeBroadcast = this.mServiceBroadcast;
        if (bluetoothLeBroadcast == null) {
            Log.d(
                    "LocalBluetoothLeBroadcast",
                    "unregisterServiceCallBack failed, the BluetoothLeBroadcast is null.");
            return;
        }
        try {
            bluetoothLeBroadcast.unregisterCallback(callback);
        } catch (IllegalArgumentException e) {
            Log.w(
                    "LocalBluetoothLeBroadcast",
                    "unregisterServiceCallBack failed. " + e.getMessage());
        }
    }

    public final void updateBroadcastInfoFromBroadcastMetadata(
            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
        if (bluetoothLeBroadcastMetadata == null) {
            Log.d("LocalBluetoothLeBroadcast", "The bluetoothLeBroadcastMetadata is null");
            return;
        }
        setBroadcastName(bluetoothLeBroadcastMetadata.getBroadcastName(), true);
        setBroadcastCode(bluetoothLeBroadcastMetadata.getBroadcastCode(), true);
        setLatestBroadcastId(bluetoothLeBroadcastMetadata.getBroadcastId());
        List subgroups = bluetoothLeBroadcastMetadata.getSubgroups();
        if (subgroups == null || subgroups.size() < 1) {
            Log.d("LocalBluetoothLeBroadcast", "The subgroup is not valid value");
        } else {
            setProgramInfo(
                    ((BluetoothLeBroadcastSubgroup) subgroups.get(0))
                            .getContentMetadata()
                            .getProgramInfo(),
                    true);
            setAppSourceName(this.mAppSourceName, true);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00cc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateBroadcastInfoFromContentProvider() {
        /*
            r7 = this;
            android.content.ContentResolver r0 = r7.mContentResolver
            java.lang.String r1 = "LocalBluetoothLeBroadcast"
            if (r0 != 0) goto Lc
            java.lang.String r7 = "updateBroadcastInfoFromContentProvider: mContentResolver is null"
            android.util.Log.d(r1, r7)
            return
        Lc:
            java.lang.String r2 = "bluetooth_le_broadcast_program_info"
            java.lang.String r0 = android.provider.Settings.Secure.getString(r0, r2)
            java.lang.String r2 = "_"
            r3 = 9999(0x270f, float:1.4012E-41)
            r4 = 1000(0x3e8, float:1.401E-42)
            if (r0 != 0) goto L3c
            java.util.concurrent.ThreadLocalRandom r0 = java.util.concurrent.ThreadLocalRandom.current()
            int r0 = r0.nextInt(r4, r3)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            android.bluetooth.BluetoothAdapter r6 = android.bluetooth.BluetoothAdapter.getDefaultAdapter()
            java.lang.String r6 = r6.getName()
            r5.append(r6)
            r5.append(r2)
            r5.append(r0)
            java.lang.String r0 = r5.toString()
        L3c:
            r5 = 0
            r7.setProgramInfo(r0, r5)
            android.content.ContentResolver r0 = r7.mContentResolver
            java.lang.String r6 = "bluetooth_le_broadcast_name"
            java.lang.String r0 = android.provider.Settings.Secure.getString(r0, r6)
            if (r0 != 0) goto L6c
            java.util.concurrent.ThreadLocalRandom r0 = java.util.concurrent.ThreadLocalRandom.current()
            int r0 = r0.nextInt(r4, r3)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            android.bluetooth.BluetoothAdapter r4 = android.bluetooth.BluetoothAdapter.getDefaultAdapter()
            java.lang.String r4 = r4.getName()
            r3.append(r4)
            r3.append(r2)
            r3.append(r0)
            java.lang.String r0 = r3.toString()
        L6c:
            r7.setBroadcastName(r0, r5)
            android.content.ContentResolver r0 = r7.mContentResolver
            java.lang.String r2 = "bluetooth_le_broadcast_code"
            java.lang.String r0 = android.provider.Settings.Secure.getString(r0, r2)
            r2 = 4
            if (r0 == 0) goto L93
            java.nio.charset.Charset r3 = java.nio.charset.StandardCharsets.UTF_8
            byte[] r4 = r0.getBytes(r3)
            int r4 = r4.length
            if (r4 < r2) goto L8c
            byte[] r3 = r0.getBytes(r3)
            int r3 = r3.length
            r4 = 16
            if (r3 <= r4) goto L93
        L8c:
            java.lang.String r3 = "updateBroadcastInfoFromContentProvider: wrong pref broadcast code"
            android.util.Log.e(r1, r3)
            r1 = 1
            goto L94
        L93:
            r1 = r5
        L94:
            if (r0 == 0) goto La0
            if (r1 == 0) goto L99
            goto La0
        L99:
            java.nio.charset.Charset r2 = java.nio.charset.StandardCharsets.UTF_8
            byte[] r0 = r0.getBytes(r2)
            goto Lb2
        La0:
            java.util.UUID r0 = java.util.UUID.randomUUID()
            java.lang.String r0 = r0.toString()
            java.lang.String r0 = r0.substring(r5, r2)
            java.nio.charset.Charset r2 = java.nio.charset.StandardCharsets.UTF_8
            byte[] r0 = r0.getBytes(r2)
        Lb2:
            r7.setBroadcastCode(r0, r1)
            android.content.ContentResolver r0 = r7.mContentResolver
            java.lang.String r1 = "bluetooth_le_broadcast_app_source_name"
            java.lang.String r0 = android.provider.Settings.Secure.getString(r0, r1)
            r7.setAppSourceName(r0, r5)
            android.content.ContentResolver r0 = r7.mContentResolver
            java.lang.String r1 = "bluetooth_le_broadcast_improve_compatibility"
            java.lang.String r0 = android.provider.Settings.Secure.getString(r0, r1)
            if (r0 != 0) goto Lcc
            r0 = r5
            goto Ld2
        Lcc:
            java.lang.String r1 = "1"
            boolean r0 = r0.equals(r1)
        Ld2:
            r7.setImproveCompatibility(r0, r5)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast.updateBroadcastInfoFromContentProvider():void");
    }
}

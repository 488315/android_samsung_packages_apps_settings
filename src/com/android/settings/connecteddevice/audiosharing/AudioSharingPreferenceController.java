package com.android.settings.connecteddevice.audiosharing;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothLeBroadcast;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.bluetooth.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.BluetoothEventManager;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioSharingPreferenceController extends BasePreferenceController
        implements DefaultLifecycleObserver, BluetoothCallback {
    private static final String TAG = "AudioSharingPreferenceController";
    private final LocalBluetoothLeBroadcast mBroadcast;
    final BluetoothLeBroadcast.Callback mBroadcastCallback;
    private final LocalBluetoothManager mBtManager;
    private final BluetoothEventManager mEventManager;
    private final Executor mExecutor;
    private Preference mPreference;

    public AudioSharingPreferenceController(Context context, String str) {
        super(context, str);
        this.mBroadcastCallback =
                new BluetoothLeBroadcast
                        .Callback() { // from class:
                                      // com.android.settings.connecteddevice.audiosharing.AudioSharingPreferenceController.1
                    public final void onBroadcastStarted(int i, int i2) {
                        AudioSharingPreferenceController.this.refreshSummary();
                    }

                    public final void onBroadcastStopped(int i, int i2) {
                        AudioSharingPreferenceController.this.refreshSummary();
                    }

                    public final void onBroadcastStartFailed(int i) {}

                    public final void onBroadcastStopFailed(int i) {}

                    public final void onBroadcastMetadataChanged(
                            int i, BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {}

                    public final void onBroadcastUpdateFailed(int i, int i2) {}

                    public final void onBroadcastUpdated(int i, int i2) {}

                    public final void onPlaybackStarted(int i, int i2) {}

                    public final void onPlaybackStopped(int i, int i2) {}
                };
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(context, Utils.mOnInitCallback);
        this.mBtManager = localBluetoothManager;
        this.mEventManager =
                localBluetoothManager == null ? null : localBluetoothManager.mEventManager;
        this.mBroadcast =
                localBluetoothManager != null
                        ? localBluetoothManager.mProfileManager.mLeAudioBroadcast
                        : null;
        this.mExecutor = Executors.newSingleThreadExecutor();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshSummary$0(CharSequence charSequence) {
        Preference preference = this.mPreference;
        if (preference != null) {
            preference.setSummary(charSequence);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshSummary$1() {
        final CharSequence summary = getSummary();
        AudioSharingUtils.postOnMainThread(
                this.mContext,
                new Runnable() { // from class:
                                 // com.android.settings.connecteddevice.audiosharing.AudioSharingPreferenceController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        AudioSharingPreferenceController.this.lambda$refreshSummary$0(summary);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshSummary() {
        if (this.mPreference == null) {
            return;
        }
        ThreadUtils.postOnBackgroundThread(
                new Runnable() { // from class:
                                 // com.android.settings.connecteddevice.audiosharing.AudioSharingPreferenceController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AudioSharingPreferenceController.this.lambda$refreshSummary$1();
                    }
                });
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        BluetoothAdapter.getDefaultAdapter();
        return 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return AudioSharingUtils.isBroadcasting(this.mBtManager)
                ? this.mContext.getString(R.string.audio_sharing_summary_on)
                : this.mContext.getString(R.string.audio_sharing_summary_off);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public void onBluetoothStateChanged(int i) {
        refreshSummary();
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onCreate(LifecycleOwner lifecycleOwner) {
        super.onCreate(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onDestroy(LifecycleOwner lifecycleOwner) {
        super.onDestroy(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onPause(LifecycleOwner lifecycleOwner) {
        super.onPause(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onResume(LifecycleOwner lifecycleOwner) {
        super.onResume(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStart(LifecycleOwner lifecycleOwner) {
        if (!isAvailable()) {
            Log.d(TAG, "Skip register callbacks, feature not support");
            return;
        }
        BluetoothEventManager bluetoothEventManager = this.mEventManager;
        if (bluetoothEventManager == null || this.mBroadcast == null) {
            Log.d(TAG, "Skip register callbacks, profile not ready");
        } else {
            bluetoothEventManager.registerCallback(this);
            this.mBroadcast.registerServiceCallBack(this.mExecutor, this.mBroadcastCallback);
        }
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStop(LifecycleOwner lifecycleOwner) {
        if (!isAvailable()) {
            Log.d(TAG, "Skip unregister callbacks, feature not support");
            return;
        }
        BluetoothEventManager bluetoothEventManager = this.mEventManager;
        if (bluetoothEventManager == null || this.mBroadcast == null) {
            Log.d(TAG, "Skip register callbacks, profile not ready");
        } else {
            bluetoothEventManager.unregisterCallback(this);
            this.mBroadcast.unregisterServiceCallBack(this.mBroadcastCallback);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onAudioModeChanged() {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onAutoOnStateChanged(int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onDeviceAdded(
            CachedBluetoothDevice cachedBluetoothDevice) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onDeviceDeleted(
            CachedBluetoothDevice cachedBluetoothDevice) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onScanningStateChanged(boolean z) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onAclConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onActiveDeviceChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onDeviceBondStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onProfileConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {}
}

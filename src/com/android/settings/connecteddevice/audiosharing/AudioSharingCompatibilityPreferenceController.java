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
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.bluetooth.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioSharingCompatibilityPreferenceController extends TogglePreferenceController
        implements DefaultLifecycleObserver, LocalBluetoothProfileManager.ServiceListener {
    private static final String PREF_KEY = "audio_sharing_stream_compatibility";
    private static final String TAG = "AudioSharingCompatibilityPrefController";
    private final LocalBluetoothLeBroadcast mBroadcast;
    final BluetoothLeBroadcast.Callback mBroadcastCallback;
    private final LocalBluetoothManager mBtManager;
    private final AtomicBoolean mCallbacksRegistered;
    private final Executor mExecutor;
    private final MetricsFeatureProvider mMetricsFeatureProvider;
    private TwoStatePreference mPreference;
    private final LocalBluetoothProfileManager mProfileManager;

    public AudioSharingCompatibilityPreferenceController(Context context, String str) {
        super(context, str);
        this.mCallbacksRegistered = new AtomicBoolean(false);
        this.mBroadcastCallback =
                new BluetoothLeBroadcast
                        .Callback() { // from class:
                                      // com.android.settings.connecteddevice.audiosharing.AudioSharingCompatibilityPreferenceController.1
                    public final void onBroadcastStarted(int i, int i2) {
                        MainClearConfirm$$ExternalSyntheticOutline0.m(
                                "onBroadcastStarted(), reason = ",
                                ", broadcastId = ",
                                i,
                                i2,
                                AudioSharingCompatibilityPreferenceController.TAG);
                        AudioSharingCompatibilityPreferenceController.this.updateEnabled();
                    }

                    public final void onBroadcastStopped(int i, int i2) {
                        MainClearConfirm$$ExternalSyntheticOutline0.m(
                                "onBroadcastStopped(), reason = ",
                                ", broadcastId = ",
                                i,
                                i2,
                                AudioSharingCompatibilityPreferenceController.TAG);
                        AudioSharingCompatibilityPreferenceController.this.updateEnabled();
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
        LocalBluetoothProfileManager localBluetoothProfileManager =
                localBluetoothManager == null ? null : localBluetoothManager.mProfileManager;
        this.mProfileManager = localBluetoothProfileManager;
        this.mBroadcast =
                localBluetoothProfileManager != null
                        ? localBluetoothProfileManager.mLeAudioBroadcast
                        : null;
        this.mExecutor = Executors.newSingleThreadExecutor();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onServiceConnected$0() {
        TwoStatePreference twoStatePreference = this.mPreference;
        if (twoStatePreference != null) {
            updateState(twoStatePreference);
        }
        updateEnabled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateEnabled$1(boolean z, int i, int i2) {
        TwoStatePreference twoStatePreference = this.mPreference;
        if (twoStatePreference != null) {
            twoStatePreference.setEnabled(!z);
            this.mPreference.setSummary(
                    z ? this.mContext.getString(i) : this.mContext.getString(i2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateEnabled$2(final int i, final int i2) {
        final boolean isBroadcasting = AudioSharingUtils.isBroadcasting(this.mBtManager);
        AudioSharingUtils.postOnMainThread(
                this.mContext,
                new Runnable() { // from class:
                                 // com.android.settings.connecteddevice.audiosharing.AudioSharingCompatibilityPreferenceController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        AudioSharingCompatibilityPreferenceController.this.lambda$updateEnabled$1(
                                isBroadcasting, i, i2);
                    }
                });
    }

    private void registerCallbacks() {
        if (this.mBroadcast == null) {
            Log.d(TAG, "Skip register callbacks, profile not ready");
        } else {
            if (this.mCallbacksRegistered.get()) {
                return;
            }
            Log.d(TAG, "Register callbacks");
            this.mBroadcast.registerServiceCallBack(this.mExecutor, this.mBroadcastCallback);
            this.mCallbacksRegistered.set(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateEnabled() {
        ThreadUtils.postOnBackgroundThread(
                new AudioSharingCompatibilityPreferenceController$$ExternalSyntheticLambda0(
                        this, 0));
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = (TwoStatePreference) preferenceScreen.findPreference(getPreferenceKey());
        updateEnabled();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        BluetoothAdapter.getDefaultAdapter();
        return 3;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return PREF_KEY;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mBroadcast;
        return localBluetoothLeBroadcast != null && localBluetoothLeBroadcast.mImproveCompatibility;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
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

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public void onServiceConnected() {
        if (AudioSharingUtils.isAudioSharingProfileReady(this.mProfileManager)) {
            registerCallbacks();
            AudioSharingUtils.postOnMainThread(
                    this.mContext,
                    new AudioSharingCompatibilityPreferenceController$$ExternalSyntheticLambda0(
                            this, 1));
            LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
            if (localBluetoothProfileManager != null) {
                localBluetoothProfileManager.removeServiceListener(this);
            }
        }
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStart(LifecycleOwner lifecycleOwner) {
        if (!isAvailable()) {
            Log.d(TAG, "Skip register callbacks, feature not support");
            return;
        }
        if (AudioSharingUtils.isAudioSharingProfileReady(this.mProfileManager)) {
            registerCallbacks();
            return;
        }
        Log.d(TAG, "Skip register callbacks, profile not ready");
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        if (localBluetoothProfileManager != null) {
            localBluetoothProfileManager.addServiceListener(this);
        }
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStop(LifecycleOwner lifecycleOwner) {
        if (!isAvailable()) {
            Log.d(TAG, "Skip unregister callbacks, feature not support");
            return;
        }
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        if (localBluetoothProfileManager != null) {
            localBluetoothProfileManager.removeServiceListener(this);
        }
        if (this.mBroadcast == null
                || !AudioSharingUtils.isAudioSharingProfileReady(this.mProfileManager)) {
            Log.d(TAG, "Skip unregister callbacks, profile not ready");
        } else if (this.mCallbacksRegistered.get()) {
            Log.d(TAG, "Unregister callbacks");
            this.mBroadcast.unregisterServiceCallBack(this.mBroadcastCallback);
            this.mCallbacksRegistered.set(false);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void setCallbacksRegistered(boolean z) {
        this.mCallbacksRegistered.set(z);
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mBroadcast;
        if (localBluetoothLeBroadcast != null
                && localBluetoothLeBroadcast.mImproveCompatibility != z) {
            localBluetoothLeBroadcast.setImproveCompatibility(z, true);
            this.mMetricsFeatureProvider.action(this.mContext, 1929, z);
            return true;
        }
        if (localBluetoothLeBroadcast == null) {
            return false;
        }
        Log.d(TAG, "Skip setting improveCompatibility, unchanged");
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public void onServiceDisconnected() {}
}

package com.android.settings.connecteddevice.audiosharing;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothLeBroadcast;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothLeBroadcastSettings;
import android.bluetooth.BluetoothLeBroadcastSubgroupSettings;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.bluetooth.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.ValidatedEditTextPreference;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioSharingNamePreferenceController extends BasePreferenceController
        implements ValidatedEditTextPreference.Validator,
                Preference.OnPreferenceChangeListener,
                DefaultLifecycleObserver,
                LocalBluetoothProfileManager.ServiceListener {
    private static final boolean DEBUG = true;
    private static final String PREF_KEY = "audio_sharing_stream_name";
    private static final String TAG = "AudioSharingNamePreferenceController";
    private final AudioSharingNameTextValidator mAudioSharingNameTextValidator;
    private final LocalBluetoothLeBroadcast mBroadcast;
    private final BluetoothLeBroadcast.Callback mBroadcastCallback;
    private final LocalBluetoothManager mBtManager;
    private AtomicBoolean mCallbacksRegistered;
    private final Executor mExecutor;
    private final MetricsFeatureProvider mMetricsFeatureProvider;
    private AudioSharingNamePreference mPreference;
    private final LocalBluetoothProfileManager mProfileManager;

    public AudioSharingNamePreferenceController(Context context, String str) {
        super(context, str);
        this.mBroadcastCallback =
                new BluetoothLeBroadcast
                        .Callback() { // from class:
                                      // com.android.settings.connecteddevice.audiosharing.AudioSharingNamePreferenceController.1
                    public final void onBroadcastMetadataChanged(
                            int i, BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
                        Log.d(
                                AudioSharingNamePreferenceController.TAG,
                                "onBroadcastMetadataChanged() broadcastId : "
                                        + i
                                        + " metadata: "
                                        + bluetoothLeBroadcastMetadata);
                        AudioSharingNamePreferenceController.this.updateQrCodeIcon(true);
                    }

                    public final void onBroadcastStopped(int i, int i2) {
                        MainClearConfirm$$ExternalSyntheticOutline0.m(
                                "onBroadcastStopped() reason : ",
                                " broadcastId: ",
                                i,
                                i2,
                                AudioSharingNamePreferenceController.TAG);
                        AudioSharingNamePreferenceController.this.updateQrCodeIcon(false);
                    }

                    public final void onBroadcastUpdateFailed(int i, int i2) {
                        RecordingInputConnection$$ExternalSyntheticOutline0.m(
                                i,
                                "onBroadcastUpdateFailed() reason : ",
                                AudioSharingNamePreferenceController.TAG);
                    }

                    public final void onBroadcastUpdated(int i, int i2) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                                i,
                                "onBroadcastUpdated() reason : ",
                                AudioSharingNamePreferenceController.TAG);
                    }

                    public final void onBroadcastStartFailed(int i) {}

                    public final void onBroadcastStopFailed(int i) {}

                    public final void onBroadcastStarted(int i, int i2) {}

                    public final void onPlaybackStarted(int i, int i2) {}

                    public final void onPlaybackStopped(int i, int i2) {}
                };
        this.mCallbacksRegistered = new AtomicBoolean(false);
        LocalBluetoothManager localBluetoothManager = Utils.getLocalBluetoothManager(context);
        this.mBtManager = localBluetoothManager;
        LocalBluetoothProfileManager localBluetoothProfileManager =
                localBluetoothManager == null ? null : localBluetoothManager.mProfileManager;
        this.mProfileManager = localBluetoothProfileManager;
        this.mBroadcast =
                localBluetoothProfileManager != null
                        ? localBluetoothProfileManager.mLeAudioBroadcast
                        : null;
        this.mAudioSharingNameTextValidator = new AudioSharingNameTextValidator();
        this.mExecutor = Executors.newSingleThreadExecutor();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$onPreferenceChange$0(Object obj) {
        if (this.mBroadcast != null) {
            boolean isBroadcasting = AudioSharingUtils.isBroadcasting(this.mBtManager);
            String str = (String) obj;
            this.mBroadcast.setBroadcastName(str, true);
            this.mBroadcast.setProgramInfo(str, true);
            if (isBroadcasting) {
                LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mBroadcast;
                if (localBluetoothLeBroadcast.mServiceBroadcast == null) {
                    Log.d(
                            "LocalBluetoothLeBroadcast",
                            "The BluetoothLeBroadcast is null when updating the broadcast.");
                } else {
                    String str2 = localBluetoothLeBroadcast.mProgramInfo;
                    String str3 = localBluetoothLeBroadcast.mBroadcastName;
                    localBluetoothLeBroadcast.mBluetoothLeAudioContentMetadata =
                            localBluetoothLeBroadcast.mBuilder.setProgramInfo(str2).build();
                    BluetoothLeBroadcastSettings build =
                            new BluetoothLeBroadcastSettings.Builder()
                                    .setBroadcastName(str3)
                                    .addSubgroupSettings(
                                            new BluetoothLeBroadcastSubgroupSettings.Builder()
                                                    .setContentMetadata(
                                                            localBluetoothLeBroadcast
                                                                    .mBluetoothLeAudioContentMetadata)
                                                    .build())
                                    .build();
                    Log.d(
                            "LocalBluetoothLeBroadcast",
                            "updateBroadcast: broadcastName = " + str3 + " programInfo = " + str2);
                    localBluetoothLeBroadcast.mServiceBroadcast.updateBroadcast(
                            localBluetoothLeBroadcast.mBroadcastId, build);
                }
            }
            updateBroadcastName();
            this.mMetricsFeatureProvider.action(this.mContext, 1943, isBroadcasting ? 1 : 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateBroadcastName$1(String str) {
        AudioSharingNamePreference audioSharingNamePreference = this.mPreference;
        if (audioSharingNamePreference != null) {
            audioSharingNamePreference.setText(str);
            this.mPreference.setSummary(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$updateBroadcastName$2() {
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mBroadcast;
        if (localBluetoothLeBroadcast != null) {
            AudioSharingUtils.postOnMainThread(
                    this.mContext,
                    new AudioSharingNamePreferenceController$$ExternalSyntheticLambda0(
                            this, localBluetoothLeBroadcast.mBroadcastName, 0));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$updateQrCodeIcon$3(boolean z) {
        AudioSharingNamePreference audioSharingNamePreference = this.mPreference;
        if (audioSharingNamePreference != null) {
            audioSharingNamePreference.mShowQrCodeIcon = z;
            audioSharingNamePreference.notifyChanged();
        }
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

    private void updateBroadcastName() {
        if (this.mPreference != null) {
            ThreadUtils.postOnBackgroundThread(
                    new Runnable() { // from class:
                                     // com.android.settings.connecteddevice.audiosharing.AudioSharingNamePreferenceController$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            AudioSharingNamePreferenceController.this
                                    .lambda$updateBroadcastName$2();
                        }
                    });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateQrCodeIcon(final boolean z) {
        if (this.mPreference != null) {
            AudioSharingUtils.postOnMainThread(
                    this.mContext,
                    new Runnable() { // from class:
                                     // com.android.settings.connecteddevice.audiosharing.AudioSharingNamePreferenceController$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            AudioSharingNamePreferenceController.this.lambda$updateQrCodeIcon$3(z);
                        }
                    });
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        AudioSharingNamePreference audioSharingNamePreference =
                (AudioSharingNamePreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = audioSharingNamePreference;
        if (audioSharingNamePreference != null) {
            audioSharingNamePreference.mValidator = this;
            updateBroadcastName();
            updateQrCodeIcon(AudioSharingUtils.isBroadcasting(this.mBtManager));
        }
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return PREF_KEY;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
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

    @Override // com.android.settings.widget.ValidatedEditTextPreference.Validator
    public boolean isTextValid(String str) {
        return this.mAudioSharingNameTextValidator.isTextValid(str);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
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

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        AudioSharingNamePreference audioSharingNamePreference = this.mPreference;
        if (audioSharingNamePreference != null
                && audioSharingNamePreference.getSummary() != null
                && ((String) obj).contentEquals(this.mPreference.getSummary())) {
            return false;
        }
        ThreadUtils.postOnBackgroundThread(
                new AudioSharingNamePreferenceController$$ExternalSyntheticLambda0(this, obj, 1));
        return true;
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onResume(LifecycleOwner lifecycleOwner) {
        super.onResume(lifecycleOwner);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public void onServiceConnected() {
        if (AudioSharingUtils.isAudioSharingProfileReady(this.mProfileManager)) {
            registerCallbacks();
            updateBroadcastName();
            updateQrCodeIcon(AudioSharingUtils.isBroadcasting(this.mBtManager));
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

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public void onServiceDisconnected() {}
}

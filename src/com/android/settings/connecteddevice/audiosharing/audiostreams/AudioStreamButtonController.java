package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeBroadcastAssistant;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothLeBroadcastReceiveState;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.bluetooth.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.bluetooth.BluetoothLeBroadcastMetadataExt;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.utils.ThreadUtils;
import com.android.settingslib.widget.ActionButtonsPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioStreamButtonController extends BasePreferenceController
        implements DefaultLifecycleObserver {
    private static final String KEY = "audio_stream_button";
    private static final int SOURCE_ORIGIN_REPOSITORY = 4;
    private static final String TAG = "AudioStreamButtonController";
    private final AudioStreamsHelper mAudioStreamsHelper;
    private final AudioStreamsRepository mAudioStreamsRepository;
    private final BluetoothLeBroadcastAssistant.Callback mBroadcastAssistantCallback;
    private int mBroadcastId;
    private final Executor mExecutor;
    private final LocalBluetoothLeBroadcastAssistant mLeBroadcastAssistant;
    private final MetricsFeatureProvider mMetricsFeatureProvider;
    private ActionButtonsPreference mPreference;

    public AudioStreamButtonController(Context context, String str) {
        super(context, str);
        this.mBroadcastAssistantCallback =
                new AudioStreamsBroadcastAssistantCallback() { // from class:
                                                               // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamButtonController.1
                    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsBroadcastAssistantCallback
                    public final void onReceiveStateChanged(
                            BluetoothDevice bluetoothDevice,
                            int i,
                            BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
                        super.onReceiveStateChanged(
                                bluetoothDevice, i, bluetoothLeBroadcastReceiveState);
                        if (AudioStreamsHelper.isConnected(bluetoothLeBroadcastReceiveState)) {
                            AudioStreamButtonController.this.updateButton();
                            AudioStreamButtonController.this.mMetricsFeatureProvider.action(
                                    ((AbstractPreferenceController)
                                                    AudioStreamButtonController.this)
                                            .mContext,
                                    1946,
                                    AudioStreamButtonController.SOURCE_ORIGIN_REPOSITORY);
                        }
                    }

                    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsBroadcastAssistantCallback
                    public final void onSourceAddFailed(
                            BluetoothDevice bluetoothDevice,
                            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata,
                            int i) {
                        super.onSourceAddFailed(bluetoothDevice, bluetoothLeBroadcastMetadata, i);
                        AudioStreamButtonController.this.updateButton();
                        AudioStreamButtonController.this.mMetricsFeatureProvider.action(
                                ((AbstractPreferenceController) AudioStreamButtonController.this)
                                        .mContext,
                                1955,
                                AudioStreamButtonController.SOURCE_ORIGIN_REPOSITORY);
                    }

                    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsBroadcastAssistantCallback
                    public final void onSourceLost(int i) {
                        super.onSourceLost(i);
                        AudioStreamButtonController.this.updateButton();
                    }

                    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsBroadcastAssistantCallback
                    public final void onSourceRemoveFailed(
                            BluetoothDevice bluetoothDevice, int i, int i2) {
                        super.onSourceRemoveFailed(bluetoothDevice, i, i2);
                        AudioStreamButtonController.this.updateButton();
                        AudioStreamButtonController.this.mMetricsFeatureProvider.action(
                                ((AbstractPreferenceController) AudioStreamButtonController.this)
                                        .mContext,
                                1948,
                                new Pair[0]);
                    }

                    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsBroadcastAssistantCallback
                    public final void onSourceRemoved(
                            BluetoothDevice bluetoothDevice, int i, int i2) {
                        super.onSourceRemoved(bluetoothDevice, i, i2);
                        AudioStreamButtonController.this.updateButton();
                    }
                };
        this.mAudioStreamsRepository = AudioStreamsRepository.getInstance();
        this.mBroadcastId = -1;
        this.mExecutor = Executors.newSingleThreadExecutor();
        AudioStreamsHelper audioStreamsHelper =
                new AudioStreamsHelper(
                        LocalBluetoothManager.getInstance(context, Utils.mOnInitCallback));
        this.mAudioStreamsHelper = audioStreamsHelper;
        this.mLeBroadcastAssistant = audioStreamsHelper.getLeBroadcastAssistant();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$updateButton$0(Integer num) {
        return num.intValue() == this.mBroadcastId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateButton$1() {
        ActionButtonsPreference actionButtonsPreference = this.mPreference;
        if (actionButtonsPreference != null) {
            actionButtonsPreference.setButton1Enabled(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$updateButton$2() {
        final AudioStreamsHelper audioStreamsHelper = this.mAudioStreamsHelper;
        final int i = this.mBroadcastId;
        if (audioStreamsHelper.mLeBroadcastAssistant == null) {
            Log.w("AudioStreamsHelper", "removeSource(): LeBroadcastAssistant is null!");
        } else {
            ThreadUtils.postOnBackgroundThread(
                    new Runnable() { // from class:
                        // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsHelper$$ExternalSyntheticLambda10
                        @Override // java.lang.Runnable
                        public final void run() {
                            final AudioStreamsHelper audioStreamsHelper2 = AudioStreamsHelper.this;
                            final int i2 = i;
                            for (final BluetoothDevice bluetoothDevice :
                                    AudioStreamsHelper.getConnectedBluetoothDevices(
                                            audioStreamsHelper2.mBluetoothManager, true)) {
                                StringBuilder m =
                                        ListPopupWindow$$ExternalSyntheticOutline0.m(
                                                i2,
                                                "removeSource(): remove all sources with broadcast"
                                                    + " id :",
                                                " from sink : ");
                                m.append(bluetoothDevice.getAddress());
                                Log.d("AudioStreamsHelper", m.toString());
                                audioStreamsHelper2
                                        .mLeBroadcastAssistant
                                        .getAllSources(bluetoothDevice)
                                        .stream()
                                        .filter(
                                                new Predicate() { // from class:
                                                    // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsHelper$$ExternalSyntheticLambda13
                                                    @Override // java.util.function.Predicate
                                                    public final boolean test(Object obj) {
                                                        return ((BluetoothLeBroadcastReceiveState)
                                                                                obj)
                                                                        .getBroadcastId()
                                                                == i2;
                                                    }
                                                })
                                        .forEach(
                                                new Consumer() { // from class:
                                                    // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsHelper$$ExternalSyntheticLambda14
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        AudioStreamsHelper audioStreamsHelper3 =
                                                                AudioStreamsHelper.this;
                                                        audioStreamsHelper3.mLeBroadcastAssistant
                                                                .removeSource(
                                                                        bluetoothDevice,
                                                                        ((BluetoothLeBroadcastReceiveState)
                                                                                        obj)
                                                                                .getSourceId());
                                                    }
                                                });
                            }
                        }
                    });
        }
        this.mMetricsFeatureProvider.action(this.mContext, 1947, new Pair[0]);
        ThreadUtils.postOnMainThread(
                new AudioStreamButtonController$$ExternalSyntheticLambda0(this, 2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateButton$3(View view) {
        ThreadUtils.postOnBackgroundThread(
                new AudioStreamButtonController$$ExternalSyntheticLambda0(this, 0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateButton$4(View.OnClickListener onClickListener) {
        ActionButtonsPreference actionButtonsPreference = this.mPreference;
        if (actionButtonsPreference != null) {
            actionButtonsPreference.setButton1Enabled(true);
            ActionButtonsPreference actionButtonsPreference2 = this.mPreference;
            actionButtonsPreference2.setButton1Text(R.string.audio_streams_disconnect);
            actionButtonsPreference2.setButton1Icon(R.drawable.ic_settings_close);
            actionButtonsPreference2.setButton1OnClickListener(onClickListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateButton$5() {
        ActionButtonsPreference actionButtonsPreference = this.mPreference;
        if (actionButtonsPreference != null) {
            actionButtonsPreference.setButton1Enabled(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$updateButton$6() {
        AudioStreamsRepository audioStreamsRepository = this.mAudioStreamsRepository;
        Context context = this.mContext;
        int i = this.mBroadcastId;
        audioStreamsRepository.getClass();
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("bluetooth_audio_stream_pref", 0);
        BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata = null;
        if (sharedPreferences != null) {
            String string = sharedPreferences.getString("bluetooth_audio_stream_metadata", null);
            if (string == null) {
                Log.w("AudioStreamsRepository", "getSavedMetadata(): savedMetadataStr is null");
            } else {
                BluetoothLeBroadcastMetadata convertToBroadcastMetadata =
                        BluetoothLeBroadcastMetadataExt.convertToBroadcastMetadata(string);
                if (convertToBroadcastMetadata == null
                        || convertToBroadcastMetadata.getBroadcastId() != i) {
                    Log.w(
                            "AudioStreamsRepository",
                            "getSavedMetadata(): savedMetadata doesn't match broadcast Id.");
                } else {
                    Log.d(
                            "AudioStreamsRepository",
                            "getSavedMetadata(): broadcastId "
                                    + convertToBroadcastMetadata.getBroadcastId()
                                    + " metadata found in storage.");
                    bluetoothLeBroadcastMetadata = convertToBroadcastMetadata;
                }
            }
        }
        if (bluetoothLeBroadcastMetadata != null) {
            AudioStreamsHelper audioStreamsHelper = this.mAudioStreamsHelper;
            if (audioStreamsHelper.mLeBroadcastAssistant == null) {
                Log.w("AudioStreamsHelper", "addSource(): LeBroadcastAssistant is null!");
            } else {
                ThreadUtils.postOnBackgroundThread(
                        new AudioStreamsHelper$$ExternalSyntheticLambda7(
                                audioStreamsHelper, bluetoothLeBroadcastMetadata));
            }
            this.mMetricsFeatureProvider.action(this.mContext, 1945, SOURCE_ORIGIN_REPOSITORY);
            ThreadUtils.postOnMainThread(
                    new AudioStreamButtonController$$ExternalSyntheticLambda0(this, 3));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateButton$7(View view) {
        ThreadUtils.postOnBackgroundThread(
                new AudioStreamButtonController$$ExternalSyntheticLambda0(this, 1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateButton$8(View.OnClickListener onClickListener) {
        ActionButtonsPreference actionButtonsPreference = this.mPreference;
        if (actionButtonsPreference != null) {
            actionButtonsPreference.setButton1Enabled(true);
            ActionButtonsPreference actionButtonsPreference2 = this.mPreference;
            actionButtonsPreference2.setButton1Text(R.string.audio_streams_connect);
            actionButtonsPreference2.setButton1Icon(R.drawable.ic_add_24dp);
            actionButtonsPreference2.setButton1OnClickListener(onClickListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamButtonController$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamButtonController$$ExternalSyntheticLambda3] */
    public void updateButton() {
        if (this.mPreference == null) {
            Log.w(TAG, "updateButton(): preference is null!");
        } else if (this.mAudioStreamsHelper.getAllConnectedSources().stream()
                .map(new AudioStreamButtonController$$ExternalSyntheticLambda1())
                .anyMatch(
                        new Predicate() { // from class:
                                          // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamButtonController$$ExternalSyntheticLambda2
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                boolean lambda$updateButton$0;
                                lambda$updateButton$0 =
                                        AudioStreamButtonController.this.lambda$updateButton$0(
                                                (Integer) obj);
                                return lambda$updateButton$0;
                            }
                        })) {
            final int i = 0;
            ThreadUtils.postOnMainThread(
                    new AudioStreamButtonController$$ExternalSyntheticLambda4(
                            this,
                            new View.OnClickListener(
                                    this) { // from class:
                                            // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamButtonController$$ExternalSyntheticLambda3
                                public final /* synthetic */ AudioStreamButtonController f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    int i2 = i;
                                    AudioStreamButtonController audioStreamButtonController =
                                            this.f$0;
                                    switch (i2) {
                                        case 0:
                                            audioStreamButtonController.lambda$updateButton$3(view);
                                            break;
                                        default:
                                            audioStreamButtonController.lambda$updateButton$7(view);
                                            break;
                                    }
                                }
                            }));
        } else {
            final int i2 = 1;
            ThreadUtils.postOnMainThread(
                    new AudioStreamButtonController$$ExternalSyntheticLambda4(
                            this,
                            new View.OnClickListener(
                                    this) { // from class:
                                            // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamButtonController$$ExternalSyntheticLambda3
                                public final /* synthetic */ AudioStreamButtonController f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    int i22 = i2;
                                    AudioStreamButtonController audioStreamButtonController =
                                            this.f$0;
                                    switch (i22) {
                                        case 0:
                                            audioStreamButtonController.lambda$updateButton$3(view);
                                            break;
                                        default:
                                            audioStreamButtonController.lambda$updateButton$7(view);
                                            break;
                                    }
                                }
                            },
                            (byte) 0));
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        this.mPreference =
                (ActionButtonsPreference) preferenceScreen.findPreference(getPreferenceKey());
        updateButton();
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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
        return KEY;
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

    public void init(int i) {
        this.mBroadcastId = i;
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
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                this.mLeBroadcastAssistant;
        if (localBluetoothLeBroadcastAssistant == null) {
            Log.w(TAG, "onStart(): LeBroadcastAssistant is null!");
        } else {
            localBluetoothLeBroadcastAssistant.registerServiceCallBack(
                    this.mExecutor, this.mBroadcastAssistantCallback);
        }
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStop(LifecycleOwner lifecycleOwner) {
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                this.mLeBroadcastAssistant;
        if (localBluetoothLeBroadcastAssistant == null) {
            Log.w(TAG, "onStop(): LeBroadcastAssistant is null!");
        } else {
            localBluetoothLeBroadcastAssistant.unregisterServiceCallBack(
                    this.mBroadcastAssistantCallback);
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
}

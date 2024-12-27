package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeBroadcastAssistant;
import android.bluetooth.BluetoothLeBroadcastReceiveState;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.bluetooth.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.widget.EntityHeaderController;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.utils.ThreadUtils;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioStreamHeaderController extends BasePreferenceController
        implements DefaultLifecycleObserver {
    static final int AUDIO_STREAM_HEADER_LISTENING_NOW_SUMMARY = 2132018374;
    static final String AUDIO_STREAM_HEADER_NOT_LISTENING_SUMMARY = "";
    private static final String KEY = "audio_stream_header";
    private static final String TAG = "AudioStreamHeaderController";
    private final AudioStreamsHelper mAudioStreamsHelper;
    private final BluetoothLeBroadcastAssistant.Callback mBroadcastAssistantCallback;
    private int mBroadcastId;
    private String mBroadcastName;
    private final Executor mExecutor;
    private DashboardFragment mFragment;
    private EntityHeaderController mHeaderController;
    private final LocalBluetoothLeBroadcastAssistant mLeBroadcastAssistant;

    public AudioStreamHeaderController(Context context, String str) {
        super(context, str);
        this.mBroadcastAssistantCallback =
                new AudioStreamsBroadcastAssistantCallback() { // from class:
                                                               // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamHeaderController.1
                    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsBroadcastAssistantCallback
                    public final void onReceiveStateChanged(
                            BluetoothDevice bluetoothDevice,
                            int i,
                            BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
                        super.onReceiveStateChanged(
                                bluetoothDevice, i, bluetoothLeBroadcastReceiveState);
                        if (AudioStreamsHelper.isConnected(bluetoothLeBroadcastReceiveState)) {
                            AudioStreamHeaderController.this.updateSummary();
                            AudioStreamHeaderController.this.mAudioStreamsHelper.startMediaService(
                                    ((AbstractPreferenceController)
                                                    AudioStreamHeaderController.this)
                                            .mContext,
                                    AudioStreamHeaderController.this.mBroadcastId,
                                    AudioStreamHeaderController.this.mBroadcastName);
                        }
                    }

                    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsBroadcastAssistantCallback
                    public final void onSourceLost(int i) {
                        super.onSourceLost(i);
                        AudioStreamHeaderController.this.updateSummary();
                    }

                    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsBroadcastAssistantCallback
                    public final void onSourceRemoved(
                            BluetoothDevice bluetoothDevice, int i, int i2) {
                        super.onSourceRemoved(bluetoothDevice, i, i2);
                        AudioStreamHeaderController.this.updateSummary();
                    }
                };
        this.mBroadcastName = "";
        this.mBroadcastId = -1;
        this.mExecutor = Executors.newSingleThreadExecutor();
        AudioStreamsHelper audioStreamsHelper =
                new AudioStreamsHelper(
                        LocalBluetoothManager.getInstance(context, Utils.mOnInitCallback));
        this.mAudioStreamsHelper = audioStreamsHelper;
        this.mLeBroadcastAssistant = audioStreamsHelper.getLeBroadcastAssistant();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$updateSummary$0(Integer num) {
        return num.intValue() == this.mBroadcastId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$updateSummary$1(String str) {
        EntityHeaderController entityHeaderController = this.mHeaderController;
        if (entityHeaderController != null) {
            entityHeaderController.mSummary = str;
            entityHeaderController.done(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateSummary$2() {
        final String string =
                this.mAudioStreamsHelper.getAllConnectedSources().stream()
                                .map(new AudioStreamButtonController$$ExternalSyntheticLambda1())
                                .anyMatch(
                                        new Predicate() { // from class:
                                                          // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamHeaderController$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                boolean lambda$updateSummary$0;
                                                lambda$updateSummary$0 =
                                                        AudioStreamHeaderController.this
                                                                .lambda$updateSummary$0(
                                                                        (Integer) obj);
                                                return lambda$updateSummary$0;
                                            }
                                        })
                        ? this.mContext.getString(AUDIO_STREAM_HEADER_LISTENING_NOW_SUMMARY)
                        : "";
        ThreadUtils.postOnMainThread(
                new Runnable() { // from class:
                                 // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamHeaderController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        AudioStreamHeaderController.this.lambda$updateSummary$1(string);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSummary() {
        ThreadUtils.postOnBackgroundThread(
                new Runnable() { // from class:
                                 // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamHeaderController$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        AudioStreamHeaderController.this.lambda$updateSummary$2();
                    }
                });
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        DashboardFragment dashboardFragment;
        LayoutPreference layoutPreference = (LayoutPreference) preferenceScreen.findPreference(KEY);
        if (layoutPreference != null && (dashboardFragment = this.mFragment) != null) {
            EntityHeaderController entityHeaderController =
                    new EntityHeaderController(
                            dashboardFragment.getActivity(),
                            this.mFragment,
                            layoutPreference.mRootView.findViewById(R.id.entity_header));
            this.mHeaderController = entityHeaderController;
            String str = this.mBroadcastName;
            if (str != null) {
                entityHeaderController.mLabel = str;
            }
            entityHeaderController.setIcon(
                    preferenceScreen.getContext().getDrawable(R.drawable.ic_bt_le_audio_sharing));
            preferenceScreen.addPreference(layoutPreference);
            updateSummary();
        }
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

    public void init(AudioStreamDetailsFragment audioStreamDetailsFragment, String str, int i) {
        this.mFragment = audioStreamDetailsFragment;
        this.mBroadcastName = str;
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

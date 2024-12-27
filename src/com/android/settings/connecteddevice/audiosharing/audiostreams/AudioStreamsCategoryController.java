package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;

import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.bluetooth.Utils;
import com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController;
import com.android.settings.connecteddevice.audiosharing.AudioSharingUtils;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioStreamsCategoryController extends AudioSharingBasePreferenceController {
    private static final boolean DEBUG = true;
    private static final String TAG = "AudioStreamsCategoryController";
    private final BluetoothCallback mBluetoothCallback;
    private final Executor mExecutor;
    private final LocalBluetoothManager mLocalBtManager;

    public AudioStreamsCategoryController(Context context, String str) {
        super(context, str);
        this.mBluetoothCallback =
                new BluetoothCallback() { // from class:
                                          // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsCategoryController.1
                    @Override // com.android.settingslib.bluetooth.BluetoothCallback
                    public final void onActiveDeviceChanged(
                            CachedBluetoothDevice cachedBluetoothDevice, int i) {
                        if (i == 22) {
                            AudioStreamsCategoryController.this.updateVisibility();
                        }
                    }
                };
        this.mLocalBtManager =
                LocalBluetoothManager.getInstance(this.mContext, Utils.mOnInitCallback);
        this.mExecutor = Executors.newSingleThreadExecutor();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateVisibility$0() {
        Preference preference = this.mPreference;
        if (preference != null) {
            preference.setVisible(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateVisibility$1(
            boolean z, boolean z2, boolean z3, boolean z4) {
        Preference preference = this.mPreference;
        if (preference != null) {
            preference.setVisible(z && z2 && z3 && !z4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$updateVisibility$2() {
        if (!isAvailable()) {
            Log.d(TAG, "skip updateVisibility, unavailable preference");
            AudioSharingUtils.postOnMainThread(
                    this.mContext,
                    new AudioStreamsCategoryController$$ExternalSyntheticLambda0(this, 1));
            return;
        }
        final boolean isPresent =
                AudioStreamsHelper.getCachedBluetoothDeviceInSharingOrLeConnected(
                                this.mLocalBtManager)
                        .isPresent();
        final boolean isAudioSharingProfileReady =
                AudioSharingUtils.isAudioSharingProfileReady(this.mLocalBtManager.mProfileManager);
        final boolean isBroadcasting = isBroadcasting();
        final boolean isBluetoothStateOn = isBluetoothStateOn();
        StringBuilder m =
                Utils$$ExternalSyntheticOutline0.m(
                        "updateVisibility() isBroadcasting : ",
                        isBroadcasting,
                        " hasConnectedLe : ",
                        isPresent,
                        " is BT on : ");
        m.append(isBluetoothStateOn);
        m.append(" is profile ready : ");
        m.append(isAudioSharingProfileReady);
        Log.d(TAG, m.toString());
        AudioSharingUtils.postOnMainThread(
                this.mContext,
                new Runnable() { // from class:
                                 // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsCategoryController$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        AudioStreamsCategoryController.this.lambda$updateVisibility$1(
                                isAudioSharingProfileReady,
                                isBluetoothStateOn,
                                isPresent,
                                isBroadcasting);
                    }
                });
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 3;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onCreate(LifecycleOwner lifecycleOwner) {
        super.onCreate(lifecycleOwner);
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onDestroy(LifecycleOwner lifecycleOwner) {
        super.onDestroy(lifecycleOwner);
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onPause(LifecycleOwner lifecycleOwner) {
        super.onPause(lifecycleOwner);
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onResume(LifecycleOwner lifecycleOwner) {
        super.onResume(lifecycleOwner);
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, androidx.lifecycle.DefaultLifecycleObserver
    public void onStart(LifecycleOwner lifecycleOwner) {
        if (isAvailable()) {
            super.onStart(lifecycleOwner);
            LocalBluetoothManager localBluetoothManager = this.mLocalBtManager;
            if (localBluetoothManager != null) {
                localBluetoothManager.mEventManager.registerCallback(this.mBluetoothCallback);
            }
        }
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, androidx.lifecycle.DefaultLifecycleObserver
    public void onStop(LifecycleOwner lifecycleOwner) {
        if (isAvailable()) {
            super.onStop(lifecycleOwner);
            LocalBluetoothManager localBluetoothManager = this.mLocalBtManager;
            if (localBluetoothManager != null) {
                localBluetoothManager.mEventManager.unregisterCallback(this.mBluetoothCallback);
            }
        }
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController
    public void updateVisibility() {
        if (this.mPreference == null) {
            return;
        }
        this.mExecutor.execute(
                new AudioStreamsCategoryController$$ExternalSyntheticLambda0(this, 0));
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}

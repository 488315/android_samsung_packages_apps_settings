package com.android.settings.connecteddevice.audiosharing;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.bluetooth.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AudioSharingBasePreferenceController extends BasePreferenceController
        implements DefaultLifecycleObserver {
    private static final String TAG = "AudioSharingBasePreferenceController";
    private final BluetoothAdapter mBluetoothAdapter;
    protected final LocalBluetoothLeBroadcast mBroadcast;
    private final LocalBluetoothManager mBtManager;
    protected Preference mPreference;
    private final LocalBluetoothProfileManager mProfileManager;

    public AudioSharingBasePreferenceController(Context context, String str) {
        super(context, str);
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
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
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateVisibility$0() {
        Preference preference = this.mPreference;
        if (preference != null) {
            preference.setVisible(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateVisibility$1(boolean z) {
        Preference preference = this.mPreference;
        if (preference != null) {
            preference.setVisible(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateVisibility$2() {
        if (!isAvailable()) {
            Log.w(TAG, "Skip updateVisibility, unavailable preference");
            AudioSharingUtils.postOnMainThread(
                    this.mContext,
                    new AudioSharingBasePreferenceController$$ExternalSyntheticLambda0(this, 0));
            return;
        }
        boolean isBluetoothStateOn = isBluetoothStateOn();
        boolean isAudioSharingProfileReady =
                AudioSharingUtils.isAudioSharingProfileReady(this.mProfileManager);
        boolean isBroadcasting = isBroadcasting();
        final boolean z = isBluetoothStateOn && isAudioSharingProfileReady && isBroadcasting;
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                Utils$$ExternalSyntheticOutline0.m(
                        "updateVisibility, isBtOn = ",
                        isBluetoothStateOn,
                        ", isProfileReady = ",
                        isAudioSharingProfileReady,
                        ", isBroadcasting = "),
                isBroadcasting,
                TAG);
        AudioSharingUtils.postOnMainThread(
                this.mContext,
                new Runnable() { // from class:
                                 // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        AudioSharingBasePreferenceController.this.lambda$updateVisibility$1(z);
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public boolean isBluetoothStateOn() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    }

    public boolean isBroadcasting() {
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mBroadcast;
        return localBluetoothLeBroadcast != null && localBluetoothLeBroadcast.isEnabled(null);
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
        updateVisibility();
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onStop(LifecycleOwner lifecycleOwner) {
        super.onStop(lifecycleOwner);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void updateVisibility() {
        if (this.mPreference == null) {
            Log.d(TAG, "Skip updateVisibility, null preference");
        } else {
            ThreadUtils.postOnBackgroundThread(
                    new AudioSharingBasePreferenceController$$ExternalSyntheticLambda0(this, 1));
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    public void onAudioSharingProfilesConnected() {}
}

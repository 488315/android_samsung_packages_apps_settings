package com.android.settings.connecteddevice.audiosharing;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.bluetooth.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class StreamSettingsCategoryController extends BasePreferenceController
        implements DefaultLifecycleObserver, LocalBluetoothProfileManager.ServiceListener {
    private static final String TAG = "StreamSettingsCategoryController";
    private final BluetoothAdapter mBluetoothAdapter;
    private final LocalBluetoothManager mBtManager;
    final IntentFilter mIntentFilter;
    private Preference mPreference;
    private final LocalBluetoothProfileManager mProfileManager;
    BroadcastReceiver mReceiver;

    public StreamSettingsCategoryController(Context context, String str) {
        super(context, str);
        this.mReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.settings.connecteddevice.audiosharing.StreamSettingsCategoryController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if ("android.bluetooth.adapter.action.STATE_CHANGED"
                                .equals(intent.getAction())) {
                            StreamSettingsCategoryController.this.updateVisibility();
                        }
                    }
                };
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(context, Utils.mOnInitCallback);
        this.mBtManager = localBluetoothManager;
        this.mProfileManager =
                localBluetoothManager == null ? null : localBluetoothManager.mProfileManager;
        this.mIntentFilter = new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED");
    }

    private boolean isBluetoothOn() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    }

    private boolean isProfileReady() {
        return AudioSharingUtils.isAudioSharingProfileReady(this.mProfileManager);
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
    public void updateVisibility() {
        if (this.mPreference == null) {
            Log.w(TAG, "Skip updateVisibility, null preference");
        } else if (isAvailable()) {
            final boolean z = isBluetoothOn() && isProfileReady();
            AudioSharingUtils.postOnMainThread(
                    this.mContext,
                    new Runnable() { // from class:
                                     // com.android.settings.connecteddevice.audiosharing.StreamSettingsCategoryController$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            StreamSettingsCategoryController.this.lambda$updateVisibility$1(z);
                        }
                    });
        } else {
            Log.w(TAG, "Skip updateVisibility, unavailable preference");
            AudioSharingUtils.postOnMainThread(
                    this.mContext,
                    new Runnable() { // from class:
                                     // com.android.settings.connecteddevice.audiosharing.StreamSettingsCategoryController$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            StreamSettingsCategoryController.this.lambda$updateVisibility$0();
                        }
                    });
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
        updateVisibility();
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

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public void onServiceConnected() {
        if (isAvailable() && isProfileReady()) {
            updateVisibility();
            LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
            if (localBluetoothProfileManager != null) {
                localBluetoothProfileManager.removeServiceListener(this);
            }
        }
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStart(LifecycleOwner lifecycleOwner) {
        LocalBluetoothProfileManager localBluetoothProfileManager;
        if (isAvailable()) {
            this.mContext.registerReceiver(this.mReceiver, this.mIntentFilter, 2);
            if (isProfileReady() || (localBluetoothProfileManager = this.mProfileManager) == null) {
                return;
            }
            localBluetoothProfileManager.addServiceListener(this);
        }
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStop(LifecycleOwner lifecycleOwner) {
        if (isAvailable()) {
            this.mContext.unregisterReceiver(this.mReceiver);
            LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
            if (localBluetoothProfileManager != null) {
                localBluetoothProfileManager.removeServiceListener(this);
            }
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

package com.android.settings.connecteddevice.audiosharing.audiostreams;

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
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioStreamsScanQrCodeController extends BasePreferenceController
        implements DefaultLifecycleObserver {
    static final String KEY = "audio_streams_scan_qr_code";
    static final int REQUEST_SCAN_BT_BROADCAST_QR_CODE = 0;
    private static final String TAG = "AudioStreamsProgressCategoryController";
    final BluetoothCallback mBluetoothCallback;
    private AudioStreamsDashboardFragment mFragment;
    private final LocalBluetoothManager mLocalBtManager;
    private Preference mPreference;

    public AudioStreamsScanQrCodeController(Context context, String str) {
        super(context, str);
        this.mBluetoothCallback =
                new BluetoothCallback() { // from class:
                                          // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsScanQrCodeController.1
                    @Override // com.android.settingslib.bluetooth.BluetoothCallback
                    public final void onActiveDeviceChanged(
                            CachedBluetoothDevice cachedBluetoothDevice, int i) {
                        if (i == 22) {
                            AudioStreamsScanQrCodeController.this.updateVisibility();
                        }
                    }
                };
        this.mLocalBtManager =
                LocalBluetoothManager.getInstance(this.mContext, Utils.mOnInitCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean lambda$displayPreference$0(Preference preference) {
        if (this.mFragment == null) {
            Log.w(TAG, "displayPreference() mFragment is null!");
            return false;
        }
        if (!preference.getKey().equals(KEY)) {
            return false;
        }
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        subSettingLauncher.setTitleRes(R.string.audio_streams_main_page_scan_qr_code_title, null);
        String name = AudioStreamsQrCodeScanFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        subSettingLauncher.setResultListener(this.mFragment, 0);
        this.mFragment.getClass();
        launchRequest.mSourceMetricsCategory = 2093;
        subSettingLauncher.launch();
        return true;
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
        final boolean isPresent =
                AudioStreamsHelper.getCachedBluetoothDeviceInSharingOrLeConnected(
                                this.mLocalBtManager)
                        .isPresent();
        ThreadUtils.postOnMainThread(
                new Runnable() { // from class:
                                 // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsScanQrCodeController$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        AudioStreamsScanQrCodeController.this.lambda$updateVisibility$1(isPresent);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVisibility() {
        ThreadUtils.postOnBackgroundThread(
                new Runnable() { // from class:
                                 // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsScanQrCodeController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        AudioStreamsScanQrCodeController.this.lambda$updateVisibility$2();
                    }
                });
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = findPreference;
        if (findPreference == null) {
            Log.w(TAG, "displayPreference() mPreference is null!");
        } else {
            findPreference.setOnPreferenceClickListener(
                    new Preference
                            .OnPreferenceClickListener() { // from class:
                                                           // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsScanQrCodeController$$ExternalSyntheticLambda0
                        @Override // androidx.preference.Preference.OnPreferenceClickListener
                        public final boolean onPreferenceClick(Preference preference) {
                            boolean lambda$displayPreference$0;
                            lambda$displayPreference$0 =
                                    AudioStreamsScanQrCodeController.this
                                            .lambda$displayPreference$0(preference);
                            return lambda$displayPreference$0;
                        }
                    });
        }
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
        LocalBluetoothManager localBluetoothManager = this.mLocalBtManager;
        if (localBluetoothManager != null) {
            localBluetoothManager.mEventManager.registerCallback(this.mBluetoothCallback);
        }
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStop(LifecycleOwner lifecycleOwner) {
        LocalBluetoothManager localBluetoothManager = this.mLocalBtManager;
        if (localBluetoothManager != null) {
            localBluetoothManager.mEventManager.unregisterCallback(this.mBluetoothCallback);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setFragment(AudioStreamsDashboardFragment audioStreamsDashboardFragment) {
        this.mFragment = audioStreamsDashboardFragment;
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

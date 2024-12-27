package com.android.settings.connecteddevice.audiosharing;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeAudioContentMetadata;
import android.bluetooth.BluetoothLeBroadcast;
import android.bluetooth.BluetoothLeBroadcastAssistant;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothLeBroadcastReceiveState;
import android.bluetooth.BluetoothLeBroadcastSettings;
import android.bluetooth.BluetoothLeBroadcastSubgroupSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.FeatureFlagUtils;
import android.util.Log;
import android.util.Pair;
import android.widget.CompoundButton;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SeslSwitchBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.bluetooth.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.utils.ThreadUtils;

import com.google.common.collect.ImmutableList;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioSharingSwitchBarController extends BasePreferenceController
        implements DefaultLifecycleObserver,
                CompoundButton.OnCheckedChangeListener,
                LocalBluetoothProfileManager.ServiceListener {
    private static final String PREF_KEY = "audio_sharing_main_switch";
    private static final String TAG = "AudioSharingSwitchBarCtl";
    private final LocalBluetoothLeBroadcastAssistant mAssistant;
    private final BluetoothAdapter mBluetoothAdapter;
    private final LocalBluetoothLeBroadcast mBroadcast;
    private final BluetoothLeBroadcastAssistant.Callback mBroadcastAssistantCallback;
    final BluetoothLeBroadcast.Callback mBroadcastCallback;
    private final LocalBluetoothManager mBtManager;
    private final AtomicBoolean mCallbacksRegistered;
    private List<AudioSharingDeviceItem> mDeviceItemsForSharing;
    private final Executor mExecutor;
    private Fragment mFragment;
    private Map<Integer, List<CachedBluetoothDevice>> mGroupedConnectedDevices;
    IntentFilter mIntentFilter;
    private final OnAudioSharingStateChangedListener mListener;
    private final MetricsFeatureProvider mMetricsFeatureProvider;
    private final LocalBluetoothProfileManager mProfileManager;
    BroadcastReceiver mReceiver;
    private final SettingsMainSwitchBar mSwitchBar;
    private List<BluetoothDevice> mTargetActiveSinks;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.connecteddevice.audiosharing.AudioSharingSwitchBarController$4, reason: invalid class name */
    public final class AnonymousClass4 implements AudioSharingDialogFragment.DialogEventListener {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnAudioSharingStateChangedListener {}

    public AudioSharingSwitchBarController(
            Context context,
            SettingsMainSwitchBar settingsMainSwitchBar,
            OnAudioSharingStateChangedListener onAudioSharingStateChangedListener) {
        super(context, PREF_KEY);
        this.mGroupedConnectedDevices = new HashMap();
        this.mTargetActiveSinks = new ArrayList();
        this.mDeviceItemsForSharing = new ArrayList();
        this.mCallbacksRegistered = new AtomicBoolean(false);
        this.mReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.settings.connecteddevice.audiosharing.AudioSharingSwitchBarController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        AudioSharingSwitchBarController.this.updateSwitch();
                        ((AudioSharingDashboardFragment)
                                        AudioSharingSwitchBarController.this.mListener)
                                .onAudioSharingStateChanged();
                    }
                };
        this.mBroadcastCallback =
                new BluetoothLeBroadcast
                        .Callback() { // from class:
                                      // com.android.settings.connecteddevice.audiosharing.AudioSharingSwitchBarController.2
                    public final void onBroadcastMetadataChanged(
                            int i, BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
                        StringBuilder m =
                                ListPopupWindow$$ExternalSyntheticOutline0.m(
                                        i,
                                        "onBroadcastMetadataChanged(), broadcastId = ",
                                        ", metadata = ");
                        m.append(bluetoothLeBroadcastMetadata.getBroadcastName());
                        Log.d(AudioSharingSwitchBarController.TAG, m.toString());
                    }

                    public final void onBroadcastStartFailed(int i) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                                i,
                                "onBroadcastStartFailed(), reason = ",
                                AudioSharingSwitchBarController.TAG);
                        AudioSharingSwitchBarController.this.updateSwitch();
                    }

                    public final void onBroadcastStarted(int i, int i2) {
                        MainClearConfirm$$ExternalSyntheticOutline0.m(
                                "onBroadcastStarted(), reason = ",
                                ", broadcastId = ",
                                i,
                                i2,
                                AudioSharingSwitchBarController.TAG);
                        AudioSharingSwitchBarController.this.updateSwitch();
                        ((AudioSharingDashboardFragment)
                                        AudioSharingSwitchBarController.this.mListener)
                                .onAudioSharingStateChanged();
                    }

                    public final void onBroadcastStopFailed(int i) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                                i,
                                "onBroadcastStopFailed(), reason = ",
                                AudioSharingSwitchBarController.TAG);
                        AudioSharingSwitchBarController.this.updateSwitch();
                    }

                    public final void onBroadcastStopped(int i, int i2) {
                        MainClearConfirm$$ExternalSyntheticOutline0.m(
                                "onBroadcastStopped(), reason = ",
                                ", broadcastId = ",
                                i,
                                i2,
                                AudioSharingSwitchBarController.TAG);
                        AudioSharingSwitchBarController.this.updateSwitch();
                        ((AudioSharingDashboardFragment)
                                        AudioSharingSwitchBarController.this.mListener)
                                .onAudioSharingStateChanged();
                    }

                    public final void onPlaybackStarted(int i, int i2) {
                        MainClearConfirm$$ExternalSyntheticOutline0.m(
                                "onPlaybackStarted(), reason = ",
                                ", broadcastId = ",
                                i,
                                i2,
                                AudioSharingSwitchBarController.TAG);
                        AudioSharingSwitchBarController.this.handleOnBroadcastReady();
                    }

                    public final void onBroadcastUpdateFailed(int i, int i2) {}

                    public final void onBroadcastUpdated(int i, int i2) {}

                    public final void onPlaybackStopped(int i, int i2) {}
                };
        this.mBroadcastAssistantCallback =
                new BluetoothLeBroadcastAssistant
                        .Callback() { // from class:
                                      // com.android.settings.connecteddevice.audiosharing.AudioSharingSwitchBarController.3
                    public final void onSourceAddFailed(
                            BluetoothDevice bluetoothDevice,
                            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata,
                            int i) {
                        StringBuilder sb = new StringBuilder("onSourceAddFailed(), sink = ");
                        sb.append(bluetoothDevice);
                        sb.append(", source = ");
                        sb.append(bluetoothLeBroadcastMetadata);
                        sb.append(", reason = ");
                        Preference$$ExternalSyntheticOutline0.m(
                                sb, i, AudioSharingSwitchBarController.TAG);
                        Context context2 =
                                ((AbstractPreferenceController)
                                                AudioSharingSwitchBarController.this)
                                        .mContext;
                        Locale locale = Locale.US;
                        AudioSharingUtils.toastMessage(
                                context2,
                                "Fail to add source to "
                                        + bluetoothDevice.getAddress()
                                        + " reason "
                                        + i);
                    }

                    public final void onSourceAdded(
                            BluetoothDevice bluetoothDevice, int i, int i2) {
                        StringBuilder sb = new StringBuilder("onSourceAdded(), sink = ");
                        sb.append(bluetoothDevice);
                        sb.append(", sourceId = ");
                        sb.append(i);
                        sb.append(", reason = ");
                        Preference$$ExternalSyntheticOutline0.m(
                                sb, i2, AudioSharingSwitchBarController.TAG);
                    }

                    public final void onSearchStartFailed(int i) {}

                    public final void onSearchStarted(int i) {}

                    public final void onSearchStopFailed(int i) {}

                    public final void onSearchStopped(int i) {}

                    public final void onSourceFound(
                            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {}

                    public final void onReceiveStateChanged(
                            BluetoothDevice bluetoothDevice,
                            int i,
                            BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {}

                    public final void onSourceModified(
                            BluetoothDevice bluetoothDevice, int i, int i2) {}

                    public final void onSourceModifyFailed(
                            BluetoothDevice bluetoothDevice, int i, int i2) {}

                    public final void onSourceRemoveFailed(
                            BluetoothDevice bluetoothDevice, int i, int i2) {}

                    public final void onSourceRemoved(
                            BluetoothDevice bluetoothDevice, int i, int i2) {}
                };
        this.mSwitchBar = settingsMainSwitchBar;
        this.mListener = onAudioSharingStateChangedListener;
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mIntentFilter = new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED");
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(context, Utils.mOnInitCallback);
        this.mBtManager = localBluetoothManager;
        LocalBluetoothProfileManager localBluetoothProfileManager =
                localBluetoothManager == null ? null : localBluetoothManager.mProfileManager;
        this.mProfileManager = localBluetoothProfileManager;
        this.mBroadcast =
                localBluetoothProfileManager == null
                        ? null
                        : localBluetoothProfileManager.mLeAudioBroadcast;
        this.mAssistant =
                localBluetoothProfileManager != null
                        ? localBluetoothProfileManager.mLeAudioBroadcastAssistant
                        : null;
        this.mExecutor = Executors.newSingleThreadExecutor();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnBroadcastReady() {
        Pair<Integer, Object>[] buildAudioSharingDialogEventData =
                AudioSharingUtils.buildAudioSharingDialogEventData(
                        false,
                        2048,
                        2086,
                        !this.mTargetActiveSinks.isEmpty() ? 1 : 0,
                        this.mDeviceItemsForSharing.size());
        if (!this.mTargetActiveSinks.isEmpty()) {
            Log.d(TAG, "handleOnBroadcastReady: automatically add source to active sinks.");
            AudioSharingUtils.addSourceToTargetSinks(this.mTargetActiveSinks, this.mBtManager);
            this.mMetricsFeatureProvider.action(this.mContext, 1938, new Pair[0]);
            this.mTargetActiveSinks.clear();
        }
        if (this.mFragment != null) {
            showDialog(buildAudioSharingDialogEventData);
            return;
        }
        Log.d(TAG, "handleOnBroadcastReady: dialog fail to show due to null fragment.");
        this.mGroupedConnectedDevices.clear();
        this.mDeviceItemsForSharing.clear();
    }

    private boolean isBluetoothOn() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$onCheckedChanged$0() {
        this.mSwitchBar.setEnabled(true);
        this.mSwitchBar.setChecked(false);
        if (this.mFragment != null) {
            BluetoothAdapter.getDefaultAdapter();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$showDialog$3(
            AudioSharingDialogFragment.DialogEventListener dialogEventListener, Pair[] pairArr) {
        if (this.mFragment != null) {
            Pair[] pairArr2 = AudioSharingDialogFragment.sEventData;
            BluetoothAdapter.getDefaultAdapter();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$updateSwitch$1(boolean z, boolean z2) {
        if (((SeslSwitchBar) this.mSwitchBar).mSwitch.isChecked() != z) {
            this.mSwitchBar.setChecked(z);
        }
        if (this.mSwitchBar.isEnabled() != z2) {
            this.mSwitchBar.setEnabled(z2);
        }
        Utils$$ExternalSyntheticOutline0.m653m(
                "updateSwitch, checked = ", z, ", enabled = ", z2, TAG);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateSwitch$2() {
        final boolean isBroadcasting = AudioSharingUtils.isBroadcasting(this.mBtManager);
        final boolean z =
                isBluetoothOn()
                        && AudioSharingUtils.isAudioSharingProfileReady(this.mProfileManager);
        AudioSharingUtils.postOnMainThread(
                this.mContext,
                new Runnable() { // from class:
                                 // com.android.settings.connecteddevice.audiosharing.AudioSharingSwitchBarController$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        AudioSharingSwitchBarController.this.lambda$updateSwitch$1(
                                isBroadcasting, z);
                    }
                });
    }

    private void registerCallbacks() {
        if (!isAvailable()) {
            Log.d(TAG, "Skip registerCallbacks(). Feature is not available.");
            return;
        }
        if (this.mBroadcast == null || this.mAssistant == null) {
            Log.d(TAG, "Skip registerCallbacks(). Profile not support on this device.");
            return;
        }
        if (this.mCallbacksRegistered.get()) {
            return;
        }
        Log.d(TAG, "registerCallbacks()");
        this.mSwitchBar.addOnSwitchChangeListener(this);
        this.mBroadcast.registerServiceCallBack(this.mExecutor, this.mBroadcastCallback);
        this.mAssistant.registerServiceCallBack(this.mExecutor, this.mBroadcastAssistantCallback);
        this.mCallbacksRegistered.set(true);
    }

    private void showDialog(final Pair<Integer, Object>[] pairArr) {
        final AnonymousClass4 anonymousClass4 = new AnonymousClass4();
        AudioSharingUtils.postOnMainThread(
                this.mContext,
                new Runnable() { // from class:
                                 // com.android.settings.connecteddevice.audiosharing.AudioSharingSwitchBarController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AudioSharingSwitchBarController.this.lambda$showDialog$3(
                                (AudioSharingSwitchBarController.AnonymousClass4) anonymousClass4,
                                pairArr);
                    }
                });
    }

    private void startAudioSharing() {
        Map<Integer, List<CachedBluetoothDevice>> fetchConnectedDevicesByGroupId =
                AudioSharingUtils.fetchConnectedDevicesByGroupId(this.mBtManager);
        this.mGroupedConnectedDevices = fetchConnectedDevicesByGroupId;
        List buildOrderedConnectedLeadAudioSharingDeviceItem =
                AudioSharingUtils.buildOrderedConnectedLeadAudioSharingDeviceItem(
                        this.mBtManager, fetchConnectedDevicesByGroupId, false);
        this.mDeviceItemsForSharing =
                new ArrayList(buildOrderedConnectedLeadAudioSharingDeviceItem);
        this.mTargetActiveSinks = new ArrayList();
        if (!buildOrderedConnectedLeadAudioSharingDeviceItem.isEmpty()
                && ((AudioSharingDeviceItem) buildOrderedConnectedLeadAudioSharingDeviceItem.get(0))
                        .mIsActive) {
            Iterator<CachedBluetoothDevice> it =
                    this.mGroupedConnectedDevices
                            .getOrDefault(
                                    Integer.valueOf(
                                            ((AudioSharingDeviceItem)
                                                            buildOrderedConnectedLeadAudioSharingDeviceItem
                                                                    .get(0))
                                                    .mGroupId),
                                    ImmutableList.of())
                            .iterator();
            while (it.hasNext()) {
                this.mTargetActiveSinks.add(it.next().mDevice);
            }
            this.mDeviceItemsForSharing.remove(0);
        }
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mBroadcast;
        if (localBluetoothLeBroadcast != null) {
            localBluetoothLeBroadcast.mNewAppSourceName = "Sharing audio";
            BluetoothLeBroadcast bluetoothLeBroadcast = localBluetoothLeBroadcast.mServiceBroadcast;
            if (bluetoothLeBroadcast == null) {
                Log.d(
                        "LocalBluetoothLeBroadcast",
                        "The BluetoothLeBroadcast is null when starting the private broadcast.");
                return;
            }
            if (bluetoothLeBroadcast.getAllBroadcastMetadata().size()
                    >= localBluetoothLeBroadcast.mServiceBroadcast.getMaximumNumberOfBroadcasts()) {
                Log.d(
                        "LocalBluetoothLeBroadcast",
                        "Skip starting the broadcast due to number limit.");
                return;
            }
            String str = localBluetoothLeBroadcast.mBroadcastName;
            String str2 = localBluetoothLeBroadcast.mProgramInfo;
            boolean z = localBluetoothLeBroadcast.mImproveCompatibility;
            StringBuilder m =
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                            "startBroadcast: language = null , programInfo = ",
                            str2,
                            ", broadcastName = ",
                            str,
                            ", improveCompatibility = ");
            m.append(z);
            Log.d("LocalBluetoothLeBroadcast", m.toString());
            byte[] bArr = null;
            localBluetoothLeBroadcast.mBluetoothLeAudioContentMetadata =
                    new BluetoothLeAudioContentMetadata.Builder()
                            .setLanguage((String) null)
                            .setProgramInfo(str2)
                            .build();
            BluetoothLeBroadcastSubgroupSettings build =
                    new BluetoothLeBroadcastSubgroupSettings.Builder()
                            .setPreferredQuality(!z ? 1 : 0)
                            .setContentMetadata(
                                    localBluetoothLeBroadcast.mBluetoothLeAudioContentMetadata)
                            .build();
            if (TextUtils.isEmpty(str)) {
                str = null;
            }
            byte[] bArr2 = localBluetoothLeBroadcast.mBroadcastCode;
            if (bArr2 != null && bArr2.length > 0) {
                bArr = bArr2;
            }
            ImmutableList construct = ImmutableList.construct(build);
            BluetoothLeBroadcastSettings.Builder broadcastCode =
                    new BluetoothLeBroadcastSettings.Builder()
                            .setPublicBroadcast(true)
                            .setBroadcastName(str)
                            .setBroadcastCode(bArr);
            ImmutableList.Itr listIterator = construct.listIterator(0);
            while (listIterator.hasNext()) {
                broadcastCode.addSubgroupSettings(
                        (BluetoothLeBroadcastSubgroupSettings) listIterator.next());
            }
            localBluetoothLeBroadcast.mServiceBroadcast.startBroadcast(broadcastCode.build());
        }
    }

    private void stopAudioSharing() {
        this.mSwitchBar.setEnabled(false);
        if (!AudioSharingUtils.isBroadcasting(this.mBtManager)) {
            Log.d(TAG, "Skip stopAudioSharing, already not broadcasting or broadcast not support.");
            this.mSwitchBar.setEnabled(true);
        } else {
            LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mBroadcast;
            if (localBluetoothLeBroadcast != null) {
                localBluetoothLeBroadcast.stopBroadcast(localBluetoothLeBroadcast.mBroadcastId);
            }
        }
    }

    private void unregisterCallbacks() {
        if (!isAvailable() || !AudioSharingUtils.isAudioSharingProfileReady(this.mProfileManager)) {
            Log.d(TAG, "Skip unregisterCallbacks(). Feature is not available.");
            return;
        }
        if (this.mBroadcast == null || this.mAssistant == null) {
            Log.d(TAG, "Skip unregisterCallbacks(). Profile not support on this device.");
            return;
        }
        if (this.mCallbacksRegistered.get()) {
            Log.d(TAG, "unregisterCallbacks()");
            this.mSwitchBar.removeOnSwitchChangeListener(this);
            this.mBroadcast.unregisterServiceCallBack(this.mBroadcastCallback);
            this.mAssistant.unregisterServiceCallBack(this.mBroadcastAssistantCallback);
            this.mCallbacksRegistered.set(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSwitch() {
        ThreadUtils.postOnBackgroundThread(
                new AudioSharingSwitchBarController$$ExternalSyntheticLambda1(this, 1));
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

    public void init(Fragment fragment) {
        this.mFragment = fragment;
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

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (compoundButton.isEnabled()) {
            if (!z) {
                stopAudioSharing();
                return;
            }
            this.mSwitchBar.setEnabled(false);
            boolean isBroadcasting = AudioSharingUtils.isBroadcasting(this.mBtManager);
            if (this.mAssistant == null || this.mBroadcast == null || isBroadcasting) {
                Log.d(TAG, "Skip startAudioSharing, already broadcasting or not support.");
                this.mSwitchBar.setEnabled(true);
                if (isBroadcasting) {
                    return;
                }
                this.mSwitchBar.setChecked(false);
                return;
            }
            if (FeatureFlagUtils.isEnabled(
                            this.mContext, "settings_need_connected_ble_device_for_broadcast")
                    && this.mAssistant
                            .getDevicesMatchingConnectionStates(new int[] {2})
                            .isEmpty()) {
                AudioSharingUtils.postOnMainThread(
                        this.mContext,
                        new AudioSharingSwitchBarController$$ExternalSyntheticLambda1(this, 0));
            } else {
                startAudioSharing();
            }
        }
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
        Log.d(TAG, "onServiceConnected()");
        if (AudioSharingUtils.isAudioSharingProfileReady(this.mProfileManager)) {
            registerCallbacks();
            updateSwitch();
            ((AudioSharingDashboardFragment) this.mListener)
                    .mAudioSharingDeviceVolumeGroupController.onAudioSharingProfilesConnected();
            ((AudioSharingDashboardFragment) this.mListener).onAudioSharingStateChanged();
            LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
            if (localBluetoothProfileManager != null) {
                localBluetoothProfileManager.removeServiceListener(this);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public void onServiceDisconnected() {
        Log.d(TAG, "onServiceDisconnected()");
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStart(LifecycleOwner lifecycleOwner) {
        if (!isAvailable()) {
            Log.d(TAG, "Skip register callbacks. Feature is not available.");
            return;
        }
        this.mContext.registerReceiver(this.mReceiver, this.mIntentFilter, 2);
        updateSwitch();
        if (AudioSharingUtils.isAudioSharingProfileReady(this.mProfileManager)) {
            registerCallbacks();
            return;
        }
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        if (localBluetoothProfileManager != null) {
            localBluetoothProfileManager.addServiceListener(this);
        }
        Log.d(TAG, "Skip register callbacks. Profile is not ready.");
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStop(LifecycleOwner lifecycleOwner) {
        if (!isAvailable()) {
            Log.d(TAG, "Skip unregister callbacks. Feature is not available.");
            return;
        }
        this.mContext.unregisterReceiver(this.mReceiver);
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        if (localBluetoothProfileManager != null) {
            localBluetoothProfileManager.removeServiceListener(this);
        }
        unregisterCallbacks();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setCallbacksRegistered(boolean z) {
        this.mCallbacksRegistered.set(z);
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

package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothLeBroadcastReceiveState;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.session.MediaController;
import android.util.Log;
import android.util.Pair;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.bluetooth.Utils;
import com.android.settings.connecteddevice.ConnectedDeviceDashboardFragment;
import com.android.settings.connecteddevice.audiosharing.AudioSharingUtils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.media.BluetoothMediaDevice;
import com.android.settingslib.media.LocalMediaManager;
import com.android.settingslib.media.MediaDevice;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioStreamsProgressCategoryController extends BasePreferenceController
        implements DefaultLifecycleObserver {
    private static final boolean DEBUG = true;
    private static final String TAG = "AudioStreamsProgressCategoryController";
    private static final int UNSET_BROADCAST_ID = -1;
    private final AudioStreamsHelper mAudioStreamsHelper;
    private final BluetoothCallback mBluetoothCallback;
    private final LocalBluetoothManager mBluetoothManager;
    private final AudioStreamsProgressCategoryCallback mBroadcastAssistantCallback;
    private final ConcurrentHashMap<Integer, AudioStreamPreference> mBroadcastIdToPreferenceMap;
    private AudioStreamsProgressCategoryPreference mCategoryPreference;
    private final Comparator<AudioStreamPreference> mComparator;
    private final Executor mExecutor;
    private AudioStreamsDashboardFragment mFragment;
    private final LocalBluetoothLeBroadcastAssistant mLeBroadcastAssistant;
    private final MediaControlHelper mMediaControlHelper;
    private BluetoothLeBroadcastMetadata mSourceFromQrCode;
    private SourceOriginForLogging mSourceFromQrCodeOriginForLogging;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsProgressCategoryController$1, reason: invalid class name */
    public final class AnonymousClass1 implements BluetoothCallback {
        public AnonymousClass1() {}

        @Override // com.android.settingslib.bluetooth.BluetoothCallback
        public final void onActiveDeviceChanged(
                CachedBluetoothDevice cachedBluetoothDevice, int i) {
            if (i == 22) {
                AudioStreamsProgressCategoryController.this.mExecutor.execute(
                        new AudioStreamsProgressCategoryController$$ExternalSyntheticLambda3(
                                5, this));
            }
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class AudioStreamState {
        public static final /* synthetic */ AudioStreamState[] $VALUES;
        public static final AudioStreamState ADD_SOURCE_BAD_CODE;
        public static final AudioStreamState ADD_SOURCE_FAILED;
        public static final AudioStreamState ADD_SOURCE_WAIT_FOR_RESPONSE;
        public static final AudioStreamState SOURCE_ADDED;
        public static final AudioStreamState SYNCED;
        public static final AudioStreamState UNKNOWN;
        public static final AudioStreamState WAIT_FOR_SYNC;

        static {
            AudioStreamState audioStreamState = new AudioStreamState("UNKNOWN", 0);
            UNKNOWN = audioStreamState;
            AudioStreamState audioStreamState2 = new AudioStreamState("WAIT_FOR_SYNC", 1);
            WAIT_FOR_SYNC = audioStreamState2;
            AudioStreamState audioStreamState3 = new AudioStreamState("SYNCED", 2);
            SYNCED = audioStreamState3;
            AudioStreamState audioStreamState4 =
                    new AudioStreamState("ADD_SOURCE_WAIT_FOR_RESPONSE", 3);
            ADD_SOURCE_WAIT_FOR_RESPONSE = audioStreamState4;
            AudioStreamState audioStreamState5 = new AudioStreamState("ADD_SOURCE_BAD_CODE", 4);
            ADD_SOURCE_BAD_CODE = audioStreamState5;
            AudioStreamState audioStreamState6 = new AudioStreamState("ADD_SOURCE_FAILED", 5);
            ADD_SOURCE_FAILED = audioStreamState6;
            AudioStreamState audioStreamState7 = new AudioStreamState("SOURCE_ADDED", 6);
            SOURCE_ADDED = audioStreamState7;
            $VALUES =
                    new AudioStreamState[] {
                        audioStreamState,
                        audioStreamState2,
                        audioStreamState3,
                        audioStreamState4,
                        audioStreamState5,
                        audioStreamState6,
                        audioStreamState7
                    };
        }

        public static AudioStreamState valueOf(String str) {
            return (AudioStreamState) Enum.valueOf(AudioStreamState.class, str);
        }

        public static AudioStreamState[] values() {
            return (AudioStreamState[]) $VALUES.clone();
        }
    }

    public AudioStreamsProgressCategoryController(Context context, String str) {
        super(context, str);
        this.mBluetoothCallback = new AnonymousClass1();
        this.mComparator =
                Comparator.comparing(
                                new AudioStreamsProgressCategoryController$$ExternalSyntheticLambda12())
                        .thenComparingInt(
                                new AudioStreamsProgressCategoryController$$ExternalSyntheticLambda13())
                        .reversed();
        this.mBroadcastIdToPreferenceMap = new ConcurrentHashMap<>();
        this.mExecutor = Executors.newSingleThreadExecutor();
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(this.mContext, Utils.mOnInitCallback);
        this.mBluetoothManager = localBluetoothManager;
        AudioStreamsHelper audioStreamsHelper = new AudioStreamsHelper(localBluetoothManager);
        this.mAudioStreamsHelper = audioStreamsHelper;
        this.mMediaControlHelper = new MediaControlHelper(this.mContext, localBluetoothManager);
        this.mLeBroadcastAssistant = audioStreamsHelper.getLeBroadcastAssistant();
        this.mBroadcastAssistantCallback = new AudioStreamsProgressCategoryCallback(this);
    }

    private AudioStreamPreference addNewPreference(
            BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState,
            AudioStreamState audioStreamState) {
        AudioStreamPreference audioStreamPreference =
                new AudioStreamPreference(this.mContext, null);
        audioStreamPreference.setTitle(
                AudioStreamsHelper.getBroadcastName(bluetoothLeBroadcastReceiveState));
        AudioStreamPreference.AudioStream audioStream = new AudioStreamPreference.AudioStream();
        audioStream.mSourceOriginForLogging = SourceOriginForLogging.UNKNOWN;
        audioStream.mState = AudioStreamState.UNKNOWN;
        audioStream.mReceiveState = bluetoothLeBroadcastReceiveState;
        audioStreamPreference.mAudioStream = audioStream;
        moveToState(audioStreamPreference, audioStreamState);
        return audioStreamPreference;
    }

    private AudioStreamsDialogFragment.DialogBuilder getNoLeDeviceDialog() {
        AudioStreamsDialogFragment.DialogBuilder dialogBuilder =
                new AudioStreamsDialogFragment.DialogBuilder(this.mContext);
        dialogBuilder.mTitle =
                this.mContext.getString(R.string.audio_streams_dialog_no_le_device_title);
        dialogBuilder.mSubTitle2 =
                this.mContext.getString(R.string.audio_streams_dialog_no_le_device_subtitle);
        dialogBuilder.mLeftButtonText =
                this.mContext.getString(R.string.audio_streams_dialog_close);
        dialogBuilder.mLeftButtonOnClickListener =
                new AddSourceWaitForResponseState$$ExternalSyntheticLambda2();
        dialogBuilder.mRightButtonText =
                this.mContext.getString(R.string.audio_streams_dialog_no_le_device_button);
        dialogBuilder.mRightButtonOnClickListener =
                new AudioStreamsProgressCategoryController$$ExternalSyntheticLambda2(this, 1);
        return dialogBuilder;
    }

    private void handleSourceFromQrCodeIfExists() {
        Log.d(TAG, "handleSourceFromQrCodeIfExists()");
        BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata = this.mSourceFromQrCode;
        if (bluetoothLeBroadcastMetadata == null) {
            return;
        }
        this.mBroadcastIdToPreferenceMap.compute(
                Integer.valueOf(bluetoothLeBroadcastMetadata.getBroadcastId()),
                new AudioStreamsProgressCategoryController$$ExternalSyntheticLambda6(this, 2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void init() {
        this.mBroadcastIdToPreferenceMap.clear();
        boolean isPresent =
                AudioStreamsHelper.getCachedBluetoothDeviceInSharingOrLeConnected(
                                this.mBluetoothManager)
                        .isPresent();
        AudioSharingUtils.postOnMainThread(
                this.mContext,
                new AudioStreamsProgressCategoryController$$ExternalSyntheticLambda5(
                        this, isPresent, 1));
        if (isPresent) {
            startScanning();
            AudioSharingUtils.postOnMainThread(
                    this.mContext,
                    new AudioStreamsProgressCategoryController$$ExternalSyntheticLambda3(1, this));
        } else {
            stopScanning();
            AudioSharingUtils.postOnMainThread(
                    this.mContext,
                    new AudioStreamsProgressCategoryController$$ExternalSyntheticLambda3(2, this));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$getNoLeDeviceDialog$17(AlertDialog alertDialog) {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = ConnectedDeviceDashboardFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 2096;
        subSettingLauncher.launch();
        alertDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ AudioStreamPreference lambda$handleSourceAddRequest$11(
            AudioStreamPreference audioStreamPreference,
            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata,
            Integer num,
            AudioStreamPreference audioStreamPreference2) {
        if (!audioStreamPreference2.equals(audioStreamPreference)) {
            Log.w(TAG, "handleSourceAddedRequest(): existing preference not match");
        }
        audioStreamPreference2.setAudioStreamMetadata(bluetoothLeBroadcastMetadata);
        moveToState(audioStreamPreference2, AudioStreamState.ADD_SOURCE_WAIT_FOR_RESPONSE);
        return audioStreamPreference2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ AudioStreamPreference lambda$handleSourceConnectBadCode$9(
            Integer num, AudioStreamPreference audioStreamPreference) {
        moveToState(audioStreamPreference, AudioStreamState.ADD_SOURCE_BAD_CODE);
        return audioStreamPreference;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ AudioStreamPreference lambda$handleSourceConnected$8(
            BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState,
            Integer num,
            AudioStreamPreference audioStreamPreference) {
        BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata;
        AudioStreamState audioStreamState = AudioStreamState.SOURCE_ADDED;
        if (audioStreamPreference == null) {
            return addNewPreference(bluetoothLeBroadcastReceiveState, audioStreamState);
        }
        if (audioStreamPreference.getAudioStreamState() == AudioStreamState.WAIT_FOR_SYNC
                && audioStreamPreference.getAudioStreamBroadcastId() == -1
                && (bluetoothLeBroadcastMetadata = this.mSourceFromQrCode) != null) {
            audioStreamPreference.setAudioStreamMetadata(bluetoothLeBroadcastMetadata);
        }
        moveToState(audioStreamPreference, audioStreamState);
        return audioStreamPreference;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ AudioStreamPreference lambda$handleSourceFailedToConnect$10(
            Integer num, AudioStreamPreference audioStreamPreference) {
        moveToState(audioStreamPreference, AudioStreamState.ADD_SOURCE_FAILED);
        return audioStreamPreference;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ AudioStreamPreference lambda$handleSourceFound$2(
            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata,
            int i,
            Integer num,
            AudioStreamPreference audioStreamPreference) {
        if (audioStreamPreference == null) {
            return addNewPreference(
                    bluetoothLeBroadcastMetadata,
                    AudioStreamState.SYNCED,
                    SourceOriginForLogging.BROADCAST_SEARCH);
        }
        AudioStreamState audioStreamState = audioStreamPreference.getAudioStreamState();
        if (audioStreamState != AudioStreamState.WAIT_FOR_SYNC || this.mSourceFromQrCode == null) {
            audioStreamPreference.setAudioStreamMetadata(bluetoothLeBroadcastMetadata);
            if (audioStreamState != AudioStreamState.SOURCE_ADDED) {
                Log.w(
                        TAG,
                        "handleSourceFound(): unexpected state : "
                                + audioStreamState
                                + " for broadcastId : "
                                + i);
            }
        } else {
            audioStreamPreference.setAudioStreamMetadata(
                    new BluetoothLeBroadcastMetadata.Builder(bluetoothLeBroadcastMetadata)
                            .setBroadcastCode(this.mSourceFromQrCode.getBroadcastCode())
                            .build());
            moveToState(audioStreamPreference, AudioStreamState.ADD_SOURCE_WAIT_FOR_RESPONSE);
        }
        return audioStreamPreference;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ AudioStreamPreference lambda$handleSourceFromQrCodeIfExists$3(
            Integer num, AudioStreamPreference audioStreamPreference) {
        BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata;
        if (audioStreamPreference == null
                && (bluetoothLeBroadcastMetadata = this.mSourceFromQrCode) != null) {
            return addNewPreference(
                    bluetoothLeBroadcastMetadata,
                    AudioStreamState.WAIT_FOR_SYNC,
                    this.mSourceFromQrCodeOriginForLogging);
        }
        StringBuilder sb =
                new StringBuilder("handleSourceFromQrCodeIfExists(): unexpected state : ");
        sb.append(audioStreamPreference.getAudioStreamState());
        sb.append(" for broadcastId : ");
        BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata2 = this.mSourceFromQrCode;
        sb.append(
                bluetoothLeBroadcastMetadata2 == null
                        ? "null"
                        : Integer.valueOf(bluetoothLeBroadcastMetadata2.getBroadcastId()));
        Log.w(TAG, sb.toString());
        return audioStreamPreference;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$handleSourceLost$4(
            int i, BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
        return bluetoothLeBroadcastReceiveState.getBroadcastId() == i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleSourceLost$5(
            AudioStreamPreference audioStreamPreference) {
        AudioStreamsProgressCategoryPreference audioStreamsProgressCategoryPreference =
                this.mCategoryPreference;
        if (audioStreamsProgressCategoryPreference != null) {
            audioStreamsProgressCategoryPreference.removePreference(audioStreamPreference);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$handleSourceRemoved$6(
            AudioStreamPreference audioStreamPreference,
            BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
        return bluetoothLeBroadcastReceiveState.getBroadcastId()
                == audioStreamPreference.getAudioStreamBroadcastId();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$handleSourceRemoved$7(AudioStreamPreference audioStreamPreference) {
        AudioStreamPreference.AudioStream audioStream = audioStreamPreference.mAudioStream;
        if ((audioStream != null ? audioStream.mMetadata : null) != null) {
            moveToState(audioStreamPreference, AudioStreamState.SYNCED);
        } else {
            handleSourceLost(audioStreamPreference.getAudioStreamBroadcastId());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$init$12(boolean z) {
        AudioStreamsProgressCategoryPreference audioStreamsProgressCategoryPreference =
                this.mCategoryPreference;
        if (audioStreamsProgressCategoryPreference != null) {
            Iterator it =
                    ((ArrayList)
                                    audioStreamsProgressCategoryPreference
                                            .getAllAudioStreamPreferences())
                            .iterator();
            while (it.hasNext()) {
                audioStreamsProgressCategoryPreference.removePreference(
                        (AudioStreamPreference) it.next());
            }
            this.mCategoryPreference.setVisible(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$init$13() {
        DialogFragment dialogFragment;
        Dialog dialog;
        AudioStreamsDashboardFragment audioStreamsDashboardFragment = this.mFragment;
        if (audioStreamsDashboardFragment != null) {
            if (!audioStreamsDashboardFragment.isAdded()) {
                Log.w(
                        "AudioStreamsDialogFragment",
                        "The host fragment is not added to the activity!");
                return;
            }
            Fragment findFragmentByTag =
                    audioStreamsDashboardFragment
                            .getChildFragmentManager()
                            .findFragmentByTag("AudioStreamsDialogFragment");
            if (findFragmentByTag == null
                    || (dialog = (dialogFragment = (DialogFragment) findFragmentByTag).mDialog)
                            == null
                    || !dialog.isShowing()) {
                return;
            }
            dialogFragment.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$14() {
        AudioStreamsDashboardFragment audioStreamsDashboardFragment = this.mFragment;
        if (audioStreamsDashboardFragment != null) {
            AudioStreamsDialogFragment.show(
                    audioStreamsDashboardFragment, getNoLeDeviceDialog(), 2096);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$moveToState$16(AudioStreamPreference audioStreamPreference) {
        AudioStreamsProgressCategoryPreference audioStreamsProgressCategoryPreference =
                this.mCategoryPreference;
        if (audioStreamsProgressCategoryPreference != null) {
            Comparator<AudioStreamPreference> comparator = this.mComparator;
            audioStreamsProgressCategoryPreference.addPreference(audioStreamPreference);
            ArrayList arrayList =
                    (ArrayList)
                            audioStreamsProgressCategoryPreference.getAllAudioStreamPreferences();
            arrayList.sort(comparator);
            int i = 0;
            while (i < arrayList.size()) {
                AudioStreamPreference audioStreamPreference2 =
                        (AudioStreamPreference) arrayList.get(i);
                i++;
                audioStreamPreference2.setOrder(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Boolean lambda$new$0(
            AudioStreamPreference audioStreamPreference) {
        return Boolean.valueOf(
                audioStreamPreference.getAudioStreamState() == AudioStreamState.SOURCE_ADDED);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$setScanning$1(boolean z) {
        AudioStreamsProgressCategoryPreference audioStreamsProgressCategoryPreference =
                this.mCategoryPreference;
        if (audioStreamsProgressCategoryPreference != null) {
            audioStreamsProgressCategoryPreference.mProgress = z;
            audioStreamsProgressCategoryPreference.notifyChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$startScanning$15() {
        handleSourceFromQrCodeIfExists();
        this.mAudioStreamsHelper
                .getAllConnectedSources()
                .forEach(
                        new AudioStreamsProgressCategoryController$$ExternalSyntheticLambda2(
                                this, 0));
        this.mLeBroadcastAssistant.startSearchingForSources(Collections.emptyList());
        MediaControlHelper mediaControlHelper = this.mMediaControlHelper;
        LocalBluetoothManager localBluetoothManager = mediaControlHelper.mLocalBluetoothManager;
        if (localBluetoothManager == null) {
            return;
        }
        final Optional cachedBluetoothDeviceInSharingOrLeConnected =
                AudioStreamsHelper.getCachedBluetoothDeviceInSharingOrLeConnected(
                        localBluetoothManager);
        if (cachedBluetoothDeviceInSharingOrLeConnected.isEmpty()) {
            Log.d("MediaControlHelper", "start() : current LE device is empty!");
            return;
        }
        for (final MediaController mediaController :
                mediaControlHelper.mMediaSessionManager.getActiveSessions(null)) {
            String packageName = mediaController.getPackageName();
            if (Objects.equals(packageName, mediaControlHelper.mContext.getPackageName())) {
                DialogFragment$$ExternalSyntheticOutline0.m(
                        "start() : skip package: ", packageName, "MediaControlHelper");
            } else {
                final LocalMediaManager localMediaManager =
                        new LocalMediaManager(mediaControlHelper.mContext, packageName);
                LocalMediaManager.DeviceCallback deviceCallback =
                        new LocalMediaManager
                                .DeviceCallback() { // from class:
                                                    // com.android.settings.connecteddevice.audiosharing.audiostreams.MediaControlHelper.1
                            @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
                            public final void onDeviceListUpdate(List list) {
                                MediaController mediaController2 = mediaController;
                                CachedBluetoothDevice cachedBluetoothDevice =
                                        (CachedBluetoothDevice)
                                                cachedBluetoothDeviceInSharingOrLeConnected.get();
                                MediaDevice currentConnectedDevice =
                                        localMediaManager.getCurrentConnectedDevice();
                                if (mediaController2.getPlaybackState() != null
                                        && mediaController2.getPlaybackState().getState() == 1) {
                                    Log.d(
                                            "MediaControlHelper",
                                            "shouldStopMedia() : skip already stopped: "
                                                    + mediaController2.getPackageName());
                                    return;
                                }
                                BluetoothMediaDevice bluetoothMediaDevice =
                                        currentConnectedDevice instanceof BluetoothMediaDevice
                                                ? (BluetoothMediaDevice) currentConnectedDevice
                                                : null;
                                if (bluetoothMediaDevice != null) {
                                    CachedBluetoothDevice cachedBluetoothDevice2 =
                                            bluetoothMediaDevice.mCachedDevice;
                                    if (cachedBluetoothDevice2.equals(cachedBluetoothDevice)
                                            || cachedBluetoothDevice2.mMemberDevices.contains(
                                                    cachedBluetoothDevice)
                                            || cachedBluetoothDevice.mMemberDevices.contains(
                                                    cachedBluetoothDevice2)) {
                                        Log.d(
                                                "MediaControlHelper",
                                                "start() : Stopping media player for package: "
                                                        + mediaController.getPackageName());
                                        MediaController.TransportControls transportControls =
                                                mediaController.getTransportControls();
                                        if (transportControls != null) {
                                            transportControls.stop();
                                        }
                                    }
                                }
                            }
                        };
                localMediaManager.registerCallback(deviceCallback);
                localMediaManager.mInfoMediaManager.startScanOnRouter();
                ((ArrayList) mediaControlHelper.mLocalMediaManagers)
                        .add(new Pair(localMediaManager, deviceCallback));
            }
        }
    }

    private boolean maybeUpdateId(String str, int i) {
        BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata = this.mSourceFromQrCode;
        if (bluetoothLeBroadcastMetadata == null
                || !str.equals(AudioStreamsHelper.getBroadcastName(bluetoothLeBroadcastMetadata))) {
            return false;
        }
        Log.d(
                TAG,
                "maybeUpdateId() : updating unset broadcastId for metadataFromQrCode with"
                    + " broadcastName: "
                        + AudioStreamsHelper.getBroadcastName(this.mSourceFromQrCode)
                        + " to broadcast Id: "
                        + i);
        this.mSourceFromQrCode =
                new BluetoothLeBroadcastMetadata.Builder(this.mSourceFromQrCode)
                        .setBroadcastId(i)
                        .build();
        return true;
    }

    private void moveToState(
            final AudioStreamPreference audioStreamPreference, AudioStreamState audioStreamState) {
        final AudioStreamStateHandler audioStreamStateHandler;
        switch (audioStreamState.ordinal()) {
            case 1:
                if (WaitForSyncState.sInstance == null) {
                    WaitForSyncState.sInstance = new WaitForSyncState();
                }
                audioStreamStateHandler = WaitForSyncState.sInstance;
                break;
            case 2:
                if (SyncedState.sInstance == null) {
                    SyncedState.sInstance = new SyncedState();
                }
                audioStreamStateHandler = SyncedState.sInstance;
                break;
            case 3:
                if (AddSourceWaitForResponseState.sInstance == null) {
                    AddSourceWaitForResponseState.sInstance = new AddSourceWaitForResponseState();
                }
                audioStreamStateHandler = AddSourceWaitForResponseState.sInstance;
                break;
            case 4:
                if (AddSourceBadCodeState.sInstance == null) {
                    AddSourceBadCodeState.sInstance = new AddSourceBadCodeState();
                }
                audioStreamStateHandler = AddSourceBadCodeState.sInstance;
                break;
            case 5:
                if (AddSourceFailedState.sInstance == null) {
                    AddSourceFailedState.sInstance = new AddSourceFailedState();
                }
                audioStreamStateHandler = AddSourceFailedState.sInstance;
                break;
            case 6:
                if (SourceAddedState.sInstance == null) {
                    SourceAddedState.sInstance = new SourceAddedState();
                }
                audioStreamStateHandler = SourceAddedState.sInstance;
                break;
            default:
                throw new IllegalArgumentException("Unsupported state: " + audioStreamState);
        }
        AudioStreamsHelper audioStreamsHelper = this.mAudioStreamsHelper;
        final AudioStreamState stateEnum = audioStreamStateHandler.getStateEnum();
        if (audioStreamPreference.getAudioStreamState() != stateEnum) {
            StringBuilder sb = new StringBuilder("moveToState() : moving preference : [");
            sb.append(audioStreamPreference.getAudioStreamBroadcastId());
            sb.append(", ");
            AudioStreamPreference.AudioStream audioStream = audioStreamPreference.mAudioStream;
            String str = null;
            if (audioStream != null) {
                BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata = audioStream.mMetadata;
                if (bluetoothLeBroadcastMetadata != null) {
                    str = AudioStreamsHelper.getBroadcastName(bluetoothLeBroadcastMetadata);
                } else {
                    BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState =
                            audioStream.mReceiveState;
                    if (bluetoothLeBroadcastReceiveState != null) {
                        str = AudioStreamsHelper.getBroadcastName(bluetoothLeBroadcastReceiveState);
                    }
                }
            }
            sb.append(str);
            sb.append("] from state : ");
            sb.append(audioStreamPreference.getAudioStreamState());
            sb.append(" to state : ");
            sb.append(stateEnum);
            Log.d("AudioStreamStateHandler", sb.toString());
            AudioStreamPreference.AudioStream audioStream2 = audioStreamPreference.mAudioStream;
            if (audioStream2 != null) {
                audioStream2.mState = stateEnum;
            }
            audioStreamStateHandler.performAction(audioStreamPreference, this, audioStreamsHelper);
            ThreadUtils.postOnMainThread(
                    new Runnable() { // from class:
                                     // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamStateHandler$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AudioStreamStateHandler audioStreamStateHandler2 =
                                    AudioStreamStateHandler.this;
                            AudioStreamPreference audioStreamPreference2 = audioStreamPreference;
                            AudioStreamsProgressCategoryController.AudioStreamState
                                    audioStreamState2 = stateEnum;
                            AudioStreamsProgressCategoryController
                                    audioStreamsProgressCategoryController = this;
                            audioStreamStateHandler2.getClass();
                            boolean z =
                                    audioStreamState2
                                            == AudioStreamsProgressCategoryController
                                                    .AudioStreamState.SOURCE_ADDED;
                            String string =
                                    audioStreamStateHandler2.getSummary() != 0
                                            ? audioStreamPreference2
                                                    .getContext()
                                                    .getString(
                                                            audioStreamStateHandler2.getSummary())
                                            : ApnSettings.MVNO_NONE;
                            Preference.OnPreferenceClickListener onClickListener =
                                    audioStreamStateHandler2.getOnClickListener(
                                            audioStreamsProgressCategoryController);
                            if (audioStreamPreference2.mIsConnected == z
                                    && audioStreamPreference2.getSummary() == string
                                    && audioStreamPreference2.getOnPreferenceClickListener()
                                            == onClickListener) {
                                return;
                            }
                            audioStreamPreference2.mIsConnected = z;
                            audioStreamPreference2.setSummary(string);
                            audioStreamPreference2.setOnPreferenceClickListener(onClickListener);
                            audioStreamPreference2.notifyChanged();
                        }
                    });
        }
        AudioSharingUtils.postOnMainThread(
                this.mContext,
                new AudioStreamsProgressCategoryController$$ExternalSyntheticLambda1(
                        this, audioStreamPreference, 1));
    }

    private void startScanning() {
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                this.mLeBroadcastAssistant;
        if (localBluetoothLeBroadcastAssistant == null) {
            Log.w(TAG, "startScanning(): LeBroadcastAssistant is null!");
            return;
        }
        if (localBluetoothLeBroadcastAssistant.isSearchInProgress()) {
            Log.w(TAG, "startScanning(): scanning still in progress, stop scanning first.");
            stopScanning();
        }
        this.mLeBroadcastAssistant.registerServiceCallBack(
                this.mExecutor, this.mBroadcastAssistantCallback);
        this.mExecutor.execute(
                new AudioStreamsProgressCategoryController$$ExternalSyntheticLambda3(4, this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopScanning() {
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                this.mLeBroadcastAssistant;
        if (localBluetoothLeBroadcastAssistant == null) {
            Log.w(TAG, "stopScanning(): LeBroadcastAssistant is null!");
            return;
        }
        if (localBluetoothLeBroadcastAssistant.isSearchInProgress()) {
            Log.d(TAG, "stopScanning()");
            this.mLeBroadcastAssistant.stopSearchingForSources$1();
            this.mLeBroadcastAssistant.unregisterServiceCallBack(this.mBroadcastAssistantCallback);
        }
        MediaControlHelper mediaControlHelper = this.mMediaControlHelper;
        ((ArrayList) mediaControlHelper.mLocalMediaManagers)
                .forEach(new MediaControlHelper$$ExternalSyntheticLambda0());
        ((ArrayList) mediaControlHelper.mLocalMediaManagers).clear();
        this.mSourceFromQrCode = null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mCategoryPreference =
                (AudioStreamsProgressCategoryPreference)
                        preferenceScreen.findPreference(getPreferenceKey());
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

    public AudioStreamsDashboardFragment getFragment() {
        return this.mFragment;
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

    public void handleSourceAddRequest(
            final AudioStreamPreference audioStreamPreference,
            final BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
        Log.d(TAG, "handleSourceAddRequest()");
        this.mBroadcastIdToPreferenceMap.computeIfPresent(
                Integer.valueOf(bluetoothLeBroadcastMetadata.getBroadcastId()),
                new BiFunction() { // from class:
                                   // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsProgressCategoryController$$ExternalSyntheticLambda20
                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        AudioStreamPreference lambda$handleSourceAddRequest$11;
                        lambda$handleSourceAddRequest$11 =
                                AudioStreamsProgressCategoryController.this
                                        .lambda$handleSourceAddRequest$11(
                                                audioStreamPreference,
                                                bluetoothLeBroadcastMetadata,
                                                (Integer) obj,
                                                (AudioStreamPreference) obj2);
                        return lambda$handleSourceAddRequest$11;
                    }
                });
    }

    public void handleSourceConnectBadCode(
            BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
        Log.d(TAG, "handleSourceConnectBadCode()");
        if (bluetoothLeBroadcastReceiveState.getPaSyncState() == 2
                && bluetoothLeBroadcastReceiveState.getBigEncryptionState() == 3) {
            this.mBroadcastIdToPreferenceMap.computeIfPresent(
                    Integer.valueOf(bluetoothLeBroadcastReceiveState.getBroadcastId()),
                    new AudioStreamsProgressCategoryController$$ExternalSyntheticLambda6(this, 0));
        }
    }

    public void handleSourceConnected(
            final BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
        Log.d(TAG, "handleSourceConnected()");
        if (AudioStreamsHelper.isConnected(bluetoothLeBroadcastReceiveState)) {
            int broadcastId = bluetoothLeBroadcastReceiveState.getBroadcastId();
            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata = this.mSourceFromQrCode;
            if (bluetoothLeBroadcastMetadata != null
                    && bluetoothLeBroadcastMetadata.getBroadcastId() == -1) {
                Log.d(
                        TAG,
                        "handleSourceConnected() : processing mSourceFromQrCode with broadcastId"
                            + " unset");
                if (maybeUpdateId(
                                AudioStreamsHelper.getBroadcastName(
                                        bluetoothLeBroadcastReceiveState),
                                bluetoothLeBroadcastReceiveState.getBroadcastId())
                        && this.mBroadcastIdToPreferenceMap.containsKey(-1)) {
                    this.mBroadcastIdToPreferenceMap.put(
                            Integer.valueOf(bluetoothLeBroadcastReceiveState.getBroadcastId()),
                            this.mBroadcastIdToPreferenceMap.remove(-1));
                }
            }
            this.mBroadcastIdToPreferenceMap.compute(
                    Integer.valueOf(broadcastId),
                    new BiFunction() { // from class:
                                       // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsProgressCategoryController$$ExternalSyntheticLambda9
                        @Override // java.util.function.BiFunction
                        public final Object apply(Object obj, Object obj2) {
                            AudioStreamPreference lambda$handleSourceConnected$8;
                            lambda$handleSourceConnected$8 =
                                    AudioStreamsProgressCategoryController.this
                                            .lambda$handleSourceConnected$8(
                                                    bluetoothLeBroadcastReceiveState,
                                                    (Integer) obj,
                                                    (AudioStreamPreference) obj2);
                            return lambda$handleSourceConnected$8;
                        }
                    });
        }
    }

    public void handleSourceFailedToConnect(int i) {
        Log.d(TAG, "handleSourceFailedToConnect()");
        this.mBroadcastIdToPreferenceMap.computeIfPresent(
                Integer.valueOf(i),
                new AudioStreamsProgressCategoryController$$ExternalSyntheticLambda6(this, 1));
    }

    public void handleSourceFound(final BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
        Log.d(TAG, "handleSourceFound()");
        final int broadcastId = bluetoothLeBroadcastMetadata.getBroadcastId();
        BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata2 = this.mSourceFromQrCode;
        if (bluetoothLeBroadcastMetadata2 != null
                && bluetoothLeBroadcastMetadata2.getBroadcastId() == -1) {
            Log.d(TAG, "handleSourceFound() : processing mSourceFromQrCode with broadcastId unset");
            if (maybeUpdateId(
                            AudioStreamsHelper.getBroadcastName(bluetoothLeBroadcastMetadata),
                            bluetoothLeBroadcastMetadata.getBroadcastId())
                    && this.mBroadcastIdToPreferenceMap.containsKey(-1)) {
                this.mBroadcastIdToPreferenceMap.put(
                        Integer.valueOf(bluetoothLeBroadcastMetadata.getBroadcastId()),
                        this.mBroadcastIdToPreferenceMap.remove(-1));
            }
        }
        this.mBroadcastIdToPreferenceMap.compute(
                Integer.valueOf(broadcastId),
                new BiFunction() { // from class:
                                   // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsProgressCategoryController$$ExternalSyntheticLambda10
                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        AudioStreamPreference lambda$handleSourceFound$2;
                        lambda$handleSourceFound$2 =
                                AudioStreamsProgressCategoryController.this
                                        .lambda$handleSourceFound$2(
                                                bluetoothLeBroadcastMetadata,
                                                broadcastId,
                                                (Integer) obj,
                                                (AudioStreamPreference) obj2);
                        return lambda$handleSourceFound$2;
                    }
                });
    }

    public void handleSourceLost(final int i) {
        Log.d(TAG, "handleSourceLost()");
        if (this.mAudioStreamsHelper.getAllConnectedSources().stream()
                .anyMatch(
                        new Predicate() { // from class:
                                          // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsProgressCategoryController$$ExternalSyntheticLambda7
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                boolean lambda$handleSourceLost$4;
                                lambda$handleSourceLost$4 =
                                        AudioStreamsProgressCategoryController
                                                .lambda$handleSourceLost$4(
                                                        i, (BluetoothLeBroadcastReceiveState) obj);
                                return lambda$handleSourceLost$4;
                            }
                        })) {
            Log.d(
                    TAG,
                    "handleSourceLost() : keep this preference as the source is still connected.");
            return;
        }
        AudioStreamPreference remove = this.mBroadcastIdToPreferenceMap.remove(Integer.valueOf(i));
        if (remove != null) {
            ThreadUtils.postOnMainThread(
                    new AudioStreamsProgressCategoryController$$ExternalSyntheticLambda1(
                            this, remove, 2));
        }
    }

    public void handleSourceRemoved() {
        Log.d(TAG, "handleSourceRemoved()");
        Iterator<Map.Entry<Integer, AudioStreamPreference>> it =
                this.mBroadcastIdToPreferenceMap.entrySet().iterator();
        while (it.hasNext()) {
            final AudioStreamPreference value = it.next().getValue();
            if (value.getAudioStreamState() == AudioStreamState.SOURCE_ADDED
                    && this.mAudioStreamsHelper.getAllConnectedSources().stream()
                            .noneMatch(
                                    new Predicate() { // from class:
                                                      // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsProgressCategoryController$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            boolean lambda$handleSourceRemoved$6;
                                            lambda$handleSourceRemoved$6 =
                                                    AudioStreamsProgressCategoryController
                                                            .lambda$handleSourceRemoved$6(
                                                                    AudioStreamPreference.this,
                                                                    (BluetoothLeBroadcastReceiveState)
                                                                            obj);
                                            return lambda$handleSourceRemoved$6;
                                        }
                                    })) {
                ThreadUtils.postOnMainThread(
                        new AudioStreamsProgressCategoryController$$ExternalSyntheticLambda1(
                                this, value, 0));
                return;
            }
        }
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
        LocalBluetoothManager localBluetoothManager = this.mBluetoothManager;
        if (localBluetoothManager != null) {
            localBluetoothManager.mEventManager.registerCallback(this.mBluetoothCallback);
        }
        this.mExecutor.execute(
                new AudioStreamsProgressCategoryController$$ExternalSyntheticLambda3(3, this));
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStop(LifecycleOwner lifecycleOwner) {
        LocalBluetoothManager localBluetoothManager = this.mBluetoothManager;
        if (localBluetoothManager != null) {
            localBluetoothManager.mEventManager.unregisterCallback(this.mBluetoothCallback);
        }
        this.mExecutor.execute(
                new AudioStreamsProgressCategoryController$$ExternalSyntheticLambda3(0, this));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setFragment(AudioStreamsDashboardFragment audioStreamsDashboardFragment) {
        this.mFragment = audioStreamsDashboardFragment;
    }

    public void setScanning(boolean z) {
        ThreadUtils.postOnMainThread(
                new AudioStreamsProgressCategoryController$$ExternalSyntheticLambda5(this, z, 0));
    }

    public void setSourceFromQrCode(
            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata,
            SourceOriginForLogging sourceOriginForLogging) {
        Log.d(
                TAG,
                "setSourceFromQrCode(): broadcastId "
                        + bluetoothLeBroadcastMetadata.getBroadcastId());
        this.mSourceFromQrCode = bluetoothLeBroadcastMetadata;
        this.mSourceFromQrCodeOriginForLogging = sourceOriginForLogging;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void showToast(String str) {
        AudioSharingUtils.toastMessage(this.mContext, str);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    private AudioStreamPreference addNewPreference(
            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata,
            AudioStreamState audioStreamState,
            SourceOriginForLogging sourceOriginForLogging) {
        AudioStreamPreference audioStreamPreference =
                new AudioStreamPreference(this.mContext, null);
        audioStreamPreference.mIsEncrypted = bluetoothLeBroadcastMetadata.isEncrypted();
        audioStreamPreference.setTitle(
                AudioStreamsHelper.getBroadcastName(bluetoothLeBroadcastMetadata));
        AudioStreamPreference.AudioStream audioStream = new AudioStreamPreference.AudioStream();
        audioStream.mState = AudioStreamState.UNKNOWN;
        audioStream.mMetadata = bluetoothLeBroadcastMetadata;
        audioStream.mSourceOriginForLogging = sourceOriginForLogging;
        audioStreamPreference.mAudioStream = audioStream;
        moveToState(audioStreamPreference, audioStreamState);
        return audioStreamPreference;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}

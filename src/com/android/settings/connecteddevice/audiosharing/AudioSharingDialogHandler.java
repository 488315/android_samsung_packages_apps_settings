package com.android.settings.connecteddevice.audiosharing;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothLeBroadcast;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.content.Context;
import android.util.Log;
import android.util.Pair;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.core.view.DifferentialMotionFlingController$$ExternalSyntheticLambda0;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.bluetooth.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AudioSharingDialogHandler {
    public final LocalBluetoothLeBroadcastAssistant mAssistant;
    public final LocalBluetoothLeBroadcast mBroadcast;
    public final Context mContext;
    public final Fragment mHostFragment;
    public final LocalBluetoothManager mLocalBtManager;
    public final SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    public List mTargetSinks = new ArrayList();
    final BluetoothLeBroadcast.Callback mBroadcastCallback =
            new BluetoothLeBroadcast
                    .Callback() { // from class:
                                  // com.android.settings.connecteddevice.audiosharing.AudioSharingDialogHandler.1
                public final void onBroadcastMetadataChanged(
                        int i, BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
                    Log.d(
                            "AudioSharingDialogHandler",
                            "onBroadcastMetadataChanged(), broadcastId = "
                                    + i
                                    + ", metadata = "
                                    + bluetoothLeBroadcastMetadata);
                }

                public final void onBroadcastStartFailed(int i) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                            i, "onBroadcastStartFailed(), reason = ", "AudioSharingDialogHandler");
                    AudioSharingUtils.toastMessage(
                            AudioSharingDialogHandler.this.mContext,
                            "Fail to start broadcast, reason " + i);
                }

                public final void onBroadcastStarted(int i, int i2) {
                    MainClearConfirm$$ExternalSyntheticOutline0.m(
                            "onBroadcastStarted(), reason = ",
                            ", broadcastId = ",
                            i,
                            i2,
                            "AudioSharingDialogHandler");
                }

                public final void onBroadcastStopFailed(int i) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                            i, "onBroadcastStopFailed(), reason = ", "AudioSharingDialogHandler");
                    AudioSharingUtils.toastMessage(
                            AudioSharingDialogHandler.this.mContext,
                            "Fail to stop broadcast, reason " + i);
                }

                public final void onBroadcastStopped(int i, int i2) {
                    MainClearConfirm$$ExternalSyntheticOutline0.m(
                            "onBroadcastStopped(), reason = ",
                            ", broadcastId = ",
                            i,
                            i2,
                            "AudioSharingDialogHandler");
                }

                public final void onPlaybackStarted(int i, int i2) {
                    MainClearConfirm$$ExternalSyntheticOutline0.m(
                            "onPlaybackStarted(), reason = ",
                            ", broadcastId = ",
                            i,
                            i2,
                            "AudioSharingDialogHandler");
                    if (AudioSharingDialogHandler.this.mTargetSinks.isEmpty()) {
                        return;
                    }
                    AudioSharingDialogHandler audioSharingDialogHandler =
                            AudioSharingDialogHandler.this;
                    AudioSharingUtils.addSourceToTargetSinks(
                            audioSharingDialogHandler.mTargetSinks,
                            audioSharingDialogHandler.mLocalBtManager);
                    SubSettingLauncher subSettingLauncher =
                            new SubSettingLauncher(AudioSharingDialogHandler.this.mContext);
                    String name = AudioSharingDashboardFragment.class.getName();
                    SubSettingLauncher.LaunchRequest launchRequest =
                            subSettingLauncher.mLaunchRequest;
                    launchRequest.mDestinationName = name;
                    Fragment fragment = AudioSharingDialogHandler.this.mHostFragment;
                    launchRequest.mSourceMetricsCategory =
                            fragment instanceof DashboardFragment
                                    ? ((DashboardFragment) fragment).getMetricsCategory()
                                    : 0;
                    subSettingLauncher.launch();
                    AudioSharingDialogHandler.this.mTargetSinks = new ArrayList();
                }

                public final void onBroadcastUpdateFailed(int i, int i2) {}

                public final void onBroadcastUpdated(int i, int i2) {}

                public final void onPlaybackStopped(int i, int i2) {}
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.connecteddevice.audiosharing.AudioSharingDialogHandler$2, reason: invalid class name */
    public final class AnonymousClass2
            implements AudioSharingJoinDialogFragment.DialogEventListener {}

    public AudioSharingDialogHandler(Context context, Fragment fragment) {
        this.mContext = context;
        this.mHostFragment = fragment;
        LocalBluetoothManager localBluetoothManager = Utils.getLocalBluetoothManager(context);
        this.mLocalBtManager = localBluetoothManager;
        this.mBroadcast =
                localBluetoothManager != null
                        ? localBluetoothManager.mProfileManager.mLeAudioBroadcast
                        : null;
        this.mAssistant =
                localBluetoothManager != null
                        ? localBluetoothManager.mProfileManager.mLeAudioBroadcastAssistant
                        : null;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    public static void getCachedBluetoothDeviceFromDialog(Fragment fragment) {
        if (fragment instanceof AudioSharingJoinDialogFragment) {
            ((AudioSharingJoinDialogFragment) fragment).getClass();
        } else if (fragment instanceof AudioSharingStopDialogFragment) {
            ((AudioSharingStopDialogFragment) fragment).getClass();
        } else if (fragment instanceof AudioSharingDisconnectDialogFragment) {
            ((AudioSharingDisconnectDialogFragment) fragment).getClass();
        }
    }

    public final void closeOpeningDialogsForLeaDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        Fragment fragment = this.mHostFragment;
        if (fragment == null) {
            return;
        }
        AudioSharingUtils.getGroupId(cachedBluetoothDevice);
        Iterator it = fragment.getChildFragmentManager().mFragmentStore.getFragments().iterator();
        while (it.hasNext()) {
            getCachedBluetoothDeviceFromDialog((Fragment) it.next());
        }
    }

    public final void closeOpeningDialogsOtherThan(String str) {
        Fragment fragment = this.mHostFragment;
        if (fragment == null) {
            return;
        }
        for (Fragment fragment2 :
                fragment.getChildFragmentManager().mFragmentStore.getFragments()) {
            if ((fragment2 instanceof DialogFragment)
                    && fragment2.getTag() != null
                    && !fragment2.getTag().equals(str)) {
                Log.d(
                        "AudioSharingDialogHandler",
                        "Remove staled opening dialog " + fragment2.getTag());
                ((DialogFragment) fragment2).dismiss();
                final DialogFragment dialogFragment = (DialogFragment) fragment2;
                ThreadUtils.postOnBackgroundThread(
                        new Runnable() { // from class:
                                         // com.android.settings.connecteddevice.audiosharing.AudioSharingDialogHandler$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                int i;
                                AudioSharingDialogHandler audioSharingDialogHandler =
                                        AudioSharingDialogHandler.this;
                                Fragment fragment3 = dialogFragment;
                                audioSharingDialogHandler.getClass();
                                if (fragment3 instanceof AudioSharingJoinDialogFragment) {
                                    i =
                                            ((AudioSharingJoinDialogFragment) fragment3)
                                                    .getMetricsCategory();
                                } else if (fragment3 instanceof AudioSharingStopDialogFragment) {
                                    ((AudioSharingStopDialogFragment) fragment3).getClass();
                                    i = 2050;
                                } else if (fragment3
                                        instanceof AudioSharingDisconnectDialogFragment) {
                                    ((AudioSharingDisconnectDialogFragment) fragment3).getClass();
                                    i = 2051;
                                } else {
                                    i = 0;
                                }
                                audioSharingDialogHandler.mMetricsFeatureProvider.action(
                                        audioSharingDialogHandler.mContext, 1942, i);
                            }
                        });
            }
        }
    }

    public final void handleDeviceConnected(
            final CachedBluetoothDevice cachedBluetoothDevice, boolean z) {
        String anonymizedAddress = cachedBluetoothDevice.mDevice.getAnonymizedAddress();
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mBroadcast;
        boolean z2 = localBluetoothLeBroadcast != null && localBluetoothLeBroadcast.isEnabled(null);
        boolean anyMatch =
                cachedBluetoothDevice.getProfiles().stream()
                        .anyMatch(
                                new AudioSharingUtils$$ExternalSyntheticLambda1(
                                        cachedBluetoothDevice));
        LocalBluetoothManager localBluetoothManager = this.mLocalBtManager;
        if (!anyMatch) {
            DialogFragment$$ExternalSyntheticOutline0.m(
                    "Handle non LE audio device connected, device = ",
                    anonymizedAddress,
                    "AudioSharingDialogHandler");
            if (!z2) {
                if (z) {
                    cachedBluetoothDevice.setActive();
                }
                Log.d(
                        "AudioSharingDialogHandler",
                        "Ignore onProfileConnectionStateChanged for non LE audio without sharing"
                            + " session");
                return;
            } else {
                final List buildOrderedConnectedLeadAudioSharingDeviceItem =
                        AudioSharingUtils.buildOrderedConnectedLeadAudioSharingDeviceItem(
                                localBluetoothManager,
                                AudioSharingUtils.fetchConnectedDevicesByGroupId(
                                        localBluetoothManager),
                                true);
                final DifferentialMotionFlingController$$ExternalSyntheticLambda0
                        differentialMotionFlingController$$ExternalSyntheticLambda0 =
                                new DifferentialMotionFlingController$$ExternalSyntheticLambda0();
                final Pair[] buildAudioSharingDialogEventData =
                        AudioSharingUtils.buildAudioSharingDialogEventData(
                                z,
                                747,
                                2050,
                                buildOrderedConnectedLeadAudioSharingDeviceItem.size(),
                                0);
                final int i = 0;
                this.mContext
                        .getMainExecutor()
                        .execute(
                                new Runnable(
                                        this,
                                        buildOrderedConnectedLeadAudioSharingDeviceItem,
                                        cachedBluetoothDevice,
                                        differentialMotionFlingController$$ExternalSyntheticLambda0,
                                        buildAudioSharingDialogEventData,
                                        i) { // from class:
                                             // com.android.settings.connecteddevice.audiosharing.AudioSharingDialogHandler$$ExternalSyntheticLambda0
                                    public final /* synthetic */ int $r8$classId;
                                    public final /* synthetic */ AudioSharingDialogHandler f$0;

                                    {
                                        this.$r8$classId = i;
                                        this.f$0 = this;
                                    }

                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        int i2 = this.$r8$classId;
                                        AudioSharingDialogHandler audioSharingDialogHandler =
                                                this.f$0;
                                        audioSharingDialogHandler.getClass();
                                        switch (i2) {
                                            case 0:
                                                Pair[] pairArr =
                                                        AudioSharingStopDialogFragment.sEventData;
                                                audioSharingDialogHandler
                                                        .closeOpeningDialogsOtherThan(
                                                                "AudioSharingStopDialog");
                                                BluetoothAdapter.getDefaultAdapter();
                                                break;
                                            case 1:
                                                Pair[] pairArr2 =
                                                        AudioSharingDisconnectDialogFragment
                                                                .sEventData;
                                                audioSharingDialogHandler
                                                        .closeOpeningDialogsOtherThan(
                                                                "AudioSharingDisconnectDialog");
                                                BluetoothAdapter.getDefaultAdapter();
                                                break;
                                            case 2:
                                                Pair[] pairArr3 =
                                                        AudioSharingJoinDialogFragment.sEventData;
                                                audioSharingDialogHandler
                                                        .closeOpeningDialogsOtherThan(
                                                                "AudioSharingJoinDialog");
                                                BluetoothAdapter.getDefaultAdapter();
                                                break;
                                            default:
                                                Pair[] pairArr4 =
                                                        AudioSharingJoinDialogFragment.sEventData;
                                                audioSharingDialogHandler
                                                        .closeOpeningDialogsOtherThan(
                                                                "AudioSharingJoinDialog");
                                                BluetoothAdapter.getDefaultAdapter();
                                                break;
                                        }
                                    }
                                });
                return;
            }
        }
        Log.d(
                "AudioSharingDialogHandler",
                "Handle LE audio device connected, device = " + anonymizedAddress);
        Map fetchConnectedDevicesByGroupId =
                AudioSharingUtils.fetchConnectedDevicesByGroupId(localBluetoothManager);
        if (!z2) {
            final ArrayList arrayList = new ArrayList();
            Iterator it = ((HashMap) fetchConnectedDevicesByGroupId).values().iterator();
            while (it.hasNext()) {
                CachedBluetoothDevice cachedBluetoothDevice2 =
                        (CachedBluetoothDevice) ((List) it.next()).get(0);
                if (AudioSharingUtils.getGroupId(cachedBluetoothDevice2)
                        != AudioSharingUtils.getGroupId(cachedBluetoothDevice)) {
                    String name = cachedBluetoothDevice2.getName();
                    int groupId = AudioSharingUtils.getGroupId(cachedBluetoothDevice2);
                    boolean z3 = BluetoothUtils.DEBUG;
                    arrayList.add(
                            new AudioSharingDeviceItem(
                                    name, groupId, cachedBluetoothDevice2.isActiveDevice(22)));
                }
            }
            if (arrayList.size() != 1) {
                if (z) {
                    cachedBluetoothDevice.setActive();
                    return;
                }
                return;
            } else {
                final AnonymousClass2 anonymousClass2 = new AnonymousClass2();
                final Pair[] buildAudioSharingDialogEventData2 =
                        AudioSharingUtils.buildAudioSharingDialogEventData(z, 747, 2049, 0, 2);
                final int i2 = 3;
                this.mContext
                        .getMainExecutor()
                        .execute(
                                new Runnable(
                                        this,
                                        arrayList,
                                        cachedBluetoothDevice,
                                        anonymousClass2,
                                        buildAudioSharingDialogEventData2,
                                        i2) { // from class:
                                              // com.android.settings.connecteddevice.audiosharing.AudioSharingDialogHandler$$ExternalSyntheticLambda0
                                    public final /* synthetic */ int $r8$classId;
                                    public final /* synthetic */ AudioSharingDialogHandler f$0;

                                    {
                                        this.$r8$classId = i2;
                                        this.f$0 = this;
                                    }

                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        int i22 = this.$r8$classId;
                                        AudioSharingDialogHandler audioSharingDialogHandler =
                                                this.f$0;
                                        audioSharingDialogHandler.getClass();
                                        switch (i22) {
                                            case 0:
                                                Pair[] pairArr =
                                                        AudioSharingStopDialogFragment.sEventData;
                                                audioSharingDialogHandler
                                                        .closeOpeningDialogsOtherThan(
                                                                "AudioSharingStopDialog");
                                                BluetoothAdapter.getDefaultAdapter();
                                                break;
                                            case 1:
                                                Pair[] pairArr2 =
                                                        AudioSharingDisconnectDialogFragment
                                                                .sEventData;
                                                audioSharingDialogHandler
                                                        .closeOpeningDialogsOtherThan(
                                                                "AudioSharingDisconnectDialog");
                                                BluetoothAdapter.getDefaultAdapter();
                                                break;
                                            case 2:
                                                Pair[] pairArr3 =
                                                        AudioSharingJoinDialogFragment.sEventData;
                                                audioSharingDialogHandler
                                                        .closeOpeningDialogsOtherThan(
                                                                "AudioSharingJoinDialog");
                                                BluetoothAdapter.getDefaultAdapter();
                                                break;
                                            default:
                                                Pair[] pairArr4 =
                                                        AudioSharingJoinDialogFragment.sEventData;
                                                audioSharingDialogHandler
                                                        .closeOpeningDialogsOtherThan(
                                                                "AudioSharingJoinDialog");
                                                BluetoothAdapter.getDefaultAdapter();
                                                break;
                                        }
                                    }
                                });
                return;
            }
        }
        int groupId2 = AudioSharingUtils.getGroupId(cachedBluetoothDevice);
        HashMap hashMap = (HashMap) fetchConnectedDevicesByGroupId;
        if (hashMap.containsKey(Integer.valueOf(groupId2))
                && ((List) hashMap.get(Integer.valueOf(groupId2)))
                        .stream()
                                .anyMatch(
                                        new Predicate() { // from class:
                                                          // com.android.settings.connecteddevice.audiosharing.AudioSharingDialogHandler$$ExternalSyntheticLambda2
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                return BluetoothUtils.hasConnectedBroadcastSource(
                                                        (CachedBluetoothDevice) obj,
                                                        AudioSharingDialogHandler.this
                                                                .mLocalBtManager);
                                            }
                                        })) {
            Log.d(
                    "AudioSharingDialogHandler",
                    "Automatically add another device within the same group to the sharing: "
                            + cachedBluetoothDevice.mDevice.getAnonymizedAddress());
            LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant = this.mAssistant;
            if (localBluetoothLeBroadcastAssistant == null || localBluetoothLeBroadcast == null) {
                return;
            }
            localBluetoothLeBroadcastAssistant.addSource(
                    cachedBluetoothDevice.mDevice,
                    localBluetoothLeBroadcast.getLatestBluetoothLeBroadcastMetadata(),
                    false);
            return;
        }
        final List buildOrderedConnectedLeadAudioSharingDeviceItem2 =
                AudioSharingUtils.buildOrderedConnectedLeadAudioSharingDeviceItem(
                        localBluetoothManager, fetchConnectedDevicesByGroupId, true);
        if (buildOrderedConnectedLeadAudioSharingDeviceItem2.size() >= 2) {
            final AudioSharingDialogHandler$$ExternalSyntheticLambda3
                    audioSharingDialogHandler$$ExternalSyntheticLambda3 =
                            new AudioSharingDialogHandler$$ExternalSyntheticLambda3();
            final Pair[] buildAudioSharingDialogEventData3 =
                    AudioSharingUtils.buildAudioSharingDialogEventData(
                            z,
                            747,
                            2051,
                            buildOrderedConnectedLeadAudioSharingDeviceItem2.size(),
                            1);
            final int i3 = 1;
            this.mContext
                    .getMainExecutor()
                    .execute(
                            new Runnable(
                                    this,
                                    buildOrderedConnectedLeadAudioSharingDeviceItem2,
                                    cachedBluetoothDevice,
                                    audioSharingDialogHandler$$ExternalSyntheticLambda3,
                                    buildAudioSharingDialogEventData3,
                                    i3) { // from class:
                                          // com.android.settings.connecteddevice.audiosharing.AudioSharingDialogHandler$$ExternalSyntheticLambda0
                                public final /* synthetic */ int $r8$classId;
                                public final /* synthetic */ AudioSharingDialogHandler f$0;

                                {
                                    this.$r8$classId = i3;
                                    this.f$0 = this;
                                }

                                @Override // java.lang.Runnable
                                public final void run() {
                                    int i22 = this.$r8$classId;
                                    AudioSharingDialogHandler audioSharingDialogHandler = this.f$0;
                                    audioSharingDialogHandler.getClass();
                                    switch (i22) {
                                        case 0:
                                            Pair[] pairArr =
                                                    AudioSharingStopDialogFragment.sEventData;
                                            audioSharingDialogHandler.closeOpeningDialogsOtherThan(
                                                    "AudioSharingStopDialog");
                                            BluetoothAdapter.getDefaultAdapter();
                                            break;
                                        case 1:
                                            Pair[] pairArr2 =
                                                    AudioSharingDisconnectDialogFragment.sEventData;
                                            audioSharingDialogHandler.closeOpeningDialogsOtherThan(
                                                    "AudioSharingDisconnectDialog");
                                            BluetoothAdapter.getDefaultAdapter();
                                            break;
                                        case 2:
                                            Pair[] pairArr3 =
                                                    AudioSharingJoinDialogFragment.sEventData;
                                            audioSharingDialogHandler.closeOpeningDialogsOtherThan(
                                                    "AudioSharingJoinDialog");
                                            BluetoothAdapter.getDefaultAdapter();
                                            break;
                                        default:
                                            Pair[] pairArr4 =
                                                    AudioSharingJoinDialogFragment.sEventData;
                                            audioSharingDialogHandler.closeOpeningDialogsOtherThan(
                                                    "AudioSharingJoinDialog");
                                            BluetoothAdapter.getDefaultAdapter();
                                            break;
                                    }
                                }
                            });
            return;
        }
        final AnonymousClass2 anonymousClass22 = new AnonymousClass2();
        final Pair[] buildAudioSharingDialogEventData4 =
                AudioSharingUtils.buildAudioSharingDialogEventData(
                        z, 747, 2086, buildOrderedConnectedLeadAudioSharingDeviceItem2.size(), 1);
        final int i4 = 2;
        this.mContext
                .getMainExecutor()
                .execute(
                        new Runnable(
                                this,
                                buildOrderedConnectedLeadAudioSharingDeviceItem2,
                                cachedBluetoothDevice,
                                anonymousClass22,
                                buildAudioSharingDialogEventData4,
                                i4) { // from class:
                                      // com.android.settings.connecteddevice.audiosharing.AudioSharingDialogHandler$$ExternalSyntheticLambda0
                            public final /* synthetic */ int $r8$classId;
                            public final /* synthetic */ AudioSharingDialogHandler f$0;

                            {
                                this.$r8$classId = i4;
                                this.f$0 = this;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                int i22 = this.$r8$classId;
                                AudioSharingDialogHandler audioSharingDialogHandler = this.f$0;
                                audioSharingDialogHandler.getClass();
                                switch (i22) {
                                    case 0:
                                        Pair[] pairArr = AudioSharingStopDialogFragment.sEventData;
                                        audioSharingDialogHandler.closeOpeningDialogsOtherThan(
                                                "AudioSharingStopDialog");
                                        BluetoothAdapter.getDefaultAdapter();
                                        break;
                                    case 1:
                                        Pair[] pairArr2 =
                                                AudioSharingDisconnectDialogFragment.sEventData;
                                        audioSharingDialogHandler.closeOpeningDialogsOtherThan(
                                                "AudioSharingDisconnectDialog");
                                        BluetoothAdapter.getDefaultAdapter();
                                        break;
                                    case 2:
                                        Pair[] pairArr3 = AudioSharingJoinDialogFragment.sEventData;
                                        audioSharingDialogHandler.closeOpeningDialogsOtherThan(
                                                "AudioSharingJoinDialog");
                                        BluetoothAdapter.getDefaultAdapter();
                                        break;
                                    default:
                                        Pair[] pairArr4 = AudioSharingJoinDialogFragment.sEventData;
                                        audioSharingDialogHandler.closeOpeningDialogsOtherThan(
                                                "AudioSharingJoinDialog");
                                        BluetoothAdapter.getDefaultAdapter();
                                        break;
                                }
                            }
                        });
    }

    public final void registerCallbacks(Executor executor) {
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mBroadcast;
        if (localBluetoothLeBroadcast != null) {
            localBluetoothLeBroadcast.registerServiceCallBack(executor, this.mBroadcastCallback);
        }
    }

    public final void unregisterCallbacks() {
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mBroadcast;
        if (localBluetoothLeBroadcast != null) {
            localBluetoothLeBroadcast.unregisterServiceCallBack(this.mBroadcastCallback);
        }
    }
}

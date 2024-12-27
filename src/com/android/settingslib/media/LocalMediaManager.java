package com.android.settingslib.media;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioDeviceAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.MediaRoute2Info;
import android.media.RouteListingPreference;
import android.media.RoutingSessionInfo;
import android.media.session.MediaController;
import android.os.Process;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settingslib.bluetooth.A2dpProfile;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.CachedBluetoothDeviceManager;
import com.android.settingslib.bluetooth.HearingAidProfile;
import com.android.settingslib.bluetooth.LeAudioProfile;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LocalMediaManager implements BluetoothCallback {

    @VisibleForTesting AudioManager mAudioManager;

    @VisibleForTesting BluetoothAdapter mBluetoothAdapter;
    public final Context mContext;

    @VisibleForTesting MediaDevice mCurrentConnectedDevice;
    public final InfoMediaManager mInfoMediaManager;
    public final LocalBluetoothManager mLocalBluetoothManager;
    public MediaDevice mOnTransferBluetoothDevice;
    public final String mPackageName;
    public final Collection mCallbacks = new CopyOnWriteArrayList();
    public final Object mMediaDevicesLock = new Object();

    @VisibleForTesting final MediaDeviceCallback mMediaDeviceCallback = new MediaDeviceCallback();

    @VisibleForTesting List<MediaDevice> mMediaDevices = new CopyOnWriteArrayList();

    @VisibleForTesting List<MediaDevice> mDisconnectedMediaDevices = new CopyOnWriteArrayList();

    @VisibleForTesting
    DeviceAttributeChangeCallback mDeviceAttributeChangeCallback =
            new DeviceAttributeChangeCallback();

    public final Receiver mReceiver = new Receiver();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @VisibleForTesting
    class DeviceAttributeChangeCallback implements CachedBluetoothDevice.Callback {
        public DeviceAttributeChangeCallback() {}

        @Override // com.android.settingslib.bluetooth.CachedBluetoothDevice.Callback
        public final void onDeviceAttributesChanged() {
            LocalMediaManager localMediaManager = LocalMediaManager.this;
            MediaDevice mediaDevice = localMediaManager.mOnTransferBluetoothDevice;
            if (mediaDevice != null
                    && !((BluetoothMediaDevice) mediaDevice).mCachedDevice.isBusy()
                    && !localMediaManager.mOnTransferBluetoothDevice.isConnected()) {
                localMediaManager.mOnTransferBluetoothDevice.mState = 3;
                localMediaManager.mOnTransferBluetoothDevice = null;
                Iterator it = new CopyOnWriteArrayList(localMediaManager.mCallbacks).iterator();
                while (it.hasNext()) {
                    ((DeviceCallback) it.next()).onRequestFailed(0);
                }
            }
            Iterator it2 = new CopyOnWriteArrayList(localMediaManager.mCallbacks).iterator();
            while (it2.hasNext()) {
                ((DeviceCallback) it2.next()).onDeviceAttributesChanged();
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MediaDeviceCallback {
        public MediaDeviceCallback() {}

        public static boolean isMediaDevice(CachedBluetoothDevice cachedBluetoothDevice) {
            Iterator it = ((ArrayList) cachedBluetoothDevice.getConnectableProfiles()).iterator();
            while (it.hasNext()) {
                LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) it.next();
                if ((localBluetoothProfile instanceof A2dpProfile)
                        || (localBluetoothProfile instanceof HearingAidProfile)
                        || (localBluetoothProfile instanceof LeAudioProfile)) {
                    return true;
                }
            }
            return false;
        }

        public final List buildDisconnectedBluetoothDevice() {
            LocalMediaManager localMediaManager = LocalMediaManager.this;
            BluetoothAdapter bluetoothAdapter = localMediaManager.mBluetoothAdapter;
            if (bluetoothAdapter == null) {
                Log.w(
                        "LocalMediaManager",
                        "buildDisconnectedBluetoothDevice() BluetoothAdapter is null");
                return new ArrayList();
            }
            List mostRecentlyConnectedDevices = bluetoothAdapter.getMostRecentlyConnectedDevices();
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager =
                    localMediaManager.mLocalBluetoothManager.mCachedDeviceManager;
            ArrayList arrayList = new ArrayList();
            Iterator it = mostRecentlyConnectedDevices.iterator();
            int i = 0;
            while (it.hasNext()) {
                CachedBluetoothDevice findDevice =
                        cachedBluetoothDeviceManager.findDevice((BluetoothDevice) it.next());
                if (findDevice != null
                        && findDevice.mBondState == 12
                        && !findDevice.isConnected()
                        && isMediaDevice(findDevice)) {
                    i++;
                    arrayList.add(findDevice);
                    if (i >= 5) {
                        break;
                    }
                }
            }
            Iterator<MediaDevice> it2 = localMediaManager.mDisconnectedMediaDevices.iterator();
            while (it2.hasNext()) {
                ((BluetoothMediaDevice) it2.next())
                        .mCachedDevice.unregisterCallback(
                                localMediaManager.mDeviceAttributeChangeCallback);
            }
            localMediaManager.mDisconnectedMediaDevices.clear();
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) it3.next();
                BluetoothMediaDevice bluetoothMediaDevice =
                        new BluetoothMediaDevice(
                                localMediaManager.mContext, cachedBluetoothDevice, null);
                if (!localMediaManager.mMediaDevices.contains(bluetoothMediaDevice)) {
                    cachedBluetoothDevice.registerCallback(
                            localMediaManager.mDeviceAttributeChangeCallback);
                    localMediaManager.mDisconnectedMediaDevices.add(bluetoothMediaDevice);
                }
            }
            return new ArrayList(localMediaManager.mDisconnectedMediaDevices);
        }

        public final BluetoothMediaDevice getMutingExpectedDevice() {
            LocalMediaManager localMediaManager = LocalMediaManager.this;
            if (localMediaManager.mBluetoothAdapter != null
                    && localMediaManager.mAudioManager.getMutingExpectedDevice() != null) {
                List mostRecentlyConnectedDevices =
                        localMediaManager.mBluetoothAdapter.getMostRecentlyConnectedDevices();
                CachedBluetoothDeviceManager cachedBluetoothDeviceManager =
                        localMediaManager.mLocalBluetoothManager.mCachedDeviceManager;
                Iterator it = mostRecentlyConnectedDevices.iterator();
                while (it.hasNext()) {
                    CachedBluetoothDevice findDevice =
                            cachedBluetoothDeviceManager.findDevice((BluetoothDevice) it.next());
                    if (findDevice != null
                            && findDevice.mBondState == 12
                            && !findDevice.isConnected()
                            && isMediaDevice(findDevice)) {
                        AudioDeviceAttributes mutingExpectedDevice =
                                localMediaManager.mAudioManager.getMutingExpectedDevice();
                        if (mutingExpectedDevice != null
                                ? findDevice
                                        .mDevice
                                        .getAddress()
                                        .equals(mutingExpectedDevice.getAddress())
                                : false) {
                            return new BluetoothMediaDevice(
                                    localMediaManager.mContext, findDevice, null);
                        }
                    }
                }
            }
            return null;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Receiver extends BroadcastReceiver {
        public Receiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            MediaDevice updateCurrentConnectedDevice;
            String action = intent.getAction();
            if ((!"android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED".equals(action)
                            && !"android.bluetooth.action.LE_AUDIO_ACTIVE_DEVICE_CHANGED"
                                    .equals(action))
                    || (updateCurrentConnectedDevice =
                                    LocalMediaManager.this.updateCurrentConnectedDevice())
                            == null
                    || updateCurrentConnectedDevice.equals(
                            LocalMediaManager.this.mCurrentConnectedDevice)) {
                return;
            }
            LocalMediaManager localMediaManager = LocalMediaManager.this;
            localMediaManager.mCurrentConnectedDevice = updateCurrentConnectedDevice;
            localMediaManager.dispatchSelectedDeviceStateChanged(updateCurrentConnectedDevice);
        }
    }

    public LocalMediaManager(Context context, String str) {
        InfoMediaManager noOpInfoMediaManager;
        this.mContext = context;
        this.mPackageName = str;
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(context, null);
        this.mLocalBluetoothManager = localBluetoothManager;
        this.mAudioManager = (AudioManager) context.getSystemService(AudioManager.class);
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (localBluetoothManager == null) {
            Log.e("LocalMediaManager", "Bluetooth is not supported on this device");
            return;
        }
        int i = InfoMediaManager.$r8$clinit;
        str = TextUtils.isEmpty(str) ? context.getPackageName() : str;
        UserHandle myUserHandle = Process.myUserHandle();
        try {
            noOpInfoMediaManager =
                    new RouterInfoMediaManager(
                            context, str, myUserHandle, localBluetoothManager, null);
        } catch (InfoMediaManager.PackageNotAvailableException unused) {
            Log.w("InfoMediaManager", "Returning a no-op InfoMediaManager for package " + str);
            noOpInfoMediaManager =
                    new NoOpInfoMediaManager(
                            context, str, myUserHandle, localBluetoothManager, null);
        }
        this.mInfoMediaManager = noOpInfoMediaManager;
        Receiver receiver = this.mReceiver;
        receiver.getClass();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED");
        intentFilter.addAction("android.bluetooth.action.LE_AUDIO_ACTIVE_DEVICE_CHANGED");
        LocalMediaManager.this.mContext.registerReceiver(receiver, intentFilter);
    }

    public final void adjustSessionVolume(int i, String str) {
        InfoMediaManager infoMediaManager = this.mInfoMediaManager;
        RoutingSessionInfo routingSessionById = infoMediaManager.getRoutingSessionById(str);
        if (routingSessionById != null) {
            infoMediaManager.setSessionVolume(routingSessionById, i);
        } else {
            MotionLayout$$ExternalSyntheticOutline0.m(
                    "adjustSessionVolume: Unable to find session: ", str, "LocalMediaManager");
        }
    }

    public final void dispatchSelectedDeviceStateChanged(MediaDevice mediaDevice) {
        Iterator it = new CopyOnWriteArrayList(this.mCallbacks).iterator();
        while (it.hasNext()) {
            ((DeviceCallback) it.next()).onSelectedDeviceStateChanged(mediaDevice, 0);
        }
    }

    public final MediaDevice getCurrentConnectedDevice() {
        return this.mCurrentConnectedDevice;
    }

    public final MediaDevice getMediaDeviceById(String str) {
        synchronized (this.mMediaDevicesLock) {
            try {
                for (MediaDevice mediaDevice : this.mMediaDevices) {
                    if (TextUtils.equals(mediaDevice.getId(), str)) {
                        return mediaDevice;
                    }
                }
                AbsAdapter$$ExternalSyntheticOutline0.m(
                        "getMediaDeviceById() failed to find device with id: ",
                        str,
                        "LocalMediaManager");
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isActiveDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        int semGetCurrentDeviceType = this.mAudioManager.semGetCurrentDeviceType();
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        A2dpProfile a2dpProfile = localBluetoothManager.mProfileManager.mA2dpProfile;
        boolean z =
                a2dpProfile != null
                        && semGetCurrentDeviceType == 8
                        && cachedBluetoothDevice.mDevice.equals(a2dpProfile.getActiveDevice());
        LocalBluetoothProfileManager localBluetoothProfileManager =
                localBluetoothManager.mProfileManager;
        HearingAidProfile hearingAidProfile = localBluetoothProfileManager.mHearingAidProfile;
        boolean z2 =
                hearingAidProfile != null
                        && semGetCurrentDeviceType == 23
                        && hearingAidProfile
                                .getActiveDevices()
                                .contains(cachedBluetoothDevice.mDevice);
        LeAudioProfile leAudioProfile = localBluetoothProfileManager.mLeAudioProfile;
        return z
                || z2
                || (leAudioProfile != null
                        && ((semGetCurrentDeviceType == 26
                                        || semGetCurrentDeviceType == 27
                                        || semGetCurrentDeviceType == 30)
                                && leAudioProfile
                                        .getActiveDevices()
                                        .contains(cachedBluetoothDevice.mDevice)));
    }

    public final void registerCallback(DeviceCallback deviceCallback) {
        boolean isEmpty = ((CopyOnWriteArrayList) this.mCallbacks).isEmpty();
        if (((CopyOnWriteArrayList) this.mCallbacks).contains(deviceCallback)) {
            return;
        }
        ((CopyOnWriteArrayList) this.mCallbacks).add(deviceCallback);
        if (isEmpty) {
            MediaDeviceCallback mediaDeviceCallback = this.mMediaDeviceCallback;
            InfoMediaManager infoMediaManager = this.mInfoMediaManager;
            boolean isEmpty2 = ((CopyOnWriteArrayList) infoMediaManager.mCallbacks).isEmpty();
            if (((CopyOnWriteArrayList) infoMediaManager.mCallbacks)
                    .contains(mediaDeviceCallback)) {
                return;
            }
            ((CopyOnWriteArrayList) infoMediaManager.mCallbacks).add(mediaDeviceCallback);
            if (isEmpty2) {
                ((CopyOnWriteArrayList) infoMediaManager.mMediaDevices).clear();
                infoMediaManager.registerRouter();
                MediaController mediaController = infoMediaManager.mMediaController;
                if (mediaController != null) {
                    mediaController.registerCallback(infoMediaManager.mMediaControllerCallback);
                }
                RouteListingPreference routeListingPreference =
                        infoMediaManager.getRouteListingPreference();
                ConcurrentHashMap concurrentHashMap =
                        (ConcurrentHashMap) infoMediaManager.mPreferenceItemMap;
                concurrentHashMap.clear();
                if (routeListingPreference != null) {
                    routeListingPreference
                            .getItems()
                            .forEach(
                                    new InfoMediaManager$Api34Impl$$ExternalSyntheticLambda0(
                                            concurrentHashMap));
                }
                infoMediaManager.refreshDevices();
            }
        }
    }

    public final boolean shouldEnableVolumeSeekBar(RoutingSessionInfo routingSessionInfo) {
        this.mInfoMediaManager.getClass();
        return routingSessionInfo.isSystemSession() || routingSessionInfo.getVolumeHandling() != 0;
    }

    public final void unregisterCallback(DeviceCallback deviceCallback) {
        if (((CopyOnWriteArrayList) this.mCallbacks).remove(deviceCallback)
                && ((CopyOnWriteArrayList) this.mCallbacks).isEmpty()) {
            MediaDeviceCallback mediaDeviceCallback = this.mMediaDeviceCallback;
            InfoMediaManager infoMediaManager = this.mInfoMediaManager;
            if (((CopyOnWriteArrayList) infoMediaManager.mCallbacks).remove(mediaDeviceCallback)
                    && ((CopyOnWriteArrayList) infoMediaManager.mCallbacks).isEmpty()) {
                MediaController mediaController = infoMediaManager.mMediaController;
                if (mediaController != null) {
                    mediaController.unregisterCallback(infoMediaManager.mMediaControllerCallback);
                }
                infoMediaManager.unregisterRouter();
            }
            Iterator<MediaDevice> it = this.mDisconnectedMediaDevices.iterator();
            while (it.hasNext()) {
                ((BluetoothMediaDevice) it.next())
                        .mCachedDevice.unregisterCallback(this.mDeviceAttributeChangeCallback);
            }
        }
    }

    @VisibleForTesting
    public MediaDevice updateCurrentConnectedDevice() {
        MediaRoute2Info mediaRoute2Info;
        synchronized (this.mMediaDevicesLock) {
            try {
                int semGetCurrentDeviceType = this.mAudioManager.semGetCurrentDeviceType();
                Log.i(
                        "LocalMediaManager",
                        "updateCurrentConnectedDevice curDeviceType = " + semGetCurrentDeviceType);
                MediaDevice mediaDevice = null;
                for (MediaDevice mediaDevice2 : this.mMediaDevices) {
                    if ((mediaDevice2 instanceof BluetoothMediaDevice)
                            && (semGetCurrentDeviceType == 8
                                    || semGetCurrentDeviceType == 23
                                    || semGetCurrentDeviceType == 26
                                    || semGetCurrentDeviceType == 27
                                    || semGetCurrentDeviceType == 30)) {
                        if (isActiveDevice(((BluetoothMediaDevice) mediaDevice2).mCachedDevice)
                                && mediaDevice2.isConnected()
                                && (mediaRoute2Info = mediaDevice2.mRouteInfo) != null
                                && mediaRoute2Info.getType() == semGetCurrentDeviceType) {
                            Log.i(
                                    "LocalMediaManager",
                                    "updateCurrentConnectedDevice device name = "
                                            + ((BluetoothMediaDevice) mediaDevice2)
                                                    .mCachedDevice.getName()
                                            + " device type = "
                                            + mediaDevice2.mType);
                            return mediaDevice2;
                        }
                    } else if ((mediaDevice2 instanceof PhoneMediaDevice)
                            && AudioDeviceInfo.convertDeviceTypeToInternalDevice(
                                            semGetCurrentDeviceType)
                                    == mediaDevice2.getDevice()) {
                        Log.i(
                                "LocalMediaManager",
                                "updateCurrentConnectedDevice device name = "
                                        + mediaDevice2.getName()
                                        + " device type = "
                                        + mediaDevice2.mType);
                        mediaDevice = mediaDevice2;
                    }
                }
                return mediaDevice;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface DeviceCallback {
        void onDeviceListUpdate(List list);

        default void onDeviceAttributesChanged() {}

        default void onRequestFailed(int i) {}

        default void onSelectedDeviceStateChanged(MediaDevice mediaDevice, int i) {}
    }
}

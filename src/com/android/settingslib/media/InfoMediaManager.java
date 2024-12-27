package com.android.settingslib.media;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2Manager;
import android.media.RouteListingPreference;
import android.media.RoutingSessionInfo;
import android.media.session.MediaController;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;

import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class InfoMediaManager {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final LocalBluetoothManager mBluetoothManager;
    public final Context mContext;
    public MediaController.PlaybackInfo mLastKnownPlaybackInfo;
    public MediaController mMediaController;
    public final String mPackageName;
    public final MediaRouter2Manager mRouterManager;
    public final UserHandle mUserHandle;
    public final List mMediaDevices = new CopyOnWriteArrayList();
    public final Collection mCallbacks = new CopyOnWriteArrayList();
    public final Map mPreferenceItemMap = new ConcurrentHashMap();
    public final MediaControllerCallback mMediaControllerCallback = new MediaControllerCallback();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MediaControllerCallback extends MediaController.Callback {
        public MediaControllerCallback() {}

        @Override // android.media.session.MediaController.Callback
        public final void onAudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
            if (playbackInfo.getPlaybackType()
                            != InfoMediaManager.this.mLastKnownPlaybackInfo.getPlaybackType()
                    || !TextUtils.equals(
                            playbackInfo.getVolumeControlId(),
                            InfoMediaManager.this.mLastKnownPlaybackInfo.getVolumeControlId())) {
                InfoMediaManager.this.refreshDevices();
            }
            InfoMediaManager.this.mLastKnownPlaybackInfo = playbackInfo;
        }

        @Override // android.media.session.MediaController.Callback
        public final void onSessionDestroyed() {
            InfoMediaManager infoMediaManager = InfoMediaManager.this;
            infoMediaManager.mMediaController = null;
            infoMediaManager.refreshDevices();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class PackageNotAvailableException extends Exception {
        public PackageNotAvailableException(String str) {
            super(str);
        }
    }

    static {
        Log.isLoggable("InfoMediaManager", 3);
    }

    public InfoMediaManager(
            Context context,
            String str,
            UserHandle userHandle,
            LocalBluetoothManager localBluetoothManager,
            MediaController mediaController) {
        this.mContext = context;
        this.mBluetoothManager = localBluetoothManager;
        this.mMediaController = mediaController;
        if (mediaController != null) {
            this.mLastKnownPlaybackInfo = mediaController.getPlaybackInfo();
        }
        this.mRouterManager = MediaRouter2Manager.getInstance(context);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:25:0x01a6  */
    /* JADX WARN: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v16, types: [com.android.settingslib.media.BluetoothMediaDevice] */
    /* JADX WARN: Type inference failed for: r10v3, types: [com.android.settingslib.media.MediaDevice] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.settingslib.media.MediaDevice] */
    @com.android.internal.annotations.VisibleForTesting
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void addMediaDevice(
            android.media.MediaRoute2Info r9, android.media.RoutingSessionInfo r10) {
        /*
            Method dump skipped, instructions count: 470
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.media.InfoMediaManager.addMediaDevice(android.media.MediaRoute2Info,"
                    + " android.media.RoutingSessionInfo):void");
    }

    public final void dispatchDeviceListAdded(List list) {
        Iterator it = new CopyOnWriteArrayList(this.mCallbacks).iterator();
        while (it.hasNext()) {
            LocalMediaManager.MediaDeviceCallback mediaDeviceCallback =
                    (LocalMediaManager.MediaDeviceCallback) it.next();
            ArrayList arrayList = new ArrayList(list);
            synchronized (LocalMediaManager.this.mMediaDevicesLock) {
                try {
                    LocalMediaManager.this.mMediaDevices.clear();
                    LocalMediaManager.this.mMediaDevices.addAll(arrayList);
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        int i = ((MediaDevice) it2.next()).mType;
                        if (i == 2 || i == 3 || i == 1) {
                            PackageManager packageManager =
                                    LocalMediaManager.this.mContext.getPackageManager();
                            if (!packageManager.hasSystemFeature("android.hardware.type.television")
                                    && !packageManager.hasSystemFeature(
                                            "android.software.leanback")) {
                                BluetoothMediaDevice mutingExpectedDevice =
                                        mediaDeviceCallback.getMutingExpectedDevice();
                                if (mutingExpectedDevice != null) {
                                    LocalMediaManager.this.mMediaDevices.add(mutingExpectedDevice);
                                }
                            }
                            LocalMediaManager.this.mMediaDevices.addAll(
                                    mediaDeviceCallback.buildDisconnectedBluetoothDevice());
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            LocalMediaManager.this.mInfoMediaManager.getClass();
            LocalMediaManager localMediaManager = LocalMediaManager.this;
            localMediaManager.mCurrentConnectedDevice =
                    localMediaManager.updateCurrentConnectedDevice();
            LocalMediaManager localMediaManager2 = LocalMediaManager.this;
            localMediaManager2.getClass();
            ArrayList arrayList2 = new ArrayList(localMediaManager2.mMediaDevices);
            Iterator it3 = new CopyOnWriteArrayList(localMediaManager2.mCallbacks).iterator();
            while (it3.hasNext()) {
                ((LocalMediaManager.DeviceCallback) it3.next()).onDeviceListUpdate(arrayList2);
            }
            MediaDevice mediaDevice = LocalMediaManager.this.mOnTransferBluetoothDevice;
            if (mediaDevice != null && mediaDevice.isConnected()) {
                LocalMediaManager localMediaManager3 = LocalMediaManager.this;
                MediaDevice mediaDevice2 = localMediaManager3.mOnTransferBluetoothDevice;
                MediaDevice mediaDeviceById =
                        localMediaManager3.getMediaDeviceById(mediaDevice2.getId());
                if (mediaDeviceById == null) {
                    Log.w("LocalMediaManager", "connectDevice() connectDevice not in the list!");
                } else {
                    if (mediaDeviceById instanceof BluetoothMediaDevice) {
                        CachedBluetoothDevice cachedBluetoothDevice =
                                ((BluetoothMediaDevice) mediaDeviceById).mCachedDevice;
                        if (!cachedBluetoothDevice.isConnected()
                                && !cachedBluetoothDevice.isBusy()) {
                            localMediaManager3.mOnTransferBluetoothDevice = mediaDevice2;
                            mediaDeviceById.mState = 1;
                            cachedBluetoothDevice.connect$1();
                        } else if (cachedBluetoothDevice.isConnected()) {
                            mediaDeviceById.mState = 0;
                            mediaDeviceById.mAudioManager.setDeviceToForceByUser(
                                    mediaDeviceById.getDevice(),
                                    mediaDeviceById.getAddress(),
                                    false);
                            localMediaManager3.mCurrentConnectedDevice = mediaDeviceById;
                            localMediaManager3.dispatchSelectedDeviceStateChanged(mediaDeviceById);
                        }
                    }
                    if (mediaDeviceById.equals(localMediaManager3.mCurrentConnectedDevice)) {
                        Log.d(
                                "LocalMediaManager",
                                "connectDevice() this device is already connected! : "
                                        + mediaDeviceById.getName());
                    } else {
                        mediaDeviceById.mState = 1;
                        if (mediaDeviceById instanceof PhoneMediaDevice) {
                            mediaDeviceById.mAudioManager.setDeviceToForceByUser(
                                    mediaDeviceById.getDevice(),
                                    mediaDeviceById.getAddress(),
                                    false);
                            mediaDeviceById.mState = 0;
                            localMediaManager3.mCurrentConnectedDevice = mediaDeviceById;
                            localMediaManager3.dispatchSelectedDeviceStateChanged(mediaDeviceById);
                        } else {
                            InfoMediaManager infoMediaManager =
                                    localMediaManager3.mInfoMediaManager;
                            infoMediaManager.getClass();
                            if (mediaDeviceById.mRouteInfo == null) {
                                Log.w("InfoMediaManager", "Unable to connect. RouteInfo is empty");
                            } else {
                                mediaDeviceById.mConnectedRecord++;
                                ConnectionRecordManager connectionRecordManager =
                                        ConnectionRecordManager.getInstance();
                                Context context = mediaDeviceById.mContext;
                                String id = mediaDeviceById.getId();
                                int i2 = mediaDeviceById.mConnectedRecord;
                                synchronized (connectionRecordManager) {
                                    SharedPreferences.Editor edit =
                                            context.getSharedPreferences(
                                                            "seamless_transfer_record", 0)
                                                    .edit();
                                    connectionRecordManager.mLastSelectedDevice = id;
                                    edit.putInt(id, i2);
                                    edit.putString(
                                            "last_selected_device",
                                            connectionRecordManager.mLastSelectedDevice);
                                    edit.apply();
                                }
                                infoMediaManager.transferToRoute(mediaDeviceById.mRouteInfo);
                            }
                        }
                    }
                }
                LocalMediaManager localMediaManager4 = LocalMediaManager.this;
                MediaDevice mediaDevice3 = localMediaManager4.mOnTransferBluetoothDevice;
                mediaDevice3.mState = 0;
                localMediaManager4.dispatchSelectedDeviceStateChanged(mediaDevice3);
                LocalMediaManager.this.mOnTransferBluetoothDevice = null;
            }
        }
    }

    public abstract List getRemoteSessions();

    public abstract RouteListingPreference getRouteListingPreference();

    public abstract RoutingSessionInfo getRoutingSessionById(String str);

    public final synchronized void refreshDevices() {
        Iterator it = this.mRouterManager.getAllRoutes().iterator();
        while (it.hasNext()) {
            addMediaDevice((MediaRoute2Info) it.next(), null);
        }
        dispatchDeviceListAdded(this.mMediaDevices);
    }

    public abstract void registerRouter();

    public abstract void setSessionVolume(RoutingSessionInfo routingSessionInfo, int i);

    public abstract void startScanOnRouter();

    public abstract void stopScanOnRouter();

    public abstract void transferToRoute(MediaRoute2Info mediaRoute2Info);

    public abstract void unregisterRouter();
}

package com.android.settingslib.media;

import android.content.Context;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2;
import android.media.MediaRouter2Manager;
import android.media.RouteDiscoveryPreference;
import android.media.RouteListingPreference;
import android.media.RoutingSessionInfo;
import android.media.session.MediaController;
import android.os.UserHandle;
import android.text.TextUtils;

import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;

import com.android.media.flags.Flags;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class RouterInfoMediaManager extends InfoMediaManager {
    public final ControllerCallback mControllerCallback;
    public final Executor mExecutor;
    public final RouteCallback mRouteCallback;
    public final RouterInfoMediaManager$$ExternalSyntheticLambda0 mRouteListingPreferenceCallback;
    public final MediaRouter2 mRouter;
    public final MediaRouter2Manager mRouterManager;
    public MediaRouter2.ScanToken mScanToken;
    public final TransferCallback mTransferCallback;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ControllerCallback extends MediaRouter2.ControllerCallback {
        public ControllerCallback() {}

        @Override // android.media.MediaRouter2.ControllerCallback
        public final void onControllerUpdated(MediaRouter2.RoutingController routingController) {
            RouterInfoMediaManager.this.refreshDevices();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RouteCallback extends MediaRouter2.RouteCallback {
        public RouteCallback() {}

        public final void onPreferredFeaturesChanged(List list) {
            RouterInfoMediaManager.this.refreshDevices();
        }

        @Override // android.media.MediaRouter2.RouteCallback
        public final void onRoutesUpdated(List list) {
            RouterInfoMediaManager.this.refreshDevices();
        }
    }

    /* JADX WARN: Type inference failed for: r4v5, types: [com.android.settingslib.media.RouterInfoMediaManager$$ExternalSyntheticLambda0] */
    public RouterInfoMediaManager(
            Context context,
            String str,
            UserHandle userHandle,
            LocalBluetoothManager localBluetoothManager,
            MediaController mediaController) {
        super(context, str, userHandle, localBluetoothManager, mediaController);
        MediaRouter2 mediaRouter2;
        this.mExecutor = Executors.newSingleThreadExecutor();
        this.mRouteCallback = new RouteCallback();
        this.mTransferCallback = new TransferCallback();
        this.mControllerCallback = new ControllerCallback();
        this.mRouteListingPreferenceCallback =
                new Consumer() { // from class:
                                 // com.android.settingslib.media.RouterInfoMediaManager$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        RouterInfoMediaManager routerInfoMediaManager = RouterInfoMediaManager.this;
                        RouteListingPreference routeListingPreference =
                                (RouteListingPreference) obj;
                        ConcurrentHashMap concurrentHashMap =
                                (ConcurrentHashMap) routerInfoMediaManager.mPreferenceItemMap;
                        concurrentHashMap.clear();
                        if (routeListingPreference != null) {
                            routeListingPreference
                                    .getItems()
                                    .forEach(
                                            new InfoMediaManager$Api34Impl$$ExternalSyntheticLambda0(
                                                    concurrentHashMap));
                        }
                        routerInfoMediaManager.refreshDevices();
                    }
                };
        if (Flags.enableCrossUserRoutingInMediaRouter2()) {
            try {
                mediaRouter2 = MediaRouter2.getInstance(context, str, userHandle);
            } catch (IllegalArgumentException unused) {
                mediaRouter2 = null;
            }
        } else {
            mediaRouter2 = MediaRouter2.getInstance(context, str);
        }
        if (mediaRouter2 == null) {
            throw new InfoMediaManager.PackageNotAvailableException(
                    ComposerKt$$ExternalSyntheticOutline0.m(
                            "Package name ", str, " does not exist."));
        }
        this.mRouter = mediaRouter2;
        this.mRouterManager = MediaRouter2Manager.getInstance(context);
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final List getRemoteSessions() {
        return this.mRouterManager.getRemoteSessions();
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final RouteListingPreference getRouteListingPreference() {
        return this.mRouter.getRouteListingPreference();
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final RoutingSessionInfo getRoutingSessionById(String str) {
        for (RoutingSessionInfo routingSessionInfo : this.mRouterManager.getRemoteSessions()) {
            if (TextUtils.equals(routingSessionInfo.getId(), str)) {
                return routingSessionInfo;
            }
        }
        RoutingSessionInfo systemRoutingSession =
                this.mRouterManager.getSystemRoutingSession((String) null);
        if (TextUtils.equals(systemRoutingSession.getId(), str)) {
            return systemRoutingSession;
        }
        return null;
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void registerRouter() {
        this.mRouter.registerRouteCallback(
                this.mExecutor, this.mRouteCallback, RouteDiscoveryPreference.EMPTY);
        this.mRouter.registerRouteListingPreferenceUpdatedCallback(
                this.mExecutor, this.mRouteListingPreferenceCallback);
        this.mRouter.registerTransferCallback(this.mExecutor, this.mTransferCallback);
        this.mRouter.registerControllerCallback(this.mExecutor, this.mControllerCallback);
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void setSessionVolume(RoutingSessionInfo routingSessionInfo, int i) {
        this.mRouterManager.setSessionVolume(routingSessionInfo, i);
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void startScanOnRouter() {
        if (!Flags.enableScreenOffScanning()) {
            this.mRouter.startScan();
            return;
        }
        synchronized (this) {
            try {
                if (this.mScanToken == null) {
                    this.mScanToken =
                            this.mRouter.requestScan(
                                    new MediaRouter2.ScanRequest.Builder().build());
                }
            } finally {
            }
        }
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void stopScanOnRouter() {
        if (!Flags.enableScreenOffScanning()) {
            this.mRouter.stopScan();
            return;
        }
        synchronized (this) {
            try {
                MediaRouter2.ScanToken scanToken = this.mScanToken;
                if (scanToken != null) {
                    this.mRouter.cancelScanRequest(scanToken);
                    this.mScanToken = null;
                }
            } finally {
            }
        }
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void transferToRoute(MediaRoute2Info mediaRoute2Info) {
        this.mRouter.transferTo(mediaRoute2Info);
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void unregisterRouter() {
        this.mRouter.unregisterControllerCallback(this.mControllerCallback);
        this.mRouter.unregisterTransferCallback(this.mTransferCallback);
        this.mRouter.unregisterRouteListingPreferenceUpdatedCallback(
                this.mRouteListingPreferenceCallback);
        this.mRouter.unregisterRouteCallback(this.mRouteCallback);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class TransferCallback extends MediaRouter2.TransferCallback {
        public TransferCallback() {}

        public final void onRequestFailed(int i) {
            RouterInfoMediaManager routerInfoMediaManager = RouterInfoMediaManager.this;
            routerInfoMediaManager.getClass();
            Iterator it = new CopyOnWriteArrayList(routerInfoMediaManager.mCallbacks).iterator();
            while (it.hasNext()) {
                LocalMediaManager.MediaDeviceCallback mediaDeviceCallback =
                        (LocalMediaManager.MediaDeviceCallback) it.next();
                synchronized (LocalMediaManager.this.mMediaDevicesLock) {
                    try {
                        for (MediaDevice mediaDevice : LocalMediaManager.this.mMediaDevices) {
                            if (mediaDevice.mState == 1) {
                                mediaDevice.mState = 3;
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                LocalMediaManager localMediaManager = LocalMediaManager.this;
                localMediaManager.getClass();
                Iterator it2 = new CopyOnWriteArrayList(localMediaManager.mCallbacks).iterator();
                while (it2.hasNext()) {
                    ((LocalMediaManager.DeviceCallback) it2.next()).onRequestFailed(i);
                }
            }
        }

        @Override // android.media.MediaRouter2.TransferCallback
        public final void onStop(MediaRouter2.RoutingController routingController) {
            RouterInfoMediaManager.this.refreshDevices();
        }

        @Override // android.media.MediaRouter2.TransferCallback
        public final void onTransfer(
                MediaRouter2.RoutingController routingController,
                MediaRouter2.RoutingController routingController2) {
            RouterInfoMediaManager routerInfoMediaManager = RouterInfoMediaManager.this;
            Iterator it =
                    ((InfoMediaManager) routerInfoMediaManager)
                            .mRouterManager
                            .getAllRoutes()
                            .iterator();
            while (it.hasNext()) {
                routerInfoMediaManager.addMediaDevice((MediaRoute2Info) it.next(), null);
            }
            RouterInfoMediaManager routerInfoMediaManager2 = RouterInfoMediaManager.this;
            routerInfoMediaManager2.getClass();
            Iterator it2 = new CopyOnWriteArrayList(routerInfoMediaManager2.mCallbacks).iterator();
            while (it2.hasNext()) {
                LocalMediaManager localMediaManager = LocalMediaManager.this;
                MediaDevice mediaDeviceById = localMediaManager.getMediaDeviceById(null);
                if (mediaDeviceById == null) {
                    mediaDeviceById = localMediaManager.updateCurrentConnectedDevice();
                }
                localMediaManager.mCurrentConnectedDevice = mediaDeviceById;
                if (mediaDeviceById != null) {
                    mediaDeviceById.mState = 0;
                    localMediaManager.dispatchSelectedDeviceStateChanged(mediaDeviceById);
                }
            }
        }

        @Override // android.media.MediaRouter2.TransferCallback
        public final void onTransferFailure(MediaRoute2Info mediaRoute2Info) {}
    }
}

package com.android.settingslib.media;

import android.media.MediaRoute2Info;
import android.media.MediaRouter2Manager;
import android.media.RouteListingPreference;
import android.media.RoutingSessionInfo;
import android.util.Log;

import com.android.internal.annotations.VisibleForTesting;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ManagerInfoMediaManager extends InfoMediaManager {
    public static final boolean DEBUG = Log.isLoggable("ManagerInfoMediaManager", 3);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @VisibleForTesting
    final class RouterManagerCallback implements MediaRouter2Manager.Callback {
        public final void onPreferredFeaturesChanged(String str, List list) {
            throw null;
        }

        public final void onRequestFailed(int i) {
            throw null;
        }

        public final void onRouteListingPreferenceUpdated(
                String str, RouteListingPreference routeListingPreference) {
            throw null;
        }

        public final void onRoutesUpdated() {
            throw null;
        }

        public final void onSessionReleased(RoutingSessionInfo routingSessionInfo) {
            throw null;
        }

        public final void onSessionUpdated(RoutingSessionInfo routingSessionInfo) {
            throw null;
        }

        public final void onTransferred(
                RoutingSessionInfo routingSessionInfo, RoutingSessionInfo routingSessionInfo2) {
            if (ManagerInfoMediaManager.DEBUG) {
                Log.d(
                        "ManagerInfoMediaManager",
                        "onTransferred() oldSession : "
                                + ((Object) routingSessionInfo.getName())
                                + ", newSession : "
                                + ((Object) routingSessionInfo2.getName()));
            }
            throw null;
        }

        public final void onTransferFailed(
                RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) {}
    }
}

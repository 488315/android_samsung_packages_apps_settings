package com.android.settingslib.media;

import android.media.MediaRoute2Info;
import android.media.RouteListingPreference;
import android.media.RoutingSessionInfo;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NoOpInfoMediaManager extends InfoMediaManager {
    static {
        new RoutingSessionInfo.Builder("FAKE_ROUTING_SESSION", ApnSettings.MVNO_NONE)
                .addSelectedRoute("FAKE_SELECTED_ROUTE_ID")
                .setVolumeMax(-1)
                .setVolume(-1)
                .build();
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final List getRemoteSessions() {
        return Collections.emptyList();
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final RouteListingPreference getRouteListingPreference() {
        return null;
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final RoutingSessionInfo getRoutingSessionById(String str) {
        return null;
    }

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void registerRouter() {}

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void startScanOnRouter() {}

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void stopScanOnRouter() {}

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void unregisterRouter() {}

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void transferToRoute(MediaRoute2Info mediaRoute2Info) {}

    @Override // com.android.settingslib.media.InfoMediaManager
    public final void setSessionVolume(RoutingSessionInfo routingSessionInfo, int i) {}
}

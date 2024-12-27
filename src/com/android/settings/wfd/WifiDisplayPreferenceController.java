package com.android.settings.wfd;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaRouter;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiDisplayPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    private Preference mPreference;
    private final MediaRouter mRouter;
    private final MediaRouter.Callback mRouterCallback;

    public WifiDisplayPreferenceController(Context context, String str) {
        super(context, str);
        this.mRouterCallback =
                new MediaRouter
                        .SimpleCallback() { // from class:
                                            // com.android.settings.wfd.WifiDisplayPreferenceController.1
                    @Override // android.media.MediaRouter.SimpleCallback,
                              // android.media.MediaRouter.Callback
                    public final void onRouteAdded(
                            MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
                        WifiDisplayPreferenceController wifiDisplayPreferenceController =
                                WifiDisplayPreferenceController.this;
                        wifiDisplayPreferenceController.refreshSummary(
                                wifiDisplayPreferenceController.mPreference);
                    }

                    @Override // android.media.MediaRouter.SimpleCallback,
                              // android.media.MediaRouter.Callback
                    public final void onRouteChanged(
                            MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
                        WifiDisplayPreferenceController wifiDisplayPreferenceController =
                                WifiDisplayPreferenceController.this;
                        wifiDisplayPreferenceController.refreshSummary(
                                wifiDisplayPreferenceController.mPreference);
                    }

                    @Override // android.media.MediaRouter.SimpleCallback,
                              // android.media.MediaRouter.Callback
                    public final void onRouteRemoved(
                            MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
                        WifiDisplayPreferenceController wifiDisplayPreferenceController =
                                WifiDisplayPreferenceController.this;
                        wifiDisplayPreferenceController.refreshSummary(
                                wifiDisplayPreferenceController.mPreference);
                    }

                    @Override // android.media.MediaRouter.SimpleCallback,
                              // android.media.MediaRouter.Callback
                    public final void onRouteSelected(
                            MediaRouter mediaRouter, int i, MediaRouter.RouteInfo routeInfo) {
                        WifiDisplayPreferenceController wifiDisplayPreferenceController =
                                WifiDisplayPreferenceController.this;
                        wifiDisplayPreferenceController.refreshSummary(
                                wifiDisplayPreferenceController.mPreference);
                    }

                    @Override // android.media.MediaRouter.SimpleCallback,
                              // android.media.MediaRouter.Callback
                    public final void onRouteUnselected(
                            MediaRouter mediaRouter, int i, MediaRouter.RouteInfo routeInfo) {
                        WifiDisplayPreferenceController wifiDisplayPreferenceController =
                                WifiDisplayPreferenceController.this;
                        wifiDisplayPreferenceController.refreshSummary(
                                wifiDisplayPreferenceController.mPreference);
                    }
                };
        MediaRouter mediaRouter = (MediaRouter) context.getSystemService(MediaRouter.class);
        this.mRouter = mediaRouter;
        mediaRouter.setRouterGroupId("android.media.mirroring_group");
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return WifiDisplaySettings.isAvailable(this.mContext) ? 0 : 3;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        String string = this.mContext.getString(R.string.disconnected);
        int routeCount = this.mRouter.getRouteCount();
        for (int i = 0; i < routeCount; i++) {
            MediaRouter.RouteInfo routeAt = this.mRouter.getRouteAt(i);
            if (routeAt.matchesTypes(4) && routeAt.isSelected() && !routeAt.isConnecting()) {
                CharSequence status = routeAt.getStatus();
                return !TextUtils.isEmpty(status)
                        ? status
                        : this.mContext.getString(R.string.wifi_display_status_connected);
            }
        }
        return string;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mRouter.addCallback(4, this.mRouterCallback);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mRouter.removeCallback(this.mRouterCallback);
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
}

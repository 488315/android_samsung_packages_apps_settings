package com.android.settings.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.PreferenceScreen;

import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.core.lifecycle.LifecycleObserver;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class LocationInjectedServiceBasePreferenceController
        extends LocationBasePreferenceController implements LifecycleObserver {
    static final IntentFilter INTENT_FILTER_INJECTED_SETTING_CHANGED =
            new IntentFilter("android.location.InjectedSettingChanged");
    private static final String TAG = "LocationPrefCtrl";
    BroadcastReceiver mInjectedSettingsReceiver;
    AppSettingsInjector mInjector;

    public LocationInjectedServiceBasePreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        injectLocationServices(preferenceScreen);
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.location.LocationBasePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    /* JADX WARN: Can't wrap try/catch for region: R(7:28|(1:61)(3:32|(1:60)(2:34|(1:59)(2:36|(1:58)(2:38|(1:57))))|45)|40|41|(2:43|44)(2:46|47)|45|26) */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0146, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x014e, code lost:

       android.util.Log.w("SettingsInjector", "Unable to load service info " + r12, r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0148, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x015e, code lost:

       android.util.Log.w("SettingsInjector", "Unable to load service info " + r12, r0);
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v35, types: [com.android.settingslib.widget.AppPreference] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.Map<java.lang.Integer, java.util.List<androidx.preference.Preference>>
            getLocationServices() {
        /*
            Method dump skipped, instructions count: 585
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.location.LocationInjectedServiceBasePreferenceController.getLocationServices():java.util.Map");
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.location.LocationBasePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.location.LocationBasePreferenceController
    public void init(DashboardFragment dashboardFragment) {
        super.init(dashboardFragment);
        this.mInjector = new AppSettingsInjector(this.mContext, getMetricsCategory());
    }

    public abstract void injectLocationServices(PreferenceScreen preferenceScreen);

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.location.LocationEnabler.LocationModeChangeListener
    public void onLocationModeChanged(int i, boolean z) {
        this.mInjector.reloadStatusMessages();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        this.mContext.unregisterReceiver(this.mInjectedSettingsReceiver);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        if (this.mInjectedSettingsReceiver == null) {
            this.mInjectedSettingsReceiver =
                    new BroadcastReceiver() { // from class:
                                              // com.android.settings.location.LocationInjectedServiceBasePreferenceController.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            if (Log.isLoggable(
                                    LocationInjectedServiceBasePreferenceController.TAG, 3)) {
                                Log.d(
                                        LocationInjectedServiceBasePreferenceController.TAG,
                                        "Received settings change intent: " + intent);
                            }
                            LocationInjectedServiceBasePreferenceController.this.mInjector
                                    .reloadStatusMessages();
                        }
                    };
        }
        this.mContext.registerReceiver(
                this.mInjectedSettingsReceiver, INTENT_FILTER_INJECTED_SETTING_CHANGED, 2);
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}

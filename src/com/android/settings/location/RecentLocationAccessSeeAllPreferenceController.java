package com.android.settings.location;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.applications.RecentAppOpsAccess;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.widget.AppPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RecentLocationAccessSeeAllPreferenceController
        extends LocationBasePreferenceController {
    private PreferenceScreen mCategoryAllRecentLocationAccess;
    private MetricsFeatureProvider mMetricsFeatureProvider;
    private Preference mPreference;
    private final RecentAppOpsAccess mRecentLocationAccesses;
    private boolean mShowSystem;

    public RecentLocationAccessSeeAllPreferenceController(Context context, String str) {
        super(context, str);
        boolean z = false;
        this.mShowSystem = false;
        if (DeviceConfig.getBoolean("privacy", "location_indicators_small_enabled", false)
                && Settings.Secure.getInt(
                                this.mContext.getContentResolver(), "locationShowSystemOps", 0)
                        == 1) {
            z = true;
        }
        this.mShowSystem = z;
        this.mRecentLocationAccesses = RecentAppOpsAccess.createForLocation(context);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mCategoryAllRecentLocationAccess =
                (PreferenceScreen) preferenceScreen.findPreference(getPreferenceKey());
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
        this.mCategoryAllRecentLocationAccess.setEnabled(this.mLocationEnabler.isEnabled(i));
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void setShowSystem(boolean z) {
        this.mShowSystem = z;
        Preference preference = this.mPreference;
        if (preference != null) {
            updateState(preference);
            this.mMetricsFeatureProvider.logClickedPreference(
                    this.mPreference, getMetricsCategory());
        }
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        this.mCategoryAllRecentLocationAccess.removeAll();
        this.mPreference = preference;
        UserManager userManager = UserManager.get(this.mContext);
        ArrayList arrayList = new ArrayList();
        List<RecentAppOpsAccess.Access> appList =
                this.mRecentLocationAccesses.getAppList(this.mShowSystem);
        Collections.sort(
                appList, Collections.reverseOrder(new RecentAppOpsAccess.AnonymousClass1()));
        for (RecentAppOpsAccess.Access access : appList) {
            if (RecentLocationAccessPreferenceController.isRequestMatchesProfileType(
                    userManager, access, 7)) {
                arrayList.add(access);
            }
        }
        if (arrayList.isEmpty()) {
            AppPreference appPreference = new AppPreference(this.mContext);
            appPreference.setTitle(R.string.location_no_recent_apps);
            appPreference.setSelectable(false);
            this.mCategoryAllRecentLocationAccess.addPreference(appPreference);
            return;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            this.mCategoryAllRecentLocationAccess.addPreference(
                    RecentLocationAccessPreferenceController.createAppPreference(
                            preference.getContext(),
                            (RecentAppOpsAccess.Access) it.next(),
                            this.mFragment));
        }
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}

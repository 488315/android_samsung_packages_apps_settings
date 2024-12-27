package com.android.settings.location;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserManager;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.location.RecentLocationApps;
import com.android.settingslib.widget.AppPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RecentLocationRequestSeeAllPreferenceController
        extends LocationBasePreferenceController {
    private PreferenceScreen mCategoryAllRecentLocationRequests;
    private Preference mPreference;
    private RecentLocationApps mRecentLocationApps;
    private boolean mShowSystem;
    private int mType;

    public RecentLocationRequestSeeAllPreferenceController(Context context, String str) {
        super(context, str);
        this.mShowSystem = false;
        this.mType = 7;
        this.mRecentLocationApps = new RecentLocationApps(context);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mCategoryAllRecentLocationRequests =
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
        this.mCategoryAllRecentLocationRequests.setEnabled(this.mLocationEnabler.isEnabled(i));
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void setProfileType(int i) {
        this.mType = i;
    }

    public void setShowSystem(boolean z) {
        this.mShowSystem = z;
        Preference preference = this.mPreference;
        if (preference != null) {
            updateState(preference);
        }
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        this.mCategoryAllRecentLocationRequests.removeAll();
        this.mPreference = preference;
        UserManager userManager = UserManager.get(this.mContext);
        ArrayList arrayList = new ArrayList();
        Iterator it =
                ((ArrayList) this.mRecentLocationApps.getAppListSorted(this.mShowSystem))
                        .iterator();
        while (it.hasNext()) {
            RecentLocationApps.Request request = (RecentLocationApps.Request) it.next();
            if (RecentLocationRequestPreferenceController.isRequestMatchesProfileType(
                    userManager, request, this.mType)) {
                arrayList.add(request);
            }
        }
        if (arrayList.isEmpty()) {
            AppPreference appPreference = new AppPreference(this.mContext);
            appPreference.setTitle(R.string.location_no_recent_apps);
            appPreference.setSelectable(false);
            this.mCategoryAllRecentLocationRequests.addPreference(appPreference);
            return;
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            this.mCategoryAllRecentLocationRequests.addPreference(
                    RecentLocationRequestPreferenceController.createAppPreference(
                            preference.getContext(),
                            (RecentLocationApps.Request) it2.next(),
                            this.mFragment));
        }
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}

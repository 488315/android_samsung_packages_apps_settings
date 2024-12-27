package com.samsung.android.settings.privacy;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.provider.SearchIndexableData;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.privacy.PrivacyDashboardOverViewChartPreference.PermissionInfoTask;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class PrivacyDashboardChartPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    private static final String PRIVACY_DASHBOARD_PKG = "com.samsung.android.privacydashboard";
    private PrivacyDashboardOverViewChartPreference mPreference;

    public PrivacyDashboardChartPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (PrivacyDashboardOverViewChartPreference)
                        preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return !Utils.isPackageEnabled(this.mContext, PRIVACY_DASHBOARD_PKG) ? 3 : 0;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        PrivacyDashboardOverViewChartPreference.PermissionInfoTask permissionInfoTask;
        PrivacyDashboardOverViewChartPreference privacyDashboardOverViewChartPreference =
                this.mPreference;
        if (privacyDashboardOverViewChartPreference == null
                || (permissionInfoTask =
                                privacyDashboardOverViewChartPreference.mPermissionInfoTask)
                        == null) {
            return;
        }
        permissionInfoTask.cancel(true);
        privacyDashboardOverViewChartPreference.mPermissionInfoTask = null;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        PrivacyDashboardOverViewChartPreference privacyDashboardOverViewChartPreference =
                this.mPreference;
        if (privacyDashboardOverViewChartPreference != null) {
            if (privacyDashboardOverViewChartPreference.mUpdateFlag) {
                privacyDashboardOverViewChartPreference.updateAppIcons();
            }
            if (privacyDashboardOverViewChartPreference.mPermissionInfoTask == null) {
                privacyDashboardOverViewChartPreference.mPermissionInfoTask =
                        privacyDashboardOverViewChartPreference.new PermissionInfoTask();
            }
            if (SecurityDashboardUtils.isChinaModel()) {
                privacyDashboardOverViewChartPreference.mPermissionInfoTask.executeOnExecutor(
                        AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
            } else {
                privacyDashboardOverViewChartPreference.mPermissionInfoTask.execute(new Void[0]);
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public void updateRawDataToIndex(List<SearchIndexableRaw> list) {
        if (getAvailabilityStatus() != 0) {
            return;
        }
        SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(this.mContext);
        ((SearchIndexableData) searchIndexableRaw).key = getPreferenceKey();
        searchIndexableRaw.title =
                this.mContext.getString(R.string.sec_privacy_summary_permission_usage);
        ((SearchIndexableData) searchIndexableRaw).intentAction =
                "com.samsung.android.intent.action.REVIEW_PERMISSION_USAGE";
        ((SearchIndexableData) searchIndexableRaw).intentTargetPackage = PRIVACY_DASHBOARD_PKG;
        ((SearchIndexableData) searchIndexableRaw).intentTargetClass =
                "com.samsung.android.privacydashboard.views.MainActivity";
        list.add(searchIndexableRaw);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}

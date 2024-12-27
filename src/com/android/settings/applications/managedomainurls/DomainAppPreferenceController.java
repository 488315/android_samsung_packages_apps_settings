package com.android.settings.applications.managedomainurls;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;

import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.applications.AppInfoBase;
import com.android.settings.applications.intentpicker.AppLaunchSettings;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.applications.ApplicationsState;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DomainAppPreferenceController extends BasePreferenceController
        implements ApplicationsState.Callbacks {
    private static final int INSTALLED_APP_DETAILS = 1;
    private ApplicationsState mApplicationsState;
    private PreferenceGroup mDomainAppList;
    private ManageDomainUrls mFragment;
    private int mMetricsCategory;
    private Map<String, Preference> mPreferenceCache;
    private ApplicationsState.Session mSession;

    public DomainAppPreferenceController(Context context, String str) {
        super(context, str);
        this.mApplicationsState =
                ApplicationsState.getInstance((Application) this.mContext.getApplicationContext());
    }

    private void cacheAllPrefs(PreferenceGroup preferenceGroup) {
        this.mPreferenceCache = new ArrayMap();
        int preferenceCount = preferenceGroup.getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            Preference preference = preferenceGroup.getPreference(i);
            if (!TextUtils.isEmpty(preference.getKey())) {
                this.mPreferenceCache.put(preference.getKey(), preference);
            }
        }
    }

    private Preference getCachedPreference(String str) {
        Map<String, Preference> map = this.mPreferenceCache;
        if (map != null) {
            return map.remove(str);
        }
        return null;
    }

    private void rebuild() {
        this.mSession.rebuild(
                ApplicationsState.FILTER_WITH_DOMAIN_URLS,
                ApplicationsState.ALPHA_COMPARATOR,
                true);
    }

    private void rebuildAppList(
            PreferenceGroup preferenceGroup, ArrayList<ApplicationsState.AppEntry> arrayList) {
        cacheAllPrefs(preferenceGroup);
        int size = arrayList.size();
        Context context = preferenceGroup.getContext();
        for (int i = 0; i < size; i++) {
            ApplicationsState.AppEntry appEntry = arrayList.get(i);
            String str = appEntry.info.packageName + "|" + appEntry.info.uid;
            if (!SemPersonaManager.isSecureFolderId(UserHandle.myUserId())
                    || SemPersonaManager.isSecureFolderId(
                            UserHandle.getUserId(appEntry.info.uid))) {
                DomainAppPreference domainAppPreference =
                        (DomainAppPreference) getCachedPreference(str);
                if (domainAppPreference == null) {
                    domainAppPreference = new DomainAppPreference(context, appEntry);
                    domainAppPreference.setKey(str);
                    preferenceGroup.addPreference(domainAppPreference);
                } else {
                    domainAppPreference.setState$2();
                    domainAppPreference.notifyChanged();
                }
                domainAppPreference.setOrder(i);
            }
        }
        removeCachedPrefs(preferenceGroup);
    }

    private void removeCachedPrefs(PreferenceGroup preferenceGroup) {
        Iterator<Preference> it = this.mPreferenceCache.values().iterator();
        while (it.hasNext()) {
            preferenceGroup.removePreference(it.next());
        }
        this.mPreferenceCache = null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mDomainAppList = (PreferenceGroup) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!(preference instanceof DomainAppPreference)) {
            return false;
        }
        ApplicationsState.AppEntry appEntry = ((DomainAppPreference) preference).mEntry;
        String string = this.mContext.getString(R.string.auto_launch_label);
        ApplicationInfo applicationInfo = appEntry.info;
        AppInfoBase.startAppInfoFragment(
                AppLaunchSettings.class,
                string,
                applicationInfo.packageName,
                applicationInfo.uid,
                this.mFragment,
                1,
                this.mMetricsCategory);
        return true;
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

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onLoadEntriesCompleted() {
        rebuild();
    }

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onRebuildComplete(ArrayList<ApplicationsState.AppEntry> arrayList) {
        Context context = this.mContext;
        if (context == null) {
            return;
        }
        AppUtils.preloadTopIcons(
                context,
                arrayList,
                context.getResources().getInteger(R.integer.config_num_visible_app_icons));
        rebuildAppList(this.mDomainAppList, arrayList);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setFragment(ManageDomainUrls manageDomainUrls) {
        this.mFragment = manageDomainUrls;
        manageDomainUrls.getClass();
        this.mMetricsCategory = 143;
        this.mSession =
                this.mApplicationsState.newSession(this, this.mFragment.getSettingsLifecycle());
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

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onAllSizesComputed() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onLauncherInfoChanged() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onPackageIconChanged() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onPackageListChanged() {}

    public /* bridge */ /* synthetic */ void onDisabledAppCheckCompleted(Boolean bool) {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onPackageSizeChanged(String str) {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onRunningStateChanged(boolean z) {}
}

package com.android.settings.applications.specialaccess.notificationaccess;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.VersionedPackage;
import android.service.notification.NotificationListenerFilter;
import android.util.Log;

import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.applications.AppStateBaseBridge;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.widget.AppCheckBoxPreference;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.applications.ApplicationsState;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BridgedAppsPreferenceController extends BasePreferenceController
        implements LifecycleObserver, ApplicationsState.Callbacks, AppStateBaseBridge.Callback {
    private ApplicationsState mApplicationsState;
    private ComponentName mCn;
    private ApplicationsState.AppFilter mFilter;
    private NotificationListenerFilter mNlf;
    private NotificationBackend mNm;
    private PreferenceScreen mScreen;
    private ApplicationsState.Session mSession;
    private int mUserId;

    public BridgedAppsPreferenceController(Context context, String str) {
        super(context, str);
    }

    private void removeUselessPrefs(Set<String> set) {
        int preferenceCount = this.mScreen.getPreferenceCount();
        if (preferenceCount > 0) {
            for (int i = preferenceCount - 1; i >= 0; i--) {
                Preference preference = this.mScreen.getPreference(i);
                if (!set.contains(preference.getKey())) {
                    this.mScreen.removePreference(preference);
                }
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        this.mScreen = preferenceScreen;
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

    @Override // com.android.settings.applications.AppStateBaseBridge.Callback
    public void onExtraInfoUpdated() {
        rebuild();
    }

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onLoadEntriesCompleted() {
        rebuild();
    }

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onPackageIconChanged() {
        rebuild();
    }

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onPackageListChanged() {
        rebuild();
    }

    public boolean onPreferenceChange(Preference preference, Object obj) {
        if (!(preference instanceof CheckBoxPreference)) {
            return false;
        }
        String substring = preference.getKey().substring(0, preference.getKey().indexOf("|"));
        int parseInt =
                Integer.parseInt(
                        preference.getKey().substring(preference.getKey().indexOf("|") + 1));
        boolean z = obj == Boolean.TRUE;
        NotificationBackend notificationBackend = this.mNm;
        ComponentName componentName = this.mCn;
        int i = this.mUserId;
        notificationBackend.getClass();
        NotificationListenerFilter listenerFilter =
                NotificationBackend.getListenerFilter(componentName, i);
        this.mNlf = listenerFilter;
        if (z) {
            listenerFilter.removePackage(new VersionedPackage(substring, parseInt));
        } else {
            listenerFilter.addPackage(new VersionedPackage(substring, parseInt));
        }
        NotificationBackend notificationBackend2 = this.mNm;
        ComponentName componentName2 = this.mCn;
        int i2 = this.mUserId;
        NotificationListenerFilter notificationListenerFilter = this.mNlf;
        notificationBackend2.getClass();
        try {
            NotificationBackend.sINM.setListenerFilter(
                    componentName2, i2, notificationListenerFilter);
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
        }
        return true;
    }

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onRebuildComplete(ArrayList<ApplicationsState.AppEntry> arrayList) {
        if (arrayList == null) {
            return;
        }
        NotificationBackend notificationBackend = this.mNm;
        ComponentName componentName = this.mCn;
        int i = this.mUserId;
        notificationBackend.getClass();
        this.mNlf = NotificationBackend.getListenerFilter(componentName, i);
        TreeSet treeSet = new TreeSet();
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            ApplicationsState.AppEntry appEntry = arrayList.get(i2);
            String str = appEntry.info.packageName + "|" + appEntry.info.uid;
            treeSet.add(str);
            AppCheckBoxPreference appCheckBoxPreference =
                    (AppCheckBoxPreference) this.mScreen.findPreference(str);
            if (appCheckBoxPreference == null) {
                appCheckBoxPreference = new AppCheckBoxPreference(this.mScreen.getContext());
                appCheckBoxPreference.setIcon(AppUtils.getIcon(this.mContext, appEntry));
                appCheckBoxPreference.setTitle(appEntry.label);
                appCheckBoxPreference.setKey(str);
                this.mScreen.addPreference(appCheckBoxPreference);
            }
            appCheckBoxPreference.setOrder(i2);
            NotificationListenerFilter notificationListenerFilter = this.mNlf;
            ApplicationInfo applicationInfo = appEntry.info;
            appCheckBoxPreference.setChecked(
                    notificationListenerFilter.isPackageAllowed(
                            new VersionedPackage(
                                    applicationInfo.packageName, applicationInfo.uid)));
            appCheckBoxPreference.setOnPreferenceChangeListener(
                    new Preference
                            .OnPreferenceChangeListener() { // from class:
                                                            // com.android.settings.applications.specialaccess.notificationaccess.BridgedAppsPreferenceController$$ExternalSyntheticLambda0
                        @Override // androidx.preference.Preference.OnPreferenceChangeListener
                        public final boolean onPreferenceChange(Preference preference, Object obj) {
                            return BridgedAppsPreferenceController.this.onPreferenceChange(
                                    preference, obj);
                        }
                    });
        }
        removeUselessPrefs(treeSet);
    }

    public void rebuild() {
        this.mSession.rebuild(this.mFilter, ApplicationsState.ALPHA_COMPARATOR, true);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public BridgedAppsPreferenceController setAppState(ApplicationsState applicationsState) {
        this.mApplicationsState = applicationsState;
        return this;
    }

    public BridgedAppsPreferenceController setCn(ComponentName componentName) {
        this.mCn = componentName;
        return this;
    }

    public BridgedAppsPreferenceController setFilter(ApplicationsState.AppFilter appFilter) {
        this.mFilter = appFilter;
        return this;
    }

    public BridgedAppsPreferenceController setNm(NotificationBackend notificationBackend) {
        this.mNm = notificationBackend;
        return this;
    }

    public BridgedAppsPreferenceController setSession(Lifecycle lifecycle) {
        this.mSession = this.mApplicationsState.newSession(this, lifecycle);
        return this;
    }

    public BridgedAppsPreferenceController setUserId(int i) {
        this.mUserId = i;
        return this;
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

    public /* bridge */ /* synthetic */ void onDisabledAppCheckCompleted(Boolean bool) {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onPackageSizeChanged(String str) {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onRunningStateChanged(boolean z) {}
}

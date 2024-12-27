package com.android.settings.applications;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.os.UserHandle;
import android.provider.DeviceConfig;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ClonedAppsPreferenceController extends BasePreferenceController
        implements LifecycleObserver {
    private Context mContext;
    private Preference mPreference;

    public ClonedAppsPreferenceController(Context context, String str) {
        super(context, str);
        this.mContext = context;
    }

    private void updatePreferenceSummary() {
        if (isAvailable()) {
            new AsyncTask() { // from class:
                              // com.android.settings.applications.ClonedAppsPreferenceController.1
                @Override // android.os.AsyncTask
                public final Object doInBackground(Object[] objArr) {
                    List asList =
                            Arrays.asList(
                                    ClonedAppsPreferenceController.this
                                            .mContext
                                            .getResources()
                                            .getStringArray(
                                                    R.array.config_defaultNotificationVibePattern));
                    final int i = 0;
                    final List list =
                            ClonedAppsPreferenceController.this
                                    .mContext
                                    .getPackageManager()
                                    .getInstalledPackagesAsUser(0, UserHandle.myUserId())
                                    .stream()
                                    .map(
                                            new Function() { // from class:
                                                             // com.android.settings.applications.ClonedAppsPreferenceController$1$$ExternalSyntheticLambda0
                                                @Override // java.util.function.Function
                                                public final Object apply(Object obj) {
                                                    PackageInfo packageInfo = (PackageInfo) obj;
                                                    switch (i) {
                                                    }
                                                    return packageInfo.packageName;
                                                }
                                            })
                                    .toList();
                    final int i2 = 0;
                    int count =
                            (int)
                                    asList.stream()
                                            .filter(
                                                    new Predicate() { // from class:
                                                                      // com.android.settings.applications.ClonedAppsPreferenceController$1$$ExternalSyntheticLambda1
                                                        @Override // java.util.function.Predicate
                                                        public final boolean test(Object obj) {
                                                            int i3 = i2;
                                                            List list2 = list;
                                                            String str = (String) obj;
                                                            switch (i3) {
                                                            }
                                                            return list2.contains(str);
                                                        }
                                                    })
                                            .count();
                    int cloneUserId =
                            Utils.getCloneUserId(ClonedAppsPreferenceController.this.mContext);
                    if (cloneUserId == -1) {
                        return new Integer[] {0, Integer.valueOf(count)};
                    }
                    final int i3 = 1;
                    final List list2 =
                            ClonedAppsPreferenceController.this
                                    .mContext
                                    .getPackageManager()
                                    .getInstalledPackagesAsUser(0, cloneUserId)
                                    .stream()
                                    .map(
                                            new Function() { // from class:
                                                             // com.android.settings.applications.ClonedAppsPreferenceController$1$$ExternalSyntheticLambda0
                                                @Override // java.util.function.Function
                                                public final Object apply(Object obj) {
                                                    PackageInfo packageInfo = (PackageInfo) obj;
                                                    switch (i3) {
                                                    }
                                                    return packageInfo.packageName;
                                                }
                                            })
                                    .toList();
                    final int i4 = 1;
                    int count2 =
                            (int)
                                    asList.stream()
                                            .filter(
                                                    new Predicate() { // from class:
                                                                      // com.android.settings.applications.ClonedAppsPreferenceController$1$$ExternalSyntheticLambda1
                                                        @Override // java.util.function.Predicate
                                                        public final boolean test(Object obj) {
                                                            int i32 = i4;
                                                            List list22 = list2;
                                                            String str = (String) obj;
                                                            switch (i32) {
                                                            }
                                                            return list22.contains(str);
                                                        }
                                                    })
                                            .count();
                    return new Integer[] {Integer.valueOf(count2), Integer.valueOf(count - count2)};
                }

                @Override // android.os.AsyncTask
                public final void onPostExecute(Object obj) {
                    Integer[] numArr = (Integer[]) obj;
                    ClonedAppsPreferenceController.this.updateSummary(
                            numArr[0].intValue(), numArr[1].intValue());
                }
            }.execute(new Void[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSummary(int i, int i2) {
        this.mPreference.setSummary(
                this.mContext
                        .getResources()
                        .getString(
                                com.android.settings.R.string.cloned_apps_summary,
                                Integer.valueOf(i),
                                Integer.valueOf(i2)));
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (DeviceConfig.getBoolean("app_cloning", "cloned_apps_enabled", true)
                        && this.mContext
                                .getResources()
                                .getBoolean(
                                        com.android.settings.R.bool
                                                .config_cloned_apps_page_enabled))
                ? 0
                : 3;
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
        if (getPreferenceKey().equals(preference.getKey())) {
            preference.getExtras().putInt(ImsProfile.SERVICE_PROFILE, 1);
        }
        return super.handlePreferenceTreeClick(preference);
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

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        updatePreferenceSummary();
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

package com.android.settings.development.graphicsdriver;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.development.DevelopmentSettingsEnabler;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class GraphicsDriverAppPreferenceController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener,
                GraphicsDriverContentObserver.OnGraphicsDriverContentChangedListener,
                LifecycleObserver,
                OnStart,
                OnStop {
    private final Comparator<AppInfo> mAppInfoComparator;
    private final List<AppInfo> mAppInfos;
    private final ContentResolver mContentResolver;
    private final Context mContext;
    private final Set<String> mDevOptInApps;
    private final Set<String> mDevOptOutApps;
    private final Set<String> mDevPrereleaseOptInApps;
    CharSequence[] mEntryList;
    GraphicsDriverContentObserver mGraphicsDriverContentObserver;
    private final String mPreferenceDefault;
    private PreferenceGroup mPreferenceGroup;
    private final String mPreferencePrereleaseDriver;
    private final String mPreferenceProductionDriver;
    private final String mPreferenceSystem;
    private final String mPreferenceTitle;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.development.graphicsdriver.GraphicsDriverAppPreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return Collator.getInstance().compare(((AppInfo) obj).label, ((AppInfo) obj2).label);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppInfo {
        public final ApplicationInfo info;
        public final String label;

        public AppInfo(PackageManager packageManager, ApplicationInfo applicationInfo) {
            this.info = applicationInfo;
            this.label = packageManager.getApplicationLabel(applicationInfo).toString();
        }
    }

    public GraphicsDriverAppPreferenceController(Context context, String str) {
        super(context, str);
        this.mAppInfoComparator = new AnonymousClass1();
        this.mContext = context;
        ContentResolver contentResolver = context.getContentResolver();
        this.mContentResolver = contentResolver;
        this.mGraphicsDriverContentObserver =
                new GraphicsDriverContentObserver(new Handler(Looper.getMainLooper()), this);
        Resources resources = context.getResources();
        this.mPreferenceTitle = resources.getString(R.string.graphics_driver_app_preference_title);
        this.mPreferenceDefault =
                resources.getString(R.string.graphics_driver_app_preference_default);
        this.mPreferenceProductionDriver =
                resources.getString(R.string.graphics_driver_app_preference_production_driver);
        this.mPreferencePrereleaseDriver =
                resources.getString(R.string.graphics_driver_app_preference_prerelease_driver);
        this.mPreferenceSystem =
                resources.getString(R.string.graphics_driver_app_preference_system);
        this.mEntryList =
                GraphicsDriverEnableForAllAppsPreferenceController.constructEntryList(
                        context, true);
        this.mAppInfos = getAppInfos(context);
        this.mDevOptInApps =
                getGlobalSettingsString(contentResolver, "updatable_driver_production_opt_in_apps");
        this.mDevPrereleaseOptInApps =
                getGlobalSettingsString(contentResolver, "updatable_driver_prerelease_opt_in_apps");
        this.mDevOptOutApps =
                getGlobalSettingsString(
                        contentResolver, "updatable_driver_production_opt_out_apps");
    }

    private List<AppInfo> getAppInfos(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(0);
        ArrayList arrayList = new ArrayList();
        for (ApplicationInfo applicationInfo : installedApplications) {
            if ((applicationInfo.flags & 1) == 0) {
                arrayList.add(new AppInfo(packageManager, applicationInfo));
            }
        }
        Collections.sort(arrayList, this.mAppInfoComparator);
        return arrayList;
    }

    private Set<String> getGlobalSettingsString(ContentResolver contentResolver, String str) {
        String string = Settings.Global.getString(contentResolver, str);
        if (string == null) {
            return new HashSet();
        }
        HashSet hashSet = new HashSet(Arrays.asList(string.split(",")));
        hashSet.remove(ApnSettings.MVNO_NONE);
        return hashSet;
    }

    public ListPreference createListPreference(Context context, String str, String str2) {
        ListPreference listPreference = new ListPreference(context, null);
        listPreference.setKey(str);
        listPreference.setTitle(str2);
        listPreference.mDialogTitle = this.mPreferenceTitle;
        CharSequence[] charSequenceArr = this.mEntryList;
        listPreference.mEntries = charSequenceArr;
        listPreference.mEntryValues = charSequenceArr;
        if (this.mDevOptOutApps.contains(str)) {
            listPreference.setValue(this.mPreferenceSystem);
            listPreference.setSummary(this.mPreferenceSystem);
        } else if (this.mDevPrereleaseOptInApps.contains(str)) {
            listPreference.setValue(this.mPreferencePrereleaseDriver);
            listPreference.setSummary(this.mPreferencePrereleaseDriver);
        } else if (this.mDevOptInApps.contains(str)) {
            listPreference.setValue(this.mPreferenceProductionDriver);
            listPreference.setSummary(this.mPreferenceProductionDriver);
        } else {
            listPreference.setValue(this.mPreferenceDefault);
            listPreference.setSummary(this.mPreferenceDefault);
        }
        listPreference.setOnPreferenceChangeListener(this);
        return listPreference;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        PreferenceGroup preferenceGroup =
                (PreferenceGroup) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreferenceGroup = preferenceGroup;
        Context context = preferenceGroup.getContext();
        for (AppInfo appInfo : this.mAppInfos) {
            this.mPreferenceGroup.addPreference(
                    createListPreference(context, appInfo.info.packageName, appInfo.label));
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (!DevelopmentSettingsEnabler.isDevelopmentSettingsEnabled(this.mContext)
                        || Settings.Global.getInt(
                                        this.mContentResolver, "updatable_driver_all_apps", 0)
                                == 3)
                ? 2
                : 0;
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

    @Override // com.android.settings.development.graphicsdriver.GraphicsDriverContentObserver.OnGraphicsDriverContentChangedListener
    public void onGraphicsDriverContentChanged() {
        updateState(this.mPreferenceGroup);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        ListPreference listPreference = (ListPreference) preference;
        String obj2 = obj.toString();
        String key = preference.getKey();
        if (obj2.equals(this.mPreferenceSystem)) {
            this.mDevOptInApps.remove(key);
            this.mDevPrereleaseOptInApps.remove(key);
            this.mDevOptOutApps.add(key);
        } else if (obj2.equals(this.mPreferenceProductionDriver)) {
            this.mDevOptInApps.add(key);
            this.mDevPrereleaseOptInApps.remove(key);
            this.mDevOptOutApps.remove(key);
        } else if (obj2.equals(this.mPreferencePrereleaseDriver)) {
            this.mDevOptInApps.remove(key);
            this.mDevPrereleaseOptInApps.add(key);
            this.mDevOptOutApps.remove(key);
        } else {
            this.mDevOptInApps.remove(key);
            this.mDevPrereleaseOptInApps.remove(key);
            this.mDevOptOutApps.remove(key);
        }
        listPreference.setValue(obj2);
        listPreference.setSummary(obj2);
        Settings.Global.putString(
                this.mContentResolver,
                "updatable_driver_production_opt_in_apps",
                String.join(",", this.mDevOptInApps));
        Settings.Global.putString(
                this.mContentResolver,
                "updatable_driver_prerelease_opt_in_apps",
                String.join(",", this.mDevPrereleaseOptInApps));
        Settings.Global.putString(
                this.mContentResolver,
                "updatable_driver_production_opt_out_apps",
                String.join(",", this.mDevOptOutApps));
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        GraphicsDriverContentObserver graphicsDriverContentObserver =
                this.mGraphicsDriverContentObserver;
        ContentResolver contentResolver = this.mContentResolver;
        graphicsDriverContentObserver.getClass();
        contentResolver.registerContentObserver(
                Settings.Global.getUriFor("updatable_driver_all_apps"),
                false,
                graphicsDriverContentObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        GraphicsDriverContentObserver graphicsDriverContentObserver =
                this.mGraphicsDriverContentObserver;
        ContentResolver contentResolver = this.mContentResolver;
        graphicsDriverContentObserver.getClass();
        contentResolver.unregisterContentObserver(graphicsDriverContentObserver);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        preference.setVisible(isAvailable());
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}

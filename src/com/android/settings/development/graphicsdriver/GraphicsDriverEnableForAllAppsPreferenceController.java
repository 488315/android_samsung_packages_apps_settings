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
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.development.DevelopmentSettingsEnabler;

import dalvik.system.VMRuntime;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class GraphicsDriverEnableForAllAppsPreferenceController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener,
                GraphicsDriverContentObserver.OnGraphicsDriverContentChangedListener,
                LifecycleObserver,
                OnStart,
                OnStop {
    public static final String PROPERTY_GFX_DRIVER_PRERELEASE = "ro.gfx.driver.1";
    public static final String PROPERTY_GFX_DRIVER_PRODUCTION = "ro.gfx.driver.0";
    public static final int UPDATABLE_DRIVER_DEFAULT = 0;
    public static final int UPDATABLE_DRIVER_OFF = 3;
    public static final int UPDATABLE_DRIVER_PRERELEASE_ALL_APPS = 2;
    public static final int UPDATABLE_DRIVER_PRODUCTION_ALL_APPS = 1;
    private final ContentResolver mContentResolver;
    private final Context mContext;
    CharSequence[] mEntryList;
    GraphicsDriverContentObserver mGraphicsDriverContentObserver;
    private ListPreference mPreference;
    private final String mPreferenceDefault;
    private final String mPreferencePrereleaseDriver;
    private final String mPreferenceProductionDriver;

    public GraphicsDriverEnableForAllAppsPreferenceController(Context context, String str) {
        super(context, str);
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        Resources resources = context.getResources();
        this.mPreferenceDefault =
                resources.getString(R.string.graphics_driver_app_preference_default);
        this.mPreferenceProductionDriver =
                resources.getString(R.string.graphics_driver_app_preference_production_driver);
        this.mPreferencePrereleaseDriver =
                resources.getString(R.string.graphics_driver_app_preference_prerelease_driver);
        this.mEntryList = constructEntryList(context, false);
        this.mGraphicsDriverContentObserver =
                new GraphicsDriverContentObserver(new Handler(Looper.getMainLooper()), this);
    }

    private static String chooseAbi(ApplicationInfo applicationInfo) {
        String currentInstructionSet = VMRuntime.getCurrentInstructionSet();
        String str = applicationInfo.primaryCpuAbi;
        if (str != null && currentInstructionSet.equals(VMRuntime.getInstructionSet(str))) {
            return applicationInfo.primaryCpuAbi;
        }
        String str2 = applicationInfo.secondaryCpuAbi;
        if (str2 == null || !currentInstructionSet.equals(VMRuntime.getInstructionSet(str2))) {
            return null;
        }
        return applicationInfo.secondaryCpuAbi;
    }

    public static CharSequence[] constructEntryList(Context context, boolean z) {
        Resources resources = context.getResources();
        String str = SystemProperties.get(PROPERTY_GFX_DRIVER_PRERELEASE);
        String str2 = SystemProperties.get(PROPERTY_GFX_DRIVER_PRODUCTION);
        ArrayList arrayList = new ArrayList();
        arrayList.add(resources.getString(R.string.graphics_driver_app_preference_default));
        PackageManager packageManager = context.getPackageManager();
        if (!TextUtils.isEmpty(str) && hasDriverPackage(packageManager, str)) {
            arrayList.add(
                    resources.getString(R.string.graphics_driver_app_preference_prerelease_driver));
        }
        if (!TextUtils.isEmpty(str2) && hasDriverPackage(packageManager, str2)) {
            arrayList.add(
                    resources.getString(R.string.graphics_driver_app_preference_production_driver));
        }
        if (z) {
            arrayList.add(resources.getString(R.string.graphics_driver_app_preference_system));
        }
        return (CharSequence[]) arrayList.toArray(new CharSequence[arrayList.size()]);
    }

    private static boolean hasDriverPackage(PackageManager packageManager, String str) {
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 1048576);
            return applicationInfo.targetSdkVersion >= 26 && chooseAbi(applicationInfo) != null;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        ListPreference listPreference =
                (ListPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = listPreference;
        listPreference.setEntries(this.mEntryList);
        ListPreference listPreference2 = this.mPreference;
        listPreference2.mEntryValues = this.mEntryList;
        listPreference2.setOnPreferenceChangeListener(this);
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
        updateState(this.mPreference);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        ListPreference listPreference = (ListPreference) preference;
        String obj2 = obj.toString();
        int i = 0;
        int i2 = Settings.Global.getInt(this.mContentResolver, "updatable_driver_all_apps", 0);
        if (obj2.equals(this.mPreferenceProductionDriver)) {
            i = 1;
        } else if (obj2.equals(this.mPreferencePrereleaseDriver)) {
            i = 2;
        }
        listPreference.setValue(obj2);
        listPreference.setSummary(obj2);
        if (i != i2) {
            Settings.Global.putInt(this.mContentResolver, "updatable_driver_all_apps", i);
        }
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
        ListPreference listPreference = (ListPreference) preference;
        listPreference.setVisible(isAvailable());
        int i = Settings.Global.getInt(this.mContentResolver, "updatable_driver_all_apps", 0);
        if (i == 1) {
            listPreference.setValue(this.mPreferenceProductionDriver);
            listPreference.setSummary(this.mPreferenceProductionDriver);
        } else if (i == 2) {
            listPreference.setValue(this.mPreferencePrereleaseDriver);
            listPreference.setSummary(this.mPreferencePrereleaseDriver);
        } else {
            listPreference.setValue(this.mPreferenceDefault);
            listPreference.setSummary(this.mPreferenceDefault);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}

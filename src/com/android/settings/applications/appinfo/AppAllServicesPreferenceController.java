package com.android.settings.applications.appinfo;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppAllServicesPreferenceController extends AppInfoPreferenceControllerBase {
    private static final String SUMMARY_METADATA_KEY = "app_features_preference_summary";
    private static final String TAG = "AllServicesPrefControl";
    private final PackageManager mPackageManager;
    private String mPackageName;

    public AppAllServicesPreferenceController(Context context, String str) {
        super(context, str);
        this.mPackageManager = context.getPackageManager();
    }

    private ResolveInfo getResolveInfo(int i) {
        if (this.mPackageName == null) {
            return null;
        }
        Intent intent = new Intent("android.intent.action.VIEW_APP_FEATURES");
        intent.setPackage(this.mPackageName);
        return this.mPackageManager.resolveActivity(intent, i);
    }

    private CharSequence getStorageSummary() {
        ResolveInfo resolveInfo = getResolveInfo(128);
        if (resolveInfo == null) {
            Log.d(TAG, "mResolveInfo is null.");
            return null;
        }
        Bundle bundle = resolveInfo.activityInfo.metaData;
        if (bundle != null) {
            try {
                return this.mPackageManager
                        .getResourcesForActivity(
                                new ComponentName(this.mPackageName, resolveInfo.activityInfo.name))
                        .getString(bundle.getInt(SUMMARY_METADATA_KEY));
            } catch (PackageManager.NameNotFoundException unused) {
                Log.d(TAG, "Name of resource not found for summary string.");
            } catch (Resources.NotFoundException unused2) {
                Log.d(TAG, "Resource not found for summary string.");
            }
        }
        return null;
    }

    private void startAllServicesActivity() {
        Intent intent = new Intent("android.intent.action.VIEW_APP_FEATURES");
        intent.setComponent(
                new ComponentName(this.mPackageName, getResolveInfo(0).activityInfo.name));
        FragmentActivity activity = this.mParent.getActivity();
        if (activity != null) {
            try {
                activity.startActivity(intent);
            } catch (ActivityNotFoundException unused) {
                Log.e(TAG, "The app cannot handle android.intent.action.VIEW_APP_FEATURES");
            }
        }
    }

    public boolean canPackageHandleIntent() {
        return getResolveInfo(0) != null;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        CharSequence storageSummary = getStorageSummary();
        if (storageSummary != null) {
            this.mPreference.setSummary(storageSummary);
        }
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (canPackageHandleIntent() && isLocationProvider()) ? 0 : 2;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!getPreferenceKey().equals(preference.getKey())) {
            return false;
        }
        startAllServicesActivity();
        return true;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    public boolean isLocationProvider() {
        LocationManager locationManager =
                (LocationManager) this.mContext.getSystemService(LocationManager.class);
        Objects.requireNonNull(locationManager);
        return locationManager.isProviderPackage(this.mPackageName);
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void setPackageName(String str) {
        this.mPackageName = str;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}

package com.android.settings.location;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.permission.PermissionControllerManager;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.Utils;
import com.android.settings.core.PreferenceControllerMixin;

import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.sec.ims.gls.GlsIntent;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppLocationPermissionPreferenceController extends LocationBasePreferenceController
        implements PreferenceControllerMixin {
    final AtomicInteger loadingInProgress;
    private final LocationManager mLocationManager;
    int mNumHasLocation;
    private int mNumHasLocationLoading;
    int mNumTotal;
    private int mNumTotalLoading;
    private Preference mPreference;

    public AppLocationPermissionPreferenceController(Context context, String str) {
        super(context, str);
        this.mNumTotal = -1;
        this.mNumHasLocation = -1;
        this.loadingInProgress = new AtomicInteger(0);
        this.mNumTotalLoading = 0;
        this.mNumHasLocationLoading = 0;
        this.mLocationManager =
                (LocationManager) context.getSystemService(GlsIntent.Extras.EXTRA_LOCATION);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateState$0(int i) {
        this.mNumTotalLoading += i;
        if (this.loadingInProgress.decrementAndGet() == 0) {
            setAppCounts(this.mNumTotalLoading, this.mNumHasLocationLoading);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateState$1(int i) {
        this.mNumHasLocationLoading += i;
        if (this.loadingInProgress.decrementAndGet() == 0) {
            setAppCounts(this.mNumTotalLoading, this.mNumHasLocationLoading);
        }
    }

    private void setAppCounts(int i, int i2) {
        this.mNumTotal = i;
        this.mNumHasLocation = i2;
        refreshSummary(this.mPreference);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        if (findPreference != null) {
            findPreference.setIntent(
                    new Intent("android.intent.action.MANAGE_PERMISSION_APPS")
                            .setPackage(
                                    this.mContext
                                            .getPackageManager()
                                            .getPermissionControllerPackageName())
                            .putExtra(
                                    TextUtils.equals(
                                                    findPreference.getKey(),
                                                    "app_level_permissions")
                                            ? "android.intent.extra.PERMISSION_NAME"
                                            : "android.intent.extra.PERMISSION_GROUP_NAME",
                                    "android.permission-group.LOCATION"));
        }
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (!isEmergencymode(this.mContext)
                        && Settings.Global.getInt(
                                        this.mContext.getContentResolver(),
                                        "location_settings_link_to_permissions_enabled",
                                        1)
                                == 1)
                ? 0
                : 3;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return null;
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!preference.getKey().equals(getPreferenceKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        LoggingHelper.insertEventLogging(131, 56610);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MANAGE_PERMISSION_APPS");
        intent.putExtra(
                "android.intent.extra.PERMISSION_NAME", "android.permission-group.LOCATION");
        this.mContext.startActivity(intent);
        return true;
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

    public boolean isEmergencymode(Context context) {
        return SemEmergencyManager.getInstance(context) != null
                && SemEmergencyManager.isEmergencyMode(context);
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
        Preference preference = this.mPreference;
        if (preference != null) {
            updateState(preference);
        }
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        this.mPreference = preference;
        refreshSummary(preference);
        if (this.mLocationManager.isLocationEnabled() && this.loadingInProgress.get() == 0) {
            this.mNumTotalLoading = 0;
            this.mNumHasLocationLoading = 0;
            List<UserHandle> userProfiles =
                    ((UserManager) this.mContext.getSystemService(UserManager.class))
                            .getUserProfiles();
            this.loadingInProgress.set(userProfiles.size() * 2);
            Iterator<UserHandle> it = userProfiles.iterator();
            while (it.hasNext()) {
                Context createPackageContextAsUser =
                        Utils.createPackageContextAsUser(this.mContext, it.next().getIdentifier());
                if (createPackageContextAsUser == null) {
                    for (int i = 0; i < 2; i++) {
                        if (this.loadingInProgress.decrementAndGet() == 0) {
                            setAppCounts(this.mNumTotalLoading, this.mNumHasLocationLoading);
                        }
                    }
                } else {
                    PermissionControllerManager permissionControllerManager =
                            (PermissionControllerManager)
                                    createPackageContextAsUser.getSystemService(
                                            PermissionControllerManager.class);
                    final int i2 = 0;
                    permissionControllerManager.countPermissionApps(
                            Arrays.asList(
                                    "android.permission.ACCESS_FINE_LOCATION",
                                    "android.permission.ACCESS_COARSE_LOCATION"),
                            0,
                            new PermissionControllerManager.OnCountPermissionAppsResultCallback(
                                    this) { // from class:
                                            // com.android.settings.location.AppLocationPermissionPreferenceController$$ExternalSyntheticLambda0
                                public final /* synthetic */
                                AppLocationPermissionPreferenceController f$0;

                                {
                                    this.f$0 = this;
                                }

                                public final void onCountPermissionApps(int i3) {
                                    int i4 = i2;
                                    AppLocationPermissionPreferenceController
                                            appLocationPermissionPreferenceController = this.f$0;
                                    switch (i4) {
                                        case 0:
                                            appLocationPermissionPreferenceController
                                                    .lambda$updateState$0(i3);
                                            break;
                                        default:
                                            appLocationPermissionPreferenceController
                                                    .lambda$updateState$1(i3);
                                            break;
                                    }
                                }
                            },
                            (Handler) null);
                    final int i3 = 1;
                    permissionControllerManager.countPermissionApps(
                            Arrays.asList(
                                    "android.permission.ACCESS_FINE_LOCATION",
                                    "android.permission.ACCESS_COARSE_LOCATION"),
                            1,
                            new PermissionControllerManager.OnCountPermissionAppsResultCallback(
                                    this) { // from class:
                                            // com.android.settings.location.AppLocationPermissionPreferenceController$$ExternalSyntheticLambda0
                                public final /* synthetic */
                                AppLocationPermissionPreferenceController f$0;

                                {
                                    this.f$0 = this;
                                }

                                public final void onCountPermissionApps(int i32) {
                                    int i4 = i3;
                                    AppLocationPermissionPreferenceController
                                            appLocationPermissionPreferenceController = this.f$0;
                                    switch (i4) {
                                        case 0:
                                            appLocationPermissionPreferenceController
                                                    .lambda$updateState$0(i32);
                                            break;
                                        default:
                                            appLocationPermissionPreferenceController
                                                    .lambda$updateState$1(i32);
                                            break;
                                    }
                                }
                            },
                            (Handler) null);
                }
            }
        }
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}

package com.android.settings.display;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.activityembedding.ActivityEmbeddingRulesController;
import com.android.settings.activityembedding.ActivityEmbeddingUtils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedTopLevelPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TopLevelWallpaperPreferenceController extends BasePreferenceController {
    private static final String LAUNCHED_SETTINGS = "app_launched_settings";
    private static final String TAG = "TopLevelWallpaperPreferenceController";
    private final String mStylesAndWallpaperClass;
    private final String mWallpaperClass;
    private final String mWallpaperLaunchExtra;
    private final String mWallpaperPackage;

    public TopLevelWallpaperPreferenceController(Context context, String str) {
        super(context, str);
        this.mWallpaperPackage = this.mContext.getString(R.string.config_wallpaper_picker_package);
        this.mWallpaperClass = this.mContext.getString(R.string.config_wallpaper_picker_class);
        this.mStylesAndWallpaperClass =
                this.mContext.getString(R.string.config_styles_and_wallpaper_picker_class);
        this.mWallpaperLaunchExtra =
                this.mContext.getString(R.string.config_wallpaper_picker_launch_extra);
    }

    private boolean canResolveWallpaperComponent(String str) {
        List<ResolveInfo> queryIntentActivities =
                this.mContext
                        .getPackageManager()
                        .queryIntentActivities(
                                new Intent()
                                        .setComponent(
                                                new ComponentName(this.mWallpaperPackage, str)),
                                0);
        return (queryIntentActivities == null || queryIntentActivities.isEmpty()) ? false : true;
    }

    private void disablePreferenceIfManaged(
            RestrictedTopLevelPreference restrictedTopLevelPreference) {
        if (restrictedTopLevelPreference != null) {
            if (restrictedTopLevelPreference.mHelper.setDisabledByAdmin(null)) {
                restrictedTopLevelPreference.notifyChanged();
            }
            if (RestrictedLockUtilsInternal.hasBaseUserRestriction(
                    this.mContext, UserHandle.myUserId(), "no_set_wallpaper")) {
                restrictedTopLevelPreference.setEnabled(false);
            } else {
                restrictedTopLevelPreference.mHelper.checkRestrictionAndSetDisabled(
                        UserHandle.myUserId(), "no_set_wallpaper");
            }
        }
    }

    public boolean areStylesAvailable() {
        return !TextUtils.isEmpty(this.mStylesAndWallpaperClass)
                && canResolveWallpaperComponent(this.mStylesAndWallpaperClass);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        preferenceScreen.findPreference(getPreferenceKey()).setTitle(getTitle());
        ActivityEmbeddingRulesController.registerTwoPanePairRuleForSettingsHome(
                this.mContext, getComponentName(), null, true);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if ((!TextUtils.isEmpty(this.mWallpaperClass)
                        || !TextUtils.isEmpty(this.mStylesAndWallpaperClass))
                && !TextUtils.isEmpty(this.mWallpaperPackage)) {
            return canResolveWallpaperComponent(getComponentClassString()) ? 1 : 2;
        }
        Log.e(TAG, "No Wallpaper picker specified!");
        return 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public String getComponentClassString() {
        return areStylesAvailable() ? this.mStylesAndWallpaperClass : this.mWallpaperClass;
    }

    public ComponentName getComponentName() {
        return new ComponentName(this.mWallpaperPackage, getComponentClassString());
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

    public String getTitle() {
        return this.mContext.getString(
                areStylesAvailable()
                        ? R.string.style_and_wallpaper_settings_title
                        : R.string.wallpaper_settings_title);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!getPreferenceKey().equals(preference.getKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        Intent putExtra =
                new Intent()
                        .setComponent(getComponentName())
                        .putExtra(this.mWallpaperLaunchExtra, LAUNCHED_SETTINGS);
        if (areStylesAvailable()
                && !ActivityEmbeddingUtils.isEmbeddingActivityEnabled(this.mContext)) {
            putExtra.setFlags(268468224);
        }
        preference.getContext().startActivity(putExtra);
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
        disablePreferenceIfManaged((RestrictedTopLevelPreference) preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}

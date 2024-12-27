package com.samsung.android.settings.general;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SmartManagerFeaturePreferenceController extends BasePreferenceController {
    private static final String KEY_BATTERY_SETTINGS = "battery_settings";
    private static final String KEY_MEMORY_SETTINGS = "memory_settings";
    private static final String KEY_SMART_MANAGER_FEATURE_CATEGORY =
            "smart_manager_feature_category";
    private static final String KEY_STORAGE_SETTINGS = "storage_settings";
    private static final String SMART_MANAGER_PKG = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
    private static final String SM_ACTION_BATTERY = "com.samsung.android.sm.ACTION_BATTERY";
    private static final String SM_ACTION_RAM = "com.samsung.android.sm.ACTION_RAM";
    private static final String SM_ACTION_STORAGE = "com.samsung.android.sm.ACTION_STORAGE";
    private PreferenceGroup mPreferenceGroup;

    public SmartManagerFeaturePreferenceController(Context context) {
        super(context, KEY_SMART_MANAGER_FEATURE_CATEGORY);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreferenceGroup =
                (PreferenceGroup)
                        preferenceScreen.findPreference(KEY_SMART_MANAGER_FEATURE_CATEGORY);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 3;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_SMART_MANAGER_FEATURE_CATEGORY;
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
        String str =
                TextUtils.equals(preference.getKey(), KEY_BATTERY_SETTINGS)
                        ? SM_ACTION_BATTERY
                        : TextUtils.equals(preference.getKey(), KEY_STORAGE_SETTINGS)
                                ? SM_ACTION_STORAGE
                                : TextUtils.equals(preference.getKey(), KEY_MEMORY_SETTINGS)
                                        ? SM_ACTION_RAM
                                        : null;
        if (str == null) {
            return super.handlePreferenceTreeClick(preference);
        }
        try {
            Intent intent = new Intent(str);
            intent.setPackage(SMART_MANAGER_PKG);
            ResolveInfo resolveActivity =
                    this.mContext.getPackageManager().resolveActivity(intent, 0);
            if (resolveActivity == null || !resolveActivity.activityInfo.isEnabled()) {
                return true;
            }
            Context context = this.mContext;
            context.startActivityForResult(
                    null, intent, Utils.isLaunchModeSingleInstance(context, intent) ? -1 : 0, null);
            return true;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return true;
        }
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

    @Override // com.android.settings.core.BasePreferenceController
    public void updateNonIndexableKeys(List<String> list) {
        if (getAvailabilityStatus() == 0 && Utils.hasPackage(this.mContext, SMART_MANAGER_PKG)) {
            return;
        }
        list.add(KEY_BATTERY_SETTINGS);
        list.add(KEY_STORAGE_SETTINGS);
        list.add(KEY_MEMORY_SETTINGS);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        this.mPreferenceGroup.setEnabled(Utils.hasPackage(this.mContext, SMART_MANAGER_PKG));
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}

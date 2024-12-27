package com.android.settings.applications;

import android.app.role.RoleManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.icu.text.ListFormatter;
import android.text.TextUtils;

import androidx.core.text.BidiFormatter;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.applications.AppUtils;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DefaultAppsPreferenceController extends BasePreferenceController {
    private final PackageManager mPackageManager;
    private final RoleManager mRoleManager;

    public DefaultAppsPreferenceController(Context context, String str) {
        super(context, str);
        this.mPackageManager = context.getPackageManager();
        this.mRoleManager = (RoleManager) context.getSystemService(RoleManager.class);
    }

    private CharSequence getDefaultAppLabel(String str) {
        List roleHolders = this.mRoleManager.getRoleHolders(str);
        if (roleHolders.isEmpty()) {
            return null;
        }
        String str2 = (String) roleHolders.get(0);
        BidiFormatter bidiFormatter = BidiFormatter.getInstance();
        PackageManager packageManager = this.mPackageManager;
        Intent intent = AppUtils.sBrowserIntent;
        return bidiFormatter.unicodeWrap(
                com.android.settingslib.utils.applications.AppUtils.getApplicationLabel(
                        packageManager, str2),
                bidiFormatter.mDefaultTextDirectionHeuristicCompat);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        if (findPreference != null) {
            findPreference.setIntent(
                    new Intent("android.settings.MANAGE_DEFAULT_APPS_SETTINGS")
                            .setPackage(this.mPackageManager.getPermissionControllerPackageName()));
        }
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        ArrayList arrayList = new ArrayList();
        CharSequence defaultAppLabel = getDefaultAppLabel("android.app.role.BROWSER");
        if (!TextUtils.isEmpty(defaultAppLabel)) {
            arrayList.add(defaultAppLabel);
        }
        CharSequence defaultAppLabel2 = getDefaultAppLabel("android.app.role.DIALER");
        if (!TextUtils.isEmpty(defaultAppLabel2)) {
            arrayList.add(defaultAppLabel2);
        }
        CharSequence defaultAppLabel3 = getDefaultAppLabel("android.app.role.SMS");
        if (!TextUtils.isEmpty(defaultAppLabel3)) {
            arrayList.add(defaultAppLabel3);
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return ListFormatter.getInstance().format(arrayList);
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

package com.android.settings.deviceinfo.legal;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class LegalPreferenceController extends BasePreferenceController {
    private final PackageManager mPackageManager;
    private Preference mPreference;

    public LegalPreferenceController(Context context, String str) {
        super(context, str);
        this.mPackageManager = this.mContext.getPackageManager();
    }

    private ResolveInfo findMatchingSpecificActivity() {
        List<ResolveInfo> queryIntentActivities;
        Intent intent = getIntent();
        if (intent == null
                || (queryIntentActivities = this.mPackageManager.queryIntentActivities(intent, 0))
                        == null) {
            return null;
        }
        for (ResolveInfo resolveInfo : queryIntentActivities) {
            if ((resolveInfo.activityInfo.applicationInfo.flags & 1) != 0) {
                return resolveInfo;
            }
        }
        return null;
    }

    private void replacePreferenceIntent() {
        ResolveInfo findMatchingSpecificActivity = findMatchingSpecificActivity();
        if (findMatchingSpecificActivity == null) {
            return;
        }
        Preference preference = this.mPreference;
        Intent intent = new Intent();
        ActivityInfo activityInfo = findMatchingSpecificActivity.activityInfo;
        preference.setIntent(
                intent.setClassName(activityInfo.packageName, activityInfo.name)
                        .addFlags(268435456));
        this.mPreference.setTitle(findMatchingSpecificActivity.loadLabel(this.mPackageManager));
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
        super.displayPreference(preferenceScreen);
        if (getAvailabilityStatus() == 0) {
            replacePreferenceIntent();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return findMatchingSpecificActivity() != null ? 0 : 3;
    }

    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public abstract Intent getIntent();

    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

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

    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

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

    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}

package com.android.settings.accounts;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class EmergencyInfoPreferenceController extends BasePreferenceController {
    Intent mIntent;

    public EmergencyInfoPreferenceController(Context context, String str) {
        super(context, str);
    }

    private boolean isAOSPVersionSupported() {
        this.mIntent =
                new Intent(
                                this.mContext
                                        .getResources()
                                        .getString(R.string.config_aosp_emergency_intent_action))
                        .setPackage(
                                this.mContext
                                        .getResources()
                                        .getString(R.string.config_aosp_emergency_package_name));
        List<ResolveInfo> queryIntentActivities =
                this.mContext.getPackageManager().queryIntentActivities(this.mIntent, 0);
        return (queryIntentActivities == null || queryIntentActivities.isEmpty()) ? false : true;
    }

    private boolean isEmergencyInfoSupported() {
        this.mIntent =
                new Intent(
                                this.mContext
                                        .getResources()
                                        .getString(R.string.config_emergency_intent_action))
                        .setPackage(
                                this.mContext
                                        .getResources()
                                        .getString(R.string.config_emergency_package_name));
        List<ResolveInfo> queryIntentActivities =
                this.mContext.getPackageManager().queryIntentActivities(this.mIntent, 0);
        return (queryIntentActivities == null || queryIntentActivities.isEmpty()) ? false : true;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (this.mContext
                .getResources()
                .getBoolean(R.bool.config_show_emergency_info_in_device_info)) {
            return (isEmergencyInfoSupported() || isAOSPVersionSupported()) ? 0 : 3;
        }
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
        Intent intent;
        if (!TextUtils.equals(getPreferenceKey(), preference.getKey())
                || (intent = this.mIntent) == null) {
            return false;
        }
        intent.setFlags(67108864);
        this.mContext.startActivity(this.mIntent);
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

    @Override // com.android.settings.core.BasePreferenceController
    public void updateRawDataToIndex(List<SearchIndexableRaw> list) {
        if (isAvailable()) {
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(this.mContext);
            Resources resources = this.mContext.getResources();
            searchIndexableRaw.title = resources.getString(R.string.emergency_info_title);
            searchIndexableRaw.screenTitle = resources.getString(R.string.emergency_info_title);
            list.add(searchIndexableRaw);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        preference.setSummary(
                this.mContext.getString(
                        R.string.emergency_info_summary,
                        ((UserManager) this.mContext.getSystemService(UserManager.class))
                                .getUserInfo(UserHandle.myUserId())
                                .name));
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}

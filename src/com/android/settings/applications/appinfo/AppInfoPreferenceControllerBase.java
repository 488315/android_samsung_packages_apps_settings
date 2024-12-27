package com.android.settings.applications.appinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.applications.ApplicationsState;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppInfoPreferenceControllerBase extends BasePreferenceController
        implements AppInfoDashboardFragment.Callback {
    protected ApplicationsState.AppEntry mAppEntry;
    private final Class<? extends SettingsPreferenceFragment> mDetailFragmentClass;
    protected AppInfoDashboardFragment mParent;
    protected Preference mPreference;

    public AppInfoPreferenceControllerBase(Context context, String str) {
        super(context, str);
        this.mDetailFragmentClass = getDetailFragmentClass();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    public Bundle getArguments() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public Class<? extends SettingsPreferenceFragment> getDetailFragmentClass() {
        return null;
    }

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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        Class<? extends SettingsPreferenceFragment> cls;
        if (!TextUtils.equals(preference.getKey(), this.mPreferenceKey)
                || (cls = this.mDetailFragmentClass) == null) {
            return false;
        }
        Bundle arguments = getArguments();
        AppInfoDashboardFragment appInfoDashboardFragment = this.mParent;
        AppInfoDashboardFragment.startAppInfoFragment(
                cls, -1, arguments, appInfoDashboardFragment, appInfoDashboardFragment.mAppEntry);
        return true;
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

    public void refreshUi() {
        updateState(this.mPreference);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setParentFragment(AppInfoDashboardFragment appInfoDashboardFragment) {
        this.mParent = appInfoDashboardFragment;
        ((ArrayList) appInfoDashboardFragment.mCallbacks).add(this);
        this.mAppEntry = this.mParent.mAppEntry;
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

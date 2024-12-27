package com.samsung.android.settings.privacy;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.widget.SecExpandableMenuPreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SecurityDashboardPreferenceController extends BasePreferenceController {
    protected static SecurityDashboardConstants$Status mGoogleAccountMenuStatus;
    protected static boolean mIsGoogleAccountSupported;
    protected static boolean mIsScreenLockEnabled;
    protected static SecurityDashboardConstants$Status mSamsungAccountMenuStatus;
    protected SecExpandableMenuPreference mPreference;

    public SecurityDashboardPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecExpandableMenuPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    public Drawable getScanCategoryIcon() {
        SecExpandableMenuPreference secExpandableMenuPreference = this.mPreference;
        if (TextUtils.isEmpty(secExpandableMenuPreference.mScanIconDrawableName)) {
            return null;
        }
        return secExpandableMenuPreference
                .getContext()
                .getResources()
                .getDrawable(
                        secExpandableMenuPreference
                                .getContext()
                                .getResources()
                                .getIdentifier(
                                        secExpandableMenuPreference.mScanIconDrawableName,
                                        "drawable",
                                        secExpandableMenuPreference.getContext().getPackageName()),
                        null);
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

    public boolean isShown() {
        return this.mPreference.isShown();
    }

    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    public void onStopScanVi() {
        this.mPreference.mIsScanAnimationDone = true;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setStatus(SecurityDashboardConstants$Status securityDashboardConstants$Status) {
        this.mPreference.setStatus(securityDashboardConstants$Status);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void updateStateAfterVi() {
        updateState(this.mPreference);
    }

    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    public void setDefaultSummary() {}
}

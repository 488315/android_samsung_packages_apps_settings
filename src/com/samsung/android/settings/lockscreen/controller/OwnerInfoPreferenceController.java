package com.samsung.android.settings.lockscreen.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.RestrictedLockUtilsInternal;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.lockscreen.ContactInformation;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.widget.SecRestrictedPreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class OwnerInfoPreferenceController extends BasePreferenceController {
    private static final int CHANGE_OWNERINFO_TEXT = 200;
    private static final String TAG = "OwnerInfoPreferenceController";
    private LockPatternUtils mLockPatternUtils;
    private Fragment mParentFragment;
    private SecRestrictedPreference mPreference;
    private int mRequestCode;
    private int mUserId;

    public OwnerInfoPreferenceController(Context context, String str) {
        super(context, str);
        this.mLockPatternUtils = new LockPatternUtils(context);
        this.mUserId = UserHandle.myUserId();
    }

    private void setSummaryColorToPrimaryColor(boolean z) {
        SecRestrictedPreference secRestrictedPreference = this.mPreference;
        if (secRestrictedPreference != null) {
            secRestrictedPreference.getClass();
            SecPreferenceUtils.applySummaryColor(secRestrictedPreference, z);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecRestrictedPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mLockPatternUtils.isLockScreenDisabled(this.mUserId) ? 2 : 0;
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
        if (this.mLockPatternUtils.isDeviceOwnerInfoEnabled()) {
            String deviceOwnerInfo = this.mLockPatternUtils.getDeviceOwnerInfo();
            return deviceOwnerInfo != null ? deviceOwnerInfo.trim() : ApnSettings.MVNO_NONE;
        }
        String ownerInfo = this.mLockPatternUtils.getOwnerInfo(UserHandle.myUserId());
        if (ownerInfo == null || ownerInfo.isEmpty()) {
            setSummaryColorToPrimaryColor(false);
            return ApnSettings.MVNO_NONE;
        }
        String trim = ownerInfo.trim();
        setSummaryColorToPrimaryColor(true);
        return trim;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (getPreferenceKey().equals(preference.getKey())) {
            SALogging.insertSALog(
                    TextUtils.isEmpty(getSummary()) ? 0L : 1L,
                    String.valueOf(4400),
                    "LSS4462",
                    (String) null);
            if (this.mParentFragment.getFragmentManager().findFragmentByTag("dialog") != null) {
                return super.handlePreferenceTreeClick(preference);
            }
            ContactInformation contactInformation = new ContactInformation();
            Bundle bundle = new Bundle();
            bundle.putInt(
                    UniversalCredentialUtil.AGENT_TITLE, R.string.contact_info_settings_title);
            contactInformation.setArguments(bundle);
            contactInformation.setTargetFragment(this.mParentFragment, 200);
            contactInformation.show(this.mParentFragment.getFragmentManager(), "dialog");
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public void init(Fragment fragment, int i) {
        this.mParentFragment = fragment;
        this.mRequestCode = i;
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

    public void updatePreference() {
        this.mPreference.setVisible(isAvailable());
        updateState(this.mPreference);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (isAvailable()) {
            if (this.mLockPatternUtils.isDeviceOwnerInfoEnabled()) {
                this.mPreference.setDisabledByAdmin(
                        RestrictedLockUtilsInternal.getDeviceOwner(this.mContext));
            }
            super.updateState(preference);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}

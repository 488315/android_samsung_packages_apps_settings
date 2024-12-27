package com.android.settings.location;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.Utils;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.widget.SecRestrictedSwitchPreference;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LocationForPrivateProfilePreferenceController extends LocationBasePreferenceController
        implements Preference.OnPreferenceChangeListener {
    private SecRestrictedSwitchPreference mPreference;
    private final UserHandle mPrivateProfileHandle;

    public LocationForPrivateProfilePreferenceController(Context context, String str) {
        super(context, str);
        this.mPrivateProfileHandle = Utils.getProfileOfType(this.mUserManager, 4);
    }

    private boolean isPrivateProfileAvailable() {
        UserHandle userHandle = this.mPrivateProfileHandle;
        return (userHandle == null || this.mUserManager.isQuietModeEnabled(userHandle))
                ? false
                : true;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecRestrictedSwitchPreference secRestrictedSwitchPreference =
                (SecRestrictedSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = secRestrictedSwitchPreference;
        if (secRestrictedSwitchPreference != null) {
            secRestrictedSwitchPreference.setEnabled(isPrivateProfileAvailable());
        }
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (Flags.allowPrivateProfile()
                        && android.multiuser.Flags.enablePrivateSpaceFeatures()
                        && android.multiuser.Flags.handleInterleavedSettingsForPrivateSpace()
                        && isPrivateProfileAvailable())
                ? 0
                : 2;
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

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        return false;
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
        SecRestrictedSwitchPreference secRestrictedSwitchPreference = this.mPreference;
        if ((secRestrictedSwitchPreference == null || secRestrictedSwitchPreference.isVisible())
                && isAvailable()
                && isPrivateProfileAvailable()) {
            RestrictedLockUtils.EnforcedAdmin shareLocationEnforcedAdmin =
                    this.mLocationEnabler.getShareLocationEnforcedAdmin(
                            this.mPrivateProfileHandle.getIdentifier());
            if (shareLocationEnforcedAdmin != null) {
                this.mPreference.setDisabledByAdmin(shareLocationEnforcedAdmin);
                return;
            }
            boolean isEnabled = this.mLocationEnabler.isEnabled(i);
            this.mPreference.setEnabled(isEnabled);
            LocationEnabler locationEnabler = this.mLocationEnabler;
            if (RestrictedLockUtilsInternal.hasBaseUserRestriction(
                            locationEnabler.mContext,
                            this.mPrivateProfileHandle.getIdentifier(),
                            "no_share_location")
                    || !isEnabled) {
                this.mPreference.setChecked(false);
            } else {
                this.mPreference.setChecked(true);
            }
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        this.mUserManager.setUserRestriction(
                "no_share_location", !((Boolean) obj).booleanValue(), this.mPrivateProfileHandle);
        return true;
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

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}

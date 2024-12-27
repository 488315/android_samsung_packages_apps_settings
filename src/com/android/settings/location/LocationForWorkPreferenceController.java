package com.android.settings.location;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.Utils;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.widget.SecRestrictedSwitchPreference;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LocationForWorkPreferenceController extends LocationBasePreferenceController
        implements Preference.OnPreferenceChangeListener {
    private SecRestrictedSwitchPreference mPreference;

    public LocationForWorkPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecRestrictedSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        UserHandle userHandle;
        UserInfo userInfo;
        UserManager userManager = this.mUserManager;
        String str = KnoxUtils.mDeviceType;
        List<UserHandle> userProfiles = userManager.getUserProfiles();
        int size = userProfiles.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                userHandle = null;
                break;
            }
            userHandle = userProfiles.get(i);
            if (userHandle.getIdentifier() != userManager.getUserHandle()
                    && (userInfo = userManager.getUserInfo(userHandle.getIdentifier())) != null
                    && userInfo.isManagedProfile()
                    && !userInfo.isSecureFolder()
                    && !userInfo.isUserTypeAppSeparation()
                    && !userInfo.isDualAppProfile()) {
                break;
            }
            i++;
        }
        return userHandle != null ? 0 : 3;
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

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0048, code lost:

       if (com.android.settingslib.RestrictedLockUtilsInternal.hasBaseUserRestriction(r4.mContext, r0.getIdentifier(), "no_share_location") != false) goto L16;
    */
    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.location.LocationEnabler.LocationModeChangeListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onLocationModeChanged(int r3, boolean r4) {
        /*
            r2 = this;
            com.samsung.android.settings.widget.SecRestrictedSwitchPreference r4 = r2.mPreference
            boolean r4 = r4.isVisible()
            if (r4 == 0) goto L5a
            boolean r4 = r2.isAvailable()
            if (r4 != 0) goto Lf
            goto L5a
        Lf:
            com.android.settings.location.LocationEnabler r4 = r2.mLocationEnabler
            android.os.UserManager r0 = r2.mUserManager
            android.os.UserHandle r0 = com.android.settings.Utils.getManagedProfile(r0)
            int r0 = r0.getIdentifier()
            com.android.settingslib.RestrictedLockUtils$EnforcedAdmin r4 = r4.getShareLocationEnforcedAdmin(r0)
            if (r4 == 0) goto L27
            com.samsung.android.settings.widget.SecRestrictedSwitchPreference r2 = r2.mPreference
            r2.setDisabledByAdmin(r4)
            goto L5a
        L27:
            com.android.settings.location.LocationEnabler r4 = r2.mLocationEnabler
            boolean r3 = r4.isEnabled(r3)
            com.samsung.android.settings.widget.SecRestrictedSwitchPreference r4 = r2.mPreference
            r4.setEnabled(r3)
            com.android.settings.location.LocationEnabler r4 = r2.mLocationEnabler
            android.os.UserManager r0 = r4.mUserManager
            android.os.UserHandle r0 = com.android.settings.Utils.getManagedProfile(r0)
            if (r0 == 0) goto L4b
            int r0 = r0.getIdentifier()
            android.content.Context r4 = r4.mContext
            java.lang.String r1 = "no_share_location"
            boolean r4 = com.android.settingslib.RestrictedLockUtilsInternal.hasBaseUserRestriction(r4, r0, r1)
            if (r4 == 0) goto L4b
            goto L4d
        L4b:
            if (r3 != 0) goto L54
        L4d:
            com.samsung.android.settings.widget.SecRestrictedSwitchPreference r2 = r2.mPreference
            r3 = 0
            r2.setChecked(r3)
            goto L5a
        L54:
            com.samsung.android.settings.widget.SecRestrictedSwitchPreference r2 = r2.mPreference
            r3 = 1
            r2.setChecked(r3)
        L5a:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.location.LocationForWorkPreferenceController.onLocationModeChanged(int,"
                    + " boolean):void");
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        boolean booleanValue = ((Boolean) obj).booleanValue();
        UserManager userManager = this.mUserManager;
        userManager.setUserRestriction(
                "no_share_location", !booleanValue, Utils.getManagedProfile(userManager));
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

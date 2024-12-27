package com.android.settings.development;

import android.R;
import android.content.Context;
import android.content.om.IOverlayManager;
import android.content.pm.UserInfo;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserManager;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ForceEnableNotesRolePreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {

    @VisibleForTesting static final String OVERLAY_PACKAGE_NAME = "com.android.role.notes.enabled";
    public final IOverlayManager mOverlayManager;
    public final UserManager mUserManager;

    public ForceEnableNotesRolePreferenceController(Context context) {
        super(context);
        this.mOverlayManager =
                IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay"));
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "force_enable_notes_role";
    }

    @VisibleForTesting
    public boolean isEnabled() {
        return this.mContext.getResources().getBoolean(R.bool.config_enableDefaultNotes);
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        ((TwoStatePreference) this.mPreference).setChecked(false);
        setEnabled(false);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        setEnabled(((Boolean) obj).booleanValue());
        return true;
    }

    @VisibleForTesting
    public void setEnabled(boolean z) {
        try {
            for (UserInfo userInfo : this.mUserManager.getUsers()) {
                if (userInfo.isFull() || userInfo.isProfile()) {
                    try {
                        this.mOverlayManager.setEnabled(OVERLAY_PACKAGE_NAME, z, userInfo.id);
                    } catch (IllegalArgumentException e) {
                        Log.e("ForceEnableNotesRolePreferenceController", e.getMessage());
                    }
                }
            }
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ((TwoStatePreference) this.mPreference).setChecked(isEnabled());
    }
}

package com.android.settings.accounts;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedSwitchPreference;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ContactSearchPreferenceController extends TogglePreferenceController
        implements DefaultLifecycleObserver,
                ManagedProfileQuietModeEnabler.QuietModeChangeListener {
    private final UserHandle mManagedUser;
    private Preference mPreference;
    private final ManagedProfileQuietModeEnabler mQuietModeEnabler;

    public ContactSearchPreferenceController(Context context, String str) {
        super(context, str);
        this.mManagedUser =
                Utils.getManagedProfile((UserManager) context.getSystemService(UserManager.class));
        this.mQuietModeEnabler = new ManagedProfileQuietModeEnabler(context, this);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = findPreference;
        updateState(findPreference);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mQuietModeEnabler.mManagedProfile != null ? 0 : 4;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_accounts;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getSliceType() {
        return 1;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return (this.mManagedUser == null
                        || this.mQuietModeEnabler.isQuietModeEnabled()
                        || Settings.Secure.getIntForUser(
                                        this.mContext.getContentResolver(),
                                        "managed_profile_contact_remote_search",
                                        0,
                                        this.mManagedUser.getIdentifier())
                                == 0)
                ? false
                : true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onCreate(LifecycleOwner lifecycleOwner) {
        super.onCreate(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onDestroy(LifecycleOwner lifecycleOwner) {
        super.onDestroy(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onPause(LifecycleOwner lifecycleOwner) {
        super.onPause(lifecycleOwner);
    }

    @Override // com.android.settings.accounts.ManagedProfileQuietModeEnabler.QuietModeChangeListener
    public void onQuietModeChanged() {
        Preference preference = this.mPreference;
        if (preference != null) {
            updateState(preference);
        }
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onResume(LifecycleOwner lifecycleOwner) {
        super.onResume(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStart(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().addObserver(this.mQuietModeEnabler);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStop(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().removeObserver(this.mQuietModeEnabler);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        if (this.mManagedUser == null || this.mQuietModeEnabler.isQuietModeEnabled()) {
            return false;
        }
        Settings.Secure.putIntForUser(
                this.mContext.getContentResolver(),
                "managed_profile_contact_remote_search",
                z ? 1 : 0,
                this.mManagedUser.getIdentifier());
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        DevicePolicyManager devicePolicyManager;
        ComponentName profileOwnerAsUser;
        super.updateState(preference);
        if (preference instanceof RestrictedSwitchPreference) {
            RestrictedSwitchPreference restrictedSwitchPreference =
                    (RestrictedSwitchPreference) preference;
            restrictedSwitchPreference.setChecked(getThreadEnabled());
            restrictedSwitchPreference.setEnabled(!this.mQuietModeEnabler.isQuietModeEnabled());
            UserHandle userHandle = this.mManagedUser;
            if (userHandle != null) {
                Context context = this.mContext;
                int identifier = userHandle.getIdentifier();
                boolean z = RestrictedLockUtilsInternal.DEBUG;
                DevicePolicyManager devicePolicyManager2 =
                        (DevicePolicyManager) context.getSystemService("device_policy");
                RestrictedLockUtils.EnforcedAdmin enforcedAdmin = null;
                if (devicePolicyManager2 != null) {
                    RestrictedLockUtils.EnforcedAdmin enforcedAdmin2 =
                            (identifier == -10000
                                            || (devicePolicyManager =
                                                            (DevicePolicyManager)
                                                                    context.getSystemService(
                                                                            "device_policy"))
                                                    == null
                                            || (profileOwnerAsUser =
                                                            devicePolicyManager
                                                                    .getProfileOwnerAsUser(
                                                                            identifier))
                                                    == null)
                                    ? null
                                    : new RestrictedLockUtils.EnforcedAdmin(
                                            profileOwnerAsUser,
                                            null,
                                            RestrictedLockUtilsInternal.getUserHandleOf(
                                                    identifier));
                    if (enforcedAdmin2 != null) {
                        UserHandle of = UserHandle.of(identifier);
                        if (devicePolicyManager2.getCrossProfileContactsSearchDisabled(of)
                                && devicePolicyManager2.getCrossProfileCallerIdDisabled(of)) {
                            enforcedAdmin = enforcedAdmin2;
                        }
                    }
                }
                restrictedSwitchPreference.setDisabledByAdmin(enforcedAdmin);
            }
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}

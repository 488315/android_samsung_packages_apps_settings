package com.android.settings.users;

import android.content.Context;
import android.os.UserHandle;
import android.provider.Settings;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.SettingsActivity;
import com.android.settings.widget.MainSwitchBarController;
import com.android.settings.widget.SwitchWidgetController;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class MultiUserSwitchBarController
        implements SwitchWidgetController.OnSwitchChangeListener,
                LifecycleObserver,
                OnStart,
                OnStop {
    public final Context mContext;
    public final OnMultiUserSwitchChangedListener mListener;
    final SwitchWidgetController mSwitchBar;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnMultiUserSwitchChangedListener {}

    public MultiUserSwitchBarController(
            SettingsActivity settingsActivity,
            MainSwitchBarController mainSwitchBarController,
            OnMultiUserSwitchChangedListener onMultiUserSwitchChangedListener) {
        this.mContext = settingsActivity;
        this.mSwitchBar = mainSwitchBarController;
        this.mListener = onMultiUserSwitchChangedListener;
        UserCapabilities create = UserCapabilities.create(settingsActivity);
        mainSwitchBarController.setChecked(create.mUserSwitcherEnabled);
        if (create.mDisallowSwitchUser) {
            mainSwitchBarController.setDisabledByAdmin(
                    RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                            settingsActivity, UserHandle.myUserId(), "no_user_switch"));
        } else if (create.mDisallowAddUser) {
            mainSwitchBarController.setDisabledByAdmin(
                    RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                            settingsActivity, UserHandle.myUserId(), "no_add_user"));
        } else {
            mainSwitchBarController.setEnabled(create.mIsMain);
        }
        mainSwitchBarController.mListener = this;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public final void onStart() {
        this.mSwitchBar.startListening();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public final void onStop() {
        this.mSwitchBar.stopListening();
    }

    @Override // com.android.settings.widget.SwitchWidgetController.OnSwitchChangeListener
    public final boolean onSwitchToggled(boolean z) {
        OnMultiUserSwitchChangedListener onMultiUserSwitchChangedListener;
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "Toggling multi-user feature enabled state to: ", "MultiUserSwitchBarCtrl", z);
        boolean putInt =
                Settings.Global.putInt(
                        this.mContext.getContentResolver(), "user_switcher_enabled", z ? 1 : 0);
        LoggingHelper.insertEventLogging(96, 57510, z);
        if (putInt && (onMultiUserSwitchChangedListener = this.mListener) != null) {
            ((UserSettings) onMultiUserSwitchChangedListener).updateUI$4();
        }
        return putInt;
    }
}

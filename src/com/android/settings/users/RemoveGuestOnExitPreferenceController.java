package com.android.settings.users;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settingslib.RestrictedSwitchPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RemoveGuestOnExitPreferenceController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener {
    private static final int REMOVE_GUEST_ON_EXIT_DEFAULT = 1;
    private static final String TAG = "RemoveGuestOnExitPreferenceController";
    private static final String TAG_CONFIRM_GUEST_REMOVE = "confirmGuestRemove";
    private final Handler mHandler;
    private final Fragment mParentFragment;
    private final UserCapabilities mUserCaps;
    private final UserManager mUserManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class ConfirmGuestRemoveFragment extends InstrumentedDialogFragment
            implements DialogInterface.OnClickListener {
        public static final String TAG = ConfirmGuestRemoveFragment.class.getSimpleName();
        public boolean mEnabling;
        public int mGuestUserId;
        public Handler mHandler;
        public RestrictedSwitchPreference mPreference;

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 591;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            if (i != -1) {
                return;
            }
            UserManager userManager =
                    (UserManager) getContext().getSystemService(UserManager.class);
            String str = TAG;
            if (userManager == null) {
                Log.e(str, "Unable to get user manager service");
                return;
            }
            UserInfo userInfo = userManager.getUserInfo(this.mGuestUserId);
            if (userInfo == null || !userInfo.isGuest() || !this.mEnabling) {
                Log.w(str, "Removing guest user ... failed, id=" + this.mGuestUserId);
            } else if (this.mPreference != null) {
                if (!userManager.markGuestForDeletion(userInfo.id)) {
                    Log.w(str, "Couldn't mark the guest for deletion for user " + userInfo.id);
                } else {
                    userManager.removeUser(userInfo.id);
                    if (RemoveGuestOnExitPreferenceController.setChecked(
                            getContext(), this.mEnabling)) {
                        this.mPreference.setChecked(this.mEnabling);
                        this.mHandler.sendEmptyMessage(3);
                    }
                }
            }
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            final FragmentActivity activity = getActivity();
            if (bundle != null) {
                this.mEnabling = bundle.getBoolean("enabling");
                this.mGuestUserId = bundle.getInt("guestUserId");
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(R.string.remove_guest_on_exit_dialog_title);
            builder.setMessage(R.string.remove_guest_on_exit_dialog_message);
            builder.setPositiveButton(R.string.guest_exit_clear_data_button, this);
            builder.setNegativeButton(
                    android.R.string.cancel, (DialogInterface.OnClickListener) null);
            AlertDialog create = builder.create();
            create.setOnShowListener(
                    new DialogInterface
                            .OnShowListener() { // from class:
                                                // com.android.settings.users.RemoveGuestOnExitPreferenceController$ConfirmGuestRemoveFragment$$ExternalSyntheticLambda0
                        @Override // android.content.DialogInterface.OnShowListener
                        public final void onShow(DialogInterface dialogInterface) {
                            Context context = activity;
                            String str =
                                    RemoveGuestOnExitPreferenceController.ConfirmGuestRemoveFragment
                                            .TAG;
                            ((AlertDialog) dialogInterface)
                                    .getButton(-1)
                                    .setTextColor(
                                            context.getColor(
                                                    R.color
                                                            .sec_biometrics_dialog_remove_btn_color));
                        }
                    });
            return create;
        }

        @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
        public final void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            bundle.putBoolean("enabling", this.mEnabling);
            bundle.putInt("guestUserId", this.mGuestUserId);
        }
    }

    public RemoveGuestOnExitPreferenceController(
            Context context, String str, Fragment fragment, Handler handler) {
        super(context, str);
        this.mUserCaps = UserCapabilities.create(context);
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mParentFragment = fragment;
        this.mHandler = handler;
    }

    private boolean isChecked() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "remove_guest_on_exit", 1)
                != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean setChecked(Context context, boolean z) {
        Settings.Global.putInt(context.getContentResolver(), "remove_guest_on_exit", z ? 1 : 0);
        return true;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (UserManager.isGuestUserAlwaysEphemeral()
                || !UserManager.isGuestUserAllowEphemeralStateChange()) {
            return 4;
        }
        UserCapabilities userCapabilities = this.mUserCaps;
        if (!userCapabilities.mIsAdmin
                || userCapabilities.mDisallowAddUser
                || userCapabilities.mDisallowAddUserSetByAdmin) {
            return 4;
        }
        return userCapabilities.mUserSwitcherEnabled ? 0 : 2;
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

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        UserInfo findCurrentGuestUser = this.mUserManager.findCurrentGuestUser();
        if (findCurrentGuestUser == null) {
            return setChecked(this.mContext, booleanValue);
        }
        if (!findCurrentGuestUser.isInitialized()) {
            if (this.mUserManager.setUserEphemeral(findCurrentGuestUser.id, booleanValue)) {
                return setChecked(this.mContext, booleanValue);
            }
            Log.w(
                    TAG,
                    "Unused guest, id="
                            + findCurrentGuestUser.id
                            + ". Mark ephemeral as "
                            + booleanValue
                            + " failed !!!");
            return false;
        }
        if (findCurrentGuestUser.isInitialized()
                && !findCurrentGuestUser.isEphemeral()
                && booleanValue) {
            Fragment fragment = this.mParentFragment;
            Handler handler = this.mHandler;
            int i = findCurrentGuestUser.id;
            RestrictedSwitchPreference restrictedSwitchPreference =
                    (RestrictedSwitchPreference) preference;
            String str = ConfirmGuestRemoveFragment.TAG;
            if (fragment.isAdded()) {
                ConfirmGuestRemoveFragment confirmGuestRemoveFragment =
                        new ConfirmGuestRemoveFragment();
                confirmGuestRemoveFragment.mHandler = handler;
                confirmGuestRemoveFragment.mEnabling = booleanValue;
                confirmGuestRemoveFragment.mGuestUserId = i;
                confirmGuestRemoveFragment.setTargetFragment(fragment, 0);
                confirmGuestRemoveFragment.mPreference = restrictedSwitchPreference;
                confirmGuestRemoveFragment.show(
                        fragment.getFragmentManager(), TAG_CONFIRM_GUEST_REMOVE);
            }
        }
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        this.mUserCaps.updateAddUserCapabilities(this.mContext);
        RestrictedSwitchPreference restrictedSwitchPreference =
                (RestrictedSwitchPreference) preference;
        restrictedSwitchPreference.setChecked(isChecked());
        if (!isAvailable()) {
            restrictedSwitchPreference.setVisible(false);
            return;
        }
        UserCapabilities userCapabilities = this.mUserCaps;
        restrictedSwitchPreference.setDisabledByAdmin(
                userCapabilities.mDisallowAddUser ? userCapabilities.mEnforcedAdmin : null);
        restrictedSwitchPreference.setVisible(this.mUserCaps.mUserSwitcherEnabled);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}

package com.android.settings.users;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.SemUserInfo;
import android.content.pm.UserInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.utils.CustomDialogHelper;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import com.samsung.android.settings.widget.SecDividerItemDecorator;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UserDetailsSettings extends SettingsPreferenceFragment
        implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    Preference mAppAndContentAccessPref;
    Preference mAppCopyingPref;
    public Dialog mDialog;
    TwoStatePreference mGrantAdminPref;
    public Preference mGuestExitPreference;
    public Preference mGuestResetPreference;
    public GuestTelephonyPreferenceController mGuestTelephonyPreferenceController;
    public boolean mGuestUserAutoCreated;
    public final AnonymousClass6 mHandler;
    public TwoStatePreference mPhonePref;
    public RemoveGuestOnExitPreferenceController mRemoveGuestOnExitPreferenceController;
    Preference mRemoveUserPref;
    RestrictedPreference mSwitchUserPref;
    public UserCapabilities mUserCaps;
    public LayoutPreference mUserDetailHeader;
    UserInfo mUserInfo;
    public UserManager mUserManager;

    /* renamed from: -$$Nest$mexitGuest, reason: not valid java name */
    public static void m1015$$Nest$mexitGuest(UserDetailsSettings userDetailsSettings) {
        if (userDetailsSettings.mUserCaps.mIsGuest) {
            userDetailsSettings.mMetricsFeatureProvider.action(
                    userDetailsSettings.getActivity(), 1763, new Pair[0]);
            userDetailsSettings.switchToUserId(0);
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.users.UserDetailsSettings$6] */
    public UserDetailsSettings() {
        new AtomicBoolean();
        Executors.newSingleThreadExecutor();
        this.mHandler =
                new Handler() { // from class: com.android.settings.users.UserDetailsSettings.6
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        if (message.what != 3) {
                            return;
                        }
                        UserDetailsSettings.this.getActivity().onBackPressed();
                    }
                };
    }

    public static void updateDialogAnchorView$1(Dialog dialog, Preference preference) {
        if (preference == null || dialog == null) {
            return;
        }
        Rect rect = new Rect();
        preference.seslGetPreferenceBounds(rect);
        int width = (rect.width() / 2) + rect.left;
        int i = rect.bottom;
        if (width < 0 || i < 0) {
            return;
        }
        dialog.semSetAnchor(width, i);
    }

    public boolean canDeleteUser() {
        FragmentActivity activity;
        if (!this.mUserManager.isAdminUser()
                || this.mUserInfo.isMain()
                || (activity = getActivity()) == null) {
            return false;
        }
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced =
                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        activity, UserHandle.myUserId(), "no_remove_user");
        if (checkIfRestrictionEnforced == null) {
            return true;
        }
        RestrictedLockUtils.sendShowAdminSupportDetailsIntent(activity, checkIfRestrictionEnforced);
        return false;
    }

    public boolean canSwitchUserNow() {
        return this.mUserManager.getUserSwitchability() == 0;
    }

    public void clearAndExitGuest() {
        if (this.mUserCaps.mIsGuest) {
            this.mMetricsFeatureProvider.action(getActivity(), 1763, new Pair[0]);
            int myUserId = UserHandle.myUserId();
            if (!this.mUserManager.markGuestForDeletion(myUserId)) {
                RecordingInputConnection$$ExternalSyntheticOutline0.m(
                        myUserId,
                        "Couldn't mark the guest for deletion for user ",
                        "UserDetailsSettings");
                return;
            }
            if (Utils.isOnCall(getActivity())) {
                Toast.makeText(getActivity(), R.string.sec_user_mode_delete_toast_on_during_call, 1)
                        .show();
                return;
            }
            if (!canSwitchUserNow()) {
                Log.w(
                        "UserDetailsSettings",
                        "Cannot remove current user when switching is disabled");
                return;
            }
            try {
                ((UserManager) getContext().getSystemService(UserManager.class))
                        .removeUserWhenPossible(UserHandle.of(UserHandle.myUserId()), false);
                ActivityManager.getService().switchUser(0);
            } catch (RemoteException unused) {
                Log.e("UserDetailsSettings", "Unable to remove self user");
            }
        }
    }

    public final void enableCallsAndSms(boolean z) {
        this.mPhonePref.setChecked(z);
        for (int i : this.mUserManager.getProfileIdsWithDisabled(this.mUserInfo.id)) {
            UserHandle of = UserHandle.of(i);
            boolean z2 = !z;
            this.mUserManager.setUserRestriction("no_outgoing_calls", z2, of);
            this.mUserManager.setUserRestriction("no_sms", z2, of);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final int getDialogMetricsCategory(int i) {
        switch (i) {
            case 1:
            case 4:
            case 5:
                return 591;
            case 2:
                return 593;
            case 3:
                return 596;
            case 6:
                return VolteConstants.ErrorCode.CALL_FORBIDDEN;
            case 7:
                return 2000;
            default:
                switch (i) {
                    case 13:
                    case 14:
                    case 15:
                        return 600;
                    default:
                        return 0;
                }
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 98;
    }

    public void initialize(Context context, Bundle bundle) {
        int i =
                bundle != null
                        ? bundle.getInt(
                                UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, -10000)
                        : -10000;
        if (i == -10000) {
            throw new IllegalStateException("Arguments to this fragment must contain the user id");
        }
        boolean z = bundle.getBoolean("new_user", false);
        this.mUserInfo = this.mUserManager.getUserInfo(i);
        this.mSwitchUserPref = (RestrictedPreference) findPreference("switch_user");
        this.mPhonePref = (TwoStatePreference) findPreference("enable_calling");
        this.mRemoveUserPref = findPreference("remove_user");
        this.mAppAndContentAccessPref = findPreference("app_and_content_access");
        this.mAppCopyingPref = findPreference("app_copying");
        TwoStatePreference twoStatePreference =
                (TwoStatePreference) findPreference("user_grant_admin");
        this.mGrantAdminPref = twoStatePreference;
        twoStatePreference.setChecked(this.mUserInfo.isAdmin());
        LayoutPreference layoutPreference = (LayoutPreference) findPreference("user_detail_header");
        this.mUserDetailHeader = layoutPreference;
        ImageView imageView =
                (ImageView) layoutPreference.mRootView.findViewById(R.id.photo_default);
        imageView.setImageDrawable(
                com.android.settingslib.Utils.secGetUserIcon(
                        context, this.mUserManager, this.mUserInfo));
        imageView.setContentDescription(
                context.getResources().getString(R.string.user_image_photo_selector));
        ((ImageView) this.mUserDetailHeader.mRootView.findViewById(R.id.add_a_photo_icon))
                .setImageDrawable(
                        context.getResources().getDrawable(R.drawable.add_a_photo_circled));
        ((TextView) this.mUserDetailHeader.mRootView.findViewById(R.id.userName))
                .setText(this.mUserInfo.name);
        this.mSwitchUserPref.setTitle(
                context.getString(R.string.user_switch_to_user, this.mUserInfo.name));
        if (this.mUserCaps.mDisallowSwitchUser) {
            this.mSwitchUserPref.setDisabledByAdmin(
                    RestrictedLockUtilsInternal.getDeviceOwner(context));
        } else {
            this.mSwitchUserPref.setDisabledByAdmin(null);
            this.mSwitchUserPref.setSelectable(true);
            this.mSwitchUserPref.setOnPreferenceClickListener(this);
        }
        if (this.mUserInfo.isMain()
                || this.mUserInfo.isGuest()
                || !UserManager.isMultipleAdminEnabled()
                || this.mUserManager.hasUserRestrictionForUser(
                        "no_grant_admin", this.mUserInfo.getUserHandle())) {
            removePreference("user_grant_admin");
        }
        this.mGuestTelephonyPreferenceController =
                new GuestTelephonyPreferenceController(context, "enable_guest_calling");
        this.mRemoveGuestOnExitPreferenceController =
                new RemoveGuestOnExitPreferenceController(
                        context, "remove_guest_on_exit", this, this.mHandler);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        this.mGuestTelephonyPreferenceController.displayPreference(preferenceScreen);
        this.mRemoveGuestOnExitPreferenceController.displayPreference(preferenceScreen);
        preferenceScreen
                .findPreference(this.mGuestTelephonyPreferenceController.getPreferenceKey())
                .setOnPreferenceChangeListener(this.mGuestTelephonyPreferenceController);
        preferenceScreen
                .findPreference(this.mRemoveGuestOnExitPreferenceController.getPreferenceKey())
                .setOnPreferenceChangeListener(this.mRemoveGuestOnExitPreferenceController);
        Preference findPreference = findPreference("guest_reset");
        this.mGuestResetPreference = findPreference;
        findPreference.setOnPreferenceClickListener(this);
        this.mGuestExitPreference = findPreference("guest_exit");
        this.mGuestExitPreference.setSummary(
                this.mUserCaps.mIsEphemeral
                        ? context.getString(R.string.guest_notification_ephemeral)
                        : context.getString(R.string.guest_notification_non_ephemeral));
        this.mGuestExitPreference.setOnPreferenceClickListener(this);
        if (this.mUserInfo.isGuest() && this.mUserCaps.mIsGuest) {
            removePreference("switch_user");
            removePreference("remove_guest_on_exit");
            removePreference("enable_guest_calling");
        } else if (this.mUserInfo.isGuest() && this.mUserCaps.mIsAdmin) {
            removePreference("guest_reset");
            removePreference("guest_exit");
        } else {
            removePreference("guest_reset");
            removePreference("guest_exit");
            removePreference("remove_guest_on_exit");
            removePreference("enable_guest_calling");
        }
        if (!this.mUserManager.isAdminUser()) {
            removePreference("enable_calling");
            removePreference("remove_user");
            removePreference("user_grant_admin");
            removePreference("app_and_content_access");
            removePreference("app_copying");
            return;
        }
        if (!Utils.isVoiceCapable(context)) {
            removePreference("enable_calling");
        }
        if (this.mUserInfo.isMain() || UserManager.isHeadlessSystemUserMode()) {
            removePreference("enable_calling");
        }
        if (this.mUserInfo.isRestricted()) {
            removePreference("enable_calling");
            if (z) {
                openAppAndContentAccessScreen(true);
            }
        } else {
            removePreference("app_and_content_access");
        }
        if (this.mUserInfo.isGuest()) {
            removePreference("enable_calling");
            this.mRemoveUserPref.setTitle(
                    this.mGuestUserAutoCreated
                            ? R.string.guest_reset_guest
                            : R.string.guest_exit_guest);
            if (this.mGuestUserAutoCreated) {
                this.mRemoveUserPref.setEnabled((this.mUserInfo.flags & 16) != 0);
            }
            removePreference("app_copying");
        } else {
            this.mPhonePref.setChecked(
                    !this.mUserManager.hasUserRestriction("no_outgoing_calls", new UserHandle(i)));
            this.mRemoveUserPref.setTitle(R.string.user_remove_user);
            removePreference("app_copying");
        }
        if (RestrictedLockUtilsInternal.hasBaseUserRestriction(
                        context, UserHandle.myUserId(), "no_remove_user")
                || this.mUserInfo.isMain()) {
            removePreference("remove_user");
        }
        this.mRemoveUserPref.setOnPreferenceClickListener(this);
        this.mPhonePref.setOnPreferenceChangeListener(this);
        this.mGrantAdminPref.setOnPreferenceChangeListener(this);
        this.mAppAndContentAccessPref.setOnPreferenceClickListener(this);
        this.mAppCopyingPref.setOnPreferenceClickListener(this);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mUserManager = (UserManager) activity.getSystemService("user");
        this.mUserCaps = UserCapabilities.create(activity);
        addPreferencesFromResource(R.xml.user_details_settings);
        this.mGuestUserAutoCreated =
                getPrefContext().getResources().getBoolean(android.R.bool.config_imeDrawsImeNavBar);
        initialize(activity, getArguments());
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(final int i) {
        final FragmentActivity activity = getActivity();
        if (activity == null) {
            return null;
        }
        if (getView() != null) {
            getView()
                    .addOnLayoutChangeListener(
                            new View
                                    .OnLayoutChangeListener() { // from class:
                                                                // com.android.settings.users.UserDetailsSettings.1
                                @Override // android.view.View.OnLayoutChangeListener
                                public final void onLayoutChange(
                                        View view,
                                        int i2,
                                        int i3,
                                        int i4,
                                        int i5,
                                        int i6,
                                        int i7,
                                        int i8,
                                        int i9) {
                                    Dialog dialog = UserDetailsSettings.this.mDialog;
                                    if (dialog == null || !dialog.isShowing()) {
                                        return;
                                    }
                                    int i10 = i;
                                    switch (i10) {
                                        case 1:
                                            UserDetailsSettings userDetailsSettings =
                                                    UserDetailsSettings.this;
                                            UserDetailsSettings.updateDialogAnchorView$1(
                                                    userDetailsSettings.mDialog,
                                                    userDetailsSettings.mRemoveUserPref);
                                            break;
                                        case 2:
                                            UserDetailsSettings userDetailsSettings2 =
                                                    UserDetailsSettings.this;
                                            UserDetailsSettings.updateDialogAnchorView$1(
                                                    userDetailsSettings2.mDialog,
                                                    userDetailsSettings2.mPhonePref);
                                            break;
                                        case 3:
                                            UserDetailsSettings userDetailsSettings3 =
                                                    UserDetailsSettings.this;
                                            UserDetailsSettings.updateDialogAnchorView$1(
                                                    userDetailsSettings3.mDialog,
                                                    userDetailsSettings3.mSwitchUserPref);
                                            break;
                                        case 4:
                                            UserDetailsSettings userDetailsSettings4 =
                                                    UserDetailsSettings.this;
                                            UserDetailsSettings.updateDialogAnchorView$1(
                                                    userDetailsSettings4.mDialog,
                                                    userDetailsSettings4.mSwitchUserPref);
                                            break;
                                        case 5:
                                            UserDetailsSettings userDetailsSettings5 =
                                                    UserDetailsSettings.this;
                                            UserDetailsSettings.updateDialogAnchorView$1(
                                                    userDetailsSettings5.mDialog,
                                                    userDetailsSettings5.mSwitchUserPref);
                                            break;
                                        case 6:
                                            UserDetailsSettings userDetailsSettings6 =
                                                    UserDetailsSettings.this;
                                            UserDetailsSettings.updateDialogAnchorView$1(
                                                    userDetailsSettings6.mDialog,
                                                    userDetailsSettings6.mGrantAdminPref);
                                            break;
                                        default:
                                            switch (i10) {
                                                case 13:
                                                    UserDetailsSettings userDetailsSettings7 =
                                                            UserDetailsSettings.this;
                                                    UserDetailsSettings.updateDialogAnchorView$1(
                                                            userDetailsSettings7.mDialog,
                                                            userDetailsSettings7
                                                                    .mGuestResetPreference);
                                                    break;
                                                case 14:
                                                    UserDetailsSettings userDetailsSettings8 =
                                                            UserDetailsSettings.this;
                                                    UserDetailsSettings.updateDialogAnchorView$1(
                                                            userDetailsSettings8.mDialog,
                                                            userDetailsSettings8
                                                                    .mGuestExitPreference);
                                                    break;
                                                case 15:
                                                    UserDetailsSettings userDetailsSettings9 =
                                                            UserDetailsSettings.this;
                                                    UserDetailsSettings.updateDialogAnchorView$1(
                                                            userDetailsSettings9.mDialog,
                                                            userDetailsSettings9
                                                                    .mGuestExitPreference);
                                                    break;
                                            }
                                    }
                                }
                            });
        }
        switch (i) {
            case 1:
                final int i2 = 0;
                AlertDialog createRemoveDialog =
                        UserDialogs.createRemoveDialog(
                                getActivity(),
                                this.mUserInfo.id,
                                new DialogInterface.OnClickListener(
                                        this) { // from class:
                                                // com.android.settings.users.UserDetailsSettings$$ExternalSyntheticLambda0
                                    public final /* synthetic */ UserDetailsSettings f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i3) {
                                        int i4 = i2;
                                        UserDetailsSettings userDetailsSettings = this.f$0;
                                        switch (i4) {
                                            case 0:
                                                userDetailsSettings.mUserManager.removeUser(
                                                        userDetailsSettings.mUserInfo.id);
                                                userDetailsSettings.finishFragment();
                                                break;
                                            case 1:
                                                int i5 = UserDetailsSettings.$r8$clinit;
                                                userDetailsSettings.enableCallsAndSms(true);
                                                break;
                                            case 2:
                                                int i6 = UserDetailsSettings.$r8$clinit;
                                                if (userDetailsSettings.canSwitchUserNow()) {
                                                    userDetailsSettings.switchUser();
                                                    break;
                                                }
                                                break;
                                            case 3:
                                                int i7 = UserDetailsSettings.$r8$clinit;
                                                userDetailsSettings.resetGuest();
                                                break;
                                            case 4:
                                                int i8 = UserDetailsSettings.$r8$clinit;
                                                userDetailsSettings.resetGuest();
                                                break;
                                            case 5:
                                                int i9 = UserDetailsSettings.$r8$clinit;
                                                userDetailsSettings.switchUser();
                                                break;
                                            default:
                                                int i10 = UserDetailsSettings.$r8$clinit;
                                                userDetailsSettings.switchUser();
                                                break;
                                        }
                                    }
                                });
                this.mDialog = createRemoveDialog;
                updateDialogAnchorView$1(createRemoveDialog, this.mRemoveUserPref);
                return this.mDialog;
            case 2:
                FragmentActivity activity2 = getActivity();
                final int i3 = 1;
                DialogInterface.OnClickListener onClickListener =
                        new DialogInterface.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.users.UserDetailsSettings$$ExternalSyntheticLambda0
                            public final /* synthetic */ UserDetailsSettings f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i32) {
                                int i4 = i3;
                                UserDetailsSettings userDetailsSettings = this.f$0;
                                switch (i4) {
                                    case 0:
                                        userDetailsSettings.mUserManager.removeUser(
                                                userDetailsSettings.mUserInfo.id);
                                        userDetailsSettings.finishFragment();
                                        break;
                                    case 1:
                                        int i5 = UserDetailsSettings.$r8$clinit;
                                        userDetailsSettings.enableCallsAndSms(true);
                                        break;
                                    case 2:
                                        int i6 = UserDetailsSettings.$r8$clinit;
                                        if (userDetailsSettings.canSwitchUserNow()) {
                                            userDetailsSettings.switchUser();
                                            break;
                                        }
                                        break;
                                    case 3:
                                        int i7 = UserDetailsSettings.$r8$clinit;
                                        userDetailsSettings.resetGuest();
                                        break;
                                    case 4:
                                        int i8 = UserDetailsSettings.$r8$clinit;
                                        userDetailsSettings.resetGuest();
                                        break;
                                    case 5:
                                        int i9 = UserDetailsSettings.$r8$clinit;
                                        userDetailsSettings.switchUser();
                                        break;
                                    default:
                                        int i10 = UserDetailsSettings.$r8$clinit;
                                        userDetailsSettings.switchUser();
                                        break;
                                }
                            }
                        };
                AlertDialog.Builder builder = new AlertDialog.Builder(activity2);
                builder.setMessage(R.string.user_enable_calling_and_sms_confirm_message);
                builder.setPositiveButton(R.string.okay, onClickListener);
                builder.setNegativeButton(
                        android.R.string.cancel, (DialogInterface.OnClickListener) null);
                AlertDialog create = builder.create();
                this.mDialog = create;
                updateDialogAnchorView$1(create, this.mPhonePref);
                return this.mDialog;
            case 3:
                FragmentActivity activity3 = getActivity();
                final int i4 = 2;
                DialogInterface.OnClickListener onClickListener2 =
                        new DialogInterface.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.users.UserDetailsSettings$$ExternalSyntheticLambda0
                            public final /* synthetic */ UserDetailsSettings f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i32) {
                                int i42 = i4;
                                UserDetailsSettings userDetailsSettings = this.f$0;
                                switch (i42) {
                                    case 0:
                                        userDetailsSettings.mUserManager.removeUser(
                                                userDetailsSettings.mUserInfo.id);
                                        userDetailsSettings.finishFragment();
                                        break;
                                    case 1:
                                        int i5 = UserDetailsSettings.$r8$clinit;
                                        userDetailsSettings.enableCallsAndSms(true);
                                        break;
                                    case 2:
                                        int i6 = UserDetailsSettings.$r8$clinit;
                                        if (userDetailsSettings.canSwitchUserNow()) {
                                            userDetailsSettings.switchUser();
                                            break;
                                        }
                                        break;
                                    case 3:
                                        int i7 = UserDetailsSettings.$r8$clinit;
                                        userDetailsSettings.resetGuest();
                                        break;
                                    case 4:
                                        int i8 = UserDetailsSettings.$r8$clinit;
                                        userDetailsSettings.resetGuest();
                                        break;
                                    case 5:
                                        int i9 = UserDetailsSettings.$r8$clinit;
                                        userDetailsSettings.switchUser();
                                        break;
                                    default:
                                        int i10 = UserDetailsSettings.$r8$clinit;
                                        userDetailsSettings.switchUser();
                                        break;
                                }
                            }
                        };
                AlertDialog.Builder builder2 = new AlertDialog.Builder(activity3);
                builder2.setTitle(R.string.user_setup_dialog_title);
                builder2.setMessage(R.string.user_setup_dialog_message);
                builder2.setPositiveButton(R.string.user_setup_button_setup_now, onClickListener2);
                builder2.setNegativeButton(
                        R.string.user_setup_button_setup_later,
                        (DialogInterface.OnClickListener) null);
                AlertDialog create2 = builder2.create();
                this.mDialog = create2;
                updateDialogAnchorView$1(create2, this.mSwitchUserPref);
                return this.mDialog;
            case 4:
                if (this.mGuestUserAutoCreated) {
                    final int i5 = 3;
                    this.mDialog =
                            UserDialogs.createResetGuestDialog(
                                    getActivity(),
                                    new DialogInterface.OnClickListener(
                                            this) { // from class:
                                                    // com.android.settings.users.UserDetailsSettings$$ExternalSyntheticLambda0
                                        public final /* synthetic */ UserDetailsSettings f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // android.content.DialogInterface.OnClickListener
                                        public final void onClick(
                                                DialogInterface dialogInterface, int i32) {
                                            int i42 = i5;
                                            UserDetailsSettings userDetailsSettings = this.f$0;
                                            switch (i42) {
                                                case 0:
                                                    userDetailsSettings.mUserManager.removeUser(
                                                            userDetailsSettings.mUserInfo.id);
                                                    userDetailsSettings.finishFragment();
                                                    break;
                                                case 1:
                                                    int i52 = UserDetailsSettings.$r8$clinit;
                                                    userDetailsSettings.enableCallsAndSms(true);
                                                    break;
                                                case 2:
                                                    int i6 = UserDetailsSettings.$r8$clinit;
                                                    if (userDetailsSettings.canSwitchUserNow()) {
                                                        userDetailsSettings.switchUser();
                                                        break;
                                                    }
                                                    break;
                                                case 3:
                                                    int i7 = UserDetailsSettings.$r8$clinit;
                                                    userDetailsSettings.resetGuest();
                                                    break;
                                                case 4:
                                                    int i8 = UserDetailsSettings.$r8$clinit;
                                                    userDetailsSettings.resetGuest();
                                                    break;
                                                case 5:
                                                    int i9 = UserDetailsSettings.$r8$clinit;
                                                    userDetailsSettings.switchUser();
                                                    break;
                                                default:
                                                    int i10 = UserDetailsSettings.$r8$clinit;
                                                    userDetailsSettings.switchUser();
                                                    break;
                                            }
                                        }
                                    });
                } else {
                    final int i6 = 4;
                    this.mDialog =
                            UserDialogs.createRemoveGuestDialog(
                                    getActivity(),
                                    new DialogInterface.OnClickListener(
                                            this) { // from class:
                                                    // com.android.settings.users.UserDetailsSettings$$ExternalSyntheticLambda0
                                        public final /* synthetic */ UserDetailsSettings f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // android.content.DialogInterface.OnClickListener
                                        public final void onClick(
                                                DialogInterface dialogInterface, int i32) {
                                            int i42 = i6;
                                            UserDetailsSettings userDetailsSettings = this.f$0;
                                            switch (i42) {
                                                case 0:
                                                    userDetailsSettings.mUserManager.removeUser(
                                                            userDetailsSettings.mUserInfo.id);
                                                    userDetailsSettings.finishFragment();
                                                    break;
                                                case 1:
                                                    int i52 = UserDetailsSettings.$r8$clinit;
                                                    userDetailsSettings.enableCallsAndSms(true);
                                                    break;
                                                case 2:
                                                    int i62 = UserDetailsSettings.$r8$clinit;
                                                    if (userDetailsSettings.canSwitchUserNow()) {
                                                        userDetailsSettings.switchUser();
                                                        break;
                                                    }
                                                    break;
                                                case 3:
                                                    int i7 = UserDetailsSettings.$r8$clinit;
                                                    userDetailsSettings.resetGuest();
                                                    break;
                                                case 4:
                                                    int i8 = UserDetailsSettings.$r8$clinit;
                                                    userDetailsSettings.resetGuest();
                                                    break;
                                                case 5:
                                                    int i9 = UserDetailsSettings.$r8$clinit;
                                                    userDetailsSettings.switchUser();
                                                    break;
                                                default:
                                                    int i10 = UserDetailsSettings.$r8$clinit;
                                                    userDetailsSettings.switchUser();
                                                    break;
                                            }
                                        }
                                    });
                }
                updateDialogAnchorView$1(this.mDialog, this.mSwitchUserPref);
                return this.mDialog;
            case 5:
                if (this.mGuestUserAutoCreated) {
                    final int i7 = 5;
                    this.mDialog =
                            UserDialogs.createResetGuestDialog(
                                    getActivity(),
                                    new DialogInterface.OnClickListener(
                                            this) { // from class:
                                                    // com.android.settings.users.UserDetailsSettings$$ExternalSyntheticLambda0
                                        public final /* synthetic */ UserDetailsSettings f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // android.content.DialogInterface.OnClickListener
                                        public final void onClick(
                                                DialogInterface dialogInterface, int i32) {
                                            int i42 = i7;
                                            UserDetailsSettings userDetailsSettings = this.f$0;
                                            switch (i42) {
                                                case 0:
                                                    userDetailsSettings.mUserManager.removeUser(
                                                            userDetailsSettings.mUserInfo.id);
                                                    userDetailsSettings.finishFragment();
                                                    break;
                                                case 1:
                                                    int i52 = UserDetailsSettings.$r8$clinit;
                                                    userDetailsSettings.enableCallsAndSms(true);
                                                    break;
                                                case 2:
                                                    int i62 = UserDetailsSettings.$r8$clinit;
                                                    if (userDetailsSettings.canSwitchUserNow()) {
                                                        userDetailsSettings.switchUser();
                                                        break;
                                                    }
                                                    break;
                                                case 3:
                                                    int i72 = UserDetailsSettings.$r8$clinit;
                                                    userDetailsSettings.resetGuest();
                                                    break;
                                                case 4:
                                                    int i8 = UserDetailsSettings.$r8$clinit;
                                                    userDetailsSettings.resetGuest();
                                                    break;
                                                case 5:
                                                    int i9 = UserDetailsSettings.$r8$clinit;
                                                    userDetailsSettings.switchUser();
                                                    break;
                                                default:
                                                    int i10 = UserDetailsSettings.$r8$clinit;
                                                    userDetailsSettings.switchUser();
                                                    break;
                                            }
                                        }
                                    });
                } else {
                    final int i8 = 6;
                    this.mDialog =
                            UserDialogs.createRemoveGuestDialog(
                                    getActivity(),
                                    new DialogInterface.OnClickListener(
                                            this) { // from class:
                                                    // com.android.settings.users.UserDetailsSettings$$ExternalSyntheticLambda0
                                        public final /* synthetic */ UserDetailsSettings f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // android.content.DialogInterface.OnClickListener
                                        public final void onClick(
                                                DialogInterface dialogInterface, int i32) {
                                            int i42 = i8;
                                            UserDetailsSettings userDetailsSettings = this.f$0;
                                            switch (i42) {
                                                case 0:
                                                    userDetailsSettings.mUserManager.removeUser(
                                                            userDetailsSettings.mUserInfo.id);
                                                    userDetailsSettings.finishFragment();
                                                    break;
                                                case 1:
                                                    int i52 = UserDetailsSettings.$r8$clinit;
                                                    userDetailsSettings.enableCallsAndSms(true);
                                                    break;
                                                case 2:
                                                    int i62 = UserDetailsSettings.$r8$clinit;
                                                    if (userDetailsSettings.canSwitchUserNow()) {
                                                        userDetailsSettings.switchUser();
                                                        break;
                                                    }
                                                    break;
                                                case 3:
                                                    int i72 = UserDetailsSettings.$r8$clinit;
                                                    userDetailsSettings.resetGuest();
                                                    break;
                                                case 4:
                                                    int i82 = UserDetailsSettings.$r8$clinit;
                                                    userDetailsSettings.resetGuest();
                                                    break;
                                                case 5:
                                                    int i9 = UserDetailsSettings.$r8$clinit;
                                                    userDetailsSettings.switchUser();
                                                    break;
                                                default:
                                                    int i10 = UserDetailsSettings.$r8$clinit;
                                                    userDetailsSettings.switchUser();
                                                    break;
                                            }
                                        }
                                    });
                }
                updateDialogAnchorView$1(this.mDialog, this.mSwitchUserPref);
                return this.mDialog;
            case 6:
                Context context = getContext();
                final CustomDialogHelper customDialogHelper = new CustomDialogHelper(context);
                customDialogHelper.mDialogIcon.setImageDrawable(
                        context.getDrawable(R.drawable.ic_admin_panel_settings));
                customDialogHelper.setTitle(R.string.user_revoke_admin_confirm_title);
                customDialogHelper.setMessage(R.string.user_revoke_admin_confirm_message);
                customDialogHelper.mDialogMessage.setPadding(20, 20, 20, 20);
                final int i9 = 0;
                customDialogHelper.setButton(
                        6,
                        R.string.remove,
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.users.UserDetailsSettings$$ExternalSyntheticLambda10
                            public final /* synthetic */ UserDetailsSettings f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i9) {
                                    case 0:
                                        UserDetailsSettings userDetailsSettings = this.f$0;
                                        CustomDialogHelper customDialogHelper2 = customDialogHelper;
                                        int i10 = UserDetailsSettings.$r8$clinit;
                                        userDetailsSettings.updateUserAdminStatus(false);
                                        customDialogHelper2.mDialog.dismiss();
                                        break;
                                    default:
                                        UserDetailsSettings userDetailsSettings2 = this.f$0;
                                        CustomDialogHelper customDialogHelper3 = customDialogHelper;
                                        int i11 = UserDetailsSettings.$r8$clinit;
                                        userDetailsSettings2.updateUserAdminStatus(true);
                                        customDialogHelper3.mDialog.dismiss();
                                        break;
                                }
                            }
                        });
                final int i10 = 0;
                customDialogHelper.setButton(
                        4,
                        R.string.cancel,
                        new View
                                .OnClickListener() { // from class:
                                                     // com.android.settings.users.UserDetailsSettings$$ExternalSyntheticLambda11
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i11 = i10;
                                CustomDialogHelper customDialogHelper2 = customDialogHelper;
                                switch (i11) {
                                    case 0:
                                        int i12 = UserDetailsSettings.$r8$clinit;
                                        customDialogHelper2.mDialog.dismiss();
                                        break;
                                    default:
                                        int i13 = UserDetailsSettings.$r8$clinit;
                                        customDialogHelper2.mDialog.dismiss();
                                        break;
                                }
                            }
                        });
                Dialog dialog = customDialogHelper.mDialog;
                this.mDialog = dialog;
                updateDialogAnchorView$1(dialog, this.mGrantAdminPref);
                return this.mDialog;
            case 7:
                Context context2 = getContext();
                final CustomDialogHelper customDialogHelper2 = new CustomDialogHelper(context2);
                customDialogHelper2.mDialogIcon.setImageDrawable(
                        context2.getDrawable(R.drawable.ic_admin_panel_settings));
                customDialogHelper2.setTitle(R.string.user_grant_admin_title);
                customDialogHelper2.setMessage(R.string.user_grant_admin_message);
                customDialogHelper2.mDialogMessage.setPadding(20, 20, 20, 20);
                final int i11 = 1;
                customDialogHelper2.setButton(
                        6,
                        R.string.user_grant_admin_button,
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.users.UserDetailsSettings$$ExternalSyntheticLambda10
                            public final /* synthetic */ UserDetailsSettings f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i11) {
                                    case 0:
                                        UserDetailsSettings userDetailsSettings = this.f$0;
                                        CustomDialogHelper customDialogHelper22 =
                                                customDialogHelper2;
                                        int i102 = UserDetailsSettings.$r8$clinit;
                                        userDetailsSettings.updateUserAdminStatus(false);
                                        customDialogHelper22.mDialog.dismiss();
                                        break;
                                    default:
                                        UserDetailsSettings userDetailsSettings2 = this.f$0;
                                        CustomDialogHelper customDialogHelper3 =
                                                customDialogHelper2;
                                        int i112 = UserDetailsSettings.$r8$clinit;
                                        userDetailsSettings2.updateUserAdminStatus(true);
                                        customDialogHelper3.mDialog.dismiss();
                                        break;
                                }
                            }
                        });
                final int i12 = 1;
                customDialogHelper2.setButton(
                        4,
                        R.string.cancel,
                        new View
                                .OnClickListener() { // from class:
                                                     // com.android.settings.users.UserDetailsSettings$$ExternalSyntheticLambda11
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i112 = i12;
                                CustomDialogHelper customDialogHelper22 = customDialogHelper2;
                                switch (i112) {
                                    case 0:
                                        int i122 = UserDetailsSettings.$r8$clinit;
                                        customDialogHelper22.mDialog.dismiss();
                                        break;
                                    default:
                                        int i13 = UserDetailsSettings.$r8$clinit;
                                        customDialogHelper22.mDialog.dismiss();
                                        break;
                                }
                            }
                        });
                Dialog dialog2 = customDialogHelper2.mDialog;
                this.mDialog = dialog2;
                updateDialogAnchorView$1(dialog2, this.mGrantAdminPref);
                return this.mDialog;
            default:
                switch (i) {
                    case 13:
                        AlertDialog.Builder builder3 = new AlertDialog.Builder(activity);
                        builder3.setTitle(R.string.guest_reset_and_restart_dialog_title);
                        builder3.setMessage(R.string.guest_reset_and_restart_dialog_message);
                        final int i13 = 3;
                        builder3.setPositiveButton(
                                R.string.guest_reset_guest_confirm_button,
                                new DialogInterface.OnClickListener(this) { // from class:
                                    // com.android.settings.users.UserDetailsSettings.2
                                    public final /* synthetic */ UserDetailsSettings this$0;

                                    {
                                        this.this$0 = this;
                                    }

                                    /* JADX WARN: Removed duplicated region for block: B:19:0x0043 A[Catch: RemoteException -> 0x0063, TryCatch #0 {RemoteException -> 0x0063, blocks: (B:11:0x0022, B:14:0x0028, B:16:0x0030, B:19:0x0043, B:21:0x005a, B:23:0x0037, B:26:0x003b), top: B:10:0x0022, inners: #1 }] */
                                    /* JADX WARN: Removed duplicated region for block: B:21:0x005a A[Catch: RemoteException -> 0x0063, TRY_LEAVE, TryCatch #0 {RemoteException -> 0x0063, blocks: (B:11:0x0022, B:14:0x0028, B:16:0x0030, B:19:0x0043, B:21:0x005a, B:23:0x0037, B:26:0x003b), top: B:10:0x0022, inners: #1 }] */
                                    @Override // android.content.DialogInterface.OnClickListener
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                                    */
                                    public final void onClick(
                                            android.content.DialogInterface r4, int r5) {
                                        /*
                                            r3 = this;
                                            int r4 = r2
                                            switch(r4) {
                                                case 0: goto L75;
                                                case 1: goto L6f;
                                                case 2: goto L69;
                                                default: goto L5;
                                            }
                                        L5:
                                            com.android.settings.users.UserDetailsSettings r3 = r3.this$0
                                            com.android.settings.users.UserCapabilities r4 = r3.mUserCaps
                                            boolean r4 = r4.mIsGuest
                                            if (r4 != 0) goto Le
                                            goto L68
                                        Le:
                                            int r4 = android.os.UserHandle.myUserId()
                                            android.os.UserManager r5 = r3.mUserManager
                                            boolean r5 = r5.markGuestForDeletion(r4)
                                            java.lang.String r0 = "UserDetailsSettings"
                                            if (r5 != 0) goto L22
                                            java.lang.String r3 = "Couldn't mark the guest for deletion for user "
                                            androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0.m(r4, r3, r0)
                                            goto L68
                                        L22:
                                            android.content.Context r5 = r3.getPrefContext()     // Catch: android.os.RemoteException -> L63
                                            r1 = -10000(0xffffffffffffd8f0, float:NaN)
                                            android.os.UserManager r2 = r3.mUserManager     // Catch: android.os.UserManager.UserOperationException -> L3a android.os.RemoteException -> L63
                                            android.content.pm.UserInfo r5 = r2.createGuest(r5)     // Catch: android.os.UserManager.UserOperationException -> L3a android.os.RemoteException -> L63
                                            if (r5 != 0) goto L37
                                            java.lang.String r5 = "Couldn't create guest, most likely because there already exists one"
                                            android.util.Log.e(r0, r5)     // Catch: android.os.RemoteException -> L63
                                        L35:
                                            r5 = r1
                                            goto L41
                                        L37:
                                            int r5 = r5.id     // Catch: android.os.RemoteException -> L63
                                            goto L41
                                        L3a:
                                            r5 = move-exception
                                            java.lang.String r2 = "Couldn't create guest user"
                                            android.util.Log.e(r0, r2, r5)     // Catch: android.os.RemoteException -> L63
                                            goto L35
                                        L41:
                                            if (r5 != r1) goto L5a
                                            java.lang.String r5 = "Could not create new guest, switching back to system user"
                                            android.util.Log.e(r0, r5)     // Catch: android.os.RemoteException -> L63
                                            r5 = 0
                                            r3.switchToUserId(r5)     // Catch: android.os.RemoteException -> L63
                                            android.os.UserManager r3 = r3.mUserManager     // Catch: android.os.RemoteException -> L63
                                            r3.removeUser(r4)     // Catch: android.os.RemoteException -> L63
                                            android.view.IWindowManager r3 = android.view.WindowManagerGlobal.getWindowManagerService()     // Catch: android.os.RemoteException -> L63
                                            r4 = 0
                                            r3.lockNow(r4)     // Catch: android.os.RemoteException -> L63
                                            goto L68
                                        L5a:
                                            r3.switchToUserId(r5)     // Catch: android.os.RemoteException -> L63
                                            android.os.UserManager r3 = r3.mUserManager     // Catch: android.os.RemoteException -> L63
                                            r3.removeUser(r4)     // Catch: android.os.RemoteException -> L63
                                            goto L68
                                        L63:
                                            java.lang.String r3 = "Couldn't remove guest because ActivityManager or WindowManager is dead"
                                            android.util.Log.e(r0, r3)
                                        L68:
                                            return
                                        L69:
                                            com.android.settings.users.UserDetailsSettings r3 = r3.this$0
                                            com.android.settings.users.UserDetailsSettings.m1015$$Nest$mexitGuest(r3)
                                            return
                                        L6f:
                                            com.android.settings.users.UserDetailsSettings r3 = r3.this$0
                                            r3.clearAndExitGuest()
                                            return
                                        L75:
                                            com.android.settings.users.UserDetailsSettings r3 = r3.this$0
                                            r3.clearAndExitGuest()
                                            return
                                        */
                                        throw new UnsupportedOperationException(
                                                "Method not decompiled:"
                                                    + " com.android.settings.users.UserDetailsSettings.AnonymousClass2.onClick(android.content.DialogInterface,"
                                                    + " int):void");
                                    }
                                });
                        builder3.setNeutralButton(android.R.string.cancel, null);
                        AlertDialog create3 = builder3.create();
                        this.mDialog = create3;
                        updateDialogAnchorView$1(create3, this.mGuestResetPreference);
                        Dialog dialog3 = this.mDialog;
                        if (dialog3 != null) {
                            final int i14 = 2;
                            dialog3.setOnShowListener(
                                    new DialogInterface
                                            .OnShowListener() { // from class:
                                                                // com.android.settings.users.UserDetailsSettings$$ExternalSyntheticLambda7
                                        @Override // android.content.DialogInterface.OnShowListener
                                        public final void onShow(DialogInterface dialogInterface) {
                                            int i15 = i14;
                                            Context context3 = activity;
                                            switch (i15) {
                                                case 0:
                                                    int i16 = UserDetailsSettings.$r8$clinit;
                                                    ((AlertDialog) dialogInterface)
                                                            .getButton(-1)
                                                            .setTextColor(
                                                                    context3.getColor(
                                                                            R.color
                                                                                    .sec_biometrics_dialog_remove_btn_color));
                                                    break;
                                                case 1:
                                                    int i17 = UserDetailsSettings.$r8$clinit;
                                                    ((AlertDialog) dialogInterface)
                                                            .getButton(-1)
                                                            .setTextColor(
                                                                    context3.getColor(
                                                                            R.color
                                                                                    .sec_biometrics_dialog_remove_btn_color));
                                                    break;
                                                default:
                                                    int i18 = UserDetailsSettings.$r8$clinit;
                                                    ((AlertDialog) dialogInterface)
                                                            .getButton(-1)
                                                            .setTextColor(
                                                                    context3.getColor(
                                                                            R.color
                                                                                    .sec_biometrics_dialog_remove_btn_color));
                                                    break;
                                            }
                                        }
                                    });
                        }
                        return this.mDialog;
                    case 14:
                        AlertDialog.Builder builder4 = new AlertDialog.Builder(activity);
                        builder4.setTitle(R.string.guest_exit_dialog_title);
                        builder4.setMessage(R.string.guest_exit_dialog_message);
                        final int i15 = 0;
                        builder4.setPositiveButton(
                                R.string.guest_exit_dialog_button,
                                new DialogInterface.OnClickListener(this) { // from class:
                                    // com.android.settings.users.UserDetailsSettings.2
                                    public final /* synthetic */ UserDetailsSettings this$0;

                                    {
                                        this.this$0 = this;
                                    }

                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i16) {
                                        /*
                                            this = this;
                                            int r4 = r2
                                            switch(r4) {
                                                case 0: goto L75;
                                                case 1: goto L6f;
                                                case 2: goto L69;
                                                default: goto L5;
                                            }
                                        L5:
                                            com.android.settings.users.UserDetailsSettings r3 = r3.this$0
                                            com.android.settings.users.UserCapabilities r4 = r3.mUserCaps
                                            boolean r4 = r4.mIsGuest
                                            if (r4 != 0) goto Le
                                            goto L68
                                        Le:
                                            int r4 = android.os.UserHandle.myUserId()
                                            android.os.UserManager r5 = r3.mUserManager
                                            boolean r5 = r5.markGuestForDeletion(r4)
                                            java.lang.String r0 = "UserDetailsSettings"
                                            if (r5 != 0) goto L22
                                            java.lang.String r3 = "Couldn't mark the guest for deletion for user "
                                            androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0.m(r4, r3, r0)
                                            goto L68
                                        L22:
                                            android.content.Context r5 = r3.getPrefContext()     // Catch: android.os.RemoteException -> L63
                                            r1 = -10000(0xffffffffffffd8f0, float:NaN)
                                            android.os.UserManager r2 = r3.mUserManager     // Catch: android.os.UserManager.UserOperationException -> L3a android.os.RemoteException -> L63
                                            android.content.pm.UserInfo r5 = r2.createGuest(r5)     // Catch: android.os.UserManager.UserOperationException -> L3a android.os.RemoteException -> L63
                                            if (r5 != 0) goto L37
                                            java.lang.String r5 = "Couldn't create guest, most likely because there already exists one"
                                            android.util.Log.e(r0, r5)     // Catch: android.os.RemoteException -> L63
                                        L35:
                                            r5 = r1
                                            goto L41
                                        L37:
                                            int r5 = r5.id     // Catch: android.os.RemoteException -> L63
                                            goto L41
                                        L3a:
                                            r5 = move-exception
                                            java.lang.String r2 = "Couldn't create guest user"
                                            android.util.Log.e(r0, r2, r5)     // Catch: android.os.RemoteException -> L63
                                            goto L35
                                        L41:
                                            if (r5 != r1) goto L5a
                                            java.lang.String r5 = "Could not create new guest, switching back to system user"
                                            android.util.Log.e(r0, r5)     // Catch: android.os.RemoteException -> L63
                                            r5 = 0
                                            r3.switchToUserId(r5)     // Catch: android.os.RemoteException -> L63
                                            android.os.UserManager r3 = r3.mUserManager     // Catch: android.os.RemoteException -> L63
                                            r3.removeUser(r4)     // Catch: android.os.RemoteException -> L63
                                            android.view.IWindowManager r3 = android.view.WindowManagerGlobal.getWindowManagerService()     // Catch: android.os.RemoteException -> L63
                                            r4 = 0
                                            r3.lockNow(r4)     // Catch: android.os.RemoteException -> L63
                                            goto L68
                                        L5a:
                                            r3.switchToUserId(r5)     // Catch: android.os.RemoteException -> L63
                                            android.os.UserManager r3 = r3.mUserManager     // Catch: android.os.RemoteException -> L63
                                            r3.removeUser(r4)     // Catch: android.os.RemoteException -> L63
                                            goto L68
                                        L63:
                                            java.lang.String r3 = "Couldn't remove guest because ActivityManager or WindowManager is dead"
                                            android.util.Log.e(r0, r3)
                                        L68:
                                            return
                                        L69:
                                            com.android.settings.users.UserDetailsSettings r3 = r3.this$0
                                            com.android.settings.users.UserDetailsSettings.m1015$$Nest$mexitGuest(r3)
                                            return
                                        L6f:
                                            com.android.settings.users.UserDetailsSettings r3 = r3.this$0
                                            r3.clearAndExitGuest()
                                            return
                                        L75:
                                            com.android.settings.users.UserDetailsSettings r3 = r3.this$0
                                            r3.clearAndExitGuest()
                                            return
                                        */
                                        throw new UnsupportedOperationException(
                                                "Method not decompiled:"
                                                    + " com.android.settings.users.UserDetailsSettings.AnonymousClass2.onClick(android.content.DialogInterface,"
                                                    + " int):void");
                                    }
                                });
                        builder4.setNeutralButton(android.R.string.cancel, null);
                        AlertDialog create4 = builder4.create();
                        this.mDialog = create4;
                        updateDialogAnchorView$1(create4, this.mGuestExitPreference);
                        Dialog dialog4 = this.mDialog;
                        if (dialog4 != null) {
                            final int i16 = 0;
                            dialog4.setOnShowListener(
                                    new DialogInterface
                                            .OnShowListener() { // from class:
                                                                // com.android.settings.users.UserDetailsSettings$$ExternalSyntheticLambda7
                                        @Override // android.content.DialogInterface.OnShowListener
                                        public final void onShow(DialogInterface dialogInterface) {
                                            int i152 = i16;
                                            Context context3 = activity;
                                            switch (i152) {
                                                case 0:
                                                    int i162 = UserDetailsSettings.$r8$clinit;
                                                    ((AlertDialog) dialogInterface)
                                                            .getButton(-1)
                                                            .setTextColor(
                                                                    context3.getColor(
                                                                            R.color
                                                                                    .sec_biometrics_dialog_remove_btn_color));
                                                    break;
                                                case 1:
                                                    int i17 = UserDetailsSettings.$r8$clinit;
                                                    ((AlertDialog) dialogInterface)
                                                            .getButton(-1)
                                                            .setTextColor(
                                                                    context3.getColor(
                                                                            R.color
                                                                                    .sec_biometrics_dialog_remove_btn_color));
                                                    break;
                                                default:
                                                    int i18 = UserDetailsSettings.$r8$clinit;
                                                    ((AlertDialog) dialogInterface)
                                                            .getButton(-1)
                                                            .setTextColor(
                                                                    context3.getColor(
                                                                            R.color
                                                                                    .sec_biometrics_dialog_remove_btn_color));
                                                    break;
                                            }
                                        }
                                    });
                        }
                        return this.mDialog;
                    case 15:
                        AlertDialog.Builder builder5 = new AlertDialog.Builder(activity);
                        builder5.setTitle(R.string.guest_exit_dialog_title_non_ephemeral);
                        builder5.setMessage(R.string.guest_exit_dialog_message_non_ephemeral);
                        final int i17 = 2;
                        builder5.setNegativeButton(
                                R.string.guest_exit_save_data_button,
                                new DialogInterface.OnClickListener(this) { // from class:
                                    // com.android.settings.users.UserDetailsSettings.2
                                    public final /* synthetic */ UserDetailsSettings this$0;

                                    {
                                        this.this$0 = this;
                                    }

                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i18) {
                                        /*
                                            this = this;
                                            int r4 = r2
                                            switch(r4) {
                                                case 0: goto L75;
                                                case 1: goto L6f;
                                                case 2: goto L69;
                                                default: goto L5;
                                            }
                                        L5:
                                            com.android.settings.users.UserDetailsSettings r3 = r3.this$0
                                            com.android.settings.users.UserCapabilities r4 = r3.mUserCaps
                                            boolean r4 = r4.mIsGuest
                                            if (r4 != 0) goto Le
                                            goto L68
                                        Le:
                                            int r4 = android.os.UserHandle.myUserId()
                                            android.os.UserManager r5 = r3.mUserManager
                                            boolean r5 = r5.markGuestForDeletion(r4)
                                            java.lang.String r0 = "UserDetailsSettings"
                                            if (r5 != 0) goto L22
                                            java.lang.String r3 = "Couldn't mark the guest for deletion for user "
                                            androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0.m(r4, r3, r0)
                                            goto L68
                                        L22:
                                            android.content.Context r5 = r3.getPrefContext()     // Catch: android.os.RemoteException -> L63
                                            r1 = -10000(0xffffffffffffd8f0, float:NaN)
                                            android.os.UserManager r2 = r3.mUserManager     // Catch: android.os.UserManager.UserOperationException -> L3a android.os.RemoteException -> L63
                                            android.content.pm.UserInfo r5 = r2.createGuest(r5)     // Catch: android.os.UserManager.UserOperationException -> L3a android.os.RemoteException -> L63
                                            if (r5 != 0) goto L37
                                            java.lang.String r5 = "Couldn't create guest, most likely because there already exists one"
                                            android.util.Log.e(r0, r5)     // Catch: android.os.RemoteException -> L63
                                        L35:
                                            r5 = r1
                                            goto L41
                                        L37:
                                            int r5 = r5.id     // Catch: android.os.RemoteException -> L63
                                            goto L41
                                        L3a:
                                            r5 = move-exception
                                            java.lang.String r2 = "Couldn't create guest user"
                                            android.util.Log.e(r0, r2, r5)     // Catch: android.os.RemoteException -> L63
                                            goto L35
                                        L41:
                                            if (r5 != r1) goto L5a
                                            java.lang.String r5 = "Could not create new guest, switching back to system user"
                                            android.util.Log.e(r0, r5)     // Catch: android.os.RemoteException -> L63
                                            r5 = 0
                                            r3.switchToUserId(r5)     // Catch: android.os.RemoteException -> L63
                                            android.os.UserManager r3 = r3.mUserManager     // Catch: android.os.RemoteException -> L63
                                            r3.removeUser(r4)     // Catch: android.os.RemoteException -> L63
                                            android.view.IWindowManager r3 = android.view.WindowManagerGlobal.getWindowManagerService()     // Catch: android.os.RemoteException -> L63
                                            r4 = 0
                                            r3.lockNow(r4)     // Catch: android.os.RemoteException -> L63
                                            goto L68
                                        L5a:
                                            r3.switchToUserId(r5)     // Catch: android.os.RemoteException -> L63
                                            android.os.UserManager r3 = r3.mUserManager     // Catch: android.os.RemoteException -> L63
                                            r3.removeUser(r4)     // Catch: android.os.RemoteException -> L63
                                            goto L68
                                        L63:
                                            java.lang.String r3 = "Couldn't remove guest because ActivityManager or WindowManager is dead"
                                            android.util.Log.e(r0, r3)
                                        L68:
                                            return
                                        L69:
                                            com.android.settings.users.UserDetailsSettings r3 = r3.this$0
                                            com.android.settings.users.UserDetailsSettings.m1015$$Nest$mexitGuest(r3)
                                            return
                                        L6f:
                                            com.android.settings.users.UserDetailsSettings r3 = r3.this$0
                                            r3.clearAndExitGuest()
                                            return
                                        L75:
                                            com.android.settings.users.UserDetailsSettings r3 = r3.this$0
                                            r3.clearAndExitGuest()
                                            return
                                        */
                                        throw new UnsupportedOperationException(
                                                "Method not decompiled:"
                                                    + " com.android.settings.users.UserDetailsSettings.AnonymousClass2.onClick(android.content.DialogInterface,"
                                                    + " int):void");
                                    }
                                });
                        final int i18 = 1;
                        builder5.setPositiveButton(
                                R.string.guest_exit_clear_data_button,
                                new DialogInterface.OnClickListener(this) { // from class:
                                    // com.android.settings.users.UserDetailsSettings.2
                                    public final /* synthetic */ UserDetailsSettings this$0;

                                    {
                                        this.this$0 = this;
                                    }

                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i19) {
                                        /*
                                            this = this;
                                            int r4 = r2
                                            switch(r4) {
                                                case 0: goto L75;
                                                case 1: goto L6f;
                                                case 2: goto L69;
                                                default: goto L5;
                                            }
                                        L5:
                                            com.android.settings.users.UserDetailsSettings r3 = r3.this$0
                                            com.android.settings.users.UserCapabilities r4 = r3.mUserCaps
                                            boolean r4 = r4.mIsGuest
                                            if (r4 != 0) goto Le
                                            goto L68
                                        Le:
                                            int r4 = android.os.UserHandle.myUserId()
                                            android.os.UserManager r5 = r3.mUserManager
                                            boolean r5 = r5.markGuestForDeletion(r4)
                                            java.lang.String r0 = "UserDetailsSettings"
                                            if (r5 != 0) goto L22
                                            java.lang.String r3 = "Couldn't mark the guest for deletion for user "
                                            androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0.m(r4, r3, r0)
                                            goto L68
                                        L22:
                                            android.content.Context r5 = r3.getPrefContext()     // Catch: android.os.RemoteException -> L63
                                            r1 = -10000(0xffffffffffffd8f0, float:NaN)
                                            android.os.UserManager r2 = r3.mUserManager     // Catch: android.os.UserManager.UserOperationException -> L3a android.os.RemoteException -> L63
                                            android.content.pm.UserInfo r5 = r2.createGuest(r5)     // Catch: android.os.UserManager.UserOperationException -> L3a android.os.RemoteException -> L63
                                            if (r5 != 0) goto L37
                                            java.lang.String r5 = "Couldn't create guest, most likely because there already exists one"
                                            android.util.Log.e(r0, r5)     // Catch: android.os.RemoteException -> L63
                                        L35:
                                            r5 = r1
                                            goto L41
                                        L37:
                                            int r5 = r5.id     // Catch: android.os.RemoteException -> L63
                                            goto L41
                                        L3a:
                                            r5 = move-exception
                                            java.lang.String r2 = "Couldn't create guest user"
                                            android.util.Log.e(r0, r2, r5)     // Catch: android.os.RemoteException -> L63
                                            goto L35
                                        L41:
                                            if (r5 != r1) goto L5a
                                            java.lang.String r5 = "Could not create new guest, switching back to system user"
                                            android.util.Log.e(r0, r5)     // Catch: android.os.RemoteException -> L63
                                            r5 = 0
                                            r3.switchToUserId(r5)     // Catch: android.os.RemoteException -> L63
                                            android.os.UserManager r3 = r3.mUserManager     // Catch: android.os.RemoteException -> L63
                                            r3.removeUser(r4)     // Catch: android.os.RemoteException -> L63
                                            android.view.IWindowManager r3 = android.view.WindowManagerGlobal.getWindowManagerService()     // Catch: android.os.RemoteException -> L63
                                            r4 = 0
                                            r3.lockNow(r4)     // Catch: android.os.RemoteException -> L63
                                            goto L68
                                        L5a:
                                            r3.switchToUserId(r5)     // Catch: android.os.RemoteException -> L63
                                            android.os.UserManager r3 = r3.mUserManager     // Catch: android.os.RemoteException -> L63
                                            r3.removeUser(r4)     // Catch: android.os.RemoteException -> L63
                                            goto L68
                                        L63:
                                            java.lang.String r3 = "Couldn't remove guest because ActivityManager or WindowManager is dead"
                                            android.util.Log.e(r0, r3)
                                        L68:
                                            return
                                        L69:
                                            com.android.settings.users.UserDetailsSettings r3 = r3.this$0
                                            com.android.settings.users.UserDetailsSettings.m1015$$Nest$mexitGuest(r3)
                                            return
                                        L6f:
                                            com.android.settings.users.UserDetailsSettings r3 = r3.this$0
                                            r3.clearAndExitGuest()
                                            return
                                        L75:
                                            com.android.settings.users.UserDetailsSettings r3 = r3.this$0
                                            r3.clearAndExitGuest()
                                            return
                                        */
                                        throw new UnsupportedOperationException(
                                                "Method not decompiled:"
                                                    + " com.android.settings.users.UserDetailsSettings.AnonymousClass2.onClick(android.content.DialogInterface,"
                                                    + " int):void");
                                    }
                                });
                        builder5.setNeutralButton(android.R.string.cancel, null);
                        AlertDialog create5 = builder5.create();
                        this.mDialog = create5;
                        updateDialogAnchorView$1(create5, this.mGuestExitPreference);
                        Dialog dialog5 = this.mDialog;
                        if (dialog5 != null) {
                            final int i19 = 1;
                            dialog5.setOnShowListener(
                                    new DialogInterface
                                            .OnShowListener() { // from class:
                                                                // com.android.settings.users.UserDetailsSettings$$ExternalSyntheticLambda7
                                        @Override // android.content.DialogInterface.OnShowListener
                                        public final void onShow(DialogInterface dialogInterface) {
                                            int i152 = i19;
                                            Context context3 = activity;
                                            switch (i152) {
                                                case 0:
                                                    int i162 = UserDetailsSettings.$r8$clinit;
                                                    ((AlertDialog) dialogInterface)
                                                            .getButton(-1)
                                                            .setTextColor(
                                                                    context3.getColor(
                                                                            R.color
                                                                                    .sec_biometrics_dialog_remove_btn_color));
                                                    break;
                                                case 1:
                                                    int i172 = UserDetailsSettings.$r8$clinit;
                                                    ((AlertDialog) dialogInterface)
                                                            .getButton(-1)
                                                            .setTextColor(
                                                                    context3.getColor(
                                                                            R.color
                                                                                    .sec_biometrics_dialog_remove_btn_color));
                                                    break;
                                                default:
                                                    int i182 = UserDetailsSettings.$r8$clinit;
                                                    ((AlertDialog) dialogInterface)
                                                            .getButton(-1)
                                                            .setTextColor(
                                                                    context3.getColor(
                                                                            R.color
                                                                                    .sec_biometrics_dialog_remove_btn_color));
                                                    break;
                                            }
                                        }
                                    });
                        }
                        return this.mDialog;
                    default:
                        throw new IllegalArgumentException(
                                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                        i, "Unsupported dialogId "));
                }
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (preference == this.mPhonePref) {
            if (Boolean.TRUE.equals(obj)) {
                this.mMetricsFeatureProvider.action(getActivity(), 1814, new Pair[0]);
                showDialog(2);
                return false;
            }
            this.mMetricsFeatureProvider.action(getActivity(), 1815, new Pair[0]);
            enableCallsAndSms(false);
            return true;
        }
        if (preference != this.mGrantAdminPref) {
            return true;
        }
        if (Boolean.FALSE.equals(obj)) {
            this.mMetricsFeatureProvider.action(getActivity(), 1821, new Pair[0]);
            showDialog(6);
        } else {
            this.mMetricsFeatureProvider.action(getActivity(), 1820, new Pair[0]);
            showDialog(7);
        }
        return false;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        if (preference != null && preference.getKey() != null) {
            this.mMetricsFeatureProvider.logSettingsTileClick(98, preference.getKey());
        }
        if (preference == this.mRemoveUserPref) {
            MetricsFeatureProvider metricsFeatureProvider = this.mMetricsFeatureProvider;
            FragmentActivity activity = getActivity();
            UserInfo userInfo = this.mUserInfo;
            metricsFeatureProvider.action(
                    activity,
                    userInfo.isGuest() ? 1810 : userInfo.isRestricted() ? 1811 : 1809,
                    new Pair[0]);
            if (canDeleteUser()) {
                if (Utils.isOnCall(getActivity())) {
                    Toast.makeText(
                                    getActivity(),
                                    R.string.sec_user_mode_delete_toast_on_during_call,
                                    1)
                            .show();
                    return true;
                }
                SemUserInfo semGetSemUserInfo =
                        this.mUserManager.semGetSemUserInfo(this.mUserInfo.id);
                if (semGetSemUserInfo != null && semGetSemUserInfo.isSecondNumberMode()) {
                    Intent intent =
                            new Intent("com.samsung.android.settings.action.REMOVE_TWO_PHONE_USER");
                    if (Utils.isIntentAvailable(getActivity(), intent)) {
                        startActivity(intent);
                        return true;
                    }
                }
                if (this.mUserInfo.isGuest()) {
                    showDialog(4);
                } else {
                    showDialog(1);
                }
                return true;
            }
        } else if (preference == this.mSwitchUserPref) {
            MetricsFeatureProvider metricsFeatureProvider2 = this.mMetricsFeatureProvider;
            FragmentActivity activity2 = getActivity();
            UserInfo userInfo2 = this.mUserInfo;
            metricsFeatureProvider2.action(
                    activity2,
                    userInfo2.isGuest() ? 1765 : userInfo2.isRestricted() ? 1812 : 1813,
                    new Pair[0]);
            if (canSwitchUserNow()) {
                if (!"android.os.usertype.full.SECONDARY".equals(this.mUserInfo.userType)
                        || this.mUserInfo.isInitialized()) {
                    UserCapabilities userCapabilities = this.mUserCaps;
                    if (userCapabilities.mIsGuest && userCapabilities.mIsEphemeral) {
                        showDialog(5);
                    } else {
                        switchUser();
                    }
                } else {
                    showDialog(3);
                }
                return true;
            }
        } else {
            if (preference == this.mAppAndContentAccessPref) {
                openAppAndContentAccessScreen(false);
                return true;
            }
            if (preference == this.mAppCopyingPref) {
                return true;
            }
        }
        UserCapabilities userCapabilities2 = this.mUserCaps;
        if (userCapabilities2.mIsGuest) {
            Preference preference2 = this.mGuestResetPreference;
            if (preference2 != null && preference == preference2) {
                showDialog(13);
                return true;
            }
            Preference preference3 = this.mGuestExitPreference;
            if (preference3 != null && preference == preference3) {
                if (userCapabilities2.mIsEphemeral) {
                    showDialog(14);
                } else {
                    showDialog(15);
                }
                return true;
            }
        }
        return false;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mSwitchUserPref.setEnabled(canSwitchUserNow());
        if (this.mUserInfo.isGuest() && this.mGuestUserAutoCreated) {
            this.mRemoveUserPref.setEnabled((this.mUserInfo.flags & 16) != 0);
        }
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen.findPreference(
                        this.mGuestTelephonyPreferenceController.getPreferenceKey())
                != null) {
            GuestTelephonyPreferenceController guestTelephonyPreferenceController =
                    this.mGuestTelephonyPreferenceController;
            guestTelephonyPreferenceController.updateState(
                    preferenceScreen.findPreference(
                            guestTelephonyPreferenceController.getPreferenceKey()));
        }
        if (preferenceScreen.findPreference(
                        this.mRemoveGuestOnExitPreferenceController.getPreferenceKey())
                != null) {
            RemoveGuestOnExitPreferenceController removeGuestOnExitPreferenceController =
                    this.mRemoveGuestOnExitPreferenceController;
            removeGuestOnExitPreferenceController.updateState(
                    preferenceScreen.findPreference(
                            removeGuestOnExitPreferenceController.getPreferenceKey()));
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setDivider(null);
        getListView()
                .addItemDecoration(
                        new SecDividerItemDecorator(
                                (int)
                                        (getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_app_list_item_icon_min_width)
                                                + getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_widget_list_item_padding_start)),
                                getContext(),
                                getResources().getDrawable(R.drawable.sec_top_level_list_divider)));
    }

    public final void openAppAndContentAccessScreen(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, this.mUserInfo.id);
        bundle.putBoolean("new_user", z);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getContext());
        String name = AppRestrictionsFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = bundle;
        subSettingLauncher.setTitleRes(R.string.user_restrictions_title, null);
        launchRequest.mSourceMetricsCategory = 98;
        subSettingLauncher.launch();
    }

    public final void resetGuest() {
        if (this.mUserInfo.isGuest()) {
            this.mMetricsFeatureProvider.action(getActivity(), 1763, new Pair[0]);
            this.mUserManager.removeUser(this.mUserInfo.id);
            setResult(100);
            finishFragment();
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public void showDialog(int i) {
        super.showDialog(i);
    }

    public final void switchToUserId(int i) {
        if (!canSwitchUserNow()) {
            Log.w("UserDetailsSettings", "Cannot switch current user when switching is disabled");
            return;
        }
        try {
            ActivityManager.getService().switchUser(i);
        } catch (RemoteException unused) {
            Log.e("UserDetailsSettings", "Unable to switch user");
        }
    }

    public void switchUser() {
        Trace.beginSection("UserDetailSettings.switchUser");
        try {
            try {
                UserCapabilities userCapabilities = this.mUserCaps;
                if (userCapabilities.mIsGuest && userCapabilities.mIsEphemeral) {
                    int myUserId = UserHandle.myUserId();
                    if (!this.mUserManager.markGuestForDeletion(myUserId)) {
                        Log.w(
                                "UserDetailsSettings",
                                "Couldn't mark the guest for deletion for user " + myUserId);
                        return;
                    }
                }
                ActivityManager.getService().switchUser(this.mUserInfo.id);
            } catch (RemoteException unused) {
                Log.e("UserDetailsSettings", "Error while switching to other user.");
            }
        } finally {
            Trace.endSection();
            finishFragment();
        }
    }

    public final void updateUserAdminStatus(boolean z) {
        this.mGrantAdminPref.setChecked(z);
        if (!z) {
            this.mUserManager.revokeUserAdmin(this.mUserInfo.id);
            return;
        }
        UserInfo userInfo = this.mUserInfo;
        if ((userInfo.flags & 2) == 0) {
            this.mUserManager.setUserAdmin(userInfo.id);
        }
    }
}

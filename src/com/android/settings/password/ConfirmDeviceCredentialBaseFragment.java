package com.android.settings.password;

import android.app.Dialog;
import android.app.RemoteLockscreenValidationResult;
import android.app.RemoteLockscreenValidationSession;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback;
import android.service.remotelockscreenvalidation.RemoteLockscreenValidationClient;
import android.telecom.TelecomManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.security.SecureBox;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.InstrumentedFragment;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.knox.KnoxConfirmDeviceCredentialBaseFragmentHelper;
import com.samsung.android.settings.knox.KnoxConfirmLockHelper;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.settings.password.LockTypePolicy;
import com.samsung.android.settings.password.SecGlifLayout;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ConfirmDeviceCredentialBaseFragment extends InstrumentedFragment
        implements KnoxConfirmDeviceCredentialBaseFragmentHelper.Callback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public CharSequence mAlternateButtonText;
    public Button mCancelButton;
    public CheckBox mCheckBox;
    public DevicePolicyManager mDevicePolicyManager;
    public int mEffectiveUserId;
    public TextView mErrorTextView;
    public Button mForgotButton;
    public boolean mFrp;
    public SecGlifLayout mGlifLayout;
    public KnoxConfirmDeviceCredentialBaseFragmentHelper
            mKnoxConfirmDeviceCredentialBaseFragmentHelper;
    public LockPatternUtils mLockPatternUtils;
    public LockTypePolicy mLockTypePolicy;
    public RemoteLockscreenValidationClient mRemoteLockscreenValidationClient;
    public RemoteLockscreenValidationFragment mRemoteLockscreenValidationFragment;
    public RemoteLockscreenValidationSession mRemoteLockscreenValidationSession;
    public boolean mRemoteValidation;
    public boolean mRepairMode;
    public boolean mRequestWriteRepairModePassword;
    public int mUserId;
    public UserManager mUserManager;
    public boolean mReturnCredentials = false;
    public boolean mReturnGatekeeperPassword = false;
    public boolean mForceVerifyPath = false;
    public final Handler mHandler = new Handler();
    public boolean mForFingerprint = false;
    public boolean mUnlockRecovery = false;
    public final AnonymousClass1 mScreenOffReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.settings.password.ConfirmDeviceCredentialBaseFragment.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    ConfirmDeviceCredentialBaseFragment confirmDeviceCredentialBaseFragment =
                            ConfirmDeviceCredentialBaseFragment.this;
                    if (confirmDeviceCredentialBaseFragment.mReturnGatekeeperPassword) {
                        confirmDeviceCredentialBaseFragment.getActivity().finish();
                    }
                }
            };
    public final AnonymousClass3 mResetErrorRunnable =
            new Runnable() { // from class:
                             // com.android.settings.password.ConfirmDeviceCredentialBaseFragment.3
                @Override // java.lang.Runnable
                public final void run() {
                    ConfirmDeviceCredentialBaseFragment.this.mErrorTextView.setText(
                            ApnSettings.MVNO_NONE);
                    if (ConfirmDeviceCredentialBaseFragment.this.getActivity() != null) {
                        ConfirmDeviceCredentialBaseFragment.this.onShowDefault();
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.password.ConfirmDeviceCredentialBaseFragment$2, reason: invalid class name */
    public final class AnonymousClass2 extends OnBackPressedCallback {
        @Override // androidx.activity.OnBackPressedCallback
        public final void handleOnBackPressed() {
            int i = ConfirmDeviceCredentialBaseFragment.$r8$clinit;
            Log.d(
                    "ConfirmDeviceCredentialBaseFragment",
                    "handleOnBackPressed: Back key is pressed");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class LastTryDialog extends DialogFragment {
        public static final String TAG = LastTryDialog.class.getSimpleName();

        public static void show(
                FragmentManager fragmentManager,
                String str,
                String str2,
                int i,
                boolean z,
                int i2) {
            String str3 = TAG;
            LastTryDialog lastTryDialog = (LastTryDialog) fragmentManager.findFragmentByTag(str3);
            if (lastTryDialog == null || lastTryDialog.isRemoving()) {
                Bundle bundle = new Bundle();
                bundle.putString(UniversalCredentialUtil.AGENT_TITLE, str);
                bundle.putString("message", str2);
                bundle.putInt("button", i);
                bundle.putBoolean("dismiss", z);
                bundle.putInt("numAttempts", i2);
                LastTryDialog lastTryDialog2 = new LastTryDialog();
                lastTryDialog2.setArguments(bundle);
                lastTryDialog2.show(fragmentManager, str3);
                fragmentManager.executePendingTransactions();
            }
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            String format =
                    String.format(
                            getArguments().getString("message"),
                            Integer.valueOf(getArguments().getInt("numAttempts")));
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            String string = getArguments().getString(UniversalCredentialUtil.AGENT_TITLE);
            AlertController.AlertParams alertParams = builder.P;
            alertParams.mTitle = string;
            alertParams.mMessage = format;
            builder.setPositiveButton(
                    getArguments().getInt("button"), (DialogInterface.OnClickListener) null);
            AlertDialog create = builder.create();
            create.setCanceledOnTouchOutside(false);
            return create;
        }

        @Override // androidx.fragment.app.DialogFragment,
                  // android.content.DialogInterface.OnDismissListener
        public final void onDismiss(DialogInterface dialogInterface) {
            super.onDismiss(dialogInterface);
            if (getActivity() == null || !getArguments().getBoolean("dismiss")) {
                return;
            }
            getActivity().finish();
        }
    }

    public abstract void authenticationSucceeded();

    public abstract int getLastTryDefaultErrorMessage(int i);

    public abstract String getLastTryOverrideErrorMessageId(int i);

    public final boolean isStrongAuthRequired$1() {
        return this.mFrp
                || this.mRepairMode
                || !this.mLockPatternUtils.isBiometricAllowedForUser(this.mEffectiveUserId)
                || !this.mUserManager.isUserUnlocked(this.mUserId);
    }

    /* JADX WARN: Code restructure failed: missing block: B:57:0x01e3, code lost:

       if (com.samsung.android.settings.knox.KnoxUtils.isChangeRequestedForInner(getContext()) > 0) goto L59;
    */
    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onCreate(android.os.Bundle r10) {
        /*
            Method dump skipped, instructions count: 496
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.password.ConfirmDeviceCredentialBaseFragment.onCreate(android.os.Bundle):void");
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public void onDestroy() {
        KnoxConfirmLockHelper knoxConfirmLockHelper;
        RemoteLockscreenValidationClient remoteLockscreenValidationClient =
                this.mRemoteLockscreenValidationClient;
        if (remoteLockscreenValidationClient != null) {
            remoteLockscreenValidationClient.disconnect();
        }
        super.onDestroy();
        getActivity().unregisterReceiver(this.mScreenOffReceiver);
        KnoxConfirmLockHelper knoxConfirmLockHelper2 =
                this.mKnoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
        if (knoxConfirmLockHelper2 != null) {
            knoxConfirmLockHelper2.onDestroy();
        }
        if (getActivity().isChangingConfigurations()
                || (knoxConfirmLockHelper =
                                this.mKnoxConfirmDeviceCredentialBaseFragmentHelper
                                        .mKnoxConfirmLockHelper)
                        == null) {
            return;
        }
        knoxConfirmLockHelper.cancelBiometricIfNeeded();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        KnoxConfirmLockHelper knoxConfirmLockHelper =
                this.mKnoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
    }

    public final void onRemoteLockscreenValidationFailure(String str) {
        if (!TextUtils.isEmpty(str)) {
            Log.w("ConfirmDeviceCredentialBaseFragment", str);
        }
        getActivity().setResult(1);
        getActivity().finish();
    }

    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (!Rune.isSamsungDexMode(getActivity()) && LockUtils.isInMultiWindow(getActivity())) {
            if (this.mLockTypePolicy.mIsKnoxId && !this.mReturnCredentials) {
                return;
            }
            Toast.makeText(
                            getActivity(),
                            getString(R.string.lock_screen_doesnt_support_multi_window_text),
                            0)
                    .show();
            getActivity().finish();
        }
        KnoxConfirmDeviceCredentialBaseFragmentHelper
                knoxConfirmDeviceCredentialBaseFragmentHelper =
                        this.mKnoxConfirmDeviceCredentialBaseFragmentHelper;
        FragmentActivity activity = getActivity();
        KnoxConfirmLockHelper knoxConfirmLockHelper =
                knoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
        if (knoxConfirmLockHelper != null
                ? knoxConfirmLockHelper.interceptOnResumeIfNeeded(activity)
                : false) {
            return;
        }
        refreshLockScreen();
    }

    public abstract void onShowDefault();

    public abstract void onShowError();

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        if (LockUtils.isApplyingTabletGUI(getActivity())
                && ((getActivity() instanceof ConfirmLockPattern.InternalActivity)
                        || (getActivity() instanceof ConfirmLockPattern))) {
            getActivity().getWindow().setGravity(17);
        }
        if (!this.mRemoteValidation && !this.mFrp) {
            getActivity()
                    .getWindow()
                    .setStatusBarColor(
                            getResources().getColor(R.color.sec_widget_round_and_bgcolor, null));
            getActivity()
                    .getWindow()
                    .setNavigationBarColor(
                            getResources().getColor(R.color.sec_widget_round_and_bgcolor, null));
        }
        this.mCancelButton = (Button) view.findViewById(R.id.cancelButton);
        boolean z =
                this.mRemoteValidation
                        || getActivity()
                                .getIntent()
                                .getBooleanExtra(
                                        "com.android.settings.ConfirmCredentials.showCancelButton",
                                        false);
        final boolean z2 =
                (this.mFrp || this.mRemoteValidation)
                        && !TextUtils.isEmpty(this.mAlternateButtonText);
        this.mCancelButton.setVisibility((z || z2) ? 0 : 8);
        if (z2) {
            Button button = this.mCancelButton;
            button.setPaintFlags(button.getPaintFlags() | 8);
            this.mCancelButton.setText(this.mAlternateButtonText);
        }
        this.mCancelButton.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.password.ConfirmDeviceCredentialBaseFragment$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        ConfirmDeviceCredentialBaseFragment confirmDeviceCredentialBaseFragment =
                                ConfirmDeviceCredentialBaseFragment.this;
                        if (z2) {
                            int i = ConfirmDeviceCredentialBaseFragment.$r8$clinit;
                            confirmDeviceCredentialBaseFragment.getActivity().setResult(1);
                            confirmDeviceCredentialBaseFragment.getActivity().finish();
                        } else if (confirmDeviceCredentialBaseFragment.mRemoteValidation) {
                            confirmDeviceCredentialBaseFragment.onRemoteLockscreenValidationFailure(
                                    "Forgot lockscreen credential button pressed.");
                        }
                    }
                });
        if (this.mUserManager.isManagedProfile(this.mUserId)
                && this.mUserManager.isQuietModeEnabled(UserHandle.of(this.mUserId))
                && this.mDevicePolicyManager.canProfileOwnerResetPasswordWhenLocked(this.mUserId)) {
            Button button2 = (Button) view.findViewById(R.id.forgotButton);
            this.mForgotButton = button2;
            if (button2 == null) {
                Log.wtf(
                        "ConfirmDeviceCredentialBaseFragment",
                        "Forgot button not found in managed profile credential dialog");
            } else {
                button2.setVisibility(0);
                final int i = 0;
                this.mForgotButton.setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.password.ConfirmDeviceCredentialBaseFragment$$ExternalSyntheticLambda2
                            public final /* synthetic */ ConfirmDeviceCredentialBaseFragment f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view2) {
                                int i2 = i;
                                ConfirmDeviceCredentialBaseFragment
                                        confirmDeviceCredentialBaseFragment = this.f$0;
                                switch (i2) {
                                    case 0:
                                        int i3 = ConfirmDeviceCredentialBaseFragment.$r8$clinit;
                                        confirmDeviceCredentialBaseFragment.getClass();
                                        Intent intent = new Intent();
                                        intent.setClassName(
                                                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                                                ForgotPasswordActivity.class.getName());
                                        intent.setFlags(268435456);
                                        intent.putExtra(
                                                "android.intent.extra.USER_ID",
                                                confirmDeviceCredentialBaseFragment.mUserId);
                                        confirmDeviceCredentialBaseFragment
                                                .getActivity()
                                                .startActivity(intent);
                                        confirmDeviceCredentialBaseFragment.getActivity().finish();
                                        break;
                                    default:
                                        int i4 = ConfirmDeviceCredentialBaseFragment.$r8$clinit;
                                        confirmDeviceCredentialBaseFragment
                                                .getActivity()
                                                .startActivity(
                                                        ((TelecomManager)
                                                                        confirmDeviceCredentialBaseFragment
                                                                                .getActivity()
                                                                                .getSystemService(
                                                                                        TelecomManager
                                                                                                .class))
                                                                .createLaunchEmergencyDialerIntent(
                                                                        null)
                                                                .setFlags(335544320));
                                        confirmDeviceCredentialBaseFragment.getActivity().finish();
                                        break;
                                }
                            }
                        });
                KnoxConfirmDeviceCredentialBaseFragmentHelper
                        knoxConfirmDeviceCredentialBaseFragmentHelper =
                                this.mKnoxConfirmDeviceCredentialBaseFragmentHelper;
                Button button3 = this.mForgotButton;
                KnoxConfirmLockHelper knoxConfirmLockHelper =
                        knoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
                if (knoxConfirmLockHelper != null) {
                    knoxConfirmLockHelper.setupForgotButtonIfManagedProfile(button3);
                }
            }
        }
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        this.mCheckBox = checkBox;
        if (checkBox != null && this.mRemoteValidation) {
            checkBox.setVisibility(0);
        }
        if (((DevicePolicyManager) getContext().getSystemService(DevicePolicyManager.class))
                        .getManagedSubscriptionsPolicy()
                        .getPolicyType()
                == 1) {
            Button button4 = (Button) view.findViewById(R.id.emergencyCallButton);
            if (button4 == null) {
                Log.wtf(
                        "ConfirmDeviceCredentialBaseFragment",
                        "Emergency call button not found in managed profile credential dialog");
            } else {
                button4.setVisibility(0);
                final int i2 = 1;
                button4.setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.password.ConfirmDeviceCredentialBaseFragment$$ExternalSyntheticLambda2
                            public final /* synthetic */ ConfirmDeviceCredentialBaseFragment f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view2) {
                                int i22 = i2;
                                ConfirmDeviceCredentialBaseFragment
                                        confirmDeviceCredentialBaseFragment = this.f$0;
                                switch (i22) {
                                    case 0:
                                        int i3 = ConfirmDeviceCredentialBaseFragment.$r8$clinit;
                                        confirmDeviceCredentialBaseFragment.getClass();
                                        Intent intent = new Intent();
                                        intent.setClassName(
                                                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                                                ForgotPasswordActivity.class.getName());
                                        intent.setFlags(268435456);
                                        intent.putExtra(
                                                "android.intent.extra.USER_ID",
                                                confirmDeviceCredentialBaseFragment.mUserId);
                                        confirmDeviceCredentialBaseFragment
                                                .getActivity()
                                                .startActivity(intent);
                                        confirmDeviceCredentialBaseFragment.getActivity().finish();
                                        break;
                                    default:
                                        int i4 = ConfirmDeviceCredentialBaseFragment.$r8$clinit;
                                        confirmDeviceCredentialBaseFragment
                                                .getActivity()
                                                .startActivity(
                                                        ((TelecomManager)
                                                                        confirmDeviceCredentialBaseFragment
                                                                                .getActivity()
                                                                                .getSystemService(
                                                                                        TelecomManager
                                                                                                .class))
                                                                .createLaunchEmergencyDialerIntent(
                                                                        null)
                                                                .setFlags(335544320));
                                        confirmDeviceCredentialBaseFragment.getActivity().finish();
                                        break;
                                }
                            }
                        });
            }
        }
        if (this.mUserManager.isManagedProfile(this.mUserId)) {
            KnoxConfirmDeviceCredentialBaseFragmentHelper
                    knoxConfirmDeviceCredentialBaseFragmentHelper2 =
                            this.mKnoxConfirmDeviceCredentialBaseFragmentHelper;
            FragmentActivity activity = getActivity();
            KnoxConfirmLockHelper knoxConfirmLockHelper2 =
                    knoxConfirmDeviceCredentialBaseFragmentHelper2.mKnoxConfirmLockHelper;
            if (knoxConfirmLockHelper2 != null) {
                knoxConfirmLockHelper2.setWorkChallengeBackgroundIfNeeded(view, activity);
            }
        }
    }

    public final void refreshLockScreen() {
        updateErrorMessage(
                this.mLockPatternUtils.getCurrentFailedPasswordAttempts(this.mEffectiveUserId));
    }

    public final void reportFailedAttempt() {
        updateErrorMessage(
                this.mLockPatternUtils.getCurrentFailedPasswordAttempts(this.mEffectiveUserId) + 1);
        this.mLockPatternUtils.reportFailedPasswordAttempt(this.mEffectiveUserId);
        KnoxConfirmDeviceCredentialBaseFragmentHelper
                knoxConfirmDeviceCredentialBaseFragmentHelper =
                        this.mKnoxConfirmDeviceCredentialBaseFragmentHelper;
        int currentFailedPasswordAttempts =
                this.mLockPatternUtils.getCurrentFailedPasswordAttempts(this.mEffectiveUserId);
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentActivity activity = getActivity();
        KnoxConfirmLockHelper knoxConfirmLockHelper =
                knoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
        if (knoxConfirmLockHelper != null) {
            knoxConfirmLockHelper.reportFailedAttempt(
                    currentFailedPasswordAttempts, childFragmentManager, activity);
        }
    }

    public final void setAccessibilityTitle(CharSequence charSequence) {
        AppCompatTextView appCompatTextView;
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            CharSequence charSequenceExtra =
                    intent.getCharSequenceExtra("com.android.settings.ConfirmCredentials.title");
            if (charSequence == null) {
                return;
            }
            if (charSequenceExtra == null) {
                getActivity().setTitle(charSequence);
            } else {
                getActivity()
                        .setTitle(
                                Utils.createAccessibleSequence(
                                        charSequenceExtra + "," + charSequence, charSequence));
            }
        }
        if (!LockUtils.isApplyingTabletGUI(getContext())
                || (appCompatTextView =
                                (AppCompatTextView)
                                        getActivity()
                                                .getWindow()
                                                .getDecorView()
                                                .findViewById(R.id.title))
                        == null) {
            return;
        }
        appCompatTextView.setText(getActivity().getTitle());
    }

    public final void showError(CharSequence charSequence, long j) {
        this.mErrorTextView.setText(charSequence);
        onShowError();
        this.mHandler.removeCallbacks(this.mResetErrorRunnable);
        if (j != 0) {
            this.mHandler.postDelayed(this.mResetErrorRunnable, j);
        }
    }

    public final void updateErrorMessage(int i) {
        String string;
        KnoxConfirmLockHelper knoxConfirmLockHelper =
                this.mKnoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
        if (knoxConfirmLockHelper != null) {
            knoxConfirmLockHelper.interceptUpdateErrorMessageIfNeeded(i);
            return;
        }
        int maximumFailedPasswordsForWipe =
                this.mLockPatternUtils.getMaximumFailedPasswordsForWipe(this.mEffectiveUserId);
        if (maximumFailedPasswordsForWipe <= 0 || i <= 0) {
            return;
        }
        if (this.mErrorTextView != null) {
            showError(
                    getActivity()
                            .getString(
                                    R.string.lock_failed_attempts_before_wipe,
                                    new Object[] {
                                        Integer.valueOf(i),
                                        Integer.valueOf(maximumFailedPasswordsForWipe)
                                    }),
                    0L);
        }
        int i2 = maximumFailedPasswordsForWipe - i;
        if (i2 > 1) {
            return;
        }
        FragmentManager childFragmentManager = getChildFragmentManager();
        UserInfo userInfo =
                this.mUserManager.getUserInfo(
                        this.mDevicePolicyManager.getProfileWithMinimumFailedPasswordsForWipe(
                                this.mEffectiveUserId));
        UserHandle userHandle = UserHandle.SYSTEM;
        UserHandle mainUser = this.mUserManager.getMainUser();
        if (mainUser != null) {
            userHandle = mainUser;
        }
        int i3 =
                (userInfo == null || userInfo.getUserHandle().equals(userHandle))
                        ? 1
                        : userInfo.isManagedProfile() ? 2 : 3;
        if (i2 == 1) {
            String string2 =
                    getActivity().getString(R.string.lock_last_attempt_before_wipe_warning_title);
            String lastTryOverrideErrorMessageId = getLastTryOverrideErrorMessageId(i3);
            final int lastTryDefaultErrorMessage = getLastTryDefaultErrorMessage(i3);
            LastTryDialog.show(
                    childFragmentManager,
                    string2,
                    this.mDevicePolicyManager
                            .getResources()
                            .getString(
                                    lastTryOverrideErrorMessageId,
                                    new Supplier() { // from class:
                                                     // com.android.settings.password.ConfirmDeviceCredentialBaseFragment$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            ConfirmDeviceCredentialBaseFragment
                                                    confirmDeviceCredentialBaseFragment =
                                                            ConfirmDeviceCredentialBaseFragment
                                                                    .this;
                                            int i4 = lastTryDefaultErrorMessage;
                                            int i5 = ConfirmDeviceCredentialBaseFragment.$r8$clinit;
                                            return confirmDeviceCredentialBaseFragment.getString(
                                                    i4);
                                        }
                                    }),
                    android.R.string.ok,
                    false,
                    i);
            return;
        }
        if (i3 == 1) {
            string = getString(R.string.failed_attempts_now_wiping_device);
        } else if (i3 == 2) {
            string =
                    this.mDevicePolicyManager
                            .getResources()
                            .getString(
                                    "Settings.WORK_PROFILE_LOCK_ATTEMPTS_FAILED",
                                    new Supplier() { // from class:
                                                     // com.android.settings.password.ConfirmDeviceCredentialBaseFragment$$ExternalSyntheticLambda3
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            ConfirmDeviceCredentialBaseFragment
                                                    confirmDeviceCredentialBaseFragment =
                                                            ConfirmDeviceCredentialBaseFragment
                                                                    .this;
                                            int i4 = ConfirmDeviceCredentialBaseFragment.$r8$clinit;
                                            return confirmDeviceCredentialBaseFragment.getString(
                                                    R.string.failed_attempts_now_wiping_profile);
                                        }
                                    });
        } else {
            if (i3 != 3) {
                throw new IllegalArgumentException(
                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                i3, "Unrecognized user type:"));
            }
            string = getString(R.string.failed_attempts_now_wiping_user);
        }
        LastTryDialog.show(
                childFragmentManager,
                null,
                string,
                R.string.failed_attempts_now_wiping_dialog_dismiss,
                true,
                i);
    }

    public final void updateRemoteLockscreenValidationViews() {
        RemoteLockscreenValidationFragment remoteLockscreenValidationFragment;
        if (!this.mRemoteValidation
                || (remoteLockscreenValidationFragment = this.mRemoteLockscreenValidationFragment)
                        == null) {
            return;
        }
        boolean z = remoteLockscreenValidationFragment.mIsInProgress;
        this.mGlifLayout.setProgressBarShown(z);
        this.mCheckBox.setEnabled(!z);
        this.mCancelButton.setEnabled(!z);
    }

    public final void validateGuess(LockscreenCredential lockscreenCredential) {
        byte[] bArr;
        final RemoteLockscreenValidationFragment remoteLockscreenValidationFragment =
                this.mRemoteLockscreenValidationFragment;
        RemoteLockscreenValidationClient remoteLockscreenValidationClient =
                this.mRemoteLockscreenValidationClient;
        byte[] sourcePublicKey = this.mRemoteLockscreenValidationSession.getSourcePublicKey();
        if (this.mCheckBox.isChecked()) {
            remoteLockscreenValidationFragment.mLockscreenCredential = lockscreenCredential;
        } else {
            remoteLockscreenValidationFragment.getClass();
        }
        try {
            bArr =
                    SecureBox.encrypt(
                            SecureBox.decodePublicKey(sourcePublicKey),
                            LockPatternUtils.ENCRYPTED_REMOTE_CREDENTIALS_HEADER,
                            lockscreenCredential.getCredential());
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            Log.w(
                    "RemoteLockscreenValidationFragment",
                    "Error encrypting device credential guess. Returning empty byte[].",
                    e);
            bArr = new byte[0];
        }
        remoteLockscreenValidationClient.validateLockscreenGuess(
                bArr,
                new IRemoteLockscreenValidationCallback
                        .Stub() { // from class:
                                  // com.android.settings.password.RemoteLockscreenValidationFragment.1
                    public final void onFailure(String str) {
                        RemoteLockscreenValidationFragment remoteLockscreenValidationFragment2 =
                                RemoteLockscreenValidationFragment.this;
                        remoteLockscreenValidationFragment2.mErrorMessage = str;
                        Handler handler = remoteLockscreenValidationFragment2.mHandler;
                        if (handler != null) {
                            handler.post(
                                    new RemoteLockscreenValidationFragment$$ExternalSyntheticLambda0(
                                            remoteLockscreenValidationFragment2, 1));
                        }
                    }

                    public final void onSuccess(
                            RemoteLockscreenValidationResult remoteLockscreenValidationResult) {
                        RemoteLockscreenValidationFragment remoteLockscreenValidationFragment2 =
                                RemoteLockscreenValidationFragment.this;
                        remoteLockscreenValidationFragment2.mResult =
                                remoteLockscreenValidationResult;
                        Handler handler = remoteLockscreenValidationFragment2.mHandler;
                        if (handler != null) {
                            handler.post(
                                    new RemoteLockscreenValidationFragment$$ExternalSyntheticLambda0(
                                            remoteLockscreenValidationFragment2, 0));
                        }
                    }
                });
        remoteLockscreenValidationFragment.mIsInProgress = true;
    }

    @Override // com.samsung.android.settings.knox.KnoxConfirmDeviceCredentialBaseFragmentHelper.Callback
    public void disableConfirmCredentialCallback() {}

    @Override // com.samsung.android.settings.knox.KnoxConfirmDeviceCredentialBaseFragmentHelper.Callback
    public void enableConfirmCredentialCallback() {}

    @Override // com.samsung.android.settings.knox.KnoxConfirmDeviceCredentialBaseFragmentHelper.Callback
    public void onSaveInstanceStateCallback() {}

    @Override // com.samsung.android.settings.knox.KnoxConfirmDeviceCredentialBaseFragmentHelper.Callback
    public void updateState() {}

    @Override // com.samsung.android.settings.knox.KnoxConfirmDeviceCredentialBaseFragmentHelper.Callback
    public void adjustPasswordViewWithIme(View view, int i) {}
}

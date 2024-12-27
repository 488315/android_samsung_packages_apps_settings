package com.android.settings.password;

import android.app.RemoteLockscreenValidationResult;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.internal.widget.LinearLayoutWithDefaultTouchRecepient;
import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockPatternView;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.biometrics.GatekeeperPasswordProvider;
import com.android.settingslib.animation.AppearAnimationUtils;
import com.android.settingslib.animation.DisappearAnimationUtils;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.accounts.HostAuth;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import com.samsung.android.settings.knox.KnoxConfirmDeviceCredentialBaseFragmentHelper;
import com.samsung.android.settings.knox.KnoxConfirmLockHelper;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.settings.password.SecGlifLayout;

import java.util.List;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ConfirmLockPattern extends ConfirmDeviceCredentialBaseActivity {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class InternalActivity extends ConfirmLockPattern {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class KnoxWorkChallengeActivity extends ConfirmLockPattern {}

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class Stage {
        public static final /* synthetic */ Stage[] $VALUES;
        public static final Stage LockedOut;
        public static final Stage NeedToUnlock;
        public static final Stage NeedToUnlockWrong;

        static {
            Stage stage = new Stage("NeedToUnlock", 0);
            NeedToUnlock = stage;
            Stage stage2 = new Stage("NeedToUnlockWrong", 1);
            NeedToUnlockWrong = stage2;
            Stage stage3 = new Stage("LockedOut", 2);
            LockedOut = stage3;
            $VALUES = new Stage[] {stage, stage2, stage3};
        }

        public static Stage valueOf(String str) {
            return (Stage) Enum.valueOf(Stage.class, str);
        }

        public static Stage[] values() {
            return (Stage[]) $VALUES.clone();
        }
    }

    @Override // com.android.settings.SettingsActivity, android.app.Activity
    public final Intent getIntent() {
        Intent intent = new Intent(super.getIntent());
        intent.putExtra(":settings:show_fragment", ConfirmLockPatternFragment.class.getName());
        intent.putExtra(":settings:show_fragment_as_subsetting", true);
        return intent;
    }

    @Override // com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return ConfirmLockPatternFragment.class.getName().equals(str);
    }

    @Override // com.android.settings.password.ConfirmDeviceCredentialBaseActivity,
              // com.android.settings.SettingsActivity, android.app.Activity,
              // android.view.ContextThemeWrapper
    public final void onApplyThemeResource(Resources.Theme theme, int i, boolean z) {
        super.onApplyThemeResource(
                theme,
                (!Utils.isDeviceProvisioned(this) || this.mRemoteValidation)
                        ? R.style.LockscreenSUWTheme
                        : R.style.LockScreenTheme,
                z);
    }

    @Override // com.android.settings.password.ConfirmDeviceCredentialBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity, android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        Fragment findFragmentById = getSupportFragmentManager().findFragmentById(R.id.main_content);
        if (findFragmentById != null && (findFragmentById instanceof ConfirmLockPatternFragment)) {
            ConfirmLockPatternFragment confirmLockPatternFragment =
                    (ConfirmLockPatternFragment) findFragmentById;
            if (confirmLockPatternFragment.mLockTypePolicy.isDevicePasswordAdminEnabled()
                    && KnoxUtils.isChangeRequested(
                                    confirmLockPatternFragment.getContext(),
                                    confirmLockPatternFragment.mUserId)
                            > 0) {
                return true;
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity, android.app.Activity,
              // android.view.Window.Callback
    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        Fragment findFragmentById = getSupportFragmentManager().findFragmentById(R.id.main_content);
        if (findFragmentById == null || !(findFragmentById instanceof ConfirmLockPatternFragment)) {
            return;
        }
        ConfirmLockPatternFragment confirmLockPatternFragment =
                (ConfirmLockPatternFragment) findFragmentById;
        KnoxConfirmLockHelper knoxConfirmLockHelper =
                confirmLockPatternFragment
                        .mKnoxConfirmDeviceCredentialBaseFragmentHelper
                        .mKnoxConfirmLockHelper;
        if (knoxConfirmLockHelper != null) {
            knoxConfirmLockHelper.onWindowFocusChanged(z);
        }
        KnoxConfirmLockHelper knoxConfirmLockHelper2 =
                confirmLockPatternFragment
                        .mKnoxConfirmDeviceCredentialBaseFragmentHelper
                        .mKnoxConfirmLockHelper;
        if (knoxConfirmLockHelper2 != null
                ? knoxConfirmLockHelper2.setCredentialCheckResultTrackerIfNeeded(z)
                : false) {
            confirmLockPatternFragment.mCredentialCheckResultTracker.setListener(
                    confirmLockPatternFragment);
        }
        if (LockUtils.isApplyingTabletGUI(confirmLockPatternFragment.getActivity())) {
            confirmLockPatternFragment.getActivity().getWindow().setCloseOnTouchOutside(false);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class ConfirmLockPatternFragment extends ConfirmDeviceCredentialBaseFragment
            implements CredentialCheckResultTracker.Listener,
                    SaveAndFinishWorker.Listener,
                    RemoteLockscreenValidationFragment.Listener {
        public static final /* synthetic */ int $r8$clinit = 0;
        public Button mBackUpPinButton;
        public CharSequence mCheckBoxLabel;
        public CountDownTimer mCountdownTimer;
        public CredentialCheckResultTracker mCredentialCheckResultTracker;
        public CharSequence mDetailsText;
        public boolean mExternal;
        public CharSequence mHeaderText;
        public boolean mIsManagedProfile;
        public LockPatternView mLockPatternView;
        public LockscreenCredential mPattern;
        public AsyncTask mPendingLockCheck;
        public View mSudContent;
        public LinearLayoutWithDefaultTouchRecepient mTopLayout;
        public boolean mDisappearing = false;
        public Stage mUiStage = Stage.NeedToUnlock;
        public final AnonymousClass1 mBackupPinButtonClickListner =
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.password.ConfirmLockPattern.ConfirmLockPatternFragment.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        Intent intent =
                                new Intent()
                                        .setClass(
                                                ConfirmLockPatternFragment.this.getActivity(),
                                                ConfirmLockPassword.class);
                        intent.putExtra(
                                "com.android.settings.ConfirmCredentials.header",
                                ConfirmLockPatternFragment.this
                                        .getActivity()
                                        .getString(R.string.confirm_backup_pin));
                        if (ConfirmLockPatternFragment.this.mReturnGatekeeperPassword) {
                            intent.putExtra("request_gk_pw_handle", true);
                        }
                        intent.addFlags(33554432);
                        ConfirmLockPatternFragment.this.getActivity().startActivity(intent);
                        ConfirmLockPatternFragment.this.getActivity().finish();
                    }
                };
        public boolean mTwoFactorBiometricConfirmed = false;
        public boolean mWaitingForTwoFactorConfirmation = false;
        public final AnonymousClass3 mClearPatternRunnable =
                new Runnable() { // from class:
                                 // com.android.settings.password.ConfirmLockPattern.ConfirmLockPatternFragment.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        ConfirmLockPatternFragment.this.mLockPatternView.clearPattern();
                    }
                };
        public final AnonymousClass4 mConfirmExistingLockPatternListener = new AnonymousClass4();

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.password.ConfirmLockPattern$ConfirmLockPatternFragment$2, reason: invalid class name */
        public final class AnonymousClass2 implements AppearAnimationUtils.RowTranslationScaler {}

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.password.ConfirmLockPattern$ConfirmLockPatternFragment$5, reason: invalid class name */
        public final class AnonymousClass5 extends CountDownTimer {
            public AnonymousClass5(long j) {
                super(j, 1000L);
            }

            @Override // android.os.CountDownTimer
            public final void onFinish() {
                ConfirmLockPatternFragment confirmLockPatternFragment =
                        ConfirmLockPatternFragment.this;
                Stage stage = Stage.NeedToUnlock;
                int i = ConfirmLockPatternFragment.$r8$clinit;
                confirmLockPatternFragment.updateStage(stage);
                ConfirmLockPatternFragment confirmLockPatternFragment2 =
                        ConfirmLockPatternFragment.this;
                confirmLockPatternFragment2.mLockPatternUtils.getLockoutAttemptDeadline(
                        confirmLockPatternFragment2.mEffectiveUserId);
                KnoxConfirmLockHelper knoxConfirmLockHelper =
                        ConfirmLockPatternFragment.this
                                .mKnoxConfirmDeviceCredentialBaseFragmentHelper
                                .mKnoxConfirmLockHelper;
                if (knoxConfirmLockHelper != null) {
                    knoxConfirmLockHelper.updateState();
                }
            }

            @Override // android.os.CountDownTimer
            public final void onTick(long j) {
                ConfirmLockPatternFragment confirmLockPatternFragment =
                        ConfirmLockPatternFragment.this;
                confirmLockPatternFragment.mGlifLayout.setDescriptionText(
                        LockUtils.updateAttemptLockoutDesc(
                                confirmLockPatternFragment.getActivity().getApplicationContext(),
                                j));
            }
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment
        public final void authenticationSucceeded() {
            KnoxConfirmDeviceCredentialBaseFragmentHelper
                    knoxConfirmDeviceCredentialBaseFragmentHelper =
                            this.mKnoxConfirmDeviceCredentialBaseFragmentHelper;
            boolean z = this.mPendingLockCheck != null;
            boolean z2 = this.mDisappearing;
            KnoxConfirmLockHelper knoxConfirmLockHelper =
                    knoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
            if (knoxConfirmLockHelper != null
                    ? knoxConfirmLockHelper.interceptAuthenticationSucceededIfNeeded(z, z2)
                    : false) {
                Log.d("ConfirmLockPatternFragment", "interceptAuthenticationSucceededIfNeeded");
            } else {
                this.mCredentialCheckResultTracker.setResult(
                        true, new Intent(), 0, this.mEffectiveUserId);
            }
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment,
                  // com.samsung.android.settings.knox.KnoxConfirmDeviceCredentialBaseFragmentHelper.Callback
        public final void disableConfirmCredentialCallback() {
            updateStage(Stage.LockedOut);
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment,
                  // com.samsung.android.settings.knox.KnoxConfirmDeviceCredentialBaseFragmentHelper.Callback
        public final void enableConfirmCredentialCallback() {
            this.mTwoFactorBiometricConfirmed = true;
            this.mWaitingForTwoFactorConfirmation = false;
            if (this.mLockPatternUtils.getLockoutAttemptDeadline(this.mEffectiveUserId) == 0) {
                refreshLockScreen();
                updateStage(Stage.NeedToUnlock);
            }
        }

        public final String getDefaultDetails$1() {
            KnoxConfirmLockHelper knoxConfirmLockHelper =
                    this.mKnoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
            int defaultDetails =
                    knoxConfirmLockHelper != null ? knoxConfirmLockHelper.getDefaultDetails() : 0;
            if (defaultDetails > 0) {
                return getString(defaultDetails);
            }
            if (SemPersonaManager.isDoEnabled(this.mUserId)
                    && KnoxUtils.isChangeRequested(getContext(), this.mUserId) > 0) {
                this.mGlifLayout.mDetailsTextView.setMaxLines(Preference.DEFAULT_ORDER);
                return getString(R.string.sec_knox_lockpassword_strong_auth_required_work_pattern);
            }
            if (this.mFrp) {
                return getString(
                        Utils.isTablet()
                                ? R.string.sec_lockpassword_confirm_your_pattern_details_frp_tablet
                                : R.string.sec_lockpassword_confirm_your_pattern_details_frp);
            }
            if (this.mRepairMode) {
                return getString(R.string.lockpassword_confirm_repair_mode_pattern_details);
            }
            if (this.mRemoteValidation) {
                return getString(R.string.sec_lockpassword_remote_validation_pattern_details);
            }
            isStrongAuthRequired$1();
            return getString(R.string.lockpattern_need_to_unlock);
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment
        public final int getLastTryDefaultErrorMessage(int i) {
            if (i == 1) {
                return R.string.sec_confirm_lock_last_attempt_before_wipe_device;
            }
            if (i == 2) {
                return R.string.sec_confirm_lock_last_attempt_before_wipe_profile;
            }
            if (i == 3) {
                return R.string.sec_confirm_lock_last_attempt_before_wipe_user;
            }
            throw new IllegalArgumentException(
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "Unrecognized user type:"));
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment
        public final String getLastTryOverrideErrorMessageId(int i) {
            return i == 2
                    ? "Settings.WORK_PROFILE_LAST_PATTERN_ATTEMPT_BEFORE_WIPE"
                    : PeripheralBarcodeConstants.Symbology.UNDEFINED;
        }

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 31;
        }

        @Override // com.android.settings.password.SaveAndFinishWorker.Listener
        public final void onChosenLockSaveFinished(Intent intent, boolean z) {
            Log.i(
                    "ConfirmLockPatternFragment",
                    "Device lockscreen has been set to remote device's lockscreen.");
            this.mRemoteLockscreenValidationFragment.clearLockscreenCredential();
            Intent intent2 = new Intent();
            if (this.mRemoteValidation
                    && GatekeeperPasswordProvider.containsGatekeeperPasswordHandle(intent)) {
                intent2.putExtra(
                        "gk_pw_handle",
                        GatekeeperPasswordProvider.getGatekeeperPasswordHandle(intent));
            }
            this.mCredentialCheckResultTracker.setResult(true, intent2, 0, this.mEffectiveUserId);
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment,
                  // com.android.settingslib.core.lifecycle.ObservableFragment,
                  // androidx.fragment.app.Fragment
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            Intent intent = getActivity().getIntent();
            if (intent != null) {
                this.mUnlockRecovery = intent.getBooleanExtra("unlock_recovery", false);
                this.mExternal =
                        intent.getBooleanExtra(
                                "com.android.settings.ConfirmCredentials.showWhenLocked", false);
            }
            KnoxConfirmLockHelper knoxConfirmLockHelper =
                    this.mKnoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
            if (knoxConfirmLockHelper != null) {
                knoxConfirmLockHelper.onCreate(bundle);
            }
            if (bundle != null) {
                this.mTwoFactorBiometricConfirmed =
                        bundle.getBoolean("two_factor_biometric_confirmed", false);
                this.mWaitingForTwoFactorConfirmation =
                        bundle.getBoolean("waiting_for_two_factor_confirmation", false);
            }
        }

        @Override // androidx.fragment.app.Fragment
        public final View onCreateView(
                LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            View inflate;
            if (this.mFrp || this.mRemoteValidation) {
                inflate =
                        layoutInflater.inflate(
                                R.layout.sec_setup_confirm_lock_pattern, viewGroup, false);
            } else {
                inflate =
                        layoutInflater.inflate(
                                LockUtils.isApplyingTabletGUI(getContext())
                                        ? R.layout.sec_confirm_lock_pattern_tablet
                                        : R.layout.sec_confirm_lock_pattern,
                                viewGroup,
                                false);
            }
            KnoxConfirmDeviceCredentialBaseFragmentHelper
                    knoxConfirmDeviceCredentialBaseFragmentHelper =
                            this.mKnoxConfirmDeviceCredentialBaseFragmentHelper;
            FragmentActivity activity = getActivity();
            KnoxConfirmLockHelper knoxConfirmLockHelper =
                    knoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
            if (knoxConfirmLockHelper != null) {
                inflate =
                        knoxConfirmLockHelper.switchViewIfNeeded(layoutInflater, inflate, activity);
            }
            this.mGlifLayout =
                    new SecGlifLayout(getContext(), inflate, this.mFrp || this.mRemoteValidation);
            this.mLockPatternView = inflate.findViewById(R.id.lockPattern);
            Button button = (Button) inflate.findViewById(R.id.backupPin);
            this.mBackUpPinButton = button;
            button.setOnClickListener(this.mBackupPinButtonClickListner);
            LinearLayoutWithDefaultTouchRecepient findViewById =
                    inflate.findViewById(R.id.topLayout);
            this.mTopLayout = findViewById;
            if ((this.mFrp || this.mRemoteValidation) && findViewById != null) {
                findViewById.setPadding(0, 0, 0, 0);
            }
            View findViewById2 = this.mGlifLayout.findViewById(R.id.sud_layout_content);
            this.mSudContent = findViewById2;
            findViewById2.setPadding(
                    findViewById2.getPaddingLeft(), 0, this.mSudContent.getPaddingRight(), 0);
            this.mIsManagedProfile =
                    UserManager.get(getActivity()).isManagedProfile(this.mEffectiveUserId);
            LinearLayoutWithDefaultTouchRecepient linearLayoutWithDefaultTouchRecepient =
                    this.mTopLayout;
            if (linearLayoutWithDefaultTouchRecepient != null) {
                linearLayoutWithDefaultTouchRecepient.setDefaultTouchRecepient(
                        this.mLockPatternView);
            }
            if (this.mIsManagedProfile
                    && !SemPersonaManager.isKnoxId(this.mEffectiveUserId)
                    && this.mExternal) {
                this.mGlifLayout.mHeaderTextView.setVisibility(0);
                this.mGlifLayout.mIcon.setVisibility(0);
            }
            Intent intent = getActivity().getIntent();
            if (intent != null) {
                this.mHeaderText =
                        intent.getCharSequenceExtra(
                                "com.android.settings.ConfirmCredentials.header");
                this.mDetailsText =
                        intent.getCharSequenceExtra(
                                "com.android.settings.ConfirmCredentials.details");
                this.mCheckBoxLabel =
                        intent.getCharSequenceExtra("android.app.extra.CHECKBOX_LABEL");
            }
            if (TextUtils.isEmpty(this.mHeaderText) && this.mIsManagedProfile) {
                this.mHeaderText =
                        this.mDevicePolicyManager.getOrganizationNameForUser(this.mUserId);
            }
            this.mLockPatternView.setInStealthMode(
                    !this.mLockPatternUtils.isVisiblePatternEnabled(this.mEffectiveUserId));
            this.mLockPatternView.setOnPatternListener(this.mConfirmExistingLockPatternListener);
            this.mLockPatternView.setOnTouchListener(
                    new ConfirmLockPattern$ConfirmLockPatternFragment$$ExternalSyntheticLambda1());
            updateStage(Stage.NeedToUnlock);
            if (bundle != null) {
                updateStage(Stage.values()[bundle.getInt("uiStage")]);
                this.mBackUpPinButton.setVisibility(bundle.getInt("backup_pin_btn_visibility"));
            } else if (!this.mFrp
                    && !this.mRemoteValidation
                    && !this.mRepairMode
                    && !this.mLockPatternUtils.isLockPatternEnabled(this.mEffectiveUserId)) {
                getActivity().setResult(-1);
                getActivity().finish();
            }
            new AppearAnimationUtils(
                    getContext(),
                    220L,
                    2.0f,
                    1.3f,
                    AnimationUtils.loadInterpolator(
                            getContext(), android.R.interpolator.linear_out_slow_in));
            new DisappearAnimationUtils(
                    getContext(),
                    125L,
                    4.0f,
                    0.3f,
                    AnimationUtils.loadInterpolator(
                            getContext(), android.R.interpolator.fast_out_linear_in),
                    new AnonymousClass2());
            if (!this.mFrp && !this.mRemoteValidation) {
                setAccessibilityTitle(this.mGlifLayout.mHeaderTextView.getText());
            }
            CredentialCheckResultTracker credentialCheckResultTracker =
                    (CredentialCheckResultTracker)
                            getFragmentManager().findFragmentByTag("check_lock_result");
            this.mCredentialCheckResultTracker = credentialCheckResultTracker;
            if (credentialCheckResultTracker == null) {
                this.mCredentialCheckResultTracker = new CredentialCheckResultTracker();
                FragmentManager fragmentManager = getFragmentManager();
                BackStackRecord m =
                        DialogFragment$$ExternalSyntheticOutline0.m(
                                fragmentManager, fragmentManager);
                m.doAddOp(0, this.mCredentialCheckResultTracker, "check_lock_result", 1);
                m.commitInternal(false);
            }
            if (this.mLockTypePolicy.mIsKnoxId) {
                this.mLockPatternView.setInStealthMode(
                        this.mLockPatternUtils.isVisiblePatternDisabledByMDM()
                                | (!this.mLockPatternUtils.isVisiblePatternEnabled(
                                        this.mEffectiveUserId)));
            }
            if (!LockUtils.isApplyingTabletGUI(getContext())) {
                inflate.semSetRoundedCorners(15);
                inflate.semSetRoundedCornerColor(
                        15, getResources().getColor(R.color.sec_widget_round_and_bgcolor, null));
            }
            KnoxConfirmDeviceCredentialBaseFragmentHelper
                    knoxConfirmDeviceCredentialBaseFragmentHelper2 =
                            this.mKnoxConfirmDeviceCredentialBaseFragmentHelper;
            LockPatternView lockPatternView = this.mLockPatternView;
            KnoxConfirmLockHelper knoxConfirmLockHelper2 =
                    knoxConfirmDeviceCredentialBaseFragmentHelper2.mKnoxConfirmLockHelper;
            if (knoxConfirmLockHelper2 != null) {
                knoxConfirmLockHelper2.setPatternColor(lockPatternView);
            }
            KnoxConfirmLockHelper knoxConfirmLockHelper3 =
                    this.mKnoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
            if (knoxConfirmLockHelper3 != null) {
                knoxConfirmLockHelper3.onCreateView(inflate);
            }
            if (this.mFrp || this.mRemoteValidation) {
                inflate.semSetRoundedCorners(0);
                inflate.setBackgroundColor(
                        getResources().getColor(R.color.sec_lock_suw_background_color, null));
            }
            if (this.mRemoteValidation) {
                this.mGlifLayout.setProgressBarShown(false);
                this.mLockPatternView.setInStealthMode(false);
            }
            return inflate;
        }

        @Override // com.android.settings.password.CredentialCheckResultTracker.Listener
        public final void onCredentialChecked(boolean z, Intent intent, int i, int i2, boolean z2) {
            onPatternChecked(z, intent, i, i2, z2);
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment,
                  // com.android.settingslib.core.lifecycle.ObservableFragment,
                  // androidx.fragment.app.Fragment
        public final void onDestroy() {
            LockscreenCredential lockscreenCredential;
            super.onDestroy();
            if (!this.mLockTypePolicy.isEnterpriseUser()
                    || (lockscreenCredential = this.mPattern) == null) {
                return;
            }
            lockscreenCredential.zeroize();
        }

        public final void onPatternChecked(boolean z, Intent intent, int i, int i2, boolean z2) {
            StringBuilder sb = new StringBuilder("!@onPatternChecked matched=");
            sb.append(z);
            sb.append(" timeoutMs=");
            sb.append(i);
            sb.append(" userId=");
            Preference$$ExternalSyntheticOutline0.m(sb, i2, "ConfirmLockPatternFragment");
            this.mLockPatternView.setEnabled(true);
            if (!z) {
                if (i > 0) {
                    KnoxConfirmLockHelper knoxConfirmLockHelper =
                            this.mKnoxConfirmDeviceCredentialBaseFragmentHelper
                                    .mKnoxConfirmLockHelper;
                    if (knoxConfirmLockHelper != null) {
                        knoxConfirmLockHelper.setBiometricLockoutDeadline(i);
                    }
                    refreshLockScreen();
                    long lockoutAttemptDeadline =
                            this.mLockPatternUtils.setLockoutAttemptDeadline(i2, i);
                    this.mHandler.removeCallbacks(this.mResetErrorRunnable);
                    updateStage(Stage.LockedOut);
                    this.mCountdownTimer =
                            new AnonymousClass5(
                                            lockoutAttemptDeadline - SystemClock.elapsedRealtime())
                                    .start();
                } else {
                    updateStage(Stage.NeedToUnlockWrong);
                    this.mLockPatternView.clearPattern();
                }
                if (z2) {
                    reportFailedAttempt();
                    return;
                }
                return;
            }
            if (z2) {
                KnoxConfirmLockHelper knoxConfirmLockHelper2 =
                        this.mKnoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
                if (knoxConfirmLockHelper2 != null) {
                    knoxConfirmLockHelper2.reportSuccessfulAttempt();
                }
                ConfirmDeviceCredentialUtils.reportSuccessfulAttempt(
                        this.mLockPatternUtils,
                        this.mUserManager,
                        this.mDevicePolicyManager,
                        this.mEffectiveUserId,
                        true);
            }
            try {
                if (this.mUnlockRecovery
                        && LockUtils.isFmmUnlockAllowed(
                                getContext(),
                                this.mUserId,
                                this.mLockTypePolicy.isEnterpriseUser())) {
                    getActivity()
                            .startService(LockUtils.getFmmService(this.mPattern, getContext()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            int windowingMode =
                    getActivity()
                            .getResources()
                            .getConfiguration()
                            .windowConfiguration
                            .getWindowingMode();
            if (windowingMode == 5 || windowingMode == 6) {
                ConfirmDeviceCredentialUtils.checkForPendingIntent(getActivity());
                startDisappearAnimation$1(intent);
            } else {
                startDisappearAnimation$1(intent);
                ConfirmDeviceCredentialUtils.checkForPendingIntent(getActivity());
            }
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment,
                  // com.android.settingslib.core.lifecycle.ObservableFragment,
                  // androidx.fragment.app.Fragment
        public final void onPause() {
            super.onPause();
            CountDownTimer countDownTimer = this.mCountdownTimer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            this.mCredentialCheckResultTracker.setListener(null);
            RemoteLockscreenValidationFragment remoteLockscreenValidationFragment =
                    this.mRemoteLockscreenValidationFragment;
            if (remoteLockscreenValidationFragment != null) {
                remoteLockscreenValidationFragment.setListener(null, null);
            }
        }

        @Override // com.android.settings.password.RemoteLockscreenValidationFragment.Listener
        public final void onRemoteLockscreenValidationResult(
                RemoteLockscreenValidationResult remoteLockscreenValidationResult) {
            int resultCode = remoteLockscreenValidationResult.getResultCode();
            if (resultCode != 1) {
                if (resultCode == 2) {
                    this.mCredentialCheckResultTracker.setResult(
                            false, new Intent(), 0, this.mEffectiveUserId);
                } else if (resultCode == 3) {
                    this.mCredentialCheckResultTracker.setResult(
                            false,
                            new Intent(),
                            (int) remoteLockscreenValidationResult.getTimeoutMillis(),
                            this.mEffectiveUserId);
                } else if (resultCode == 4 || resultCode == 5) {
                    onRemoteLockscreenValidationFailure(
                            String.format(
                                    "Cannot continue remote lockscreen validation. ResultCode=%d",
                                    Integer.valueOf(
                                            remoteLockscreenValidationResult.getResultCode())));
                }
                updateRemoteLockscreenValidationViews();
                this.mRemoteLockscreenValidationFragment.clearLockscreenCredential();
                return;
            }
            if (!this.mCheckBox.isChecked()
                    || this.mRemoteLockscreenValidationFragment.mLockscreenCredential == null) {
                this.mCredentialCheckResultTracker.setResult(
                        true, new Intent(), 0, this.mEffectiveUserId);
                return;
            }
            Log.i(
                    "ConfirmLockPatternFragment",
                    "Setting device screen lock to the other device's screen lock.");
            SaveAndFinishWorker saveAndFinishWorker = new SaveAndFinishWorker();
            FragmentManager fragmentManager = getFragmentManager();
            BackStackRecord m =
                    DialogFragment$$ExternalSyntheticOutline0.m(fragmentManager, fragmentManager);
            m.doAddOp(0, saveAndFinishWorker, null, 1);
            m.commitInternal(false);
            getFragmentManager().executePendingTransactions();
            saveAndFinishWorker.setListener(this);
            saveAndFinishWorker.mRequestGatekeeperPassword = true;
            saveAndFinishWorker.start(
                    this.mLockPatternUtils,
                    this.mRemoteLockscreenValidationFragment.mLockscreenCredential,
                    null,
                    this.mEffectiveUserId);
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment,
                  // com.android.settings.core.InstrumentedFragment,
                  // com.android.settingslib.core.lifecycle.ObservableFragment,
                  // androidx.fragment.app.Fragment
        public final void onResume() {
            Intent intent;
            super.onResume();
            long lockoutAttemptDeadline =
                    this.mLockPatternUtils.getLockoutAttemptDeadline(this.mEffectiveUserId);
            if (lockoutAttemptDeadline != 0) {
                CredentialCheckResultTracker credentialCheckResultTracker =
                        this.mCredentialCheckResultTracker;
                credentialCheckResultTracker.mHasResult = false;
                credentialCheckResultTracker.mResultMatched = false;
                credentialCheckResultTracker.mResultData = null;
                credentialCheckResultTracker.mResultTimeoutMs = 0;
                credentialCheckResultTracker.mResultEffectiveUserId = 0;
                this.mHandler.removeCallbacks(this.mResetErrorRunnable);
                updateStage(Stage.LockedOut);
                this.mCountdownTimer =
                        new AnonymousClass5(lockoutAttemptDeadline - SystemClock.elapsedRealtime())
                                .start();
            } else if (!this.mLockPatternView.isEnabled()) {
                updateStage(Stage.NeedToUnlock);
            }
            this.mCredentialCheckResultTracker.setListener(this);
            RemoteLockscreenValidationFragment remoteLockscreenValidationFragment =
                    this.mRemoteLockscreenValidationFragment;
            if (remoteLockscreenValidationFragment != null) {
                remoteLockscreenValidationFragment.setListener(this, this.mHandler);
                if (this.mRemoteLockscreenValidationFragment.mIsInProgress) {
                    this.mLockPatternView.setEnabled(false);
                }
            }
            if (getActivity() == null
                    || (intent = getActivity().getIntent()) == null
                    || this.mTwoFactorBiometricConfirmed) {
                return;
            }
            boolean booleanExtra = intent.getBooleanExtra("from_knox_work_profile_security", false);
            KnoxConfirmDeviceCredentialBaseFragmentHelper
                    knoxConfirmDeviceCredentialBaseFragmentHelper =
                            this.mKnoxConfirmDeviceCredentialBaseFragmentHelper;
            FragmentActivity activity = getActivity();
            boolean z = this.mWaitingForTwoFactorConfirmation;
            KnoxConfirmLockHelper knoxConfirmLockHelper =
                    knoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
            this.mWaitingForTwoFactorConfirmation =
                    knoxConfirmLockHelper != null
                            ? knoxConfirmLockHelper.confirmBiometricIfNeeded(
                                    booleanExtra, activity, z)
                            : false;
        }

        @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
                  // androidx.fragment.app.Fragment
        public final void onSaveInstanceState(Bundle bundle) {
            if (Stage.values()[this.mUiStage.ordinal()] == Stage.NeedToUnlock) {
                this.mLockPatternView.clearPattern();
            }
            bundle.putInt("uiStage", this.mUiStage.ordinal());
            bundle.putInt("backup_pin_btn_visibility", this.mBackUpPinButton.getVisibility());
            bundle.putBoolean("two_factor_biometric_confirmed", this.mTwoFactorBiometricConfirmed);
            bundle.putBoolean(
                    "waiting_for_two_factor_confirmation", this.mWaitingForTwoFactorConfirmation);
            KnoxConfirmLockHelper knoxConfirmLockHelper =
                    this.mKnoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
            if (knoxConfirmLockHelper != null) {
                knoxConfirmLockHelper.onSavedInstanceState(bundle);
            }
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment,
                  // com.samsung.android.settings.knox.KnoxConfirmDeviceCredentialBaseFragmentHelper.Callback
        public final void onSaveInstanceStateCallback() {
            if (Stage.values()[this.mUiStage.ordinal()] == Stage.NeedToUnlockWrong) {
                this.mLockPatternView.clearPattern();
            }
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment
        public final void onShowDefault() {
            CharSequence charSequence = this.mDetailsText;
            if (charSequence != null) {
                this.mGlifLayout.setDescriptionText(charSequence);
                return;
            }
            this.mGlifLayout.setDescriptionText(getDefaultDetails$1());
            this.mKnoxConfirmDeviceCredentialBaseFragmentHelper.needToUpdateErrorMessage(
                    this.mLockPatternUtils.getCurrentFailedPasswordAttempts(this.mEffectiveUserId),
                    this.mGlifLayout.mDetailsTextView);
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment,
                  // androidx.fragment.app.Fragment
        public final void onViewCreated(View view, Bundle bundle) {
            CharSequence charSequence;
            super.onViewCreated(view, bundle);
            if (this.mRemoteValidation) {
                CheckBox checkBox = this.mCheckBox;
                if (checkBox != null) {
                    if (!TextUtils.isEmpty(this.mCheckBoxLabel)) {
                        charSequence = this.mCheckBoxLabel;
                    } else {
                        if (!this.mRemoteValidation) {
                            throw new IllegalStateException(
                                    "Trying to get default checkbox label for illegal flow");
                        }
                        charSequence =
                                getString(
                                        R.string
                                                .sec_lockpassword_remote_validation_set_pattern_as_screenlock);
                    }
                    checkBox.setText(charSequence);
                }
                if (this.mCancelButton != null && TextUtils.isEmpty(this.mAlternateButtonText)) {
                    this.mCancelButton.setText(R.string.lockpassword_forgot_pattern);
                }
                updateRemoteLockscreenValidationViews();
            }
            Button button = this.mForgotButton;
            if (button != null) {
                button.setText(R.string.lockpassword_forgot_pattern);
            }
        }

        public final void startDisappearAnimation$1(Intent intent) {
            if (this.mDisappearing) {
                return;
            }
            this.mDisappearing = true;
            ConfirmLockPattern confirmLockPattern = (ConfirmLockPattern) getActivity();
            if (confirmLockPattern == null || confirmLockPattern.isFinishing()) {
                return;
            }
            confirmLockPattern.setResult(-1, intent);
            confirmLockPattern.finish();
        }

        public final void updateStage(Stage stage) {
            KnoxConfirmDeviceCredentialBaseFragmentHelper
                    knoxConfirmDeviceCredentialBaseFragmentHelper =
                            this.mKnoxConfirmDeviceCredentialBaseFragmentHelper;
            boolean z = this.mWaitingForTwoFactorConfirmation;
            KnoxConfirmLockHelper knoxConfirmLockHelper =
                    knoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
            if (!(knoxConfirmLockHelper != null
                            ? knoxConfirmLockHelper.interceptUpdateEntryOrStateIfNeeded(z)
                            : false)
                    || stage == Stage.LockedOut) {
                this.mUiStage = stage;
                int ordinal = stage.ordinal();
                if (ordinal == 0) {
                    CharSequence charSequence = this.mHeaderText;
                    if (charSequence != null) {
                        this.mGlifLayout.setHeaderText(charSequence);
                    } else {
                        this.mGlifLayout.setHeaderText(
                                this.mFrp
                                        ? getString(R.string.sec_confirm_lock_pattern_header_frp)
                                        : this.mRepairMode
                                                ? getString(
                                                        R.string
                                                                .lockpassword_confirm_repair_mode_pattern_header)
                                                : this.mRemoteValidation
                                                        ? getString(
                                                                R.string
                                                                        .sec_lockpassword_remote_validation_header)
                                                        : this.mIsManagedProfile
                                                                ? this.mDevicePolicyManager
                                                                        .getResources()
                                                                        .getString(
                                                                                "Settings.CONFIRM_WORK_PROFILE_PATTERN_HEADER",
                                                                                new Supplier() { // from class: com.android.settings.password.ConfirmLockPattern$ConfirmLockPatternFragment$$ExternalSyntheticLambda0
                                                                                    @Override // java.util.function.Supplier
                                                                                    public final
                                                                                    Object get() {
                                                                                        ConfirmLockPattern
                                                                                                        .ConfirmLockPatternFragment
                                                                                                confirmLockPatternFragment =
                                                                                                        ConfirmLockPattern
                                                                                                                .ConfirmLockPatternFragment
                                                                                                                .this;
                                                                                        int i =
                                                                                                ConfirmLockPattern
                                                                                                        .ConfirmLockPatternFragment
                                                                                                        .$r8$clinit;
                                                                                        return confirmLockPatternFragment
                                                                                                .getString(
                                                                                                        R
                                                                                                                .string
                                                                                                                .lockpassword_confirm_your_work_pattern_header);
                                                                                    }
                                                                                })
                                                                : getString(
                                                                        R.string
                                                                                .sec_lockpassword_confirm_your_pattern_header));
                    }
                    CharSequence charSequence2 = this.mDetailsText;
                    if (charSequence2 == null) {
                        charSequence2 = getDefaultDetails$1();
                    }
                    if (this.mDetailsText == null) {
                        this.mKnoxConfirmDeviceCredentialBaseFragmentHelper
                                .needToUpdateErrorMessage(
                                        this.mLockPatternUtils.getCurrentFailedPasswordAttempts(
                                                this.mEffectiveUserId),
                                        this.mGlifLayout.mDetailsTextView);
                    }
                    if (!this.mIsManagedProfile
                            || SemPersonaManager.isKnoxId(this.mEffectiveUserId)) {
                        this.mGlifLayout.setDescriptionText(charSequence2);
                    } else {
                        this.mGlifLayout.mDetailsTextView.setVisibility(8);
                    }
                    updateErrorMessage(
                            this.mLockPatternUtils.getCurrentFailedPasswordAttempts(
                                    this.mEffectiveUserId));
                    if (this.mLockPatternView.getVisibility() == 4) {
                        this.mLockPatternView.setVisibility(0);
                    }
                    this.mLockPatternView.setEnabled(true);
                    this.mLockPatternView.enableInput();
                    this.mLockPatternView.clearPattern();
                } else if (ordinal == 1) {
                    KnoxConfirmLockHelper knoxConfirmLockHelper2 =
                            this.mKnoxConfirmDeviceCredentialBaseFragmentHelper
                                    .mKnoxConfirmLockHelper;
                    this.mGlifLayout.setDescriptionText(
                            knoxConfirmLockHelper2 != null
                                    ? knoxConfirmLockHelper2.getErrorMessage()
                                    : R.string.sec_lockpassword_need_to_unlock_wrong);
                    TextView textView = this.mGlifLayout.mDetailsTextView;
                    textView.announceForAccessibility(textView.getText());
                    this.mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
                    this.mLockPatternView.setEnabled(true);
                    this.mLockPatternView.enableInput();
                } else if (ordinal == 2) {
                    this.mLockPatternView.setVisibility(4);
                    this.mLockPatternView.clearPattern();
                    this.mLockPatternView.setEnabled(false);
                }
                KnoxConfirmDeviceCredentialBaseFragmentHelper
                        knoxConfirmDeviceCredentialBaseFragmentHelper2 =
                                this.mKnoxConfirmDeviceCredentialBaseFragmentHelper;
                SecGlifLayout secGlifLayout = this.mGlifLayout;
                TextView textView2 = secGlifLayout.mDetailsTextView;
                TextView textView3 = secGlifLayout.mHeaderTextView;
                KnoxConfirmLockHelper knoxConfirmLockHelper3 =
                        knoxConfirmDeviceCredentialBaseFragmentHelper2.mKnoxConfirmLockHelper;
                if (knoxConfirmLockHelper3 != null) {
                    knoxConfirmLockHelper3.updateStageNeedToUnlock(textView3);
                }
                TextView textView4 = this.mGlifLayout.mHeaderTextView;
                textView4.announceForAccessibility(textView4.getText());
            }
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment,
                  // com.samsung.android.settings.knox.KnoxConfirmDeviceCredentialBaseFragmentHelper.Callback
        public final void updateState() {
            refreshLockScreen();
            updateStage(Stage.NeedToUnlock);
            this.mLockPatternUtils.getLockoutAttemptDeadline(this.mEffectiveUserId);
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment
        public final void onShowError() {}

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.password.ConfirmLockPattern$ConfirmLockPatternFragment$4, reason: invalid class name */
        public final class AnonymousClass4 implements LockPatternView.OnPatternListener {
            public AnonymousClass4() {}

            public final void onPatternCleared() {
                ConfirmLockPatternFragment confirmLockPatternFragment =
                        ConfirmLockPatternFragment.this;
                confirmLockPatternFragment.mLockPatternView.removeCallbacks(
                        confirmLockPatternFragment.mClearPatternRunnable);
            }

            public final void onPatternDetected(List list) {
                Log.d("ConfirmLockPatternFragment", "onPatternDetected");
                ConfirmLockPatternFragment confirmLockPatternFragment =
                        ConfirmLockPatternFragment.this;
                if (confirmLockPatternFragment.mPendingLockCheck != null
                        || confirmLockPatternFragment.mDisappearing) {
                    return;
                }
                confirmLockPatternFragment.mLockPatternView.setEnabled(false);
                final LockscreenCredential createPattern = LockscreenCredential.createPattern(list);
                ConfirmLockPatternFragment confirmLockPatternFragment2 =
                        ConfirmLockPatternFragment.this;
                confirmLockPatternFragment2.mPattern = createPattern;
                if (confirmLockPatternFragment2.mRemoteValidation) {
                    confirmLockPatternFragment2.validateGuess(createPattern);
                    ConfirmLockPatternFragment.this.updateRemoteLockscreenValidationViews();
                    return;
                }
                final Intent intent = new Intent();
                ConfirmLockPatternFragment confirmLockPatternFragment3 =
                        ConfirmLockPatternFragment.this;
                if (confirmLockPatternFragment3.mReturnGatekeeperPassword) {
                    if (confirmLockPatternFragment3.getActivity() instanceof InternalActivity) {
                        startVerifyPattern(createPattern, intent, 1);
                        return;
                    }
                } else {
                    if (!confirmLockPatternFragment3.mForceVerifyPath) {
                        if (createPattern.size() < 4) {
                            ConfirmLockPatternFragment confirmLockPatternFragment4 =
                                    ConfirmLockPatternFragment.this;
                            int i = confirmLockPatternFragment4.mEffectiveUserId;
                            int i2 = ConfirmLockPatternFragment.$r8$clinit;
                            confirmLockPatternFragment4.onPatternChecked(
                                    false, intent, 0, i, false);
                            return;
                        }
                        Log.d("ConfirmLockPatternFragment", "startCheckPassword()");
                        ConfirmLockPatternFragment confirmLockPatternFragment5 =
                                ConfirmLockPatternFragment.this;
                        final int i3 = confirmLockPatternFragment5.mEffectiveUserId;
                        confirmLockPatternFragment5.mPendingLockCheck =
                                LockPatternChecker.checkCredential(
                                        confirmLockPatternFragment5.mLockPatternUtils,
                                        createPattern,
                                        i3,
                                        new LockPatternChecker
                                                .OnCheckCallback() { // from class:
                                                                     // com.android.settings.password.ConfirmLockPattern.ConfirmLockPatternFragment.4.1
                                            public final void onChecked(boolean z, int i4) {
                                                Log.d("ConfirmLockPatternFragment", "onChecked()");
                                                ConfirmLockPatternFragment
                                                        confirmLockPatternFragment6 =
                                                                ConfirmLockPatternFragment.this;
                                                confirmLockPatternFragment6.mPendingLockCheck =
                                                        null;
                                                if (z
                                                        && (confirmLockPatternFragment6
                                                                        .getActivity()
                                                                instanceof InternalActivity)
                                                        && ConfirmLockPatternFragment.this
                                                                .mReturnCredentials) {
                                                    intent.putExtra(
                                                            HostAuth.PASSWORD,
                                                            (Parcelable) createPattern);
                                                }
                                                ConfirmLockPatternFragment.this
                                                        .mCredentialCheckResultTracker.setResult(
                                                        z, intent, i4, i3);
                                            }
                                        });
                        return;
                    }
                    if (confirmLockPatternFragment3.getActivity() instanceof InternalActivity) {
                        startVerifyPattern(
                                createPattern,
                                intent,
                                ConfirmLockPatternFragment.this.mRequestWriteRepairModePassword
                                        ? 2
                                        : 0);
                        return;
                    }
                }
                ConfirmLockPatternFragment confirmLockPatternFragment6 =
                        ConfirmLockPatternFragment.this;
                confirmLockPatternFragment6.mCredentialCheckResultTracker.setResult(
                        false, intent, 0, confirmLockPatternFragment6.mEffectiveUserId);
            }

            public final void onPatternStart() {
                Log.d("ConfirmLockPatternFragment", "onPatternStart");
                ConfirmLockPatternFragment confirmLockPatternFragment =
                        ConfirmLockPatternFragment.this;
                confirmLockPatternFragment.mLockPatternView.removeCallbacks(
                        confirmLockPatternFragment.mClearPatternRunnable);
                ConfirmLockPatternFragment.this
                        .mLockPatternView
                        .getParent()
                        .requestDisallowInterceptTouchEvent(true);
            }

            public final void startVerifyPattern(
                    final LockscreenCredential lockscreenCredential,
                    final Intent intent,
                    final int i) {
                Log.d("ConfirmLockPatternFragment", "startVerifyPattern()");
                ConfirmLockPatternFragment confirmLockPatternFragment =
                        ConfirmLockPatternFragment.this;
                final int i2 = confirmLockPatternFragment.mEffectiveUserId;
                int i3 = confirmLockPatternFragment.mUserId;
                LockPatternChecker.OnVerifyCallback onVerifyCallback =
                        new LockPatternChecker
                                .OnVerifyCallback() { // from class:
                                                      // com.android.settings.password.ConfirmLockPattern$ConfirmLockPatternFragment$4$$ExternalSyntheticLambda0
                            public final void onVerified(
                                    VerifyCredentialResponse verifyCredentialResponse, int i4) {
                                ConfirmLockPattern.ConfirmLockPatternFragment.AnonymousClass4
                                        anonymousClass4 =
                                                ConfirmLockPattern.ConfirmLockPatternFragment
                                                        .AnonymousClass4.this;
                                int i5 = i;
                                Intent intent2 = intent;
                                LockscreenCredential lockscreenCredential2 = lockscreenCredential;
                                int i6 = i2;
                                anonymousClass4.getClass();
                                Log.d("ConfirmLockPatternFragment", "OnVerifyCallback");
                                ConfirmLockPattern.ConfirmLockPatternFragment.this
                                                .mPendingLockCheck =
                                        null;
                                boolean isMatched = verifyCredentialResponse.isMatched();
                                if (isMatched
                                        && ConfirmLockPattern.ConfirmLockPatternFragment.this
                                                .mReturnCredentials) {
                                    if ((i5 & 1) != 0) {
                                        intent2.putExtra(
                                                "gk_pw_handle",
                                                verifyCredentialResponse
                                                        .getGatekeeperPasswordHandle());
                                    } else {
                                        intent2.putExtra(
                                                "hw_auth_token",
                                                verifyCredentialResponse.getGatekeeperHAT());
                                    }
                                    intent2.putExtra(
                                            HostAuth.PASSWORD, (Parcelable) lockscreenCredential2);
                                }
                                ConfirmLockPattern.ConfirmLockPatternFragment.this
                                        .mCredentialCheckResultTracker.setResult(
                                        isMatched, intent2, i4, i6);
                            }
                        };
                confirmLockPatternFragment.mPendingLockCheck =
                        i2 == i3
                                ? LockPatternChecker.verifyCredential(
                                        confirmLockPatternFragment.mLockPatternUtils,
                                        lockscreenCredential,
                                        i3,
                                        i,
                                        onVerifyCallback)
                                : LockPatternChecker.verifyTiedProfileChallenge(
                                        confirmLockPatternFragment.mLockPatternUtils,
                                        lockscreenCredential,
                                        i3,
                                        i,
                                        onVerifyCallback);
            }

            public final void onPatternCellAdded(List list) {}
        }
    }
}

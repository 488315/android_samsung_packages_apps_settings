package com.android.settings.password;

import android.app.RemoteLockscreenValidationResult;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.UserManager;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImeAwareEditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.android.internal.graphics.ColorUtils;
import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.TextViewInputDisabler;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;
import com.android.settings.biometrics.GatekeeperPasswordProvider;
import com.android.settingslib.animation.AppearAnimationUtils;
import com.android.settingslib.animation.DisappearAnimationUtils;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.GlifLayout;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.accounts.HostAuth;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.dar.StreamCipher;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.devicesecurity.PasswordPolicy;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.knox.KnoxConfirmDeviceCredentialBaseFragmentHelper;
import com.samsung.android.settings.knox.KnoxConfirmLockHelper;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.settings.password.LockContentViewInsetsCallback;
import com.samsung.android.settings.password.LockTypePolicy;
import com.samsung.android.settings.password.SecGlifLayout;
import com.samsung.android.util.SemLog;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ConfirmLockPassword extends ConfirmDeviceCredentialBaseActivity {
    public static final int[] DETAIL_TEXTS = {
        R.string.sec_confirm_lock_pin_your_pin_header,
        R.string.sec_confirm_lock_password_your_password_generic,
        R.string.sec_confirm_lock_password_strong_auth_required_reason_restart_device_pin,
        R.string.sec_confirm_lock_password_strong_auth_required_reason_restart_device_password
    };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class InternalActivity extends ConfirmLockPassword {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class KnoxWorkChallengeActivity extends ConfirmLockPassword {}

    @Override // com.android.settings.SettingsActivity, android.app.Activity
    public final Intent getIntent() {
        Intent intent = new Intent(super.getIntent());
        intent.putExtra(":settings:show_fragment", ConfirmLockPasswordFragment.class.getName());
        intent.putExtra(":settings:show_fragment_as_subsetting", true);
        return intent;
    }

    @Override // com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return ConfirmLockPasswordFragment.class.getName().equals(str);
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

    @Override // android.app.Activity
    public final boolean onCreateOptionsMenu(Menu menu) {
        Menu menu2;
        Fragment findFragmentById = getSupportFragmentManager().findFragmentById(R.id.main_content);
        if (findFragmentById == null
                || !(findFragmentById instanceof ConfirmLockPasswordFragment)) {
            return false;
        }
        ConfirmLockPasswordFragment confirmLockPasswordFragment =
                (ConfirmLockPasswordFragment) findFragmentById;
        if (LockUtils.isSupportActionBarButton(confirmLockPasswordFragment.getActivity())) {
            MenuItem add = menu.add(0, 2, 1, R.string.lockpassword_continue_label);
            add.setShowAsAction(2);
            if (confirmLockPasswordFragment.mPasswordEntry == null) {
                add.setEnabled(false);
            } else if (confirmLockPasswordFragment.isEnabledNdigitsPinForConfirm()) {
                add.setEnabled(
                        confirmLockPasswordFragment.mPasswordEntry.getText().length()
                                >= confirmLockPasswordFragment.nDigitsPin);
            } else {
                add.setEnabled(confirmLockPasswordFragment.mPasswordEntry.getText().length() > 0);
            }
            menu.add(0, 1, 0, R.string.sec_lockpattern_tutorial_cancel_label).setShowAsAction(2);
            confirmLockPasswordFragment.mMenu = menu;
            boolean z =
                    confirmLockPasswordFragment.mLockTypePolicy.isDevicePasswordAdminEnabled()
                            && KnoxUtils.isChangeRequested(
                                            confirmLockPasswordFragment.getContext(),
                                            confirmLockPasswordFragment.mUserId)
                                    > 0;
            String str = KnoxUtils.mDeviceType;
            boolean z2 =
                    DualDarManager.isOnDeviceOwnerEnabled()
                            && KnoxUtils.isChangeRequestedForInner(
                                            confirmLockPasswordFragment.getContext())
                                    > 0;
            if ((z || z2) && (menu2 = confirmLockPasswordFragment.mMenu) != null) {
                menu2.findItem(1).setEnabled(false);
            }
        }
        return true;
    }

    @Override // com.android.settings.password.ConfirmDeviceCredentialBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity, android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        Fragment findFragmentById = getSupportFragmentManager().findFragmentById(R.id.main_content);
        if (findFragmentById != null && (findFragmentById instanceof ConfirmLockPasswordFragment)) {
            ConfirmLockPasswordFragment confirmLockPasswordFragment =
                    (ConfirmLockPasswordFragment) findFragmentById;
            if (menuItem.getItemId() != 2) {
                boolean z =
                        confirmLockPasswordFragment.mLockTypePolicy.isDevicePasswordAdminEnabled()
                                && KnoxUtils.isChangeRequested(
                                                confirmLockPasswordFragment.getContext(),
                                                confirmLockPasswordFragment.mUserId)
                                        > 0;
                String str = KnoxUtils.mDeviceType;
                boolean z2 =
                        DualDarManager.isOnDeviceOwnerEnabled()
                                && KnoxUtils.isChangeRequestedForInner(
                                                confirmLockPasswordFragment.getContext())
                                        > 0;
                if (z || z2) {
                    return true;
                }
            }
            if (menuItem.getItemId() != 16908332) {
                if (!LockUtils.isSupportActionBarButton(
                        confirmLockPasswordFragment.getActivity())) {
                    return false;
                }
                int itemId = menuItem.getItemId();
                if (itemId == 2) {
                    confirmLockPasswordFragment.handleNext$3();
                } else if (itemId == 1) {
                    confirmLockPasswordFragment.getActivity().setResult(0);
                    confirmLockPasswordFragment.getActivity().finish();
                }
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
        if (findFragmentById == null
                || !(findFragmentById instanceof ConfirmLockPasswordFragment)) {
            return;
        }
        ConfirmLockPasswordFragment confirmLockPasswordFragment =
                (ConfirmLockPasswordFragment) findFragmentById;
        KnoxConfirmLockHelper knoxConfirmLockHelper =
                confirmLockPasswordFragment
                        .mKnoxConfirmDeviceCredentialBaseFragmentHelper
                        .mKnoxConfirmLockHelper;
        if (knoxConfirmLockHelper != null) {
            knoxConfirmLockHelper.onWindowFocusChanged(z);
        }
        KnoxConfirmLockHelper knoxConfirmLockHelper2 =
                confirmLockPasswordFragment
                        .mKnoxConfirmDeviceCredentialBaseFragmentHelper
                        .mKnoxConfirmLockHelper;
        if (knoxConfirmLockHelper2 != null
                ? knoxConfirmLockHelper2.setCredentialCheckResultTrackerIfNeeded(z)
                : false) {
            confirmLockPasswordFragment.mCredentialCheckResultTracker.setListener(
                    confirmLockPasswordFragment);
        }
        if (z) {
            if (LockUtils.isApplyingTabletGUI(confirmLockPasswordFragment.getActivity())) {
                confirmLockPasswordFragment.getActivity().getWindow().setCloseOnTouchOutside(false);
            }
            confirmLockPasswordFragment.mPasswordEntry.post(
                    new ConfirmLockPassword$ConfirmLockPasswordFragment$$ExternalSyntheticLambda0(
                            0, confirmLockPasswordFragment));
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class ConfirmLockPasswordFragment extends ConfirmDeviceCredentialBaseFragment
            implements View.OnClickListener,
                    TextView.OnEditorActionListener,
                    CredentialCheckResultTracker.Listener,
                    SaveAndFinishWorker.Listener,
                    RemoteLockscreenValidationFragment.Listener,
                    TextWatcher {
        public static final /* synthetic */ int $r8$clinit = 0;
        public AppearAnimationUtils mAppearAnimationUtils;
        public View mBottomView;
        public CharSequence mCheckBoxLabel;
        public Button mContinueButton;
        public CountDownTimer mCountdownTimer;
        public CredentialCheckResultTracker mCredentialCheckResultTracker;
        public boolean mExternal;
        public boolean mFromAppLock;
        public InputMethodManager mImm;
        public boolean mIsAlpha;
        public boolean mIsManagedProfile;
        public boolean mIsPasswordShown;
        public Menu mMenu;
        public ImeAwareEditText mPasswordEntry;
        public TextViewInputDisabler mPasswordEntryInputDisabler;
        public ImageButton mPasswordShowButton;
        public AsyncTask mPendingLockCheck;
        public FooterButton mPrimaryButton;
        public int nDigitsPin;
        public boolean mDisappearing = false;
        public boolean mUnlockRecovery = false;
        public boolean mAppLock_isPin = false;
        public int mPasswordMaxLength = 256;
        public boolean mIsPasswordMaxLength = false;
        public CharSequence mDetailMsg = null;
        public final AnonymousClass1 mMaxLengthFilter = new AnonymousClass1();
        public boolean mTwoFactorBiometricConfirmed = false;
        public boolean mWaitingForTwoFactorConfirmation = false;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.password.ConfirmLockPassword$ConfirmLockPasswordFragment$1, reason: invalid class name */
        public final class AnonymousClass1 implements InputFilter {
            public AnonymousClass1() {}

            @Override // android.text.InputFilter
            public final CharSequence filter(
                    CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
                if (ConfirmLockPasswordFragment.this.mPasswordEntry != null
                        && spanned.length() > 0) {
                    int length = ConfirmLockPasswordFragment.this.mPasswordEntry.length();
                    ConfirmLockPasswordFragment confirmLockPasswordFragment =
                            ConfirmLockPasswordFragment.this;
                    int i5 = confirmLockPasswordFragment.mPasswordMaxLength;
                    if (length >= i5) {
                        if (confirmLockPasswordFragment.mIsPasswordMaxLength) {
                            return null;
                        }
                        confirmLockPasswordFragment.mIsPasswordMaxLength = true;
                        ConfirmLockPasswordFragment.this.mGlifLayout.setDescriptionText(
                                confirmLockPasswordFragment.getString(
                                        confirmLockPasswordFragment.mIsAlpha
                                                ? R.string
                                                        .sec_choose_lock_password_password_too_long
                                                : R.string.sec_choose_lock_pin_pin_too_long,
                                        Integer.valueOf(i5)));
                        ConfirmLockPasswordFragment.this.mGlifLayout.mDetailsTextView.post(
                                new ConfirmLockPassword$ConfirmLockPasswordFragment$$ExternalSyntheticLambda0(
                                        1, this));
                        return null;
                    }
                }
                ConfirmLockPasswordFragment.this.mIsPasswordMaxLength = false;
                return null;
            }
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment,
                  // com.samsung.android.settings.knox.KnoxConfirmDeviceCredentialBaseFragmentHelper.Callback
        public final void adjustPasswordViewWithIme(View view, int i) {
            ScrollView scrollView = (ScrollView) view.findViewById(R.id.pwdLayoutScrollView);
            scrollView.setPadding(
                    scrollView.getPaddingLeft(),
                    i,
                    scrollView.getPaddingRight(),
                    scrollView.getPaddingBottom());
        }

        @Override // android.text.TextWatcher
        public final void afterTextChanged(Editable editable) {
            if (isEnabledNdigitsPinForConfirm()) {
                this.mContinueButton.setEnabled(
                        this.mPasswordEntry.getText().length() > this.nDigitsPin);
                Menu menu = this.mMenu;
                if (menu != null) {
                    menu.findItem(2)
                            .setEnabled(this.mPasswordEntry.getText().length() > this.nDigitsPin);
                }
                FooterButton footerButton = this.mPrimaryButton;
                if (footerButton != null) {
                    footerButton.setEnabled(
                            this.mPasswordEntry.getText().length() > this.nDigitsPin);
                }
            } else {
                this.mContinueButton.setEnabled(this.mPasswordEntry.getText().length() > 0);
                Menu menu2 = this.mMenu;
                if (menu2 != null) {
                    menu2.findItem(2).setEnabled(this.mPasswordEntry.getText().length() > 0);
                }
                FooterButton footerButton2 = this.mPrimaryButton;
                if (footerButton2 != null) {
                    footerButton2.setEnabled(this.mPasswordEntry.getText().length() > 0);
                }
            }
            if (isEnabledNdigitsPinForConfirm() && editable.length() == this.nDigitsPin) {
                handleNext$3();
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
                Log.d("ConfirmLockPasswordFragment", "interceptAuthenticationSucceededIfNeeded");
            } else {
                this.mCredentialCheckResultTracker.setResult(
                        true, new Intent(), 0, this.mEffectiveUserId);
            }
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment,
                  // com.samsung.android.settings.knox.KnoxConfirmDeviceCredentialBaseFragmentHelper.Callback
        public final void disableConfirmCredentialCallback() {
            this.mPasswordEntry.setEnabled(false);
            this.mPasswordEntry.setFocusable(false);
            this.mPasswordEntry.setFocusableInTouchMode(false);
            this.mKnoxConfirmDeviceCredentialBaseFragmentHelper.needToUpdateErrorMessage(
                    this.mLockPatternUtils.getCurrentFailedPasswordAttempts(this.mEffectiveUserId),
                    this.mGlifLayout.mDetailsTextView);
            this.mPasswordEntryInputDisabler.setInputEnabled(false);
            this.mImm.hideSoftInputFromWindow(this.mPasswordEntry.getWindowToken(), 0);
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment,
                  // com.samsung.android.settings.knox.KnoxConfirmDeviceCredentialBaseFragmentHelper.Callback
        public final void enableConfirmCredentialCallback() {
            this.mTwoFactorBiometricConfirmed = true;
            this.mWaitingForTwoFactorConfirmation = false;
            if (this.mLockPatternUtils.getLockoutAttemptDeadline(this.mEffectiveUserId) == 0) {
                refreshLockScreen();
                this.mPasswordEntry.setEnabled(true);
                this.mPasswordEntry.setFocusable(true);
                this.mPasswordEntry.setFocusableInTouchMode(true);
                this.mPasswordEntry.requestFocus();
                this.mKnoxConfirmDeviceCredentialBaseFragmentHelper.needToUpdateErrorMessage(
                        this.mLockPatternUtils.getCurrentFailedPasswordAttempts(
                                this.mEffectiveUserId),
                        this.mGlifLayout.mDetailsTextView);
                this.mPasswordEntryInputDisabler.setInputEnabled(true);
                this.mPasswordEntry.scheduleShowSoftInput();
            }
        }

        public final String getDefaultDetails() {
            String str = KnoxUtils.mDeviceType;
            if (DualDarManager.isOnDeviceOwnerEnabled()) {
                if (KnoxUtils.isChangeRequested(getContext(), this.mUserId) > 0) {
                    return this.mIsAlpha
                            ? getString(R.string.confirm_lockpassword_your_first_password_header)
                            : getString(R.string.confirm_lockpassword_your_first_pin_header);
                }
                if (KnoxUtils.isChangeRequestedForInner(getContext()) > 0) {
                    return this.mIsAlpha
                            ? getString(R.string.confirm_lockpassword_your_second_password_header)
                            : getString(R.string.confirm_lockpassword_your_second_pin_header);
                }
            }
            KnoxConfirmLockHelper knoxConfirmLockHelper =
                    this.mKnoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
            int defaultDetails =
                    knoxConfirmLockHelper != null ? knoxConfirmLockHelper.getDefaultDetails() : 0;
            if (defaultDetails > 0) {
                return getString(defaultDetails);
            }
            if (SemPersonaManager.isDoEnabled(this.mUserId)
                    && KnoxUtils.isChangeRequested(getContext(), this.mUserId) > 0) {
                return this.mIsAlpha
                        ? getString(
                                R.string.sec_knox_lockpassword_strong_auth_required_work_password)
                        : getString(R.string.sec_knox_lockpassword_strong_auth_required_work_pin);
            }
            if (this.mFrp) {
                if (Utils.isTablet()) {
                    return getString(
                            this.mIsAlpha
                                    ? R.string
                                            .sec_lockpassword_confirm_your_password_details_frp_tablet
                                    : R.string
                                            .sec_lockpassword_confirm_your_pin_details_frp_tablet);
                }
                return getString(
                        this.mIsAlpha
                                ? R.string.sec_lockpassword_confirm_your_password_details_frp
                                : R.string.sec_lockpassword_confirm_your_pin_details_frp);
            }
            if (this.mRepairMode) {
                return this.mIsAlpha
                        ? getString(R.string.lockpassword_confirm_repair_mode_password_details)
                        : getString(R.string.lockpassword_confirm_repair_mode_pin_details);
            }
            if (this.mRemoteValidation) {
                return getContext()
                        .getString(
                                this.mIsAlpha
                                        ? R.string
                                                .sec_lockpassword_remote_validation_password_details
                                        : R.string.sec_lockpassword_remote_validation_pin_details);
            }
            return getString(
                    ConfirmLockPassword.DETAIL_TEXTS[
                            ((isStrongAuthRequired$1() ? 1 : 0) << 1) + (this.mIsAlpha ? 1 : 0)]);
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
                    ? this.mIsAlpha
                            ? "Settings.WORK_PROFILE_LAST_PASSWORD_ATTEMPT_BEFORE_WIPE"
                            : "Settings.WORK_PROFILE_LAST_PIN_ATTEMPT_BEFORE_WIPE"
                    : PeripheralBarcodeConstants.Symbology.UNDEFINED;
        }

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 30;
        }

        public final void handleAttemptLockout(long j) {
            this.mPasswordEntry.setEnabled(false);
            this.mPasswordEntry.setFocusable(false);
            ImageButton imageButton = this.mPasswordShowButton;
            if (imageButton != null) {
                imageButton.setEnabled(false);
            }
            this.mContinueButton.setEnabled(false);
            Menu menu = this.mMenu;
            if (menu != null) {
                menu.findItem(2).setEnabled(false);
            }
            FooterButton footerButton = this.mPrimaryButton;
            if (footerButton != null) {
                footerButton.setEnabled(false);
            }
            this.mHandler.removeCallbacks(this.mResetErrorRunnable);
            this.mCountdownTimer =
                    new CountDownTimer(
                            j
                                    - SystemClock
                                            .elapsedRealtime()) { // from class:
                                                                  // com.android.settings.password.ConfirmLockPassword.ConfirmLockPasswordFragment.3
                        @Override // android.os.CountDownTimer
                        public final void onFinish() {
                            ConfirmLockPasswordFragment confirmLockPasswordFragment =
                                    ConfirmLockPasswordFragment.this;
                            int i = ConfirmLockPasswordFragment.$r8$clinit;
                            confirmLockPasswordFragment.updatePasswordEntry();
                            ConfirmLockPasswordFragment confirmLockPasswordFragment2 =
                                    ConfirmLockPasswordFragment.this;
                            confirmLockPasswordFragment2.mErrorTextView.setText(
                                    confirmLockPasswordFragment2.getDefaultDetails());
                            KnoxConfirmLockHelper knoxConfirmLockHelper =
                                    ConfirmLockPasswordFragment.this
                                            .mKnoxConfirmDeviceCredentialBaseFragmentHelper
                                            .mKnoxConfirmLockHelper;
                            if (knoxConfirmLockHelper != null) {
                                knoxConfirmLockHelper.updateState();
                            }
                            ConfirmLockPasswordFragment confirmLockPasswordFragment3 =
                                    ConfirmLockPasswordFragment.this;
                            confirmLockPasswordFragment3
                                    .mKnoxConfirmDeviceCredentialBaseFragmentHelper
                                    .needToUpdateErrorMessage(
                                            confirmLockPasswordFragment3.mLockPatternUtils
                                                    .getCurrentFailedPasswordAttempts(
                                                            confirmLockPasswordFragment3
                                                                    .mEffectiveUserId),
                                            confirmLockPasswordFragment3
                                                    .mGlifLayout
                                                    .mDetailsTextView);
                            ConfirmLockPasswordFragment confirmLockPasswordFragment4 =
                                    ConfirmLockPasswordFragment.this;
                            confirmLockPasswordFragment4.updateErrorMessage(
                                    confirmLockPasswordFragment4.mLockPatternUtils
                                            .getCurrentFailedPasswordAttempts(
                                                    confirmLockPasswordFragment4.mEffectiveUserId));
                            ImageButton imageButton2 =
                                    ConfirmLockPasswordFragment.this.mPasswordShowButton;
                            if (imageButton2 != null) {
                                imageButton2.setEnabled(true);
                            }
                        }

                        @Override // android.os.CountDownTimer
                        public final void onTick(long j2) {
                            ConfirmLockPasswordFragment.this.showError(
                                    LockUtils.updateAttemptLockoutDesc(
                                            ConfirmLockPasswordFragment.this
                                                    .getActivity()
                                                    .getApplicationContext(),
                                            j2),
                                    0L);
                        }
                    }.start();
            updatePasswordEntry();
        }

        public final void handleNext$3() {
            if (this.mPendingLockCheck != null || this.mDisappearing) {
                return;
            }
            Editable text = this.mPasswordEntry.getText();
            if (TextUtils.isEmpty(text)) {
                return;
            }
            final LockscreenCredential createPassword =
                    this.mIsAlpha
                            ? LockscreenCredential.createPassword(text)
                            : LockscreenCredential.createPin(text);
            if (this.mLockTypePolicy.isEnterpriseUser()) {
                this.mPasswordEntry.setText(ApnSettings.MVNO_NONE);
            }
            Editable text2 = this.mPasswordEntry.getText();
            if (text2 != null) {
                for (int i = 0; i < text2.length(); i++) {
                    char charAt = text2.charAt(i);
                    if (charAt < ' ' || charAt > 127) {
                        KnoxConfirmLockHelper knoxConfirmLockHelper =
                                this.mKnoxConfirmDeviceCredentialBaseFragmentHelper
                                        .mKnoxConfirmLockHelper;
                        showError(
                                getText(
                                        knoxConfirmLockHelper != null
                                                ? knoxConfirmLockHelper.getErrorMessage()
                                                : R.string.sec_lockpassword_need_to_unlock_wrong),
                                3000L);
                        return;
                    }
                }
            }
            this.mPasswordEntryInputDisabler.setInputEnabled(false);
            if (this.mRemoteValidation) {
                validateGuess(createPassword);
                updateRemoteLockscreenValidationViews();
                updatePasswordEntry();
                return;
            }
            final Intent intent = new Intent();
            if (this.mReturnGatekeeperPassword) {
                if (getActivity() instanceof InternalActivity) {
                    startVerifyPassword(createPassword, intent, 1);
                    return;
                }
            } else if (!this.mForceVerifyPath) {
                Log.d("ConfirmLockPasswordFragment", "startCheckPassword()");
                final int i2 = this.mEffectiveUserId;
                this.mPendingLockCheck =
                        LockPatternChecker.checkCredential(
                                this.mLockPatternUtils,
                                createPassword,
                                i2,
                                new LockPatternChecker
                                        .OnCheckCallback() { // from class:
                                                             // com.android.settings.password.ConfirmLockPassword.ConfirmLockPasswordFragment.2
                                    public final void onChecked(boolean z, int i3) {
                                        Log.d("ConfirmLockPasswordFragment", "onChecked()");
                                        ConfirmLockPasswordFragment confirmLockPasswordFragment =
                                                ConfirmLockPasswordFragment.this;
                                        confirmLockPasswordFragment.mPendingLockCheck = null;
                                        if (z
                                                && (confirmLockPasswordFragment.getActivity()
                                                        instanceof InternalActivity)
                                                && ConfirmLockPasswordFragment.this
                                                        .mReturnCredentials) {
                                            intent.putExtra(
                                                    HostAuth.PASSWORD, (Parcelable) createPassword);
                                        }
                                        ConfirmLockPasswordFragment.this
                                                .mCredentialCheckResultTracker.setResult(
                                                z, intent, i3, i2);
                                    }
                                });
                return;
            } else if (getActivity() instanceof InternalActivity) {
                startVerifyPassword(
                        createPassword, intent, this.mRequestWriteRepairModePassword ? 2 : 0);
                return;
            }
            this.mCredentialCheckResultTracker.setResult(false, intent, 0, this.mEffectiveUserId);
        }

        public final boolean isEnabledNdigitsPinForConfirm() {
            if (this.nDigitsPin > 0) {
                return !this.mIsAlpha;
            }
            return false;
        }

        @Override // com.android.settings.password.SaveAndFinishWorker.Listener
        public final void onChosenLockSaveFinished(Intent intent, boolean z) {
            Log.i(
                    "ConfirmLockPasswordFragment",
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

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            if (view.getId() == R.id.next_button) {
                handleNext$3();
            } else if (view.getId() == R.id.cancel_button) {
                getActivity().setResult(0);
                getActivity().finish();
            }
            if (view.getId() == R.id.password_show_btn) {
                int selectionEnd = this.mPasswordEntry.getSelectionEnd();
                if (this.mIsPasswordShown) {
                    this.mPasswordShowButton.setForeground(
                            getResources()
                                    .getDrawable(
                                            R.drawable.sec_lock_setting_btn_password_hide_mtrl,
                                            null));
                    this.mPasswordEntry.setTransformationMethod(
                            PasswordTransformationMethod.getInstance());
                    this.mPasswordShowButton.setContentDescription(
                            getString(R.string.sec_lock_screen_password_show_button));
                    this.mIsPasswordShown = false;
                } else {
                    this.mPasswordShowButton.setForeground(
                            getResources()
                                    .getDrawable(
                                            R.drawable.sec_lock_setting_btn_password_show_mtrl,
                                            null));
                    this.mPasswordEntry.setTransformationMethod((TransformationMethod) null);
                    this.mPasswordShowButton.setContentDescription(
                            getString(R.string.sec_lock_screen_password_hide_button));
                    this.mIsPasswordShown = true;
                }
                KnoxConfirmDeviceCredentialBaseFragmentHelper
                        knoxConfirmDeviceCredentialBaseFragmentHelper =
                                this.mKnoxConfirmDeviceCredentialBaseFragmentHelper;
                ImageButton imageButton = this.mPasswordShowButton;
                boolean z = this.mIsPasswordShown;
                KnoxConfirmLockHelper knoxConfirmLockHelper =
                        knoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
                if (knoxConfirmLockHelper != null) {
                    knoxConfirmLockHelper.setShowPwdImgColor(imageButton, z);
                }
                try {
                    this.mPasswordEntry.setSelection(selectionEnd);
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment,
                  // com.android.settingslib.core.lifecycle.ObservableFragment,
                  // androidx.fragment.app.Fragment
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            Intent intent = getActivity().getIntent();
            if (intent != null) {
                this.mUnlockRecovery = intent.getBooleanExtra("unlock_recovery", false);
                this.mAppLock_isPin = intent.getBooleanExtra("applock_mQuality", false);
                this.mExternal =
                        intent.getBooleanExtra(
                                "com.android.settings.ConfirmCredentials.showWhenLocked", false);
                this.mFromAppLock = intent.getBooleanExtra("from_applock", false);
            }
            this.nDigitsPin =
                    this.mLockPatternUtils.isAutoPinConfirmEnabled(this.mUserId)
                            ? this.mLockPatternUtils.getPinLength(this.mUserId)
                            : Settings.Secure.getInt(
                                    getActivity().getContentResolver(), "n_digits_pin_enabled", -1);
            LockTypePolicy lockTypePolicy = this.mLockTypePolicy;
            if (lockTypePolicy.mIsKnoxId) {
                if (lockTypePolicy.mIsSecureFolderId && this.mForFingerprint) {
                    this.nDigitsPin =
                            this.mLockPatternUtils.isAutoPinConfirmEnabled(0)
                                    ? this.mLockPatternUtils.getPinLength(0)
                                    : Settings.Secure.getIntForUser(
                                            getActivity().getContentResolver(),
                                            "n_digits_pin_enabled",
                                            -1,
                                            0);
                } else {
                    this.nDigitsPin =
                            this.mLockPatternUtils.isAutoPinConfirmEnabled(this.mEffectiveUserId)
                                    ? this.mLockPatternUtils.getPinLength(this.mEffectiveUserId)
                                    : Settings.Secure.getIntForUser(
                                            getActivity().getContentResolver(),
                                            "n_digits_pin_enabled",
                                            -1,
                                            this.mEffectiveUserId);
                }
            }
            SemLog.d(
                    "ConfirmLockPasswordFragment.SDP",
                    "onCreate - StreamCipher initialized with handle("
                            + StreamCipher.getInstance().issueKeyStream()
                            + ")");
            if (bundle != null) {
                this.mTwoFactorBiometricConfirmed =
                        bundle.getBoolean("two_factor_biometric_confirmed", false);
                this.mWaitingForTwoFactorConfirmation =
                        bundle.getBoolean("waiting_for_two_factor_confirmation", false);
            }
            KnoxConfirmLockHelper knoxConfirmLockHelper =
                    this.mKnoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
            if (knoxConfirmLockHelper != null) {
                knoxConfirmLockHelper.onCreate(bundle);
            }
        }

        @Override // androidx.fragment.app.Fragment
        public final View onCreateView(
                LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            View inflate;
            View findViewById;
            final int i = 1;
            int keyguardStoredPasswordQuality =
                    this.mLockPatternUtils.getKeyguardStoredPasswordQuality(this.mEffectiveUserId);
            ConfirmLockPassword confirmLockPassword = (ConfirmLockPassword) getActivity();
            final int i2 = 0;
            if (this.mFrp || this.mRemoteValidation) {
                inflate =
                        layoutInflater.inflate(
                                R.layout.sec_setup_confirm_lock_password, viewGroup, false);
            } else {
                inflate =
                        layoutInflater.inflate(
                                LockUtils.isApplyingTabletGUI(confirmLockPassword)
                                        ? R.layout.sec_confirm_lock_password_tablet
                                        : R.layout.sec_confirm_lock_password,
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
            ImeAwareEditText findViewById2 = inflate.findViewById(R.id.password_entry);
            this.mPasswordEntry = findViewById2;
            findViewById2.setOnEditorActionListener(this);
            this.mPasswordEntry.requestFocus();
            if (this.mRemoteValidation) {
                this.mIsAlpha = this.mRemoteLockscreenValidationSession.getLockType() == 0;
                this.mGlifLayout.setProgressBarShown(false);
            } else {
                this.mIsAlpha =
                        262144 == keyguardStoredPasswordQuality
                                || 327680 == keyguardStoredPasswordQuality
                                || 393216 == keyguardStoredPasswordQuality
                                || 524288 == keyguardStoredPasswordQuality;
            }
            this.mErrorTextView = this.mGlifLayout.mDetailsTextView;
            if (this.mIsAlpha || Rune.isSamsungDexMode(getContext())) {
                if (Utils.isRTL(getActivity())) {
                    this.mPasswordEntry.setGravity(8388613);
                    ImeAwareEditText imeAwareEditText = this.mPasswordEntry;
                    imeAwareEditText.setPaddingRelative(
                            imeAwareEditText.getPaddingEnd(),
                            this.mPasswordEntry.getPaddingTop(),
                            (int)
                                    getResources()
                                            .getDimension(
                                                    R.dimen.sec_lock_password_edittext_padding_end),
                            this.mPasswordEntry.getPaddingBottom());
                } else {
                    this.mPasswordEntry.setGravity(8388611);
                    ImeAwareEditText imeAwareEditText2 = this.mPasswordEntry;
                    imeAwareEditText2.setPadding(
                            imeAwareEditText2.getPaddingLeft(),
                            this.mPasswordEntry.getPaddingTop(),
                            (int)
                                    getResources()
                                            .getDimension(
                                                    R.dimen.sec_lock_password_edittext_padding_end),
                            this.mPasswordEntry.getPaddingBottom());
                }
                KnoxConfirmDeviceCredentialBaseFragmentHelper
                        knoxConfirmDeviceCredentialBaseFragmentHelper2 =
                                this.mKnoxConfirmDeviceCredentialBaseFragmentHelper;
                ImeAwareEditText imeAwareEditText3 = this.mPasswordEntry;
                KnoxConfirmLockHelper knoxConfirmLockHelper2 =
                        knoxConfirmDeviceCredentialBaseFragmentHelper2.mKnoxConfirmLockHelper;
                if (knoxConfirmLockHelper2 != null) {
                    knoxConfirmLockHelper2.setPasswordEntry(imeAwareEditText3);
                }
            }
            this.mPasswordMaxLength = 256;
            this.mPasswordEntry.setFilters(
                    new InputFilter[] {
                        this.mMaxLengthFilter, new InputFilter.LengthFilter(this.mPasswordMaxLength)
                    });
            this.mPasswordEntryInputDisabler = new TextViewInputDisabler(this.mPasswordEntry);
            this.mImm = (InputMethodManager) getActivity().getSystemService("input_method");
            this.mIsManagedProfile =
                    UserManager.get(getActivity()).isManagedProfile(this.mEffectiveUserId);
            Intent intent = getActivity().getIntent();
            if (intent != null) {
                CharSequence charSequenceExtra =
                        intent.getCharSequenceExtra(
                                "com.android.settings.ConfirmCredentials.header");
                CharSequence charSequenceExtra2 =
                        intent.getCharSequenceExtra(
                                "com.android.settings.ConfirmCredentials.details");
                if (TextUtils.isEmpty(charSequenceExtra) && this.mIsManagedProfile) {
                    charSequenceExtra =
                            this.mDevicePolicyManager.getOrganizationNameForUser(this.mUserId);
                }
                if (TextUtils.isEmpty(charSequenceExtra)) {
                    charSequenceExtra =
                            this.mFrp
                                    ? this.mIsAlpha
                                            ? getString(
                                                    R.string.sec_confirm_lock_password_header_frp)
                                            : getString(R.string.sec_confirm_lock_pin_header_frp)
                                    : this.mRepairMode
                                            ? this.mIsAlpha
                                                    ? getString(
                                                            R.string
                                                                    .lockpassword_confirm_repair_mode_password_header)
                                                    : getString(
                                                            R.string
                                                                    .lockpassword_confirm_repair_mode_pin_header)
                                            : this.mRemoteValidation
                                                    ? getString(
                                                            R.string
                                                                    .sec_lockpassword_remote_validation_header)
                                                    : this.mIsManagedProfile
                                                            ? this.mIsAlpha
                                                                    ? this.mDevicePolicyManager
                                                                            .getResources()
                                                                            .getString(
                                                                                    "Settings.CONFIRM_WORK_PROFILE_PASSWORD_HEADER",
                                                                                    new Supplier(
                                                                                            this) { // from class: com.android.settings.password.ConfirmLockPassword$ConfirmLockPasswordFragment$$ExternalSyntheticLambda4
                                                                                        public
                                                                                        final /* synthetic */
                                                                                        ConfirmLockPassword
                                                                                                        .ConfirmLockPasswordFragment
                                                                                                f$0;

                                                                                        {
                                                                                            this
                                                                                                            .f$0 =
                                                                                                    this;
                                                                                        }

                                                                                        @Override // java.util.function.Supplier
                                                                                        public final
                                                                                        Object
                                                                                                get() {
                                                                                            int i3 =
                                                                                                    i2;
                                                                                            ConfirmLockPassword
                                                                                                            .ConfirmLockPasswordFragment
                                                                                                    confirmLockPasswordFragment =
                                                                                                            this
                                                                                                                    .f$0;
                                                                                            switch (i3) {
                                                                                                case 0:
                                                                                                    int
                                                                                                            i4 =
                                                                                                                    ConfirmLockPassword
                                                                                                                            .ConfirmLockPasswordFragment
                                                                                                                            .$r8$clinit;
                                                                                                    return confirmLockPasswordFragment
                                                                                                            .getString(
                                                                                                                    R
                                                                                                                            .string
                                                                                                                            .lockpassword_confirm_your_work_password_header);
                                                                                                default:
                                                                                                    int
                                                                                                            i5 =
                                                                                                                    ConfirmLockPassword
                                                                                                                            .ConfirmLockPasswordFragment
                                                                                                                            .$r8$clinit;
                                                                                                    return confirmLockPasswordFragment
                                                                                                            .getString(
                                                                                                                    R
                                                                                                                            .string
                                                                                                                            .lockpassword_confirm_your_work_pin_header);
                                                                                            }
                                                                                        }
                                                                                    })
                                                                    : this.mDevicePolicyManager
                                                                            .getResources()
                                                                            .getString(
                                                                                    "Settings.CONFIRM_WORK_PROFILE_PIN_HEADER",
                                                                                    new Supplier(
                                                                                            this) { // from class: com.android.settings.password.ConfirmLockPassword$ConfirmLockPasswordFragment$$ExternalSyntheticLambda4
                                                                                        public
                                                                                        final /* synthetic */
                                                                                        ConfirmLockPassword
                                                                                                        .ConfirmLockPasswordFragment
                                                                                                f$0;

                                                                                        {
                                                                                            this
                                                                                                            .f$0 =
                                                                                                    this;
                                                                                        }

                                                                                        @Override // java.util.function.Supplier
                                                                                        public final
                                                                                        Object
                                                                                                get() {
                                                                                            int i3 =
                                                                                                    i;
                                                                                            ConfirmLockPassword
                                                                                                            .ConfirmLockPasswordFragment
                                                                                                    confirmLockPasswordFragment =
                                                                                                            this
                                                                                                                    .f$0;
                                                                                            switch (i3) {
                                                                                                case 0:
                                                                                                    int
                                                                                                            i4 =
                                                                                                                    ConfirmLockPassword
                                                                                                                            .ConfirmLockPasswordFragment
                                                                                                                            .$r8$clinit;
                                                                                                    return confirmLockPasswordFragment
                                                                                                            .getString(
                                                                                                                    R
                                                                                                                            .string
                                                                                                                            .lockpassword_confirm_your_work_password_header);
                                                                                                default:
                                                                                                    int
                                                                                                            i5 =
                                                                                                                    ConfirmLockPassword
                                                                                                                            .ConfirmLockPasswordFragment
                                                                                                                            .$r8$clinit;
                                                                                                    return confirmLockPasswordFragment
                                                                                                            .getString(
                                                                                                                    R
                                                                                                                            .string
                                                                                                                            .lockpassword_confirm_your_work_pin_header);
                                                                                            }
                                                                                        }
                                                                                    })
                                                            : this.mIsAlpha
                                                                    ? getString(
                                                                            R.string
                                                                                    .sec_confirm_lock_password_your_password_header)
                                                                    : getString(
                                                                            R.string
                                                                                    .sec_lockpassword_confirm_your_pin_header);
                }
                if (TextUtils.isEmpty(charSequenceExtra2)) {
                    charSequenceExtra2 = getDefaultDetails();
                }
                KnoxConfirmDeviceCredentialBaseFragmentHelper
                        knoxConfirmDeviceCredentialBaseFragmentHelper3 =
                                this.mKnoxConfirmDeviceCredentialBaseFragmentHelper;
                boolean z = this.mIsAlpha;
                String charSequence = charSequenceExtra.toString();
                KnoxConfirmLockHelper knoxConfirmLockHelper3 =
                        knoxConfirmDeviceCredentialBaseFragmentHelper3.mKnoxConfirmLockHelper;
                if (knoxConfirmLockHelper3 != null) {
                    charSequence = knoxConfirmLockHelper3.getDefaultHeader(charSequence, z);
                }
                this.mGlifLayout.setHeaderText(charSequence);
                if (!this.mIsManagedProfile || SemPersonaManager.isKnoxId(this.mEffectiveUserId)) {
                    this.mGlifLayout.setDescriptionText(charSequenceExtra2);
                } else {
                    this.mGlifLayout.mDetailsTextView.setVisibility(8);
                }
                this.mCheckBoxLabel =
                        intent.getCharSequenceExtra("android.app.extra.CHECKBOX_LABEL");
                this.mDetailMsg = charSequenceExtra2;
            }
            int inputType = this.mPasswordEntry.getInputType();
            if (this.mIsAlpha) {
                this.mPasswordEntry.setInputType(inputType);
                this.mPasswordEntry.setContentDescription(
                        getContext().getString(R.string.sec_unlock_set_unlock_password_title));
            } else {
                this.mPasswordEntry.setInputType(18);
                this.mPasswordEntry.setContentDescription(
                        getContext().getString(R.string.sec_unlock_set_unlock_pin_title));
            }
            new AppearAnimationUtils(
                    getContext(),
                    220L,
                    2.0f,
                    1.0f,
                    AnimationUtils.loadInterpolator(
                            getContext(), android.R.interpolator.linear_out_slow_in));
            new DisappearAnimationUtils(
                    getContext(),
                    AnimationUtils.loadInterpolator(
                            getContext(), android.R.interpolator.fast_out_linear_in));
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
            inflate.findViewById(R.id.cancel_button).setOnClickListener(this);
            Button button = (Button) inflate.findViewById(R.id.next_button);
            this.mContinueButton = button;
            button.setOnClickListener(this);
            this.mContinueButton.setEnabled(false);
            this.mBottomView = inflate.findViewById(R.id.bottomView);
            boolean z2 =
                    this.mLockTypePolicy.isDevicePasswordAdminEnabled()
                            && KnoxUtils.isChangeRequested(getContext(), this.mUserId) > 0;
            String str = KnoxUtils.mDeviceType;
            boolean z3 =
                    DualDarManager.isOnDeviceOwnerEnabled()
                            && KnoxUtils.isChangeRequestedForInner(getContext()) > 0;
            if (z2 || z3) {
                inflate.findViewById(R.id.cancel_button).setEnabled(false);
                Menu menu = this.mMenu;
                if (menu != null) {
                    menu.findItem(1).setEnabled(false);
                }
            }
            this.mPasswordEntry.addTextChangedListener(this);
            this.mPasswordEntry.setImeOptions(33554432);
            this.mPasswordEntry.setPrivateImeOptions(
                    "disableToolbar=true;lockScreenPasswordField=true;");
            if (isEnabledNdigitsPinForConfirm()) {
                this.mPasswordEntry.setPrivateImeOptions(
                        "disableToolbar=true;disableEnterKey=true;lockScreenPasswordField=true;");
            }
            if (this.mIsManagedProfile
                    && !SemPersonaManager.isKnoxId(this.mEffectiveUserId)
                    && this.mExternal) {
                this.mGlifLayout.mHeaderTextView.setVisibility(0);
                this.mGlifLayout.mIcon.setVisibility(0);
            }
            if (this.mFromAppLock) {
                if (this.mAppLock_isPin) {
                    this.mIsAlpha = false;
                } else {
                    this.mIsAlpha = true;
                }
            }
            ImageButton imageButton = (ImageButton) inflate.findViewById(R.id.password_show_btn);
            this.mPasswordShowButton = imageButton;
            if (imageButton != null) {
                if (this.mIsAlpha || Rune.isSamsungDexMode(getContext())) {
                    this.mPasswordShowButton.setVisibility(0);
                }
                if (bundle != null) {
                    this.mIsPasswordShown = bundle.getBoolean("password_shown");
                }
                if (this.mIsPasswordShown) {
                    this.mPasswordShowButton.setForeground(
                            getResources()
                                    .getDrawable(
                                            R.drawable.sec_lock_setting_btn_password_show_mtrl,
                                            null));
                    this.mPasswordEntry.setTransformationMethod((TransformationMethod) null);
                    this.mPasswordShowButton.setContentDescription(
                            getString(R.string.sec_lock_screen_password_hide_button));
                } else {
                    this.mPasswordShowButton.setForeground(
                            getResources()
                                    .getDrawable(
                                            R.drawable.sec_lock_setting_btn_password_hide_mtrl,
                                            null));
                    this.mPasswordEntry.setTransformationMethod(
                            PasswordTransformationMethod.getInstance());
                    this.mPasswordShowButton.setContentDescription(
                            getString(R.string.sec_lock_screen_password_show_button));
                }
                this.mPasswordShowButton.setImageTintList(
                        ColorStateList.valueOf(getResources().getColor(R.color.body_text_color)));
                int colorFromAttribute = LockUtils.getColorFromAttribute(getContext());
                Context context = getContext();
                ImageButton imageButton2 = this.mPasswordShowButton;
                PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
                if (context != null && imageButton2 != null) {
                    imageButton2.setForegroundTintList(
                            ColorStateList.valueOf(
                                    ColorUtils.setAlphaComponent(colorFromAttribute, 160)));
                    imageButton2.setForegroundTintMode(mode);
                }
                KnoxConfirmDeviceCredentialBaseFragmentHelper
                        knoxConfirmDeviceCredentialBaseFragmentHelper4 =
                                this.mKnoxConfirmDeviceCredentialBaseFragmentHelper;
                ImageButton imageButton3 = this.mPasswordShowButton;
                boolean z4 = this.mIsPasswordShown;
                KnoxConfirmLockHelper knoxConfirmLockHelper4 =
                        knoxConfirmDeviceCredentialBaseFragmentHelper4.mKnoxConfirmLockHelper;
                if (knoxConfirmLockHelper4 != null) {
                    knoxConfirmLockHelper4.setShowPwdImgColor(imageButton3, z4);
                }
                this.mPasswordShowButton.setOnClickListener(this);
            }
            if (!LockUtils.isApplyingTabletGUI(getContext())) {
                if (!this.mFrp
                        && !this.mRemoteValidation
                        && (findViewById =
                                        inflate.findViewById(R.id.confirm_lock_password_main_view))
                                != null) {
                    findViewById.semSetRoundedCorners(15);
                    findViewById.semSetRoundedCornerColor(
                            15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
                }
                View view = this.mBottomView;
                if (view != null) {
                    view.semSetRoundedCorners(12);
                    this.mBottomView.semSetRoundedCornerColor(
                            15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
                }
            }
            KnoxConfirmLockHelper knoxConfirmLockHelper5 =
                    this.mKnoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
            if (knoxConfirmLockHelper5 != null) {
                knoxConfirmLockHelper5.onCreateView(inflate);
            }
            if (this.mFrp || this.mRemoteValidation) {
                View findViewById3 = inflate.findViewById(R.id.button_layout);
                if (findViewById3 != null) {
                    findViewById3.setVisibility(8);
                }
                View view2 = this.mBottomView;
                if (view2 != null) {
                    view2.semSetRoundedCorners(0);
                }
            }
            if (!this.mIsManagedProfile && (getActivity() instanceof InternalActivity)) {
                getActivity().getWindow().setDecorFitsSystemWindows(false);
                View findViewById4 = getActivity().findViewById(android.R.id.content);
                LockContentViewInsetsCallback lockContentViewInsetsCallback =
                        new LockContentViewInsetsCallback(
                                findViewById4,
                                WindowInsets.Type.systemBars(),
                                WindowInsets.Type.ime());
                findViewById4.setWindowInsetsAnimationCallback(lockContentViewInsetsCallback);
                findViewById4.setOnApplyWindowInsetsListener(lockContentViewInsetsCallback);
            }
            return inflate;
        }

        @Override // com.android.settings.password.CredentialCheckResultTracker.Listener
        public final void onCredentialChecked(boolean z, Intent intent, int i, int i2, boolean z2) {
            this.mPasswordEntryInputDisabler.setInputEnabled(true);
            if (!z) {
                if (i > 0) {
                    KnoxConfirmLockHelper knoxConfirmLockHelper =
                            this.mKnoxConfirmDeviceCredentialBaseFragmentHelper
                                    .mKnoxConfirmLockHelper;
                    if (knoxConfirmLockHelper != null) {
                        knoxConfirmLockHelper.setBiometricLockoutDeadline(i);
                    }
                    refreshLockScreen();
                    handleAttemptLockout(this.mLockPatternUtils.setLockoutAttemptDeadline(i2, i));
                } else {
                    KnoxConfirmLockHelper knoxConfirmLockHelper2 =
                            this.mKnoxConfirmDeviceCredentialBaseFragmentHelper
                                    .mKnoxConfirmLockHelper;
                    showError(
                            getText(
                                    knoxConfirmLockHelper2 != null
                                            ? knoxConfirmLockHelper2.getErrorMessage()
                                            : R.string.sec_lockpassword_need_to_unlock_wrong),
                            3000L);
                }
                if (z2) {
                    reportFailedAttempt();
                    return;
                }
                return;
            }
            if (z2) {
                KnoxConfirmLockHelper knoxConfirmLockHelper3 =
                        this.mKnoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
                if (knoxConfirmLockHelper3 != null) {
                    knoxConfirmLockHelper3.reportSuccessfulAttempt();
                }
                KnoxConfirmLockHelper knoxConfirmLockHelper4 =
                        this.mKnoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
                if (knoxConfirmLockHelper4 != null) {
                    knoxConfirmLockHelper4.removeGlobalLayoutListenerIfRequired();
                }
                KnoxConfirmDeviceCredentialBaseFragmentHelper
                        knoxConfirmDeviceCredentialBaseFragmentHelper =
                                this.mKnoxConfirmDeviceCredentialBaseFragmentHelper;
                ImeAwareEditText imeAwareEditText = this.mPasswordEntry;
                KnoxConfirmLockHelper knoxConfirmLockHelper5 =
                        knoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
                if (knoxConfirmLockHelper5 != null) {
                    knoxConfirmLockHelper5.hideSoftInputIfNeeded(imeAwareEditText);
                }
                ConfirmDeviceCredentialUtils.reportSuccessfulAttempt(
                        this.mLockPatternUtils,
                        this.mUserManager,
                        this.mDevicePolicyManager,
                        this.mEffectiveUserId,
                        true);
            }
            int windowingMode =
                    getActivity()
                            .getResources()
                            .getConfiguration()
                            .windowConfiguration
                            .getWindowingMode();
            if (windowingMode == 5 || windowingMode == 6) {
                ConfirmDeviceCredentialUtils.checkForPendingIntent(getActivity());
                startDisappearAnimation(intent);
            } else {
                startDisappearAnimation(intent);
                ConfirmDeviceCredentialUtils.checkForPendingIntent(getActivity());
            }
            try {
                if (this.mUnlockRecovery
                        && LockUtils.isFmmUnlockAllowed(
                                getContext(),
                                this.mUserId,
                                this.mLockTypePolicy.isEnterpriseUser())) {
                    getActivity()
                            .startService(
                                    LockUtils.getFmmService(
                                            this.mIsAlpha
                                                    ? LockscreenCredential.createPasswordOrNone(
                                                            this.mPasswordEntry.getText())
                                                    : LockscreenCredential.createPinOrNone(
                                                            this.mPasswordEntry.getText()),
                                            getContext()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment,
                  // com.android.settingslib.core.lifecycle.ObservableFragment,
                  // androidx.fragment.app.Fragment
        public final void onDestroy() {
            ImeAwareEditText imeAwareEditText;
            super.onDestroy();
            if (this.mLockTypePolicy.isEnterpriseUser()
                    && (imeAwareEditText = this.mPasswordEntry) != null) {
                imeAwareEditText.setText(ApnSettings.MVNO_NONE);
            }
            ImeAwareEditText imeAwareEditText2 = this.mPasswordEntry;
            if (imeAwareEditText2 != null) {
                imeAwareEditText2.setText((CharSequence) null);
            }
            new Handler(Looper.myLooper())
                    .postDelayed(
                            new ConfirmLockPassword$ConfirmLockPasswordFragment$$ExternalSyntheticLambda1(),
                            5000L);
        }

        @Override // android.widget.TextView.OnEditorActionListener
        public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i != 0 && i != 6 && i != 5) {
                return false;
            }
            if (isEnabledNdigitsPinForConfirm()) {
                SemLog.d(
                        "ConfirmLockPasswordFragment",
                        "Done key has no action because Confirm PIN without OK is enabled");
                return true;
            }
            handleNext$3();
            return true;
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment,
                  // com.android.settingslib.core.lifecycle.ObservableFragment,
                  // androidx.fragment.app.Fragment
        public final void onPause() {
            super.onPause();
            CountDownTimer countDownTimer = this.mCountdownTimer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
                this.mCountdownTimer = null;
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
                updatePasswordEntry();
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
                    "ConfirmLockPasswordFragment",
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
            PasswordPolicy passwordPolicy;
            Intent intent;
            int i;
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
                handleAttemptLockout(lockoutAttemptDeadline);
            } else {
                updatePasswordEntry();
                this.mErrorTextView.setText(getDefaultDetails());
                updateErrorMessage(
                        this.mLockPatternUtils.getCurrentFailedPasswordAttempts(
                                this.mEffectiveUserId));
            }
            this.mCredentialCheckResultTracker.setListener(this);
            RemoteLockscreenValidationFragment remoteLockscreenValidationFragment =
                    this.mRemoteLockscreenValidationFragment;
            if (remoteLockscreenValidationFragment != null) {
                remoteLockscreenValidationFragment.setListener(this, this.mHandler);
            }
            int i2 = this.mUserId;
            Log.d("ConfirmLockPasswordFragment", "getPwdPolicy () " + i2);
            if (SemPersonaManager.isKnoxId(i2)) {
                try {
                    i =
                            getActivity()
                                    .getPackageManager()
                                    .getPackageUidAsUser(
                                            SemPersonaManager.getAdminComponentName(i2)
                                                    .getPackageName(),
                                            i2);
                } catch (Exception e) {
                    CloneBackend$$ExternalSyntheticOutline0.m(
                            e,
                            new StringBuilder("Error fetching admin uid "),
                            "ChooseLockPassword");
                    i = i2;
                }
                FragmentActivity activity = getActivity();
                ContextInfo contextInfo = new ContextInfo(i, i2);
                String str = KnoxUtils.mDeviceType;
                EnterpriseKnoxManager enterpriseKnoxManager = EnterpriseKnoxManager.getInstance();
                KnoxContainerManager knoxContainerManager =
                        enterpriseKnoxManager == null
                                ? null
                                : enterpriseKnoxManager.getKnoxContainerManager(
                                        activity, contextInfo);
                if (knoxContainerManager != null) {
                    passwordPolicy = knoxContainerManager.getPasswordPolicy();
                }
                passwordPolicy = null;
            } else {
                EnterpriseDeviceManager enterpriseDeviceManager =
                        EnterpriseDeviceManager.getInstance(getActivity());
                if (enterpriseDeviceManager != null) {
                    passwordPolicy = enterpriseDeviceManager.getPasswordPolicy();
                }
                passwordPolicy = null;
            }
            if (passwordPolicy == null) {
                Log.d("ConfirmLockPasswordFragment", "PasswordPolicy is NULL!");
            } else if (!passwordPolicy.isPasswordVisibilityEnabled()) {
                this.mPasswordShowButton.setVisibility(8);
                this.mPasswordShowButton.setForeground(
                        getResources()
                                .getDrawable(
                                        R.drawable.sec_lock_setting_btn_password_hide_mtrl, null));
                this.mPasswordEntry.setTransformationMethod(
                        PasswordTransformationMethod.getInstance());
                this.mPasswordShowButton.setContentDescription(
                        getString(R.string.sec_lock_screen_password_show_button));
                this.mIsPasswordShown = false;
                KnoxConfirmDeviceCredentialBaseFragmentHelper
                        knoxConfirmDeviceCredentialBaseFragmentHelper =
                                this.mKnoxConfirmDeviceCredentialBaseFragmentHelper;
                ImageButton imageButton = this.mPasswordShowButton;
                KnoxConfirmLockHelper knoxConfirmLockHelper =
                        knoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
                if (knoxConfirmLockHelper != null) {
                    knoxConfirmLockHelper.setShowPwdImgColor(imageButton, false);
                }
            } else if (this.mIsAlpha) {
                this.mPasswordShowButton.setVisibility(0);
            }
            if (getActivity() == null
                    || (intent = getActivity().getIntent()) == null
                    || this.mTwoFactorBiometricConfirmed) {
                return;
            }
            boolean booleanExtra = intent.getBooleanExtra("from_knox_work_profile_security", false);
            KnoxConfirmDeviceCredentialBaseFragmentHelper
                    knoxConfirmDeviceCredentialBaseFragmentHelper2 =
                            this.mKnoxConfirmDeviceCredentialBaseFragmentHelper;
            FragmentActivity activity2 = getActivity();
            boolean z = this.mWaitingForTwoFactorConfirmation;
            KnoxConfirmLockHelper knoxConfirmLockHelper2 =
                    knoxConfirmDeviceCredentialBaseFragmentHelper2.mKnoxConfirmLockHelper;
            this.mWaitingForTwoFactorConfirmation =
                    knoxConfirmLockHelper2 != null
                            ? knoxConfirmLockHelper2.confirmBiometricIfNeeded(
                                    booleanExtra, activity2, z)
                            : false;
        }

        @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
                  // androidx.fragment.app.Fragment
        public final void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            bundle.putBoolean("password_shown", this.mIsPasswordShown);
            bundle.putBoolean("two_factor_biometric_confirmed", this.mTwoFactorBiometricConfirmed);
            bundle.putBoolean(
                    "waiting_for_two_factor_confirmation", this.mWaitingForTwoFactorConfirmation);
            KnoxConfirmLockHelper knoxConfirmLockHelper =
                    this.mKnoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
            if (knoxConfirmLockHelper != null) {
                knoxConfirmLockHelper.onSavedInstanceState(bundle);
            }
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment
        public final void onShowDefault() {
            CharSequence charSequence = this.mDetailMsg;
            if (charSequence != null) {
                this.mErrorTextView.setText(charSequence.toString());
                return;
            }
            this.mErrorTextView.setText(getDefaultDetails());
            this.mKnoxConfirmDeviceCredentialBaseFragmentHelper.needToUpdateErrorMessage(
                    this.mLockPatternUtils.getCurrentFailedPasswordAttempts(this.mEffectiveUserId),
                    this.mGlifLayout.mDetailsTextView);
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment
        public final void onShowError() {
            this.mPasswordEntry.setText((CharSequence) null);
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment,
                  // androidx.fragment.app.Fragment
        public final void onViewCreated(View view, Bundle bundle) {
            CharSequence charSequence;
            super.onViewCreated(view, bundle);
            boolean z = this.mRemoteValidation;
            int i = R.string.lockpassword_forgot_pin;
            if (z) {
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
                                        this.mIsAlpha
                                                ? R.string
                                                        .sec_lockpassword_remote_validation_set_password_as_screenlock
                                                : R.string
                                                        .sec_lockpassword_remote_validation_set_pin_as_screenlock);
                    }
                    checkBox.setText(charSequence);
                }
                if (this.mCancelButton != null && TextUtils.isEmpty(this.mAlternateButtonText)) {
                    this.mCancelButton.setText(
                            this.mIsAlpha
                                    ? R.string.lockpassword_forgot_password
                                    : R.string.lockpassword_forgot_pin);
                }
                updateRemoteLockscreenValidationViews();
            }
            Button button = this.mForgotButton;
            if (button != null) {
                if (this.mIsAlpha) {
                    i = R.string.lockpassword_forgot_password;
                }
                button.setText(i);
            }
            if (this.mFrp) {
                FooterBarMixin footerBarMixin =
                        (FooterBarMixin) ((GlifLayout) view).getMixin(FooterBarMixin.class);
                footerBarMixin.setPrimaryButton(
                        new FooterButton(
                                getActivity().getString(R.string.lockpassword_continue_label),
                                new View
                                        .OnClickListener() { // from class:
                                                             // com.android.settings.password.ConfirmLockPassword$ConfirmLockPasswordFragment$$ExternalSyntheticLambda3
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view2) {
                                        ConfirmLockPassword.ConfirmLockPasswordFragment.this
                                                .handleNext$3();
                                    }
                                },
                                5,
                                2132083805));
                footerBarMixin
                        .getButtonContainer()
                        .setBackgroundColor(
                                getResources()
                                        .getColor(R.color.sec_lock_suw_background_color, null));
                FooterButton footerButton = footerBarMixin.primaryButton;
                this.mPrimaryButton = footerButton;
                if (footerButton != null) {
                    footerButton.setEnabled(false);
                }
            }
        }

        public final void startDisappearAnimation(Intent intent) {
            View decorView = getActivity().getWindow().getDecorView();
            if (decorView.isAttachedToWindow()
                    && decorView.getRootWindowInsets().isVisible(WindowInsets.Type.ime())) {
                decorView.getWindowInsetsController().hide(WindowInsets.Type.ime());
            }
            if (this.mDisappearing) {
                return;
            }
            this.mDisappearing = true;
            ConfirmLockPassword confirmLockPassword = (ConfirmLockPassword) getActivity();
            if (confirmLockPassword == null || confirmLockPassword.isFinishing()) {
                return;
            }
            this.mImm.hideSoftInputFromWindow(this.mPasswordEntry.getWindowToken(), 0);
            confirmLockPassword.setResult(-1, intent);
            confirmLockPassword.finish();
        }

        public final void startVerifyPassword(
                final LockscreenCredential lockscreenCredential, final Intent intent, final int i) {
            Log.d("ConfirmLockPasswordFragment", "startVerifyPassword()");
            final int i2 = this.mEffectiveUserId;
            int i3 = this.mUserId;
            LockPatternChecker.OnVerifyCallback onVerifyCallback =
                    new LockPatternChecker.OnVerifyCallback() { // from class:
                        // com.android.settings.password.ConfirmLockPassword$ConfirmLockPasswordFragment$$ExternalSyntheticLambda2
                        public final void onVerified(
                                VerifyCredentialResponse verifyCredentialResponse, int i4) {
                            ConfirmLockPassword.ConfirmLockPasswordFragment
                                    confirmLockPasswordFragment =
                                            ConfirmLockPassword.ConfirmLockPasswordFragment.this;
                            int i5 = i;
                            Intent intent2 = intent;
                            LockscreenCredential lockscreenCredential2 = lockscreenCredential;
                            int i6 = i2;
                            int i7 = ConfirmLockPassword.ConfirmLockPasswordFragment.$r8$clinit;
                            confirmLockPasswordFragment.getClass();
                            Log.d("ConfirmLockPasswordFragment", "OnVerifyCallback");
                            confirmLockPasswordFragment.mPendingLockCheck = null;
                            boolean isMatched = verifyCredentialResponse.isMatched();
                            if (isMatched && confirmLockPasswordFragment.mReturnCredentials) {
                                if ((i5 & 1) != 0) {
                                    intent2.putExtra(
                                            "gk_pw_handle",
                                            verifyCredentialResponse.getGatekeeperPasswordHandle());
                                } else {
                                    intent2.putExtra(
                                            "hw_auth_token",
                                            verifyCredentialResponse.getGatekeeperHAT());
                                }
                                if (confirmLockPasswordFragment.mLockTypePolicy
                                        .isEnterpriseUser()) {
                                    Log.d(
                                            "ConfirmLockPasswordFragment.SDP",
                                            "startVerifyPassword - Secured password required for"
                                                + " user "
                                                    + confirmLockPasswordFragment.mUserId);
                                    intent2.putExtra(
                                            HostAuth.PASSWORD,
                                            (Parcelable)
                                                    KnoxUtils.getCipher(
                                                            lockscreenCredential2,
                                                            KnoxUtils.getCipherPublicHandle()));
                                    intent2.putExtra("is_knox_password_secured", true);
                                } else {
                                    intent2.putExtra(
                                            HostAuth.PASSWORD, (Parcelable) lockscreenCredential2);
                                }
                            }
                            confirmLockPasswordFragment.mCredentialCheckResultTracker.setResult(
                                    isMatched, intent2, i4, i6);
                        }
                    };
            this.mPendingLockCheck =
                    i2 == i3
                            ? LockPatternChecker.verifyCredential(
                                    this.mLockPatternUtils,
                                    lockscreenCredential,
                                    i3,
                                    i,
                                    onVerifyCallback)
                            : LockPatternChecker.verifyTiedProfileChallenge(
                                    this.mLockPatternUtils,
                                    lockscreenCredential,
                                    i3,
                                    i,
                                    onVerifyCallback);
        }

        public final void updatePasswordEntry() {
            KnoxConfirmDeviceCredentialBaseFragmentHelper
                    knoxConfirmDeviceCredentialBaseFragmentHelper =
                            this.mKnoxConfirmDeviceCredentialBaseFragmentHelper;
            boolean z = this.mWaitingForTwoFactorConfirmation;
            KnoxConfirmLockHelper knoxConfirmLockHelper =
                    knoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
            if (knoxConfirmLockHelper != null
                    ? knoxConfirmLockHelper.interceptUpdateEntryOrStateIfNeeded(z)
                    : false) {
                return;
            }
            boolean z2 =
                    this.mLockPatternUtils.getLockoutAttemptDeadline(this.mEffectiveUserId) != 0;
            RemoteLockscreenValidationFragment remoteLockscreenValidationFragment =
                    this.mRemoteLockscreenValidationFragment;
            boolean z3 =
                    (z2
                                    || (remoteLockscreenValidationFragment != null
                                            && remoteLockscreenValidationFragment.mIsInProgress))
                            ? false
                            : true;
            this.mPasswordEntry.setEnabled(z3);
            this.mPasswordEntry.setFocusable(z3);
            this.mPasswordEntry.setFocusableInTouchMode(z3);
            this.mKnoxConfirmDeviceCredentialBaseFragmentHelper.needToUpdateErrorMessage(
                    this.mLockPatternUtils.getCurrentFailedPasswordAttempts(this.mEffectiveUserId),
                    this.mGlifLayout.mDetailsTextView);
            this.mPasswordEntryInputDisabler.setInputEnabled(z3);
            if (!z3) {
                this.mImm.hideSoftInputFromWindow(this.mPasswordEntry.getWindowToken(), 0);
            } else {
                this.mPasswordEntry.scheduleShowSoftInput();
                this.mPasswordEntry.requestFocus();
            }
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment,
                  // com.samsung.android.settings.knox.KnoxConfirmDeviceCredentialBaseFragmentHelper.Callback
        public final void updateState() {
            refreshLockScreen();
            this.mLockPatternUtils.getLockoutAttemptDeadline(this.mEffectiveUserId);
        }

        @Override // android.text.TextWatcher
        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override // android.text.TextWatcher
        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
    }
}

package com.android.settings.password;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.StatusBarManager;
import android.app.admin.DevicePolicyManager;
import android.app.admin.PasswordMetrics;
import android.app.trust.TrustManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImeAwareEditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.PasswordValidationError;
import com.android.internal.widget.TextViewInputDisabler;
import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;
import com.android.settings.core.InstrumentedFragment;
import com.android.settings.notification.RedactionInterstitial;
import com.android.settings.password.ChooseLockPassword;
import com.android.settings.password.SaveAndFinishWorker;
import com.android.settingslib.utils.StringUtil;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.GlifLayout;
import com.google.android.setupdesign.util.ThemeHelper;
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
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.knox.UCMUtils;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.settings.lockscreen.PreviousPasswordDescriptionActivity;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.password.LockContentViewInsetsCallback;
import com.samsung.android.settings.password.LockTypePolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ChooseLockPassword extends SettingsActivity {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class RecoveryActivity extends ChooseLockPassword {
        public static final /* synthetic */ int $r8$clinit = 0;
        public AnonymousClass1 mReceiver;

        @Override // com.samsung.android.settings.core.SecSettingsBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
        public final void onPause() {
            super.onPause();
            AnonymousClass1 anonymousClass1 = this.mReceiver;
            if (anonymousClass1 == null) {
                return;
            }
            try {
                unregisterReceiver(anonymousClass1);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v0, types: [android.content.BroadcastReceiver, com.android.settings.password.ChooseLockPassword$RecoveryActivity$1] */
        @Override // com.android.settings.SettingsActivity, com.samsung.android.settings.core.SecSettingsBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
        public final void onResume() {
            super.onResume();
            if (this.mReceiver != null) {
                return;
            }
            IntentFilter m = AppCompatDelegateImpl$$ExternalSyntheticOutline0.m("android.intent.action.USER_PRESENT");
            ?? r1 = new BroadcastReceiver() { // from class: com.android.settings.password.ChooseLockPassword.RecoveryActivity.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    RecoveryActivity recoveryActivity = RecoveryActivity.this;
                    int i = RecoveryActivity.$r8$clinit;
                    recoveryActivity.getClass();
                    Intent intent2 = new Intent();
                    intent2.putExtra("screen_lock_force_destroy", true);
                    recoveryActivity.setResult(0, intent2);
                    recoveryActivity.finish();
                }
            };
            this.mReceiver = r1;
            registerReceiver(r1, m);
        }
    }

    @Override // android.app.Activity
    public final void finish() {
        SaveAndFinishWorker saveAndFinishWorker;
        Fragment findFragmentById = getSupportFragmentManager().findFragmentById(R.id.main_content);
        if (findFragmentById == null || !(findFragmentById instanceof ChooseLockPasswordFragment) || (saveAndFinishWorker = ((ChooseLockPasswordFragment) findFragmentById).mSaveAndFinishWorker) == null || saveAndFinishWorker.mFinished) {
            super.finish();
        }
    }

    public Class getFragmentClass() {
        return ChooseLockPasswordFragment.class;
    }

    @Override // com.android.settings.SettingsActivity, android.app.Activity
    public final Intent getIntent() {
        Intent intent = new Intent(super.getIntent());
        intent.putExtra(":settings:show_fragment", getFragmentClass().getName());
        return intent;
    }

    @Override // com.android.settings.SettingsActivity
    public boolean isValidFragment(String str) {
        return ChooseLockPasswordFragment.class.getName().equals(str);
    }

    @Override // com.android.settings.SettingsActivity, com.android.settings.core.SettingsBaseActivity, com.samsung.android.settings.core.SecSettingsBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        setTheme((getIntent().getBooleanExtra("fromSetupWizard", false) && LockUtils.isApplyingSetupTheme(this)) ? R.style.LockscreenSUWTheme : R.style.LockScreenTheme);
        ThemeHelper.trySetDynamicColor(this);
        super.onCreate(bundle);
        findViewById(R.id.content_parent).setFitsSystemWindows(false);
        if (!LockUtils.isEmTokenAllowed(this)) {
            getWindow().addFlags(8192);
        }
        int intExtra = getIntent().getIntExtra("lockscreen.password_type", 0);
        CharSequence text = (131072 == intExtra || 196608 == intExtra) ? getText(R.string.sec_choose_lock_pin_your_pin_header) : getText(R.string.sec_choose_lock_password_your_password_header);
        setTitle(text);
        if (LockUtils.isApplyingTabletGUI(this)) {
            if (getWindow().isFloating()) {
                getWindow().clearFlags(Integer.MIN_VALUE);
                getWindow().getAttributes().gravity = 80;
            }
            hideAppBar();
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
            if (coordinatorLayout != null) {
                coordinatorLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
            }
            AppCompatTextView appCompatTextView = (AppCompatTextView) getWindow().getDecorView().findViewById(R.id.title);
            if (appCompatTextView != null) {
                appCompatTextView.setText(text);
            }
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.content_layout);
            if (linearLayout != null) {
                linearLayout.setLayoutParams(new CoordinatorLayout.LayoutParams(-1, -2));
            }
            View findViewById = findViewById(R.id.round_corner);
            if (findViewById != null) {
                findViewById.setVisibility(8);
            }
        } else {
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setHomeButtonEnabled();
                supportActionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
        disableExtendedAppBar();
    }

    @Override // android.app.Activity
    public final boolean onCreateOptionsMenu(Menu menu) {
        Fragment findFragmentById = getSupportFragmentManager().findFragmentById(R.id.main_content);
        if (findFragmentById == null || !(findFragmentById instanceof ChooseLockPasswordFragment)) {
            return false;
        }
        ChooseLockPasswordFragment chooseLockPasswordFragment = (ChooseLockPasswordFragment) findFragmentById;
        if (LockUtils.isSupportActionBarButton(chooseLockPasswordFragment.getActivity())) {
            MenuItem add = menu.add(0, 2, 1, chooseLockPasswordFragment.mUiStage.buttonText);
            add.setShowAsAction(2);
            Button button = chooseLockPasswordFragment.mFooterRightButton;
            if (button != null) {
                add.setEnabled(button.isEnabled());
            } else {
                add.setEnabled(false);
            }
            menu.add(0, 1, 0, R.string.lockpassword_cancel_label).setShowAsAction(2);
            chooseLockPasswordFragment.mMenu = menu;
        }
        return true;
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity, android.app.Activity, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        Fragment findFragmentById = getSupportFragmentManager().findFragmentById(R.id.main_content);
        if (findFragmentById != null && (findFragmentById instanceof ChooseLockPasswordFragment)) {
            ChooseLockPasswordFragment chooseLockPasswordFragment = (ChooseLockPasswordFragment) findFragmentById;
            if ((i == 4 || i == 3) && !SemPersonaManager.isKnoxId(UserHandle.myUserId()) && chooseLockPasswordFragment.mIsChangePwdRequired) {
                if (i != 4 || !chooseLockPasswordFragment.isAdded()) {
                    return true;
                }
                ChooseLockPasswordFragment.Stage stage = chooseLockPasswordFragment.mUiStage;
                if (stage != ChooseLockPasswordFragment.Stage.NeedToConfirm && stage != ChooseLockPasswordFragment.Stage.ConfirmWrong) {
                    return true;
                }
                chooseLockPasswordFragment.mPasswordEntry.setText(ApnSettings.MVNO_NONE);
                chooseLockPasswordFragment.updateStage(ChooseLockPasswordFragment.Stage.Introduction);
                return true;
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity, android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        Fragment findFragmentById = getSupportFragmentManager().findFragmentById(R.id.main_content);
        if (findFragmentById == null || !(findFragmentById instanceof ChooseLockPasswordFragment) || menuItem.getItemId() == 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        ChooseLockPasswordFragment chooseLockPasswordFragment = (ChooseLockPasswordFragment) findFragmentById;
        if (!LockUtils.isSupportActionBarButton(chooseLockPasswordFragment.getActivity())) {
            return false;
        }
        int itemId = menuItem.getItemId();
        if (itemId == 2) {
            chooseLockPasswordFragment.handleNext$2();
        } else if (itemId == 1) {
            chooseLockPasswordFragment.handleCancel();
        }
        return true;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class ChooseLockPasswordFragment extends InstrumentedFragment implements TextView.OnEditorActionListener, TextWatcher, SaveAndFinishWorker.Listener, View.OnClickListener {
        public AlarmManager mAlarmManager;
        public CheckBox mAutoPinConfirmOption;
        public LinearLayout mBottomBar;
        public LockscreenCredential mChosenPassword;
        public LockscreenCredential mCurrentCredential;
        public LockscreenCredential mFirstPassword;
        public Button mFooterLeftButton;
        public Button mFooterRightButton;
        public boolean mForAppLockBackupKey;
        public boolean mForFace;
        public boolean mForFingerprint;
        public boolean mFromAppLock;
        public boolean mFromPrevPassword;
        public boolean mFromSetupWizard;
        public TextView mHeaderText;
        public TextView mHelpText;
        public boolean mIsAlphaMode;
        public boolean mIsApplyingSetupTheme;
        public boolean mIsApplyingTabletGui;
        public boolean mIsAutoPinConfirmOptionSetManually;
        public boolean mIsChangePwdRequired;
        public boolean mIsFromKnoxTwoStep;
        public boolean mIsPasswordShown;
        public boolean mIsUnlockRecovery;
        public boolean mKnoxEnforcePassword;
        public GlifLayout mLayout;
        public LockPatternUtils mLockPatternUtils;
        public LockTypePolicy mLockTypePolicy;
        public LinearLayout mMainView;
        public Menu mMenu;
        public PasswordMetrics mMinMetrics;
        public FooterBarMixin mMixin;
        public LockscreenCredential mOldPassword;
        public ImeAwareEditText mPasswordEntry;
        public TextViewInputDisabler mPasswordEntryInputDisabler;
        public byte[] mPasswordHistoryHashFactor;
        public ImageButton mPasswordShowButton;
        public FooterButton mPrimaryButton;
        public boolean mRequestGatekeeperPassword;
        public boolean mRequestWriteRepairModePassword;
        public SaveAndFinishWorker mSaveAndFinishWorker;
        public int mStatusBarDisableCount;
        public StatusBarManager mStatusBarManager;
        public TextChangedHandler mTextChangedHandler;
        public TextView mUCMChangePinMessage;
        public int mUserId;
        public List mValidationErrors;
        public int mMinComplexity = 0;
        public int mUnificationProfileId = -10000;
        public int mPasswordType = 131072;
        public Stage mUiStage = Stage.Introduction;
        public boolean mCheckSimplePassword = false;
        public boolean mIsSimplePassword = false;
        public int mPasswordMinLength = 4;
        public int mPasswordMaxLength = 256;
        public int mPwdChangeEnforceStatus = 0;
        public int mPwdChangeTimeout = 0;
        public InputMethodManager mImm = null;
        public AlertDialog mEasyPinAlertDialog = null;
        public final AnonymousClass1 mScreenOffReceiver = new BroadcastReceiver() { // from class: com.android.settings.password.ChooseLockPassword.ChooseLockPasswordFragment.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                ChooseLockPasswordFragment chooseLockPasswordFragment = ChooseLockPasswordFragment.this;
                if (!chooseLockPasswordFragment.mRequestGatekeeperPassword || chooseLockPasswordFragment.getActivity() == null) {
                    return;
                }
                ChooseLockPasswordFragment.this.getActivity().finish();
            }
        };
        public final AnonymousClass2 MaxLengthFilter = new InputFilter() { // from class: com.android.settings.password.ChooseLockPassword.ChooseLockPasswordFragment.2
            @Override // android.text.InputFilter
            public final CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
                ImeAwareEditText imeAwareEditText = ChooseLockPasswordFragment.this.mPasswordEntry;
                if (imeAwareEditText == null) {
                    return null;
                }
                int length = imeAwareEditText.length();
                ChooseLockPasswordFragment chooseLockPasswordFragment = ChooseLockPasswordFragment.this;
                int i5 = chooseLockPasswordFragment.mPasswordMaxLength;
                if (length < i5) {
                    return null;
                }
                ChooseLockPasswordFragment.this.mHeaderText.setText(chooseLockPasswordFragment.getString(chooseLockPasswordFragment.mIsAlphaMode ? R.string.sec_choose_lock_password_password_too_long : R.string.sec_choose_lock_pin_pin_too_long, Integer.valueOf(i5)));
                return null;
            }
        };

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public enum Stage {
            Introduction(R.string.lockpassword_choose_your_password_header, "Settings.SET_WORK_PROFILE_PASSWORD_HEADER", R.string.lockpassword_choose_your_profile_password_header, R.string.lockpassword_choose_your_password_header_for_fingerprint, R.string.lockpassword_choose_your_password_header_for_face, R.string.lockpassword_choose_your_password_header_for_biometrics, R.string.private_space_choose_your_password_header, R.string.lockpassword_choose_your_pin_header, "Settings.SET_WORK_PROFILE_PIN_HEADER", R.string.lockpassword_choose_your_profile_pin_header, R.string.lockpassword_choose_your_pin_header_for_fingerprint, R.string.lockpassword_choose_your_pin_header_for_face, R.string.lockpassword_choose_your_pin_header_for_biometrics, R.string.private_space_choose_your_pin_header, R.string.lock_settings_picker_biometrics_added_security_message, R.string.lock_settings_picker_biometrics_added_security_message, R.string.lockpassword_continue_label),
            NeedToConfirm(R.string.lockpassword_confirm_your_password_header, "Settings.REENTER_WORK_PROFILE_PASSWORD_HEADER", R.string.lockpassword_reenter_your_profile_password_header, R.string.lockpassword_confirm_your_password_header, R.string.lockpassword_confirm_your_password_header, R.string.lockpassword_confirm_your_password_header, R.string.lockpassword_confirm_your_password_header, R.string.lockpassword_confirm_your_pin_header, "Settings.REENTER_WORK_PROFILE_PIN_HEADER", R.string.lockpassword_reenter_your_profile_pin_header, R.string.lockpassword_confirm_your_pin_header, R.string.lockpassword_confirm_your_pin_header, R.string.lockpassword_confirm_your_pin_header, R.string.lockpassword_confirm_your_pin_header, 0, 0, R.string.sec_lockpassword_ok_label),
            ConfirmWrong(R.string.lockpassword_confirm_passwords_dont_match, PeripheralBarcodeConstants.Symbology.UNDEFINED, R.string.lockpassword_confirm_passwords_dont_match, R.string.lockpassword_confirm_passwords_dont_match, R.string.lockpassword_confirm_passwords_dont_match, R.string.lockpassword_confirm_passwords_dont_match, R.string.lockpassword_confirm_passwords_dont_match, R.string.lockpassword_confirm_pins_dont_match, PeripheralBarcodeConstants.Symbology.UNDEFINED, R.string.lockpassword_confirm_pins_dont_match, R.string.lockpassword_confirm_pins_dont_match, R.string.lockpassword_confirm_pins_dont_match, R.string.lockpassword_confirm_pins_dont_match, R.string.lockpassword_confirm_pins_dont_match, 0, 0, R.string.sec_lockpassword_ok_label);

            public final int alphaHint;
            public final int alphaHintForBiometrics;
            public final int alphaHintForFace;
            public final int alphaHintForFingerprint;
            public final int alphaHintForManagedProfile;
            public final int alphaHintForPrivateProfile;
            public final String alphaHintOverrideForProfile;
            public final int alphaMessageForBiometrics;
            public final int buttonText;
            public final int numericHint;
            public final int numericHintForBiometrics;
            public final int numericHintForFace;
            public final int numericHintForFingerprint;
            public final int numericHintForManagedProfile;
            public final int numericHintForPrivateProfile;
            public final String numericHintOverrideForProfile;
            public final int numericMessageForBiometrics;

            Stage(int i, String str, int i2, int i3, int i4, int i5, int i6, int i7, String str2, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15) {
                this.alphaHint = i;
                this.alphaHintOverrideForProfile = str;
                this.alphaHintForManagedProfile = i2;
                this.alphaHintForFingerprint = i3;
                this.alphaHintForFace = i4;
                this.alphaHintForBiometrics = i5;
                this.alphaHintForPrivateProfile = i6;
                this.numericHint = i7;
                this.numericHintOverrideForProfile = str2;
                this.numericHintForManagedProfile = i8;
                this.numericHintForFingerprint = i9;
                this.numericHintForFace = i10;
                this.numericHintForBiometrics = i11;
                this.numericHintForPrivateProfile = i12;
                this.alphaMessageForBiometrics = i13;
                this.numericMessageForBiometrics = i14;
                this.buttonText = i15;
            }
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class TextChangedHandler extends Handler {
            public static final /* synthetic */ int $r8$clinit = 0;

            public TextChangedHandler() {
            }

            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                ChooseLockPasswordFragment chooseLockPasswordFragment = ChooseLockPasswordFragment.this;
                if (chooseLockPasswordFragment.getActivity() != null && message.what == 1) {
                    chooseLockPasswordFragment.updateUi();
                }
            }
        }

        public static boolean checkRepeatingChars(String str) {
            if (str != null && str.length() > 1) {
                char charAt = str.charAt(0);
                int i = 0;
                for (int i2 = 1; i2 < str.length(); i2++) {
                    i = charAt == str.charAt(i2) ? i + 1 : 0;
                    if (i >= 3) {
                        Log.secD("ChooseLockPassword", "has internal loop password : " + i);
                        return true;
                    }
                    charAt = str.charAt(i2);
                }
            }
            return false;
        }

        public static boolean checkSequentialChars(String str) {
            for (int i = 0; i <= 6; i++) {
                int i2 = i + 4;
                String substring = "0123456789".substring(i, i2);
                String substring2 = "9876543210".substring(i, i2);
                if (str != null && (str.contains(substring) || str.contains(substring2))) {
                    Log.secD("ChooseLockPassword", "has Sequential password");
                    return true;
                }
            }
            for (int i3 = 0; i3 <= 22; i3++) {
                int i4 = i3 + 4;
                String substring3 = "abcdefghijklmnopqrstuvwxyz".substring(i3, i4);
                String substring4 = "zyxwvutsrqponmlkjihgfedcba".substring(i3, i4);
                if (str != null && (str.contains(substring3) || str.contains(substring4))) {
                    Log.secD("ChooseLockPassword", "has Sequential password");
                    return true;
                }
            }
            return false;
        }

        @Override // android.text.TextWatcher
        public final void afterTextChanged(Editable editable) {
            if (this.mUiStage == Stage.ConfirmWrong) {
                this.mUiStage = Stage.NeedToConfirm;
            }
            TextChangedHandler textChangedHandler = this.mTextChangedHandler;
            int i = TextChangedHandler.$r8$clinit;
            textChangedHandler.removeMessages(1);
            textChangedHandler.sendEmptyMessageDelayed(1, 100L);
        }

        public final String[] convertErrorCodeToMessages() {
            ArrayList arrayList = new ArrayList();
            for (PasswordValidationError passwordValidationError : this.mValidationErrors) {
                switch (passwordValidationError.errorCode) {
                    case 2:
                        arrayList.add(getString(R.string.sec_lockpassword_illegal_character));
                        break;
                    case 3:
                        if (this.mIsAlphaMode) {
                            Resources resources = getResources();
                            int i = passwordValidationError.requirement;
                            arrayList.add(resources.getQuantityString(R.plurals.sec_choose_lock_password_password_too_short, i, Integer.valueOf(i)));
                            break;
                        } else {
                            arrayList.add(getDefaultPINMessage());
                            break;
                        }
                    case 4:
                        arrayList.add(StringUtil.getIcuPluralsString(getContext(), passwordValidationError.requirement, R.string.lockpassword_password_too_short_all_numeric));
                        break;
                    case 5:
                        arrayList.add(getString(this.mIsAlphaMode ? R.string.sec_choose_lock_password_password_too_long : R.string.sec_choose_lock_pin_pin_too_long, Integer.valueOf(passwordValidationError.requirement + 1)));
                        break;
                    case 6:
                        arrayList.add(getString(R.string.sec_lockpassword_pin_no_sequential_digits));
                        break;
                    case 7:
                        Resources resources2 = getResources();
                        int i2 = passwordValidationError.requirement;
                        arrayList.add(resources2.getQuantityString(R.plurals.sec_lockpassword_password_requires_letters, i2, Integer.valueOf(i2)));
                        break;
                    case 8:
                        Resources resources3 = getResources();
                        int i3 = passwordValidationError.requirement;
                        arrayList.add(resources3.getQuantityString(R.plurals.sec_lockpassword_password_requires_uppercase, i3, Integer.valueOf(i3)));
                        break;
                    case 9:
                        Resources resources4 = getResources();
                        int i4 = passwordValidationError.requirement;
                        arrayList.add(resources4.getQuantityString(R.plurals.sec_lockpassword_password_requires_lowercase, i4, Integer.valueOf(i4)));
                        break;
                    case 10:
                        Resources resources5 = getResources();
                        int i5 = passwordValidationError.requirement;
                        arrayList.add(resources5.getQuantityString(R.plurals.sec_lockpassword_password_requires_numeric, i5, Integer.valueOf(i5)));
                        break;
                    case 11:
                        Resources resources6 = getResources();
                        int i6 = passwordValidationError.requirement;
                        arrayList.add(resources6.getQuantityString(R.plurals.sec_lockpassword_password_requires_symbols, i6, Integer.valueOf(i6)));
                        break;
                    case 12:
                        Resources resources7 = getResources();
                        int i7 = passwordValidationError.requirement;
                        arrayList.add(resources7.getQuantityString(R.plurals.sec_lockpassword_password_requires_nonletter, i7, Integer.valueOf(i7)));
                        break;
                    case 13:
                        arrayList.add(StringUtil.getIcuPluralsString(getContext(), passwordValidationError.requirement, R.string.lockpassword_password_requires_nonnumerical));
                        break;
                    case 14:
                        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getContext().getSystemService(DevicePolicyManager.class);
                        if (this.mIsAlphaMode) {
                            final int i8 = 0;
                            arrayList.add(devicePolicyManager.getResources().getString("Settings.PASSWORD_RECENTLY_USED", new Supplier(this) { // from class: com.android.settings.password.ChooseLockPassword$ChooseLockPasswordFragment$$ExternalSyntheticLambda0
                                public final /* synthetic */ ChooseLockPassword.ChooseLockPasswordFragment f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // java.util.function.Supplier
                                public final Object get() {
                                    int i9 = i8;
                                    ChooseLockPassword.ChooseLockPasswordFragment chooseLockPasswordFragment = this.f$0;
                                    switch (i9) {
                                        case 0:
                                            return chooseLockPasswordFragment.getString(R.string.sec_lockpassword_password_recently_used);
                                        default:
                                            return chooseLockPasswordFragment.getString(R.string.sec_lockpassword_pin_recently_used);
                                    }
                                }
                            }));
                            break;
                        } else {
                            final int i9 = 1;
                            arrayList.add(devicePolicyManager.getResources().getString("Settings.PIN_RECENTLY_USED", new Supplier(this) { // from class: com.android.settings.password.ChooseLockPassword$ChooseLockPasswordFragment$$ExternalSyntheticLambda0
                                public final /* synthetic */ ChooseLockPassword.ChooseLockPasswordFragment f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // java.util.function.Supplier
                                public final Object get() {
                                    int i92 = i9;
                                    ChooseLockPassword.ChooseLockPasswordFragment chooseLockPasswordFragment = this.f$0;
                                    switch (i92) {
                                        case 0:
                                            return chooseLockPasswordFragment.getString(R.string.sec_lockpassword_password_recently_used);
                                        default:
                                            return chooseLockPasswordFragment.getString(R.string.sec_lockpassword_pin_recently_used);
                                    }
                                }
                            }));
                            break;
                        }
                    case 15:
                        if (this.mUnificationProfileId != -10000) {
                            PasswordPolicy pwdPolicy = getPwdPolicy(this.mUserId);
                            PasswordPolicy pwdPolicy2 = getPwdPolicy(this.mUnificationProfileId);
                            int maximumNumericSequenceLength = pwdPolicy.getMaximumNumericSequenceLength();
                            if (maximumNumericSequenceLength == 0) {
                                maximumNumericSequenceLength = pwdPolicy2.getMaximumNumericSequenceLength();
                            } else if (pwdPolicy2.getMaximumNumericSequenceLength() != 0 && maximumNumericSequenceLength > pwdPolicy2.getMaximumNumericSequenceLength()) {
                                maximumNumericSequenceLength = pwdPolicy2.getMaximumNumericSequenceLength();
                            }
                            arrayList.add(getString(R.string.password_must_not_contain_numbers_in_order, Integer.valueOf(maximumNumericSequenceLength + 1)));
                            break;
                        } else {
                            arrayList.add(getString(R.string.password_must_not_contain_numbers_in_order, Integer.valueOf(getPwdPolicy(this.mUserId).getMaximumNumericSequenceLength() + 1)));
                            break;
                        }
                        break;
                    case 16:
                        if (this.mUnificationProfileId != -10000) {
                            PasswordPolicy pwdPolicy3 = getPwdPolicy(this.mUserId);
                            PasswordPolicy pwdPolicy4 = getPwdPolicy(this.mUnificationProfileId);
                            int maximumCharacterSequenceLength = pwdPolicy3.getMaximumCharacterSequenceLength();
                            if (maximumCharacterSequenceLength == 0) {
                                maximumCharacterSequenceLength = pwdPolicy4.getMaximumCharacterSequenceLength();
                            } else if (pwdPolicy4.getMaximumCharacterSequenceLength() != 0 && maximumCharacterSequenceLength > pwdPolicy4.getMaximumCharacterSequenceLength()) {
                                maximumCharacterSequenceLength = pwdPolicy4.getMaximumCharacterSequenceLength();
                            }
                            arrayList.add(getString(R.string.password_must_not_contain_numbers_in_order, Integer.valueOf(maximumCharacterSequenceLength + 1)));
                            break;
                        } else {
                            arrayList.add(getString(R.string.password_must_not_contain_letters_in_order, Integer.valueOf(getPwdPolicy(this.mUserId).getMaximumCharacterSequenceLength() + 1)));
                            break;
                        }
                        break;
                    case 17:
                        if (this.mUnificationProfileId != -10000) {
                            PasswordPolicy pwdPolicy5 = getPwdPolicy(this.mUserId);
                            PasswordPolicy pwdPolicy6 = getPwdPolicy(this.mUnificationProfileId);
                            int minimumCharacterChangeLength = pwdPolicy5.getMinimumCharacterChangeLength();
                            if (minimumCharacterChangeLength == 0) {
                                minimumCharacterChangeLength = pwdPolicy6.getMinimumCharacterChangeLength();
                            } else if (pwdPolicy6.getMinimumCharacterChangeLength() != 0 && minimumCharacterChangeLength > pwdPolicy6.getMinimumCharacterChangeLength()) {
                                minimumCharacterChangeLength = pwdPolicy6.getMinimumCharacterChangeLength();
                            }
                            arrayList.add(getString(R.string.password_must_not_contain_numbers_in_order, Integer.valueOf(minimumCharacterChangeLength)));
                            break;
                        } else {
                            arrayList.add(getString(R.string.minimum_characters_should_be_changed_in_new_password, Integer.valueOf(getPwdPolicy(this.mUserId).getMinimumCharacterChangeLength())));
                            break;
                        }
                        break;
                    case 18:
                        if (this.mUnificationProfileId != -10000) {
                            PasswordPolicy pwdPolicy7 = getPwdPolicy(this.mUserId);
                            PasswordPolicy pwdPolicy8 = getPwdPolicy(this.mUnificationProfileId);
                            int maximumCharacterOccurences = pwdPolicy7.getMaximumCharacterOccurences();
                            if (maximumCharacterOccurences == 0) {
                                maximumCharacterOccurences = pwdPolicy8.getMaximumCharacterOccurences();
                            } else if (pwdPolicy8.getMaximumCharacterOccurences() != 0 && maximumCharacterOccurences > pwdPolicy8.getMaximumCharacterOccurences()) {
                                maximumCharacterOccurences = pwdPolicy8.getMaximumCharacterOccurences();
                            }
                            arrayList.add(getString(R.string.password_must_not_contain_numbers_in_order, Integer.valueOf(maximumCharacterOccurences)));
                            break;
                        } else {
                            arrayList.add(getString(R.string.maximum_characters_occurance_is_allowed_in_new_password, Integer.valueOf(getPwdPolicy(this.mUserId).getMaximumCharacterOccurences())));
                            break;
                        }
                    case 19:
                        arrayList.add(getString(R.string.lockpassword_password_failed_match_pattern));
                        break;
                    case 20:
                        arrayList.add(getString(this.mIsAlphaMode ? R.string.lockpassword_password_sequential_chars : R.string.sec_lockpassword_pin_sequential_nums));
                        break;
                    case 21:
                        arrayList.add(getString(this.mIsAlphaMode ? R.string.lockpassword_password_repeating_chars : R.string.sec_lockpassword_pin_repeating_nums));
                        break;
                    case 22:
                        arrayList.add(getString(R.string.password_must_not_contain_banned_words));
                        break;
                    default:
                        Log.wtf("ChooseLockPassword", "unknown error validating password: " + passwordValidationError);
                        break;
                }
            }
            return (String[]) arrayList.toArray(new String[0]);
        }

        public final void enableStatusBar$1() {
            synchronized (this) {
                try {
                    int i = this.mStatusBarDisableCount;
                    if (i > 0) {
                        int i2 = i - 1;
                        this.mStatusBarDisableCount = i2;
                        if (i2 == 0) {
                            this.mStatusBarManager.disable(0);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final String getDefaultPINMessage() {
            if (UCMUtils.isEnforcedCredentialStorageExistAsUser(this.mUserId) && UCMUtils.isSupportChangePin(this.mUserId)) {
                return getString(R.string.ucm_choose_lock_pin_recommended, Integer.valueOf(UCMUtils.mPinMinLength), Integer.valueOf(UCMUtils.mPinMaxLength));
            }
            if (this.mPasswordMinLength < 6) {
                return getResources().getString(R.string.sec_choose_lock_pin_recommended);
            }
            Resources resources = getResources();
            int i = this.mPasswordMinLength;
            return resources.getQuantityString(R.plurals.sec_choose_lock_pin_pin_too_short, i, Integer.valueOf(i));
        }

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 28;
        }

        public final PasswordPolicy getPwdPolicy(int i) {
            int i2;
            Log.secD("ChooseLockPassword", "getPwdPolicy () " + i);
            try {
                i2 = getActivity().getPackageManager().getPackageUidAsUser(SemPersonaManager.getAdminComponentName(i).getPackageName(), i);
            } catch (Exception e) {
                Log.secE("ChooseLockPassword", "Error fetching admin uid " + e.getMessage());
                i2 = i;
            }
            if (!SemPersonaManager.isKnoxId(i)) {
                EnterpriseDeviceManager enterpriseDeviceManager = EnterpriseDeviceManager.getInstance(getActivity());
                if (enterpriseDeviceManager != null) {
                    return enterpriseDeviceManager.getPasswordPolicy();
                }
                return null;
            }
            FragmentActivity activity = getActivity();
            ContextInfo contextInfo = new ContextInfo(i2, i);
            String str = KnoxUtils.mDeviceType;
            EnterpriseKnoxManager enterpriseKnoxManager = EnterpriseKnoxManager.getInstance();
            KnoxContainerManager knoxContainerManager = enterpriseKnoxManager == null ? null : enterpriseKnoxManager.getKnoxContainerManager(activity, contextInfo);
            if (knoxContainerManager != null) {
                return knoxContainerManager.getPasswordPolicy();
            }
            return null;
        }

        public Intent getRedactionInterstitialIntent(FragmentActivity fragmentActivity) {
            return RedactionInterstitial.createStartIntent(fragmentActivity, this.mUserId);
        }

        public final void handleCancel() {
            int i;
            int intExtra = getActivity().getIntent().getIntExtra("lockscreen.password_type", 0);
            boolean z = 131072 == intExtra || 196608 == intExtra;
            Stage stage = this.mUiStage;
            Stage stage2 = Stage.Introduction;
            if (stage == stage2) {
                LoggingHelper.insertEventLogging(28, z ? 4414 : 4416);
            } else {
                LoggingHelper.insertEventLogging(28, z ? 4415 : 4417);
            }
            if (!this.mIsChangePwdRequired) {
                getActivity().finish();
                return;
            }
            Stage stage3 = this.mUiStage;
            if (stage3 != stage2 || this.mPwdChangeEnforceStatus != 1 || (i = this.mPwdChangeTimeout) <= 0) {
                if (stage3 == Stage.NeedToConfirm || stage3 == Stage.ConfirmWrong) {
                    updateStage(stage2);
                    this.mPasswordEntry.setText(ApnSettings.MVNO_NONE);
                    return;
                }
                return;
            }
            int i2 = this.mUserId;
            this.mAlarmManager.set(2, (i * 60000) + SystemClock.elapsedRealtime(), PendingIntent.getBroadcastAsUser(getActivity().getApplicationContext(), 0, new Intent(PasswordPolicy.ACTION_PWD_CHANGE_TIMEOUT_INTERNAL), 268435456, new UserHandle(i2)));
            PasswordPolicy pwdPolicy = getPwdPolicy(i2);
            if (pwdPolicy == null) {
                Log.secD("ChooseLockPassword", "PasswordPolicy is NULL!");
            } else {
                pwdPolicy.setPwdChangeRequested(-1);
            }
            getActivity().setResult(0);
            getActivity().finish();
        }

        public final void handleNext$2() {
            boolean isEmpty;
            if (this.mSaveAndFinishWorker != null) {
                return;
            }
            Editable text = this.mPasswordEntry.getText();
            if (TextUtils.isEmpty(text)) {
                return;
            }
            boolean z = this.mFromSetupWizard;
            Stage stage = Stage.Introduction;
            if ((z && this.mIsApplyingSetupTheme && !this.mPrimaryButton.enabled) || (!z && !this.mFooterRightButton.isEnabled())) {
                if (this.mIsAlphaMode || this.mUiStage != stage || text.length() >= this.mPasswordMinLength) {
                    return;
                }
                Toast.makeText(getActivity(), getResources().getString(R.string.sec_choose_lock_pin_too_short_toast, Integer.valueOf(this.mPasswordMinLength), Integer.valueOf(this.mPasswordMinLength)), 1).show();
                return;
            }
            LockscreenCredential createPassword = this.mIsAlphaMode ? LockscreenCredential.createPassword(text) : LockscreenCredential.createPin(text);
            this.mChosenPassword = createPassword;
            Stage stage2 = this.mUiStage;
            if (stage2 == stage) {
                if (!validatePassword(createPassword)) {
                    this.mChosenPassword.zeroize();
                    return;
                }
                LockscreenCredential lockscreenCredential = this.mChosenPassword;
                if (!this.mIsAlphaMode && lockscreenCredential.getCredential().length <= 16) {
                    PasswordMetrics passwordMetrics = new PasswordMetrics(3);
                    passwordMetrics.length = 4;
                    passwordMetrics.seqLength = 3;
                    isEmpty = PasswordMetrics.validateCredential(passwordMetrics, this.mMinComplexity, lockscreenCredential).isEmpty();
                } else {
                    isEmpty = true;
                }
                if (isEmpty) {
                    refreshPasswordEntryToNeedToConfirm();
                    return;
                }
                AlertDialog alertDialog = this.mEasyPinAlertDialog;
                if (alertDialog == null || !alertDialog.isShowing()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage(R.string.sec_choose_lock_pin_easy_to_guess_body);
                    builder.P.mCancelable = true;
                    final int i = 0;
                    builder.setPositiveButton(R.string.sec_choose_lock_pin_easy_to_guess_use_anyway, new DialogInterface.OnClickListener(this) { // from class: com.android.settings.password.ChooseLockPassword$ChooseLockPasswordFragment$$ExternalSyntheticLambda4
                        public final /* synthetic */ ChooseLockPassword.ChooseLockPasswordFragment f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i2) {
                            int i3 = i;
                            ChooseLockPassword.ChooseLockPasswordFragment chooseLockPasswordFragment = this.f$0;
                            chooseLockPasswordFragment.getClass();
                            switch (i3) {
                                case 0:
                                    LoggingHelper.insertEventLogging(28, "LSE4446");
                                    ImeAwareEditText imeAwareEditText = chooseLockPasswordFragment.mPasswordEntry;
                                    if (imeAwareEditText != null) {
                                        imeAwareEditText.requestFocus();
                                        chooseLockPasswordFragment.mPasswordEntry.scheduleShowSoftInput();
                                    }
                                    chooseLockPasswordFragment.mEasyPinAlertDialog = null;
                                    chooseLockPasswordFragment.refreshPasswordEntryToNeedToConfirm();
                                    break;
                                default:
                                    LoggingHelper.insertEventLogging(28, "LSE4445");
                                    ImeAwareEditText imeAwareEditText2 = chooseLockPasswordFragment.mPasswordEntry;
                                    if (imeAwareEditText2 != null) {
                                        imeAwareEditText2.requestFocus();
                                        chooseLockPasswordFragment.mPasswordEntry.scheduleShowSoftInput();
                                    }
                                    chooseLockPasswordFragment.mChosenPassword.zeroize();
                                    chooseLockPasswordFragment.mPasswordEntry.setText(ApnSettings.MVNO_NONE);
                                    chooseLockPasswordFragment.mEasyPinAlertDialog = null;
                                    break;
                            }
                        }
                    });
                    final int i2 = 1;
                    builder.setNegativeButton(R.string.sec_choose_lock_pin_easy_to_guess_change_pin, new DialogInterface.OnClickListener(this) { // from class: com.android.settings.password.ChooseLockPassword$ChooseLockPasswordFragment$$ExternalSyntheticLambda4
                        public final /* synthetic */ ChooseLockPassword.ChooseLockPasswordFragment f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i22) {
                            int i3 = i2;
                            ChooseLockPassword.ChooseLockPasswordFragment chooseLockPasswordFragment = this.f$0;
                            chooseLockPasswordFragment.getClass();
                            switch (i3) {
                                case 0:
                                    LoggingHelper.insertEventLogging(28, "LSE4446");
                                    ImeAwareEditText imeAwareEditText = chooseLockPasswordFragment.mPasswordEntry;
                                    if (imeAwareEditText != null) {
                                        imeAwareEditText.requestFocus();
                                        chooseLockPasswordFragment.mPasswordEntry.scheduleShowSoftInput();
                                    }
                                    chooseLockPasswordFragment.mEasyPinAlertDialog = null;
                                    chooseLockPasswordFragment.refreshPasswordEntryToNeedToConfirm();
                                    break;
                                default:
                                    LoggingHelper.insertEventLogging(28, "LSE4445");
                                    ImeAwareEditText imeAwareEditText2 = chooseLockPasswordFragment.mPasswordEntry;
                                    if (imeAwareEditText2 != null) {
                                        imeAwareEditText2.requestFocus();
                                        chooseLockPasswordFragment.mPasswordEntry.scheduleShowSoftInput();
                                    }
                                    chooseLockPasswordFragment.mChosenPassword.zeroize();
                                    chooseLockPasswordFragment.mPasswordEntry.setText(ApnSettings.MVNO_NONE);
                                    chooseLockPasswordFragment.mEasyPinAlertDialog = null;
                                    break;
                            }
                        }
                    });
                    this.mEasyPinAlertDialog = builder.create();
                    hideSoftInput$2();
                    this.mEasyPinAlertDialog.show();
                    return;
                }
                return;
            }
            if (stage2 == Stage.NeedToConfirm) {
                if (!createPassword.equals(this.mFirstPassword)) {
                    Editable text2 = this.mPasswordEntry.getText();
                    if (text2 != null) {
                        Selection.setSelection(text2, 0, text2.length());
                    }
                    updateStage(Stage.ConfirmWrong);
                    this.mChosenPassword.zeroize();
                    return;
                }
                enableStatusBar$1();
                hideSoftInput$2();
                if (this.mFromAppLock || this.mForAppLockBackupKey) {
                    if (getActivity().getIntent().getBooleanExtra("applock_mQuality", false)) {
                        Log.i("ChooseLockPassword", "Save PIN for AppLock");
                    } else {
                        Log.i("ChooseLockPassword", "Save Password for AppLock");
                    }
                    getActivity().setResult(1);
                    getActivity().finish();
                }
                if (this.mSaveAndFinishWorker != null) {
                    Log.w("ChooseLockPassword", "startSaveAndFinish with an existing SaveAndFinishWorker.");
                    return;
                }
                View decorView = getActivity().getWindow().getDecorView();
                if (decorView.isAttachedToWindow() && decorView.getRootWindowInsets().isVisible(WindowInsets.Type.ime())) {
                    decorView.getWindowInsetsController().hide(WindowInsets.Type.ime());
                }
                this.mPasswordEntryInputDisabler.setInputEnabled(false);
                SaveAndFinishWorker saveAndFinishWorker = new SaveAndFinishWorker();
                this.mSaveAndFinishWorker = saveAndFinishWorker;
                saveAndFinishWorker.setListener(this);
                saveAndFinishWorker.mRequestGatekeeperPassword = this.mRequestGatekeeperPassword;
                saveAndFinishWorker.mRequestWriteRepairModePassword = this.mRequestWriteRepairModePassword;
                FragmentManager fragmentManager = getFragmentManager();
                BackStackRecord m = DialogFragment$$ExternalSyntheticOutline0.m(fragmentManager, fragmentManager);
                m.doAddOp(0, this.mSaveAndFinishWorker, "save_and_finish_worker", 1);
                m.commitInternal(false);
                getFragmentManager().executePendingTransactions();
                Intent intent = getActivity().getIntent();
                if (this.mUnificationProfileId != -10000) {
                    LockscreenCredential parcelableExtra = intent.getParcelableExtra("unification_profile_credential");
                    try {
                        SaveAndFinishWorker saveAndFinishWorker2 = this.mSaveAndFinishWorker;
                        saveAndFinishWorker2.mUnificationProfileId = this.mUnificationProfileId;
                        saveAndFinishWorker2.mUnificationProfileCredential = parcelableExtra.duplicate();
                        parcelableExtra.close();
                    } catch (Throwable th) {
                        if (parcelableExtra != null) {
                            try {
                                parcelableExtra.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                }
                LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
                CheckBox checkBox = this.mAutoPinConfirmOption;
                lockPatternUtils.setAutoPinConfirm(checkBox != null && checkBox.isChecked(), this.mUserId);
                this.mSaveAndFinishWorker.start(this.mLockPatternUtils, this.mChosenPassword, this.mCurrentCredential, this.mUserId);
            }
        }

        public final void hideSoftInput$2() {
            ImeAwareEditText imeAwareEditText;
            if (this.mImm == null || (imeAwareEditText = this.mPasswordEntry) == null || !imeAwareEditText.hasFocus()) {
                return;
            }
            this.mImm.hideSoftInputFromWindow(this.mPasswordEntry.getWindowToken(), 0);
        }

        @Override // androidx.fragment.app.Fragment
        public final void onActivityResult(int i, int i2, Intent intent) {
            super.onActivityResult(i, i2, intent);
            if (i != 58) {
                return;
            }
            if (i2 == -1) {
                this.mCurrentCredential = intent.getParcelableExtra(HostAuth.PASSWORD);
            } else {
                getActivity().setResult(1);
                getActivity().finish();
            }
        }

        @Override // com.android.settings.password.SaveAndFinishWorker.Listener
        public final void onChosenLockSaveFinished(Intent intent, boolean z) {
            Intent fmmService;
            Settings.Secure.putIntForUser(getActivity().getContentResolver(), "is_smpw_key", this.mIsSimplePassword ? 1 : 0, this.mUserId);
            if (!KnoxUtils.isMultifactorAuthEnforced(getActivity(), this.mUserId)) {
                this.mAlarmManager.cancel(PendingIntent.getBroadcastAsUser(getActivity().getApplicationContext(), 0, new Intent(PasswordPolicy.ACTION_PWD_CHANGE_TIMEOUT_INTERNAL), 335544320, new UserHandle(UserHandle.myUserId())));
                PasswordPolicy pwdPolicy = getPwdPolicy(UserHandle.myUserId());
                if (pwdPolicy == null) {
                    Log.secD("ChooseLockPassword", "PasswordPolicy is NULL!");
                } else if (!DualDarManager.isOnDeviceOwnerEnabled()) {
                    pwdPolicy.setPwdChangeRequested(0);
                }
            }
            enableStatusBar$1();
            if (!this.mFromSetupWizard && !this.mFromPrevPassword) {
                try {
                    if (LockUtils.isFmmUnlockAllowed(getContext(), this.mUserId, this.mLockTypePolicy.isEnterpriseUser()) && (fmmService = LockUtils.getFmmService(this.mChosenPassword, getContext())) != null) {
                        getActivity().startService(fmmService);
                    }
                } catch (Exception e) {
                    Log.e("ChooseLockPassword", e.toString());
                }
            }
            this.mPasswordEntry.setText(ApnSettings.MVNO_NONE);
            LockTypePolicy lockTypePolicy = this.mLockTypePolicy;
            if (lockTypePolicy.mIsOrganizationOwned || lockTypePolicy.mIsManagedProfile) {
                Intent chooseLockHintSettingsIntent = LockUtils.getChooseLockHintSettingsIntent(getActivity(), this.mUserId);
                if (chooseLockHintSettingsIntent != null) {
                    chooseLockHintSettingsIntent.putExtra(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_SECRET_KEY, (Parcelable) this.mChosenPassword);
                    startActivity(chooseLockHintSettingsIntent);
                }
            } else {
                if (this.mFromPrevPassword) {
                    LockUtils.resetHint(getContext(), this.mUserId);
                    Settings.Secure.putInt(getContext().getContentResolver(), "reset_credential_from_previous", 1);
                }
                int i = Settings.Secure.getInt(getContext().getContentResolver(), "save_previous_credential_description_show_count", 0);
                if (!z || this.mFromPrevPassword || i >= 3) {
                    Intent chooseLockHintSettingsIntent2 = this.mFromPrevPassword ? null : LockUtils.getChooseLockHintSettingsIntent(getActivity(), this.mUserId);
                    if (chooseLockHintSettingsIntent2 != null) {
                        chooseLockHintSettingsIntent2.putExtra(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_SECRET_KEY, (Parcelable) this.mChosenPassword);
                        startActivity(chooseLockHintSettingsIntent2);
                    }
                } else {
                    FragmentActivity activity = getActivity();
                    int i2 = this.mUserId;
                    String str = LockUtils.CONFIG_LOCK_SCREEN_CLOCK_DISPLAY_POLICY;
                    int i3 = PreviousPasswordDescriptionActivity.$r8$clinit;
                    Intent putExtra = new Intent(activity, (Class<?>) PreviousPasswordDescriptionActivity.class).putExtra("android.intent.extra.USER_ID", i2);
                    if (putExtra != null) {
                        Settings.Secure.putInt(getContext().getContentResolver(), "save_previous_credential_description_show_count", i + 1);
                        if (LockUtils.getChooseLockHintSettingsIntent(getActivity(), this.mUserId) != null) {
                            putExtra.putExtra(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_SECRET_KEY, (Parcelable) this.mChosenPassword);
                        }
                        startActivity(putExtra);
                    }
                }
            }
            if (!z && !this.mForAppLockBackupKey && !this.mForFingerprint && !this.mForFace && !this.mIsFromKnoxTwoStep && !KnoxUtils.isDualDarDoInnerAuthUser(getActivity(), this.mUserId) && !this.mFromPrevPassword && getRedactionInterstitialIntent(getActivity()) != null) {
                if (intent == null) {
                    intent = new Intent();
                }
                intent.putExtra("need_to_launch_ls_notification", true);
            }
            getActivity().setResult(1, intent);
            LockscreenCredential lockscreenCredential = this.mChosenPassword;
            if (lockscreenCredential != null && !this.mIsFromKnoxTwoStep) {
                lockscreenCredential.zeroize();
            }
            LockscreenCredential lockscreenCredential2 = this.mCurrentCredential;
            if (lockscreenCredential2 != null) {
                lockscreenCredential2.zeroize();
            }
            LockscreenCredential lockscreenCredential3 = this.mFirstPassword;
            if (lockscreenCredential3 != null) {
                lockscreenCredential3.zeroize();
            }
            GlifLayout glifLayout = this.mLayout;
            if (glifLayout != null) {
                glifLayout.announceForAccessibility(getString(R.string.accessibility_setup_password_complete));
            }
            if (!z && this.mIsFromKnoxTwoStep) {
                ((TrustManager) getActivity().getSystemService("trust")).setDeviceLockedForUser(UserHandle.myUserId(), false);
            }
            getActivity().finish();
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            int id = view.getId();
            if (id == R.id.next_button) {
                handleNext$2();
                return;
            }
            if (id == R.id.cancel_button) {
                handleCancel();
                return;
            }
            if (id == R.id.emergencyCall) {
                if (ActivityManager.getCurrentUser() != UserHandle.OWNER.getIdentifier()) {
                    return;
                }
                Intent intent = new Intent("com.samsung.android.app.telephonyui.action.OPEN_EMERGENCY_DIALER");
                intent.setFlags(276824064);
                intent.putExtra("enable_ice_contact_list", false);
                intent.putExtra("enable_emergency_medical_info", false);
                try {
                    getActivity().getApplicationContext().startActivity(intent);
                    return;
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    return;
                }
            }
            if (id == R.id.password_show_btn) {
                int selectionEnd = this.mPasswordEntry.getSelectionEnd();
                if (this.mIsPasswordShown) {
                    this.mPasswordShowButton.setForeground(getResources().getDrawable(R.drawable.sec_lock_setting_btn_password_hide_mtrl, null));
                    this.mPasswordEntry.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    this.mPasswordShowButton.setContentDescription(getString(R.string.sec_lock_screen_password_show_button));
                    this.mIsPasswordShown = false;
                } else {
                    this.mPasswordShowButton.setForeground(getResources().getDrawable(R.drawable.sec_lock_setting_btn_password_show_mtrl, null));
                    this.mPasswordEntry.setTransformationMethod((TransformationMethod) null);
                    this.mPasswordShowButton.setContentDescription(getString(R.string.sec_lock_screen_password_hide_button));
                    this.mIsPasswordShown = true;
                }
                try {
                    this.mPasswordEntry.setSelection(selectionEnd);
                } catch (IndexOutOfBoundsException e2) {
                    e2.printStackTrace();
                }
            }
        }

        @Override // com.android.settingslib.core.lifecycle.ObservableFragment, androidx.fragment.app.Fragment
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            this.mLockPatternUtils = new LockPatternUtils(getActivity());
            Intent intent = getActivity().getIntent();
            if (!(getActivity() instanceof ChooseLockPassword)) {
                throw new SecurityException("Fragment contained in wrong activity");
            }
            this.mImm = (InputMethodManager) getActivity().getSystemService(InputMethodManager.class);
            this.mUserId = Utils.getUserIdFromBundle(getActivity(), intent.getExtras(), false);
            if (!KnoxUtils.isDualDarDoInnerAuthUser(getActivity(), this.mUserId)) {
                UserManager userManager = (UserManager) getContext().createContextAsUser(UserHandle.of(this.mUserId), 0).getSystemService(UserManager.class);
                if (!userManager.isManagedProfile() && (!Flags.allowPrivateProfile() || !android.multiuser.Flags.enablePrivateSpaceFeatures() || !userManager.isPrivateProfile())) {
                    userManager.isProfile();
                }
            }
            this.mForFingerprint = intent.getBooleanExtra("for_fingerprint", false);
            this.mForFace = intent.getBooleanExtra("for_face", false);
            intent.getBooleanExtra("for_biometrics", false);
            this.mPasswordType = intent.getIntExtra("lockscreen.password_type", 131072);
            this.mUnificationProfileId = intent.getIntExtra("unification_profile_id", -10000);
            this.mMinComplexity = intent.getIntExtra("min_complexity", 0);
            PasswordMetrics parcelableExtra = intent.getParcelableExtra("min_metrics");
            this.mMinMetrics = parcelableExtra;
            if (parcelableExtra == null) {
                this.mMinMetrics = new PasswordMetrics(-1);
            }
            this.mTextChangedHandler = new TextChangedHandler();
            this.mLockTypePolicy = new LockTypePolicy(this.mUserId, getActivity(), intent);
            this.mFromAppLock = intent.getBooleanExtra("from_applock", false);
            this.mFromSetupWizard = intent.getBooleanExtra("fromSetupWizard", false);
            Log.d("ChooseLockPassword", "mForFingerprint : " + this.mForFingerprint + ", mForFace : " + this.mForFace);
            this.mForAppLockBackupKey = intent.getStringExtra("forAppLockBackupKey") != null;
            this.mKnoxEnforcePassword = intent.getBooleanExtra("knoxEnforcePassword", false);
            this.mFromPrevPassword = intent.getBooleanExtra("previous_credential", false);
            if (intent.getAction() != null) {
                this.mIsChangePwdRequired = intent.getAction().equals("com.samsung.app.action.CHANGE_DEVICE_PASSWORD");
            }
            if (!this.mLockPatternUtils.getDevicePolicyManager().semIsSimplePasswordEnabled(null)) {
                this.mCheckSimplePassword = true;
            }
            Log.d("ChooseLockPassword", "mCheckSimplePassword = " + this.mCheckSimplePassword);
            this.mIsUnlockRecovery = Settings.System.getInt(getContext().getContentResolver(), "fmm_unlock_recovery", 0) != 0;
            this.mOldPassword = intent.getParcelableExtra("lockscreen.password_old");
            this.mPasswordMinLength = Math.max(this.mPasswordMinLength, 4);
            this.mIsApplyingTabletGui = LockUtils.isApplyingTabletGUI(getContext());
            this.mIsApplyingSetupTheme = LockUtils.isApplyingSetupTheme(getContext());
            this.mAlarmManager = (AlarmManager) getActivity().getSystemService("alarm");
            this.mStatusBarManager = (StatusBarManager) getActivity().getSystemService("statusbar");
            if (UCMUtils.isEnforcedCredentialStorageExistAsUser(this.mUserId) && UCMUtils.isSupportChangePin(this.mUserId)) {
                this.mPasswordMinLength = UCMUtils.mPinMinLength;
                this.mPasswordMaxLength = UCMUtils.mPinMaxLength;
            }
            if (this.mLockTypePolicy.isWorkDeviceOrProfile()) {
                getActivity().getWindow().addFlags(8192);
            }
            Log.d("ChooseLockPassword.SDP", "onCreate - StreamCipher initialized with handle(" + StreamCipher.getInstance().issueKeyStream() + ")");
            if (this.mLockTypePolicy.isEnterpriseUser() || (UCMUtils.isEnforcedCredentialStorageExistAsUser(this.mUserId) && UCMUtils.isSupportChangePin(this.mUserId))) {
                boolean booleanExtra = intent.getBooleanExtra("is_knox_password_secured", false);
                Log.d("ChooseLockPassword.SDP", "onCreate - Password secured for user " + this.mUserId + " : " + booleanExtra);
                if (booleanExtra) {
                    this.mOldPassword = KnoxUtils.restoreCipherPassword(this.mOldPassword, KnoxUtils.getCipherPublicHandle());
                }
            }
            this.mIsFromKnoxTwoStep = intent.getBooleanExtra("is_knox_two_step", false);
            getActivity().registerReceiver(this.mScreenOffReceiver, new IntentFilter("android.intent.action.SCREEN_OFF"));
            PasswordPolicy pwdPolicy = getPwdPolicy(this.mUserId);
            if (pwdPolicy == null) {
                Log.secD("ChooseLockPassword", "PasswordPolicy is NULL!");
            } else {
                pwdPolicy.getRequiredPwdPatternRestrictions(true);
            }
            this.mPwdChangeEnforceStatus = pwdPolicy != null ? KnoxUtils.isDualDarDoInnerAuthUser(getActivity(), this.mUserId) ? pwdPolicy.isChangeRequestedForInner() : pwdPolicy.isChangeRequested() : 0;
            if (pwdPolicy != null) {
                this.mPwdChangeTimeout = pwdPolicy.getPasswordChangeTimeout();
            }
        }

        @Override // androidx.fragment.app.Fragment
        public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            View inflate = this.mIsApplyingTabletGui ? layoutInflater.inflate(R.layout.sec_choose_lock_password_tablet, viewGroup, false) : (this.mFromSetupWizard && this.mIsApplyingSetupTheme) ? layoutInflater.inflate(R.layout.sec_setup_choose_lock_password, viewGroup, false) : layoutInflater.inflate(R.layout.sec_choose_lock_password, viewGroup, false);
            getActivity().getWindow().setDecorFitsSystemWindows(false);
            View findViewById = getActivity().findViewById(android.R.id.content);
            LockContentViewInsetsCallback lockContentViewInsetsCallback = new LockContentViewInsetsCallback(findViewById, WindowInsets.Type.systemBars(), WindowInsets.Type.ime());
            findViewById.setWindowInsetsAnimationCallback(lockContentViewInsetsCallback);
            findViewById.setOnApplyWindowInsetsListener(lockContentViewInsetsCallback);
            return inflate;
        }

        @Override // com.android.settingslib.core.lifecycle.ObservableFragment, androidx.fragment.app.Fragment
        public final void onDestroy() {
            super.onDestroy();
            LockscreenCredential lockscreenCredential = this.mCurrentCredential;
            if (lockscreenCredential != null) {
                lockscreenCredential.zeroize();
                this.mCurrentCredential = null;
            }
            System.gc();
            System.runFinalization();
            System.gc();
            if (this.mLockTypePolicy.isEnterpriseUser()) {
                boolean z = KnoxUtils.mHasChooseLockResult;
                AirplaneModeEnabler$$ExternalSyntheticOutline0.m("onDestroy() - Has ChooseLock result? ", "ChooseLockPassword.SDP", z);
                if (z) {
                    LockscreenCredential lockscreenCredential2 = this.mChosenPassword;
                    if (lockscreenCredential2 != null) {
                        lockscreenCredential2.zeroize();
                    }
                    LockscreenCredential lockscreenCredential3 = this.mFirstPassword;
                    if (lockscreenCredential3 != null) {
                        lockscreenCredential3.zeroize();
                    }
                    LockscreenCredential lockscreenCredential4 = this.mOldPassword;
                    if (lockscreenCredential4 != null) {
                        lockscreenCredential4.zeroize();
                    }
                    ImeAwareEditText imeAwareEditText = this.mPasswordEntry;
                    if (imeAwareEditText != null) {
                        imeAwareEditText.setText(ApnSettings.MVNO_NONE);
                    }
                }
            }
        }

        @Override // android.widget.TextView.OnEditorActionListener
        public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i != 0 && i != 6 && i != 5) {
                return false;
            }
            handleNext$2();
            return true;
        }

        @Override // androidx.fragment.app.Fragment
        public final void onMultiWindowModeChanged(boolean z) {
            super.onMultiWindowModeChanged(z);
            if (z && isResumed()) {
                if (Rune.isSamsungDexMode(getActivity())) {
                    Log.d("ChooseLockPassword", "If Dex stand alone mode, enable multi window");
                } else {
                    Toast.makeText(getActivity(), getString(R.string.lock_screen_doesnt_support_multi_window_text), 1).show();
                    getActivity().finish();
                }
            }
        }

        @Override // com.android.settingslib.core.lifecycle.ObservableFragment, androidx.fragment.app.Fragment
        public final void onPause() {
            SaveAndFinishWorker saveAndFinishWorker = this.mSaveAndFinishWorker;
            if (saveAndFinishWorker != null) {
                saveAndFinishWorker.setListener(null);
            }
            if (!this.mLockPatternUtils.isSecure(this.mUserId) || getActivity().isChangingConfigurations()) {
                hideSoftInput$2();
                enableStatusBar$1();
            } else {
                Intent intent = new Intent();
                intent.putExtra("screen_lock_force_destroy", true);
                getActivity().setResult(0, intent);
                getActivity().finish();
            }
            super.onPause();
        }

        @Override // com.android.settings.core.InstrumentedFragment, com.android.settingslib.core.lifecycle.ObservableFragment, androidx.fragment.app.Fragment
        public final void onResume() {
            super.onResume();
            if (!Rune.isSamsungDexMode(getActivity()) && LockUtils.isInMultiWindow(getActivity())) {
                Toast.makeText(getActivity(), getString(R.string.lock_screen_doesnt_support_multi_window_text), 1).show();
                getActivity().finish();
                return;
            }
            updateStage(this.mUiStage);
            SaveAndFinishWorker saveAndFinishWorker = this.mSaveAndFinishWorker;
            if (saveAndFinishWorker != null) {
                saveAndFinishWorker.setListener(this);
            } else {
                this.mPasswordEntry.requestFocus();
                this.mPasswordEntry.scheduleShowSoftInput();
            }
            if (this.mIsChangePwdRequired) {
                this.mHeaderText.setText(R.string.sec_choose_lock_password_expired);
            }
            PasswordPolicy pwdPolicy = getPwdPolicy(this.mUserId);
            if (pwdPolicy == null) {
                Log.secD("ChooseLockPassword", "PasswordPolicy is NULL!");
                return;
            }
            if (pwdPolicy.isPasswordVisibilityEnabled()) {
                if (this.mIsAlphaMode) {
                    this.mPasswordShowButton.setVisibility(0);
                }
            } else {
                this.mPasswordShowButton.setVisibility(8);
                this.mPasswordShowButton.setForeground(getResources().getDrawable(R.drawable.sec_lock_setting_btn_password_hide_mtrl, null));
                this.mPasswordEntry.setTransformationMethod(PasswordTransformationMethod.getInstance());
                this.mPasswordShowButton.setContentDescription(getString(R.string.sec_lock_screen_password_show_button));
                this.mIsPasswordShown = false;
            }
        }

        @Override // com.android.settingslib.core.lifecycle.ObservableFragment, androidx.fragment.app.Fragment
        public final void onSaveInstanceState(Bundle bundle) {
            Parcelable parcelable;
            boolean z;
            super.onSaveInstanceState(bundle);
            bundle.putString("ui_stage", this.mUiStage.name());
            bundle.putBoolean("password_shown", this.mIsPasswordShown);
            Parcelable parcelable2 = null;
            if (this.mLockTypePolicy.isEnterpriseUser() || (UCMUtils.isEnforcedCredentialStorageExistAsUser(this.mUserId) && UCMUtils.isSupportChangePin(this.mUserId))) {
                Preference$$ExternalSyntheticOutline0.m(new StringBuilder("onSaveInstanceState - Secured password required for user "), this.mUserId, "ChooseLockPassword.SDP");
                Parcelable cipher = KnoxUtils.getCipher(this.mFirstPassword, KnoxUtils.getCipherPublicHandle());
                LockscreenCredential lockscreenCredential = this.mCurrentCredential;
                if (lockscreenCredential != null && !lockscreenCredential.isNone()) {
                    parcelable2 = KnoxUtils.getCipher(this.mCurrentCredential, KnoxUtils.getCipherPublicHandle());
                }
                bundle.putBoolean("is_knox_password_secured", true);
                parcelable = parcelable2;
                parcelable2 = cipher;
                z = true;
            } else {
                z = false;
                parcelable = null;
            }
            if (z) {
                bundle.putParcelable("first_password", parcelable2);
                bundle.putParcelable("current_credential", parcelable);
            } else {
                bundle.putParcelable("first_password", this.mFirstPassword);
                LockscreenCredential lockscreenCredential2 = this.mCurrentCredential;
                if (lockscreenCredential2 != null) {
                    bundle.putParcelable("current_credential", lockscreenCredential2.duplicate());
                }
            }
            bundle.putBoolean("auto_confirm_option_set_manually", this.mIsAutoPinConfirmOptionSetManually);
        }

        /* JADX WARN: Removed duplicated region for block: B:122:0x0417  */
        /* JADX WARN: Removed duplicated region for block: B:148:0x0501  */
        /* JADX WARN: Removed duplicated region for block: B:154:0x051e  */
        /* JADX WARN: Removed duplicated region for block: B:159:0x05de  */
        /* JADX WARN: Removed duplicated region for block: B:187:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:188:0x054f  */
        /* JADX WARN: Removed duplicated region for block: B:62:0x02b1  */
        /* JADX WARN: Removed duplicated region for block: B:70:0x02fe  */
        @Override // androidx.fragment.app.Fragment
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onViewCreated(android.view.View r17, android.os.Bundle r18) {
            /*
                Method dump skipped, instructions count: 1667
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.settings.password.ChooseLockPassword.ChooseLockPasswordFragment.onViewCreated(android.view.View, android.os.Bundle):void");
        }

        public final void refreshPasswordEntryToNeedToConfirm() {
            this.mFooterRightButton.setEnabled(false);
            Menu menu = this.mMenu;
            if (menu != null) {
                menu.findItem(2).setEnabled(false);
            }
            this.mPasswordEntry.setPrivateImeOptions("disableToolbar=true;disableEnterKey=true;lockScreenPasswordField=true;");
            this.mPasswordEntry.setFilters(new InputFilter[]{this.MaxLengthFilter, new InputFilter.LengthFilter(this.mPasswordMaxLength)});
            this.mPasswordEntryInputDisabler = new TextViewInputDisabler(this.mPasswordEntry);
            this.mFirstPassword = this.mChosenPassword;
            updateStage(Stage.NeedToConfirm);
            this.mPasswordEntry.setText(ApnSettings.MVNO_NONE);
            this.mPasswordEntry.requestFocus();
            this.mPasswordEntry.requestAccessibilityFocus();
            this.mAutoPinConfirmOption.setVisibility(8);
        }

        public final void setAutoPinConfirmOption(int i, boolean z) {
            if (!LockPatternUtils.isAutoPinConfirmFeatureAvailable() || this.mAutoPinConfirmOption == null || UCMUtils.isUCMKeyguardEnabledAsUser(this.mUserId)) {
                return;
            }
            if (!z || this.mIsAlphaMode || i < 6) {
                this.mAutoPinConfirmOption.setVisibility(8);
                this.mAutoPinConfirmOption.setChecked(false);
            } else {
                this.mAutoPinConfirmOption.setVisibility(0);
                if (this.mIsAutoPinConfirmOptionSetManually) {
                    return;
                }
                this.mAutoPinConfirmOption.setChecked(i >= 6);
            }
        }

        public final void setNextEnabled(boolean z) {
            Menu menu = this.mMenu;
            if (menu != null) {
                menu.findItem(2).setEnabled(z);
            }
            if (!this.mFromSetupWizard || !this.mIsApplyingSetupTheme) {
                this.mFooterRightButton.setEnabled(z);
            } else {
                this.mPrimaryButton.setEnabled(z);
                this.mMixin.getPrimaryButtonView().setTextColor(getResources().getColor(R.color.sswl_bottom_primary_button_text_color, null));
            }
        }

        public final void updateStage(Stage stage) {
            TextView textView;
            if (Rune.isVzwDemoMode(getActivity())) {
                stage = Stage.Introduction;
            }
            Stage stage2 = this.mUiStage;
            this.mUiStage = stage;
            updateUi();
            if (stage2 == stage || (textView = this.mHeaderText) == null) {
                return;
            }
            textView.announceForAccessibility(textView.getText());
        }

        public void updateUi() {
            String string;
            String defaultPINMessage;
            String defaultPINMessage2;
            boolean z = this.mSaveAndFinishWorker == null;
            LockscreenCredential createPassword = this.mIsAlphaMode ? LockscreenCredential.createPassword(this.mPasswordEntry.getText()) : LockscreenCredential.createPin(this.mPasswordEntry.getText());
            int size = createPassword.size();
            Stage stage = this.mUiStage;
            if (stage != Stage.Introduction) {
                if (stage == Stage.NeedToConfirm || stage == Stage.ConfirmWrong) {
                    this.mHeaderText.setText(this.mIsAlphaMode ? stage.alphaHint : stage.numericHint);
                } else {
                    if (!this.mIsAlphaMode) {
                        string = getDefaultPINMessage();
                    } else if (this.mLockPatternUtils.getRequestedMinimumPasswordLength(UserHandle.myUserId()) == 0) {
                        Resources resources = getResources();
                        int i = this.mPasswordMinLength;
                        string = resources.getQuantityString(R.plurals.sec_choose_lock_password_password_too_short, i, Integer.valueOf(i));
                    } else {
                        string = getString(R.string.sec_choose_lock_password_password_policy_too_short, Integer.valueOf(this.mPasswordMinLength));
                    }
                    this.mHeaderText.setText(string);
                }
                setNextEnabled(z && size > 0);
            } else if (size <= 0) {
                boolean validatePassword = validatePassword(createPassword);
                convertErrorCodeToMessages();
                setAutoPinConfirmOption(size, validatePassword);
                if (this.mIsAlphaMode) {
                    Resources resources2 = getResources();
                    int i2 = this.mPasswordMinLength;
                    defaultPINMessage = resources2.getQuantityString(R.plurals.sec_choose_lock_password_password_too_short, i2, Integer.valueOf(i2));
                } else {
                    defaultPINMessage = getDefaultPINMessage();
                }
                this.mHeaderText.setText(defaultPINMessage);
                setNextEnabled(validatePassword);
            } else if (size < this.mPasswordMinLength) {
                if (this.mIsAlphaMode) {
                    Resources resources3 = getResources();
                    int i3 = this.mPasswordMinLength;
                    defaultPINMessage2 = resources3.getQuantityString(R.plurals.sec_choose_lock_password_password_too_short, i3, Integer.valueOf(i3));
                } else {
                    defaultPINMessage2 = getDefaultPINMessage();
                }
                this.mHeaderText.setText(defaultPINMessage2);
                setNextEnabled(false);
            } else {
                boolean validatePassword2 = validatePassword(createPassword);
                setAutoPinConfirmOption(size, validatePassword2);
                if (validatePassword2) {
                    this.mHeaderText.setText(R.string.sec_choose_lock_password_press_continue);
                    setNextEnabled(true);
                } else {
                    this.mHeaderText.setText(String.join("\n", convertErrorCodeToMessages()));
                    setNextEnabled(false);
                }
            }
            int i4 = this.mUiStage.buttonText;
            Menu menu = this.mMenu;
            if (menu != null) {
                menu.findItem(2).setTitle(i4);
            }
            if (this.mFromSetupWizard && this.mIsApplyingSetupTheme) {
                this.mPrimaryButton.setText(getString(i4));
            } else {
                this.mFooterRightButton.setText(i4);
            }
            this.mPasswordEntryInputDisabler.setInputEnabled(z);
            createPassword.zeroize();
        }

        @VisibleForTesting
        public boolean validatePassword(LockscreenCredential lockscreenCredential) {
            byte[] credential = lockscreenCredential.getCredential();
            this.mValidationErrors = PasswordMetrics.validateCredential(this.mMinMetrics, this.mMinComplexity, lockscreenCredential);
            Editable text = this.mPasswordEntry.getText();
            boolean z = false;
            if (text != null) {
                for (int i = 0; i < text.length(); i++) {
                    char charAt = text.charAt(i);
                    if (charAt < ' ' || charAt > 127) {
                        this.mValidationErrors = Collections.singletonList(new PasswordValidationError(2, 0));
                        break;
                    }
                }
            }
            if (this.mValidationErrors.isEmpty()) {
                LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
                byte[] credential2 = lockscreenCredential.getCredential();
                if (this.mPasswordHistoryHashFactor == null) {
                    LockPatternUtils lockPatternUtils2 = this.mLockPatternUtils;
                    LockscreenCredential lockscreenCredential2 = this.mCurrentCredential;
                    if (lockscreenCredential2 == null) {
                        lockscreenCredential2 = LockscreenCredential.createNone();
                    }
                    this.mPasswordHistoryHashFactor = lockPatternUtils2.getPasswordHistoryHashFactor(lockscreenCredential2, this.mFromPrevPassword ? -9899 : this.mUserId);
                }
                if (lockPatternUtils.checkPasswordHistory(credential2, this.mPasswordHistoryHashFactor, this.mUserId)) {
                    this.mValidationErrors = Collections.singletonList(new PasswordValidationError(14));
                }
            }
            String str = ApnSettings.MVNO_NONE;
            String str2 = ApnSettings.MVNO_NONE;
            for (byte b : credential) {
                StringBuilder m = EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(str2);
                m.append((char) b);
                str2 = m.toString();
            }
            if (this.mUnificationProfileId != -10000) {
                PasswordPolicy pwdPolicy = getPwdPolicy(this.mUserId);
                PasswordPolicy pwdPolicy2 = getPwdPolicy(this.mUnificationProfileId);
                if (pwdPolicy != null && pwdPolicy2 != null && (pwdPolicy.getMaximumNumericSequenceLength() != 0 || pwdPolicy2.getMaximumNumericSequenceLength() != 0 || pwdPolicy.getForbiddenStrings(true) != null || pwdPolicy2.getForbiddenStrings(true) != null || pwdPolicy.getMaximumCharacterOccurences() != 0 || pwdPolicy2.getMaximumCharacterOccurences() != 0 || pwdPolicy.getMaximumCharacterSequenceLength() != 0 || pwdPolicy2.getMaximumCharacterSequenceLength() != 0 || pwdPolicy.getMinimumCharacterChangeLength() != 0 || pwdPolicy2.getMinimumCharacterChangeLength() != 0 || pwdPolicy.getRequiredPwdPatternRestrictions(true) != null || pwdPolicy2.getRequiredPwdPatternRestrictions(true) != null)) {
                    LockscreenCredential lockscreenCredential3 = this.mCurrentCredential;
                    if (lockscreenCredential3 != null) {
                        for (byte b2 : lockscreenCredential3.getCredential()) {
                            StringBuilder m2 = EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(str);
                            m2.append((char) b2);
                            str = m2.toString();
                        }
                    }
                    if ((pwdPolicy.hasForbiddenNumericSequence(str2) || pwdPolicy2.hasForbiddenNumericSequence(str2)) && this.mValidationErrors.isEmpty()) {
                        this.mValidationErrors.add(new PasswordValidationError(15));
                    }
                    if ((pwdPolicy.hasForbiddenCharacterSequence(str2) || pwdPolicy2.hasForbiddenCharacterSequence(str2)) && this.mValidationErrors.isEmpty()) {
                        this.mValidationErrors.add(new PasswordValidationError(16));
                    }
                    if ((pwdPolicy.hasForbiddenStringDistance(str2, str) || pwdPolicy2.hasForbiddenStringDistance(str2, str)) && this.mValidationErrors.isEmpty()) {
                        this.mValidationErrors.add(new PasswordValidationError(17));
                    }
                    if ((pwdPolicy.hasForbiddenData(str2) || pwdPolicy2.hasForbiddenData(str2)) && this.mValidationErrors.isEmpty()) {
                        this.mValidationErrors.add(new PasswordValidationError(22));
                    }
                    if ((pwdPolicy.hasMaxRepeatedCharacters(str2) || pwdPolicy2.hasMaxRepeatedCharacters(str2)) && this.mValidationErrors.isEmpty()) {
                        this.mValidationErrors.add(new PasswordValidationError(18));
                    }
                    if ((!pwdPolicy.isPasswordPatternMatched(str2) || !pwdPolicy2.isPasswordPatternMatched(str2)) && this.mValidationErrors.isEmpty()) {
                        this.mValidationErrors.add(new PasswordValidationError(19));
                    }
                }
            } else {
                PasswordPolicy pwdPolicy3 = getPwdPolicy(this.mUserId);
                if (pwdPolicy3 == null) {
                    Log.secD("ChooseLockPassword", "PasswordPolicy is NULL!");
                    return this.mValidationErrors.isEmpty();
                }
                if (pwdPolicy3.getMaximumNumericSequenceLength() != 0 || pwdPolicy3.getForbiddenStrings(true) != null || pwdPolicy3.getMaximumCharacterOccurences() != 0 || pwdPolicy3.getMaximumCharacterSequenceLength() != 0 || pwdPolicy3.getMinimumCharacterChangeLength() != 0 || pwdPolicy3.getRequiredPwdPatternRestrictions(true) != null) {
                    LockscreenCredential lockscreenCredential4 = this.mCurrentCredential;
                    if (lockscreenCredential4 != null) {
                        for (byte b3 : lockscreenCredential4.getCredential()) {
                            StringBuilder m3 = EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(str);
                            m3.append((char) b3);
                            str = m3.toString();
                        }
                    }
                    if (pwdPolicy3.hasForbiddenNumericSequence(str2) && this.mValidationErrors.isEmpty()) {
                        this.mValidationErrors.add(new PasswordValidationError(15));
                    }
                    if (pwdPolicy3.hasForbiddenCharacterSequence(str2) && this.mValidationErrors.isEmpty()) {
                        this.mValidationErrors.add(new PasswordValidationError(16));
                    }
                    if (pwdPolicy3.hasForbiddenStringDistance(str2, str) && this.mValidationErrors.isEmpty()) {
                        this.mValidationErrors.add(new PasswordValidationError(17));
                    }
                    if (pwdPolicy3.hasForbiddenData(str2) && this.mValidationErrors.isEmpty()) {
                        this.mValidationErrors.add(new PasswordValidationError(22));
                    }
                    if (pwdPolicy3.hasMaxRepeatedCharacters(str2) && this.mValidationErrors.isEmpty()) {
                        this.mValidationErrors.add(new PasswordValidationError(18));
                    }
                    if (!pwdPolicy3.isPasswordPatternMatched(str2) && this.mValidationErrors.isEmpty()) {
                        this.mValidationErrors.add(new PasswordValidationError(19));
                    }
                }
            }
            if (this.mCheckSimplePassword) {
                if (checkSequentialChars(str2) && this.mValidationErrors.isEmpty()) {
                    this.mValidationErrors.add(new PasswordValidationError(20));
                }
                if (checkRepeatingChars(str2) && this.mValidationErrors.isEmpty()) {
                    this.mValidationErrors.add(new PasswordValidationError(21));
                }
            }
            if (this.mUiStage == Stage.Introduction && !str2.isEmpty()) {
                if (!this.mCheckSimplePassword && (checkSequentialChars(str2) || checkRepeatingChars(str2))) {
                    z = true;
                }
                this.mIsSimplePassword = z;
            }
            return this.mValidationErrors.isEmpty();
        }

        @Override // android.text.TextWatcher
        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }
}

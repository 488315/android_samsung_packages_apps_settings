package com.android.settings.password;

import android.app.trust.TrustManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.reflect.view.SeslViewReflector;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockPatternView;
import com.android.internal.widget.LockscreenCredential;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;
import com.android.settings.core.InstrumentedFragment;
import com.android.settings.notification.RedactionInterstitial;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.android.settings.password.SaveAndFinishWorker;
import com.google.android.collect.Lists;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.GlifLayout;
import com.google.android.setupdesign.util.ThemeHelper;
import com.samsung.android.knox.accounts.HostAuth;
import com.samsung.android.knox.dar.StreamCipher;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.settings.lockscreen.PreviousPasswordDescriptionActivity;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.password.LockTypePolicy;
import com.samsung.android.settings.security.SecurityUtils;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ChooseLockPattern extends SettingsActivity {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class ChooseLockPatternFragment extends InstrumentedFragment implements SaveAndFinishWorker.Listener {

        @VisibleForTesting
        static final String KEY_UI_STAGE = "uiStage";
        public LinearLayout mBottomBar;

        @VisibleForTesting
        protected LockscreenCredential mChosenPattern;
        public LockscreenCredential mCurrentCredential;
        public ColorStateList mDefaultHeaderColorList;
        public TextView mFooterLeftButton;
        public TextView mFooterRightButton;
        public TextView mFooterText;
        public boolean mForAppLockBackupKey;
        public boolean mForFace;
        public boolean mForFingerprint;
        public boolean mFromAppLock;
        public boolean mFromPrevPassword;
        public boolean mFromSetupWizard;
        public TextView mHeaderText;
        public TextView mHelpText;
        public boolean mIsApplyingSetupTheme;
        public boolean mIsApplyingTabletGui;
        public boolean mIsFromKnoxTwoStep;
        public LockPatternUtils mLockPatternUtils;
        public LockPatternView mLockPatternView;
        public LockTypePolicy mLockTypePolicy;
        public LinearLayout mMainView;
        public Menu mMenu;
        public FooterBarMixin mMixin;
        public FooterButton mPrimaryButton;
        public boolean mRequestGatekeeperPassword;
        public boolean mRequestWriteRepairModePassword;
        public SaveAndFinishWorker mSaveAndFinishWorker;
        public FooterButton mSecondaryButton;
        public int mUserId;
        public int mPwdChangeEnforceStatus = 0;
        public boolean mIsUnlockRecovery = false;
        public final AnonymousClass1 mScreenOffReceiver = new BroadcastReceiver() { // from class: com.android.settings.password.ChooseLockPattern.ChooseLockPatternFragment.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                ChooseLockPatternFragment chooseLockPatternFragment = ChooseLockPatternFragment.this;
                if (chooseLockPatternFragment.mRequestGatekeeperPassword) {
                    chooseLockPatternFragment.getActivity().finish();
                }
            }
        };
        public final List mAnimatePattern = Collections.unmodifiableList(Lists.newArrayList(new LockPatternView.Cell[]{LockPatternView.Cell.of(0, 0), LockPatternView.Cell.of(0, 1), LockPatternView.Cell.of(1, 1), LockPatternView.Cell.of(2, 1)}));
        public final AnonymousClass2 mChooseNewLockPatternListener = new LockPatternView.OnPatternListener() { // from class: com.android.settings.password.ChooseLockPattern.ChooseLockPatternFragment.2
            public final void onPatternCleared() {
                ChooseLockPatternFragment chooseLockPatternFragment = ChooseLockPatternFragment.this;
                chooseLockPatternFragment.mLockPatternView.removeCallbacks(chooseLockPatternFragment.mClearPatternRunnable);
            }

            public final void onPatternDetected(List list) {
                Log.d("ChooseLockPattern", "onPatternDetected");
                SeslViewReflector.requestAccessibilityFocus(ChooseLockPatternFragment.this.mHeaderText);
                ChooseLockPatternFragment chooseLockPatternFragment = ChooseLockPatternFragment.this;
                Stage stage = chooseLockPatternFragment.mUiStage;
                Stage stage2 = Stage.NeedToConfirm;
                Stage stage3 = Stage.ConfirmWrong;
                if (stage != stage2 && stage != stage3) {
                    Stage stage4 = Stage.Introduction;
                    Stage stage5 = Stage.ChoiceTooShort;
                    if (stage != stage4 && stage != stage5) {
                        throw new IllegalStateException("Unexpected stage " + ChooseLockPatternFragment.this.mUiStage + " when entering the pattern.");
                    }
                    if (list.size() < 4) {
                        ChooseLockPatternFragment.this.updateStage(stage5);
                        return;
                    }
                    ChooseLockPatternFragment.this.mChosenPattern = LockscreenCredential.createPattern(list);
                    ChooseLockPatternFragment.this.updateStage(Stage.FirstChoiceValid);
                    return;
                }
                if (chooseLockPatternFragment.mChosenPattern == null) {
                    throw new IllegalStateException("null chosen pattern in stage 'need to confirm");
                }
                LockscreenCredential createPattern = LockscreenCredential.createPattern(list);
                try {
                    if (ChooseLockPatternFragment.this.mChosenPattern.equals(createPattern)) {
                        ChooseLockPatternFragment.this.updateStage(Stage.ChoiceConfirmed);
                    } else {
                        ChooseLockPatternFragment.this.updateStage(stage3);
                    }
                    if (createPattern != null) {
                        createPattern.close();
                    }
                } catch (Throwable th) {
                    if (createPattern != null) {
                        try {
                            createPattern.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }

            public final void onPatternStart() {
                Log.d("ChooseLockPattern", "onPatternStart");
                ChooseLockPatternFragment chooseLockPatternFragment = ChooseLockPatternFragment.this;
                chooseLockPatternFragment.mLockPatternView.removeCallbacks(chooseLockPatternFragment.mClearPatternRunnable);
                ChooseLockPatternFragment.this.mLockPatternView.getParent().requestDisallowInterceptTouchEvent(true);
                ChooseLockPatternFragment.this.mHeaderText.setText(R.string.lockpattern_recording_inprogress);
                ChooseLockPatternFragment chooseLockPatternFragment2 = ChooseLockPatternFragment.this;
                ColorStateList colorStateList = chooseLockPatternFragment2.mDefaultHeaderColorList;
                if (colorStateList != null) {
                    chooseLockPatternFragment2.mHeaderText.setTextColor(colorStateList);
                }
                ChooseLockPatternFragment.this.mFooterText.setText(ApnSettings.MVNO_NONE);
                ChooseLockPatternFragment.this.mFooterLeftButton.setEnabled(false);
                ChooseLockPatternFragment.this.mFooterRightButton.setEnabled(false);
                Menu menu = ChooseLockPatternFragment.this.mMenu;
                if (menu != null) {
                    menu.findItem(1).setEnabled(false);
                    ChooseLockPatternFragment.this.mMenu.findItem(2).setEnabled(false);
                }
            }

            public final void onPatternCellAdded(List list) {
            }
        };
        public Stage mUiStage = Stage.Introduction;
        public final AnonymousClass3 mClearPatternRunnable = new Runnable() { // from class: com.android.settings.password.ChooseLockPattern.ChooseLockPatternFragment.3
            @Override // java.lang.Runnable
            public final void run() {
                ChooseLockPatternFragment.this.mLockPatternView.clearPattern();
            }
        };

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public enum LeftButtonMode {
            Cancel("Cancel", true),
            Retry("Retry", true),
            /* JADX INFO: Fake field, exist only in values array */
            EF3("RetryDisabled", false),
            Gone("Gone", false);

            final boolean enabled;
            final int text;

            LeftButtonMode(String str, boolean z) {
                this.text = r2;
                this.enabled = z;
            }
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public enum RightButtonMode {
            Continue("Continue", true),
            ContinueDisabled("ContinueDisabled", false),
            Confirm("Confirm", true),
            ConfirmDisabled("ConfirmDisabled", false),
            Ok("Ok", true);

            final boolean enabled;
            final int text;

            RightButtonMode(String str, boolean z) {
                this.text = r2;
                this.enabled = z;
            }
        }

        /* JADX WARN: Enum visitor error
        jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'Introduction' uses external variables
        	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
        	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
        	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
        	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
        	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
        	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
         */
        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static final class Stage {
            public static final /* synthetic */ Stage[] $VALUES;
            public static final Stage ChoiceConfirmed;
            public static final Stage ChoiceTooShort;
            public static final Stage ConfirmWrong;
            public static final Stage FirstChoiceValid;
            public static final Stage HelpScreen;
            public static final Stage Introduction;
            public static final Stage NeedToConfirm;
            final int footerMessage = -1;
            final int headerMessage;
            final LeftButtonMode leftMode;
            final int messageForBiometrics;
            final boolean patternEnabled;
            final RightButtonMode rightMode;

            static {
                LeftButtonMode leftButtonMode = LeftButtonMode.Cancel;
                RightButtonMode rightButtonMode = RightButtonMode.ContinueDisabled;
                Stage stage = new Stage("Introduction", 0, R.string.lock_settings_picker_biometrics_added_security_message, R.string.lockpattern_recording_intro_header, leftButtonMode, rightButtonMode, true);
                Introduction = stage;
                Stage stage2 = new Stage("HelpScreen", 1, R.string.lockpattern_settings_help_how_to_record, R.string.lockpattern_settings_help_how_to_record, leftButtonMode, RightButtonMode.Ok, false);
                HelpScreen = stage2;
                LeftButtonMode leftButtonMode2 = LeftButtonMode.Retry;
                Stage stage3 = new Stage("ChoiceTooShort", 2, R.string.lockpattern_recording_incorrect_too_short, R.string.lockpattern_recording_incorrect_too_short, leftButtonMode2, rightButtonMode, true);
                ChoiceTooShort = stage3;
                Stage stage4 = new Stage("FirstChoiceValid", 3, R.string.lockpattern_pattern_entered_header, R.string.lockpattern_pattern_entered_header, leftButtonMode2, RightButtonMode.Continue, false);
                FirstChoiceValid = stage4;
                RightButtonMode rightButtonMode2 = RightButtonMode.ConfirmDisabled;
                Stage stage5 = new Stage("NeedToConfirm", 4, R.string.lockpattern_need_to_confirm, R.string.lockpattern_need_to_confirm, leftButtonMode, rightButtonMode2, true);
                NeedToConfirm = stage5;
                Stage stage6 = new Stage("ConfirmWrong", 5, R.string.lockpattern_need_to_unlock_wrong, R.string.lockpattern_need_to_unlock_wrong, leftButtonMode, rightButtonMode2, true);
                ConfirmWrong = stage6;
                Stage stage7 = new Stage("ChoiceConfirmed", 6, R.string.lockpattern_pattern_confirmed_header, R.string.lockpattern_pattern_confirmed_header, leftButtonMode, RightButtonMode.Confirm, false);
                ChoiceConfirmed = stage7;
                $VALUES = new Stage[]{stage, stage2, stage3, stage4, stage5, stage6, stage7};
            }

            public Stage(String str, int i, int i2, int i3, LeftButtonMode leftButtonMode, RightButtonMode rightButtonMode, boolean z) {
                this.headerMessage = i3;
                this.messageForBiometrics = i2;
                this.leftMode = leftButtonMode;
                this.rightMode = rightButtonMode;
                this.patternEnabled = z;
            }

            public static Stage valueOf(String str) {
                return (Stage) Enum.valueOf(Stage.class, str);
            }

            public static Stage[] values() {
                return (Stage[]) $VALUES.clone();
            }
        }

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 29;
        }

        public Intent getRedactionInterstitialIntent(FragmentActivity fragmentActivity) {
            return RedactionInterstitial.createStartIntent(fragmentActivity, this.mUserId);
        }

        public final void handleLeftButton() {
            Stage stage = this.mUiStage;
            LeftButtonMode leftButtonMode = stage.leftMode;
            LeftButtonMode leftButtonMode2 = LeftButtonMode.Retry;
            Stage stage2 = Stage.Introduction;
            if (leftButtonMode == leftButtonMode2) {
                LockscreenCredential lockscreenCredential = this.mChosenPattern;
                if (lockscreenCredential != null) {
                    lockscreenCredential.zeroize();
                    this.mChosenPattern = null;
                }
                LoggingHelper.insertEventLogging(29, 4412);
                this.mLockPatternView.clearPattern();
                updateStage(stage2);
                return;
            }
            if (leftButtonMode != LeftButtonMode.Cancel) {
                throw new IllegalStateException("left footer button pressed, but stage of " + this.mUiStage + " doesn't make sense");
            }
            if (this.mPwdChangeEnforceStatus < 1) {
                LoggingHelper.insertEventLogging(29, 4413);
                getActivity().finish();
            } else if (stage == stage2) {
                getActivity().setResult(0);
                getActivity().finish();
            } else {
                this.mChosenPattern = null;
                this.mLockPatternView.clearPattern();
                updateStage(stage2);
            }
        }

        public final void handleRightButton() {
            Stage stage = this.mUiStage;
            RightButtonMode rightButtonMode = stage.rightMode;
            RightButtonMode rightButtonMode2 = RightButtonMode.Continue;
            if (rightButtonMode == rightButtonMode2) {
                Stage stage2 = Stage.FirstChoiceValid;
                if (stage == stage2) {
                    updateStage(Stage.NeedToConfirm);
                    return;
                }
                throw new IllegalStateException("expected ui stage " + stage2 + " when button is " + rightButtonMode2);
            }
            RightButtonMode rightButtonMode3 = RightButtonMode.Confirm;
            if (rightButtonMode != rightButtonMode3) {
                if (rightButtonMode == RightButtonMode.Ok) {
                    if (stage != Stage.HelpScreen) {
                        throw new IllegalStateException("Help screen is only mode with ok button, but stage is " + this.mUiStage);
                    }
                    this.mLockPatternView.clearPattern();
                    this.mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Correct);
                    updateStage(Stage.Introduction);
                    return;
                }
                return;
            }
            Stage stage3 = Stage.ChoiceConfirmed;
            if (stage != stage3) {
                throw new IllegalStateException("expected ui stage " + stage3 + " when button is " + rightButtonMode3);
            }
            if (this.mFromAppLock || this.mForAppLockBackupKey) {
                getActivity().finish();
                return;
            }
            if (this.mSaveAndFinishWorker != null) {
                Log.w("ChooseLockPattern", "startSaveAndFinish with an existing SaveAndFinishWorker.");
            } else {
                setRightButtonEnabled(false);
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
                if (intent.hasExtra("unification_profile_id")) {
                    LockscreenCredential parcelableExtra = intent.getParcelableExtra("unification_profile_credential");
                    try {
                        SaveAndFinishWorker saveAndFinishWorker2 = this.mSaveAndFinishWorker;
                        saveAndFinishWorker2.mUnificationProfileId = intent.getIntExtra("unification_profile_id", -10000);
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
                this.mSaveAndFinishWorker.start(this.mLockPatternUtils, this.mChosenPattern, this.mCurrentCredential, this.mUserId);
            }
            boolean z = this.mLockTypePolicy.mIsKnoxId;
        }

        @Override // androidx.fragment.app.Fragment
        public final void onActivityResult(int i, int i2, Intent intent) {
            super.onActivityResult(i, i2, intent);
            if (i != 55) {
                return;
            }
            if (i2 != -1) {
                getActivity().setResult(1);
                getActivity().finish();
            } else {
                this.mCurrentCredential = intent.getParcelableExtra(HostAuth.PASSWORD);
            }
            updateStage(Stage.Introduction);
        }

        @Override // com.android.settings.password.SaveAndFinishWorker.Listener
        public final void onChosenLockSaveFinished(Intent intent, boolean z) {
            Intent fmmService;
            if (!this.mFromSetupWizard && this.mFromPrevPassword) {
                try {
                    if (LockUtils.isFmmUnlockAllowed(getContext(), this.mUserId, this.mLockTypePolicy.isEnterpriseUser()) && (fmmService = LockUtils.getFmmService(this.mChosenPattern, getContext())) != null) {
                        getActivity().startService(fmmService);
                    }
                } catch (Exception e) {
                    Log.e("ChooseLockPattern", e.toString());
                }
            }
            LockscreenCredential lockscreenCredential = this.mChosenPattern;
            if (lockscreenCredential != null && !this.mIsFromKnoxTwoStep) {
                lockscreenCredential.zeroize();
            }
            LockscreenCredential lockscreenCredential2 = this.mCurrentCredential;
            if (lockscreenCredential2 != null) {
                lockscreenCredential2.zeroize();
            }
            LockTypePolicy lockTypePolicy = this.mLockTypePolicy;
            if (lockTypePolicy.mIsOrganizationOwned || lockTypePolicy.mIsManagedProfile) {
                Intent chooseLockHintSettingsIntent = LockUtils.getChooseLockHintSettingsIntent(getActivity(), this.mUserId);
                if (chooseLockHintSettingsIntent != null) {
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
                        startActivity(putExtra);
                    }
                }
            }
            if (!z && !this.mForAppLockBackupKey && !this.mForFingerprint && !this.mForFace && !this.mIsFromKnoxTwoStep && !this.mFromPrevPassword && getRedactionInterstitialIntent(getActivity()) != null) {
                if (intent == null) {
                    intent = new Intent();
                }
                intent.putExtra("need_to_launch_ls_notification", true);
            }
            getActivity().setResult(1, intent);
            if (!z && this.mIsFromKnoxTwoStep) {
                ((TrustManager) getActivity().getSystemService("trust")).setDeviceLockedForUser(UserHandle.myUserId(), false);
            }
            getActivity().finish();
        }

        @Override // com.android.settingslib.core.lifecycle.ObservableFragment, androidx.fragment.app.Fragment
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            if (!(getActivity() instanceof ChooseLockPattern)) {
                throw new SecurityException("Fragment contained in wrong activity");
            }
            Intent intent = getActivity().getIntent();
            this.mUserId = Utils.getUserIdFromBundle(getActivity(), intent.getExtras(), false);
            UserManager.get(getActivity()).isManagedProfile(this.mUserId);
            this.mLockPatternUtils = new LockPatternUtils(getActivity());
            this.mForFingerprint = intent.getBooleanExtra("for_fingerprint", false);
            this.mForFace = intent.getBooleanExtra("for_face", false);
            intent.getBooleanExtra("for_biometrics", false);
            this.mLockTypePolicy = new LockTypePolicy(this.mUserId, getActivity(), intent);
            this.mFromAppLock = intent.getBooleanExtra("from_applock", false);
            this.mForAppLockBackupKey = intent.getStringExtra("forAppLockBackupKey") != null;
            this.mFromSetupWizard = intent.getBooleanExtra("fromSetupWizard", false);
            this.mFromPrevPassword = intent.getBooleanExtra("previous_credential", false);
            this.mIsApplyingTabletGui = LockUtils.isApplyingTabletGUI(getContext());
            this.mIsApplyingSetupTheme = LockUtils.isApplyingSetupTheme(getContext());
            this.mIsUnlockRecovery = Settings.System.getInt(getContext().getContentResolver(), "fmm_unlock_recovery", 0) != 0;
            this.mPwdChangeEnforceStatus = Utils.getEnterprisePolicyEnabledInt(getActivity(), "isChangeRequested", null);
            getActivity().registerReceiver(this.mScreenOffReceiver, new IntentFilter("android.intent.action.SCREEN_OFF"));
            if (this.mLockTypePolicy.isWorkDeviceOrProfile()) {
                getActivity().getWindow().addFlags(8192);
            }
            Log.d("ChooseLockPattern.SDP", "onCreate - StreamCipher initialized with handle(" + StreamCipher.getInstance().issueKeyStream() + ")");
            this.mIsFromKnoxTwoStep = intent.getBooleanExtra("is_knox_two_step", false);
        }

        @Override // androidx.fragment.app.Fragment
        public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            if (this.mIsApplyingTabletGui) {
                return layoutInflater.inflate(R.layout.sec_choose_lock_pattern_tablet, viewGroup, false);
            }
            if (!this.mFromSetupWizard || !this.mIsApplyingSetupTheme) {
                return layoutInflater.inflate(R.layout.sec_choose_lock_pattern, viewGroup, false);
            }
            GlifLayout glifLayout = (GlifLayout) layoutInflater.inflate(R.layout.sec_setup_choose_lock_pattern, viewGroup, false);
            FooterBarMixin footerBarMixin = (FooterBarMixin) glifLayout.getMixin(FooterBarMixin.class);
            getActivity();
            footerBarMixin.setSecondaryButton(new FooterButton(getString(R.string.lockpattern_retry_button_text), new ChooseLockPattern$ChooseLockPatternFragment$$ExternalSyntheticLambda0(this, 0), 0, 2132083806), false);
            footerBarMixin.setPrimaryButton(new FooterButton(getActivity().getString(R.string.lockpassword_continue_label), new ChooseLockPattern$ChooseLockPatternFragment$$ExternalSyntheticLambda0(this, 1), 5, 2132083805));
            footerBarMixin.getButtonContainer().setBackgroundColor(getResources().getColor(R.color.sec_lock_suw_background_color, null));
            this.mSecondaryButton = footerBarMixin.secondaryButton;
            this.mPrimaryButton = footerBarMixin.primaryButton;
            this.mMixin = footerBarMixin;
            return glifLayout;
        }

        @Override // com.android.settingslib.core.lifecycle.ObservableFragment, androidx.fragment.app.Fragment
        public final void onDestroy() {
            super.onDestroy();
            LockscreenCredential lockscreenCredential = this.mCurrentCredential;
            if (lockscreenCredential != null) {
                lockscreenCredential.zeroize();
            }
            System.gc();
            System.runFinalization();
            System.gc();
            getActivity().unregisterReceiver(this.mScreenOffReceiver);
        }

        @Override // androidx.fragment.app.Fragment
        public final void onMultiWindowModeChanged(boolean z) {
            super.onMultiWindowModeChanged(z);
            if (z && isResumed()) {
                if (Rune.isSamsungDexMode(getActivity())) {
                    Log.d("ChooseLockPattern", "If Dex stand alone mode, enable multi window");
                } else {
                    Toast.makeText(getActivity(), getString(R.string.lock_screen_doesnt_support_multi_window_text), 1).show();
                    getActivity().finish();
                }
            }
        }

        @Override // com.android.settingslib.core.lifecycle.ObservableFragment, androidx.fragment.app.Fragment
        public final void onPause() {
            super.onPause();
            SaveAndFinishWorker saveAndFinishWorker = this.mSaveAndFinishWorker;
            if (saveAndFinishWorker != null) {
                saveAndFinishWorker.setListener(null);
            }
            if (!this.mLockPatternUtils.isSecure(this.mUserId) || getActivity().isChangingConfigurations()) {
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("screen_lock_force_destroy", true);
            getActivity().setResult(0, intent);
            getActivity().finish();
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
            if (this.mSaveAndFinishWorker != null) {
                setRightButtonEnabled(false);
                this.mSaveAndFinishWorker.setListener(this);
            }
        }

        @Override // com.android.settingslib.core.lifecycle.ObservableFragment, androidx.fragment.app.Fragment
        public final void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            bundle.putInt(KEY_UI_STAGE, this.mUiStage.ordinal());
            LockscreenCredential lockscreenCredential = this.mChosenPattern;
            if (lockscreenCredential != null) {
                bundle.putParcelable("chosenPattern", lockscreenCredential);
            }
            LockscreenCredential lockscreenCredential2 = this.mCurrentCredential;
            if (lockscreenCredential2 != null) {
                bundle.putParcelable("currentPattern", lockscreenCredential2.duplicate());
            }
        }

        public void onSkipOrClearButtonClick() {
            handleLeftButton();
        }

        @Override // androidx.fragment.app.Fragment
        public final void onViewCreated(View view, Bundle bundle) {
            TextView textView = (TextView) view.findViewById(R.id.headerText);
            this.mHeaderText = textView;
            this.mDefaultHeaderColorList = textView.getTextColors();
            LockPatternView findViewById = view.findViewById(R.id.lockPattern);
            this.mLockPatternView = findViewById;
            findViewById.setOnPatternListener(this.mChooseNewLockPatternListener);
            Utils.setMaxFontScale(getContext(), this.mHeaderText);
            this.mLockPatternView.setFadePattern(false);
            this.mFooterText = (TextView) view.findViewById(R.id.footerText);
            view.findViewById(R.id.topLayout).setDefaultTouchRecepient(this.mLockPatternView);
            boolean booleanExtra = getActivity().getIntent().getBooleanExtra("confirm_credentials", true);
            Intent intent = getActivity().getIntent();
            if (bundle == null) {
                this.mCurrentCredential = intent.getParcelableExtra(HostAuth.PASSWORD);
            }
            this.mRequestGatekeeperPassword = intent.getBooleanExtra("request_gk_pw_handle", false);
            this.mRequestWriteRepairModePassword = intent.getBooleanExtra("request_write_repair_mode_pw", false);
            if (this.mLockTypePolicy.isEnterpriseUser() && bundle == null) {
                boolean booleanExtra2 = intent.getBooleanExtra("is_knox_password_secured", false);
                Log.d("ChooseLockPattern.SDP", "onViewCreated - Password secured for user " + this.mUserId + " : " + booleanExtra2);
                if (booleanExtra2) {
                    this.mCurrentCredential = KnoxUtils.restoreCipherPassword(this.mCurrentCredential, KnoxUtils.getCipherPublicHandle());
                }
            }
            this.mBottomBar = (LinearLayout) view.findViewById(R.id.bottom_bar);
            this.mMainView = (LinearLayout) view.findViewById(R.id.choose_lock_pattern_main_view);
            if (this.mFromSetupWizard && this.mIsApplyingSetupTheme) {
                this.mBottomBar.setVisibility(8);
            }
            if (LockUtils.isSupportActionBarButton(getActivity())) {
                this.mBottomBar.setVisibility(8);
            }
            this.mFooterLeftButton = (TextView) view.findViewById(R.id.footerLeftButton);
            this.mFooterRightButton = (TextView) view.findViewById(R.id.footerRightButton);
            this.mFooterLeftButton.setOnClickListener(new ChooseLockPattern$ChooseLockPatternFragment$$ExternalSyntheticLambda0(this, 0));
            this.mFooterRightButton.setOnClickListener(new ChooseLockPattern$ChooseLockPatternFragment$$ExternalSyntheticLambda0(this, 1));
            boolean z = this.mUserId != -10000 && UserManager.get(getActivity()).isManagedProfile(this.mUserId);
            boolean z2 = getResources().getConfiguration().orientation != 2;
            TextView textView2 = (TextView) view.findViewById(R.id.helpText);
            this.mHelpText = textView2;
            if (textView2 != null) {
                Utils.setMaxFontScale(getContext(), this.mHelpText);
                LockTypePolicy lockTypePolicy = this.mLockTypePolicy;
                if (lockTypePolicy.mIsKnoxId && lockTypePolicy.mIsSecureFolderId) {
                    this.mHelpText.setText(String.format(getActivity().getString(KnoxUtils.isResetWithSamsungAccountEnable(getContext(), this.mUserId) ? R.string.sec_choose_lock_pattern_sign_in_warning_text : R.string.sec_choose_lock_pattern_uninstall_warning_text), new Object[0]));
                    this.mHelpText.setVisibility(0);
                }
                if (!SecurityUtils.ConnectedMobileKeypad(getActivity()) && ((z2 || Utils.isTablet()) && !this.mFromAppLock && !z && !this.mLockTypePolicy.mIsKnoxId)) {
                    this.mHelpText.setVisibility(0);
                    this.mHelpText.setText(String.format(getActivity().getString(R.string.sec_choose_lock_pattern_warning_text), new Object[0]));
                }
                if (this.mIsUnlockRecovery) {
                    this.mHelpText.setVisibility(4);
                }
            }
            if (getActivity() instanceof SettingsActivity) {
                SettingsActivity settingsActivity = (SettingsActivity) getActivity();
                if (this.mLockTypePolicy.mIsKnoxId && !this.mForFingerprint) {
                    String string = getString(R.string.sec_knox_pattern_title, KnoxUtils.getKnoxName(getActivity(), this.mUserId));
                    if (this.mLockTypePolicy.mIsSecureFolderId) {
                        string = getString(R.string.sec_sf_set_pattern_header);
                    }
                    settingsActivity.setTitle(string);
                }
            }
            if (!this.mFromSetupWizard && !this.mIsApplyingTabletGui) {
                LockUtils.addHorizontalSpacing(getContext(), (LinearLayout) getActivity().findViewById(R.id.content_layout), (FrameLayout) getActivity().findViewById(R.id.content_frame));
                this.mMainView.semSetRoundedCorners(15);
                this.mMainView.semSetRoundedCornerColor(15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
            }
            if (bundle != null) {
                this.mChosenPattern = bundle.getParcelable("chosenPattern");
                this.mCurrentCredential = bundle.getParcelable("currentPattern");
                updateStage(Stage.values()[bundle.getInt(KEY_UI_STAGE)]);
                this.mSaveAndFinishWorker = (SaveAndFinishWorker) getFragmentManager().findFragmentByTag("save_and_finish_worker");
                return;
            }
            Stage stage = Stage.Introduction;
            if (!booleanExtra) {
                updateStage(stage);
                return;
            }
            updateStage(Stage.NeedToConfirm);
            ChooseLockSettingsHelper.Builder builder = new ChooseLockSettingsHelper.Builder(getActivity());
            builder.mRequestCode = 55;
            builder.mTitle = getString(R.string.sec_unlock_set_unlock_launch_picker_title);
            builder.mReturnCredentials = true;
            builder.mRequestGatekeeperPasswordHandle = this.mRequestGatekeeperPassword;
            builder.mRequestWriteRepairModePassword = this.mRequestWriteRepairModePassword;
            builder.mUserId = this.mUserId;
            if (builder.show()) {
                return;
            }
            updateStage(stage);
        }

        public final void setRightButtonEnabled(boolean z) {
            this.mFooterRightButton.setEnabled(z);
            Menu menu = this.mMenu;
            if (menu != null) {
                menu.findItem(2).setEnabled(z);
            }
            if (this.mFromSetupWizard && this.mIsApplyingSetupTheme) {
                this.mPrimaryButton.setEnabled(z);
                this.mMixin.getPrimaryButtonView().setTextColor(getResources().getColor(R.color.sswl_bottom_primary_button_text_color, null));
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:40:0x010c, code lost:
        
            if (r1 != 5) goto L53;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void updateStage(com.android.settings.password.ChooseLockPattern.ChooseLockPatternFragment.Stage r8) {
            /*
                Method dump skipped, instructions count: 343
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.settings.password.ChooseLockPattern.ChooseLockPatternFragment.updateStage(com.android.settings.password.ChooseLockPattern$ChooseLockPatternFragment$Stage):void");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class RecoveryActivity extends ChooseLockPattern {
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
        /* JADX WARN: Type inference failed for: r1v0, types: [android.content.BroadcastReceiver, com.android.settings.password.ChooseLockPattern$RecoveryActivity$1] */
        @Override // com.android.settings.SettingsActivity, com.samsung.android.settings.core.SecSettingsBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
        public final void onResume() {
            super.onResume();
            if (this.mReceiver != null) {
                return;
            }
            IntentFilter m = AppCompatDelegateImpl$$ExternalSyntheticOutline0.m("android.intent.action.USER_PRESENT");
            ?? r1 = new BroadcastReceiver() { // from class: com.android.settings.password.ChooseLockPattern.RecoveryActivity.1
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
        if (findFragmentById == null || !(findFragmentById instanceof ChooseLockPatternFragment) || (saveAndFinishWorker = ((ChooseLockPatternFragment) findFragmentById).mSaveAndFinishWorker) == null || saveAndFinishWorker.mFinished) {
            super.finish();
        }
    }

    public Class getFragmentClass() {
        return ChooseLockPatternFragment.class;
    }

    @Override // com.android.settings.SettingsActivity, android.app.Activity
    public final Intent getIntent() {
        Intent intent = new Intent(super.getIntent());
        intent.putExtra(":settings:show_fragment", getFragmentClass().getName());
        return intent;
    }

    @Override // com.android.settings.SettingsActivity
    public boolean isValidFragment(String str) {
        return ChooseLockPatternFragment.class.getName().equals(str);
    }

    @Override // com.android.settings.SettingsActivity, com.android.settings.core.SettingsBaseActivity, com.samsung.android.settings.core.SecSettingsBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        setTheme((getIntent().getBooleanExtra("fromSetupWizard", false) && LockUtils.isApplyingSetupTheme(this)) ? R.style.LockscreenSUWTheme : R.style.LockScreenTheme);
        ThemeHelper.trySetDynamicColor(this);
        super.onCreate(bundle);
        CharSequence text = getText(R.string.sec_choose_lock_pattern_your_pattern_header);
        setTitle(text);
        if (LockUtils.isApplyingTabletGUI(this)) {
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
        findViewById(R.id.content_parent).setFitsSystemWindows(false);
        if (LockUtils.isEmTokenAllowed(this)) {
            return;
        }
        getWindow().addFlags(8192);
    }

    @Override // android.app.Activity
    public final boolean onCreateOptionsMenu(Menu menu) {
        Fragment findFragmentById = getSupportFragmentManager().findFragmentById(R.id.main_content);
        if (findFragmentById == null || !(findFragmentById instanceof ChooseLockPatternFragment)) {
            return false;
        }
        ChooseLockPatternFragment chooseLockPatternFragment = (ChooseLockPatternFragment) findFragmentById;
        if (LockUtils.isSupportActionBarButton(chooseLockPatternFragment.getActivity())) {
            MenuItem add = menu.add(0, 2, 1, chooseLockPatternFragment.mUiStage.rightMode.text);
            add.setShowAsAction(2);
            add.setEnabled(chooseLockPatternFragment.mUiStage.rightMode.enabled);
            MenuItem add2 = menu.add(0, 1, 0, chooseLockPatternFragment.mUiStage.leftMode.text);
            add2.setShowAsAction(2);
            add2.setEnabled(chooseLockPatternFragment.mUiStage.leftMode.enabled);
            chooseLockPatternFragment.mMenu = menu;
        }
        return true;
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity, android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        Fragment findFragmentById = getSupportFragmentManager().findFragmentById(R.id.main_content);
        if (findFragmentById == null || !(findFragmentById instanceof ChooseLockPatternFragment) || menuItem.getItemId() == 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        ChooseLockPatternFragment chooseLockPatternFragment = (ChooseLockPatternFragment) findFragmentById;
        if (!LockUtils.isSupportActionBarButton(chooseLockPatternFragment.getActivity())) {
            return false;
        }
        int itemId = menuItem.getItemId();
        if (itemId == 2) {
            chooseLockPatternFragment.handleRightButton();
        } else if (itemId == 1) {
            chooseLockPatternFragment.handleLeftButton();
        }
        return true;
    }
}

package com.samsung.android.settings.knox;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.glance.session.SessionManagerImpl$scope$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.TextViewInputDisabler;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.applications.AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.android.settings.password.ConfirmDeviceCredentialBaseActivity;
import com.android.settings.password.ConfirmDeviceCredentialBaseFragment;
import com.android.settings.password.ConfirmDeviceCredentialUtils;
import com.android.settingslib.animation.AppearAnimationUtils;
import com.android.settingslib.animation.DisappearAnimationUtils;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.accounts.HostAuth;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.lockscreen.LockUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ConfirmUCMLockPassword extends ConfirmDeviceCredentialBaseActivity {
    public static final boolean DBG = Debug.semIsProductDev();
    public static LockscreenCredential mCurrentPassword = null;
    public static boolean mAuthenticating = false;
    public static LockscreenCredential mUCMCurrentPassword = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class InternalActivity extends ConfirmUCMLockPassword {}

    @Override // com.android.settings.SettingsActivity, android.app.Activity
    public final Intent getIntent() {
        Intent intent = new Intent(super.getIntent());
        intent.putExtra(":settings:show_fragment", ConfirmUCMLockPasswordFragment.class.getName());
        intent.putExtra(":android:no_headers", true);
        return intent;
    }

    @Override // com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return ConfirmUCMLockPasswordFragment.class.getName().equals(str);
    }

    @Override // com.android.settings.SettingsActivity, androidx.fragment.app.FragmentActivity,
              // androidx.activity.ComponentActivity, android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        boolean z = DBG;
        if (z) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    "onActivityResult called : ", ", ", i, i2, "ConfirmUCMLockPassword");
        }
        super.onActivityResult(i, i2, intent);
        mAuthenticating = false;
        if (intent != null) {
            mCurrentPassword = intent.getParcelableExtra(HostAuth.PASSWORD);
        }
        if (i2 != -1 || mCurrentPassword == null) {
            setResult(1);
            finish();
        } else if (z) {
            Log.d(
                    "ConfirmUCMLockPassword",
                    "CONFIRM_EXISTING_REQUEST resultCode == Activity.RESULT_OK");
        }
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity, android.app.Activity,
              // android.view.Window.Callback
    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        Fragment findFragmentById = getSupportFragmentManager().findFragmentById(R.id.main_content);
        if (findFragmentById == null
                || !(findFragmentById instanceof ConfirmUCMLockPasswordFragment)) {
            return;
        }
        final ConfirmUCMLockPasswordFragment confirmUCMLockPasswordFragment =
                (ConfirmUCMLockPasswordFragment) findFragmentById;
        KnoxConfirmLockHelper knoxConfirmLockHelper =
                confirmUCMLockPasswordFragment
                        .mKnoxConfirmDeviceCredentialBaseFragmentHelper
                        .mKnoxConfirmLockHelper;
        if (knoxConfirmLockHelper != null) {
            knoxConfirmLockHelper.onWindowFocusChanged(z);
        }
        if (!z || confirmUCMLockPasswordFragment.mBlockImm) {
            return;
        }
        confirmUCMLockPasswordFragment.mPasswordEntry.post(
                new Runnable() { // from class:
                                 // com.samsung.android.settings.knox.ConfirmUCMLockPassword.ConfirmUCMLockPasswordFragment.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        if (ConfirmUCMLockPasswordFragment.this.mPasswordEntry.isEnabled()) {
                            ConfirmUCMLockPasswordFragment.this.resetState$1();
                        } else {
                            ConfirmUCMLockPasswordFragment confirmUCMLockPasswordFragment2 =
                                    ConfirmUCMLockPasswordFragment.this;
                            confirmUCMLockPasswordFragment2.mImm.hideSoftInputFromWindow(
                                    confirmUCMLockPasswordFragment2.mPasswordEntry.getWindowToken(),
                                    1);
                        }
                    }
                });
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class ConfirmUCMLockPasswordFragment extends ConfirmDeviceCredentialBaseFragment
            implements View.OnClickListener, TextView.OnEditorActionListener, TextWatcher {
        public static final /* synthetic */ int $r8$clinit = 0;
        public String mAgentTitle;
        public AppearAnimationUtils mAppearAnimationUtils;
        public final boolean mBlockImm;
        public String mCsName;
        public String mCsNameUri;
        public TextView mDetailsTextView;
        public TextView mErrorTextView;
        public boolean mFromSettings;
        public TextView mHeaderTextView;
        public InputMethodManager mImm;
        public boolean mIsKnoxTheme;
        public LockPatternUtils mLockPatternUtils;
        public int mNumWrongConfirmAttempts;
        public TextView mPasswordEntry;
        public TextViewInputDisabler mPasswordEntryInputDisabler;
        public AsyncTask mPendingLockCheck;
        public final StateMachine mStateMachine = new StateMachine();
        public String mStorageType;
        public final AnonymousClass1 mTester;
        public int mUserId;
        public String mVendorID;
        public TextView mVendorIDView;
        public ProgressDialog progDlg;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class StateMachine {
            public int mAtmRemain;
            public int mErrorState;
            public String mInputPin;
            public String mInputPuk;
            public int mState;

            public StateMachine() {}

            public final int getState() {
                if (ConfirmUCMLockPassword.DBG) {
                    Preference$$ExternalSyntheticOutline0.m(
                            new StringBuilder("getState : "),
                            this.mState,
                            "ConfirmUCMLockPasswordFragment");
                }
                return this.mState;
            }

            public final void setState(int i) {
                if (ConfirmUCMLockPassword.DBG) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                            i, "setState : ", "ConfirmUCMLockPasswordFragment");
                }
                this.mState = i;
            }
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class UCMAsyncTask extends AsyncTask {
            public final String csNameUri;
            public final int opCode;

            public UCMAsyncTask(String str, int i) {
                this.opCode = -1;
                this.csNameUri = null;
                if (ConfirmUCMLockPassword.DBG) {
                    Log.d(
                            "ConfirmUCMLockPasswordFragment",
                            "UCMAsyncTask csNameUri : " + str + " opCode : " + i);
                }
                this.opCode = i;
                this.csNameUri = str;
            }

            /* JADX WARN: Removed duplicated region for block: B:113:0x0305  */
            /* JADX WARN: Removed duplicated region for block: B:115:0x030c  */
            /* JADX WARN: Removed duplicated region for block: B:118:0x0319  */
            /* JADX WARN: Removed duplicated region for block: B:121:0x0333  */
            /* JADX WARN: Removed duplicated region for block: B:126:0x031f  */
            /* JADX WARN: Removed duplicated region for block: B:127:0x0325  */
            /* JADX WARN: Removed duplicated region for block: B:77:0x04b8  */
            /* JADX WARN: Removed duplicated region for block: B:81:0x04d8  */
            /* JADX WARN: Removed duplicated region for block: B:86:0x04e5  */
            @Override // android.os.AsyncTask
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object doInBackground(java.lang.Object[] r28) {
                /*
                    Method dump skipped, instructions count: 1314
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException(
                        "Method not decompiled:"
                            + " com.samsung.android.settings.knox.ConfirmUCMLockPassword.ConfirmUCMLockPasswordFragment.UCMAsyncTask.doInBackground(java.lang.Object[]):java.lang.Object");
            }

            @Override // android.os.AsyncTask
            public final void onPostExecute(Object obj) {
                try {
                    updateUI();
                    if (this.opCode != 99) {
                        ConfirmUCMLockPasswordFragment confirmUCMLockPasswordFragment =
                                ConfirmUCMLockPasswordFragment.this;
                        confirmUCMLockPasswordFragment.getClass();
                        if (ConfirmUCMLockPassword.DBG) {
                            Log.d("ConfirmUCMLockPasswordFragment", "stopProgress");
                        }
                        ProgressDialog progressDialog = confirmUCMLockPasswordFragment.progDlg;
                        if (progressDialog != null) {
                            try {
                                progressDialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e2) {
                    boolean z = ConfirmUCMLockPassword.DBG;
                    if (z) {
                        SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m(
                                "Exception ", e2, "ConfirmUCMLockPasswordFragment");
                    }
                    if (z) {
                        e2.printStackTrace();
                    }
                }
            }

            @Override // android.os.AsyncTask
            public final void onPreExecute() {
                if (this.opCode != 99) {
                    ConfirmUCMLockPasswordFragment confirmUCMLockPasswordFragment =
                            ConfirmUCMLockPasswordFragment.this;
                    int i = ConfirmUCMLockPasswordFragment.$r8$clinit;
                    confirmUCMLockPasswordFragment.getClass();
                    if (ConfirmUCMLockPassword.DBG) {
                        Log.d("ConfirmUCMLockPasswordFragment", "startProgress");
                    }
                    ProgressDialog progressDialog = confirmUCMLockPasswordFragment.progDlg;
                    if (progressDialog != null) {
                        try {
                            progressDialog.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            public final void startVerifyPassword(
                    final LockscreenCredential lockscreenCredential,
                    final Intent intent,
                    final int i) {
                ConfirmUCMLockPasswordFragment confirmUCMLockPasswordFragment =
                        ConfirmUCMLockPasswordFragment.this;
                int i2 = ConfirmUCMLockPasswordFragment.$r8$clinit;
                final int i3 = confirmUCMLockPasswordFragment.mEffectiveUserId;
                int i4 = confirmUCMLockPasswordFragment.mUserId;
                LockPatternChecker.OnVerifyCallback onVerifyCallback =
                        new LockPatternChecker
                                .OnVerifyCallback() { // from class:
                                                      // com.samsung.android.settings.knox.ConfirmUCMLockPassword$ConfirmUCMLockPasswordFragment$UCMAsyncTask$$ExternalSyntheticLambda0
                            public final void onVerified(
                                    VerifyCredentialResponse verifyCredentialResponse, int i5) {
                                ConfirmUCMLockPassword.ConfirmUCMLockPasswordFragment.UCMAsyncTask
                                        uCMAsyncTask =
                                                ConfirmUCMLockPassword
                                                        .ConfirmUCMLockPasswordFragment.UCMAsyncTask
                                                        .this;
                                int i6 = i;
                                Intent intent2 = intent;
                                LockscreenCredential lockscreenCredential2 = lockscreenCredential;
                                int i7 = i3;
                                ConfirmUCMLockPassword.ConfirmUCMLockPasswordFragment.this
                                                .mPendingLockCheck =
                                        null;
                                boolean isMatched = verifyCredentialResponse.isMatched();
                                if (isMatched
                                        && ConfirmUCMLockPassword.ConfirmUCMLockPasswordFragment
                                                .this
                                                .mReturnCredentials) {
                                    if ((i6 & 1) != 0) {
                                        intent2.putExtra(
                                                "gk_pw_handle",
                                                verifyCredentialResponse
                                                        .getGatekeeperPasswordHandle());
                                    } else {
                                        intent2.putExtra(
                                                "hw_auth_token",
                                                verifyCredentialResponse.getGatekeeperHAT());
                                    }
                                    if (UCMUtils.isEnforcedCredentialStorageExistAsUser(
                                            ConfirmUCMLockPassword.ConfirmUCMLockPasswordFragment
                                                    .this
                                                    .mUserId)) {
                                        intent2.putExtra(
                                                HostAuth.PASSWORD,
                                                (Parcelable)
                                                        KnoxUtils.getCipher(
                                                                ConfirmUCMLockPassword
                                                                        .mUCMCurrentPassword,
                                                                KnoxUtils.getCipherPublicHandle()));
                                        intent2.putExtra("is_knox_password_secured", true);
                                    } else {
                                        ConfirmUCMLockPassword.mUCMCurrentPassword = null;
                                        intent2.putExtra(
                                                HostAuth.PASSWORD,
                                                (Parcelable) lockscreenCredential2);
                                    }
                                    ConfirmUCMLockPassword.ConfirmUCMLockPasswordFragment.this
                                            .getActivity()
                                            .setResult(-1, intent2);
                                    ConfirmUCMLockPassword.ConfirmUCMLockPasswordFragment
                                            confirmUCMLockPasswordFragment2 =
                                                    ConfirmUCMLockPassword
                                                            .ConfirmUCMLockPasswordFragment.this;
                                    confirmUCMLockPasswordFragment2.onPasswordChecked$1(
                                            confirmUCMLockPasswordFragment2.mUserId, intent2, true);
                                    ConfirmUCMLockPassword.ConfirmUCMLockPasswordFragment.this
                                            .getActivity()
                                            .finish();
                                } else {
                                    ConfirmUCMLockPassword.ConfirmUCMLockPasswordFragment
                                            confirmUCMLockPasswordFragment3 =
                                                    ConfirmUCMLockPassword
                                                            .ConfirmUCMLockPasswordFragment.this;
                                    String string =
                                            confirmUCMLockPasswordFragment3.getString(
                                                    R.string.sc_pin_dialog_associationerror);
                                    TextView textView =
                                            confirmUCMLockPasswordFragment3.mDetailsTextView;
                                    if (textView != null) {
                                        textView.setText(string);
                                    }
                                    ConfirmUCMLockPassword.ConfirmUCMLockPasswordFragment.this
                                            .resetState$1();
                                    ConfirmUCMLockPassword.ConfirmUCMLockPasswordFragment.this
                                                    .mStateMachine
                                                    .mErrorState =
                                            0;
                                }
                                ConfirmUCMLockPassword.ConfirmUCMLockPasswordFragment.this
                                        .onPasswordChecked$1(i7, intent2, isMatched);
                            }
                        };
                confirmUCMLockPasswordFragment.mPendingLockCheck =
                        i3 == i4
                                ? LockPatternChecker.verifyCredential(
                                        confirmUCMLockPasswordFragment.mLockPatternUtils,
                                        lockscreenCredential,
                                        i4,
                                        i,
                                        onVerifyCallback)
                                : LockPatternChecker.verifyTiedProfileChallenge(
                                        confirmUCMLockPasswordFragment.mLockPatternUtils,
                                        lockscreenCredential,
                                        i4,
                                        i,
                                        onVerifyCallback);
            }

            /* JADX WARN: Removed duplicated region for block: B:68:0x0324  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void updateUI() {
                /*
                    Method dump skipped, instructions count: 834
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException(
                        "Method not decompiled:"
                            + " com.samsung.android.settings.knox.ConfirmUCMLockPassword.ConfirmUCMLockPasswordFragment.UCMAsyncTask.updateUI():void");
            }
        }

        /* renamed from: -$$Nest$mshowMISCInfo, reason: not valid java name */
        public static void m1242$$Nest$mshowMISCInfo(
                ConfirmUCMLockPasswordFragment confirmUCMLockPasswordFragment) {
            confirmUCMLockPasswordFragment.getClass();
            if (ConfirmUCMLockPassword.DBG) {
                AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                        new StringBuilder("showMISCInfo called : "),
                        UCMUtils.miscInfo,
                        ", true",
                        "ConfirmUCMLockPasswordFragment");
            }
            boolean z = confirmUCMLockPasswordFragment.mIsKnoxTheme;
            String str = z ? ApnSettings.MVNO_NONE : confirmUCMLockPasswordFragment.mVendorID;
            if (z) {
                str = UCMUtils.miscInfo;
            } else {
                String str2 = UCMUtils.miscInfo;
                if (str2 != null && str2.length() > 0) {
                    StringBuilder m =
                            PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                                    str, "\n");
                    m.append(UCMUtils.miscInfo);
                    str = m.toString();
                }
            }
            TextView textView = confirmUCMLockPasswordFragment.mVendorIDView;
            if (textView == null) {
                return;
            }
            textView.setText(str);
        }

        /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.settings.knox.ConfirmUCMLockPassword$ConfirmUCMLockPasswordFragment$1] */
        public ConfirmUCMLockPasswordFragment() {
            new Handler();
            this.mTester =
                    new Handler() { // from class:
                                    // com.samsung.android.settings.knox.ConfirmUCMLockPassword.ConfirmUCMLockPasswordFragment.1
                        @Override // android.os.Handler
                        public final void handleMessage(Message message) {
                            if (message.what != 1) {
                                return;
                            }
                            ConfirmUCMLockPasswordFragment confirmUCMLockPasswordFragment =
                                    ConfirmUCMLockPasswordFragment.this;
                            confirmUCMLockPasswordFragment.mTester.removeMessages(1);
                            confirmUCMLockPasswordFragment.showSoftInput$2();
                        }
                    };
            this.mCsNameUri = null;
            this.mCsName = null;
            this.mFromSettings = true;
            this.mIsKnoxTheme = false;
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment
        public final void authenticationSucceeded() {
            onPasswordChecked$1(this.mUserId, new Intent(), true);
            getActivity().setResult(-1, new Intent());
            getActivity().finish();
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
            return PeripheralBarcodeConstants.Symbology.UNDEFINED;
        }

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 30;
        }

        public final String getRemainingCount(int i) {
            return i <= 1
                    ? getString(
                            R.string.keyguard_password_attempt_count_pin_code, Integer.valueOf(i))
                    : getString(
                            R.string.keyguard_password_attempts_count_pin_code, Integer.valueOf(i));
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public final void handleNext$4() {
            String[] strArr;
            boolean z = ConfirmUCMLockPassword.DBG;
            if (z) {
                Log.d("ConfirmUCMLockPasswordFragment", "handleNext");
            }
            int i = 0;
            this.mPasswordEntryInputDisabler.setInputEnabled(false);
            AsyncTask asyncTask = this.mPendingLockCheck;
            if (asyncTask != null) {
                asyncTask.cancel(false);
            }
            String charSequence = this.mPasswordEntry.getText().toString();
            if (charSequence == null || charSequence.length() <= 0) {
                resetState$1();
                return;
            }
            StateMachine stateMachine = this.mStateMachine;
            int state = stateMachine.getState();
            if (z) {
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                        state, "next mCurState : ", "ConfirmUCMLockPasswordFragment");
            }
            switch (state) {
                case 65536:
                    strArr = new String[] {charSequence};
                    break;
                case 65537:
                    stateMachine.mInputPuk = charSequence;
                    stateMachine.mState = 65538;
                    strArr = null;
                    i = 99;
                    break;
                case 65538:
                    stateMachine.mInputPin = charSequence;
                    stateMachine.mState = 65539;
                    strArr = null;
                    i = 99;
                    break;
                case 65539:
                    if (!charSequence.equals(stateMachine.mInputPin)) {
                        stateMachine.mState = 65538;
                        stateMachine.mErrorState = 65542;
                        strArr = null;
                        i = 99;
                        break;
                    } else {
                        strArr = new String[] {stateMachine.mInputPuk, stateMachine.mInputPin};
                        i = 2;
                        break;
                    }
                default:
                    strArr = null;
                    i = 99;
                    break;
            }
            ConfirmUCMLockPasswordFragment confirmUCMLockPasswordFragment =
                    ConfirmUCMLockPasswordFragment.this;
            confirmUCMLockPasswordFragment
                    .new UCMAsyncTask(confirmUCMLockPasswordFragment.mCsNameUri, i)
                    .execute(strArr);
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            if (view.getId() == R.id.next_button) {
                handleNext$4();
            } else if (view.getId() == R.id.cancel_button) {
                getActivity().setResult(0);
                getActivity().finish();
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:72:0x0153 A[Catch: Exception -> 0x0131, TryCatch #1 {Exception -> 0x0131, blocks: (B:53:0x00ff, B:57:0x0109, B:59:0x0114, B:62:0x011b, B:64:0x011f, B:65:0x013d, B:67:0x0148, B:70:0x014f, B:72:0x0153, B:73:0x016e, B:75:0x0179, B:78:0x0180, B:80:0x0184, B:83:0x0196, B:85:0x019a, B:88:0x0165, B:90:0x0169, B:91:0x0134, B:93:0x0138), top: B:52:0x00ff }] */
        /* JADX WARN: Removed duplicated region for block: B:75:0x0179 A[Catch: Exception -> 0x0131, TryCatch #1 {Exception -> 0x0131, blocks: (B:53:0x00ff, B:57:0x0109, B:59:0x0114, B:62:0x011b, B:64:0x011f, B:65:0x013d, B:67:0x0148, B:70:0x014f, B:72:0x0153, B:73:0x016e, B:75:0x0179, B:78:0x0180, B:80:0x0184, B:83:0x0196, B:85:0x019a, B:88:0x0165, B:90:0x0169, B:91:0x0134, B:93:0x0138), top: B:52:0x00ff }] */
        /* JADX WARN: Removed duplicated region for block: B:80:0x0184 A[Catch: Exception -> 0x0131, TryCatch #1 {Exception -> 0x0131, blocks: (B:53:0x00ff, B:57:0x0109, B:59:0x0114, B:62:0x011b, B:64:0x011f, B:65:0x013d, B:67:0x0148, B:70:0x014f, B:72:0x0153, B:73:0x016e, B:75:0x0179, B:78:0x0180, B:80:0x0184, B:83:0x0196, B:85:0x019a, B:88:0x0165, B:90:0x0169, B:91:0x0134, B:93:0x0138), top: B:52:0x00ff }] */
        /* JADX WARN: Removed duplicated region for block: B:82:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:85:0x019a A[Catch: Exception -> 0x0131, TRY_LEAVE, TryCatch #1 {Exception -> 0x0131, blocks: (B:53:0x00ff, B:57:0x0109, B:59:0x0114, B:62:0x011b, B:64:0x011f, B:65:0x013d, B:67:0x0148, B:70:0x014f, B:72:0x0153, B:73:0x016e, B:75:0x0179, B:78:0x0180, B:80:0x0184, B:83:0x0196, B:85:0x019a, B:88:0x0165, B:90:0x0169, B:91:0x0134, B:93:0x0138), top: B:52:0x00ff }] */
        /* JADX WARN: Removed duplicated region for block: B:87:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:90:0x0169 A[Catch: Exception -> 0x0131, TryCatch #1 {Exception -> 0x0131, blocks: (B:53:0x00ff, B:57:0x0109, B:59:0x0114, B:62:0x011b, B:64:0x011f, B:65:0x013d, B:67:0x0148, B:70:0x014f, B:72:0x0153, B:73:0x016e, B:75:0x0179, B:78:0x0180, B:80:0x0184, B:83:0x0196, B:85:0x019a, B:88:0x0165, B:90:0x0169, B:91:0x0134, B:93:0x0138), top: B:52:0x00ff }] */
        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment,
                  // com.android.settingslib.core.lifecycle.ObservableFragment,
                  // androidx.fragment.app.Fragment
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onCreate(android.os.Bundle r10) {
            /*
                Method dump skipped, instructions count: 420
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.settings.knox.ConfirmUCMLockPassword.ConfirmUCMLockPasswordFragment.onCreate(android.os.Bundle):void");
        }

        @Override // androidx.fragment.app.Fragment
        public final View onCreateView(
                LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            String string;
            int i;
            this.progDlg = new ProgressDialog(getActivity());
            this.progDlg.setMessage(getString(R.string.ucm_loading));
            this.progDlg.setIndeterminate(true);
            this.progDlg.setProgressStyle(0);
            this.progDlg.setCancelable(false);
            if (ConfirmUCMLockPassword.DBG) {
                Preference$$ExternalSyntheticOutline0.m(
                        new StringBuilder("onCreateView : check1: :"),
                        this.mUserId,
                        "ConfirmUCMLockPasswordFragment");
            }
            this.mLockPatternUtils.getKeyguardStoredPasswordQuality(this.mUserId);
            ConfirmUCMLockPassword confirmUCMLockPassword = (ConfirmUCMLockPassword) getActivity();
            View inflate =
                    layoutInflater.inflate(
                            LockUtils.isApplyingTabletGUI(confirmUCMLockPassword)
                                    ? R.layout.sec_confirm_lock_password_tablet
                                    : R.layout.sec_confirm_lock_password,
                            viewGroup,
                            false);
            KnoxConfirmDeviceCredentialBaseFragmentHelper
                    knoxConfirmDeviceCredentialBaseFragmentHelper =
                            this.mKnoxConfirmDeviceCredentialBaseFragmentHelper;
            FragmentActivity activity = getActivity();
            KnoxConfirmLockHelper knoxConfirmLockHelper =
                    knoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
            View switchViewIfNeeded =
                    knoxConfirmLockHelper != null
                            ? knoxConfirmLockHelper.switchViewIfNeeded(
                                    layoutInflater, inflate, activity)
                            : inflate;
            this.mIsKnoxTheme = !inflate.equals(switchViewIfNeeded);
            View findViewById = switchViewIfNeeded.findViewById(R.id.button_layout);
            if (findViewById != null) {
                findViewById.setVisibility(8);
            }
            TextView textView = (TextView) switchViewIfNeeded.findViewById(R.id.password_entry);
            this.mPasswordEntry = textView;
            textView.setOnEditorActionListener(this);
            this.mPasswordEntry.addTextChangedListener(this);
            this.mPasswordEntry.setImeOptions(33554432);
            this.mPasswordEntryInputDisabler = new TextViewInputDisabler(this.mPasswordEntry);
            this.mHeaderTextView = (TextView) switchViewIfNeeded.findViewById(R.id.headerText);
            TextView textView2 = (TextView) switchViewIfNeeded.findViewById(R.id.detailsText);
            this.mDetailsTextView = textView2;
            this.mErrorTextView = textView2;
            TextView textView3 = (TextView) switchViewIfNeeded.findViewById(R.id.ucmvendorIDText);
            this.mVendorIDView = textView3;
            textView3.setVisibility(0);
            this.mImm = (InputMethodManager) getActivity().getSystemService("input_method");
            try {
                if (LockUtils.isApplyingTabletGUI(confirmUCMLockPassword)) {
                    switchViewIfNeeded.findViewById(R.id.bottom_bar).setVisibility(8);
                }
            } catch (NullPointerException e) {
                Log.i("ConfirmUCMLockPasswordFragment", "NullPointerException " + e);
            }
            Intent intent = getActivity().getIntent();
            if (intent != null) {
                intent.getCharSequenceExtra("com.android.settings.ConfirmCredentials.header");
                CharSequence charSequenceExtra =
                        intent.getCharSequenceExtra(
                                "com.android.settings.ConfirmCredentials.details");
                String str = this.mAgentTitle;
                if (str == null || str.isEmpty()) {
                    string =
                            getString(
                                    this.mFrp
                                            ? R.string.ucm_google_account_frp_validation_header
                                            : R.string.lockpassword_confirm_your_pin_header);
                } else {
                    string = this.mAgentTitle;
                }
                if (TextUtils.isEmpty(charSequenceExtra)) {
                    int state = this.mStateMachine.getState();
                    if (state == 65536 || state == 65537) {
                        StringBuilder sb = new StringBuilder();
                        switch (state) {
                            case 65536:
                                i = R.string.confirm_ucm_your_pin_header;
                                break;
                            case 65537:
                                i = R.string.ucm_enter_puk;
                                break;
                            case 65538:
                                i = R.string.ucm_enter_new_pin;
                                break;
                            case 65539:
                                i = R.string.ucm_confirm_pin;
                                break;
                            case 65540:
                                i = R.string.success_puk_string;
                                break;
                            default:
                                i = R.string.ucm_failed_to_connect_smartcard;
                                break;
                        }
                        sb.append(getString(i));
                        sb.append("\n");
                        sb.append(getRemainingCount(this.mStateMachine.mAtmRemain));
                        charSequenceExtra = sb.toString();
                    } else {
                        charSequenceExtra = ApnSettings.MVNO_NONE;
                    }
                }
                TextView textView4 = this.mHeaderTextView;
                if (textView4 != null) {
                    textView4.setText(string);
                }
                String charSequence = charSequenceExtra.toString();
                TextView textView5 = this.mDetailsTextView;
                if (textView5 != null) {
                    textView5.setText(charSequence);
                }
            }
            if (this.mIsKnoxTheme) {
                this.mVendorIDView.setTextColor(
                        getActivity()
                                .getResources()
                                .getColor(R.color.work_profile_lock_screen_text_color));
            } else {
                String str2 = this.mVendorID;
                if (str2 != null && str2.length() > 0) {
                    this.mVendorIDView.setText(this.mVendorID);
                }
            }
            this.mPasswordEntry.getInputType();
            this.mPasswordEntry.setInputType(18);
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
            TextView textView6 = this.mHeaderTextView;
            if (textView6 != null) {
                setAccessibilityTitle(textView6.getText());
            }
            boolean booleanExtra =
                    intent != null ? intent.getBooleanExtra("confirm_credentials", true) : true;
            if (!this.mFromSettings
                    && booleanExtra
                    && !ConfirmUCMLockPassword.mAuthenticating
                    && ConfirmUCMLockPassword.mCurrentPassword == null) {
                ConfirmUCMLockPassword.mAuthenticating = true;
                boolean booleanExtra2 = intent.getBooleanExtra("request_gk_pw_handle", false);
                ChooseLockSettingsHelper.Builder builder =
                        new ChooseLockSettingsHelper.Builder(getActivity(), this);
                builder.mRequestCode = 100;
                builder.mTitle = getString(R.string.sec_unlock_set_unlock_launch_picker_title);
                builder.mReturnCredentials = true;
                builder.mRequestGatekeeperPasswordHandle = booleanExtra2;
                builder.mUserId = this.mUserId;
                builder.show();
            }
            KnoxConfirmLockHelper knoxConfirmLockHelper2 =
                    this.mKnoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
            if (knoxConfirmLockHelper2 != null) {
                knoxConfirmLockHelper2.onCreateView(switchViewIfNeeded);
            }
            return switchViewIfNeeded;
        }

        @Override // android.widget.TextView.OnEditorActionListener
        public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i != 0 && i != 6 && i != 5) {
                return false;
            }
            handleNext$4();
            return true;
        }

        public final void onPasswordChecked$1(int i, Intent intent, boolean z) {
            if (ConfirmUCMLockPassword.DBG) {
                Log.d(
                        "ConfirmUCMLockPasswordFragment",
                        "onPasswordChecked, matched: " + z + ", effectiveUserId:" + i);
            }
            this.mPasswordEntryInputDisabler.setInputEnabled(true);
            if (z) {
                ConfirmDeviceCredentialUtils.reportSuccessfulAttempt(
                        this.mLockPatternUtils,
                        this.mUserManager,
                        this.mDevicePolicyManager,
                        this.mEffectiveUserId,
                        true);
                getActivity().setResult(-1, intent);
                getActivity().finish();
                ConfirmDeviceCredentialUtils.checkForPendingIntent(getActivity());
            }
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment,
                  // com.android.settingslib.core.lifecycle.ObservableFragment,
                  // androidx.fragment.app.Fragment
        public final void onPause() {
            super.onPause();
            if (!ConfirmUCMLockPassword.mAuthenticating
                    && !getActivity().isChangingConfigurations()) {
                getActivity().finish();
            }
            InputMethodManager inputMethodManager = this.mImm;
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(this.mPasswordEntry.getWindowToken(), 1);
            }
            AsyncTask asyncTask = this.mPendingLockCheck;
            if (asyncTask != null) {
                asyncTask.cancel(false);
                this.mPendingLockCheck = null;
            }
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment,
                  // com.android.settings.core.InstrumentedFragment,
                  // com.android.settingslib.core.lifecycle.ObservableFragment,
                  // androidx.fragment.app.Fragment
        public final void onResume() {
            super.onResume();
            boolean z = ConfirmUCMLockPassword.DBG;
            if (z) {
                Log.d("ConfirmUCMLockPasswordFragment", "onResume");
            }
            getActivity().getWindow().addFlags(128);
            resetState$1();
            if (z) {
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        new StringBuilder("get settings config for the vendor : "),
                        this.mCsNameUri,
                        "ConfirmUCMLockPasswordFragment");
            }
            new UCMAsyncTask(this.mCsNameUri, 1).execute(ApnSettings.MVNO_NONE);
            sendEmptyMessageDelayed(1, 100L);
        }

        @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
                  // androidx.fragment.app.Fragment
        public final void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            bundle.putInt(
                    "confirm_lock_password_fragment.key_num_wrong_confirm_attempts",
                    this.mNumWrongConfirmAttempts);
            bundle.putBoolean("AuthViewState", ConfirmUCMLockPassword.mAuthenticating);
            bundle.putParcelable("CurrentPassword", ConfirmUCMLockPassword.mCurrentPassword);
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment
        public final void onShowDefault() {
            this.mErrorTextView.setText(
                    this.mFrp
                            ? R.string.ucm_google_account_frp_validation_header
                            : R.string.lockpassword_confirm_your_pin_header);
        }

        @Override // com.android.settings.password.ConfirmDeviceCredentialBaseFragment
        public final void onShowError() {
            this.mPasswordEntry.setText((CharSequence) null);
        }

        public final void resetState$1() {
            if (this.mBlockImm) {
                return;
            }
            if (this.mStateMachine.mErrorState != 65542) {
                if (ConfirmUCMLockPassword.DBG) {
                    Log.d(
                            "ConfirmUCMLockPasswordFragment",
                            "resetState set the passwordentry as null");
                }
                if (this.mPasswordEntry.getText() != null
                        && this.mPasswordEntry.getText().length() > 0) {
                    this.mPasswordEntry.setText((CharSequence) null);
                }
            }
            this.mPasswordEntry.setEnabled(true);
            this.mPasswordEntry.setFocusable(true);
            this.mPasswordEntry.setFocusableInTouchMode(true);
            this.mPasswordEntry.requestFocus();
            this.mPasswordEntryInputDisabler.setInputEnabled(true);
            if (this.mPasswordEntry.isEnabled()) {
                showSoftInput$2();
            }
            KnoxConfirmLockHelper knoxConfirmLockHelper =
                    this.mKnoxConfirmDeviceCredentialBaseFragmentHelper.mKnoxConfirmLockHelper;
        }

        public final void showSoftInput$2() {
            TextView textView;
            if (this.mImm == null
                    || (textView = this.mPasswordEntry) == null
                    || !textView.hasFocus()
                    || this.mImm.showSoftInput(this.mPasswordEntry, 1)
                    || !this.mPasswordEntry.isFocused()
                    || SemPersonaManager.isKnoxId(this.mUserId)) {
                return;
            }
            sendEmptyMessageDelayed(1, 500L);
        }

        @Override // android.text.TextWatcher
        public final void afterTextChanged(Editable editable) {}

        @Override // android.text.TextWatcher
        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override // android.text.TextWatcher
        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
    }
}

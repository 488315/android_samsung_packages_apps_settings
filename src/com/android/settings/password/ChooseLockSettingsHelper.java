package com.android.settings.password;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.RemoteLockscreenValidationSession;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.Utils;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ChooseLockSettingsHelper {
    public final Activity mActivity;
    public final ActivityResultLauncher mActivityResultLauncher;
    public final Builder mBuilder;
    public final boolean mForFace;
    public final boolean mForFingerPrint;
    public final Fragment mFragment;
    public final boolean mKnoxWorkProfileSecurity;
    LockPatternUtils mLockPatternUtils;
    public final boolean mUnlockRecovery;

    public ChooseLockSettingsHelper(
            Builder builder,
            Activity activity,
            Fragment fragment,
            ActivityResultLauncher activityResultLauncher) {
        this.mForFingerPrint = false;
        this.mForFace = false;
        this.mUnlockRecovery = false;
        this.mBuilder = builder;
        this.mActivity = activity;
        this.mFragment = fragment;
        this.mActivityResultLauncher = activityResultLauncher;
        this.mLockPatternUtils = new LockPatternUtils(activity);
        String simpleName = activity.getClass().getSimpleName();
        if (simpleName.contains("Fingerprint")) {
            this.mForFingerPrint = true;
        }
        if (simpleName.contains("Face")) {
            this.mForFace = true;
        }
        this.mUnlockRecovery = builder.mUnlockRecovery;
        this.mKnoxWorkProfileSecurity = builder.mKnoxWorkProfileSecurity;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0182  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean launch() {
        /*
            Method dump skipped, instructions count: 494
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.password.ChooseLockSettingsHelper.launch():boolean");
    }

    public final void launchConfirmationActivity(
            int i,
            CharSequence charSequence,
            CharSequence charSequence2,
            CharSequence charSequence3,
            Class cls,
            boolean z,
            boolean z2,
            boolean z3,
            int i2,
            CharSequence charSequence4,
            CharSequence charSequence5,
            boolean z4,
            RemoteLockscreenValidationSession remoteLockscreenValidationSession,
            ComponentName componentName,
            boolean z5,
            boolean z6,
            boolean z7,
            boolean z8,
            boolean z9) {
        Bundle bundle;
        Intent intent = new Intent();
        intent.putExtra("com.android.settings.ConfirmCredentials.title", charSequence);
        intent.putExtra("com.android.settings.ConfirmCredentials.header", charSequence2);
        intent.putExtra("com.android.settings.ConfirmCredentials.details", charSequence3);
        intent.putExtra("com.android.settings.ConfirmCredentials.darkTheme", false);
        intent.putExtra("com.android.settings.ConfirmCredentials.showCancelButton", false);
        intent.putExtra("com.android.settings.ConfirmCredentials.showWhenLocked", z2);
        intent.putExtra("com.android.settings.ConfirmCredentials.useFadeAnimation", z2);
        intent.putExtra("com.android.settings.ConfirmCredentials.isRemoteLockscreenValidation", z4);
        intent.putExtra("return_credentials", z);
        intent.putExtra("force_verify", z3);
        intent.putExtra("android.intent.extra.USER_ID", i2);
        intent.putExtra("android.app.extra.ALTERNATE_BUTTON_LABEL", charSequence4);
        intent.putExtra("android.app.extra.CHECKBOX_LABEL", charSequence5);
        intent.putExtra(
                "android.app.extra.REMOTE_LOCKSCREEN_VALIDATION_SESSION",
                (Parcelable) remoteLockscreenValidationSession);
        intent.putExtra("android.intent.extra.COMPONENT_NAME", componentName);
        intent.putExtra("foreground_only", z6);
        intent.putExtra("allow_any_user", z5);
        intent.putExtra("request_gk_pw_handle", z7);
        intent.putExtra("request_write_repair_mode_pw", z8);
        if (this.mForFingerPrint) {
            intent.putExtra("for_fingerprint", true);
        }
        if (this.mForFace) {
            intent.putExtra("for_face", true);
        }
        if (this.mUnlockRecovery) {
            intent.putExtra("unlock_recovery", true);
        }
        intent.putExtra("from_knox_work_profile_security", this.mKnoxWorkProfileSecurity);
        intent.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, cls.getName());
        intent.putExtra("page_transition_type", 1);
        Activity activity = this.mActivity;
        Fragment fragment = this.mFragment;
        Intent intent2 =
                fragment != null ? fragment.getActivity().getIntent() : activity.getIntent();
        WizardManagerHelper.copyWizardManagerExtras(intent2, intent);
        String stringExtra = intent2.getStringExtra("theme");
        if (stringExtra != null) {
            intent.putExtra("theme", stringExtra);
        }
        if (z9) {
            ActivityOptions makeBasic = ActivityOptions.makeBasic();
            makeBasic.setLaunchTaskId(activity.getTaskId());
            makeBasic.setTaskOverlay(true, true);
            bundle = makeBasic.toBundle();
        } else {
            bundle = null;
        }
        ActivityResultLauncher activityResultLauncher = this.mActivityResultLauncher;
        if (!z2) {
            if (activityResultLauncher != null) {
                activityResultLauncher.launch(intent);
                return;
            } else if (fragment != null) {
                fragment.startActivityForResult(intent, i, bundle);
                return;
            } else {
                activity.startActivityForResult(intent, i, bundle);
                return;
            }
        }
        intent.addFlags(33554432);
        IntentSender intentSender =
                (IntentSender) intent2.getParcelableExtra("android.intent.extra.INTENT");
        if (intentSender != null) {
            intent.putExtra("android.intent.extra.INTENT", intentSender);
        }
        int intExtra = intent2.getIntExtra("android.intent.extra.TASK_ID", -1);
        if (intExtra != -1) {
            intent.putExtra("android.intent.extra.TASK_ID", intExtra);
        }
        if (intentSender != null || intExtra != -1) {
            intent.addFlags(8388608);
            intent.addFlags(1073741824);
        }
        intent.putExtra(
                "knox.container.proxy.EXTRA_SHOW_WHEN_LOCKED",
                intent2.getBooleanExtra("knox.container.proxy.EXTRA_SHOW_WHEN_LOCKED", true));
        intent.putExtra(
                "fromPwdNotification", intent2.getBooleanExtra("fromPwdNotification", false));
        intent.putExtra(
                "knox.container.proxy.EXTRA_TASK_ID",
                intent2.getIntExtra("knox.container.proxy.EXTRA_TASK_ID", -1));
        if (activityResultLauncher != null) {
            activityResultLauncher.launch(intent);
            return;
        }
        if (fragment != null) {
            int windowingMode =
                    fragment.getResources()
                            .getConfiguration()
                            .windowConfiguration
                            .getWindowingMode();
            if (windowingMode != 5) {
                fragment.startActivity(intent);
                return;
            }
            ActivityOptions makeBasic2 = ActivityOptions.makeBasic();
            makeBasic2.setLaunchWindowingMode(windowingMode);
            fragment.startActivity(intent, makeBasic2.toBundle());
            return;
        }
        ActivityOptions makeBasic3 = ActivityOptions.makeBasic();
        int intExtra2 = intent.getIntExtra("knox.container.proxy.EXTRA_TASK_ID", -1);
        if (intExtra2 != -1) {
            makeBasic3.setLaunchTaskId(intExtra2);
            makeBasic3.setTaskOverlay(true, true);
        }
        int windowingMode2 =
                activity.getResources().getConfiguration().windowConfiguration.getWindowingMode();
        if (windowingMode2 == 5) {
            makeBasic3.setLaunchWindowingMode(windowingMode2);
        }
        activity.startActivity(intent, makeBasic3.toBundle());
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder {
        public final Activity mActivity;
        public ActivityResultLauncher mActivityResultLauncher;
        public boolean mAllowAnyUserId;
        public CharSequence mAlternateButton;
        public CharSequence mCheckBoxLabel;
        public CharSequence mDescription;
        public boolean mExternal;
        public boolean mForceVerifyPath;
        public boolean mForegroundOnly;
        public final Fragment mFragment;
        public CharSequence mHeader;
        public boolean mKnoxWorkProfileSecurity;
        public boolean mRemoteLockscreenValidation;
        public ComponentName mRemoteLockscreenValidationServiceComponent;
        public RemoteLockscreenValidationSession mRemoteLockscreenValidationSession;
        public int mRequestCode;
        public boolean mRequestGatekeeperPasswordHandle;
        public boolean mRequestWriteRepairModePassword;
        public boolean mReturnCredentials;
        public boolean mTaskOverlay;
        public CharSequence mTitle;
        public boolean mUnlockRecovery;
        public int mUserId;

        public Builder(Activity activity) {
            this.mUnlockRecovery = false;
            this.mKnoxWorkProfileSecurity = false;
            this.mActivity = activity;
            StringBuilder sb = Utils.sBuilder;
            this.mUserId =
                    ((UserManager) activity.getSystemService(UserManager.class))
                            .getCredentialOwnerProfile(UserHandle.myUserId());
        }

        public final ChooseLockSettingsHelper build() {
            int i;
            boolean z = this.mAllowAnyUserId;
            Activity activity = this.mActivity;
            if (!z && (i = this.mUserId) != -9999 && i != -9998) {
                Utils.enforceSameOwner(activity, i);
            }
            if (this.mExternal && this.mReturnCredentials && !this.mRemoteLockscreenValidation) {
                throw new IllegalArgumentException(
                        "External and ReturnCredentials specified.  External callers should never"
                            + " be allowed to receive credentials in onActivityResult");
            }
            if (this.mRequestGatekeeperPasswordHandle && !this.mReturnCredentials) {
                Log.w(
                        "ChooseLockSettingsHelper",
                        "Requested gatekeeper password handle but not requesting ReturnCredentials."
                            + " Are you sure this is what you want?");
            }
            return new ChooseLockSettingsHelper(
                    this, activity, this.mFragment, this.mActivityResultLauncher);
        }

        public final boolean show() {
            return build().launch();
        }

        public Builder(Activity activity, Fragment fragment) {
            this(activity);
            this.mFragment = fragment;
        }
    }
}

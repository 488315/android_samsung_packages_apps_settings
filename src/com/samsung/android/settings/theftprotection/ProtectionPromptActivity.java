package com.samsung.android.settings.theftprotection;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.mediarouter.media.MediaRoute2Provider$$ExternalSyntheticLambda0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.password.ScreenLockType;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.theftprotection.logging.Log;
import com.samsung.android.settings.theftprotection.timer.ProtectionTimerService$TimeDelayState;

import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ProtectionPromptActivity extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public String mCallerPackage;
    public Intent mIntent;
    public boolean mIsLockoutStatus;
    public String mLoggingEventValue;
    public boolean mSecurityDelay = true;
    public final AnonymousClass1 mAuthenticationCallback =
            new BiometricPrompt
                    .AuthenticationCallback() { // from class:
                                                // com.samsung.android.settings.theftprotection.ProtectionPromptActivity.1
                @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                public final void onAuthenticationError(int i, CharSequence charSequence) {
                    int i2 = ProtectionPromptActivity.$r8$clinit;
                    Log.d(
                            "ProtectionPromptActivity",
                            "ProtectionPrompt.onAuthenticationError = "
                                    + i
                                    + ", errString = "
                                    + ((Object) charSequence));
                    super.onAuthenticationError(i, charSequence);
                    ProtectionPromptActivity.this.finishWithResult(0);
                }

                @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                public final void onAuthenticationFailed() {
                    int i = ProtectionPromptActivity.$r8$clinit;
                    Log.d("ProtectionPromptActivity", "ProtectionPrompt.onAuthenticationFailed");
                    super.onAuthenticationFailed();
                }

                @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                public final void onAuthenticationHelp(int i, CharSequence charSequence) {
                    int i2 = ProtectionPromptActivity.$r8$clinit;
                    Log.d(
                            "ProtectionPromptActivity",
                            "ProtectionPrompt.onAuthenticationHelp = "
                                    + i
                                    + ", helpString = "
                                    + ((Object) charSequence));
                    super.onAuthenticationHelp(i, charSequence);
                }

                @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
                public final void onAuthenticationSucceeded(
                        BiometricPrompt.AuthenticationResult authenticationResult) {
                    int i = ProtectionPromptActivity.$r8$clinit;
                    Log.d(
                            "ProtectionPromptActivity",
                            "ProtectionPrompt.onAuthenticationSucceeded = "
                                    + authenticationResult.getAuthenticationType());
                    super.onAuthenticationSucceeded(authenticationResult);
                    ProtectionPromptActivity protectionPromptActivity =
                            ProtectionPromptActivity.this;
                    if (protectionPromptActivity.mIsLockoutStatus) {
                        protectionPromptActivity.finishWithResult(0);
                    } else {
                        protectionPromptActivity.handleAuthenticationSucceeded();
                    }
                }
            };

    public final void buildBiometricPromptAuthDelay() {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Log.d("ProtectionPromptActivity", "realTime = " + elapsedRealtime);
        long j =
                Settings.Secure.getLong(
                        getContentResolver(), "mandatory_biometrics_delay_time", 0L);
        if (j <= 0 || elapsedRealtime <= j) {
            long j2 =
                    Settings.Secure.getLong(
                            getContentResolver(), "mandatory_biometrics_grace_time", 0L);
            if (j2 > 0 && elapsedRealtime > j2) {
                Log.w(
                        "ProtectionPromptActivity",
                        "The grace period has expired but it hasn't been reset. Reset the grace"
                            + " period.");
                Intent intent =
                        new Intent("com.samsung.android.settings.action.GRACE_TIME_EXPIRATION");
                intent.setPackage(getPackageName());
                sendBroadcast(intent);
            }
        } else {
            Log.w(
                    "ProtectionPromptActivity",
                    "The security delay time has expired but it hasn't been reset. Reset the"
                        + " security delay time.");
            TheftProtectionUtils.isSecurityDelayTest();
            if (elapsedRealtime > j + TheftProtectionConstants.SECURITY_GRACE_DURATION_MILLIS) {
                Settings.Secure.putLong(
                        getContentResolver(), "mandatory_biometrics_delay_time", 0L);
            } else {
                Intent intent2 =
                        new Intent("com.samsung.android.settings.action.TIME_DELAY_EXPIRATION");
                intent2.setPackage(getPackageName());
                sendBroadcast(intent2);
            }
        }
        BiometricPrompt.Builder builder = new BiometricPrompt.Builder(this);
        builder.setTitle(getText(R.string.sec_lockpassword_remote_validation_header));
        builder.setSubtitle(getText(R.string.protection_prompt_subtitle));
        builder.setAllowedAuthenticators(15);
        setPromptLogo(builder, this.mCallerPackage);
        if (this.mIntent.getBooleanExtra("pp_account_authentication", false)) {
            final String stringExtra = this.mIntent.getStringExtra("pp_client_id");
            builder.setNegativeButton(
                    getString(R.string.protection_prompt_account_authentication),
                    new Executor() { // from class:
                                     // com.samsung.android.settings.theftprotection.ProtectionPromptActivity$$ExternalSyntheticLambda0
                        @Override // java.util.concurrent.Executor
                        public final void execute(Runnable runnable) {
                            ProtectionPromptActivity protectionPromptActivity =
                                    ProtectionPromptActivity.this;
                            String str = stringExtra;
                            int i = ProtectionPromptActivity.$r8$clinit;
                            protectionPromptActivity.getClass();
                            Log.d(
                                    "ProtectionPromptActivity",
                                    "ProtectionPrompt.onSamsungAccountClicked");
                            Intent intent3 =
                                    new Intent(
                                            "com.msc.action.samsungaccount.CONFIRM_PASSWORD_POPUP");
                            intent3.setPackage("com.osp.app.signin");
                            intent3.putExtra("client_id", str);
                            protectionPromptActivity.startActivityForResult(intent3, 101);
                            LoggingHelper.insertEventLogging(54102, 54207);
                        }
                    },
                    new ProtectionPromptActivity$$ExternalSyntheticLambda1());
        } else {
            builder.setNegativeButton(
                    getString(R.string.cancel),
                    new Executor() { // from class:
                                     // com.samsung.android.settings.theftprotection.ProtectionPromptActivity$$ExternalSyntheticLambda2
                        @Override // java.util.concurrent.Executor
                        public final void execute(Runnable runnable) {
                            ProtectionPromptActivity protectionPromptActivity =
                                    ProtectionPromptActivity.this;
                            int i = ProtectionPromptActivity.$r8$clinit;
                            protectionPromptActivity.getClass();
                            Log.d("ProtectionPromptActivity", "ProtectionPrompt.onCancelClicked");
                            protectionPromptActivity.finishWithResult(0);
                        }
                    },
                    new ProtectionPromptActivity$$ExternalSyntheticLambda1());
        }
        builder.build()
                .authenticate(
                        new CancellationSignal(),
                        new MediaRoute2Provider$$ExternalSyntheticLambda0(
                                new Handler(Looper.getMainLooper())),
                        this.mAuthenticationCallback);
    }

    public final void finishWithResult(int i) {
        setResult(i);
        finish();
    }

    public final int getLockoutDescriptionRes() {
        ScreenLockType fromQuality =
                ScreenLockType.fromQuality(
                        new LockPatternUtils(this)
                                .getKeyguardStoredPasswordQuality(UserHandle.myUserId()));
        if (ScreenLockType.PIN.equals(fromQuality)) {
            return R.string.protection_prompt_lockout_description_pin;
        }
        if (ScreenLockType.PATTERN.equals(fromQuality)) {
            return R.string.protection_prompt_lockout_description_pattern;
        }
        if (ScreenLockType.PASSWORD.equals(fromQuality)) {
            return R.string.protection_prompt_lockout_description_password;
        }
        return 0;
    }

    public final void handleAuthenticationSucceeded() {
        boolean z = Settings.Secure.getInt(getContentResolver(), "mandatory_biometrics", -1) == 1;
        boolean isInTrustedPlace = TheftProtectionUtils.isInTrustedPlace(getApplicationContext());
        StringBuilder m =
                Utils$$ExternalSyntheticOutline0.m(
                        "handleAuthenticationSucceeded: isMandatoryBiometricsOn=",
                        z,
                        ", inTrustedPlace=",
                        isInTrustedPlace,
                        ", timeDelay=");
        m.append(this.mSecurityDelay);
        Log.d("ProtectionPromptActivity", m.toString());
        if (z && !isInTrustedPlace && this.mSecurityDelay) {
            long j =
                    Settings.Secure.getLong(
                            getContentResolver(), "mandatory_biometrics_grace_time", 0L);
            ProtectionTimerService$TimeDelayState protectionTimerService$TimeDelayState =
                    ProtectionTimerService$TimeDelayState.NONE;
            ProtectionTimerService$TimeDelayState protectionTimerService$TimeDelayState2 =
                    ProtectionTimerService$TimeDelayState.SECURITY_DELAY;
            ProtectionTimerService$TimeDelayState protectionTimerService$TimeDelayState3 =
                    j > 0
                            ? ProtectionTimerService$TimeDelayState.GRACE_PERIOD
                            : Settings.Secure.getLong(
                                                    getContentResolver(),
                                                    "mandatory_biometrics_delay_time",
                                                    0L)
                                            > 0
                                    ? protectionTimerService$TimeDelayState2
                                    : protectionTimerService$TimeDelayState;
            if (protectionTimerService$TimeDelayState3 == protectionTimerService$TimeDelayState
                    || protectionTimerService$TimeDelayState3
                            == protectionTimerService$TimeDelayState2) {
                Log.d(
                        "ProtectionPromptActivity",
                        "handleAuthenticationSucceeded: TimeDelayState = NONE || SECURITY_DELAY");
                launchTimeDelayScreen(
                        protectionTimerService$TimeDelayState3
                                == protectionTimerService$TimeDelayState2);
                finishWithResult(0);
                return;
            }
            Log.d("ProtectionPromptActivity", "handleAuthenticationSucceeded: GRACE_PERIOD");
        }
        finishWithResult(-1);
    }

    public final void launchTimeDelayScreen(boolean z) {
        Intent intent = new Intent("com.samsung.android.settings.action.TIME_DELAY_SERVICE");
        intent.putExtra("delay_running", z);
        Bundle bundle = new Bundle();
        bundle.putString("pp_package_name", this.mCallerPackage);
        bundle.putString("pp_action_name", this.mIntent.getStringExtra("pp_action_name"));
        bundle.putInt(
                "pp_security_delay_title_res",
                this.mIntent.getIntExtra("pp_security_delay_title_res", -1));
        bundle.putInt(
                "pp_security_delay_description_res",
                this.mIntent.getIntExtra("pp_security_delay_description_res", -1));
        bundle.putInt(
                "pp_noti_in_progress_res", this.mIntent.getIntExtra("pp_noti_in_progress_res", -1));
        bundle.putInt(
                "pp_noti_delay_end_res", this.mIntent.getIntExtra("pp_noti_delay_end_res", -1));
        bundle.putInt(
                "pp_noti_delay_end_immediately_res",
                this.mIntent.getIntExtra("pp_noti_delay_end_immediately_res", -1));
        bundle.putString("pp_caller_name", this.mLoggingEventValue);
        intent.putExtra("pp_security_delay_bundle", bundle);
        startActivity(intent);
    }

    @Override // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        Log.d(
                "ProtectionPromptActivity",
                "onActivityResult: requestCode=" + i + ", resultCode=" + i2);
        if (i == 101) {
            if (i2 == -1) {
                handleAuthenticationSucceeded();
            } else {
                finishWithResult(0);
            }
        }
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        BiometricManager biometricManager;
        int canAuthenticate;
        super.onCreate(bundle);
        if (!TheftProtectionUtils.isMandatoryBiometricEnabled(this)) {
            Log.e("ProtectionPromptActivity", "ProtectionPrompt is not enabled.");
            finishWithResult(-1);
            return;
        }
        Intent intent = getIntent();
        this.mIntent = intent;
        if (intent == null) {
            Log.e("ProtectionPromptActivity", "Intent is null.");
            finishWithResult(0);
            return;
        }
        String stringExtra = intent.getStringExtra("pp_package_name");
        this.mCallerPackage = stringExtra;
        if (TextUtils.isEmpty(stringExtra)) {
            Log.e("ProtectionPromptActivity", "Caller package name is empty.");
            finishWithResult(0);
            return;
        }
        this.mLoggingEventValue = this.mIntent.getStringExtra("pp_caller_name");
        this.mSecurityDelay = this.mIntent.getBooleanExtra("pp_security_delay", true);
        String action = getIntent() == null ? ApnSettings.MVNO_NONE : getIntent().getAction();
        if (TextUtils.equals(action, "com.samsung.intent.action.MB_LOCKOUT_PROMPT")) {
            this.mIsLockoutStatus = true;
        } else if (TextUtils.equals(action, "com.samsung.intent.action.MANDATORY_BIOMETRICS_PROMPT")
                && (biometricManager =
                                (BiometricManager)
                                        getApplicationContext().getSystemService("biometric"))
                        != null
                && (7 == (canAuthenticate = biometricManager.canAuthenticate(15))
                        || 9 == canAuthenticate)) {
            this.mIsLockoutStatus = true;
        }
        if (this.mIsLockoutStatus) {
            BiometricPrompt.Builder builder = new BiometricPrompt.Builder(this);
            builder.setTitle(getText(R.string.sec_biometircs_prompt_title));
            builder.setSubtitle(getText(R.string.protection_prompt_subtitle));
            if (getLockoutDescriptionRes() != 0) {
                builder.setDescription(getText(getLockoutDescriptionRes()));
            }
            builder.setAllowedAuthenticators(NetworkAnalyticsConstants.DataPoints.FLAG_UID);
            setPromptLogo(builder, this.mCallerPackage);
            builder.build()
                    .authenticate(
                            new CancellationSignal(),
                            new MediaRoute2Provider$$ExternalSyntheticLambda0(
                                    new Handler(Looper.getMainLooper())),
                            this.mAuthenticationCallback);
            return;
        }
        boolean isInTrustedPlace = TheftProtectionUtils.isInTrustedPlace(getApplicationContext());
        long j =
                Settings.Secure.getLong(
                        getContentResolver(), "mandatory_biometrics_grace_time", 0L);
        ProtectionTimerService$TimeDelayState protectionTimerService$TimeDelayState =
                ProtectionTimerService$TimeDelayState.SECURITY_DELAY;
        ProtectionTimerService$TimeDelayState protectionTimerService$TimeDelayState2 =
                j > 0
                        ? ProtectionTimerService$TimeDelayState.GRACE_PERIOD
                        : Settings.Secure.getLong(
                                                getContentResolver(),
                                                "mandatory_biometrics_delay_time",
                                                0L)
                                        > 0
                                ? protectionTimerService$TimeDelayState
                                : ProtectionTimerService$TimeDelayState.NONE;
        StringBuilder m =
                RowView$$ExternalSyntheticOutline0.m(
                        "onCreate() : inTrustedPlace=", ", timeDelay=", isInTrustedPlace);
        m.append(this.mSecurityDelay);
        m.append(", timeState");
        m.append(protectionTimerService$TimeDelayState2.ordinal());
        Log.d("ProtectionPromptActivity", m.toString());
        if (!this.mSecurityDelay) {
            buildBiometricPromptAuthDelay();
            return;
        }
        if (isInTrustedPlace) {
            finishWithResult(-1);
        } else if (protectionTimerService$TimeDelayState2
                != protectionTimerService$TimeDelayState) {
            buildBiometricPromptAuthDelay();
        } else {
            launchTimeDelayScreen(true);
            finishWithResult(0);
        }
    }

    public final void setPromptLogo(BiometricPrompt.Builder builder, String str) {
        try {
            String str2 = (String) Utils.getApplicationLabel(this, str);
            if (TextUtils.isEmpty(str2)) {
                return;
            }
            Drawable applicationIcon = getPackageManager().getApplicationIcon(str);
            builder.setLogoBitmap(
                    Utils.createBitmap(
                            applicationIcon,
                            applicationIcon.getIntrinsicWidth(),
                            applicationIcon.getIntrinsicHeight()));
            builder.setLogoDescription(str2);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

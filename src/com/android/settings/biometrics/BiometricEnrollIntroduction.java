package com.android.settings.biometrics;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;

import androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$semanticsModifier$1$1$3$$ExternalSyntheticOutline0;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.biometrics.face.FaceEnrollParentalConsent;

import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.span.LinkSpan;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BiometricEnrollIntroduction extends BiometricEnrollBase
        implements LinkSpan.OnClickListener {
    public GatekeeperPasswordProvider mGatekeeperPasswordProvider;

    public abstract int checkMaxEnrolled();

    public abstract void getChallenge(
            BiometricEnrollIntroduction$$ExternalSyntheticLambda0
                    biometricEnrollIntroduction$$ExternalSyntheticLambda0);

    public GatekeeperPasswordProvider getGatekeeperPasswordProvider() {
        if (this.mGatekeeperPasswordProvider == null) {
            this.mGatekeeperPasswordProvider =
                    new GatekeeperPasswordProvider(getLockPatternUtils());
        }
        return this.mGatekeeperPasswordProvider;
    }

    public LockPatternUtils getLockPatternUtils() {
        return new LockPatternUtils(this);
    }

    public abstract FooterButton getNextButton();

    public abstract Intent getSecBiometricsEnrollIntent();

    public UserManager getUserManager() {
        return UserManager.get(this);
    }

    public final void handleBiometricResultSkipOrFinished(int i, Intent intent) {
        if (intent != null && intent.getBooleanExtra("skip_pending_enroll", false)) {
            getIntent().removeExtra("enroll_after_face");
            getIntent().removeExtra("enroll_after_finger");
        }
        if (i == 2) {
            onEnrollmentSkipped(intent);
        } else if (i == 1) {
            onFinishedEnrolling(intent);
        }
    }

    @Override // com.android.settings.biometrics.BiometricEnrollBase
    public void initViews$1() {
        super.initViews$1();
        updateDescriptionText();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        Log.d(
                "BiometricEnrollIntroduction",
                CoreTextFieldKt$CoreTextField$semanticsModifier$1$1$3$$ExternalSyntheticOutline0.m(
                        "onActivityResult(requestCode=", ", resultCode=", i, i2, ")"));
        boolean z = i == 7 && getIntent().hasExtra("enroll_after_finger");
        if (i == 2) {
            if (i2 == 1) {
                handleBiometricResultSkipOrFinished(i2, getSetResultIntentExtra(intent));
            } else if (i2 == 2 || i2 == 11) {
                if (!BiometricUtils.tryStartingNextBiometricEnroll(this)) {
                    handleBiometricResultSkipOrFinished(i2, intent);
                }
            } else if (i2 == 3) {
                setResult(i2, intent);
                finish();
            }
        } else if (i == 1) {
            if (i2 == 1) {
                getLockPatternUtils();
                throw null;
            }
            setResult(i2, intent);
            finish();
        } else if (i == 4) {
            if (i2 != -1 || intent == null) {
                setResult(i2, intent);
                finish();
            } else if (!onSetOrConfirmCredentials()) {
                overridePendingTransition(R.anim.sud_slide_next_in, R.anim.sud_slide_next_out);
                getNextButton().setEnabled(false);
                getChallenge(
                        new BiometricEnrollIntroduction$$ExternalSyntheticLambda0(this, intent));
            }
        } else if (i == 3) {
            overridePendingTransition(R.anim.sud_slide_back_in, R.anim.sud_slide_back_out);
        } else if (i == 6 || z) {
            if (i2 == 1) {
                handleBiometricResultSkipOrFinished(i2, intent);
            } else if (i2 == 2 || i2 == 11) {
                if (i != 7) {
                    handleBiometricResultSkipOrFinished(i2, intent);
                } else if (checkMaxEnrolled() != 0) {
                    handleBiometricResultSkipOrFinished(i2, intent);
                }
            } else if (i2 != 0) {
                setResult(i2, intent);
                finish();
            }
        }
        super.onActivityResult(i, i2, intent);
    }

    @Override // com.android.settings.biometrics.BiometricEnrollBase,
              // com.android.settings.core.InstrumentedActivity,
              // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("SecBiometricsEnrollment", "launchSecBiometricsEnroll");
        Intent secBiometricsEnrollIntent = getSecBiometricsEnrollIntent();
        int i = this.mUserId;
        if (i != -10000) {
            secBiometricsEnrollIntent.putExtra("android.intent.extra.USER_ID", i);
        }
        try {
            startActivity(secBiometricsEnrollIntent);
            finish();
        } catch (ActivityNotFoundException e) {
            Log.e("SecBiometricsEnrollment", "Activity Not Found !");
            e.printStackTrace();
        }
    }

    public void onEnrollmentSkipped(Intent intent) {
        setResult(2, intent);
        finish();
    }

    public abstract void onFinishedEnrolling(Intent intent);

    @Override // com.android.settings.biometrics.BiometricEnrollBase,
              // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("confirming_credentials", false);
        bundle.putBoolean("scrolled", false);
    }

    public boolean onSetOrConfirmCredentials() {
        return this instanceof FaceEnrollParentalConsent;
    }

    public void updateDescriptionText() {}

    public Intent getSetResultIntentExtra(Intent intent) {
        return intent;
    }
}

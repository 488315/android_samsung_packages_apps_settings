package com.android.settings.biometrics.activeunlock;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.biometrics.BiometricEnrollActivity;
import com.android.settings.biometrics.BiometricEnrollBase;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ActiveUnlockRequireBiometricSetup extends BiometricEnrollBase {
    static final int BIOMETRIC_ENROLL_REQUEST = 1001;
    public ActiveUnlockStatusUtils mActiveUnlockStatusUtils;
    public long mGkPwHandle;
    public boolean mNextClicked;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1940;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        Intent intent2;
        super.onActivityResult(i, i2, intent);
        if (i == 1001 && i2 != 0) {
            int i3 = this.mUserId;
            FingerprintManager fingerprintManagerOrNull = Utils.getFingerprintManagerOrNull(this);
            FaceManager faceManagerOrNull = Utils.getFaceManagerOrNull(this);
            if (((fingerprintManagerOrNull != null
                                    && fingerprintManagerOrNull.hasEnrolledFingerprints(i3))
                            || (faceManagerOrNull != null
                                    && faceManagerOrNull.hasEnrolledTemplates(i3)))
                    && (intent2 = this.mActiveUnlockStatusUtils.getIntent()) != null) {
                startActivityForResult(intent2, 1002);
            }
        }
        this.mNextClicked = false;
        finish();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onBackPressed() {
        finish();
    }

    @Override // com.android.settings.biometrics.BiometricEnrollBase,
              // com.android.settings.core.InstrumentedActivity,
              // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activeunlock_require_biometric_setup);
        this.mActiveUnlockStatusUtils = new ActiveUnlockStatusUtils(this);
        this.mUserId =
                getIntent().getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
        Log.i("ActiveUnlockRequireBiometricSetup", "mUserId = " + this.mUserId);
        this.mGkPwHandle = getIntent().getLongExtra("gk_pw_handle", 0L);
        PackageManager packageManager = getApplicationContext().getPackageManager();
        boolean hasSystemFeature =
                packageManager.hasSystemFeature("android.hardware.biometrics.face");
        boolean hasSystemFeature2 = packageManager.hasSystemFeature("android.hardware.fingerprint");
        if (hasSystemFeature && hasSystemFeature2) {
            setHeaderText(
                    R.string.security_settings_activeunlock_require_face_fingerprint_setup_title);
            setDescriptionText(
                    R.string.security_settings_activeunlock_require_face_fingerprint_setup_message);
        } else if (hasSystemFeature2) {
            setHeaderText(R.string.security_settings_activeunlock_require_fingerprint_setup_title);
            setDescriptionText(
                    R.string.security_settings_activeunlock_require_fingerprint_setup_message);
        } else if (hasSystemFeature) {
            setHeaderText(R.string.security_settings_activeunlock_require_face_setup_title);
            setDescriptionText(R.string.security_settings_activeunlock_require_face_setup_message);
        }
        FooterBarMixin footerBarMixin = (FooterBarMixin) getLayout().getMixin(FooterBarMixin.class);
        this.mFooterBarMixin = footerBarMixin;
        final int i = 0;
        footerBarMixin.setSecondaryButton(
                new FooterButton(
                        getString(R.string.cancel),
                        new View.OnClickListener(this) { // from class:
                            // com.android.settings.biometrics.activeunlock.ActiveUnlockRequireBiometricSetup$$ExternalSyntheticLambda0
                            public final /* synthetic */ ActiveUnlockRequireBiometricSetup f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i2 = i;
                                ActiveUnlockRequireBiometricSetup
                                        activeUnlockRequireBiometricSetup = this.f$0;
                                switch (i2) {
                                    case 0:
                                        int i3 =
                                                ActiveUnlockRequireBiometricSetup
                                                        .BIOMETRIC_ENROLL_REQUEST;
                                        activeUnlockRequireBiometricSetup.finish();
                                        break;
                                    default:
                                        activeUnlockRequireBiometricSetup.mNextClicked = true;
                                        Intent intent =
                                                new Intent(
                                                        activeUnlockRequireBiometricSetup,
                                                        (Class<?>)
                                                                BiometricEnrollActivity
                                                                        .InternalActivity.class);
                                        intent.setAction("android.settings.BIOMETRIC_ENROLL");
                                        intent.putExtra(
                                                "android.provider.extra.BIOMETRIC_AUTHENTICATORS_ALLOWED",
                                                15);
                                        intent.putExtra(
                                                "android.intent.extra.USER_ID",
                                                activeUnlockRequireBiometricSetup.mUserId);
                                        intent.putExtra(
                                                "gk_pw_handle",
                                                activeUnlockRequireBiometricSetup.mGkPwHandle);
                                        activeUnlockRequireBiometricSetup.startActivityForResult(
                                                intent, 1001);
                                        break;
                                }
                            }
                        },
                        2,
                        2132083806),
                false);
        final int i2 = 1;
        this.mFooterBarMixin.setPrimaryButton(
                new FooterButton(
                        getString(R.string.security_settings_activeunlock_biometric_setup),
                        new View.OnClickListener(this) { // from class:
                            // com.android.settings.biometrics.activeunlock.ActiveUnlockRequireBiometricSetup$$ExternalSyntheticLambda0
                            public final /* synthetic */ ActiveUnlockRequireBiometricSetup f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i22 = i2;
                                ActiveUnlockRequireBiometricSetup
                                        activeUnlockRequireBiometricSetup = this.f$0;
                                switch (i22) {
                                    case 0:
                                        int i3 =
                                                ActiveUnlockRequireBiometricSetup
                                                        .BIOMETRIC_ENROLL_REQUEST;
                                        activeUnlockRequireBiometricSetup.finish();
                                        break;
                                    default:
                                        activeUnlockRequireBiometricSetup.mNextClicked = true;
                                        Intent intent =
                                                new Intent(
                                                        activeUnlockRequireBiometricSetup,
                                                        (Class<?>)
                                                                BiometricEnrollActivity
                                                                        .InternalActivity.class);
                                        intent.setAction("android.settings.BIOMETRIC_ENROLL");
                                        intent.putExtra(
                                                "android.provider.extra.BIOMETRIC_AUTHENTICATORS_ALLOWED",
                                                15);
                                        intent.putExtra(
                                                "android.intent.extra.USER_ID",
                                                activeUnlockRequireBiometricSetup.mUserId);
                                        intent.putExtra(
                                                "gk_pw_handle",
                                                activeUnlockRequireBiometricSetup.mGkPwHandle);
                                        activeUnlockRequireBiometricSetup.startActivityForResult(
                                                intent, 1001);
                                        break;
                                }
                            }
                        },
                        5,
                        2132083805));
    }

    @Override // com.android.settings.biometrics.BiometricEnrollBase
    public final boolean shouldFinishWhenBackgrounded() {
        return super.shouldFinishWhenBackgrounded() && !this.mNextClicked;
    }
}

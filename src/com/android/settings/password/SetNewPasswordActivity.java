package com.android.settings.password;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.app.admin.PasswordMetrics;
import android.content.ComponentName;
import android.content.Intent;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;

import com.android.settings.Utils;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.knox.UCMUtils;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SetNewPasswordActivity extends Activity implements SetNewPasswordController.Ui {
    public String mNewPasswordAction;
    public SetNewPasswordController mSetNewPasswordController;
    public int mRequestedMinComplexity = 0;
    public boolean mDevicePasswordRequirementOnly = false;
    public String mCallerAppName = null;

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        Bundle bundle2;
        super.onCreate(bundle);
        Intent intent = getIntent();
        String action = intent.getAction();
        this.mNewPasswordAction = action;
        if (!"android.app.action.SET_NEW_PASSWORD".equals(action)
                && !"android.app.action.SET_NEW_PARENT_PROFILE_PASSWORD"
                        .equals(this.mNewPasswordAction)) {
            Log.e("SetNewPasswordActivity", "Unexpected action to launch this activity");
            finish();
            return;
        }
        String callingAppPackageName = PasswordUtils.getCallingAppPackageName(getActivityToken());
        int intExtra =
                (getIntent().hasExtra("android.app.extra.PASSWORD_COMPLEXITY")
                                ? getIntent()
                                        .getIntExtra("android.app.extra.PASSWORD_COMPLEXITY", 0)
                                : Integer.MIN_VALUE)
                        | (getIntent()
                                        .getBooleanExtra(
                                                "android.app.extra.DEVICE_PASSWORD_REQUIREMENT_ONLY",
                                                false)
                                ? 1073741824
                                : 0);
        int i = "android.app.action.SET_NEW_PASSWORD".equals(this.mNewPasswordAction) ? 1645 : 1646;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        SettingsMetricsFeatureProvider metricsFeatureProvider =
                featureFactoryImpl.getMetricsFeatureProvider();
        metricsFeatureProvider.getClass();
        metricsFeatureProvider.action(
                MetricsFeatureProvider.getAttribution(this),
                i,
                1644,
                intExtra,
                callingAppPackageName);
        if (UCMUtils.isEnforcedCredentialStorageExistAsUser(UserHandle.myUserId())) {
            Log.e("SetNewPasswordActivity", "Do not change locktype in UCMKeyguard");
            finish();
            return;
        }
        IBinder activityToken = getActivityToken();
        String callingAppPackageName2 = PasswordUtils.getCallingAppPackageName(activityToken);
        this.mCallerAppName =
                (String)
                        ((callingAppPackageName2 == null
                                        || callingAppPackageName2.equals(
                                                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG))
                                ? null
                                : Utils.getApplicationLabel(this, callingAppPackageName2));
        if ("android.app.action.SET_NEW_PASSWORD".equals(this.mNewPasswordAction)
                && intent.hasExtra("android.app.extra.PASSWORD_COMPLEXITY")) {
            if (!PasswordUtils.isCallingAppPermitted(this, activityToken)) {
                PasswordUtils.crashCallingApplication(activityToken);
                finish();
                return;
            }
            this.mRequestedMinComplexity =
                    PasswordMetrics.sanitizeComplexityLevel(
                            intent.getIntExtra("android.app.extra.PASSWORD_COMPLEXITY", 0));
        }
        if ("android.app.action.SET_NEW_PARENT_PROFILE_PASSWORD".equals(this.mNewPasswordAction)) {
            boolean booleanExtra =
                    intent.getBooleanExtra(
                            "android.app.extra.DEVICE_PASSWORD_REQUIREMENT_ONLY", false);
            this.mDevicePasswordRequirementOnly = booleanExtra;
            AbsAdapter$$ExternalSyntheticOutline0.m(
                    "DEVICE_PASSWORD_REQUIREMENT_ONLY: ", "SetNewPasswordActivity", booleanExtra);
        }
        SetNewPasswordController setNewPasswordController =
                new SetNewPasswordController(
                        "android.app.action.SET_NEW_PASSWORD".equals(intent.getAction())
                                ? Utils.getSecureTargetUser(
                                                activityToken,
                                                UserManager.get(this),
                                                null,
                                                intent.getExtras())
                                        .getIdentifier()
                                : ActivityManager.getCurrentUser(),
                        getPackageManager(),
                        Utils.getFingerprintManagerOrNull(this),
                        !intent.getBooleanExtra("for_fingerprint_only", false)
                                ? Utils.getFaceManagerOrNull(this)
                                : null,
                        (DevicePolicyManager) getSystemService("device_policy"),
                        this);
        this.mSetNewPasswordController = setNewPasswordController;
        setNewPasswordController.mShowCredentialTypeOnly =
                getIntent().getBooleanExtra("hide_biometrics_menu", false);
        this.mSetNewPasswordController.mFromSecNonBiometrics =
                getIntent().getBooleanExtra("from_sec_non_biometrics", false);
        SetNewPasswordController setNewPasswordController2 = this.mSetNewPasswordController;
        boolean z = setNewPasswordController2.mShowCredentialTypeOnly;
        int i2 = setNewPasswordController2.mTargetUserId;
        if (z) {
            boolean hasSystemFeature =
                    setNewPasswordController2.mPackageManager.hasSystemFeature(
                            "android.hardware.fingerprint");
            boolean hasSystemFeature2 =
                    setNewPasswordController2.mPackageManager.hasSystemFeature(
                            "android.hardware.biometrics.face");
            FingerprintManager fingerprintManager = setNewPasswordController2.mFingerprintManager;
            boolean z2 =
                    fingerprintManager != null
                            && fingerprintManager.isHardwareDetected()
                            && !setNewPasswordController2.mFingerprintManager
                                    .hasEnrolledFingerprints(i2)
                            && (setNewPasswordController2.mDevicePolicyManager
                                                    .getKeyguardDisabledFeatures(null, i2)
                                            & 32)
                                    == 0;
            FaceManager faceManager = setNewPasswordController2.mFaceManager;
            boolean z3 =
                    faceManager != null
                            && faceManager.isHardwareDetected()
                            && !setNewPasswordController2.mFaceManager.hasEnrolledTemplates(i2)
                            && (setNewPasswordController2.mDevicePolicyManager
                                                    .getKeyguardDisabledFeatures(null, i2)
                                            & 128)
                                    == 0;
            if (hasSystemFeature2 && z3 && hasSystemFeature && z2) {
                bundle2 = new Bundle();
                bundle2.putBoolean("hide_insecure_options", true);
                bundle2.putBoolean("request_gk_pw_handle", true);
                bundle2.putBoolean(
                        "for_biometrics", !setNewPasswordController2.mFromSecNonBiometrics);
            } else if (hasSystemFeature2 && z3) {
                bundle2 = new Bundle();
                bundle2.putBoolean("hide_insecure_options", true);
                bundle2.putBoolean("request_gk_pw_handle", true);
                bundle2.putBoolean("for_face", !setNewPasswordController2.mFromSecNonBiometrics);
            } else if (hasSystemFeature && z2) {
                bundle2 = new Bundle();
                bundle2.putBoolean("hide_insecure_options", true);
                bundle2.putBoolean("request_gk_pw_handle", true);
                bundle2.putBoolean(
                        "for_fingerprint", !setNewPasswordController2.mFromSecNonBiometrics);
            } else {
                bundle2 = new Bundle();
            }
        } else {
            bundle2 = new Bundle();
        }
        bundle2.putInt("android.intent.extra.USER_ID", i2);
        SetNewPasswordActivity setNewPasswordActivity =
                (SetNewPasswordActivity) setNewPasswordController2.mUi;
        Intent intent2 =
                WizardManagerHelper.isAnySetupWizard(setNewPasswordActivity.getIntent())
                        ? new Intent(
                                setNewPasswordActivity, (Class<?>) SetupChooseLockGeneric.class)
                        : new Intent(setNewPasswordActivity, (Class<?>) ChooseLockGeneric.class);
        intent2.setAction(setNewPasswordActivity.mNewPasswordAction);
        intent2.putExtras(bundle2);
        intent2.putExtra(
                "choose_lock_setup_screen_title",
                setNewPasswordActivity
                        .getIntent()
                        .getIntExtra("choose_lock_setup_screen_title", -1));
        intent2.putExtra(
                "choose_lock_setup_screen_description",
                setNewPasswordActivity
                        .getIntent()
                        .getIntExtra("choose_lock_setup_screen_description", -1));
        intent2.putExtra(
                "for_fingerprint_only",
                setNewPasswordActivity.getIntent().getBooleanExtra("for_fingerprint_only", false));
        String str = setNewPasswordActivity.mCallerAppName;
        if (str != null) {
            intent2.putExtra("caller_app_name", str);
        }
        int i3 = setNewPasswordActivity.mRequestedMinComplexity;
        if (i3 != 0) {
            intent2.putExtra("requested_min_complexity", i3);
        }
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager)
                        setNewPasswordActivity.getSystemService(DevicePolicyManager.class);
        String callingAppPackageName3 =
                PasswordUtils.getCallingAppPackageName(setNewPasswordActivity.getActivityToken());
        List<ComponentName> activeAdmins = devicePolicyManager.getActiveAdmins();
        if (activeAdmins != null) {
            Iterator<ComponentName> it = activeAdmins.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (it.next().getPackageName().equals(callingAppPackageName3)) {
                    intent2.putExtra("is_calling_app_admin", true);
                    break;
                }
            }
        }
        intent2.putExtra(
                "device_password_requirement_only",
                setNewPasswordActivity.mDevicePasswordRequirementOnly);
        WizardManagerHelper.copyWizardManagerExtras(setNewPasswordActivity.getIntent(), intent2);
        setNewPasswordActivity.startActivity(intent2);
        setNewPasswordActivity.finish();
    }
}

package com.samsung.android.settings.biometrics;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.password.SetupSkipDialog;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.biometrics.face.FaceLockSettings;
import com.samsung.android.settings.biometrics.face.FaceSettingsPreferenceController;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintLockSettings;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsPreferenceController;
import com.sec.android.secsetupwizardlib.SuwBaseActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SuwBiometricsSetupActivity extends SuwBaseActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityResultRegistry.AnonymousClass2 mSetLockResult =
            (ActivityResultRegistry.AnonymousClass2)
                    registerForActivityResult(
                            new ActivityResultContracts$StartActivityForResult(0),
                            new ActivityResultCallback() { // from class:
                                                           // com.samsung.android.settings.biometrics.SuwBiometricsSetupActivity$$ExternalSyntheticLambda3
                                @Override // androidx.activity.result.ActivityResultCallback
                                public final void onActivityResult(Object obj) {
                                    int i = SuwBiometricsSetupActivity.$r8$clinit;
                                    SuwBiometricsSetupActivity suwBiometricsSetupActivity =
                                            SuwBiometricsSetupActivity.this;
                                    suwBiometricsSetupActivity.getClass();
                                    int i2 = ((ActivityResult) obj).mResultCode;
                                    Log.d(
                                            "SuwBiometricsSetupLock",
                                            "Biometrics Setup Result : " + i2);
                                    suwBiometricsSetupActivity.setResult(i2);
                                    if (i2 != 0) {
                                        suwBiometricsSetupActivity.finish();
                                    }
                                }
                            });
    public int mUserId;

    @Override // com.sec.android.secsetupwizardlib.SuwBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        Log.d("SuwBiometricsSetupLock", "onCreate");
        super.onCreate(bundle);
        this.mUserId =
                getIntent().getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
        setHeaderTitle(R.string.sec_biometrics_set_up);
        setDescriptionText(R.string.sec_biometrics_set_up_description);
        setHeaderIcon(getDrawable(R.drawable.sec_ic_biometric_lock_suw));
        setContentView(R.layout.sec_biometrics_setup_layout);
        if (!Rune.LOCKSCREEN_SECURITY_HIDE_SKIP_SUW_BUTTON) {
            final int i = 2;
            setSecondaryActionButton(
                    R.string.sec_biometrics_disclaimer_button_skip,
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.samsung.android.settings.biometrics.SuwBiometricsSetupActivity$$ExternalSyntheticLambda0
                        public final /* synthetic */ SuwBiometricsSetupActivity f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            int i2 = i;
                            SuwBiometricsSetupActivity suwBiometricsSetupActivity = this.f$0;
                            switch (i2) {
                                case 0:
                                    int i3 = SuwBiometricsSetupActivity.$r8$clinit;
                                    suwBiometricsSetupActivity.getClass();
                                    Intent intent = new Intent();
                                    intent.setClass(
                                            suwBiometricsSetupActivity, FaceLockSettings.class);
                                    intent.putExtra("fromSetupWizard", true);
                                    intent.putExtra("previousStage", "setupwizard_face");
                                    intent.putExtra("useImmersiveMode", true);
                                    suwBiometricsSetupActivity.mSetLockResult.launch(intent);
                                    break;
                                case 1:
                                    int i4 = SuwBiometricsSetupActivity.$r8$clinit;
                                    suwBiometricsSetupActivity.getClass();
                                    Intent intent2 = new Intent();
                                    intent2.setClass(
                                            suwBiometricsSetupActivity,
                                            FingerprintLockSettings.class);
                                    intent2.putExtra("fromSetupWizard", true);
                                    intent2.putExtra(
                                            "previousStage", "google_setupwizard_fingerprint");
                                    intent2.putExtra("useImmersiveMode", true);
                                    suwBiometricsSetupActivity.mSetLockResult.launch(intent2);
                                    break;
                                default:
                                    int i5 = SuwBiometricsSetupActivity.$r8$clinit;
                                    suwBiometricsSetupActivity.getClass();
                                    if (!new LockPatternUtils(suwBiometricsSetupActivity)
                                            .isSecure(suwBiometricsSetupActivity.mUserId)) {
                                        Intent intent3 = suwBiometricsSetupActivity.getIntent();
                                        SetupSkipDialog.newInstance(
                                                        intent3.getBooleanExtra(
                                                                ":settings:frp_supported", false),
                                                        intent3.getBooleanExtra(
                                                                "isSetupFlow", false))
                                                .show(
                                                        suwBiometricsSetupActivity
                                                                .getSupportFragmentManager());
                                        break;
                                    } else {
                                        suwBiometricsSetupActivity.setResult(11);
                                        suwBiometricsSetupActivity.finish();
                                        break;
                                    }
                            }
                        }
                    });
        }
        boolean z = new FaceSettingsPreferenceController(this).getAvailabilityStatus() == 0;
        boolean z2 = new FingerprintSettingsPreferenceController(this).getAvailabilityStatus() == 0;
        if (z || z2) {
            View findViewById = findViewById(R.id.face_unlock);
            if (findViewById != null) {
                if (z) {
                    final int i2 = 0;
                    findViewById.setOnClickListener(
                            new View.OnClickListener(
                                    this) { // from class:
                                            // com.samsung.android.settings.biometrics.SuwBiometricsSetupActivity$$ExternalSyntheticLambda0
                                public final /* synthetic */ SuwBiometricsSetupActivity f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    int i22 = i2;
                                    SuwBiometricsSetupActivity suwBiometricsSetupActivity =
                                            this.f$0;
                                    switch (i22) {
                                        case 0:
                                            int i3 = SuwBiometricsSetupActivity.$r8$clinit;
                                            suwBiometricsSetupActivity.getClass();
                                            Intent intent = new Intent();
                                            intent.setClass(
                                                    suwBiometricsSetupActivity,
                                                    FaceLockSettings.class);
                                            intent.putExtra("fromSetupWizard", true);
                                            intent.putExtra("previousStage", "setupwizard_face");
                                            intent.putExtra("useImmersiveMode", true);
                                            suwBiometricsSetupActivity.mSetLockResult.launch(
                                                    intent);
                                            break;
                                        case 1:
                                            int i4 = SuwBiometricsSetupActivity.$r8$clinit;
                                            suwBiometricsSetupActivity.getClass();
                                            Intent intent2 = new Intent();
                                            intent2.setClass(
                                                    suwBiometricsSetupActivity,
                                                    FingerprintLockSettings.class);
                                            intent2.putExtra("fromSetupWizard", true);
                                            intent2.putExtra(
                                                    "previousStage",
                                                    "google_setupwizard_fingerprint");
                                            intent2.putExtra("useImmersiveMode", true);
                                            suwBiometricsSetupActivity.mSetLockResult.launch(
                                                    intent2);
                                            break;
                                        default:
                                            int i5 = SuwBiometricsSetupActivity.$r8$clinit;
                                            suwBiometricsSetupActivity.getClass();
                                            if (!new LockPatternUtils(suwBiometricsSetupActivity)
                                                    .isSecure(suwBiometricsSetupActivity.mUserId)) {
                                                Intent intent3 =
                                                        suwBiometricsSetupActivity.getIntent();
                                                SetupSkipDialog.newInstance(
                                                                intent3.getBooleanExtra(
                                                                        ":settings:frp_supported",
                                                                        false),
                                                                intent3.getBooleanExtra(
                                                                        "isSetupFlow", false))
                                                        .show(
                                                                suwBiometricsSetupActivity
                                                                        .getSupportFragmentManager());
                                                break;
                                            } else {
                                                suwBiometricsSetupActivity.setResult(11);
                                                suwBiometricsSetupActivity.finish();
                                                break;
                                            }
                                    }
                                }
                            });
                } else {
                    findViewById.setVisibility(8);
                }
            }
            View findViewById2 = findViewById(R.id.fingerprint_unlock);
            if (findViewById2 != null) {
                if (z2) {
                    final int i3 = 1;
                    findViewById2.setOnClickListener(
                            new View.OnClickListener(
                                    this) { // from class:
                                            // com.samsung.android.settings.biometrics.SuwBiometricsSetupActivity$$ExternalSyntheticLambda0
                                public final /* synthetic */ SuwBiometricsSetupActivity f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    int i22 = i3;
                                    SuwBiometricsSetupActivity suwBiometricsSetupActivity =
                                            this.f$0;
                                    switch (i22) {
                                        case 0:
                                            int i32 = SuwBiometricsSetupActivity.$r8$clinit;
                                            suwBiometricsSetupActivity.getClass();
                                            Intent intent = new Intent();
                                            intent.setClass(
                                                    suwBiometricsSetupActivity,
                                                    FaceLockSettings.class);
                                            intent.putExtra("fromSetupWizard", true);
                                            intent.putExtra("previousStage", "setupwizard_face");
                                            intent.putExtra("useImmersiveMode", true);
                                            suwBiometricsSetupActivity.mSetLockResult.launch(
                                                    intent);
                                            break;
                                        case 1:
                                            int i4 = SuwBiometricsSetupActivity.$r8$clinit;
                                            suwBiometricsSetupActivity.getClass();
                                            Intent intent2 = new Intent();
                                            intent2.setClass(
                                                    suwBiometricsSetupActivity,
                                                    FingerprintLockSettings.class);
                                            intent2.putExtra("fromSetupWizard", true);
                                            intent2.putExtra(
                                                    "previousStage",
                                                    "google_setupwizard_fingerprint");
                                            intent2.putExtra("useImmersiveMode", true);
                                            suwBiometricsSetupActivity.mSetLockResult.launch(
                                                    intent2);
                                            break;
                                        default:
                                            int i5 = SuwBiometricsSetupActivity.$r8$clinit;
                                            suwBiometricsSetupActivity.getClass();
                                            if (!new LockPatternUtils(suwBiometricsSetupActivity)
                                                    .isSecure(suwBiometricsSetupActivity.mUserId)) {
                                                Intent intent3 =
                                                        suwBiometricsSetupActivity.getIntent();
                                                SetupSkipDialog.newInstance(
                                                                intent3.getBooleanExtra(
                                                                        ":settings:frp_supported",
                                                                        false),
                                                                intent3.getBooleanExtra(
                                                                        "isSetupFlow", false))
                                                        .show(
                                                                suwBiometricsSetupActivity
                                                                        .getSupportFragmentManager());
                                                break;
                                            } else {
                                                suwBiometricsSetupActivity.setResult(11);
                                                suwBiometricsSetupActivity.finish();
                                                break;
                                            }
                                    }
                                }
                            });
                } else {
                    findViewById2.setVisibility(8);
                }
            }
        } else {
            finish();
        }
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.sswl_scroll_view);
        if (viewGroup != null) {
            viewGroup.setFocusable(false);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        View findViewById = findViewById(R.id.content_layout);
        if (findViewById != null) {
            findViewById.setBackgroundColor(
                    getResources().getColor(R.color.sec_widget_round_and_bgcolor, null));
            findViewById.semSetRoundedCorners(15);
            findViewById.semSetRoundedCornerColor(
                    15, getColor(R.color.sec_lock_suw_background_color));
        }
    }
}

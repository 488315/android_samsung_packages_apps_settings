package com.samsung.android.settings.biometrics;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BiometricsParentalConsentActivity extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mHasFeatureFace;
    public boolean mHasFeatureFingerprint;

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        String str;
        String str2;
        super.onCreate(bundle);
        Log.d("BiometricsParentalConsentActivity", "onCreate");
        this.mHasFeatureFingerprint = getIntent().getBooleanExtra("mHasFeatureFingerprint", false);
        this.mHasFeatureFace = getIntent().getBooleanExtra("mHasFeatureFace", false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Resources resources = getResources();
        String string = resources.getString(R.string.sec_biometrics_parental_consent_allow);
        String string2 = resources.getString(R.string.sec_biometrics_parental_consent_dont_allow);
        boolean z = this.mHasFeatureFingerprint;
        if (z && this.mHasFeatureFace) {
            str = resources.getString(R.string.sec_biometrics_parental_consent_title_finger_face);
            str2 =
                    resources.getString(
                            R.string.sec_biometrics_parental_consent_message_finger_face);
        } else if (z) {
            str = resources.getString(R.string.sec_biometrics_parental_consent_title_finger);
            str2 = resources.getString(R.string.sec_biometrics_parental_consent_message_finger);
        } else if (this.mHasFeatureFace) {
            str = resources.getString(R.string.sec_biometrics_parental_consent_title_face);
            str2 = resources.getString(R.string.sec_biometrics_parental_consent_message_face);
        } else {
            str = ApnSettings.MVNO_NONE;
            str2 = ApnSettings.MVNO_NONE;
        }
        builder.setTitle(str);
        builder.setMessage(str2);
        builder.setCancelable(false);
        final int i = 0;
        builder.setPositiveButton(
                string,
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.biometrics.BiometricsParentalConsentActivity$$ExternalSyntheticLambda0
                    public final /* synthetic */ BiometricsParentalConsentActivity f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        int i3 = i;
                        BiometricsParentalConsentActivity biometricsParentalConsentActivity =
                                this.f$0;
                        switch (i3) {
                            case 0:
                                int i4 = BiometricsParentalConsentActivity.$r8$clinit;
                                biometricsParentalConsentActivity.setResult(4);
                                break;
                            default:
                                int i5 = BiometricsParentalConsentActivity.$r8$clinit;
                                biometricsParentalConsentActivity.setResult(5);
                                break;
                        }
                    }
                });
        final int i2 = 1;
        builder.setNegativeButton(
                string2,
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.biometrics.BiometricsParentalConsentActivity$$ExternalSyntheticLambda0
                    public final /* synthetic */ BiometricsParentalConsentActivity f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i22) {
                        int i3 = i2;
                        BiometricsParentalConsentActivity biometricsParentalConsentActivity =
                                this.f$0;
                        switch (i3) {
                            case 0:
                                int i4 = BiometricsParentalConsentActivity.$r8$clinit;
                                biometricsParentalConsentActivity.setResult(4);
                                break;
                            default:
                                int i5 = BiometricsParentalConsentActivity.$r8$clinit;
                                biometricsParentalConsentActivity.setResult(5);
                                break;
                        }
                    }
                });
        builder.setOnDismissListener(
                new DialogInterface
                        .OnDismissListener() { // from class:
                                               // com.samsung.android.settings.biometrics.BiometricsParentalConsentActivity$$ExternalSyntheticLambda2
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        BiometricsParentalConsentActivity biometricsParentalConsentActivity =
                                BiometricsParentalConsentActivity.this;
                        int i3 = BiometricsParentalConsentActivity.$r8$clinit;
                        biometricsParentalConsentActivity.finish();
                    }
                });
        builder.create().show();
    }
}

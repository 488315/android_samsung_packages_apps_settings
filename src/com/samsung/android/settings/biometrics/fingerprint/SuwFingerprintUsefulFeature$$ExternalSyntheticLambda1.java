package com.samsung.android.settings.biometrics.fingerprint;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.settings.biometrics.BiometricsGenericHelper;
import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SuwFingerprintUsefulFeature$$ExternalSyntheticLambda1
        implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SuwFingerprintUsefulFeature f$0;

    public /* synthetic */ SuwFingerprintUsefulFeature$$ExternalSyntheticLambda1(
            SuwFingerprintUsefulFeature suwFingerprintUsefulFeature, int i) {
        this.$r8$classId = i;
        this.f$0 = suwFingerprintUsefulFeature;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        SuwFingerprintUsefulFeature suwFingerprintUsefulFeature = this.f$0;
        switch (i) {
            case 0:
                int i2 = SuwFingerprintUsefulFeature.$r8$clinit;
                suwFingerprintUsefulFeature.getClass();
                Bundle bundle = new Bundle();
                bundle.putInt("android.intent.extra.USER_ID", suwFingerprintUsefulFeature.mUserId);
                bundle.putBoolean("fingerprint_useful_feature", true);
                SubSettingLauncher subSettingLauncher =
                        new SubSettingLauncher(suwFingerprintUsefulFeature.mContext);
                String name = FingerprintShowIconSettings.class.getName();
                SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
                launchRequest.mDestinationName = name;
                launchRequest.mArguments = bundle;
                launchRequest.mSourceMetricsCategory = 8288;
                subSettingLauncher.launch();
                break;
            case 1:
                if (!FingerprintSettingsUtils.getFingerprintEffectValue(
                        suwFingerprintUsefulFeature, suwFingerprintUsefulFeature.mUserId)) {
                    Log.d("FpstSuwFingerprintUsefulFeature", "Set Finger effect enable");
                    suwFingerprintUsefulFeature.mFingerEffectSwitch.setChecked(true);
                    break;
                } else {
                    Log.d("FpstSuwFingerprintUsefulFeature", "Set Finger effect disable");
                    suwFingerprintUsefulFeature.mFingerEffectSwitch.setChecked(false);
                    break;
                }
            case 2:
                if (!FingerprintSettingsUtils.getFingerprintAlwaysOnBooleanValue(
                        suwFingerprintUsefulFeature, suwFingerprintUsefulFeature.mUserId)) {
                    Log.d("FpstSuwFingerprintUsefulFeature", "Set Always on enable");
                    suwFingerprintUsefulFeature.mAlwaysOnSwitch.setChecked(true);
                    break;
                } else {
                    Log.d("FpstSuwFingerprintUsefulFeature", "Set Always on disable");
                    suwFingerprintUsefulFeature.mAlwaysOnSwitch.setChecked(false);
                    break;
                }
            case 3:
                suwFingerprintUsefulFeature.mScreenOffIconAodSpinner.performClick();
                break;
            default:
                int i3 = SuwFingerprintUsefulFeature.$r8$clinit;
                suwFingerprintUsefulFeature.getClass();
                LoggingHelper.insertEventLogging(
                        BiometricsGenericHelper.getSaLogIdByDisplayType(
                                suwFingerprintUsefulFeature, 8286),
                        8287);
                suwFingerprintUsefulFeature.setResult(-1);
                suwFingerprintUsefulFeature.finish();
                break;
        }
    }
}

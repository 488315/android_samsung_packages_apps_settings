package com.samsung.android.settings.biometrics.face;

import android.util.Log;
import android.view.View;

import com.samsung.android.settings.biometrics.BiometricsGenericHelper;
import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SuwFaceUsefulFeature$$ExternalSyntheticLambda0
        implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SuwFaceUsefulFeature f$0;

    public /* synthetic */ SuwFaceUsefulFeature$$ExternalSyntheticLambda0(
            SuwFaceUsefulFeature suwFaceUsefulFeature, int i) {
        this.$r8$classId = i;
        this.f$0 = suwFaceUsefulFeature;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        SuwFaceUsefulFeature suwFaceUsefulFeature = this.f$0;
        switch (i) {
            case 0:
                int i2 = SuwFaceUsefulFeature.$r8$clinit;
                suwFaceUsefulFeature.getClass();
                LoggingHelper.insertEventLogging(
                        BiometricsGenericHelper.getSaLogIdByDisplayType(suwFaceUsefulFeature, 8424),
                        8425);
                suwFaceUsefulFeature.setResult(-1);
                suwFaceUsefulFeature.finish();
                break;
            case 1:
                if (!FaceSettingsHelper.getFaceBrightenScreenBooleanValue(
                        suwFaceUsefulFeature, suwFaceUsefulFeature.mUserId)) {
                    Log.d("FcstSuwFaceUsefulFeature", "Set BrightenScreen enable");
                    suwFaceUsefulFeature.mBrightenScreenSwitch.setChecked(true);
                    break;
                } else {
                    Log.d("FcstSuwFaceUsefulFeature", "Set BrightenScreen disable");
                    suwFaceUsefulFeature.mBrightenScreenSwitch.setChecked(false);
                    break;
                }
            case 2:
                if (!FaceSettingsHelper.getFaceStayOnLockScreenBooleanValue(
                        suwFaceUsefulFeature, suwFaceUsefulFeature.mUserId)) {
                    Log.d("FcstSuwFaceUsefulFeature", "Set StayOnLockScreen enable");
                    suwFaceUsefulFeature.mStayOnLockScreenSwitch.setChecked(true);
                    break;
                } else {
                    Log.d("FcstSuwFaceUsefulFeature", "Set StayOnLockScreen disable");
                    suwFaceUsefulFeature.mStayOnLockScreenSwitch.setChecked(false);
                    break;
                }
            case 3:
                if (FaceSettingsHelper.getFaceRecognizeMaskDbValue(
                                suwFaceUsefulFeature, suwFaceUsefulFeature.mUserId)
                        != 1) {
                    Log.d("FcstSuwFaceUsefulFeature", "Set StayOnLockScreen enable");
                    suwFaceUsefulFeature.mRecognizeWithMaskSwitch.setChecked(true);
                    break;
                } else {
                    Log.d("FcstSuwFaceUsefulFeature", "Set StayOnLockScreen disable");
                    suwFaceUsefulFeature.mRecognizeWithMaskSwitch.setChecked(false);
                    break;
                }
            default:
                if (!FaceSettingsHelper.getFaceOpenEyesBooleanValue(
                        suwFaceUsefulFeature, suwFaceUsefulFeature.mUserId)) {
                    Log.d("FcstSuwFaceUsefulFeature", "Set OpenEyes enable");
                    suwFaceUsefulFeature.mOpenEyesSwitch.setChecked(true);
                    break;
                } else {
                    Log.d("FcstSuwFaceUsefulFeature", "Set OpenEyes disable");
                    suwFaceUsefulFeature.mOpenEyesSwitch.setChecked(false);
                    break;
                }
        }
    }
}

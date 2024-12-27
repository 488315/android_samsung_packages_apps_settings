package com.samsung.android.settings.lockscreen.bixbyroutine;

import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;

import com.samsung.android.bio.face.SemBioFaceManager;
import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.settings.biometrics.face.FaceLockSettings;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintLockSettings;
import com.samsung.android.settings.security.SecurityUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class LockScreenRoutineActionActivity$$ExternalSyntheticLambda0
        implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LockScreenRoutineActionActivity f$0;

    public /* synthetic */ LockScreenRoutineActionActivity$$ExternalSyntheticLambda0(
            LockScreenRoutineActionActivity lockScreenRoutineActionActivity, int i) {
        this.$r8$classId = i;
        this.f$0 = lockScreenRoutineActionActivity;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        LockScreenRoutineActionActivity lockScreenRoutineActionActivity = this.f$0;
        switch (i) {
            case 0:
                int i2 = LockScreenRoutineActionActivity.$r8$clinit;
                lockScreenRoutineActionActivity.getClass();
                Log.d("LockScreenRoutineActionActivity", "startFingerprintLockSettings()");
                if (!SecurityUtils.isFingerprintDisabled(
                        lockScreenRoutineActionActivity, UserHandle.myUserId())) {
                    FingerprintManager fingerprintManager =
                            lockScreenRoutineActionActivity.mFingerprintManager;
                    if (fingerprintManager != null && fingerprintManager.isHardwareDetected()) {
                        Intent intent =
                                new Intent()
                                        .setClass(
                                                lockScreenRoutineActionActivity,
                                                FingerprintLockSettings.class);
                        intent.putExtra("previousStage", "FingerprintSettings_register");
                        lockScreenRoutineActionActivity.startActivityForResult(intent, 10011);
                        break;
                    } else {
                        Log.d(
                                "LockScreenRoutineActionActivity",
                                "isHardwareDetected() of Fingerprint is FALSE.");
                        break;
                    }
                } else {
                    Log.d("LockScreenRoutineActionActivity", "isFingerprintDisabled() = TRUE");
                    lockScreenRoutineActionActivity.finish();
                    break;
                }
            case 1:
                int i3 = LockScreenRoutineActionActivity.$r8$clinit;
                StringBuilder sb = new StringBuilder(" routineFP : ");
                ParameterValues parameterValues = lockScreenRoutineActionActivity.mParameterValues;
                Boolean bool = Boolean.FALSE;
                sb.append(parameterValues.getBoolean("unlock_with_fingerprint", bool));
                sb.append(" routineFace : ");
                sb.append(
                        lockScreenRoutineActionActivity.mParameterValues.getBoolean(
                                "unlock_with_face", bool));
                Log.d("LockScreenRoutineActionActivity", sb.toString());
                ParameterValues parameterValues2 = lockScreenRoutineActionActivity.mParameterValues;
                Intent intent2 = new Intent();
                intent2.putExtra("intent_params", parameterValues2.toJsonString());
                lockScreenRoutineActionActivity.setResult(-1, intent2);
                lockScreenRoutineActionActivity.finish();
                break;
            case 2:
                int i4 = LockScreenRoutineActionActivity.$r8$clinit;
                lockScreenRoutineActionActivity.finish();
                break;
            default:
                int i5 = LockScreenRoutineActionActivity.$r8$clinit;
                lockScreenRoutineActionActivity.getClass();
                Log.d("LockScreenRoutineActionActivity", "startFaceLockSettings()");
                SemBioFaceManager semBioFaceManager = lockScreenRoutineActionActivity.mFaceManager;
                if (semBioFaceManager != null && semBioFaceManager.isHardwareDetected()) {
                    if (!SecurityUtils.isFaceDisabled(
                            lockScreenRoutineActionActivity, UserHandle.myUserId())) {
                        Intent intent3 =
                                new Intent()
                                        .setClass(
                                                lockScreenRoutineActionActivity,
                                                FaceLockSettings.class);
                        intent3.putExtra("previousStage", "FaceSettings_register");
                        lockScreenRoutineActionActivity.startActivityForResult(intent3, 10012);
                        break;
                    } else {
                        Log.d("LockScreenRoutineActionActivity", "isFaceDisabled() = TRUE");
                        lockScreenRoutineActionActivity.finish();
                        break;
                    }
                } else {
                    Log.d(
                            "LockScreenRoutineActionActivity",
                            "isHardwareDetected() of Face is FALSE.");
                    break;
                }
                break;
        }
    }
}

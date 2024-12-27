package com.samsung.android.settings.eternal.provider.items;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.lib.episode.Scene;
import com.samsung.android.lib.episode.SceneResult;
import com.samsung.android.lib.episode.SourceInfo;
import com.samsung.android.settings.biometrics.ScreenTransitionEffectPreferenceController;
import com.samsung.android.settings.biometrics.face.FaceSettingsHelper;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsUtils;
import com.samsung.android.util.SemLog;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BiometricsItem implements Recoverable {
    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final boolean followRestoreSkipPolicy(Scene scene) {
        return true;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final List getTestScenes(Context context, String str) {
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v15, types: [int] */
    /* JADX WARN: Type inference failed for: r9v16 */
    /* JADX WARN: Type inference failed for: r9v17 */
    /* JADX WARN: Type inference failed for: r9v18 */
    /* JADX WARN: Type inference failed for: r9v19 */
    /* JADX WARN: Type inference failed for: r9v20 */
    /* JADX WARN: Type inference failed for: r9v21 */
    /* JADX WARN: Type inference failed for: r9v22 */
    /* JADX WARN: Type inference failed for: r9v23 */
    /* JADX WARN: Type inference failed for: r9v24 */
    /* JADX WARN: Type inference failed for: r9v25 */
    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final Scene.Builder getValue(Context context, SourceInfo sourceInfo, String str) {
        char c;
        ?? r9;
        boolean z = true;
        Scene.Builder builder = new Scene.Builder(str);
        int myUserId = UserHandle.myUserId();
        try {
            switch (str.hashCode()) {
                case -1404298572:
                    if (str.equals("/Settings/Biometrics/FingerTapToShowIcon")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -1055223680:
                    if (str.equals("/Settings/Biometrics/FaceOpenEyes")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -1031613171:
                    if (str.equals("/Settings/Biometrics/FingerShowIconLockscreen")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -730124698:
                    if (str.equals("/Settings/Biometrics/FaceRecognizeMask")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -468050845:
                    if (str.equals("/Settings/Biometrics/FaceStayOnLockScreen")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 575110912:
                    if (str.equals("/Settings/Biometrics/FingerShowIconAod")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 1167609607:
                    if (str.equals("/Settings/Biometrics/FingerShowAnimation")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 1172996015:
                    if (str.equals("/Settings/Biometrics/FingerStayOnLockScreen")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1993638691:
                    if (str.equals("/Settings/Biometrics/FaceBrightenScreen")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 2032280353:
                    if (str.equals("/Settings/Biometrics/UnlockTransitionEffect")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    if (FaceSettingsHelper.isSupportBioFaceOpenEyes()) {
                        int intForUser = Settings.Secure.getIntForUser(context.getContentResolver(), "face_open_eyes", -1, myUserId);
                        if (1 != intForUser) {
                            z = false;
                        }
                        builder.setDefault(z);
                        r9 = intForUser;
                        break;
                    }
                    r9 = -10;
                    break;
                case 1:
                    int faceStayOnLockScreenDbValue = FaceSettingsHelper.getFaceStayOnLockScreenDbValue(context, myUserId);
                    if (1 != faceStayOnLockScreenDbValue) {
                        z = false;
                    }
                    builder.setDefault(z);
                    r9 = faceStayOnLockScreenDbValue;
                    break;
                case 2:
                    int faceBrightenScreenDbValue = FaceSettingsHelper.getFaceBrightenScreenDbValue(context, myUserId);
                    if (1 != faceBrightenScreenDbValue) {
                        z = false;
                    }
                    builder.setDefault(z);
                    r9 = faceBrightenScreenDbValue;
                    break;
                case 3:
                    if (FaceSettingsHelper.isSupportBioFaceRecognizeWithMask(context)) {
                        int faceRecognizeMaskDbValue = FaceSettingsHelper.getFaceRecognizeMaskDbValue(context, myUserId);
                        if (faceRecognizeMaskDbValue != 0) {
                            z = false;
                        }
                        builder.setDefault(z);
                        r9 = faceRecognizeMaskDbValue;
                        break;
                    }
                    r9 = -10;
                    break;
                case 4:
                    boolean stayOnLockScreen = FingerprintSettingsUtils.getStayOnLockScreen(context, myUserId);
                    builder.setDefault(true ^ stayOnLockScreen);
                    r9 = stayOnLockScreen;
                    break;
                case 5:
                    if (FingerprintSettingsUtils.isSupportFingerprintScreenOffIconAod()) {
                        int fingerprintScreenOffIconAodDbValue = FingerprintSettingsUtils.getFingerprintScreenOffIconAodDbValue(context, myUserId);
                        if (2 != fingerprintScreenOffIconAodDbValue) {
                            z = false;
                        }
                        builder.setDefault(z);
                        r9 = fingerprintScreenOffIconAodDbValue;
                        break;
                    }
                    r9 = -10;
                    break;
                case 6:
                    boolean z2 = FingerprintSettingsUtils.SUB_DISPLAY_IS_LARGE_SCREEN;
                    r9 = -10;
                    break;
                case 7:
                    int fingerprintLockIconValue = FingerprintSettingsUtils.getFingerprintLockIconValue(context, myUserId);
                    if (2 != fingerprintLockIconValue) {
                        z = false;
                    }
                    builder.setDefault(z);
                    r9 = fingerprintLockIconValue;
                    break;
                case '\b':
                    char c2 = FingerprintSettingsUtils.getFingerprintEffectValue(context, myUserId) ? (char) 65535 : (char) 0;
                    if (65535 != c2) {
                        z = false;
                    }
                    builder.setDefault(z);
                    r9 = c2;
                    break;
                case '\t':
                    if (new ScreenTransitionEffectPreferenceController(context).getAvailabilityStatus() == 0) {
                        int semGetTransitionEffectValue = FingerprintManager.semGetTransitionEffectValue();
                        if (semGetTransitionEffectValue == -1) {
                            semGetTransitionEffectValue = 1;
                        }
                        int i = Settings.System.getInt(context.getContentResolver(), "screen_transition_effect");
                        if (semGetTransitionEffectValue != i) {
                            z = false;
                        }
                        builder.setDefault(z);
                        r9 = i;
                        break;
                    }
                    r9 = -10;
                    break;
                default:
                    SemLog.w("BiometricsItem", "unknown key : ".concat(str));
                    r9 = -10;
                    break;
            }
            if (r9 != -10) {
                builder.setValue(Integer.valueOf((int) r9), false);
            }
        } catch (Exception e) {
            SemLog.e("BiometricsItem", e.getStackTrace()[0].toString());
        }
        return builder;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final SceneResult isOpenable(Context context, String str) {
        return null;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final SceneResult setValue(Context context, String str, Scene scene, SourceInfo sourceInfo) {
        char c;
        String value = scene.getValue(null, false);
        int myUserId = UserHandle.myUserId();
        SceneResult.Builder builder = new SceneResult.Builder(str);
        builder.mResultType = SceneResult.ResultType.RESULT_OK;
        if (TextUtils.isEmpty(value)) {
            builder.mResultType = SceneResult.ResultType.RESULT_FAIL;
            builder.mErrorType = SceneResult.ErrorType.INVALID_DATA;
            return builder.build();
        }
        try {
            boolean z = true;
            boolean z2 = true;
            boolean z3 = true;
            int i = 1;
            boolean z4 = true;
            boolean z5 = true;
            switch (str.hashCode()) {
                case -1404298572:
                    if (str.equals("/Settings/Biometrics/FingerTapToShowIcon")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -1055223680:
                    if (str.equals("/Settings/Biometrics/FaceOpenEyes")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -1031613171:
                    if (str.equals("/Settings/Biometrics/FingerShowIconLockscreen")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -730124698:
                    if (str.equals("/Settings/Biometrics/FaceRecognizeMask")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -468050845:
                    if (str.equals("/Settings/Biometrics/FaceStayOnLockScreen")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 575110912:
                    if (str.equals("/Settings/Biometrics/FingerShowIconAod")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 1167609607:
                    if (str.equals("/Settings/Biometrics/FingerShowAnimation")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 1172996015:
                    if (str.equals("/Settings/Biometrics/FingerStayOnLockScreen")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1993638691:
                    if (str.equals("/Settings/Biometrics/FaceBrightenScreen")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 2032280353:
                    if (str.equals("/Settings/Biometrics/UnlockTransitionEffect")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    if (Integer.valueOf(value).intValue() != 1) {
                        z = false;
                    }
                    FaceSettingsHelper.setFaceOpenEyes(context, myUserId, z);
                    break;
                case 1:
                    if (Integer.valueOf(value).intValue() != 1) {
                        z5 = false;
                    }
                    FaceSettingsHelper.setFaceStayOnLockScreen(context, myUserId, z5);
                    break;
                case 2:
                    if (Integer.valueOf(value).intValue() != 1) {
                        z4 = false;
                    }
                    FaceSettingsHelper.setFaceBrightenScreen(context, myUserId, z4);
                    break;
                case 3:
                    if (Integer.valueOf(value).intValue() != 1) {
                        i = 0;
                    }
                    boolean z6 = FaceSettingsHelper.IS_SUPPORT_FACE_DAEMON_DETECT_OPEN_EYES;
                    Settings.Secure.putIntForUser(context.getContentResolver(), "face_recognize_mask", i, myUserId);
                    break;
                case 4:
                    if (Integer.valueOf(value).intValue() != 1) {
                        z3 = false;
                    }
                    FingerprintSettingsUtils.setStayOnLockScreen(context, myUserId, z3);
                    break;
                case 5:
                    FingerprintSettingsUtils.setFingerprintScreenOffIconAodValue(context, Integer.valueOf(value).intValue(), myUserId);
                    break;
                case 6:
                    FingerprintSettingsUtils.setFingerprintLockIconValue(context, Integer.valueOf(value).intValue(), myUserId);
                    break;
                case 7:
                    Integer.valueOf(value).intValue();
                    boolean z7 = FingerprintSettingsUtils.SUB_DISPLAY_IS_LARGE_SCREEN;
                    Log.d("FpstFingerprintSettingsUtils", "setFingerprintScreenOffIconValue: not supported device");
                case '\b':
                    if (Integer.valueOf(value).intValue() != -1) {
                        z2 = false;
                    }
                    FingerprintSettingsUtils.setFingerprintEffectValue(context, myUserId, z2);
                    break;
                case '\t':
                    Settings.System.putInt(context.getContentResolver(), "screen_transition_effect", Integer.valueOf(value).intValue());
                    break;
                default:
                    SemLog.w("BiometricsItem", "unknown key : ".concat(str));
                    break;
            }
        } catch (Exception e) {
            SemLog.e("BiometricsItem", e.getStackTrace()[0].toString());
        }
        return builder.build();
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final void open(Context context, String str) {
    }
}

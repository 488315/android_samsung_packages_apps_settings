package com.samsung.android.settings.biometrics.face;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.face.FaceManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.util.SeslKoreanGeneralizer;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.bio.face.SemBioFaceManager;
import com.samsung.android.settings.biometrics.BiometricsGenericHelper;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.security.SecurityUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class FaceSettingsHelper {
    public static final boolean IS_SUPPORT_FACE_DAEMON_ALTERNATIVE_FACE;
    public static final boolean IS_SUPPORT_FACE_DAEMON_DETECT_OPEN_EYES;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class FaceTurnOnDialog extends DialogFragment
            implements DialogInterface.OnCancelListener {
        public FragmentActivity mContext;

        @Override // androidx.fragment.app.DialogFragment,
                  // android.content.DialogInterface.OnCancelListener
        public final void onCancel(DialogInterface dialogInterface) {
            Log.d("FpstFaceTurnOnDialog", "onCancel!!");
        }

        @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            this.mContext = getActivity();
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            Log.d("FpstFaceTurnOnDialog", "onCreateDialog");
            final LockPatternUtils lockPatternUtils = new LockPatternUtils(this.mContext);
            AlertDialog create = new AlertDialog.Builder(this.mContext).create();
            create.setButton(
                    -1,
                    getResources().getString(R.string.common_ok),
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.biometrics.face.FaceSettingsHelper$FaceTurnOnDialog$$ExternalSyntheticLambda0
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            FaceSettingsHelper.FaceTurnOnDialog faceTurnOnDialog =
                                    FaceSettingsHelper.FaceTurnOnDialog.this;
                            LockPatternUtils lockPatternUtils2 = lockPatternUtils;
                            faceTurnOnDialog.getClass();
                            Log.d("FpstFaceTurnOnDialog", "mNeedRegistration : false");
                            FaceSettingsHelper.setFaceLock(
                                    0, faceTurnOnDialog.mContext, lockPatternUtils2);
                        }
                    });
            create.setButton(
                    -2,
                    getResources().getString(R.string.cancel),
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.biometrics.face.FaceSettingsHelper$FaceTurnOnDialog$$ExternalSyntheticLambda1
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            FaceSettingsHelper.FaceTurnOnDialog.this.getClass();
                        }
                    });
            return create;
        }

        @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
        public final void onDestroyView() {
            Log.d("FpstFaceTurnOnDialog", "onDestroyView");
            super.onDestroyView();
        }
    }

    static {
        boolean z = SemBioFaceManager.IS_SUPPORTED_ALTERNATIVE_ENROLLMENT_AND_CLOSED_EYES_DETECTION;
        IS_SUPPORT_FACE_DAEMON_DETECT_OPEN_EYES = z;
        IS_SUPPORT_FACE_DAEMON_ALTERNATIVE_FACE = z;
    }

    public static boolean getFaceBrightenScreenBooleanValue(Context context, int i) {
        int intForUser =
                Settings.Secure.getIntForUser(
                        context.getContentResolver(), "face_brighten_screen", -1, i);
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                intForUser, "getFaceBrightenScreenBooleanValue :", "FcstFaceSettingsHelper");
        return intForUser == 1;
    }

    public static int getFaceBrightenScreenDbValue(Context context, int i) {
        int intForUser =
                Settings.Secure.getIntForUser(
                        context.getContentResolver(), "face_brighten_screen", -1, i);
        if (intForUser == -1) {
            intForUser = 1;
            setFaceBrightenScreen(context, i, true);
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                intForUser, "getFaceBrightenScreenDbValue :", "FcstFaceSettingsHelper");
        return intForUser;
    }

    public static boolean getFaceOpenEyesBooleanValue(Context context, int i) {
        if (!isSupportBioFaceOpenEyes()) {
            Log.d(
                    "FcstFaceSettingsHelper",
                    "getFaceOpenEyesBooleanValue: Not support Face Open eyes");
            return false;
        }
        int intForUser =
                Settings.Secure.getIntForUser(
                        context.getContentResolver(), "face_open_eyes", -1, i);
        if (intForUser == -1) {
            if (Utils.isTalkBackEnabled(context)) {
                setFaceOpenEyes(context, i, false);
                intForUser = 0;
            } else {
                setFaceOpenEyes(context, i, true);
                intForUser = 1;
            }
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                intForUser, "getFaceOpenEyesBooleanValue :", "FcstFaceSettingsHelper");
        return intForUser == 1;
    }

    public static int getFaceRecognizeMaskDbValue(Context context, int i) {
        return Settings.Secure.getIntForUser(
                context.getContentResolver(), "face_recognize_mask", -1, i);
    }

    public static boolean getFaceStayOnLockScreenBooleanValue(Context context, int i) {
        int intForUser =
                Settings.Secure.getIntForUser(
                        context.getContentResolver(), "face_stay_on_lock_screen", -1, i);
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                intForUser, "getFaceStayOnLockScreenBooleanValue :", "FcstFaceSettingsHelper");
        return intForUser != 0;
    }

    public static int getFaceStayOnLockScreenDbValue(Context context, int i) {
        int intForUser =
                Settings.Secure.getIntForUser(
                        context.getContentResolver(), "face_stay_on_lock_screen", -1, i);
        if (intForUser == -1) {
            int intForUser2 =
                    Settings.Secure.getIntForUser(
                            context.getContentResolver(), "face_without_swipe_to_unlock", -1, i);
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    intForUser2, "getFaceUnlockWithoutSwipeValue :", "FcstFaceSettingsHelper");
            if (intForUser2 == 1) {
                intForUser = 0;
                setFaceStayOnLockScreen(context, i, false);
            } else {
                setFaceStayOnLockScreen(context, i, true);
                intForUser = 1;
            }
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                intForUser, "getFaceStayOnLockScreenDbValue :", "FcstFaceSettingsHelper");
        return intForUser;
    }

    public static boolean getFaceUnlockEnabled(LockPatternUtils lockPatternUtils, int i) {
        return lockPatternUtils != null && lockPatternUtils.getBiometricState(256, i) == 1;
    }

    public static boolean isSupportBioFaceAlternativeFace() {
        StringBuilder sb = new StringBuilder("isSupportBioFaceAlternativeFace : ");
        boolean z = IS_SUPPORT_FACE_DAEMON_ALTERNATIVE_FACE;
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, z, "FcstFaceSettingsHelper");
        return z;
    }

    public static boolean isSupportBioFaceOpenEyes() {
        StringBuilder sb = new StringBuilder("isSupportBioFaceOpenEyes : ");
        boolean z = IS_SUPPORT_FACE_DAEMON_DETECT_OPEN_EYES;
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, z, "FcstFaceSettingsHelper");
        return z;
    }

    public static boolean isSupportBioFaceRecognizeWithMask(Context context) {
        if (context == null || Utils.getFaceManagerOrNull(context) == null) {
            return false;
        }
        return FaceManager.semIsSupportOnMask();
    }

    public static void removeFaceLock(int i, Context context, LockPatternUtils lockPatternUtils) {
        Log.d("FcstFaceSettingsHelper", "removeFaceLock!");
        SecurityUtils.removeBiometricLock(context, lockPatternUtils, 256, i);
    }

    public static void setFaceBrightenScreen(Context context, int i, boolean z) {
        Settings.Secure.putIntForUser(
                context.getContentResolver(), "face_brighten_screen", z ? 1 : 0, i);
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("setFaceBrightenScreenBooleanValue: "),
                z,
                "FcstFaceSettingsHelper");
    }

    public static void setFaceLock(int i, Context context, LockPatternUtils lockPatternUtils) {
        Log.d("FcstFaceSettingsHelper", "Enable Face lock!");
        if (SecurityUtils.isFaceDisabled(context, i)) {
            Log.d("FcstFaceSettingsHelper", "face disabled by MDM policy");
        } else if (KnoxUtils.getTwoFactorValue(context, i) == 1) {
            Log.d(
                    "FcstFaceSettingsHelper",
                    "Face is not allowed because two-step is set as lock type");
        } else {
            SecurityUtils.setBiometricLock(context, lockPatternUtils, 256, i);
        }
    }

    public static void setFaceOpenEyes(Context context, int i, boolean z) {
        if (!isSupportBioFaceOpenEyes()) {
            Log.d("FcstFaceSettingsHelper", "setFaceOpenEyes: Not support Face Open eyes");
        } else {
            Settings.Secure.putIntForUser(
                    context.getContentResolver(), "face_open_eyes", z ? 1 : 0, i);
            ActionBarContextView$$ExternalSyntheticOutline0.m(
                    new StringBuilder("setFaceOpenEyes: "), z, "FcstFaceSettingsHelper");
        }
    }

    public static void setFaceStayOnLockScreen(Context context, int i, boolean z) {
        Settings.Secure.putIntForUser(
                context.getContentResolver(), "face_stay_on_lock_screen", z ? 1 : 0, i);
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("setFaceStayOnLockScreen: "), z, "FcstFaceSettingsHelper");
    }

    public static void showFaceSensorErrorDialog(
            final Activity activity, String str, String str2, final boolean z) {
        if (activity == null) {
            Log.secE("FcstFaceSettingsHelper", "Activity is null. Skip SensorErrorDialog");
            return;
        }
        String naturalizeText = new SeslKoreanGeneralizer().naturalizeText(str2);
        if (str == null) {
            str = activity.getString(R.string.sec_fingerprint_attention);
        }
        AlertDialog create =
                new AlertDialog.Builder(
                                activity, BiometricsGenericHelper.isLightTheme(activity) ? 5 : 4)
                        .setTitle(str)
                        .setMessage(naturalizeText)
                        .setPositiveButton(
                                android.R.string.ok,
                                new FaceSettingsHelper$$ExternalSyntheticLambda0())
                        .create();
        create.setOnDismissListener(
                new DialogInterface
                        .OnDismissListener() { // from class:
                                               // com.samsung.android.settings.biometrics.face.FaceSettingsHelper$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        boolean z2 = z;
                        Activity activity2 = activity;
                        if (z2) {
                            Intent intent = new Intent();
                            intent.putExtra("screen_lock_force_destroy", true);
                            activity2.setResult(0, intent);
                            activity2.finish();
                        }
                    }
                });
        create.getWindow().setDimAmount(0.3f);
        create.getWindow().addFlags(2);
        create.show();
    }
}

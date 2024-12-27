package com.android.settings.homepage.contextualcards;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.face.Face;
import android.hardware.face.FaceManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.biometrics.face.FaceUpdater;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FaceReEnrollDialog extends AlertActivity implements DialogInterface.OnClickListener {
    public FaceManager mFaceManager;
    public FaceUpdater mFaceUpdater;
    public int mReEnrollType;

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        removeFaceAndReEnroll();
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        int i =
                getApplicationContext()
                                .getPackageManager()
                                .hasSystemFeature("android.hardware.fingerprint")
                        ? R.string.security_settings_face_enroll_improve_face_alert_body_fingerprint
                        : R.string.security_settings_face_enroll_improve_face_alert_body;
        AlertController.AlertParams alertParams = ((AlertActivity) this).mAlertParams;
        alertParams.mTitle =
                getText(R.string.security_settings_face_enroll_improve_face_alert_title);
        alertParams.mMessage = getText(i);
        alertParams.mPositiveButtonText = getText(R.string.storage_menu_set_up);
        alertParams.mNegativeButtonText = getText(R.string.cancel);
        alertParams.mPositiveButtonListener = this;
        this.mFaceManager = Utils.getFaceManagerOrNull(getApplicationContext());
        this.mFaceUpdater = new FaceUpdater(getApplicationContext(), this.mFaceManager);
        this.mReEnrollType =
                Settings.Secure.getIntForUser(
                        getApplicationContext().getContentResolver(),
                        "face_unlock_re_enroll",
                        0,
                        getUserId());
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("ReEnroll Type : "), this.mReEnrollType, "FaceReEnrollDialog");
        int i2 = this.mReEnrollType;
        if (i2 == 1) {
            setupAlert();
            return;
        }
        if (i2 == 3) {
            removeFaceAndReEnroll();
            return;
        }
        Log.d("FaceReEnrollDialog", "Error unsupported flow for : " + this.mReEnrollType);
        dismiss();
    }

    public final void removeFaceAndReEnroll() {
        int userId = getUserId();
        FaceManager faceManager = this.mFaceManager;
        if (faceManager == null || !faceManager.hasEnrolledTemplates(userId)) {
            finish();
        }
        FaceUpdater faceUpdater = this.mFaceUpdater;
        faceUpdater.mFaceManager.remove(
                new Face(ApnSettings.MVNO_NONE, 0, 0L),
                userId,
                new FaceUpdater.NotifyingRemovalCallback(
                        faceUpdater.mContext,
                        new FaceManager
                                .RemovalCallback() { // from class:
                                                     // com.android.settings.homepage.contextualcards.FaceReEnrollDialog.1
                            public final void onRemovalError(
                                    Face face, int i, CharSequence charSequence) {
                                super.onRemovalError(face, i, charSequence);
                                FaceReEnrollDialog.this.finish();
                            }

                            public final void onRemovalSucceeded(Face face, int i) {
                                super.onRemovalSucceeded(face, i);
                                if (i != 0) {
                                    return;
                                }
                                try {
                                    FaceReEnrollDialog.this.startActivity(
                                            new Intent("android.settings.BIOMETRIC_ENROLL")
                                                    .setPackage(
                                                            FaceReEnrollDialog.this
                                                                    .getPackageName()));
                                } catch (Exception unused) {
                                    Log.e("FaceReEnrollDialog", "Failed to startActivity");
                                }
                                FaceReEnrollDialog.this.finish();
                            }
                        }));
    }
}

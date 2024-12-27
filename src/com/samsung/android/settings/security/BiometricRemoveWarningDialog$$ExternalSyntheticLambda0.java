package com.samsung.android.settings.security;

import android.R;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;

import com.android.settings.security.LockUnificationPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class BiometricRemoveWarningDialog$$ExternalSyntheticLambda0
        implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BiometricRemoveWarningDialog f$0;

    public /* synthetic */ BiometricRemoveWarningDialog$$ExternalSyntheticLambda0(
            BiometricRemoveWarningDialog biometricRemoveWarningDialog, int i) {
        this.$r8$classId = i;
        this.f$0 = biometricRemoveWarningDialog;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        int i2 = this.$r8$classId;
        BiometricRemoveWarningDialog biometricRemoveWarningDialog = this.f$0;
        switch (i2) {
            case 0:
                biometricRemoveWarningDialog.getClass();
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(
                                new ContextThemeWrapper(
                                        biometricRemoveWarningDialog.getActivity(),
                                        R.style.Theme.DeviceDefault.DayNight));
                builder.setMessage(
                        com.android.settings.R.string
                                .sec_screen_locktype_change_nonsecure_biometric_dialog_biometric);
                builder.setPositiveButton(
                        com.android.settings.R.string.common_remove,
                        new BiometricRemoveWarningDialog$$ExternalSyntheticLambda0(
                                biometricRemoveWarningDialog, 2));
                builder.setNegativeButton(
                        com.android.settings.R.string.cancel,
                        new BiometricRemoveWarningDialog$$ExternalSyntheticLambda0(
                                biometricRemoveWarningDialog, 3));
                AlertDialog create = builder.create();
                create.setOnKeyListener(
                        new BiometricRemoveWarningDialog$$ExternalSyntheticLambda2(
                                biometricRemoveWarningDialog, 1));
                create.setCanceledOnTouchOutside(false);
                create.show();
                break;
            case 1:
                LockUnificationPreferenceController.AnonymousClass1 anonymousClass1 =
                        biometricRemoveWarningDialog.mCallback;
                if (anonymousClass1 != null) {
                    anonymousClass1.updatePreference();
                    break;
                }
                break;
            case 2:
                LockUnificationPreferenceController.AnonymousClass1 anonymousClass12 =
                        biometricRemoveWarningDialog.mCallback;
                if (anonymousClass12 != null) {
                    LockUnificationPreferenceController.this.unifyLocks();
                    biometricRemoveWarningDialog.mCallback.updatePreference();
                    break;
                }
                break;
            default:
                LockUnificationPreferenceController.AnonymousClass1 anonymousClass13 =
                        biometricRemoveWarningDialog.mCallback;
                if (anonymousClass13 != null) {
                    anonymousClass13.updatePreference();
                    break;
                }
                break;
        }
    }
}

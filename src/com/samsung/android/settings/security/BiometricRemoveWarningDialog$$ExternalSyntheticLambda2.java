package com.samsung.android.settings.security;

import android.content.DialogInterface;
import android.view.KeyEvent;

import com.android.settings.security.LockUnificationPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class BiometricRemoveWarningDialog$$ExternalSyntheticLambda2
        implements DialogInterface.OnKeyListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BiometricRemoveWarningDialog f$0;

    public /* synthetic */ BiometricRemoveWarningDialog$$ExternalSyntheticLambda2(
            BiometricRemoveWarningDialog biometricRemoveWarningDialog, int i) {
        this.$r8$classId = i;
        this.f$0 = biometricRemoveWarningDialog;
    }

    @Override // android.content.DialogInterface.OnKeyListener
    public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        LockUnificationPreferenceController.AnonymousClass1 anonymousClass1;
        LockUnificationPreferenceController.AnonymousClass1 anonymousClass12;
        int i2 = this.$r8$classId;
        BiometricRemoveWarningDialog biometricRemoveWarningDialog = this.f$0;
        biometricRemoveWarningDialog.getClass();
        switch (i2) {
            case 0:
                if (i == 4
                        && keyEvent.getAction() == 1
                        && (anonymousClass1 = biometricRemoveWarningDialog.mCallback) != null) {
                    anonymousClass1.updatePreference();
                    break;
                }
                break;
            default:
                if (i == 4
                        && keyEvent.getAction() == 1
                        && (anonymousClass12 = biometricRemoveWarningDialog.mCallback) != null) {
                    anonymousClass12.updatePreference();
                    break;
                }
                break;
        }
        return false;
    }
}

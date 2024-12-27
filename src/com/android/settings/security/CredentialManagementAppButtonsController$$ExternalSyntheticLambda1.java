package com.android.settings.security;

import android.app.admin.DevicePolicyEventLogger;
import android.os.RemoteException;
import android.security.KeyChain;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */
class CredentialManagementAppButtonsController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ CredentialManagementAppButtonsController$$ExternalSyntheticLambda1(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((CredentialManagementAppButtonsController) obj).lambda$uninstallCertificates$4();
                return;
            default:
                CredentialManagementAppButtonsController.RemoveCredentialManagementAppDialog
                        removeCredentialManagementAppDialog =
                                (CredentialManagementAppButtonsController
                                                .RemoveCredentialManagementAppDialog)
                                        obj;
                removeCredentialManagementAppDialog.getClass();
                try {
                    KeyChain.KeyChainConnection bind =
                            KeyChain.bind(removeCredentialManagementAppDialog.getContext());
                    try {
                        bind.getService().removeCredentialManagementApp();
                        DevicePolicyEventLogger.createEvent(187).write();
                        removeCredentialManagementAppDialog
                                .getParentFragment()
                                .getActivity()
                                .finish();
                        bind.close();
                        return;
                    } catch (Throwable th) {
                        if (bind != null) {
                            try {
                                bind.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (RemoteException | InterruptedException unused) {
                    Log.e(
                            "CredentialManagementApp",
                            "Unable to remove the credential management app");
                    return;
                }
        }
    }
}

package com.android.settings.security;

import android.os.RemoteException;
import android.security.IKeyChainService;
import android.security.KeyChain;
import android.util.Log;

import androidx.preference.PreferenceViewHolder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class CredentialManagementAppPolicyPreference$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CredentialManagementAppPolicyPreference f$0;
    public final /* synthetic */ PreferenceViewHolder f$1;

    public /* synthetic */ CredentialManagementAppPolicyPreference$$ExternalSyntheticLambda0(
            CredentialManagementAppPolicyPreference credentialManagementAppPolicyPreference,
            PreferenceViewHolder preferenceViewHolder,
            int i) {
        this.$r8$classId = i;
        this.f$0 = credentialManagementAppPolicyPreference;
        this.f$1 = preferenceViewHolder;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                CredentialManagementAppPolicyPreference credentialManagementAppPolicyPreference =
                        this.f$0;
                PreferenceViewHolder preferenceViewHolder = this.f$1;
                credentialManagementAppPolicyPreference.getClass();
                try {
                    KeyChain.KeyChainConnection bind =
                            KeyChain.bind(credentialManagementAppPolicyPreference.mContext);
                    try {
                        IKeyChainService service = bind.getService();
                        credentialManagementAppPolicyPreference.mHasCredentialManagerPackage =
                                service.hasCredentialManagementApp();
                        credentialManagementAppPolicyPreference.mCredentialManagerPackageName =
                                service.getCredentialManagementAppPackageName();
                        credentialManagementAppPolicyPreference.mCredentialManagerPolicy =
                                service.getCredentialManagementAppPolicy();
                        bind.close();
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
                            "Unable to display credential management app policy");
                }
                credentialManagementAppPolicyPreference.mHandler.post(
                        new CredentialManagementAppPolicyPreference$$ExternalSyntheticLambda0(
                                credentialManagementAppPolicyPreference, preferenceViewHolder, 1));
                return;
            default:
                CredentialManagementAppPolicyPreference credentialManagementAppPolicyPreference2 =
                        this.f$0;
                PreferenceViewHolder preferenceViewHolder2 = this.f$1;
                if (credentialManagementAppPolicyPreference2.mHasCredentialManagerPackage) {
                    RecyclerView recyclerView =
                            (RecyclerView) preferenceViewHolder2.findViewById(R.id.recycler_view);
                    recyclerView.setLayoutManager(new LinearLayoutManager(1));
                    recyclerView.setAdapter(
                            new CredentialManagementAppAdapter(
                                    credentialManagementAppPolicyPreference2.mContext,
                                    credentialManagementAppPolicyPreference2
                                            .mCredentialManagerPackageName,
                                    credentialManagementAppPolicyPreference2
                                            .mCredentialManagerPolicy.getAppAndUriMappings(),
                                    false,
                                    true));
                    return;
                }
                return;
        }
    }
}

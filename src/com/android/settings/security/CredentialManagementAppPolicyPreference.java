package com.android.settings.security;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.security.AppUriAuthenticationPolicy;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CredentialManagementAppPolicyPreference extends Preference {
    public final Context mContext;
    public String mCredentialManagerPackageName;
    public AppUriAuthenticationPolicy mCredentialManagerPolicy;
    public final ExecutorService mExecutor;
    public final Handler mHandler;
    public boolean mHasCredentialManagerPackage;

    public CredentialManagementAppPolicyPreference(Context context) {
        super(context);
        this.mExecutor = Executors.newSingleThreadExecutor();
        this.mHandler = new Handler(Looper.getMainLooper());
        setLayoutResource(R.layout.credential_management_app_policy);
        this.mContext = context;
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mExecutor.execute(
                new CredentialManagementAppPolicyPreference$$ExternalSyntheticLambda0(
                        this, preferenceViewHolder, 0));
    }
}

package com.android.settings.security;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.security.IKeyChainService;
import android.security.KeyChain;
import android.util.Log;

import androidx.preference.Preference;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CredentialManagementAppPreferenceController extends BasePreferenceController {
    private static final String TAG = "CredentialManagementApp";
    private String mCredentialManagerPackageName;
    private final ExecutorService mExecutor;
    private final Handler mHandler;
    private boolean mHasCredentialManagerPackage;
    private final PackageManager mPackageManager;

    public CredentialManagementAppPreferenceController(Context context, String str) {
        super(context, str);
        this.mExecutor = Executors.newSingleThreadExecutor();
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mPackageManager = context.getPackageManager();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateState$1(Preference preference) {
        try {
            KeyChain.KeyChainConnection bind = KeyChain.bind(this.mContext);
            try {
                IKeyChainService service = bind.getService();
                this.mHasCredentialManagerPackage = service.hasCredentialManagementApp();
                this.mCredentialManagerPackageName =
                        service.getCredentialManagementAppPackageName();
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
            Log.e(TAG, "Unable to display credential management app preference");
        }
        this.mHandler.post(
                new CredentialManagementAppPreferenceController$$ExternalSyntheticLambda0(
                        this, preference, 0));
    }

    @VisibleForTesting
    /* renamed from: displayPreference, reason: merged with bridge method [inline-methods] */
    public void lambda$updateState$0(Preference preference) {
        if (!this.mHasCredentialManagerPackage) {
            preference.setEnabled(false);
            preference.setSummary(R.string.no_certificate_management_app);
        } else {
            preference.setEnabled(true);
            try {
                preference.setSummary(
                        this.mPackageManager
                                .getApplicationInfo(this.mCredentialManagerPackageName, 0)
                                .loadLabel(this.mPackageManager));
            } catch (PackageManager.NameNotFoundException unused) {
                preference.setSummary(this.mCredentialManagerPackageName);
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        this.mExecutor.execute(
                new CredentialManagementAppPreferenceController$$ExternalSyntheticLambda0(
                        this, preference, 1));
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}

package com.android.settings.security;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.security.KeyChain;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CredentialManagementAppHeaderController extends BasePreferenceController {
    private static final String TAG = "CredentialManagementApp";
    private String mCredentialManagerPackageName;
    private final ExecutorService mExecutor;
    private final Handler mHandler;
    private final PackageManager mPackageManager;

    public CredentialManagementAppHeaderController(Context context, String str) {
        super(context, str);
        this.mExecutor = Executors.newSingleThreadExecutor();
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mPackageManager = context.getPackageManager();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: displayHeader, reason: merged with bridge method [inline-methods] */
    public void lambda$displayPreference$0(PreferenceScreen preferenceScreen) {
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference(getPreferenceKey());
        ImageView imageView =
                (ImageView) layoutPreference.mRootView.findViewById(R.id.entity_header_icon);
        TextView textView =
                (TextView) layoutPreference.mRootView.findViewById(R.id.entity_header_title);
        TextView textView2 =
                (TextView) layoutPreference.mRootView.findViewById(R.id.entity_header_summary);
        ((TextView) layoutPreference.mRootView.findViewById(R.id.entity_header_second_summary))
                .setVisibility(8);
        try {
            ApplicationInfo applicationInfo =
                    this.mPackageManager.getApplicationInfo(this.mCredentialManagerPackageName, 0);
            imageView.setImageDrawable(this.mPackageManager.getApplicationIcon(applicationInfo));
            textView.setText(applicationInfo.loadLabel(this.mPackageManager));
            textView2.setText(R.string.certificate_management_app_description);
        } catch (PackageManager.NameNotFoundException unused) {
            imageView.setImageDrawable(null);
            textView.setText(this.mCredentialManagerPackageName);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$displayPreference$1(PreferenceScreen preferenceScreen) {
        try {
            KeyChain.KeyChainConnection bind = KeyChain.bind(this.mContext);
            try {
                this.mCredentialManagerPackageName =
                        bind.getService().getCredentialManagementAppPackageName();
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
            Log.e(TAG, "Unable to display credential management app header");
        }
        this.mHandler.post(
                new CredentialManagementAppHeaderController$$ExternalSyntheticLambda0(
                        this, preferenceScreen, 0));
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mExecutor.execute(
                new CredentialManagementAppHeaderController$$ExternalSyntheticLambda0(
                        this, preferenceScreen, 1));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 1;
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

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}

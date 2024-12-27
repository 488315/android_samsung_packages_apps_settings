package com.samsung.android.settings.security;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.credentials.CredentialManager;
import android.credentials.CredentialProviderInfo;
import android.os.UserHandle;
import android.service.autofill.AutofillServiceInfo;

import com.android.settings.R;
import com.android.settings.applications.credentials.CombinedProviderInfo;
import com.android.settings.applications.credentials.CredentialManagerPreferenceController;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class PassKeysAndAutofillPreferenceController extends BasePreferenceController {
    private static final String TAG =
            "com.samsung.android.settings.security.PassKeysAndAutofillPreferenceController";
    private CredentialManager mCredentialManager;

    public PassKeysAndAutofillPreferenceController(Context context, String str) {
        super(context, str);
        this.mCredentialManager =
                (CredentialManager) context.getSystemService(CredentialManager.class);
    }

    private List<CombinedProviderInfo> getAllProviders(int i) {
        return CombinedProviderInfo.buildMergedList(
                AutofillServiceInfo.getAvailableServices(this.mContext, i),
                getAvailableCredmanProvider(i),
                CredentialManagerPreferenceController.getSelectedAutofillProvider(
                        this.mContext, i, TAG));
    }

    private List<CredentialProviderInfo> getAvailableCredmanProvider(int i) {
        ArrayList arrayList = new ArrayList();
        CredentialManager credentialManager = this.mCredentialManager;
        if (credentialManager != null) {
            arrayList.addAll(credentialManager.getCredentialProviderServices(i, 3));
        }
        return arrayList;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        int myUserId = UserHandle.myUserId();
        CombinedProviderInfo topProvider =
                CombinedProviderInfo.getTopProvider(getAllProviders(myUserId));
        if (topProvider == null) {
            return this.mContext
                    .getResources()
                    .getString(R.string.passkeys_and_autofill_subtext_none);
        }
        int i = 0;
        for (CredentialProviderInfo credentialProviderInfo :
                getAvailableCredmanProvider(myUserId)) {
            if (credentialProviderInfo.isEnabled() && !credentialProviderInfo.isPrimary()) {
                i++;
            }
        }
        String charSequence = topProvider.getAppName(this.mContext).toString();
        return i == 0
                ? this.mContext
                        .getResources()
                        .getString(
                                R.string.passkeys_and_autofill_subtext_only_preferred, charSequence)
                : this.mContext
                        .getResources()
                        .getQuantityString(
                                R.plurals.passkeys_and_autofill_subtext,
                                i,
                                charSequence,
                                Integer.valueOf(i));
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

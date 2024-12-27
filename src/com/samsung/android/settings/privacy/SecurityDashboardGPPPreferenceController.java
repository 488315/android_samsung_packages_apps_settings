package com.samsung.android.settings.privacy;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Looper;

import androidx.preference.Preference;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.ApiExceptionMapper;
import com.google.android.gms.common.api.internal.TaskApiCall$Builder;
import com.google.android.gms.common.api.internal.zacv;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetClient;
import com.google.android.gms.safetynet.zzm;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.gms.tasks.zzj;
import com.google.android.gms.tasks.zzw;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.util.SemLog;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecurityDashboardGPPPreferenceController
        extends SecurityDashboardAppSecurityPreferenceController {
    private static final String TAG = "SecurityDashboardGPPPreferenceController";

    public SecurityDashboardGPPPreferenceController(Context context, String str) {
        super(context, str);
    }

    private void launchGPPIntent() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setClassName(
                "com.google.android.gms",
                "com.google.android.gms.security.settings.VerifyAppsSettingsActivity");
        try {
            this.mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            SemLog.d(TAG, "GPP Activity Not Found" + e.getMessage());
        }
    }

    private void updateMenuStatus() {
        Context context = this.mContext;
        OnCompleteListener onCompleteListener = this.onCompleteListenerCheckGPPAndGetStatus;
        Uri uri = SecurityDashboardUtils.FMM_SUPPORT_URI;
        Api api = SafetyNet.API;
        SafetyNetClient safetyNetClient =
                new SafetyNetClient(
                        context,
                        SafetyNet.API,
                        null,
                        new GoogleApi.Settings(new ApiExceptionMapper(), Looper.getMainLooper()));
        TaskApiCall$Builder taskApiCall$Builder = new TaskApiCall$Builder();
        taskApiCall$Builder.zaa = new zzm(safetyNetClient);
        zzw zae =
                safetyNetClient.zae(
                        0, new zacv(taskApiCall$Builder, taskApiCall$Builder.zac, true, 4201));
        zae.getClass();
        zae.zzb.zza(new zzj(TaskExecutors.MAIN_THREAD, onCompleteListener));
        synchronized (zae.zza) {
            try {
                if (zae.zzc) {
                    zae.zzb.zzb(zae);
                }
            } finally {
            }
        }
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mIsGPPSupported ? 0 : 3;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if ("key_google_play_protection".equals(preference.getKey())) {
            launchGPPIntent();
            LoggingHelper.insertEventLogging(9032, 7801);
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.android.settings.core.BasePreferenceController
    public void updateNonIndexableKeys(List<String> list) {
        if (this.mIsGPPSupported) {
            return;
        }
        list.add("key_google_play_protection");
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        updateMenuStatus();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController, com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}

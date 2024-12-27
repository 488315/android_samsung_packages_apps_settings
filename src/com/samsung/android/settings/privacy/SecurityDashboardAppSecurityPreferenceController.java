package com.samsung.android.settings.privacy;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.text.format.DateFormat;
import androidx.core.util.Consumer;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import com.android.settings.R;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.ApiExceptionMapper;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.TaskApiCall$Builder;
import com.google.android.gms.common.api.internal.zabv;
import com.google.android.gms.common.api.internal.zach;
import com.google.android.gms.common.api.internal.zacv;
import com.google.android.gms.common.api.internal.zae;
import com.google.android.gms.common.internal.zap;
import com.google.android.gms.internal.base.zaq;
import com.google.android.gms.internal.safetynet.zzaa;
import com.google.android.gms.internal.safetynet.zzad;
import com.google.android.gms.internal.safetynet.zzae;
import com.google.android.gms.internal.safetynet.zzn;
import com.google.android.gms.safetynet.HarmfulAppsData;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi$HarmfulAppsResponse;
import com.google.android.gms.safetynet.SafetyNetApi$VerifyAppsUserResponse;
import com.google.android.gms.safetynet.SafetyNetClient;
import com.google.android.gms.safetynet.zzd;
import com.google.android.gms.safetynet.zzm;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.gms.tasks.zzj;
import com.google.android.gms.tasks.zzw;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecExpandableMenuPreference;
import com.samsung.android.settings.widget.SecurityDashboardStatusPreference;
import com.samsung.android.util.SemLog;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecurityDashboardAppSecurityPreferenceController extends SecurityDashboardPreferenceController {
    private static final String TAG = "SecurityDashboardAppSecurityPreferenceController";
    protected long mDPLastScannedTime;
    protected SecurityDashboardConstants$Status mDPMenuStatus;
    protected int mDpSecurityInfo;
    private ExecutorService mExecutorService;
    protected List<HarmfulAppsData> mGppHarmfulAppList;
    protected long mGppLastScanTime;
    protected SecurityDashboardConstants$Status mGppMenuStatus;
    private Handler mHandler;
    private boolean mIsAppSecuritySupported;
    protected boolean mIsDPSupported;
    protected boolean mIsDeviceProtectionElapsed;
    protected boolean mIsGPPSupported;
    protected boolean mIsGppEnabled;
    private SecurityDashboardStatusPreference mStatusPreference;
    protected OnCompleteListener onCompleteListenerCheckGPPAndGetStatus;
    private OnCompleteListener onCompleteListenerGetHarmfulApps;

    public SecurityDashboardAppSecurityPreferenceController(Context context, String str) {
        super(context, str);
        SecurityDashboardConstants$Status securityDashboardConstants$Status = SecurityDashboardConstants$Status.NONE;
        this.mGppMenuStatus = securityDashboardConstants$Status;
        this.mDPMenuStatus = securityDashboardConstants$Status;
        final int i = 0;
        this.onCompleteListenerGetHarmfulApps = new OnCompleteListener(this) { // from class: com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController$$ExternalSyntheticLambda3
            public final /* synthetic */ SecurityDashboardAppSecurityPreferenceController f$0;

            {
                this.f$0 = this;
            }

            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                int i2 = i;
                SecurityDashboardAppSecurityPreferenceController securityDashboardAppSecurityPreferenceController = this.f$0;
                switch (i2) {
                    case 0:
                        securityDashboardAppSecurityPreferenceController.lambda$new$0(task);
                        break;
                    default:
                        securityDashboardAppSecurityPreferenceController.lambda$new$1(task);
                        break;
                }
            }
        };
        final int i2 = 1;
        this.onCompleteListenerCheckGPPAndGetStatus = new OnCompleteListener(this) { // from class: com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController$$ExternalSyntheticLambda3
            public final /* synthetic */ SecurityDashboardAppSecurityPreferenceController f$0;

            {
                this.f$0 = this;
            }

            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                int i22 = i2;
                SecurityDashboardAppSecurityPreferenceController securityDashboardAppSecurityPreferenceController = this.f$0;
                switch (i22) {
                    case 0:
                        securityDashboardAppSecurityPreferenceController.lambda$new$0(task);
                        break;
                    default:
                        securityDashboardAppSecurityPreferenceController.lambda$new$1(task);
                        break;
                }
            }
        };
        this.mExecutorService = Executors.newSingleThreadExecutor();
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mIsDPSupported = DeviceProtectionUtils.getDeviceProtectionSecurityInfo(this.mContext) != 3;
        boolean isGoogleServiceEnabled = SecurityDashboardUtils.isChinaModel() ? false : SecurityDashboardUtils.isGoogleServiceEnabled(context);
        this.mIsGPPSupported = isGoogleServiceEnabled;
        this.mIsAppSecuritySupported = isGoogleServiceEnabled || this.mIsDPSupported;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDPInfo$2(Integer num) {
        if ("key_app_security".equals(getPreferenceKey())) {
            this.mPreference.setStatus(this.mDPMenuStatus.ordinal() > this.mGppMenuStatus.ordinal() ? this.mDPMenuStatus : this.mGppMenuStatus);
            setAppSecuritySummary();
        } else if ("key_device_protection".equals(getPreferenceKey())) {
            this.mPreference.setStatus(this.mDPMenuStatus);
            setDPSummary(this.mIsDeviceProtectionElapsed, this.mDPLastScannedTime, num.intValue(), this.mDPMenuStatus);
        }
        SecurityDashboardStatusPreference securityDashboardStatusPreference = this.mStatusPreference;
        if (securityDashboardStatusPreference != null) {
            securityDashboardStatusPreference.setMenuStatus("key_device_protection", "DP", this.mDPMenuStatus);
            this.mStatusPreference.setupStatus(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$getDPInfo$3(final Integer num, String str) {
        this.mDPLastScannedTime = TextUtils.isEmpty(str) ? 0L : Long.parseLong(str);
        SemLog.d(TAG, "getLastScanTimeAsync: " + this.mDPLastScannedTime);
        Context context = this.mContext;
        Uri uri = DeviceProtectionUtils.DEVICE_PROTECTION_DEEPLINK_URI;
        String string = context.getContentResolver().call(DeviceProtectionUtils.AUTHORITY_URI, "dc_security_status", (String) null, new Bundle()).getString("key_status");
        this.mIsDeviceProtectionElapsed = string != null && string.equalsIgnoreCase("scan_needed");
        this.mDpSecurityInfo = num.intValue();
        int intValue = num.intValue();
        boolean z = this.mIsDeviceProtectionElapsed;
        SecurityDashboardConstants$Status securityDashboardConstants$Status = SecurityDashboardConstants$Status.NONE;
        if (intValue == 1) {
            securityDashboardConstants$Status = SecurityDashboardConstants$Status.CRITICAL;
        } else if (intValue == 2 || z) {
            securityDashboardConstants$Status = SecurityDashboardConstants$Status.WARNING;
        } else if (intValue == 0) {
            securityDashboardConstants$Status = SecurityDashboardConstants$Status.OK;
        }
        SemLog.d("DeviceProtectionUtils", "getStatusForDeviceProtectionMenu returned status = " + securityDashboardConstants$Status);
        this.mDPMenuStatus = securityDashboardConstants$Status;
        this.mHandler.post(new Runnable() { // from class: com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                SecurityDashboardAppSecurityPreferenceController.this.lambda$getDPInfo$2(num);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController$$ExternalSyntheticLambda0] */
    public void lambda$getDPInfo$4(final Integer num) {
        SemLog.d(TAG, "getDeviceProtectionSecurityInfoAsync: " + num);
        if (num.intValue() != 3) {
            Context context = this.mContext;
            ExecutorService executorService = this.mExecutorService;
            ?? r2 = new Consumer() { // from class: com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController$$ExternalSyntheticLambda0
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    SecurityDashboardAppSecurityPreferenceController.this.lambda$getDPInfo$3(num, (String) obj);
                }
            };
            Uri uri = DeviceProtectionUtils.DEVICE_PROTECTION_DEEPLINK_URI;
            executorService.execute(new DeviceProtectionUtils$$ExternalSyntheticLambda0(context, (SecurityDashboardAppSecurityPreferenceController$$ExternalSyntheticLambda0) r2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$new$0(Task task) {
        SafetyNetApi$HarmfulAppsResponse safetyNetApi$HarmfulAppsResponse;
        if (!task.isSuccessful() || (safetyNetApi$HarmfulAppsResponse = (SafetyNetApi$HarmfulAppsResponse) task.getResult()) == null) {
            return;
        }
        zzd zzdVar = ((zzaa) safetyNetApi$HarmfulAppsResponse.zza).zzb;
        List<HarmfulAppsData> emptyList = zzdVar == null ? Collections.emptyList() : Arrays.asList(zzdVar.zzb);
        this.mGppMenuStatus = emptyList.isEmpty() ? SecurityDashboardConstants$Status.OK : SecurityDashboardConstants$Status.CRITICAL;
        zzd zzdVar2 = ((zzaa) safetyNetApi$HarmfulAppsResponse.zza).zzb;
        long j = zzdVar2 == null ? 0L : zzdVar2.zza;
        this.mIsGppEnabled = true;
        this.mGppHarmfulAppList = emptyList;
        this.mGppLastScanTime = j;
        if ("key_app_security".equals(getPreferenceKey())) {
            this.mPreference.setStatus(this.mDPMenuStatus.ordinal() > this.mGppMenuStatus.ordinal() ? this.mDPMenuStatus : this.mGppMenuStatus);
            setAppSecuritySummary();
        } else if ("key_google_play_protection".equals(getPreferenceKey())) {
            this.mPreference.setStatus(this.mGppMenuStatus);
            setGPPSummary(true, emptyList, j);
        }
        SecurityDashboardStatusPreference securityDashboardStatusPreference = this.mStatusPreference;
        if (securityDashboardStatusPreference != null) {
            securityDashboardStatusPreference.setMenuStatus("key_google_play_protection", "GPP", this.mGppMenuStatus);
            this.mStatusPreference.setupStatus(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$new$1(Task task) {
        if (task.isSuccessful()) {
            zzad zzadVar = (zzad) ((SafetyNetApi$VerifyAppsUserResponse) task.getResult()).zza;
            Status status = zzadVar.zza;
            if (!((status == null || status.zzc > 0) ? false : zzadVar.zzb)) {
                this.mGppMenuStatus = SecurityDashboardConstants$Status.CRITICAL;
                this.mIsGppEnabled = false;
                if ("key_app_security".equals(getPreferenceKey())) {
                    this.mPreference.setStatus(this.mDPMenuStatus.ordinal() > this.mGppMenuStatus.ordinal() ? this.mDPMenuStatus : this.mGppMenuStatus);
                    setAppSecuritySummary();
                } else if ("key_google_play_protection".equals(getPreferenceKey())) {
                    this.mPreference.setStatus(this.mGppMenuStatus);
                    setGPPSummary(false, null, 0L);
                }
                SecurityDashboardStatusPreference securityDashboardStatusPreference = this.mStatusPreference;
                if (securityDashboardStatusPreference != null) {
                    securityDashboardStatusPreference.setMenuStatus("key_google_play_protection", "GPP", this.mGppMenuStatus);
                    this.mStatusPreference.setupStatus(false);
                    return;
                }
                return;
            }
            Context context = this.mContext;
            OnCompleteListener onCompleteListener = this.onCompleteListenerGetHarmfulApps;
            Uri uri = SecurityDashboardUtils.FMM_SUPPORT_URI;
            Api api = SafetyNet.API;
            SafetyNetClient safetyNetClient = new SafetyNetClient(context, SafetyNet.API, null, new GoogleApi.Settings(new ApiExceptionMapper(), Looper.getMainLooper()));
            zzae zzaeVar = SafetyNet.SafetyNetApi;
            zabv zabvVar = safetyNetClient.zai;
            zzaeVar.getClass();
            zzn zznVar = new zzn(zabvVar);
            GoogleApi googleApi = zabvVar.zaa;
            googleApi.getClass();
            zznVar.zaq = zznVar.zaq || ((Boolean) BasePendingResult.zaa.get()).booleanValue();
            GoogleApiManager googleApiManager = googleApi.zaa;
            googleApiManager.getClass();
            zae zaeVar = new zae(zznVar);
            zaq zaqVar = googleApiManager.zat;
            zaqVar.sendMessage(zaqVar.obtainMessage(4, new zach(zaeVar, googleApiManager.zao.get(), googleApi)));
            com.google.android.gms.common.internal.zaq zaqVar2 = new com.google.android.gms.common.internal.zaq(new SafetyNetApi$HarmfulAppsResponse());
            TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
            zznVar.addStatusListener(new zap(zznVar, taskCompletionSource, zaqVar2));
            zzw zzwVar = taskCompletionSource.zza;
            zzwVar.getClass();
            zzwVar.zzb.zza(new zzj(TaskExecutors.MAIN_THREAD, onCompleteListener));
            synchronized (zzwVar.zza) {
                try {
                    if (zzwVar.zzc) {
                        zzwVar.zzb.zzb(zzwVar);
                    }
                } finally {
                }
            }
        }
    }

    private void setAppSecuritySummary() {
        SecExpandableMenuPreference secExpandableMenuPreference = this.mPreference;
        if (secExpandableMenuPreference.mStatus == SecurityDashboardConstants$Status.OK) {
            secExpandableMenuPreference.setSummary(this.mContext.getResources().getString(R.string.security_dashboard_app_security_all_good_description), false);
        } else if (this.mGppMenuStatus.ordinal() >= this.mDPMenuStatus.ordinal()) {
            setGPPSummary(this.mIsGppEnabled, this.mGppHarmfulAppList, this.mGppLastScanTime);
        } else {
            setDPSummary(this.mIsDeviceProtectionElapsed, this.mDPLastScannedTime, this.mDpSecurityInfo, this.mDPMenuStatus);
        }
        setSummaryColor();
    }

    private void setDPSummary(boolean z, long j, int i, SecurityDashboardConstants$Status securityDashboardConstants$Status) {
        Uri uri = SecurityDashboardUtils.FMM_SUPPORT_URI;
        String charSequence = DateFormat.format(DateFormat.getBestDateTimePattern(Locale.getDefault(), "dMMMMyyyy"), j).toString();
        if (i == 2) {
            this.mPreference.setSummary(this.mContext.getResources().getString(R.string.security_dashboard_device_protection_off_description), false);
        } else if (securityDashboardConstants$Status == SecurityDashboardConstants$Status.CRITICAL || !(z || j == 0 || charSequence.equalsIgnoreCase(ApnSettings.MVNO_NONE))) {
            int i2 = this.mContext.getSharedPreferences("shared_pref_security_dashboard", 0).getInt("shared_pref_key_device_protection_threat_count", 0);
            if (i2 == 0) {
                this.mPreference.setSummary(this.mContext.getResources().getString(R.string.security_dashboard_gpp_dp_apps_scanned_subtext_description, charSequence), false);
            } else {
                this.mPreference.setSummary(this.mContext.getResources().getQuantityString(R.plurals.security_dashboard_malware_apps_detected, i2, Integer.valueOf(i2)), false);
            }
        } else {
            this.mPreference.setSummary(this.mContext.getResources().getString(R.string.security_dashboard_summary_status_scan_for_malware), false);
        }
        setSummaryColor();
    }

    private void setGPPSummary(boolean z, List<HarmfulAppsData> list, long j) {
        if (!z) {
            this.mPreference.setSummary(this.mContext.getResources().getString(R.string.security_dashboard_device_gpp_off_description), false);
        } else if (!list.isEmpty()) {
            this.mPreference.setSummary(this.mContext.getResources().getQuantityString(R.plurals.security_dashboard_malware_apps_detected, list.size(), Integer.valueOf(list.size())), false);
        } else if (j == 0) {
            this.mPreference.setSummary(this.mContext.getResources().getString(R.string.security_dashboard_summary_status_scan_for_malware), false);
        } else if (j > 0) {
            SecExpandableMenuPreference secExpandableMenuPreference = this.mPreference;
            Resources resources = this.mContext.getResources();
            Uri uri = SecurityDashboardUtils.FMM_SUPPORT_URI;
            secExpandableMenuPreference.setSummary(resources.getString(R.string.security_dashboard_gpp_dp_apps_scanned_subtext_description, DateFormat.format(DateFormat.getBestDateTimePattern(Locale.getDefault(), "dMMMMyyyy"), j).toString()), false);
        }
        setSummaryColor();
    }

    private void setSummaryColor() {
        int ordinal = this.mPreference.mStatus.ordinal();
        if (ordinal == 2) {
            this.mPreference.seslSetSummaryColor(this.mContext.getResources().getColor(R.color.security_dashboard_menu_subtext_default_color, null), false);
        } else if (ordinal == 3) {
            this.mPreference.seslSetSummaryColor(this.mContext.getResources().getColor(R.color.security_dashboard_menu_subtext_suggestion_color, null), false);
        } else {
            if (ordinal != 4) {
                return;
            }
            this.mPreference.seslSetSummaryColor(this.mContext.getResources().getColor(R.color.security_dashboard_menu_subtext_warning_color, null), false);
        }
    }

    private void updateMenuStatus() {
        if (this.mIsGPPSupported) {
            Context context = this.mContext;
            OnCompleteListener onCompleteListener = this.onCompleteListenerCheckGPPAndGetStatus;
            Uri uri = SecurityDashboardUtils.FMM_SUPPORT_URI;
            Api api = SafetyNet.API;
            SafetyNetClient safetyNetClient = new SafetyNetClient(context, SafetyNet.API, null, new GoogleApi.Settings(new ApiExceptionMapper(), Looper.getMainLooper()));
            TaskApiCall$Builder taskApiCall$Builder = new TaskApiCall$Builder();
            taskApiCall$Builder.zaa = new zzm(safetyNetClient);
            zzw zae = safetyNetClient.zae(0, new zacv(taskApiCall$Builder, taskApiCall$Builder.zac, true, 4201));
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
        if (this.mIsDPSupported) {
            getDPInfo();
        }
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mStatusPreference = (SecurityDashboardStatusPreference) preferenceScreen.findPreference("security_dashboard_status");
        updateMenuStatus();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mIsAppSecuritySupported ? 0 : 3;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController$$ExternalSyntheticLambda1] */
    public void getDPInfo() {
        Context context = this.mContext;
        ExecutorService executorService = this.mExecutorService;
        ?? r2 = new Consumer() { // from class: com.samsung.android.settings.privacy.SecurityDashboardAppSecurityPreferenceController$$ExternalSyntheticLambda1
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                SecurityDashboardAppSecurityPreferenceController.this.lambda$getDPInfo$4((Integer) obj);
            }
        };
        Uri uri = DeviceProtectionUtils.DEVICE_PROTECTION_DEEPLINK_URI;
        executorService.execute(new DeviceProtectionUtils$$ExternalSyntheticLambda0((SecurityDashboardAppSecurityPreferenceController$$ExternalSyntheticLambda1) r2, context));
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if ("key_app_security".equals(preference.getKey())) {
            LoggingHelper.insertEventLogging(9032, 56036);
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController
    public void setDefaultSummary() {
        StringBuilder sb = new StringBuilder();
        if (this.mIsDPSupported) {
            sb.append(this.mContext.getResources().getString(R.string.device_protection_title));
        }
        if (this.mIsGPPSupported) {
            if (sb.length() > 0) {
                sb.append(this.mContext.getResources().getString(R.string.comma) + " ");
            }
            sb.append(this.mContext.getResources().getString(R.string.google_play_protect_title));
        }
        this.mPreference.setSummary(sb, true);
        this.mPreference.seslSetSummaryColor(this.mContext.getResources().getColor(R.color.security_dashboard_menu_subtext_default_color, null), true);
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public void updateNonIndexableKeys(List<String> list) {
        if (this.mIsAppSecuritySupported) {
            return;
        }
        list.add("key_app_security");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        updateMenuStatus();
    }

    @Override // com.samsung.android.settings.privacy.SecurityDashboardPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}

package com.samsung.android.settings.datausage;

import android.app.usage.NetworkStats;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkTemplate;
import android.os.Bundle;
import android.os.UserHandle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.datausage.DataUsageList;
import com.android.settings.datausage.DataUsageUtils;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnCreate;
import com.android.settingslib.net.DataUsageController;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.knox.SecManagedProfileNetworkStatsSummaryLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecRoamingDataUsagePreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnCreate {
    private static final String TAG = "SecRoamingDataUsagePreferenceController";
    private static long sSecureFolderTotalBytes;
    private DataUsageController mDataUsageController;
    private DataUsageController.DataUsageInfo mDataUsageInfo;
    int mDefaultSubId;
    private NetworkTemplate mNetworkTemplate;

    public SecRoamingDataUsagePreferenceController(Context context, String str) {
        super(context, str);
        this.mDefaultSubId = DataUsageUtils.getDefaultSubscriptionId(this.mContext);
    }

    private void refreshDataUsageInfo() {
        DataUsageController dataUsageController = this.mDataUsageController;
        if (dataUsageController != null) {
            this.mDataUsageInfo = dataUsageController.getDataUsageInfo(this.mNetworkTemplate);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return DataUsageUtils.isDataRoaming(this.mDefaultSubId) ? 0 : 2;
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
    public Intent getLaunchIntent() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("network_template", this.mNetworkTemplate);
        bundle.putInt("sub_id", 0);
        bundle.putBoolean("is_roaming", true);
        bundle.putInt("network_type", 0);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mArguments = bundle;
        launchRequest.mDestinationName = DataUsageList.class.getName();
        launchRequest.mSourceMetricsCategory = 0;
        subSettingLauncher.setTitleRes(R.string.roaming_datauage_title, null);
        return subSettingLauncher.toIntent();
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
        long j;
        Object formatFileSize;
        if (SemPersonaManager.isSecureFolderId(UserHandle.myUserId())) {
            j = sSecureFolderTotalBytes;
            Log.i(TAG, "secureFolderTotalBytes : " + j);
        } else {
            j = this.mDataUsageInfo.usageLevel;
            Log.i(TAG, "TotalBytes: " + j);
        }
        if (Rune.SUPPORT_SMARTMANAGER_CN) {
            formatFileSize = DataUsageUtilsCHN.formatDataUsage(this.mContext, j);
        } else {
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            formatFileSize =
                    featureFactoryImpl
                            .getDataUsageFeatureProvider()
                            .formatFileSize(this.mContext, j);
        }
        return this.mContext.getString(
                R.string.data_usage_template, formatFileSize, this.mDataUsageInfo.period);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        this.mContext.startActivity(getLaunchIntent());
        return true;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnCreate
    public void onCreate(Bundle bundle) {
        TelephonyManager telephonyManager =
                (TelephonyManager) this.mContext.getSystemService("phone");
        int i = this.mDefaultSubId;
        if (i != -1) {
            String subscriberId = telephonyManager.getSubscriberId(i);
            if (subscriberId != null) {
                this.mNetworkTemplate =
                        new NetworkTemplate.Builder(1)
                                .setRoaming(1)
                                .setSubscriberIds(Set.of(subscriberId))
                                .build();
            }
            DataUsageController dataUsageController = new DataUsageController(this.mContext);
            this.mDataUsageController = dataUsageController;
            this.mDataUsageInfo = dataUsageController.getDataUsageInfo(this.mNetworkTemplate);
        }
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
    public void updateState(final Preference preference) {
        super.updateState(preference);
        refreshDataUsageInfo();
        boolean isSecureFolderId = SemPersonaManager.isSecureFolderId(UserHandle.myUserId());
        if (isSecureFolderId) {
            SecManagedProfileNetworkStatsSummaryLoader.Builder builder =
                    new SecManagedProfileNetworkStatsSummaryLoader.Builder(this.mContext);
            DataUsageController.DataUsageInfo dataUsageInfo = this.mDataUsageInfo;
            builder.mStart = dataUsageInfo.cycleStart;
            builder.mEnd = dataUsageInfo.cycleEnd;
            builder.mNetworkTemplate = this.mNetworkTemplate;
            new SecManagedProfileNetworkStatsSummaryLoader(
                            builder,
                            new SecManagedProfileNetworkStatsSummaryLoader
                                    .NetworkStatsLoaderResponse() { // from class:
                                                                    // com.samsung.android.settings.datausage.SecRoamingDataUsagePreferenceController.1
                                @Override // com.samsung.android.settings.knox.SecManagedProfileNetworkStatsSummaryLoader.NetworkStatsLoaderResponse
                                public final void setNetworkStatsData(NetworkStats networkStats) {
                                    SecRoamingDataUsagePreferenceController
                                            secRoamingDataUsagePreferenceController =
                                                    SecRoamingDataUsagePreferenceController.this;
                                    SecRoamingDataUsagePreferenceController
                                                    .sSecureFolderTotalBytes =
                                            KnoxUtils.bindStats(
                                                    ((AbstractPreferenceController)
                                                                    secRoamingDataUsagePreferenceController)
                                                            .mContext,
                                                    networkStats);
                                    long j =
                                            SecRoamingDataUsagePreferenceController
                                                    .sSecureFolderTotalBytes;
                                    Preference preference2 = preference;
                                    if (j > 0) {
                                        preference2.setEnabled(true);
                                    } else {
                                        preference2.setEnabled(false);
                                    }
                                    if (secRoamingDataUsagePreferenceController.mDataUsageInfo
                                            != null) {
                                        secRoamingDataUsagePreferenceController.refreshSummary(
                                                preference2);
                                    }
                                }
                            })
                    .execute(new NetworkTemplate[0]);
        } else if (this.mDataUsageInfo != null) {
            refreshSummary(preference);
        }
        long j = this.mDataUsageInfo.usageLevel;
        if (isSecureFolderId) {
            j = sSecureFolderTotalBytes;
        }
        if (j <= 0) {
            preference.setEnabled(false);
            return;
        }
        Log.i(
                TAG,
                "cycle start time : "
                        + this.mDataUsageInfo.cycleStart
                        + ", cycle end time : "
                        + this.mDataUsageInfo.cycleEnd);
        preference.setEnabled(true);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}

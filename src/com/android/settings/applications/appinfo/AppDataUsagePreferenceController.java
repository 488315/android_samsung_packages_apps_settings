package com.android.settings.applications.appinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkTemplate;
import android.os.Bundle;
import android.os.INetworkManagementService;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.SubscriptionManager;
import android.text.format.DateUtils;
import android.text.format.Formatter;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.datausage.AppDataUsage;
import com.android.settings.datausage.DataUsageUtils;
import com.android.settingslib.AppItem;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.net.NetworkCycleDataForUid;
import com.android.settingslib.net.NetworkCycleDataForUidLoader;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Deprecated(forRemoval = true)
/* loaded from: classes2.dex */
public class AppDataUsagePreferenceController extends AppInfoPreferenceControllerBase
        implements LoaderManager.LoaderCallbacks, LifecycleObserver, OnResume, OnPause {
    private List<NetworkCycleDataForUid> mAppUsageData;

    public AppDataUsagePreferenceController(Context context, String str) {
        super(context, str);
    }

    private CharSequence getDataSummary() {
        if (this.mAppUsageData == null) {
            return this.mContext.getString(R.string.computing_size);
        }
        long currentTimeMillis = System.currentTimeMillis();
        long j = 0;
        for (NetworkCycleDataForUid networkCycleDataForUid : this.mAppUsageData) {
            j += networkCycleDataForUid.mTotalUsage;
            long j2 = networkCycleDataForUid.mStartTime;
            if (j2 < currentTimeMillis) {
                currentTimeMillis = j2;
            }
        }
        if (j == 0) {
            return this.mContext.getString(R.string.no_data_usage);
        }
        Context context = this.mContext;
        return context.getString(
                R.string.data_summary_format,
                Formatter.formatFileSize(context, j, 8),
                DateUtils.formatDateTime(this.mContext, currentTimeMillis, 65552));
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return isBandwidthControlEnabled() ? 0 : 2;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase
    public Class<? extends SettingsPreferenceFragment> getDetailFragmentClass() {
        return AppDataUsage.class;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    public boolean isBandwidthControlEnabled() {
        StringBuilder sb = Utils.sBuilder;
        try {
            return INetworkManagementService.Stub.asInterface(
                            ServiceManager.getService("network_management"))
                    .isBandwidthControlEnabled();
        } catch (RemoteException unused) {
            return false;
        }
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public Loader onCreateLoader(int i, Bundle bundle) {
        Context context = this.mContext;
        Intrinsics.checkNotNullParameter(context, "context");
        NetworkTemplate defaultTemplate =
                DataUsageUtils.getDefaultTemplate(
                        context, SubscriptionManager.getDefaultDataSubscriptionId());
        Intrinsics.checkNotNullExpressionValue(defaultTemplate, "getDefaultTemplate(...)");
        int i2 = this.mParent.mAppEntry.info.uid;
        NetworkCycleDataForUidLoader.AnonymousClass1 anonymousClass1 =
                new NetworkCycleDataForUidLoader.AnonymousClass1(this.mContext);
        anonymousClass1.mRetrieveDetail = false;
        anonymousClass1.mNetworkTemplate = defaultTemplate;
        ((ArrayList) anonymousClass1.mUids).add(Integer.valueOf(i2));
        if (Process.isApplicationUid(i2)) {
            int sdkSandboxUid = Process.toSdkSandboxUid(i2);
            ((ArrayList) anonymousClass1.mUids).add(Integer.valueOf(sdkSandboxUid));
        }
        return new NetworkCycleDataForUidLoader(anonymousClass1);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        if (isAvailable()) {
            this.mParent.getLoaderManager().destroyLoader(2);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        if (isAvailable()) {
            int i = this.mParent.mAppEntry.info.uid;
            new AppItem(i).addUid(i);
            this.mParent.getLoaderManager().restartLoader(2, null, this);
        }
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        preference.setSummary(getDataSummary());
        if (AppUtils.isAppInstalled(this.mContext, this.mAppEntry.info.packageName)) {
            preference.setEnabled(true);
        } else {
            preference.setEnabled(false);
        }
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public void onLoadFinished(Loader loader, List<NetworkCycleDataForUid> list) {
        this.mAppUsageData = list;
        updateState(this.mPreference);
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public void onLoaderReset(Loader loader) {}
}

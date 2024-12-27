package com.android.settings.spa.app.appinfo;

import android.os.Bundle;
import android.util.Log;

import androidx.core.os.BundleKt;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.fuelgauge.AdvancedPowerUsageDetail;
import com.android.settings.fuelgauge.batteryusage.BatteryDiffEntry;
import com.android.settingslib.Utils;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final /* synthetic */ class AppBatteryPreferenceKt$AppBatteryPreference$2$onClick$1
        extends FunctionReferenceImpl implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    /* renamed from: invoke */
    public final Object mo1068invoke() {
        AppBatteryPresenter appBatteryPresenter = (AppBatteryPresenter) this.receiver;
        LoadingState loadingState =
                (LoadingState) appBatteryPresenter.batteryDiffEntryState$delegate.getValue();
        loadingState.getClass();
        BatteryDiffEntry batteryDiffEntry =
                (BatteryDiffEntry)
                        (loadingState instanceof LoadingState.Done
                                ? ((LoadingState.Done) loadingState).result
                                : null);
        if (batteryDiffEntry != null) {
            Log.i("AppBatteryPresenter", "handlePreferenceTreeClick():\n" + batteryDiffEntry);
            AdvancedPowerUsageDetail.startBatteryDetailPage(
                    appBatteryPresenter.context,
                    20,
                    batteryDiffEntry,
                    Utils.formatPercentage(batteryDiffEntry.mPercentage, true),
                    null,
                    false,
                    null,
                    null);
        } else {
            Log.i(
                    "AppBatteryPresenter",
                    "Launch : " + appBatteryPresenter.app.packageName + " with package name");
            Bundle bundleOf =
                    BundleKt.bundleOf(
                            new Pair("extra_package_name", appBatteryPresenter.app.packageName),
                            new Pair("extra_power_usage_percent", Utils.formatPercentage(0)),
                            new Pair("extra_uid", Integer.valueOf(appBatteryPresenter.app.uid)));
            SubSettingLauncher subSettingLauncher =
                    new SubSettingLauncher(appBatteryPresenter.context);
            String name = AdvancedPowerUsageDetail.class.getName();
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            launchRequest.mDestinationName = name;
            subSettingLauncher.setTitleRes(R.string.battery_details_title, null);
            launchRequest.mArguments = bundleOf;
            launchRequest.mUserHandle = ApplicationInfosKt.getUserHandle(appBatteryPresenter.app);
            launchRequest.mSourceMetricsCategory = 20;
            subSettingLauncher.launch();
        }
        return Unit.INSTANCE;
    }
}

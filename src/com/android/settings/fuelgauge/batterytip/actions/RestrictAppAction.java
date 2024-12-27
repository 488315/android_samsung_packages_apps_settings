package com.android.settings.fuelgauge.batterytip.actions;

import android.content.Context;

import com.android.internal.util.CollectionUtils;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.fuelgauge.BatteryUtils;
import com.android.settings.fuelgauge.batterytip.AppInfo;
import com.android.settings.fuelgauge.batterytip.BatteryDatabaseManager;
import com.android.settings.fuelgauge.batterytip.tips.RestrictAppTip;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class RestrictAppAction extends BatteryTipAction {
    BatteryDatabaseManager mBatteryDatabaseManager;
    BatteryUtils mBatteryUtils;
    public final RestrictAppTip mRestrictAppTip;

    public RestrictAppAction(Context context, RestrictAppTip restrictAppTip) {
        super(context);
        this.mRestrictAppTip = restrictAppTip;
        this.mBatteryUtils = BatteryUtils.getInstance(context);
        this.mBatteryDatabaseManager = BatteryDatabaseManager.getInstance(context);
    }

    @Override // com.android.settings.fuelgauge.batterytip.actions.BatteryTipAction
    public final void handlePositiveAction(int i) {
        List list = this.mRestrictAppTip.mRestrictAppList;
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            AppInfo appInfo = (AppInfo) list.get(i2);
            String str = appInfo.packageName;
            this.mBatteryUtils.setForceAppStandby(appInfo.uid, 1, 2, str);
            boolean isEmpty = CollectionUtils.isEmpty(appInfo.anomalyTypes);
            SettingsMetricsFeatureProvider settingsMetricsFeatureProvider =
                    this.mMetricsFeatureProvider;
            if (isEmpty) {
                settingsMetricsFeatureProvider.action(0, 1362, i, 0, str);
            } else {
                Iterator it = appInfo.anomalyTypes.iterator();
                while (it.hasNext()) {
                    settingsMetricsFeatureProvider.action(
                            0, 1362, i, ((Integer) it.next()).intValue(), str);
                }
            }
        }
        this.mBatteryDatabaseManager.updateAnomalies(list);
    }
}

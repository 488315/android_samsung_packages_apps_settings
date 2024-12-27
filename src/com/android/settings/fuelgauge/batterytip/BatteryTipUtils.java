package com.android.settings.fuelgauge.batterytip;

import android.app.AppOpsManager;
import android.os.UserHandle;
import android.os.UserManager;

import com.android.internal.util.CollectionUtils;
import com.android.settings.SettingsActivity;
import com.android.settings.core.InstrumentedPreferenceFragment;
import com.android.settings.fuelgauge.batterytip.actions.BatteryTipAction;
import com.android.settings.fuelgauge.batterytip.actions.OpenBatterySaverAction;
import com.android.settings.fuelgauge.batterytip.actions.OpenRestrictAppFragmentAction;
import com.android.settings.fuelgauge.batterytip.actions.RestrictAppAction;
import com.android.settings.fuelgauge.batterytip.actions.SmartBatteryAction;
import com.android.settings.fuelgauge.batterytip.actions.UnrestrictAppAction;
import com.android.settings.fuelgauge.batterytip.tips.BatteryTip;
import com.android.settings.fuelgauge.batterytip.tips.RestrictAppTip;
import com.android.settings.fuelgauge.batterytip.tips.UnrestrictAppTip;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BatteryTipUtils {
    public static BatteryTipAction getActionForBatteryTip(
            BatteryTip batteryTip,
            SettingsActivity settingsActivity,
            InstrumentedPreferenceFragment instrumentedPreferenceFragment) {
        int i = batteryTip.mType;
        if (i == 0) {
            SmartBatteryAction smartBatteryAction =
                    new SmartBatteryAction(settingsActivity.getApplicationContext());
            smartBatteryAction.mSettingsActivity = settingsActivity;
            smartBatteryAction.mFragment = instrumentedPreferenceFragment;
            return smartBatteryAction;
        }
        if (i == 1) {
            return batteryTip.mState == 1
                    ? new OpenRestrictAppFragmentAction(
                            instrumentedPreferenceFragment, (RestrictAppTip) batteryTip)
                    : new RestrictAppAction(settingsActivity, (RestrictAppTip) batteryTip);
        }
        if (i == 3 || i == 5) {
            return new OpenBatterySaverAction(settingsActivity);
        }
        if (i != 7) {
            return null;
        }
        return new UnrestrictAppAction(settingsActivity, (UnrestrictAppTip) batteryTip);
    }

    public static List getRestrictedAppsList(AppOpsManager appOpsManager, UserManager userManager) {
        List<UserHandle> userProfiles = userManager.getUserProfiles();
        List packagesForOps = appOpsManager.getPackagesForOps(new int[] {70});
        ArrayList arrayList = new ArrayList();
        int size = CollectionUtils.size(packagesForOps);
        for (int i = 0; i < size; i++) {
            AppOpsManager.PackageOps packageOps = (AppOpsManager.PackageOps) packagesForOps.get(i);
            List ops = packageOps.getOps();
            int size2 = ops.size();
            for (int i2 = 0; i2 < size2; i2++) {
                AppOpsManager.OpEntry opEntry = (AppOpsManager.OpEntry) ops.get(i2);
                if (opEntry.getOp() == 70
                        && opEntry.getMode() != 0
                        && userProfiles.contains(
                                new UserHandle(UserHandle.getUserId(packageOps.getUid())))) {
                    AppInfo.Builder builder = new AppInfo.Builder();
                    builder.mPackageName = packageOps.getPackageName();
                    builder.mUid = packageOps.getUid();
                    arrayList.add(new AppInfo(builder));
                }
            }
        }
        return arrayList;
    }
}

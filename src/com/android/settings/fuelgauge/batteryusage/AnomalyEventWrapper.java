package com.android.settings.fuelgauge.batteryusage;

import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;

import com.android.settings.core.SubSettingLauncher;

import java.util.ArrayList;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AnomalyEventWrapper {
    public final int mCardStyleId;
    public final Context mContext;
    public final PowerAnomalyEvent mPowerAnomalyEvent;
    public final int mResourceIndex;
    public SubSettingLauncher mSubSettingLauncher = null;
    public Pair mHighlightSlotPair = null;
    public BatteryDiffEntry mRelatedBatteryDiffEntry = null;

    public AnomalyEventWrapper(Context context, PowerAnomalyEvent powerAnomalyEvent) {
        this.mContext = context;
        this.mPowerAnomalyEvent = powerAnomalyEvent;
        this.mCardStyleId = powerAnomalyEvent.getType().getNumber();
        this.mResourceIndex = powerAnomalyEvent.getKey().getNumber();
    }

    public final String getAnomalyEntryKey() {
        PowerAnomalyEvent powerAnomalyEvent = this.mPowerAnomalyEvent;
        if (powerAnomalyEvent.hasWarningItemInfo()
                && powerAnomalyEvent.getWarningItemInfo().hasItemKey()) {
            return powerAnomalyEvent.getWarningItemInfo().getItemKey();
        }
        return null;
    }

    public final Pair getHighlightSlotPair(BatteryLevelData batteryLevelData) {
        Pair pair = this.mHighlightSlotPair;
        if (pair != null) {
            return pair;
        }
        PowerAnomalyEvent powerAnomalyEvent = this.mPowerAnomalyEvent;
        if (!powerAnomalyEvent.hasWarningItemInfo()) {
            return null;
        }
        WarningItemInfo warningItemInfo = powerAnomalyEvent.getWarningItemInfo();
        Long valueOf =
                warningItemInfo.hasStartTimestamp()
                        ? Long.valueOf(warningItemInfo.getStartTimestamp())
                        : null;
        Long valueOf2 =
                warningItemInfo.hasEndTimestamp()
                        ? Long.valueOf(warningItemInfo.getEndTimestamp())
                        : null;
        if (valueOf != null && valueOf2 != null) {
            long longValue = valueOf.longValue();
            long longValue2 = valueOf2.longValue();
            int m908$$Nest$mgetIndexByTimestamps =
                    BatteryLevelData.PeriodBatteryLevelData.m908$$Nest$mgetIndexByTimestamps(
                            batteryLevelData.mDailyBatteryLevels, longValue, longValue2);
            Pair create =
                    Pair.create(
                            Integer.valueOf(m908$$Nest$mgetIndexByTimestamps),
                            Integer.valueOf(
                                    m908$$Nest$mgetIndexByTimestamps == -2
                                            ? -2
                                            : BatteryLevelData.PeriodBatteryLevelData
                                                    .m908$$Nest$mgetIndexByTimestamps(
                                                            (BatteryLevelData
                                                                            .PeriodBatteryLevelData)
                                                                    ((ArrayList)
                                                                                    batteryLevelData
                                                                                            .mHourlyBatteryLevelsPerDay)
                                                                            .get(
                                                                                    m908$$Nest$mgetIndexByTimestamps),
                                                            longValue,
                                                            longValue2)));
            this.mHighlightSlotPair = create;
            if (((Integer) create.first).intValue() == -2
                    || ((Integer) this.mHighlightSlotPair.second).intValue() == -2) {
                this.mHighlightSlotPair = null;
            }
        }
        return this.mHighlightSlotPair;
    }

    public final Object getInfo(Function function, Function function2) {
        PowerAnomalyEvent powerAnomalyEvent = this.mPowerAnomalyEvent;
        if (function != null && powerAnomalyEvent.hasWarningBannerInfo()) {
            return function.apply(powerAnomalyEvent.getWarningBannerInfo());
        }
        if (function2 == null || !powerAnomalyEvent.hasWarningItemInfo()) {
            return null;
        }
        return function2.apply(powerAnomalyEvent.getWarningItemInfo());
    }

    public final int getResourceId(int i, int i2, String str) {
        String stringFromArrayResource = getStringFromArrayResource(i, i2);
        if (TextUtils.isEmpty(stringFromArrayResource)) {
            return 0;
        }
        return this.mContext
                .getResources()
                .getIdentifier(stringFromArrayResource, str, this.mContext.getPackageName());
    }

    public final String getStringFromArrayResource(int i, int i2) {
        if (i <= 0 || i2 < 0) {
            return null;
        }
        String[] stringArray = this.mContext.getResources().getStringArray(i);
        if (i2 < 0 || i2 >= stringArray.length) {
            return null;
        }
        return stringArray[i2];
    }
}

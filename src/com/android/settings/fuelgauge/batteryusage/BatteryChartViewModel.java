package com.android.settings.fuelgauge.batteryusage;

import androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$semanticsModifier$1$1$3$$ExternalSyntheticOutline0;
import androidx.core.util.Preconditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryChartViewModel {
    public final AxisLabelPosition mAxisLabelPosition;
    public final String[] mBatteryLevelTexts;
    public final String[] mContentDescription;
    public final String[] mFullTexts;
    public final BatteryChartPreferenceController.BaseLabelTextGenerator mLabelTextGenerator;
    public final List mLevels;
    public final String[] mTexts;
    public final List mTimestamps;
    public int mSelectedIndex = -1;
    public int mHighlightSlotIndex = -2;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    final class AxisLabelPosition {
        public static final /* synthetic */ AxisLabelPosition[] $VALUES;
        public static final AxisLabelPosition BETWEEN_TRAPEZOIDS;
        public static final AxisLabelPosition CENTER_OF_TRAPEZOIDS;

        static {
            AxisLabelPosition axisLabelPosition = new AxisLabelPosition("BETWEEN_TRAPEZOIDS", 0);
            BETWEEN_TRAPEZOIDS = axisLabelPosition;
            AxisLabelPosition axisLabelPosition2 = new AxisLabelPosition("CENTER_OF_TRAPEZOIDS", 1);
            CENTER_OF_TRAPEZOIDS = axisLabelPosition2;
            $VALUES = new AxisLabelPosition[] {axisLabelPosition, axisLabelPosition2};
        }

        public static AxisLabelPosition valueOf(String str) {
            return (AxisLabelPosition) Enum.valueOf(AxisLabelPosition.class, str);
        }

        public static AxisLabelPosition[] values() {
            return (AxisLabelPosition[]) $VALUES.clone();
        }
    }

    public BatteryChartViewModel(
            List list,
            List list2,
            AxisLabelPosition axisLabelPosition,
            BatteryChartPreferenceController.BaseLabelTextGenerator baseLabelTextGenerator) {
        ArrayList arrayList = (ArrayList) list;
        boolean z = arrayList.size() == list2.size() && arrayList.size() >= 2;
        Locale locale = Locale.ENGLISH;
        Preconditions.checkArgument(
                CoreTextFieldKt$CoreTextField$semanticsModifier$1$1$3$$ExternalSyntheticOutline0.m(
                        "Invalid BatteryChartViewModel levels.size: ",
                        ", timestamps.size: ",
                        arrayList.size(),
                        list2.size(),
                        "."),
                z);
        this.mLevels = arrayList;
        this.mTimestamps = list2;
        this.mAxisLabelPosition = axisLabelPosition;
        this.mLabelTextGenerator = baseLabelTextGenerator;
        this.mTexts = new String[arrayList.size()];
        this.mFullTexts = new String[arrayList.size()];
        this.mContentDescription = new String[arrayList.size()];
        this.mBatteryLevelTexts = new String[arrayList.size() + 1];
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BatteryChartViewModel)) {
            return false;
        }
        BatteryChartViewModel batteryChartViewModel = (BatteryChartViewModel) obj;
        return Objects.equals(this.mLevels, batteryChartViewModel.mLevels)
                && Objects.equals(this.mTimestamps, batteryChartViewModel.mTimestamps)
                && this.mAxisLabelPosition == batteryChartViewModel.mAxisLabelPosition
                && this.mSelectedIndex == batteryChartViewModel.mSelectedIndex;
    }

    public final String getContentDescription(int i) {
        String[] strArr = this.mContentDescription;
        if (strArr[i] == null) {
            strArr[i] = this.mLabelTextGenerator.generateContentDescription(i, this.mTimestamps);
        }
        return strArr[i];
    }

    public final String getFullText(int i) {
        String[] strArr = this.mFullTexts;
        if (strArr[i] == null) {
            strArr[i] = this.mLabelTextGenerator.generateFullText(i, this.mTimestamps);
        }
        return strArr[i];
    }

    public final String getSlotBatteryLevelText(int i) {
        int size = i != -1 ? i : this.mLevels.size();
        String[] strArr = this.mBatteryLevelTexts;
        if (strArr[size] == null) {
            strArr[size] = this.mLabelTextGenerator.generateSlotBatteryLevelText(i, this.mLevels);
        }
        return strArr[size];
    }

    public final String getText(int i) {
        String[] strArr = this.mTexts;
        if (strArr[i] == null) {
            strArr[i] = this.mLabelTextGenerator.generateText(i, this.mTimestamps);
        }
        return strArr[i];
    }

    public final int hashCode() {
        return Objects.hash(
                this.mLevels,
                this.mTimestamps,
                Integer.valueOf(this.mSelectedIndex),
                this.mAxisLabelPosition);
    }

    public final String toString() {
        for (int i = 0; i < this.mLevels.size(); i++) {
            getText(i);
            getFullText(i);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("levels: " + Objects.toString(this.mLevels));
        sb.append(", timestamps: " + Objects.toString(this.mTimestamps));
        sb.append(", texts: " + Arrays.toString(this.mTexts));
        sb.append(", fullTexts: " + Arrays.toString(this.mFullTexts));
        sb.append(", axisLabelPosition: " + this.mAxisLabelPosition);
        sb.append(", selectedIndex: " + this.mSelectedIndex);
        return sb.toString();
    }
}

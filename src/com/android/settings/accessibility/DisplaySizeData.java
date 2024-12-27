package com.android.settings.accessibility;

import android.content.Context;

import com.android.settingslib.display.DisplayDensityUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DisplaySizeData extends PreviewSizeData {
    public final DisplayDensityUtils mDensity;

    public DisplaySizeData(Context context) {
        super(context);
        DisplayDensityUtils displayDensityUtils = new DisplayDensityUtils(context);
        this.mDensity = displayDensityUtils;
        int i = displayDensityUtils.mCurrentIndex;
        if (i >= 0) {
            this.mDefaultValue =
                    Integer.valueOf(displayDensityUtils.mDefaultDensityForDefaultDisplay);
            this.mInitialIndex = i;
            this.mValues =
                    (List)
                            Arrays.stream(displayDensityUtils.mDefaultDisplayDensityValues)
                                    .boxed()
                                    .collect(Collectors.toList());
        } else {
            int i2 = context.getResources().getDisplayMetrics().densityDpi;
            this.mDefaultValue = Integer.valueOf(i2);
            this.mInitialIndex = 0;
            this.mValues = Collections.singletonList(Integer.valueOf(i2));
        }
    }
}

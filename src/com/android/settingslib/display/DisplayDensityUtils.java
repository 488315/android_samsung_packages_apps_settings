package com.android.settingslib.display;

import android.content.Context;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.os.RemoteException;
import android.util.Log;
import android.util.MathUtils;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.WindowManagerGlobal;

import com.android.settings.R;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DisplayDensityUtils {
    public final int mCurrentIndex;
    public final int mDefaultDensityForDefaultDisplay;
    public final String[] mDefaultDisplayDensityEntries;
    public final int[] mDefaultDisplayDensityValues;
    public final DisplayManager mDisplayManager;
    public final Predicate mPredicate;
    public final Map mValuesPerDisplay;
    public static final int[] SUMMARIES_SMALLER = {R.string.screen_zoom_summary_small};
    public static final int[] SUMMARIES_LARGER = {
        R.string.screen_zoom_summary_large,
        R.string.screen_zoom_summary_very_large,
        R.string.screen_zoom_summary_extremely_large
    };
    public static final DisplayDensityUtils$$ExternalSyntheticLambda1 INTERNAL_ONLY =
            new DisplayDensityUtils$$ExternalSyntheticLambda1();

    public DisplayDensityUtils(Context context) {
        int i;
        Display[] displayArr;
        int i2;
        int i3;
        int i4;
        int i5;
        DisplayDensityUtils$$ExternalSyntheticLambda1
                displayDensityUtils$$ExternalSyntheticLambda1 = INTERNAL_ONLY;
        this.mValuesPerDisplay = new HashMap();
        int i6 = -1;
        this.mCurrentIndex = -1;
        this.mPredicate = displayDensityUtils$$ExternalSyntheticLambda1;
        DisplayManager displayManager =
                (DisplayManager) context.getSystemService(DisplayManager.class);
        this.mDisplayManager = displayManager;
        Display[] displays =
                displayManager.getDisplays(
                        "android.hardware.display.category.ALL_INCLUDING_DISABLED");
        int length = displays.length;
        int i7 = 0;
        while (i7 < length) {
            Display display = displays[i7];
            DisplayInfo displayInfo = new DisplayInfo();
            if (!display.getDisplayInfo(displayInfo)) {
                Log.w(
                        "DisplayDensityUtils",
                        "Cannot fetch display info for display " + display.getDisplayId());
            } else if (this.mPredicate.test(displayInfo)) {
                try {
                    i =
                            WindowManagerGlobal.getWindowManagerService()
                                    .getInitialDisplayDensity(display.getDisplayId());
                } catch (RemoteException unused) {
                    i = i6;
                }
                if (i <= 0) {
                    Log.w(
                            "DisplayDensityUtils",
                            "Cannot fetch default density for display " + display.getDisplayId());
                } else {
                    Resources resources = context.getResources();
                    int i8 = displayInfo.logicalDensityDpi;
                    float f = i;
                    float min =
                            Math.min(
                                    context.getResources()
                                            .getFraction(
                                                    R.fraction.display_density_max_scale, 1, 1),
                                    ((Math.min(displayInfo.logicalWidth, displayInfo.logicalHeight)
                                                            * 160)
                                                    / FileType.XLSX)
                                            / f);
                    float fraction =
                            context.getResources()
                                    .getFraction(R.fraction.display_density_min_scale, 1, 1);
                    float fraction2 =
                            context.getResources()
                                    .getFraction(
                                            R.fraction.display_density_min_scale_interval, 1, 1);
                    float f2 = min - 1.0f;
                    displayArr = displays;
                    int constrain = (int) MathUtils.constrain(f2 / fraction2, 0.0f, 3);
                    float f3 = 1.0f - fraction;
                    int constrain2 = (int) MathUtils.constrain(f3 / fraction2, 0.0f, 1);
                    int i9 = constrain2 + 1 + constrain;
                    String[] strArr = new String[i9];
                    int[] iArr = new int[i9];
                    i2 = length;
                    if (constrain2 > 0) {
                        float f4 = f3 / constrain2;
                        int i10 = constrain2 - 1;
                        i4 = 0;
                        i5 = -1;
                        while (i10 >= 0) {
                            int i11 = i7;
                            int i12 = ((int) ((1.0f - ((i10 + 1) * f4)) * f)) & (-2);
                            if (i8 == i12) {
                                i5 = i4;
                            }
                            strArr[i4] = resources.getString(SUMMARIES_SMALLER[i10]);
                            iArr[i4] = i12;
                            i4++;
                            i10--;
                            i7 = i11;
                            f4 = f4;
                        }
                        i3 = i7;
                    } else {
                        i3 = i7;
                        i4 = 0;
                        i5 = -1;
                    }
                    i5 = i8 == i ? i4 : i5;
                    iArr[i4] = i;
                    strArr[i4] = resources.getString(R.string.screen_zoom_summary_default);
                    int i13 = i4 + 1;
                    if (constrain > 0) {
                        float f5 = f2 / constrain;
                        int i14 = 0;
                        while (i14 < constrain) {
                            int i15 = i14 + 1;
                            int i16 = ((int) (((i15 * f5) + 1.0f) * f)) & (-2);
                            if (i8 == i16) {
                                i5 = i13;
                            }
                            iArr[i13] = i16;
                            strArr[i13] = resources.getString(SUMMARIES_LARGER[i14]);
                            i13++;
                            i14 = i15;
                        }
                    }
                    if (i5 >= 0) {
                        i13 = i5;
                    } else {
                        int i17 = i9 + 1;
                        iArr = Arrays.copyOf(iArr, i17);
                        iArr[i13] = i8;
                        strArr = (String[]) Arrays.copyOf(strArr, i17);
                        strArr[i13] =
                                resources.getString(
                                        R.string.screen_zoom_summary_custom, Integer.valueOf(i8));
                    }
                    if (display.getDisplayId() == 0) {
                        this.mDefaultDensityForDefaultDisplay = i;
                        this.mCurrentIndex = i13;
                        this.mDefaultDisplayDensityEntries = strArr;
                        this.mDefaultDisplayDensityValues = iArr;
                    }
                    ((HashMap) this.mValuesPerDisplay).put(displayInfo.uniqueId, iArr);
                    i7 = i3 + 1;
                    displays = displayArr;
                    length = i2;
                    i6 = -1;
                }
            } else if (display.getDisplayId() == 0) {
                throw new IllegalArgumentException(
                        "Predicate must not filter out the default display.");
            }
            displayArr = displays;
            i2 = length;
            i3 = i7;
            i7 = i3 + 1;
            displays = displayArr;
            length = i2;
            i6 = -1;
        }
    }
}

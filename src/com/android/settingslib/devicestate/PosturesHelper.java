package com.android.settingslib.devicestate;

import android.R;
import android.content.Context;

import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PosturesHelper {
    public final int[] foldedDeviceStates;
    public final int[] halfFoldedDeviceStates;
    public final int[] rearDisplayDeviceStates;
    public final int[] unfoldedDeviceStates;

    public PosturesHelper(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        int[] intArray =
                context.getResources()
                        .getIntArray(R.array.preloaded_freeform_multi_window_drawables);
        Intrinsics.checkNotNullExpressionValue(intArray, "getIntArray(...)");
        this.foldedDeviceStates = intArray;
        int[] intArray2 = context.getResources().getIntArray(R.array.sim_colors);
        Intrinsics.checkNotNullExpressionValue(intArray2, "getIntArray(...)");
        this.halfFoldedDeviceStates = intArray2;
        int[] intArray3 = context.getResources().getIntArray(17236280);
        Intrinsics.checkNotNullExpressionValue(intArray3, "getIntArray(...)");
        this.unfoldedDeviceStates = intArray3;
        int[] intArray4 = context.getResources().getIntArray(17236286);
        Intrinsics.checkNotNullExpressionValue(intArray4, "getIntArray(...)");
        this.rearDisplayDeviceStates = intArray4;
    }

    public final int deviceStateToPosture(int i) {
        if (ArraysKt___ArraysKt.contains(i, this.foldedDeviceStates)) {
            return 0;
        }
        if (ArraysKt___ArraysKt.contains(i, this.halfFoldedDeviceStates)) {
            return 1;
        }
        if (ArraysKt___ArraysKt.contains(i, this.unfoldedDeviceStates)) {
            return 2;
        }
        return ArraysKt___ArraysKt.contains(i, this.rearDisplayDeviceStates) ? 3 : -1;
    }
}

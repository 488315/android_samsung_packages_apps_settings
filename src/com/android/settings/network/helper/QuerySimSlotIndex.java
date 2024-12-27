package com.android.settings.network.helper;

import android.telephony.TelephonyManager;
import android.telephony.UiccPortInfo;
import android.telephony.UiccSlotInfo;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class QuerySimSlotIndex implements Callable {
    public boolean mDisabledSlotsIncluded;
    public boolean mOnlySlotWithSim;
    public TelephonyManager mTelephonyManager;

    @Override // java.util.concurrent.Callable
    public final Object call() {
        UiccSlotInfo[] uiccSlotsInfo = this.mTelephonyManager.getUiccSlotsInfo();
        if (uiccSlotsInfo == null) {
            return new AtomicIntegerArray(0);
        }
        final int i = this.mOnlySlotWithSim ? 0 : -1;
        return new AtomicIntegerArray(
                Arrays.stream(uiccSlotsInfo)
                        .flatMapToInt(
                                new Function() { // from class:
                                                 // com.android.settings.network.helper.QuerySimSlotIndex$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        final QuerySimSlotIndex querySimSlotIndex =
                                                QuerySimSlotIndex.this;
                                        UiccSlotInfo uiccSlotInfo = (UiccSlotInfo) obj;
                                        querySimSlotIndex.getClass();
                                        return uiccSlotInfo == null
                                                ? IntStream.of(-1)
                                                : uiccSlotInfo.getCardStateInfo() == 1
                                                        ? IntStream.of(-1)
                                                        : uiccSlotInfo.getPorts().stream()
                                                                .filter(
                                                                        new Predicate() { // from
                                                                                          // class:
                                                                                          // com.android.settings.network.helper.QuerySimSlotIndex$$ExternalSyntheticLambda2
                                                                            @Override // java.util.function.Predicate
                                                                            public final boolean
                                                                                    test(
                                                                                            Object
                                                                                                    obj2) {
                                                                                UiccPortInfo
                                                                                        uiccPortInfo =
                                                                                                (UiccPortInfo)
                                                                                                        obj2;
                                                                                if (QuerySimSlotIndex
                                                                                        .this
                                                                                        .mDisabledSlotsIncluded) {
                                                                                    return true;
                                                                                }
                                                                                if (uiccPortInfo
                                                                                        == null) {
                                                                                    return false;
                                                                                }
                                                                                return uiccPortInfo
                                                                                        .isActive();
                                                                            }
                                                                        })
                                                                .mapToInt(
                                                                        new QuerySimSlotIndex$$ExternalSyntheticLambda3());
                                    }
                                })
                        .filter(
                                new IntPredicate() { // from class:
                                                     // com.android.settings.network.helper.QuerySimSlotIndex$$ExternalSyntheticLambda1
                                    @Override // java.util.function.IntPredicate
                                    public final boolean test(int i2) {
                                        return i2 >= i;
                                    }
                                })
                        .toArray());
    }
}

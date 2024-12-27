package com.android.settings.network;

import android.telephony.UiccCardInfo;
import android.telephony.UiccPortInfo;
import android.telephony.UiccSlotInfo;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class UiccSlotUtil$$ExternalSyntheticLambda4 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ UiccSlotUtil$$ExternalSyntheticLambda4(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((UiccCardInfo) obj).isMultipleEnabledProfilesSupported();
            case 1:
                UiccSlotInfo uiccSlotInfo = (UiccSlotInfo) obj;
                return uiccSlotInfo != null
                        && uiccSlotInfo.isRemovable()
                        && !uiccSlotInfo.getIsEuicc()
                        && uiccSlotInfo.getPorts().stream()
                                .anyMatch(new UiccSlotUtil$$ExternalSyntheticLambda4(2))
                        && uiccSlotInfo.getCardStateInfo() == 2;
            default:
                return ((UiccPortInfo) obj).isActive();
        }
    }
}

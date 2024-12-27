package com.samsung.android.settings.analyzestorage.data.model;

import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class RecommendCardState {
    public final int removedCardPosition;
    public final ArrayList supportedCardList;
    public final int type;
    public final int updatedCardPosition;

    public RecommendCardState(int i, ArrayList supportedCardList, int i2, int i3, int i4) {
        i2 = (i4 & 4) != 0 ? -1 : i2;
        i3 = (i4 & 8) != 0 ? -1 : i3;
        Intrinsics.checkNotNullParameter(supportedCardList, "supportedCardList");
        this.type = i;
        this.supportedCardList = supportedCardList;
        this.removedCardPosition = i2;
        this.updatedCardPosition = i3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RecommendCardState)) {
            return false;
        }
        RecommendCardState recommendCardState = (RecommendCardState) obj;
        return this.type == recommendCardState.type
                && Intrinsics.areEqual(this.supportedCardList, recommendCardState.supportedCardList)
                && this.removedCardPosition == recommendCardState.removedCardPosition
                && this.updatedCardPosition == recommendCardState.updatedCardPosition;
    }

    public final int hashCode() {
        return Integer.hashCode(this.updatedCardPosition)
                + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(
                        this.removedCardPosition,
                        (this.supportedCardList.hashCode() + (Integer.hashCode(this.type) * 31))
                                * 31,
                        31);
    }

    public final String toString() {
        ArrayList arrayList = this.supportedCardList;
        StringBuilder sb = new StringBuilder("RecommendCardState(type=");
        sb.append(this.type);
        sb.append(", supportedCardList=");
        sb.append(arrayList);
        sb.append(", removedCardPosition=");
        sb.append(this.removedCardPosition);
        sb.append(", updatedCardPosition=");
        return Anchor$$ExternalSyntheticOutline0.m(sb, this.updatedCardPosition, ")");
    }
}

package com.samsung.android.settings.analyzestorage.data.model;

import androidx.compose.foundation.text.modifiers.TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0;

import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class RecommendCardInfo {
    public final String confirmButton;
    public final String descriptionCard;
    public final int recommendCardType;
    public final List subList;

    public RecommendCardInfo(String str, String str2, int i, List list) {
        this.recommendCardType = i;
        this.descriptionCard = str;
        this.subList = list;
        this.confirmButton = str2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RecommendCardInfo)) {
            return false;
        }
        RecommendCardInfo recommendCardInfo = (RecommendCardInfo) obj;
        return this.recommendCardType == recommendCardInfo.recommendCardType
                && Intrinsics.areEqual(this.descriptionCard, recommendCardInfo.descriptionCard)
                && Intrinsics.areEqual(this.subList, recommendCardInfo.subList)
                && Intrinsics.areEqual(this.confirmButton, recommendCardInfo.confirmButton);
    }

    public final int hashCode() {
        int m =
                TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0.m(
                        Integer.hashCode(this.recommendCardType) * 31, 31, this.descriptionCard);
        List list = this.subList;
        int hashCode = (m + (list == null ? 0 : list.hashCode())) * 31;
        String str = this.confirmButton;
        return hashCode + (str != null ? str.hashCode() : 0);
    }

    public final String toString() {
        return "RecommendCardInfo(recommendCardType="
                + this.recommendCardType
                + ", descriptionCard="
                + this.descriptionCard
                + ", subList="
                + this.subList
                + ", confirmButton="
                + this.confirmButton
                + ")";
    }
}

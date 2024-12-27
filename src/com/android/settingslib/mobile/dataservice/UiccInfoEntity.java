package com.android.settingslib.mobile.dataservice;

import android.text.TextUtils;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.modifiers.TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UiccInfoEntity {
    public final int cardId;
    public final int cardState;
    public final boolean isActive;
    public final boolean isEuicc;
    public final boolean isMultipleEnabledProfilesSupported;
    public final boolean isRemovable;
    public final int logicalSlotIndex;
    public final String physicalSlotIndex;
    public final int portIndex;
    public final String subId;

    public UiccInfoEntity(
            String str,
            String str2,
            int i,
            int i2,
            boolean z,
            boolean z2,
            int i3,
            boolean z3,
            boolean z4,
            int i4) {
        this.subId = str;
        this.physicalSlotIndex = str2;
        this.logicalSlotIndex = i;
        this.cardId = i2;
        this.isEuicc = z;
        this.isMultipleEnabledProfilesSupported = z2;
        this.cardState = i3;
        this.isRemovable = z3;
        this.isActive = z4;
        this.portIndex = i4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UiccInfoEntity)) {
            return false;
        }
        UiccInfoEntity uiccInfoEntity = (UiccInfoEntity) obj;
        return TextUtils.equals(this.subId, uiccInfoEntity.subId)
                && TextUtils.equals(this.physicalSlotIndex, uiccInfoEntity.physicalSlotIndex)
                && this.logicalSlotIndex == uiccInfoEntity.logicalSlotIndex
                && this.cardId == uiccInfoEntity.cardId
                && this.isEuicc == uiccInfoEntity.isEuicc
                && this.isMultipleEnabledProfilesSupported
                        == uiccInfoEntity.isMultipleEnabledProfilesSupported
                && this.cardState == uiccInfoEntity.cardState
                && this.isRemovable == uiccInfoEntity.isRemovable
                && this.isActive == uiccInfoEntity.isActive
                && this.portIndex == uiccInfoEntity.portIndex;
    }

    public final int hashCode() {
        return TransitionData$$ExternalSyntheticOutline0.m(
                        TransitionData$$ExternalSyntheticOutline0.m(
                                (TransitionData$$ExternalSyntheticOutline0.m(
                                                        TransitionData$$ExternalSyntheticOutline0.m(
                                                                (((TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0
                                                                                                        .m(
                                                                                                                TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0
                                                                                                                        .m(
                                                                                                                                FileType
                                                                                                                                        .TORRENT,
                                                                                                                                31,
                                                                                                                                this
                                                                                                                                        .subId),
                                                                                                                31,
                                                                                                                this
                                                                                                                        .physicalSlotIndex)
                                                                                                + this
                                                                                                        .logicalSlotIndex)
                                                                                        * 31)
                                                                                + this.cardId)
                                                                        * 31,
                                                                31,
                                                                this.isEuicc),
                                                        31,
                                                        this.isMultipleEnabledProfilesSupported)
                                                + this.cardState)
                                        * 31,
                                31,
                                this.isRemovable),
                        31,
                        this.isActive)
                + this.portIndex;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(" {UiccInfoEntity(subId = ");
        sb.append(this.subId);
        sb.append(", logicalSlotIndex = ");
        sb.append(this.physicalSlotIndex);
        sb.append(", logicalSlotIndex = ");
        sb.append(this.logicalSlotIndex);
        sb.append(", cardId = ");
        sb.append(this.cardId);
        sb.append(", isEuicc = ");
        sb.append(this.isEuicc);
        sb.append(", isMultipleEnabledProfilesSupported = ");
        sb.append(this.isMultipleEnabledProfilesSupported);
        sb.append(", cardState = ");
        sb.append(this.cardState);
        sb.append(", isRemovable = ");
        sb.append(this.isRemovable);
        sb.append(", isActive = ");
        sb.append(this.isActive);
        sb.append(", portIndex = ");
        return Anchor$$ExternalSyntheticOutline0.m(sb, this.portIndex, ")}");
    }
}

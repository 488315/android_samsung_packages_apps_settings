package com.android.settingslib.mobile.dataservice;

import android.text.TextUtils;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SubscriptionInfoEntity {
    public final int cardId;
    public final int carrierId;
    public final String carrierName;
    public final String countryIso;
    public final int dataRoaming;
    public final String displayName;
    public final String formattedPhoneNumber;
    public final String groupUUID;
    public final boolean isActiveDataSubscriptionId;
    public final boolean isActiveSubscriptionId;
    public final boolean isAvailableSubscription;
    public final boolean isDefaultSubscriptionSelection;
    public final boolean isEmbedded;
    public final boolean isFirstRemovableSubscription;
    public final boolean isOpportunistic;
    public final boolean isSubscriptionVisible;
    public final boolean isUsableSubscription;
    public final boolean isValidSubscription;
    public final String mcc;
    public final String mnc;
    public final int portIndex;
    public final int simSlotIndex;
    public final String subId;
    public final int subscriptionType;
    public final String uniqueName;

    public SubscriptionInfoEntity(
            String str,
            int i,
            int i2,
            String str2,
            String str3,
            int i3,
            String str4,
            String str5,
            String str6,
            boolean z,
            int i4,
            int i5,
            boolean z2,
            String str7,
            int i6,
            String str8,
            boolean z3,
            String str9,
            boolean z4,
            boolean z5,
            boolean z6,
            boolean z7,
            boolean z8,
            boolean z9,
            boolean z10) {
        this.subId = str;
        this.simSlotIndex = i;
        this.carrierId = i2;
        this.displayName = str2;
        this.carrierName = str3;
        this.dataRoaming = i3;
        this.mcc = str4;
        this.mnc = str5;
        this.countryIso = str6;
        this.isEmbedded = z;
        this.cardId = i4;
        this.portIndex = i5;
        this.isOpportunistic = z2;
        this.groupUUID = str7;
        this.subscriptionType = i6;
        this.uniqueName = str8;
        this.isSubscriptionVisible = z3;
        this.formattedPhoneNumber = str9;
        this.isFirstRemovableSubscription = z4;
        this.isDefaultSubscriptionSelection = z5;
        this.isValidSubscription = z6;
        this.isUsableSubscription = z7;
        this.isActiveSubscriptionId = z8;
        this.isAvailableSubscription = z9;
        this.isActiveDataSubscriptionId = z10;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SubscriptionInfoEntity)) {
            return false;
        }
        SubscriptionInfoEntity subscriptionInfoEntity = (SubscriptionInfoEntity) obj;
        return TextUtils.equals(this.subId, subscriptionInfoEntity.subId)
                && this.simSlotIndex == subscriptionInfoEntity.simSlotIndex
                && this.carrierId == subscriptionInfoEntity.carrierId
                && TextUtils.equals(this.displayName, subscriptionInfoEntity.displayName)
                && TextUtils.equals(this.carrierName, subscriptionInfoEntity.carrierName)
                && this.dataRoaming == subscriptionInfoEntity.dataRoaming
                && TextUtils.equals(this.mcc, subscriptionInfoEntity.mcc)
                && TextUtils.equals(this.mnc, subscriptionInfoEntity.mnc)
                && TextUtils.equals(this.countryIso, subscriptionInfoEntity.countryIso)
                && this.isEmbedded == subscriptionInfoEntity.isEmbedded
                && this.cardId == subscriptionInfoEntity.cardId
                && this.portIndex == subscriptionInfoEntity.portIndex
                && this.isOpportunistic == subscriptionInfoEntity.isOpportunistic
                && TextUtils.equals(this.groupUUID, subscriptionInfoEntity.groupUUID)
                && this.subscriptionType == subscriptionInfoEntity.subscriptionType
                && TextUtils.equals(this.uniqueName, subscriptionInfoEntity.uniqueName)
                && this.isSubscriptionVisible == subscriptionInfoEntity.isSubscriptionVisible
                && TextUtils.equals(
                        this.formattedPhoneNumber, subscriptionInfoEntity.formattedPhoneNumber)
                && this.isFirstRemovableSubscription
                        == subscriptionInfoEntity.isFirstRemovableSubscription
                && this.isDefaultSubscriptionSelection
                        == subscriptionInfoEntity.isDefaultSubscriptionSelection
                && this.isValidSubscription == subscriptionInfoEntity.isValidSubscription
                && this.isUsableSubscription == subscriptionInfoEntity.isUsableSubscription
                && this.isActiveSubscriptionId == subscriptionInfoEntity.isActiveSubscriptionId
                && this.isAvailableSubscription == subscriptionInfoEntity.isAvailableSubscription
                && this.isActiveDataSubscriptionId
                        == subscriptionInfoEntity.isActiveDataSubscriptionId;
    }

    public final int getSubId() {
        return Integer.valueOf(this.subId).intValue();
    }

    public final int hashCode() {
        return Objects.hash(
                this.subId,
                Integer.valueOf(this.simSlotIndex),
                Integer.valueOf(this.carrierId),
                this.displayName,
                this.carrierName,
                Integer.valueOf(this.dataRoaming),
                this.mcc,
                this.mnc,
                this.countryIso,
                Boolean.valueOf(this.isEmbedded),
                Integer.valueOf(this.cardId),
                Integer.valueOf(this.portIndex),
                Boolean.valueOf(this.isOpportunistic),
                this.groupUUID,
                Integer.valueOf(this.subscriptionType),
                this.uniqueName,
                Boolean.valueOf(this.isSubscriptionVisible),
                this.formattedPhoneNumber,
                Boolean.valueOf(this.isFirstRemovableSubscription),
                Boolean.valueOf(this.isDefaultSubscriptionSelection),
                Boolean.valueOf(this.isValidSubscription),
                Boolean.valueOf(this.isUsableSubscription),
                Boolean.valueOf(this.isActiveSubscriptionId),
                Boolean.valueOf(this.isAvailableSubscription),
                Boolean.valueOf(this.isActiveDataSubscriptionId));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(" {SubscriptionInfoEntity(subId = ");
        sb.append(this.subId);
        sb.append(", simSlotIndex = ");
        sb.append(this.simSlotIndex);
        sb.append(", carrierId = ");
        sb.append(this.carrierId);
        sb.append(", displayName = ");
        sb.append(this.displayName);
        sb.append(", carrierName = ");
        sb.append(this.carrierName);
        sb.append(", dataRoaming = ");
        sb.append(this.dataRoaming);
        sb.append(", mcc = ");
        sb.append(this.mcc);
        sb.append(", mnc = ");
        sb.append(this.mnc);
        sb.append(", countryIso = ");
        sb.append(this.countryIso);
        sb.append(", isEmbedded = ");
        sb.append(this.isEmbedded);
        sb.append(", cardId = ");
        sb.append(this.cardId);
        sb.append(", portIndex = ");
        sb.append(this.portIndex);
        sb.append(", isOpportunistic = ");
        sb.append(this.isOpportunistic);
        sb.append(", groupUUID = ");
        sb.append(this.groupUUID);
        sb.append(", subscriptionType = ");
        sb.append(this.subscriptionType);
        sb.append(", uniqueName = ");
        sb.append(this.uniqueName);
        sb.append(", isSubscriptionVisible = ");
        sb.append(this.isSubscriptionVisible);
        sb.append(", formattedPhoneNumber = ");
        sb.append(this.formattedPhoneNumber);
        sb.append(", isFirstRemovableSubscription = ");
        sb.append(this.isFirstRemovableSubscription);
        sb.append(", isDefaultSubscriptionSelection = ");
        sb.append(this.isDefaultSubscriptionSelection);
        sb.append(", isValidSubscription = ");
        sb.append(this.isValidSubscription);
        sb.append(", isUsableSubscription = ");
        sb.append(this.isUsableSubscription);
        sb.append(", isActiveSubscriptionId = ");
        sb.append(this.isActiveSubscriptionId);
        sb.append(", isAvailableSubscription = ");
        sb.append(this.isAvailableSubscription);
        sb.append(", isActiveDataSubscriptionId = ");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(
                sb, this.isActiveDataSubscriptionId, ")}");
    }
}

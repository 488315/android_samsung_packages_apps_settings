package com.android.settingslib.mobile.dataservice;

import android.text.TextUtils;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.modifiers.TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class MobileNetworkInfoEntity {
    public final boolean activeNetworkIsCellular;
    public final boolean isCdmaOptions;
    public final boolean isContactDiscoveryEnabled;
    public final boolean isContactDiscoveryVisible;
    public final boolean isDataRoamingEnabled;
    public final boolean isGsmOptions;
    public final boolean isMobileDataEnabled;
    public final boolean isTdscdmaSupported;
    public final boolean isWorldMode;
    public final boolean shouldDisplayNetworkSelectOptions;
    public final boolean showToggleForPhysicalSim;
    public final String subId;

    public MobileNetworkInfoEntity(
            String str,
            boolean z,
            boolean z2,
            boolean z3,
            boolean z4,
            boolean z5,
            boolean z6,
            boolean z7,
            boolean z8,
            boolean z9,
            boolean z10,
            boolean z11) {
        this.subId = str;
        this.isContactDiscoveryEnabled = z;
        this.isContactDiscoveryVisible = z2;
        this.isMobileDataEnabled = z3;
        this.isCdmaOptions = z4;
        this.isGsmOptions = z5;
        this.isWorldMode = z6;
        this.shouldDisplayNetworkSelectOptions = z7;
        this.isTdscdmaSupported = z8;
        this.activeNetworkIsCellular = z9;
        this.showToggleForPhysicalSim = z10;
        this.isDataRoamingEnabled = z11;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MobileNetworkInfoEntity)) {
            return false;
        }
        MobileNetworkInfoEntity mobileNetworkInfoEntity = (MobileNetworkInfoEntity) obj;
        return TextUtils.equals(this.subId, mobileNetworkInfoEntity.subId)
                && this.isContactDiscoveryEnabled
                        == mobileNetworkInfoEntity.isContactDiscoveryEnabled
                && this.isContactDiscoveryVisible
                        == mobileNetworkInfoEntity.isContactDiscoveryVisible
                && this.isMobileDataEnabled == mobileNetworkInfoEntity.isMobileDataEnabled
                && this.isCdmaOptions == mobileNetworkInfoEntity.isCdmaOptions
                && this.isGsmOptions == mobileNetworkInfoEntity.isGsmOptions
                && this.isWorldMode == mobileNetworkInfoEntity.isWorldMode
                && this.shouldDisplayNetworkSelectOptions
                        == mobileNetworkInfoEntity.shouldDisplayNetworkSelectOptions
                && this.isTdscdmaSupported == mobileNetworkInfoEntity.isTdscdmaSupported
                && this.activeNetworkIsCellular == mobileNetworkInfoEntity.activeNetworkIsCellular
                && this.showToggleForPhysicalSim == mobileNetworkInfoEntity.showToggleForPhysicalSim
                && this.isDataRoamingEnabled == mobileNetworkInfoEntity.isDataRoamingEnabled;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isDataRoamingEnabled)
                + TransitionData$$ExternalSyntheticOutline0.m(
                        TransitionData$$ExternalSyntheticOutline0.m(
                                TransitionData$$ExternalSyntheticOutline0.m(
                                        TransitionData$$ExternalSyntheticOutline0.m(
                                                TransitionData$$ExternalSyntheticOutline0.m(
                                                        TransitionData$$ExternalSyntheticOutline0.m(
                                                                TransitionData$$ExternalSyntheticOutline0
                                                                        .m(
                                                                                TransitionData$$ExternalSyntheticOutline0
                                                                                        .m(
                                                                                                TransitionData$$ExternalSyntheticOutline0
                                                                                                        .m(
                                                                                                                TransitionData$$ExternalSyntheticOutline0
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
                                                                                                                                        .isContactDiscoveryEnabled),
                                                                                                                31,
                                                                                                                this
                                                                                                                        .isContactDiscoveryVisible),
                                                                                                31,
                                                                                                this
                                                                                                        .isMobileDataEnabled),
                                                                                31,
                                                                                this.isCdmaOptions),
                                                                31,
                                                                this.isGsmOptions),
                                                        31,
                                                        this.isWorldMode),
                                                31,
                                                this.shouldDisplayNetworkSelectOptions),
                                        31,
                                        this.isTdscdmaSupported),
                                31,
                                this.activeNetworkIsCellular),
                        31,
                        this.showToggleForPhysicalSim);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(" {MobileNetworkInfoEntity(subId = ");
        sb.append(this.subId);
        sb.append(", isContactDiscoveryEnabled = ");
        sb.append(this.isContactDiscoveryEnabled);
        sb.append(", isContactDiscoveryVisible = ");
        sb.append(this.isContactDiscoveryVisible);
        sb.append(", isMobileDataEnabled = ");
        sb.append(this.isMobileDataEnabled);
        sb.append(", isCdmaOptions = ");
        sb.append(this.isCdmaOptions);
        sb.append(", isGsmOptions = ");
        sb.append(this.isGsmOptions);
        sb.append(", isWorldMode = ");
        sb.append(this.isWorldMode);
        sb.append(", shouldDisplayNetworkSelectOptions = ");
        sb.append(this.shouldDisplayNetworkSelectOptions);
        sb.append(", isTdscdmaSupported = ");
        sb.append(this.isTdscdmaSupported);
        sb.append(", activeNetworkIsCellular = ");
        sb.append(this.activeNetworkIsCellular);
        sb.append(", showToggleForPhysicalSim = ");
        sb.append(this.showToggleForPhysicalSim);
        sb.append(", isDataRoamingEnabled = ");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(
                sb, this.isDataRoamingEnabled, ")}");
    }
}

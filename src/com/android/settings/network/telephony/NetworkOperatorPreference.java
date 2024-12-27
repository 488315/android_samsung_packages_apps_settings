package com.android.settings.network.telephony;

import android.R;
import android.content.Context;
import android.telephony.CellIdentity;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityNr;
import android.telephony.CellIdentityTdscdma;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;

import androidx.apppickerview.features.applabel.AbstractAppLabelMapFactory$$ExternalSyntheticOutline0;
import androidx.preference.Preference;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\b\u0017\u0018\u00002\u00020\u0001¨\u0006\u0002"
        },
        d2 = {
            "Lcom/android/settings/network/telephony/NetworkOperatorPreference;",
            "Landroidx/preference/Preference;",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public class NetworkOperatorPreference extends Preference {
    public CellIdentity cellId;
    public CellInfo cellInfo;
    public final List forbiddenPlmns;
    public final boolean show4GForLTE;
    public final boolean useNewApi;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NetworkOperatorPreference(Context context, List forbiddenPlmns, boolean z) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(forbiddenPlmns, "forbiddenPlmns");
        this.forbiddenPlmns = forbiddenPlmns;
        this.show4GForLTE = z;
        this.useNewApi =
                context.getResources().getBoolean(R.bool.config_enableTelephonyTimeZoneDetection);
    }

    @Override // androidx.preference.Preference
    public final void setIcon(int i) {
        int i2;
        if (!this.useNewApi || i < 0 || i >= 5) {
            return;
        }
        Context context = getContext();
        CellIdentity cellIdentity = this.cellId;
        if (cellIdentity instanceof CellIdentityGsm) {
            i2 = com.android.settings.R.drawable.signal_strength_g;
        } else if (cellIdentity instanceof CellIdentityCdma) {
            i2 = com.android.settings.R.drawable.signal_strength_1x;
        } else {
            i2 =
                    cellIdentity instanceof CellIdentityWcdma
                            ? true
                            : cellIdentity instanceof CellIdentityTdscdma
                                    ? com.android.settings.R.drawable.signal_strength_3g
                                    : cellIdentity instanceof CellIdentityLte
                                            ? this.show4GForLTE
                                                    ? com.android.settings.R.drawable
                                                            .ic_signal_strength_4g
                                                    : com.android.settings.R.drawable
                                                            .signal_strength_lte
                                            : cellIdentity instanceof CellIdentityNr
                                                    ? com.android.settings.R.drawable
                                                            .signal_strength_5g
                                                    : 0;
        }
        setIcon(MobileNetworkUtils.getSignalStrengthIcon(context, i, 5, i2, false, false));
    }

    public final void updateCell(CellInfo cellInfo, CellIdentity cellIdentity) {
        String networkTitle;
        this.cellInfo = cellInfo;
        this.cellId = cellIdentity;
        if (cellIdentity == null
                || (networkTitle = CellInfoUtil.getNetworkTitle(cellIdentity)) == null) {
            return;
        }
        List list = this.forbiddenPlmns;
        CellIdentity cellIdentity2 = this.cellId;
        if (CollectionsKt___CollectionsKt.contains(
                list,
                cellIdentity2 != null ? CellInfoUtil.getOperatorNumeric(cellIdentity2) : null)) {
            networkTitle =
                    AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(
                            networkTitle,
                            " ",
                            getContext()
                                    .getString(com.android.settings.R.string.forbidden_network));
        }
        setTitle(networkTitle);
        CellInfo cellInfo2 = this.cellInfo;
        if (cellInfo2 == null) {
            return;
        }
        setIcon(cellInfo2.getCellSignalStrength().getLevel());
    }
}

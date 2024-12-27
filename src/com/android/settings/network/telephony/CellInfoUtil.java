package com.android.settings.network.telephony;

import android.telephony.CellIdentity;
import android.text.BidiFormatter;
import android.text.TextDirectionHeuristics;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class CellInfoUtil {
    public static final String getNetworkTitle(CellIdentity cellIdentity) {
        Intrinsics.checkNotNullParameter(cellIdentity, "<this>");
        CharSequence operatorAlphaLong = cellIdentity.getOperatorAlphaLong();
        if (operatorAlphaLong != null) {
            if (!(!StringsKt__StringsJVMKt.isBlank(operatorAlphaLong))) {
                operatorAlphaLong = null;
            }
            if (operatorAlphaLong != null) {
                return operatorAlphaLong.toString();
            }
        }
        CharSequence operatorAlphaShort = cellIdentity.getOperatorAlphaShort();
        if (operatorAlphaShort != null) {
            if (!(!StringsKt__StringsJVMKt.isBlank(operatorAlphaShort))) {
                operatorAlphaShort = null;
            }
            if (operatorAlphaShort != null) {
                return operatorAlphaShort.toString();
            }
        }
        String operatorNumeric = getOperatorNumeric(cellIdentity);
        if (operatorNumeric == null) {
            return null;
        }
        return BidiFormatter.getInstance()
                .unicodeWrap(operatorNumeric, TextDirectionHeuristics.LTR);
    }

    public static final String getOperatorNumeric(CellIdentity cellIdentity) {
        Intrinsics.checkNotNullParameter(cellIdentity, "<this>");
        String mccString = cellIdentity.getMccString();
        String mncString = cellIdentity.getMncString();
        if (mccString == null || mncString == null) {
            return null;
        }
        return mccString.concat(mncString);
    }
}

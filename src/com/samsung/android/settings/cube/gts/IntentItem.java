package com.samsung.android.settings.cube.gts;

import com.samsung.android.gtscell.data.GtsExpressionBuilder;
import com.samsung.android.gtscell.data.GtsExpressionRaw;
import com.samsung.android.settings.cube.ControlValue;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class IntentItem extends GtsItemWrapper {
    @Override // com.samsung.android.settings.cube.gts.GtsItemWrapper
    public final GtsExpressionRaw buildExpression(GtsExpressionBuilder gtsExpressionBuilder) {
        GtsExpressionBuilder title = gtsExpressionBuilder.setTitle(this.mControlData.mTitle);
        String str = this.mControlValue.mSummary;
        if (str == null) {
            str = null;
        }
        return title.setSubTitle(str).build();
    }

    @Override // com.samsung.android.settings.cube.gts.GtsItemWrapper
    public final boolean isValidControlValue(ControlValue controlValue) {
        return true;
    }
}

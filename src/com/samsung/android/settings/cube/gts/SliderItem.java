package com.samsung.android.settings.cube.gts;

import com.samsung.android.gtscell.data.GtsExpressionBuilder;
import com.samsung.android.gtscell.data.GtsExpressionRaw;
import com.samsung.android.settings.cube.ControlValue;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SliderItem extends GtsItemWrapper {
    @Override // com.samsung.android.settings.cube.gts.GtsItemWrapper
    public final GtsExpressionRaw buildExpression(GtsExpressionBuilder gtsExpressionBuilder) {
        return gtsExpressionBuilder
                .setTitle(this.mControlData.mTitle)
                .addSubExpression(
                        new GtsExpressionBuilder.ProgressExpressionBuilder()
                                .setProgress(Integer.parseInt(this.mControlValue.getValue()))
                                .setMin(this.mControlValue.getAttributeInt("min"))
                                .setMax(this.mControlValue.getAttributeInt("max")))
                .build();
    }

    @Override // com.samsung.android.settings.cube.gts.GtsItemWrapper
    public final boolean isValidControlValue(ControlValue controlValue) {
        return (controlValue.getValue() == null
                        || controlValue.getAttributeInt("min") == -1
                        || controlValue.getAttributeInt("max") == -1)
                ? false
                : true;
    }
}

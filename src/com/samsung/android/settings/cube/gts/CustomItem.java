package com.samsung.android.settings.cube.gts;

import android.text.TextUtils;

import com.samsung.android.gtscell.data.GtsExpressionBuilder;
import com.samsung.android.gtscell.data.GtsExpressionRaw;
import com.samsung.android.gtscell.data.GtsStoreContent;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlValue;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CustomItem extends GtsItemWrapper {
    @Override // com.samsung.android.settings.cube.gts.GtsItemWrapper
    public final GtsExpressionRaw buildExpression(GtsExpressionBuilder gtsExpressionBuilder) {
        ControlValue controlValue = this.mControlValue;
        String str = controlValue.mSummary;
        if (str == null) {
            str = null;
        }
        String str2 = controlValue.mStorePackage;
        if (!TextUtils.isEmpty(str2)) {
            gtsExpressionBuilder.addStoreContent(new GtsStoreContent.GalaxyStoreApp(str2));
        }
        GtsExpressionBuilder title = gtsExpressionBuilder.setTitle(this.mControlData.mTitle);
        if (TextUtils.isEmpty(str)) {
            str = ApnSettings.MVNO_NONE;
        }
        return title.setSubTitle(str).build();
    }

    @Override // com.samsung.android.settings.cube.gts.GtsItemWrapper
    public final boolean isValidControlValue(ControlValue controlValue) {
        return true;
    }
}

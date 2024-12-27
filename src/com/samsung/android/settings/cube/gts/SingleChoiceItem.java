package com.samsung.android.settings.cube.gts;

import android.text.TextUtils;
import android.util.Log;

import com.samsung.android.gtscell.data.GtsExpressionBuilder;
import com.samsung.android.gtscell.data.GtsExpressionRaw;
import com.samsung.android.settings.cube.ControlValue;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SingleChoiceItem extends GtsItemWrapper {
    @Override // com.samsung.android.settings.cube.gts.GtsItemWrapper
    public final GtsExpressionRaw buildExpression(GtsExpressionBuilder gtsExpressionBuilder) {
        String str;
        ArrayList attributeStringArrayList =
                this.mControlValue.getAttributeStringArrayList("entries");
        ArrayList attributeStringArrayList2 =
                this.mControlValue.getAttributeStringArrayList("entryValues");
        String value = this.mControlValue.getValue();
        if (attributeStringArrayList != null && attributeStringArrayList2 != null) {
            for (int i = 0; i < attributeStringArrayList2.size(); i++) {
                if (TextUtils.equals((CharSequence) attributeStringArrayList2.get(i), value)) {
                    Log.i(
                            GtsItemWrapper.TAG,
                            "SINGLE_CHOICE EntryName : "
                                    + ((String) attributeStringArrayList.get(i)));
                    str = (String) attributeStringArrayList.get(i);
                    break;
                }
            }
        }
        str = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return gtsExpressionBuilder.setTitle(this.mControlData.mTitle).setSubTitle(str).build();
    }

    @Override // com.samsung.android.settings.cube.gts.GtsItemWrapper
    public final boolean isValidControlValue(ControlValue controlValue) {
        return (controlValue.getAttributeStringArrayList("entries") == null
                        || controlValue.getAttributeStringArrayList("entryValues") == null)
                ? false
                : true;
    }
}

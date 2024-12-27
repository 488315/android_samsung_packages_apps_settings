package com.samsung.android.settings.cube.gts;

import android.os.Debug;
import android.util.Log;

import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;

import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.index.ControlData;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class GtsItemConverter {
    private static final boolean DEBUG = Debug.semIsProductDev();
    private static final String TAG = "GtsItemConverter";

    public static GtsItemWrapper convertData(ControlData controlData, ControlValue controlValue) {
        String value = controlValue.getValue();
        if (value == null) {
            Log.w(TAG, "convertData() : itemValue is null");
            return null;
        }
        if (DEBUG) {
            String str = TAG;
            Log.i(str, "convertData Title() : " + controlData.mTitle);
            Log.i(str, "convertData itemValue() : " + ((Object) value));
            TooltipPopup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("convertData data.getControlType() : "),
                    controlData.mControlType,
                    str);
        }
        try {
            GtsItemWrapper createItem = GtsItemWrapper.createItem(controlData.mControlType);
            if (createItem.isValidControlValue(controlValue)) {
                createItem.mControlData = controlData;
                createItem.mControlValue = controlValue;
                return createItem;
            }
            Log.w(TAG, "convertData() : " + controlValue.mKey);
            return null;
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "convertData() " + e.toString());
            return null;
        }
    }
}

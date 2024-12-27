package com.samsung.android.settings.cube.gts;

import android.os.Debug;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.samsung.android.gtscell.data.GtsEmbeddedItemBuilder;
import com.samsung.android.gtscell.data.GtsExpressionBuilder;
import com.samsung.android.gtscell.data.GtsExpressionRaw;
import com.samsung.android.gtscell.data.GtsItem;
import com.samsung.android.gtscell.data.GtsItemBuilder;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.index.ControlData;
import com.samsung.android.settings.gts.GtsResources;

import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class GtsItemWrapper {
    protected static final boolean DEBUG = Debug.semIsProductDev();
    protected static final int INVALID_MIN_MAX_ATTRIBUTE_VALUE = -1;
    protected static final String TAG = "GtsItemWrapper";
    protected ControlData mControlData;
    protected ControlValue mControlValue;

    public static GtsItemWrapper createItem(int i) {
        if (i == 0) {
            return new IntentItem();
        }
        if (i == 1) {
            return new SwitchItem();
        }
        if (i == 2) {
            return new SliderItem();
        }
        switch (i) {
            case 100:
                return new SliderTickMarkItem();
            case 101:
                return new SingleChoiceItem();
            case 102:
                return new CustomItem();
            default:
                throw new IllegalArgumentException(
                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "controlType : "));
        }
    }

    public abstract GtsExpressionRaw buildExpression(GtsExpressionBuilder gtsExpressionBuilder);

    public final GtsItem buildGtsItem(String str, GtsItemBuilder gtsItemBuilder) {
        Map map = GtsResources.mResourceMap;
        GtsResources.LazyHolder.INSTANCE.getClass();
        Map map2 = GtsResources.mMinVersionMap;
        gtsItemBuilder.setRevision(
                map2.containsKey(str) ? ((Integer) ((LinkedHashMap) map2).get(str)).intValue() : 1);
        if (this.mControlValue.mUri != null) {
            GtsEmbeddedItemBuilder gtsEmbeddedItemBuilder = new GtsEmbeddedItemBuilder();
            gtsEmbeddedItemBuilder.setUri("uri", this.mControlValue.mUri);
            gtsItemBuilder.addEmbeddedItem(gtsEmbeddedItemBuilder.build());
        }
        return gtsItemBuilder.setText(this.mControlValue.toString()).build();
    }

    public final String getKey() {
        return this.mControlData.mKey;
    }

    public abstract boolean isValidControlValue(ControlValue controlValue);
}

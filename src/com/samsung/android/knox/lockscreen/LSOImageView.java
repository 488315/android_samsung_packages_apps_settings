package com.samsung.android.knox.lockscreen;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LSOImageView extends ImageView {
    public LSOImageView(Context context, LSOItemImage lSOItemImage) {
        super(context);
        Bitmap maxBitmap;
        int maxImageSize = LSOUtils.getMaxImageSize(context);
        int maxImageSize2 = LSOUtils.getMaxImageSize(context);
        if (lSOItemImage.isFieldUpdated(64)) {
            LSOAttributeSet attrs = lSOItemImage.getAttrs();
            maxImageSize =
                    attrs.containsKey(LSOAttrConst.ATTR_MAX_WIDTH)
                            ? attrs.getAsInteger(LSOAttrConst.ATTR_MAX_WIDTH).intValue()
                            : maxImageSize;
            if (attrs.containsKey(LSOAttrConst.ATTR_MAX_HEIGHT)) {
                maxImageSize2 = attrs.getAsInteger(LSOAttrConst.ATTR_MAX_HEIGHT).intValue();
            }
        }
        if (lSOItemImage.isFieldUpdated(128)
                && (maxBitmap =
                                LSOUtils.getMaxBitmap(
                                        lSOItemImage.getImagePath(), maxImageSize, maxImageSize2))
                        != null) {
            setImageBitmap(maxBitmap);
        }
        if (lSOItemImage.isFieldUpdated(512)) {
            setScaleType(lSOItemImage.getScaleType());
        } else {
            setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }
    }
}

package com.google.android.flexbox;

import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public interface FlexItem extends Parcelable {
    int getAlignSelf();

    float getFlexBasisPercent();

    float getFlexGrow();

    float getFlexShrink();

    int getHeight();

    int getMarginBottom();

    int getMarginLeft();

    int getMarginRight();

    int getMarginTop();

    int getMaxHeight();

    int getMaxWidth();

    int getMinHeight();

    int getMinWidth();

    int getOrder();

    int getWidth();

    boolean isWrapBefore();

    void setMinHeight(int i);

    void setMinWidth(int i);
}

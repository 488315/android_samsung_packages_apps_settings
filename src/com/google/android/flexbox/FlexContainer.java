package com.google.android.flexbox;

import android.view.View;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public interface FlexContainer {
    int getAlignContent();

    int getAlignItems();

    int getChildHeightMeasureSpec(int i, int i2, int i3);

    int getChildWidthMeasureSpec(int i, int i2, int i3);

    int getDecorationLengthCrossAxis(View view);

    int getDecorationLengthMainAxis(View view, int i, int i2);

    int getFlexDirection();

    View getFlexItemAt(int i);

    int getFlexItemCount();

    List getFlexLinesInternal();

    int getFlexWrap();

    int getLargestMainSize();

    int getMaxLine();

    int getPaddingBottom();

    int getPaddingEnd();

    int getPaddingLeft();

    int getPaddingRight();

    int getPaddingStart();

    int getPaddingTop();

    View getReorderedFlexItemAt(int i);

    int getSumOfCrossSize();

    boolean isMainAxisDirectionHorizontal();

    void onNewFlexItemAdded(View view, int i, int i2, FlexLine flexLine);

    void onNewFlexLineAdded(FlexLine flexLine);

    void setFlexLines(List list);

    void updateViewCache(View view, int i);
}

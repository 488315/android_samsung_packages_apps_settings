package com.samsung.android.settings.accessibility.base.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EditAppBar extends LinearLayout {
    public EditAppBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Context context2 = getContext();
        Resources resources = context2.getResources();
        setBackground(new ColorDrawable(context2.getColor(R.color.rounded_corner_color)));
        setPaddingRelative(
                resources.getDimensionPixelSize(R.dimen.list_item_start_padding),
                0,
                resources.getDimensionPixelSize(R.dimen.list_item_end_padding),
                0);
        LinearLayout.inflate(getContext(), R.layout.accessibility_edit_app_bar, this);
    }
}

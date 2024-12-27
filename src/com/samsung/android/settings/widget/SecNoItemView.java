package com.samsung.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecNoItemView extends LinearLayout {
    public TextView mMainText;

    public SecNoItemView(Context context) {
        super(context);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mMainText = (TextView) findViewById(R.id.no_item_main_text);
    }

    public SecNoItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SecNoItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}

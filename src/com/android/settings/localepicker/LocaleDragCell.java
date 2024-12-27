package com.android.settings.localepicker;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
class LocaleDragCell extends LinearLayout {
    public CheckBox mCheckbox;
    public ImageView mDragHandle;
    public TextView mLabel;
    public TextView mLocalized;
    public TextView mMiniLabel;
    public int mPosition;
    public ImageView mSelectBox;

    public LocaleDragCell(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mLabel = (TextView) findViewById(R.id.label);
        this.mLocalized = (TextView) findViewById(R.id.l10nWarn);
        this.mMiniLabel = (TextView) findViewById(R.id.miniLabel);
        this.mCheckbox = (CheckBox) findViewById(R.id.checkbox);
        this.mDragHandle = (ImageView) findViewById(R.id.dragHandle);
        this.mSelectBox = (ImageView) findViewById(R.id.select_box);
    }
}

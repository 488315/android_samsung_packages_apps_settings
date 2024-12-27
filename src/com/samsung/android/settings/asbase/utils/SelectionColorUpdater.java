package com.samsung.android.settings.asbase.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SelectionColorUpdater {
    public static final void updateColor(View view, boolean z) {
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            imageView.setColorFilter(
                    imageView.getContext().getColor(SelectionColorSetType.Icon.getColorId(z)));
        } else if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setTextColor(
                    textView.getContext().getColor(SelectionColorSetType.Text.getColorId(z)));
        }
    }
}

package com.android.settings.accessibility;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ColorPreference extends ListDialogPreference {
    public ColorPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDialogLayoutResId = R.layout.grid_picker_dialog;
        this.mListItemLayout = R.layout.color_picker_item;
    }

    @Override // com.android.settings.accessibility.ListDialogPreference
    public final CharSequence getTitleAt(int i) {
        CharSequence titleAt = super.getTitleAt(i);
        if (titleAt != null) {
            return titleAt;
        }
        int i2 = this.mEntryValues[i];
        return getContext()
                .getString(
                        R.string.color_custom,
                        Integer.valueOf(Color.red(i2)),
                        Integer.valueOf(Color.green(i2)),
                        Integer.valueOf(Color.blue(i2)));
    }

    @Override // com.android.settings.accessibility.ListDialogPreference
    public final void onBindListItem(View view, int i) {
        int i2 = this.mEntryValues[i];
        int alpha = Color.alpha(i2);
        ImageView imageView = (ImageView) view.findViewById(R.id.color_swatch);
        if (alpha < 255) {
            imageView.setBackgroundResource(R.drawable.transparency_tileable);
        } else {
            imageView.setBackground(null);
        }
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof ColorDrawable) {
            ((ColorDrawable) drawable).setColor(i2);
        } else {
            imageView.setImageDrawable(new ColorDrawable(i2));
        }
        CharSequence titleAt = getTitleAt(i);
        if (titleAt != null) {
            ((TextView) view.findViewById(R.id.summary)).setText(titleAt);
        }
    }

    @Override // androidx.preference.Preference
    public final boolean shouldDisableDependents$1() {
        return Color.alpha(this.mValue) == 0 || super.shouldDisableDependents$1();
    }
}

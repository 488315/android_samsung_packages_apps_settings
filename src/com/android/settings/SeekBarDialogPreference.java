package com.android.settings;

import android.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.android.settingslib.CustomDialogPreferenceCompat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class SeekBarDialogPreference extends CustomDialogPreferenceCompat {
    public final Drawable mMyIcon;

    public SeekBarDialogPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDialogLayoutResId = R.layout.preference_dialog_seekbar_material;
        this.mPositiveButtonText = getContext().getString(R.string.ok);
        this.mNegativeButtonText = getContext().getString(R.string.cancel);
        this.mMyIcon = this.mDialogIcon;
        this.mDialogIcon = null;
    }

    @Override // com.android.settingslib.CustomDialogPreferenceCompat
    public void onBindDialogView(View view) {
        ImageView imageView = (ImageView) view.findViewById(R.id.icon);
        Drawable drawable = this.mMyIcon;
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        } else {
            imageView.setVisibility(8);
        }
    }
}

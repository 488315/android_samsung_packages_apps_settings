package com.android.settings.accessibility;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.android.internal.widget.SubtitleView;
import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PresetPreference extends ListDialogPreference {
    public final CaptionHelper mCaptionHelper;

    public PresetPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCaptionHelper = new CaptionHelper(context);
        this.mDialogLayoutResId = R.layout.grid_picker_dialog;
        this.mListItemLayout = R.layout.preset_picker_item;
    }

    @Override // com.android.settings.accessibility.ListDialogPreference
    public final void onBindListItem(View view, int i) {
        view.findViewById(R.id.preview_viewport);
        SubtitleView findViewById = view.findViewById(R.id.preview);
        int i2 = this.mEntryValues[i];
        CaptionHelper captionHelper = this.mCaptionHelper;
        captionHelper.getClass();
        findViewById.setStyle(i2);
        findViewById.setTextSize(
                captionHelper.mCaptioningManager.getFontScale()
                        * findViewById
                                .getContext()
                                .getResources()
                                .getDimensionPixelSize(
                                        R.dimen.captioning_preview_caption_font_size));
        findViewById.setTextSize(getContext().getResources().getDisplayMetrics().density * 32.0f);
        CharSequence titleAt = getTitleAt(i);
        if (titleAt != null) {
            ((TextView) view.findViewById(R.id.summary)).setText(titleAt);
        }
    }

    @Override // androidx.preference.Preference
    public final boolean shouldDisableDependents$1() {
        return this.mValue != -1 || super.shouldDisableDependents$1();
    }
}

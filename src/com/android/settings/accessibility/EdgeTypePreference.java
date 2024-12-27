package com.android.settings.accessibility;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.android.internal.widget.SubtitleView;
import com.android.settings.R;

import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class EdgeTypePreference extends ListDialogPreference {
    public EdgeTypePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Resources resources = context.getResources();
        setValues(resources.getIntArray(R.array.captioning_edge_type_selector_values));
        this.mEntryTitles = resources.getStringArray(R.array.captioning_edge_type_selector_titles);
        this.mDialogLayoutResId = R.layout.grid_picker_dialog;
        this.mListItemLayout = R.layout.preset_picker_item;
    }

    @Override // com.android.settings.accessibility.ListDialogPreference
    public final void onBindListItem(View view, int i) {
        SubtitleView findViewById = view.findViewById(R.id.preview);
        findViewById.setForegroundColor(-1);
        findViewById.setBackgroundColor(0);
        findViewById.setTextSize(getContext().getResources().getDisplayMetrics().density * 32.0f);
        findViewById.setEdgeType(this.mEntryValues[i]);
        findViewById.setEdgeColor(EmergencyPhoneWidget.BG_COLOR);
        CharSequence titleAt = getTitleAt(i);
        if (titleAt != null) {
            ((TextView) view.findViewById(R.id.summary)).setText(titleAt);
        }
    }

    @Override // androidx.preference.Preference
    public final boolean shouldDisableDependents$1() {
        return this.mValue == 0 || super.shouldDisableDependents$1();
    }
}

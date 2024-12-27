package com.android.settings.slices;

import android.content.Context;
import android.util.AttributeSet;

import androidx.slice.widget.SliceView;

import com.android.settings.R;
import com.android.settingslib.widget.LayoutPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SlicePreference extends LayoutPreference {
    public SliceView mSliceView;

    public SlicePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr.slicePreferenceStyle);
        init$18();
    }

    public final void init$18() {
        SliceView sliceView = (SliceView) this.mRootView.findViewById(R.id.slice_view);
        this.mSliceView = sliceView;
        sliceView.setShowTitleItems();
        this.mSliceView.setScrollable();
        this.mSliceView.setVisibility(8);
    }

    public SlicePreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init$18();
    }
}

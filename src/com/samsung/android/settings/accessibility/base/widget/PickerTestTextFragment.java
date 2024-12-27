package com.samsung.android.settings.accessibility.base.widget;

import android.view.View;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class PickerTestTextFragment extends PickerFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public int getMetricsCategory() {
        return 0;
    }

    public final void setEditTextOnKeyListener(View.OnKeyListener onKeyListener) {
        this.mTestEdit.setOnKeyListener(onKeyListener);
        this.mTestEditLayout.setVisibility(0);
    }
}

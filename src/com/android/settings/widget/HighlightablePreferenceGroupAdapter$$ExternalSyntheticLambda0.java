package com.android.settings.widget;

import android.text.TextUtils;
import android.view.View;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class HighlightablePreferenceGroupAdapter$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ HighlightablePreferenceGroupAdapter f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ HighlightablePreferenceGroupAdapter$$ExternalSyntheticLambda0(
            HighlightablePreferenceGroupAdapter highlightablePreferenceGroupAdapter,
            Object obj,
            int i) {
        this.$r8$classId = i;
        this.f$0 = highlightablePreferenceGroupAdapter;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                HighlightablePreferenceGroupAdapter highlightablePreferenceGroupAdapter = this.f$0;
                PreferenceViewHolder preferenceViewHolder = (PreferenceViewHolder) this.f$1;
                highlightablePreferenceGroupAdapter.mHighlightPosition = -1;
                highlightablePreferenceGroupAdapter.removeHighlightBackground(
                        preferenceViewHolder, true);
                break;
            default:
                HighlightablePreferenceGroupAdapter highlightablePreferenceGroupAdapter2 = this.f$0;
                View view = (View) this.f$1;
                if (highlightablePreferenceGroupAdapter2.mHighlightPosition != -1
                        && view.getParent() != null) {
                    String str = (String) view.getTag(R.id.preference_key);
                    if (!TextUtils.isEmpty(str)
                            && str.equals(highlightablePreferenceGroupAdapter2.mHighlightKey)) {
                        if (view.getBackground() != null) {
                            view.getBackground()
                                    .setHotspot(view.getWidth() / 2, view.getHeight() / 2);
                        }
                        view.setPressed(true);
                        highlightablePreferenceGroupAdapter2.mHighlightPosition = -1;
                        break;
                    }
                }
                break;
        }
    }
}

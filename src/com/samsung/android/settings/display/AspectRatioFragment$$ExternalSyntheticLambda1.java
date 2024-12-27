package com.samsung.android.settings.display;

import android.view.View;

import androidx.apppickerview.widget.AppPickerView;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class AspectRatioFragment$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AspectRatioFragment$$ExternalSyntheticLambda1(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                AspectRatioFragment aspectRatioFragment = (AspectRatioFragment) obj;
                AppPickerView.ViewHolder viewHolder = aspectRatioFragment.mViewHolder;
                if (viewHolder != null) {
                    View view = viewHolder.itemView;
                    if (aspectRatioFragment.mHighlightPosition != -1 && view.getParent() != null) {
                        if (view.getBackground() != null) {
                            view.getBackground()
                                    .setHotspot(view.getWidth() / 2, view.getHeight() / 2);
                        }
                        view.setPressed(true);
                        aspectRatioFragment.mHighlightPosition = -1;
                        aspectRatioFragment.mHighlightKey = ApnSettings.MVNO_NONE;
                        aspectRatioFragment.mHighlightRequest = false;
                        aspectRatioFragment.mViewHolder = null;
                    }
                    view.post(new AspectRatioFragment$$ExternalSyntheticLambda1(1, view));
                    break;
                }
                break;
            default:
                ((View) obj).setPressed(false);
                break;
        }
    }
}

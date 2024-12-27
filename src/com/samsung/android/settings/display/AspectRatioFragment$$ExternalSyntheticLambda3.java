package com.samsung.android.settings.display;

import android.view.View;
import android.view.ViewGroup;

import androidx.apppickerview.widget.AppPickerView;
import androidx.apppickerview.widget.AppPickerView.AnonymousClass5;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class AspectRatioFragment$$ExternalSyntheticLambda3
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ AspectRatioFragment$$ExternalSyntheticLambda3(
            int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                View view = (View) this.f$0;
                view.setLayoutParams((ViewGroup.LayoutParams) this.f$1);
                view.requestLayout();
                break;
            default:
                AspectRatioFragment.RequestHighlight requestHighlight =
                        (AspectRatioFragment.RequestHighlight) this.f$0;
                Integer num = (Integer) this.f$1;
                AspectRatioFragment aspectRatioFragment = requestHighlight.this$0;
                aspectRatioFragment.mHighlightRequest = true;
                AppPickerView appPickerView = aspectRatioFragment.mAppPickerView;
                int intValue = num.intValue();
                appPickerView.getClass();
                appPickerView.post(appPickerView.new AnonymousClass5(intValue));
                break;
        }
    }
}

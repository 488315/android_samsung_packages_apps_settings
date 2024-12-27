package com.google.android.material.appbar.model.view;

import android.view.View;

import com.google.android.material.appbar.model.AppBarModel;
import com.google.android.material.appbar.model.ButtonModel;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class SuggestAppBarView$$ExternalSyntheticLambda0
        implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ SuggestAppBarView f$1;

    public /* synthetic */ SuggestAppBarView$$ExternalSyntheticLambda0(
            Object obj, SuggestAppBarView suggestAppBarView, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = suggestAppBarView;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                SuggestAppBarView.generateButton$lambda$9$lambda$8(
                        (ButtonModel) this.f$0, this.f$1, view);
                break;
            default:
                SuggestAppBarView.setCloseClickListener$lambda$4$lambda$3(
                        (AppBarModel.OnClickListener) this.f$0, this.f$1, view);
                break;
        }
    }
}

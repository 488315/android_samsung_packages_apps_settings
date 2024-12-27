package com.android.settings.panel;

import android.view.View;

import androidx.fragment.app.FragmentActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class PanelFragment$$ExternalSyntheticLambda1
        implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PanelFragment f$0;

    public /* synthetic */ PanelFragment$$ExternalSyntheticLambda1(
            PanelFragment panelFragment, int i) {
        this.$r8$classId = i;
        this.f$0 = panelFragment;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        PanelFragment panelFragment = this.f$0;
        switch (i) {
            case 0:
                panelFragment.mPanelClosedKey = "done";
                panelFragment.getActivity().finish();
                break;
            case 1:
                FragmentActivity activity = panelFragment.getActivity();
                panelFragment.mPanel.getClass();
                activity.startActivity(null);
                break;
            default:
                panelFragment.mPanelClosedKey = "see_more";
                FragmentActivity activity2 = panelFragment.getActivity();
                panelFragment.mPanel.getClass();
                activity2.startActivityForResult(panelFragment.mPanel.getSeeMoreIntent(), 0);
                activity2.finish();
                break;
        }
    }
}

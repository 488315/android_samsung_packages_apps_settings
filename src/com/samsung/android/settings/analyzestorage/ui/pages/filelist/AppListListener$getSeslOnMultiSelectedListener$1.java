package com.samsung.android.settings.analyzestorage.ui.pages.filelist;

import android.graphics.Point;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.samsung.android.settings.analyzestorage.ui.widget.MyFilesRecyclerView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AppListListener$getSeslOnMultiSelectedListener$1 {
    public final AppListListener.MultiSelectedInfo info;
    public final /* synthetic */ AppListListener this$0;

    public AppListListener$getSeslOnMultiSelectedListener$1(AppListListener appListListener) {
        this.this$0 = appListListener;
        AppListListener.MultiSelectedInfo multiSelectedInfo =
                new AppListListener.MultiSelectedInfo();
        multiSelectedInfo.startPoint = new Point();
        multiSelectedInfo.stopPoint = new Point();
        this.info = multiSelectedInfo;
    }

    public final void onMultiSelectStart(int i, int i2) {
        View seslFindNearChildViewUnder;
        AppListListener appListListener = this.this$0;
        appListListener.getClass();
        AppListListener.MultiSelectedInfo multiSelectedInfo = this.info;
        ((Point) multiSelectedInfo.startPoint).set(i, i2);
        float f = i;
        float f2 = i2;
        MyFilesRecyclerView myFilesRecyclerView = appListListener.recyclerView;
        View findChildViewUnder = myFilesRecyclerView.findChildViewUnder(f, f2);
        int childLayoutPosition =
                findChildViewUnder != null
                        ? RecyclerView.getChildLayoutPosition(findChildViewUnder)
                        : -1;
        multiSelectedInfo.selectionStartPosition = childLayoutPosition;
        if (childLayoutPosition != -1
                || (seslFindNearChildViewUnder =
                                myFilesRecyclerView.seslFindNearChildViewUnder(f, f2))
                        == null) {
            return;
        }
        multiSelectedInfo.selectionStartPosition =
                RecyclerView.getChildLayoutPosition(seslFindNearChildViewUnder);
    }
}

package com.samsung.android.settings.analyzestorage.ui.pages.filelist;

import android.view.View;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ListItemHandler;
import com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage.AsSubListAdapter;
import com.samsung.android.settings.analyzestorage.ui.widget.MyFilesRecyclerView;
import com.samsung.android.settings.analyzestorage.ui.widget.viewholder.SubAppListViewHolder;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AppListListener {
    public final AsSubListAdapter adapter;
    public boolean isDexMousePressed;
    public final ListItemHandler listItemHandler;
    public final MyFilesRecyclerView recyclerView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MultiSelectedInfo
            implements RecyclerView.SeslLongPressMultiSelectionListener {
        public int selectionStartPosition;
        public Object startPoint;
        public Object stopPoint;

        @Override // androidx.recyclerview.widget.RecyclerView.SeslLongPressMultiSelectionListener
        public void onItemSelected(int i) {
            boolean contains = ((ArrayList) ((List) this.startPoint)).contains(Integer.valueOf(i));
            AppListListener appListListener = (AppListListener) this.stopPoint;
            if (contains) {
                ((ArrayList) ((List) this.startPoint)).remove(Integer.valueOf(i));
                AppListListener.access$itemChecked(appListListener, i, false);
            } else {
                ((ArrayList) ((List) this.startPoint)).add(Integer.valueOf(i));
                AppListListener.access$itemChecked(appListListener, i, true);
            }
            if (this.selectionStartPosition > -1) {
                this.selectionStartPosition = -1;
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.SeslLongPressMultiSelectionListener
        public void onLongPressMultiSelectionEnded() {
            ((ArrayList) ((List) this.startPoint)).clear();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.SeslLongPressMultiSelectionListener
        public void onLongPressMultiSelectionStarted(int i, int i2) {
            int i3;
            AppListListener appListListener = (AppListListener) this.stopPoint;
            View findChildViewUnder = appListListener.recyclerView.findChildViewUnder(i, i2);
            if (findChildViewUnder != null) {
                appListListener.recyclerView.getClass();
                i3 = RecyclerView.getChildLayoutPosition(findChildViewUnder);
            } else {
                i3 = -1;
            }
            this.selectionStartPosition = i3;
            if (i3 != -1) {
                AppListListener.access$itemChecked(appListListener, i3, true);
            }
        }
    }

    public AppListListener(
            MyFilesRecyclerView myFilesRecyclerView,
            AsSubListAdapter asSubListAdapter,
            AppListController controller) {
        Intrinsics.checkNotNullParameter(controller, "controller");
        this.recyclerView = myFilesRecyclerView;
        this.adapter = asSubListAdapter;
        this.listItemHandler = controller.mListItemHandler;
    }

    public static final void access$itemChecked(AppListListener appListListener, int i, boolean z) {
        CheckBox checkBox;
        appListListener.listItemHandler.setItemChecked(i, z);
        RecyclerView.ViewHolder findViewHolderForAdapterPosition =
                appListListener.recyclerView.findViewHolderForAdapterPosition(i);
        SubAppListViewHolder subAppListViewHolder =
                findViewHolderForAdapterPosition instanceof SubAppListViewHolder
                        ? (SubAppListViewHolder) findViewHolderForAdapterPosition
                        : null;
        if (subAppListViewHolder == null || (checkBox = subAppListViewHolder.checkBox) == null) {
            return;
        }
        checkBox.setChecked(z);
    }
}

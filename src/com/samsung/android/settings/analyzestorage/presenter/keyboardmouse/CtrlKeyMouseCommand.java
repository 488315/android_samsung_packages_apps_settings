package com.samsung.android.settings.analyzestorage.presenter.keyboardmouse;

import android.view.KeyEvent;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController;
import com.samsung.android.settings.analyzestorage.presenter.controllers.FileListController;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ListItemHandler;
import com.samsung.android.settings.analyzestorage.presenter.page.PageType;
import com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage.AsSubListAdapter;
import com.samsung.android.settings.analyzestorage.ui.widget.MyFilesRecyclerView;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class CtrlKeyMouseCommand implements KeyMouseCommand {
    @Override // com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.KeyMouseCommand
    public final boolean onKeyDown(
            EventContext eventContext, FragmentActivity fragmentActivity, KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        boolean z = true;
        if (keyCode == 29) {
            if (eventContext != null) {
                AbsPageController absPageController = eventContext.mController;
                ListItemHandler listItemHandler = absPageController.mListItemHandler;
                PageType pageType = absPageController.mPageInfo.mPageType;
                pageType.getClass();
                if ((pageType == PageType.ANALYZE_STORAGE_UNUSED_APPS
                                || pageType == PageType.ANALYZE_STORAGE_APP_CACHE)
                        && listItemHandler.getItemCount() > 0) {
                    listItemHandler.setAllItemChecked(true);
                    RecyclerView recyclerView = eventContext.mRecycleView;
                    eventContext.mRecycleView.requestChildFocus(
                            recyclerView.getChildAt(recyclerView.getChildCount() - 1), null);
                    AsSubListAdapter asSubListAdapter = eventContext.mListBehavior.adapter;
                    if (asSubListAdapter != null) {
                        asSubListAdapter.notifyDataSetChanged();
                    }
                }
            }
            z = false;
        } else {
            if (keyCode != 51) {
                return false;
            }
            if (fragmentActivity != null) {
                fragmentActivity.finishAffinity();
            }
            z = false;
        }
        return z;
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.KeyMouseCommand
    public final void onMouseDown(AbsPageController absPageController, int i) {
        if (i <= -1) {
            Log.e("CtrlKeyMouseCommand", "onMouseDown position is " + i);
        } else {
            if (absPageController == null || !absPageController.isFileListController()) {
                return;
            }
            ((FileListController) absPageController)
                    .mListItemHandler.setItemChecked(
                            i, !((ArrayList) r1.mCheckedItemList).contains(r1.getItemAt(i)));
        }
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.KeyMouseCommand
    public final boolean onMouseScroll(KeyMouseCommand.IMouseScroll iMouseScroll) {
        return ((MyFilesRecyclerView) iMouseScroll).getLayoutManager() instanceof GridLayoutManager;
    }
}

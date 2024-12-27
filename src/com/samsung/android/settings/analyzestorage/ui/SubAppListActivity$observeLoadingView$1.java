package com.samsung.android.settings.analyzestorage.ui;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;

import androidx.lifecycle.Observer;

import com.android.settings.R;

import com.samsung.android.settings.analyzestorage.ui.menu.MenuIdType;

import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SubAppListActivity$observeLoadingView$1 implements Observer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object this$0;

    public /* synthetic */ SubAppListActivity$observeLoadingView$1(int i, Object obj) {
        this.$r8$classId = i;
        this.this$0 = obj;
    }

    @Override // androidx.lifecycle.Observer
    public final void onChanged(Object obj) {
        MenuItem findItem;
        switch (this.$r8$classId) {
            case 0:
                Boolean bool = (Boolean) obj;
                SubAppListActivity subAppListActivity = (SubAppListActivity) this.this$0;
                ViewStub viewStub =
                        (ViewStub) subAppListActivity.findViewById(R.id.loading_view_stub);
                Intrinsics.checkNotNull(bool);
                if (bool.booleanValue()) {
                    if ((viewStub != null ? viewStub.getParent() : null) != null) {
                        subAppListActivity.loadingView = viewStub.inflate();
                    }
                }
                View view = subAppListActivity.getListBehavior().bottomButton;
                if (view != null) {
                    view.setVisibility(
                            Intrinsics.areEqual(
                                                    subAppListActivity
                                                            .getController()
                                                            .mListLoading
                                                            .mIsEmptyList
                                                            .getValue(),
                                                    Boolean.TRUE)
                                            ^ true
                                    ? 0
                                    : 8);
                }
                View view2 = subAppListActivity.loadingView;
                if (view2 != null) {
                    view2.setVisibility(bool.booleanValue() ? 0 : 8);
                }
                ((View) subAppListActivity.listview$delegate.getValue())
                        .setVisibility(bool.booleanValue() ? 8 : 0);
                break;
            default:
                if (((List) obj) != null
                        && (findItem = ((Menu) this.this$0).findItem(MenuIdType.CANCEL.getMenuId()))
                                != null) {
                    findItem.setVisible(!r6.isEmpty());
                    break;
                }
                break;
        }
    }
}

package com.samsung.android.settings.analyzestorage.ui.pages.filelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;

import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ListItemHandler;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageUsageManager;
import com.samsung.android.settings.analyzestorage.presenter.page.PageInfo;
import com.samsung.android.settings.analyzestorage.ui.manager.ListMarginManager;
import com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage.AsSubListAdapter;
import com.samsung.android.settings.analyzestorage.ui.widget.EmptyView;
import com.samsung.android.settings.analyzestorage.ui.widget.MyFilesRecyclerView;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AppListBehavior {
    public ActionBar actionBar;
    public final FragmentActivity activity;
    public AsSubListAdapter adapter;
    public View bottomButton;
    public final Context context;
    public final AppListController controller;
    public final Lazy customActionBar$delegate;
    public EmptyView emptyView;
    public final ListItemHandler listItemHandler;
    public final Lazy listMarginManager$delegate;
    public final Lazy listViewLayout$delegate;
    public final PageInfo pageInfo;
    public MyFilesRecyclerView recyclerView;

    public AppListBehavior(
            FragmentActivity activity, AppListController controller, PageInfo pageInfo) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(controller, "controller");
        Intrinsics.checkNotNullParameter(pageInfo, "pageInfo");
        this.activity = activity;
        this.controller = controller;
        this.pageInfo = pageInfo;
        this.context = activity.getBaseContext();
        this.listItemHandler = controller.mListItemHandler;
        this.listViewLayout$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.samsung.android.settings.analyzestorage.ui.pages.filelist.AppListBehavior$listViewLayout$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                AppListBehavior appListBehavior = AppListBehavior.this;
                                Context context = appListBehavior.context;
                                Intrinsics.checkNotNullExpressionValue(context, "context");
                                ListViewLayout listViewLayout = new ListViewLayout(context);
                                listViewLayout.layoutListener =
                                        new AppListBehavior$initListViewLayout$1$1(appListBehavior);
                                return listViewLayout;
                            }
                        });
        this.listMarginManager$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.samsung.android.settings.analyzestorage.ui.pages.filelist.AppListBehavior$listMarginManager$2
                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                ListMarginManager.Companion companion = ListMarginManager.Companion;
                                ListMarginManager listMarginManager = ListMarginManager.instance;
                                if (listMarginManager == null) {
                                    synchronized (companion) {
                                        listMarginManager = new ListMarginManager();
                                        ListMarginManager.instance = listMarginManager;
                                    }
                                }
                                return listMarginManager;
                            }
                        });
        this.customActionBar$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.samsung.android.settings.analyzestorage.ui.pages.filelist.AppListBehavior$customActionBar$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                View inflate =
                                        LayoutInflater.from(AppListBehavior.this.activity)
                                                .inflate(
                                                        R.layout.as_action_bar_select_layout,
                                                        (ViewGroup) null);
                                Intrinsics.checkNotNullExpressionValue(inflate, "inflate(...)");
                                return inflate;
                            }
                        });
    }

    public final String getCapacityOfPercent() {
        AppListController appListController = this.controller;
        if (appListController.mSaAppsType != 5) {
            return null;
        }
        appListController.getClass();
        long storageTotalSize = StorageUsageManager.getStorageTotalSize(0);
        String str =
                ((StorageUsageManager.getLowMemoryMinPercent(
                                                appListController.mContext, storageTotalSize)
                                        * storageTotalSize)
                                / 100000000000L)
                        + appListController
                                .mContext
                                .getResources()
                                .getString(R.string.gigabyteShort);
        Log.i(appListController.mTag, "getCapacityOfPercent() ]  capacity : " + str);
        return str;
    }

    public final View getCustomActionBar() {
        return (View) this.customActionBar$delegate.getValue();
    }
}

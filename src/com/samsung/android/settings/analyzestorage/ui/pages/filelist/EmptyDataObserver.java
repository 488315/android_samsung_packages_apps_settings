package com.samsung.android.settings.analyzestorage.ui.pages.filelist;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController;
import com.samsung.android.settings.analyzestorage.presenter.page.PageInfo;
import com.samsung.android.settings.analyzestorage.presenter.page.PageType;
import com.samsung.android.settings.analyzestorage.presenter.utils.StringConverter;
import com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage.AsSubListAdapter;
import com.samsung.android.settings.analyzestorage.ui.widget.EmptyView;
import com.samsung.android.settings.logging.LoggingHelper;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class EmptyDataObserver extends RecyclerView.AdapterDataObserver {
    public final AppListBehavior emptyViewInterface;
    public final RecyclerView recyclerView;
    public final Handler handler = new Handler(Looper.getMainLooper());
    public final EmptyDataObserver$animationFinishedListener$1 animationFinishedListener =
            new RecyclerView.ItemAnimator
                    .ItemAnimatorFinishedListener() { // from class:
                                                      // com.samsung.android.settings.analyzestorage.ui.pages.filelist.EmptyDataObserver$animationFinishedListener$1
                @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator.ItemAnimatorFinishedListener
                public final void onAnimationsFinished() {
                    EmptyDataObserver emptyDataObserver = EmptyDataObserver.this;
                    emptyDataObserver.handler.post(
                            new EmptyDataObserver$onChanged$1(emptyDataObserver, 1));
                }
            };

    /* JADX WARN: Type inference failed for: r1v2, types: [com.samsung.android.settings.analyzestorage.ui.pages.filelist.EmptyDataObserver$animationFinishedListener$1] */
    public EmptyDataObserver(RecyclerView recyclerView, AppListBehavior appListBehavior) {
        this.recyclerView = recyclerView;
        this.emptyViewInterface = appListBehavior;
    }

    public static final void access$waitRecyclerViewAnimationToFinish(
            EmptyDataObserver emptyDataObserver) {
        String string;
        RecyclerView recyclerView = emptyDataObserver.recyclerView;
        RecyclerView.ItemAnimator itemAnimator = recyclerView.mItemAnimator;
        if (itemAnimator != null && itemAnimator.isRunning()) {
            RecyclerView.ItemAnimator itemAnimator2 = recyclerView.getItemAnimator();
            if (itemAnimator2 != null) {
                itemAnimator2.isRunning(emptyDataObserver.animationFinishedListener);
                return;
            }
            return;
        }
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        boolean z = true;
        boolean z2 = adapter != null && adapter.getItemCount() == 0;
        boolean z3 = !z2;
        recyclerView.setVisibility(z3 ? 0 : 8);
        final AppListBehavior appListBehavior = emptyDataObserver.emptyViewInterface;
        View view = appListBehavior.bottomButton;
        if (view != null) {
            view.setVisibility(z3 ? 0 : 8);
        }
        Log.d("AppListBehavior", "updateAppBarTitle emptyVisible " + z2);
        ActionBar actionBar = appListBehavior.actionBar;
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(z3);
            actionBar.setDisplayHomeAsUpEnabled(z2);
            actionBar.setHomeButtonEnabled();
            actionBar.setDisplayShowTitleEnabled(z2);
        }
        AppListController controller = appListBehavior.controller;
        PageInfo pageInfo = appListBehavior.pageInfo;
        FragmentActivity fragmentActivity = appListBehavior.activity;
        if (z2) {
            ActionBar actionBar2 = appListBehavior.actionBar;
            if (actionBar2 != null) {
                PageType pageType = pageInfo.mPageType;
                pageType.getClass();
                actionBar2.setTitle(
                        pageType == PageType.ANALYZE_STORAGE_APP_CACHE
                                ? fragmentActivity.getString(R.string.as_app_cache)
                                : fragmentActivity.getString(R.string.as_unused_apps));
            }
        } else {
            ActionBar actionBar3 = appListBehavior.actionBar;
            if (actionBar3 != null) {
                actionBar3.setCustomView(appListBehavior.getCustomActionBar());
                View requireViewById =
                        appListBehavior.getCustomActionBar().requireViewById(R.id.title);
                Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById(...)");
                final TextView textView = (TextView) requireViewById;
                View requireViewById2 =
                        appListBehavior.getCustomActionBar().requireViewById(R.id.sub_title);
                Intrinsics.checkNotNullExpressionValue(requireViewById2, "requireViewById(...)");
                final TextView textView2 = (TextView) requireViewById2;
                View requireViewById3 =
                        appListBehavior.getCustomActionBar().requireViewById(R.id.checkbox);
                Intrinsics.checkNotNullExpressionValue(requireViewById3, "requireViewById(...)");
                final CheckBox checkBox = (CheckBox) requireViewById3;
                controller.mListItemHandler.mCheckItemSize.observe(
                        fragmentActivity,
                        new Observer() { // from class:
                                         // com.samsung.android.settings.analyzestorage.ui.pages.filelist.AppListBehavior$initObserve$1
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                String quantityString;
                                String str = (String) obj;
                                TextView textView3 = textView;
                                AppListBehavior appListBehavior2 = appListBehavior;
                                int checkedItemCount =
                                        appListBehavior2.controller.mListItemHandler
                                                .getCheckedItemCount();
                                if (checkedItemCount == 0) {
                                    quantityString =
                                            appListBehavior2.context.getString(
                                                    R.string.as_select_items);
                                    Intrinsics.checkNotNull(quantityString);
                                } else {
                                    quantityString =
                                            appListBehavior2
                                                    .context
                                                    .getResources()
                                                    .getQuantityString(
                                                            R.plurals.as_n_selected,
                                                            checkedItemCount,
                                                            Integer.valueOf(checkedItemCount));
                                    Intrinsics.checkNotNull(quantityString);
                                }
                                textView3.setText(quantityString);
                                textView2.setText(str);
                            }
                        });
                controller.mListItemHandler.mIsAllChecked.observe(
                        fragmentActivity,
                        new Observer() { // from class:
                                         // com.samsung.android.settings.analyzestorage.ui.pages.filelist.AppListBehavior$initObserve$2
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                Boolean bool = (Boolean) obj;
                                if (bool != null) {
                                    checkBox.setChecked(bool.booleanValue());
                                }
                            }
                        });
                ((LinearLayout)
                                appListBehavior
                                        .getCustomActionBar()
                                        .requireViewById(R.id.check_box_container))
                        .setOnClickListener(
                                new View
                                        .OnClickListener() { // from class:
                                                             // com.samsung.android.settings.analyzestorage.ui.pages.filelist.AppListBehavior$initCheckBox$1
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view2) {
                                        Intrinsics.checkNotNullExpressionValue(
                                                AppListBehavior.this
                                                        .getCustomActionBar()
                                                        .requireViewById(R.id.checkbox),
                                                "requireViewById(...)");
                                        AppListBehavior.this.controller.mListItemHandler
                                                .setAllItemChecked(!((CheckBox) r3).isChecked());
                                        String screenIdForSA =
                                                AppListBehavior.this.controller.mPageInfo.mPageType
                                                        .getScreenIdForSA();
                                        PageType pageType2 =
                                                AppListBehavior.this.controller.mPageInfo.mPageType;
                                        pageType2.getClass();
                                        LoggingHelper.insertEventLogging(
                                                screenIdForSA,
                                                pageType2 == PageType.ANALYZE_STORAGE_APP_CACHE
                                                        ? 8892
                                                        : 8895);
                                        AsSubListAdapter asSubListAdapter =
                                                AppListBehavior.this.adapter;
                                        if (asSubListAdapter != null) {
                                            asSubListAdapter.notifyDataSetChanged();
                                        }
                                    }
                                });
                Toolbar toolbar = (Toolbar) fragmentActivity.findViewById(R.id.toolbar);
                if (toolbar != null) {
                    toolbar.ensureContentInsets();
                    toolbar.mContentInsets.setRelative(0, 0);
                }
            }
        }
        Log.d("AppListBehavior", "updateView emptyVisible " + z2);
        EmptyView emptyView = appListBehavior.emptyView;
        if (emptyView != null) {
            emptyView.setVisibility(z2 ? 0 : 8);
            if (z2) {
                PageType pageType2 = pageInfo.mPageType;
                Intrinsics.checkNotNullExpressionValue(pageType2, "getPageType(...)");
                String capacityOfPercent = appListBehavior.getCapacityOfPercent();
                Intrinsics.checkNotNullParameter(controller, "controller");
                if (pageType2 != PageType.ANALYZE_STORAGE_UNUSED_APPS
                        && pageType2 != PageType.ANALYZE_STORAGE_APP_CACHE) {
                    z = false;
                }
                if (!z) {
                    Log.d("EmptyView", "updateView() ] Unsupported PageType : " + pageType2);
                    return;
                }
                long j = controller.mFreedUpSize;
                Log.d(
                        "EmptyView",
                        "updateView() ] pageType : " + pageType2 + " , freedUpSize : " + j);
                if (j <= 0) {
                    emptyView.setSubText(pageType2, capacityOfPercent);
                    return;
                }
                String formatFileSize =
                        StringConverter.formatFileSize(0, j, emptyView.getContext());
                int ordinal = pageType2.ordinal();
                if (ordinal == 23) {
                    string =
                            emptyView
                                    .getContext()
                                    .getResources()
                                    .getString(
                                            R.string.as_no_unused_apps_sub_text_feedback,
                                            formatFileSize);
                    Intrinsics.checkNotNull(string);
                } else if (ordinal != 24) {
                    string = ApnSettings.MVNO_NONE;
                } else {
                    string =
                            emptyView
                                    .getContext()
                                    .getResources()
                                    .getString(
                                            R.string.as_no_app_cache_sub_text_feedback,
                                            formatFileSize);
                    Intrinsics.checkNotNull(string);
                }
                ((TextView) emptyView.requireViewById(R.id.no_item_sub_text)).setText(string);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
    public final void onChanged() {
        this.handler.post(new EmptyDataObserver$onChanged$1(this, 0));
    }
}

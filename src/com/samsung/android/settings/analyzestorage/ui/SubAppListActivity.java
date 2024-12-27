package com.samsung.android.settings.analyzestorage.ui;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelProviderImpl;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settings.R;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.domain.thread.ThreadExecutor;
import com.samsung.android.settings.analyzestorage.external.injection.ControllerFactory;
import com.samsung.android.settings.analyzestorage.presenter.ActivityInfo$ActivityInfoListener;
import com.samsung.android.settings.analyzestorage.presenter.ActivityInfoStore;
import com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.EventListener;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ListLoading;
import com.samsung.android.settings.analyzestorage.presenter.execution.MenuExecuteManager;
import com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.EventContext;
import com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.KeyboardMouseManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.DetailsManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.FeatureManager$UiFeature;
import com.samsung.android.settings.analyzestorage.presenter.managers.detailinfo.AbsDetailsInfoLoader$LoadHandler;
import com.samsung.android.settings.analyzestorage.presenter.managers.detailinfo.CheckedItemsDetailsLoader;
import com.samsung.android.settings.analyzestorage.presenter.page.PageInfo;
import com.samsung.android.settings.analyzestorage.presenter.page.PageType;
import com.samsung.android.settings.analyzestorage.ui.exception.ExceptionHandler;
import com.samsung.android.settings.analyzestorage.ui.manager.ListMarginManager;
import com.samsung.android.settings.analyzestorage.ui.menu.MenuIdType;
import com.samsung.android.settings.analyzestorage.ui.menu.PrepareMenu;
import com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage.AsSubListAdapter;
import com.samsung.android.settings.analyzestorage.ui.pages.filelist.AppListBehavior;
import com.samsung.android.settings.analyzestorage.ui.pages.filelist.AppListBehavior$initListViewLayout$1$1;
import com.samsung.android.settings.analyzestorage.ui.pages.filelist.AppListListener;
import com.samsung.android.settings.analyzestorage.ui.pages.filelist.AppListListener$getMouseEventCallBack$1;
import com.samsung.android.settings.analyzestorage.ui.pages.filelist.AppListListener$getSeslOnMultiSelectedListener$1;
import com.samsung.android.settings.analyzestorage.ui.pages.filelist.EmptyDataObserver;
import com.samsung.android.settings.analyzestorage.ui.pages.filelist.ListViewLayout;
import com.samsung.android.settings.analyzestorage.ui.pages.filelist.SynchronizedViewPool;
import com.samsung.android.settings.analyzestorage.ui.utils.UiUtils;
import com.samsung.android.settings.analyzestorage.ui.widget.EmptyView;
import com.samsung.android.settings.analyzestorage.ui.widget.MyFilesRecyclerView;
import com.samsung.android.settings.logging.LoggingHelper;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"Lcom/samsung/android/settings/analyzestorage/ui/SubAppListActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/samsung/android/settings/analyzestorage/presenter/ActivityInfo$ActivityInfoListener;", "<init>", "()V", "applications__sources__apps__SecSettings__android_common__SecSettings-core"}, k = 1, mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class SubAppListActivity extends AppCompatActivity implements ActivityInfo$ActivityInfoListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int instanceId;
    public boolean isRecreate;
    public View loadingView;
    public final Lazy pageInfo$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.android.settings.analyzestorage.ui.SubAppListActivity$pageInfo$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        /* renamed from: invoke */
        public final Object mo1068invoke() {
            SubAppListActivity subAppListActivity = SubAppListActivity.this;
            int i = SubAppListActivity.$r8$clinit;
            if (Intrinsics.areEqual(subAppListActivity.getIntent().getAction(), "com.samsung.android.settings.analyzestorage.ENTER_UNUSED_APPS")) {
                PageInfo pageInfo = new PageInfo(PageType.ANALYZE_STORAGE_UNUSED_APPS);
                pageInfo.putExtra(4, "asType");
                return pageInfo;
            }
            PageInfo pageInfo2 = new PageInfo(PageType.ANALYZE_STORAGE_APP_CACHE);
            pageInfo2.putExtra(5, "asType");
            return pageInfo2;
        }
    });
    public final Lazy controller$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.android.settings.analyzestorage.ui.SubAppListActivity$controller$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        /* renamed from: invoke */
        public final Object mo1068invoke() {
            SubAppListActivity subAppListActivity = SubAppListActivity.this;
            int i = SubAppListActivity.$r8$clinit;
            subAppListActivity.getClass();
            Application application = subAppListActivity.getApplication();
            PageType pageType = subAppListActivity.getPageInfo().mPageType;
            ControllerFactory controllerFactory = new ControllerFactory(application, subAppListActivity.instanceId);
            ViewModelStore store = subAppListActivity.getViewModelStore();
            CreationExtras defaultViewModelCreationExtras = subAppListActivity.getDefaultViewModelCreationExtras();
            Intrinsics.checkNotNullParameter(store, "store");
            ViewModelProviderImpl viewModelProviderImpl = new ViewModelProviderImpl(store, controllerFactory, defaultViewModelCreationExtras);
            KClass kotlinClass = JvmClassMappingKt.getKotlinClass(AppListController.class);
            String qualifiedName = kotlinClass.getQualifiedName();
            if (qualifiedName != null) {
                return (AppListController) viewModelProviderImpl.getViewModel$lifecycle_viewmodel_release(kotlinClass, "androidx.lifecycle.ViewModelProvider.DefaultKey:".concat(qualifiedName));
            }
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels".toString());
        }
    });
    public final Lazy listBehavior$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.android.settings.analyzestorage.ui.SubAppListActivity$listBehavior$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        /* renamed from: invoke */
        public final Object mo1068invoke() {
            SubAppListActivity subAppListActivity = SubAppListActivity.this;
            return new AppListBehavior(subAppListActivity, subAppListActivity.getController(), SubAppListActivity.this.getPageInfo());
        }
    });
    public final Lazy listview$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.android.settings.analyzestorage.ui.SubAppListActivity$listview$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        /* renamed from: invoke */
        public final Object mo1068invoke() {
            View requireViewById = SubAppListActivity.this.requireViewById(R.id.list_container);
            Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById(...)");
            return requireViewById;
        }
    });
    public final Lazy bottomButtonContainer$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.android.settings.analyzestorage.ui.SubAppListActivity$bottomButtonContainer$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        /* renamed from: invoke */
        public final Object mo1068invoke() {
            View requireViewById = SubAppListActivity.this.requireViewById(R.id.bottom_button_container);
            Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById(...)");
            return requireViewById;
        }
    });
    public final Lazy bottomButton1$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.android.settings.analyzestorage.ui.SubAppListActivity$bottomButton1$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        /* renamed from: invoke */
        public final Object mo1068invoke() {
            View requireViewById = SubAppListActivity.this.requireViewById(R.id.bottom_button1);
            Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById(...)");
            return (Button) requireViewById;
        }
    });
    public final Lazy bottomButton2$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.android.settings.analyzestorage.ui.SubAppListActivity$bottomButton2$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        /* renamed from: invoke */
        public final Object mo1068invoke() {
            View requireViewById = SubAppListActivity.this.requireViewById(R.id.bottom_button2);
            Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById(...)");
            return (Button) requireViewById;
        }
    });
    public int buttonOrientation = -1;
    public final SubAppListActivity$resultCallback$1 resultCallback = new MenuExecuteManager.ResultListener() { // from class: com.samsung.android.settings.analyzestorage.ui.SubAppListActivity$resultCallback$1
        @Override // com.samsung.android.settings.analyzestorage.presenter.execution.MenuExecuteManager.ResultListener
        public final void onResult(MenuExecuteManager.Result result) {
            AsSubListAdapter asSubListAdapter;
            int i = result.mMenuType;
            if ((i == 350 || i == 340) && result.mIsSuccess) {
                int i2 = SubAppListActivity.$r8$clinit;
                SubAppListActivity subAppListActivity = SubAppListActivity.this;
                AppListBehavior listBehavior = subAppListActivity.getListBehavior();
                List list = (List) listBehavior.listItemHandler.mListItems.getValue();
                if (list != null && (asSubListAdapter = listBehavior.adapter) != null) {
                    asSubListAdapter.updateItems(CollectionsKt___CollectionsKt.toMutableList((Collection) list));
                }
                subAppListActivity.setResult(1001);
            }
        }
    };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PageType.values().length];
            try {
                iArr[PageType.ANALYZE_STORAGE_UNUSED_APPS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x006f, code lost:
    
        if (((com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.KeyMouseCommand) java.util.Optional.ofNullable(com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.KeyboardMouseManager.sKeyMouseCommand).orElseGet(new com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.KeyboardMouseManager$$ExternalSyntheticLambda0(2))).onKeyDown(r2, r16, r17) != false) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0071, code lost:
    
        r2 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0119, code lost:
    
        if (((com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.KeyMouseCommand) java.util.Optional.ofNullable(r2).orElseGet(new com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.KeyboardMouseManager$$ExternalSyntheticLambda0(3))).onKeyDown(r13, r16, r17) != false) goto L22;
     */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean dispatchKeyEvent(android.view.KeyEvent r17) {
        /*
            Method dump skipped, instructions count: 620
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settings.analyzestorage.ui.SubAppListActivity.dispatchKeyEvent(android.view.KeyEvent):boolean");
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.ActivityInfo$ActivityInfoListener
    public final ActivityResultLauncher getActivityResultLauncher() {
        return null;
    }

    public final Button getBottomButton1() {
        return (Button) this.bottomButton1$delegate.getValue();
    }

    public final Button getBottomButton2() {
        return (Button) this.bottomButton2$delegate.getValue();
    }

    public final String getButtonStr(int i, int i2, String str, boolean z) {
        int checkedItemCount = getController().mListItemHandler.getCheckedItemCount();
        if (z) {
            String quantityString = getResources().getQuantityString(i2, checkedItemCount, Integer.valueOf(checkedItemCount), str);
            Intrinsics.checkNotNull(quantityString);
            return quantityString;
        }
        String string = getResources().getString(i);
        Intrinsics.checkNotNull(string);
        return string;
    }

    public final AppListController getController() {
        return (AppListController) this.controller$delegate.getValue();
    }

    public final AppListBehavior getListBehavior() {
        return (AppListBehavior) this.listBehavior$delegate.getValue();
    }

    public final PageInfo getPageInfo() {
        return (PageInfo) this.pageInfo$delegate.getValue();
    }

    public final void initButtonOrientation() {
        int i = getResources().getConfiguration().screenWidthDp >= 589 ? 1 : 0;
        if (this.buttonOrientation != i) {
            this.buttonOrientation = i;
            int i2 = i != 1 ? 0 : 1;
            View view = (View) this.bottomButtonContainer$delegate.getValue();
            LinearLayout linearLayout = view instanceof LinearLayout ? (LinearLayout) view : null;
            if (linearLayout != null) {
                linearLayout.setOrientation(i2 ^ 1);
            }
            updateBottomButtonWidth(getBottomButton1());
            if (getBottomButton2().getVisibility() == 0) {
                updateBottomButtonWidth(getBottomButton2());
                ViewGroup.LayoutParams layoutParams = getBottomButton2().getLayoutParams();
                ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
                if (marginLayoutParams != null) {
                    marginLayoutParams.topMargin = getResources().getDimensionPixelSize(i2 != 0 ? R.dimen.as_basic_contained_button_horizontal_margin_top : R.dimen.as_basic_contained_button_vertical_margin_top);
                }
            }
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        View requireViewById = requireViewById(R.id.contents_container);
        Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById(...)");
        UiUtils.setFlexibleSpacing(requireViewById);
        initButtonOrientation();
    }

    @Override // android.app.Activity
    public final boolean onContextItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        AppListController controller = getController();
        MenuIdType.Companion companion = MenuIdType.Companion;
        int itemId = item.getItemId();
        companion.getClass();
        MenuIdType[] values = MenuIdType.values();
        ArrayList arrayList = new ArrayList();
        for (MenuIdType menuIdType : values) {
            if (menuIdType.getMenuId() == itemId) {
                arrayList.add(menuIdType);
            }
        }
        controller.mMenuDirector.execute(((MenuIdType) (CollectionsKt__CollectionsKt.getLastIndex(arrayList) >= 0 ? arrayList.get(0) : MenuIdType.NONE)).getMenuType(), new PrepareMenu());
        return false;
    }

    /* JADX WARN: Type inference failed for: r2v4, types: [com.samsung.android.settings.analyzestorage.ui.SubAppListActivity$observeCheckedItemCount$listener$1] */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        String str;
        int i;
        PageType pageType;
        MyFilesRecyclerView myFilesRecyclerView;
        super.onCreate(bundle);
        setContentView(R.layout.as_sub_app_list_activity_main);
        this.isRecreate = bundle != null;
        int intExtra = getIntent().getIntExtra("instanceId", -1);
        this.instanceId = intExtra;
        Log.d("SubAppListActivity", "onCreate] instanceId : " + intExtra);
        getController().mSaAppsType = getPageInfo().mPageType == PageType.ANALYZE_STORAGE_APP_CACHE ? 5 : 4;
        if (!this.isRecreate) {
            AppListController controller = getController();
            controller.setPageInfo(getPageInfo());
            MenuExecuteManager.sCallbackListener.put(controller, Boolean.TRUE);
            controller.mListLoading.mCancelLoad = false;
            controller.loadListItem(false);
            Context context = controller.mContext;
            IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_FULLY_REMOVED");
            intentFilter.addDataScheme("package");
            context.registerReceiver(controller.mPackageBroadcastReceiver, intentFilter);
        }
        EventListener eventListener = getController().mListener;
        if (eventListener != null) {
            eventListener.mExceptionListener = new ExceptionHandler();
            SparseArray sparseArray = KeyboardMouseManager.sEventContextMap;
            KeyboardMouseManager keyboardMouseManager = KeyboardMouseManager.KeyboardMouseManagerHolder.INSTANCE;
            keyboardMouseManager.getClass();
            new WeakReference(keyboardMouseManager);
        }
        Context context2 = ActivityInfoStore.context;
        ActivityInfoStore.Companion.getInstance(this.instanceId).register(this);
        View requireViewById = requireViewById(R.id.toolbar);
        Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById(...)");
        setSupportActionBar((Toolbar) requireViewById);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeButtonEnabled();
            supportActionBar.setDisplayShowTitleEnabled(true);
            supportActionBar.setTitle(ApnSettings.MVNO_NONE);
            supportActionBar.setDisplayShowCustomEnabled(false);
            supportActionBar.setBackgroundDrawable(null);
        }
        View requireViewById2 = requireViewById(R.id.no_item_layout);
        Intrinsics.checkNotNullExpressionValue(requireViewById2, "requireViewById(...)");
        EmptyView emptyView = (EmptyView) requireViewById2;
        View requireViewById3 = requireViewById(R.id.apps_list_view);
        Intrinsics.checkNotNullExpressionValue(requireViewById3, "requireViewById(...)");
        RecyclerView recyclerView = (RecyclerView) requireViewById3;
        final AppListBehavior listBehavior = getListBehavior();
        ActionBar supportActionBar2 = getSupportActionBar();
        View bottomButton = (View) this.bottomButtonContainer$delegate.getValue();
        listBehavior.getClass();
        Intrinsics.checkNotNullParameter(bottomButton, "bottomButton");
        if (recyclerView instanceof MyFilesRecyclerView) {
            MyFilesRecyclerView myFilesRecyclerView2 = (MyFilesRecyclerView) recyclerView;
            myFilesRecyclerView2.setNestedScrollingEnabled(true);
            Context context3 = listBehavior.context;
            Intrinsics.checkNotNullExpressionValue(context3, "context");
            AppListController appListController = listBehavior.controller;
            FragmentActivity fragmentActivity = listBehavior.activity;
            final AsSubListAdapter asSubListAdapter = new AsSubListAdapter(context3, appListController, fragmentActivity);
            asSubListAdapter.setHasStableIds(true);
            Lazy lazy = listBehavior.listViewLayout$delegate;
            final ListViewLayout listViewLayout = (ListViewLayout) lazy.getValue();
            Context context4 = listViewLayout.context;
            myFilesRecyclerView2.setLayoutManager(new LinearLayoutManager() { // from class: com.samsung.android.settings.analyzestorage.ui.pages.filelist.ListViewLayout$getLayoutManager$1
                {
                    super(1);
                }

                @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
                public final View onFocusSearchFailed(View focused, int i2, RecyclerView.Recycler recycler, RecyclerView.State state) {
                    int findLastVisibleItemPosition;
                    AppListBehavior$initListViewLayout$1$1 appListBehavior$initListViewLayout$1$1;
                    MyFilesRecyclerView myFilesRecyclerView3;
                    Intrinsics.checkNotNullParameter(focused, "focused");
                    Intrinsics.checkNotNullParameter(recycler, "recycler");
                    Intrinsics.checkNotNullParameter(state, "state");
                    if (i2 != 130 || (findLastVisibleItemPosition = findLastVisibleItemPosition()) >= state.getItemCount()) {
                        return super.onFocusSearchFailed(focused, i2, recycler, state);
                    }
                    View findViewByPosition = findViewByPosition(findLastVisibleItemPosition);
                    if (findViewByPosition != null && (appListBehavior$initListViewLayout$1$1 = ListViewLayout.this.layoutListener) != null && (myFilesRecyclerView3 = appListBehavior$initListViewLayout$1$1.this$0.recyclerView) != null) {
                        myFilesRecyclerView3.scrollBy(0, findViewByPosition.getHeight());
                    }
                    return focused;
                }
            });
            myFilesRecyclerView2.setAdapter(asSubListAdapter);
            Context context5 = listBehavior.context;
            Intrinsics.checkNotNullExpressionValue(context5, "context");
            Object systemService = context5.getSystemService("accessibility");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.accessibility.AccessibilityManager");
            if (((AccessibilityManager) systemService).semIsScreenReaderEnabled()) {
                myFilesRecyclerView2.setItemAnimator(null);
            }
            recyclerView.seslSetFastScrollerEnabled(true);
            recyclerView.seslSetGoToTopEnabled(true);
            final ListViewLayout listViewLayout2 = (ListViewLayout) lazy.getValue();
            listViewLayout2.getClass();
            final SynchronizedViewPool synchronizedViewPool = new SynchronizedViewPool();
            RecyclerView.RecycledViewPool.ScrapData scrapDataForType = synchronizedViewPool.getScrapDataForType(R.layout.as_sub_app_list_item);
            scrapDataForType.mMaxScrap = 20;
            ArrayList arrayList = scrapDataForType.mScrapHeap;
            str = "requireViewById(...)";
            while (arrayList.size() > 20) {
                arrayList.remove(arrayList.size() - 1);
            }
            ThreadExecutor.runOnWorkThread(new Runnable() { // from class: com.samsung.android.settings.analyzestorage.ui.pages.filelist.ListViewLayout$initViewPool$1
                public final /* synthetic */ int $poolSize = 20;
                public final /* synthetic */ int $viewType = R.layout.as_sub_app_list_item;

                @Override // java.lang.Runnable
                public final void run() {
                    MyFilesRecyclerView myFilesRecyclerView3;
                    RecyclerView.Adapter adapter;
                    for (int i2 = 0; i2 < this.$poolSize; i2++) {
                        try {
                            ListViewLayout listViewLayout3 = ListViewLayout.this;
                            int i3 = this.$viewType;
                            AppListBehavior$initListViewLayout$1$1 appListBehavior$initListViewLayout$1$1 = listViewLayout3.layoutListener;
                            RecyclerView.ViewHolder viewHolder = null;
                            if (appListBehavior$initListViewLayout$1$1 != null && (myFilesRecyclerView3 = appListBehavior$initListViewLayout$1$1.this$0.recyclerView) != null && (adapter = myFilesRecyclerView3.getAdapter()) != null) {
                                viewHolder = adapter.createViewHolder(myFilesRecyclerView3, i3);
                            }
                            if (viewHolder != null) {
                                synchronizedViewPool.putRecycledView(viewHolder);
                            }
                        } catch (Exception e) {
                            ListViewLayout.this.getClass();
                            Log.e("ListViewLayout", "initViewPool, adding viewHolder failed. e=" + e.getMessage());
                        }
                    }
                }
            });
            AppListBehavior$initListViewLayout$1$1 appListBehavior$initListViewLayout$1$1 = listViewLayout2.layoutListener;
            if (appListBehavior$initListViewLayout$1$1 != null && (myFilesRecyclerView = appListBehavior$initListViewLayout$1$1.this$0.recyclerView) != null) {
                myFilesRecyclerView.setRecycledViewPool(synchronizedViewPool);
            }
            AppListListener appListListener = new AppListListener(myFilesRecyclerView2, asSubListAdapter, appListController);
            myFilesRecyclerView2.mOnMultiSelectedListener = new AppListListener$getSeslOnMultiSelectedListener$1(appListListener);
            AppListListener.MultiSelectedInfo multiSelectedInfo = new AppListListener.MultiSelectedInfo();
            multiSelectedInfo.stopPoint = appListListener;
            multiSelectedInfo.startPoint = new ArrayList();
            i = -1;
            multiSelectedInfo.selectionStartPosition = -1;
            myFilesRecyclerView2.mLongPressMultiSelectionListener = multiSelectedInfo;
            KeyboardMouseManager.sMouseEventCallBack = new AppListListener$getMouseEventCallBack$1(appListListener);
            PageType pageType2 = listBehavior.pageInfo.mPageType;
            Intrinsics.checkNotNullExpressionValue(pageType2, "getPageType(...)");
            String capacityOfPercent = listBehavior.getCapacityOfPercent();
            int ordinal = pageType2.ordinal();
            Integer valueOf = ordinal != 23 ? ordinal != 24 ? null : Integer.valueOf(R.string.as_no_app_cache) : Integer.valueOf(R.string.as_no_unused_apps);
            if (valueOf != null) {
                ((TextView) emptyView.requireViewById(R.id.no_item_title)).setText(valueOf.intValue());
            }
            emptyView.setSubText(pageType2, capacityOfPercent);
            Context context6 = emptyView.getContext();
            Intrinsics.checkNotNullExpressionValue(context6, "getContext(...)");
            if (!FeatureManager$UiFeature.isDefaultTheme(context6)) {
                int color = emptyView.getContext().getColor(R.color.as_theme_main_text_color);
                ((TextView) emptyView.requireViewById(R.id.no_item_title)).setTextColor(color);
                ((TextView) emptyView.requireViewById(R.id.no_item_sub_text)).setTextColor(color);
            }
            listBehavior.emptyView = emptyView;
            asSubListAdapter.registerAdapterDataObserver(new EmptyDataObserver(recyclerView, listBehavior));
            ListMarginManager listMarginManager = (ListMarginManager) listBehavior.listMarginManager$delegate.getValue();
            listMarginManager.getClass();
            if (!((ArrayList) listMarginManager.listenerList).contains(listBehavior)) {
                ((ArrayList) listMarginManager.listenerList).add(listBehavior);
            }
            listBehavior.listItemHandler.mListItems.observe(fragmentActivity, new Observer() { // from class: com.samsung.android.settings.analyzestorage.ui.pages.filelist.AppListBehavior$initRecyclerView$1$1
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    List list = (List) obj;
                    ListLoading listLoading = AppListBehavior.this.controller.mListLoading;
                    if (Boolean.FALSE.equals(listLoading.mIsEmptyList.getValue()) && listLoading.mListItemInterface.getListItems() == null) {
                        return;
                    }
                    Intrinsics.checkNotNull(list);
                    asSubListAdapter.updateItems(CollectionsKt___CollectionsKt.toMutableList((Collection) list));
                }
            });
            listBehavior.adapter = asSubListAdapter;
            listBehavior.recyclerView = myFilesRecyclerView2;
        } else {
            str = "requireViewById(...)";
            i = -1;
        }
        listBehavior.bottomButton = bottomButton;
        listBehavior.actionBar = supportActionBar2;
        PageType pageType3 = getPageInfo().mPageType;
        pageType3.getClass();
        PageType pageType4 = PageType.ANALYZE_STORAGE_UNUSED_APPS;
        if (pageType3 == pageType4) {
            getBottomButton2().setVisibility(0);
        }
        initButtonOrientation();
        PageType pageType5 = getPageInfo().mPageType;
        final MenuIdType menuIdType = (pageType5 == null ? i : WhenMappings.$EnumSwitchMapping$0[pageType5.ordinal()]) == 1 ? MenuIdType.UNINSTALL : MenuIdType.CLEAR_APP_CACHE;
        updateBottomButton1(ApnSettings.MVNO_NONE, false);
        getBottomButton1().setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.settings.analyzestorage.ui.SubAppListActivity$initBottomButton1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (UiUtils.isValidClick$default(view.getId())) {
                    LoggingHelper.insertEventLogging(SubAppListActivity.this.getPageInfo().mPageType.getScreenIdForSA(), menuIdType == MenuIdType.CLEAR_APP_CACHE ? 8893 : 8896);
                    SubAppListActivity.this.getController().mMenuDirector.execute(menuIdType.getMenuType(), new PrepareMenu());
                }
            }
        });
        PageType pageType6 = getPageInfo().mPageType;
        pageType6.getClass();
        if (pageType6 == pageType4) {
            updateBottomButton2(ApnSettings.MVNO_NONE, false);
            getBottomButton2().setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.settings.analyzestorage.ui.SubAppListActivity$initBottomButton2$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    if (UiUtils.isValidClick$default(view.getId())) {
                        AppListController controller2 = SubAppListActivity.this.getController();
                        controller2.mMenuDirector.execute(750, new PrepareMenu());
                    }
                }
            });
        }
        Context baseContext = getBaseContext();
        Intrinsics.checkNotNullExpressionValue(baseContext, "getBaseContext(...)");
        if (!FeatureManager$UiFeature.isDefaultTheme(baseContext)) {
            int color2 = getBaseContext().getColor(R.color.as_theme_button_text_color);
            getBottomButton1().setBackground(getBaseContext().getDrawable(R.drawable.as_theme_sub_app_list_bottom_btn));
            getBottomButton1().setTextColor(color2);
            getBottomButton2().setBackground(getBaseContext().getDrawable(R.drawable.as_contained_button_bg_theme));
            getBottomButton2().setTextColor(color2);
        }
        View requireViewById4 = requireViewById(R.id.contents_container);
        Intrinsics.checkNotNullExpressionValue(requireViewById4, str);
        UiUtils.setFlexibleSpacing(requireViewById4);
        addMenuProvider(new MenuProvider() { // from class: com.samsung.android.settings.analyzestorage.ui.SubAppListActivity$initCancelMenu$1
            @Override // androidx.core.view.MenuProvider
            public final void onCreateMenu(Menu menu, MenuInflater menuInflater) {
                Intrinsics.checkNotNullParameter(menu, "menu");
                Intrinsics.checkNotNullParameter(menuInflater, "menuInflater");
                menuInflater.inflate(R.menu.as_app_cache_clear_menu, menu);
            }

            @Override // androidx.core.view.MenuProvider
            public final boolean onMenuItemSelected(MenuItem menuItem) {
                Intrinsics.checkNotNullParameter(menuItem, "menuItem");
                AppListController controller2 = SubAppListActivity.this.getController();
                controller2.mMenuDirector.execute(MenuIdType.CANCEL.getMenuType(), new PrepareMenu());
                return true;
            }

            @Override // androidx.core.view.MenuProvider
            public final void onPrepareMenu(Menu menu) {
                Intrinsics.checkNotNullParameter(menu, "menu");
                SubAppListActivity subAppListActivity = SubAppListActivity.this;
                subAppListActivity.getController().mListItemHandler.mListItems.observe(subAppListActivity, new SubAppListActivity$observeLoadingView$1(1, menu));
            }
        }, this);
        EventContext.EventContextPosition eventContextPosition = EventContext.EventContextPosition.CONTENTS_PANEL;
        MyFilesRecyclerView myFilesRecyclerView3 = getListBehavior().recyclerView;
        AppListController controller2 = getController();
        AppListBehavior listBehavior2 = getListBehavior();
        SparseArray sparseArray2 = KeyboardMouseManager.sEventContextMap;
        if (controller2 != null) {
            PageInfo pageInfo = controller2.mPageInfo;
            if (myFilesRecyclerView3 != null && pageInfo != null && (pageType = pageInfo.mPageType) != null && (pageType.equals(PageType.LOCAL_INTERNAL) || pageType.equals(PageType.LOCAL_APP_CLONE) || pageType.equals(PageType.LOCAL_SDCARD) || pageType == PageType.LOCAL_USB || pageType == PageType.GOOGLE_DRIVE || pageType == PageType.ONE_DRIVE || pageType == PageType.IMAGES || pageType == PageType.AUDIO || pageType == PageType.VIDEOS || pageType == PageType.DOCUMENTS || pageType == PageType.APK || pageType == PageType.DOWNLOADS || pageType == PageType.ANALYZE_STORAGE_DUPLICATED_FILES || pageType == PageType.ANALYZE_STORAGE_LARGE_FILES || pageType == PageType.ANALYZE_STORAGE_CACHED_FILES || pageType == pageType4 || pageType == PageType.ANALYZE_STORAGE_APP_CACHE || pageType == PageType.SEARCH)) {
                SparseArray sparseArray3 = KeyboardMouseManager.sEventContextMap;
                EnumMap enumMap = (EnumMap) sparseArray3.get(1);
                if (enumMap == null) {
                    enumMap = new EnumMap(EventContext.EventContextPosition.class);
                    sparseArray3.put(1, enumMap);
                }
                EventContext eventContext = (EventContext) enumMap.get(eventContextPosition);
                if (eventContext == null) {
                    eventContext = new EventContext();
                    eventContext.mPosition = eventContextPosition;
                    enumMap.put((EnumMap) eventContextPosition, (EventContext.EventContextPosition) eventContext);
                }
                eventContext.mController = controller2;
                eventContext.mRecycleView = myFilesRecyclerView3;
                eventContext.mListBehavior = listBehavior2;
                eventContext.mIsExpandable = false;
            }
        }
        registerForContextMenu(getListBehavior().recyclerView);
        getController().mListLoading.mLoading.observe(this, new SubAppListActivity$observeLoadingView$1(0, this));
        final DetailsManager detailsManager = DetailsManager.instance;
        if (detailsManager == null) {
            detailsManager = new DetailsManager();
            DetailsManager.instance = detailsManager;
        }
        final ?? r2 = new CheckedItemsDetailsLoader.DetailsResultListener() { // from class: com.samsung.android.settings.analyzestorage.ui.SubAppListActivity$observeCheckedItemCount$listener$1
            /* JADX WARN: Removed duplicated region for block: B:21:0x00b3  */
            @Override // com.samsung.android.settings.analyzestorage.presenter.managers.detailinfo.CheckedItemsDetailsLoader.DetailsResultListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onResult(com.samsung.android.settings.analyzestorage.presenter.managers.detailinfo.CheckedItemsDetailsLoader.DirInfo r9) {
                /*
                    r8 = this;
                    com.samsung.android.settings.analyzestorage.ui.SubAppListActivity r8 = com.samsung.android.settings.analyzestorage.ui.SubAppListActivity.this
                    com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController r0 = r8.getController()
                    com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ListItemHandler r0 = r0.mListItemHandler
                    int r1 = r0.getItemCount()
                    r2 = 0
                    if (r1 <= 0) goto L13
                    long r4 = r9.mSize
                    goto L14
                L13:
                    r4 = r2
                L14:
                    android.content.Context r9 = r8.getBaseContext()
                    r1 = 0
                    java.lang.String r9 = com.samsung.android.settings.analyzestorage.presenter.utils.StringConverter.formatFileSize(r1, r4, r9)
                    androidx.lifecycle.MutableLiveData r4 = r0.mCheckItemSize
                    r4.setValue(r9)
                    java.lang.Boolean r9 = java.lang.Boolean.TRUE
                    androidx.lifecycle.MutableLiveData r5 = r0.mIsDisplayCheckItemSize
                    java.lang.Object r6 = r5.getValue()
                    boolean r6 = r9.equals(r6)
                    r7 = 1
                    if (r6 == r7) goto L34
                    r5.setValue(r9)
                L34:
                    java.lang.Object r9 = r4.getValue()
                    java.lang.String r9 = (java.lang.String) r9
                    java.lang.String r4 = "getCheckItemSize(...)"
                    kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r9, r4)
                    int r4 = r0.getCheckedItemCount()
                    if (r4 <= 0) goto L47
                    r4 = r7
                    goto L48
                L47:
                    r4 = r1
                L48:
                    r8.updateBottomButton1(r9, r4)
                    com.samsung.android.settings.analyzestorage.presenter.page.PageInfo r9 = r8.getPageInfo()
                    com.samsung.android.settings.analyzestorage.presenter.page.PageType r9 = r9.mPageType
                    r9.getClass()
                    com.samsung.android.settings.analyzestorage.presenter.page.PageType r4 = com.samsung.android.settings.analyzestorage.presenter.page.PageType.ANALYZE_STORAGE_UNUSED_APPS
                    if (r9 != r4) goto Ld1
                    com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController r9 = r8.getController()
                    android.content.Context r4 = r8.getBaseContext()
                    android.content.pm.PackageManager r4 = r4.getPackageManager()
                    com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ListItemHandler r9 = r9.mListItemHandler
                    java.util.List r5 = r9.mCheckedItemList
                    java.util.ArrayList r5 = (java.util.ArrayList) r5
                    boolean r5 = r5.isEmpty()
                    if (r5 == 0) goto L72
                L70:
                    r7 = r1
                    goto L97
                L72:
                    java.util.List r9 = r9.mCheckedItemList
                    java.util.ArrayList r9 = (java.util.ArrayList) r9
                    java.util.Iterator r9 = r9.iterator()
                L7a:
                    boolean r5 = r9.hasNext()
                    if (r5 == 0) goto L97
                    java.lang.Object r5 = r9.next()
                    com.samsung.android.settings.analyzestorage.domain.entity.AppInfo r5 = (com.samsung.android.settings.analyzestorage.domain.entity.AppInfo) r5
                    boolean r6 = r5 instanceof com.samsung.android.settings.analyzestorage.data.model.UnusedAppInfo
                    if (r6 == 0) goto L70
                    com.samsung.android.settings.analyzestorage.data.model.CommonAppInfo r5 = (com.samsung.android.settings.analyzestorage.data.model.CommonAppInfo) r5
                    java.lang.String r5 = r5.getPackageName()
                    boolean r5 = com.samsung.android.settings.analyzestorage.presenter.managers.ArchiveManager.isArchivalApp(r4, r5)
                    if (r5 != 0) goto L7a
                    goto L70
                L97:
                    if (r7 == 0) goto Lc6
                    int r9 = r0.getItemCount()
                    if (r9 <= 0) goto Lc6
                    com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController r9 = r8.getController()
                    com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ListItemHandler r9 = r9.mListItemHandler
                    java.util.List r9 = r9.mCheckedItemList
                    java.util.ArrayList r9 = (java.util.ArrayList) r9
                    java.util.Iterator r9 = r9.iterator()
                Lad:
                    boolean r0 = r9.hasNext()
                    if (r0 == 0) goto Lc6
                    java.lang.Object r0 = r9.next()
                    com.samsung.android.settings.analyzestorage.domain.entity.AppInfo r0 = (com.samsung.android.settings.analyzestorage.domain.entity.AppInfo) r0
                    boolean r4 = r0 instanceof com.samsung.android.settings.analyzestorage.data.model.UnusedAppInfo
                    if (r4 == 0) goto Lad
                    com.samsung.android.settings.analyzestorage.data.model.UnusedAppInfo r0 = (com.samsung.android.settings.analyzestorage.data.model.UnusedAppInfo) r0
                    long r4 = r0.getPackageAppByte()
                    long r4 = r4 + r2
                    r2 = r4
                    goto Lad
                Lc6:
                    android.content.Context r9 = r8.getBaseContext()
                    java.lang.String r9 = com.samsung.android.settings.analyzestorage.presenter.utils.StringConverter.formatFileSize(r1, r2, r9)
                    r8.updateBottomButton2(r9, r7)
                Ld1:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settings.analyzestorage.ui.SubAppListActivity$observeCheckedItemCount$listener$1.onResult(com.samsung.android.settings.analyzestorage.presenter.managers.detailinfo.CheckedItemsDetailsLoader$DirInfo):void");
            }
        };
        getController().mListItemHandler.mCheckedItemCount.observe(this, new Observer() { // from class: com.samsung.android.settings.analyzestorage.ui.SubAppListActivity$observeCheckedItemCount$1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SubAppListActivity subAppListActivity = this;
                String activity = subAppListActivity.toString();
                DetailsManager detailsManager2 = DetailsManager.this;
                detailsManager2.getClass();
                Log.d("DetailsManager", "cancelCheckedItemsLoader() ");
                CheckedItemsDetailsLoader checkedItemsDetailsLoader = detailsManager2.checkedItemLoader;
                checkedItemsDetailsLoader.mIsCancel = true;
                Object obj2 = ((HashMap) checkedItemsDetailsLoader.mListenerMap).get(activity);
                CheckedItemsDetailsLoader.DetailsResultListener detailsResultListener = r2;
                if (obj2 != null && ((List) ((HashMap) checkedItemsDetailsLoader.mListenerMap).get(activity)).contains(detailsResultListener)) {
                    ((CopyOnWriteArrayList) checkedItemsDetailsLoader.mListenerList).remove(detailsResultListener);
                }
                Context baseContext2 = subAppListActivity.getBaseContext();
                PageInfo pageInfo2 = subAppListActivity.getPageInfo();
                List list = subAppListActivity.getController().mListItemHandler.mCheckedItemList;
                if (!(list instanceof List)) {
                    list = null;
                }
                CheckedItemsDetailsLoader.DataInfoTaskItem dataInfoTaskItem = new CheckedItemsDetailsLoader.DataInfoTaskItem(baseContext2, pageInfo2, list, (SubAppListActivity$observeCheckedItemCount$listener$1) detailsResultListener);
                Log.d("CheckedItemsDetailsLoader", "createFileInfoTask ");
                checkedItemsDetailsLoader.mIsCancel = false;
                AbsDetailsInfoLoader$LoadHandler absDetailsInfoLoader$LoadHandler = checkedItemsDetailsLoader.mLoadHandler;
                if (absDetailsInfoLoader$LoadHandler != null) {
                    absDetailsInfoLoader$LoadHandler.sendMessageAtFrontOfQueue(absDetailsInfoLoader$LoadHandler.obtainMessage(0, dataInfoTaskItem));
                }
            }
        });
        UiUtils.initStatusBar(this);
        MenuExecuteManager.sCallbackListener.put(this.resultCallback, Boolean.FALSE);
        ArrayList<String> stringArrayListExtra = getIntent().getStringArrayListExtra("archive_app_package_name_list");
        if (getController().mSaAppsType != 4 || stringArrayListExtra == null || stringArrayListExtra.isEmpty()) {
            return;
        }
        getController().selectedAppPackageNameList.addAll(stringArrayListExtra);
    }

    @Override // android.app.Activity, android.view.View.OnCreateContextMenuListener
    public final void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        if (getController().mListItemHandler.getCheckedItemCount() > 0) {
            getMenuInflater().inflate(R.menu.as_app_cache_clear_menu, contextMenu);
            ArrayList arrayList = new ArrayList();
            if (getPageInfo().mPageType == PageType.ANALYZE_STORAGE_APP_CACHE) {
                arrayList.add(Integer.valueOf(MenuIdType.CLEAR_APP_CACHE.getMenuId()));
            } else {
                arrayList.add(Integer.valueOf(MenuIdType.UNINSTALL.getMenuId()));
            }
            if (!(!arrayList.isEmpty()) || contextMenu == null) {
                return;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                MenuItem findItem = contextMenu.findItem(((Number) it.next()).intValue());
                if (findItem != null) {
                    findItem.setVisible(true);
                }
            }
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        DetailsManager detailsManager = DetailsManager.instance;
        if (detailsManager != null) {
            CheckedItemsDetailsLoader checkedItemsDetailsLoader = detailsManager.checkedItemLoader;
            checkedItemsDetailsLoader.mLoadHandler.getLooper().quit();
            checkedItemsDetailsLoader.mHandlerThread.quit();
        }
        DetailsManager.instance = null;
        Context context = ActivityInfoStore.context;
        ((ArrayList) ActivityInfoStore.Companion.getInstance(this.instanceId).activityListenerList).remove(this);
        MenuExecuteManager.sCallbackListener.remove(this.resultCallback);
        MyFilesRecyclerView myFilesRecyclerView = getListBehavior().recyclerView;
        if (myFilesRecyclerView != null) {
            unregisterForContextMenu(myFilesRecyclerView);
        }
    }

    @Override // android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        LoggingHelper.insertEventLogging(getPageInfo().mPageType.getScreenIdForSA(), getPageInfo().mPageType == PageType.ANALYZE_STORAGE_APP_CACHE ? 8891 : 8894);
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }

    public final void updateBottomButton1(String str, boolean z) {
        updateBottomButtonWidth(getBottomButton1());
        Button bottomButton1 = getBottomButton1();
        boolean z2 = getController().mListItemHandler.getCheckedItemCount() > 0;
        bottomButton1.setText(getPageInfo().mPageType == PageType.ANALYZE_STORAGE_UNUSED_APPS ? getButtonStr(R.string.as_menu_uninstall, R.plurals.as_uninstall_button_text, str, z2) : getButtonStr(R.string.as_clear, R.plurals.as_clear_button_text, str, z2));
        Button bottomButton12 = getBottomButton1();
        if (bottomButton12 != null) {
            bottomButton12.setEnabled(z);
            bottomButton12.setAlpha(z ? 1.0f : 0.4f);
        }
    }

    public final void updateBottomButton2(String str, boolean z) {
        PageType pageType = getPageInfo().mPageType;
        pageType.getClass();
        if (pageType == PageType.ANALYZE_STORAGE_UNUSED_APPS) {
            updateBottomButtonWidth(getBottomButton2());
            getBottomButton2().setText(getButtonStr(R.string.as_menu_archive, R.plurals.as_archive_button_text, str, z));
            Button bottomButton2 = getBottomButton2();
            if (bottomButton2 != null) {
                bottomButton2.setEnabled(z);
                bottomButton2.setAlpha(z ? 1.0f : 0.4f);
            }
        }
    }

    public final void updateBottomButtonWidth(final Button button) {
        if (button.getVisibility() == 8) {
            return;
        }
        button.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.samsung.android.settings.analyzestorage.ui.SubAppListActivity$updateBottomButtonWidth$1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                button.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                boolean z = button.getContext().getResources().getConfiguration().smallestScreenWidthDp >= 600;
                int i = z ? R.dimen.as_basic_contained_button_horizontal_min_width : R.dimen.as_basic_contained_button_min_width;
                float f = z ? 0.6f : 0.8f;
                Button button2 = button;
                button2.setMinWidth(button2.getContext().getResources().getDimensionPixelSize(i));
                if (((View) this.listview$delegate.getValue()).getWidth() > 0) {
                    button.setMaxWidth((int) (((View) this.listview$delegate.getValue()).getWidth() * f));
                }
            }
        });
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.ActivityInfo$ActivityInfoListener
    public final Activity getActivity() {
        return this;
    }
}

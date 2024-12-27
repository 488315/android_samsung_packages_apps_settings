package com.samsung.android.settings.share;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelProviderImpl;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.picker.model.AppInfo;
import androidx.picker.model.AppInfoDataImpl;
import androidx.picker.widget.AppPickerEvent$OnItemClickEventListener;
import androidx.picker.widget.AppPickerState$OnStateChangeListener;
import androidx.picker.widget.SeslAppPickerSelectLayout;
import androidx.picker.widget.SeslAppPickerSelectLayout$$ExternalSyntheticLambda7;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.share.common.ShareUtil;
import com.samsung.android.settings.share.common.ShareUtil$$ExternalSyntheticLambda0;
import com.samsung.android.settings.share.data.ShareDBAdapter;
import com.samsung.android.settings.share.structure.CallerComponent;
import com.samsung.android.settings.share.structure.DBShareComponent;
import com.samsung.android.settings.share.structure.ShareComponent;

import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SelectAppActivity extends AppCompatActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ShareDBAdapter mDBAdapter;
    public List mDBOriginalList;
    public Pair mInitialListPair;
    public Boolean mIsPortrait;
    public boolean mIsSearchEnabled;
    public Menu mMenuLandscape;
    public Menu mOptionsMenu;
    public ActionMenuView mPortraitMenuView;
    public ConstraintLayout mPortraitMenuViewWrapper;
    public CheckBox mSelectAllCheckBox;
    public View mSelectAllView;
    public SeslAppPickerSelectLayout mSelectLayout;
    public TextView mSelectNoApp;
    public List mSourcesIntentList;
    public Toolbar mToolbar;
    public SelectAppViewModel mViewModel;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.share.SelectAppActivity$2, reason: invalid class name */
    public final class AnonymousClass2 implements AppPickerEvent$OnItemClickEventListener {
        @Override // androidx.picker.widget.AppPickerEvent$OnItemClickEventListener
        public final boolean onClick(View view, AppInfo appInfo) {
            return false;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.share.SelectAppActivity$3, reason: invalid class name */
    public final class AnonymousClass3
            implements AppPickerState$OnStateChangeListener, SearchView.OnQueryTextListener {
        public /* synthetic */ AnonymousClass3() {}

        @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
        public boolean onQueryTextChange(String str) {
            SeslAppPickerSelectLayout seslAppPickerSelectLayout =
                    SelectAppActivity.this.mSelectLayout;
            seslAppPickerSelectLayout.mAppPickerStateView.setSearchFilter(
                    str, seslAppPickerSelectLayout.mOnSearchFilterListenerForLayout);
            return false;
        }

        @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
        public void onQueryTextSubmit(String str) {
            SeslAppPickerSelectLayout seslAppPickerSelectLayout =
                    SelectAppActivity.this.mSelectLayout;
            seslAppPickerSelectLayout.mAppPickerStateView.setSearchFilter(
                    str, seslAppPickerSelectLayout.mOnSearchFilterListenerForLayout);
        }

        @Override // androidx.picker.widget.AppPickerState$OnStateChangeListener
        public void onStateAllChanged(boolean z) {
            SelectAppActivity selectAppActivity = SelectAppActivity.this;
            SelectAppViewModel selectAppViewModel = selectAppActivity.mViewModel;
            if (z) {
                selectAppViewModel.mFavoriteComponentList.clear();
                selectAppViewModel.mFavoriteComponentList.addAll(
                        (Collection) selectAppViewModel.mInitialListPair.first);
                selectAppViewModel.mFavoriteComponentList.addAll(
                        (Collection) selectAppViewModel.mInitialListPair.second);
                if (((ShareComponent) selectAppViewModel.mFavoriteComponentList.get(0))
                        .mComponentName
                        .getPackageName()
                        .equals("com.samsung.android.app.sharelive")) {
                    SelectAppViewModel.quickShareShareComponent =
                            (ShareComponent) selectAppViewModel.mFavoriteComponentList.get(0);
                }
                selectAppViewModel.mIsAllChecked = true;
            } else {
                selectAppViewModel.mFavoriteComponentList.clear();
                selectAppViewModel.mFavoriteComponentList.add(
                        SelectAppViewModel.quickShareShareComponent);
                selectAppViewModel.mIsAllChecked = false;
            }
            selectAppActivity.updateSelectCount$1();
        }

        @Override // androidx.picker.widget.AppPickerState$OnStateChangeListener
        public void onStateChanged(AppInfo appInfo, boolean z) {
            SelectAppActivity selectAppActivity = SelectAppActivity.this;
            SelectAppViewModel selectAppViewModel = selectAppActivity.mViewModel;
            ShareComponent shareComponent =
                    (ShareComponent)
                            ((HashMap) selectAppViewModel.mSharedComponentMap).get(appInfo);
            if (z) {
                selectAppViewModel.mFavoriteComponentList.add(shareComponent);
                selectAppViewModel.mIsAllChecked =
                        ((List) selectAppViewModel.mInitialListPair.second).size()
                                        + ((List) selectAppViewModel.mInitialListPair.first).size()
                                == selectAppViewModel.mFavoriteComponentList.size();
            } else {
                selectAppViewModel.mFavoriteComponentList.remove(shareComponent);
                selectAppViewModel.mIsAllChecked = false;
            }
            selectAppActivity.updateSelectCount$1();
            selectAppActivity.updateSelectAllCheckBox();
        }
    }

    public SelectAppActivity() {
        new ArrayList();
        new ArrayList();
        Collections.emptyMap();
        this.mSourcesIntentList = Collections.emptyList();
    }

    public final void doRevert() {
        SelectAppViewModel selectAppViewModel = this.mViewModel;
        if (selectAppViewModel != null) {
            selectAppViewModel.modifiedLiveData.postValue(Boolean.FALSE);
            SelectAppViewModel selectAppViewModel2 = this.mViewModel;
            selectAppViewModel2.mFavoriteComponentList.clear();
            selectAppViewModel2.mFavoriteComponentList.addAll(
                    (Collection) selectAppViewModel2.mInitialListPair.first);
            selectAppViewModel2.mOtherComponentList.clear();
            selectAppViewModel2.mOtherComponentList.addAll(
                    (Collection) selectAppViewModel2.mInitialListPair.second);
            selectAppViewModel2.mOtherComponentList.addAll(
                    (Collection) selectAppViewModel2.mInitialListPair.first);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onBackPressed() {
        doRevert();
        super.onBackPressed();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity,
              // android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mIsPortrait = Boolean.valueOf(getResources().getConfiguration().orientation == 1);
        updateEditMenuVisibility$1();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        ArrayList parcelableArrayListExtra =
                intent.getParcelableArrayListExtra("android.intent.extra.INTENT", Intent.class);
        new ArrayList();
        List list =
                (List)
                        Optional.of(parcelableArrayListExtra)
                                .filter(new SelectAppActivity$$ExternalSyntheticLambda2(1))
                                .orElse(Collections.singletonList(new Intent()));
        Log.i("share_SelectApp", "mSourcesIntentList : " + list);
        this.mSourcesIntentList = list;
        final List parseJsonArray =
                ShareUtil.parseJsonArray(intent.getStringExtra("extra_app_list"));
        Log.i("ShareUtil", "getShareComponentsFromIntent() is " + parseJsonArray.toString());
        String stringExtra = intent.getStringExtra("extra_caller_target_list");
        ArrayList parcelableArrayListExtra2 =
                intent.getParcelableArrayListExtra(
                        "extra_caller_resolveinfo_list", ResolveInfo.class);
        Log.i("ShareUtil", "INTENT_EXTRA_CALLER_RESOLVE_INFO_LIST is " + parcelableArrayListExtra2);
        final Optional map =
                Optional.ofNullable(parcelableArrayListExtra2)
                        .map(new ShareUtil$$ExternalSyntheticLambda0(0));
        List list2 =
                (List)
                        ShareUtil.parseJsonArray(stringExtra).stream()
                                .map(
                                        new Function() { // from class:
                                                         // com.samsung.android.settings.share.common.ShareUtil$$ExternalSyntheticLambda1
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                Optional optional = map;
                                                ShareComponent shareComponent =
                                                        (ShareComponent) obj;
                                                ComponentName componentName =
                                                        shareComponent.mComponentName;
                                                int i = shareComponent.mUserId;
                                                return new CallerComponent(componentName, i, null);
                                            }
                                        })
                                .collect(Collectors.toList());
        Log.i("ShareUtil", "getCallerComponentsFromIntent() is. " + list2);
        ShareDBAdapter shareDBAdapter = new ShareDBAdapter(this);
        this.mDBAdapter = shareDBAdapter;
        shareDBAdapter.open();
        final int i = 1;
        List<DBShareComponent> list3 =
                (List)
                        this.mDBAdapter.getAllRankedLabelComponents().stream()
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.samsung.android.settings.share.SelectAppActivity$$ExternalSyntheticLambda7
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                int i2 = i;
                                                Object obj2 = parseJsonArray;
                                                switch (i2) {
                                                    case 0:
                                                        int i3 = SelectAppActivity.$r8$clinit;
                                                        return !((ArrayList) obj2)
                                                                .contains((ShareComponent) obj);
                                                    case 1:
                                                        return ((List) obj2)
                                                                .contains((DBShareComponent) obj);
                                                    default:
                                                        return ((DBShareComponent) obj2)
                                                                .equals((ShareComponent) obj);
                                                }
                                            }
                                        })
                                .collect(Collectors.toList());
        this.mDBAdapter.close();
        for (final DBShareComponent dBShareComponent : list3) {
            Stream stream = parseJsonArray.stream();
            Objects.requireNonNull(dBShareComponent);
            final int i2 = 2;
            stream.filter(
                            new Predicate() { // from class:
                                              // com.samsung.android.settings.share.SelectAppActivity$$ExternalSyntheticLambda7
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    int i22 = i2;
                                    Object obj2 = dBShareComponent;
                                    switch (i22) {
                                        case 0:
                                            int i3 = SelectAppActivity.$r8$clinit;
                                            return !((ArrayList) obj2)
                                                    .contains((ShareComponent) obj);
                                        case 1:
                                            return ((List) obj2).contains((DBShareComponent) obj);
                                        default:
                                            return ((DBShareComponent) obj2)
                                                    .equals((ShareComponent) obj);
                                    }
                                }
                            })
                    .findFirst()
                    .ifPresent(
                            new Consumer() { // from class:
                                             // com.samsung.android.settings.share.SelectAppActivity$$ExternalSyntheticLambda13
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    DBShareComponent dBShareComponent2 = DBShareComponent.this;
                                    int i3 = SelectAppActivity.$r8$clinit;
                                    Pair pair = ((ShareComponent) obj).mLabelPair;
                                    if (pair == null) {
                                        pair =
                                                Pair.create(
                                                        ApnSettings.MVNO_NONE,
                                                        ApnSettings.MVNO_NONE);
                                    }
                                    dBShareComponent2.mLabelPair = pair;
                                }
                            });
        }
        this.mDBOriginalList = Collections.unmodifiableList(list3);
        ViewModelStore store = getViewModelStore();
        ViewModelProvider.Factory factory = getDefaultViewModelProviderFactory();
        CreationExtras defaultViewModelCreationExtras = getDefaultViewModelCreationExtras();
        Intrinsics.checkNotNullParameter(store, "store");
        Intrinsics.checkNotNullParameter(factory, "factory");
        ViewModelProviderImpl viewModelProviderImpl =
                new ViewModelProviderImpl(store, factory, defaultViewModelCreationExtras);
        KClass kotlinClass = JvmClassMappingKt.getKotlinClass(SelectAppViewModel.class);
        String qualifiedName = kotlinClass.getQualifiedName();
        if (qualifiedName == null) {
            throw new IllegalArgumentException(
                    "Local and anonymous classes can not be ViewModels".toString());
        }
        SelectAppViewModel selectAppViewModel =
                (SelectAppViewModel)
                        viewModelProviderImpl.getViewModel$lifecycle_viewmodel_release(
                                kotlinClass,
                                "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                        .concat(qualifiedName));
        this.mViewModel = selectAppViewModel;
        if (bundle == null) {
            final ArrayList arrayList = new ArrayList(list3);
            arrayList.addAll(0, list2);
            final int i3 = 0;
            Pair create =
                    Pair.create(
                            Collections.unmodifiableList(arrayList),
                            Collections.unmodifiableList(
                                    (List)
                                            parseJsonArray.stream()
                                                    .distinct()
                                                    .filter(
                                                            new Predicate() { // from class:
                                                                              // com.samsung.android.settings.share.SelectAppActivity$$ExternalSyntheticLambda7
                                                                @Override // java.util.function.Predicate
                                                                public final boolean test(
                                                                        Object obj) {
                                                                    int i22 = i3;
                                                                    Object obj2 = arrayList;
                                                                    switch (i22) {
                                                                        case 0:
                                                                            int i32 =
                                                                                    SelectAppActivity
                                                                                            .$r8$clinit;
                                                                            return !((ArrayList)
                                                                                            obj2)
                                                                                    .contains(
                                                                                            (ShareComponent)
                                                                                                    obj);
                                                                        case 1:
                                                                            return ((List) obj2)
                                                                                    .contains(
                                                                                            (DBShareComponent)
                                                                                                    obj);
                                                                        default:
                                                                            return ((DBShareComponent)
                                                                                            obj2)
                                                                                    .equals(
                                                                                            (ShareComponent)
                                                                                                    obj);
                                                                    }
                                                                }
                                                            })
                                                    .collect(Collectors.toList())));
            this.mInitialListPair = create;
            SelectAppViewModel selectAppViewModel2 = this.mViewModel;
            selectAppViewModel2.mInitialListPair = create;
            selectAppViewModel2.mFavoriteComponentList =
                    new ArrayList((Collection) this.mInitialListPair.first);
            this.mViewModel.mOtherComponentList =
                    new ArrayList((Collection) this.mInitialListPair.second);
            new ArrayList((Collection) this.mInitialListPair.second);
        } else {
            SelectAppActivity$$ExternalSyntheticLambda0
                    selectAppActivity$$ExternalSyntheticLambda0 =
                            new SelectAppActivity$$ExternalSyntheticLambda0(bundle);
            selectAppViewModel.mFavoriteComponentList =
                    (ArrayList)
                            selectAppActivity$$ExternalSyntheticLambda0.apply(
                                    "instance_key_favorites");
            this.mViewModel.mOtherComponentList =
                    (ArrayList)
                            selectAppActivity$$ExternalSyntheticLambda0.apply(
                                    "instance_key_others");
            Pair create2 =
                    Pair.create(
                            Collections.unmodifiableList(
                                    (List)
                                            selectAppActivity$$ExternalSyntheticLambda0.apply(
                                                    "instance_key_initial_favorites")),
                            Collections.unmodifiableList(
                                    (List)
                                            selectAppActivity$$ExternalSyntheticLambda0.apply(
                                                    "instance_key_initial_others")));
            this.mInitialListPair = create2;
            SelectAppViewModel selectAppViewModel3 = this.mViewModel;
            selectAppViewModel3.mInitialListPair = create2;
            selectAppViewModel3.modifiedLiveData.postValue(
                    Boolean.valueOf(bundle.getBoolean("instance_key_modified")));
        }
        ((ArrayList) this.mViewModel.mSourcesIntentList).addAll(this.mSourcesIntentList);
        SelectAppViewModel selectAppViewModel4 = this.mViewModel;
        ArrayList arrayList2 = new ArrayList(list2);
        Context applicationContext = getApplicationContext();
        selectAppViewModel4.getClass();
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            CallerComponent callerComponent = (CallerComponent) it.next();
            if (!selectAppViewModel4.mAppItemPackageName.contains(
                    callerComponent.mComponentName.getClassName())) {
                AppInfoDataImpl makeAppData =
                        selectAppViewModel4.makeAppData(callerComponent, applicationContext);
                makeAppData.selected = true;
                makeAppData.dimmed = true;
                selectAppViewModel4.mAppItems.add(makeAppData);
            }
        }
        this.mIsPortrait = Boolean.valueOf(getResources().getConfiguration().orientation == 1);
        setContentView(R.layout.sec_share_activity_main_layout);
        this.mSelectNoApp = (TextView) findViewById(R.id.shared_other_list_label);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.mToolbar = toolbar;
        int color = getColor(R.color.sec_share_type_text);
        toolbar.getClass();
        ColorStateList valueOf = ColorStateList.valueOf(color);
        toolbar.mTitleTextColor = valueOf;
        AppCompatTextView appCompatTextView = toolbar.mTitleTextView;
        if (appCompatTextView != null) {
            appCompatTextView.setTextColor(valueOf);
        }
        setSupportActionBar(this.mToolbar);
        this.mToolbar.setBackInvokedCallbackEnabled(false);
        getOnBackInvokedDispatcher()
                .registerSystemOnBackInvokedCallback(
                        new OnBackInvokedCallback() { // from class:
                                                      // com.samsung.android.settings.share.SelectAppActivity.1
                            @Override // android.window.OnBackInvokedCallback
                            public final void onBackInvoked() {
                                MenuItem findItem;
                                SelectAppActivity selectAppActivity = SelectAppActivity.this;
                                if (!selectAppActivity.mIsSearchEnabled) {
                                    selectAppActivity.finish();
                                    return;
                                }
                                Menu menu = selectAppActivity.mOptionsMenu;
                                if (menu == null
                                        || (findItem = menu.findItem(R.id.menu_search)) == null) {
                                    return;
                                }
                                findItem.collapseActionView();
                                SelectAppActivity selectAppActivity2 = SelectAppActivity.this;
                                selectAppActivity2.mIsSearchEnabled = false;
                                selectAppActivity2.mSelectNoApp.setVisibility(0);
                            }
                        });
        this.mPortraitMenuViewWrapper =
                (ConstraintLayout) findViewById(R.id.edit_appbar_portrait_wrapper);
        this.mPortraitMenuView = (ActionMenuView) findViewById(R.id.edit_appbar_portrait);
        getMenuInflater().inflate(R.menu.sec_share_edit_menu, this.mPortraitMenuView.getMenu());
        this.mPortraitMenuView.mOnMenuItemClickListener =
                new SelectAppActivity$$ExternalSyntheticLambda0(this);
        SeslAppPickerSelectLayout seslAppPickerSelectLayout =
                (SeslAppPickerSelectLayout) findViewById(R.id.select_app_activity_main);
        this.mSelectLayout = seslAppPickerSelectLayout;
        seslAppPickerSelectLayout.mAppPickerStateView.setAppListOrder(2);
        this.mSelectLayout.enableSelectedAppPickerView(true);
        this.mSelectLayout.mAppPickerStateView.setOnItemClickEventListener(new AnonymousClass2());
        this.mSelectLayout.mOnStateChangeListener = new AnonymousClass3();
        this.mSelectAllView = findViewById(R.id.select_all_wrapper);
        this.mSelectAllCheckBox = (CheckBox) findViewById(R.id.select_all_checkbox);
        updateSelectAllCheckBox();
        this.mSelectAllView.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.share.SelectAppActivity$$ExternalSyntheticLambda6
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        SelectAppActivity selectAppActivity = SelectAppActivity.this;
                        selectAppActivity.mSelectLayout.setStateAll(
                                !selectAppActivity.mSelectAllCheckBox.isChecked());
                        selectAppActivity.updateSelectAllCheckBox();
                    }
                });
        updateSelectCount$1();
        SeslAppPickerSelectLayout seslAppPickerSelectLayout2 = this.mSelectLayout;
        String string = getResources().getString(R.string.sec_share_favorites_apps);
        seslAppPickerSelectLayout2.mSelectedViewTitleView.setVisibility(
                TextUtils.isEmpty(string) ? 8 : 0);
        seslAppPickerSelectLayout2.mSelectedViewTitleView.setText(string);
        SeslAppPickerSelectLayout seslAppPickerSelectLayout3 = this.mSelectLayout;
        String string2 = getResources().getString(R.string.sec_share_other_apps);
        seslAppPickerSelectLayout3.getClass();
        boolean z = string2 != null;
        seslAppPickerSelectLayout3.mIsMainViewTitleCustomized = z;
        seslAppPickerSelectLayout3.mMainViewTitleView.setText(
                z
                        ? string2
                        : seslAppPickerSelectLayout3
                                .getContext()
                                .getResources()
                                .getText(R.string.title_all_apps));
        seslAppPickerSelectLayout3.mMainViewTitleView.post(
                new SeslAppPickerSelectLayout$$ExternalSyntheticLambda7(
                        0, seslAppPickerSelectLayout3, string2));
        SeslAppPickerSelectLayout seslAppPickerSelectLayout4 = this.mSelectLayout;
        SelectAppViewModel selectAppViewModel5 = this.mViewModel;
        Context applicationContext2 = getApplicationContext();
        Iterator it2 = selectAppViewModel5.mOtherComponentList.iterator();
        while (it2.hasNext()) {
            ShareComponent shareComponent = (ShareComponent) it2.next();
            if (!selectAppViewModel5.mAppItemPackageName.contains(
                    shareComponent.mComponentName.getClassName())) {
                selectAppViewModel5.mAppItems.add(
                        selectAppViewModel5.makeAppData(shareComponent, applicationContext2));
            }
        }
        Iterator it3 = selectAppViewModel5.mFavoriteComponentList.iterator();
        while (it3.hasNext()) {
            ShareComponent shareComponent2 = (ShareComponent) it3.next();
            if (!selectAppViewModel5.mAppItemPackageName.contains(
                    shareComponent2.mComponentName.getClassName())) {
                AppInfoDataImpl makeAppData2 =
                        selectAppViewModel5.makeAppData(shareComponent2, applicationContext2);
                makeAppData2.selected = true;
                selectAppViewModel5.mAppItems.add(makeAppData2);
            }
        }
        seslAppPickerSelectLayout4.submitList(selectAppViewModel5.mAppItems.stream().toList());
    }

    @Override // android.app.Activity
    public final boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sec_share_menu_search, menu);
        this.mOptionsMenu = menu;
        menu.findItem(R.id.menu_search)
                .getIcon()
                .setColorFilter(
                        getResources().getColor(R.color.sec_search_magnifier_icon_tint_color),
                        PorterDuff.Mode.SRC_IN);
        getMenuInflater().inflate(R.menu.sec_share_edit_menu, menu);
        this.mMenuLandscape = menu;
        updateEditMenuVisibility$1();
        return super.onCreateOptionsMenu(menu);
    }

    @Override // android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.menu_search) {
            SearchView searchView = (SearchView) menuItem.getActionView();
            this.mIsSearchEnabled = true;
            this.mSelectNoApp.setVisibility(8);
            searchView.mOnQueryChangeListener = new AnonymousClass3();
            searchView.mOnQueryTextFocusChangeListener =
                    new View
                            .OnFocusChangeListener() { // from class:
                                                       // com.samsung.android.settings.share.SelectAppActivity.5
                        @Override // android.view.View.OnFocusChangeListener
                        public final void onFocusChange(View view, boolean z) {
                            if (z) {
                                return;
                            }
                            SelectAppActivity selectAppActivity = SelectAppActivity.this;
                            int i = SelectAppActivity.$r8$clinit;
                            selectAppActivity.getClass();
                            InputMethodManager inputMethodManager =
                                    InputMethodManager.getInstance();
                            if (view == null) {
                                inputMethodManager.semForceHideSoftInput();
                            } else {
                                inputMethodManager.hideSoftInputFromWindow(
                                        view.getWindowToken(), 2);
                            }
                        }
                    };
        }
        return selectEditMenuItem$1(menuItem) || super.onOptionsItemSelected(menuItem);
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelableArray(
                "instance_key_favorites",
                (Parcelable[])
                        this.mViewModel.mFavoriteComponentList.toArray(new ShareComponent[0]));
        bundle.putParcelableArray(
                "instance_key_others",
                (Parcelable[]) this.mViewModel.mOtherComponentList.toArray(new ShareComponent[0]));
        Pair pair = this.mViewModel.mInitialListPair;
        this.mInitialListPair = pair;
        bundle.putParcelableArray(
                "instance_key_initial_favorites",
                (Parcelable[]) ((List) pair.first).toArray(new ShareComponent[0]));
        bundle.putParcelableArray(
                "instance_key_initial_others",
                (Parcelable[])
                        ((List) this.mInitialListPair.second).toArray(new ShareComponent[0]));
        bundle.putBoolean(
                "instance_key_modified",
                Boolean.TRUE.equals(
                        Optional.ofNullable(this.mViewModel)
                                .map(new SelectAppActivity$$ExternalSyntheticLambda1(0))
                                .orElse(Boolean.FALSE)));
    }

    public final boolean selectEditMenuItem$1(MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.action_done) {
            if (menuItem.getItemId() != R.id.action_cancel) {
                return false;
            }
            doRevert();
            finish();
            return true;
        }
        final List list =
                (List)
                        this.mViewModel.mFavoriteComponentList.stream()
                                .filter(new SelectAppActivity$$ExternalSyntheticLambda2(0))
                                .collect(Collectors.toList());
        if (!(this.mDBOriginalList.size() == list.size()
                ? IntStream.range(0, this.mDBOriginalList.size())
                        .allMatch(
                                new IntPredicate() { // from class:
                                                     // com.samsung.android.settings.share.SelectAppActivity$$ExternalSyntheticLambda4
                                    @Override // java.util.function.IntPredicate
                                    public final boolean test(int i) {
                                        return ((ShareComponent)
                                                        SelectAppActivity.this.mDBOriginalList.get(
                                                                i))
                                                .equals((ShareComponent) list.get(i));
                                    }
                                })
                : false)) {
            ShareDBAdapter shareDBAdapter = this.mDBAdapter;
            Consumer consumer = new Consumer() { // from class:
                        // com.samsung.android.settings.share.SelectAppActivity$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            final SelectAppActivity selectAppActivity = SelectAppActivity.this;
                            List list2 = list;
                            final SQLiteDatabase sQLiteDatabase = (SQLiteDatabase) obj;
                            int i = SelectAppActivity.$r8$clinit;
                            selectAppActivity.getClass();
                            final Iterator it = list2.iterator();
                            selectAppActivity.mDBOriginalList.stream()
                                    .mapToInt(new SelectAppActivity$$ExternalSyntheticLambda14())
                                    .forEach(
                                            new IntConsumer() { // from class:
                                                // com.samsung.android.settings.share.SelectAppActivity$$ExternalSyntheticLambda15
                                                @Override // java.util.function.IntConsumer
                                                public final void accept(int i2) {
                                                    SelectAppActivity selectAppActivity2 =
                                                            SelectAppActivity.this;
                                                    Iterator it2 = it;
                                                    SQLiteDatabase sQLiteDatabase2 = sQLiteDatabase;
                                                    int i3 = SelectAppActivity.$r8$clinit;
                                                    selectAppActivity2.getClass();
                                                    if (!it2.hasNext()) {
                                                        selectAppActivity2.mDBAdapter.getClass();
                                                        if (sQLiteDatabase2.delete(
                                                                        "ranked_apps",
                                                                        "id= ?",
                                                                        new String[] {
                                                                            i2
                                                                                    + ApnSettings
                                                                                            .MVNO_NONE
                                                                        })
                                                                == 0) {
                                                            MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0
                                                                    .m(
                                                                            i2,
                                                                            "[Failed to delete] not"
                                                                                + " found on db :"
                                                                                + " idx(",
                                                                            ")",
                                                                            "ShareDBAdapter");
                                                            return;
                                                        }
                                                        return;
                                                    }
                                                    ShareDBAdapter shareDBAdapter2 =
                                                            selectAppActivity2.mDBAdapter;
                                                    ShareComponent shareComponent =
                                                            (ShareComponent) it2.next();
                                                    shareDBAdapter2.getClass();
                                                    ContentValues contentValues =
                                                            new ContentValues();
                                                    contentValues.put(
                                                            "package_name",
                                                            shareComponent.mComponentName
                                                                    .getPackageName());
                                                    contentValues.put(
                                                            "activity_name",
                                                            shareComponent.mComponentName
                                                                    .getClassName());
                                                    contentValues.put(
                                                            "label", ApnSettings.MVNO_NONE);
                                                    contentValues.put(
                                                            "userId",
                                                            Integer.valueOf(
                                                                    shareComponent.mUserId));
                                                    sQLiteDatabase2.update(
                                                            "ranked_apps",
                                                            contentValues,
                                                            "id = ?",
                                                            new String[] {
                                                                i2 + ApnSettings.MVNO_NONE
                                                            });
                                                }
                                            });
                            while (it.hasNext()) {
                                ShareDBAdapter shareDBAdapter2 = selectAppActivity.mDBAdapter;
                                ShareComponent shareComponent = (ShareComponent) it.next();
                                shareDBAdapter2.getClass();
                                Cursor query =
                                        sQLiteDatabase.query(
                                                "ranked_apps",
                                                new String[] {"package_name"},
                                                "package_name",
                                                null,
                                                null,
                                                null,
                                                null,
                                                null);
                                try {
                                    if (query.getCount() >= 1024) {
                                        query.close();
                                    } else {
                                        query.close();
                                        query =
                                                sQLiteDatabase.query(
                                                        "ranked_apps",
                                                        new String[] {"package_name"},
                                                        "package_name = ? AND activity_name = ? AND"
                                                            + " userId = ?",
                                                        new String[] {
                                                            shareComponent.mComponentName
                                                                    .getPackageName(),
                                                            shareComponent.mComponentName
                                                                    .getClassName(),
                                                            String.valueOf(shareComponent.mUserId)
                                                        },
                                                        null,
                                                        null,
                                                        null,
                                                        null);
                                        try {
                                            if (query.getCount() >= 1) {
                                                query.close();
                                            } else {
                                                query.close();
                                                ContentValues contentValues = new ContentValues();
                                                contentValues.put(
                                                        "package_name",
                                                        shareComponent.mComponentName
                                                                .getPackageName());
                                                contentValues.put(
                                                        "activity_name",
                                                        shareComponent.mComponentName
                                                                .getClassName());
                                                contentValues.put("label", ApnSettings.MVNO_NONE);
                                                contentValues.put(
                                                        "userId",
                                                        Integer.valueOf(shareComponent.mUserId));
                                                sQLiteDatabase.insert(
                                                        "ranked_apps", null, contentValues);
                                            }
                                        } finally {
                                        }
                                    }
                                } finally {
                                }
                            }
                        }
                    };
            SQLiteDatabase writableDatabase = shareDBAdapter.mDBHelper.getWritableDatabase();
            try {
                try {
                    try {
                        writableDatabase.beginTransaction();
                        consumer.accept(writableDatabase);
                        writableDatabase.setTransactionSuccessful();
                    } catch (SQLException | IllegalStateException e) {
                        e.printStackTrace();
                    }
                    if (writableDatabase != null) {
                        writableDatabase.close();
                    }
                } catch (Throwable th) {
                    if (writableDatabase != null) {
                        try {
                            writableDatabase.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } finally {
                writableDatabase.endTransaction();
            }
        }
        finish();
        return true;
    }

    public final void updateEditMenuVisibility$1() {
        ActionMenuView actionMenuView = this.mPortraitMenuView;
        if (actionMenuView != null) {
            actionMenuView.setVisibility(this.mIsPortrait.booleanValue() ? 0 : 8);
            this.mPortraitMenuViewWrapper.setVisibility(this.mIsPortrait.booleanValue() ? 0 : 8);
            if (this.mIsPortrait.booleanValue()) {
                this.mPortraitMenuView.getMenu().findItem(R.id.action_cancel).setEnabled(true);
                this.mPortraitMenuView.getMenu().findItem(R.id.action_done).setEnabled(true);
            }
        }
        Menu menu = this.mMenuLandscape;
        if (menu != null) {
            menu.findItem(R.id.action_cancel).setVisible(!this.mIsPortrait.booleanValue());
            this.mMenuLandscape
                    .findItem(R.id.action_done)
                    .setVisible(!this.mIsPortrait.booleanValue());
            if (this.mIsPortrait.booleanValue()) {
                return;
            }
            this.mMenuLandscape.findItem(R.id.action_cancel).setEnabled(true);
            this.mMenuLandscape.findItem(R.id.action_done).setEnabled(true);
        }
    }

    public final void updateSelectAllCheckBox() {
        boolean z;
        CheckBox checkBox = this.mSelectAllCheckBox;
        if (checkBox != null) {
            SelectAppViewModel selectAppViewModel = this.mViewModel;
            if (!selectAppViewModel.mIsAllChecked) {
                if (((List) selectAppViewModel.mInitialListPair.second).size()
                                        + ((List) selectAppViewModel.mInitialListPair.first).size()
                                != selectAppViewModel.mFavoriteComponentList.size()
                        || selectAppViewModel.mFavoriteComponentList.size() == 0) {
                    z = false;
                    checkBox.setChecked(z);
                }
            }
            z = true;
            checkBox.setChecked(z);
        }
    }

    public final void updateSelectCount$1() {
        int size = this.mViewModel.mFavoriteComponentList.size();
        String string =
                size <= 1
                        ? getResources().getString(R.string.dnd_app_picker_selection_title)
                        : getResources()
                                .getString(
                                        R.string.dnd_app_picker_multiple_selected,
                                        Integer.valueOf(size));
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(string);
        }
    }
}

package com.android.settings.notification.zen.lifestyle.apppicker;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.window.OnBackInvokedCallback;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelProviderImpl;
import androidx.picker.model.AppData;
import androidx.picker.model.AppInfo;
import androidx.picker.widget.AppPickerState$OnStateChangeListener;
import androidx.picker.widget.SeslAppPickerSelectLayout;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.connection.moreconnection.EmergencyAlertsPreferenceController;

import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DndAppSelectActivity extends AppCompatActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SeslAppPickerSelectLayout mAppPickerLayout;
    public Context mContext;
    public Boolean mIsPortrait;
    public boolean mIsSearchExpanded;
    public Menu mOptionsMenu;
    public PackageManager mPackageManager;
    public ActionMenuView mPortraitMenuView;
    public MenuItem mSearchMenu;
    public SearchView mSearchView;
    public CheckBox mSelectAllCheckBox;
    public View mSelectAllWrapper;
    public DndAppSelectDataViewModel mViewModel;
    public List mSupportApps = new ArrayList();
    public List mSelectedApps = new ArrayList();
    public final List mSelectAppList = new ArrayList();
    public String mSearchQuery = ApnSettings.MVNO_NONE;
    public Boolean mIsSelectListChanged = Boolean.FALSE;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        DndAppSelectDataViewModel dndAppSelectDataViewModel;
        String str;
        final int i = 2;
        super.onCreate(bundle);
        Context applicationContext = getApplicationContext();
        this.mContext = applicationContext;
        this.mPackageManager = applicationContext.getPackageManager();
        setContentView(R.layout.activity_apppickerview);
        setTitle(ApnSettings.MVNO_NONE);
        setSupportActionBar((Toolbar) findViewById(R.id.dnd_toolbar));
        Intent intent = getIntent();
        final int i2 = 0;
        final int i3 = 1;
        if (intent != null) {
            this.mSupportApps = intent.getStringArrayListExtra("supported_apps");
            this.mSelectedApps = intent.getStringArrayListExtra("selected_apps");
            ViewModelStore store = getViewModelStore();
            ViewModelProvider.Factory factory = getDefaultViewModelProviderFactory();
            CreationExtras defaultViewModelCreationExtras = getDefaultViewModelCreationExtras();
            Intrinsics.checkNotNullParameter(store, "store");
            Intrinsics.checkNotNullParameter(factory, "factory");
            ViewModelProviderImpl viewModelProviderImpl =
                    new ViewModelProviderImpl(store, factory, defaultViewModelCreationExtras);
            KClass kotlinClass = JvmClassMappingKt.getKotlinClass(DndAppSelectDataViewModel.class);
            String qualifiedName = kotlinClass.getQualifiedName();
            if (qualifiedName == null) {
                throw new IllegalArgumentException(
                        "Local and anonymous classes can not be ViewModels".toString());
            }
            this.mViewModel =
                    (DndAppSelectDataViewModel)
                            viewModelProviderImpl.getViewModel$lifecycle_viewmodel_release(
                                    kotlinClass,
                                    "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                            .concat(qualifiedName));
            if (bundle != null) {
                this.mSelectedApps = bundle.getStringArrayList("selected_apps_new");
                this.mSupportApps = bundle.getStringArrayList("support_apps");
                this.mSearchQuery = bundle.getString("query");
                this.mIsSelectListChanged =
                        Boolean.valueOf(bundle.getBoolean("isSelectListChanged"));
                this.mIsSearchExpanded = bundle.getBoolean("expand_search_view");
            }
            DndAppSelectDataViewModel dndAppSelectDataViewModel2 = this.mViewModel;
            List list = this.mSupportApps;
            dndAppSelectDataViewModel2.getClass();
            if (list != null && list.size() != 0) {
                dndAppSelectDataViewModel2.mSupportAppList.clear();
                dndAppSelectDataViewModel2.mSupportAppList = list;
                dndAppSelectDataViewModel2.mSupportAppInfoList.clear();
                for (String str2 : dndAppSelectDataViewModel2.mSupportAppList) {
                    dndAppSelectDataViewModel2.mSelectMap.putIfAbsent(str2, Boolean.FALSE);
                    String[] split = str2.split(":");
                    Application application = dndAppSelectDataViewModel2.getApplication();
                    try {
                        str =
                                application
                                        .getPackageManager()
                                        .getApplicationInfoAsUser(
                                                split[0], 0, Integer.parseInt(split[1]))
                                        .loadLabel(application.getPackageManager())
                                        .toString();
                    } catch (PackageManager.NameNotFoundException unused) {
                        str = ApnSettings.MVNO_NONE;
                    }
                    if (str != null) {
                        CopyOnWriteArrayList copyOnWriteArrayList =
                                dndAppSelectDataViewModel2.mSupportAppInfoList;
                        SelectAppInfo selectAppInfo = new SelectAppInfo();
                        selectAppInfo.packageName = str2;
                        copyOnWriteArrayList.add(selectAppInfo);
                    }
                }
                synchronized (dndAppSelectDataViewModel2) {
                    try {
                        ArrayList arrayList = new ArrayList();
                        Iterator it = dndAppSelectDataViewModel2.mSupportAppInfoList.iterator();
                        while (it.hasNext()) {
                            arrayList.add(((SelectAppInfo) it.next()).packageName);
                        }
                        dndAppSelectDataViewModel2.mSupportAppListLiveData.postValue(arrayList);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            DndAppSelectDataViewModel dndAppSelectDataViewModel3 = this.mViewModel;
            List<String> list2 = this.mSelectedApps;
            dndAppSelectDataViewModel3.getClass();
            if (list2 != null) {
                for (String str3 : list2) {
                    if (dndAppSelectDataViewModel3.mSelectMap.containsKey(str3)) {
                        dndAppSelectDataViewModel3.mSelectMap.put(str3, Boolean.TRUE);
                    }
                }
            }
            this.mViewModel.mSelectAppLiveData.observe(
                    this,
                    new Observer(this) { // from class:
                        // com.android.settings.notification.zen.lifestyle.apppicker.DndAppSelectActivity.7
                        public final /* synthetic */ DndAppSelectActivity this$0;

                        {
                            this.this$0 = this;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:15:0x005d A[SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:19:0x004b A[SYNTHETIC] */
                        @Override // androidx.lifecycle.Observer
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final void onChanged(java.lang.Object r4) {
                            /*
                                r3 = this;
                                com.android.settings.notification.zen.lifestyle.apppicker.DndAppSelectActivity r0 = r3.this$0
                                int r3 = r2
                                switch(r3) {
                                    case 0: goto L69;
                                    case 1: goto L13;
                                    default: goto L7;
                                }
                            L7:
                                java.lang.Boolean r4 = (java.lang.Boolean) r4
                                android.widget.CheckBox r3 = r0.mSelectAllCheckBox
                                boolean r4 = r4.booleanValue()
                                r3.setChecked(r4)
                                return
                            L13:
                                java.util.List r4 = (java.util.List) r4
                                r0.mSupportApps = r4
                                java.util.concurrent.CopyOnWriteArrayList r3 = new java.util.concurrent.CopyOnWriteArrayList
                                r3.<init>(r4)
                                java.util.Iterator r3 = r3.iterator()
                            L20:
                                boolean r4 = r3.hasNext()
                                if (r4 == 0) goto L65
                                java.lang.Object r4 = r3.next()
                                java.lang.String r4 = (java.lang.String) r4
                                com.android.settings.notification.zen.lifestyle.apppicker.DndAppSelectDataViewModel r1 = r0.mViewModel
                                if (r4 == 0) goto L45
                                java.util.concurrent.ConcurrentHashMap r2 = r1.mSelectMap
                                boolean r2 = r2.containsKey(r4)
                                if (r2 == 0) goto L48
                                java.util.concurrent.ConcurrentHashMap r1 = r1.mSelectMap
                                java.lang.Object r1 = r1.get(r4)
                                java.lang.Boolean r1 = (java.lang.Boolean) r1
                                boolean r1 = r1.booleanValue()
                                goto L49
                            L45:
                                r1.getClass()
                            L48:
                                r1 = 0
                            L49:
                                if (r1 == 0) goto L5d
                                java.util.List r1 = r0.mSelectAppList
                                java.util.ArrayList r1 = (java.util.ArrayList) r1
                                boolean r1 = r1.contains(r4)
                                if (r1 != 0) goto L20
                                java.util.List r1 = r0.mSelectAppList
                                java.util.ArrayList r1 = (java.util.ArrayList) r1
                                r1.add(r4)
                                goto L20
                            L5d:
                                java.util.List r1 = r0.mSelectAppList
                                java.util.ArrayList r1 = (java.util.ArrayList) r1
                                r1.remove(r4)
                                goto L20
                            L65:
                                r0.updateSelectCount()
                                return
                            L69:
                                java.lang.Integer r4 = (java.lang.Integer) r4
                                int r3 = r4.intValue()
                                if (r3 < 0) goto L87
                                java.util.List r4 = r0.mSelectAppList
                                java.util.ArrayList r4 = (java.util.ArrayList) r4
                                int r4 = r4.size()
                                if (r3 >= r4) goto L89
                                java.util.List r4 = r0.mSelectAppList
                                java.util.ArrayList r4 = (java.util.ArrayList) r4
                                r4.remove(r3)
                                java.lang.Boolean r3 = java.lang.Boolean.TRUE
                                r0.mIsSelectListChanged = r3
                                goto L89
                            L87:
                                int r3 = com.android.settings.notification.zen.lifestyle.apppicker.DndAppSelectActivity.$r8$clinit
                            L89:
                                r0.updateSelectCount()
                                return
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.settings.notification.zen.lifestyle.apppicker.DndAppSelectActivity.AnonymousClass7.onChanged(java.lang.Object):void");
                        }
                    });
            this.mViewModel.mSupportAppListLiveData.observe(
                    this,
                    new Observer(this) { // from class:
                        // com.android.settings.notification.zen.lifestyle.apppicker.DndAppSelectActivity.7
                        public final /* synthetic */ DndAppSelectActivity this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // androidx.lifecycle.Observer
                        public final void onChanged(Object obj) {
                            /*
                                this = this;
                                com.android.settings.notification.zen.lifestyle.apppicker.DndAppSelectActivity r0 = r3.this$0
                                int r3 = r2
                                switch(r3) {
                                    case 0: goto L69;
                                    case 1: goto L13;
                                    default: goto L7;
                                }
                            L7:
                                java.lang.Boolean r4 = (java.lang.Boolean) r4
                                android.widget.CheckBox r3 = r0.mSelectAllCheckBox
                                boolean r4 = r4.booleanValue()
                                r3.setChecked(r4)
                                return
                            L13:
                                java.util.List r4 = (java.util.List) r4
                                r0.mSupportApps = r4
                                java.util.concurrent.CopyOnWriteArrayList r3 = new java.util.concurrent.CopyOnWriteArrayList
                                r3.<init>(r4)
                                java.util.Iterator r3 = r3.iterator()
                            L20:
                                boolean r4 = r3.hasNext()
                                if (r4 == 0) goto L65
                                java.lang.Object r4 = r3.next()
                                java.lang.String r4 = (java.lang.String) r4
                                com.android.settings.notification.zen.lifestyle.apppicker.DndAppSelectDataViewModel r1 = r0.mViewModel
                                if (r4 == 0) goto L45
                                java.util.concurrent.ConcurrentHashMap r2 = r1.mSelectMap
                                boolean r2 = r2.containsKey(r4)
                                if (r2 == 0) goto L48
                                java.util.concurrent.ConcurrentHashMap r1 = r1.mSelectMap
                                java.lang.Object r1 = r1.get(r4)
                                java.lang.Boolean r1 = (java.lang.Boolean) r1
                                boolean r1 = r1.booleanValue()
                                goto L49
                            L45:
                                r1.getClass()
                            L48:
                                r1 = 0
                            L49:
                                if (r1 == 0) goto L5d
                                java.util.List r1 = r0.mSelectAppList
                                java.util.ArrayList r1 = (java.util.ArrayList) r1
                                boolean r1 = r1.contains(r4)
                                if (r1 != 0) goto L20
                                java.util.List r1 = r0.mSelectAppList
                                java.util.ArrayList r1 = (java.util.ArrayList) r1
                                r1.add(r4)
                                goto L20
                            L5d:
                                java.util.List r1 = r0.mSelectAppList
                                java.util.ArrayList r1 = (java.util.ArrayList) r1
                                r1.remove(r4)
                                goto L20
                            L65:
                                r0.updateSelectCount()
                                return
                            L69:
                                java.lang.Integer r4 = (java.lang.Integer) r4
                                int r3 = r4.intValue()
                                if (r3 < 0) goto L87
                                java.util.List r4 = r0.mSelectAppList
                                java.util.ArrayList r4 = (java.util.ArrayList) r4
                                int r4 = r4.size()
                                if (r3 >= r4) goto L89
                                java.util.List r4 = r0.mSelectAppList
                                java.util.ArrayList r4 = (java.util.ArrayList) r4
                                r4.remove(r3)
                                java.lang.Boolean r3 = java.lang.Boolean.TRUE
                                r0.mIsSelectListChanged = r3
                                goto L89
                            L87:
                                int r3 = com.android.settings.notification.zen.lifestyle.apppicker.DndAppSelectActivity.$r8$clinit
                            L89:
                                r0.updateSelectCount()
                                return
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.settings.notification.zen.lifestyle.apppicker.DndAppSelectActivity.AnonymousClass7.onChanged(java.lang.Object):void");
                        }
                    });
            this.mViewModel.misAllSelected.observe(
                    this,
                    new Observer(this) { // from class:
                        // com.android.settings.notification.zen.lifestyle.apppicker.DndAppSelectActivity.7
                        public final /* synthetic */ DndAppSelectActivity this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // androidx.lifecycle.Observer
                        public final void onChanged(Object obj) {
                            /*
                                this = this;
                                com.android.settings.notification.zen.lifestyle.apppicker.DndAppSelectActivity r0 = r3.this$0
                                int r3 = r2
                                switch(r3) {
                                    case 0: goto L69;
                                    case 1: goto L13;
                                    default: goto L7;
                                }
                            L7:
                                java.lang.Boolean r4 = (java.lang.Boolean) r4
                                android.widget.CheckBox r3 = r0.mSelectAllCheckBox
                                boolean r4 = r4.booleanValue()
                                r3.setChecked(r4)
                                return
                            L13:
                                java.util.List r4 = (java.util.List) r4
                                r0.mSupportApps = r4
                                java.util.concurrent.CopyOnWriteArrayList r3 = new java.util.concurrent.CopyOnWriteArrayList
                                r3.<init>(r4)
                                java.util.Iterator r3 = r3.iterator()
                            L20:
                                boolean r4 = r3.hasNext()
                                if (r4 == 0) goto L65
                                java.lang.Object r4 = r3.next()
                                java.lang.String r4 = (java.lang.String) r4
                                com.android.settings.notification.zen.lifestyle.apppicker.DndAppSelectDataViewModel r1 = r0.mViewModel
                                if (r4 == 0) goto L45
                                java.util.concurrent.ConcurrentHashMap r2 = r1.mSelectMap
                                boolean r2 = r2.containsKey(r4)
                                if (r2 == 0) goto L48
                                java.util.concurrent.ConcurrentHashMap r1 = r1.mSelectMap
                                java.lang.Object r1 = r1.get(r4)
                                java.lang.Boolean r1 = (java.lang.Boolean) r1
                                boolean r1 = r1.booleanValue()
                                goto L49
                            L45:
                                r1.getClass()
                            L48:
                                r1 = 0
                            L49:
                                if (r1 == 0) goto L5d
                                java.util.List r1 = r0.mSelectAppList
                                java.util.ArrayList r1 = (java.util.ArrayList) r1
                                boolean r1 = r1.contains(r4)
                                if (r1 != 0) goto L20
                                java.util.List r1 = r0.mSelectAppList
                                java.util.ArrayList r1 = (java.util.ArrayList) r1
                                r1.add(r4)
                                goto L20
                            L5d:
                                java.util.List r1 = r0.mSelectAppList
                                java.util.ArrayList r1 = (java.util.ArrayList) r1
                                r1.remove(r4)
                                goto L20
                            L65:
                                r0.updateSelectCount()
                                return
                            L69:
                                java.lang.Integer r4 = (java.lang.Integer) r4
                                int r3 = r4.intValue()
                                if (r3 < 0) goto L87
                                java.util.List r4 = r0.mSelectAppList
                                java.util.ArrayList r4 = (java.util.ArrayList) r4
                                int r4 = r4.size()
                                if (r3 >= r4) goto L89
                                java.util.List r4 = r0.mSelectAppList
                                java.util.ArrayList r4 = (java.util.ArrayList) r4
                                r4.remove(r3)
                                java.lang.Boolean r3 = java.lang.Boolean.TRUE
                                r0.mIsSelectListChanged = r3
                                goto L89
                            L87:
                                int r3 = com.android.settings.notification.zen.lifestyle.apppicker.DndAppSelectActivity.$r8$clinit
                            L89:
                                r0.updateSelectCount()
                                return
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.settings.notification.zen.lifestyle.apppicker.DndAppSelectActivity.AnonymousClass7.onChanged(java.lang.Object):void");
                        }
                    });
        }
        this.mIsPortrait = Boolean.valueOf(getResources().getConfiguration().orientation == 1);
        this.mAppPickerLayout =
                (SeslAppPickerSelectLayout) findViewById(R.id.selectlayoutapppickerview);
        this.mSelectAllCheckBox = (CheckBox) findViewById(R.id.select_all_checkbox);
        this.mSelectAllWrapper = findViewById(R.id.select_all_wrapper);
        ((Toolbar) findViewById(R.id.dnd_toolbar)).setBackInvokedCallbackEnabled(false);
        getOnBackInvokedDispatcher()
                .registerSystemOnBackInvokedCallback(
                        new OnBackInvokedCallback() { // from class:
                                                      // com.android.settings.notification.zen.lifestyle.apppicker.DndAppSelectActivity.4
                            @Override // android.window.OnBackInvokedCallback
                            public final void onBackInvoked() {
                                MenuItem findItem;
                                DndAppSelectActivity dndAppSelectActivity =
                                        DndAppSelectActivity.this;
                                if (!dndAppSelectActivity.mIsSearchExpanded) {
                                    dndAppSelectActivity.finish();
                                    return;
                                }
                                Menu menu = dndAppSelectActivity.mOptionsMenu;
                                if (menu == null
                                        || (findItem =
                                                        menu.findItem(
                                                                R.id.zen_mode_list_search_menu))
                                                == null) {
                                    return;
                                }
                                findItem.collapseActionView();
                                DndAppSelectActivity.this.mIsSearchExpanded = false;
                            }
                        });
        CheckBox checkBox = this.mSelectAllCheckBox;
        if (checkBox != null && (dndAppSelectDataViewModel = this.mViewModel) != null) {
            checkBox.setChecked(dndAppSelectDataViewModel.isAllSelected$1());
        }
        View view = this.mSelectAllWrapper;
        if (view != null) {
            view.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.notification.zen.lifestyle.apppicker.DndAppSelectActivity$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            DndAppSelectActivity dndAppSelectActivity = DndAppSelectActivity.this;
                            boolean z = !dndAppSelectActivity.mSelectAllCheckBox.isChecked();
                            dndAppSelectActivity.mSelectAllCheckBox.setChecked(z);
                            if (z) {
                                Iterator it2 = dndAppSelectActivity.mSupportApps.iterator();
                                while (it2.hasNext()) {
                                    dndAppSelectActivity.mViewModel.mSelectMap.put(
                                            (String) it2.next(), Boolean.TRUE);
                                }
                                ((ArrayList) dndAppSelectActivity.mSelectAppList).clear();
                                ((ArrayList) dndAppSelectActivity.mSelectAppList)
                                        .addAll(dndAppSelectActivity.mSupportApps);
                                dndAppSelectActivity.mAppPickerLayout.setStateAll(true);
                            } else {
                                for (String str4 : dndAppSelectActivity.mSupportApps) {
                                    if (((ArrayList) dndAppSelectActivity.mSelectAppList)
                                            .contains(str4)) {
                                        try {
                                            dndAppSelectActivity.mViewModel.mSelectMap.put(
                                                    str4, Boolean.FALSE);
                                            ((ArrayList) dndAppSelectActivity.mSelectAppList)
                                                    .remove(str4);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                dndAppSelectActivity.mAppPickerLayout.setStateAll(false);
                            }
                            dndAppSelectActivity.mIsSelectListChanged = Boolean.TRUE;
                            dndAppSelectActivity.updateSelectCount();
                        }
                    });
        }
        this.mPortraitMenuView = (ActionMenuView) findViewById(R.id.edit_appbar_portrait);
        getMenuInflater().inflate(R.menu.dnd_edit_menu, this.mPortraitMenuView.getMenu());
        this.mPortraitMenuView.mOnMenuItemClickListener = new AnonymousClass2();
        this.mAppPickerLayout.enableSelectedAppPickerView(true);
        this.mAppPickerLayout.mAppPickerStateView.setAppListOrder(2);
        SeslAppPickerSelectLayout seslAppPickerSelectLayout = this.mAppPickerLayout;
        seslAppPickerSelectLayout.mOnStateChangeListener = new AnonymousClass2();
        ArrayList arrayList2 = new ArrayList();
        try {
            for (String str4 : this.mSupportApps) {
                String str5 = str4.split(":")[0];
                int parseInt = Integer.parseInt(str4.split(":")[1]);
                DndAppSelectDataViewModel dndAppSelectDataViewModel4 = this.mViewModel;
                boolean booleanValue =
                        dndAppSelectDataViewModel4.mSelectMap.containsKey(str4)
                                ? ((Boolean) dndAppSelectDataViewModel4.mSelectMap.get(str4))
                                        .booleanValue()
                                : false;
                Intent launchIntentForPackage =
                        this.mPackageManager.getLaunchIntentForPackage(str5);
                if (launchIntentForPackage == null
                        && str5.equals(
                                EmergencyAlertsPreferenceController.AOSP_GOOGLE_CMAS_PACKAGE)) {
                    Intent intent2 = new Intent();
                    intent2.setPackage(
                            EmergencyAlertsPreferenceController.AOSP_GOOGLE_CMAS_PACKAGE);
                    String str6 =
                            this.mPackageManager.resolveActivity(intent2, 0).activityInfo.name;
                    AppInfo.Companion companion = AppInfo.Companion;
                    arrayList2.add(
                            new AppData.ListCheckBoxAppDataBuilder(
                                            AppInfo.Companion.obtain(parseInt, str5, str6))
                                    .setSelected(booleanValue)
                                    .build());
                } else if (launchIntentForPackage != null || parseInt == 0) {
                    String className = launchIntentForPackage.getComponent().getClassName();
                    AppInfo.Companion companion2 = AppInfo.Companion;
                    arrayList2.add(
                            new AppData.ListCheckBoxAppDataBuilder(
                                            AppInfo.Companion.obtain(parseInt, str5, className))
                                    .setSelected(booleanValue)
                                    .build());
                } else {
                    new Intent().setPackage(str5);
                    AppInfo.Companion companion3 = AppInfo.Companion;
                    arrayList2.add(
                            new AppData.ListCheckBoxAppDataBuilder(
                                            AppInfo.Companion.obtain(
                                                    parseInt, str5, ApnSettings.MVNO_NONE))
                                    .setSelected(booleanValue)
                                    .build());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        seslAppPickerSelectLayout.submitList(arrayList2);
    }

    @Override // android.app.Activity
    public final boolean onCreateOptionsMenu(Menu menu) {
        this.mOptionsMenu = menu;
        getMenuInflater().inflate(R.menu.zen_mode_list_menu, menu);
        this.mSearchMenu = menu.findItem(R.id.zen_mode_list_search_menu);
        menu.findItem(R.id.cancel);
        menu.findItem(R.id.done);
        this.mSearchMenu
                .getIcon()
                .setColorFilter(
                        getResources().getColor(R.color.sec_search_magnifier_icon_tint_color),
                        PorterDuff.Mode.SRC_IN);
        SearchView searchView = (SearchView) this.mSearchMenu.getActionView();
        this.mSearchView = searchView;
        LinearLayout linearLayout = (LinearLayout) searchView.findViewById(R.id.search_plate);
        if (linearLayout != null) {
            linearLayout.setPadding(
                    0,
                    linearLayout.getPaddingTop(),
                    (int) getResources().getDimension(R.dimen.sec_search_icon_end_padding),
                    linearLayout.getPaddingBottom());
            linearLayout.setFocusedByDefault(false);
        }
        if (this.mIsSearchExpanded) {
            this.mSearchMenu.expandActionView();
            String str = this.mSearchQuery;
            if (str != null && str.length() > 0) {
                EditText editText = (EditText) linearLayout.findViewById(R.id.search_src_text);
                editText.setText(this.mSearchQuery);
                editText.setSelection(editText.length());
                SeslAppPickerSelectLayout seslAppPickerSelectLayout = this.mAppPickerLayout;
                seslAppPickerSelectLayout.mAppPickerStateView.setSearchFilter(
                        this.mSearchQuery,
                        seslAppPickerSelectLayout.mOnSearchFilterListenerForLayout);
            }
            this.mOptionsMenu.findItem(R.id.empty).setVisible(true);
            this.mSelectAllWrapper.setVisibility(8);
            this.mAppPickerLayout.enableSelectedAppPickerView(false);
        }
        this.mSearchMenu.setOnActionExpandListener(
                new MenuItem
                        .OnActionExpandListener() { // from class:
                                                    // com.android.settings.notification.zen.lifestyle.apppicker.DndAppSelectActivity.1
                    @Override // android.view.MenuItem.OnActionExpandListener
                    public final boolean onMenuItemActionCollapse(MenuItem menuItem) {
                        DndAppSelectActivity dndAppSelectActivity = DndAppSelectActivity.this;
                        dndAppSelectActivity.mIsSearchExpanded = false;
                        dndAppSelectActivity.mSelectAllWrapper.setVisibility(0);
                        DndAppSelectActivity.this
                                .mOptionsMenu
                                .findItem(R.id.empty)
                                .setVisible(false);
                        DndAppSelectActivity.this.mAppPickerLayout.enableSelectedAppPickerView(
                                true);
                        return true;
                    }

                    @Override // android.view.MenuItem.OnActionExpandListener
                    public final boolean onMenuItemActionExpand(MenuItem menuItem) {
                        DndAppSelectActivity dndAppSelectActivity = DndAppSelectActivity.this;
                        dndAppSelectActivity.mIsSearchExpanded = true;
                        dndAppSelectActivity.mSelectAllWrapper.setVisibility(8);
                        DndAppSelectActivity.this
                                .mOptionsMenu
                                .findItem(R.id.empty)
                                .setVisible(true);
                        DndAppSelectActivity.this.mAppPickerLayout.enableSelectedAppPickerView(
                                false);
                        return true;
                    }
                });
        SearchView searchView2 = this.mSearchView;
        if (searchView2 != null) {
            searchView2.setQueryHint(getText(R.string.search_settings));
            SearchView searchView3 = this.mSearchView;
            searchView3.mOnQueryChangeListener = new AnonymousClass2();
            searchView3.mOnQueryTextFocusChangeListener =
                    new View
                            .OnFocusChangeListener() { // from class:
                                                       // com.android.settings.notification.zen.lifestyle.apppicker.DndAppSelectActivity.3
                        @Override // android.view.View.OnFocusChangeListener
                        public final void onFocusChange(View view, boolean z) {
                            if (z) {
                                return;
                            }
                            DndAppSelectActivity dndAppSelectActivity = DndAppSelectActivity.this;
                            int i = DndAppSelectActivity.$r8$clinit;
                            dndAppSelectActivity.getClass();
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
        updateEditMenuVisibility();
        return true;
    }

    @Override // android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        return selectEditMenuItem(menuItem) || super.onOptionsItemSelected(menuItem);
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putStringArrayList("selected_apps_new", (ArrayList) this.mSelectAppList);
        bundle.putStringArrayList("support_apps", (ArrayList) this.mSupportApps);
        bundle.putBoolean("isSelectListChanged", this.mIsSelectListChanged.booleanValue());
        if (this.mSearchView != null) {
            bundle.putBoolean("expand_search_view", this.mIsSearchExpanded);
            if (this.mIsSearchExpanded) {
                bundle.putString(
                        "query",
                        ((EditText)
                                        ((LinearLayout)
                                                        this.mSearchView.findViewById(
                                                                R.id.search_plate))
                                                .findViewById(R.id.search_src_text))
                                .getText()
                                .toString());
            }
        }
        super.onSaveInstanceState(bundle);
    }

    public final boolean selectEditMenuItem(MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.done && menuItem.getItemId() != R.id.action_done) {
            if (menuItem.getItemId() != R.id.cancel) {
                return false;
            }
            finish();
            return true;
        }
        if (this.mViewModel != null) {
            new ArrayList();
            DndAppSelectDataViewModel dndAppSelectDataViewModel = this.mViewModel;
            dndAppSelectDataViewModel.getClass();
            ArrayList<String> arrayList = new ArrayList<>();
            for (String str : dndAppSelectDataViewModel.mSelectMap.keySet()) {
                if (((Boolean) dndAppSelectDataViewModel.mSelectMap.get(str)).booleanValue()) {
                    arrayList.add(str);
                }
            }
            Intent intent = new Intent();
            intent.putStringArrayListExtra("selected_apps", arrayList);
            setResult(-1, intent);
        }
        finish();
        return true;
    }

    public final void updateEditMenuVisibility() {
        int checkedCount$1 = this.mViewModel.getCheckedCount$1();
        ActionMenuView actionMenuView = this.mPortraitMenuView;
        if (actionMenuView != null) {
            actionMenuView.setVisibility(this.mIsPortrait.booleanValue() ? 0 : 8);
            if (checkedCount$1 != 0 || this.mIsSelectListChanged.booleanValue()) {
                this.mPortraitMenuView.getMenu().findItem(R.id.action_done).setEnabled(true);
            } else {
                this.mPortraitMenuView.getMenu().findItem(R.id.action_done).setEnabled(false);
            }
        }
        Menu menu = this.mOptionsMenu;
        if (menu != null) {
            menu.findItem(R.id.done).setVisible(!this.mIsPortrait.booleanValue());
            if (checkedCount$1 != 0 || this.mIsSelectListChanged.booleanValue()) {
                this.mOptionsMenu.findItem(R.id.done).setEnabled(true);
            } else {
                this.mOptionsMenu.findItem(R.id.done).setEnabled(false);
            }
        }
    }

    public final void updateSelectCount() {
        int checkedCount$1 = this.mViewModel.getCheckedCount$1();
        String string =
                checkedCount$1 == 0
                        ? this.mContext
                                .getResources()
                                .getString(R.string.dnd_app_picker_selection_title)
                        : this.mContext
                                .getResources()
                                .getString(
                                        R.string.dnd_app_picker_multiple_selected,
                                        Integer.valueOf(checkedCount$1));
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(string);
        }
        updateEditMenuVisibility();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.zen.lifestyle.apppicker.DndAppSelectActivity$2, reason: invalid class name */
    public final class AnonymousClass2
            implements SearchView.OnQueryTextListener,
                    ActionMenuView.OnMenuItemClickListener,
                    AppPickerState$OnStateChangeListener {
        public /* synthetic */ AnonymousClass2() {}

        @Override // androidx.appcompat.widget.ActionMenuView.OnMenuItemClickListener
        public boolean onMenuItemClick(MenuItem menuItem) {
            int i = DndAppSelectActivity.$r8$clinit;
            return DndAppSelectActivity.this.selectEditMenuItem(menuItem);
        }

        @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
        public boolean onQueryTextChange(String str) {
            SeslAppPickerSelectLayout seslAppPickerSelectLayout =
                    DndAppSelectActivity.this.mAppPickerLayout;
            seslAppPickerSelectLayout.mAppPickerStateView.setSearchFilter(
                    str, seslAppPickerSelectLayout.mOnSearchFilterListenerForLayout);
            return true;
        }

        @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
        public void onQueryTextSubmit(String str) {
            SeslAppPickerSelectLayout seslAppPickerSelectLayout =
                    DndAppSelectActivity.this.mAppPickerLayout;
            seslAppPickerSelectLayout.mAppPickerStateView.setSearchFilter(
                    str, seslAppPickerSelectLayout.mOnSearchFilterListenerForLayout);
        }

        @Override // androidx.picker.widget.AppPickerState$OnStateChangeListener
        public void onStateChanged(AppInfo appInfo, boolean z) {
            String str = appInfo.packageName + ":" + appInfo.user;
            DndAppSelectActivity dndAppSelectActivity = DndAppSelectActivity.this;
            dndAppSelectActivity.mViewModel.mSelectMap.put(str, Boolean.valueOf(z));
            if (!z || ((ArrayList) dndAppSelectActivity.mSelectAppList).contains(str)) {
                int i = 0;
                while (true) {
                    if (i >= ((ArrayList) dndAppSelectActivity.mSelectAppList).size()) {
                        i = -1;
                        break;
                    } else if (((String) ((ArrayList) dndAppSelectActivity.mSelectAppList).get(i))
                            .contains(str)) {
                        break;
                    } else {
                        i++;
                    }
                }
                if (i != -1) {
                    ((ArrayList) dndAppSelectActivity.mSelectAppList).remove(i);
                }
            } else {
                ((ArrayList) dndAppSelectActivity.mSelectAppList).add(str);
            }
            dndAppSelectActivity.mIsSelectListChanged = Boolean.TRUE;
            dndAppSelectActivity.updateSelectCount();
        }

        @Override // androidx.picker.widget.AppPickerState$OnStateChangeListener
        public void onStateAllChanged(boolean z) {}
    }
}

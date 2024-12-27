package com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.LruCache;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.LifecycleOwner;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settings.R;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.data.model.CommonAppInfo;
import com.samsung.android.settings.analyzestorage.data.model.UnusedAppInfo;
import com.samsung.android.settings.analyzestorage.domain.entity.AppInfo;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController;
import com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController$$ExternalSyntheticLambda3;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ListItemHandler;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ManageStorageListItemHandler;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ManageStorageListItemHandler$$ExternalSyntheticLambda0;
import com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.KeyMouseCommand;
import com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.KeyboardMouseManager;
import com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.ShiftKeyMouseCommand;
import com.samsung.android.settings.analyzestorage.presenter.managers.FeatureManager$UiFeature;
import com.samsung.android.settings.analyzestorage.presenter.utils.StringConverter;
import com.samsung.android.settings.analyzestorage.ui.manager.ListMarginManager;
import com.samsung.android.settings.analyzestorage.ui.utils.UiUtils;
import com.samsung.android.settings.analyzestorage.ui.widget.SortByItemLayout;
import com.samsung.android.settings.analyzestorage.ui.widget.viewholder.SubAppListHeaderViewHolder;
import com.samsung.android.settings.analyzestorage.ui.widget.viewholder.SubAppListViewHolder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AsSubListAdapter extends RecyclerView.Adapter {
    public final Context context;
    public final AppListController controller;
    public List itemList;
    public final ListMarginManager listMarginManager;

    public AsSubListAdapter(Context context, AppListController controller, LifecycleOwner owner) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(controller, "controller");
        Intrinsics.checkNotNullParameter(owner, "owner");
        this.context = context;
        this.controller = controller;
        ListMarginManager.Companion companion = ListMarginManager.Companion;
        ListMarginManager listMarginManager = ListMarginManager.instance;
        if (listMarginManager == null) {
            synchronized (companion) {
                listMarginManager = new ListMarginManager();
                ListMarginManager.instance = listMarginManager;
            }
        }
        this.listMarginManager = listMarginManager;
        this.itemList = new ArrayList();
    }

    public final List getCustomViewText(int i, int i2) {
        String quantityString;
        AppListController appListController = this.controller;
        ListItemHandler listItemHandler = appListController.mListItemHandler;
        Intrinsics.checkNotNull(listItemHandler, "null cannot be cast to non-null type com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ManageStorageListItemHandler<@[FlexibleNullability] com.samsung.android.settings.analyzestorage.domain.entity.AppInfo?>");
        long[] jArr = {((ArrayList) r4.mAppDataList).size(), ((ManageStorageListItemHandler) listItemHandler).mAppDataList.parallelStream().mapToLong(new ManageStorageListItemHandler$$ExternalSyntheticLambda0()).sum()};
        int i3 = (int) jArr[0];
        long j = jArr[1];
        if (i3 > 0 && j > 0 && appListController.mPageInfo.mExtra.getBoolean("manageStorageSubPageInitialEntry")) {
            appListController.mPageInfo.mExtra.putBoolean("manageStorageSubPageInitialEntry", false);
            Log.d("AsSubListAdapter", "getCustomViewText() ] Initial Entry - Set initialEntrySize to " + j);
        }
        ArrayList arrayList = new ArrayList(2);
        String str = ApnSettings.MVNO_NONE;
        if (i == 4 || i == 5) {
            quantityString = this.context.getResources().getQuantityString(i2, i3, Integer.valueOf(i3), StringConverter.formatFileSize(0, j, this.context));
            Intrinsics.checkNotNull(quantityString);
        } else {
            quantityString = ApnSettings.MVNO_NONE;
        }
        arrayList.add(0, quantityString);
        if (i == 4) {
            str = this.context.getString(R.string.as_unused_apps_sub_title);
            Intrinsics.checkNotNullExpressionValue(str, "getString(...)");
        } else if (i == 5) {
            str = this.context.getString(R.string.as_app_cache_sub_title);
            Intrinsics.checkNotNullExpressionValue(str, "getString(...)");
        }
        arrayList.add(1, str);
        return arrayList;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        if (this.itemList.size() > 0) {
            return this.itemList.size() + 1;
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final long getItemId(int i) {
        return i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        return i == 0 ? 0 : 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        TextView textView;
        boolean z = viewHolder instanceof SubAppListHeaderViewHolder;
        AppListController appListController = this.controller;
        if (!z) {
            SubAppListViewHolder subAppListViewHolder = (SubAppListViewHolder) viewHolder;
            final AppInfo appInfo = (AppInfo) this.itemList.get(i - 1);
            PackageManager packageManager = this.context.getPackageManager();
            if (packageManager != null) {
                try {
                    CommonAppInfo commonAppInfo = (CommonAppInfo) appInfo;
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(commonAppInfo.getPackageName(), PackageManager.ApplicationInfoFlags.of(0L));
                    Intrinsics.checkNotNullExpressionValue(applicationInfo, "getApplicationInfo(...)");
                    Drawable semGetApplicationIconForIconTray = packageManager.semGetApplicationIconForIconTray(applicationInfo, 1);
                    Intrinsics.checkNotNullExpressionValue(semGetApplicationIconForIconTray, "semGetApplicationIconForIconTray(...)");
                    ImageView imageView = subAppListViewHolder.appIcon;
                    if (imageView != null) {
                        imageView.setImageDrawable(semGetApplicationIconForIconTray);
                    }
                    ImageView imageView2 = subAppListViewHolder.appIcon;
                    if (imageView2 != null) {
                        imageView2.setImportantForAccessibility(2);
                    }
                    CharSequence applicationLabel = packageManager.getApplicationLabel(applicationInfo);
                    Intrinsics.checkNotNullExpressionValue(applicationLabel, "getApplicationLabel(...)");
                    TextView textView2 = subAppListViewHolder.mainText;
                    if (textView2 != null) {
                        textView2.setText(applicationLabel);
                    }
                    Context context = this.context;
                    long size = commonAppInfo.getSize();
                    LruCache lruCache = StringConverter.sCachedSize;
                    String str = (String) lruCache.get(Long.valueOf(size));
                    if (str == null && context != null) {
                        str = StringConverter.formatFileSize(0, size, context);
                        lruCache.put(Long.valueOf(size), str);
                    }
                    if (str != null && (textView = subAppListViewHolder.subText) != null) {
                        textView.setText(str);
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
            ImageView imageView3 = subAppListViewHolder.appInfoIcon;
            if (imageView3 != null) {
                imageView3.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage.AsSubListAdapter$bindItemViewHolder$2$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AppListController appListController2 = AsSubListAdapter.this.controller;
                        AppInfo appInfo2 = appInfo;
                        appListController2.getClass();
                        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                        intent.setData(Uri.parse("package:" + ((CommonAppInfo) appInfo2).getPackageName()));
                        intent.addFlags(268468224);
                        try {
                            appListController2.mContext.startActivity(intent);
                            Log.i(appListController2.mTag, "handleItemClick");
                        } catch (ActivityNotFoundException e2) {
                            Log.e(appListController2.mTag, "handleItemClick() ] ActivityNotFoundException : " + e2.getMessage());
                        }
                    }
                });
                String string = this.context.getString(R.string.as_app_info);
                TextView textView3 = subAppListViewHolder.mainText;
                UiUtils.setAccessibilityForWidget(string + ", " + ((Object) (textView3 != null ? textView3.getText() : null)), imageView3, Button.class.getName());
            }
            boolean z2 = !(this.itemList.size() == i);
            View view = subAppListViewHolder.divider;
            if (view != null) {
                view.setVisibility(z2 ? 0 : 8);
            }
            CheckBox checkBox = subAppListViewHolder.checkBox;
            if (checkBox == null) {
                return;
            }
            checkBox.setChecked(((ArrayList) appListController.mListItemHandler.mCheckedItemList).contains(appInfo));
            return;
        }
        SubAppListHeaderViewHolder subAppListHeaderViewHolder = (SubAppListHeaderViewHolder) viewHolder;
        if (appListController.mSaAppsType == 5) {
            ImageView imageView4 = subAppListHeaderViewHolder.icon;
            if (imageView4 != null) {
                imageView4.setImageResource(R.drawable.as_app_cache_title);
            }
            List customViewText = getCustomViewText(5, R.plurals.as_app_cache_title);
            TextView textView4 = subAppListHeaderViewHolder.mainText;
            if (textView4 != null) {
                textView4.setText((CharSequence) ((ArrayList) customViewText).get(0));
            }
            TextView textView5 = subAppListHeaderViewHolder.subText;
            if (textView5 == null) {
                return;
            }
            textView5.setText((CharSequence) ((ArrayList) customViewText).get(1));
            return;
        }
        ImageView imageView5 = subAppListHeaderViewHolder.icon;
        if (imageView5 != null) {
            imageView5.setImageResource(R.drawable.as_unused_apps_title);
        }
        List customViewText2 = getCustomViewText(4, R.plurals.as_unused_apps_title);
        TextView textView6 = subAppListHeaderViewHolder.mainText;
        if (textView6 != null) {
            textView6.setText((CharSequence) ((ArrayList) customViewText2).get(0));
        }
        TextView textView7 = subAppListHeaderViewHolder.subText;
        if (textView7 != null) {
            textView7.setText((CharSequence) ((ArrayList) customViewText2).get(1));
        }
        final SortByItemLayout sortByItemLayout = subAppListHeaderViewHolder.sortByItem;
        if (sortByItemLayout != null) {
            sortByItemLayout.setVisibility(0);
            sortByItemLayout.controller = appListController;
            sortByItemLayout.updateSortByText();
            LinearLayout linearLayout = sortByItemLayout.sortBy;
            if (linearLayout != null) {
                final int i2 = 0;
                linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.settings.analyzestorage.ui.widget.SortByItemLayout$initSortByType$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        final SortByItemLayout sortByItemLayout2;
                        ImageView imageView6;
                        switch (i2) {
                            case 0:
                                if (!UiUtils.isValidClick$default(view2.getId()) || (imageView6 = (sortByItemLayout2 = sortByItemLayout).sortByOrder) == null) {
                                    return;
                                }
                                PopupMenu popupMenu = new PopupMenu(sortByItemLayout2.getContext(), imageView6, 8388613);
                                popupMenu.inflate(R.menu.sort_by_type_menu);
                                AppListController appListController2 = sortByItemLayout2.controller;
                                if (appListController2 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("controller");
                                    throw null;
                                }
                                final int i3 = 0;
                                Stream map = new ArrayList(appListController2.mListItemHandler.mAppDataList).stream().filter(new Predicate() { // from class: com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController$$ExternalSyntheticLambda2
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        switch (i3) {
                                            case 0:
                                                return ((AppInfo) obj) instanceof UnusedAppInfo;
                                            default:
                                                return ((UnusedAppInfo) obj).getLastLaunchTime() != 0;
                                        }
                                    }
                                }).map(new AppListController$$ExternalSyntheticLambda3());
                                final int i4 = 1;
                                boolean anyMatch = map.anyMatch(new Predicate() { // from class: com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController$$ExternalSyntheticLambda2
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        switch (i4) {
                                            case 0:
                                                return ((AppInfo) obj) instanceof UnusedAppInfo;
                                            default:
                                                return ((UnusedAppInfo) obj).getLastLaunchTime() != 0;
                                        }
                                    }
                                });
                                MenuBuilder menuBuilder = popupMenu.mMenu;
                                int i5 = R.id.sort_by_date_used;
                                if (!anyMatch) {
                                    menuBuilder.findItem(R.id.sort_by_date_used).setVisible(false);
                                }
                                int i6 = PreferenceManager.getDefaultSharedPreferences(sortByItemLayout2.getContext()).getInt("sort_type_unused_apps", 0);
                                if (i6 == 0) {
                                    i5 = R.id.sort_by_size;
                                } else if (i6 == 1 || i6 != 2) {
                                    i5 = R.id.sort_by_name;
                                }
                                MenuItem findItem = menuBuilder.findItem(i5);
                                if (findItem != null) {
                                    findItem.setChecked(true);
                                }
                                popupMenu.mMenuItemClickListener = new PopupMenu.OnMenuItemClickListener() { // from class: com.samsung.android.settings.analyzestorage.ui.widget.SortByItemLayout$dropListView$1
                                    @Override // androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener
                                    public final void onMenuItemClick(MenuItem menuItem) {
                                        if (menuItem.isChecked()) {
                                            return;
                                        }
                                        int i7 = 1;
                                        menuItem.setChecked(true);
                                        int itemId = menuItem.getItemId();
                                        int i8 = SortByItemLayout.$r8$clinit;
                                        SortByItemLayout sortByItemLayout3 = SortByItemLayout.this;
                                        sortByItemLayout3.getClass();
                                        if (itemId == R.id.sort_by_date_used) {
                                            i7 = 2;
                                        } else if (itemId != R.id.sort_by_name && itemId == R.id.sort_by_size) {
                                            i7 = 0;
                                        }
                                        boolean z3 = PreferenceManager.getDefaultSharedPreferences(sortByItemLayout3.getContext()).getBoolean("sort_order_unused_apps", false);
                                        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(sortByItemLayout3.getContext()).edit();
                                        edit.putInt("sort_type_unused_apps", i7);
                                        edit.apply();
                                        AppListController appListController3 = sortByItemLayout3.controller;
                                        if (appListController3 == null) {
                                            Intrinsics.throwUninitializedPropertyAccessException("controller");
                                            throw null;
                                        }
                                        appListController3.applySort(i7, z3);
                                        sortByItemLayout3.updateSortByText();
                                    }
                                };
                                popupMenu.show();
                                return;
                            default:
                                if (UiUtils.isValidClick$default(view2.getId())) {
                                    int i7 = PreferenceManager.getDefaultSharedPreferences(sortByItemLayout.getContext()).getInt("sort_type_unused_apps", 0);
                                    boolean z3 = !PreferenceManager.getDefaultSharedPreferences(sortByItemLayout.getContext()).getBoolean("sort_order_unused_apps", false);
                                    SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(sortByItemLayout.getContext()).edit();
                                    edit.putBoolean("sort_order_unused_apps", z3);
                                    edit.apply();
                                    AppListController appListController3 = sortByItemLayout.controller;
                                    if (appListController3 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("controller");
                                        throw null;
                                    }
                                    appListController3.applySort(i7, z3);
                                    SortByItemLayout sortByItemLayout3 = sortByItemLayout;
                                    sortByItemLayout3.getClass();
                                    int i8 = z3 ? R.drawable.as_sort_ascending : R.drawable.as_sort_descending;
                                    ImageView imageView7 = sortByItemLayout3.sortByOrder;
                                    if (imageView7 != null) {
                                        imageView7.setImageResource(i8);
                                        return;
                                    }
                                    return;
                                }
                                return;
                        }
                    }
                });
            }
            Context context2 = sortByItemLayout.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            if (!FeatureManager$UiFeature.isDefaultTheme(context2)) {
                int color = sortByItemLayout.getContext().getColor(R.color.as_theme_sub_text_color);
                ImageView imageView6 = sortByItemLayout.sortByOrder;
                if (imageView6 != null) {
                    imageView6.setColorFilter(color);
                }
                ImageView imageView7 = sortByItemLayout.sortByIcon;
                if (imageView7 != null) {
                    imageView7.setColorFilter(color);
                }
                TextView textView8 = sortByItemLayout.sortByText;
                if (textView8 != null) {
                    textView8.setTextColor(color);
                }
            }
            int i3 = PreferenceManager.getDefaultSharedPreferences(sortByItemLayout.getContext()).getBoolean("sort_order_unused_apps", false) ? R.drawable.as_sort_ascending : R.drawable.as_sort_descending;
            ImageView imageView8 = sortByItemLayout.sortByOrder;
            if (imageView8 != null) {
                imageView8.setImageResource(i3);
            }
            ImageView imageView9 = sortByItemLayout.sortByOrder;
            if (imageView9 != null) {
                final int i4 = 1;
                imageView9.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.settings.analyzestorage.ui.widget.SortByItemLayout$initSortByType$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        final SortByItemLayout sortByItemLayout2;
                        ImageView imageView62;
                        switch (i4) {
                            case 0:
                                if (!UiUtils.isValidClick$default(view2.getId()) || (imageView62 = (sortByItemLayout2 = sortByItemLayout).sortByOrder) == null) {
                                    return;
                                }
                                PopupMenu popupMenu = new PopupMenu(sortByItemLayout2.getContext(), imageView62, 8388613);
                                popupMenu.inflate(R.menu.sort_by_type_menu);
                                AppListController appListController2 = sortByItemLayout2.controller;
                                if (appListController2 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("controller");
                                    throw null;
                                }
                                final int i32 = 0;
                                Stream map = new ArrayList(appListController2.mListItemHandler.mAppDataList).stream().filter(new Predicate() { // from class: com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController$$ExternalSyntheticLambda2
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        switch (i32) {
                                            case 0:
                                                return ((AppInfo) obj) instanceof UnusedAppInfo;
                                            default:
                                                return ((UnusedAppInfo) obj).getLastLaunchTime() != 0;
                                        }
                                    }
                                }).map(new AppListController$$ExternalSyntheticLambda3());
                                final int i42 = 1;
                                boolean anyMatch = map.anyMatch(new Predicate() { // from class: com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController$$ExternalSyntheticLambda2
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        switch (i42) {
                                            case 0:
                                                return ((AppInfo) obj) instanceof UnusedAppInfo;
                                            default:
                                                return ((UnusedAppInfo) obj).getLastLaunchTime() != 0;
                                        }
                                    }
                                });
                                MenuBuilder menuBuilder = popupMenu.mMenu;
                                int i5 = R.id.sort_by_date_used;
                                if (!anyMatch) {
                                    menuBuilder.findItem(R.id.sort_by_date_used).setVisible(false);
                                }
                                int i6 = PreferenceManager.getDefaultSharedPreferences(sortByItemLayout2.getContext()).getInt("sort_type_unused_apps", 0);
                                if (i6 == 0) {
                                    i5 = R.id.sort_by_size;
                                } else if (i6 == 1 || i6 != 2) {
                                    i5 = R.id.sort_by_name;
                                }
                                MenuItem findItem = menuBuilder.findItem(i5);
                                if (findItem != null) {
                                    findItem.setChecked(true);
                                }
                                popupMenu.mMenuItemClickListener = new PopupMenu.OnMenuItemClickListener() { // from class: com.samsung.android.settings.analyzestorage.ui.widget.SortByItemLayout$dropListView$1
                                    @Override // androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener
                                    public final void onMenuItemClick(MenuItem menuItem) {
                                        if (menuItem.isChecked()) {
                                            return;
                                        }
                                        int i7 = 1;
                                        menuItem.setChecked(true);
                                        int itemId = menuItem.getItemId();
                                        int i8 = SortByItemLayout.$r8$clinit;
                                        SortByItemLayout sortByItemLayout3 = SortByItemLayout.this;
                                        sortByItemLayout3.getClass();
                                        if (itemId == R.id.sort_by_date_used) {
                                            i7 = 2;
                                        } else if (itemId != R.id.sort_by_name && itemId == R.id.sort_by_size) {
                                            i7 = 0;
                                        }
                                        boolean z3 = PreferenceManager.getDefaultSharedPreferences(sortByItemLayout3.getContext()).getBoolean("sort_order_unused_apps", false);
                                        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(sortByItemLayout3.getContext()).edit();
                                        edit.putInt("sort_type_unused_apps", i7);
                                        edit.apply();
                                        AppListController appListController3 = sortByItemLayout3.controller;
                                        if (appListController3 == null) {
                                            Intrinsics.throwUninitializedPropertyAccessException("controller");
                                            throw null;
                                        }
                                        appListController3.applySort(i7, z3);
                                        sortByItemLayout3.updateSortByText();
                                    }
                                };
                                popupMenu.show();
                                return;
                            default:
                                if (UiUtils.isValidClick$default(view2.getId())) {
                                    int i7 = PreferenceManager.getDefaultSharedPreferences(sortByItemLayout.getContext()).getInt("sort_type_unused_apps", 0);
                                    boolean z3 = !PreferenceManager.getDefaultSharedPreferences(sortByItemLayout.getContext()).getBoolean("sort_order_unused_apps", false);
                                    SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(sortByItemLayout.getContext()).edit();
                                    edit.putBoolean("sort_order_unused_apps", z3);
                                    edit.apply();
                                    AppListController appListController3 = sortByItemLayout.controller;
                                    if (appListController3 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("controller");
                                        throw null;
                                    }
                                    appListController3.applySort(i7, z3);
                                    SortByItemLayout sortByItemLayout3 = sortByItemLayout;
                                    sortByItemLayout3.getClass();
                                    int i8 = z3 ? R.drawable.as_sort_ascending : R.drawable.as_sort_descending;
                                    ImageView imageView72 = sortByItemLayout3.sortByOrder;
                                    if (imageView72 != null) {
                                        imageView72.setImageResource(i8);
                                        return;
                                    }
                                    return;
                                }
                                return;
                        }
                    }
                });
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v3, types: [com.samsung.android.settings.analyzestorage.ui.widget.viewholder.SubAppListViewHolder] */
    /* JADX WARN: Type inference failed for: r7v4, types: [androidx.recyclerview.widget.RecyclerView$ViewHolder] */
    /* JADX WARN: Type inference failed for: r7v6, types: [androidx.recyclerview.widget.RecyclerView$ViewHolder, com.samsung.android.settings.analyzestorage.ui.widget.viewholder.SubAppListHeaderViewHolder] */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        final ?? subAppListViewHolder;
        Intrinsics.checkNotNullParameter(parent, "parent");
        if (i == 0) {
            View m = MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(parent, R.layout.as_sub_app_list_header, parent, false);
            Intrinsics.checkNotNull(m);
            subAppListViewHolder = new SubAppListHeaderViewHolder(m);
            ImageView imageView = (ImageView) m.findViewById(R.id.page_icon);
            subAppListViewHolder.icon = imageView;
            TextView textView = (TextView) m.findViewById(R.id.page_main_text);
            subAppListViewHolder.mainText = textView;
            TextView textView2 = (TextView) m.findViewById(R.id.page_sub_text);
            subAppListViewHolder.subText = textView2;
            subAppListViewHolder.sortByItem = (SortByItemLayout) m.findViewById(R.id.sort_by_layout);
            if (!FeatureManager$UiFeature.isDefaultTheme(this.context)) {
                int color = this.context.getColor(R.color.as_theme_main_text_color);
                if (imageView != null) {
                    imageView.setColorFilter(color);
                }
                if (textView != null) {
                    textView.setTextColor(color);
                }
                if (textView2 != null) {
                    textView2.setTextColor(color);
                }
            }
        } else {
            View m2 = MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(parent, R.layout.as_sub_app_list_item, parent, false);
            this.listMarginManager.getClass();
            Intrinsics.checkNotNull(m2);
            subAppListViewHolder = new SubAppListViewHolder(m2);
            if (!(this.controller.mSaAppsType == 5)) {
                ViewStub viewStub = subAppListViewHolder.appInfoIconStub;
                if ((viewStub != null ? viewStub.getParent() : null) != null) {
                    subAppListViewHolder.appInfoIconStub.setOnInflateListener(new ViewStub.OnInflateListener() { // from class: com.samsung.android.settings.analyzestorage.ui.widget.viewholder.SubAppListViewHolder$initAppInfoIcon$1
                        @Override // android.view.ViewStub.OnInflateListener
                        public final void onInflate(ViewStub viewStub2, View view) {
                            SubAppListViewHolder.this.appInfoIcon = (ImageView) view.findViewById(R.id.app_info_icon);
                            SubAppListViewHolder.this.verticalDivider = view.findViewById(R.id.vertical_divider);
                        }
                    });
                    subAppListViewHolder.appInfoIconStub.inflate();
                }
            }
            final Function1 function1 = new Function1() { // from class: com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage.AsSubListAdapter$initItemClickListener$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    KeyMouseCommand keyMouseCommand;
                    View it = (View) obj;
                    Intrinsics.checkNotNullParameter(it, "it");
                    int bindingAdapterPosition = SubAppListViewHolder.this.getBindingAdapterPosition() - 1;
                    AppListController appListController = this.controller;
                    if (KeyboardMouseManager.sMouseEventCallBack != null && (keyMouseCommand = KeyboardMouseManager.sKeyMouseCommand) != null) {
                        keyMouseCommand.onMouseDown(appListController, bindingAdapterPosition);
                        if (KeyboardMouseManager.sPressedKey.get(4)) {
                            KeyboardMouseManager.sMouseEventCallBack.this$0.adapter.notifyDataSetChanged();
                            return Unit.INSTANCE;
                        }
                    }
                    ListItemHandler listItemHandler = this.controller.mListItemHandler;
                    boolean z = !((ArrayList) listItemHandler.mCheckedItemList).contains(listItemHandler.getItemAt(bindingAdapterPosition));
                    this.controller.mListItemHandler.setItemChecked(bindingAdapterPosition, z);
                    CheckBox checkBox = SubAppListViewHolder.this.checkBox;
                    if (checkBox != null) {
                        checkBox.setChecked(z);
                    }
                    if (bindingAdapterPosition <= -1) {
                        bindingAdapterPosition = 0;
                    }
                    ShiftKeyMouseCommand.sStartPosition = bindingAdapterPosition;
                    return Unit.INSTANCE;
                }
            };
            m2.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.settings.analyzestorage.ui.widget.viewholder.SubAppListViewHolder$initClickListener$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Function1 function12 = Function1.this;
                    Intrinsics.checkNotNull(view);
                    function12.invoke(view);
                }
            });
            if (!FeatureManager$UiFeature.isDefaultTheme(this.context)) {
                int color2 = this.context.getColor(R.color.as_theme_main_text_color);
                int color3 = this.context.getColor(R.color.as_theme_sub_text_color);
                int color4 = this.context.getColor(R.color.as_theme_icon_color);
                TextView textView3 = subAppListViewHolder.mainText;
                if (textView3 != null) {
                    textView3.setTextColor(color2);
                }
                TextView textView4 = subAppListViewHolder.subText;
                if (textView4 != null) {
                    textView4.setTextColor(color3);
                }
                View view = subAppListViewHolder.divider;
                if (view != null) {
                    view.setBackgroundColor(color4);
                }
                View view2 = subAppListViewHolder.verticalDivider;
                if (view2 != null) {
                    view2.setBackgroundColor(color4);
                }
                ImageView imageView2 = subAppListViewHolder.appInfoIcon;
                if (imageView2 != null) {
                    imageView2.setColorFilter(color4);
                }
            }
        }
        return subAppListViewHolder;
    }

    public final void updateItems(List list) {
        AppListController appListController = this.controller;
        ListItemHandler listItemHandler = appListController.mListItemHandler;
        ((HashSet) listItemHandler.mCheckableItemList).clear();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((HashSet) listItemHandler.mCheckableItemList).add((AppInfo) it.next());
        }
        this.itemList = list;
        if (appListController.mSaAppsType == 4) {
            Set set = appListController.selectedAppPackageNameList;
            Intrinsics.checkNotNull(set);
            HashSet hashSet = (HashSet) set;
            if (!hashSet.isEmpty()) {
                int i = 0;
                for (Object obj : this.itemList) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                        throw null;
                    }
                    if (hashSet.contains(((CommonAppInfo) ((AppInfo) obj)).getPackageName())) {
                        appListController.mListItemHandler.setItemChecked(i, true);
                    }
                    i = i2;
                }
                ((HashSet) appListController.selectedAppPackageNameList).clear();
            }
        }
        notifyDataSetChanged();
    }
}

package com.android.settings.core;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.android.settings.dashboard.CategoryManager;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.homepage.SettingsHomepageActivity;
import com.android.settingslib.drawer.DashboardCategory;
import com.android.settingslib.drawer.Tile;

import com.samsung.android.settings.PkgUtils;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CategoryMixin implements LifecycleObserver {
    public static final ArraySet sTileDenylist = new ArraySet();
    public int mCategoriesUpdateTaskCount;
    public final Context mContext;
    public boolean mFirstOnResume;
    public final PackageReceiver mPackageReceiver = new PackageReceiver();
    public final List mCategoryListeners = new ArrayList();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class CategoriesUpdateTask extends AsyncTask {
        public final CategoryManager mCategoryManager;
        public Map mPreviousTileMap;

        public CategoriesUpdateTask() {
            CategoryMixin.this.mCategoriesUpdateTaskCount++;
            this.mCategoryManager = CategoryManager.get(CategoryMixin.this.mContext);
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            Boolean[] boolArr = (Boolean[]) objArr;
            this.mPreviousTileMap = this.mCategoryManager.getTileByComponentMap();
            CategoryManager categoryManager = this.mCategoryManager;
            Context context = CategoryMixin.this.mContext;
            synchronized (categoryManager) {
                boolean applyNewConfig =
                        categoryManager.mInterestingConfigChanges.applyNewConfig(
                                context.getResources());
                categoryManager.mCategories = null;
                categoryManager.tryInitCategories(context, applyNewConfig);
            }
            CategoryManager categoryManager2 = this.mCategoryManager;
            ArraySet arraySet = CategoryMixin.sTileDenylist;
            synchronized (categoryManager2) {
                if (categoryManager2.mCategories == null) {
                    Log.w("CategoryManager", "Category is null, skipping denylist update");
                } else {
                    for (int i = 0; i < ((ArrayList) categoryManager2.mCategories).size(); i++) {
                        DashboardCategory dashboardCategory =
                                (DashboardCategory)
                                        ((ArrayList) categoryManager2.mCategories).get(i);
                        int i2 = 0;
                        while (i2 < ((ArrayList) dashboardCategory.mTiles).size()) {
                            if (arraySet.contains(
                                    ((Tile) ((ArrayList) dashboardCategory.mTiles).get(i2))
                                            .mIntent.getComponent())) {
                                int i3 = i2 - 1;
                                synchronized (dashboardCategory) {
                                    ((ArrayList) dashboardCategory.mTiles).remove(i2);
                                }
                                i2 = i3;
                            }
                            i2++;
                        }
                    }
                }
            }
            if (!boolArr[0].booleanValue()) {
                return null;
            }
            final ArraySet arraySet2 = new ArraySet();
            ArrayMap arrayMap = (ArrayMap) this.mCategoryManager.getTileByComponentMap();
            arrayMap.forEach(
                    new BiConsumer() { // from class:
                                       // com.android.settings.core.CategoryMixin$CategoriesUpdateTask$$ExternalSyntheticLambda0
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            CategoryMixin.CategoriesUpdateTask categoriesUpdateTask =
                                    CategoryMixin.CategoriesUpdateTask.this;
                            Set set = arraySet2;
                            ComponentName componentName = (ComponentName) obj;
                            Tile tile = (Tile) obj2;
                            Tile tile2 =
                                    (Tile)
                                            ((ArrayMap) categoriesUpdateTask.mPreviousTileMap)
                                                    .get(componentName);
                            if (tile2 == null) {
                                Log.i(
                                        "CategoryMixin",
                                        "Tile added: " + componentName.flattenToShortString());
                                set.add(tile.mCategory);
                                return;
                            }
                            if (TextUtils.equals(
                                            tile.getTitle(CategoryMixin.this.mContext),
                                            tile2.getTitle(CategoryMixin.this.mContext))
                                    && TextUtils.equals(
                                            tile.getSummary(CategoryMixin.this.mContext),
                                            tile2.getSummary(CategoryMixin.this.mContext))) {
                                return;
                            }
                            Log.i(
                                    "CategoryMixin",
                                    "Tile changed: " + componentName.flattenToShortString());
                            set.add(tile.mCategory);
                        }
                    });
            ArraySet arraySet3 = new ArraySet(((ArrayMap) this.mPreviousTileMap).keySet());
            arraySet3.removeAll(arrayMap.keySet());
            arraySet3.forEach(
                    new Consumer() { // from class:
                                     // com.android.settings.core.CategoryMixin$CategoriesUpdateTask$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            CategoryMixin.CategoriesUpdateTask categoriesUpdateTask =
                                    CategoryMixin.CategoriesUpdateTask.this;
                            Set set = arraySet2;
                            ComponentName componentName = (ComponentName) obj;
                            categoriesUpdateTask.getClass();
                            Log.i(
                                    "CategoryMixin",
                                    "Tile removed: " + componentName.flattenToShortString());
                            set.add(
                                    ((Tile)
                                                    ((ArrayMap)
                                                                    categoriesUpdateTask
                                                                            .mPreviousTileMap)
                                                            .get(componentName))
                                            .mCategory);
                        }
                    });
            return arraySet2;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Set set) {
            if (set == null || !set.isEmpty()) {
                CategoryMixin.this.onCategoriesChanged(set);
            }
            CategoryMixin categoryMixin = CategoryMixin.this;
            categoryMixin.mCategoriesUpdateTaskCount--;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface CategoryHandler {
        CategoryMixin getCategoryMixin();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PackageReceiver extends BroadcastReceiver {
        public PackageReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Uri data = intent.getData();
            if (data != null) {
                String encodedSchemeSpecificPart = data.getEncodedSchemeSpecificPart();
                if (!TextUtils.isEmpty(encodedSchemeSpecificPart)) {
                    AbsAdapter$$ExternalSyntheticOutline0.m(
                            "PkgUtil.inValidatePackageCache : ",
                            encodedSchemeSpecificPart,
                            "CategoryMixin");
                    HashMap hashMap = PkgUtils.mHasPackageMap;
                    SemLog.i("PkgUtil", "inValidatePackageCache + " + encodedSchemeSpecificPart);
                    PkgUtils.mHasPackageMap.remove(encodedSchemeSpecificPart);
                    PkgUtils.mPackageEnabledMap.remove(encodedSchemeSpecificPart);
                }
            }
            CategoryMixin.this.updateCategories(true);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SecCategoriesUpdateTask extends CategoriesUpdateTask {
        public SecCategoriesUpdateTask() {
            super();
        }

        @Override // com.android.settings.core.CategoryMixin.CategoriesUpdateTask,
                  // android.os.AsyncTask
        public final void onPostExecute(Set set) {
            CategoryMixin categoryMixin = CategoryMixin.this;
            categoryMixin.mCategoriesUpdateTaskCount--;
        }
    }

    public CategoryMixin(Context context) {
        this.mFirstOnResume = true;
        this.mContext = context;
        if (CategoryManager.sInstance == null || !(context instanceof SettingsHomepageActivity)) {
            return;
        }
        Log.i("CategoryMixin", "Maybe Settings Relaunch, set mFirstOnResume false");
        this.mFirstOnResume = false;
    }

    public void onCategoriesChanged(final Set<String> set) {
        ((ArrayList) this.mCategoryListeners)
                .forEach(
                        new Consumer() { // from class:
                                         // com.android.settings.core.CategoryMixin$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ((DashboardFragment) obj).onCategoriesChanged(set);
                            }
                        });
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        this.mContext.unregisterReceiver(this.mPackageReceiver);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addDataScheme("package");
        this.mContext.registerReceiver(this.mPackageReceiver, intentFilter);
        if (!this.mFirstOnResume || !(this.mContext instanceof SettingsHomepageActivity)) {
            updateCategories(false);
        } else {
            Log.i("CategoryMixin", "Skip categories update");
            this.mFirstOnResume = false;
        }
    }

    public final void updateCategories(boolean z) {
        if (this.mCategoriesUpdateTaskCount < 2) {
            new CategoriesUpdateTask().execute(Boolean.valueOf(z));
        }
    }
}

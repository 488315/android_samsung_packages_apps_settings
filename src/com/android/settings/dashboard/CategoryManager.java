package com.android.settings.dashboard;

import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;

import com.android.settings.homepage.HighlightableMenu;
import com.android.settings.safetycenter.SafetyCenterManagerWrapper;
import com.android.settingslib.applications.InterestingConfigChanges;
import com.android.settingslib.drawer.CategoryKey;
import com.android.settingslib.drawer.DashboardCategory;
import com.android.settingslib.drawer.ProviderTile;
import com.android.settingslib.drawer.Tile;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.settings.core.SecTileUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CategoryManager {
    public static CategoryManager sInstance;
    public List mCategories;
    public final InterestingConfigChanges mInterestingConfigChanges;
    public final Map mTileByComponentCache = new ArrayMap();
    public final Map mCategoryByKeyMap = new ArrayMap();

    public CategoryManager(Context context) {
        InterestingConfigChanges interestingConfigChanges = new InterestingConfigChanges();
        this.mInterestingConfigChanges = interestingConfigChanges;
        interestingConfigChanges.applyNewConfig(context.getResources());
    }

    public static CategoryManager get(Context context) {
        if (sInstance == null) {
            sInstance = new CategoryManager(context);
        }
        return sInstance;
    }

    public synchronized void backwardCompatCleanupForCategory(
            Map<Pair<String, String>, Tile> map, Map<String, DashboardCategory> map2) {
        try {
            HashMap hashMap = new HashMap();
            for (Map.Entry<Pair<String, String>, Tile> entry : map.entrySet()) {
                String str = (String) entry.getKey().first;
                List list = (List) hashMap.get(str);
                if (list == null) {
                    list = new ArrayList();
                    hashMap.put(str, list);
                }
                list.add(entry.getValue());
            }
            Iterator it = hashMap.entrySet().iterator();
            while (it.hasNext()) {
                List<Tile> list2 = (List) ((Map.Entry) it.next()).getValue();
                Iterator it2 = list2.iterator();
                boolean z = false;
                boolean z2 = false;
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    if (!((HashMap) CategoryKey.KEY_COMPAT_MAP)
                            .containsKey(((Tile) it2.next()).mCategory)) {
                        z = true;
                        break;
                    }
                    z2 = true;
                }
                if (z2 && !z) {
                    for (Tile tile : list2) {
                        String str2 =
                                (String) ((HashMap) CategoryKey.KEY_COMPAT_MAP).get(tile.mCategory);
                        tile.mCategory = str2;
                        DashboardCategory dashboardCategory = map2.get(str2);
                        if (dashboardCategory == null) {
                            dashboardCategory = new DashboardCategory(str2);
                            map2.put(str2, dashboardCategory);
                        }
                        dashboardCategory.addTile(tile);
                    }
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void filterDuplicateTiles(Map<String, DashboardCategory> map) {
        try {
            Iterator<Map.Entry<String, DashboardCategory>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                DashboardCategory value = it.next().getValue();
                int size = ((ArrayList) value.mTiles).size();
                ArraySet arraySet = new ArraySet();
                ArraySet arraySet2 = new ArraySet();
                for (int i = size - 1; i >= 0; i--) {
                    Tile tile = (Tile) ((ArrayList) value.mTiles).get(i);
                    if (tile instanceof ProviderTile) {
                        String description = tile.getDescription();
                        if (arraySet.contains(description)) {
                            synchronized (value) {
                                ((ArrayList) value.mTiles).remove(i);
                            }
                        } else {
                            arraySet.add(description);
                        }
                    } else {
                        ComponentName component = tile.mIntent.getComponent();
                        if (arraySet2.contains(component)) {
                            synchronized (value) {
                                ((ArrayList) value.mTiles).remove(i);
                            }
                        } else {
                            arraySet2.add(component);
                        }
                    }
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized Map getTileByComponentMap() {
        final ArrayMap arrayMap = new ArrayMap();
        List list = this.mCategories;
        if (list == null) {
            Log.w("CategoryManager", "Category is null, no tiles");
            return arrayMap;
        }
        ((ArrayList) list)
                .forEach(
                        new Consumer() { // from class:
                                         // com.android.settings.dashboard.CategoryManager$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                Map map = arrayMap;
                                DashboardCategory dashboardCategory = (DashboardCategory) obj;
                                for (int i = 0;
                                        i < ((ArrayList) dashboardCategory.mTiles).size();
                                        i++) {
                                    Tile tile =
                                            (Tile) ((ArrayList) dashboardCategory.mTiles).get(i);
                                    map.put(tile.mIntent.getComponent(), tile);
                                }
                            }
                        });
        return arrayMap;
    }

    public final synchronized DashboardCategory getTilesByCategory(Context context, String str) {
        synchronized (this) {
            tryInitCategories(context, false);
        }
        return (DashboardCategory) ((ArrayMap) this.mCategoryByKeyMap).get(str);
        return (DashboardCategory) ((ArrayMap) this.mCategoryByKeyMap).get(str);
    }

    public synchronized void mergeSecurityPrivacyKeys(
            Context context,
            Map<Pair<String, String>, Tile> map,
            Map<String, DashboardCategory> map2) {
        try {
            SafetyCenterManagerWrapper.get().getClass();
            if (SafetyCenterManagerWrapper.isEnabled(context)) {
                Iterator<Map.Entry<Pair<String, String>, Tile>> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Tile value = it.next().getValue();
                    if (!Objects.equals(
                                    value.mCategory,
                                    "com.android.settings.category.ia.advanced_security")
                            && !Objects.equals(
                                    value.mCategory, "com.android.settings.category.ia.privacy")) {}
                    value.mCategory =
                            "com.android.settings.category.ia.more_security_privacy_settings";
                    DashboardCategory dashboardCategory =
                            map2.get(
                                    "com.android.settings.category.ia.more_security_privacy_settings");
                    if (dashboardCategory == null) {
                        dashboardCategory =
                                new DashboardCategory(
                                        "com.android.settings.category.ia.more_security_privacy_settings");
                        map2.put(
                                "com.android.settings.category.ia.more_security_privacy_settings",
                                dashboardCategory);
                    }
                    dashboardCategory.addTile(value);
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void sortCategories(Context context, Map<String, DashboardCategory> map) {
        Iterator<Map.Entry<String, DashboardCategory>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            DashboardCategory value = it.next().getValue();
            final String packageName = context.getPackageName();
            synchronized (value) {
                Collections.sort(
                        value.mTiles,
                        new Comparator() { // from class:
                                           // com.android.settingslib.drawer.DashboardCategory$$ExternalSyntheticLambda0
                            @Override // java.util.Comparator
                            public final int compare(Object obj, Object obj2) {
                                String str = packageName;
                                Tile tile = (Tile) obj;
                                Tile tile2 = (Tile) obj2;
                                int order = tile2.getOrder() - tile.getOrder();
                                if (order != 0) {
                                    return order;
                                }
                                String str2 = tile.mComponentPackage;
                                String str3 = tile2.mComponentPackage;
                                int compare = String.CASE_INSENSITIVE_ORDER.compare(str2, str3);
                                if (compare == 0) {
                                    return compare;
                                }
                                if (TextUtils.equals(str2, str)) {
                                    return -1;
                                }
                                if (TextUtils.equals(str3, str)) {
                                    return 1;
                                }
                                return compare;
                            }
                        });
            }
        }
    }

    public final synchronized void tryInitCategories(Context context, boolean z) {
        try {
            if (WizardManagerHelper.isUserSetupComplete(context)) {
                if (this.mCategories == null) {
                    boolean isEmpty = ((ArrayMap) this.mCategoryByKeyMap).isEmpty();
                    if (z) {
                        ((ArrayMap) this.mTileByComponentCache).clear();
                    }
                    ((ArrayMap) this.mCategoryByKeyMap).clear();
                    List<DashboardCategory> categories =
                            SecTileUtils.getCategories(context, this.mTileByComponentCache);
                    this.mCategories = categories;
                    for (DashboardCategory dashboardCategory : categories) {
                        ((ArrayMap) this.mCategoryByKeyMap)
                                .put(dashboardCategory.key, dashboardCategory);
                    }
                    backwardCompatCleanupForCategory(
                            this.mTileByComponentCache, this.mCategoryByKeyMap);
                    sortCategories(context, this.mCategoryByKeyMap);
                    filterDuplicateTiles(this.mCategoryByKeyMap);
                    if (isEmpty) {
                        DashboardCategory dashboardCategory2 =
                                (DashboardCategory)
                                        ((ArrayMap) this.mCategoryByKeyMap)
                                                .get("com.android.settings.category.ia.homepage");
                        if (dashboardCategory2 == null) {
                            return;
                        }
                        Iterator it = ((ArrayList) dashboardCategory2.getTiles()).iterator();
                        while (it.hasNext()) {
                            Tile tile = (Tile) it.next();
                            String key = tile.getKey(context);
                            if (TextUtils.isEmpty(key)) {
                                Log.w(
                                        "CategoryManager",
                                        "Key hint missing for homepage tile: "
                                                + ((Object) tile.getTitle(context)));
                            } else {
                                HighlightableMenu.addMenuKey(key);
                            }
                        }
                    }
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}

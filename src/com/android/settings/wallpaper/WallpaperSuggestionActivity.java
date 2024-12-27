package com.android.settings.wallpaper;

import android.R;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.provider.SearchIndexableData;

import com.android.settings.display.WallpaperPreferenceController;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WallpaperSuggestionActivity extends StyleSuggestionActivityBase {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.wallpaper.WallpaperSuggestionActivity$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            WallpaperPreferenceController wallpaperPreferenceController =
                    new WallpaperPreferenceController(context, "unused key");
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            String title = wallpaperPreferenceController.getTitle();
            searchIndexableRaw.title = title;
            searchIndexableRaw.screenTitle = title;
            ((SearchIndexableData) searchIndexableRaw).intentTargetPackage =
                    context.getPackageName();
            ((SearchIndexableData) searchIndexableRaw).intentTargetClass =
                    WallpaperSuggestionActivity.class.getName();
            ((SearchIndexableData) searchIndexableRaw).intentAction = "android.intent.action.MAIN";
            ((SearchIndexableData) searchIndexableRaw).key = "wallpaper_type";
            searchIndexableRaw.keywords = wallpaperPreferenceController.getKeywords();
            arrayList.add(searchIndexableRaw);
            return arrayList;
        }
    }

    public static boolean isSuggestionComplete(Context context) {
        return !context.getResources().getBoolean(R.bool.config_enhanced_iwlan_handover_check)
                || ((WallpaperManager) context.getSystemService("wallpaper")).getWallpaperId(1) > 0;
    }

    @Override // com.android.settings.wallpaper.StyleSuggestionActivityBase
    public final void addExtras(Intent intent) {
        if (!intent.getBooleanExtra("isSetupFlow", false)) {
            intent.putExtra("com.android.launcher3.WALLPAPER_FLAVOR", "focus_wallpaper");
        } else {
            intent.putExtra("com.android.launcher3.WALLPAPER_FLAVOR", "wallpaper_only");
            intent.putExtra(
                    getResources()
                            .getString(
                                    com.android.settings.R.string
                                            .config_wallpaper_picker_launch_extra),
                    "app_launched_suw");
        }
    }
}

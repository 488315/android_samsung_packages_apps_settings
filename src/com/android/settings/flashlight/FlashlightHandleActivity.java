package com.android.settings.flashlight;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchIndexableData;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FlashlightHandleActivity extends Activity {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.flashlight.FlashlightHandleActivity$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (!FlashlightSlice.isFlashlightAvailable(context)) {
                Log.i("FlashlightActivity", "Flashlight is unavailable");
                ((ArrayList) nonIndexableKeys).add("flashlight");
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            if (!context.getResources()
                    .getBoolean(R.bool.config_settingsintelligence_slice_supported)) {
                Log.d("FlashlightActivity", "Search doesn't support Slice");
                return arrayList;
            }
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            searchIndexableRaw.title = context.getString(R.string.power_flashlight);
            searchIndexableRaw.screenTitle = context.getString(R.string.power_flashlight);
            searchIndexableRaw.keywords = context.getString(R.string.keywords_flashlight);
            ((SearchIndexableData) searchIndexableRaw).intentTargetPackage =
                    context.getPackageName();
            ((SearchIndexableData) searchIndexableRaw).intentTargetClass =
                    FlashlightHandleActivity.class.getName();
            ((SearchIndexableData) searchIndexableRaw).intentAction = "android.intent.action.MAIN";
            ((SearchIndexableData) searchIndexableRaw).key = "flashlight";
            arrayList.add(searchIndexableRaw);
            return arrayList;
        }
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getIntent().getBooleanExtra("fallback_to_homepage", false)) {
            startActivity(new Intent("android.settings.SETTINGS").setPackage(getPackageName()));
        }
        finish();
    }
}

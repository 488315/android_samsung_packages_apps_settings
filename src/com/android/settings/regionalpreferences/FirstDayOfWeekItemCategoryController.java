package com.android.settings.regionalpreferences;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.widget.PreferenceCategoryController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FirstDayOfWeekItemCategoryController extends PreferenceCategoryController {
    private static final String KEY_PREFERENCE_CATEGORY_FIRST_DAY_OF_WEEK_ITEM =
            "first_day_of_week_item_category";
    private static final String KEY_PREFERENCE_FIRST_DAY_OF_WEEK_ITEM =
            "first_day_of_week_item_list";
    private static final String LOG_TAG = "FirstDayOfWeekItemCategoryController";
    private FirstDayOfWeekItemListController mFirstDayOfWeekItemListController;
    private PreferenceCategory mPreferenceCategory;

    public FirstDayOfWeekItemCategoryController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        PreferenceCategory preferenceCategory =
                (PreferenceCategory)
                        preferenceScreen.findPreference(
                                KEY_PREFERENCE_CATEGORY_FIRST_DAY_OF_WEEK_ITEM);
        this.mPreferenceCategory = preferenceCategory;
        if (preferenceCategory == null) {
            Log.d(LOG_TAG, "displayPreference(), Can not find the category.");
            return;
        }
        preferenceCategory.setVisible(isAvailable());
        FirstDayOfWeekItemListController firstDayOfWeekItemListController =
                new FirstDayOfWeekItemListController(
                        this.mContext, KEY_PREFERENCE_FIRST_DAY_OF_WEEK_ITEM);
        this.mFirstDayOfWeekItemListController = firstDayOfWeekItemListController;
        firstDayOfWeekItemListController.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.widget.PreferenceCategoryController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.widget.PreferenceCategoryController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.widget.PreferenceCategoryController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}

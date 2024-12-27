package com.android.settings.regionalpreferences;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.android.settings.R;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FirstDayOfWeekItemListController
        extends RegionalPreferenceListBasePreferenceController {
    private static final String KEY_PREFERENCE_CATEGORY_FIRST_DAY_OF_WEEK_ITEM =
            "first_day_of_week_item_category";
    private static final String KEY_PREFERENCE_FIRST_DAY_OF_WEEK_ITEM =
            "first_day_of_week_item_list";

    public FirstDayOfWeekItemListController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.regionalpreferences.RegionalPreferenceListBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.regionalpreferences.RegionalPreferenceListBasePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.regionalpreferences.RegionalPreferenceListBasePreferenceController
    public String getExtensionTypes() {
        return "fw";
    }

    @Override // com.android.settings.regionalpreferences.RegionalPreferenceListBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.regionalpreferences.RegionalPreferenceListBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.regionalpreferences.RegionalPreferenceListBasePreferenceController
    public int getMetricsActionKey() {
        return 1827;
    }

    @Override // com.android.settings.regionalpreferences.RegionalPreferenceListBasePreferenceController
    public String getPreferenceCategoryKey() {
        return KEY_PREFERENCE_CATEGORY_FIRST_DAY_OF_WEEK_ITEM;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_PREFERENCE_FIRST_DAY_OF_WEEK_ITEM;
    }

    @Override // com.android.settings.regionalpreferences.RegionalPreferenceListBasePreferenceController
    public String getPreferenceTitle(String str) {
        return RegionalPreferencesDataUtils.dayConverter(this.mContext, str);
    }

    @Override // com.android.settings.regionalpreferences.RegionalPreferenceListBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.regionalpreferences.RegionalPreferenceListBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.regionalpreferences.RegionalPreferenceListBasePreferenceController
    public String[] getUnitValues() {
        return this.mContext.getResources().getStringArray(R.array.first_day_of_week);
    }

    @Override // com.android.settings.regionalpreferences.RegionalPreferenceListBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.regionalpreferences.RegionalPreferenceListBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.regionalpreferences.RegionalPreferenceListBasePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.regionalpreferences.RegionalPreferenceListBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.regionalpreferences.RegionalPreferenceListBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.regionalpreferences.RegionalPreferenceListBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.regionalpreferences.RegionalPreferenceListBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.regionalpreferences.RegionalPreferenceListBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.regionalpreferences.RegionalPreferenceListBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.regionalpreferences.RegionalPreferenceListBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
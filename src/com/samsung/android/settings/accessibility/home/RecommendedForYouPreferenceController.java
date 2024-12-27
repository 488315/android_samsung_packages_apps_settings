package com.samsung.android.settings.accessibility.home;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.accessibility.shortcuts.EditShortcutsPreferenceFragment$$ExternalSyntheticLambda3;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.accessibility.AccessibilityRune;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController;
import com.samsung.android.settings.accessibility.recommend.UsingFunctionItem;
import com.samsung.android.settings.accessibility.recommend.UsingFunctionsProvider;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class RecommendedForYouPreferenceController extends BasePreferenceController
        implements AccessibilityObservableController {
    private List<UsingFunctionItem> functionItems;

    public RecommendedForYouPreferenceController(Context context, String str) {
        super(context, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getSummary$0(UsingFunctionItem usingFunctionItem) {
        return usingFunctionItem.isTurnedOn(this.mContext);
    }

    private void updateUsingFunctionProfile(int i) {
        SharedPreferences accessibilitySharedPreferences =
                SecAccessibilityUtils.getAccessibilitySharedPreferences(this.mContext);
        if (accessibilitySharedPreferences.getBoolean("using_function_profile", false)) {
            return;
        }
        int i2 = accessibilitySharedPreferences.getInt("using_function_changed_count", 0);
        if (i != accessibilitySharedPreferences.getInt("using_function_count", 0)) {
            int i3 = i2 + 1;
            SharedPreferences.Editor putInt =
                    accessibilitySharedPreferences
                            .edit()
                            .putInt("using_function_changed_count", i3)
                            .putInt("using_function_count", i);
            if (i3 >= 10) {
                putInt.putBoolean("using_function_profile", true);
            }
            putInt.apply();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        if (findPreference != null) {
            findPreference.seslSetSummaryColor(
                    this.mContext.getColorStateList(R.color.text_color_primary_dark));
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return AccessibilityRune.getFloatingFeatureBooleanValue(
                        "SEC_FLOATING_FEATURE_COMMON_SUPPORT_DISABLED_MENU_K05")
                ? 3
                : 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        int count =
                (int)
                        this.functionItems.stream()
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.samsung.android.settings.accessibility.home.RecommendedForYouPreferenceController$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                boolean lambda$getSummary$0;
                                                lambda$getSummary$0 =
                                                        RecommendedForYouPreferenceController.this
                                                                .lambda$getSummary$0(
                                                                        (UsingFunctionItem) obj);
                                                return lambda$getSummary$0;
                                            }
                                        })
                                .count();
        updateUsingFunctionProfile(count);
        return count > 0
                ? this.mContext
                        .getResources()
                        .getQuantityString(
                                R.plurals.recommended_for_you_enabled_summary,
                                count,
                                Integer.valueOf(count))
                : ApnSettings.MVNO_NONE;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController
    public List<Uri> getUriList() {
        this.functionItems = UsingFunctionsProvider.getInstance(this.mContext).usingFunctionItems;
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(
                Stream.of(
                                (Object[])
                                        new String[] {
                                            "enabled_accessibility_services",
                                            "accessibility_button_targets",
                                            "accessibility_shortcut_target_service",
                                            "accessibility_direct_access_target_service"
                                        })
                        .map(new RecommendedForYouPreferenceController$$ExternalSyntheticLambda1(0))
                        .toList());
        arrayList.addAll(
                this.functionItems.stream()
                        .map(new RecommendedForYouPreferenceController$$ExternalSyntheticLambda1(1))
                        .flatMap(new EditShortcutsPreferenceFragment$$ExternalSyntheticLambda3())
                        .distinct()
                        .toList());
        return arrayList;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}

package com.samsung.android.settings.accessibility.recommend;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.accessibility.shortcuts.EditShortcutsPreferenceFragment$$ExternalSyntheticLambda3;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController;
import com.samsung.android.settings.accessibility.home.RecommendedForYouPreferenceController$$ExternalSyntheticLambda1;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class UsingFunctionsCategoryPreferenceController extends BasePreferenceController
        implements AccessibilityObservableController {
    private final List<UsingFunctionItem> functionItems;

    public UsingFunctionsCategoryPreferenceController(Context context, String str) {
        super(context, str);
        this.functionItems = UsingFunctionsProvider.getInstance(this.mContext).usingFunctionItems;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) preferenceScreen.findPreference(getPreferenceKey());
        if (preferenceCategory != null) {
            Iterator<UsingFunctionItem> it = this.functionItems.iterator();
            while (it.hasNext()) {
                preferenceCategory.addPreference(
                        new UsingFunctionPreference(this.mContext, it.next()));
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController
    public List<Uri> getUriList() {
        return (List)
                this.functionItems.stream()
                        .map(new RecommendedForYouPreferenceController$$ExternalSyntheticLambda1(1))
                        .flatMap(new EditShortcutsPreferenceFragment$$ExternalSyntheticLambda3())
                        .distinct()
                        .collect(Collectors.toList());
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (preference instanceof PreferenceCategory) {
            PreferenceCategory preferenceCategory = (PreferenceCategory) preference;
            int i = 0;
            boolean z = false;
            for (int i2 = 0; i2 < preferenceCategory.getPreferenceCount(); i2++) {
                Preference preference2 = preferenceCategory.getPreference(i2);
                if (preference2 instanceof UsingFunctionPreference) {
                    UsingFunctionPreference usingFunctionPreference =
                            (UsingFunctionPreference) preference2;
                    boolean isTurnedOn =
                            usingFunctionPreference.item.isTurnedOn(
                                    usingFunctionPreference.getContext());
                    usingFunctionPreference.setVisible(isTurnedOn);
                    z |= isTurnedOn;
                    if (isTurnedOn) {
                        i++;
                    }
                }
            }
            preference.setTitle(
                    this.mContext
                            .getResources()
                            .getQuantityString(
                                    R.plurals.recommended_for_you_enabled_summary,
                                    i,
                                    Integer.valueOf(i)));
            preferenceCategory.setVisible(z);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}

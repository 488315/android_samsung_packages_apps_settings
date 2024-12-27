package com.samsung.android.settings.accessibility.advanced.shortcut;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ShortcutGuidePreferenceController extends BasePreferenceController {
    public ShortcutGuidePreferenceController(Context context, String str) {
        super(context, str);
    }

    public static ArrayList<String> getFeatureList(Context context, int i) {
        String shortcutService = SecAccessibilityUtils.getShortcutService(context, i);
        Set emptySet =
                shortcutService == null
                        ? Collections.emptySet()
                        : new HashSet(
                                Arrays.asList(
                                        shortcutService.split(
                                                SecAccessibilityUtils
                                                        .COMPONENT_NAME_SEPARATOR_STRING)));
        ArrayList<String> arrayList = new ArrayList<>();
        if (emptySet.isEmpty()) {
            return arrayList;
        }
        List list = ShortcutFeatureManager.candidateBuilders;
        HashMap hashMap = new HashMap();
        Map componentNameAccessibilityServiceInfoMap =
                ShortcutFeatureManager.getComponentNameAccessibilityServiceInfoMap(context);
        Iterator it =
                ((ArrayList)
                                ShortcutFeatureManager.getTalkbackCandidateBuilder(
                                        componentNameAccessibilityServiceInfoMap))
                        .iterator();
        while (it.hasNext()) {
            ShortcutSimpleEntry buildEntry =
                    ((ShortcutCandidate.Builder) it.next()).buildEntry(context);
            if (buildEntry != null) {
                hashMap.put(buildEntry.getKey(), buildEntry);
            }
        }
        Iterator it2 =
                ((ArrayList)
                                ShortcutFeatureManager.getServiceCandidateBuilder(
                                        componentNameAccessibilityServiceInfoMap))
                        .iterator();
        while (it2.hasNext()) {
            ShortcutSimpleEntry buildEntry2 =
                    ((ShortcutServiceCandidate.Builder) it2.next()).buildEntry(context);
            if (buildEntry2 != null) {
                hashMap.put(buildEntry2.getKey(), buildEntry2);
            }
        }
        Iterator it3 =
                ((ArrayList) ShortcutFeatureManager.getActivityCandidateBuilders(context))
                        .iterator();
        while (it3.hasNext()) {
            ShortcutSimpleEntry buildEntry3 =
                    ((ShortcutActivityCandidate.Builder) it3.next()).buildEntry(context);
            if (buildEntry3 != null) {
                hashMap.put(buildEntry3.getKey(), buildEntry3);
            }
        }
        ArrayList arrayList2 = new ArrayList(hashMap.values());
        Collections.sort(arrayList2);
        Iterator it4 = arrayList2.iterator();
        while (it4.hasNext()) {
            ShortcutSimpleEntry shortcutSimpleEntry = (ShortcutSimpleEntry) it4.next();
            if (emptySet.contains(shortcutSimpleEntry.getKey())
                    && shortcutSimpleEntry.getValue() != null
                    && !shortcutSimpleEntry.getValue().isEmpty()) {
                arrayList.add(shortcutSimpleEntry.getValue());
            }
        }
        return arrayList;
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

    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    public abstract int getShortcutType();

    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

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
        super.updateState(preference);
        ArrayList<String> featureList = getFeatureList(this.mContext, getShortcutType());
        if (featureList.isEmpty()) {
            preference.setSummary(R.string.accessibility_shortcut_none);
            return;
        }
        preference.setSummary(
                String.join(this.mContext.getString(R.string.comma) + " ", featureList));
    }

    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}

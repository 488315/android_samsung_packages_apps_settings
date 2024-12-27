package com.android.settings.notification.modes;

import android.app.Flags;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.provider.SearchIndexableData;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
class ZenModesListPreferenceController extends BasePreferenceController {
    protected static final String KEY = "zen_modes_list";
    protected ZenModesBackend mBackend;
    protected Fragment mParent;

    public ZenModesListPreferenceController(
            Context context, Fragment fragment, ZenModesBackend zenModesBackend) {
        super(context, KEY);
        this.mParent = fragment;
        this.mBackend = zenModesBackend;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return Flags.modesUi() ? 1 : 3;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY;
    }

    @Override // com.android.settings.slices.Sliceable
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

    @Override // com.android.settings.core.BasePreferenceController
    public void updateDynamicRawDataToIndex(List<SearchIndexableRaw> list) {
        if (Flags.modesUi() && this.mBackend != null) {
            Resources resources = this.mContext.getResources();
            Iterator it = ((ArrayList) this.mBackend.getModes()).iterator();
            while (it.hasNext()) {
                ZenMode zenMode = (ZenMode) it.next();
                SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(this.mContext);
                ((SearchIndexableData) searchIndexableRaw).key = zenMode.mId;
                searchIndexableRaw.title = zenMode.mRule.getName();
                searchIndexableRaw.screenTitle = resources.getString(R.string.zen_modes_list_title);
                list.add(searchIndexableRaw);
            }
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (this.mBackend == null) {
            return;
        }
        PreferenceCategory preferenceCategory = (PreferenceCategory) preference;
        HashMap hashMap = new HashMap();
        for (int i = 0; i < preferenceCategory.getPreferenceCount(); i++) {
            ZenModesListItemPreference zenModesListItemPreference =
                    (ZenModesListItemPreference) preferenceCategory.getPreference(i);
            hashMap.put(zenModesListItemPreference.getKey(), zenModesListItemPreference);
        }
        ArrayList arrayList = (ArrayList) this.mBackend.getModes();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ZenMode zenMode = (ZenMode) it.next();
            ZenModesListItemPreference zenModesListItemPreference2 =
                    (ZenModesListItemPreference) hashMap.get(zenMode.mId);
            if (zenModesListItemPreference2 != null) {
                zenModesListItemPreference2.setZenMode(zenMode);
            } else {
                zenModesListItemPreference2 =
                        new ZenModesListItemPreference(this.mContext, zenMode);
                preferenceCategory.addPreference(zenModesListItemPreference2);
            }
            zenModesListItemPreference2.setOrder(arrayList.indexOf(zenMode));
            hashMap.remove(zenMode.mId);
        }
        Iterator it2 = hashMap.keySet().iterator();
        while (it2.hasNext()) {
            preferenceCategory.removePreferenceRecursively((String) it2.next());
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}

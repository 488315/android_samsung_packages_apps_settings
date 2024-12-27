package com.android.settings.wifi.savedaccesspoints2;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.wifi.WifiEntryPreference;
import com.android.settings.wifi.details.WifiNetworkDetailsFragment;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SavedAccessPointsPreferenceController2 extends BasePreferenceController
        implements Preference.OnPreferenceClickListener {
    private SavedAccessPointsWifiSettings2 mHost;
    private PreferenceGroup mPreferenceGroup;
    List<WifiEntry> mWifiEntries;

    public SavedAccessPointsPreferenceController2(Context context, String str) {
        super(context, str);
        this.mWifiEntries = new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$updatePreference$0(
            WifiEntryPreference wifiEntryPreference, WifiEntry wifiEntry) {
        return TextUtils.equals(wifiEntryPreference.getKey(), wifiEntry.getKey());
    }

    private void updatePreference() {
        ArrayList arrayList = new ArrayList();
        int preferenceCount = this.mPreferenceGroup.getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            final WifiEntryPreference wifiEntryPreference =
                    (WifiEntryPreference) this.mPreferenceGroup.getPreference(i);
            WifiEntry orElse =
                    this.mWifiEntries.stream()
                            .filter(
                                    new Predicate() { // from class:
                                                      // com.android.settings.wifi.savedaccesspoints2.SavedAccessPointsPreferenceController2$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            boolean lambda$updatePreference$0;
                                            lambda$updatePreference$0 =
                                                    SavedAccessPointsPreferenceController2
                                                            .lambda$updatePreference$0(
                                                                    WifiEntryPreference.this,
                                                                    (WifiEntry) obj);
                                            return lambda$updatePreference$0;
                                        }
                                    })
                            .findFirst()
                            .orElse(null);
            if (orElse != null) {
                wifiEntryPreference.mWifiEntry = orElse;
                orElse.setListener(wifiEntryPreference);
                wifiEntryPreference.refresh();
            } else {
                arrayList.add(wifiEntryPreference.getKey());
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            PreferenceGroup preferenceGroup = this.mPreferenceGroup;
            preferenceGroup.removePreference(preferenceGroup.findPreference(str));
        }
        for (WifiEntry wifiEntry : this.mWifiEntries) {
            if (this.mPreferenceGroup.findPreference(wifiEntry.getKey()) == null) {
                WifiEntryPreference wifiEntryPreference2 =
                        new WifiEntryPreference(this.mContext, wifiEntry);
                wifiEntryPreference2.setKey(wifiEntry.getKey());
                wifiEntryPreference2.setOnPreferenceClickListener(this);
                this.mPreferenceGroup.addPreference(wifiEntryPreference2);
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        this.mPreferenceGroup =
                (PreferenceGroup) preferenceScreen.findPreference(getPreferenceKey());
        updatePreference();
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mWifiEntries.size() > 0 ? 0 : 2;
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

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference preference) {
        SavedAccessPointsWifiSettings2 savedAccessPointsWifiSettings2 = this.mHost;
        if (savedAccessPointsWifiSettings2 == null) {
            return false;
        }
        String key = preference.getKey();
        CharSequence title = preference.getTitle();
        savedAccessPointsWifiSettings2.removeDialog(1);
        if (TextUtils.isEmpty(key)) {
            Log.e("SavedAccessPoints2", "Not able to show WifiEntry of an empty key");
            return false;
        }
        Bundle m = AbsAdapter$1$$ExternalSyntheticOutline0.m("key_chosen_wifientry_key", key);
        SubSettingLauncher subSettingLauncher =
                new SubSettingLauncher(savedAccessPointsWifiSettings2.getContext());
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mTitle = title;
        launchRequest.mDestinationName = WifiNetworkDetailsFragment.class.getName();
        launchRequest.mArguments = m;
        launchRequest.mSourceMetricsCategory = 106;
        subSettingLauncher.launch();
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public SavedAccessPointsPreferenceController2 setHost(
            SavedAccessPointsWifiSettings2 savedAccessPointsWifiSettings2) {
        this.mHost = savedAccessPointsWifiSettings2;
        return this;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public void displayPreference(PreferenceScreen preferenceScreen, List<WifiEntry> list) {
        if (list != null && !list.isEmpty()) {
            this.mWifiEntries = list;
        } else {
            this.mWifiEntries.clear();
        }
        displayPreference(preferenceScreen);
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}

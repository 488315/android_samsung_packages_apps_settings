package com.samsung.android.settings.asbase.vibration;

import android.content.Context;
import android.os.Bundle;
import android.provider.SearchIndexableData;
import android.provider.SearchIndexableResource;

import com.android.settings.R;
import com.android.settings.core.OnActivityResultListener;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.Indexable$SearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.asbase.audio.SoundSettings;
import com.samsung.android.settings.asbase.utils.VibUtils;
import com.samsung.android.settings.gts.GtsGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class VibrationSystemIntensitySettings extends DashboardFragment
        implements OnActivityResultListener {
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1();
    public SecVibrationIntensityHelper mVibIntensityHelper;
    public SecVibrationIntensityHelper.AnonymousClass1 mVibSeekBarTouchListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.vibration.VibrationSystemIntensitySettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            ArrayList arrayList = new ArrayList();
            if (!VibUtils.hasSystemVibrationMenu(context)) {
                return arrayList;
            }
            arrayList.add(new VibrationSystemIntensitySettingsPreferenceController(context));
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final HashMap getGtsResourceGroup() {
            HashMap hashMap = new HashMap();
            GtsGroup gtsGroup = GtsGroup.GROUP_KEY_VIBRATION_SYSTEM;
            hashMap.put("vibrate_on_touch", gtsGroup.getGroupName());
            hashMap.put("dialing_keypad_vibration", gtsGroup.getGroupName());
            hashMap.put("navigation_gestures_vibration", gtsGroup.getGroupName());
            hashMap.put("charging_feedback_vibration", gtsGroup.getGroupName());
            hashMap.put(SecSoundKeyboardVibrationController.KEY, gtsGroup.getGroupName());
            hashMap.put("camera_feedback_vibration", gtsGroup.getGroupName());
            return hashMap;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            ((ArrayList) nonIndexableKeys).add("sound_feedback_link");
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            if (!VibUtils.hasSystemVibrationMenu(context)) {
                return arrayList;
            }
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            ((SearchIndexableData) searchIndexableRaw).key =
                    SecSoundKeyboardVibrationController.KEY;
            searchIndexableRaw.title = String.valueOf(R.string.sec_keyboard_sound_vibration);
            searchIndexableRaw.screenTitle = context.getString(R.string.sec_vibration_system_title);
            arrayList.add(searchIndexableRaw);
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            if (!VibUtils.hasSystemVibrationMenu(context)) {
                return arrayList;
            }
            searchIndexableResource.xmlResId = R.xml.as_vibration_system_intensity_settings;
            arrayList.add(searchIndexableResource);
            return arrayList;
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return SoundSettings.class.getName();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final String getLogTag() {
        return "VibrationSystemIntensitySettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return FileType.SDOCX;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.as_vibration_system_intensity_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        super.onCreatePreferences(bundle, str);
        SecVibrationIntensityHelper secVibrationIntensityHelper =
                new SecVibrationIntensityHelper(getContext());
        this.mVibIntensityHelper = secVibrationIntensityHelper;
        secVibrationIntensityHelper.mTouchController =
                (SecVibrationIntensityTouchVibrationController)
                        use(SecVibrationIntensityTouchVibrationController.class);
        this.mVibSeekBarTouchListener = this.mVibIntensityHelper.mVibSeekBarTouchListener;
        SecVibrationSeekBarPreference secVibrationSeekBarPreference =
                (SecVibrationSeekBarPreference)
                        getPreferenceScreen().findPreference("system_vibration");
        if (secVibrationSeekBarPreference != null) {
            secVibrationSeekBarPreference.mTouchSeekBarCallBack = this.mVibSeekBarTouchListener;
            secVibrationSeekBarPreference.setTitle(R.string.sec_system_vibration_intensity);
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        SecVibrationIntensityHelper secVibrationIntensityHelper = this.mVibIntensityHelper;
        if (secVibrationIntensityHelper != null) {
            secVibrationIntensityHelper.mStopFlag = true;
            secVibrationIntensityHelper.stopVibration();
        }
        super.onPause();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        SecVibrationIntensityHelper secVibrationIntensityHelper = this.mVibIntensityHelper;
        if (secVibrationIntensityHelper != null) {
            secVibrationIntensityHelper.mStopFlag = false;
        }
        super.onResume();
    }
}

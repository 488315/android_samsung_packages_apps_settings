package com.samsung.android.settings.asbase.vibration;

import android.content.Context;
import android.os.Bundle;
import android.provider.SearchIndexableResource;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.Indexable$SearchIndexProvider;

import com.samsung.android.settings.asbase.audio.SoundSettings;
import com.samsung.android.settings.gts.GtsGroup;
import com.samsung.android.util.SemLog;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecVibrationIntensitySettings extends DashboardFragment {
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1();
    public SecVibrationIntensityHelper mVibIntensityHelper;
    public SecVibrationIntensityHelper.AnonymousClass1 mVibSeekBarTouchListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.vibration.SecVibrationIntensitySettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final HashMap getGtsResourceGroup() {
            HashMap hashMap = new HashMap();
            GtsGroup gtsGroup = GtsGroup.GROUP_KEY_VIBRATION_INTENSITY;
            hashMap.put("ring_vibration", gtsGroup.getGroupName());
            hashMap.put("notification_vibration", gtsGroup.getGroupName());
            hashMap.put("system_vibration", gtsGroup.getGroupName());
            hashMap.put("media_vibration", gtsGroup.getGroupName());
            hashMap.put("force_touch", gtsGroup.getGroupName());
            hashMap.put("vibration_sound_enabled", gtsGroup.getGroupName());
            return hashMap;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.className = SecVibrationIntensitySettings.class.getName();
            searchIndexableResource.xmlResId = R.xml.as_vibration_intensity_settings;
            return List.of(searchIndexableResource);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return new SecSoundVibrationFeedbackController(context).isAvailable();
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return SoundSettings.class.getName();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SecVibrationIntensitySettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return VolteConstants.ErrorCode.MDMN_PUSHCALL_TO_PRIMARY;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.as_vibration_intensity_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        super.onCreatePreferences(bundle, str);
        SecVibrationIntensityHelper secVibrationIntensityHelper =
                new SecVibrationIntensityHelper(getContext());
        this.mVibIntensityHelper = secVibrationIntensityHelper;
        secVibrationIntensityHelper.mRingtoneController =
                (SecVibrationIntensityIncomingCallController)
                        use(SecVibrationIntensityIncomingCallController.class);
        this.mVibIntensityHelper.mNotificationController =
                (SecVibrationIntensityNotificationController)
                        use(SecVibrationIntensityNotificationController.class);
        this.mVibIntensityHelper.mTouchController =
                (SecVibrationIntensityTouchVibrationController)
                        use(SecVibrationIntensityTouchVibrationController.class);
        SecVibrationIntensityHelper secVibrationIntensityHelper2 = this.mVibIntensityHelper;
        secVibrationIntensityHelper2.getClass();
        this.mVibIntensityHelper.mMediaController =
                (SecVibrationIntensityMediaController)
                        use(SecVibrationIntensityMediaController.class);
        this.mVibSeekBarTouchListener = this.mVibIntensityHelper.mVibSeekBarTouchListener;
        SecVibrationSeekBarPreference secVibrationSeekBarPreference =
                (SecVibrationSeekBarPreference)
                        getPreferenceScreen().findPreference("ring_vibration");
        if (secVibrationSeekBarPreference != null) {
            secVibrationSeekBarPreference.mTouchSeekBarCallBack = this.mVibSeekBarTouchListener;
        }
        SecVibrationSeekBarPreference secVibrationSeekBarPreference2 =
                (SecVibrationSeekBarPreference)
                        getPreferenceScreen().findPreference("notification_vibration");
        if (secVibrationSeekBarPreference2 != null) {
            secVibrationSeekBarPreference2.mTouchSeekBarCallBack = this.mVibSeekBarTouchListener;
        }
        SecVibrationSeekBarPreference secVibrationSeekBarPreference3 =
                (SecVibrationSeekBarPreference)
                        getPreferenceScreen().findPreference("system_vibration");
        if (secVibrationSeekBarPreference3 != null) {
            secVibrationSeekBarPreference3.mTouchSeekBarCallBack = this.mVibSeekBarTouchListener;
        }
        SecVibrationSeekBarPreference secVibrationSeekBarPreference4 =
                (SecVibrationSeekBarPreference) getPreferenceScreen().findPreference("force_touch");
        if (secVibrationSeekBarPreference4 != null) {
            secVibrationSeekBarPreference4.mTouchSeekBarCallBack = this.mVibSeekBarTouchListener;
        }
        SecVibrationSeekBarPreference secVibrationSeekBarPreference5 =
                (SecVibrationSeekBarPreference)
                        getPreferenceScreen().findPreference("media_vibration");
        if (secVibrationSeekBarPreference5 != null) {
            secVibrationSeekBarPreference5.mTouchSeekBarCallBack = this.mVibSeekBarTouchListener;
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        SemLog.i("SecVibrationIntensitySettings", "onPause");
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

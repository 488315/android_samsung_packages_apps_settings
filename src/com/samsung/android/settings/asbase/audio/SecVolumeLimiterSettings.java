package com.samsung.android.settings.asbase.audio;

import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.SearchIndexableData;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.widget.CompoundButton;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.search.Indexable$SearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.asbase.utils.VibUtils;
import com.samsung.android.settings.asbase.widget.SecVolumeLimiterSeekBarPreference;
import com.samsung.android.settings.logging.LoggingHelper;
import com.sec.ims.presence.ServiceTuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecVolumeLimiterSettings extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener {
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1();
    public AudioManager mAudioManager;
    public ContentResolver mContentResolver;
    public FragmentActivity mContext;
    public SecVolumeLimiterSeekBarPreference mMaximumVolume;
    public SettingsMainSwitchBar mSwitchBar;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.audio.SecVolumeLimiterSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            ((ArrayList) nonIndexableKeys).add("limiter_description");
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            searchIndexableRaw.title =
                    context.getResources().getString(R.string.sec_volume_limiter_title);
            searchIndexableRaw.screenTitle =
                    context.getResources()
                            .getString(
                                    VibUtils.hasVibrator(context)
                                            ? R.string.sound_and_vibrations_title
                                            : R.string.sound_settings);
            ((SearchIndexableData) searchIndexableRaw).key = "volume_limiter";
            arrayList.add(searchIndexableRaw);
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.as_volume_limiter_settings;
            return Arrays.asList(searchIndexableResource);
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return SecVolumeSettings.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4116;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_sounds";
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.show();
        this.mSwitchBar.setChecked(
                Settings.System.getInt(this.mContentResolver, "volumelimit_on", 0) != 0);
        setVolumeLimiter(Settings.System.getInt(this.mContentResolver, "volumelimit_on", 0) != 0);
        this.mSwitchBar.addOnSwitchChangeListener(this);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        LoggingHelper.insertEventLogging(4116, 4117, z);
        setVolumeLimiter(z);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mContext = activity;
        this.mContentResolver = activity.getContentResolver();
        this.mAudioManager =
                (AudioManager) this.mContext.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        addPreferencesFromResource(R.xml.as_volume_limiter_settings);
        SecVolumeLimiterSeekBarPreference secVolumeLimiterSeekBarPreference =
                (SecVolumeLimiterSeekBarPreference) findPreference("maximum_volume");
        this.mMaximumVolume = secVolumeLimiterSeekBarPreference;
        secVolumeLimiterSeekBarPreference.setEnabled(
                Settings.System.getInt(this.mContentResolver, "volumelimit_on", 0) != 0);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        this.mSwitchBar.removeOnSwitchChangeListener(this);
        this.mSwitchBar.hide();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mMaximumVolume.setEnabled(
                Settings.System.getInt(this.mContentResolver, "volumelimit_on", 0) != 0);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        this.mMaximumVolume.getClass();
    }

    public final void setVolumeLimiter(boolean z) {
        int i = Settings.System.getInt(this.mContentResolver, "volume_limiter_value", 9);
        Settings.System.putInt(this.mContentResolver, "volumelimit_on", z ? 1 : 0);
        this.mAudioManager.enableVolumeLimiter(z);
        this.mAudioManager.setVolumeLimiterValue(i);
        this.mMaximumVolume.setEnabled(z);
    }
}

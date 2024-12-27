package com.android.settings.notification;

import android.content.Context;
import android.content.Intent;
import android.media.AudioSystem;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.RingtonePreference;
import com.android.settings.Utils;
import com.android.settings.core.OnActivityResultListener;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.lifecycle.Lifecycle;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SoundWorkSettings extends DashboardFragment implements OnActivityResultListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.sound_work_settings);
    public RingtonePreference mRequestPreference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.SoundWorkSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return SoundWorkSettings.isSupportWorkProfileSound(context);
        }
    }

    public static final boolean isSupportWorkProfileSound(Context context) {
        return (Utils.getManagedProfileId(UserManager.get(context), UserHandle.myUserId())
                        != -10000)
                && (AudioSystem.isSingleVolume(context) ^ true);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        Lifecycle settingsLifecycle = getSettingsLifecycle();
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                new SoundWorkSettingsController(
                        context, this, settingsLifecycle, new AudioHelper(context)));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SoundWorkSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1877;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sound_work_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        RingtonePreference ringtonePreference = this.mRequestPreference;
        if (ringtonePreference != null) {
            ringtonePreference.onActivityResult(i, i2, intent);
            this.mRequestPreference = null;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            String string = bundle.getString("selected_preference", null);
            if (!TextUtils.isEmpty(string)) {
                this.mRequestPreference = (RingtonePreference) findPreference(string);
            }
        }
        replaceEnterprisePreferenceScreenTitle(
                "Settings.WORK_PROFILE_SOUND_SETTINGS_SECTION_HEADER",
                R.string.sound_work_settings);
        replaceEnterpriseStringTitle(
                "work_use_personal_sounds",
                "Settings.WORK_PROFILE_USE_PERSONAL_SOUNDS_TITLE",
                R.string.work_use_personal_sounds_title);
        replaceEnterpriseStringSummary(
                "work_use_personal_sounds",
                "Settings.WORK_PROFILE_USE_PERSONAL_SOUNDS_SUMMARY",
                R.string.work_use_personal_sounds_summary);
        replaceEnterpriseStringTitle(
                "work_ringtone",
                "Settings.WORK_PROFILE_RINGTONE_TITLE",
                R.string.work_ringtone_title);
        replaceEnterpriseStringTitle(
                "work_alarm_ringtone",
                "Settings.WORK_PROFILE_ALARM_RINGTONE_TITLE",
                R.string.work_alarm_ringtone_title);
        replaceEnterpriseStringTitle(
                "work_notification_ringtone",
                "Settings.WORK_PROFILE_NOTIFICATION_RINGTONE_TITLE",
                R.string.work_notification_ringtone_title);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (!(preference instanceof RingtonePreference)) {
            return super.onPreferenceTreeClick(preference);
        }
        writePreferenceClickMetric(preference);
        RingtonePreference ringtonePreference = (RingtonePreference) preference;
        this.mRequestPreference = ringtonePreference;
        ringtonePreference.onPrepareRingtonePickerIntent(ringtonePreference.getIntent());
        getActivity()
                .startActivityForResultAsUser(
                        this.mRequestPreference.getIntent(),
                        200,
                        null,
                        UserHandle.of(this.mRequestPreference.mUserId));
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        RingtonePreference ringtonePreference = this.mRequestPreference;
        if (ringtonePreference != null) {
            bundle.putString("selected_preference", ringtonePreference.getKey());
        }
    }
}

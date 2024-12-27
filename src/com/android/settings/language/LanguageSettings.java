package com.android.settings.language;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.PreferenceCategoryController;
import com.android.settingslib.core.lifecycle.Lifecycle;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LanguageSettings extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.language_settings);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.language.LanguageSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return LanguageSettings.buildPreferenceControllers$1(context, null);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return true;
        }
    }

    public static List buildPreferenceControllers$1(Context context, Lifecycle lifecycle) {
        ArrayList arrayList = new ArrayList();
        DefaultVoiceInputPreferenceController defaultVoiceInputPreferenceController =
                new DefaultVoiceInputPreferenceController(context);
        defaultVoiceInputPreferenceController.mContext = context;
        VoiceInputHelper voiceInputHelper = new VoiceInputHelper(context);
        defaultVoiceInputPreferenceController.mHelper = voiceInputHelper;
        voiceInputHelper.buildUi();
        if (lifecycle != null) {
            lifecycle.addObserver(defaultVoiceInputPreferenceController);
        }
        TtsPreferenceController ttsPreferenceController =
                new TtsPreferenceController(context, "tts_settings_summary");
        OnDeviceRecognitionPreferenceController onDeviceRecognitionPreferenceController =
                new OnDeviceRecognitionPreferenceController(
                        context, "on_device_recognition_settings");
        arrayList.add(defaultVoiceInputPreferenceController);
        arrayList.add(ttsPreferenceController);
        ArrayList arrayList2 =
                new ArrayList(
                        List.of(defaultVoiceInputPreferenceController, ttsPreferenceController));
        if (onDeviceRecognitionPreferenceController.isAvailable()) {
            arrayList.add(onDeviceRecognitionPreferenceController);
            arrayList2.add(onDeviceRecognitionPreferenceController);
        }
        arrayList.add(
                new PreferenceCategoryController(context, "speech_category")
                        .setChildren(arrayList2));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return buildPreferenceControllers$1(context, getSettingsLifecycle());
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "LanguageSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1950;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.language_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        activity.setTitle(R.string.languages_settings);
    }
}

package com.android.settings.language;

import android.content.ComponentName;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import com.android.settings.R;
import com.android.settings.applications.defaultapps.DefaultAppPickerFragment;
import com.android.settingslib.applications.DefaultAppInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DefaultVoiceInputPicker extends DefaultAppPickerFragment {
    public VoiceInputHelper mHelper;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class VoiceInputDefaultAppInfo extends DefaultAppInfo {
        public VoiceInputHelper.RecognizerInfo mInfo;

        @Override // com.android.settingslib.applications.DefaultAppInfo,
                  // com.android.settingslib.widget.CandidateInfo
        public final String getKey() {
            return this.mInfo.mKey;
        }

        @Override // com.android.settingslib.applications.DefaultAppInfo,
                  // com.android.settingslib.widget.CandidateInfo
        public final CharSequence loadLabel() {
            return this.mInfo.mLabel;
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final List getCandidates() {
        ArrayList arrayList = new ArrayList();
        Context context = getContext();
        Iterator it = this.mHelper.mAvailableRecognizerInfos.iterator();
        while (it.hasNext()) {
            VoiceInputHelper.RecognizerInfo recognizerInfo =
                    (VoiceInputHelper.RecognizerInfo) it.next();
            VoiceInputDefaultAppInfo voiceInputDefaultAppInfo =
                    new VoiceInputDefaultAppInfo(
                            context, this.mPm, this.mUserId, recognizerInfo.mComponentName, 0);
            voiceInputDefaultAppInfo.mInfo = recognizerInfo;
            arrayList.add(voiceInputDefaultAppInfo);
        }
        return arrayList;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final String getDefaultKey() {
        ComponentName componentName = this.mHelper.mCurrentRecognizer;
        if (componentName == null) {
            return null;
        }
        return componentName.flattenToShortString();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 844;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.default_voice_settings;
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPickerFragment,
              // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        VoiceInputHelper voiceInputHelper = new VoiceInputHelper(context);
        this.mHelper = voiceInputHelper;
        voiceInputHelper.buildUi();
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final boolean setDefaultKey(String str) {
        Iterator it = this.mHelper.mAvailableRecognizerInfos.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            if (TextUtils.equals(str, ((VoiceInputHelper.RecognizerInfo) it.next()).mKey)) {
                Settings.Secure.putString(
                        getContext().getContentResolver(), "voice_recognition_service", str);
                break;
            }
        }
        return true;
    }
}

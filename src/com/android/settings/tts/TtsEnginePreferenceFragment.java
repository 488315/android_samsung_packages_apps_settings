package com.android.settings.tts;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TtsEngines;
import android.util.Log;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.glance.session.SessionManagerImpl$scope$1$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.RadioButtonPickerFragment;
import com.android.settingslib.widget.CandidateInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TtsEnginePreferenceFragment extends RadioButtonPickerFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.tts_engine_picker);
    public Context mContext;
    public Map mEngineMap;
    public String mPreviousEngine;
    public TextToSpeech mTts = null;
    public TtsEngines mEnginesHelper = null;
    public final AnonymousClass1 mUpdateListener =
            new TextToSpeech
                    .OnInitListener() { // from class:
                                        // com.android.settings.tts.TtsEnginePreferenceFragment.1
                @Override // android.speech.tts.TextToSpeech.OnInitListener
                public final void onInit(int i) {
                    TtsEnginePreferenceFragment ttsEnginePreferenceFragment =
                            TtsEnginePreferenceFragment.this;
                    if (i == 0) {
                        Log.d(
                                "TtsEnginePrefFragment",
                                "Updating engine: Successfully bound to the engine: "
                                        + ttsEnginePreferenceFragment.mTts.getCurrentEngine());
                        Settings.Secure.putString(
                                ttsEnginePreferenceFragment.mContext.getContentResolver(),
                                "tts_default_synth",
                                ttsEnginePreferenceFragment.mTts.getCurrentEngine());
                        return;
                    }
                    ttsEnginePreferenceFragment.getClass();
                    Log.d(
                            "TtsEnginePrefFragment",
                            "Updating engine: Failed to bind to engine, reverting.");
                    if (ttsEnginePreferenceFragment.mPreviousEngine != null) {
                        ttsEnginePreferenceFragment.mTts =
                                new TextToSpeech(
                                        ttsEnginePreferenceFragment.mContext,
                                        null,
                                        ttsEnginePreferenceFragment.mPreviousEngine);
                        ttsEnginePreferenceFragment.updateCheckedState$1(
                                ttsEnginePreferenceFragment.mPreviousEngine);
                    }
                    ttsEnginePreferenceFragment.mPreviousEngine = null;
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EngineCandidateInfo extends CandidateInfo {
        public final TextToSpeech.EngineInfo mEngineInfo;

        public EngineCandidateInfo(TextToSpeech.EngineInfo engineInfo) {
            super(true);
            this.mEngineInfo = engineInfo;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final String getKey() {
            return this.mEngineInfo.name;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final Drawable loadIcon() {
            return null;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final CharSequence loadLabel() {
            return this.mEngineInfo.label;
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final List getCandidates() {
        ArrayList arrayList = new ArrayList();
        for (TextToSpeech.EngineInfo engineInfo : this.mEnginesHelper.getEngines()) {
            EngineCandidateInfo engineCandidateInfo = new EngineCandidateInfo(engineInfo);
            arrayList.add(engineCandidateInfo);
            ((HashMap) this.mEngineMap).put(engineInfo.name, engineCandidateInfo);
        }
        return arrayList;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final String getDefaultKey() {
        return this.mEnginesHelper.getDefaultEngine();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 93;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.tts_engine_picker;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        this.mContext = getContext().getApplicationContext();
        this.mEnginesHelper = new TtsEngines(this.mContext);
        this.mEngineMap = new HashMap();
        this.mTts = new TextToSpeech(this.mContext, null);
        super.onCreate(bundle);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        TextToSpeech textToSpeech = this.mTts;
        if (textToSpeech != null) {
            textToSpeech.shutdown();
            this.mTts = null;
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final void onRadioButtonConfirmed(final String str) {
        EngineCandidateInfo engineCandidateInfo =
                (EngineCandidateInfo) ((HashMap) this.mEngineMap).get(str);
        if (!(!engineCandidateInfo.mEngineInfo.system)) {
            setDefaultKey(str);
            return;
        }
        DialogInterface.OnClickListener onClickListener =
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.tts.TtsEnginePreferenceFragment$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        TtsEnginePreferenceFragment ttsEnginePreferenceFragment =
                                TtsEnginePreferenceFragment.this;
                        String str2 = str;
                        BaseSearchIndexProvider baseSearchIndexProvider =
                                TtsEnginePreferenceFragment.SEARCH_INDEX_DATA_PROVIDER;
                        ttsEnginePreferenceFragment.setDefaultKey(str2);
                    }
                };
        Log.i(
                "TtsEnginePrefFragment",
                "Displaying data alert for :" + engineCandidateInfo.mEngineInfo.name);
        AlertDialog.Builder builder = new AlertDialog.Builder(getPrefContext());
        builder.setTitle(android.R.string.dialog_alert_title);
        String string =
                this.mContext.getString(
                        R.string.tts_engine_security_warning,
                        engineCandidateInfo.mEngineInfo.label);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mMessage = string;
        alertParams.mCancelable = true;
        builder.setPositiveButton(android.R.string.ok, onClickListener);
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
        builder.create().show();
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final boolean setDefaultKey(String str) {
        Log.d("TtsEnginePrefFragment", "Updating default synth to : " + str);
        Log.i("TtsEnginePrefFragment", "Shutting down current tts engine");
        TextToSpeech textToSpeech = this.mTts;
        if (textToSpeech != null) {
            this.mPreviousEngine = textToSpeech.getCurrentEngine();
            try {
                this.mTts.shutdown();
                this.mTts = null;
            } catch (Exception e) {
                SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m(
                        "Error shutting down TTS engine", e, "TtsEnginePrefFragment");
            }
        }
        Log.i("TtsEnginePrefFragment", "Updating engine : Attempting to connect to engine: " + str);
        this.mTts = new TextToSpeech(this.mContext, this.mUpdateListener, str);
        Log.i("TtsEnginePrefFragment", "Success");
        updateCheckedState$1(str);
        return true;
    }
}

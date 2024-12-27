package com.android.settings.tts;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TtsEngines;
import android.speech.tts.UtteranceProgressListener;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.glance.session.SessionManagerImpl$scope$1$$ExternalSyntheticOutline0;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelProviderImpl;
import androidx.preference.Preference;
import androidx.preference.SecListPreference;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.applications.intentpicker.ProgressDialogFragment$$ExternalSyntheticOutline0;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.SeekBarPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecActionButtonsPreference;
import com.samsung.android.settings.widget.SecGearPreference;
import com.sec.ims.scab.CABContract;

import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TextToSpeechSettings extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener, SecGearPreference.OnGearClickListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass4(R.xml.sec_tts_settings);
    public SecActionButtonsPreference mActionButtons;
    public List mAvailableStrLocals;
    public Locale mCurrentDefaultLocale;
    public String mCurrentEngine;
    public SeekBarPreference mDefaultPitchPref;
    public SeekBarPreference mDefaultRatePref;
    public SecListPreference mLocalePreference;
    public final AnonymousClass1 mPitchObserver;
    public final AnonymousClass1 mSpeechRateObserver;
    public UserManager mUserManager;
    public int mDefaultPitch = 100;
    public int mDefaultRate = 100;
    public int mSelectedLocaleIndex = -1;
    public TextToSpeech mTts = null;
    public TtsEngines mEnginesHelper = null;
    public String mSampleText = null;
    public final TextToSpeechSettings$$ExternalSyntheticLambda0 mInitListener =
            new TextToSpeech
                    .OnInitListener() { // from class:
                                        // com.android.settings.tts.TextToSpeechSettings$$ExternalSyntheticLambda0
                @Override // android.speech.tts.TextToSpeech.OnInitListener
                public final void onInit(int i) {
                    TextToSpeechSettings textToSpeechSettings = TextToSpeechSettings.this;
                    if (i == 0) {
                        textToSpeechSettings.successSetup();
                    } else {
                        textToSpeechSettings.updateWidgetState(false);
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.tts.TextToSpeechSettings$3, reason: invalid class name */
    public final class AnonymousClass3 extends UtteranceProgressListener {
        public AnonymousClass3() {}

        @Override // android.speech.tts.UtteranceProgressListener
        public final void onDone(String str) {
            TextToSpeechSettings textToSpeechSettings = TextToSpeechSettings.this;
            BaseSearchIndexProvider baseSearchIndexProvider =
                    TextToSpeechSettings.SEARCH_INDEX_DATA_PROVIDER;
            textToSpeechSettings.updateWidgetState(true);
        }

        @Override // android.speech.tts.UtteranceProgressListener
        public final void onError(String str) {
            Log.e("TextToSpeechSettings", "Error while trying to synthesize sample text");
            TextToSpeechSettings textToSpeechSettings = TextToSpeechSettings.this;
            BaseSearchIndexProvider baseSearchIndexProvider =
                    TextToSpeechSettings.SEARCH_INDEX_DATA_PROVIDER;
            textToSpeechSettings.updateWidgetState(true);
        }

        @Override // android.speech.tts.UtteranceProgressListener
        public final void onStart(String str) {
            TextToSpeechSettings textToSpeechSettings = TextToSpeechSettings.this;
            BaseSearchIndexProvider baseSearchIndexProvider =
                    TextToSpeechSettings.SEARCH_INDEX_DATA_PROVIDER;
            textToSpeechSettings.updateWidgetState(false);
        }

        @Override // android.speech.tts.UtteranceProgressListener
        public final void onStop(String str, boolean z) {
            super.onStop(str, z);
            TextToSpeechSettings textToSpeechSettings = TextToSpeechSettings.this;
            BaseSearchIndexProvider baseSearchIndexProvider =
                    TextToSpeechSettings.SEARCH_INDEX_DATA_PROVIDER;
            textToSpeechSettings.updateWidgetState(true);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.tts.TextToSpeechSettings$4, reason: invalid class name */
    public final class AnonymousClass4 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return !new TtsEngines(context).getEngines().isEmpty()
                    && context.getResources().getBoolean(R.bool.config_show_tts_settings_summary);
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settings.tts.TextToSpeechSettings$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.settings.tts.TextToSpeechSettings$1] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.settings.tts.TextToSpeechSettings$1] */
    public TextToSpeechSettings() {
        final int i = 0;
        this.mSpeechRateObserver =
                new ContentObserver(
                        this,
                        new Handler()) { // from class:
                                         // com.android.settings.tts.TextToSpeechSettings.1
                    public final /* synthetic */ TextToSpeechSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i) {
                            case 0:
                                TextToSpeechSettings textToSpeechSettings = this.this$0;
                                BaseSearchIndexProvider baseSearchIndexProvider =
                                        TextToSpeechSettings.SEARCH_INDEX_DATA_PROVIDER;
                                textToSpeechSettings.mDefaultRate =
                                        Settings.Secure.getInt(
                                                textToSpeechSettings.getContentResolver(),
                                                "tts_default_rate",
                                                100);
                                TextToSpeechSettings textToSpeechSettings2 = this.this$0;
                                textToSpeechSettings2.mDefaultRatePref.setProgress(
                                        TextToSpeechSettings.getSeekBarProgressFromValue(
                                                textToSpeechSettings2.mDefaultRate,
                                                "tts_default_rate"));
                                break;
                            default:
                                TextToSpeechSettings textToSpeechSettings3 = this.this$0;
                                BaseSearchIndexProvider baseSearchIndexProvider2 =
                                        TextToSpeechSettings.SEARCH_INDEX_DATA_PROVIDER;
                                textToSpeechSettings3.mDefaultPitch =
                                        Settings.Secure.getInt(
                                                textToSpeechSettings3.getContentResolver(),
                                                "tts_default_pitch",
                                                100);
                                TextToSpeechSettings textToSpeechSettings4 = this.this$0;
                                textToSpeechSettings4.mDefaultPitchPref.setProgress(
                                        TextToSpeechSettings.getSeekBarProgressFromValue(
                                                textToSpeechSettings4.mDefaultPitch,
                                                "tts_default_pitch"));
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mPitchObserver =
                new ContentObserver(
                        this,
                        new Handler()) { // from class:
                                         // com.android.settings.tts.TextToSpeechSettings.1
                    public final /* synthetic */ TextToSpeechSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i2) {
                            case 0:
                                TextToSpeechSettings textToSpeechSettings = this.this$0;
                                BaseSearchIndexProvider baseSearchIndexProvider =
                                        TextToSpeechSettings.SEARCH_INDEX_DATA_PROVIDER;
                                textToSpeechSettings.mDefaultRate =
                                        Settings.Secure.getInt(
                                                textToSpeechSettings.getContentResolver(),
                                                "tts_default_rate",
                                                100);
                                TextToSpeechSettings textToSpeechSettings2 = this.this$0;
                                textToSpeechSettings2.mDefaultRatePref.setProgress(
                                        TextToSpeechSettings.getSeekBarProgressFromValue(
                                                textToSpeechSettings2.mDefaultRate,
                                                "tts_default_rate"));
                                break;
                            default:
                                TextToSpeechSettings textToSpeechSettings3 = this.this$0;
                                BaseSearchIndexProvider baseSearchIndexProvider2 =
                                        TextToSpeechSettings.SEARCH_INDEX_DATA_PROVIDER;
                                textToSpeechSettings3.mDefaultPitch =
                                        Settings.Secure.getInt(
                                                textToSpeechSettings3.getContentResolver(),
                                                "tts_default_pitch",
                                                100);
                                TextToSpeechSettings textToSpeechSettings4 = this.this$0;
                                textToSpeechSettings4.mDefaultPitchPref.setProgress(
                                        TextToSpeechSettings.getSeekBarProgressFromValue(
                                                textToSpeechSettings4.mDefaultPitch,
                                                "tts_default_pitch"));
                                break;
                        }
                    }
                };
    }

    public static int getSeekBarProgressFromValue(int i, String str) {
        return str.equals("tts_default_rate")
                ? i - 10
                : str.equals("tts_default_pitch") ? i - 25 : i;
    }

    public final void checkDefaultLocale() {
        Locale defaultLanguage = this.mTts.getDefaultLanguage();
        if (defaultLanguage == null) {
            Log.e(
                    "TextToSpeechSettings",
                    "Failed to get default language from engine " + this.mCurrentEngine);
            updateWidgetState(false);
            return;
        }
        Locale locale = this.mCurrentDefaultLocale;
        Locale parseLocaleString =
                this.mEnginesHelper.parseLocaleString(defaultLanguage.toString());
        this.mCurrentDefaultLocale = parseLocaleString;
        if (!Objects.equals(locale, parseLocaleString)) {
            this.mSampleText = null;
        }
        this.mTts.setLanguage(defaultLanguage);
        if (evaluateDefaultLocale() && this.mSampleText == null) {
            getSampleText();
        }
    }

    public final boolean evaluateDefaultLocale() {
        boolean z;
        Locale locale = this.mCurrentDefaultLocale;
        if (locale != null && this.mAvailableStrLocals != null) {
            try {
                String iSO3Language = locale.getISO3Language();
                if (!TextUtils.isEmpty(this.mCurrentDefaultLocale.getISO3Country())) {
                    iSO3Language = iSO3Language + "-" + this.mCurrentDefaultLocale.getISO3Country();
                }
                if (!TextUtils.isEmpty(this.mCurrentDefaultLocale.getVariant())) {
                    iSO3Language = iSO3Language + "-" + this.mCurrentDefaultLocale.getVariant();
                }
                Iterator it = this.mAvailableStrLocals.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = true;
                        break;
                    }
                    if (((String) it.next()).equalsIgnoreCase(iSO3Language)) {
                        z = false;
                        break;
                    }
                }
                int language = this.mTts.setLanguage(this.mCurrentDefaultLocale);
                if (language == -2 || language == -1 || z) {
                    updateWidgetState(false);
                    return false;
                }
                updateWidgetState(true);
                return true;
            } catch (MissingResourceException unused) {
                updateWidgetState(false);
            }
        }
        return false;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 94;
    }

    public final void getSampleText() {
        String currentEngine = this.mTts.getCurrentEngine();
        if (TextUtils.isEmpty(currentEngine)) {
            currentEngine = this.mTts.getDefaultEngine();
        }
        Intent intent = new Intent("android.speech.tts.engine.GET_SAMPLE_TEXT");
        intent.putExtra("language", this.mCurrentDefaultLocale.getLanguage());
        intent.putExtra(
                CABContract.CABBusinessContactAddress.COUNTRY,
                this.mCurrentDefaultLocale.getCountry());
        intent.putExtra("variant", this.mCurrentDefaultLocale.getVariant());
        intent.setPackage(currentEngine);
        try {
            startActivityForResult(intent, 1983);
        } catch (ActivityNotFoundException unused) {
            Log.e(
                    "TextToSpeechSettings",
                    "Failed to get sample text, no activity found for " + intent + ")");
        }
    }

    public final void initSettings() {
        ContentResolver contentResolver = getContentResolver();
        this.mDefaultRate = Settings.Secure.getInt(contentResolver, "tts_default_rate", 100);
        this.mDefaultPitch = Settings.Secure.getInt(contentResolver, "tts_default_pitch", 100);
        this.mDefaultRatePref.setProgress(
                getSeekBarProgressFromValue(this.mDefaultRate, "tts_default_rate"));
        this.mDefaultRatePref.setOnPreferenceChangeListener(this);
        this.mDefaultRatePref.setMax(getSeekBarProgressFromValue(600, "tts_default_rate"));
        SeekBarPreference seekBarPreference = this.mDefaultRatePref;
        seekBarPreference.mContinuousUpdates = true;
        seekBarPreference.mHapticFeedbackMode = 2;
        this.mDefaultPitchPref.setProgress(
                getSeekBarProgressFromValue(this.mDefaultPitch, "tts_default_pitch"));
        this.mDefaultPitchPref.setOnPreferenceChangeListener(this);
        this.mDefaultPitchPref.setMax(getSeekBarProgressFromValue(400, "tts_default_pitch"));
        SeekBarPreference seekBarPreference2 = this.mDefaultPitchPref;
        seekBarPreference2.mContinuousUpdates = true;
        seekBarPreference2.mHapticFeedbackMode = 2;
        this.mDefaultRatePref.setSeekBarContentDescription(Integer.toString(this.mDefaultRate));
        this.mDefaultPitchPref.setSeekBarContentDescription(Integer.toString(this.mDefaultPitch));
        TextToSpeech textToSpeech = this.mTts;
        if (textToSpeech != null) {
            this.mCurrentEngine = textToSpeech.getCurrentEngine();
            this.mTts.setSpeechRate(this.mDefaultRate / 100.0f);
            this.mTts.setPitch(this.mDefaultPitch / 100.0f);
        }
        if (!(getActivity() instanceof SettingsActivity)) {
            throw new IllegalStateException("TextToSpeechSettings used outside a Settings");
        }
        String str = this.mCurrentEngine;
        if (str != null) {
            TextToSpeech.EngineInfo engineInfo = this.mEnginesHelper.getEngineInfo(str);
            Preference findPreference = findPreference("tts_engine_preference");
            SecGearPreference secGearPreference = (SecGearPreference) findPreference;
            secGearPreference.mOnGearClickListener = this;
            secGearPreference.notifyChanged();
            findPreference.setSummary(engineInfo.label);
            findPreference.seslSetSummaryColor(
                    getContext().getColor(R.color.sec_widget_multi_button_selected_color));
        }
        String str2 = this.mCurrentEngine;
        Intent intent = new Intent("android.speech.tts.engine.CHECK_TTS_DATA");
        intent.setPackage(str2);
        try {
            startActivityForResult(intent, 1977);
        } catch (ActivityNotFoundException unused) {
            Log.e(
                    "TextToSpeechSettings",
                    "Failed to check TTS data, no activity found for " + intent + ")");
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        String string;
        if (i == 1983) {
            TextToSpeech textToSpeech = this.mTts;
            if (textToSpeech != null && textToSpeech.getLanguage() != null) {
                try {
                    String iSO3Language = this.mTts.getLanguage().getISO3Language();
                    String[] stringArray =
                            getActivity().getResources().getStringArray(R.array.tts_demo_strings);
                    String[] stringArray2 =
                            getActivity()
                                    .getResources()
                                    .getStringArray(R.array.tts_demo_string_langs);
                    for (int i3 = 0; i3 < stringArray.length; i3++) {
                        if (stringArray2[i3].equals(iSO3Language)) {
                            string = stringArray[i3];
                            break;
                        }
                    }
                } catch (MissingResourceException unused) {
                }
            }
            string = getString(R.string.tts_default_sample_string);
            if (i2 == 0 && intent != null && intent.getStringExtra("sampleText") != null) {
                string = intent.getStringExtra("sampleText");
            }
            this.mSampleText = string;
            if (string != null) {
                updateWidgetState(true);
                return;
            } else {
                Log.e(
                        "TextToSpeechSettings",
                        "Did not have a sample string for the requested language. Using default");
                return;
            }
        }
        if (i == 1977) {
            String currentEngine = this.mTts.getCurrentEngine();
            if (currentEngine == null) {
                Log.e("TextToSpeechSettings", "Voice data check complete, but no engine bound");
            } else if (intent == null) {
                Log.e(
                        "TextToSpeechSettings",
                        "Engine failed voice data integrity check (null return)"
                                + this.mTts.getCurrentEngine());
            } else {
                Settings.Secure.putString(getContentResolver(), "tts_default_synth", currentEngine);
                ArrayList<String> stringArrayListExtra =
                        intent.getStringArrayListExtra("availableVoices");
                this.mAvailableStrLocals = stringArrayListExtra;
                if (stringArrayListExtra == null) {
                    Log.e(
                            "TextToSpeechSettings",
                            "Voice data check complete, but no available voices found");
                    this.mAvailableStrLocals = new ArrayList();
                }
                if (evaluateDefaultLocale()) {
                    getSampleText();
                }
            }
            if (i2 != 0) {
                ArrayList<String> stringArrayListExtra2 =
                        intent.getStringArrayListExtra("availableVoices");
                intent.getStringArrayListExtra("unavailableVoices");
                if (stringArrayListExtra2 == null || stringArrayListExtra2.size() == 0) {
                    this.mLocalePreference.setEnabled(false);
                    return;
                }
                Locale localePrefForEngine =
                        !this.mEnginesHelper.isLocaleSetToDefaultForEngine(
                                        this.mTts.getCurrentEngine())
                                ? this.mEnginesHelper.getLocalePrefForEngine(
                                        this.mTts.getCurrentEngine())
                                : null;
                ArrayList arrayList = new ArrayList(stringArrayListExtra2.size());
                for (int i4 = 0; i4 < stringArrayListExtra2.size(); i4++) {
                    Locale parseLocaleString =
                            this.mEnginesHelper.parseLocaleString(stringArrayListExtra2.get(i4));
                    if (parseLocaleString != null) {
                        arrayList.add(
                                new Pair(parseLocaleString.getDisplayName(), parseLocaleString));
                    }
                }
                final Collator collator =
                        Collator.getInstance(getResources().getConfiguration().getLocales().get(0));
                Collections.sort(
                        arrayList,
                        new Comparator() { // from class:
                                           // com.android.settings.tts.TextToSpeechSettings$$ExternalSyntheticLambda5
                            @Override // java.util.Comparator
                            public final int compare(Object obj, Object obj2) {
                                Collator collator2 = collator;
                                BaseSearchIndexProvider baseSearchIndexProvider =
                                        TextToSpeechSettings.SEARCH_INDEX_DATA_PROVIDER;
                                return collator2.compare(
                                        (String) ((Pair) obj).first, (String) ((Pair) obj2).first);
                            }
                        });
                this.mSelectedLocaleIndex = 0;
                CharSequence[] charSequenceArr = new CharSequence[stringArrayListExtra2.size() + 1];
                CharSequence[] charSequenceArr2 =
                        new CharSequence[stringArrayListExtra2.size() + 1];
                charSequenceArr[0] = getActivity().getString(R.string.tts_lang_use_system);
                charSequenceArr2[0] = ApnSettings.MVNO_NONE;
                Iterator it = arrayList.iterator();
                int i5 = 1;
                while (it.hasNext()) {
                    Pair pair = (Pair) it.next();
                    if (((Locale) pair.second).equals(localePrefForEngine)) {
                        this.mSelectedLocaleIndex = i5;
                    }
                    charSequenceArr[i5] = (CharSequence) pair.first;
                    charSequenceArr2[i5] = ((Locale) pair.second).toString();
                    i5++;
                }
                this.mLocalePreference.setEntries(charSequenceArr);
                SecListPreference secListPreference = this.mLocalePreference;
                secListPreference.mEntryValues = charSequenceArr2;
                secListPreference.setEnabled(true);
                setLocalePreference(this.mSelectedLocaleIndex);
            }
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.sec_tts_settings);
        getActivity().setVolumeControlStream(3);
        this.mEnginesHelper = new TtsEngines(getActivity().getApplicationContext());
        SecListPreference secListPreference =
                (SecListPreference) findPreference("tts_default_lang");
        this.mLocalePreference = secListPreference;
        secListPreference.setOnPreferenceChangeListener(this);
        this.mDefaultPitchPref = (SeekBarPreference) findPreference("tts_default_pitch");
        this.mDefaultRatePref = (SeekBarPreference) findPreference("tts_default_rate");
        SecActionButtonsPreference secActionButtonsPreference =
                (SecActionButtonsPreference) findPreference("action_buttons");
        String string = secActionButtonsPreference.getContext().getString(R.string.tts_play);
        if (!TextUtils.equals(string, secActionButtonsPreference.mButton1Info.mText)) {
            secActionButtonsPreference.mButton1Info.mText = string;
            secActionButtonsPreference.notifyChanged();
        }
        final int i = 0;
        View.OnClickListener onClickListener = new View.OnClickListener(this) { // from class:
                    // com.android.settings.tts.TextToSpeechSettings$$ExternalSyntheticLambda1
                    public final /* synthetic */ TextToSpeechSettings f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i2 = i;
                        TextToSpeechSettings textToSpeechSettings = this.f$0;
                        switch (i2) {
                            case 0:
                                Set<String> features =
                                        textToSpeechSettings.mTts.getFeatures(
                                                textToSpeechSettings.mCurrentDefaultLocale);
                                boolean z =
                                        (features == null
                                                        || !features.contains("networkTts")
                                                        || features.contains("embeddedTts"))
                                                ? false
                                                : true;
                                if (z
                                        && (!z
                                                || textToSpeechSettings.mTts.isLanguageAvailable(
                                                                textToSpeechSettings
                                                                        .mCurrentDefaultLocale)
                                                        < 0)) {
                                    Log.w(
                                            "TextToSpeechSettings",
                                            "Network required for sample synthesis for requested"
                                                + " language");
                                    AlertDialog.Builder builder =
                                            new AlertDialog.Builder(
                                                    textToSpeechSettings.getActivity());
                                    builder.setTitle(android.R.string.dialog_alert_title);
                                    String string2 =
                                            textToSpeechSettings
                                                    .getActivity()
                                                    .getString(
                                                            R.string.tts_engine_network_required);
                                    AlertController.AlertParams alertParams = builder.P;
                                    alertParams.mMessage = string2;
                                    alertParams.mCancelable = false;
                                    builder.setPositiveButton(
                                            android.R.string.ok,
                                            (DialogInterface.OnClickListener) null);
                                    builder.create().show();
                                    break;
                                } else {
                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("utteranceId", "Sample");
                                    textToSpeechSettings.mTts.speak(
                                            textToSpeechSettings.mSampleText, 0, hashMap);
                                    break;
                                }
                                break;
                            default:
                                BaseSearchIndexProvider baseSearchIndexProvider =
                                        TextToSpeechSettings.SEARCH_INDEX_DATA_PROVIDER;
                                textToSpeechSettings.getClass();
                                int seekBarProgressFromValue =
                                        TextToSpeechSettings.getSeekBarProgressFromValue(
                                                100, "tts_default_rate");
                                textToSpeechSettings.mDefaultRatePref.setProgress(
                                        seekBarProgressFromValue);
                                textToSpeechSettings.updateSpeechRate(seekBarProgressFromValue);
                                int seekBarProgressFromValue2 =
                                        TextToSpeechSettings.getSeekBarProgressFromValue(
                                                100, "tts_default_pitch");
                                textToSpeechSettings.mDefaultPitchPref.setProgress(
                                        seekBarProgressFromValue2);
                                textToSpeechSettings.updateSpeechPitchValue(
                                        seekBarProgressFromValue2);
                                break;
                        }
                    }
                };
        SecActionButtonsPreference.ButtonInfo buttonInfo = secActionButtonsPreference.mButton1Info;
        if (onClickListener != buttonInfo.mListener) {
            buttonInfo.mListener = onClickListener;
            secActionButtonsPreference.notifyChanged();
        }
        SecActionButtonsPreference.ButtonInfo buttonInfo2 = secActionButtonsPreference.mButton1Info;
        boolean z = false;
        if (buttonInfo2.mIsEnabled) {
            buttonInfo2.mIsEnabled = false;
            secActionButtonsPreference.notifyChanged();
        }
        String string2 = secActionButtonsPreference.getContext().getString(R.string.tts_reset);
        if (!TextUtils.equals(string2, secActionButtonsPreference.mButton2Info.mText)) {
            secActionButtonsPreference.mButton2Info.mText = string2;
            secActionButtonsPreference.notifyChanged();
        }
        final int i2 = 1;
        View.OnClickListener onClickListener2 = new View.OnClickListener(this) { // from class:
                    // com.android.settings.tts.TextToSpeechSettings$$ExternalSyntheticLambda1
                    public final /* synthetic */ TextToSpeechSettings f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i22 = i2;
                        TextToSpeechSettings textToSpeechSettings = this.f$0;
                        switch (i22) {
                            case 0:
                                Set<String> features =
                                        textToSpeechSettings.mTts.getFeatures(
                                                textToSpeechSettings.mCurrentDefaultLocale);
                                boolean z2 =
                                        (features == null
                                                        || !features.contains("networkTts")
                                                        || features.contains("embeddedTts"))
                                                ? false
                                                : true;
                                if (z2
                                        && (!z2
                                                || textToSpeechSettings.mTts.isLanguageAvailable(
                                                                textToSpeechSettings
                                                                        .mCurrentDefaultLocale)
                                                        < 0)) {
                                    Log.w(
                                            "TextToSpeechSettings",
                                            "Network required for sample synthesis for requested"
                                                + " language");
                                    AlertDialog.Builder builder =
                                            new AlertDialog.Builder(
                                                    textToSpeechSettings.getActivity());
                                    builder.setTitle(android.R.string.dialog_alert_title);
                                    String string22 =
                                            textToSpeechSettings
                                                    .getActivity()
                                                    .getString(
                                                            R.string.tts_engine_network_required);
                                    AlertController.AlertParams alertParams = builder.P;
                                    alertParams.mMessage = string22;
                                    alertParams.mCancelable = false;
                                    builder.setPositiveButton(
                                            android.R.string.ok,
                                            (DialogInterface.OnClickListener) null);
                                    builder.create().show();
                                    break;
                                } else {
                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("utteranceId", "Sample");
                                    textToSpeechSettings.mTts.speak(
                                            textToSpeechSettings.mSampleText, 0, hashMap);
                                    break;
                                }
                                break;
                            default:
                                BaseSearchIndexProvider baseSearchIndexProvider =
                                        TextToSpeechSettings.SEARCH_INDEX_DATA_PROVIDER;
                                textToSpeechSettings.getClass();
                                int seekBarProgressFromValue =
                                        TextToSpeechSettings.getSeekBarProgressFromValue(
                                                100, "tts_default_rate");
                                textToSpeechSettings.mDefaultRatePref.setProgress(
                                        seekBarProgressFromValue);
                                textToSpeechSettings.updateSpeechRate(seekBarProgressFromValue);
                                int seekBarProgressFromValue2 =
                                        TextToSpeechSettings.getSeekBarProgressFromValue(
                                                100, "tts_default_pitch");
                                textToSpeechSettings.mDefaultPitchPref.setProgress(
                                        seekBarProgressFromValue2);
                                textToSpeechSettings.updateSpeechPitchValue(
                                        seekBarProgressFromValue2);
                                break;
                        }
                    }
                };
        SecActionButtonsPreference.ButtonInfo buttonInfo3 = secActionButtonsPreference.mButton2Info;
        if (onClickListener2 != buttonInfo3.mListener) {
            buttonInfo3.mListener = onClickListener2;
            secActionButtonsPreference.notifyChanged();
        }
        SecActionButtonsPreference.ButtonInfo buttonInfo4 = secActionButtonsPreference.mButton1Info;
        if (true != buttonInfo4.mIsEnabled) {
            buttonInfo4.mIsEnabled = true;
            secActionButtonsPreference.notifyChanged();
        }
        this.mActionButtons = secActionButtonsPreference;
        this.mDefaultPitchPref.seslSetRoundedBg(12);
        this.mActionButtons.seslSetRoundedBg(0);
        this.mUserManager =
                (UserManager) getActivity().getApplicationContext().getSystemService("user");
        if (bundle == null) {
            this.mLocalePreference.setEnabled(false);
            this.mLocalePreference.setEntries(new CharSequence[0]);
            this.mLocalePreference.mEntryValues = new CharSequence[0];
        } else {
            CharSequence[] charSequenceArray = bundle.getCharSequenceArray("locale_entries");
            CharSequence[] charSequenceArray2 = bundle.getCharSequenceArray("locale_entry_values");
            CharSequence charSequence = bundle.getCharSequence("locale_value");
            this.mLocalePreference.setEntries(charSequenceArray);
            SecListPreference secListPreference2 = this.mLocalePreference;
            secListPreference2.mEntryValues = charSequenceArray2;
            secListPreference2.setValue(charSequence != null ? charSequence.toString() : null);
            this.mLocalePreference.setEnabled(charSequenceArray.length > 0);
        }
        ViewModelStore store = getViewModelStore();
        ViewModelProvider.Factory factory = getDefaultViewModelProviderFactory();
        CreationExtras defaultViewModelCreationExtras = getDefaultViewModelCreationExtras();
        Intrinsics.checkNotNullParameter(store, "store");
        Intrinsics.checkNotNullParameter(factory, "factory");
        ViewModelProviderImpl m =
                ProgressDialogFragment$$ExternalSyntheticOutline0.m(
                        defaultViewModelCreationExtras,
                        "defaultCreationExtras",
                        store,
                        factory,
                        defaultViewModelCreationExtras);
        KClass kotlinClass = JvmClassMappingKt.getKotlinClass(TextToSpeechViewModel.class);
        String qualifiedName = kotlinClass.getQualifiedName();
        if (qualifiedName == null) {
            throw new IllegalArgumentException(
                    "Local and anonymous classes can not be ViewModels".toString());
        }
        TextToSpeechViewModel textToSpeechViewModel =
                (TextToSpeechViewModel)
                        m.getViewModel$lifecycle_viewmodel_release(
                                kotlinClass,
                                "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                        .concat(qualifiedName));
        TextToSpeechSettings$$ExternalSyntheticLambda0
                textToSpeechSettings$$ExternalSyntheticLambda0 = this.mInitListener;
        if (textToSpeechViewModel.mTts == null) {
            textToSpeechViewModel.mTts =
                    new TextToSpeech(
                            textToSpeechViewModel.mApplication,
                            textToSpeechSettings$$ExternalSyntheticLambda0);
            z = true;
        }
        Pair create = Pair.create(textToSpeechViewModel.mTts, Boolean.valueOf(z));
        this.mTts = (TextToSpeech) create.first;
        if (!((Boolean) create.second).booleanValue()) {
            successSetup();
        }
        TextToSpeech textToSpeech = this.mTts;
        if (textToSpeech != null) {
            textToSpeech.setOnUtteranceProgressListener(new AnonymousClass3());
        }
        initSettings();
    }

    @Override // com.samsung.android.settings.widget.SecGearPreference.OnGearClickListener
    public final void onGearClick(SecGearPreference secGearPreference) {
        if ("tts_engine_preference".equals(secGearPreference.getKey())) {
            Intent settingsIntent =
                    this.mEnginesHelper.getSettingsIntent(
                            this.mEnginesHelper.getEngineInfo(this.mCurrentEngine).name);
            if (settingsIntent != null) {
                startActivity(settingsIntent);
            } else {
                Log.e("TextToSpeechSettings", "settingsIntent is null");
            }
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            featureFactoryImpl
                    .getMetricsFeatureProvider()
                    .logClickedPreference(secGearPreference, 94);
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(this.mSpeechRateObserver);
        getContentResolver().unregisterContentObserver(this.mPitchObserver);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if ("tts_default_rate".equals(preference.getKey())) {
            updateSpeechRate(((Integer) obj).intValue());
        } else if ("tts_default_pitch".equals(preference.getKey())) {
            updateSpeechPitchValue(((Integer) obj).intValue());
        } else if (preference == this.mLocalePreference) {
            String str = (String) obj;
            Locale parseLocaleString =
                    !TextUtils.isEmpty(str) ? this.mEnginesHelper.parseLocaleString(str) : null;
            String locale =
                    parseLocaleString != null
                            ? parseLocaleString.toString()
                            : ApnSettings.MVNO_NONE;
            int i = 0;
            while (true) {
                CharSequence[] charSequenceArr = this.mLocalePreference.mEntryValues;
                if (i >= charSequenceArr.length) {
                    i = -1;
                    break;
                }
                if (locale.equalsIgnoreCase(charSequenceArr[i].toString())) {
                    break;
                }
                i++;
            }
            if (i == -1) {
                Log.w(
                        "TextToSpeechSettings",
                        "updateLanguageTo called with unknown locale argument");
            } else {
                SecListPreference secListPreference = this.mLocalePreference;
                secListPreference.setSummary(secListPreference.mEntries[i]);
                this.mSelectedLocaleIndex = i;
                this.mEnginesHelper.updateLocalePrefForEngine(
                        this.mTts.getCurrentEngine(), parseLocaleString);
                TextToSpeech textToSpeech = this.mTts;
                if (parseLocaleString == null) {
                    parseLocaleString = Locale.getDefault();
                }
                textToSpeech.setLanguage(parseLocaleString);
            }
            checkDefaultLocale();
        }
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        boolean z;
        super.onResume();
        if (getListView().getItemAnimator() != null) {
            getListView().mDrawLastRoundedCorner = false;
        }
        TextToSpeech textToSpeech = this.mTts;
        if (textToSpeech == null || this.mCurrentDefaultLocale == null) {
            return;
        }
        if (textToSpeech.getDefaultEngine().equals(this.mTts.getCurrentEngine())) {
            this.mTts.setPitch(
                    Settings.Secure.getInt(getContentResolver(), "tts_default_pitch", 100)
                            / 100.0f);
        } else {
            ViewModelStore store = getViewModelStore();
            ViewModelProvider.Factory factory = getDefaultViewModelProviderFactory();
            CreationExtras defaultViewModelCreationExtras = getDefaultViewModelCreationExtras();
            Intrinsics.checkNotNullParameter(store, "store");
            Intrinsics.checkNotNullParameter(factory, "factory");
            ViewModelProviderImpl m =
                    ProgressDialogFragment$$ExternalSyntheticOutline0.m(
                            defaultViewModelCreationExtras,
                            "defaultCreationExtras",
                            store,
                            factory,
                            defaultViewModelCreationExtras);
            KClass kotlinClass = JvmClassMappingKt.getKotlinClass(TextToSpeechViewModel.class);
            String qualifiedName = kotlinClass.getQualifiedName();
            if (qualifiedName == null) {
                throw new IllegalArgumentException(
                        "Local and anonymous classes can not be ViewModels".toString());
            }
            TextToSpeechViewModel textToSpeechViewModel =
                    (TextToSpeechViewModel)
                            m.getViewModel$lifecycle_viewmodel_release(
                                    kotlinClass,
                                    "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                            .concat(qualifiedName));
            try {
                textToSpeechViewModel.mTts.shutdown();
                textToSpeechViewModel.mTts = null;
            } catch (Exception e) {
                SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m(
                        "Error shutting down TTS engine", e, "TextToSpeechSettings");
            }
            TextToSpeechSettings$$ExternalSyntheticLambda0
                    textToSpeechSettings$$ExternalSyntheticLambda0 = this.mInitListener;
            if (textToSpeechViewModel.mTts == null) {
                textToSpeechViewModel.mTts =
                        new TextToSpeech(
                                textToSpeechViewModel.mApplication,
                                textToSpeechSettings$$ExternalSyntheticLambda0);
                z = true;
            } else {
                z = false;
            }
            Pair create = Pair.create(textToSpeechViewModel.mTts, Boolean.valueOf(z));
            this.mTts = (TextToSpeech) create.first;
            if (!((Boolean) create.second).booleanValue()) {
                successSetup();
            }
            TextToSpeech textToSpeech2 = this.mTts;
            if (textToSpeech2 != null) {
                textToSpeech2.setOnUtteranceProgressListener(new AnonymousClass3());
            }
            initSettings();
        }
        Locale defaultLanguage = this.mTts.getDefaultLanguage();
        if (defaultLanguage != null) {
            defaultLanguage = this.mEnginesHelper.parseLocaleString(defaultLanguage.toString());
        }
        Locale locale = this.mCurrentDefaultLocale;
        if (locale != null && !locale.equals(defaultLanguage)) {
            updateWidgetState(false);
            checkDefaultLocale();
            if (defaultLanguage != null) {
                String locale2 = defaultLanguage.toString();
                int i = 0;
                while (true) {
                    CharSequence[] charSequenceArr = this.mLocalePreference.mEntryValues;
                    if (i >= charSequenceArr.length) {
                        i = -1;
                        break;
                    } else if (locale2.equalsIgnoreCase(charSequenceArr[i].toString())) {
                        break;
                    } else {
                        i++;
                    }
                }
                this.mSelectedLocaleIndex = i;
                setLocalePreference(i);
            }
        }
        this.mDefaultRate = Settings.Secure.getInt(getContentResolver(), "tts_default_rate", 100);
        this.mDefaultPitch = Settings.Secure.getInt(getContentResolver(), "tts_default_pitch", 100);
        this.mDefaultRatePref.setProgress(
                getSeekBarProgressFromValue(this.mDefaultRate, "tts_default_rate"));
        this.mDefaultPitchPref.setProgress(
                getSeekBarProgressFromValue(this.mDefaultPitch, "tts_default_pitch"));
        if (Settings.Secure.getUriFor("tts_default_rate") != null) {
            getContentResolver()
                    .registerContentObserver(
                            Settings.Secure.getUriFor("tts_default_rate"),
                            false,
                            this.mSpeechRateObserver);
        }
        if (Settings.Secure.getUriFor("tts_default_pitch") != null) {
            getContentResolver()
                    .registerContentObserver(
                            Settings.Secure.getUriFor("tts_default_pitch"),
                            false,
                            this.mPitchObserver);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putCharSequenceArray("locale_entries", this.mLocalePreference.mEntries);
        bundle.putCharSequenceArray("locale_entry_values", this.mLocalePreference.mEntryValues);
        bundle.putCharSequence("locale_value", this.mLocalePreference.mValue);
    }

    public final void setLocalePreference(int i) {
        if (i < 0) {
            this.mLocalePreference.setValue(ApnSettings.MVNO_NONE);
            this.mLocalePreference.setSummary(R.string.tts_lang_not_selected);
        } else {
            this.mLocalePreference.setValueIndex(i);
            SecListPreference secListPreference = this.mLocalePreference;
            secListPreference.setSummary(secListPreference.mEntries[i]);
        }
    }

    public final void successSetup() {
        checkDefaultLocale();
        if (getActivity() == null) {
            return;
        }
        getActivity()
                .runOnUiThread(
                        new Runnable() { // from class:
                                         // com.android.settings.tts.TextToSpeechSettings$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                TextToSpeechSettings.this.mLocalePreference.setEnabled(true);
                            }
                        });
    }

    public final void updateSpeechPitchValue(int i) {
        int i2 = i + 25;
        this.mDefaultPitch = i2;
        try {
            Settings.Secure.putInt(getContentResolver(), "tts_default_pitch", i2);
            int managedProfileId =
                    Utils.getManagedProfileId(this.mUserManager, UserHandle.myUserId());
            if (managedProfileId != -10000) {
                Settings.Secure.putIntForUser(
                        getContentResolver(), "tts_default_pitch", i2, managedProfileId);
            }
            this.mDefaultPitchPref.setSeekBarContentDescription(
                    Integer.toString(this.mDefaultPitch));
            TextToSpeech textToSpeech = this.mTts;
            if (textToSpeech != null) {
                textToSpeech.setPitch(this.mDefaultPitch / 100.0f);
            }
        } catch (NumberFormatException e) {
            Log.e("TextToSpeechSettings", "could not persist default TTS pitch setting", e);
        }
    }

    public final void updateSpeechRate(int i) {
        this.mDefaultRate = i + 10;
        LoggingHelper.insertEventLogging(94, 4704);
        try {
            int i2 = this.mDefaultRate;
            Settings.Secure.putInt(getContentResolver(), "tts_default_rate", i2);
            int managedProfileId =
                    Utils.getManagedProfileId(this.mUserManager, UserHandle.myUserId());
            if (managedProfileId != -10000) {
                Settings.Secure.putIntForUser(
                        getContentResolver(), "tts_default_rate", i2, managedProfileId);
            }
            this.mDefaultRatePref.setSeekBarContentDescription(Integer.toString(this.mDefaultRate));
            TextToSpeech textToSpeech = this.mTts;
            if (textToSpeech != null) {
                textToSpeech.setSpeechRate(this.mDefaultRate / 100.0f);
            }
        } catch (NumberFormatException e) {
            Log.e("TextToSpeechSettings", "could not persist default TTS rate setting", e);
        }
    }

    public final void updateWidgetState(final boolean z) {
        if (getActivity() == null) {
            return;
        }
        getActivity()
                .runOnUiThread(
                        new Runnable() { // from class:
                                         // com.android.settings.tts.TextToSpeechSettings$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                TextToSpeechSettings textToSpeechSettings =
                                        TextToSpeechSettings.this;
                                boolean z2 = z;
                                SecActionButtonsPreference secActionButtonsPreference =
                                        textToSpeechSettings.mActionButtons;
                                SecActionButtonsPreference.ButtonInfo buttonInfo =
                                        secActionButtonsPreference.mButton1Info;
                                if (z2 != buttonInfo.mIsEnabled) {
                                    buttonInfo.mIsEnabled = z2;
                                    secActionButtonsPreference.notifyChanged();
                                }
                                textToSpeechSettings.mDefaultRatePref.setEnabled(z2);
                                textToSpeechSettings.mDefaultPitchPref.setEnabled(z2);
                            }
                        });
    }
}

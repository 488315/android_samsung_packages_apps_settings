package com.samsung.android.settings.voiceinput;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SecPreferenceCategory;

import com.android.settings.R;
import com.android.settings.applications.SpacePreference;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.voiceinput.offline.LangPackPreference;
import com.samsung.android.settings.voiceinput.offline.Language;
import com.samsung.android.settings.voiceinput.offline.OfflineLanguageUtil;
import com.samsung.android.settings.voiceinput.offline.OnViewAttachedListener;
import com.samsung.android.util.SemLog;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.android.schedulers.HandlerScheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.observable.ObservableCreate;
import io.reactivex.internal.operators.observable.ObservableObserveOn;
import io.reactivex.internal.operators.observable.ObservableSubscribeOn;
import io.reactivex.schedulers.Schedulers;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class VoiceInputSettingsFragment extends PreferenceFragmentCompat
        implements OnViewAttachedListener {
    public static final String KEY_OFFLINE_PREFERENCE = "offline_language_pack";
    public static final String KEY_SPACE_PREFERENCE = "padding_bottom_space_layout";
    private static final String SETTINGS_DB_DATA_CONFIG_ENABLED = "scs_search_data_config_enabled";
    private static final String TAG = "VoiceInputSettingsFragment";
    private Observable mLangPackResultObservable;
    private int mViewInflatedCount = 0;
    private int mLangPackCount = 0;
    private boolean isFragmentShowing = false;
    private List<OfflineLanguageUtil.onLangPackResultListener> mListenerArrayList =
            new CopyOnWriteArrayList();

    public static void $r8$lambda$n1AAjjg1GFGQnaWs6ZsT7tXoKXw(
            VoiceInputSettingsFragment voiceInputSettingsFragment) {
        voiceInputSettingsFragment.getClass();
        SemLog.d(TAG, "Go to advanced settings");
        voiceInputSettingsFragment.isFragmentShowing = false;
        Intent intent = new Intent("com.samsung.settings.USEFUL_FEATURE_MAIN_SETTINGS");
        Bundle bundle = new Bundle();
        bundle.putString(":settings:fragment_args_key", "samsung_core_services");
        intent.putExtra(":settings:show_fragment_args", bundle);
        voiceInputSettingsFragment.startActivity(intent);
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        SemLog.i(TAG, "onCreatePreferences");
        preloadFragment();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        preloadFragment();
    }

    @Override // com.samsung.android.settings.voiceinput.offline.OnViewAttachedListener
    public final void onViewAttached() {
        this.mViewInflatedCount++;
        SemLog.d(TAG, "viewInflatedCount  :" + this.mViewInflatedCount);
        if (this.mViewInflatedCount == this.mLangPackCount) {
            SemLog.d(TAG, "LanguagePack Preferences Attached!!");
            if (VoiceInputUtils.isLangPackRequestNeeded(getContext())) {
                ObservableCreate observableCreate =
                        new ObservableCreate(
                                new VoiceInputSettingsFragment$$ExternalSyntheticLambda3(this));
                this.mLangPackResultObservable = observableCreate;
                HandlerScheduler handlerScheduler = AndroidSchedulers.MAIN_THREAD;
                if (handlerScheduler == null) {
                    throw new NullPointerException("scheduler == null");
                }
                ObservableObserveOn observeOn = observableCreate.observeOn(handlerScheduler);
                Scheduler scheduler = Schedulers.IO;
                ObjectHelper.requireNonNull(scheduler, "scheduler is null");
                new ObservableSubscribeOn(observeOn, scheduler)
                        .subscribe(
                                new Observer() { // from class:
                                                 // com.samsung.android.settings.voiceinput.VoiceInputSettingsFragment.1
                                    @Override // io.reactivex.Observer
                                    public final void onComplete() {
                                        SemLog.d(
                                                VoiceInputSettingsFragment.TAG,
                                                "langPackResultObservable onComplete ");
                                        VoiceInputSettingsFragment.this.mListenerArrayList.forEach(
                                                new VoiceInputSettingsFragment$1$$ExternalSyntheticLambda0(
                                                        0));
                                        VoiceInputSettingsFragment.this.mListenerArrayList.clear();
                                    }

                                    @Override // io.reactivex.Observer
                                    public final void onError(Throwable th) {
                                        SemLog.d(
                                                VoiceInputSettingsFragment.TAG,
                                                "langPackResultObservable onError " + th);
                                        th.printStackTrace();
                                        VoiceInputSettingsFragment.this.mListenerArrayList.forEach(
                                                new VoiceInputSettingsFragment$1$$ExternalSyntheticLambda0(
                                                        1));
                                    }

                                    @Override // io.reactivex.Observer
                                    public final void onNext(Object obj) {
                                        SemLog.d(
                                                VoiceInputSettingsFragment.TAG,
                                                "langPackResultObservable onNext "
                                                        + Thread.currentThread().getName());
                                    }

                                    @Override // io.reactivex.Observer
                                    public final void onSubscribe(Disposable disposable) {
                                        SemLog.d(
                                                VoiceInputSettingsFragment.TAG,
                                                "langPackResultObservable onSubscribe");
                                        VoiceInputSettingsFragment.this.mListenerArrayList.forEach(
                                                new VoiceInputSettingsFragment$1$$ExternalSyntheticLambda0(
                                                        2));
                                    }
                                });
            }
        }
    }

    public final void preloadFragment() {
        if (this.isFragmentShowing) {
            return;
        }
        this.isFragmentShowing = true;
        if (RecognizerUtils.isSCSFeatureEnabled(getContext())
                == RecognizerUtils.SCS.NOT_AVAILABLE) {
            SemLog.d(TAG, "SCS is unavailable");
            getPreferenceScreen().removePreference(findPreference(KEY_OFFLINE_PREFERENCE));
            return;
        }
        if (Rune.isChinaModel()
                && Settings.Global.getInt(
                                getContext().getContentResolver(),
                                SETTINGS_DB_DATA_CONFIG_ENABLED,
                                -1)
                        != -1) {
            SemLog.d(TAG, "SCS require popup");
            View inflate =
                    getLayoutInflater()
                            .inflate(R.layout.voice_input_popup_layout, (ViewGroup) null);
            ((TextView) inflate.findViewById(R.id.popup_text))
                    .setText(getResources().getString(R.string.request_turn_on_scs_dialog_content));
            new AlertDialog.Builder(getContext(), R.style.VoiceInputDialogAlertTheme)
                    .setView(inflate)
                    .setPositiveButton(
                            getResources()
                                    .getString(R.string.request_turn_on_scs_dialog_settings_button),
                            new DialogInterface
                                    .OnClickListener() { // from class:
                                                         // com.samsung.android.settings.voiceinput.VoiceInputSettingsFragment$$ExternalSyntheticLambda0
                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(DialogInterface dialogInterface, int i) {
                                    VoiceInputSettingsFragment
                                            .$r8$lambda$n1AAjjg1GFGQnaWs6ZsT7tXoKXw(
                                                    VoiceInputSettingsFragment.this);
                                }
                            })
                    .setNegativeButton(
                            getResources()
                                    .getString(R.string.request_turn_on_scs_dialog_cancel_button),
                            new VoiceInputSettingsFragment$$ExternalSyntheticLambda1())
                    .setOnCancelListener(
                            new DialogInterface
                                    .OnCancelListener() { // from class:
                                                          // com.samsung.android.settings.voiceinput.VoiceInputSettingsFragment$$ExternalSyntheticLambda2
                                @Override // android.content.DialogInterface.OnCancelListener
                                public final void onCancel(DialogInterface dialogInterface) {
                                    VoiceInputSettingsFragment voiceInputSettingsFragment =
                                            VoiceInputSettingsFragment.this;
                                    voiceInputSettingsFragment.getClass();
                                    SemLog.d(
                                            "VoiceInputSettingsFragment",
                                            "Cancel require scs dialog");
                                    try {
                                        voiceInputSettingsFragment.getActivity().onBackPressed();
                                    } catch (NullPointerException e) {
                                        SemLog.e("VoiceInputSettingsFragment", e.getMessage());
                                    }
                                }
                            })
                    .show();
            return;
        }
        SemLog.d(TAG, "SCS is available");
        addPreferencesFromResource(R.xml.voice_input_pref_setting);
        SecPreferenceCategory secPreferenceCategory =
                (SecPreferenceCategory) findPreference(KEY_OFFLINE_PREFERENCE);
        if (secPreferenceCategory == null) {
            return;
        }
        OnDeviceLanguageList.setLanguageList(getContext());
        List languageList = OnDeviceLanguageList.getLanguageList();
        boolean z = languageList.size() == 0;
        SpacePreference spacePreference = (SpacePreference) findPreference(KEY_SPACE_PREFERENCE);
        if (spacePreference != null) {
            spacePreference.setVisible(z);
        }
        for (int i = 0; i < languageList.size(); i++) {
            Language language = (Language) languageList.get(i);
            Context context = getContext();
            SemLog.i("@VoiceIn: OfflinePackageChecker", "updateCacheForCurrentLocale locale : ");
            String localeCode = RecognizerUtils.getLocaleCode(language.getLocale());
            String string =
                    PreferenceManager.getDefaultSharedPreferences(context)
                            .getString(localeCode + "_version", "000000000");
            SemLog.i(
                    "@VoiceIn: OfflineLanguageUtil",
                    "getLanguagePackageVersion locale :" + localeCode + "  version " + string);
            String valueOf =
                    PackageUtils.isPackageInstalled(context, language.getLangPackage())
                            ? String.valueOf(
                                    PackageUtils.getVersionCode(context, language.getLangPackage()))
                            : "000000000";
            SemLog.i(
                    "@VoiceIn: OfflinePackageChecker",
                    "cachedVersion : " + string + " installedVersion : " + valueOf);
            SemLog.i(
                    "@VoiceIn: OfflinePackageChecker",
                    "checkAndUpdateVersion cachedVersion : "
                            + string
                            + " installedVersion : "
                            + valueOf);
            int version = VoiceInputUtils.getVersion(string);
            int version2 = VoiceInputUtils.getVersion(valueOf);
            String localeCode2 = RecognizerUtils.getLocaleCode(language.getLocale());
            if (version == version2 && version == 0) {
                OfflineLanguageUtil.setLanguagePackageStatus(context, 0, localeCode2);
            } else if (version <= version2) {
                OfflineLanguageUtil.setLanguagePackageStatus(context, 3, localeCode2);
                OfflineLanguageUtil.setLanguagePackageVersion(context, localeCode2, valueOf);
            } else if (PackageUtils.isPackageInstalled(context, language.getLanguageName())) {
                OfflineLanguageUtil.setLanguagePackageStatus(context, 2, localeCode2);
            } else {
                OfflineLanguageUtil.setLanguagePackageStatus(context, 1, localeCode2);
            }
            LangPackPreference langPackPreference = new LangPackPreference(getContext(), language);
            langPackPreference.addViewInflateListener(this);
            secPreferenceCategory.addPreference(langPackPreference);
            this.mLangPackCount++;
            this.mListenerArrayList.add(langPackPreference);
        }
    }
}

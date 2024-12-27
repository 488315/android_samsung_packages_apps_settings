package com.samsung.android.settings.languagepack;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.view.View;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.languagepack.data.LanguageInfo;
import com.samsung.android.settings.languagepack.data.PackageInfo;
import com.samsung.android.settings.languagepack.logger.Log;
import com.samsung.android.settings.languagepack.manager.LanguagePackManager;
import com.samsung.android.settings.languagepack.widget.LanguagePackDetailsPreference;
import com.samsung.android.sivs.ai.sdkcommon.asr.SpeechRecognitionConst;
import com.samsung.android.util.SemLog;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LanguagePackDetailsFragment extends SettingsPreferenceFragment {
    public LanguageInfo mLanguageInfo;

    public final void addPreference(String str, PackageInfo packageInfo) {
        String str2 = packageInfo.mPkgName;
        boolean z = packageInfo.mIsPreloaded;
        long j = packageInfo.mPreloadedVersionCode;
        String str3 = packageInfo.mPreloadedVersionName;
        try {
            android.content.pm.PackageInfo packageInfoAsUser =
                    getContext()
                            .getPackageManager()
                            .getPackageInfoAsUser(str2, 0, UserHandle.myUserId());
            if (packageInfoAsUser != null) {
                if (!z) {
                    str3 = packageInfoAsUser.versionName;
                } else if (j < packageInfoAsUser.getLongVersionCode()) {
                    str3 = packageInfoAsUser.versionName;
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
            SemLog.e("LanguagePackDetailsFragment", "invalid package name : " + str2);
        }
        if (TextUtils.isEmpty(str3)) {
            return;
        }
        LanguagePackDetailsPreference languagePackDetailsPreference =
                new LanguagePackDetailsPreference(getContext(), null);
        languagePackDetailsPreference.setTitle(str);
        languagePackDetailsPreference.setSummary(
                getContext().getString(R.string.sec_offline_lang_pack_detail_version_prefix, str3));
        getPreferenceScreen().addPreference(languagePackDetailsPreference);
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    public final String getTitle(int i) {
        return i != 1
                ? i != 2
                        ? i != 8
                                ? i != 16
                                        ? ApnSettings.MVNO_NONE
                                        : getContext()
                                                .getString(
                                                        R.string
                                                                .sec_offline_lang_pack_voice_translation_title)
                                : getContext()
                                        .getString(R.string.sec_offline_lang_pack_translation_title)
                        : getContext().getString(R.string.sec_offline_lang_pack_tts_title)
                : getContext().getString(R.string.sec_offline_lang_pack_asr_title);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        if (arguments != null) {
            String string = arguments.getString(SpeechRecognitionConst.Key.LOCALE);
            if (TextUtils.isEmpty(string)) {
                SemLog.e("LanguagePackDetailsFragment", "invalid locale info : " + string);
                finish();
                return;
            }
            ArrayList arrayList = new ArrayList();
            try {
                arrayList.addAll(LanguagePackManager.getInstance(getContext()).makeLanguageList());
            } catch (JSONException e) {
                Log.e(
                        "LanguagePackDetailsFragment",
                        "failed to make language info list : " + e.getMessage());
            }
            Optional findFirst =
                    arrayList.stream()
                            .filter(
                                    new LanguagePackDetailsFragment$$ExternalSyntheticLambda1(
                                            4, string))
                            .findFirst();
            if (findFirst == null || !findFirst.isPresent()) {
                SemLog.e("LanguagePackDetailsFragment", "unsupported locale : " + string);
                finish();
                return;
            }
            this.mLanguageInfo = (LanguageInfo) findFirst.get();
        }
        addPreferencesFromResource(R.xml.sec_language_pack_details);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        final int i = 0;
        Optional findFirst =
                this.mLanguageInfo.mList.stream()
                        .filter(
                                new Predicate() { // from class:
                                                  // com.samsung.android.settings.languagepack.LanguagePackDetailsFragment$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        PackageInfo packageInfo = (PackageInfo) obj;
                                        switch (i) {
                                            case 0:
                                                return packageInfo.mType == 1;
                                            case 1:
                                                return packageInfo.mType == 16;
                                            case 2:
                                                return packageInfo.mType == 2;
                                            default:
                                                return packageInfo.mType == 8;
                                        }
                                    }
                                })
                        .filter(new LanguagePackDetailsFragment$$ExternalSyntheticLambda1(1, this))
                        .findFirst();
        final int i2 = 2;
        findFirst.ifPresent(
                new Consumer(
                        this) { // from class:
                                // com.samsung.android.settings.languagepack.LanguagePackDetailsFragment$$ExternalSyntheticLambda2
                    public final /* synthetic */ LanguagePackDetailsFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i3 = i2;
                        LanguagePackDetailsFragment languagePackDetailsFragment = this.f$0;
                        PackageInfo packageInfo = (PackageInfo) obj;
                        switch (i3) {
                            case 0:
                                languagePackDetailsFragment.addPreference(
                                        languagePackDetailsFragment.getTitle(16), packageInfo);
                                break;
                            case 1:
                                languagePackDetailsFragment.addPreference(
                                        languagePackDetailsFragment.getTitle(8), packageInfo);
                                break;
                            case 2:
                                languagePackDetailsFragment.addPreference(
                                        languagePackDetailsFragment.getTitle(1), packageInfo);
                                break;
                            default:
                                languagePackDetailsFragment.addPreference(
                                        languagePackDetailsFragment.getTitle(2), packageInfo);
                                break;
                        }
                    }
                });
        final int i3 = 2;
        Optional findFirst2 =
                this.mLanguageInfo.mList.stream()
                        .filter(
                                new Predicate() { // from class:
                                                  // com.samsung.android.settings.languagepack.LanguagePackDetailsFragment$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        PackageInfo packageInfo = (PackageInfo) obj;
                                        switch (i3) {
                                            case 0:
                                                return packageInfo.mType == 1;
                                            case 1:
                                                return packageInfo.mType == 16;
                                            case 2:
                                                return packageInfo.mType == 2;
                                            default:
                                                return packageInfo.mType == 8;
                                        }
                                    }
                                })
                        .filter(new LanguagePackDetailsFragment$$ExternalSyntheticLambda1(2, this))
                        .findFirst();
        final int i4 = 3;
        findFirst2.ifPresent(
                new Consumer(
                        this) { // from class:
                                // com.samsung.android.settings.languagepack.LanguagePackDetailsFragment$$ExternalSyntheticLambda2
                    public final /* synthetic */ LanguagePackDetailsFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i32 = i4;
                        LanguagePackDetailsFragment languagePackDetailsFragment = this.f$0;
                        PackageInfo packageInfo = (PackageInfo) obj;
                        switch (i32) {
                            case 0:
                                languagePackDetailsFragment.addPreference(
                                        languagePackDetailsFragment.getTitle(16), packageInfo);
                                break;
                            case 1:
                                languagePackDetailsFragment.addPreference(
                                        languagePackDetailsFragment.getTitle(8), packageInfo);
                                break;
                            case 2:
                                languagePackDetailsFragment.addPreference(
                                        languagePackDetailsFragment.getTitle(1), packageInfo);
                                break;
                            default:
                                languagePackDetailsFragment.addPreference(
                                        languagePackDetailsFragment.getTitle(2), packageInfo);
                                break;
                        }
                    }
                });
        final int i5 = 3;
        Optional findFirst3 =
                this.mLanguageInfo.mList.stream()
                        .filter(
                                new Predicate() { // from class:
                                                  // com.samsung.android.settings.languagepack.LanguagePackDetailsFragment$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        PackageInfo packageInfo = (PackageInfo) obj;
                                        switch (i5) {
                                            case 0:
                                                return packageInfo.mType == 1;
                                            case 1:
                                                return packageInfo.mType == 16;
                                            case 2:
                                                return packageInfo.mType == 2;
                                            default:
                                                return packageInfo.mType == 8;
                                        }
                                    }
                                })
                        .filter(new LanguagePackDetailsFragment$$ExternalSyntheticLambda1(3, this))
                        .findFirst();
        final int i6 = 1;
        findFirst3.ifPresent(
                new Consumer(
                        this) { // from class:
                                // com.samsung.android.settings.languagepack.LanguagePackDetailsFragment$$ExternalSyntheticLambda2
                    public final /* synthetic */ LanguagePackDetailsFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i32 = i6;
                        LanguagePackDetailsFragment languagePackDetailsFragment = this.f$0;
                        PackageInfo packageInfo = (PackageInfo) obj;
                        switch (i32) {
                            case 0:
                                languagePackDetailsFragment.addPreference(
                                        languagePackDetailsFragment.getTitle(16), packageInfo);
                                break;
                            case 1:
                                languagePackDetailsFragment.addPreference(
                                        languagePackDetailsFragment.getTitle(8), packageInfo);
                                break;
                            case 2:
                                languagePackDetailsFragment.addPreference(
                                        languagePackDetailsFragment.getTitle(1), packageInfo);
                                break;
                            default:
                                languagePackDetailsFragment.addPreference(
                                        languagePackDetailsFragment.getTitle(2), packageInfo);
                                break;
                        }
                    }
                });
        final int i7 = 1;
        Stream filter =
                this.mLanguageInfo.mList.stream()
                        .filter(
                                new Predicate() { // from class:
                                                  // com.samsung.android.settings.languagepack.LanguagePackDetailsFragment$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        PackageInfo packageInfo = (PackageInfo) obj;
                                        switch (i7) {
                                            case 0:
                                                return packageInfo.mType == 1;
                                            case 1:
                                                return packageInfo.mType == 16;
                                            case 2:
                                                return packageInfo.mType == 2;
                                            default:
                                                return packageInfo.mType == 8;
                                        }
                                    }
                                });
        final int i8 = 0;
        filter.filter(new LanguagePackDetailsFragment$$ExternalSyntheticLambda1(0, this))
                .findFirst()
                .ifPresent(
                        new Consumer(
                                this) { // from class:
                                        // com.samsung.android.settings.languagepack.LanguagePackDetailsFragment$$ExternalSyntheticLambda2
                            public final /* synthetic */ LanguagePackDetailsFragment f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i32 = i8;
                                LanguagePackDetailsFragment languagePackDetailsFragment = this.f$0;
                                PackageInfo packageInfo = (PackageInfo) obj;
                                switch (i32) {
                                    case 0:
                                        languagePackDetailsFragment.addPreference(
                                                languagePackDetailsFragment.getTitle(16),
                                                packageInfo);
                                        break;
                                    case 1:
                                        languagePackDetailsFragment.addPreference(
                                                languagePackDetailsFragment.getTitle(8),
                                                packageInfo);
                                        break;
                                    case 2:
                                        languagePackDetailsFragment.addPreference(
                                                languagePackDetailsFragment.getTitle(1),
                                                packageInfo);
                                        break;
                                    default:
                                        languagePackDetailsFragment.addPreference(
                                                languagePackDetailsFragment.getTitle(2),
                                                packageInfo);
                                        break;
                                }
                            }
                        });
    }
}

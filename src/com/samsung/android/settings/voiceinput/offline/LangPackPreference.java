package com.samsung.android.settings.voiceinput.offline;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecPreference;
import com.android.settings.R;
import com.samsung.android.settings.voiceinput.PackageUtils;
import com.samsung.android.settings.voiceinput.RecognizerUtils;
import com.samsung.android.settings.voiceinput.offline.OfflineLanguageUtil;
import com.samsung.android.settings.voiceinput.offline.SCSPackageDownloadManager;
import com.samsung.android.util.SemLog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.android.schedulers.HandlerScheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.observers.LambdaObserver;
import io.reactivex.internal.operators.observable.ObservableHide;
import io.reactivex.internal.operators.observable.ObservableObserveOn;
import io.reactivex.internal.util.OpenHashSet;
import java.util.Optional;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class LangPackPreference extends SecPreference implements OfflineLanguageUtil.onLangPackResultListener {
    private static final String TAG = "@VoiceIn:LangPackPreference ";
    private CompositeDisposable mCompositeDisposable;
    Context mContext;
    private ImageButton mDownloadBtn;
    private View mItemView;
    private Language mLanguage;
    private OnViewAttachedListener mListener;
    String mLocaleCode;
    private ProgressBar mProgressBar;
    private TextView mSummary;
    private TextView mTitle;

    /* JADX WARN: Type inference failed for: r2v3, types: [com.samsung.android.settings.voiceinput.offline.LangPackPreference$$ExternalSyntheticLambda3] */
    public static void $r8$lambda$1QdpaTYi6WAApriTeDTwcOOGMeo(final LangPackPreference langPackPreference) {
        if (!PackageUtils.isNetworkConnected(langPackPreference.getContext().getApplicationContext(), 1) && !PackageUtils.isNetworkConnected(langPackPreference.getContext().getApplicationContext(), 0)) {
            Toast.makeText(langPackPreference.mContext, R.string.web_tos_no_network_connection_content, 0).show();
            return;
        }
        SCSPackageDownloadManager.addDownloadPackage(langPackPreference.getContext(), langPackPreference.mLocaleCode, langPackPreference.mLanguage.getLangPackage());
        CompositeDisposable compositeDisposable = langPackPreference.mCompositeDisposable;
        ObservableHide installResult = SCSPackageDownloadManager.getInstallResult();
        HandlerScheduler handlerScheduler = AndroidSchedulers.MAIN_THREAD;
        if (handlerScheduler == null) {
            throw new NullPointerException("scheduler == null");
        }
        ObservableObserveOn observeOn = installResult.observeOn(handlerScheduler);
        ?? r2 = new Consumer() { // from class: com.samsung.android.settings.voiceinput.offline.LangPackPreference$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SCSPackageDownloadManager.LanguageData languageData = (SCSPackageDownloadManager.LanguageData) obj;
                LangPackPreference langPackPreference2 = LangPackPreference.this;
                langPackPreference2.getClass();
                String str = languageData.language;
                if (str != null && str.equals(langPackPreference2.mLocaleCode)) {
                    String str2 = languageData.version;
                    SemLog.i("@VoiceIn:LangPackPreference ", "got notification after updated for locale " + languageData.language);
                    OfflineLanguageUtil.setLanguagePackageStatus(langPackPreference2.mContext, 3, langPackPreference2.mLocaleCode);
                    OfflineLanguageUtil.setLanguagePackageVersion(langPackPreference2.mContext, langPackPreference2.mLocaleCode, str2);
                    langPackPreference2.setPreferenceStatus();
                }
            }
        };
        Functions.EmptyRunnable emptyRunnable = Functions.EMPTY_RUNNABLE;
        LambdaObserver lambdaObserver = new LambdaObserver(r2);
        observeOn.subscribe(lambdaObserver);
        compositeDisposable.add(lambdaObserver);
        Intent intent = new Intent();
        intent.setData(Uri.parse(langPackPreference.mLanguage.getStoreIntent().getData().toString()));
        intent.putExtra("type", "cover");
        intent.addFlags(268435456);
        langPackPreference.mContext.startActivity(intent);
    }

    public LangPackPreference(Context context, Language language) {
        super(context, null);
        this.mContext = context;
        setLayoutResource(R.layout.lang_pack_preference_layout);
        setKey(RecognizerUtils.getLocaleCode(language.getLocale()) + "_pref");
        this.mCompositeDisposable = new CompositeDisposable();
        this.mLocaleCode = RecognizerUtils.getLocaleCode(language.getLocale());
        this.mLanguage = language;
        SemLog.i(TAG, "LangPackPreference constructor() : language " + this.mLanguage);
    }

    public final void addViewInflateListener(OnViewAttachedListener onViewAttachedListener) {
        SemLog.i(TAG, "addViewInflateListener  localeCode : " + this.mLocaleCode);
        this.mListener = onViewAttachedListener;
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mItemView = preferenceViewHolder.itemView;
        this.mDownloadBtn = (ImageButton) preferenceViewHolder.findViewById(R.id.download_btn);
        this.mProgressBar = (ProgressBar) preferenceViewHolder.findViewById(R.id.progress);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.title_text);
        this.mTitle = textView;
        textView.setText(this.mLanguage.getLanguageName());
        this.mSummary = (TextView) preferenceViewHolder.findViewById(R.id.summary_text);
        this.mDownloadBtn.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.settings.voiceinput.offline.LangPackPreference$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LangPackPreference.$r8$lambda$1QdpaTYi6WAApriTeDTwcOOGMeo(LangPackPreference.this);
            }
        });
        SemLog.i(TAG, "onBindViewHolder");
        Optional.ofNullable(this.mListener).ifPresent(new LangPackPreference$$ExternalSyntheticLambda2(0));
        setPreferenceStatus();
    }

    @Override // androidx.preference.Preference
    public final void onClick() {
        SemLog.i(TAG, "LangPackPreference clicked!!");
        this.mDownloadBtn.performClick();
    }

    @Override // androidx.preference.Preference
    public final void onDetached() {
        super.onDetached();
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        if (compositeDisposable.disposed) {
            return;
        }
        synchronized (compositeDisposable) {
            try {
                if (!compositeDisposable.disposed) {
                    OpenHashSet openHashSet = compositeDisposable.resources;
                    compositeDisposable.resources = null;
                    CompositeDisposable.dispose(openHashSet);
                }
            } finally {
            }
        }
    }

    @Override // com.samsung.android.settings.voiceinput.offline.OfflineLanguageUtil.onLangPackResultListener
    public final void onLangPackResultSubscribed() {
        SemLog.i(TAG, "onLangPackResultSubscribed : localeCode " + this.mLocaleCode);
        Optional.ofNullable(this.mProgressBar).ifPresent(new LangPackPreference$$ExternalSyntheticLambda2(1));
    }

    @Override // com.samsung.android.settings.voiceinput.offline.OfflineLanguageUtil.onLangPackResultListener
    public final void onLangPackResultUpdated() {
        SemLog.i(TAG, "onLangPackResultUpdated : localeCode " + this.mLocaleCode);
        Optional.ofNullable(this.mProgressBar).ifPresent(new LangPackPreference$$ExternalSyntheticLambda2(2));
        Optional.ofNullable(this.mLanguage).ifPresent(new java.util.function.Consumer() { // from class: com.samsung.android.settings.voiceinput.offline.LangPackPreference$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                LangPackPreference.this.setPreferenceStatus();
            }
        });
        this.mListener = null;
    }

    @Override // androidx.preference.Preference
    public final void setEnabled(final boolean z) {
        if (z) {
            this.mTitle.setEnabled(true);
            this.mDownloadBtn.setImageResource(R.drawable.ic_voice_input_download);
        } else {
            this.mTitle.setEnabled(false);
            this.mDownloadBtn.setImageResource(R.drawable.ic_voice_input_download_disabled);
        }
        this.mItemView.post(new Runnable() { // from class: com.samsung.android.settings.voiceinput.offline.LangPackPreference$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                super/*androidx.preference.Preference*/.setEnabled(z);
            }
        });
    }

    public final void setPreferenceStatus() {
        SemLog.i(TAG, "setPreferenceStatus:: " + this.mLocaleCode);
        Language language = this.mLanguage;
        Context context = this.mContext;
        String str = this.mLocaleCode;
        int i = PreferenceManager.getDefaultSharedPreferences(context).getInt(str, 0);
        SemLog.i("@VoiceIn: OfflineLanguageUtil", "getLanguagePackageStatus locale :" + str + " Status:" + i);
        language.setStatus(i);
        try {
            int status = this.mLanguage.getStatus();
            if (status == 1) {
                SemLog.i(TAG, "setPreferenceStatus:: Not installed");
                this.mSummary.setVisibility(8);
                setEnabled(true);
            } else if (status == 2) {
                SemLog.i(TAG, "setPreferenceStatus:: A newer version is available");
                this.mSummary.setVisibility(0);
                this.mSummary.setText(R.string.langpack_new_version_available);
                setEnabled(true);
            } else if (status != 3) {
                SemLog.e(TAG, "setPreferenceStatus:: Not available, mLanguage.getStatus = " + this.mLanguage.getStatus());
                this.mSummary.setVisibility(8);
                setEnabled(true);
            } else {
                SemLog.i(TAG, "setPreferenceStatus:: Latest version");
                this.mSummary.setVisibility(0);
                this.mSummary.setText(R.string.langpack_latest_version);
                setEnabled(false);
            }
        } catch (NullPointerException unused) {
            SemLog.e(TAG, "setPreferenceStatus:: Preference is not bind viewholder");
        }
    }
}

package com.samsung.android.settings.languagepack;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import androidx.preference.PreferenceCategory;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.settings.languagepack.data.LanguageInfo;
import com.samsung.android.settings.languagepack.logger.Log;
import com.samsung.android.settings.languagepack.manager.LanguagePackManager;
import com.samsung.android.settings.languagepack.service.LanguagePackDownloadService;
import com.samsung.android.settings.languagepack.widget.LanguagePackPreference;
import com.samsung.android.settings.languagepack.widget.LanguagePackSettingsDividerItemDecorator;
import com.samsung.android.sivs.ai.sdkcommon.asr.SpeechRecognitionConst;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LanguagePackSettingsFragment extends SettingsPreferenceFragment
        implements LanguagePackPreference.OnClickListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public PreferenceCategory mAvailableCategory;
    public PreferenceCategory mDownloadedCategory;
    public LanguagePackManager mLanguagePackManager;
    public String mLanguagePackType;
    public Menu mOptionsMenu;
    public int mRequestType;
    public boolean mSelectMode = false;
    public String mCallingPackage = null;
    public String mCurrentLangCode = null;
    public final List mRequiredLangCodeList = new ArrayList();
    public final AnonymousClass1 mServiceConnection = new AnonymousClass1();
    public final AnonymousClass2 mLangPackReceiver = new AnonymousClass2();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.languagepack.LanguagePackSettingsFragment$1, reason: invalid class name */
    public final class AnonymousClass1 implements ServiceConnection {
        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            int i = LanguagePackSettingsFragment.$r8$clinit;
            Log.d("LanguagePackSettingsFragment", "onServiceConnected() : " + iBinder);
            LanguagePackDownloadService.mLanguagePackService =
                    (LanguagePackDownloadService.LanguagePackServiceBinder) iBinder;
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            int i = LanguagePackSettingsFragment.$r8$clinit;
            Log.d("LanguagePackSettingsFragment", "onServiceDisconnected() : " + componentName);
            LanguagePackDownloadService.mLanguagePackService = null;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.languagepack.LanguagePackSettingsFragment$2, reason: invalid class name */
    public final class AnonymousClass2 extends BroadcastReceiver {
        public AnonymousClass2() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            LanguagePackPreference languagePackPreference;
            String action = intent.getAction();
            action.getClass();
            switch (action) {
                case "android.intent.action.PACKAGE_REPLACED":
                case "android.intent.action.PACKAGE_CHANGED":
                case "com.samsung.android.settings.action.LANGUAGE_PACK_UPDATE_VERSION":
                case "android.intent.action.PACKAGE_REMOVED":
                case "com.samsung.android.settings.action.LANGUAGE_PACK_UPDATE_RESULT":
                case "android.intent.action.PACKAGE_ADDED":
                    new Handler()
                            .postDelayed(
                                    new Runnable() { // from class:
                                                     // com.samsung.android.settings.languagepack.LanguagePackSettingsFragment$2$$ExternalSyntheticLambda0
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            LanguagePackSettingsFragment
                                                    languagePackSettingsFragment =
                                                            LanguagePackSettingsFragment.this;
                                            int i = LanguagePackSettingsFragment.$r8$clinit;
                                            languagePackSettingsFragment.updatePreference$10$1();
                                        }
                                    },
                                    100L);
                    break;
                case "com.samsung.android.settings.action.LANGUAGE_PACK_UPDATE_PROGRESS":
                    String stringExtra = intent.getStringExtra("extra_lang_code");
                    if (stringExtra != null
                            && (languagePackPreference =
                                            (LanguagePackPreference)
                                                    LanguagePackSettingsFragment.this
                                                            .getPreferenceScreen()
                                                            .findPreference(stringExtra))
                                    != null) {
                        languagePackPreference.updateProgress$1$1();
                        break;
                    }
                    break;
            }
        }
    }

    public final List getLanguageInfoList$2() {
        ArrayList arrayList = new ArrayList();
        try {
            arrayList.addAll(this.mLanguagePackManager.makeLanguageList());
        } catch (JSONException e) {
            Log.e(
                    "LanguagePackSettingsFragment",
                    "failed to make language info list : " + e.getMessage());
        }
        return !((ArrayList) this.mRequiredLangCodeList).isEmpty()
                ? (List)
                        arrayList.stream()
                                .filter(
                                        new LanguagePackSettingsFragment$$ExternalSyntheticLambda0(
                                                this, 0))
                                .collect(Collectors.toList())
                : arrayList;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 8140;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.sec_download_language);
        Intent intent = getIntent();
        if (intent != null) {
            this.mSelectMode = intent.getIntExtra("mode", 0) == 1;
            this.mCallingPackage = intent.getStringExtra("package");
            this.mCurrentLangCode = intent.getStringExtra(SpeechRecognitionConst.Key.LOCALE);
            this.mLanguagePackType = intent.getStringExtra("type");
            ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra("locales");
            String stringExtra = intent.getStringExtra(UniversalCredentialUtil.AGENT_TITLE);
            String stringExtra2 = intent.getStringExtra("description");
            String stringExtra3 = intent.getStringExtra("primary_category");
            String stringExtra4 = intent.getStringExtra("secondary_category");
            if (stringArrayListExtra != null) {
                ((ArrayList) this.mRequiredLangCodeList).clear();
                ((ArrayList) this.mRequiredLangCodeList).addAll(stringArrayListExtra);
            }
            if (!TextUtils.isEmpty(stringExtra)) {
                getActivity().setTitle(stringExtra);
            }
            if (!TextUtils.isEmpty(stringExtra2)) {
                findPreference("key_description").setTitle(stringExtra2);
            }
            if (!TextUtils.isEmpty(stringExtra3)) {
                findPreference("key_downloaded_language_category").setTitle(stringExtra3);
            }
            if (!TextUtils.isEmpty(stringExtra4)) {
                findPreference("key_available_language_category").setTitle(stringExtra4);
            }
            Log.d(
                    "LanguagePackSettingsFragment",
                    "Select mode: "
                            + this.mSelectMode
                            + ", Package: "
                            + this.mCallingPackage
                            + ", Current language code: "
                            + this.mCurrentLangCode
                            + ", LanguagePack Type : "
                            + this.mLanguagePackType);
        }
        getContext()
                .startService(
                        new Intent(getContext(), (Class<?>) LanguagePackDownloadService.class));
        this.mDownloadedCategory =
                (PreferenceCategory) findPreference("key_downloaded_language_category");
        this.mAvailableCategory =
                (PreferenceCategory) findPreference("key_available_language_category");
        this.mLanguagePackManager = LanguagePackManager.getInstance(getContext());
        updatePreference$10$1();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        this.mOptionsMenu = menu;
        menu.add(0, 2, 1, R.string.common_edit).setShowAsAction(2);
        updateVisibilityMenuItem();
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 2) {
            Bundle bundle = new Bundle();
            bundle.putInt("type", this.mRequestType);
            SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getContext());
            String name = LanguagePackEditFragment.class.getName();
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            launchRequest.mDestinationName = name;
            launchRequest.mArguments = bundle;
            subSettingLauncher.setTitleRes(R.string.sec_language_pack_title, null);
            launchRequest.mSourceMetricsCategory = 8140;
            subSettingLauncher.launch();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        getActivity().unbindService(this.mServiceConnection);
        LanguagePackDownloadService.mLanguagePackService = null;
        super.onPause();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        getActivity()
                .bindService(
                        new Intent(getActivity(), (Class<?>) LanguagePackDownloadService.class),
                        this.mServiceConnection,
                        1);
        super.onResume();
        updatePreference$10$1();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.android.settings.action.LANGUAGE_PACK_UPDATE_PROGRESS");
        intentFilter.addAction("com.samsung.android.settings.action.LANGUAGE_PACK_UPDATE_RESULT");
        intentFilter.addAction("com.samsung.android.settings.action.LANGUAGE_PACK_UPDATE_VERSION");
        getActivity().registerReceiver(this.mLangPackReceiver, intentFilter, 2);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter2.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter2.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter2.addDataScheme("package");
        getActivity().registerReceiver(this.mLangPackReceiver, intentFilter2, 2);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(this.mLangPackReceiver);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (this.mSelectMode) {
            setDivider(null);
            getListView()
                    .addItemDecoration(
                            new LanguagePackSettingsDividerItemDecorator(
                                    getResources()
                                                    .getDimensionPixelSize(
                                                            R.dimen
                                                                    .sec_widget_list_checkbox_width_for_divider_padding_inset)
                                            + getResources()
                                                    .getDimensionPixelSize(
                                                            R.dimen
                                                                    .sec_widget_checkbox_width_for_divider_inset),
                                    getContext(),
                                    getResources()
                                            .getDrawable(R.drawable.sec_top_level_list_divider)));
        }
        getListView().setItemViewCacheSize(60);
    }

    public final void updatePreference$10$1() {
        if (getContext() == null) {
            return;
        }
        this.mDownloadedCategory.removeAll();
        this.mAvailableCategory.removeAll();
        List languageInfoList$2 = getLanguageInfoList$2();
        if (!TextUtils.isEmpty(this.mLanguagePackType)) {
            boolean contains = this.mLanguagePackType.contains("asr");
            this.mRequestType = contains ? 1 : 0;
            this.mRequestType =
                    (contains ? 1 : 0) | (this.mLanguagePackType.contains("tts") ? 6 : 0);
        }
        Log.d("LanguagePackSettingsFragment", "updatePreference() START");
        List<LanguageInfo> list =
                (List)
                        languageInfoList$2.stream()
                                .filter(
                                        new LanguagePackSettingsFragment$$ExternalSyntheticLambda0(
                                                this, 1))
                                .collect(Collectors.toList());
        for (LanguageInfo languageInfo : list) {
            Context context = getContext();
            boolean z = this.mSelectMode;
            LanguagePackPreference languagePackPreference = new LanguagePackPreference(context);
            languagePackPreference.mLanguageInfo = languageInfo;
            languagePackPreference.mIsSelectMode = z;
            languagePackPreference.setOrder(languageInfo.mDisplayOrder);
            languagePackPreference.setKey(languageInfo.mLanguageCode);
            languagePackPreference.mListener = this;
            languagePackPreference.setTitle(languageInfo.getDisplayName());
            boolean checkDownloadedWithType =
                    languageInfo.checkDownloadedWithType(getContext(), this.mRequestType, list);
            languagePackPreference.mIsInstall = checkDownloadedWithType;
            if (checkDownloadedWithType) {
                this.mDownloadedCategory.addPreference(languagePackPreference);
            } else {
                this.mAvailableCategory.addPreference(languagePackPreference);
            }
            Log.d(
                    "LanguagePackSettingsFragment",
                    "updatePreference() lang : "
                            + languageInfo.mLanguageCode
                            + " : "
                            + checkDownloadedWithType
                            + " : "
                            + Integer.toBinaryString(this.mRequestType)
                            + " : "
                            + Integer.toBinaryString(languageInfo.mSupportType));
        }
        Log.d("LanguagePackSettingsFragment", "updatePreference() END");
        if (this.mSelectMode) {
            updateRadioButtons();
        }
        PreferenceCategory preferenceCategory = this.mDownloadedCategory;
        preferenceCategory.setVisible(preferenceCategory.getPreferenceCount() != 0);
        PreferenceCategory preferenceCategory2 = this.mAvailableCategory;
        preferenceCategory2.setVisible(preferenceCategory2.getPreferenceCount() != 0);
        updateVisibilityMenuItem();
    }

    public final void updateRadioButtons() {
        for (int i = 0; i < this.mDownloadedCategory.getPreferenceCount(); i++) {
            LanguagePackPreference languagePackPreference =
                    (LanguagePackPreference) this.mDownloadedCategory.getPreference(i);
            if (languagePackPreference.getKey().equals(this.mCurrentLangCode)) {
                if (!languagePackPreference.mChecked) {
                    languagePackPreference.mChecked = true;
                    RadioButton radioButton = languagePackPreference.mRadioButton;
                    if (radioButton != null) {
                        radioButton.setChecked(true);
                    }
                }
            } else if (languagePackPreference.mChecked) {
                languagePackPreference.mChecked = false;
                RadioButton radioButton2 = languagePackPreference.mRadioButton;
                if (radioButton2 != null) {
                    radioButton2.setChecked(false);
                }
            }
        }
    }

    public final void updateVisibilityMenuItem() {
        boolean z;
        Menu menu = this.mOptionsMenu;
        if (menu == null || this.mDownloadedCategory == null) {
            return;
        }
        MenuItem findItem = menu.findItem(2);
        if (findItem != null) {
            findItem.setTitle(R.string.delete);
            if (this.mDownloadedCategory.getPreferenceCount() != 0) {
                Iterator it = getLanguageInfoList$2().iterator();
                while (it.hasNext()) {
                    if (((LanguageInfo) it.next())
                            .hasUninstallablePackage(getContext(), this.mRequestType)) {
                        z = true;
                        break;
                    }
                }
            }
            z = false;
            findItem.setShowAsAction(z ? 2 : 0);
            findItem.setVisible(z);
        }
    }
}

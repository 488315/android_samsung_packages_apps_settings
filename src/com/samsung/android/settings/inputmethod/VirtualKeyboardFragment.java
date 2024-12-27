package com.samsung.android.settings.inputmethod;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.SearchIndexableData;
import android.provider.SearchIndexableResource;
import android.util.SparseArray;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.SecPreferenceCategory;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.inputmethod.InputMethodAndSubtypeUtilCompat;
import com.android.settingslib.inputmethod.InputMethodSettingValuesWrapper;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class VirtualKeyboardFragment extends DashboardFragment
        implements SecInputMethodPreference.OnSavePreferenceListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass2();
    public InputMethodSettingValuesWrapper mInputMethodSettingValues;
    public SecPreferenceCategory mInstalledKeyboardsCategory;
    public Context mUserAwareContext;
    public int mUserId;
    public final ArrayList mInputMethodPreferenceList = new ArrayList();
    public final Map mKeyboardListMap =
            Collections.unmodifiableMap(
                    new HashMap<String, Integer>() { // from class:
                        // com.samsung.android.settings.inputmethod.VirtualKeyboardFragment.1
                        {
                            put("com.samsung.android.honeyboard/.service.HoneyBoardService", -5);
                            put("com.samsung.android.svoiceime/.SamsungVoiceReco", -4);
                            put(
                                    "com.google.android.tts/com.google.android.apps.speech.tts.googletts.settings.asr.voiceime.VoiceInputMethodService",
                                    -3);
                            put(
                                    "com.google.android.tts/com.google.android.apps.search.transcription.voiceime.standalone.VoiceInputMethodService",
                                    -3);
                            put(
                                    "com.google.android.googlequicksearchbox/com.google.android.voicesearch.ime.VoiceInputMethodService",
                                    -2);
                            put("com.touchtype.swiftkey/com.touchtype.KeyboardService", -1);
                        }
                    });

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.inputmethod.VirtualKeyboardFragment$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            BaseSearchIndexProvider baseSearchIndexProvider =
                    VirtualKeyboardFragment.SEARCH_INDEX_DATA_PROVIDER;
            ArrayList arrayList = new ArrayList();
            arrayList.add(new ShowKeyboardButtonPreferenceController(context, null));
            arrayList.add(new CurrentKeyboardPreferenceController(context, null));
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getDynamicRawDataToIndex(Context context) {
            List dynamicRawDataToIndex = super.getDynamicRawDataToIndex(context);
            PackageManager packageManager = context.getPackageManager();
            Resources resources = context.getResources();
            InputMethodSettingValuesWrapper inputMethodSettingValuesWrapper =
                    InputMethodSettingValuesWrapper.getInstance(context);
            inputMethodSettingValuesWrapper.getClass();
            ArrayList arrayList = new ArrayList(inputMethodSettingValuesWrapper.mMethodList);
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                InputMethodInfo inputMethodInfo = (InputMethodInfo) arrayList.get(i);
                SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
                ((SearchIndexableData) searchIndexableRaw).key = inputMethodInfo.getId();
                searchIndexableRaw.title = inputMethodInfo.loadLabel(packageManager).toString();
                searchIndexableRaw.screenTitle = resources.getString(R.string.all_keyboards_list);
                ((SearchIndexableData) searchIndexableRaw).className =
                        VirtualKeyboardFragment.class.getName();
                ((ArrayList) dynamicRawDataToIndex).add(searchIndexableRaw);
            }
            SparseArray sparseArray = InputMethodSettingValuesWrapper.sInstanceMap;
            if (sparseArray != null) {
                sparseArray.clear();
            }
            return dynamicRawDataToIndex;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.sec_virtual_keyboard_settings;
            return Arrays.asList(searchIndexableResource);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class FirstInstallTimeComparator implements Comparator {
        public final Context mContext;

        public FirstInstallTimeComparator(Context context) {
            this.mContext = context;
        }

        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            SecInputMethodPreference secInputMethodPreference = (SecInputMethodPreference) obj2;
            try {
                long j =
                        this.mContext
                                .getPackageManager()
                                .getPackageInfo(
                                        ((SecInputMethodPreference) obj).mImi.getPackageName(), 0)
                                .firstInstallTime;
                long j2 =
                        this.mContext
                                .getPackageManager()
                                .getPackageInfo(secInputMethodPreference.mImi.getPackageName(), 0)
                                .firstInstallTime;
                if (j < j2) {
                    return -1;
                }
                return j2 < j ? 1 : 0;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        Lifecycle settingsLifecycle = getSettingsLifecycle();
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ShowKeyboardButtonPreferenceController(context, settingsLifecycle));
        arrayList.add(new CurrentKeyboardPreferenceController(context, settingsLifecycle));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "VirtualKeyboardFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return FileType.ODS;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_virtual_keyboard_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        UserHandle managedProfile;
        super.onAttach(context);
        int i = getArguments().getInt(ImsProfile.SERVICE_PROFILE);
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        int myUserId = UserHandle.myUserId();
        if (i == 1) {
            UserHandle userHandle = userManager.getPrimaryUser().getUserHandle();
            myUserId = userHandle.getIdentifier();
            context = context.createContextAsUser(userHandle, 0);
        } else if (i == 2) {
            if (myUserId == 10) {
                managedProfile = UserHandle.of(myUserId);
            } else {
                myUserId = Utils.getManagedProfileId(userManager, myUserId);
                managedProfile = Utils.getManagedProfile(userManager);
            }
            context = context.createContextAsUser(managedProfile, 0);
        }
        this.mUserId = myUserId;
        this.mUserAwareContext = context;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setAutoRemoveInsetCategory(false);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        super.onCreatePreferences(bundle, str);
        FragmentActivity activity = getActivity();
        if (this.mUserAwareContext.getApplicationContext() != null) {
            this.mInputMethodSettingValues =
                    InputMethodSettingValuesWrapper.getInstance(
                            this.mUserAwareContext.getApplicationContext());
        } else {
            this.mInputMethodSettingValues =
                    InputMethodSettingValuesWrapper.getInstance(this.mUserAwareContext);
        }
        this.mInstalledKeyboardsCategory =
                (SecPreferenceCategory) findPreference("installed_keyboards");
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        this.mInputMethodSettingValues.getClass();
        SparseArray sparseArray = InputMethodSettingValuesWrapper.sInstanceMap;
        if (sparseArray != null) {
            sparseArray.clear();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mInputMethodSettingValues.refreshAllInputMethodAndSubtypes();
        if (this.mInstalledKeyboardsCategory == null) {
            return;
        }
        this.mInputMethodSettingValues.refreshAllInputMethodAndSubtypes();
        this.mInputMethodPreferenceList.clear();
        List permittedInputMethods =
                ((DevicePolicyManager)
                                this.mUserAwareContext.getSystemService(DevicePolicyManager.class))
                        .getPermittedInputMethods();
        Context prefContext = getPrefContext();
        InputMethodSettingValuesWrapper inputMethodSettingValuesWrapper =
                this.mInputMethodSettingValues;
        inputMethodSettingValuesWrapper.getClass();
        ArrayList arrayList = new ArrayList(inputMethodSettingValuesWrapper.mMethodList);
        List<InputMethodInfo> enabledInputMethodList =
                ((InputMethodManager) getContext().getSystemService(InputMethodManager.class))
                        .getEnabledInputMethodList();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            InputMethodInfo inputMethodInfo = (InputMethodInfo) arrayList.get(i);
            SecInputMethodPreference secInputMethodPreference =
                    new SecInputMethodPreference(
                            prefContext,
                            inputMethodInfo,
                            permittedInputMethods == null
                                    || permittedInputMethods.contains(
                                            inputMethodInfo.getPackageName())
                                    || enabledInputMethodList.contains(inputMethodInfo),
                            this,
                            this.mUserId);
            secInputMethodPreference.setIcon(getResources().getDrawable(R.drawable.empty_icon));
            this.mInputMethodPreferenceList.add(secInputMethodPreference);
        }
        Collections.sort(
                this.mInputMethodPreferenceList, new FirstInstallTimeComparator(prefContext));
        this.mInstalledKeyboardsCategory.removeAll();
        for (int i2 = 0; i2 < size; i2++) {
            SecInputMethodPreference secInputMethodPreference2 =
                    (SecInputMethodPreference) this.mInputMethodPreferenceList.get(i2);
            if (this.mKeyboardListMap.containsKey(secInputMethodPreference2.getKey())) {
                secInputMethodPreference2.setOrder(
                        ((Integer) this.mKeyboardListMap.get(secInputMethodPreference2.getKey()))
                                .intValue());
            }
            this.mInstalledKeyboardsCategory.addPreference(secInputMethodPreference2);
            InputMethodAndSubtypeUtilCompat.removeUnnecessaryNonPersistentPreference(
                    secInputMethodPreference2);
            secInputMethodPreference2.updatePreferenceViews();
        }
    }
}

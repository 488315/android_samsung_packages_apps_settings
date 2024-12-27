package com.android.settings.inputmethod;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.SearchIndexableResource;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.inputmethod.InputMethodAndSubtypeUtilCompat;
import com.android.settingslib.inputmethod.InputMethodPreference;
import com.android.settingslib.inputmethod.InputMethodSettingValuesWrapper;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.sec.ims.settings.ImsProfile;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AvailableVirtualKeyboardFragment extends DashboardFragment
        implements InputMethodPreference.OnSavePreferenceListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();

    @VisibleForTesting
    final ArrayList<InputMethodPreference> mInputMethodPreferenceList = new ArrayList<>();

    @VisibleForTesting InputMethodSettingValuesWrapper mInputMethodSettingValues;

    @VisibleForTesting Context mUserAwareContext;
    public int mUserId;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.inputmethod.AvailableVirtualKeyboardFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.available_virtual_keyboard;
            arrayList.add(searchIndexableResource);
            return arrayList;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AvailableVirtualKeyboardFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return FileType.EPUB;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.available_virtual_keyboard;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        int i = getArguments().getInt(ImsProfile.SERVICE_PROFILE);
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        int myUserId = UserHandle.myUserId();
        if (i == 1) {
            UserHandle profileParent = userManager.getProfileParent(UserHandle.of(myUserId));
            if (profileParent != null) {
                myUserId = profileParent.getIdentifier();
                context = context.createContextAsUser(profileParent, 0);
            }
        } else if (i == 2) {
            if (!userManager.isManagedProfile()) {
                myUserId = Utils.getManagedProfileId(userManager, myUserId);
            }
            context = context.createContextAsUser(UserHandle.of(myUserId), 0);
        } else if (i == 4) {
            if (!userManager.isPrivateProfile()) {
                myUserId = Utils.getCurrentUserIdOfType(userManager, 4);
            }
            context = context.createContextAsUser(UserHandle.of(myUserId), 0);
        }
        this.mUserId = myUserId;
        this.mUserAwareContext = context;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        addPreferencesFromResource(R.xml.available_virtual_keyboard);
        this.mInputMethodSettingValues =
                InputMethodSettingValuesWrapper.getInstance(
                        this.mUserAwareContext.getApplicationContext());
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        this.mInputMethodSettingValues = null;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mInputMethodSettingValues.refreshAllInputMethodAndSubtypes();
        updateInputMethodPreferenceViews();
    }

    public final void onSaveInputMethodPreference() {
        InputMethodAndSubtypeUtilCompat.saveInputMethodSubtypeListForUserInternal(
                this,
                this.mUserAwareContext.getContentResolver(),
                ((InputMethodManager) getContext().getSystemService(InputMethodManager.class))
                        .getInputMethodListAsUser(this.mUserId),
                getResources().getConfiguration().keyboard == 2,
                this.mUserId);
        this.mInputMethodSettingValues.refreshAllInputMethodAndSubtypes();
        Iterator<InputMethodPreference> it = this.mInputMethodPreferenceList.iterator();
        while (it.hasNext()) {
            it.next().updatePreferenceViews();
        }
    }

    @VisibleForTesting
    public void updateInputMethodPreferenceViews() {
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
        List enabledInputMethodListAsUser =
                ((InputMethodManager) getContext().getSystemService(InputMethodManager.class))
                        .getEnabledInputMethodListAsUser(UserHandle.of(this.mUserId));
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            InputMethodInfo inputMethodInfo = (InputMethodInfo) arrayList.get(i);
            InputMethodPreference inputMethodPreference =
                    new InputMethodPreference(
                            prefContext,
                            inputMethodInfo,
                            inputMethodInfo.loadLabel(prefContext.getPackageManager()),
                            permittedInputMethods == null
                                    || permittedInputMethods.contains(
                                            inputMethodInfo.getPackageName())
                                    || enabledInputMethodListAsUser.contains(inputMethodInfo),
                            this,
                            this.mUserId);
            inputMethodPreference.setIcon(
                    inputMethodInfo.loadIcon(this.mUserAwareContext.getPackageManager()));
            this.mInputMethodPreferenceList.add(inputMethodPreference);
        }
        final Collator collator = Collator.getInstance();
        this.mInputMethodPreferenceList.sort(
                new Comparator() { // from class:
                                   // com.android.settings.inputmethod.AvailableVirtualKeyboardFragment$$ExternalSyntheticLambda0
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        Collator collator2 = collator;
                        InputMethodPreference inputMethodPreference2 = (InputMethodPreference) obj;
                        InputMethodPreference inputMethodPreference3 = (InputMethodPreference) obj2;
                        BaseSearchIndexProvider baseSearchIndexProvider =
                                AvailableVirtualKeyboardFragment.SEARCH_INDEX_DATA_PROVIDER;
                        if (inputMethodPreference2 == inputMethodPreference3) {
                            inputMethodPreference2.getClass();
                            return 0;
                        }
                        boolean z = inputMethodPreference2.mHasPriorityInSorting;
                        if (z != inputMethodPreference3.mHasPriorityInSorting) {
                            return z ? -1 : 1;
                        }
                        CharSequence title = inputMethodPreference2.getTitle();
                        CharSequence title2 = inputMethodPreference3.getTitle();
                        boolean isEmpty = TextUtils.isEmpty(title);
                        boolean isEmpty2 = TextUtils.isEmpty(title2);
                        if (isEmpty || isEmpty2) {
                            return (isEmpty ? -1 : 0) - (isEmpty2 ? -1 : 0);
                        }
                        return collator2.compare(title.toString(), title2.toString());
                    }
                });
        getPreferenceScreen().removeAll();
        for (int i2 = 0; i2 < size; i2++) {
            InputMethodPreference inputMethodPreference2 = this.mInputMethodPreferenceList.get(i2);
            inputMethodPreference2.setOrder(i2);
            getPreferenceScreen().addPreference(inputMethodPreference2);
            InputMethodAndSubtypeUtilCompat.removeUnnecessaryNonPersistentPreference(
                    inputMethodPreference2);
            inputMethodPreference2.updatePreferenceViews();
        }
    }
}

package com.android.settings.inputmethod;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;

import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.internal.app.LocaleHelper;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.inputmethod.InputMethodAndSubtypeEnablerManagerCompat;
import com.android.settingslib.inputmethod.InputMethodAndSubtypeUtil;
import com.android.settingslib.inputmethod.InputMethodAndSubtypeUtilCompat;
import com.android.settingslib.inputmethod.InputMethodSettingValuesWrapper;
import com.android.settingslib.inputmethod.InputMethodSubtypePreference;
import com.android.settingslib.inputmethod.SwitchWithNoTextPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class InputMethodAndSubtypePreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnStart, OnStop, OnDestroy {
    private PreferenceFragmentCompat mFragment;
    private InputMethodAndSubtypeEnablerManagerCompat mManager;
    private String mTargetImi;

    public InputMethodAndSubtypePreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        String str;
        PreferenceFragmentCompat preferenceFragmentCompat;
        Iterator it;
        String str2;
        PreferenceFragmentCompat preferenceFragmentCompat2;
        Iterator it2;
        int i;
        String str3;
        InputMethodInfo inputMethodInfo;
        ArrayList arrayList;
        PreferenceCategory preferenceCategory;
        SwitchWithNoTextPreference switchWithNoTextPreference;
        String str4;
        PreferenceGroup preferenceGroup = preferenceScreen;
        super.displayPreference(preferenceScreen);
        final InputMethodAndSubtypeEnablerManagerCompat inputMethodAndSubtypeEnablerManagerCompat =
                this.mManager;
        PreferenceFragmentCompat preferenceFragmentCompat3 = this.mFragment;
        String str5 = this.mTargetImi;
        inputMethodAndSubtypeEnablerManagerCompat.getClass();
        int i2 = 1;
        inputMethodAndSubtypeEnablerManagerCompat.mHaveHardKeyboard =
                preferenceFragmentCompat3.getResources().getConfiguration().keyboard == 2;
        Iterator it3 = inputMethodAndSubtypeEnablerManagerCompat.mInputMethodInfoList.iterator();
        while (it3.hasNext()) {
            InputMethodInfo inputMethodInfo2 = (InputMethodInfo) it3.next();
            if (inputMethodInfo2.getId().equals(str5) || TextUtils.isEmpty(str5)) {
                Context context = preferenceFragmentCompat3.getPreferenceManager().mContext;
                int subtypeCount = inputMethodInfo2.getSubtypeCount();
                if (subtypeCount > i2) {
                    String id = inputMethodInfo2.getId();
                    PreferenceCategory preferenceCategory2 = new PreferenceCategory(context);
                    preferenceGroup.addPreference(preferenceCategory2);
                    preferenceCategory2.setTitle(
                            inputMethodInfo2.loadLabel(context.getPackageManager()));
                    preferenceCategory2.setKey(id);
                    SwitchWithNoTextPreference switchWithNoTextPreference2 =
                            new SwitchWithNoTextPreference(context);
                    inputMethodAndSubtypeEnablerManagerCompat.mAutoSelectionPrefsMap.put(
                            id, switchWithNoTextPreference2);
                    preferenceCategory2.addPreference(switchWithNoTextPreference2);
                    switchWithNoTextPreference2.setOnPreferenceChangeListener(
                            inputMethodAndSubtypeEnablerManagerCompat);
                    PreferenceCategory preferenceCategory3 = new PreferenceCategory(context);
                    preferenceCategory3.setTitle(R.string.active_input_method_subtypes);
                    preferenceGroup.addPreference(preferenceCategory3);
                    ArrayList arrayList2 = new ArrayList();
                    String str6 = null;
                    int i3 = 0;
                    while (i3 < subtypeCount) {
                        InputMethodSubtype subtypeAt = inputMethodInfo2.getSubtypeAt(i3);
                        if (!subtypeAt.overridesImplicitlyEnabledSubtype()) {
                            str2 = str5;
                            preferenceFragmentCompat2 = preferenceFragmentCompat3;
                            it2 = it3;
                            i = i3;
                            str3 = str6;
                            inputMethodInfo = inputMethodInfo2;
                            arrayList = arrayList2;
                            preferenceCategory = preferenceCategory3;
                            switchWithNoTextPreference = switchWithNoTextPreference2;
                            str4 = id;
                            arrayList.add(
                                    new InputMethodSubtypePreference(
                                            context,
                                            inputMethodInfo2.getId() + subtypeAt.hashCode(),
                                            LocaleHelper.toSentenceCase(
                                                    subtypeAt
                                                            .getDisplayName(
                                                                    context,
                                                                    inputMethodInfo2
                                                                            .getPackageName(),
                                                                    inputMethodInfo2
                                                                            .getServiceInfo()
                                                                            .applicationInfo)
                                                            .toString(),
                                                    InputMethodAndSubtypeUtil.getDisplayLocale(
                                                            context)),
                                            subtypeAt.getLocaleObject(),
                                            context.getResources().getConfiguration().locale));
                        } else if (str6 == null) {
                            str2 = str5;
                            str6 =
                                    LocaleHelper.toSentenceCase(
                                            subtypeAt
                                                    .getDisplayName(
                                                            context,
                                                            inputMethodInfo2.getPackageName(),
                                                            inputMethodInfo2.getServiceInfo()
                                                                    .applicationInfo)
                                                    .toString(),
                                            InputMethodAndSubtypeUtil.getDisplayLocale(context));
                            preferenceFragmentCompat2 = preferenceFragmentCompat3;
                            it2 = it3;
                            inputMethodInfo = inputMethodInfo2;
                            i = i3;
                            arrayList = arrayList2;
                            preferenceCategory = preferenceCategory3;
                            switchWithNoTextPreference = switchWithNoTextPreference2;
                            str4 = id;
                            i3 = i + 1;
                            preferenceCategory3 = preferenceCategory;
                            switchWithNoTextPreference2 = switchWithNoTextPreference;
                            id = str4;
                            arrayList2 = arrayList;
                            str5 = str2;
                            preferenceFragmentCompat3 = preferenceFragmentCompat2;
                            it3 = it2;
                            inputMethodInfo2 = inputMethodInfo;
                        } else {
                            str2 = str5;
                            preferenceFragmentCompat2 = preferenceFragmentCompat3;
                            it2 = it3;
                            inputMethodInfo = inputMethodInfo2;
                            i = i3;
                            str3 = str6;
                            arrayList = arrayList2;
                            preferenceCategory = preferenceCategory3;
                            switchWithNoTextPreference = switchWithNoTextPreference2;
                            str4 = id;
                        }
                        str6 = str3;
                        i3 = i + 1;
                        preferenceCategory3 = preferenceCategory;
                        switchWithNoTextPreference2 = switchWithNoTextPreference;
                        id = str4;
                        arrayList2 = arrayList;
                        str5 = str2;
                        preferenceFragmentCompat3 = preferenceFragmentCompat2;
                        it3 = it2;
                        inputMethodInfo2 = inputMethodInfo;
                    }
                    str = str5;
                    preferenceFragmentCompat = preferenceFragmentCompat3;
                    it = it3;
                    String str7 = str6;
                    ArrayList arrayList3 = arrayList2;
                    PreferenceCategory preferenceCategory4 = preferenceCategory3;
                    SwitchWithNoTextPreference switchWithNoTextPreference3 =
                            switchWithNoTextPreference2;
                    String str8 = id;
                    arrayList3.sort(
                            new Comparator() { // from class:
                                               // com.android.settingslib.inputmethod.InputMethodAndSubtypeEnablerManagerCompat$$ExternalSyntheticLambda0
                                @Override // java.util.Comparator
                                public final int compare(Object obj, Object obj2) {
                                    InputMethodAndSubtypeEnablerManagerCompat
                                            inputMethodAndSubtypeEnablerManagerCompat2 =
                                                    InputMethodAndSubtypeEnablerManagerCompat.this;
                                    Preference preference = (Preference) obj;
                                    Preference preference2 = (Preference) obj2;
                                    inputMethodAndSubtypeEnablerManagerCompat2.getClass();
                                    if (!(preference instanceof InputMethodSubtypePreference)) {
                                        return preference.compareTo(preference2);
                                    }
                                    InputMethodSubtypePreference inputMethodSubtypePreference =
                                            (InputMethodSubtypePreference) preference;
                                    Collator collator =
                                            inputMethodAndSubtypeEnablerManagerCompat2.mCollator;
                                    inputMethodSubtypePreference.getClass();
                                    if (inputMethodSubtypePreference == preference2) {
                                        return 0;
                                    }
                                    if (!(preference2 instanceof InputMethodSubtypePreference)) {
                                        return inputMethodSubtypePreference.compareTo(preference2);
                                    }
                                    InputMethodSubtypePreference inputMethodSubtypePreference2 =
                                            (InputMethodSubtypePreference) preference2;
                                    boolean z = inputMethodSubtypePreference.mIsSystemLocale;
                                    if (!z || inputMethodSubtypePreference2.mIsSystemLocale) {
                                        if (z || !inputMethodSubtypePreference2.mIsSystemLocale) {
                                            boolean z2 =
                                                    inputMethodSubtypePreference.mIsSystemLanguage;
                                            if (!z2
                                                    || inputMethodSubtypePreference2
                                                            .mIsSystemLanguage) {
                                                if (z2
                                                        || !inputMethodSubtypePreference2
                                                                .mIsSystemLanguage) {
                                                    CharSequence title =
                                                            inputMethodSubtypePreference.getTitle();
                                                    CharSequence title2 = preference2.getTitle();
                                                    boolean isEmpty = TextUtils.isEmpty(title);
                                                    boolean isEmpty2 = TextUtils.isEmpty(title2);
                                                    if (isEmpty || isEmpty2) {
                                                        return (isEmpty ? -1 : 0)
                                                                - (isEmpty2 ? -1 : 0);
                                                    }
                                                    return collator.compare(
                                                            title.toString(), title2.toString());
                                                }
                                            }
                                        }
                                        return 1;
                                    }
                                    return -1;
                                }
                            });
                    Iterator it4 = arrayList3.iterator();
                    while (it4.hasNext()) {
                        Preference preference = (Preference) it4.next();
                        preferenceCategory4.addPreference(preference);
                        preference.setOnPreferenceChangeListener(
                                inputMethodAndSubtypeEnablerManagerCompat);
                        InputMethodAndSubtypeUtil.removeUnnecessaryNonPersistentPreference(
                                preference);
                    }
                    inputMethodAndSubtypeEnablerManagerCompat.mInputMethodAndSubtypePrefsMap.put(
                            str8, arrayList3);
                    if (TextUtils.isEmpty(str7)) {
                        switchWithNoTextPreference3.setTitle(
                                R.string.use_system_language_to_select_input_method_subtypes);
                    } else {
                        switchWithNoTextPreference3.setTitle(str7);
                    }
                    preferenceGroup = preferenceScreen;
                    str5 = str;
                    preferenceFragmentCompat3 = preferenceFragmentCompat;
                    it3 = it;
                    i2 = 1;
                }
            }
            str = str5;
            preferenceFragmentCompat = preferenceFragmentCompat3;
            it = it3;
            preferenceGroup = preferenceScreen;
            str5 = str;
            preferenceFragmentCompat3 = preferenceFragmentCompat;
            it3 = it;
            i2 = 1;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public void initialize(PreferenceFragmentCompat preferenceFragmentCompat, String str) {
        this.mFragment = preferenceFragmentCompat;
        this.mTargetImi = str;
        this.mManager = new InputMethodAndSubtypeEnablerManagerCompat(preferenceFragmentCompat);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public void onDestroy() {
        this.mManager.getClass();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        InputMethodAndSubtypeEnablerManagerCompat inputMethodAndSubtypeEnablerManagerCompat =
                this.mManager;
        Context context = this.mContext;
        PreferenceFragmentCompat preferenceFragmentCompat = this.mFragment;
        inputMethodAndSubtypeEnablerManagerCompat.getClass();
        InputMethodSettingValuesWrapper.getInstance(context.getApplicationContext())
                .refreshAllInputMethodAndSubtypes();
        ContentResolver contentResolver = context.getContentResolver();
        List<InputMethodInfo> list = inputMethodAndSubtypeEnablerManagerCompat.mInputMethodInfoList;
        HashMap hashMap = inputMethodAndSubtypeEnablerManagerCompat.mInputMethodAndSubtypePrefsMap;
        HashMap enabledInputMethodsAndSubtypeList =
                InputMethodAndSubtypeUtilCompat.getEnabledInputMethodsAndSubtypeList(
                        contentResolver);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String id = ((InputMethodInfo) it.next()).getId();
            Preference findPreference = preferenceFragmentCompat.findPreference(id);
            if (findPreference instanceof TwoStatePreference) {
                boolean containsKey = enabledInputMethodsAndSubtypeList.containsKey(id);
                ((TwoStatePreference) findPreference).setChecked(containsKey);
                if (hashMap != null) {
                    Iterator it2 = ((List) hashMap.get(id)).iterator();
                    while (it2.hasNext()) {
                        ((Preference) it2.next()).setEnabled(containsKey);
                    }
                }
                PreferenceScreen preferenceScreen = preferenceFragmentCompat.getPreferenceScreen();
                for (InputMethodInfo inputMethodInfo : list) {
                    if (id.equals(inputMethodInfo.getId())) {
                        int subtypeCount = inputMethodInfo.getSubtypeCount();
                        for (int i = 0; i < subtypeCount; i++) {
                            InputMethodSubtype subtypeAt = inputMethodInfo.getSubtypeAt(i);
                            StringBuilder m =
                                    EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0
                                            .m(id);
                            m.append(subtypeAt.hashCode());
                            TwoStatePreference twoStatePreference =
                                    (TwoStatePreference)
                                            preferenceScreen.findPreference(m.toString());
                            if (twoStatePreference != null) {
                                twoStatePreference.setEnabled(containsKey);
                            }
                        }
                    }
                }
            }
        }
        PreferenceScreen preferenceScreen2 = preferenceFragmentCompat.getPreferenceScreen();
        for (InputMethodInfo inputMethodInfo2 : list) {
            String id2 = inputMethodInfo2.getId();
            if (enabledInputMethodsAndSubtypeList.containsKey(id2)) {
                HashSet hashSet = (HashSet) enabledInputMethodsAndSubtypeList.get(id2);
                int subtypeCount2 = inputMethodInfo2.getSubtypeCount();
                for (int i2 = 0; i2 < subtypeCount2; i2++) {
                    String valueOf = String.valueOf(inputMethodInfo2.getSubtypeAt(i2).hashCode());
                    TwoStatePreference twoStatePreference2 =
                            (TwoStatePreference) preferenceScreen2.findPreference(id2 + valueOf);
                    if (twoStatePreference2 != null) {
                        twoStatePreference2.setChecked(hashSet.contains(valueOf));
                    }
                }
            }
        }
        inputMethodAndSubtypeEnablerManagerCompat.updateAutoSelectionPreferences();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        InputMethodAndSubtypeEnablerManagerCompat inputMethodAndSubtypeEnablerManagerCompat =
                this.mManager;
        Context context = this.mContext;
        PreferenceFragmentCompat preferenceFragmentCompat = this.mFragment;
        inputMethodAndSubtypeEnablerManagerCompat.getClass();
        ContentResolver contentResolver = context.getContentResolver();
        List list = inputMethodAndSubtypeEnablerManagerCompat.mInputMethodInfoList;
        boolean z = inputMethodAndSubtypeEnablerManagerCompat.mHaveHardKeyboard;
        TextUtils.SimpleStringSplitter simpleStringSplitter =
                InputMethodAndSubtypeUtilCompat.sStringInputMethodSplitter;
        InputMethodAndSubtypeUtilCompat.saveInputMethodSubtypeListForUserInternal(
                preferenceFragmentCompat, contentResolver, list, z, UserHandle.myUserId());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}

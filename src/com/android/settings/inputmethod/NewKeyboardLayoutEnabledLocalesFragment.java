package com.android.settings.inputmethod;

import android.content.Context;
import android.hardware.input.InputDeviceIdentifier;
import android.hardware.input.InputManager;
import android.hardware.input.KeyboardLayout;
import android.hardware.input.KeyboardLayoutSelectionResult;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import android.view.InputDevice;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.dashboard.DashboardFragment;

import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NewKeyboardLayoutEnabledLocalesFragment extends DashboardFragment
        implements InputManager.InputDeviceListener {
    public ArrayList mAutomataLanguageLocaleList;
    public Context mContext;
    public InputManager mIm;
    public InputMethodManager mImm;
    public int mInputDeviceId;
    public InputDeviceIdentifier mInputDeviceIdentifier;
    public final ArrayList mKeyboardInfoList = new ArrayList();
    public int mUserId;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.inputmethod.NewKeyboardLayoutEnabledLocalesFragment$2, reason: invalid class name */
    public final class AnonymousClass2 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ((NewKeyboardSettingsUtils.KeyboardInfo) obj)
                    .mSubtypeLabel
                    .toString()
                    .compareTo(
                            ((NewKeyboardSettingsUtils.KeyboardInfo) obj2)
                                    .mSubtypeLabel.toString());
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "NewKeyboardLayoutEnabledLocalesFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1959;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.keyboard_settings_enabled_locales_list;
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x00c6, code lost:

       if (r10 != null) goto L36;
    */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00c8, code lost:

       r10.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00d5, code lost:

       r12 = r1.trim().split(";");
       r0 = new java.util.ArrayList();
    */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00e5, code lost:

       if (r12.length <= 0) goto L48;
    */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00e7, code lost:

       r1 = 0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00e9, code lost:

       if (r1 >= r12.length) goto L63;
    */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00eb, code lost:

       r0.add(r12[r1]);
       android.util.Log.d("NewKeyboardSettingsUtils", "aAutomataResult[" + r1 + "]=" + r12[r1]);
       r1 = r1 + 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x010e, code lost:

       r11.mAutomataLanguageLocaleList = r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0110, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00d2, code lost:

       if (r10 != null) goto L36;
    */
    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onActivityCreated(android.os.Bundle r12) {
        /*
            Method dump skipped, instructions count: 273
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.inputmethod.NewKeyboardLayoutEnabledLocalesFragment.onActivityCreated(android.os.Bundle):void");
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        int i = getArguments().getInt(ImsProfile.SERVICE_PROFILE);
        int myUserId = UserHandle.myUserId();
        UserManager userManager = (UserManager) this.mContext.getSystemService(UserManager.class);
        if (i == 1) {
            myUserId = userManager.getPrimaryUser().getUserHandle().getIdentifier();
        } else if (i != 2) {
            if (i == 4 && !userManager.isPrivateProfile()) {
                myUserId = Utils.getCurrentUserIdOfType(userManager, 4);
            }
        } else if (!userManager.isManagedProfile()) {
            myUserId = Utils.getManagedProfileId(userManager, myUserId);
        }
        this.mUserId = myUserId;
        this.mIm = (InputManager) this.mContext.getSystemService(InputManager.class);
        this.mImm = (InputMethodManager) this.mContext.getSystemService(InputMethodManager.class);
        this.mInputDeviceId = -1;
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceChanged(int i) {
        int i2 = this.mInputDeviceId;
        if (i2 < 0 || i != i2) {
            return;
        }
        updateCheckedState();
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceRemoved(int i) {
        int i2 = this.mInputDeviceId;
        if (i2 < 0 || i != i2) {
            return;
        }
        getActivity().finish();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        updateCheckedState();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        this.mIm.registerInputDeviceListener(this, null);
        InputDevice inputDevice =
                NewKeyboardSettingsUtils.getInputDevice(this.mIm, this.mInputDeviceIdentifier);
        if (inputDevice != null) {
            this.mInputDeviceId = inputDevice.getId();
        } else {
            Log.e(
                    "NewKeyboardLayoutEnabledLocalesFragment",
                    "Unable to start: input device is null");
            getActivity().finish();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        this.mIm.unregisterInputDeviceListener(this);
        this.mInputDeviceId = -1;
    }

    public final void updateCheckedState() {
        String string;
        final boolean z;
        ArrayList arrayList;
        if (NewKeyboardSettingsUtils.getInputDevice(this.mIm, this.mInputDeviceIdentifier)
                == null) {
            return;
        }
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        preferenceScreen.removeAll();
        List<InputMethodInfo> enabledInputMethodListAsUser =
                this.mImm.getEnabledInputMethodListAsUser(UserHandle.of(this.mUserId));
        Collections.sort(
                enabledInputMethodListAsUser,
                new Comparator() { // from class:
                                   // com.android.settings.inputmethod.NewKeyboardLayoutEnabledLocalesFragment.1
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return ((InputMethodInfo) obj)
                                .loadLabel(
                                        NewKeyboardLayoutEnabledLocalesFragment.this.mContext
                                                .getPackageManager())
                                .toString()
                                .compareTo(
                                        ((InputMethodInfo) obj2)
                                                .loadLabel(
                                                        NewKeyboardLayoutEnabledLocalesFragment.this
                                                                .mContext.getPackageManager())
                                                .toString());
                    }
                });
        for (InputMethodInfo inputMethodInfo : enabledInputMethodListAsUser) {
            this.mKeyboardInfoList.clear();
            Iterator it =
                    this.mImm
                            .getEnabledInputMethodSubtypeListAsUser(
                                    inputMethodInfo.getId(), true, UserHandle.of(this.mUserId))
                            .iterator();
            while (true) {
                int i = 0;
                if (!it.hasNext()) {
                    break;
                }
                InputMethodSubtype inputMethodSubtype = (InputMethodSubtype) it.next();
                if (inputMethodSubtype.isSuitableForPhysicalKeyboardLayoutMapping()) {
                    CharSequence displayName =
                            inputMethodSubtype.getDisplayName(
                                    this.mContext,
                                    inputMethodInfo.getPackageName(),
                                    inputMethodInfo.getServiceInfo().applicationInfo);
                    KeyboardLayout[] keyboardLayoutListForInputDevice =
                            this.mIm.getKeyboardLayoutListForInputDevice(
                                    this.mInputDeviceIdentifier,
                                    this.mUserId,
                                    inputMethodInfo,
                                    inputMethodSubtype);
                    KeyboardLayoutSelectionResult keyboardLayoutForInputDevice =
                            this.mIm.getKeyboardLayoutForInputDevice(
                                    this.mInputDeviceIdentifier,
                                    this.mUserId,
                                    inputMethodInfo,
                                    inputMethodSubtype);
                    if (keyboardLayoutForInputDevice.getLayoutDescriptor() != null) {
                        while (true) {
                            if (i >= keyboardLayoutListForInputDevice.length) {
                                break;
                            }
                            if (keyboardLayoutListForInputDevice[i]
                                    .getDescriptor()
                                    .equals(keyboardLayoutForInputDevice.getLayoutDescriptor())) {
                                String label = keyboardLayoutListForInputDevice[i].getLabel();
                                keyboardLayoutForInputDevice.getSelectionCriteria();
                                this.mKeyboardInfoList.add(
                                        new NewKeyboardSettingsUtils.KeyboardInfo(
                                                displayName,
                                                label,
                                                inputMethodInfo,
                                                inputMethodSubtype));
                                break;
                            }
                            i++;
                        }
                    } else {
                        this.mKeyboardInfoList.add(
                                new NewKeyboardSettingsUtils.KeyboardInfo(
                                        displayName,
                                        this.mContext.getString(R.string.keyboard_default_layout),
                                        inputMethodInfo,
                                        inputMethodSubtype));
                    }
                }
            }
            boolean z2 = enabledInputMethodListAsUser.size() > 1;
            if (!this.mKeyboardInfoList.isEmpty()) {
                PreferenceCategory preferenceCategory = new PreferenceCategory(this.mContext);
                if (z2) {
                    Context context = this.mContext;
                    string =
                            context.getString(
                                    R.string.ime_label_title,
                                    inputMethodInfo.loadLabel(context.getPackageManager()));
                } else {
                    string = this.mContext.getString(R.string.enabled_locales_keyboard_layout);
                }
                preferenceCategory.setTitle(string);
                preferenceCategory.setKey(inputMethodInfo.getPackageName());
                preferenceScreen.addPreference(preferenceCategory);
                Collections.sort(this.mKeyboardInfoList, new AnonymousClass2());
                Iterator it2 = this.mKeyboardInfoList.iterator();
                while (it2.hasNext()) {
                    final NewKeyboardSettingsUtils.KeyboardInfo keyboardInfo =
                            (NewKeyboardSettingsUtils.KeyboardInfo) it2.next();
                    Preference preference = new Preference(this.mContext);
                    String locale = keyboardInfo.mInputMethodSubtype.getLocale();
                    if (locale != null
                            && !locale.isEmpty()
                            && (arrayList = this.mAutomataLanguageLocaleList) != null) {
                        Iterator it3 = arrayList.iterator();
                        while (it3.hasNext()) {
                            if (locale.equalsIgnoreCase((String) it3.next())) {
                                z = true;
                                break;
                            }
                        }
                    }
                    z = false;
                    preference.setKey(
                            keyboardInfo.mInputMethodInfo.getId()
                                    + "_"
                                    + keyboardInfo.mInputMethodSubtype.hashCode());
                    preference.setTitle(keyboardInfo.mSubtypeLabel);
                    preference.setSummary(z ? keyboardInfo.mSubtypeLabel : keyboardInfo.mLayout);
                    preference.setOnPreferenceClickListener(
                            new Preference
                                    .OnPreferenceClickListener() { // from class:
                                                                   // com.android.settings.inputmethod.NewKeyboardLayoutEnabledLocalesFragment$$ExternalSyntheticLambda0
                                @Override // androidx.preference.Preference.OnPreferenceClickListener
                                public final boolean onPreferenceClick(Preference preference2) {
                                    NewKeyboardLayoutEnabledLocalesFragment
                                            newKeyboardLayoutEnabledLocalesFragment =
                                                    NewKeyboardLayoutEnabledLocalesFragment.this;
                                    boolean z3 = z;
                                    NewKeyboardSettingsUtils.KeyboardInfo keyboardInfo2 =
                                            keyboardInfo;
                                    if (z3) {
                                        Toast.makeText(
                                                        newKeyboardLayoutEnabledLocalesFragment
                                                                .getContext(),
                                                        newKeyboardLayoutEnabledLocalesFragment
                                                                .getContext()
                                                                .getResources()
                                                                .getString(
                                                                        R.string
                                                                                .ime_physical_automata_keyboard_language,
                                                                        keyboardInfo2
                                                                                .mSubtypeLabel),
                                                        0)
                                                .show();
                                        return true;
                                    }
                                    newKeyboardLayoutEnabledLocalesFragment.getClass();
                                    CharSequence charSequence = keyboardInfo2.mSubtypeLabel;
                                    Parcelable parcelable =
                                            newKeyboardLayoutEnabledLocalesFragment
                                                    .mInputDeviceIdentifier;
                                    int i2 = newKeyboardLayoutEnabledLocalesFragment.mUserId;
                                    InputMethodInfo inputMethodInfo2 =
                                            keyboardInfo2.mInputMethodInfo;
                                    InputMethodSubtype inputMethodSubtype2 =
                                            keyboardInfo2.mInputMethodSubtype;
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable("input_device_identifier", parcelable);
                                    bundle.putParcelable("input_method_info", inputMethodInfo2);
                                    bundle.putParcelable(
                                            "input_method_subtype", inputMethodSubtype2);
                                    bundle.putInt(
                                            UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID,
                                            i2);
                                    bundle.putCharSequence(
                                            "keyboard_layout_picker_title", charSequence);
                                    SubSettingLauncher subSettingLauncher =
                                            new SubSettingLauncher(
                                                    newKeyboardLayoutEnabledLocalesFragment
                                                            .mContext);
                                    SubSettingLauncher.LaunchRequest launchRequest =
                                            subSettingLauncher.mLaunchRequest;
                                    launchRequest.mSourceMetricsCategory = 1959;
                                    launchRequest.mDestinationName =
                                            NewKeyboardLayoutPickerFragment.class.getName();
                                    launchRequest.mArguments = bundle;
                                    subSettingLauncher.launch();
                                    return true;
                                }
                            });
                    preferenceCategory.addPreference(preference);
                }
            }
        }
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceAdded(int i) {}
}

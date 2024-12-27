package com.android.settings.inputmethod;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.hardware.input.InputDeviceIdentifier;
import android.hardware.input.InputManager;
import android.hardware.input.InputSettings;
import android.hardware.input.KeyboardLayout;
import android.hardware.input.KeyboardLayoutSelectionResult;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.UserHandle;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.FeatureFlagUtils;
import android.view.InputDevice;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.internal.util.Preconditions;
import com.android.settings.R;
import com.android.settings.Settings;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PhysicalKeyboardFragment extends SettingsPreferenceFragment
        implements InputManager.InputDeviceListener,
                KeyboardLayoutDialogFragment.OnSetupKeyboardLayoutsListener {
    public final PhysicalKeyboardFragment$$ExternalSyntheticLambda0
            mAccessibilityBounceKeysSwitchPreferenceChangeListener;
    public final PhysicalKeyboardFragment$$ExternalSyntheticLambda0
            mAccessibilitySlowKeysSwitchPreferenceChangeListener;
    public final PhysicalKeyboardFragment$$ExternalSyntheticLambda0
            mAccessibilityStickyKeysSwitchPreferenceChangeListener;
    public InputDeviceIdentifier mAutoInputDeviceIdentifier;
    public InputManager mIm;
    public Intent mIntentWaitingForResult;
    public PreferenceCategory mKeyboardAssistanceCategory;
    public final PhysicalKeyboardFragment$$ExternalSyntheticLambda0
            mShowVirtualKeyboardSwitchPreferenceChangeListener;
    public static final Uri sVirtualKeyboardSettingsUri =
            Settings.Secure.getUriFor("show_ime_with_hard_keyboard");
    public static final Uri sAccessibilityBounceKeysUri =
            Settings.Secure.getUriFor("accessibility_bounce_keys");
    public static final Uri sAccessibilitySlowKeysUri =
            Settings.Secure.getUriFor("accessibility_slow_keys");
    public static final Uri sAccessibilityStickyKeysUri =
            Settings.Secure.getUriFor("accessibility_sticky_keys");
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass2();
    public final ArrayList mLastHardKeyboards = new ArrayList();
    public PreferenceCategory mKeyboardA11yCategory = null;
    public TwoStatePreference mShowVirtualKeyboardSwitch = null;
    public TwoStatePreference mAccessibilityBounceKeys = null;
    public TwoStatePreference mAccessibilitySlowKeys = null;
    public TwoStatePreference mAccessibilityStickyKeys = null;
    public final AnonymousClass1 mContentObserver =
            new ContentObserver(
                    new Handler(
                            true)) { // from class:
                                     // com.android.settings.inputmethod.PhysicalKeyboardFragment.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri) {
                    if (PhysicalKeyboardFragment.sVirtualKeyboardSettingsUri.equals(uri)) {
                        PhysicalKeyboardFragment.this.updateShowVirtualKeyboardSwitch$1();
                        return;
                    }
                    if (PhysicalKeyboardFragment.sAccessibilityBounceKeysUri.equals(uri)) {
                        PhysicalKeyboardFragment.this.updateAccessibilityBounceKeysSwitch();
                    } else if (PhysicalKeyboardFragment.sAccessibilitySlowKeysUri.equals(uri)) {
                        PhysicalKeyboardFragment.this.updateAccessibilitySlowKeysSwitch();
                    } else if (PhysicalKeyboardFragment.sAccessibilityStickyKeysUri.equals(uri)) {
                        PhysicalKeyboardFragment.this.updateAccessibilityStickyKeysSwitch();
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.inputmethod.PhysicalKeyboardFragment$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.physical_keyboard_settings;
            return Arrays.asList(searchIndexableResource);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return !((ArrayList) PhysicalKeyboardFragment.getHardKeyboards(context)).isEmpty();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class HardKeyboardDeviceInfo {
        public final String mBluetoothAddress;
        public final InputDeviceIdentifier mDeviceIdentifier;
        public final String mDeviceName;
        public final String mLayoutLabel;
        public final int mProductId;
        public final int mVendorId;

        public HardKeyboardDeviceInfo(
                String str,
                InputDeviceIdentifier inputDeviceIdentifier,
                String str2,
                String str3,
                int i,
                int i2) {
            this.mDeviceName = TextUtils.emptyIfNull(str);
            this.mDeviceIdentifier = inputDeviceIdentifier;
            this.mLayoutLabel = str2;
            this.mBluetoothAddress = str3;
            this.mVendorId = i;
            this.mProductId = i2;
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || !(obj instanceof HardKeyboardDeviceInfo)) {
                return false;
            }
            HardKeyboardDeviceInfo hardKeyboardDeviceInfo = (HardKeyboardDeviceInfo) obj;
            return TextUtils.equals(this.mDeviceName, hardKeyboardDeviceInfo.mDeviceName)
                    && Objects.equals(
                            this.mDeviceIdentifier, hardKeyboardDeviceInfo.mDeviceIdentifier)
                    && TextUtils.equals(this.mLayoutLabel, hardKeyboardDeviceInfo.mLayoutLabel)
                    && TextUtils.equals(
                            this.mBluetoothAddress, hardKeyboardDeviceInfo.mBluetoothAddress);
        }
    }

    public static void $r8$lambda$J4XxqnIbWDLeFXC5A2o7uVF1_0s(
            final PhysicalKeyboardFragment physicalKeyboardFragment, List list) {
        if (Objects.equals(physicalKeyboardFragment.mLastHardKeyboards, list)) {
            return;
        }
        physicalKeyboardFragment.mLastHardKeyboards.clear();
        physicalKeyboardFragment.mLastHardKeyboards.addAll(list);
        PreferenceScreen preferenceScreen = physicalKeyboardFragment.getPreferenceScreen();
        preferenceScreen.removeAll();
        PreferenceCategory preferenceCategory =
                new PreferenceCategory(physicalKeyboardFragment.getPrefContext());
        preferenceCategory.setTitle(R.string.builtin_keyboard_settings_title);
        preferenceCategory.setOrder(0);
        preferenceScreen.addPreference(preferenceCategory);
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            final HardKeyboardDeviceInfo hardKeyboardDeviceInfo =
                    (HardKeyboardDeviceInfo) it.next();
            Preference preference = new Preference(physicalKeyboardFragment.getPrefContext());
            preference.setTitle(hardKeyboardDeviceInfo.mDeviceName);
            Context context = physicalKeyboardFragment.getContext();
            int myUserId = UserHandle.myUserId();
            InputDeviceIdentifier inputDeviceIdentifier = hardKeyboardDeviceInfo.mDeviceIdentifier;
            InputMethodManager inputMethodManager =
                    (InputMethodManager) context.getSystemService(InputMethodManager.class);
            InputManager inputManager = (InputManager) context.getSystemService(InputManager.class);
            String str = null;
            if (inputMethodManager != null && inputManager != null) {
                InputMethodInfo currentInputMethodInfoAsUser =
                        inputMethodManager.getCurrentInputMethodInfoAsUser(UserHandle.of(myUserId));
                InputMethodSubtype currentInputMethodSubtype =
                        inputMethodManager.getCurrentInputMethodSubtype();
                KeyboardLayout[] keyboardLayoutListForInputDevice =
                        inputManager.getKeyboardLayoutListForInputDevice(
                                inputDeviceIdentifier,
                                myUserId,
                                currentInputMethodInfoAsUser,
                                currentInputMethodSubtype);
                KeyboardLayoutSelectionResult keyboardLayoutForInputDevice =
                        inputManager.getKeyboardLayoutForInputDevice(
                                inputDeviceIdentifier,
                                myUserId,
                                currentInputMethodInfoAsUser,
                                currentInputMethodSubtype);
                if (keyboardLayoutForInputDevice != null) {
                    int length = keyboardLayoutListForInputDevice.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        KeyboardLayout keyboardLayout = keyboardLayoutListForInputDevice[i];
                        if (keyboardLayout
                                .getDescriptor()
                                .equals(keyboardLayoutForInputDevice.getLayoutDescriptor())) {
                            str = keyboardLayout.getLabel();
                            break;
                        }
                        i++;
                    }
                }
            }
            if (str != null) {
                preference.setSummary(str);
            }
            preference.setOnPreferenceClickListener(
                    new Preference
                            .OnPreferenceClickListener() { // from class:
                                                           // com.android.settings.inputmethod.PhysicalKeyboardFragment$$ExternalSyntheticLambda7
                        @Override // androidx.preference.Preference.OnPreferenceClickListener
                        public final boolean onPreferenceClick(Preference preference2) {
                            Uri uri = PhysicalKeyboardFragment.sVirtualKeyboardSettingsUri;
                            PhysicalKeyboardFragment physicalKeyboardFragment2 =
                                    PhysicalKeyboardFragment.this;
                            physicalKeyboardFragment2.getClass();
                            physicalKeyboardFragment2.showEnabledLocalesKeyboardLayoutList$1(
                                    hardKeyboardDeviceInfo.mDeviceIdentifier);
                            return true;
                        }
                    });
            preferenceCategory.addPreference(preference);
            StringBuilder sb = new StringBuilder();
            String valueOf = String.valueOf(hardKeyboardDeviceInfo.mVendorId);
            String valueOf2 = String.valueOf(hardKeyboardDeviceInfo.mProductId);
            sb.append(valueOf);
            sb.append("-");
            sb.append(valueOf2);
            physicalKeyboardFragment.mMetricsFeatureProvider.action(
                    physicalKeyboardFragment.getContext(), 1839, sb.toString());
        }
        physicalKeyboardFragment.mKeyboardAssistanceCategory.setOrder(1);
        preferenceScreen.addPreference(physicalKeyboardFragment.mKeyboardAssistanceCategory);
        physicalKeyboardFragment.updateShowVirtualKeyboardSwitch$1();
        if (InputSettings.isAccessibilityBounceKeysFeatureEnabled()
                || InputSettings.isAccessibilityStickyKeysFeatureEnabled()
                || InputSettings.isAccessibilitySlowKeysFeatureFlagEnabled()) {
            PreferenceCategory preferenceCategory2 = physicalKeyboardFragment.mKeyboardA11yCategory;
            Objects.requireNonNull(preferenceCategory2);
            preferenceCategory2.setOrder(2);
            preferenceScreen.addPreference(physicalKeyboardFragment.mKeyboardA11yCategory);
            physicalKeyboardFragment.updateAccessibilityBounceKeysSwitch();
            physicalKeyboardFragment.updateAccessibilitySlowKeysSwitch();
            physicalKeyboardFragment.updateAccessibilityStickyKeysSwitch();
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settings.inputmethod.PhysicalKeyboardFragment$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settings.inputmethod.PhysicalKeyboardFragment$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.settings.inputmethod.PhysicalKeyboardFragment$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.settings.inputmethod.PhysicalKeyboardFragment$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.settings.inputmethod.PhysicalKeyboardFragment$1] */
    public PhysicalKeyboardFragment() {
        final int i = 0;
        this.mShowVirtualKeyboardSwitchPreferenceChangeListener =
                new Preference.OnPreferenceChangeListener(
                        this) { // from class:
                                // com.android.settings.inputmethod.PhysicalKeyboardFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ PhysicalKeyboardFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference, Object obj) {
                        PhysicalKeyboardFragment physicalKeyboardFragment = this.f$0;
                        switch (i) {
                            case 0:
                                Uri uri = PhysicalKeyboardFragment.sVirtualKeyboardSettingsUri;
                                ContentResolver contentResolver =
                                        physicalKeyboardFragment.getContentResolver();
                                Settings.Secure.putInt(
                                        contentResolver,
                                        "show_ime_with_hard_keyboard",
                                        ((Boolean) obj).booleanValue() ? 1 : 0);
                                contentResolver.notifyChange(
                                        Settings.Secure.getUriFor("show_ime_with_hard_keyboard"),
                                        (ContentObserver) null,
                                        NetworkAnalyticsConstants.DataPoints.FLAG_UID);
                                break;
                            case 1:
                                Uri uri2 = PhysicalKeyboardFragment.sVirtualKeyboardSettingsUri;
                                InputSettings.setAccessibilityBounceKeysThreshold(
                                        physicalKeyboardFragment.getContext(),
                                        ((Boolean) obj).booleanValue() ? 500 : 0);
                                break;
                            case 2:
                                Uri uri3 = PhysicalKeyboardFragment.sVirtualKeyboardSettingsUri;
                                InputSettings.setAccessibilitySlowKeysThreshold(
                                        physicalKeyboardFragment.getContext(),
                                        ((Boolean) obj).booleanValue() ? 500 : 0);
                                break;
                            default:
                                Uri uri4 = PhysicalKeyboardFragment.sVirtualKeyboardSettingsUri;
                                InputSettings.setAccessibilityStickyKeysEnabled(
                                        physicalKeyboardFragment.getContext(),
                                        ((Boolean) obj).booleanValue());
                                break;
                        }
                        return true;
                    }
                };
        final int i2 = 1;
        this.mAccessibilityBounceKeysSwitchPreferenceChangeListener =
                new Preference.OnPreferenceChangeListener(
                        this) { // from class:
                                // com.android.settings.inputmethod.PhysicalKeyboardFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ PhysicalKeyboardFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference, Object obj) {
                        PhysicalKeyboardFragment physicalKeyboardFragment = this.f$0;
                        switch (i2) {
                            case 0:
                                Uri uri = PhysicalKeyboardFragment.sVirtualKeyboardSettingsUri;
                                ContentResolver contentResolver =
                                        physicalKeyboardFragment.getContentResolver();
                                Settings.Secure.putInt(
                                        contentResolver,
                                        "show_ime_with_hard_keyboard",
                                        ((Boolean) obj).booleanValue() ? 1 : 0);
                                contentResolver.notifyChange(
                                        Settings.Secure.getUriFor("show_ime_with_hard_keyboard"),
                                        (ContentObserver) null,
                                        NetworkAnalyticsConstants.DataPoints.FLAG_UID);
                                break;
                            case 1:
                                Uri uri2 = PhysicalKeyboardFragment.sVirtualKeyboardSettingsUri;
                                InputSettings.setAccessibilityBounceKeysThreshold(
                                        physicalKeyboardFragment.getContext(),
                                        ((Boolean) obj).booleanValue() ? 500 : 0);
                                break;
                            case 2:
                                Uri uri3 = PhysicalKeyboardFragment.sVirtualKeyboardSettingsUri;
                                InputSettings.setAccessibilitySlowKeysThreshold(
                                        physicalKeyboardFragment.getContext(),
                                        ((Boolean) obj).booleanValue() ? 500 : 0);
                                break;
                            default:
                                Uri uri4 = PhysicalKeyboardFragment.sVirtualKeyboardSettingsUri;
                                InputSettings.setAccessibilityStickyKeysEnabled(
                                        physicalKeyboardFragment.getContext(),
                                        ((Boolean) obj).booleanValue());
                                break;
                        }
                        return true;
                    }
                };
        final int i3 = 2;
        this.mAccessibilitySlowKeysSwitchPreferenceChangeListener =
                new Preference.OnPreferenceChangeListener(
                        this) { // from class:
                                // com.android.settings.inputmethod.PhysicalKeyboardFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ PhysicalKeyboardFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference, Object obj) {
                        PhysicalKeyboardFragment physicalKeyboardFragment = this.f$0;
                        switch (i3) {
                            case 0:
                                Uri uri = PhysicalKeyboardFragment.sVirtualKeyboardSettingsUri;
                                ContentResolver contentResolver =
                                        physicalKeyboardFragment.getContentResolver();
                                Settings.Secure.putInt(
                                        contentResolver,
                                        "show_ime_with_hard_keyboard",
                                        ((Boolean) obj).booleanValue() ? 1 : 0);
                                contentResolver.notifyChange(
                                        Settings.Secure.getUriFor("show_ime_with_hard_keyboard"),
                                        (ContentObserver) null,
                                        NetworkAnalyticsConstants.DataPoints.FLAG_UID);
                                break;
                            case 1:
                                Uri uri2 = PhysicalKeyboardFragment.sVirtualKeyboardSettingsUri;
                                InputSettings.setAccessibilityBounceKeysThreshold(
                                        physicalKeyboardFragment.getContext(),
                                        ((Boolean) obj).booleanValue() ? 500 : 0);
                                break;
                            case 2:
                                Uri uri3 = PhysicalKeyboardFragment.sVirtualKeyboardSettingsUri;
                                InputSettings.setAccessibilitySlowKeysThreshold(
                                        physicalKeyboardFragment.getContext(),
                                        ((Boolean) obj).booleanValue() ? 500 : 0);
                                break;
                            default:
                                Uri uri4 = PhysicalKeyboardFragment.sVirtualKeyboardSettingsUri;
                                InputSettings.setAccessibilityStickyKeysEnabled(
                                        physicalKeyboardFragment.getContext(),
                                        ((Boolean) obj).booleanValue());
                                break;
                        }
                        return true;
                    }
                };
        final int i4 = 3;
        this.mAccessibilityStickyKeysSwitchPreferenceChangeListener =
                new Preference.OnPreferenceChangeListener(
                        this) { // from class:
                                // com.android.settings.inputmethod.PhysicalKeyboardFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ PhysicalKeyboardFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference, Object obj) {
                        PhysicalKeyboardFragment physicalKeyboardFragment = this.f$0;
                        switch (i4) {
                            case 0:
                                Uri uri = PhysicalKeyboardFragment.sVirtualKeyboardSettingsUri;
                                ContentResolver contentResolver =
                                        physicalKeyboardFragment.getContentResolver();
                                Settings.Secure.putInt(
                                        contentResolver,
                                        "show_ime_with_hard_keyboard",
                                        ((Boolean) obj).booleanValue() ? 1 : 0);
                                contentResolver.notifyChange(
                                        Settings.Secure.getUriFor("show_ime_with_hard_keyboard"),
                                        (ContentObserver) null,
                                        NetworkAnalyticsConstants.DataPoints.FLAG_UID);
                                break;
                            case 1:
                                Uri uri2 = PhysicalKeyboardFragment.sVirtualKeyboardSettingsUri;
                                InputSettings.setAccessibilityBounceKeysThreshold(
                                        physicalKeyboardFragment.getContext(),
                                        ((Boolean) obj).booleanValue() ? 500 : 0);
                                break;
                            case 2:
                                Uri uri3 = PhysicalKeyboardFragment.sVirtualKeyboardSettingsUri;
                                InputSettings.setAccessibilitySlowKeysThreshold(
                                        physicalKeyboardFragment.getContext(),
                                        ((Boolean) obj).booleanValue() ? 500 : 0);
                                break;
                            default:
                                Uri uri4 = PhysicalKeyboardFragment.sVirtualKeyboardSettingsUri;
                                InputSettings.setAccessibilityStickyKeysEnabled(
                                        physicalKeyboardFragment.getContext(),
                                        ((Boolean) obj).booleanValue());
                                break;
                        }
                        return true;
                    }
                };
    }

    public static List getHardKeyboards(Context context) {
        String string;
        ArrayList arrayList = new ArrayList();
        InputManager inputManager = (InputManager) context.getSystemService(InputManager.class);
        if (inputManager == null) {
            return new ArrayList();
        }
        for (int i : InputDevice.getDeviceIds()) {
            InputDevice device = InputDevice.getDevice(i);
            if (device != null && !device.isVirtual() && device.isFullKeyboard()) {
                String name = device.getName();
                InputDeviceIdentifier identifier = device.getIdentifier();
                String currentKeyboardLayoutForInputDevice =
                        inputManager.getCurrentKeyboardLayoutForInputDevice(device.getIdentifier());
                if (currentKeyboardLayoutForInputDevice == null) {
                    string = context.getString(R.string.keyboard_layout_default_label);
                } else {
                    KeyboardLayout keyboardLayout =
                            inputManager.getKeyboardLayout(currentKeyboardLayoutForInputDevice);
                    string =
                            keyboardLayout == null
                                    ? context.getString(R.string.keyboard_layout_default_label)
                                    : TextUtils.emptyIfNull(keyboardLayout.getLabel());
                }
                arrayList.add(
                        new HardKeyboardDeviceInfo(
                                name,
                                identifier,
                                string,
                                device.getBluetoothAddress(),
                                device.getVendorId(),
                                device.getProductId()));
            }
        }
        final Collator collator = Collator.getInstance();
        arrayList.sort(
                new Comparator() { // from class:
                                   // com.android.settings.inputmethod.PhysicalKeyboardFragment$$ExternalSyntheticLambda4
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        Collator collator2 = collator;
                        PhysicalKeyboardFragment.HardKeyboardDeviceInfo hardKeyboardDeviceInfo =
                                (PhysicalKeyboardFragment.HardKeyboardDeviceInfo) obj;
                        PhysicalKeyboardFragment.HardKeyboardDeviceInfo hardKeyboardDeviceInfo2 =
                                (PhysicalKeyboardFragment.HardKeyboardDeviceInfo) obj2;
                        Uri uri = PhysicalKeyboardFragment.sVirtualKeyboardSettingsUri;
                        int compare =
                                collator2.compare(
                                        hardKeyboardDeviceInfo.mDeviceName,
                                        hardKeyboardDeviceInfo2.mDeviceName);
                        if (compare != 0) {
                            return compare;
                        }
                        int compareTo =
                                hardKeyboardDeviceInfo
                                        .mDeviceIdentifier
                                        .getDescriptor()
                                        .compareTo(
                                                hardKeyboardDeviceInfo2.mDeviceIdentifier
                                                        .getDescriptor());
                        return compareTo != 0
                                ? compareTo
                                : collator2.compare(
                                        hardKeyboardDeviceInfo.mLayoutLabel,
                                        hardKeyboardDeviceInfo2.mLayoutLabel);
                    }
                });
        return arrayList;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return FileType.XMIND;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        Intent intent2 = this.mIntentWaitingForResult;
        if (intent2 != null) {
            InputDeviceIdentifier inputDeviceIdentifier =
                    (InputDeviceIdentifier)
                            intent2.getParcelableExtra(
                                    "input_device_identifier", InputDeviceIdentifier.class);
            this.mIntentWaitingForResult = null;
            KeyboardLayoutDialogFragment keyboardLayoutDialogFragment =
                    new KeyboardLayoutDialogFragment(inputDeviceIdentifier);
            keyboardLayoutDialogFragment.setTargetFragment(this, 0);
            keyboardLayoutDialogFragment.show(
                    getActivity().getSupportFragmentManager(), "keyboardLayout");
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        Activity activity = (Activity) Preconditions.checkNotNull(getActivity());
        addPreferencesFromResource(R.xml.physical_keyboard_settings);
        this.mIm =
                (InputManager)
                        Preconditions.checkNotNull(
                                (InputManager) activity.getSystemService(InputManager.class));
        PreferenceCategory preferenceCategory =
                (PreferenceCategory)
                        Preconditions.checkNotNull(
                                (PreferenceCategory) findPreference("keyboard_options_category"));
        this.mKeyboardAssistanceCategory = preferenceCategory;
        TwoStatePreference twoStatePreference =
                (TwoStatePreference)
                        preferenceCategory.findPreference("show_virtual_keyboard_switch");
        Objects.requireNonNull(twoStatePreference);
        this.mShowVirtualKeyboardSwitch = twoStatePreference;
        PreferenceCategory preferenceCategory2 =
                (PreferenceCategory) findPreference("keyboard_a11y_category");
        Objects.requireNonNull(preferenceCategory2);
        this.mKeyboardA11yCategory = preferenceCategory2;
        TwoStatePreference twoStatePreference2 =
                (TwoStatePreference)
                        preferenceCategory2.findPreference("accessibility_bounce_keys");
        Objects.requireNonNull(twoStatePreference2);
        this.mAccessibilityBounceKeys = twoStatePreference2;
        twoStatePreference2.setSummary(getContext().getString(R.string.bounce_keys_summary, 500));
        TwoStatePreference twoStatePreference3 =
                (TwoStatePreference)
                        this.mKeyboardA11yCategory.findPreference("accessibility_slow_keys");
        Objects.requireNonNull(twoStatePreference3);
        this.mAccessibilitySlowKeys = twoStatePreference3;
        twoStatePreference3.setSummary(getContext().getString(R.string.slow_keys_summary, 500));
        TwoStatePreference twoStatePreference4 =
                (TwoStatePreference)
                        this.mKeyboardA11yCategory.findPreference("accessibility_sticky_keys");
        Objects.requireNonNull(twoStatePreference4);
        this.mAccessibilityStickyKeys = twoStatePreference4;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        ((KeyboardSettingsFeatureProviderImpl)
                        featureFactoryImpl.keyboardSettingsFeatureProvider$delegate.getValue())
                .getClass();
        if (!FeatureFlagUtils.isEnabled(getContext(), "settings_new_keyboard_modifier_key")) {
            this.mKeyboardAssistanceCategory.removePreference(
                    findPreference("modifier_keys_settings"));
        }
        if (!InputSettings.isAccessibilityBounceKeysFeatureEnabled()) {
            this.mKeyboardA11yCategory.removePreference(this.mAccessibilityBounceKeys);
        }
        if (!InputSettings.isAccessibilitySlowKeysFeatureFlagEnabled()) {
            this.mKeyboardA11yCategory.removePreference(this.mAccessibilitySlowKeys);
        }
        if (!InputSettings.isAccessibilityStickyKeysFeatureEnabled()) {
            this.mKeyboardA11yCategory.removePreference(this.mAccessibilityStickyKeys);
        }
        InputDeviceIdentifier inputDeviceIdentifier =
                (InputDeviceIdentifier)
                        activity.getIntent()
                                .getParcelableExtra(
                                        "input_device_identifier", InputDeviceIdentifier.class);
        int intExtra =
                activity.getIntent()
                        .getIntExtra("com.android.settings.inputmethod.EXTRA_ENTRYPOINT", -1);
        if (intExtra != -1) {
            this.mMetricsFeatureProvider.action(getContext(), 1838, intExtra);
        }
        if (inputDeviceIdentifier != null) {
            this.mAutoInputDeviceIdentifier = inputDeviceIdentifier;
        }
        if ((bundle == null
                        || bundle.getParcelable("auto_selection", InputDeviceIdentifier.class)
                                == null)
                && inputDeviceIdentifier != null) {
            showEnabledLocalesKeyboardLayoutList$1(inputDeviceIdentifier);
        }
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceAdded(int i) {
        ThreadUtils.postOnBackgroundThread(
                new PhysicalKeyboardFragment$$ExternalSyntheticLambda5(this, getContext(), 0));
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceChanged(int i) {
        ThreadUtils.postOnBackgroundThread(
                new PhysicalKeyboardFragment$$ExternalSyntheticLambda5(this, getContext(), 0));
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceRemoved(int i) {
        ThreadUtils.postOnBackgroundThread(
                new PhysicalKeyboardFragment$$ExternalSyntheticLambda5(this, getContext(), 0));
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mLastHardKeyboards.clear();
        this.mIm.unregisterInputDeviceListener(this);
        TwoStatePreference twoStatePreference = this.mShowVirtualKeyboardSwitch;
        Objects.requireNonNull(twoStatePreference);
        twoStatePreference.setOnPreferenceChangeListener(null);
        TwoStatePreference twoStatePreference2 = this.mAccessibilityBounceKeys;
        Objects.requireNonNull(twoStatePreference2);
        twoStatePreference2.setOnPreferenceChangeListener(null);
        TwoStatePreference twoStatePreference3 = this.mAccessibilitySlowKeys;
        Objects.requireNonNull(twoStatePreference3);
        twoStatePreference3.setOnPreferenceChangeListener(null);
        TwoStatePreference twoStatePreference4 = this.mAccessibilityStickyKeys;
        Objects.requireNonNull(twoStatePreference4);
        twoStatePreference4.setOnPreferenceChangeListener(null);
        getActivity().getContentResolver().unregisterContentObserver(this.mContentObserver);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (!"keyboard_shortcuts_helper".equals(preference.getKey())) {
            return super.onPreferenceTreeClick(preference);
        }
        writePreferenceClickMetric(preference);
        getActivity().requestShowKeyboardShortcuts();
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mLastHardKeyboards.clear();
        ThreadUtils.postOnBackgroundThread(
                new PhysicalKeyboardFragment$$ExternalSyntheticLambda5(this, getContext(), 0));
        this.mIm.registerInputDeviceListener(this, null);
        TwoStatePreference twoStatePreference = this.mShowVirtualKeyboardSwitch;
        Objects.requireNonNull(twoStatePreference);
        twoStatePreference.setOnPreferenceChangeListener(
                this.mShowVirtualKeyboardSwitchPreferenceChangeListener);
        TwoStatePreference twoStatePreference2 = this.mAccessibilityBounceKeys;
        Objects.requireNonNull(twoStatePreference2);
        twoStatePreference2.setOnPreferenceChangeListener(
                this.mAccessibilityBounceKeysSwitchPreferenceChangeListener);
        TwoStatePreference twoStatePreference3 = this.mAccessibilitySlowKeys;
        Objects.requireNonNull(twoStatePreference3);
        twoStatePreference3.setOnPreferenceChangeListener(
                this.mAccessibilitySlowKeysSwitchPreferenceChangeListener);
        TwoStatePreference twoStatePreference4 = this.mAccessibilityStickyKeys;
        Objects.requireNonNull(twoStatePreference4);
        twoStatePreference4.setOnPreferenceChangeListener(
                this.mAccessibilityStickyKeysSwitchPreferenceChangeListener);
        getActivity().getContentResolver().unregisterContentObserver(this.mContentObserver);
        ContentResolver contentResolver = getActivity().getContentResolver();
        contentResolver.registerContentObserver(
                sVirtualKeyboardSettingsUri, false, this.mContentObserver, UserHandle.myUserId());
        if (InputSettings.isAccessibilityBounceKeysFeatureEnabled()) {
            contentResolver.registerContentObserver(
                    sAccessibilityBounceKeysUri,
                    false,
                    this.mContentObserver,
                    UserHandle.myUserId());
        }
        if (InputSettings.isAccessibilitySlowKeysFeatureFlagEnabled()) {
            contentResolver.registerContentObserver(
                    sAccessibilitySlowKeysUri, false, this.mContentObserver, UserHandle.myUserId());
        }
        if (InputSettings.isAccessibilityStickyKeysFeatureEnabled()) {
            contentResolver.registerContentObserver(
                    sAccessibilityStickyKeysUri,
                    false,
                    this.mContentObserver,
                    UserHandle.myUserId());
        }
        updateShowVirtualKeyboardSwitch$1();
        updateAccessibilityBounceKeysSwitch();
        updateAccessibilitySlowKeysSwitch();
        updateAccessibilityStickyKeysSwitch();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable("auto_selection", this.mAutoInputDeviceIdentifier);
        super.onSaveInstanceState(bundle);
    }

    @Override // com.android.settings.inputmethod.KeyboardLayoutDialogFragment.OnSetupKeyboardLayoutsListener
    public final void onSetupKeyboardLayouts(InputDeviceIdentifier inputDeviceIdentifier) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setClass(getActivity(), Settings.KeyboardLayoutPickerActivity.class);
        intent.putExtra("input_device_identifier", (Parcelable) inputDeviceIdentifier);
        this.mIntentWaitingForResult = intent;
        startActivityForResult(intent, 0);
    }

    public final void showEnabledLocalesKeyboardLayoutList$1(
            InputDeviceIdentifier inputDeviceIdentifier) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("input_device_identifier", inputDeviceIdentifier);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getContext());
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mSourceMetricsCategory = FileType.XMIND;
        launchRequest.mDestinationName = NewKeyboardLayoutEnabledLocalesFragment.class.getName();
        launchRequest.mArguments = bundle;
        subSettingLauncher.launch();
    }

    public final void updateAccessibilityBounceKeysSwitch() {
        if (InputSettings.isAccessibilityBounceKeysFeatureEnabled()) {
            TwoStatePreference twoStatePreference = this.mAccessibilityBounceKeys;
            Objects.requireNonNull(twoStatePreference);
            twoStatePreference.setChecked(
                    InputSettings.isAccessibilityBounceKeysEnabled(getContext()));
        }
    }

    public final void updateAccessibilitySlowKeysSwitch() {
        if (InputSettings.isAccessibilitySlowKeysFeatureFlagEnabled()) {
            TwoStatePreference twoStatePreference = this.mAccessibilitySlowKeys;
            Objects.requireNonNull(twoStatePreference);
            twoStatePreference.setChecked(
                    InputSettings.isAccessibilitySlowKeysEnabled(getContext()));
        }
    }

    public final void updateAccessibilityStickyKeysSwitch() {
        if (InputSettings.isAccessibilityStickyKeysFeatureEnabled()) {
            TwoStatePreference twoStatePreference = this.mAccessibilityStickyKeys;
            Objects.requireNonNull(twoStatePreference);
            twoStatePreference.setChecked(
                    InputSettings.isAccessibilityStickyKeysEnabled(getContext()));
        }
    }

    public final void updateShowVirtualKeyboardSwitch$1() {
        TwoStatePreference twoStatePreference = this.mShowVirtualKeyboardSwitch;
        Objects.requireNonNull(twoStatePreference);
        twoStatePreference.setChecked(
                Settings.Secure.getInt(getContentResolver(), "show_ime_with_hard_keyboard", 0)
                        != 0);
    }
}

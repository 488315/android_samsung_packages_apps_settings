package com.samsung.android.settings.inputmethod;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.hardware.input.InputDeviceIdentifier;
import android.hardware.input.InputManager;
import android.hardware.input.InputManagerGlobal;
import android.hardware.input.KeyboardLayout;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.UserHandle;
import android.provider.SearchIndexableData;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.FeatureFlagUtils;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.SecPreferenceCategory;
import androidx.preference.SecPreferenceScreen;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreference;

import com.android.internal.util.Preconditions;
import com.android.settings.R;
import com.android.settings.Settings;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.inputmethod.KeyboardLayoutDialogFragment;
import com.android.settings.inputmethod.NewKeyboardLayoutEnabledLocalesFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.SettingsPreferenceFragmentLinkData;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.core.SecSettingsBaseActivity;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecRelativeLinkView;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class PhysicalKeyboardFragment extends SettingsPreferenceFragment
        implements InputManager.InputDeviceListener,
                KeyboardLayoutDialogFragment.OnSetupKeyboardLayoutsListener,
                SecSettingsBaseActivity.onKeyEventListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass3();
    public InputDeviceIdentifier mAutoInputDeviceIdentifier;
    public BluetoothAdapter mBluetoothAdapter;
    public SecPreferenceScreen mChangeLanguageShortcut;
    public InputManager mIm;
    public InputMethodManager mImm;
    public Intent mIntentWaitingForResult;
    public SecPreferenceCategory mKeyboardAssistanceCategory;
    public PhysicalKeyboardFragment$$ExternalSyntheticLambda2
            mOnWirelessKeyboardShareChangedListener;
    public AnonymousClass1 mSettingsObserver;
    public SecPreferenceScreen mShareKeyboard;
    public SecSwitchPreference mShowVirtualKeyboardSwitch;
    public SecSwitchPreference mShowVirtualKeyboardSwitchForDeX;
    public WirelessKeyboardShareDBUtil mWirelessKeyboardShareDBUtil;
    public AnonymousClass4 mWirelessKeyboardShareUiHandler;
    public SecPreferenceCategory mWirelessKeyboardSharing;
    public final ArrayList mLastHardKeyboards = new ArrayList();
    public SecRelativeLinkView mRelativeLinkView = null;
    public final AnonymousClass1 mShowVirtualKeyboardSwitchForDexObserver =
            new AnonymousClass1(this, new Handler(true), 0);
    public final PhysicalKeyboardFragment$$ExternalSyntheticLambda1
            mShowVirtualKeyboardSwitchPreferenceChangeListener =
                    new Preference
                            .OnPreferenceChangeListener() { // from class:
                                                            // com.samsung.android.settings.inputmethod.PhysicalKeyboardFragment$$ExternalSyntheticLambda1
                        @Override // androidx.preference.Preference.OnPreferenceChangeListener
                        public final boolean onPreferenceChange(Preference preference, Object obj) {
                            BaseSearchIndexProvider baseSearchIndexProvider =
                                    PhysicalKeyboardFragment.SEARCH_INDEX_DATA_PROVIDER;
                            PhysicalKeyboardFragment physicalKeyboardFragment =
                                    PhysicalKeyboardFragment.this;
                            physicalKeyboardFragment.getClass();
                            if ("show_virtual_keyboard_switch_for_dex"
                                    .equals(preference.getKey())) {
                                Utils.setDeXSettings(
                                        physicalKeyboardFragment.getContentResolver(),
                                        "keyboard_dex",
                                        String.valueOf(((Boolean) obj).booleanValue() ? 1 : 0));
                                return true;
                            }
                            Boolean bool = (Boolean) obj;
                            Settings.Secure.putInt(
                                    physicalKeyboardFragment.getContentResolver(),
                                    "show_ime_with_hard_keyboard",
                                    bool.booleanValue() ? 1 : 0);
                            LoggingHelper.insertEventLogging(
                                    FileType.XMIND, 8025, bool.booleanValue());
                            return true;
                        }
                    };
    public final AnonymousClass1 mContentObserver = new AnonymousClass1(this, new Handler(true), 1);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.inputmethod.PhysicalKeyboardFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends ContentObserver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ PhysicalKeyboardFragment this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public /* synthetic */ AnonymousClass1(
                PhysicalKeyboardFragment physicalKeyboardFragment, Handler handler, int i) {
            super(handler);
            this.$r8$classId = i;
            this.this$0 = physicalKeyboardFragment;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            switch (this.$r8$classId) {
                case 0:
                    PhysicalKeyboardFragment physicalKeyboardFragment = this.this$0;
                    BaseSearchIndexProvider baseSearchIndexProvider =
                            PhysicalKeyboardFragment.SEARCH_INDEX_DATA_PROVIDER;
                    SecSwitchPreference secSwitchPreference =
                            physicalKeyboardFragment.mShowVirtualKeyboardSwitchForDeX;
                    if (secSwitchPreference != null) {
                        secSwitchPreference.setChecked(
                                Utils.getStringFromDeXSettings(
                                                physicalKeyboardFragment.getContentResolver(),
                                                "keyboard_dex",
                                                DATA.DM_FIELD_INDEX.PCSCF_DOMAIN)
                                        .equalsIgnoreCase("1"));
                        break;
                    }
                    break;
                case 1:
                    PhysicalKeyboardFragment physicalKeyboardFragment2 = this.this$0;
                    BaseSearchIndexProvider baseSearchIndexProvider2 =
                            PhysicalKeyboardFragment.SEARCH_INDEX_DATA_PROVIDER;
                    physicalKeyboardFragment2.updateShowVirtualKeyboardSwitch();
                    break;
                default:
                    super.onChange(z);
                    int changedType = this.this$0.mWirelessKeyboardShareDBUtil.getChangedType();
                    ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                            changedType, "DB changed type is ", "PhysicalKeyboardFragment");
                    Message obtainMessage = obtainMessage(0);
                    if (changedType == 0
                            || changedType == 1
                            || changedType == 2
                            || changedType == 4
                            || changedType == 6
                            || changedType == 7) {
                        sendMessage(obtainMessage);
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.inputmethod.PhysicalKeyboardFragment$3, reason: invalid class name */
    public final class AnonymousClass3 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getDynamicRawDataToIndex(Context context) {
            super.getDynamicRawDataToIndex(context);
            ArrayList arrayList = new ArrayList();
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            if (!PhysicalKeyboardFragment.isDesktopEnabled(context)
                    || Utils.isNewDexMode(context)) {
                searchIndexableRaw.title = String.valueOf(R.string.show_ime);
                ((SearchIndexableData) searchIndexableRaw).key = "show_virtual_keyboard_switch";
            } else {
                searchIndexableRaw.title = String.valueOf(R.string.show_virtual_keyboard_for_dex);
                ((SearchIndexableData) searchIndexableRaw).key =
                        "show_virtual_keyboard_switch_for_dex";
            }
            searchIndexableRaw.screenTitle =
                    context.getResources().getString(R.string.physical_keyboard_title);
            arrayList.add(searchIndexableRaw);
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (!PhysicalKeyboardFragment.isDesktopEnabled(context)
                    || Utils.isNewDexMode(context)) {
                ((ArrayList) nonIndexableKeys).add("show_virtual_keyboard_switch_for_dex");
            } else {
                ((ArrayList) nonIndexableKeys).add("show_virtual_keyboard_switch");
            }
            if (Rune.isSamsungDexOnPCMode(context)) {
                ((ArrayList) nonIndexableKeys).add("show_virtual_keyboard_switch");
            }
            if (!Rune.IFW_WIRELESS_KEYBOARD_MOUSE_SHARE
                    || !PhysicalKeyboardFragment.isExistPogoKeyboard(context)) {
                ((ArrayList) nonIndexableKeys).add("share_keyboard");
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.sec_physical_keyboard_settings;
            return Arrays.asList(searchIndexableResource);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class HardKeyboardDeviceInfo {
        public final String mBluetoothAddress;
        public final InputDeviceIdentifier mDeviceIdentifier;
        public final String mDeviceName;
        public final String mLayoutLabel;

        public HardKeyboardDeviceInfo(
                String str, InputDeviceIdentifier inputDeviceIdentifier, String str2, String str3) {
            this.mDeviceName = TextUtils.emptyIfNull(str);
            this.mDeviceIdentifier = inputDeviceIdentifier;
            this.mLayoutLabel = str2;
            this.mBluetoothAddress = str3;
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

    public static boolean isDesktopEnabled(Context context) {
        return Rune.supportDesktopMode() && Rune.isSamsungDexMode(context);
    }

    public static boolean isExistPogoKeyboard(Context context) {
        if (context == null) {
            return false;
        }
        InputManager inputManager = (InputManager) context.getSystemService(InputManager.class);
        for (int i : InputDevice.getDeviceIds()) {
            InputDevice device = InputDevice.getDevice(i);
            if (device != null && !device.isVirtual() && device.isFullKeyboard()) {
                String name = device.getName();
                InputDeviceIdentifier identifier = device.getIdentifier();
                String currentKeyboardLayoutForInputDevice =
                        inputManager.getCurrentKeyboardLayoutForInputDevice(device.getIdentifier());
                if (currentKeyboardLayoutForInputDevice == null) {
                    context.getString(R.string.keyboard_layout_default_label);
                } else {
                    KeyboardLayout keyboardLayout =
                            inputManager.getKeyboardLayout(currentKeyboardLayoutForInputDevice);
                    if (keyboardLayout == null) {
                        context.getString(R.string.keyboard_layout_default_label);
                    } else {
                        TextUtils.emptyIfNull(keyboardLayout.getLabel());
                    }
                }
                device.getBluetoothAddress();
                String emptyIfNull = TextUtils.emptyIfNull(name);
                int vendorId = identifier.getVendorId();
                int productId = identifier.getProductId();
                if ((vendorId == 1256 && productId == 41013)
                        || "Tab S3 Book Cover Keyboard".equals(emptyIfNull)) {
                    return true;
                }
            }
        }
        return false;
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
            InputDeviceIdentifier parcelableExtra =
                    intent2.getParcelableExtra("input_device_identifier");
            this.mIntentWaitingForResult = null;
            KeyboardLayoutDialogFragment keyboardLayoutDialogFragment =
                    new KeyboardLayoutDialogFragment(parcelableExtra);
            keyboardLayoutDialogFragment.setTargetFragment(this, 0);
            keyboardLayoutDialogFragment.show(
                    getActivity().getSupportFragmentManager(), "keyboardLayout");
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        SecSwitchPreference secSwitchPreference;
        Activity activity = (Activity) Preconditions.checkNotNull(getActivity());
        addPreferencesFromResource(R.xml.sec_physical_keyboard_settings);
        this.mIm =
                (InputManager)
                        Preconditions.checkNotNull(
                                (InputManager) activity.getSystemService(InputManager.class));
        this.mImm =
                (InputMethodManager)
                        Preconditions.checkNotNull(
                                (InputMethodManager)
                                        activity.getSystemService(InputMethodManager.class));
        SecPreferenceCategory secPreferenceCategory =
                (SecPreferenceCategory)
                        Preconditions.checkNotNull(
                                (SecPreferenceCategory)
                                        findPreference("keyboard_assistance_category"));
        this.mKeyboardAssistanceCategory = secPreferenceCategory;
        this.mShowVirtualKeyboardSwitch =
                (SecSwitchPreference)
                        Preconditions.checkNotNull(
                                (SecSwitchPreference)
                                        secPreferenceCategory.findPreference(
                                                "show_virtual_keyboard_switch"));
        this.mChangeLanguageShortcut =
                (SecPreferenceScreen) findPreference("change_language_shortcut");
        if (!FeatureFlagUtils.isEnabled(getContext(), "settings_new_keyboard_modifier_key")) {
            this.mKeyboardAssistanceCategory.removePreference(
                    findPreference("modifier_keys_settings"));
        }
        InputDeviceIdentifier inputDeviceIdentifier =
                (InputDeviceIdentifier)
                        activity.getIntent().getParcelableExtra("input_device_identifier");
        if (inputDeviceIdentifier != null) {
            this.mAutoInputDeviceIdentifier = inputDeviceIdentifier;
        }
        if ((bundle == null || bundle.getParcelable("auto_selection") == null)
                && inputDeviceIdentifier != null) {
            showEnabledLocalesKeyboardLayoutList(inputDeviceIdentifier);
        }
        if (Rune.IFW_WIRELESS_KEYBOARD_MOUSE_SHARE) {
            this.mWirelessKeyboardSharing =
                    (SecPreferenceCategory)
                            Preconditions.checkNotNull(
                                    (SecPreferenceCategory)
                                            findPreference("wireless_keyboard_sharing"));
            FragmentActivity activity2 = getActivity();
            if (WirelessKeyboardShareDBUtil.mWirelessKeyboardShareDBUtil == null) {
                WirelessKeyboardShareDBUtil.mWirelessKeyboardShareDBUtil =
                        new WirelessKeyboardShareDBUtil(activity2);
            }
            this.mWirelessKeyboardShareDBUtil =
                    WirelessKeyboardShareDBUtil.mWirelessKeyboardShareDBUtil;
        }
        this.mShowVirtualKeyboardSwitchForDeX =
                (SecSwitchPreference)
                        this.mKeyboardAssistanceCategory.findPreference(
                                "show_virtual_keyboard_switch_for_dex");
        if (!isDesktopEnabled(activity) || Utils.isNewDexMode(activity)) {
            SecSwitchPreference secSwitchPreference2 = this.mShowVirtualKeyboardSwitchForDeX;
            if (secSwitchPreference2 != null) {
                this.mKeyboardAssistanceCategory.removePreference(secSwitchPreference2);
            }
        } else {
            SecSwitchPreference secSwitchPreference3 = this.mShowVirtualKeyboardSwitch;
            if (secSwitchPreference3 != null) {
                this.mKeyboardAssistanceCategory.removePreference(secSwitchPreference3);
            }
        }
        if (!Rune.isSamsungDexOnPCMode(activity)
                || (secSwitchPreference = this.mShowVirtualKeyboardSwitch) == null) {
            return;
        }
        this.mKeyboardAssistanceCategory.removePreference(secSwitchPreference);
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceAdded(int i) {
        scheduleUpdateHardKeyboards();
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceChanged(int i) {
        scheduleUpdateHardKeyboards();
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceRemoved(int i) {
        scheduleUpdateHardKeyboards();
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity.onKeyEventListener
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        Log.d("PhysicalKeyboardFragment", "onKeyDown: " + keyEvent.getKeyCode() + " " + i);
        if (i != 204) {
            return false;
        }
        scheduleUpdateHardKeyboards();
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mLastHardKeyboards.clear();
        this.mIm.unregisterInputDeviceListener(this);
        this.mShowVirtualKeyboardSwitch.setOnPreferenceChangeListener(null);
        getActivity().getContentResolver().unregisterContentObserver(this.mContentObserver);
        if (Rune.IFW_WIRELESS_KEYBOARD_MOUSE_SHARE) {
            WirelessKeyboardShareDBUtil wirelessKeyboardShareDBUtil =
                    this.mWirelessKeyboardShareDBUtil;
            if (wirelessKeyboardShareDBUtil != null) {
                wirelessKeyboardShareDBUtil.parsing(false);
            }
            if (this.mOnWirelessKeyboardShareChangedListener != null) {
                InputManagerGlobal.getInstance()
                        .unregisterOnWirelessKeyboardShareChangedListener(
                                this.mOnWirelessKeyboardShareChangedListener);
                this.mOnWirelessKeyboardShareChangedListener = null;
            }
            if (this.mSettingsObserver != null) {
                Log.d("PhysicalKeyboardFragment", "unregisterSettingsObserver");
                AnonymousClass1 anonymousClass1 = this.mSettingsObserver;
                anonymousClass1
                        .this$0
                        .getActivity()
                        .getContentResolver()
                        .unregisterContentObserver(anonymousClass1);
                this.mSettingsObserver = null;
            }
        }
        SecSwitchPreference secSwitchPreference = this.mShowVirtualKeyboardSwitchForDeX;
        if (secSwitchPreference == null || !secSwitchPreference.isShown()) {
            return;
        }
        this.mShowVirtualKeyboardSwitchForDeX.setOnPreferenceChangeListener(null);
        getActivity()
                .getContentResolver()
                .unregisterContentObserver(this.mShowVirtualKeyboardSwitchForDexObserver);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (!"keyboard_shortcuts_helper".equals(preference.getKey())) {
            return super.onPreferenceTreeClick(preference);
        }
        writePreferenceClickMetric(preference);
        LoggingHelper.insertEventLogging(FileType.XMIND, 8026);
        Log.d("PhysicalKeyboardFragment", "toggleKeyboardShortcutsMenu");
        getActivity().requestShowKeyboardShortcuts();
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        int i;
        super.onResume();
        ((SettingsActivity) getActivity()).mOnKeyEventListener = this;
        this.mLastHardKeyboards.clear();
        scheduleUpdateHardKeyboards();
        this.mIm.registerInputDeviceListener(this, null);
        this.mShowVirtualKeyboardSwitch.setOnPreferenceChangeListener(
                this.mShowVirtualKeyboardSwitchPreferenceChangeListener);
        getActivity().getContentResolver().unregisterContentObserver(this.mContentObserver);
        getActivity()
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("show_ime_with_hard_keyboard"),
                        false,
                        this.mContentObserver,
                        UserHandle.myUserId());
        updateShowVirtualKeyboardSwitch();
        SecPreferenceScreen secPreferenceScreen = this.mChangeLanguageShortcut;
        FragmentActivity activity = getActivity();
        StringBuilder sb = new StringBuilder();
        String string = activity.getResources().getString(R.string.comma);
        if (EnableCombinationKeyHelper.isEnableCombinationKey(activity, 1)) {
            sb.append(activity.getResources().getString(R.string.language_shortcut_shift_space));
            i = 1;
        } else {
            i = 0;
        }
        if (EnableCombinationKeyHelper.isEnableCombinationKey(activity, 2)) {
            if (i != 0) {
                sb.append(string);
                sb.append(" ");
            }
            sb.append(activity.getResources().getString(R.string.language_shortcut_control_space));
            i++;
        }
        if (EnableCombinationKeyHelper.isEnableCombinationKey(activity, 4)) {
            if (i != 0) {
                sb.append(string);
                sb.append(" ");
            }
            sb.append(activity.getResources().getString(R.string.language_shortcut_leftalt_shift));
        }
        secPreferenceScreen.setSummary(sb.toString());
        SecPreferenceScreen secPreferenceScreen2 = this.mChangeLanguageShortcut;
        secPreferenceScreen2.getClass();
        SecPreferenceUtils.applySummaryColor(secPreferenceScreen2, true);
        SecSwitchPreference secSwitchPreference = this.mShowVirtualKeyboardSwitchForDeX;
        if (secSwitchPreference != null && secSwitchPreference.isShown()) {
            this.mShowVirtualKeyboardSwitchForDeX.setOnPreferenceChangeListener(
                    this.mShowVirtualKeyboardSwitchPreferenceChangeListener);
            getActivity()
                    .getContentResolver()
                    .registerContentObserver(
                            Uri.parse(Utils.DEX_SETTINGS_URI.toString() + "settings/keyboard_dex"),
                            false,
                            this.mShowVirtualKeyboardSwitchForDexObserver);
            SecSwitchPreference secSwitchPreference2 = this.mShowVirtualKeyboardSwitchForDeX;
            if (secSwitchPreference2 != null) {
                secSwitchPreference2.setChecked(
                        Utils.getStringFromDeXSettings(
                                        getContentResolver(),
                                        "keyboard_dex",
                                        DATA.DM_FIELD_INDEX.PCSCF_DOMAIN)
                                .equalsIgnoreCase("1"));
            }
        }
        if (Rune.IFW_WIRELESS_KEYBOARD_MOUSE_SHARE) {
            boolean isExistPogoKeyboard = isExistPogoKeyboard(getContext());
            if (isExistPogoKeyboard) {
                if (this.mOnWirelessKeyboardShareChangedListener == null) {
                    this.mOnWirelessKeyboardShareChangedListener =
                            new PhysicalKeyboardFragment$$ExternalSyntheticLambda2(this);
                    InputManagerGlobal.getInstance()
                            .registerOnWirelessKeyboardShareChangedListener(
                                    this.mOnWirelessKeyboardShareChangedListener, (Handler) null);
                }
                registerSettingsObserver$1();
            }
            SecPreferenceCategory secPreferenceCategory = this.mWirelessKeyboardSharing;
            if (secPreferenceCategory != null) {
                secPreferenceCategory.setVisible(isExistPogoKeyboard);
                this.mWirelessKeyboardSharing.getClass();
            }
        }
        setRelativeLinkView();
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

    public final void registerSettingsObserver$1() {
        if (this.mSettingsObserver != null) {
            return;
        }
        Log.d("PhysicalKeyboardFragment", "registerSettingsObserver");
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this, new Handler(), 2);
        this.mSettingsObserver = anonymousClass1;
        getActivity()
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("wireless_keyboard_setting_repository"),
                        true,
                        anonymousClass1,
                        0);
    }

    public final void scheduleUpdateHardKeyboards() {
        ThreadUtils.postOnBackgroundThread(
                new PhysicalKeyboardFragment$$ExternalSyntheticLambda0(this, getContext(), 0));
    }

    public final void setRelativeLinkView() {
        getActivity();
        if (Rune.supportRelativeLink() && this.mRelativeLinkView == null) {
            this.mRelativeLinkView = new SecRelativeLinkView(getActivity());
            String permissionControllerPackageName =
                    getActivity().getPackageManager().getPermissionControllerPackageName();
            SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData =
                    new SettingsPreferenceFragmentLinkData();
            Intent putExtra =
                    permissionControllerPackageName != null
                            ? new Intent("android.intent.action.MANAGE_DEFAULT_APP")
                                    .setPackage(permissionControllerPackageName)
                                    .putExtra(
                                            "android.intent.extra.ROLE_NAME",
                                            "android.app.role.ASSISTANT")
                            : null;
            if (putExtra != null) {
                settingsPreferenceFragmentLinkData.flowId = "8027";
                settingsPreferenceFragmentLinkData.callerMetric = FileType.XMIND;
                putExtra.putExtra(":settings:show_fragment_args", new Bundle());
                settingsPreferenceFragmentLinkData.intent = putExtra;
                settingsPreferenceFragmentLinkData.titleRes = R.string.default_assist_title;
                this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData);
            }
            this.mRelativeLinkView.create(this);
        }
    }

    public final void showEnabledLocalesKeyboardLayoutList(
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

    public final void syncStatus() {
        try {
            String[] hostList = this.mWirelessKeyboardShareDBUtil.getHostList();
            for (int i = 0; i < 3; i++) {
                String loadByString = this.mWirelessKeyboardShareDBUtil.loadByString();
                if (!hostList[i].isEmpty() && hostList[i].equals(loadByString)) {
                    String name = this.mBluetoothAdapter.getRemoteDevice(loadByString).getName();
                    SecPreferenceScreen secPreferenceScreen = this.mShareKeyboard;
                    secPreferenceScreen.getClass();
                    SecPreferenceUtils.applySummaryColor(secPreferenceScreen, true);
                    this.mShareKeyboard.setSummary(name);
                    return;
                }
            }
        } catch (IllegalArgumentException | IllegalStateException unused) {
        }
        SecPreferenceScreen secPreferenceScreen2 = this.mShareKeyboard;
        secPreferenceScreen2.getClass();
        SecPreferenceUtils.applySummaryColor(secPreferenceScreen2, false);
        this.mShareKeyboard.setSummary(R.string.not_connected);
    }

    public final void updateShowVirtualKeyboardSwitch() {
        SecSwitchPreference secSwitchPreference = this.mShowVirtualKeyboardSwitch;
        if (secSwitchPreference != null) {
            secSwitchPreference.setChecked(
                    Settings.Secure.getInt(getContentResolver(), "show_ime_with_hard_keyboard", 0)
                            != 0);
        }
    }
}

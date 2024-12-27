package com.android.settings.connecteddevice.stylus;

import android.app.Dialog;
import android.app.role.RoleManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.InputDevice;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settings.R;
import com.android.settings.dashboard.profileselector.UserAdapter;
import com.android.settingslib.PrimarySwitchPreference;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class StylusDevicesController extends AbstractPreferenceController
        implements Preference.OnPreferenceClickListener,
                Preference.OnPreferenceChangeListener,
                LifecycleObserver,
                OnResume {
    static final String KEY_DEFAULT_NOTES = "default_notes";
    static final String KEY_HANDWRITING = "handwriting_switch";
    static final String KEY_IGNORE_BUTTON = "ignore_button";
    static final String KEY_SHOW_STYLUS_POINTER_ICON = "show_stylus_pointer_icon";
    static final String KEY_STYLUS = "device_stylus";
    public final CachedBluetoothDevice mCachedBluetoothDevice;
    Dialog mDialog;
    public final InputDevice mInputDevice;
    PreferenceCategory mPreferencesContainer;

    public static /* synthetic */ void $r8$lambda$iRDRvRt7NTgcqUylLmTihS0usR4(
            StylusDevicesController stylusDevicesController, Intent intent, List list, int i) {
        stylusDevicesController.getClass();
        intent.putExtra("android.intent.extra.USER", (Parcelable) list.get(i));
        Settings.Secure.putInt(
                stylusDevicesController.mContext.getContentResolver(),
                "default_note_task_profile",
                ((UserHandle) list.get(i)).getIdentifier());
        stylusDevicesController.mContext.startActivity(intent);
        stylusDevicesController.mDialog.dismiss();
    }

    public StylusDevicesController(
            Context context,
            InputDevice inputDevice,
            CachedBluetoothDevice cachedBluetoothDevice,
            Lifecycle lifecycle) {
        super(context);
        this.mInputDevice = inputDevice;
        this.mCachedBluetoothDevice = cachedBluetoothDevice;
        lifecycle.addObserver(this);
    }

    public UserAdapter.OnClickListener createProfileDialogClickCallback(
            final Intent intent, final List<UserHandle> list) {
        return new UserAdapter
                .OnClickListener() { // from class:
                                     // com.android.settings.connecteddevice.stylus.StylusDevicesController$$ExternalSyntheticLambda0
            @Override // com.android.settings.dashboard.profileselector.UserAdapter.OnClickListener
            public final void onClick(int i) {
                StylusDevicesController.$r8$lambda$iRDRvRt7NTgcqUylLmTihS0usR4(
                        StylusDevicesController.this, intent, list, i);
            }
        };
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        this.mPreferencesContainer =
                (PreferenceCategory) preferenceScreen.findPreference(KEY_STYLUS);
        super.displayPreference(preferenceScreen);
        refresh$1();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return KEY_STYLUS;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        InputDevice inputDevice = this.mInputDevice;
        if (inputDevice != null && inputDevice.supportsSource(16386)) {
            return true;
        }
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedBluetoothDevice;
        if (cachedBluetoothDevice != null) {
            return TextUtils.equals(
                    BluetoothUtils.getStringMetaData(cachedBluetoothDevice.mDevice, 17), "Stylus");
        }
        return false;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        String key = preference.getKey();
        key.getClass();
        if (!key.equals(KEY_HANDWRITING)) {
            return true;
        }
        Settings.Secure.putInt(
                this.mContext.getContentResolver(),
                "stylus_handwriting_enabled",
                ((Boolean) obj).booleanValue() ? 1 : 0);
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0111, code lost:

       return true;
    */
    @Override // androidx.preference.Preference.OnPreferenceClickListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onPreferenceClick(androidx.preference.Preference r6) {
        /*
            Method dump skipped, instructions count: 304
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.connecteddevice.stylus.StylusDevicesController.onPreferenceClick(androidx.preference.Preference):boolean");
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        refresh$1();
    }

    public final void refresh$1() {
        Preference preference;
        InputDevice inputDevice;
        if (isAvailable()) {
            Preference findPreference =
                    this.mPreferencesContainer.findPreference(KEY_DEFAULT_NOTES);
            RoleManager roleManager =
                    (RoleManager) this.mContext.getSystemService(RoleManager.class);
            SwitchPreferenceCompat switchPreferenceCompat = null;
            if (roleManager != null
                    && roleManager.isRoleAvailable("android.app.role.NOTES")
                    && ((inputDevice = this.mInputDevice) == null
                            || inputDevice.hasKeys(FileType.CSV)[0])) {
                preference =
                        findPreference == null ? new Preference(this.mContext) : findPreference;
                preference.setKey(KEY_DEFAULT_NOTES);
                preference.setTitle(this.mContext.getString(R.string.stylus_default_notes_app));
                preference.setIcon(R.drawable.ic_article);
                preference.setOnPreferenceClickListener(this);
                preference.setEnabled(true);
                UserHandle of =
                        UserHandle.of(
                                Settings.Secure.getInt(
                                        this.mContext.getContentResolver(),
                                        "default_note_task_profile",
                                        UserHandle.myUserId()));
                List roleHoldersAsUser =
                        roleManager.getRoleHoldersAsUser("android.app.role.NOTES", of);
                if (roleHoldersAsUser.isEmpty()) {
                    preference.setSummary(R.string.default_app_none);
                } else {
                    String str = (String) roleHoldersAsUser.get(0);
                    PackageManager packageManager = this.mContext.getPackageManager();
                    try {
                        ApplicationInfo applicationInfo =
                                packageManager.getApplicationInfo(
                                        str, PackageManager.ApplicationInfoFlags.of(0L));
                        str =
                                applicationInfo == null
                                        ? ApnSettings.MVNO_NONE
                                        : packageManager
                                                .getApplicationLabel(applicationInfo)
                                                .toString();
                    } catch (PackageManager.NameNotFoundException unused) {
                        Log.e("StylusDevicesController", "Notes role package not found.");
                    }
                    if (((UserManager) this.mContext.getSystemService(UserManager.class))
                            .isManagedProfile(of.getIdentifier())) {
                        preference.setSummary(
                                this.mContext.getString(
                                        R.string.stylus_default_notes_summary_work, str));
                    } else {
                        preference.setSummary(str);
                    }
                }
            } else {
                preference = null;
            }
            if (findPreference == null && preference != null) {
                this.mPreferencesContainer.addPreference(preference);
            }
            PrimarySwitchPreference primarySwitchPreference =
                    (PrimarySwitchPreference)
                            this.mPreferencesContainer.findPreference(KEY_HANDWRITING);
            PrimarySwitchPreference primarySwitchPreference2 =
                    primarySwitchPreference == null
                            ? new PrimarySwitchPreference(this.mContext)
                            : primarySwitchPreference;
            primarySwitchPreference2.setKey(KEY_HANDWRITING);
            primarySwitchPreference2.setTitle(
                    this.mContext.getString(R.string.stylus_textfield_handwriting));
            primarySwitchPreference2.setIcon(R.drawable.ic_text_fields_alt);
            primarySwitchPreference2.setOnPreferenceChangeListener(this);
            primarySwitchPreference2.setOnPreferenceClickListener(this);
            primarySwitchPreference2.setChecked(
                    Settings.Secure.getInt(
                                    this.mContext.getContentResolver(),
                                    "stylus_handwriting_enabled",
                                    1)
                            == 1);
            InputMethodInfo currentInputMethodInfo =
                    ((InputMethodManager) this.mContext.getSystemService(InputMethodManager.class))
                            .getCurrentInputMethodInfo();
            primarySwitchPreference2.setVisible(
                    currentInputMethodInfo != null
                            && currentInputMethodInfo.supportsStylusHandwriting());
            if (primarySwitchPreference == null) {
                this.mPreferencesContainer.addPreference(primarySwitchPreference2);
            }
            if (this.mPreferencesContainer.findPreference(KEY_IGNORE_BUTTON) == null) {
                PreferenceCategory preferenceCategory = this.mPreferencesContainer;
                SwitchPreferenceCompat switchPreferenceCompat2 =
                        new SwitchPreferenceCompat(this.mContext);
                switchPreferenceCompat2.setKey(KEY_IGNORE_BUTTON);
                switchPreferenceCompat2.setTitle(
                        this.mContext.getString(R.string.stylus_ignore_button));
                switchPreferenceCompat2.setIcon(R.drawable.ic_block);
                switchPreferenceCompat2.setOnPreferenceClickListener(this);
                switchPreferenceCompat2.setChecked(
                        Settings.Secure.getInt(
                                        this.mContext.getContentResolver(),
                                        "stylus_buttons_enabled",
                                        1)
                                == 0);
                preferenceCategory.addPreference(switchPreferenceCompat2);
            }
            SwitchPreferenceCompat switchPreferenceCompat3 =
                    (SwitchPreferenceCompat)
                            this.mPreferencesContainer.findPreference(KEY_SHOW_STYLUS_POINTER_ICON);
            if (this.mContext
                    .getResources()
                    .getBoolean(android.R.bool.config_enable_emergency_call_while_sim_locked)) {
                switchPreferenceCompat =
                        switchPreferenceCompat3 == null
                                ? new SwitchPreferenceCompat(this.mContext)
                                : switchPreferenceCompat3;
                switchPreferenceCompat.setKey(KEY_SHOW_STYLUS_POINTER_ICON);
                switchPreferenceCompat.setTitle(
                        this.mContext.getString(R.string.show_stylus_pointer_icon));
                switchPreferenceCompat.setIcon(R.drawable.ic_stylus);
                switchPreferenceCompat.setOnPreferenceClickListener(this);
                switchPreferenceCompat.setChecked(
                        Settings.Secure.getInt(
                                        this.mContext.getContentResolver(),
                                        "stylus_pointer_icon_enabled",
                                        1)
                                == 1);
            }
            if (switchPreferenceCompat3 != null || switchPreferenceCompat == null) {
                return;
            }
            this.mPreferencesContainer.addPreference(switchPreferenceCompat);
        }
    }
}

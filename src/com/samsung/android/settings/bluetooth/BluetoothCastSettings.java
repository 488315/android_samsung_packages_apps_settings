package com.samsung.android.settings.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.service.quicksettings.TileService;
import android.util.ArraySet;
import android.util.secutil.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SeslSwitchBar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.SecDropDownPreference;
import androidx.preference.SecPreferenceCategory;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.bluetooth.Utils;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.BluetoothUtils$$ExternalSyntheticLambda5;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.widget.LayoutPreference;

import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.bluetooth.SemBluetoothAudioCast;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.display.controller.SecTimeoutDockingPreferenceController;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.multidevices.MultiDevicesFragment;
import com.samsung.android.settings.widget.SecInsetCategoryPreference;
import com.samsung.android.settings.widget.SecUnclickablePreference;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.AudioCastProfile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BluetoothCastSettings extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener, Preference.OnPreferenceChangeListener {
    public static final boolean LOG_DEBUG = Debug.semIsProductDev();
    public static final Integer[] TIMEOUT_ARRAY = {
        300000,
        600000,
        Integer.valueOf(
                SecTimeoutDockingPreferenceController.FALLBACK_SCREEN_TIMEOUT_DOCKING_VALUE),
        -1
    };
    public LayoutPreference mAnimPreference;
    public List mAvailableDevices;
    public FragmentActivity mContext;
    public SecUnclickablePreference mDescPreference;
    public JSONObject mDisallowedDevices;
    public LocalBluetoothManager mLocalBluetoothManager;
    public SecDropDownPreference mModePreference;
    public SecDropDownPreference mPermPreference;
    public String mSAScreenId;
    public SettingsMainSwitchBar mSwitchBar;
    public SecDropDownPreference mTimeoutPreference;
    public SharedPreferences pref;
    public SecInsetCategoryPreference emptyCategory1 = null;
    public SecInsetCategoryPreference emptyCategory2 = null;
    public SecPreferenceCategory mAvailableDevicesCategory = null;
    public final SettingsObserver mSettingsObserver = new SettingsObserver(new Handler());
    public boolean mIsEnabled = false;
    public boolean mIsTablet = false;
    public boolean mIsBindingTileRequested = false;
    public final AnonymousClass1 handler =
            new Handler() { // from class:
                            // com.samsung.android.settings.bluetooth.BluetoothCastSettings.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    Log.d("BluetoothCastSettings", "handleMessage - EVENT_DEVICE_STATE_CHANGED ");
                    BluetoothCastSettings bluetoothCastSettings = BluetoothCastSettings.this;
                    if (!bluetoothCastSettings.isAdded()
                            || bluetoothCastSettings.getActivity() == null) {
                        Log.d("BluetoothCastSettings", "handleMessage - Ignore msg");
                    } else {
                        bluetoothCastSettings.updateHelpPreferences(
                                bluetoothCastSettings.mIsEnabled);
                        bluetoothCastSettings.updatePreferences(bluetoothCastSettings.mIsEnabled);
                    }
                }
            };
    public final AnonymousClass2 mBluetoothReceiver = new BroadcastReceiver() { // from class:
                // com.samsung.android.settings.bluetooth.BluetoothCastSettings.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    Log.d("BluetoothCastSettings", "onReceive : " + action);
                    if (action.equals(
                            "android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED")) {
                        int intExtra =
                                intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                        if (intExtra == 2 || intExtra == 0) {
                            Log.d(
                                    "BluetoothCastSettings",
                                    "onReceive : " + action + " - state : " + intExtra);
                            sendEmptyMessageDelayed(100, 500L);
                            return;
                        }
                        return;
                    }
                    if (action.equals(
                            "com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED")) {
                        int intExtra2 =
                                intent.getIntExtra(
                                        "com.samsung.android.bluetooth.cast.extra.STATE", 0);
                        if (intExtra2 == 2 || intExtra2 == 0) {
                            Log.d(
                                    "BluetoothCastSettings",
                                    "onReceive : " + action + " - state : " + intExtra2);
                            sendEmptyMessageDelayed(100, 500L);
                        }
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsObserver extends ContentObserver {
        public final Uri BLUETOOTH_CAST_DISALLOWED_URI;
        public final Uri BLUETOOTH_CAST_MODE_URI;
        public final Uri BLUETOOTH_CAST_PERMISSION_URI;
        public final Uri BLUETOOTH_CAST_RANGE_URI;
        public final Uri BLUETOOTH_CAST_TIMEOUT_URI;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.BLUETOOTH_CAST_MODE_URI = Settings.Secure.getUriFor("bluetooth_cast_mode");
            this.BLUETOOTH_CAST_RANGE_URI = Settings.Secure.getUriFor("bluetooth_cast_range");
            this.BLUETOOTH_CAST_PERMISSION_URI =
                    Settings.Secure.getUriFor("bluetooth_cast_permission");
            this.BLUETOOTH_CAST_TIMEOUT_URI = Settings.Secure.getUriFor("bluetooth_cast_timeout");
            this.BLUETOOTH_CAST_DISALLOWED_URI =
                    Settings.Secure.getUriFor("bluetooth_cast_disallowed_devices");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            FragmentActivity activity = BluetoothCastSettings.this.getActivity();
            if (activity == null) {
                return;
            }
            if (this.BLUETOOTH_CAST_MODE_URI.equals(uri)) {
                int i =
                        Settings.Secure.getInt(
                                activity.getContentResolver(), "bluetooth_cast_mode", 1);
                BluetoothCastSettings bluetoothCastSettings = BluetoothCastSettings.this;
                bluetoothCastSettings.mIsEnabled = i == 1;
                boolean isChecked =
                        ((SeslSwitchBar) bluetoothCastSettings.mSwitchBar).mSwitch.isChecked();
                BluetoothCastSettings bluetoothCastSettings2 = BluetoothCastSettings.this;
                boolean z2 = bluetoothCastSettings2.mIsEnabled;
                if (isChecked != z2) {
                    bluetoothCastSettings2.mSwitchBar.setChecked(z2);
                    return;
                }
                return;
            }
            if (this.BLUETOOTH_CAST_RANGE_URI.equals(uri)) {
                BluetoothCastSettings bluetoothCastSettings3 = BluetoothCastSettings.this;
                bluetoothCastSettings3.mModePreference.callChangeListener(
                        bluetoothCastSettings3.getResources()
                                .getStringArray(R.array.bluetooth_cast_mode_entry_values)[
                                Settings.Secure.getInt(
                                        activity.getContentResolver(), "bluetooth_cast_range", 0)]);
                return;
            }
            if (this.BLUETOOTH_CAST_PERMISSION_URI.equals(uri)) {
                BluetoothCastSettings bluetoothCastSettings4 = BluetoothCastSettings.this;
                bluetoothCastSettings4.mPermPreference.callChangeListener(
                        bluetoothCastSettings4.getResources()
                                .getStringArray(R.array.bluetooth_cast_perm_entry_values)[
                                Settings.Secure.getInt(
                                        activity.getContentResolver(),
                                        "bluetooth_cast_permission",
                                        0)]);
                return;
            }
            if (this.BLUETOOTH_CAST_TIMEOUT_URI.equals(uri)) {
                BluetoothCastSettings.this.mTimeoutPreference.callChangeListener(
                        String.valueOf(
                                Settings.Secure.getInt(
                                        activity.getContentResolver(),
                                        "bluetooth_cast_timeout",
                                        600000)));
                return;
            }
            if (this.BLUETOOTH_CAST_DISALLOWED_URI.equals(uri)) {
                try {
                    String string =
                            Settings.Secure.getString(
                                    activity.getContentResolver(),
                                    "bluetooth_cast_disallowed_devices");
                    JSONObject jSONObject =
                            string == null ? new JSONObject() : new JSONObject(string);
                    if (jSONObject.length()
                            > BluetoothCastSettings.this.mDisallowedDevices.length()) {
                        Iterator<String> keys = jSONObject.keys();
                        if (keys != null) {
                            while (keys.hasNext()) {
                                String next = keys.next();
                                if (!BluetoothCastSettings.this.mDisallowedDevices.has(next)) {
                                    if (BluetoothCastSettings.this
                                                    .getPreferenceScreen()
                                                    .findPreference(next)
                                            != null) {
                                        BluetoothCastSettings.this
                                                .getPreferenceScreen()
                                                .findPreference(next)
                                                .callChangeListener(Boolean.FALSE);
                                        return;
                                    }
                                    return;
                                }
                            }
                            return;
                        }
                        return;
                    }
                    Iterator<String> keys2 = BluetoothCastSettings.this.mDisallowedDevices.keys();
                    if (keys2 != null) {
                        while (keys2.hasNext()) {
                            String next2 = keys2.next();
                            if (!jSONObject.has(next2)) {
                                if (BluetoothCastSettings.this
                                                .getPreferenceScreen()
                                                .findPreference(next2)
                                        != null) {
                                    BluetoothCastSettings.this
                                            .getPreferenceScreen()
                                            .findPreference(next2)
                                            .callChangeListener(Boolean.TRUE);
                                    return;
                                }
                                return;
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        public final void setListening(boolean z) {
            BluetoothCastSettings bluetoothCastSettings = BluetoothCastSettings.this;
            boolean z2 = BluetoothCastSettings.LOG_DEBUG;
            ContentResolver contentResolver = bluetoothCastSettings.getContentResolver();
            if (!z) {
                contentResolver.unregisterContentObserver(this);
                return;
            }
            contentResolver.registerContentObserver(this.BLUETOOTH_CAST_MODE_URI, false, this);
            contentResolver.registerContentObserver(this.BLUETOOTH_CAST_RANGE_URI, false, this);
            contentResolver.registerContentObserver(
                    this.BLUETOOTH_CAST_PERMISSION_URI, false, this);
            contentResolver.registerContentObserver(this.BLUETOOTH_CAST_TIMEOUT_URI, false, this);
            contentResolver.registerContentObserver(
                    this.BLUETOOTH_CAST_DISALLOWED_URI, false, this);
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.sec_bluetooth_cast_title;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        String string =
                getArguments() != null ? getArguments().getString("music_share_pref_key") : null;
        Log.d("BluetoothCastSettings", "getHierarchicalParentFragment : preferenceKey - " + string);
        return (string == null || !string.equals("music_share_setting"))
                ? BluetoothAdvancedSettings.class.getName()
                : MultiDevicesFragment.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 24;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        String string =
                getArguments() != null ? getArguments().getString("music_share_pref_key") : null;
        Log.d("BluetoothCastSettings", "getTopLevelPreferenceKey : preferenceKey - " + string);
        return (string == null || !string.equals("music_share_setting"))
                ? "top_level_connections"
                : "top_level_multi_devices";
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        Log.v("BluetoothCastSettings", "onActivityCreated");
        this.mContext = getActivity();
        getPreferenceScreen().mOrderingAsAdded = false;
        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        this.pref = settingsActivity.getSharedPreferences("bluetooth_cast_pref", 0);
        SettingsMainSwitchBar settingsMainSwitchBar = settingsActivity.mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.setChecked(this.mIsEnabled);
        this.mSwitchBar.addOnSwitchChangeListener(this);
        this.mSwitchBar.show();
        SecUnclickablePreference secUnclickablePreference =
                (SecUnclickablePreference) findPreference("bluetooth_cast_description");
        this.mDescPreference = secUnclickablePreference;
        secUnclickablePreference.setSelectable(false);
        SecUnclickablePreference secUnclickablePreference2 = this.mDescPreference;
        secUnclickablePreference2.mPositionMode = this.mIsEnabled ? 1 : 0;
        if (this.mIsTablet) {
            secUnclickablePreference2.setTitle(
                    getResources().getString(R.string.sec_bluetooth_cast_description_tablet));
        } else {
            secUnclickablePreference2.setTitle(
                    getResources().getString(R.string.sec_bluetooth_cast_description));
        }
        SecDropDownPreference secDropDownPreference =
                (SecDropDownPreference) findPreference("mode_sharing_setting");
        this.mModePreference = secDropDownPreference;
        secDropDownPreference.setOnPreferenceChangeListener(this);
        SecDropDownPreference secDropDownPreference2 =
                (SecDropDownPreference) findPreference("perm_sharing_setting");
        this.mPermPreference = secDropDownPreference2;
        secDropDownPreference2.setOnPreferenceChangeListener(this);
        SecDropDownPreference secDropDownPreference3 =
                (SecDropDownPreference) findPreference("timeout_sharing_setting");
        this.mTimeoutPreference = secDropDownPreference3;
        secDropDownPreference3.setOnPreferenceChangeListener(this);
        try {
            String string =
                    Settings.Secure.getString(
                            getActivity().getContentResolver(),
                            "bluetooth_cast_disallowed_devices");
            if (string == null) {
                this.mDisallowedDevices = new JSONObject();
            } else {
                this.mDisallowedDevices = new JSONObject(string);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList arrayList = new ArrayList();
        this.mAvailableDevices = arrayList;
        arrayList.addAll(
                this.pref.getStringSet("bluetooth_cast_available_devices", new ArraySet()));
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        Log.v("BluetoothCastSettings", "onCheckedChanged " + z);
        this.mIsEnabled = z;
        Settings.Secure.putInt(
                getActivity().getContentResolver(), "bluetooth_cast_mode", z ? 1 : 0);
        SALogging.insertSALog(
                this.mSAScreenId,
                getResources()
                        .getString(R.string.event_bluetooth_music_share_setting_on_off_switch),
                Integer.toString(z ? 1 : 0));
        updateHelpPreferences(z);
        updatePreferences(z);
        if (this.mIsBindingTileRequested) {
            return;
        }
        TileService.requestListeningState(
                getActivity(),
                new ComponentName(getActivity(), (Class<?>) BluetoothCastTile.class));
        this.mIsBindingTileRequested = true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.v("BluetoothCastSettings", "onConfigurationChanged");
        updateHelpPreferences(this.mIsEnabled);
        updatePreferences(this.mIsEnabled);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.v("BluetoothCastSettings", "onCreate");
        setHasOptionsMenu(false);
        this.mLocalBluetoothManager =
                LocalBluetoothManager.getInstance(
                        getActivity().getApplicationContext(), Utils.mOnInitCallback);
        this.mSAScreenId = getResources().getString(R.string.screen_bluetooth_music_share_setting);
        IntentFilter intentFilter =
                new IntentFilter("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
        intentFilter.addAction(
                "com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED");
        getActivity().registerReceiver(this.mBluetoothReceiver, intentFilter);
        addPreferencesFromResource(R.xml.sec_bluetooth_cast_settings);
        this.mIsEnabled =
                Settings.Secure.getInt(getActivity().getContentResolver(), "bluetooth_cast_mode", 1)
                        == 1;
        this.mIsTablet = com.android.settings.Utils.isTablet();
        LayoutPreference layoutPreference =
                (LayoutPreference) findPreference("bluetooth_cast_help_image");
        this.mAnimPreference = layoutPreference;
        if (this.mIsEnabled) {
            layoutPreference.setVisible(false);
            return;
        }
        layoutPreference.setVisible(true);
        LottieAnimationView lottieAnimationView =
                getResources().getConfiguration().orientation == 2
                        ? (LottieAnimationView)
                                this.mAnimPreference.mRootView.findViewById(
                                        R.id.bluetooth_cast_help_image_landscape)
                        : (LottieAnimationView)
                                this.mAnimPreference.mRootView.findViewById(
                                        R.id.bluetooth_cast_help_image_vertical);
        if (com.android.settings.Utils.isNightMode(getActivity())) {
            lottieAnimationView.setAnimation("sec_bluetooth_music_share_help_dark.json");
        } else {
            lottieAnimationView.setAnimation("sec_bluetooth_music_share_help_light.json");
        }
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        Log.v("BluetoothCastSettings", "onDestroyView");
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
            this.mSwitchBar.hide();
        }
        getActivity().unregisterReceiver(this.mBluetoothReceiver);
        this.mSettingsObserver.setListening(false);
        super.onDestroyView();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        SALogging.insertSALog(
                this.mSAScreenId,
                getResources()
                        .getString(R.string.event_bluetooth_music_share_setting_navigate_button));
        ((SettingsActivity) getActivity()).onBackPressed();
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        Log.v("BluetoothCastSettings", "onPause");
        super.onPause();
        if (LOG_DEBUG) {
            Log.d(
                    "BluetoothCastSettings",
                    "BLUETOOTH_CAST_MODE : "
                            + Settings.Secure.getInt(
                                    getActivity().getContentResolver(), "bluetooth_cast_mode", 1));
            Log.d(
                    "BluetoothCastSettings",
                    "BLUETOOTH_CAST_RANGE : "
                            + Settings.Secure.getInt(
                                    getActivity().getContentResolver(), "bluetooth_cast_range", 0));
            Log.d(
                    "BluetoothCastSettings",
                    "BLUETOOTH_CAST_PERMISSION : "
                            + Settings.Secure.getInt(
                                    getActivity().getContentResolver(),
                                    "bluetooth_cast_permission",
                                    0));
            Log.d(
                    "BluetoothCastSettings",
                    "BLUETOOTH_CAST_TIMEOUT : "
                            + Settings.Secure.getInt(
                                    getActivity().getContentResolver(),
                                    "bluetooth_cast_timeout",
                                    600000));
            try {
                String string =
                        Settings.Secure.getString(
                                getActivity().getContentResolver(),
                                "bluetooth_cast_disallowed_devices");
                JSONObject jSONObject = string == null ? new JSONObject() : new JSONObject(string);
                Iterator<String> keys = jSONObject.keys();
                if (keys != null) {
                    while (keys.hasNext()) {
                        Log.d(
                                "BluetoothCastSettings",
                                "BLUETOOTH_CAST_DISALLOWED : " + keys.next());
                    }
                }
                Log.d(
                        "BluetoothCastSettings",
                        "BLUETOOTH_CAST_DISALLOWED size : " + jSONObject.length());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Log.d(
                "BluetoothCastSettings",
                "onPreferenceChange : "
                        + ((Object) preference.getTitle())
                        + " , checked : "
                        + obj.toString());
        if (!(preference instanceof SecSwitchPreference)) {
            if (preference instanceof SecDropDownPreference) {
                String obj2 = obj.toString();
                String key = preference.getKey();
                if ("mode_sharing_setting".equals(key)) {
                    int i =
                            Settings.Secure.getInt(
                                    getActivity().getContentResolver(), "bluetooth_cast_range", 0);
                    int indexOf =
                            Arrays.asList(
                                            getResources()
                                                    .getStringArray(
                                                            R.array
                                                                    .bluetooth_cast_mode_entry_values))
                                    .indexOf(obj2);
                    if (i != indexOf) {
                        SALogging.insertSALog(
                                this.mSAScreenId,
                                getResources()
                                        .getString(
                                                R.string
                                                        .event_bluetooth_music_share_setting_share_with),
                                Integer.toString(indexOf));
                        Settings.Secure.putInt(
                                getActivity().getContentResolver(),
                                "bluetooth_cast_range",
                                indexOf);
                    }
                } else if ("perm_sharing_setting".equals(key)) {
                    int i2 =
                            Settings.Secure.getInt(
                                    getActivity().getContentResolver(),
                                    "bluetooth_cast_permission",
                                    0);
                    int indexOf2 =
                            Arrays.asList(
                                            getResources()
                                                    .getStringArray(
                                                            R.array
                                                                    .bluetooth_cast_perm_entry_values))
                                    .indexOf(obj2);
                    if (i2 != indexOf2) {
                        SALogging.insertSALog(
                                this.mSAScreenId,
                                getResources()
                                        .getString(
                                                R.string
                                                        .event_bluetooth_music_share_setting_ask_permission),
                                Integer.toString(indexOf2));
                        Settings.Secure.putInt(
                                getActivity().getContentResolver(),
                                "bluetooth_cast_permission",
                                indexOf2);
                    }
                } else if ("timeout_sharing_setting".equals(key)) {
                    int i3 =
                            Settings.Secure.getInt(
                                    getActivity().getContentResolver(),
                                    "bluetooth_cast_timeout",
                                    600000);
                    Integer valueOf = Integer.valueOf(obj2);
                    int intValue = valueOf.intValue();
                    if (i3 != intValue) {
                        SALogging.insertSALog(
                                this.mSAScreenId,
                                getResources()
                                        .getString(
                                                R.string
                                                        .event_bluetooth_music_share_setting_disconnect_timeout),
                                Integer.toString(Arrays.asList(TIMEOUT_ARRAY).indexOf(valueOf)));
                        Settings.Secure.putInt(
                                getActivity().getContentResolver(),
                                "bluetooth_cast_timeout",
                                intValue);
                    }
                }
                SecDropDownPreference secDropDownPreference = (SecDropDownPreference) preference;
                secDropDownPreference.setValue(obj2);
                preference.setSummary(secDropDownPreference.getEntry());
            }
            return false;
        }
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean isEnabled = this.mLocalBluetoothManager.mLocalAdapter.mAdapter.isEnabled();
        new ArrayList();
        if (booleanValue) {
            JSONObject jSONObject = this.mDisallowedDevices;
            if (jSONObject != null && jSONObject.has(preference.getKey())) {
                this.mDisallowedDevices.remove(preference.getKey());
            }
        } else {
            try {
                this.mDisallowedDevices.put(preference.getKey(), false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        SALogging.insertSALog(
                this.mSAScreenId,
                getResources().getString(R.string.event_bluetooth_music_share_setting_device),
                Integer.toString(booleanValue ? 1 : 0));
        ContentResolver contentResolver = getActivity().getContentResolver();
        JSONObject jSONObject2 = this.mDisallowedDevices;
        Settings.Secure.putString(
                contentResolver,
                "bluetooth_cast_disallowed_devices",
                jSONObject2 == null ? ApnSettings.MVNO_NONE : jSONObject2.toString());
        ((SecSwitchPreference) preference).setChecked(booleanValue);
        if (!this.mIsBindingTileRequested) {
            TileService.requestListeningState(
                    getActivity(),
                    new ComponentName(getActivity(), (Class<?>) BluetoothCastTile.class));
            this.mIsBindingTileRequested = true;
        }
        if (!isEnabled) {
            FragmentActivity fragmentActivity = this.mContext;
            LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
            LocalBluetoothAdapter localBluetoothAdapter = localBluetoothManager.mLocalAdapter;
            Iterator it =
                    ((ArrayList)
                                    BluetoothUtils.getA2dpBondDevices(
                                            fragmentActivity,
                                            localBluetoothManager.mProfileManager))
                            .iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) it.next();
                if (cachedBluetoothDevice.mDevice.getAddress().equals(preference.getKey())) {
                    FragmentActivity activity = getActivity();
                    boolean z = Utils.DEBUG;
                    preference.setIcon(
                            BluetoothUtils.getHostOverlayIconDrawable(
                                    activity, cachedBluetoothDevice));
                    break;
                }
            }
        } else {
            FragmentActivity activity2 = getActivity();
            String key2 = preference.getKey();
            boolean z2 = Utils.DEBUG;
            BluetoothUtils.AnonymousClass2 anonymousClass2 = BluetoothUtils.mOnInitCallback;
            preference.setIcon(
                    BluetoothUtils.getHostOverlayIconDrawable(
                            activity2,
                            LocalBluetoothManager.getInstance(activity2, anonymousClass2)
                                    .mCachedDeviceManager
                                    .findDevice(
                                            LocalBluetoothManager.getInstance(
                                                            activity2, anonymousClass2)
                                                    .mLocalAdapter
                                                    .mAdapter
                                                    .getRemoteDevice(key2))));
        }
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        Log.v("BluetoothCastSettings", "onResume");
        super.onResume();
        if (this.mLocalBluetoothManager.mLocalAdapter.mAdapter.isEnabled()) {
            List list =
                    (List)
                            this.mLocalBluetoothManager
                                    .mLocalAdapter
                                    .mAdapter
                                    .getBondedDevices()
                                    .stream()
                                    .map(new BluetoothCastSettings$$ExternalSyntheticLambda4())
                                    .collect(Collectors.toList());
            Iterator<String> keys = this.mDisallowedDevices.keys();
            boolean z = false;
            if (keys != null) {
                while (keys.hasNext()) {
                    String next = keys.next();
                    if (list.isEmpty() || !list.contains(next)) {
                        Log.d(
                                "BluetoothCastSettings",
                                "a disallowed device is removed in bonded list");
                        keys.remove();
                        z = true;
                    }
                }
            }
            if (z) {
                Settings.Secure.putString(
                        getActivity().getContentResolver(),
                        "bluetooth_cast_disallowed_devices",
                        this.mDisallowedDevices.toString());
            }
        }
        this.mSettingsObserver.setListening(true);
        updateHelpPreferences(this.mIsEnabled);
        updatePreferences(this.mIsEnabled);
    }

    public final void updateHelpPreferences(boolean z) {
        LottieAnimationView lottieAnimationView;
        Log.v("BluetoothCastSettings", "updateHelpPreferences : " + z);
        if (this.mIsEnabled) {
            this.mAnimPreference.setVisible(false);
            this.mDescPreference.mPositionMode = 1;
        } else {
            this.mAnimPreference.setVisible(true);
            this.mDescPreference.mPositionMode = 0;
        }
        if (this.emptyCategory1 != null) {
            getPreferenceScreen().removePreference(this.emptyCategory1);
            this.emptyCategory1 = null;
        }
        if (this.emptyCategory2 != null) {
            getPreferenceScreen().removePreference(this.emptyCategory2);
            this.emptyCategory2 = null;
        }
        if (this.mContext.getResources().getConfiguration().orientation == 2) {
            lottieAnimationView =
                    (LottieAnimationView)
                            this.mAnimPreference.mRootView.findViewById(
                                    R.id.bluetooth_cast_help_image_landscape);
            this.mDescPreference.setVisible(z);
            if (!z) {
                NestedScrollView nestedScrollView =
                        (NestedScrollView)
                                this.mAnimPreference.mRootView.findViewById(
                                        R.id.bluetooth_cast_help_case_off_landscape);
                NestedScrollView nestedScrollView2 =
                        (NestedScrollView)
                                this.mAnimPreference.mRootView.findViewById(
                                        R.id.bluetooth_cast_help_case_on_view);
                nestedScrollView.setVisibility(0);
                nestedScrollView2.setVisibility(8);
                TextView textView =
                        (TextView)
                                this.mAnimPreference.mRootView.findViewById(
                                        R.id.bluetooth_cast_help_text_landscape);
                if (this.mIsTablet) {
                    textView.setText(R.string.sec_bluetooth_cast_description_tablet);
                } else {
                    textView.setText(R.string.sec_bluetooth_cast_description);
                }
                SecInsetCategoryPreference secInsetCategoryPreference =
                        new SecInsetCategoryPreference(this.mContext);
                this.emptyCategory1 = secInsetCategoryPreference;
                secInsetCategoryPreference.setHeight(1);
                this.emptyCategory1.seslSetSubheaderRoundedBackground(0);
                this.emptyCategory1.setOrder(this.mAnimPreference.getOrder() - 1);
                getPreferenceScreen().addPreference(this.emptyCategory1);
                SecInsetCategoryPreference secInsetCategoryPreference2 =
                        new SecInsetCategoryPreference(this.mContext);
                this.emptyCategory2 = secInsetCategoryPreference2;
                secInsetCategoryPreference2.setHeight(1);
                this.emptyCategory2.seslSetSubheaderRoundedBackground(0);
                this.emptyCategory2.setOrder(this.mAnimPreference.getOrder() + 1);
                getPreferenceScreen().addPreference(this.emptyCategory2);
                LinearLayout linearLayout =
                        (LinearLayout)
                                this.mAnimPreference.mRootView.findViewById(
                                        R.id.bluetooth_cast_help_case_off_landscape_layout);
                if (linearLayout != null) {
                    linearLayout.semSetRoundedCorners(15);
                    linearLayout.semSetRoundedCornerColor(
                            15,
                            this.mContext
                                    .getResources()
                                    .getColor(R.color.sec_widget_round_and_bgcolor));
                }
            }
        } else {
            lottieAnimationView =
                    (LottieAnimationView)
                            this.mAnimPreference.mRootView.findViewById(
                                    R.id.bluetooth_cast_help_image_vertical);
            NestedScrollView nestedScrollView3 =
                    (NestedScrollView)
                            this.mAnimPreference.mRootView.findViewById(
                                    R.id.bluetooth_cast_help_case_off_landscape);
            NestedScrollView nestedScrollView4 =
                    (NestedScrollView)
                            this.mAnimPreference.mRootView.findViewById(
                                    R.id.bluetooth_cast_help_case_on_view);
            nestedScrollView3.setVisibility(8);
            nestedScrollView4.setVisibility(0);
            if (!this.mDescPreference.isVisible()) {
                this.mDescPreference.setVisible(true);
            }
        }
        if (com.android.settings.Utils.isNightMode(getActivity())) {
            lottieAnimationView.setAnimation("sec_bluetooth_music_share_help_dark.json");
        } else {
            lottieAnimationView.setAnimation("sec_bluetooth_music_share_help_light.json");
        }
    }

    public final void updatePreferences(boolean z) {
        List<CachedBluetoothDevice> a2dpBondDevices;
        List arrayList;
        AudioCastProfile audioCastProfile;
        final int i = 0;
        final int i2 = 1;
        Log.v("BluetoothCastSettings", "updatePreferences : " + z);
        if (!((ArrayList) this.mAvailableDevices).isEmpty()) {
            Iterator it = ((ArrayList) this.mAvailableDevices).iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                if (getPreferenceScreen().findPreference(str) != null) {
                    getPreferenceScreen()
                            .removePreference(getPreferenceScreen().findPreference(str));
                }
            }
        }
        this.mModePreference.setVisible(z);
        this.mPermPreference.setVisible(z);
        this.mTimeoutPreference.setVisible(z);
        if (!z) {
            if (this.mAvailableDevicesCategory != null) {
                getPreferenceScreen().removePreference(this.mAvailableDevicesCategory);
                this.mAvailableDevicesCategory = null;
                return;
            }
            return;
        }
        this.mModePreference.setValue(
                getResources()
                        .getStringArray(R.array.bluetooth_cast_mode_entry_values)[
                        Settings.Secure.getInt(
                                getActivity().getContentResolver(), "bluetooth_cast_range", 0)]);
        SecDropDownPreference secDropDownPreference = this.mModePreference;
        secDropDownPreference.setSummary(secDropDownPreference.getEntry());
        SecDropDownPreference secDropDownPreference2 = this.mModePreference;
        secDropDownPreference2.getClass();
        SecPreferenceUtils.applySummaryColor(secDropDownPreference2, true);
        this.mPermPreference.setValue(
                getResources()
                        .getStringArray(R.array.bluetooth_cast_perm_entry_values)[
                        Settings.Secure.getInt(
                                getActivity().getContentResolver(),
                                "bluetooth_cast_permission",
                                0)]);
        SecDropDownPreference secDropDownPreference3 = this.mPermPreference;
        secDropDownPreference3.setSummary(secDropDownPreference3.getEntry());
        SecDropDownPreference secDropDownPreference4 = this.mPermPreference;
        secDropDownPreference4.getClass();
        SecPreferenceUtils.applySummaryColor(secDropDownPreference4, true);
        this.mTimeoutPreference.setValue(
                String.valueOf(
                        Settings.Secure.getInt(
                                getActivity().getContentResolver(),
                                "bluetooth_cast_timeout",
                                600000)));
        SecDropDownPreference secDropDownPreference5 = this.mTimeoutPreference;
        secDropDownPreference5.setSummary(secDropDownPreference5.getEntry());
        SecDropDownPreference secDropDownPreference6 = this.mTimeoutPreference;
        secDropDownPreference6.getClass();
        SecPreferenceUtils.applySummaryColor(secDropDownPreference6, true);
        boolean isEnabled = this.mLocalBluetoothManager.mLocalAdapter.mAdapter.isEnabled();
        ArrayList arrayList2 = new ArrayList();
        ((ArrayList) this.mAvailableDevices).clear();
        if (isEnabled) {
            this.mLocalBluetoothManager.mEventManager.readPairedDevices();
            Iterator<BluetoothDevice> it2 =
                    this.mLocalBluetoothManager
                            .mLocalAdapter
                            .mAdapter
                            .getBondedDevices()
                            .iterator();
            while (it2.hasNext()) {
                CachedBluetoothDevice findDevice =
                        this.mLocalBluetoothManager.mCachedDeviceManager.findDevice(it2.next());
                if (findDevice != null) {
                    arrayList2.add(findDevice);
                }
            }
            a2dpBondDevices =
                    (List)
                            arrayList2.stream()
                                    .filter(
                                            new Predicate(
                                                    this) { // from class:
                                                            // com.samsung.android.settings.bluetooth.BluetoothCastSettings$$ExternalSyntheticLambda0
                                                public final /* synthetic */ BluetoothCastSettings
                                                        f$0;

                                                {
                                                    this.f$0 = this;
                                                }

                                                @Override // java.util.function.Predicate
                                                public final boolean test(Object obj) {
                                                    int i3 = i;
                                                    BluetoothCastSettings bluetoothCastSettings =
                                                            this.f$0;
                                                    switch (i3) {
                                                        case 0:
                                                            return ((CachedBluetoothDevice) obj)
                                                                    .hasProfile(
                                                                            bluetoothCastSettings
                                                                                    .mLocalBluetoothManager
                                                                                    .mProfileManager
                                                                                    .mA2dpProfile);
                                                        default:
                                                            SemBluetoothCastDevice
                                                                    semBluetoothCastDevice =
                                                                            (SemBluetoothCastDevice)
                                                                                    obj;
                                                            AudioCastProfile audioCastProfile2 =
                                                                    bluetoothCastSettings
                                                                            .mLocalBluetoothManager
                                                                            .mLocalCastProfileManager
                                                                            .mAudioCastProfile;
                                                            android.util.Log.d(
                                                                    audioCastProfile2.TAG,
                                                                    "getConnectionState");
                                                            SemBluetoothAudioCast
                                                                    semBluetoothAudioCast =
                                                                            audioCastProfile2
                                                                                    .mService;
                                                            return (semBluetoothAudioCast == null
                                                                            ? 0
                                                                            : semBluetoothAudioCast
                                                                                    .getConnectionState(
                                                                                            semBluetoothCastDevice))
                                                                    == 2;
                                                    }
                                                }
                                            })
                                    .sorted(new BluetoothCastSettings$$ExternalSyntheticLambda1())
                                    .collect(Collectors.toList());
        } else {
            FragmentActivity fragmentActivity = this.mContext;
            LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
            LocalBluetoothAdapter localBluetoothAdapter = localBluetoothManager.mLocalAdapter;
            a2dpBondDevices =
                    BluetoothUtils.getA2dpBondDevices(
                            fragmentActivity, localBluetoothManager.mProfileManager);
        }
        if (!a2dpBondDevices.isEmpty()) {
            if (!isEnabled
                    || (audioCastProfile =
                                    this.mLocalBluetoothManager
                                            .mLocalCastProfileManager
                                            .mAudioCastProfile)
                            == null) {
                arrayList = new ArrayList();
            } else {
                SemBluetoothAudioCast semBluetoothAudioCast = audioCastProfile.mService;
                arrayList =
                        (List)
                                (semBluetoothAudioCast == null
                                                ? new ArrayList()
                                                : semBluetoothAudioCast.getConnectedDevices())
                                        .stream()
                                                .filter(
                                                        new BluetoothCastSettings$$ExternalSyntheticLambda2())
                                                .filter(
                                                        new Predicate(this) { // from class:
                                                            // com.samsung.android.settings.bluetooth.BluetoothCastSettings$$ExternalSyntheticLambda0
                                                            public final /* synthetic */
                                                            BluetoothCastSettings f$0;

                                                            {
                                                                this.f$0 = this;
                                                            }

                                                            @Override // java.util.function.Predicate
                                                            public final boolean test(Object obj) {
                                                                int i3 = i2;
                                                                BluetoothCastSettings
                                                                        bluetoothCastSettings =
                                                                                this.f$0;
                                                                switch (i3) {
                                                                    case 0:
                                                                        return ((CachedBluetoothDevice)
                                                                                        obj)
                                                                                .hasProfile(
                                                                                        bluetoothCastSettings
                                                                                                .mLocalBluetoothManager
                                                                                                .mProfileManager
                                                                                                .mA2dpProfile);
                                                                    default:
                                                                        SemBluetoothCastDevice
                                                                                semBluetoothCastDevice =
                                                                                        (SemBluetoothCastDevice)
                                                                                                obj;
                                                                        AudioCastProfile
                                                                                audioCastProfile2 =
                                                                                        bluetoothCastSettings
                                                                                                .mLocalBluetoothManager
                                                                                                .mLocalCastProfileManager
                                                                                                .mAudioCastProfile;
                                                                        android.util.Log.d(
                                                                                audioCastProfile2
                                                                                        .TAG,
                                                                                "getConnectionState");
                                                                        SemBluetoothAudioCast
                                                                                semBluetoothAudioCast2 =
                                                                                        audioCastProfile2
                                                                                                .mService;
                                                                        return (semBluetoothAudioCast2
                                                                                                == null
                                                                                        ? 0
                                                                                        : semBluetoothAudioCast2
                                                                                                .getConnectionState(
                                                                                                        semBluetoothCastDevice))
                                                                                == 2;
                                                                }
                                                            }
                                                        })
                                                .map(new BluetoothUtils$$ExternalSyntheticLambda5())
                                                .collect(Collectors.toList());
            }
            int i3 = 0;
            for (CachedBluetoothDevice cachedBluetoothDevice : a2dpBondDevices) {
                Log.d(
                        "BluetoothCastSettings",
                        "updatePreferences - Available : " + cachedBluetoothDevice.getNameForLog());
                SecSwitchPreference secSwitchPreference = new SecSwitchPreference(this.mContext);
                secSwitchPreference.setKey(cachedBluetoothDevice.mDevice.getAddress());
                secSwitchPreference.setTitle(cachedBluetoothDevice.getName());
                secSwitchPreference.setOnPreferenceChangeListener(this);
                int i4 = i3 + 1;
                secSwitchPreference.setOrder(i3 + 1000);
                if (isEnabled
                        && 2
                                == this.mLocalBluetoothManager.mProfileManager.mA2dpProfile
                                        .getConnectionStatus(cachedBluetoothDevice.mDevice)) {
                    if (arrayList.isEmpty()
                            || !arrayList.contains(cachedBluetoothDevice.mDevice.getAddress())) {
                        secSwitchPreference.setSummary(
                                R.string.bluetooth_a2dp_profile_summary_connected);
                    } else {
                        Log.d(
                                "BluetoothCastSettings",
                                cachedBluetoothDevice.getNameForLog()
                                        + " is connected for Music Share");
                        secSwitchPreference.setSummary(
                                cachedBluetoothDevice.getConnectionSummary());
                    }
                }
                FragmentActivity activity = getActivity();
                boolean z2 = Utils.DEBUG;
                secSwitchPreference.setIcon(
                        BluetoothUtils.getHostOverlayIconDrawable(activity, cachedBluetoothDevice));
                JSONObject jSONObject = this.mDisallowedDevices;
                secSwitchPreference.setChecked(
                        (jSONObject == null || jSONObject.has(secSwitchPreference.getKey()))
                                ? false
                                : true);
                if (this.mAvailableDevicesCategory == null) {
                    SecPreferenceCategory secPreferenceCategory =
                            new SecPreferenceCategory(this.mContext);
                    this.mAvailableDevicesCategory = secPreferenceCategory;
                    secPreferenceCategory.setTitle(
                            R.string.sec_bluetooth_cast_available_devices_category);
                    this.mAvailableDevicesCategory.setOrder(800);
                    getPreferenceScreen().addPreference(this.mAvailableDevicesCategory);
                }
                if (!((ArrayList) this.mAvailableDevices).contains(secSwitchPreference.getKey())) {
                    ((ArrayList) this.mAvailableDevices).add(secSwitchPreference.getKey());
                }
                getPreferenceScreen().addPreference(secSwitchPreference);
                i3 = i4;
            }
        }
        ArraySet arraySet = new ArraySet();
        arraySet.addAll(this.mAvailableDevices);
        this.pref.edit().putStringSet("bluetooth_cast_available_devices", arraySet).apply();
    }
}

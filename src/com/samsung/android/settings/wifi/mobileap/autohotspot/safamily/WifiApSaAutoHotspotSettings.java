package com.samsung.android.settings.wifi.mobileap.autohotspot.safamily;

import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.picker3.widget.SeslColorPicker$16$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.SecPreferenceCategory;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.WifiApUtils;
import com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0;
import com.samsung.android.settings.wifi.mobileap.WifiApCustomPreference;
import com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApAllowedDeviceList;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApFamilyMember;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSmartTetheringApkUtils;
import com.samsung.android.settings.wifi.mobileap.views.WifiApTextViewButtonLayoutPreference;
import com.samsung.android.wifi.SemWifiApSmartWhiteList;
import com.samsung.android.wifi.SemWifiManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApSaAutoHotspotSettings extends DashboardFragment
        implements CompoundButton.OnCheckedChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public FragmentActivity mActivity;
    public boolean mAddAllowedDeviceButtonClicked;
    public WifiApTextViewButtonLayoutPreference mAddAllowedDeviceButtonLayoutPreference;
    public LayoutPreference mAddAllowedDeviceDescription;
    public SemWifiApSmartWhiteList.SmartWhiteList mAllowedDeviceClicked;
    public LayoutPreference mAutoHotspotDescriptionLayoutPreference;
    public Context mContext;
    public EditText mDeviceNameOnDialogEditText;
    public LayoutPreference mDivider;
    public TextView mErrorTextOnDialogView;
    public boolean mIsAutoHotspotSupported;
    public SecPreferenceCategory mMyAccountPreferenceCategory;
    public PreferenceCategory mMySaGroupPreferenceCategory;
    public List mPreviousAllowedDeviceList;
    public SettingsMainSwitchBar mSwitchBar;
    public WifiApSaAutoHotspotSwitchEnabler mWifiApAutoHotspotSwitchEnabler;
    public WifiApSaFamilySharingSwitchController mWifiApSaFamilySharingSwitchController;
    public AlertDialog mDeviceNameDialog = null;
    public String mTempDeviceNameOnDialogEditText = null;
    public final AnonymousClass1 mAutoHotspotCustomPreferenceListener =
            new AnonymousClass1(this, 0);
    public final AnonymousClass4 dialogNameTextWatcher =
            new TextWatcher() { // from class:
                                // com.samsung.android.settings.wifi.mobileap.autohotspot.safamily.WifiApSaAutoHotspotSettings.4
                @Override // android.text.TextWatcher
                public final void afterTextChanged(Editable editable) {
                    boolean z;
                    EditText editText =
                            WifiApSaAutoHotspotSettings.this.mDeviceNameOnDialogEditText;
                    if (editText != null) {
                        int length = editText.getText().toString().trim().length();
                        if (WifiApSaAutoHotspotSettings.this.mDeviceNameOnDialogEditText.length()
                                        == 0
                                || length == 0) {
                            z = false;
                            WifiApSaAutoHotspotSettings.this
                                    .mDeviceNameDialog
                                    .getButton(-1)
                                    .setEnabled(z);
                        }
                    }
                    z = true;
                    WifiApSaAutoHotspotSettings.this.mDeviceNameDialog.getButton(-1).setEnabled(z);
                }

                @Override // android.text.TextWatcher
                public final void beforeTextChanged(
                        CharSequence charSequence, int i, int i2, int i3) {
                    if (charSequence == null || charSequence.toString().getBytes().length > 32) {
                        return;
                    }
                    WifiApSaAutoHotspotSettings.this.mTempDeviceNameOnDialogEditText =
                            charSequence.toString();
                }

                @Override // android.text.TextWatcher
                public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    if (charSequence.toString().getBytes().length <= 32) {
                        if (charSequence.toString().getBytes().length < 32) {
                            WifiApSaAutoHotspotSettings.this.mErrorTextOnDialogView.setVisibility(
                                    8);
                            WifiApSaAutoHotspotSettings wifiApSaAutoHotspotSettings =
                                    WifiApSaAutoHotspotSettings.this;
                            SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0
                                    .m(
                                            wifiApSaAutoHotspotSettings.mContext,
                                            R.color.sec_wifi_ap_edit_text_background_color,
                                            wifiApSaAutoHotspotSettings
                                                    .mDeviceNameOnDialogEditText);
                            return;
                        }
                        return;
                    }
                    String str = WifiApSaAutoHotspotSettings.this.mTempDeviceNameOnDialogEditText;
                    if (str == null || str.getBytes().length > 32) {
                        WifiApSaAutoHotspotSettings.this.mDeviceNameOnDialogEditText.setText(
                                ApnSettings.MVNO_NONE);
                    } else if (charSequence.toString().length()
                                    - WifiApSaAutoHotspotSettings.this
                                            .mTempDeviceNameOnDialogEditText.length()
                            > 1) {
                        String charSequence2 = charSequence.toString();
                        if (charSequence2.getBytes().length > charSequence2.length()) {
                            int i4 = 0;
                            int i5 = 0;
                            int i6 = 0;
                            while (i4 <= 32) {
                                i6 = Character.charCount(charSequence2.codePointAt(i5));
                                int i7 = i5 + i6;
                                i4 += charSequence2.substring(i5, i7).getBytes().length;
                                i5 = i7;
                            }
                            WifiApSaAutoHotspotSettings.this.mDeviceNameOnDialogEditText.setText(
                                    charSequence2.substring(0, i5 - i6));
                        } else {
                            WifiApSaAutoHotspotSettings.this.mDeviceNameOnDialogEditText.setText(
                                    charSequence2.substring(0, 32));
                        }
                    } else {
                        WifiApSaAutoHotspotSettings wifiApSaAutoHotspotSettings2 =
                                WifiApSaAutoHotspotSettings.this;
                        wifiApSaAutoHotspotSettings2.mDeviceNameOnDialogEditText.setText(
                                wifiApSaAutoHotspotSettings2.mTempDeviceNameOnDialogEditText);
                    }
                    WifiApSaAutoHotspotSettings.this.mErrorTextOnDialogView.setText(
                            R.string.wifi_ssid_max_byte_error);
                    WifiApSaAutoHotspotSettings wifiApSaAutoHotspotSettings3 =
                            WifiApSaAutoHotspotSettings.this;
                    SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0.m(
                            wifiApSaAutoHotspotSettings3.mContext,
                            R.color.sec_wifi_dialog_error_color,
                            wifiApSaAutoHotspotSettings3.mDeviceNameOnDialogEditText);
                    WifiApSaAutoHotspotSettings.this.mErrorTextOnDialogView.setVisibility(0);
                    SeslColorPicker$16$$ExternalSyntheticOutline0.m(
                            WifiApSaAutoHotspotSettings.this.mDeviceNameOnDialogEditText);
                }
            };
    public final AnonymousClass1 mOnFamilySwitchStateChangeListener = new AnonymousClass1(this, 1);
    public final AnonymousClass1 mOnAutoHotspotStateChangeListener = new AnonymousClass1(this, 2);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.autohotspot.safamily.WifiApSaAutoHotspotSettings$1, reason: invalid class name */
    public final class AnonymousClass1
            implements WifiApCustomPreference.AutoHotspotCustomPreferenceListener,
                    WifiApSaFamilySharingSwitchEnabler.OnStateChangeListener,
                    WifiApSaAutoHotspotSwitchEnabler.OnStateChangeListener,
                    Preference.OnPreferenceClickListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WifiApSaAutoHotspotSettings this$0;

        public /* synthetic */ AnonymousClass1(
                WifiApSaAutoHotspotSettings wifiApSaAutoHotspotSettings, int i) {
            this.$r8$classId = i;
            this.this$0 = wifiApSaAutoHotspotSettings;
        }

        @Override // androidx.preference.Preference.OnPreferenceClickListener
        public boolean onPreferenceClick(Preference preference) {
            WifiApSaAutoHotspotSettings wifiApSaAutoHotspotSettings = this.this$0;
            if (!wifiApSaAutoHotspotSettings.mAddAllowedDeviceButtonClicked) {
                wifiApSaAutoHotspotSettings.mAddAllowedDeviceButtonClicked = true;
                SubSettingLauncher subSettingLauncher =
                        new SubSettingLauncher(wifiApSaAutoHotspotSettings.getActivity());
                SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
                launchRequest.mSourceMetricsCategory = 3400;
                launchRequest.mDestinationName = WifiApAllowedDeviceList.class.getCanonicalName();
                subSettingLauncher.setTitleRes(R.string.wifi_ap_mhs_add_allowed_device_title, null);
                subSettingLauncher.launch();
            }
            return true;
        }

        @Override // com.samsung.android.settings.wifi.mobileap.WifiApCustomPreference.AutoHotspotCustomPreferenceListener
        public void onPreferenceClicked(Object obj) {
            int i = WifiApSaAutoHotspotSettings.$r8$clinit;
            Log.i(
                    "WifiApSaAutoHotspotSettings",
                    "mAutoHotspotCustomPreferenceListener`s onPreferenceClicked() - Triggered");
            if (obj instanceof SemWifiApSmartWhiteList.SmartWhiteList) {
                WifiApSaAutoHotspotSettings wifiApSaAutoHotspotSettings = this.this$0;
                wifiApSaAutoHotspotSettings.mAllowedDeviceClicked =
                        (SemWifiApSmartWhiteList.SmartWhiteList) obj;
                AlertDialog alertDialog = wifiApSaAutoHotspotSettings.mDeviceNameDialog;
                if (alertDialog == null || !alertDialog.isShowing()) {
                    wifiApSaAutoHotspotSettings.showDialog(11);
                }
            }
        }

        @Override // com.samsung.android.settings.wifi.mobileap.WifiApCustomPreference.AutoHotspotCustomPreferenceListener
        public void onSecondaryIconClicked(Object obj) {
            int i = WifiApSaAutoHotspotSettings.$r8$clinit;
            Log.i(
                    "WifiApSaAutoHotspotSettings",
                    "mAutoHotspotCustomPreferenceListener`s onSecondaryIconClicked() - Triggered");
            if (obj instanceof SemWifiApSmartWhiteList.SmartWhiteList) {
                SemWifiApSmartWhiteList.SmartWhiteList smartWhiteList =
                        (SemWifiApSmartWhiteList.SmartWhiteList) obj;
                if (SemWifiManager.MHSDBG) {
                    Log.d(
                            "WifiApSaAutoHotspotSettings",
                            "mAutoHotspotCustomPreferenceListener`s onSecondaryIconClicked() -"
                                + " smartWhiteList`s name: "
                                    + smartWhiteList.getName()
                                    + ", mac: "
                                    + smartWhiteList.getMac());
                }
                SemWifiApSmartWhiteList.getInstance().removeWhiteList(smartWhiteList.getMac());
            }
        }

        @Override // com.samsung.android.settings.wifi.mobileap.autohotspot.safamily.WifiApSaFamilySharingSwitchEnabler.OnStateChangeListener, com.samsung.android.settings.wifi.mobileap.autohotspot.safamily.WifiApSaAutoHotspotSwitchEnabler.OnStateChangeListener
        public void onStateChanged(int i) {
            WifiApSaAutoHotspotSettings wifiApSaAutoHotspotSettings = this.this$0;
            switch (this.$r8$classId) {
                case 1:
                    int i2 = WifiApSaAutoHotspotSettings.$r8$clinit;
                    MainClearConfirm$$ExternalSyntheticOutline0.m(
                            i,
                            "FamilySharing onStateChanged() - resultCode: ",
                            "WifiApSaAutoHotspotSettings");
                    if (i == 6) {
                        Toast.makeText(
                                        wifiApSaAutoHotspotSettings.mContext,
                                        R.string.smart_tethering_nearby_can_not_available,
                                        1)
                                .show();
                    }
                    wifiApSaAutoHotspotSettings.updateFamilySharingSection(false);
                    break;
                default:
                    int i3 = WifiApSaAutoHotspotSettings.$r8$clinit;
                    MainClearConfirm$$ExternalSyntheticOutline0.m(
                            i,
                            "AutoHotspot onStateChanged() - resultCode: ",
                            "WifiApSaAutoHotspotSettings");
                    wifiApSaAutoHotspotSettings.mSwitchBar.setChecked(
                            WifiApFrameworkUtils.isAutoHotspotSetOn(
                                    wifiApSaAutoHotspotSettings.mContext));
                    if (i != 5) {
                        if (i != 1) {
                            if (i == 2) {
                                Log.d(
                                        "WifiApSaAutoHotspotSettings",
                                        "AutoHotspot feature can`t be used: RESULT_ERROR_NO_SIM");
                            }
                            if (i != 3) {
                                if (i != 6) {
                                    wifiApSaAutoHotspotSettings.updateAutoHotspotSection$1();
                                    wifiApSaAutoHotspotSettings.updateFamilySharingSection(false);
                                    break;
                                } else {
                                    Log.d(
                                            "WifiApSaAutoHotspotSettings",
                                            "AutoHotspot feature can`t be used:"
                                                + " RESULT_ERROR_DISABLED_NEARBY_SCANNING");
                                    Toast.makeText(
                                                    wifiApSaAutoHotspotSettings.mContext,
                                                    R.string
                                                            .smart_tethering_nearby_can_not_available,
                                                    1)
                                            .show();
                                    wifiApSaAutoHotspotSettings.finish();
                                    break;
                                }
                            } else {
                                Log.d(
                                        "WifiApSaAutoHotspotSettings",
                                        "AutoHotspot feature can`t be used:"
                                            + " RESULT_ERROR_NO_ACTIVE_NETWORK");
                                wifiApSaAutoHotspotSettings.finish();
                                break;
                            }
                        } else {
                            Log.d(
                                    "WifiApSaAutoHotspotSettings",
                                    "AutoHotspot feature can`t be used:"
                                        + " RESULT_ERROR_AIRPLANE_MODE_ON");
                            Toast.makeText(
                                            wifiApSaAutoHotspotSettings.mContext,
                                            R.string.flight_mode_enabled_toast,
                                            0)
                                    .show();
                            wifiApSaAutoHotspotSettings.finish();
                            break;
                        }
                    } else {
                        Log.d(
                                "WifiApSaAutoHotspotSettings",
                                "AutoHotspot feature can`t be used:"
                                    + " RESULT_ERROR_TETHERING_RESTRICTED");
                        wifiApSaAutoHotspotSettings.finish();
                        break;
                    }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.autohotspot.safamily.WifiApSaAutoHotspotSettings$3, reason: invalid class name */
    public final class AnonymousClass3 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            int i2 = WifiApSaAutoHotspotSettings.$r8$clinit;
            Log.i(
                    "WifiApSaAutoHotspotSettings",
                    "SmartWhiteListClicked`s onClick() :  device name has not been changed");
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.wifi_ap_smart_tethering_title;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WifiApSaAutoHotspotSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 3400;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_ap_sa_autohotspot_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        Log.i("WifiApSaAutoHotspotSettings", "onActivityCreated");
        super.onActivityCreated(bundle);
        Context context = this.mContext;
        boolean z = WifiApSettingsUtils.DBG;
        if (((AccountManager) context.getSystemService("account"))
                        .getAccountsByType("com.osp.app.signin")
                        .length
                > 0) {
            WifiApSamsungAccountFamilyDb.updateSAFamilyGroupInfo(this.mContext);
        }
        setAutoRemoveInsetCategory(false);
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.show();
        }
        getListView().semSetRoundedCorners(15);
        getListView()
                .semSetRoundedCornerColor(
                        15,
                        getResources()
                                .getColor(
                                        R.color.sec_widget_round_and_bgcolor,
                                        this.mContext.getTheme()));
        this.mWifiApSaFamilySharingSwitchController.setFamilyStateListner(
                this.mOnFamilySwitchStateChangeListener);
        WifiApSaAutoHotspotSwitchEnabler wifiApSaAutoHotspotSwitchEnabler =
                new WifiApSaAutoHotspotSwitchEnabler(this);
        this.mWifiApAutoHotspotSwitchEnabler = wifiApSaAutoHotspotSwitchEnabler;
        wifiApSaAutoHotspotSwitchEnabler.mOnStateChangeListener =
                this.mOnAutoHotspotStateChangeListener;
        this.mAutoHotspotDescriptionLayoutPreference.seslSetSubheaderRoundedBackground(0);
        ((TextView) this.mAutoHotspotDescriptionLayoutPreference.mRootView.findViewById(R.id.title))
                .setText(
                        WifiApUtils.getString(this.mContext, R.string.smart_tethering_description));
        updateAutoHotspotSection$1();
        updateFamilySharingSection(true);
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                "onActivityResult() - requestCode: ",
                ", resultCode(-1 for RESULT_OK) : ",
                i,
                i2,
                "WifiApSaAutoHotspotSettings");
        super.onActivityResult(i, i2, intent);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        WifiApSaFamilySharingSwitchController wifiApSaFamilySharingSwitchController =
                (WifiApSaFamilySharingSwitchController)
                        use(WifiApSaFamilySharingSwitchController.class);
        this.mWifiApSaFamilySharingSwitchController = wifiApSaFamilySharingSwitchController;
        wifiApSaFamilySharingSwitchController.setHost(this);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "onSwitchChanged: ", "WifiApSaAutoHotspotSettings", z);
        if (!z) {
            WifiApSmartTetheringApkUtils.setFamilySharingSwitchChangedAutomatically(
                    this.mContext, WifiApFrameworkUtils.isFamilySharingSetOn(this.mContext));
        }
        this.mWifiApAutoHotspotSwitchEnabler.setChecked(z);
        if (WifiApFrameworkUtils.isAutoHotspotSetOn(this.mContext)
                && WifiApSmartTetheringApkUtils.isFamilySharingSwitchChangedAutomatically(
                        this.mContext)) {
            this.mWifiApSaFamilySharingSwitchController.setChecked(true);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Log.i("WifiApSaAutoHotspotSettings", "onCreate");
        super.onCreate(bundle);
        this.mIsAutoHotspotSupported = true;
        FragmentActivity activity = getActivity();
        this.mActivity = activity;
        Context applicationContext = activity.getApplicationContext();
        this.mContext = applicationContext;
        this.mActivity.setTitle(
                WifiApUtils.getString(applicationContext, R.string.wifi_ap_smart_tethering_title));
        this.mPreviousAllowedDeviceList = new ArrayList();
        this.mAutoHotspotDescriptionLayoutPreference =
                (LayoutPreference) findPreference("auto_hotspot_description_layout_preference");
        this.mMyAccountPreferenceCategory =
                (SecPreferenceCategory) findPreference("my_account_preference_category");
        this.mMySaGroupPreferenceCategory =
                (PreferenceCategory) findPreference("my_sa_family_preference_category");
        this.mDivider = (LayoutPreference) findPreference("divider_preference");
        this.mAddAllowedDeviceDescription =
                (LayoutPreference) findPreference("allowed_device_description_preference");
        WifiApTextViewButtonLayoutPreference wifiApTextViewButtonLayoutPreference =
                (WifiApTextViewButtonLayoutPreference)
                        findPreference("add_allowed_device_button_layout_preference");
        this.mAddAllowedDeviceButtonLayoutPreference = wifiApTextViewButtonLayoutPreference;
        wifiApTextViewButtonLayoutPreference.setTitle(
                R.string.wifi_ap_mhs_add_allowed_device_title);
        this.mAddAllowedDeviceButtonLayoutPreference.setOnPreferenceClickListener(
                new AnonymousClass1(this, 3));
        ((TextView) this.mAddAllowedDeviceDescription.mRootView.findViewById(R.id.title))
                .setText(
                        WifiApUtils.getString(
                                this.mContext,
                                R.string.wifi_ap_mhs_add_allowed_device_description));
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        LayoutInflater layoutInflater =
                (LayoutInflater) getActivity().getSystemService("layout_inflater");
        if (i != 11) {
            return null;
        }
        Log.i(
                "WifiApSaAutoHotspotSettings",
                "onCreateDialog() : create dialog  DIALOG_MODIFY_WHITE_LIST");
        View inflate =
                layoutInflater.inflate(
                        R.layout.sec_wifi_ap_auto_hotspot_white_list_edit_dialog, (ViewGroup) null);
        this.mDeviceNameOnDialogEditText = (EditText) inflate.findViewById(R.id.name);
        this.mErrorTextOnDialogView = (TextView) inflate.findViewById(R.id.error_text);
        this.mDeviceNameOnDialogEditText.setText(this.mAllowedDeviceClicked.getName());
        this.mDeviceNameOnDialogEditText.requestFocus();
        this.mDeviceNameOnDialogEditText.addTextChangedListener(this.dialogNameTextWatcher);
        EditText editText = this.mDeviceNameOnDialogEditText;
        editText.setFilters(new InputFilter[] {new Utils.EmojiInputFilter(editText.getContext())});
        FragmentActivity activity = getActivity();
        boolean z = WifiApSettingsUtils.DBG;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, 0);
        builder.setTitle(R.string.wifi_tether_otherdevices_name);
        builder.setView(inflate);
        builder.setPositiveButton(
                R.string.wifi_save,
                new DialogInterface.OnClickListener() { // from class:
                    // com.samsung.android.settings.wifi.mobileap.autohotspot.safamily.WifiApSaAutoHotspotSettings.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        if (WifiApSaAutoHotspotSettings.this.mAllowedDeviceClicked != null) {
                            if (SemWifiManager.MHSDBG) {
                                int i3 = WifiApSaAutoHotspotSettings.$r8$clinit;
                                Log.d(
                                        "WifiApSaAutoHotspotSettings",
                                        "SmartWhiteListClicked`s onClick() : (Modify) Removing"
                                            + " Device Name-> "
                                                + WifiApSaAutoHotspotSettings.this
                                                        .mAllowedDeviceClicked.getName()
                                                + " mac-> "
                                                + WifiApSaAutoHotspotSettings.this
                                                        .mAllowedDeviceClicked.getMac());
                            }
                            SemWifiApSmartWhiteList.getInstance()
                                    .modifyWhiteList(
                                            WifiApSaAutoHotspotSettings.this.mAllowedDeviceClicked
                                                    .getMac(),
                                            WifiApSaAutoHotspotSettings.this
                                                    .mDeviceNameOnDialogEditText
                                                    .getText()
                                                    .toString());
                        }
                    }
                });
        builder.setNegativeButton(R.string.wifi_cancel, new AnonymousClass3());
        AlertDialog create = builder.create();
        this.mDeviceNameDialog = create;
        create.getWindow().setSoftInputMode(21);
        return this.mDeviceNameDialog;
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        Log.i("WifiApSaAutoHotspotSettings", "onDestroyView");
        super.onDestroyView();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        Log.i("WifiApSaAutoHotspotSettings", "onPause");
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
        }
        super.onPause();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Log.i("WifiApSaAutoHotspotSettings", "onResume");
        this.mAddAllowedDeviceButtonClicked = false;
        this.mWifiApAutoHotspotSwitchEnabler.updateSwitchState();
        updateFamilySharingSection(true);
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.addOnSwitchChangeListener(this);
        }
    }

    public final void updateAutoHotspotSection$1() {
        Log.i("WifiApSaAutoHotspotSettings", "Updating AutoHotspot section.");
        WifiApFamilyMember familyOwner = WifiApSmartTetheringApkUtils.getFamilyOwner(this.mContext);
        Preference preference = new Preference(getPrefContext());
        preference.setTitle(familyOwner.mName);
        preference.setIcon(familyOwner.getPhoto());
        preference.setKey(familyOwner.mGuid);
        preference.setOnPreferenceClickListener(
                new Preference.OnPreferenceClickListener() { // from class:
                    // com.samsung.android.settings.wifi.mobileap.autohotspot.safamily.WifiApSaAutoHotspotSettings$$ExternalSyntheticLambda0
                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public final boolean onPreferenceClick(Preference preference2) {
                        int i = WifiApSaAutoHotspotSettings.$r8$clinit;
                        WifiApSaAutoHotspotSettings wifiApSaAutoHotspotSettings =
                                WifiApSaAutoHotspotSettings.this;
                        wifiApSaAutoHotspotSettings.getClass();
                        Log.i(
                                "WifiApSaAutoHotspotSettings",
                                "AutoHotspot account owner profile clicked.");
                        FragmentActivity fragmentActivity = wifiApSaAutoHotspotSettings.mActivity;
                        boolean z = WifiApSettingsUtils.DBG;
                        Log.i("WifiApSettingsUtils", "Launching Samsung Account Settings Activity");
                        Intent intent =
                                new Intent(
                                        "com.samsung.android.mobileservice.action.ACTION_OPEN_SASETTINGS");
                        intent.setPackage("com.osp.app.signin");
                        intent.setFlags(603979776);
                        fragmentActivity.startActivity(intent);
                        return true;
                    }
                });
        if (this.mMyAccountPreferenceCategory.getPreferenceCount() <= 0) {
            this.mMyAccountPreferenceCategory.addPreference(preference);
            return;
        }
        String key = preference.getKey();
        if (key == null) {
            key = ApnSettings.MVNO_NONE;
        }
        Preference findPreference = this.mMyAccountPreferenceCategory.findPreference(key);
        Preference preference2 = this.mMyAccountPreferenceCategory.getPreference(0);
        if (findPreference != null
                && preference2.getTitle().equals(preference.getTitle())
                && preference2.getKey().equals(preference.getKey())) {
            return;
        }
        this.mMyAccountPreferenceCategory.removeAll();
        this.mMyAccountPreferenceCategory.addPreference(preference);
    }

    public final void updateFamilySharingSection(boolean z) {
        WifiApSaFamilySharingSwitchController wifiApSaFamilySharingSwitchController;
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "Updating Family Sharing section.val:", "WifiApSaAutoHotspotSettings", z);
        if (z
                && (wifiApSaFamilySharingSwitchController =
                                this.mWifiApSaFamilySharingSwitchController)
                        != null) {
            wifiApSaFamilySharingSwitchController.updateStates();
        }
        boolean isFamilySharingSetOn = WifiApFrameworkUtils.isFamilySharingSetOn(this.mContext);
        boolean isAutoHotspotSetOn = WifiApFrameworkUtils.isAutoHotspotSetOn(this.mContext);
        boolean z2 = isFamilySharingSetOn && isAutoHotspotSetOn;
        if (!this.mIsAutoHotspotSupported || !z2) {
            this.mDivider.setVisible(false);
            this.mAddAllowedDeviceDescription.setVisible(false);
            Iterator it = ((ArrayList) this.mPreviousAllowedDeviceList).iterator();
            while (it.hasNext()) {
                this.mMySaGroupPreferenceCategory.removePreferenceRecursively(
                        ((SemWifiApSmartWhiteList.SmartWhiteList) it.next()).getMac());
            }
            this.mAddAllowedDeviceButtonLayoutPreference.setVisible(false);
            return;
        }
        this.mDivider.setVisible(true);
        this.mAddAllowedDeviceDescription.setVisible(true);
        ArrayList arrayList = new ArrayList();
        Iterator iterator = SemWifiApSmartWhiteList.getInstance().getIterator();
        if (iterator != null) {
            while (iterator.hasNext()) {
                arrayList.add((SemWifiApSmartWhiteList.SmartWhiteList) iterator.next());
            }
        }
        if (!((ArrayList) this.mPreviousAllowedDeviceList).equals(arrayList)) {
            Iterator it2 = ((ArrayList) this.mPreviousAllowedDeviceList).iterator();
            while (it2.hasNext()) {
                this.mMySaGroupPreferenceCategory.removePreferenceRecursively(
                        ((SemWifiApSmartWhiteList.SmartWhiteList) it2.next()).getMac());
            }
            Iterator it3 = arrayList.iterator();
            int i = 100;
            while (it3.hasNext()) {
                WifiApCustomPreference wifiApCustomPreference =
                        new WifiApCustomPreference(
                                getPrefContext(),
                                (SemWifiApSmartWhiteList.SmartWhiteList) it3.next(),
                                this.mAutoHotspotCustomPreferenceListener);
                wifiApCustomPreference.setOrder(i);
                wifiApCustomPreference.setEnabled(isAutoHotspotSetOn);
                this.mMySaGroupPreferenceCategory.addPreference(wifiApCustomPreference);
                i++;
            }
        }
        this.mPreviousAllowedDeviceList = arrayList;
        this.mAddAllowedDeviceButtonLayoutPreference.setVisible(true);
    }
}

package com.samsung.android.settings.wifi.details;

import android.content.Context;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.net.InetAddresses;
import android.net.IpConfiguration;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.ProxyInfo;
import android.net.RouteInfo;
import android.net.StaticIpConfiguration;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Debug;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.picker3.widget.SeslColorPicker$16$$ExternalSyntheticOutline0;
import androidx.preference.DropDownPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.wifi.WifiUtils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.LayoutPreference;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.knox.IMiscPolicy;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.widget.SecBottomBarButton;
import com.samsung.android.settings.wifi.SecWifiUnclickablePreference;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.WifiIssueDetectorUtil;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiNetworkConfigPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin, TextWatcher {
    public static final boolean DBG = Debug.semIsProductDev();
    public static final Object sMiscPolicyLock = new Object();
    public static volatile IMiscPolicy sMiscPolicyService;
    public boolean mAutoGenFirstTime;
    public SecWifiPreferenceControllerHelper.AnonymousClass1 mCallback;
    public SecBottomBarButton mCancelButton;
    public WifiConfiguration mConfig;
    public final ConnectivityManager mConnectivityManager;
    public final Context mContext;
    public EditText mDns1View;
    public TextView mDns2View;
    public final Fragment mFragment;
    public EditText mGatewayView;
    public ProxyInfo mHttpProxy;
    public final WifiImeHelper mImeHelper;
    public EditText mIpAddressView;
    public IpConfiguration.IpAssignment mIpAssignment;
    public DropDownPreference mIpSettingDropdown;
    public final List mIpSettingPrefs;
    public final WifiNetworkConfigPreferenceController$$ExternalSyntheticLambda2
            mIpSettingsDropDownListeners;
    public Preference mIpSettingsInset;
    public WifiNetworkDetailsFragment mListener;
    public final AnonymousClass1 mListenerForHidingKeyboard;
    public EditText mNetworkPrefixLengthView;
    public TextView mProxyExclusionListView;
    public TextView mProxyHostNameView;
    public TextView mProxyPacView;
    public EditText mProxyPortView;
    public DropDownPreference mProxySettingDropdown;
    public final List mProxySettingPrefs;
    public IpConfiguration.ProxySettings mProxySettings;
    public final WifiNetworkConfigPreferenceController$$ExternalSyntheticLambda2
            mProxySettingsDropDownListeners;
    public Preference mProxySettingsInset;
    public final String mSAScreenId;
    public SecBottomBarButton mSaveButton;
    public PreferenceScreen mScreen;
    public StaticIpConfiguration mStaticIpConfiguration;
    public final WifiEntry mWifiEntry;
    public final WifiManager mWifiManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class IpConfigFields {
        public String dns1;
        public String dns2;
        public String gateway;
        public String ipv4Address;
        public String networkPrefix;

        public final String toString() {
            return "ipv4Address="
                    + this.ipv4Address
                    + " networkPrefix="
                    + this.networkPrefix
                    + " gateway="
                    + this.gateway
                    + " dns1="
                    + this.dns1
                    + " dns2="
                    + this.dns2;
        }
    }

    /* JADX WARN: Type inference failed for: r0v5, types: [com.samsung.android.settings.wifi.details.WifiNetworkConfigPreferenceController$$ExternalSyntheticLambda2] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.samsung.android.settings.wifi.details.WifiNetworkConfigPreferenceController$$ExternalSyntheticLambda2] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.samsung.android.settings.wifi.details.WifiNetworkConfigPreferenceController$1] */
    public WifiNetworkConfigPreferenceController(
            WifiEntry wifiEntry,
            ConnectivityManager connectivityManager,
            Context context,
            Fragment fragment,
            WifiImeHelper wifiImeHelper,
            String str) {
        super(context);
        this.mIpAssignment = IpConfiguration.IpAssignment.UNASSIGNED;
        this.mStaticIpConfiguration = null;
        this.mProxySettings = IpConfiguration.ProxySettings.UNASSIGNED;
        this.mHttpProxy = null;
        this.mIpSettingPrefs = new ArrayList();
        this.mProxySettingPrefs = new ArrayList();
        this.mAutoGenFirstTime = false;
        final int i = 0;
        this.mIpSettingsDropDownListeners =
                new Preference.OnPreferenceChangeListener(
                        this) { // from class:
                                // com.samsung.android.settings.wifi.details.WifiNetworkConfigPreferenceController$$ExternalSyntheticLambda2
                    public final /* synthetic */ WifiNetworkConfigPreferenceController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference, Object obj) {
                        int i2 = i;
                        WifiNetworkConfigPreferenceController
                                wifiNetworkConfigPreferenceController = this.f$0;
                        wifiNetworkConfigPreferenceController.getClass();
                        switch (i2) {
                            case 0:
                                Log.d(
                                        "WifiNetworkConfigPrefCtrl",
                                        "mIpSettingsDropDownListeners called");
                                int parseInt = Integer.parseInt((String) obj);
                                HashMap hashMap = new HashMap();
                                hashMap.put(
                                        "ip_settings_type",
                                        (parseInt == 0
                                                        ? IpConfiguration.IpAssignment.DHCP
                                                        : parseInt == 1
                                                                ? IpConfiguration.IpAssignment
                                                                        .STATIC
                                                                : wifiNetworkConfigPreferenceController
                                                                        .mIpAssignment)
                                                .name());
                                SALogging.insertSALog(
                                        wifiNetworkConfigPreferenceController.mSAScreenId,
                                        "1027",
                                        hashMap,
                                        0);
                                WifiConfiguration wifiConfiguration =
                                        wifiNetworkConfigPreferenceController.mConfig;
                                if (wifiConfiguration != null) {
                                    wifiNetworkConfigPreferenceController.mIpAssignment =
                                            wifiConfiguration
                                                    .getIpConfiguration()
                                                    .getIpAssignment();
                                }
                                wifiNetworkConfigPreferenceController.setIpConfigFields();
                                wifiNetworkConfigPreferenceController.mAutoGenFirstTime = true;
                                wifiNetworkConfigPreferenceController.mIpAssignment =
                                        parseInt == 0
                                                ? IpConfiguration.IpAssignment.DHCP
                                                : parseInt == 1
                                                        ? IpConfiguration.IpAssignment.STATIC
                                                        : wifiNetworkConfigPreferenceController
                                                                .mIpAssignment;
                                wifiNetworkConfigPreferenceController.updateIpPreference(parseInt);
                                wifiNetworkConfigPreferenceController.updateBottomMode(2);
                                wifiNetworkConfigPreferenceController.enableSaveIfAppropriate();
                                break;
                            default:
                                Log.d(
                                        "WifiNetworkConfigPrefCtrl",
                                        "mProxySettingsDropDownListeners called");
                                int parseInt2 = Integer.parseInt((String) obj);
                                HashMap hashMap2 = new HashMap();
                                hashMap2.put(
                                        "proxy_type",
                                        (parseInt2 == 0
                                                        ? IpConfiguration.ProxySettings.NONE
                                                        : parseInt2 == 1
                                                                ? IpConfiguration.ProxySettings
                                                                        .STATIC
                                                                : parseInt2 == 2
                                                                        ? IpConfiguration
                                                                                .ProxySettings.PAC
                                                                        : wifiNetworkConfigPreferenceController
                                                                                .mProxySettings)
                                                .name());
                                SALogging.insertSALog(
                                        wifiNetworkConfigPreferenceController.mSAScreenId,
                                        "1027",
                                        hashMap2,
                                        0);
                                wifiNetworkConfigPreferenceController.initProxySettingOverride();
                                wifiNetworkConfigPreferenceController.setProxyConfigFields();
                                wifiNetworkConfigPreferenceController.mProxySettings =
                                        parseInt2 == 0
                                                ? IpConfiguration.ProxySettings.NONE
                                                : parseInt2 == 1
                                                        ? IpConfiguration.ProxySettings.STATIC
                                                        : parseInt2 == 2
                                                                ? IpConfiguration.ProxySettings.PAC
                                                                : wifiNetworkConfigPreferenceController
                                                                        .mProxySettings;
                                wifiNetworkConfigPreferenceController.updateProxyPreference(
                                        parseInt2);
                                wifiNetworkConfigPreferenceController.updateBottomMode(2);
                                wifiNetworkConfigPreferenceController.enableSaveIfAppropriate();
                                break;
                        }
                        return true;
                    }
                };
        final int i2 = 1;
        this.mProxySettingsDropDownListeners =
                new Preference.OnPreferenceChangeListener(
                        this) { // from class:
                                // com.samsung.android.settings.wifi.details.WifiNetworkConfigPreferenceController$$ExternalSyntheticLambda2
                    public final /* synthetic */ WifiNetworkConfigPreferenceController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference, Object obj) {
                        int i22 = i2;
                        WifiNetworkConfigPreferenceController
                                wifiNetworkConfigPreferenceController = this.f$0;
                        wifiNetworkConfigPreferenceController.getClass();
                        switch (i22) {
                            case 0:
                                Log.d(
                                        "WifiNetworkConfigPrefCtrl",
                                        "mIpSettingsDropDownListeners called");
                                int parseInt = Integer.parseInt((String) obj);
                                HashMap hashMap = new HashMap();
                                hashMap.put(
                                        "ip_settings_type",
                                        (parseInt == 0
                                                        ? IpConfiguration.IpAssignment.DHCP
                                                        : parseInt == 1
                                                                ? IpConfiguration.IpAssignment
                                                                        .STATIC
                                                                : wifiNetworkConfigPreferenceController
                                                                        .mIpAssignment)
                                                .name());
                                SALogging.insertSALog(
                                        wifiNetworkConfigPreferenceController.mSAScreenId,
                                        "1027",
                                        hashMap,
                                        0);
                                WifiConfiguration wifiConfiguration =
                                        wifiNetworkConfigPreferenceController.mConfig;
                                if (wifiConfiguration != null) {
                                    wifiNetworkConfigPreferenceController.mIpAssignment =
                                            wifiConfiguration
                                                    .getIpConfiguration()
                                                    .getIpAssignment();
                                }
                                wifiNetworkConfigPreferenceController.setIpConfigFields();
                                wifiNetworkConfigPreferenceController.mAutoGenFirstTime = true;
                                wifiNetworkConfigPreferenceController.mIpAssignment =
                                        parseInt == 0
                                                ? IpConfiguration.IpAssignment.DHCP
                                                : parseInt == 1
                                                        ? IpConfiguration.IpAssignment.STATIC
                                                        : wifiNetworkConfigPreferenceController
                                                                .mIpAssignment;
                                wifiNetworkConfigPreferenceController.updateIpPreference(parseInt);
                                wifiNetworkConfigPreferenceController.updateBottomMode(2);
                                wifiNetworkConfigPreferenceController.enableSaveIfAppropriate();
                                break;
                            default:
                                Log.d(
                                        "WifiNetworkConfigPrefCtrl",
                                        "mProxySettingsDropDownListeners called");
                                int parseInt2 = Integer.parseInt((String) obj);
                                HashMap hashMap2 = new HashMap();
                                hashMap2.put(
                                        "proxy_type",
                                        (parseInt2 == 0
                                                        ? IpConfiguration.ProxySettings.NONE
                                                        : parseInt2 == 1
                                                                ? IpConfiguration.ProxySettings
                                                                        .STATIC
                                                                : parseInt2 == 2
                                                                        ? IpConfiguration
                                                                                .ProxySettings.PAC
                                                                        : wifiNetworkConfigPreferenceController
                                                                                .mProxySettings)
                                                .name());
                                SALogging.insertSALog(
                                        wifiNetworkConfigPreferenceController.mSAScreenId,
                                        "1027",
                                        hashMap2,
                                        0);
                                wifiNetworkConfigPreferenceController.initProxySettingOverride();
                                wifiNetworkConfigPreferenceController.setProxyConfigFields();
                                wifiNetworkConfigPreferenceController.mProxySettings =
                                        parseInt2 == 0
                                                ? IpConfiguration.ProxySettings.NONE
                                                : parseInt2 == 1
                                                        ? IpConfiguration.ProxySettings.STATIC
                                                        : parseInt2 == 2
                                                                ? IpConfiguration.ProxySettings.PAC
                                                                : wifiNetworkConfigPreferenceController
                                                                        .mProxySettings;
                                wifiNetworkConfigPreferenceController.updateProxyPreference(
                                        parseInt2);
                                wifiNetworkConfigPreferenceController.updateBottomMode(2);
                                wifiNetworkConfigPreferenceController.enableSaveIfAppropriate();
                                break;
                        }
                        return true;
                    }
                };
        this.mListenerForHidingKeyboard =
                new Preference
                        .OnPreferenceClickListener() { // from class:
                                                       // com.samsung.android.settings.wifi.details.WifiNetworkConfigPreferenceController.1
                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public final boolean onPreferenceClick(Preference preference) {
                        WifiNetworkConfigPreferenceController
                                wifiNetworkConfigPreferenceController =
                                        WifiNetworkConfigPreferenceController.this;
                        View currentFocus =
                                wifiNetworkConfigPreferenceController
                                        .mFragment
                                        .getActivity()
                                        .getCurrentFocus();
                        wifiNetworkConfigPreferenceController.mImeHelper.hideSoftKeyboard(
                                currentFocus);
                        if (currentFocus == null) {
                            return false;
                        }
                        currentFocus.clearFocus();
                        return false;
                    }
                };
        this.mContext = context;
        this.mWifiEntry = wifiEntry;
        if (wifiEntry != null) {
            this.mConfig = wifiEntry.getWifiConfiguration();
        }
        this.mConnectivityManager = connectivityManager;
        this.mWifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
        this.mFragment = fragment;
        this.mImeHelper = wifiImeHelper;
        this.mSAScreenId = str;
    }

    public static boolean checkIp4vAddress(String str) {
        for (int i = 0; i < str.length() && str.charAt(i) != ':'; i++) {
            if (str.charAt(i) == '.') {
                return true;
            }
        }
        return false;
    }

    public static String convertToIpv4Address(int i, String str) {
        int length = str.length();
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            if (str.charAt(i2) == ',') {
                i2 += 2;
                i3 = i2;
            }
            if (str.charAt(i2) == '/' && i3 != 0) {
                return i == 1 ? str.substring(i3, i2) : str.substring(i2 + 1, length - 1);
            }
            i2++;
        }
        return null;
    }

    public static Inet4Address getIPv4Address(String str) {
        try {
            return (Inet4Address) InetAddresses.parseNumericAddress(str);
        } catch (ClassCastException | IllegalArgumentException unused) {
            return null;
        }
    }

    @Override // android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
        DropDownPreference dropDownPreference;
        DropDownPreference dropDownPreference2 = this.mIpSettingDropdown;
        if (dropDownPreference2 != null
                && dropDownPreference2.isVisible()
                && (dropDownPreference = this.mProxySettingDropdown) != null
                && dropDownPreference.isVisible()) {
            updateBottomMode(2);
        }
        enableSaveIfAppropriate();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mScreen = preferenceScreen;
        this.mIpSettingDropdown =
                (DropDownPreference) preferenceScreen.findPreference("ip_settings");
        this.mIpSettingsInset = preferenceScreen.findPreference("ip_inset");
        DropDownPreference dropDownPreference = this.mIpSettingDropdown;
        if (dropDownPreference != null) {
            dropDownPreference.setOnPreferenceChangeListener(this.mIpSettingsDropDownListeners);
            this.mIpSettingDropdown.setOnPreferenceClickListener(this.mListenerForHidingKeyboard);
        }
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference("wifi_ip_address");
        ((TextView) layoutPreference.mRootView.findViewById(R.id.title))
                .setText(R.string.wifi_ip_address);
        this.mIpAddressView = (EditText) layoutPreference.mRootView.findViewById(R.id.edittext);
        ((ArrayList) this.mIpSettingPrefs).add(layoutPreference);
        LayoutPreference layoutPreference2 =
                (LayoutPreference) preferenceScreen.findPreference("gateway");
        ((TextView) layoutPreference2.mRootView.findViewById(R.id.title))
                .setText(R.string.wifi_gateway);
        this.mGatewayView = (EditText) layoutPreference2.mRootView.findViewById(R.id.edittext);
        ((ArrayList) this.mIpSettingPrefs).add(layoutPreference2);
        LayoutPreference layoutPreference3 =
                (LayoutPreference) preferenceScreen.findPreference("wifi_network_prefix_length");
        ((TextView) layoutPreference3.mRootView.findViewById(R.id.title))
                .setText(R.string.wifi_network_prefix_length);
        this.mNetworkPrefixLengthView =
                (EditText) layoutPreference3.mRootView.findViewById(R.id.edittext);
        ((ArrayList) this.mIpSettingPrefs).add(layoutPreference3);
        LayoutPreference layoutPreference4 =
                (LayoutPreference) preferenceScreen.findPreference("wifi_dns1");
        ((TextView) layoutPreference4.mRootView.findViewById(R.id.title))
                .setText(R.string.wifi_dns1);
        this.mDns1View = (EditText) layoutPreference4.mRootView.findViewById(R.id.edittext);
        ((ArrayList) this.mIpSettingPrefs).add(layoutPreference4);
        LayoutPreference layoutPreference5 =
                (LayoutPreference) preferenceScreen.findPreference("wifi_dns2");
        ((TextView) layoutPreference5.mRootView.findViewById(R.id.title))
                .setText(R.string.wifi_dns2);
        this.mDns2View = (EditText) layoutPreference5.mRootView.findViewById(R.id.edittext);
        ((ArrayList) this.mIpSettingPrefs).add(layoutPreference5);
        this.mProxySettingDropdown =
                (DropDownPreference) preferenceScreen.findPreference("proxy_settings");
        this.mProxySettingsInset = preferenceScreen.findPreference("proxy_inset");
        DropDownPreference dropDownPreference2 = this.mProxySettingDropdown;
        if (dropDownPreference2 != null) {
            dropDownPreference2.setOnPreferenceChangeListener(this.mProxySettingsDropDownListeners);
            this.mProxySettingDropdown.setOnPreferenceClickListener(
                    this.mListenerForHidingKeyboard);
        }
        SecWifiUnclickablePreference secWifiUnclickablePreference =
                (SecWifiUnclickablePreference)
                        preferenceScreen.findPreference("proxy_warning_limited_support");
        secWifiUnclickablePreference.mTitleColor =
                this.mContext.getColor(R.color.sec_widget_body_text_color);
        ((ArrayList) this.mProxySettingPrefs).add(secWifiUnclickablePreference);
        LayoutPreference layoutPreference6 =
                (LayoutPreference) preferenceScreen.findPreference("proxy_pac");
        ((TextView) layoutPreference6.mRootView.findViewById(R.id.title))
                .setText(R.string.proxy_url_title);
        this.mProxyPacView = (TextView) layoutPreference6.mRootView.findViewById(R.id.edittext);
        ((ArrayList) this.mProxySettingPrefs).add(layoutPreference6);
        LayoutPreference layoutPreference7 =
                (LayoutPreference) preferenceScreen.findPreference("proxy_hostname");
        ((TextView) layoutPreference7.mRootView.findViewById(R.id.title))
                .setText(R.string.proxy_hostname_label);
        this.mProxyHostNameView =
                (TextView) layoutPreference7.mRootView.findViewById(R.id.edittext);
        ((ArrayList) this.mProxySettingPrefs).add(layoutPreference7);
        LayoutPreference layoutPreference8 =
                (LayoutPreference) preferenceScreen.findPreference("proxy_port");
        ((TextView) layoutPreference8.mRootView.findViewById(R.id.title))
                .setText(R.string.proxy_port_label);
        this.mProxyPortView = (EditText) layoutPreference8.mRootView.findViewById(R.id.edittext);
        ((ArrayList) this.mProxySettingPrefs).add(layoutPreference8);
        LayoutPreference layoutPreference9 =
                (LayoutPreference) preferenceScreen.findPreference("proxy_exclusionlist");
        ((TextView) layoutPreference9.mRootView.findViewById(R.id.title))
                .setText(R.string.proxy_exclusionlist_label);
        this.mProxyExclusionListView =
                (TextView) layoutPreference9.mRootView.findViewById(R.id.edittext);
        ((ArrayList) this.mProxySettingPrefs).add(layoutPreference9);
    }

    public final void enableSaveIfAppropriate() {
        boolean z;
        boolean ipAndProxyFieldsAreValid = ipAndProxyFieldsAreValid();
        SecWifiPreferenceControllerHelper.AnonymousClass1 anonymousClass1 = this.mCallback;
        if (anonymousClass1 != null) {
            SecWifiPreferenceControllerHelper secWifiPreferenceControllerHelper =
                    SecWifiPreferenceControllerHelper.this;
            ((HashMap) secWifiPreferenceControllerHelper.mValidators)
                    .put(this, Boolean.valueOf(ipAndProxyFieldsAreValid));
            SecWifiPreferenceControllerHelper.ValidationUpdater validationUpdater =
                    (SecWifiPreferenceControllerHelper.ValidationUpdater)
                            ((HashMap) secWifiPreferenceControllerHelper.mValidationUpdaters)
                                    .get(this);
            Iterator it =
                    ((HashMap) secWifiPreferenceControllerHelper.mValidators).values().iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = true;
                    break;
                } else if (!((Boolean) it.next()).booleanValue()) {
                    z = false;
                    break;
                }
            }
            validationUpdater.update(z);
        }
        SecBottomBarButton secBottomBarButton = this.mSaveButton;
        if (secBottomBarButton == null) {
            return;
        }
        secBottomBarButton.setEnabled(ipAndProxyFieldsAreValid);
    }

    public final void generateBlankFields(Inet4Address inet4Address) {
        Log.d("WifiNetworkConfigPrefCtrl", "generateBlankFields");
        if (TextUtils.isEmpty(this.mNetworkPrefixLengthView.getText().toString())) {
            this.mNetworkPrefixLengthView.setText(R.string.wifi_network_prefix_length_hint);
            SeslColorPicker$16$$ExternalSyntheticOutline0.m(this.mNetworkPrefixLengthView);
        } else {
            try {
                if (Integer.parseInt(this.mNetworkPrefixLengthView.getText().toString()) >= 0) {}
            } catch (NumberFormatException unused) {
            }
        }
        if (TextUtils.isEmpty(this.mGatewayView.getText().toString())) {
            try {
                byte[] address = inet4Address.getAddress();
                address[address.length - 1] = 1;
                this.mGatewayView.setText(InetAddress.getByAddress(address).getHostAddress());
                EditText editText = this.mGatewayView;
                editText.setSelection(editText.getText().length());
            } catch (RuntimeException | UnknownHostException unused2) {
            }
        }
        if (TextUtils.isEmpty(this.mDns1View.getText().toString())) {
            this.mDns1View.setText(R.string.wifi_dns1_hint);
            SeslColorPicker$16$$ExternalSyntheticOutline0.m(this.mDns1View);
        }
        SeslColorPicker$16$$ExternalSyntheticOutline0.m(this.mIpAddressView);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "network_config";
    }

    public final void initProxySettingOverride() {
        WifiConfiguration wifiConfiguration = this.mConfig;
        if (wifiConfiguration != null) {
            IpConfiguration.ProxySettings proxySettings =
                    wifiConfiguration.getIpConfiguration().getProxySettings();
            IpConfiguration.ProxySettings proxySettings2 = IpConfiguration.ProxySettings.STATIC;
            if (proxySettings == proxySettings2) {
                this.mProxySettings = proxySettings2;
                return;
            }
            IpConfiguration.ProxySettings proxySettings3 =
                    this.mConfig.getIpConfiguration().getProxySettings();
            IpConfiguration.ProxySettings proxySettings4 = IpConfiguration.ProxySettings.PAC;
            if (proxySettings3 == proxySettings4) {
                this.mProxySettings = proxySettings4;
            } else {
                this.mProxySettings = IpConfiguration.ProxySettings.NONE;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:131:0x0211, code lost:

       if (r2 <= 65535) goto L107;
    */
    /* JADX WARN: Removed duplicated region for block: B:105:0x023a  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x024b A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0250  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x019e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x010c A[Catch: all -> 0x00bc, TRY_ENTER, TryCatch #7 {all -> 0x00bc, blocks: (B:34:0x009e, B:39:0x00b3, B:40:0x00e2, B:72:0x00f2, B:42:0x010c, B:46:0x0116, B:49:0x011d, B:50:0x0120, B:52:0x013d, B:55:0x0144, B:58:0x0165, B:60:0x016d, B:63:0x017f, B:64:0x0182, B:67:0x015a, B:70:0x0162, B:77:0x00d4), top: B:33:0x009e }] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x016d A[Catch: all -> 0x00bc, TryCatch #7 {all -> 0x00bc, blocks: (B:34:0x009e, B:39:0x00b3, B:40:0x00e2, B:72:0x00f2, B:42:0x010c, B:46:0x0116, B:49:0x011d, B:50:0x0120, B:52:0x013d, B:55:0x0144, B:58:0x0165, B:60:0x016d, B:63:0x017f, B:64:0x0182, B:67:0x015a, B:70:0x0162, B:77:0x00d4), top: B:33:0x009e }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x015a A[Catch: all -> 0x00bc, TryCatch #7 {all -> 0x00bc, blocks: (B:34:0x009e, B:39:0x00b3, B:40:0x00e2, B:72:0x00f2, B:42:0x010c, B:46:0x0116, B:49:0x011d, B:50:0x0120, B:52:0x013d, B:55:0x0144, B:58:0x0165, B:60:0x016d, B:63:0x017f, B:64:0x0182, B:67:0x015a, B:70:0x0162, B:77:0x00d4), top: B:33:0x009e }] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00f2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean ipAndProxyFieldsAreValid() {
        /*
            Method dump skipped, instructions count: 623
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.details.WifiNetworkConfigPreferenceController.ipAndProxyFieldsAreValid():boolean");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        WifiEntry wifiEntry = this.mWifiEntry;
        return wifiEntry == null
                || !(wifiEntry.isSubscription() || this.mWifiEntry.semIsEphemeral());
    }

    public final void setIpConfigFields() {
        WifiConfiguration wifiConfiguration = this.mConfig;
        if (wifiConfiguration != null) {
            StaticIpConfiguration staticIpConfiguration =
                    wifiConfiguration.getIpConfiguration().getStaticIpConfiguration();
            LinkProperties linkProperties =
                    this.mConnectivityManager.getLinkProperties(
                            this.mWifiManager.getCurrentNetwork());
            IpConfigFields ipConfigFields = new IpConfigFields();
            String str = null;
            if (this.mIpAssignment == IpConfiguration.IpAssignment.DHCP && linkProperties != null) {
                Iterator<LinkAddress> it = linkProperties.getLinkAddresses().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    LinkAddress next = it.next();
                    String linkAddress = next.toString();
                    if (DBG) {
                        DialogFragment$$ExternalSyntheticOutline0.m(
                                "setIpConfigFields ip ", linkAddress, "WifiNetworkConfigPrefCtrl");
                    }
                    if (checkIp4vAddress(linkAddress)) {
                        ipConfigFields.ipv4Address = next.getAddress().getHostAddress();
                        ipConfigFields.networkPrefix = Integer.toString(next.getPrefixLength());
                        break;
                    } else {
                        ipConfigFields.ipv4Address = convertToIpv4Address(1, next.toString());
                        ipConfigFields.networkPrefix = convertToIpv4Address(2, next.toString());
                    }
                }
                for (RouteInfo routeInfo : linkProperties.getRoutes()) {
                    String hostAddress = routeInfo.getGateway().getHostAddress();
                    if (DBG) {
                        DialogFragment$$ExternalSyntheticOutline0.m(
                                "setIpConfigFields gw ", hostAddress, "WifiNetworkConfigPrefCtrl");
                    }
                    if (routeInfo.isDefaultRoute()) {
                        if (!checkIp4vAddress(hostAddress)) {
                            hostAddress = convertToIpv4Address(1, hostAddress);
                        }
                        ipConfigFields.gateway = hostAddress;
                    }
                }
                Iterator<InetAddress> it2 = linkProperties.getDnsServers().iterator();
                if (it2.hasNext()) {
                    InetAddress next2 = it2.next();
                    String hostAddress2 = next2.getHostAddress();
                    if (DBG) {
                        DialogFragment$$ExternalSyntheticOutline0.m(
                                "setIpConfigFields dns1 ",
                                hostAddress2,
                                "WifiNetworkConfigPrefCtrl");
                    }
                    if (next2.isLoopbackAddress() || !checkIp4vAddress(hostAddress2)) {
                        hostAddress2 = null;
                    }
                    ipConfigFields.dns1 = hostAddress2;
                }
                if (it2.hasNext()) {
                    InetAddress next3 = it2.next();
                    String hostAddress3 = next3.getHostAddress();
                    if (DBG) {
                        DialogFragment$$ExternalSyntheticOutline0.m(
                                "setIpConfigFields dns1 ",
                                hostAddress3,
                                "WifiNetworkConfigPrefCtrl");
                    }
                    if (!next3.isLoopbackAddress() && checkIp4vAddress(hostAddress3)) {
                        str = hostAddress3;
                    }
                    ipConfigFields.dns2 = str;
                }
                Log.d(
                        "WifiNetworkConfigPrefCtrl",
                        "setIpConfigFields by linkProperties : " + ipConfigFields.toString());
            } else if (staticIpConfiguration != null) {
                ipConfigFields.ipv4Address =
                        staticIpConfiguration.getIpAddress() != null
                                ? staticIpConfiguration.getIpAddress().getAddress().getHostAddress()
                                : null;
                ipConfigFields.networkPrefix =
                        staticIpConfiguration.getIpAddress() != null
                                ? Integer.toString(
                                        staticIpConfiguration.getIpAddress().getPrefixLength())
                                : null;
                ipConfigFields.gateway =
                        staticIpConfiguration.getGateway() != null
                                ? staticIpConfiguration.getGateway().getHostAddress()
                                : null;
                Iterator<InetAddress> it3 = staticIpConfiguration.getDnsServers().iterator();
                if (it3.hasNext()) {
                    InetAddress next4 = it3.next();
                    String hostAddress4 = next4.getHostAddress();
                    if (DBG) {
                        DialogFragment$$ExternalSyntheticOutline0.m(
                                "setIpConfigFields dns1 ",
                                hostAddress4,
                                "WifiNetworkConfigPrefCtrl");
                    }
                    if (next4.isLoopbackAddress() || !checkIp4vAddress(hostAddress4)) {
                        hostAddress4 = null;
                    }
                    ipConfigFields.dns1 = hostAddress4;
                }
                if (it3.hasNext()) {
                    InetAddress next5 = it3.next();
                    String hostAddress5 = next5.getHostAddress();
                    if (DBG) {
                        DialogFragment$$ExternalSyntheticOutline0.m(
                                "setIpConfigFields dns1 ",
                                hostAddress5,
                                "WifiNetworkConfigPrefCtrl");
                    }
                    if (!next5.isLoopbackAddress() && checkIp4vAddress(hostAddress5)) {
                        str = hostAddress5;
                    }
                    ipConfigFields.dns2 = str;
                }
                Log.d(
                        "WifiNetworkConfigPrefCtrl",
                        "setIpConfigFields by staticConfig : " + ipConfigFields.toString());
            }
            this.mIpAddressView.setText(ipConfigFields.ipv4Address);
            SeslColorPicker$16$$ExternalSyntheticOutline0.m(this.mIpAddressView);
            this.mGatewayView.setText(ipConfigFields.gateway);
            this.mNetworkPrefixLengthView.setText(ipConfigFields.networkPrefix);
            this.mDns1View.setText(ipConfigFields.dns1);
            this.mDns2View.setText(ipConfigFields.dns2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:40:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setProxyConfigFields() {
        /*
            r5 = this;
            android.net.wifi.WifiConfiguration r0 = r5.mConfig
            if (r0 == 0) goto Lc9
            android.net.ProxyInfo r0 = r0.getHttpProxy()
            android.net.wifi.WifiConfiguration r1 = r5.mConfig
            java.lang.String r1 = r1.SSID
            java.lang.String r2 = "\""
            java.lang.String r3 = ""
            java.lang.String r1 = r1.replace(r2, r3)
            com.samsung.android.knox.IMiscPolicy r2 = com.samsung.android.settings.wifi.details.WifiNetworkConfigPreferenceController.sMiscPolicyService
            if (r2 != 0) goto L32
            java.lang.Object r3 = com.samsung.android.settings.wifi.details.WifiNetworkConfigPreferenceController.sMiscPolicyLock
            monitor-enter(r3)
            com.samsung.android.knox.IMiscPolicy r2 = com.samsung.android.settings.wifi.details.WifiNetworkConfigPreferenceController.sMiscPolicyService     // Catch: java.lang.Throwable -> L2c
            if (r2 != 0) goto L2e
            java.lang.String r2 = "misc_policy"
            android.os.IBinder r2 = android.os.ServiceManager.getService(r2)     // Catch: java.lang.Throwable -> L2c
            com.samsung.android.knox.IMiscPolicy r2 = com.samsung.android.knox.IMiscPolicy.Stub.asInterface(r2)     // Catch: java.lang.Throwable -> L2c
            com.samsung.android.settings.wifi.details.WifiNetworkConfigPreferenceController.sMiscPolicyService = r2     // Catch: java.lang.Throwable -> L2c
            goto L2e
        L2c:
            r5 = move-exception
            goto L30
        L2e:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L2c
            goto L32
        L30:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L2c
            throw r5
        L32:
            if (r2 == 0) goto L53
            com.samsung.android.knox.IMiscPolicy r2 = com.samsung.android.settings.wifi.details.WifiNetworkConfigPreferenceController.sMiscPolicyService     // Catch: android.os.RemoteException -> L3b
            com.samsung.android.knox.net.ProxyProperties r1 = r2.getProxyForSsid(r1)     // Catch: android.os.RemoteException -> L3b
            goto L54
        L3b:
            r1 = move-exception
            java.lang.String r2 = "WifiNetworkConfigPrefCtrl"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "Exception occurred while retriving enterprise proxy information - "
            r3.<init>(r4)
            java.lang.String r1 = r1.getLocalizedMessage()
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            android.util.Log.e(r2, r1)
        L53:
            r1 = 0
        L54:
            if (r1 == 0) goto L88
            java.lang.String r0 = r1.getPacFileUrl()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            r0 = r0 ^ 1
            if (r0 != 0) goto L7c
            java.lang.String r0 = r1.getHostname()
            int r2 = r1.getPortNumber()
            java.util.List r3 = r1.getExclusionList()
            if (r3 != 0) goto L73
            java.util.List r1 = java.util.Collections.EMPTY_LIST
            goto L77
        L73:
            java.util.List r1 = r1.getExclusionList()
        L77:
            android.net.ProxyInfo r0 = android.net.ProxyInfo.buildDirectProxy(r0, r2, r1)
            goto L88
        L7c:
            java.lang.String r0 = r1.getPacFileUrl()
            android.net.Uri r0 = android.net.Uri.parse(r0)
            android.net.ProxyInfo r0 = android.net.ProxyInfo.buildPacProxy(r0)
        L88:
            if (r0 == 0) goto Lc9
            int r1 = r0.getPort()
            if (r1 <= 0) goto Lbc
            android.widget.TextView r1 = r5.mProxyHostNameView
            java.lang.String r2 = r0.getHost()
            r1.setText(r2)
            android.widget.EditText r1 = r5.mProxyPortView
            int r2 = r0.getPort()
            java.lang.String r2 = java.lang.Integer.toString(r2)
            r1.setText(r2)
            android.widget.TextView r1 = r5.mProxyExclusionListView
            java.lang.String[] r2 = r0.getExclusionList()
            android.telephony.SubscriptionManager r3 = com.samsung.android.wifitrackerlib.SemWifiUtils.mSubscriptionManager
            if (r2 != 0) goto Lb3
            java.lang.String r2 = ""
            goto Lb9
        Lb3:
            java.lang.String r3 = ","
            java.lang.String r2 = android.text.TextUtils.join(r3, r2)
        Lb9:
            r1.setText(r2)
        Lbc:
            android.widget.TextView r5 = r5.mProxyPacView
            android.net.Uri r0 = r0.getPacFileUrl()
            java.lang.String r0 = r0.toString()
            r5.setText(r0)
        Lc9:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.details.WifiNetworkConfigPreferenceController.setProxyConfigFields():void");
    }

    public final void updateBottomMode(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "updateBottomMode : ", "WifiNetworkConfigPrefCtrl");
        WifiNetworkDetailsFragment wifiNetworkDetailsFragment = this.mListener;
        if (wifiNetworkDetailsFragment == null) {
            return;
        }
        WifiDetailNavigationController wifiDetailNavigationController =
                wifiNetworkDetailsFragment.mWifiDetailNavigationController;
        if (i != wifiDetailNavigationController.mBottomMode) {
            wifiDetailNavigationController.setMode(i);
            if (i == 2) {
                this.mSaveButton =
                        (SecBottomBarButton)
                                this.mFragment.getActivity().findViewById(R.id.save_button);
                this.mCancelButton =
                        (SecBottomBarButton)
                                this.mFragment.getActivity().findViewById(R.id.cancel_button);
                final int i2 = 0;
                this.mSaveButton.setOnClickListener(
                        new View.OnClickListener(this) { // from class:
                            // com.samsung.android.settings.wifi.details.WifiNetworkConfigPreferenceController$$ExternalSyntheticLambda0
                            public final /* synthetic */ WifiNetworkConfigPreferenceController f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                String str;
                                String[] strArr;
                                String str2;
                                WifiConfiguration wifiConfiguration;
                                int i3 = i2;
                                final WifiNetworkConfigPreferenceController
                                        wifiNetworkConfigPreferenceController = this.f$0;
                                switch (i3) {
                                    case 0:
                                        SALogging.insertSALog(
                                                wifiNetworkConfigPreferenceController.mSAScreenId,
                                                "1046");
                                        if (wifiNetworkConfigPreferenceController.mConfig != null) {
                                            WifiEntry wifiEntry =
                                                    wifiNetworkConfigPreferenceController
                                                            .mWifiEntry;
                                            if (wifiEntry == null || !wifiEntry.isSaved()) {
                                                IpConfiguration ipConfiguration =
                                                        new IpConfiguration();
                                                ipConfiguration.setIpAssignment(
                                                        wifiNetworkConfigPreferenceController
                                                                .mIpAssignment);
                                                ipConfiguration.setProxySettings(
                                                        wifiNetworkConfigPreferenceController
                                                                .mProxySettings);
                                                ipConfiguration.setStaticIpConfiguration(
                                                        wifiNetworkConfigPreferenceController
                                                                .mStaticIpConfiguration);
                                                ipConfiguration.setHttpProxy(
                                                        wifiNetworkConfigPreferenceController
                                                                .mHttpProxy);
                                                wifiNetworkConfigPreferenceController.mConfig
                                                        .setIpConfiguration(ipConfiguration);
                                                Context context =
                                                        wifiNetworkConfigPreferenceController
                                                                .mContext;
                                                String packageName = context.getPackageName();
                                                String nameForUid =
                                                        context.getPackageManager()
                                                                .getNameForUid(context.getUserId());
                                                SemWifiManager semWifiManager =
                                                        (SemWifiManager)
                                                                context.getSystemService(
                                                                        WiFiManagerExt
                                                                                .SEM_WIFI_SERVICE);
                                                WifiConfiguration wifiConfiguration2 =
                                                        wifiNetworkConfigPreferenceController
                                                                .mConfig;
                                                if (wifiConfiguration2 != null) {
                                                    int i4 = wifiConfiguration2.networkId;
                                                    int i5 = 0;
                                                    if (wifiConfiguration2.allowedKeyManagement.get(
                                                                            1)
                                                                    || wifiConfiguration2
                                                                            .allowedKeyManagement
                                                                            .get(8)
                                                            ? !((str =
                                                                                    wifiConfiguration2
                                                                                            .preSharedKey)
                                                                            == null
                                                                    || str.length() <= 2)
                                                            : !(!wifiConfiguration2
                                                                            .allowedKeyManagement
                                                                            .get(0)
                                                                    || (strArr =
                                                                                    wifiConfiguration2
                                                                                            .wepKeys)
                                                                            == null
                                                                    || (str2 = strArr[0]) == null
                                                                    || str2.length() <= 2)) {
                                                        i5 = 1;
                                                    }
                                                    Bundle reportDataForWifiManagerApi =
                                                            WifiIssueDetectorUtil.ReportUtil
                                                                    .getReportDataForWifiManagerApi(
                                                                            i4,
                                                                            "updateNetwork",
                                                                            nameForUid,
                                                                            packageName);
                                                    reportDataForWifiManagerApi.putInt(
                                                            "hasPassword", i5);
                                                    semWifiManager.reportIssue(
                                                            105, reportDataForWifiManagerApi);
                                                }
                                                wifiNetworkConfigPreferenceController.mWifiManager
                                                        .updateNetwork(
                                                                wifiNetworkConfigPreferenceController
                                                                        .mConfig);
                                                Log.d(
                                                        "WifiNetworkConfigPrefCtrl",
                                                        "save mTempConfig is SAVE FOR CONNECT");
                                            } else {
                                                WifiConfiguration wifiConfiguration3 =
                                                        wifiNetworkConfigPreferenceController
                                                                .mWifiEntry.getWifiConfiguration();
                                                if (wifiConfiguration3 != null) {
                                                    int i6 = wifiConfiguration3.networkId;
                                                    if (wifiNetworkConfigPreferenceController
                                                                            .mWifiEntry
                                                                            .getConnectedState()
                                                                    != 0
                                                            && i6 != -1) {
                                                        Iterator<WifiConfiguration> it =
                                                                wifiNetworkConfigPreferenceController
                                                                        .mWifiManager
                                                                        .getConfiguredNetworks()
                                                                        .iterator();
                                                        while (true) {
                                                            if (it.hasNext()) {
                                                                wifiConfiguration = it.next();
                                                                if (i6
                                                                        == wifiConfiguration
                                                                                .networkId) {}
                                                            } else {
                                                                Log.d(
                                                                        "WifiNetworkConfigPrefCtrl",
                                                                        "getWifiConfigurationForNetworkId"
                                                                            + " return null");
                                                                wifiConfiguration = null;
                                                            }
                                                        }
                                                        if (wifiConfiguration != null) {
                                                            wifiConfiguration3 = wifiConfiguration;
                                                        }
                                                    }
                                                    Log.d(
                                                            "WifiNetworkConfigPrefCtrl",
                                                            "saveConfig mIpAssignment : "
                                                                    + wifiNetworkConfigPreferenceController
                                                                            .mIpAssignment
                                                                    + " mProxySettings : "
                                                                    + wifiNetworkConfigPreferenceController
                                                                            .mProxySettings);
                                                    IpConfiguration ipConfiguration2 =
                                                            new IpConfiguration();
                                                    ipConfiguration2.setIpAssignment(
                                                            wifiNetworkConfigPreferenceController
                                                                    .mIpAssignment);
                                                    ipConfiguration2.setProxySettings(
                                                            wifiNetworkConfigPreferenceController
                                                                    .mProxySettings);
                                                    ipConfiguration2.setStaticIpConfiguration(
                                                            wifiNetworkConfigPreferenceController
                                                                    .mStaticIpConfiguration);
                                                    ipConfiguration2.setHttpProxy(
                                                            wifiNetworkConfigPreferenceController
                                                                    .mHttpProxy);
                                                    wifiConfiguration3.setIpConfiguration(
                                                            ipConfiguration2);
                                                    if (wifiConfiguration3.BSSID != null) {
                                                        wifiConfiguration3.BSSID = "any";
                                                    }
                                                    wifiNetworkConfigPreferenceController
                                                            .mWifiManager.save(
                                                            wifiConfiguration3,
                                                            new WifiManager
                                                                    .ActionListener() { // from
                                                                // class:
                                                                // com.samsung.android.settings.wifi.details.WifiNetworkConfigPreferenceController.2
                                                                public final void onFailure(
                                                                        int i7) {
                                                                    Log.d(
                                                                            "WifiNetworkConfigPrefCtrl",
                                                                            "mSaveListener -"
                                                                                + " onFailure");
                                                                    Toast.makeText(
                                                                                    WifiNetworkConfigPreferenceController
                                                                                            .this
                                                                                            .mContext,
                                                                                    R.string
                                                                                            .wifi_failed_save_message,
                                                                                    0)
                                                                            .show();
                                                                }

                                                                public final void onSuccess() {}
                                                            });
                                                    wifiNetworkConfigPreferenceController.mConfig =
                                                            wifiConfiguration3;
                                                    wifiNetworkConfigPreferenceController.mWifiEntry
                                                            .semUpdateConfig(wifiConfiguration3);
                                                }
                                            }
                                        } else {
                                            Log.d(
                                                    "WifiNetworkConfigPrefCtrl",
                                                    "save mTempConfig is null");
                                        }
                                        wifiNetworkConfigPreferenceController
                                                .updatePreferenceByConfig();
                                        wifiNetworkConfigPreferenceController.updateBottomMode(1);
                                        break;
                                    default:
                                        SALogging.insertSALog(
                                                wifiNetworkConfigPreferenceController.mSAScreenId,
                                                "1045");
                                        wifiNetworkConfigPreferenceController
                                                .updatePreferenceByConfig();
                                        wifiNetworkConfigPreferenceController.updateBottomMode(1);
                                        break;
                                }
                            }
                        });
                final int i3 = 1;
                this.mCancelButton.setOnClickListener(
                        new View.OnClickListener(this) { // from class:
                            // com.samsung.android.settings.wifi.details.WifiNetworkConfigPreferenceController$$ExternalSyntheticLambda0
                            public final /* synthetic */ WifiNetworkConfigPreferenceController f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                String str;
                                String[] strArr;
                                String str2;
                                WifiConfiguration wifiConfiguration;
                                int i32 = i3;
                                final WifiNetworkConfigPreferenceController
                                        wifiNetworkConfigPreferenceController = this.f$0;
                                switch (i32) {
                                    case 0:
                                        SALogging.insertSALog(
                                                wifiNetworkConfigPreferenceController.mSAScreenId,
                                                "1046");
                                        if (wifiNetworkConfigPreferenceController.mConfig != null) {
                                            WifiEntry wifiEntry =
                                                    wifiNetworkConfigPreferenceController
                                                            .mWifiEntry;
                                            if (wifiEntry == null || !wifiEntry.isSaved()) {
                                                IpConfiguration ipConfiguration =
                                                        new IpConfiguration();
                                                ipConfiguration.setIpAssignment(
                                                        wifiNetworkConfigPreferenceController
                                                                .mIpAssignment);
                                                ipConfiguration.setProxySettings(
                                                        wifiNetworkConfigPreferenceController
                                                                .mProxySettings);
                                                ipConfiguration.setStaticIpConfiguration(
                                                        wifiNetworkConfigPreferenceController
                                                                .mStaticIpConfiguration);
                                                ipConfiguration.setHttpProxy(
                                                        wifiNetworkConfigPreferenceController
                                                                .mHttpProxy);
                                                wifiNetworkConfigPreferenceController.mConfig
                                                        .setIpConfiguration(ipConfiguration);
                                                Context context =
                                                        wifiNetworkConfigPreferenceController
                                                                .mContext;
                                                String packageName = context.getPackageName();
                                                String nameForUid =
                                                        context.getPackageManager()
                                                                .getNameForUid(context.getUserId());
                                                SemWifiManager semWifiManager =
                                                        (SemWifiManager)
                                                                context.getSystemService(
                                                                        WiFiManagerExt
                                                                                .SEM_WIFI_SERVICE);
                                                WifiConfiguration wifiConfiguration2 =
                                                        wifiNetworkConfigPreferenceController
                                                                .mConfig;
                                                if (wifiConfiguration2 != null) {
                                                    int i4 = wifiConfiguration2.networkId;
                                                    int i5 = 0;
                                                    if (wifiConfiguration2.allowedKeyManagement.get(
                                                                            1)
                                                                    || wifiConfiguration2
                                                                            .allowedKeyManagement
                                                                            .get(8)
                                                            ? !((str =
                                                                                    wifiConfiguration2
                                                                                            .preSharedKey)
                                                                            == null
                                                                    || str.length() <= 2)
                                                            : !(!wifiConfiguration2
                                                                            .allowedKeyManagement
                                                                            .get(0)
                                                                    || (strArr =
                                                                                    wifiConfiguration2
                                                                                            .wepKeys)
                                                                            == null
                                                                    || (str2 = strArr[0]) == null
                                                                    || str2.length() <= 2)) {
                                                        i5 = 1;
                                                    }
                                                    Bundle reportDataForWifiManagerApi =
                                                            WifiIssueDetectorUtil.ReportUtil
                                                                    .getReportDataForWifiManagerApi(
                                                                            i4,
                                                                            "updateNetwork",
                                                                            nameForUid,
                                                                            packageName);
                                                    reportDataForWifiManagerApi.putInt(
                                                            "hasPassword", i5);
                                                    semWifiManager.reportIssue(
                                                            105, reportDataForWifiManagerApi);
                                                }
                                                wifiNetworkConfigPreferenceController.mWifiManager
                                                        .updateNetwork(
                                                                wifiNetworkConfigPreferenceController
                                                                        .mConfig);
                                                Log.d(
                                                        "WifiNetworkConfigPrefCtrl",
                                                        "save mTempConfig is SAVE FOR CONNECT");
                                            } else {
                                                WifiConfiguration wifiConfiguration3 =
                                                        wifiNetworkConfigPreferenceController
                                                                .mWifiEntry.getWifiConfiguration();
                                                if (wifiConfiguration3 != null) {
                                                    int i6 = wifiConfiguration3.networkId;
                                                    if (wifiNetworkConfigPreferenceController
                                                                            .mWifiEntry
                                                                            .getConnectedState()
                                                                    != 0
                                                            && i6 != -1) {
                                                        Iterator<WifiConfiguration> it =
                                                                wifiNetworkConfigPreferenceController
                                                                        .mWifiManager
                                                                        .getConfiguredNetworks()
                                                                        .iterator();
                                                        while (true) {
                                                            if (it.hasNext()) {
                                                                wifiConfiguration = it.next();
                                                                if (i6
                                                                        == wifiConfiguration
                                                                                .networkId) {}
                                                            } else {
                                                                Log.d(
                                                                        "WifiNetworkConfigPrefCtrl",
                                                                        "getWifiConfigurationForNetworkId"
                                                                            + " return null");
                                                                wifiConfiguration = null;
                                                            }
                                                        }
                                                        if (wifiConfiguration != null) {
                                                            wifiConfiguration3 = wifiConfiguration;
                                                        }
                                                    }
                                                    Log.d(
                                                            "WifiNetworkConfigPrefCtrl",
                                                            "saveConfig mIpAssignment : "
                                                                    + wifiNetworkConfigPreferenceController
                                                                            .mIpAssignment
                                                                    + " mProxySettings : "
                                                                    + wifiNetworkConfigPreferenceController
                                                                            .mProxySettings);
                                                    IpConfiguration ipConfiguration2 =
                                                            new IpConfiguration();
                                                    ipConfiguration2.setIpAssignment(
                                                            wifiNetworkConfigPreferenceController
                                                                    .mIpAssignment);
                                                    ipConfiguration2.setProxySettings(
                                                            wifiNetworkConfigPreferenceController
                                                                    .mProxySettings);
                                                    ipConfiguration2.setStaticIpConfiguration(
                                                            wifiNetworkConfigPreferenceController
                                                                    .mStaticIpConfiguration);
                                                    ipConfiguration2.setHttpProxy(
                                                            wifiNetworkConfigPreferenceController
                                                                    .mHttpProxy);
                                                    wifiConfiguration3.setIpConfiguration(
                                                            ipConfiguration2);
                                                    if (wifiConfiguration3.BSSID != null) {
                                                        wifiConfiguration3.BSSID = "any";
                                                    }
                                                    wifiNetworkConfigPreferenceController
                                                            .mWifiManager.save(
                                                            wifiConfiguration3,
                                                            new WifiManager
                                                                    .ActionListener() { // from
                                                                // class:
                                                                // com.samsung.android.settings.wifi.details.WifiNetworkConfigPreferenceController.2
                                                                public final void onFailure(
                                                                        int i7) {
                                                                    Log.d(
                                                                            "WifiNetworkConfigPrefCtrl",
                                                                            "mSaveListener -"
                                                                                + " onFailure");
                                                                    Toast.makeText(
                                                                                    WifiNetworkConfigPreferenceController
                                                                                            .this
                                                                                            .mContext,
                                                                                    R.string
                                                                                            .wifi_failed_save_message,
                                                                                    0)
                                                                            .show();
                                                                }

                                                                public final void onSuccess() {}
                                                            });
                                                    wifiNetworkConfigPreferenceController.mConfig =
                                                            wifiConfiguration3;
                                                    wifiNetworkConfigPreferenceController.mWifiEntry
                                                            .semUpdateConfig(wifiConfiguration3);
                                                }
                                            }
                                        } else {
                                            Log.d(
                                                    "WifiNetworkConfigPrefCtrl",
                                                    "save mTempConfig is null");
                                        }
                                        wifiNetworkConfigPreferenceController
                                                .updatePreferenceByConfig();
                                        wifiNetworkConfigPreferenceController.updateBottomMode(1);
                                        break;
                                    default:
                                        SALogging.insertSALog(
                                                wifiNetworkConfigPreferenceController.mSAScreenId,
                                                "1045");
                                        wifiNetworkConfigPreferenceController
                                                .updatePreferenceByConfig();
                                        wifiNetworkConfigPreferenceController.updateBottomMode(1);
                                        break;
                                }
                            }
                        });
            }
        }
    }

    public final void updateIpPreference(int i) {
        Log.d(
                "WifiNetworkConfigPrefCtrl",
                "updateIpPreference mIpSettingDropdown : " + this.mIpAssignment);
        updateSummary$2(this.mIpSettingDropdown, i);
        if (i == 0) {
            ((ArrayList) this.mIpSettingPrefs)
                    .forEach(
                            new WifiNetworkConfigPreferenceController$$ExternalSyntheticLambda4(0));
        } else if (i == 1) {
            ((ArrayList) this.mIpSettingPrefs)
                    .forEach(
                            new WifiNetworkConfigPreferenceController$$ExternalSyntheticLambda4(2));
        }
    }

    public final void updatePreference() {
        Log.d("WifiNetworkConfigPrefCtrl", "update ip setting and proxy setting preferences");
        WifiEntry wifiEntry = this.mWifiEntry;
        if (wifiEntry != null) {
            this.mConfig = wifiEntry.getWifiConfiguration();
        }
        displayPreference(this.mScreen);
        updatePreferenceByConfig();
        this.mIpAddressView.setHint(R.string.wifi_ip_address_hint);
        this.mIpAddressView.addTextChangedListener(this);
        this.mIpAddressView.setInputType(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        this.mIpAddressView.setPrivateImeOptions("inputType=ipAddress");
        this.mGatewayView.setHint(R.string.wifi_gateway_hint);
        this.mGatewayView.addTextChangedListener(this);
        this.mGatewayView.setInputType(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        this.mGatewayView.setPrivateImeOptions("inputType=ipAddress");
        this.mNetworkPrefixLengthView.setHint(R.string.wifi_network_prefix_length_hint);
        this.mNetworkPrefixLengthView.addTextChangedListener(this);
        this.mNetworkPrefixLengthView.setInputType(4);
        this.mNetworkPrefixLengthView.setFilters(
                new InputFilter[] {new InputFilter.LengthFilter(4)});
        this.mDns1View.setHint(R.string.wifi_dns1_hint);
        this.mDns1View.addTextChangedListener(this);
        this.mDns1View.setInputType(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        this.mDns1View.setPrivateImeOptions("inputType=ipAddress");
        this.mDns2View.setHint(R.string.wifi_dns2_hint);
        this.mDns2View.addTextChangedListener(this);
        this.mDns2View.setInputType(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        this.mDns2View.setPrivateImeOptions("inputType=ipAddress");
        this.mProxyPacView.setHint(R.string.proxy_url_hint);
        this.mProxyPacView.addTextChangedListener(this);
        this.mProxyHostNameView.setHint(R.string.proxy_hostname_hint);
        this.mProxyHostNameView.addTextChangedListener(this);
        this.mProxyPortView.setHint(R.string.proxy_port_hint);
        this.mProxyPortView.addTextChangedListener(this);
        this.mProxyPortView.setInputType(4);
        this.mProxyExclusionListView.setHint(R.string.proxy_exclusionlist_hint);
        this.mProxyExclusionListView.addTextChangedListener(this);
        WifiEntry wifiEntry2 = this.mWifiEntry;
        if (wifiEntry2 == null || !wifiEntry2.isSaved()) {
            return;
        }
        if (WifiUtils.isBlockedByEnterprise(this.mContext, this.mWifiEntry.getSsid())
                || WifiUtils.isNetworkLockedDown(
                        this.mContext, this.mWifiEntry.getWifiConfiguration())) {
            DropDownPreference dropDownPreference = this.mIpSettingDropdown;
            if (dropDownPreference != null) {
                dropDownPreference.setEnabled(false);
            }
            ((ArrayList) this.mIpSettingPrefs)
                    .forEach(
                            new WifiNetworkConfigPreferenceController$$ExternalSyntheticLambda4(6));
            DropDownPreference dropDownPreference2 = this.mProxySettingDropdown;
            if (dropDownPreference2 != null) {
                dropDownPreference2.setEnabled(false);
            }
            ((ArrayList) this.mProxySettingPrefs)
                    .forEach(
                            new WifiNetworkConfigPreferenceController$$ExternalSyntheticLambda4(1));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updatePreferenceByConfig() {
        /*
            r5 = this;
            androidx.preference.DropDownPreference r0 = r5.mIpSettingDropdown
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L38
            android.net.wifi.WifiConfiguration r0 = r5.mConfig
            if (r0 == 0) goto L14
            android.net.IpConfiguration r0 = r0.getIpConfiguration()
            android.net.IpConfiguration$IpAssignment r0 = r0.getIpAssignment()
            r5.mIpAssignment = r0
        L14:
            android.net.IpConfiguration$IpAssignment r0 = r5.mIpAssignment
            android.net.IpConfiguration$IpAssignment r3 = android.net.IpConfiguration.IpAssignment.DHCP
            boolean r4 = r0.equals(r3)
            if (r4 == 0) goto L20
        L1e:
            r0 = r2
            goto L29
        L20:
            android.net.IpConfiguration$IpAssignment r4 = android.net.IpConfiguration.IpAssignment.STATIC
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L1e
            r0 = r1
        L29:
            android.net.IpConfiguration$IpAssignment r4 = r5.mIpAssignment
            boolean r3 = r4.equals(r3)
            if (r3 != 0) goto L34
            r5.setIpConfigFields()
        L34:
            r5.updateIpPreference(r0)
            goto L3d
        L38:
            androidx.preference.Preference r0 = r5.mIpSettingsInset
            r0.setVisible(r2)
        L3d:
            androidx.preference.DropDownPreference r0 = r5.mProxySettingDropdown
            if (r0 == 0) goto L71
            r5.initProxySettingOverride()
            android.net.IpConfiguration$ProxySettings r0 = r5.mProxySettings
            android.net.IpConfiguration$ProxySettings r3 = android.net.IpConfiguration.ProxySettings.NONE
            boolean r4 = r0.equals(r3)
            if (r4 == 0) goto L50
        L4e:
            r1 = r2
            goto L62
        L50:
            android.net.IpConfiguration$ProxySettings r4 = android.net.IpConfiguration.ProxySettings.STATIC
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L59
            goto L62
        L59:
            android.net.IpConfiguration$ProxySettings r1 = android.net.IpConfiguration.ProxySettings.PAC
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L4e
            r1 = 2
        L62:
            android.net.IpConfiguration$ProxySettings r0 = r5.mProxySettings
            boolean r0 = r0.equals(r3)
            if (r0 != 0) goto L6d
            r5.setProxyConfigFields()
        L6d:
            r5.updateProxyPreference(r1)
            goto L76
        L71:
            androidx.preference.Preference r5 = r5.mProxySettingsInset
            r5.setVisible(r2)
        L76:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.details.WifiNetworkConfigPreferenceController.updatePreferenceByConfig():void");
    }

    public final void updateProxyPreference(int i) {
        Log.d(
                "WifiNetworkConfigPrefCtrl",
                "updateProxyPreference mProxySettingDropdown : " + this.mProxySettings);
        updateSummary$2(this.mProxySettingDropdown, i);
        if (i == 0) {
            ((ArrayList) this.mProxySettingPrefs)
                    .forEach(
                            new WifiNetworkConfigPreferenceController$$ExternalSyntheticLambda4(3));
        } else if (i == 1) {
            ((ArrayList) this.mProxySettingPrefs)
                    .forEach(
                            new WifiNetworkConfigPreferenceController$$ExternalSyntheticLambda4(4));
        } else if (i == 2) {
            ((ArrayList) this.mProxySettingPrefs)
                    .forEach(
                            new WifiNetworkConfigPreferenceController$$ExternalSyntheticLambda4(5));
        }
    }

    public final void updateSummary$2(DropDownPreference dropDownPreference, int i) {
        ColorStateList colorStateList =
                this.mFragment
                        .getActivity()
                        .getResources()
                        .getColorStateList(
                                R.color.sec_preference_summary_primary_color,
                                this.mFragment.getActivity().getTheme());
        dropDownPreference.setValueIndex(i);
        dropDownPreference.setSummary(dropDownPreference.mEntries[i]);
        dropDownPreference.seslSetSummaryColor(colorStateList);
    }

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
}

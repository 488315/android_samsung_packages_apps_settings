package com.samsung.android.settings.wifi.details;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiManager;
import android.telephony.SubscriptionManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.picker3.widget.SeslColorPicker$16$$ExternalSyntheticOutline0;
import androidx.preference.DropDownPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settingslib.Utils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.widget.LayoutPreference;
import com.android.wifitrackerlib.WifiEntry;

import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.textfield.TextInputLayout;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.widget.SecBottomBarButton;
import com.samsung.android.settings.wifi.SecWifiUnclickablePreference;
import com.samsung.android.settings.wifi.WifiDevicePolicyManager;
import com.samsung.android.wifi.SemWifiConfiguration;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.sec.ims.extensions.WiFiManagerExt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiModifyPreferenceController extends WifiConfigurePreferenceController
        implements LifecycleObserver,
                OnResume,
                OnPause,
                Preference.OnPreferenceChangeListener,
                View.OnKeyListener,
                SecWifiPreferenceControllerHelper.ValidationUpdater,
                WifiEntry.WifiEntryCallback {
    public final Activity mActivity;
    public final Context mContext;
    public SwitchPreferenceCompat mDhcpNameSharingPref;
    public int mDisableReason;
    public LayoutPreference mDns1Pref;
    public EditText mDns1View;
    public LayoutPreference mDns2Pref;
    public EditText mDns2View;
    public EditText mEapAnonymousView;
    public SecWifiUnclickablePreference mEapCaCertErrorPref;
    public SecWifiUnclickablePreference mEapErrorPref;
    public TextView mEapIdentityErrorText;
    public boolean mExternalValidationResult;
    public LayoutPreference mGatewayPref;
    public EditText mGatewayView;
    public final WifiImeHelper mImeHelper;
    public LayoutPreference mIpAddrPref;
    public EditText mIpAddressView;
    public DropDownPreference mIpSettingsPref;
    public DropDownPreference mMeteredPref;
    public LayoutPreference mNetworkPrefixLenPref;
    public EditText mNetworkPrefixLengthView;
    public CheckableImageButton mPasswordImageButton;
    public DropDownPreference mPhase2Pref;
    public DropDownPreference mPrivacyPref;
    public LayoutPreference mProxyExclusionListPref;
    public EditText mProxyExclusionListView;
    public LayoutPreference mProxyHostNamePref;
    public EditText mProxyHostNameView;
    public LayoutPreference mProxyPacPref;
    public EditText mProxyPacView;
    public LayoutPreference mProxyPortPref;
    public EditText mProxyPortView;
    public DropDownPreference mProxySettingsPref;
    public final AnonymousClass1 mReceiver;
    public final String mSAScreenId;
    public SecBottomBarButton mSaveButton;
    public final AnonymousClass2 mSaveListener;
    public final WifiManager mWifiManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class WifiEapIdTextWatcher implements TextWatcher {
        public final EditText mEditText;
        public final TextView mErrorText;
        public String mPreviousString;

        public WifiEapIdTextWatcher(EditText editText, TextView textView) {
            this.mEditText = editText;
            this.mErrorText = textView;
        }

        @Override // android.text.TextWatcher
        public final void afterTextChanged(Editable editable) {
            WifiModifyPreferenceController.this.enableSubmitIfAppropriate();
        }

        @Override // android.text.TextWatcher
        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            int length;
            if (charSequence == null
                    || (length = charSequence.toString().getBytes().length) <= 0
                    || length > 200) {
                return;
            }
            this.mPreviousString = charSequence.toString();
        }

        @Override // android.text.TextWatcher
        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (charSequence.toString().getBytes().length <= 200) {
                this.mErrorText.setVisibility(8);
                SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0.m(
                        WifiModifyPreferenceController.this.mContext,
                        R.color.wifi_ap_dialog_title_text_color,
                        this.mEditText);
                return;
            }
            this.mEditText.setPrivateImeOptions(
                    "inputType=PredictionOff;disableEmoticonInput=true");
            String str = this.mPreviousString;
            if (str == null || str.getBytes().length > 200) {
                Log.e(
                        "WifiModifyPrefCtrl",
                        "String: "
                                + ((Object) charSequence)
                                + " start: "
                                + i
                                + " before: "
                                + i2
                                + " count: "
                                + i3);
                this.mEditText.setText(ApnSettings.MVNO_NONE);
            } else {
                this.mEditText.setText(this.mPreviousString);
            }
            this.mEditText.setPrivateImeOptions(
                    "inputType=PredictionOff;disableEmoticonInput=true;defaultInputmode=english");
            this.mErrorText.setText(R.string.max_char_reached);
            this.mErrorText.setVisibility(0);
            SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0.m(
                    WifiModifyPreferenceController.this.mContext,
                    R.color.sec_wifi_dialog_error_color,
                    this.mEditText);
            SeslColorPicker$16$$ExternalSyntheticOutline0.m(this.mEditText);
        }
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.samsung.android.settings.wifi.details.WifiModifyPreferenceController$1] */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.samsung.android.settings.wifi.details.WifiModifyPreferenceController$2] */
    @VisibleForTesting
    public WifiModifyPreferenceController(
            WifiEntry wifiEntry,
            Fragment fragment,
            Context context,
            Activity activity,
            Lifecycle lifecycle,
            WifiManager wifiManager,
            WifiImeHelper wifiImeHelper,
            String str) {
        super(context, fragment, wifiEntry);
        this.mExternalValidationResult = true;
        this.mReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.samsung.android.settings.wifi.details.WifiModifyPreferenceController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        Log.d("WifiModifyPrefCtrl", "onReceive action: " + intent.getAction());
                        String action = intent.getAction();
                        action.getClass();
                        switch (action) {
                            case "android.net.wifi.WIFI_STATE_CHANGED":
                                int intExtra = intent.getIntExtra("wifi_state", 4);
                                if (intExtra == 1 || intExtra == 0) {
                                    WifiModifyPreferenceController.this.finishActivity$2();
                                    break;
                                }
                            case "android.intent.action.SIM_STATE_CHANGED":
                            case "android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED":
                                WifiModifyPreferenceController.this.updateEapSimPref();
                                break;
                        }
                    }
                };
        this.mSaveListener =
                new WifiManager
                        .ActionListener() { // from class:
                                            // com.samsung.android.settings.wifi.details.WifiModifyPreferenceController.2
                    public final void onFailure(int i) {
                        Log.d("WifiModifyPrefCtrl", "mSaveListener - onFailure");
                        Toast.makeText(
                                        WifiModifyPreferenceController.this.mContext,
                                        R.string.wifi_failed_save_message,
                                        0)
                                .show();
                    }

                    public final void onSuccess() {
                        Log.d("WifiModifyPrefCtrl", "mSaveListener onSuccess ");
                    }
                };
        this.mContext = context;
        this.mWifiManager = wifiManager;
        this.mActivity = activity;
        this.mImeHelper = wifiImeHelper;
        WifiEntry wifiEntry2 = this.mWifiEntry;
        if (wifiEntry2 == null) {
            Log.d("WifiModifyPrefCtrl", "finish - WifiEntry is null");
            finishActivity$2();
            return;
        }
        wifiEntry2.setListener(this);
        this.mWifiConfig = this.mWifiEntry.getWifiConfiguration();
        this.mWifiEntrySecurity = wifiEntry.getSecurity$1();
        lifecycle.addObserver(this);
        this.mSAScreenId = str;
        this.mIsTrustOnFirstUseSupported = wifiManager.isTrustOnFirstUseSupported();
        int i = this.mWifiEntrySecurity;
        if ((i == 3 || i == 7) && !Utils.isWifiOnly(context)) {
            setupEapMethodForCarrierSsid();
        }
    }

    @Override // com.samsung.android.settings.wifi.details.WifiConfigurePreferenceController
    public final void afterShowSecurityFields() {
        TextInputLayout textInputLayout =
                (TextInputLayout) this.mPasswordPref.mRootView.findViewById(R.id.input_password);
        if (textInputLayout.isPasswordVisibilityToggleEnabled()) {
            if (WifiDevicePolicyManager.isAllowedToShowPasswordHiddenView(this.mContext)) {
                textInputLayout.setPasswordVisibilityToggleEnabled(true);
                SALogging.insertSALog(1L, this.mSAScreenId, "1021");
            } else {
                textInputLayout.setPasswordVisibilityToggleEnabled(false);
                SALogging.insertSALog(0L, this.mSAScreenId, "1021");
                this.mPasswordView.setInputType(129);
            }
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        DropDownPreference dropDownPreference =
                (DropDownPreference) preferenceScreen.findPreference("eap_method");
        this.mEapMethodPref = dropDownPreference;
        dropDownPreference.setOnPreferenceChangeListener(this);
        this.mEapMethodPref.setTitle(R.string.wifi_eap_method);
        this.mEapMethodPref.setVisible(false);
        setupEapItems();
        DropDownPreference dropDownPreference2 =
                (DropDownPreference) preferenceScreen.findPreference("eap_ca_cert");
        this.mEapCaCertPref = dropDownPreference2;
        dropDownPreference2.setOnPreferenceChangeListener(this);
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference("eap_domain");
        this.mEapDomainPref = layoutPreference;
        ((TextView) layoutPreference.mRootView.findViewById(R.id.title))
                .setText(R.string.wifi_eap_domain);
        EditText editText = (EditText) this.mEapDomainPref.mRootView.findViewById(R.id.edittext);
        this.mEapDomainView = editText;
        editText.setInputType(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        this.mEapDomainView.addTextChangedListener(this.mEapDomainViewWatcher);
        DropDownPreference dropDownPreference3 =
                (DropDownPreference) preferenceScreen.findPreference("eap_min_tls_ver");
        this.mMinTlsVersionPref = dropDownPreference3;
        dropDownPreference3.setOnPreferenceChangeListener(this);
        setupEapMinTlsVer(this.mWifiManager.isTlsV13Supported());
        DropDownPreference dropDownPreference4 =
                (DropDownPreference) preferenceScreen.findPreference("eap_ocsp");
        this.mEapOcspPref = dropDownPreference4;
        dropDownPreference4.setVisible(false);
        this.mEapOcspPref.setTitle(R.string.wifi_eap_ocsp);
        this.mEapOcspPref.setOnPreferenceChangeListener(this);
        setupEapOcspItems();
        DropDownPreference dropDownPreference5 =
                (DropDownPreference) preferenceScreen.findPreference("eap_user_cert");
        this.mEapUserCertPref = dropDownPreference5;
        dropDownPreference5.setOnPreferenceChangeListener(this);
        disableViewsIfAppropriate$4();
        SecWifiUnclickablePreference secWifiUnclickablePreference =
                (SecWifiUnclickablePreference)
                        preferenceScreen.findPreference("eap_ca_cert_error_text");
        this.mEapCaCertErrorPref = secWifiUnclickablePreference;
        secWifiUnclickablePreference.setVisible(false);
        SecWifiUnclickablePreference secWifiUnclickablePreference2 =
                (SecWifiUnclickablePreference)
                        preferenceScreen.findPreference("wifi_eap_ca_error_text");
        this.mEapErrorPref = secWifiUnclickablePreference2;
        secWifiUnclickablePreference2.setVisible(false);
        LayoutPreference layoutPreference2 =
                (LayoutPreference) preferenceScreen.findPreference("eap_identity");
        this.mEapIdPref = layoutPreference2;
        ((TextView) layoutPreference2.mRootView.findViewById(R.id.title))
                .setText(R.string.wifi_eap_identity);
        this.mEapIdentityView = (EditText) this.mEapIdPref.mRootView.findViewById(R.id.edittext);
        TextView textView = (TextView) this.mEapIdPref.mRootView.findViewById(R.id.wifi_error_text);
        this.mEapIdentityErrorText = textView;
        EditText editText2 = this.mEapIdentityView;
        editText2.addTextChangedListener(new WifiEapIdTextWatcher(editText2, textView));
        this.mEapIdentityErrorText.setVisibility(8);
        int i = this.mWifiEntrySecurity;
        if (i != 3 || i != 7 || i != 6) {
            this.mEapIdPref.setVisible(false);
        }
        DropDownPreference dropDownPreference6 =
                (DropDownPreference) preferenceScreen.findPreference("eap_sim_select");
        this.mEapSimPref = dropDownPreference6;
        dropDownPreference6.setVisible(false);
        this.mEapSimPref.setOnPreferenceChangeListener(this);
        updateEapSimPref();
        LayoutPreference layoutPreference3 =
                (LayoutPreference) preferenceScreen.findPreference("wifi_password");
        this.mPasswordPref = layoutPreference3;
        TextView textView2 = (TextView) layoutPreference3.mRootView.findViewById(R.id.title);
        this.mPaswordTitleView = textView2;
        textView2.setText(R.string.wifi_password);
        ((TextView) this.mPasswordPref.mRootView.findViewById(R.id.title))
                .setText(R.string.wifi_password);
        EditText editText3 = (EditText) this.mPasswordPref.mRootView.findViewById(R.id.edittext);
        this.mPasswordView = editText3;
        editText3.addTextChangedListener(this.mPasswordWatcher);
        this.mPasswordView.setOnKeyListener(this);
        this.mPasswordView.setOnEditorActionListener(this.mImeHelper);
        TextView textView3 =
                (TextView) this.mPasswordPref.mRootView.findViewById(R.id.wifi_error_text);
        this.mPasswordErrorText = textView3;
        textView3.setVisibility(8);
        CheckableImageButton checkableImageButton =
                (CheckableImageButton)
                        ((TextInputLayout)
                                        this.mPasswordPref.mRootView.findViewById(
                                                R.id.input_password))
                                .findViewById(
                                        this.mContext
                                                .getResources()
                                                .getIdentifier(
                                                        "text_input_end_icon",
                                                        "id",
                                                        this.mContext.getPackageName()));
        this.mPasswordImageButton = checkableImageButton;
        if (checkableImageButton != null) {
            checkableImageButton.setOnClickListener(
                    new WifiModifyPreferenceController$$ExternalSyntheticLambda0(this, 0));
        }
        this.mPhase2Pref = (DropDownPreference) preferenceScreen.findPreference("eap_phase2");
        this.mEapAnonymousView =
                (EditText)
                        ((LayoutPreference) preferenceScreen.findPreference("eap_anonymous"))
                                .mRootView.findViewById(R.id.edittext);
        this.mIpSettingsPref = (DropDownPreference) preferenceScreen.findPreference("ip_settings");
        this.mIpAddrPref = (LayoutPreference) preferenceScreen.findPreference("wifi_ip_address");
        this.mGatewayPref = (LayoutPreference) preferenceScreen.findPreference("gateway");
        this.mNetworkPrefixLenPref =
                (LayoutPreference) preferenceScreen.findPreference("wifi_network_prefix_length");
        this.mDns1Pref = (LayoutPreference) preferenceScreen.findPreference("wifi_dns1");
        this.mDns2Pref = (LayoutPreference) preferenceScreen.findPreference("wifi_dns2");
        this.mIpAddressView = (EditText) this.mIpAddrPref.mRootView.findViewById(R.id.edittext);
        this.mGatewayView = (EditText) this.mGatewayPref.mRootView.findViewById(R.id.edittext);
        this.mNetworkPrefixLengthView =
                (EditText) this.mNetworkPrefixLenPref.mRootView.findViewById(R.id.edittext);
        this.mDns1View = (EditText) this.mDns1Pref.mRootView.findViewById(R.id.edittext);
        this.mDns2View = (EditText) this.mDns2Pref.mRootView.findViewById(R.id.edittext);
        this.mProxySettingsPref =
                (DropDownPreference) preferenceScreen.findPreference("proxy_settings");
        this.mProxyPacPref = (LayoutPreference) preferenceScreen.findPreference("proxy_pac");
        this.mProxyHostNamePref =
                (LayoutPreference) preferenceScreen.findPreference("proxy_hostname");
        this.mProxyPortPref = (LayoutPreference) preferenceScreen.findPreference("proxy_port");
        this.mProxyExclusionListPref =
                (LayoutPreference) preferenceScreen.findPreference("proxy_exclusionlist");
        this.mProxyPacView = (EditText) this.mProxyPacPref.mRootView.findViewById(R.id.edittext);
        this.mProxyHostNameView =
                (EditText) this.mProxyHostNamePref.mRootView.findViewById(R.id.edittext);
        this.mProxyPortView = (EditText) this.mProxyPortPref.mRootView.findViewById(R.id.edittext);
        this.mProxyExclusionListView =
                (EditText) this.mProxyExclusionListPref.mRootView.findViewById(R.id.edittext);
        this.mMeteredPref = (DropDownPreference) preferenceScreen.findPreference("metered");
        this.mPrivacyPref = (DropDownPreference) preferenceScreen.findPreference("privacy");
        this.mDhcpNameSharingPref =
                (SwitchPreferenceCompat) preferenceScreen.findPreference("dhcp_name_sharing");
        if (this.mWifiConfig != null) {
            this.mPasswordView.setHint(R.string.wifi_unchanged);
            this.mTempPassword = SemWifiUtils.removeDoubleQuotes(this.mWifiConfig.preSharedKey);
        }
        setupButton();
    }

    @Override // com.samsung.android.settings.wifi.details.WifiConfigurePreferenceController
    public final void enableSubmitIfAppropriate() {
        boolean isSubmittable$1 = isSubmittable$1();
        SecBottomBarButton secBottomBarButton = this.mSaveButton;
        if (secBottomBarButton == null) {
            return;
        }
        secBottomBarButton.setEnabled(isSubmittable$1);
    }

    @Override // com.samsung.android.settings.wifi.details.WifiConfigurePreferenceController
    public final String getLogTag() {
        return "WifiModifyPrefCtrl";
    }

    @Override // com.samsung.android.settings.wifi.details.WifiConfigurePreferenceController
    public final int getMode() {
        return 1;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0060  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isSubmittable$1() {
        /*
            Method dump skipped, instructions count: 315
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.details.WifiModifyPreferenceController.isSubmittable$1():boolean");
    }

    @Override // com.samsung.android.settings.wifi.details.WifiConfigurePreferenceController
    public final boolean isUnchangedNeeded() {
        WifiEntry wifiEntry = this.mWifiEntry;
        return wifiEntry != null && wifiEntry.isSaved();
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (view != this.mPasswordView) {
            return false;
        }
        if ((i != 66 && i != 160) || !isSubmittable$1()) {
            return false;
        }
        save$1();
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        Log.d("WifiModifyPrefCtrl", "onPause");
        this.mContext.unregisterReceiver(this.mReceiver);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        String key = preference.getKey();
        int parseInt = Integer.parseInt((String) obj);
        DropDownPreference dropDownPreference = (DropDownPreference) preference;
        CharSequence[] charSequenceArr = dropDownPreference.mEntries;
        if (charSequenceArr == null || parseInt < 0 || parseInt >= charSequenceArr.length) {
            Log.e(
                    "WifiModifyPrefCtrl",
                    "onPreferenceChange: "
                            + dropDownPreference.getKey()
                            + " entries null or length error");
            return false;
        }
        Log.d("WifiModifyPrefCtrl", "onPreferenceChange " + key + "/" + parseInt);
        if ("eap_method".equals(key)) {
            updateSummary$4(dropDownPreference, parseInt);
            refreshEapItems(parseInt);
            setDefaultEAPPhase2Method(charSequenceArr[parseInt].toString());
            enableSubmitIfAppropriate();
            disableViewsIfAppropriate$4();
            WifiEapPreferenceController.AnonymousClass1 anonymousClass1 =
                    this.mEapMethodChangeListener;
            if (anonymousClass1 != null) {
                WifiEapPreferenceController wifiEapPreferenceController =
                        WifiEapPreferenceController.this;
                wifiEapPreferenceController.mPhase2Method = -1;
                wifiEapPreferenceController.updatePreference(
                        wifiEapPreferenceController.mIsExpanded);
            }
            return true;
        }
        if ("eap_ocsp".equals(key)) {
            updateSummary$4(dropDownPreference, parseInt);
            this.mWifiConfig.enterpriseConfig.setOcsp(parseInt);
            return true;
        }
        if ("eap_min_tls_ver".equals(key)) {
            updateSummary$4(dropDownPreference, parseInt);
            return true;
        }
        if (!"eap_ca_cert".equals(key)) {
            if (!"eap_user_cert".equals(key)) {
                if ("eap_sim_select".equals(key)) {
                    updateSummary$4(dropDownPreference, parseInt);
                }
                return false;
            }
            CharSequence charSequence = charSequenceArr[parseInt];
            if (!TextUtils.isEmpty(charSequence)) {
                this.mWifiConfig.enterpriseConfig.setClientCertificateAlias(
                        charSequence.toString());
            }
            updateSummary$4(dropDownPreference, parseInt);
            enableSubmitIfAppropriate();
            disableViewsIfAppropriate$4();
            return true;
        }
        this.mDisableReason = -99;
        this.mEapCaCertErrorPref.setVisible(false);
        CharSequence charSequence2 = charSequenceArr[parseInt];
        if (!TextUtils.isEmpty(charSequence2)) {
            if (this.mUseSystemCertsString.contentEquals(charSequence2)) {
                this.mWifiConfig.enterpriseConfig.setCaPath("/system/etc/security/cacerts");
            } else {
                this.mWifiConfig.enterpriseConfig.setCaPath(null);
                this.mWifiConfig.enterpriseConfig.setCaCertificateAliases(
                        new String[] {charSequence2.toString()});
            }
        }
        if (parseInt == 0) {
            updateSummaryWithWarning(dropDownPreference);
        } else {
            updateSummary$4(dropDownPreference, parseInt);
        }
        setDomainVisible();
        setOcspVisible();
        setMinTlsVersionVisible();
        enableSubmitIfAppropriate();
        disableViewsIfAppropriate$4();
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        WifiConfiguration wifiConfiguration = this.mWifiConfig;
        WifiEntry wifiEntry = this.mWifiEntry;
        if (wifiConfiguration == null) {
            this.mWifiConfig = wifiEntry.getWifiConfiguration();
            ActionBarContextView$$ExternalSyntheticOutline0.m(
                    new StringBuilder("get config "),
                    this.mWifiConfig == null,
                    "WifiModifyPrefCtrl");
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        this.mContext.registerReceiver(this.mReceiver, intentFilter, 2);
        if (this.mWifiConfig == null) {
            Log.d("WifiModifyPrefCtrl", "finish - mWifiConfig is null");
            finishActivity$2();
            return;
        }
        int security$1 = wifiEntry.getSecurity$1();
        this.mWifiEntrySecurity = security$1;
        showSecurityFields(security$1);
        updateSecurity();
        enableSubmitIfAppropriate();
        Context context = this.mContext;
        WifiConfiguration wifiConfiguration2 = this.mWifiConfig;
        SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
        SemWifiManager semWifiManager =
                (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        SemWifiConfiguration semWifiConfiguration = null;
        if (wifiConfiguration2 != null) {
            String key = wifiConfiguration2.getKey();
            List configuredNetworks = semWifiManager.getConfiguredNetworks();
            if (configuredNetworks != null && !configuredNetworks.isEmpty()) {
                Iterator it = configuredNetworks.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    SemWifiConfiguration semWifiConfiguration2 = (SemWifiConfiguration) it.next();
                    if (semWifiConfiguration2.configKey.equals(key)) {
                        semWifiConfiguration = semWifiConfiguration2;
                        break;
                    }
                }
            }
        }
        if (semWifiConfiguration == null
                || semWifiConfiguration.networkDisableReason != 11
                || this.mDisableReason == -99) {
            return;
        }
        this.mDisableReason = 11;
        this.mEapCaCertErrorPref.setTitle(R.string.wifi_wrong_ca_cert_description);
        this.mEapCaCertErrorPref.setVisible(true);
    }

    @Override // com.android.wifitrackerlib.WifiEntry.WifiEntryCallback
    public final void onUpdated() {
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("onUpdated "), this.mWifiConfig == null, "WifiModifyPrefCtrl");
        if (this.mWifiConfig == null) {
            WifiEntry wifiEntry = this.mWifiEntry;
            this.mWifiConfig = wifiEntry.getWifiConfiguration();
            ActionBarContextView$$ExternalSyntheticOutline0.m(
                    new StringBuilder("get config "),
                    this.mWifiConfig == null,
                    "WifiModifyPrefCtrl");
            if (this.mWifiConfig != null) {
                int security$1 = wifiEntry.getSecurity$1();
                this.mWifiEntrySecurity = security$1;
                showSecurityFields(security$1);
                updateSecurity();
                enableSubmitIfAppropriate();
            }
        }
        setupButton();
        WifiNetworkModifyFragment wifiNetworkModifyFragment =
                (WifiNetworkModifyFragment) this.mFragment;
        wifiNetworkModifyFragment.updatePreferenceStates();
        PreferenceScreen preferenceScreen = wifiNetworkModifyFragment.getPreferenceScreen();
        Iterator it = ((ArrayList) wifiNetworkModifyFragment.mControllers).iterator();
        while (it.hasNext()) {
            AbstractPreferenceController abstractPreferenceController =
                    (AbstractPreferenceController) it.next();
            if (!(abstractPreferenceController instanceof WifiModifyPreferenceController)
                    && !(abstractPreferenceController
                            instanceof WifiExpandablePreferenceController)) {
                abstractPreferenceController.displayPreference(preferenceScreen);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:113:0x03d5  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x03e8  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x036c  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0424  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x043d  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x04c1  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x04d4  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x04e2  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x053e  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0557  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0574  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0591  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x051f  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x04d7  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0387  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x03a1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void save$1() {
        /*
            Method dump skipped, instructions count: 1476
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.details.WifiModifyPreferenceController.save$1():void");
    }

    public final void setupButton() {
        SecBottomBarButton secBottomBarButton =
                (SecBottomBarButton) this.mActivity.findViewById(R.id.save_button);
        this.mSaveButton = secBottomBarButton;
        if (secBottomBarButton != null) {
            secBottomBarButton.setOnClickListener(
                    new WifiModifyPreferenceController$$ExternalSyntheticLambda0(this, 1));
        }
        SecBottomBarButton secBottomBarButton2 =
                (SecBottomBarButton) this.mActivity.findViewById(R.id.cancel_button);
        if (secBottomBarButton2 != null) {
            secBottomBarButton2.setOnClickListener(
                    new WifiModifyPreferenceController$$ExternalSyntheticLambda0(this, 2));
        }
    }

    @Override // com.samsung.android.settings.wifi.details.WifiConfigurePreferenceController
    public final void setupPhase2Method(int i) {
        WifiEnterpriseConfig wifiEnterpriseConfig = this.mWifiConfig.enterpriseConfig;
        if (wifiEnterpriseConfig == null) {
            Log.d("WifiModifyPrefCtrl", "enterpriseConfig of mWificonfig is null");
            return;
        }
        int phase2Method = wifiEnterpriseConfig.getPhase2Method();
        if (i != 0 || Utils.isWifiOnly(this.mContext)) {
            return;
        }
        if (phase2Method == 5 || phase2Method == 6 || phase2Method == 7) {
            this.mEapIdPref.setVisible(false);
        }
    }

    @Override // com.samsung.android.settings.wifi.details.SecWifiPreferenceControllerHelper.ValidationUpdater
    public final void update(boolean z) {
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "update button state triggered by Validator ", "WifiModifyPrefCtrl", z);
        this.mExternalValidationResult = z;
        enableSubmitIfAppropriate();
    }
}

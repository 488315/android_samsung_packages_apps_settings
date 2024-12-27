package com.android.settings.wifi;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.InetAddresses;
import android.net.IpConfiguration;
import android.net.ProxyInfo;
import android.net.StaticIpConfiguration;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.compose.ui.platform.AndroidUriHandler$$ExternalSyntheticOutline0;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.net.module.util.ProxyUtils;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.network.SubscriptionUtil;
import com.android.settings.utils.AndroidKeystoreAliasLoader;
import com.android.settings.wifi.details2.WifiPrivacyPreferenceController;
import com.android.settings.wifi.details2.WifiPrivacyPreferenceController2;
import com.android.settings.wifi.dpp.WifiDppUtils;
import com.android.settingslib.utils.ThreadUtils;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.settings.ImsProfile;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiConfigController2
        implements TextWatcher,
                AdapterView.OnItemSelectedListener,
                CompoundButton.OnCheckedChangeListener,
                TextView.OnEditorActionListener,
                View.OnKeyListener {
    static final String DEFAULT_ANONYMOUS_ID = "anonymous";
    static final String[] UNDESIRED_CERTIFICATES = {"MacRandSecret", "MacRandSapSecret"};
    public final ArrayMap mActiveSubscriptionInfos;
    public final AndroidKeystoreAliasLoader mAndroidKeystoreAliasLoader;
    public final WifiConfigUiBase2 mConfigUi;
    public final Context mContext;
    public final Spinner mDhcpSettingsSpinner;
    public TextView mDns1View;
    public TextView mDns2View;
    public final String mDoNotProvideEapUserCertString;
    TextView mEapAnonymousView;
    Spinner mEapCaCertSpinner;
    public TextView mEapDomainView;
    public TextView mEapIdentityView;
    Spinner mEapMethodSpinner;
    public Spinner mEapMinTlsVerSpinner;
    public Spinner mEapOcspSpinner;
    Spinner mEapSimSpinner;
    public Spinner mEapUserCertSpinner;
    public TextView mGatewayView;
    public final Spinner mHiddenSettingsSpinner;
    public final TextView mHiddenWarningView;
    public ProxyInfo mHttpProxy;
    String mInstallCertsString;
    public TextView mIpAddressView;
    public IpConfiguration.IpAssignment mIpAssignment;
    public final Spinner mIpSettingsSpinner;
    public final boolean mIsTrustOnFirstUseSupported;
    public int mLastShownEapMethod;
    public final String[] mLevels;
    public final Spinner mMeteredSettingsSpinner;
    public final int mMode;
    public final String mMultipleCertSetString;
    public TextView mNetworkPrefixLengthView;
    public TextView mPasswordView;
    public ArrayAdapter mPhase2Adapter;
    public final ArrayAdapter mPhase2PeapAdapter;
    public Spinner mPhase2Spinner;
    public final ArrayAdapter mPhase2TtlsAdapter;
    public final Spinner mPrivacySettingsSpinner;
    public TextView mProxyExclusionListView;
    public TextView mProxyHostView;
    public TextView mProxyPacView;
    public TextView mProxyPortView;
    public IpConfiguration.ProxySettings mProxySettings;
    public final Spinner mProxySettingsSpinner;
    Integer[] mSecurityInPosition;
    public final Spinner mSecuritySpinner;
    public final CheckBox mSharedCheckBox;
    public final ImageButton mSsidScanButton;
    public final TextView mSsidView;
    public StaticIpConfiguration mStaticIpConfiguration;
    public final String mTrustOnFirstUse;
    public final String mUnspecifiedCertString;
    public final String mUseSystemCertsString;
    public final View mView;
    public final WifiEntry mWifiEntry;
    int mWifiEntrySecurity;
    public final WifiManager mWifiManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.wifi.WifiConfigController2$1, reason: invalid class name */
    public final class AnonymousClass1 extends View.AccessibilityDelegate {
        @Override // android.view.View.AccessibilityDelegate
        public final void sendAccessibilityEvent(View view, int i) {
            if (i == 4) {
                return;
            }
            super.sendAccessibilityEvent(view, i);
        }
    }

    public WifiConfigController2(
            WifiConfigUiBase2 wifiConfigUiBase2, View view, WifiEntry wifiEntry, int i, boolean z) {
        this(
                wifiConfigUiBase2,
                view,
                wifiEntry,
                i,
                z,
                (WifiManager) wifiConfigUiBase2.getContext().getSystemService(WifiManager.class),
                new AndroidKeystoreAliasLoader(102));
    }

    public static Inet4Address getIPv4Address(String str) {
        try {
            return (Inet4Address) InetAddresses.parseNumericAddress(str);
        } catch (ClassCastException | IllegalArgumentException unused) {
            return null;
        }
    }

    public static void setSelection(Spinner spinner, String str) {
        if (str != null) {
            ArrayAdapter arrayAdapter = (ArrayAdapter) spinner.getAdapter();
            for (int count = arrayAdapter.getCount() - 1; count >= 0; count--) {
                if (str.equals(arrayAdapter.getItem(count))) {
                    spinner.setSelection(count);
                    return;
                }
            }
        }
    }

    public final void addRow(ViewGroup viewGroup, int i, String str) {
        View inflate =
                this.mConfigUi
                        .getLayoutInflater()
                        .inflate(R.layout.wifi_dialog_row, viewGroup, false);
        ((TextView) inflate.findViewById(R.id.name)).setText(i);
        ((TextView) inflate.findViewById(R.id.value)).setText(str);
        viewGroup.addView(inflate);
    }

    @Override // android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
        ThreadUtils.postOnMainThread(new WifiConfigController2$$ExternalSyntheticLambda1(this));
    }

    public final void enableSubmitIfAppropriate() {
        Button submitButton = this.mConfigUi.getSubmitButton();
        if (submitButton == null) {
            return;
        }
        submitButton.setEnabled(isSubmittable());
    }

    public CharSequence[] findAndReplaceTargetStrings(
            CharSequence[] charSequenceArr,
            CharSequence[] charSequenceArr2,
            CharSequence[] charSequenceArr3) {
        if (charSequenceArr2.length != charSequenceArr3.length) {
            return charSequenceArr;
        }
        CharSequence[] charSequenceArr4 = new CharSequence[charSequenceArr.length];
        for (int i = 0; i < charSequenceArr.length; i++) {
            charSequenceArr4[i] = charSequenceArr[i];
            for (int i2 = 0; i2 < charSequenceArr2.length; i2++) {
                if (TextUtils.equals(charSequenceArr[i], charSequenceArr2[i2])) {
                    charSequenceArr4[i] = charSequenceArr3[i2];
                }
            }
        }
        return charSequenceArr4;
    }

    public final WifiConfiguration getConfig() {
        WifiConfiguration wifiConfiguration;
        if (this.mMode == 0) {
            return null;
        }
        WifiEntry wifiEntry = this.mWifiEntry;
        if (wifiEntry == null) {
            wifiConfiguration = new WifiConfiguration();
            wifiConfiguration.SSID = "\"" + this.mSsidView.getText().toString() + "\"";
            wifiConfiguration.hiddenSSID =
                    this.mHiddenSettingsSpinner.getSelectedItemPosition() == 1;
        } else if (wifiEntry.isSaved()) {
            wifiConfiguration = new WifiConfiguration(this.mWifiEntry.getWifiConfiguration());
        } else {
            wifiConfiguration = new WifiConfiguration();
            wifiConfiguration.SSID = "\"" + this.mWifiEntry.getTitle() + "\"";
        }
        wifiConfiguration.shared = this.mSharedCheckBox.isChecked();
        switch (this.mWifiEntrySecurity) {
            case 0:
                WifiEntry wifiEntry2 = this.mWifiEntry;
                if (wifiEntry2 == null || !wifiEntry2.isSaved()) {
                    wifiConfiguration.setSecurityParams(0);
                    break;
                }
                break;
            case 1:
                WifiEntry wifiEntry3 = this.mWifiEntry;
                if (wifiEntry3 == null || !wifiEntry3.isSaved()) {
                    wifiConfiguration.setSecurityParams(1);
                }
                if (this.mPasswordView.length() != 0) {
                    int length = this.mPasswordView.length();
                    String charSequence = this.mPasswordView.getText().toString();
                    if ((length != 10 && length != 26 && length != 58)
                            || !charSequence.matches("[0-9A-Fa-f]*")) {
                        wifiConfiguration.wepKeys[0] =
                                AndroidUriHandler$$ExternalSyntheticOutline0.m(
                                        '\"', "\"", charSequence);
                        break;
                    } else {
                        wifiConfiguration.wepKeys[0] = charSequence;
                        break;
                    }
                }
                break;
            case 2:
                WifiEntry wifiEntry4 = this.mWifiEntry;
                if (wifiEntry4 == null || !wifiEntry4.isSaved()) {
                    wifiConfiguration.setSecurityParams(2);
                }
                if (this.mPasswordView.length() != 0) {
                    String charSequence2 = this.mPasswordView.getText().toString();
                    if (!charSequence2.matches("[0-9A-Fa-f]{64}")) {
                        wifiConfiguration.preSharedKey =
                                AndroidUriHandler$$ExternalSyntheticOutline0.m(
                                        '\"', "\"", charSequence2);
                        break;
                    } else {
                        wifiConfiguration.preSharedKey = charSequence2;
                        break;
                    }
                }
                break;
            case 3:
            case 6:
            case 7:
                WifiEntry wifiEntry5 = this.mWifiEntry;
                if (wifiEntry5 == null || !wifiEntry5.isSaved()) {
                    int i = this.mWifiEntrySecurity;
                    if (i == 6) {
                        wifiConfiguration.setSecurityParams(5);
                    } else if (i == 7) {
                        wifiConfiguration.setSecurityParams(9);
                    } else {
                        wifiConfiguration.setSecurityParams(3);
                    }
                }
                wifiConfiguration.enterpriseConfig = new WifiEnterpriseConfig();
                int selectedItemPosition = this.mEapMethodSpinner.getSelectedItemPosition();
                int selectedItemPosition2 = this.mPhase2Spinner.getSelectedItemPosition();
                wifiConfiguration.enterpriseConfig.setEapMethod(selectedItemPosition);
                if (selectedItemPosition != 0) {
                    if (selectedItemPosition == 2) {
                        if (selectedItemPosition2 == 0) {
                            wifiConfiguration.enterpriseConfig.setPhase2Method(1);
                        } else if (selectedItemPosition2 == 1) {
                            wifiConfiguration.enterpriseConfig.setPhase2Method(2);
                        } else if (selectedItemPosition2 == 2) {
                            wifiConfiguration.enterpriseConfig.setPhase2Method(3);
                        } else if (selectedItemPosition2 != 3) {
                            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                                    .m(
                                            selectedItemPosition2,
                                            "Unknown phase2 method",
                                            "WifiConfigController2");
                        } else {
                            wifiConfiguration.enterpriseConfig.setPhase2Method(4);
                        }
                    }
                } else if (selectedItemPosition2 == 0) {
                    wifiConfiguration.enterpriseConfig.setPhase2Method(3);
                } else if (selectedItemPosition2 == 1) {
                    wifiConfiguration.enterpriseConfig.setPhase2Method(4);
                } else if (selectedItemPosition2 == 2) {
                    wifiConfiguration.enterpriseConfig.setPhase2Method(5);
                } else if (selectedItemPosition2 == 3) {
                    wifiConfiguration.enterpriseConfig.setPhase2Method(6);
                } else if (selectedItemPosition2 != 4) {
                    WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                            .m(
                                    selectedItemPosition2,
                                    "Unknown phase2 method",
                                    "WifiConfigController2");
                } else {
                    wifiConfiguration.enterpriseConfig.setPhase2Method(7);
                }
                if (wifiConfiguration.enterpriseConfig.isAuthenticationSimBased()
                        && this.mActiveSubscriptionInfos.size() > 0) {
                    wifiConfiguration.carrierId =
                            ((SubscriptionInfo)
                                            this.mActiveSubscriptionInfos.valueAt(
                                                    this.mEapSimSpinner.getSelectedItemPosition()))
                                    .getCarrierId();
                }
                String str = (String) this.mEapCaCertSpinner.getSelectedItem();
                wifiConfiguration.enterpriseConfig.setCaCertificateAliases(null);
                wifiConfiguration.enterpriseConfig.setCaPath(null);
                wifiConfiguration.enterpriseConfig.setDomainSuffixMatch(
                        this.mEapDomainView.getText().toString());
                if (!str.equals(this.mUnspecifiedCertString)) {
                    if (this.mIsTrustOnFirstUseSupported && str.equals(this.mTrustOnFirstUse)) {
                        wifiConfiguration.enterpriseConfig.enableTrustOnFirstUse(true);
                    } else if (str.equals(this.mUseSystemCertsString)) {
                        wifiConfiguration.enterpriseConfig.setCaPath(
                                "/system/etc/security/cacerts");
                    } else if (str.equals(this.mMultipleCertSetString)) {
                        WifiEntry wifiEntry6 = this.mWifiEntry;
                        if (wifiEntry6 != null) {
                            if (!wifiEntry6.isSaved()) {
                                Log.e(
                                        "WifiConfigController2",
                                        "Multiple certs can only be set when editing saved"
                                            + " network");
                            }
                            wifiConfiguration.enterpriseConfig.setCaCertificateAliases(
                                    this.mWifiEntry
                                            .getWifiConfiguration()
                                            .enterpriseConfig
                                            .getCaCertificateAliases());
                        }
                    } else {
                        wifiConfiguration.enterpriseConfig.setCaCertificateAliases(
                                new String[] {str});
                    }
                }
                if (wifiConfiguration.enterpriseConfig.getCaCertificateAliases() != null
                        && wifiConfiguration.enterpriseConfig.getCaPath() != null) {
                    Log.e(
                            "WifiConfigController2",
                            "ca_cert ("
                                    + Arrays.toString(
                                            wifiConfiguration.enterpriseConfig
                                                    .getCaCertificateAliases())
                                    + ") and ca_path ("
                                    + wifiConfiguration.enterpriseConfig.getCaPath()
                                    + ") should not both be non-null");
                }
                if (str.equals(this.mUnspecifiedCertString)) {
                    wifiConfiguration.enterpriseConfig.setOcsp(0);
                    wifiConfiguration.enterpriseConfig.setMinimumTlsVersion(0);
                } else {
                    wifiConfiguration.enterpriseConfig.setOcsp(
                            this.mEapOcspSpinner.getSelectedItemPosition());
                    wifiConfiguration.enterpriseConfig.setMinimumTlsVersion(
                            this.mEapMinTlsVerSpinner.getSelectedItemPosition());
                }
                String str2 = (String) this.mEapUserCertSpinner.getSelectedItem();
                if (str2.equals(this.mUnspecifiedCertString)
                        || str2.equals(this.mDoNotProvideEapUserCertString)) {
                    str2 = ApnSettings.MVNO_NONE;
                }
                wifiConfiguration.enterpriseConfig.setClientCertificateAlias(str2);
                if (selectedItemPosition == 4
                        || selectedItemPosition == 5
                        || selectedItemPosition == 6) {
                    wifiConfiguration.enterpriseConfig.setIdentity(ApnSettings.MVNO_NONE);
                    wifiConfiguration.enterpriseConfig.setAnonymousIdentity(ApnSettings.MVNO_NONE);
                } else if (selectedItemPosition == 3) {
                    wifiConfiguration.enterpriseConfig.setIdentity(
                            this.mEapIdentityView.getText().toString());
                    wifiConfiguration.enterpriseConfig.setAnonymousIdentity(ApnSettings.MVNO_NONE);
                } else {
                    wifiConfiguration.enterpriseConfig.setIdentity(
                            this.mEapIdentityView.getText().toString());
                    wifiConfiguration.enterpriseConfig.setAnonymousIdentity(
                            this.mEapAnonymousView.getText().toString());
                }
                if (!this.mPasswordView.isShown()) {
                    wifiConfiguration.enterpriseConfig.setPassword(
                            this.mPasswordView.getText().toString());
                    break;
                } else if (this.mPasswordView.length() > 0) {
                    wifiConfiguration.enterpriseConfig.setPassword(
                            this.mPasswordView.getText().toString());
                    break;
                }
                break;
            case 4:
                WifiEntry wifiEntry7 = this.mWifiEntry;
                if (wifiEntry7 == null || !wifiEntry7.isSaved()) {
                    wifiConfiguration.setSecurityParams(6);
                    break;
                }
                break;
            case 5:
                WifiEntry wifiEntry8 = this.mWifiEntry;
                if (wifiEntry8 == null || !wifiEntry8.isSaved()) {
                    wifiConfiguration.setSecurityParams(4);
                }
                if (this.mPasswordView.length() != 0) {
                    wifiConfiguration.preSharedKey =
                            AndroidUriHandler$$ExternalSyntheticOutline0.m(
                                    '\"', "\"", this.mPasswordView.getText().toString());
                    break;
                }
                break;
            default:
                return null;
        }
        IpConfiguration ipConfiguration = new IpConfiguration();
        ipConfiguration.setIpAssignment(this.mIpAssignment);
        ipConfiguration.setProxySettings(this.mProxySettings);
        ipConfiguration.setStaticIpConfiguration(this.mStaticIpConfiguration);
        ipConfiguration.setHttpProxy(this.mHttpProxy);
        wifiConfiguration.setIpConfiguration(ipConfiguration);
        Spinner spinner = this.mMeteredSettingsSpinner;
        if (spinner != null) {
            wifiConfiguration.meteredOverride = spinner.getSelectedItemPosition();
        }
        Spinner spinner2 = this.mPrivacySettingsSpinner;
        if (spinner2 != null) {
            wifiConfiguration.macRandomizationSetting =
                    WifiPrivacyPreferenceController2.translatePrefValueToMacRandomizedValue(
                            spinner2.getSelectedItemPosition());
        }
        Spinner spinner3 = this.mDhcpSettingsSpinner;
        if (spinner3 != null) {
            WifiPrivacyPreferenceController.Companion companion =
                    WifiPrivacyPreferenceController.INSTANCE;
            int selectedItemPosition3 = spinner3.getSelectedItemPosition();
            companion.getClass();
            wifiConfiguration.setSendDhcpHostnameEnabled(selectedItemPosition3 == 0);
        }
        return wifiConfiguration;
    }

    public Spinner getEapMinTlsVerSpinner(boolean z) {
        Spinner spinner = (Spinner) this.mView.findViewById(R.id.min_tls_ver);
        String[] stringArray =
                this.mContext.getResources().getStringArray(R.array.wifi_eap_tls_ver);
        if (!z) {
            Log.w(
                    "WifiConfigController2",
                    "Wi-Fi Enterprise TLS v1.3 is not supported on this device");
            ArrayList arrayList = new ArrayList(Arrays.asList(stringArray));
            arrayList.remove(3);
            stringArray = (String[]) arrayList.toArray(new String[0]);
        }
        spinner.setAdapter((SpinnerAdapter) getSpinnerAdapter(stringArray));
        return spinner;
    }

    public String getSignalString() {
        int level;
        if (this.mWifiEntry.getLevel() == -1 || (level = this.mWifiEntry.getLevel()) <= -1) {
            return null;
        }
        String[] strArr = this.mLevels;
        if (level < strArr.length) {
            return strArr[level];
        }
        return null;
    }

    public ArrayAdapter<CharSequence> getSpinnerAdapter(String[] strArr) {
        ArrayAdapter<CharSequence> arrayAdapter =
                new ArrayAdapter<>(this.mContext, android.R.layout.simple_spinner_item, strArr);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return arrayAdapter;
    }

    public final ArrayAdapter getSpinnerAdapterWithEapMethodsTts(int i) {
        Resources resources = this.mContext.getResources();
        String[] stringArray = resources.getStringArray(i);
        CharSequence[] findAndReplaceTargetStrings =
                findAndReplaceTargetStrings(
                        stringArray,
                        resources.getStringArray(R.array.wifi_eap_method_target_strings),
                        resources.getStringArray(R.array.wifi_eap_method_tts_strings));
        SpannableString[] spannableStringArr = new SpannableString[stringArray.length];
        for (int i2 = 0; i2 < stringArray.length; i2++) {
            spannableStringArr[i2] =
                    Utils.createAccessibleSequence(
                            findAndReplaceTargetStrings[i2].toString(), stringArray[i2]);
        }
        ArrayAdapter arrayAdapter =
                new ArrayAdapter(
                        this.mContext, android.R.layout.simple_spinner_item, spannableStringArr);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return arrayAdapter;
    }

    /* JADX WARN: Code restructure failed: missing block: B:145:0x002f, code lost:

       if (r0.matches("[0-9A-Fa-f]{64}") != false) goto L19;
    */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x003e, code lost:

       if (r0.length() <= 63) goto L19;
    */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x005b, code lost:

       if (r0.length() <= 128) goto L27;
    */
    /* JADX WARN: Removed duplicated region for block: B:125:0x020e  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x024e A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x029f A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00a2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isSubmittable() {
        /*
            Method dump skipped, instructions count: 711
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.wifi.WifiConfigController2.isSubmittable():boolean");
    }

    public void loadCertificates(
            Spinner spinner, Collection<String> collection, String str, boolean z, boolean z2) {
        this.mConfigUi.getContext();
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.mUnspecifiedCertString);
        if (z) {
            arrayList.add(this.mMultipleCertSetString);
        }
        if (z2) {
            arrayList.add(this.mUseSystemCertsString);
            if (this.mIsTrustOnFirstUseSupported) {
                arrayList.add(this.mTrustOnFirstUse);
            }
            arrayList.add(this.mInstallCertsString);
        }
        if (collection != null && collection.size() != 0) {
            arrayList.addAll(
                    (Collection)
                            collection.stream()
                                    .filter(new WifiConfigController2$$ExternalSyntheticLambda0())
                                    .collect(Collectors.toList()));
        }
        if (!TextUtils.isEmpty(str) && this.mWifiEntrySecurity != 6) {
            arrayList.add(str);
        }
        if (arrayList.size() == 2) {
            arrayList.remove(this.mUnspecifiedCertString);
            spinner.setEnabled(false);
        } else {
            spinner.setEnabled(true);
        }
        spinner.setAdapter(
                (SpinnerAdapter)
                        getSpinnerAdapter(
                                (String[]) arrayList.toArray(new String[arrayList.size()])));
    }

    public void loadSims() {
        List<SubscriptionInfo> activeSubscriptionInfoList =
                ((SubscriptionManager) this.mContext.getSystemService(SubscriptionManager.class))
                        .getActiveSubscriptionInfoList();
        if (activeSubscriptionInfoList == null) {
            activeSubscriptionInfoList = Collections.EMPTY_LIST;
        }
        this.mActiveSubscriptionInfos.clear();
        if (activeSubscriptionInfoList.isEmpty()) {
            this.mEapSimSpinner.setAdapter(
                    (SpinnerAdapter)
                            getSpinnerAdapter(
                                    new String[] {
                                        this.mContext.getString(R.string.wifi_no_sim_card)
                                    }));
            this.mEapSimSpinner.setSelection(0);
            this.mEapSimSpinner.setEnabled(false);
            return;
        }
        ArrayMap arrayMap = new ArrayMap();
        int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
        for (SubscriptionInfo subscriptionInfo : activeSubscriptionInfoList) {
            if (!arrayMap.containsKey(Integer.valueOf(subscriptionInfo.getCarrierId()))
                    || defaultDataSubscriptionId == subscriptionInfo.getSubscriptionId()) {
                arrayMap.put(
                        Integer.valueOf(subscriptionInfo.getCarrierId()),
                        SubscriptionUtil.getUniqueSubscriptionDisplayName(
                                this.mContext, subscriptionInfo));
                this.mActiveSubscriptionInfos.put(
                        Integer.valueOf(subscriptionInfo.getCarrierId()), subscriptionInfo);
            }
        }
        this.mEapSimSpinner.setAdapter(
                (SpinnerAdapter)
                        getSpinnerAdapter(
                                (String[]) arrayMap.values().toArray(new String[arrayMap.size()])));
        this.mEapSimSpinner.setSelection(0);
        if (arrayMap.size() == 1) {
            this.mEapSimSpinner.setEnabled(false);
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (compoundButton.getId() == R.id.show_password) {
            int selectionEnd = this.mPasswordView.getSelectionEnd();
            this.mPasswordView.setInputType((z ? 144 : 128) | 1);
            if (selectionEnd >= 0) {
                ((EditText) this.mPasswordView).setSelection(selectionEnd);
                return;
            }
            return;
        }
        if (compoundButton.getId() == R.id.wifi_advanced_togglebox) {
            ((InputMethodManager) this.mContext.getSystemService(InputMethodManager.class))
                    .hideSoftInputFromWindow(this.mView.getWindowToken(), 0);
            compoundButton.setVisibility(8);
            this.mView.findViewById(R.id.wifi_advanced_fields).setVisibility(0);
        }
    }

    @Override // android.widget.TextView.OnEditorActionListener
    public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (textView != this.mPasswordView || i != 6 || !isSubmittable()) {
            return false;
        }
        this.mConfigUi.dispatchSubmit();
        return true;
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onItemSelected(AdapterView adapterView, View view, int i, long j) {
        if (adapterView == this.mSecuritySpinner) {
            this.mWifiEntrySecurity = this.mSecurityInPosition[i].intValue();
            showSecurityFields(true, true);
            if (WifiDppUtils.isSupportEnrolleeQrCodeScanner(
                    this.mContext, this.mWifiEntrySecurity)) {
                this.mSsidScanButton.setVisibility(0);
            } else {
                this.mSsidScanButton.setVisibility(8);
            }
        } else {
            Spinner spinner = this.mEapMethodSpinner;
            if (adapterView == spinner) {
                int selectedItemPosition = spinner.getSelectedItemPosition();
                if (this.mLastShownEapMethod != selectedItemPosition) {
                    this.mLastShownEapMethod = selectedItemPosition;
                    showSecurityFields(false, true);
                }
            } else if (adapterView == this.mEapCaCertSpinner) {
                if (adapterView.getItemAtPosition(i).toString().equals(this.mInstallCertsString)) {
                    Intent intent = new Intent("android.credentials.INSTALL");
                    intent.setFlags(268435456);
                    intent.setComponent(
                            new ComponentName(
                                    "com.android.certinstaller",
                                    "com.android.certinstaller.CertInstallerMain"));
                    intent.putExtra("certificate_install_usage", ImsProfile.PDN_WIFI);
                    this.mContext.startActivity(intent);
                }
                showSecurityFields(false, false);
            } else if (adapterView == this.mPhase2Spinner
                    && spinner.getSelectedItemPosition() == 0) {
                showPeapFields();
            } else if (adapterView == this.mProxySettingsSpinner) {
                showProxyFields();
            } else if (adapterView == this.mHiddenSettingsSpinner) {
                this.mHiddenWarningView.setVisibility(i != 0 ? 0 : 8);
            } else {
                showIpConfigFields();
            }
        }
        showWarningMessagesIfAppropriate();
        enableSubmitIfAppropriate();
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (view != this.mPasswordView || i != 66 || !isSubmittable()) {
            return false;
        }
        this.mConfigUi.dispatchSubmit();
        return true;
    }

    public void setAnonymousIdVisible() {
        View findViewById = this.mView.findViewById(R.id.l_anonymous);
        if (findViewById.getVisibility() == 0) {
            return;
        }
        findViewById.setVisibility(0);
        this.mEapAnonymousView.setText(DEFAULT_ANONYMOUS_ID);
    }

    public final void setAnonymousIdentInvisible() {
        this.mView.findViewById(R.id.l_anonymous).setVisibility(8);
        this.mEapAnonymousView.setText(ApnSettings.MVNO_NONE);
    }

    public final void setDomainInvisible() {
        this.mView.findViewById(R.id.l_domain).setVisibility(8);
        this.mEapDomainView.setText(ApnSettings.MVNO_NONE);
    }

    public final void setMinTlsVerInvisible() {
        this.mView.findViewById(R.id.l_min_tls_ver).setVisibility(8);
        this.mEapMinTlsVerSpinner.setSelection(0);
    }

    public final void setOcspInvisible() {
        this.mView.findViewById(R.id.l_ocsp).setVisibility(8);
        this.mEapOcspSpinner.setSelection(0);
    }

    public final void setPasswordInvisible() {
        this.mPasswordView.setText(ApnSettings.MVNO_NONE);
        this.mView.findViewById(R.id.password_layout).setVisibility(8);
        this.mView.findViewById(R.id.show_password_layout).setVisibility(8);
    }

    public final void setUserCertInvisible() {
        this.mView.findViewById(R.id.l_user_cert).setVisibility(8);
        setSelection(this.mEapUserCertSpinner, this.mUnspecifiedCertString);
    }

    public final void setVisibility(int i, int i2) {
        View findViewById = this.mView.findViewById(i);
        if (findViewById != null) {
            findViewById.setVisibility(i2);
        }
    }

    public final void showEapFieldsByMethod(int i) {
        this.mView.findViewById(R.id.l_method).setVisibility(0);
        this.mView.findViewById(R.id.l_identity).setVisibility(0);
        this.mView.findViewById(R.id.l_domain).setVisibility(0);
        this.mView.findViewById(R.id.l_ca_cert).setVisibility(0);
        if (this.mWifiManager.isTlsMinimumVersionSupported()) {
            this.mView.findViewById(R.id.l_min_tls_ver).setVisibility(0);
        }
        this.mView.findViewById(R.id.l_ocsp).setVisibility(0);
        this.mView.findViewById(R.id.password_layout).setVisibility(0);
        this.mView.findViewById(R.id.show_password_layout).setVisibility(0);
        this.mView.findViewById(R.id.l_sim).setVisibility(0);
        this.mConfigUi.getContext();
        switch (i) {
            case 0:
                ArrayAdapter arrayAdapter = this.mPhase2Adapter;
                ArrayAdapter arrayAdapter2 = this.mPhase2PeapAdapter;
                if (arrayAdapter != arrayAdapter2) {
                    this.mPhase2Adapter = arrayAdapter2;
                    this.mPhase2Spinner.setAdapter((SpinnerAdapter) arrayAdapter2);
                }
                this.mView.findViewById(R.id.l_phase2).setVisibility(0);
                setAnonymousIdVisible();
                showPeapFields();
                setUserCertInvisible();
                break;
            case 1:
                this.mView.findViewById(R.id.l_user_cert).setVisibility(0);
                this.mView.findViewById(R.id.l_phase2).setVisibility(8);
                setAnonymousIdentInvisible();
                setPasswordInvisible();
                this.mView.findViewById(R.id.l_sim).setVisibility(8);
                break;
            case 2:
                ArrayAdapter arrayAdapter3 = this.mPhase2Adapter;
                ArrayAdapter arrayAdapter4 = this.mPhase2TtlsAdapter;
                if (arrayAdapter3 != arrayAdapter4) {
                    this.mPhase2Adapter = arrayAdapter4;
                    this.mPhase2Spinner.setAdapter((SpinnerAdapter) arrayAdapter4);
                }
                this.mView.findViewById(R.id.l_phase2).setVisibility(0);
                setAnonymousIdVisible();
                setUserCertInvisible();
                this.mView.findViewById(R.id.l_sim).setVisibility(8);
                break;
            case 3:
                this.mView.findViewById(R.id.l_phase2).setVisibility(8);
                this.mView.findViewById(R.id.l_ca_cert).setVisibility(8);
                setSelection(this.mEapCaCertSpinner, this.mUnspecifiedCertString);
                setMinTlsVerInvisible();
                setOcspInvisible();
                setDomainInvisible();
                setAnonymousIdentInvisible();
                setUserCertInvisible();
                this.mView.findViewById(R.id.l_sim).setVisibility(8);
                break;
            case 4:
            case 5:
            case 6:
                this.mView.findViewById(R.id.l_phase2).setVisibility(8);
                setAnonymousIdentInvisible();
                this.mView.findViewById(R.id.l_ca_cert).setVisibility(8);
                setSelection(this.mEapCaCertSpinner, this.mUnspecifiedCertString);
                setMinTlsVerInvisible();
                setOcspInvisible();
                setDomainInvisible();
                setUserCertInvisible();
                setPasswordInvisible();
                this.mView.findViewById(R.id.l_identity).setVisibility(8);
                break;
        }
        if (this.mView.findViewById(R.id.l_ca_cert).getVisibility() != 8) {
            String str = (String) this.mEapCaCertSpinner.getSelectedItem();
            if (str.equals(this.mUnspecifiedCertString)
                    || (this.mIsTrustOnFirstUseSupported && str.equals(this.mTrustOnFirstUse))) {
                setMinTlsVerInvisible();
                setDomainInvisible();
                setOcspInvisible();
            }
        }
    }

    public final void showIpConfigFields() {
        StaticIpConfiguration staticIpConfiguration;
        this.mView.findViewById(R.id.ip_fields).setVisibility(0);
        WifiEntry wifiEntry = this.mWifiEntry;
        WifiConfiguration wifiConfiguration =
                (wifiEntry == null || !wifiEntry.isSaved())
                        ? null
                        : this.mWifiEntry.getWifiConfiguration();
        if (this.mIpSettingsSpinner.getSelectedItemPosition() != 1) {
            this.mView.findViewById(R.id.staticip).setVisibility(8);
            return;
        }
        this.mView.findViewById(R.id.staticip).setVisibility(0);
        if (this.mIpAddressView == null) {
            TextView textView = (TextView) this.mView.findViewById(R.id.ipaddress);
            this.mIpAddressView = textView;
            textView.addTextChangedListener(this);
            TextView textView2 = (TextView) this.mView.findViewById(R.id.gateway);
            this.mGatewayView = textView2;
            textView2.addTextChangedListener(new AnonymousClass2(textView2));
            TextView textView3 = (TextView) this.mView.findViewById(R.id.network_prefix_length);
            this.mNetworkPrefixLengthView = textView3;
            textView3.addTextChangedListener(new AnonymousClass2(textView3));
            TextView textView4 = (TextView) this.mView.findViewById(R.id.dns1);
            this.mDns1View = textView4;
            textView4.addTextChangedListener(new AnonymousClass2(textView4));
            TextView textView5 = (TextView) this.mView.findViewById(R.id.dns2);
            this.mDns2View = textView5;
            textView5.addTextChangedListener(this);
        }
        if (wifiConfiguration == null
                || (staticIpConfiguration =
                                wifiConfiguration.getIpConfiguration().getStaticIpConfiguration())
                        == null) {
            return;
        }
        if (staticIpConfiguration.getIpAddress() != null) {
            this.mIpAddressView.setText(
                    staticIpConfiguration.getIpAddress().getAddress().getHostAddress());
            this.mNetworkPrefixLengthView.setText(
                    Integer.toString(staticIpConfiguration.getIpAddress().getPrefixLength()));
        }
        if (staticIpConfiguration.getGateway() != null) {
            this.mGatewayView.setText(staticIpConfiguration.getGateway().getHostAddress());
        }
        Iterator<InetAddress> it = staticIpConfiguration.getDnsServers().iterator();
        if (it.hasNext()) {
            this.mDns1View.setText(it.next().getHostAddress());
        }
        if (it.hasNext()) {
            this.mDns2View.setText(it.next().getHostAddress());
        }
    }

    public final void showPeapFields() {
        int selectedItemPosition = this.mPhase2Spinner.getSelectedItemPosition();
        if (selectedItemPosition == 2 || selectedItemPosition == 3 || selectedItemPosition == 4) {
            this.mEapIdentityView.setText(ApnSettings.MVNO_NONE);
            this.mView.findViewById(R.id.l_identity).setVisibility(8);
            setPasswordInvisible();
            this.mView.findViewById(R.id.l_sim).setVisibility(0);
            return;
        }
        this.mView.findViewById(R.id.l_identity).setVisibility(0);
        this.mView.findViewById(R.id.password_layout).setVisibility(0);
        this.mView.findViewById(R.id.show_password_layout).setVisibility(0);
        this.mView.findViewById(R.id.l_sim).setVisibility(8);
    }

    public final void showProxyFields() {
        ProxyInfo httpProxy;
        ProxyInfo httpProxy2;
        this.mView.findViewById(R.id.proxy_settings_fields).setVisibility(0);
        WifiEntry wifiEntry = this.mWifiEntry;
        WifiConfiguration wifiConfiguration =
                (wifiEntry == null || !wifiEntry.isSaved())
                        ? null
                        : this.mWifiEntry.getWifiConfiguration();
        if (this.mProxySettingsSpinner.getSelectedItemPosition() != 1) {
            if (this.mProxySettingsSpinner.getSelectedItemPosition() != 2) {
                setVisibility(R.id.proxy_warning_limited_support, 8);
                setVisibility(R.id.proxy_fields, 8);
                setVisibility(R.id.proxy_pac_field, 8);
                return;
            }
            setVisibility(R.id.proxy_warning_limited_support, 8);
            setVisibility(R.id.proxy_fields, 8);
            setVisibility(R.id.proxy_pac_field, 0);
            if (this.mProxyPacView == null) {
                TextView textView = (TextView) this.mView.findViewById(R.id.proxy_pac);
                this.mProxyPacView = textView;
                textView.addTextChangedListener(this);
            }
            if (wifiConfiguration == null
                    || (httpProxy = wifiConfiguration.getHttpProxy()) == null) {
                return;
            }
            this.mProxyPacView.setText(httpProxy.getPacFileUrl().toString());
            return;
        }
        setVisibility(R.id.proxy_warning_limited_support, 0);
        setVisibility(R.id.proxy_fields, 0);
        setVisibility(R.id.proxy_pac_field, 8);
        if (this.mProxyHostView == null) {
            TextView textView2 = (TextView) this.mView.findViewById(R.id.proxy_hostname);
            this.mProxyHostView = textView2;
            textView2.addTextChangedListener(this);
            TextView textView3 = (TextView) this.mView.findViewById(R.id.proxy_port);
            this.mProxyPortView = textView3;
            textView3.addTextChangedListener(this);
            TextView textView4 = (TextView) this.mView.findViewById(R.id.proxy_exclusionlist);
            this.mProxyExclusionListView = textView4;
            textView4.addTextChangedListener(this);
        }
        if (wifiConfiguration == null || (httpProxy2 = wifiConfiguration.getHttpProxy()) == null) {
            return;
        }
        this.mProxyHostView.setText(httpProxy2.getHost());
        this.mProxyPortView.setText(Integer.toString(httpProxy2.getPort()));
        TextView textView5 = this.mProxyExclusionListView;
        String[] exclusionList = httpProxy2.getExclusionList();
        Pattern pattern = ProxyUtils.HOSTNAME_PATTERN;
        textView5.setText(
                exclusionList == null ? ApnSettings.MVNO_NONE : TextUtils.join(",", exclusionList));
    }

    public final void showSecurityFields(boolean z, boolean z2) {
        boolean z3;
        WifiEntry wifiEntry;
        int indexOfKey;
        int i = this.mWifiEntrySecurity;
        if (i == 0 || i == 4) {
            this.mView.findViewById(R.id.security_fields).setVisibility(8);
            return;
        }
        this.mView.findViewById(R.id.security_fields).setVisibility(0);
        if (this.mPasswordView == null) {
            TextView textView = (TextView) this.mView.findViewById(R.id.password);
            this.mPasswordView = textView;
            textView.addTextChangedListener(this);
            this.mPasswordView.setOnEditorActionListener(this);
            this.mPasswordView.setOnKeyListener(this);
            ((CheckBox) this.mView.findViewById(R.id.show_password))
                    .setOnCheckedChangeListener(this);
            WifiEntry wifiEntry2 = this.mWifiEntry;
            if (wifiEntry2 != null && wifiEntry2.isSaved()) {
                this.mPasswordView.setHint(R.string.wifi_unchanged);
            }
        }
        int i2 = this.mWifiEntrySecurity;
        if (i2 != 3 && i2 != 7 && i2 != 6) {
            this.mView.findViewById(R.id.eap).setVisibility(8);
            return;
        }
        this.mView.findViewById(R.id.eap).setVisibility(0);
        if (this.mEapMethodSpinner == null) {
            Spinner spinner = (Spinner) this.mView.findViewById(R.id.method);
            this.mEapMethodSpinner = spinner;
            spinner.setOnItemSelectedListener(this);
            this.mEapSimSpinner = (Spinner) this.mView.findViewById(R.id.sim);
            Spinner spinner2 = (Spinner) this.mView.findViewById(R.id.phase2);
            this.mPhase2Spinner = spinner2;
            spinner2.setOnItemSelectedListener(this);
            Spinner spinner3 = (Spinner) this.mView.findViewById(R.id.ca_cert);
            this.mEapCaCertSpinner = spinner3;
            spinner3.setOnItemSelectedListener(this);
            this.mEapMinTlsVerSpinner =
                    getEapMinTlsVerSpinner(this.mWifiManager.isTlsV13Supported());
            this.mEapOcspSpinner = (Spinner) this.mView.findViewById(R.id.ocsp);
            TextView textView2 = (TextView) this.mView.findViewById(R.id.domain);
            this.mEapDomainView = textView2;
            textView2.addTextChangedListener(this);
            Spinner spinner4 = (Spinner) this.mView.findViewById(R.id.user_cert);
            this.mEapUserCertSpinner = spinner4;
            spinner4.setOnItemSelectedListener(this);
            this.mEapIdentityView = (TextView) this.mView.findViewById(R.id.identity);
            this.mEapAnonymousView = (TextView) this.mView.findViewById(R.id.anonymous);
            AnonymousClass1 anonymousClass1 = new AnonymousClass1();
            this.mEapMethodSpinner.setAccessibilityDelegate(anonymousClass1);
            this.mPhase2Spinner.setAccessibilityDelegate(anonymousClass1);
            this.mEapCaCertSpinner.setAccessibilityDelegate(anonymousClass1);
            this.mEapMinTlsVerSpinner.setAccessibilityDelegate(anonymousClass1);
            this.mEapOcspSpinner.setAccessibilityDelegate(anonymousClass1);
            this.mEapUserCertSpinner.setAccessibilityDelegate(anonymousClass1);
            z3 = true;
        } else {
            z3 = false;
        }
        if (z) {
            if (this.mWifiEntrySecurity == 6) {
                this.mEapMethodSpinner.setAdapter(
                        (SpinnerAdapter)
                                getSpinnerAdapter(
                                        this.mContext
                                                .getResources()
                                                .getStringArray(R.array.wifi_eap_method)));
                this.mEapMethodSpinner.setSelection(1);
                this.mEapMethodSpinner.setEnabled(false);
            } else if (com.android.settingslib.Utils.isWifiOnly(this.mContext)
                    || !this.mContext
                            .getResources()
                            .getBoolean(android.R.bool.config_emergencyGestureEnabled)) {
                this.mEapMethodSpinner.setAdapter(
                        (SpinnerAdapter)
                                getSpinnerAdapter(
                                        this.mContext
                                                .getResources()
                                                .getStringArray(
                                                        R.array.eap_method_without_sim_auth)));
                this.mEapMethodSpinner.setEnabled(true);
            } else {
                this.mEapMethodSpinner.setAdapter(
                        (SpinnerAdapter)
                                getSpinnerAdapterWithEapMethodsTts(R.array.wifi_eap_method));
                this.mEapMethodSpinner.setEnabled(true);
            }
        }
        if (z2) {
            loadSims();
            loadCertificates(
                    this.mEapCaCertSpinner,
                    this.mAndroidKeystoreAliasLoader.mCaCertAliases,
                    null,
                    false,
                    true);
            loadCertificates(
                    this.mEapUserCertSpinner,
                    this.mAndroidKeystoreAliasLoader.mKeyCertAliases,
                    this.mDoNotProvideEapUserCertString,
                    false,
                    false);
            setSelection(this.mEapCaCertSpinner, this.mUnspecifiedCertString);
        }
        if (!z3 || (wifiEntry = this.mWifiEntry) == null || !wifiEntry.isSaved()) {
            showEapFieldsByMethod(this.mEapMethodSpinner.getSelectedItemPosition());
            return;
        }
        WifiConfiguration wifiConfiguration = this.mWifiEntry.getWifiConfiguration();
        WifiEnterpriseConfig wifiEnterpriseConfig = wifiConfiguration.enterpriseConfig;
        int eapMethod = wifiEnterpriseConfig.getEapMethod();
        int phase2Method = wifiEnterpriseConfig.getPhase2Method();
        this.mEapMethodSpinner.setSelection(eapMethod);
        this.mLastShownEapMethod = eapMethod;
        showEapFieldsByMethod(eapMethod);
        if (eapMethod != 0) {
            if (eapMethod == 2) {
                if (phase2Method == 1) {
                    this.mPhase2Spinner.setSelection(0);
                } else if (phase2Method == 2) {
                    this.mPhase2Spinner.setSelection(1);
                } else if (phase2Method == 3) {
                    this.mPhase2Spinner.setSelection(2);
                } else if (phase2Method != 4) {
                    WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                            .m(phase2Method, "Invalid phase 2 method ", "WifiConfigController2");
                } else {
                    this.mPhase2Spinner.setSelection(3);
                }
            }
        } else if (phase2Method == 3) {
            this.mPhase2Spinner.setSelection(0);
        } else if (phase2Method == 4) {
            this.mPhase2Spinner.setSelection(1);
        } else if (phase2Method == 5) {
            this.mPhase2Spinner.setSelection(2);
        } else if (phase2Method == 6) {
            this.mPhase2Spinner.setSelection(3);
        } else if (phase2Method != 7) {
            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                    .m(phase2Method, "Invalid phase 2 method ", "WifiConfigController2");
        } else {
            this.mPhase2Spinner.setSelection(4);
        }
        if (wifiEnterpriseConfig.isAuthenticationSimBased()
                && (indexOfKey =
                                this.mActiveSubscriptionInfos.indexOfKey(
                                        Integer.valueOf(wifiConfiguration.carrierId)))
                        > -1) {
            this.mEapSimSpinner.setSelection(indexOfKey);
        }
        if (TextUtils.isEmpty(wifiEnterpriseConfig.getCaPath())) {
            String[] caCertificateAliases = wifiEnterpriseConfig.getCaCertificateAliases();
            if (caCertificateAliases == null) {
                if (this.mIsTrustOnFirstUseSupported
                        && wifiEnterpriseConfig.isTrustOnFirstUseEnabled()) {
                    setSelection(this.mEapCaCertSpinner, this.mTrustOnFirstUse);
                } else {
                    setSelection(this.mEapCaCertSpinner, this.mUnspecifiedCertString);
                }
            } else if (caCertificateAliases.length == 1) {
                setSelection(this.mEapCaCertSpinner, caCertificateAliases[0]);
            } else {
                loadCertificates(
                        this.mEapCaCertSpinner,
                        this.mAndroidKeystoreAliasLoader.mCaCertAliases,
                        null,
                        true,
                        true);
                setSelection(this.mEapCaCertSpinner, this.mMultipleCertSetString);
            }
        } else {
            setSelection(this.mEapCaCertSpinner, this.mUseSystemCertsString);
        }
        this.mEapMinTlsVerSpinner.setSelection(wifiEnterpriseConfig.getMinimumTlsVersion());
        this.mEapOcspSpinner.setSelection(wifiEnterpriseConfig.getOcsp());
        this.mEapDomainView.setText(wifiEnterpriseConfig.getDomainSuffixMatch());
        String clientCertificateAlias = wifiEnterpriseConfig.getClientCertificateAlias();
        if (TextUtils.isEmpty(clientCertificateAlias)) {
            setSelection(this.mEapUserCertSpinner, this.mDoNotProvideEapUserCertString);
        } else {
            setSelection(this.mEapUserCertSpinner, clientCertificateAlias);
        }
        this.mEapIdentityView.setText(wifiEnterpriseConfig.getIdentity());
        this.mEapAnonymousView.setText(wifiEnterpriseConfig.getAnonymousIdentity());
    }

    public final void showWarningMessagesIfAppropriate() {
        this.mView.findViewById(R.id.no_user_cert_warning).setVisibility(8);
        this.mView.findViewById(R.id.no_domain_warning).setVisibility(8);
        this.mView.findViewById(R.id.ssid_too_long_warning).setVisibility(8);
        TextView textView = this.mSsidView;
        if (textView != null && WifiUtils.isSSIDTooLong(textView.getText().toString())) {
            this.mView.findViewById(R.id.ssid_too_long_warning).setVisibility(0);
        }
        if (this.mEapCaCertSpinner != null
                && this.mView.findViewById(R.id.l_ca_cert).getVisibility() != 8
                && this.mEapDomainView != null
                && this.mView.findViewById(R.id.l_domain).getVisibility() != 8
                && TextUtils.isEmpty(this.mEapDomainView.getText().toString())) {
            this.mView.findViewById(R.id.no_domain_warning).setVisibility(0);
        }
        if (this.mWifiEntrySecurity == 6
                && this.mEapMethodSpinner.getSelectedItemPosition() == 1
                && ((String) this.mEapUserCertSpinner.getSelectedItem())
                        .equals(this.mUnspecifiedCertString)) {
            this.mView.findViewById(R.id.no_user_cert_warning).setVisibility(0);
        }
    }

    public final void updatePassword() {
        ((TextView) this.mView.findViewById(R.id.password))
                .setInputType(
                        (((CheckBox) this.mView.findViewById(R.id.show_password)).isChecked()
                                        ? 144
                                        : 128)
                                | 1);
    }

    /* JADX WARN: Removed duplicated region for block: B:71:0x038c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public WifiConfigController2(
            com.android.settings.wifi.WifiConfigUiBase2 r20,
            android.view.View r21,
            com.android.wifitrackerlib.WifiEntry r22,
            int r23,
            boolean r24,
            android.net.wifi.WifiManager r25,
            com.android.settings.utils.AndroidKeystoreAliasLoader r26) {
        /*
            Method dump skipped, instructions count: 1273
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.wifi.WifiConfigController2.<init>(com.android.settings.wifi.WifiConfigUiBase2,"
                    + " android.view.View, com.android.wifitrackerlib.WifiEntry, int, boolean,"
                    + " android.net.wifi.WifiManager,"
                    + " com.android.settings.utils.AndroidKeystoreAliasLoader):void");
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onNothingSelected(AdapterView adapterView) {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.wifi.WifiConfigController2$2, reason: invalid class name */
    public final class AnonymousClass2 implements TextWatcher {
        public final /* synthetic */ TextView val$view;

        public AnonymousClass2(TextView textView) {
            this.val$view = textView;
        }

        @Override // android.text.TextWatcher
        public final void afterTextChanged(Editable editable) {
            if (editable.length() != 0) {
                ThreadUtils.postOnMainThread(
                        new WifiConfigController2$$ExternalSyntheticLambda1(this));
                return;
            }
            if (this.val$view.getId() == R.id.gateway) {
                WifiConfigController2.this.mGatewayView.setHint(R.string.wifi_gateway_hint);
            } else if (this.val$view.getId() == R.id.network_prefix_length) {
                WifiConfigController2.this.mNetworkPrefixLengthView.setHint(
                        R.string.wifi_network_prefix_length_hint);
            } else if (this.val$view.getId() == R.id.dns1) {
                WifiConfigController2.this.mDns1View.setHint(R.string.wifi_dns1_hint);
            }
            Button submitButton = WifiConfigController2.this.mConfigUi.getSubmitButton();
            if (submitButton == null) {
                return;
            }
            submitButton.setEnabled(false);
        }

        @Override // android.text.TextWatcher
        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override // android.text.TextWatcher
        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
    }

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
}

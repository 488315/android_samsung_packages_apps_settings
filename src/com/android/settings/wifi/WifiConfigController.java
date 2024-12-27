package com.android.settings.wifi;

import android.content.Context;
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

import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.net.module.util.ProxyUtils;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.network.SubscriptionUtil;
import com.android.settings.utils.AndroidKeystoreAliasLoader;
import com.android.settings.wifi.dpp.WifiDppUtils;
import com.android.settingslib.utils.ThreadUtils;
import com.android.settingslib.wifi.AccessPoint;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.settings.ImsProfile;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiConfigController
        implements TextWatcher,
                AdapterView.OnItemSelectedListener,
                CompoundButton.OnCheckedChangeListener,
                TextView.OnEditorActionListener,
                View.OnKeyListener {
    static final int DHCP_SPINNER_INDEX_SEND_DHCP_HOST_NAME_DISABLE = 1;
    static final int DHCP_SPINNER_INDEX_SEND_DHCP_HOST_NAME_ENABLE = 0;
    static final int PRIVACY_SPINNER_INDEX_DEVICE_MAC = 1;
    static final int PRIVACY_SPINNER_INDEX_RANDOMIZED_MAC = 0;
    static final String[] UNDESIRED_CERTIFICATES = {"MacRandSecret", "MacRandSapSecret"};
    public final AccessPoint mAccessPoint;
    int mAccessPointSecurity;
    public final List mActiveSubscriptionInfos;
    public final WifiConfigUiBase mConfigUi;
    public final Context mContext;
    public Spinner mDhcpSettingsSpinner;
    public TextView mDns1View;
    public TextView mDns2View;
    public String mDoNotProvideEapUserCertString;
    public TextView mEapAnonymousView;
    public Spinner mEapCaCertSpinner;
    public TextView mEapDomainView;
    public TextView mEapIdentityView;
    Spinner mEapMethodSpinner;
    public Spinner mEapOcspSpinner;
    Spinner mEapSimSpinner;
    public Spinner mEapUserCertSpinner;
    public TextView mGatewayView;
    public Spinner mHiddenSettingsSpinner;
    public TextView mHiddenWarningView;
    public ProxyInfo mHttpProxy;
    public TextView mIpAddressView;
    public IpConfiguration.IpAssignment mIpAssignment;
    public Spinner mIpSettingsSpinner;
    public String[] mLevels;
    public Spinner mMeteredSettingsSpinner;
    public int mMode;
    public String mMultipleCertSetString;
    public TextView mNetworkPrefixLengthView;
    public TextView mPasswordView;
    public ArrayAdapter mPhase2Adapter;
    public ArrayAdapter mPhase2PeapAdapter;
    public Spinner mPhase2Spinner;
    public ArrayAdapter mPhase2TtlsAdapter;
    public Spinner mPrivacySettingsSpinner;
    public TextView mProxyExclusionListView;
    public TextView mProxyHostView;
    public TextView mProxyPacView;
    public TextView mProxyPortView;
    public IpConfiguration.ProxySettings mProxySettings;
    public Spinner mProxySettingsSpinner;
    public final boolean mRequestFocus;
    Integer[] mSecurityInPosition;
    public Spinner mSecuritySpinner;
    public CheckBox mSharedCheckBox;
    public ImageButton mSsidScanButton;
    public TextView mSsidView;
    public StaticIpConfiguration mStaticIpConfiguration;
    public String mUnspecifiedCertString;
    public String mUseSystemCertsString;
    public final View mView;
    public final WifiManager mWifiManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.wifi.WifiConfigController$1, reason: invalid class name */
    public final class AnonymousClass1 extends View.AccessibilityDelegate {
        @Override // android.view.View.AccessibilityDelegate
        public final void sendAccessibilityEvent(View view, int i) {
            if (i == 4) {
                return;
            }
            super.sendAccessibilityEvent(view, i);
        }
    }

    public WifiConfigController(
            WifiConfigUiBase wifiConfigUiBase, View view, AccessPoint accessPoint, int i) {
        this.mIpAssignment = IpConfiguration.IpAssignment.UNASSIGNED;
        this.mProxySettings = IpConfiguration.ProxySettings.UNASSIGNED;
        this.mHttpProxy = null;
        this.mStaticIpConfiguration = null;
        this.mRequestFocus = true;
        this.mActiveSubscriptionInfos = new ArrayList();
        this.mConfigUi = wifiConfigUiBase;
        this.mView = view;
        this.mAccessPoint = accessPoint;
        Context context = wifiConfigUiBase.getContext();
        this.mContext = context;
        this.mRequestFocus = true;
        this.mWifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
        initWifiConfigController(accessPoint, i);
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
        ThreadUtils.postOnMainThread(
                new Runnable() { // from class:
                                 // com.android.settings.wifi.WifiConfigController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        WifiConfigController wifiConfigController = WifiConfigController.this;
                        wifiConfigController.showWarningMessagesIfAppropriate();
                        wifiConfigController.enableSubmitIfAppropriate();
                    }
                });
    }

    public final void enableSubmitIfAppropriate() {
        Button button = ((WifiDialog) this.mConfigUi).getButton(-1);
        if (button == null) {
            return;
        }
        button.setEnabled(isSubmittable());
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

    public AndroidKeystoreAliasLoader getAndroidKeystoreAliasLoader() {
        return new AndroidKeystoreAliasLoader(102);
    }

    public String getSignalString() {
        int level;
        if (!this.mAccessPoint.isReachable() || (level = this.mAccessPoint.getLevel()) <= -1) {
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0388  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initWifiConfigController(
            com.android.settingslib.wifi.AccessPoint r17, int r18) {
        /*
            Method dump skipped, instructions count: 1618
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.wifi.WifiConfigController.initWifiConfigController(com.android.settingslib.wifi.AccessPoint,"
                    + " int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:145:0x0031, code lost:

       if (r0.matches("[0-9A-Fa-f]{64}") != false) goto L19;
    */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x003e, code lost:

       if (r0.length() <= 63) goto L19;
    */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x0059, code lost:

       if (r0.length() <= 63) goto L27;
    */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0203  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0243 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0294 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00a0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isSubmittable() {
        /*
            Method dump skipped, instructions count: 700
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.wifi.WifiConfigController.isSubmittable():boolean");
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
        }
        if (collection != null && collection.size() != 0) {
            arrayList.addAll(
                    (Collection)
                            collection.stream()
                                    .filter(new WifiConfigController$$ExternalSyntheticLambda1())
                                    .collect(Collectors.toList()));
        }
        if (!TextUtils.isEmpty(str) && this.mAccessPointSecurity != 6) {
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
        for (SubscriptionInfo subscriptionInfo : activeSubscriptionInfoList) {
            for (SubscriptionInfo subscriptionInfo2 : this.mActiveSubscriptionInfos) {
                subscriptionInfo.getCarrierId();
                subscriptionInfo2.getCarrierId();
            }
            this.mActiveSubscriptionInfos.add(subscriptionInfo);
        }
        if (this.mActiveSubscriptionInfos.size() == 0) {
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
        ArrayList arrayList = new ArrayList();
        Iterator it = this.mActiveSubscriptionInfos.iterator();
        while (it.hasNext()) {
            arrayList.add(
                    SubscriptionUtil.getUniqueSubscriptionDisplayName(
                            this.mContext, (SubscriptionInfo) it.next()));
        }
        this.mEapSimSpinner.setAdapter(
                (SpinnerAdapter)
                        getSpinnerAdapter(
                                (String[]) arrayList.toArray(new String[arrayList.size()])));
        this.mEapSimSpinner.setSelection(0);
        if (arrayList.size() == 1) {
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
        WifiDialog wifiDialog = (WifiDialog) this.mConfigUi;
        WifiDialog.WifiDialogListener wifiDialogListener = wifiDialog.mListener;
        if (wifiDialogListener != null) {
            ((WifiDialogActivity) wifiDialogListener).onSubmit(wifiDialog);
        }
        wifiDialog.dismiss();
        return true;
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onItemSelected(AdapterView adapterView, View view, int i, long j) {
        if (adapterView == this.mSecuritySpinner) {
            this.mAccessPointSecurity = this.mSecurityInPosition[i].intValue();
            showSecurityFields(true, true);
            if (WifiDppUtils.isSupportEnrolleeQrCodeScanner(
                    this.mContext, this.mAccessPointSecurity)) {
                this.mSsidScanButton.setVisibility(0);
            } else {
                this.mSsidScanButton.setVisibility(8);
            }
        } else {
            Spinner spinner = this.mEapMethodSpinner;
            if (adapterView == spinner) {
                showSecurityFields(false, true);
            } else if (adapterView == this.mEapCaCertSpinner) {
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
        WifiDialog wifiDialog = (WifiDialog) this.mConfigUi;
        WifiDialog.WifiDialogListener wifiDialogListener = wifiDialog.mListener;
        if (wifiDialogListener != null) {
            ((WifiDialogActivity) wifiDialogListener).onSubmit(wifiDialog);
        }
        wifiDialog.dismiss();
        return true;
    }

    public final void setAnonymousIdentInvisible() {
        this.mView.findViewById(R.id.l_anonymous).setVisibility(8);
        this.mEapAnonymousView.setText(ApnSettings.MVNO_NONE);
    }

    public final void setDomainInvisible() {
        this.mView.findViewById(R.id.l_domain).setVisibility(8);
        this.mEapDomainView.setText(ApnSettings.MVNO_NONE);
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
                this.mView.findViewById(R.id.l_anonymous).setVisibility(0);
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
                this.mView.findViewById(R.id.l_anonymous).setVisibility(0);
                setUserCertInvisible();
                this.mView.findViewById(R.id.l_sim).setVisibility(8);
                break;
            case 3:
                this.mView.findViewById(R.id.l_phase2).setVisibility(8);
                this.mView.findViewById(R.id.l_ca_cert).setVisibility(8);
                setSelection(this.mEapCaCertSpinner, this.mUnspecifiedCertString);
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
                setOcspInvisible();
                setDomainInvisible();
                setUserCertInvisible();
                setPasswordInvisible();
                this.mView.findViewById(R.id.l_identity).setVisibility(8);
                break;
        }
        if (this.mView.findViewById(R.id.l_ca_cert).getVisibility() == 8
                || !((String) this.mEapCaCertSpinner.getSelectedItem())
                        .equals(this.mUnspecifiedCertString)) {
            return;
        }
        setDomainInvisible();
        setOcspInvisible();
    }

    public final void showIpConfigFields() {
        StaticIpConfiguration staticIpConfiguration;
        this.mView.findViewById(R.id.ip_fields).setVisibility(0);
        AccessPoint accessPoint = this.mAccessPoint;
        WifiConfiguration wifiConfiguration =
                (accessPoint == null || !accessPoint.isSaved()) ? null : this.mAccessPoint.mConfig;
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
            textView2.addTextChangedListener(this);
            TextView textView3 = (TextView) this.mView.findViewById(R.id.network_prefix_length);
            this.mNetworkPrefixLengthView = textView3;
            textView3.addTextChangedListener(this);
            TextView textView4 = (TextView) this.mView.findViewById(R.id.dns1);
            this.mDns1View = textView4;
            textView4.addTextChangedListener(this);
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
        this.mView.findViewById(R.id.l_anonymous).setVisibility(0);
        this.mView.findViewById(R.id.password_layout).setVisibility(0);
        this.mView.findViewById(R.id.show_password_layout).setVisibility(0);
        this.mView.findViewById(R.id.l_sim).setVisibility(8);
    }

    public final void showProxyFields() {
        ProxyInfo httpProxy;
        ProxyInfo httpProxy2;
        this.mView.findViewById(R.id.proxy_settings_fields).setVisibility(0);
        AccessPoint accessPoint = this.mAccessPoint;
        WifiConfiguration wifiConfiguration =
                (accessPoint == null || !accessPoint.isSaved()) ? null : this.mAccessPoint.mConfig;
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
        AccessPoint accessPoint;
        int i = this.mAccessPointSecurity;
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
            AccessPoint accessPoint2 = this.mAccessPoint;
            if (accessPoint2 != null && accessPoint2.isSaved()) {
                this.mPasswordView.setHint(R.string.wifi_unchanged);
            }
        }
        int i2 = this.mAccessPointSecurity;
        if (i2 != 3 && i2 != 6) {
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
            this.mEapOcspSpinner.setAccessibilityDelegate(anonymousClass1);
            this.mEapUserCertSpinner.setAccessibilityDelegate(anonymousClass1);
            z3 = true;
        } else {
            z3 = false;
        }
        if (z) {
            if (this.mAccessPointSecurity == 6) {
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
            AndroidKeystoreAliasLoader androidKeystoreAliasLoader = getAndroidKeystoreAliasLoader();
            loadCertificates(
                    this.mEapCaCertSpinner,
                    androidKeystoreAliasLoader.mCaCertAliases,
                    null,
                    false,
                    true);
            loadCertificates(
                    this.mEapUserCertSpinner,
                    androidKeystoreAliasLoader.mKeyCertAliases,
                    this.mDoNotProvideEapUserCertString,
                    false,
                    false);
            setSelection(this.mEapCaCertSpinner, this.mUseSystemCertsString);
        }
        if (!z3 || (accessPoint = this.mAccessPoint) == null || !accessPoint.isSaved()) {
            showEapFieldsByMethod(this.mEapMethodSpinner.getSelectedItemPosition());
            return;
        }
        WifiConfiguration wifiConfiguration = this.mAccessPoint.mConfig;
        WifiEnterpriseConfig wifiEnterpriseConfig = wifiConfiguration.enterpriseConfig;
        int eapMethod = wifiEnterpriseConfig.getEapMethod();
        int phase2Method = wifiEnterpriseConfig.getPhase2Method();
        this.mEapMethodSpinner.setSelection(eapMethod);
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
                            .m(phase2Method, "Invalid phase 2 method ", "WifiConfigController");
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
                    .m(phase2Method, "Invalid phase 2 method ", "WifiConfigController");
        } else {
            this.mPhase2Spinner.setSelection(4);
        }
        if (wifiEnterpriseConfig.isAuthenticationSimBased()) {
            int i3 = 0;
            while (true) {
                if (i3 >= ((ArrayList) this.mActiveSubscriptionInfos).size()) {
                    break;
                }
                if (wifiConfiguration.carrierId
                        == ((SubscriptionInfo) ((ArrayList) this.mActiveSubscriptionInfos).get(i3))
                                .getCarrierId()) {
                    this.mEapSimSpinner.setSelection(i3);
                    break;
                }
                i3++;
            }
        }
        if (TextUtils.isEmpty(wifiEnterpriseConfig.getCaPath())) {
            String[] caCertificateAliases = wifiEnterpriseConfig.getCaCertificateAliases();
            if (caCertificateAliases == null) {
                setSelection(this.mEapCaCertSpinner, this.mUnspecifiedCertString);
            } else if (caCertificateAliases.length == 1) {
                setSelection(this.mEapCaCertSpinner, caCertificateAliases[0]);
            } else {
                loadCertificates(
                        this.mEapCaCertSpinner,
                        getAndroidKeystoreAliasLoader().mCaCertAliases,
                        null,
                        true,
                        true);
                setSelection(this.mEapCaCertSpinner, this.mMultipleCertSetString);
            }
        } else {
            setSelection(this.mEapCaCertSpinner, this.mUseSystemCertsString);
        }
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
        if (this.mAccessPointSecurity == 6
                && this.mEapMethodSpinner.getSelectedItemPosition() == 1
                && ((String) this.mEapUserCertSpinner.getSelectedItem())
                        .equals(this.mUnspecifiedCertString)) {
            this.mView.findViewById(R.id.no_user_cert_warning).setVisibility(0);
        }
    }

    public WifiConfigController(
            WifiConfigUiBase wifiConfigUiBase,
            View view,
            AccessPoint accessPoint,
            int i,
            WifiManager wifiManager) {
        this.mIpAssignment = IpConfiguration.IpAssignment.UNASSIGNED;
        this.mProxySettings = IpConfiguration.ProxySettings.UNASSIGNED;
        this.mHttpProxy = null;
        this.mStaticIpConfiguration = null;
        this.mRequestFocus = true;
        this.mActiveSubscriptionInfos = new ArrayList();
        this.mConfigUi = wifiConfigUiBase;
        this.mView = view;
        this.mAccessPoint = accessPoint;
        this.mContext = wifiConfigUiBase.getContext();
        this.mWifiManager = wifiManager;
        initWifiConfigController(accessPoint, i);
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onNothingSelected(AdapterView adapterView) {}

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
}

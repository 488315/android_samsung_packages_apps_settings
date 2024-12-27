package com.samsung.android.settings.wifi.details;

import android.content.Context;
import android.content.res.ColorStateList;
import android.net.wifi.WifiConfiguration;
import android.os.ServiceManager;
import android.security.legacykeystore.ILegacyKeystore;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.preference.DropDownPreference;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.network.SubscriptionUtil;
import com.android.settings.utils.AndroidKeystoreAliasLoader;
import com.android.settings.wifi.WifiUtils;
import com.android.settingslib.Utils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.LayoutPreference;
import com.android.wifitrackerlib.WifiEntry;

import com.google.common.collect.AbstractMultimap;
import com.google.common.collect.HashMultimap;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.knox.keystore.CertificateProvisioning;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class WifiConfigurePreferenceController extends AbstractPreferenceController {

    @VisibleForTesting
    static final String[] UNDESIRED_CERTIFICATES = {"MacRandSecret", "MacRandSapSecret"};

    public final String TAG;
    public final ArrayMap mActiveSubscriptionInfos;
    public boolean mCanChangeEapMethod;
    public final Context mContext;
    public final String mDoNotProvideEapUserCertString;
    public final String mDoNotValidateEapServerString;
    public DropDownPreference mEapCaCertPref;
    public LayoutPreference mEapDomainPref;
    public EditText mEapDomainView;
    public final AnonymousClass1 mEapDomainViewWatcher;
    public LayoutPreference mEapIdPref;
    public EditText mEapIdentityView;
    public WifiEapPreferenceController.AnonymousClass1 mEapMethodChangeListener;
    public DropDownPreference mEapMethodPref;
    public DropDownPreference mEapOcspPref;
    public DropDownPreference mEapSimPref;
    public DropDownPreference mEapUserCertPref;
    public final Fragment mFragment;
    public boolean mIsTrustOnFirstUseSupported;
    public DropDownPreference mMinTlsVersionPref;
    public final int mMode;
    public final String mMultipleCertSetString;
    public TextView mPasswordErrorText;
    public LayoutPreference mPasswordPref;
    public EditText mPasswordView;
    public final AnonymousClass1 mPasswordWatcher;
    public TextView mPaswordTitleView;
    public String mTempPassword;
    public final String mTrustOnFirstUse;
    public final String mUnspecifiedCertString;
    public final String mUseSystemCertsString;
    public WifiConfiguration mWifiConfig;
    public final WifiEntry mWifiEntry;
    public int mWifiEntrySecurity;

    /* JADX WARN: Type inference failed for: r0v5, types: [com.samsung.android.settings.wifi.details.WifiConfigurePreferenceController$1] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.samsung.android.settings.wifi.details.WifiConfigurePreferenceController$1] */
    public WifiConfigurePreferenceController(
            Context context, Fragment fragment, WifiEntry wifiEntry) {
        super(context);
        this.TAG = getLogTag();
        this.mMode = getMode();
        this.mCanChangeEapMethod = true;
        this.mTempPassword = ApnSettings.MVNO_NONE;
        this.mActiveSubscriptionInfos = new ArrayMap();
        final int i = 0;
        this.mPasswordWatcher =
                new TextWatcher(
                        this) { // from class:
                                // com.samsung.android.settings.wifi.details.WifiConfigurePreferenceController.1
                    public final /* synthetic */ WifiConfigurePreferenceController this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.text.TextWatcher
                    public final void afterTextChanged(Editable editable) {
                        switch (i) {
                            case 0:
                                this.this$0.enableSubmitIfAppropriate();
                                break;
                            default:
                                WifiConfigurePreferenceController
                                        wifiConfigurePreferenceController = this.this$0;
                                wifiConfigurePreferenceController.mWifiConfig.enterpriseConfig
                                        .setDomainSuffixMatch(
                                                wifiConfigurePreferenceController
                                                        .mEapDomainView
                                                        .getText()
                                                        .toString());
                                this.this$0.enableSubmitIfAppropriate();
                                break;
                        }
                    }

                    @Override // android.text.TextWatcher
                    public final void beforeTextChanged(
                            CharSequence charSequence, int i2, int i3, int i4) {
                        int i5 = i;
                    }

                    @Override // android.text.TextWatcher
                    public final void onTextChanged(
                            CharSequence charSequence, int i2, int i3, int i4) {
                        switch (i) {
                            case 0:
                                this.this$0.passwordCheck(charSequence.toString());
                                break;
                        }
                    }

                    private final void
                            beforeTextChanged$com$samsung$android$settings$wifi$details$WifiConfigurePreferenceController$1(
                                    int i2, int i3, int i4, CharSequence charSequence) {}

                    private final void
                            beforeTextChanged$com$samsung$android$settings$wifi$details$WifiConfigurePreferenceController$2(
                                    int i2, int i3, int i4, CharSequence charSequence) {}

                    private final void
                            onTextChanged$com$samsung$android$settings$wifi$details$WifiConfigurePreferenceController$2(
                                    int i2, int i3, int i4, CharSequence charSequence) {}
                };
        final int i2 = 1;
        this.mEapDomainViewWatcher =
                new TextWatcher(
                        this) { // from class:
                                // com.samsung.android.settings.wifi.details.WifiConfigurePreferenceController.1
                    public final /* synthetic */ WifiConfigurePreferenceController this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.text.TextWatcher
                    public final void afterTextChanged(Editable editable) {
                        switch (i2) {
                            case 0:
                                this.this$0.enableSubmitIfAppropriate();
                                break;
                            default:
                                WifiConfigurePreferenceController
                                        wifiConfigurePreferenceController = this.this$0;
                                wifiConfigurePreferenceController.mWifiConfig.enterpriseConfig
                                        .setDomainSuffixMatch(
                                                wifiConfigurePreferenceController
                                                        .mEapDomainView
                                                        .getText()
                                                        .toString());
                                this.this$0.enableSubmitIfAppropriate();
                                break;
                        }
                    }

                    @Override // android.text.TextWatcher
                    public final void beforeTextChanged(
                            CharSequence charSequence, int i22, int i3, int i4) {
                        int i5 = i2;
                    }

                    @Override // android.text.TextWatcher
                    public final void onTextChanged(
                            CharSequence charSequence, int i22, int i3, int i4) {
                        switch (i2) {
                            case 0:
                                this.this$0.passwordCheck(charSequence.toString());
                                break;
                        }
                    }

                    private final void
                            beforeTextChanged$com$samsung$android$settings$wifi$details$WifiConfigurePreferenceController$1(
                                    int i22, int i3, int i4, CharSequence charSequence) {}

                    private final void
                            beforeTextChanged$com$samsung$android$settings$wifi$details$WifiConfigurePreferenceController$2(
                                    int i22, int i3, int i4, CharSequence charSequence) {}

                    private final void
                            onTextChanged$com$samsung$android$settings$wifi$details$WifiConfigurePreferenceController$2(
                                    int i22, int i3, int i4, CharSequence charSequence) {}
                };
        this.mContext = context;
        this.mFragment = fragment;
        this.mWifiEntry = wifiEntry;
        this.mUnspecifiedCertString = context.getString(R.string.wifi_select_cert);
        this.mDoNotValidateEapServerString =
                context.getString(R.string.wifi_do_not_validate_eap_server);
        this.mUseSystemCertsString = context.getString(R.string.wifi_use_system_certs);
        this.mDoNotProvideEapUserCertString =
                context.getString(R.string.wifi_do_not_provide_eap_user_cert);
        this.mTrustOnFirstUse = context.getString(R.string.wifi_trust_on_first_use);
        this.mMultipleCertSetString = context.getString(R.string.wifi_multiple_cert_added);
    }

    public static int findIndexOfValue(DropDownPreference dropDownPreference, String str) {
        CharSequence[] charSequenceArr = dropDownPreference.mEntries;
        if (charSequenceArr == null) {
            return -1;
        }
        int i = 0;
        for (CharSequence charSequence : charSequenceArr) {
            if (str.equals(charSequence)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @VisibleForTesting
    private AndroidKeystoreAliasLoader getAndroidKeystoreAliasLoader() {
        return new AndroidKeystoreAliasLoader(102);
    }

    public static boolean isEnterpriseNetwork(int i) {
        return i == 3 || i == 7;
    }

    public abstract void afterShowSecurityFields();

    public final void clearErrorTextUnderEditText(
            TextView textView, EditText editText, TextView textView2) {
        if (textView == null) {
            return;
        }
        textView.setTextColor(
                this.mContext.getColor(R.color.sec_wifi_ap_activity_label_text_color));
        textView2.setVisibility(8);
        editText.requestFocus();
        editText.setBackgroundTintList(
                this.mContext
                        .getResources()
                        .getColorStateList(R.color.wifi_ap_dialog_title_text_color));
        editText.invalidate();
    }

    public final void disableViewsIfAppropriate$4() {
        WifiEntry wifiEntry = this.mWifiEntry;
        if (wifiEntry == null || !wifiEntry.isSaved()) {
            return;
        }
        if (WifiUtils.isBlockedByEnterprise(this.mContext, wifiEntry.getSsid())
                || WifiUtils.isNetworkLockedDown(this.mContext, wifiEntry.getWifiConfiguration())) {
            DropDownPreference dropDownPreference = this.mEapMethodPref;
            if (dropDownPreference != null) {
                dropDownPreference.setEnabled(false);
            }
            DropDownPreference dropDownPreference2 = this.mEapCaCertPref;
            if (dropDownPreference2 != null) {
                dropDownPreference2.setEnabled(false);
            }
            DropDownPreference dropDownPreference3 = this.mEapUserCertPref;
            if (dropDownPreference3 != null) {
                dropDownPreference3.setEnabled(false);
            }
        }
    }

    public abstract void enableSubmitIfAppropriate();

    public final void finishActivity$2() {
        Fragment fragment = this.mFragment;
        if (fragment.getActivity() instanceof SettingsActivity) {
            ((SettingsActivity) fragment.getActivity()).finishPreferencePanel(null);
            return;
        }
        FragmentActivity activity = fragment.getActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    public final String getEapMethod$1() {
        WifiConfiguration wifiConfiguration = this.mWifiConfig;
        if (wifiConfiguration == null) {
            return "PEAP";
        }
        switch (wifiConfiguration.enterpriseConfig.getEapMethod()) {
            case 1:
                return "TLS";
            case 2:
                return "TTLS";
            case 3:
                return "PWD";
            case 4:
                return UniversalCredentialManager.APPLET_FORM_FACTOR_SIM;
            case 5:
                return "AKA";
            case 6:
                return "AKA'";
            default:
                return "PEAP";
        }
    }

    public abstract String getLogTag();

    public abstract int getMode();

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    public abstract boolean isUnchangedNeeded();

    public final void loadCertificates(
            DropDownPreference dropDownPreference, String str, String str2, boolean z, boolean z2) {
        String[] strArr;
        Collection collection;
        AndroidKeystoreAliasLoader androidKeystoreAliasLoader = getAndroidKeystoreAliasLoader();
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.mUnspecifiedCertString);
        if (z) {
            arrayList.add(this.mMultipleCertSetString);
        }
        if (z2) {
            arrayList.add(this.mUseSystemCertsString);
        }
        if (z2 && this.mIsTrustOnFirstUseSupported && this.mWifiEntrySecurity != 6) {
            arrayList.add(this.mTrustOnFirstUse);
        }
        if (TextUtils.equals(str, CertificateProvisioning.CA_CERTIFICATE)) {
            collection = androidKeystoreAliasLoader.mCaCertAliases;
        } else if (TextUtils.equals(str, "USRPKEY_")) {
            collection = androidKeystoreAliasLoader.mKeyCertAliases;
        } else {
            ILegacyKeystore asInterface =
                    ILegacyKeystore.Stub.asInterface(
                            ServiceManager.checkService("android.security.legacykeystore"));
            if (asInterface != null) {
                try {
                    strArr =
                            asInterface.list(
                                    str, EnterpriseContainerCallback.CONTAINER_MOUNT_STATUS);
                } catch (Exception unused) {
                    Log.e(
                            this.TAG,
                            "can't get the certificate list from android.security.legacykeystore");
                }
                if (strArr != null && strArr.length != 0) {
                    final int i = 0;
                    arrayList.addAll(
                            Arrays.stream(strArr)
                                    .filter(
                                            new Predicate() { // from class:
                                                              // com.samsung.android.settings.wifi.details.WifiConfigurePreferenceController$$ExternalSyntheticLambda0
                                                @Override // java.util.function.Predicate
                                                public final boolean test(Object obj) {
                                                    String str3 = (String) obj;
                                                    switch (i) {
                                                        case 0:
                                                            for (String str4 :
                                                                    WifiConfigurePreferenceController
                                                                            .UNDESIRED_CERTIFICATES) {
                                                                if (str3.startsWith(str4)) {
                                                                    break;
                                                                }
                                                            }
                                                            break;
                                                        default:
                                                            for (String str5 :
                                                                    WifiConfigurePreferenceController
                                                                            .UNDESIRED_CERTIFICATES) {
                                                                if (str3.startsWith(str5)) {
                                                                    break;
                                                                }
                                                            }
                                                            break;
                                                    }
                                                    return false;
                                                }
                                            })
                                    .toList());
                }
                collection = null;
            }
            strArr = null;
            if (strArr != null) {
                final int i2 = 0;
                arrayList.addAll(
                        Arrays.stream(strArr)
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.samsung.android.settings.wifi.details.WifiConfigurePreferenceController$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                String str3 = (String) obj;
                                                switch (i2) {
                                                    case 0:
                                                        for (String str4 :
                                                                WifiConfigurePreferenceController
                                                                        .UNDESIRED_CERTIFICATES) {
                                                            if (str3.startsWith(str4)) {
                                                                break;
                                                            }
                                                        }
                                                        break;
                                                    default:
                                                        for (String str5 :
                                                                WifiConfigurePreferenceController
                                                                        .UNDESIRED_CERTIFICATES) {
                                                            if (str3.startsWith(str5)) {
                                                                break;
                                                            }
                                                        }
                                                        break;
                                                }
                                                return false;
                                            }
                                        })
                                .toList());
            }
            collection = null;
        }
        if (collection != null && collection.size() != 0) {
            final int i3 = 1;
            arrayList.addAll(
                    collection.stream()
                            .filter(
                                    new Predicate() { // from class:
                                                      // com.samsung.android.settings.wifi.details.WifiConfigurePreferenceController$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            String str3 = (String) obj;
                                            switch (i3) {
                                                case 0:
                                                    for (String str4 :
                                                            WifiConfigurePreferenceController
                                                                    .UNDESIRED_CERTIFICATES) {
                                                        if (str3.startsWith(str4)) {
                                                            break;
                                                        }
                                                    }
                                                    break;
                                                default:
                                                    for (String str5 :
                                                            WifiConfigurePreferenceController
                                                                    .UNDESIRED_CERTIFICATES) {
                                                        if (str3.startsWith(str5)) {
                                                            break;
                                                        }
                                                    }
                                                    break;
                                            }
                                            return false;
                                        }
                                    })
                            .toList());
        }
        if (this.mWifiEntrySecurity != 6) {
            arrayList.add(str2);
        }
        CharSequence[] charSequenceArr = new CharSequence[arrayList.size()];
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            charSequenceArr[i4] = String.valueOf(i4);
        }
        CharSequence[] charSequenceArr2 =
                (CharSequence[]) arrayList.toArray(new String[arrayList.size()]);
        dropDownPreference.setPersistent();
        dropDownPreference.setEntries(charSequenceArr2);
        dropDownPreference.mEntryValues = charSequenceArr;
    }

    public final void passwordCheck(String str) {
        int length = str.length();
        if (length == 0) {
            return;
        }
        int i = this.mWifiEntrySecurity;
        if (i == 1) {
            boolean matches = str.matches("[0-9A-Fa-f]*");
            if (length <= 13 || (matches && length <= 26)) {
                clearErrorTextUnderEditText(
                        this.mPaswordTitleView, this.mPasswordView, this.mPasswordErrorText);
                return;
            }
            if (matches) {
                this.mPasswordView.setText(str.substring(0, 26));
            } else {
                this.mPasswordView.setText(str.substring(0, 13));
            }
            showErrorTextUnderEditText(
                    this.mPaswordTitleView,
                    this.mPasswordView,
                    this.mPasswordErrorText,
                    R.string.max_char_reached);
            return;
        }
        int i2 = isEnterpriseNetwork(i) ? 200 : !str.matches("[0-9A-Fa-f]*") ? 63 : 64;
        if (str.getBytes().length <= i2) {
            clearErrorTextUnderEditText(
                    this.mPaswordTitleView, this.mPasswordView, this.mPasswordErrorText);
            return;
        }
        int length2 = str.length();
        while (true) {
            if (length2 < 0) {
                break;
            }
            String substring = str.substring(0, length2);
            if (substring.getBytes().length <= i2) {
                this.mPasswordView.setText(substring);
                break;
            }
            length2--;
        }
        showErrorTextUnderEditText(
                this.mPaswordTitleView,
                this.mPasswordView,
                this.mPasswordErrorText,
                R.string.max_char_reached);
    }

    public final void refreshEapItems(int i) {
        WifiConfiguration wifiConfiguration;
        String str = this.TAG;
        Log.d(str, "refreshEapItems");
        this.mEapDomainPref.setVisible(false);
        this.mEapOcspPref.setVisible(false);
        this.mMinTlsVersionPref.setVisible(false);
        CharSequence[] charSequenceArr = this.mEapMethodPref.mEntries;
        if (charSequenceArr == null || i < 0 || i >= charSequenceArr.length) {
            MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                    i, "refreshEapItems: method= ", " entries null or length error", str);
            this.mEapCaCertPref.setVisible(false);
            this.mEapIdPref.setVisible(false);
            this.mEapUserCertPref.setVisible(false);
            this.mEapSimPref.setVisible(false);
            return;
        }
        String charSequence = charSequenceArr[i].toString();
        this.mEapUserCertPref.setVisible(false);
        if ("PEAP".equals(charSequence) || "TTLS".equals(charSequence)) {
            this.mEapCaCertPref.setVisible(true);
            this.mEapIdPref.setVisible(true);
            this.mPasswordPref.setVisible(true);
            this.mEapSimPref.setVisible(false);
            if (isUnchangedNeeded()) {
                this.mPasswordView.setHint(R.string.wifi_unchanged);
                this.mPasswordView.setText(ApnSettings.MVNO_NONE);
            }
        } else if ("TLS".equals(charSequence)) {
            this.mEapCaCertPref.setVisible(true);
            this.mEapUserCertPref.setVisible(true);
            this.mEapIdPref.setVisible(true);
            this.mPasswordPref.setVisible(false);
            this.mEapSimPref.setVisible(false);
        } else if ("PWD".equals(charSequence)) {
            this.mEapCaCertPref.setVisible(false);
            this.mEapIdPref.setVisible(true);
            this.mPasswordPref.setVisible(true);
            this.mEapSimPref.setVisible(false);
            if (isUnchangedNeeded()) {
                this.mPasswordView.setHint(R.string.wifi_unchanged);
                this.mPasswordView.setText(ApnSettings.MVNO_NONE);
            }
        } else {
            this.mEapCaCertPref.setVisible(false);
            this.mEapIdPref.setVisible(false);
            this.mPasswordPref.setVisible(false);
            this.mEapSimPref.setVisible(true);
        }
        if (this.mEapCaCertPref.isVisible()) {
            Log.d(str, "refreshEapItems load Ca Certificates");
            loadCertificates(
                    this.mEapCaCertPref,
                    CertificateProvisioning.CA_CERTIFICATE,
                    this.mDoNotValidateEapServerString,
                    false,
                    true);
            WifiConfiguration wifiConfiguration2 = this.mWifiConfig;
            if (wifiConfiguration2 != null) {
                if (TextUtils.isEmpty(wifiConfiguration2.enterpriseConfig.getCaPath())) {
                    String[] caCertificateAliases =
                            this.mWifiConfig.enterpriseConfig.getCaCertificateAliases();
                    if (caCertificateAliases == null) {
                        int findIndexOfValue =
                                findIndexOfValue(this.mEapCaCertPref, this.mUnspecifiedCertString);
                        if (findIndexOfValue <= 0) {
                            updateSummaryWithWarning(this.mEapCaCertPref);
                        } else {
                            updateSummary$4(this.mEapCaCertPref, findIndexOfValue);
                        }
                    } else if (caCertificateAliases.length == 1) {
                        int findIndexOfValue2 =
                                findIndexOfValue(this.mEapCaCertPref, caCertificateAliases[0]);
                        if (findIndexOfValue2 <= 0) {
                            updateSummaryWithWarning(this.mEapCaCertPref);
                        } else {
                            updateSummary$4(this.mEapCaCertPref, findIndexOfValue2);
                            if (!this.mUnspecifiedCertString.equals(caCertificateAliases[0])
                                    && !this.mDoNotValidateEapServerString.equals(
                                            caCertificateAliases[0])
                                    && !this.mTrustOnFirstUse.equals(caCertificateAliases[0])) {
                                this.mEapOcspPref.setVisible(true);
                                this.mMinTlsVersionPref.setVisible(true);
                            }
                        }
                    } else {
                        loadCertificates(
                                this.mEapCaCertPref,
                                CertificateProvisioning.CA_CERTIFICATE,
                                this.mDoNotValidateEapServerString,
                                true,
                                true);
                        updateSummary$4(this.mEapCaCertPref, 1);
                    }
                } else {
                    updateSummary$4(
                            this.mEapCaCertPref,
                            findIndexOfValue(this.mEapCaCertPref, this.mUseSystemCertsString));
                    this.mEapOcspPref.setVisible(true);
                    this.mMinTlsVersionPref.setVisible(true);
                }
                this.mEapDomainView.setText(
                        this.mWifiConfig.enterpriseConfig.getDomainSuffixMatch());
            }
            setDomainVisible();
        }
        if (this.mEapUserCertPref.isVisible()) {
            Log.d(str, "refreshEapItems load User Certificates");
            loadCertificates(
                    this.mEapUserCertPref,
                    "USRPKEY_",
                    this.mDoNotProvideEapUserCertString,
                    false,
                    false);
            WifiConfiguration wifiConfiguration3 = this.mWifiConfig;
            if (wifiConfiguration3 != null) {
                String clientCertificateAlias =
                        wifiConfiguration3.enterpriseConfig.getClientCertificateAlias();
                if (TextUtils.isEmpty(clientCertificateAlias)) {
                    int findIndexOfValue3 =
                            findIndexOfValue(this.mEapUserCertPref, this.mUnspecifiedCertString);
                    if (findIndexOfValue3 < 0) {
                        updateSummary$4(this.mEapUserCertPref, 0);
                    } else {
                        updateSummary$4(this.mEapUserCertPref, findIndexOfValue3);
                    }
                } else {
                    int findIndexOfValue4 =
                            findIndexOfValue(this.mEapUserCertPref, clientCertificateAlias);
                    if (findIndexOfValue4 < 0) {
                        updateSummary$4(this.mEapUserCertPref, 0);
                    } else {
                        updateSummary$4(this.mEapUserCertPref, findIndexOfValue4);
                    }
                }
            }
        }
        if (this.mEapIdPref.isVisible() && (wifiConfiguration = this.mWifiConfig) != null) {
            String identity = wifiConfiguration.enterpriseConfig.getIdentity();
            if (!TextUtils.isEmpty(identity)) {
                this.mEapIdentityView.setText(identity);
                this.mEapIdentityView.setSelection(identity.length());
            }
        }
        if (this.mMode == 0 && this.mPasswordPref.isVisible() && this.mWifiConfig != null) {
            String str2 = this.mTempPassword;
            if (!TextUtils.isEmpty(str2)) {
                this.mPasswordView.setText(str2);
                this.mPasswordView.setSelection(str2.length());
            }
        }
        if (this.mEapOcspPref.isVisible()) {
            DropDownPreference dropDownPreference = this.mEapOcspPref;
            WifiConfiguration wifiConfiguration4 = this.mWifiConfig;
            updateSummary$4(
                    dropDownPreference,
                    wifiConfiguration4 != null ? wifiConfiguration4.enterpriseConfig.getOcsp() : 0);
        }
    }

    public final void setDefaultEAPPhase2Method(String str) {
        if ("PEAP".equals(str)) {
            this.mWifiConfig.enterpriseConfig.setPhase2Method(3);
        } else if ("TTLS".equals(str)) {
            this.mWifiConfig.enterpriseConfig.setPhase2Method(1);
        }
    }

    public final void setDomainVisible() {
        CharSequence entry = this.mEapCaCertPref.getEntry();
        String charSequence = entry != null ? entry.toString() : ApnSettings.MVNO_NONE;
        if (this.mUnspecifiedCertString.equals(charSequence)
                || this.mDoNotValidateEapServerString.equals(charSequence)
                || this.mTrustOnFirstUse.equals(charSequence)) {
            this.mEapDomainPref.setVisible(false);
            this.mEapDomainView.setText(ApnSettings.MVNO_NONE);
        } else {
            this.mEapDomainPref.setVisible(true);
            this.mEapDomainPref.setEnabled(true);
            this.mEapDomainView.setEnabled(true);
        }
    }

    public final void setMinTlsVersionVisible() {
        CharSequence entry = this.mEapCaCertPref.getEntry();
        String charSequence = entry != null ? entry.toString() : ApnSettings.MVNO_NONE;
        this.mMinTlsVersionPref.setVisible(
                (this.mUnspecifiedCertString.equals(charSequence)
                                || this.mDoNotValidateEapServerString.equals(charSequence)
                                || this.mTrustOnFirstUse.equals(charSequence))
                        ? false
                        : true);
        if (this.mMinTlsVersionPref.isVisible()) {
            DropDownPreference dropDownPreference = this.mMinTlsVersionPref;
            WifiConfiguration wifiConfiguration = this.mWifiConfig;
            updateSummary$4(
                    dropDownPreference,
                    wifiConfiguration != null
                            ? wifiConfiguration.enterpriseConfig.getMinimumTlsVersion()
                            : 0);
        }
    }

    public final void setOcspVisible() {
        CharSequence entry = this.mEapCaCertPref.getEntry();
        String charSequence = entry != null ? entry.toString() : ApnSettings.MVNO_NONE;
        this.mEapOcspPref.setVisible(
                (this.mUnspecifiedCertString.equals(charSequence)
                                || this.mDoNotValidateEapServerString.equals(charSequence)
                                || this.mTrustOnFirstUse.equals(charSequence))
                        ? false
                        : true);
    }

    public final void setupEapItems() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("PEAP");
        arrayList.add("TLS");
        arrayList.add("TTLS");
        arrayList.add("PWD");
        if (!Utils.isWifiOnly(this.mContext)) {
            arrayList.add(UniversalCredentialManager.APPLET_FORM_FACTOR_SIM);
            arrayList.add("AKA");
            arrayList.add("AKA'");
        }
        CharSequence[] charSequenceArr = new CharSequence[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            charSequenceArr[i] = String.valueOf(i);
        }
        this.mEapMethodPref.mEntryValues = charSequenceArr;
        this.mEapMethodPref.setEntries(
                (CharSequence[]) arrayList.toArray(new String[arrayList.size()]));
    }

    public final void setupEapMethodForCarrierSsid() {
        String ssid = this.mWifiEntry.getSsid();
        if (TextUtils.isEmpty(ssid)) {
            return;
        }
        HashMultimap create = HashMultimap.create();
        ArraySet arraySet =
                new ArraySet(
                        this.mContext
                                .getResources()
                                .getStringArray(
                                        R.array.config_wifi_eap_sim_method_carrier_ssid_list));
        ArraySet arraySet2 =
                new ArraySet(
                        this.mContext
                                .getResources()
                                .getStringArray(
                                        R.array.config_wifi_eap_aka_method_carrier_ssid_list));
        ArraySet arraySet3 =
                new ArraySet(
                        this.mContext
                                .getResources()
                                .getStringArray(
                                        R.array.config_wifi_eap_akaprime_method_carrier_ssid_list));
        create.put(arraySet, UniversalCredentialManager.APPLET_FORM_FACTOR_SIM);
        create.put(arraySet2, "AKA");
        create.put(arraySet3, "AKA'");
        AbstractMultimap.EntrySet<Map.Entry> entrySet = create.entries;
        if (entrySet == null) {
            entrySet = new AbstractMultimap.EntrySet(create);
            create.entries = entrySet;
        }
        for (Map.Entry entry : entrySet) {
            Set set = (Set) entry.getKey();
            String str = (String) entry.getValue();
            if (set.contains(ssid)) {
                String str2 = this.TAG;
                Log.d(str2, "the eapMethod of " + ssid + " is set to " + str);
                Log.d(str2, "updateEapMethod : " + str);
                if ("PEAP".equals(str)) {
                    this.mWifiConfig.enterpriseConfig.setEapMethod(0);
                } else if ("TLS".equals(str)) {
                    this.mWifiConfig.enterpriseConfig.setEapMethod(1);
                } else if ("TTLS".equals(str)) {
                    this.mWifiConfig.enterpriseConfig.setEapMethod(2);
                } else if ("PWD".equals(str)) {
                    this.mWifiConfig.enterpriseConfig.setEapMethod(3);
                } else if (UniversalCredentialManager.APPLET_FORM_FACTOR_SIM.equals(str)) {
                    this.mWifiConfig.enterpriseConfig.setEapMethod(4);
                } else if ("AKA".equals(str)) {
                    this.mWifiConfig.enterpriseConfig.setEapMethod(5);
                } else if ("AKA'".equals(str)) {
                    this.mWifiConfig.enterpriseConfig.setEapMethod(6);
                }
                this.mCanChangeEapMethod = false;
                return;
            }
        }
    }

    public final void setupEapMinTlsVer(boolean z) {
        ArrayList arrayList =
                new ArrayList(
                        Arrays.asList(
                                this.mContext
                                        .getResources()
                                        .getStringArray(R.array.wifi_eap_tls_ver)));
        if (!z) {
            Log.w(this.TAG, "Wi-Fi Enterprise TLS v1.3 is not supported on this device");
            arrayList.remove(3);
        }
        CharSequence[] charSequenceArr = new CharSequence[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            charSequenceArr[i] = String.valueOf(i);
        }
        CharSequence[] charSequenceArr2 =
                (CharSequence[]) arrayList.toArray(new String[arrayList.size()]);
        this.mMinTlsVersionPref.setPersistent();
        this.mMinTlsVersionPref.setEntries(charSequenceArr2);
        DropDownPreference dropDownPreference = this.mMinTlsVersionPref;
        dropDownPreference.mEntryValues = charSequenceArr;
        if (dropDownPreference.isVisible()) {
            DropDownPreference dropDownPreference2 = this.mMinTlsVersionPref;
            WifiConfiguration wifiConfiguration = this.mWifiConfig;
            updateSummary$4(
                    dropDownPreference2,
                    wifiConfiguration != null
                            ? wifiConfiguration.enterpriseConfig.getMinimumTlsVersion()
                            : 0);
        }
    }

    public final void setupEapOcspItems() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.mContext.getString(R.string.wifi_do_not_validate_eap_server));
        arrayList.add(this.mContext.getString(R.string.wifi_eap_ocsp_request_cert_status));
        arrayList.add(this.mContext.getString(R.string.wifi_eap_ocsp_require_cert_status));
        arrayList.add(this.mContext.getString(R.string.wifi_eap_ocsp_require_all));
        CharSequence[] charSequenceArr = new CharSequence[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            charSequenceArr[i] = String.valueOf(i);
        }
        this.mEapOcspPref.mEntryValues = charSequenceArr;
        this.mEapOcspPref.setEntries(
                (CharSequence[]) arrayList.toArray(new String[arrayList.size()]));
    }

    public abstract void setupPhase2Method(int i);

    public final void showErrorTextUnderEditText(
            TextView textView, EditText editText, TextView textView2, int i) {
        if (textView == null) {
            return;
        }
        textView.setTextColor(
                this.mContext.getColor(R.color.sec_wifi_preference_wrong_password_color));
        textView2.setText(i);
        textView2.setVisibility(0);
        editText.setBackgroundTintList(
                this.mContext
                        .getResources()
                        .getColorStateList(R.color.sec_wifi_preference_wrong_password_color));
        editText.setSelection(editText.getText().length());
    }

    public final void showSecurityFields(int i) {
        int eapMethod;
        if (i == 0 || i == 4) {
            this.mPasswordPref.setVisible(false);
            this.mEapMethodPref.setVisible(false);
            this.mEapOcspPref.setVisible(false);
            refreshEapItems(-1);
        } else {
            WifiEntry wifiEntry = this.mWifiEntry;
            int i2 = 1;
            if (i == 1 || i == 2 || i == 5) {
                this.mEapMethodPref.setVisible(false);
                this.mEapOcspPref.setVisible(false);
                refreshEapItems(-1);
                this.mPasswordPref.setVisible(true);
                if (wifiEntry != null && wifiEntry.isSaved()) {
                    this.mPasswordView.setHint(R.string.wifi_unchanged);
                }
                fillPreviousPasswordIfNeeded();
            } else if (i == 3 || i == 7 || i == 6) {
                this.mEapMethodPref.setVisible(true);
                if (this.mWifiEntrySecurity == 6) {
                    this.mEapMethodPref.setEnabled(false);
                } else {
                    this.mEapMethodPref.setEnabled(this.mCanChangeEapMethod);
                    if (wifiEntry != null) {
                        WifiConfiguration wifiConfiguration = this.mWifiConfig;
                        if (wifiConfiguration != null) {
                            eapMethod = wifiConfiguration.enterpriseConfig.getEapMethod();
                            Log.d(
                                    this.TAG,
                                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                            eapMethod, "getEapMethodOverride return "));
                        } else {
                            eapMethod = 0;
                        }
                    } else {
                        WifiConfiguration wifiConfiguration2 = this.mWifiConfig;
                        eapMethod =
                                wifiConfiguration2 != null
                                        ? wifiConfiguration2.enterpriseConfig.getEapMethod()
                                        : -1;
                    }
                    i2 = eapMethod != -1 ? eapMethod : 0;
                }
                refreshEapItems(i2);
                updateSummary$4(this.mEapMethodPref, i2);
                setupPhase2Method(i2);
            }
        }
        afterShowSecurityFields();
    }

    public final void updateEapSimPref() {
        int indexOfKey;
        List<SubscriptionInfo> activeSubscriptionInfoList =
                ((SubscriptionManager) this.mContext.getSystemService(SubscriptionManager.class))
                        .getActiveSubscriptionInfoList();
        if (activeSubscriptionInfoList == null) {
            activeSubscriptionInfoList = Collections.EMPTY_LIST;
        }
        this.mActiveSubscriptionInfos.clear();
        if (activeSubscriptionInfoList.isEmpty()) {
            this.mEapSimPref.setEntries(
                    new String[] {this.mContext.getString(R.string.wifi_no_sim_card)});
            DropDownPreference dropDownPreference = this.mEapSimPref;
            dropDownPreference.mEntryValues = new CharSequence[] {DATA.DM_FIELD_INDEX.PCSCF_DOMAIN};
            dropDownPreference.setValueIndex(0);
            updateSummary$4(this.mEapSimPref, 0);
            this.mEapSimPref.setEnabled(false);
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
        CharSequence[] charSequenceArr = new CharSequence[this.mActiveSubscriptionInfos.size()];
        CharSequence[] charSequenceArr2 = new CharSequence[this.mActiveSubscriptionInfos.size()];
        for (int i = 0; i < this.mActiveSubscriptionInfos.size(); i++) {
            charSequenceArr[i] = (CharSequence) arrayMap.get(arrayMap.keyAt(i));
            charSequenceArr2[i] = String.valueOf(i);
        }
        this.mEapSimPref.setEntries(charSequenceArr);
        this.mEapSimPref.mEntryValues = charSequenceArr2;
        WifiConfiguration wifiConfiguration = this.mWifiConfig;
        if (wifiConfiguration != null
                && (indexOfKey =
                                this.mActiveSubscriptionInfos.indexOfKey(
                                        Integer.valueOf(wifiConfiguration.carrierId)))
                        > -1) {
            updateSummary$4(this.mEapSimPref, indexOfKey);
            this.mEapSimPref.setEnabled(true);
        }
        if (arrayMap.size() == 1) {
            this.mEapSimPref.setEnabled(false);
        }
    }

    public final void updateSecurity() {
        WifiConfiguration wifiConfiguration = this.mWifiConfig;
        if (wifiConfiguration == null) {
            return;
        }
        wifiConfiguration.allowedKeyManagement.clear();
        switch (this.mWifiEntrySecurity) {
            case 0:
                this.mWifiConfig.setSecurityParams(0);
                break;
            case 1:
                this.mWifiConfig.setSecurityParams(1);
                break;
            case 2:
                this.mWifiConfig.setSecurityParams(2);
                break;
            case 3:
                this.mWifiConfig.setSecurityParams(3);
                break;
            case 4:
                this.mWifiConfig.setSecurityParams(6);
                break;
            case 5:
                this.mWifiConfig.setSecurityParams(4);
                break;
            case 6:
                this.mWifiConfig.setSecurityParams(5);
                break;
            case 7:
                this.mWifiConfig.setSecurityParams(9);
                break;
        }
        passwordCheck(this.mPasswordView.getText().toString());
    }

    public final void updateSummary$4(DropDownPreference dropDownPreference, int i) {
        Fragment fragment = this.mFragment;
        ColorStateList colorStateList =
                fragment.getActivity()
                        .getResources()
                        .getColorStateList(
                                R.color.sec_preference_summary_primary_color,
                                fragment.getActivity().getTheme());
        CharSequence[] charSequenceArr = dropDownPreference.mEntries;
        if (charSequenceArr == null || i < 0 || i >= charSequenceArr.length) {
            Log.e(
                    this.TAG,
                    "updateSummary: "
                            + dropDownPreference.getKey()
                            + " entries null or length error");
            return;
        }
        dropDownPreference.setValueIndex(i);
        String charSequence = dropDownPreference.mEntries[i].toString();
        if (charSequence.contains("%")) {
            charSequence = charSequence.replace("%", "%%");
        }
        dropDownPreference.setSummary(charSequence);
        dropDownPreference.seslSetSummaryColor(colorStateList);
    }

    public final void updateSummaryWithWarning(DropDownPreference dropDownPreference) {
        Fragment fragment = this.mFragment;
        ColorStateList colorStateList =
                fragment.getActivity()
                        .getResources()
                        .getColorStateList(
                                R.color.sec_wifi_dialog_error_color,
                                fragment.getActivity().getTheme());
        dropDownPreference.setValueIndex(0);
        String charSequence = dropDownPreference.mEntries[0].toString();
        if (charSequence.contains("%")) {
            charSequence = charSequence.replace("%", "%%");
        }
        dropDownPreference.setSummary(charSequence);
        dropDownPreference.seslSetSummaryColor(colorStateList);
    }

    public void fillPreviousPasswordIfNeeded() {}
}

package com.android.settings.vpn2;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ProxyInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemProperties;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.FragmentActivity;

import com.android.internal.net.VpnProfile;
import com.android.net.module.util.ProxyUtils;
import com.android.settings.R;
import com.android.settings.Utils;

import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.textfield.EndCompoundLayout;
import com.google.android.material.textfield.IconHelper;
import com.google.android.material.textfield.TextInputLayout;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.security.mdf.MdfUtils;
import com.samsung.android.settings.Rune;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ConfigDialog extends AlertDialog
        implements TextWatcher,
                View.OnClickListener,
                AdapterView.OnItemSelectedListener,
                CompoundButton.OnCheckedChangeListener {
    public static final List VPN_TYPES = List.of(6, 7, 8, 9);
    public final String[] caCertificate;
    public final HashMap hmMaxLenId;
    public TextView mAlwaysOnInvalidReason;
    public CheckBox mAlwaysOnVpn;
    public TextView mDnsServers;
    public boolean mEditing;
    public CheckBox mEnablePFS;
    public final boolean mExists;
    public Spinner mIpsecCaCert;
    public TextView mIpsecIdentifier;
    public TextView mIpsecRemoteIdentifier;
    public TextView mIpsecSecret;
    public TextInputLayout mIpsecSecretLayout;
    public Spinner mIpsecServerCert;
    public Spinner mIpsecUserCert;
    public boolean mIsUserSetAlwaysOnAllowed;
    public final DialogInterface.OnClickListener mListener;
    public TextView mName;
    public TextView mOcspServerUrl;
    public TextView mPassword;
    public TextInputLayout mPasswordLayout;
    public final VpnProfile mProfile;
    public TextView mProxyHost;
    public TextView mProxyPort;
    public Spinner mProxySettings;
    public TextView mRoutes;
    public CheckBox mSaveLogin;
    public TextView mSearchDomains;
    public TextView mServer;
    public CheckBox mShowOptions;
    public Spinner mType;
    public TextView mUsername;
    public View mView;
    public final String[] userCertificate;

    public ConfigDialog(
            FragmentActivity fragmentActivity,
            DialogInterface.OnClickListener onClickListener,
            VpnProfile vpnProfile,
            boolean z,
            boolean z2,
            String[] strArr,
            String[] strArr2) {
        super(fragmentActivity, 0);
        this.hmMaxLenId = new HashMap();
        this.mListener = onClickListener;
        this.mProfile = vpnProfile;
        this.mEditing = z;
        this.mExists = z2;
        this.userCertificate = strArr;
        this.caCertificate = strArr2;
    }

    public static String getFriendlyNameForPlugin(String str) {
        UniversalCredentialUtil universalCredentialUtil;
        if (str == null
                || (universalCredentialUtil = UniversalCredentialUtil.getInstance()) == null) {
            return " [Knox]";
        }
        return " ["
                + universalCredentialUtil.getFriendlyName(UniversalCredentialUtil.getSource(str))
                + "]";
    }

    public static TextInputLayout getTextInputParent(View view) {
        Object parent = view.getParent();
        if (parent instanceof TextInputLayout) {
            return (TextInputLayout) parent;
        }
        if (parent instanceof View) {
            return getTextInputParent((View) parent);
        }
        return null;
    }

    public static boolean isUcmEnabled$1() {
        return SystemProperties.getBoolean("security.ucmcrypto", false)
                || SystemProperties.getBoolean("persist.security.ucmcrypto", false);
    }

    public static boolean validateAddresses(String str, boolean z) {
        int i;
        try {
            for (String str2 : str.split(" ")) {
                if (!str2.isEmpty()) {
                    if (z) {
                        String[] split = str2.split("/", 2);
                        String str3 = split[0];
                        i = Integer.parseInt(split[1]);
                        str2 = str3;
                    } else {
                        i = 32;
                    }
                    byte[] address = InetAddress.parseNumericAddress(str2).getAddress();
                    int i2 =
                            ((address[1] & 255) << 16)
                                    | ((address[2] & 255) << 8)
                                    | (address[3] & 255)
                                    | ((address[0] & 255) << 24);
                    if (address.length != 4 || i < 0 || i > 32 || (i < 32 && (i2 << i) != 0)) {
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    @Override // android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
        updateUiControls();
        showPasswordToggleIcon();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final void changeType(int i) {
        int i2 = 8;
        this.mView.findViewById(R.id.ipsec_psk).setVisibility(8);
        this.mView.findViewById(R.id.ipsec_user).setVisibility(8);
        this.mView.findViewById(R.id.ipsec_peer).setVisibility(8);
        this.mView.findViewById(R.id.options_ipsec_identity).setVisibility(8);
        this.mView.findViewById(R.id.options_ipsec_remote_identity).setVisibility(8);
        this.mView.findViewById(R.id.enable_pfs_m).setVisibility(8);
        View findViewById = this.mView.findViewById(R.id.userpass);
        if (i != 7 && i != 8 && i != 9) {
            i2 = 0;
        }
        findViewById.setVisibility(i2);
        this.mView.findViewById(R.id.options_ipsec_identity).setVisibility(0);
        if (MdfUtils.isMdfEnforced()) {
            this.mView.findViewById(R.id.network_options).setVisibility(0);
        }
        switch (i) {
            case 6:
                this.mView.findViewById(R.id.ipsec_peer).setVisibility(0);
                break;
            case 7:
                if (this.mShowOptions.isChecked()) {
                    String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                    if ("ATT".equals(Utils.getSalesCode()) && !MdfUtils.isMdfEnforced()) {
                        this.mView.findViewById(R.id.enable_pfs_m).setVisibility(0);
                    }
                }
                this.mView.findViewById(R.id.ipsec_psk).setVisibility(0);
                this.mView.findViewById(R.id.options_ipsec_identity).setVisibility(0);
                break;
            case 8:
            case 9:
                if (this.mShowOptions.isChecked()) {
                    String str2 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                    if ("ATT".equals(Utils.getSalesCode()) && !MdfUtils.isMdfEnforced()) {
                        this.mView.findViewById(R.id.enable_pfs_m).setVisibility(0);
                    }
                }
                if (MdfUtils.isMdfEnforced()) {
                    this.mView.findViewById(R.id.ocsp_server).setVisibility(0);
                }
                this.mView.findViewById(R.id.ipsec_user).setVisibility(0);
                this.mView.findViewById(R.id.options_ipsec_remote_identity).setVisibility(0);
                this.mView.findViewById(R.id.ipsec_peer).setVisibility(0);
                break;
        }
        configureAdvancedOptionsVisibility();
    }

    public final void configureAdvancedOptionsVisibility() {
        if (!this.mShowOptions.isChecked()
                && this.mProxyHost.getText().length() <= 0
                && this.mProxyPort.getText().length() <= 0) {
            this.mView.findViewById(R.id.options).setVisibility(8);
            this.mShowOptions.setVisibility(0);
            this.mView.findViewById(R.id.enable_pfs_m).setVisibility(8);
            return;
        }
        this.mView.findViewById(R.id.options).setVisibility(0);
        this.mShowOptions.setVisibility(8);
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        if (!"ATT".equals(Utils.getSalesCode()) || MdfUtils.isMdfEnforced()) {
            this.mView.findViewById(R.id.enable_pfs_m).setVisibility(8);
        } else {
            this.mView.findViewById(R.id.enable_pfs_m).setVisibility(0);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:21:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0171  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.internal.net.VpnProfile getProfile() {
        /*
            Method dump skipped, instructions count: 450
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.vpn2.ConfigDialog.getProfile():com.android.internal.net.VpnProfile");
    }

    public final void loadCertificates(Spinner spinner, Collection collection, int i, String str) {
        String[] strArr;
        int i2;
        Context context = getContext();
        String string = context.getString(R.string.wifi_unspecified);
        if (i != 0) {
            string = context.getString(i);
        }
        if (collection == null || collection.size() == 0) {
            strArr = new String[] {string};
        } else {
            strArr = new String[collection.size() + 1];
            strArr[0] = string;
            Iterator it = collection.iterator();
            int i3 = 1;
            while (it.hasNext()) {
                String str2 = (String) it.next();
                if (isUcmEnabled$1() && str2.startsWith("ucmkeychain://")) {
                    i2 = i3 + 1;
                    strArr[i3] =
                            Uri.parse(str2).getLastPathSegment() + getFriendlyNameForPlugin(str2);
                } else {
                    i2 = i3 + 1;
                    strArr[i3] = str2;
                }
                i3 = i2;
            }
        }
        ArrayAdapter arrayAdapter =
                new ArrayAdapter(context, android.R.layout.simple_spinner_item, strArr);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) arrayAdapter);
        if (isUcmEnabled$1() && str.startsWith("ucmkeychain://")) {
            str = Uri.parse(str).getLastPathSegment() + getFriendlyNameForPlugin(str);
        }
        for (int i4 = 1; i4 < strArr.length; i4++) {
            if (strArr[i4].equals(str)) {
                spinner.setSelection(i4);
                return;
            }
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (compoundButton == this.mAlwaysOnVpn) {
            updateUiControls();
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (view == this.mShowOptions) {
            configureAdvancedOptionsVisibility();
        }
        if (view instanceof CheckableImageButton) {
            TextInputLayout textInputParent = getTextInputParent(view);
            EditText editText =
                    textInputParent != null ? (EditText) textInputParent.getTag() : null;
            if (editText != null) {
                int selectionEnd = editText.getSelectionEnd();
                CheckableImageButton checkableImageButton = (CheckableImageButton) view;
                if (checkableImageButton.checked) {
                    checkableImageButton.setContentDescription(
                            getContext().getString(R.string.wifi_show_password_contentdescription));
                    editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    checkableImageButton.setChecked(false);
                } else {
                    checkableImageButton.setContentDescription(
                            getContext().getString(R.string.wifi_hide_password_contentdescription));
                    editText.setTransformationMethod(null);
                    checkableImageButton.setChecked(true);
                }
                editText.setSelection(selectionEnd);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x04b6  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x04f5  */
    @Override // androidx.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog,
              // androidx.activity.ComponentDialog, android.app.Dialog
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreate(android.os.Bundle r17) {
        /*
            Method dump skipped, instructions count: 1358
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.vpn2.ConfigDialog.onCreate(android.os.Bundle):void");
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onItemSelected(AdapterView adapterView, View view, int i, long j) {
        if (adapterView == this.mType) {
            changeType(((Integer) VPN_TYPES.get(i)).intValue());
        } else if (adapterView == this.mProxySettings) {
            this.mView.findViewById(R.id.vpn_proxy_fields).setVisibility(i == 1 ? 0 : 8);
        }
        updateUiControls();
    }

    @Override // android.app.Dialog
    public final void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        configureAdvancedOptionsVisibility();
    }

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        String charSequence2 = charSequence.toString();
        int length = charSequence2.length();
        if (getCurrentFocus() != null) {
            int id = getCurrentFocus().getId();
            if (this.hmMaxLenId.get(Integer.valueOf(id)) == null) {
                return;
            }
            int i4 = ((int[]) this.hmMaxLenId.get(Integer.valueOf(id)))[0];
            int i5 = ((int[]) this.hmMaxLenId.get(Integer.valueOf(id)))[1];
            EditText editText = (EditText) this.mView.findViewById(id);
            TextView textView = (TextView) this.mView.findViewById(i5);
            if (length > i4) {
                textView.setVisibility(0);
                editText.setText(charSequence2.substring(0, i4));
                editText.setBackgroundTintList(
                        getContext()
                                .getResources()
                                .getColorStateList(R.color.sec_wifi_dialog_error_color));
                editText.setSelection(editText.getText().length());
                return;
            }
            if (length == i4) {
                return;
            }
            textView.setVisibility(8);
            editText.setBackgroundTintList(
                    getContext()
                            .getResources()
                            .getColorStateList(R.color.dashboard_tab_selected_color));
        }
    }

    public final void showPasswordToggleIcon() {
        if (TextUtils.isEmpty(this.mPassword.getText())
                && !this.mPasswordLayout.isPasswordVisibilityToggleEnabled()) {
            this.mPasswordLayout.setPasswordVisibilityToggleEnabled(true);
            EndCompoundLayout endCompoundLayout = this.mPasswordLayout.endLayout;
            endCompoundLayout.endIconView.setImageDrawable(
                    AppCompatResources.getDrawable(
                            endCompoundLayout.getContext(), R.drawable.vpn_show_password_selector));
            EndCompoundLayout endCompoundLayout2 = this.mPasswordLayout.endLayout;
            CheckableImageButton checkableImageButton = endCompoundLayout2.endIconView;
            View.OnLongClickListener onLongClickListener =
                    endCompoundLayout2.endIconOnLongClickListener;
            checkableImageButton.setOnClickListener(this);
            IconHelper.setIconClickable(checkableImageButton, onLongClickListener);
            EndCompoundLayout endCompoundLayout3 = this.mPasswordLayout.endLayout;
            endCompoundLayout3.endIconView.setContentDescription(
                    endCompoundLayout3
                            .getResources()
                            .getText(R.string.wifi_show_password_contentdescription));
        }
        if (!TextUtils.isEmpty(this.mIpsecSecret.getText())
                || this.mIpsecSecretLayout.isPasswordVisibilityToggleEnabled()) {
            return;
        }
        this.mIpsecSecretLayout.setPasswordVisibilityToggleEnabled(true);
        EndCompoundLayout endCompoundLayout4 = this.mIpsecSecretLayout.endLayout;
        endCompoundLayout4.endIconView.setImageDrawable(
                AppCompatResources.getDrawable(
                        endCompoundLayout4.getContext(), R.drawable.vpn_show_password_selector));
        EndCompoundLayout endCompoundLayout5 = this.mIpsecSecretLayout.endLayout;
        CheckableImageButton checkableImageButton2 = endCompoundLayout5.endIconView;
        View.OnLongClickListener onLongClickListener2 =
                endCompoundLayout5.endIconOnLongClickListener;
        checkableImageButton2.setOnClickListener(this);
        IconHelper.setIconClickable(checkableImageButton2, onLongClickListener2);
        EndCompoundLayout endCompoundLayout6 = this.mIpsecSecretLayout.endLayout;
        endCompoundLayout6.endIconView.setContentDescription(
                endCompoundLayout6
                        .getResources()
                        .getText(R.string.wifi_show_password_contentdescription));
    }

    public final void updateUiControls() {
        VpnProfile profile = getProfile();
        if (profile.isValidLockdownProfile()) {
            this.mAlwaysOnVpn.setEnabled(true);
            this.mAlwaysOnInvalidReason.setVisibility(8);
        } else {
            this.mAlwaysOnVpn.setChecked(false);
            this.mAlwaysOnVpn.setEnabled(false);
            this.mAlwaysOnInvalidReason.setText(R.string.vpn_always_on_invalid_reason_other);
            this.mAlwaysOnInvalidReason.setVisibility(0);
        }
        ProxyInfo proxyInfo = profile.proxy;
        if (proxyInfo != null && (!proxyInfo.getHost().isEmpty() || profile.proxy.getPort() != 0)) {
            this.mProxySettings.setSelection(1);
            this.mView.findViewById(R.id.vpn_proxy_fields).setVisibility(0);
        }
        if (this.mAlwaysOnVpn.isChecked()) {
            this.mSaveLogin.setChecked(true);
            this.mSaveLogin.setEnabled(false);
        } else {
            this.mSaveLogin.setChecked(this.mProfile.saveLogin);
            this.mSaveLogin.setEnabled(true);
        }
        getButton(-1).setEnabled(validate(this.mEditing));
    }

    public final boolean validate(boolean z) {
        if (this.mAlwaysOnVpn.isChecked() && !getProfile().isValidLockdownProfile()) {
            return false;
        }
        int intValue = ((Integer) VPN_TYPES.get(this.mType.getSelectedItemPosition())).intValue();
        if (!z && intValue != 7 && intValue != 8 && intValue != 9) {
            return (this.mUsername.getText().length() == 0
                            || this.mPassword.getText().length() == 0)
                    ? false
                    : true;
        }
        if (this.mName.getText().length() == 0 || this.mServer.getText().length() == 0) {
            return false;
        }
        if (MdfUtils.isMdfEnforced()
                && (!validateAddresses(this.mDnsServers.getText().toString(), false)
                        || !validateAddresses(this.mRoutes.getText().toString(), true))) {
            return false;
        }
        if (this.mProxySettings.getSelectedItemPosition() == 1
                && ProxyUtils.validate(
                                this.mProxyHost.getText().toString().trim(),
                                this.mProxyPort.getText().toString().trim(),
                                ApnSettings.MVNO_NONE)
                        != 0) {
            return false;
        }
        switch (intValue) {
            case 6:
                return true;
            case 7:
                return this.mIpsecSecret.getText().length() != 0;
            case 8:
            case 9:
                if (MdfUtils.isMdfEnforced()) {
                    String charSequence = this.mOcspServerUrl.getText().toString();
                    if (charSequence.length() > 0) {
                        try {
                            new URL(charSequence);
                        } catch (MalformedURLException unused) {
                            return false;
                        }
                    }
                }
                return this.mIpsecUserCert.getSelectedItemPosition() != 0;
            default:
                return false;
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onNothingSelected(AdapterView adapterView) {}

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
}

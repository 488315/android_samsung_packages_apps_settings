package com.samsung.android.settings.wifi;

import android.net.wifi.SoftApConfiguration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.wifi.WifiPolicy;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApQrCodeFragment extends QrCodeFragment {
    public SoftApConfiguration mSoftApConfig;

    @Override // com.samsung.android.settings.wifi.QrCodeFragment
    public final String getLogTag() {
        return "WifiApQrCodeFragment";
    }

    @Override // com.samsung.android.settings.wifi.QrCodeFragment
    public final String getQrCodeString() {
        SoftApConfiguration softApConfiguration = this.mSoftApConfig;
        String str = ApnSettings.MVNO_NONE;
        if (softApConfiguration == null) {
            return ApnSettings.MVNO_NONE;
        }
        this.mSsid = softApConfiguration.getSsid();
        WifiQrCodeGenerator wifiQrCodeGenerator = this.mQrCodeGenerator;
        SoftApConfiguration softApConfiguration2 = this.mSoftApConfig;
        wifiQrCodeGenerator.getClass();
        if (softApConfiguration2 == null) {
            return null;
        }
        int securityType = softApConfiguration2.getSecurityType();
        String str2 = "WPA";
        if (securityType != 1 && securityType != 2) {
            str2 = securityType != 3 ? "nopass" : WifiPolicy.SECURITY_TYPE_SAE;
        }
        String passphrase = softApConfiguration2.getPassphrase();
        String ssid = softApConfiguration2.getSsid();
        StringBuilder sb = new StringBuilder("WIFI:S:");
        sb.append(WifiQrCodeGenerator.escapeSpecialCharacters(ssid));
        sb.append(";T:");
        if (TextUtils.isEmpty(str2)) {
            str2 = ApnSettings.MVNO_NONE;
        }
        sb.append(str2);
        sb.append(";P:");
        if (!TextUtils.isEmpty(passphrase)) {
            str = WifiQrCodeGenerator.escapeSpecialCharacters(passphrase);
        }
        sb.append(str);
        sb.append(";H:");
        sb.append(softApConfiguration2.isHiddenSsid());
        sb.append(";;");
        return sb.toString();
    }

    @Override // com.samsung.android.settings.wifi.QrCodeFragment
    public final void initValuesFromIntent(Bundle bundle) {
        this.mSoftApConfig = (SoftApConfiguration) bundle.getParcelable("key_config");
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mActivity.setTitle(R.string.wifi_ap_qr_code_sharing_title);
        View inflate = layoutInflater.inflate(R.layout.sec_wifi_qr_code, (ViewGroup) null);
        ((QrCodeFragment) this).mView = inflate;
        ((TextView) inflate.findViewById(R.id.ap_ssid)).setSingleLine(false);
        return ((QrCodeFragment) this).mView;
    }

    @Override // com.samsung.android.settings.wifi.QrCodeFragment
    public final void updateView() {
        TextView textView;
        setupQrImageView();
        String str = this.mSsid;
        if (str == null || (textView = this.mSsidView) == null) {
            return;
        }
        textView.setText(str);
    }
}

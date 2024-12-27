package com.samsung.android.settings.wifi;

import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.wifitrackerlib.SemWifiUtils;

import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiQrCodeFragment extends QrCodeFragment {
    public static final /* synthetic */ int $r8$clinit = 0;
    public WifiConfiguration mWifiConfig;

    @Override // com.samsung.android.settings.wifi.QrCodeFragment
    public final String getLogTag() {
        return "WifiQrCodeFragment";
    }

    @Override // com.samsung.android.settings.wifi.QrCodeFragment
    public final String getQrCodeString() {
        WifiConfiguration wifiConfiguration;
        WifiConfiguration wifiConfiguration2 = this.mWifiConfig;
        if (wifiConfiguration2 == null) {
            return ApnSettings.MVNO_NONE;
        }
        int i = wifiConfiguration2.networkId;
        Iterator it = this.mWifiManager.getPrivilegedConfiguredNetworks().iterator();
        while (true) {
            if (!it.hasNext()) {
                Log.e("WifiQrCodeFragment", "PrivilegedConfiguredNetwork is not exist ");
                wifiConfiguration = null;
                break;
            }
            wifiConfiguration = (WifiConfiguration) it.next();
            if (wifiConfiguration.networkId == i) {
                break;
            }
        }
        if (wifiConfiguration == null) {
            return ApnSettings.MVNO_NONE;
        }
        this.mSsid = SemWifiUtils.removeDoubleQuotes(wifiConfiguration.SSID);
        this.mQrCodeGenerator.getClass();
        return WifiQrCodeGenerator.getQrCodeString(wifiConfiguration);
    }

    @Override // com.samsung.android.settings.wifi.QrCodeFragment
    public final void initValuesFromIntent(Bundle bundle) {
        this.mWifiConfig = (WifiConfiguration) bundle.getParcelable("key_config");
        this.mIsSetupWizard = bundle.getBoolean("key_setup_wizard");
        Rune.isChinaModel();
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mActivity.setTitle(R.string.wifi_qr_code_sharing_title);
        if (this.mIsSetupWizard) {
            ((QrCodeFragment) this).mView =
                    layoutInflater.inflate(R.layout.sec_wifi_qr_code_setupwizard, (ViewGroup) null);
        } else {
            ((QrCodeFragment) this).mView =
                    layoutInflater.inflate(R.layout.sec_wifi_qr_code, (ViewGroup) null);
        }
        WindowManager.LayoutParams attributes = getActivity().getWindow().getAttributes();
        attributes.screenBrightness = 1.0f;
        getActivity().getWindow().setAttributes(attributes);
        return ((QrCodeFragment) this).mView;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(13:13|14|15|(7:17|18|19|20|(1:22)|23|(2:40|41)(7:26|27|(4:31|(1:33)|34|35)|38|(0)|34|35))|47|18|19|20|(0)|23|(0)|40|41) */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0096, code lost:

       r5 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x009d, code lost:

       r5.printStackTrace();
       r5 = false;
    */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0082 A[Catch: NameNotFoundException -> 0x0096, TryCatch #1 {NameNotFoundException -> 0x0096, blocks: (B:20:0x0073, B:22:0x0082, B:23:0x0098), top: B:19:0x0073 }] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00c4  */
    @Override // com.samsung.android.settings.wifi.QrCodeFragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateView() {
        /*
            r8 = this;
            r8.setupQrImageView()
            java.lang.String r0 = r8.mSsid
            if (r0 == 0) goto Le
            android.widget.TextView r1 = r8.mSsidView
            if (r1 == 0) goto Le
            r1.setText(r0)
        Le:
            java.lang.String r0 = r8.mSsid
            if (r0 == 0) goto L24
            androidx.fragment.app.FragmentActivity r1 = r8.mActivity
            r2 = 2132030957(0x7f1435ed, float:1.9700574E38)
            java.lang.Object[] r0 = new java.lang.Object[]{r0}
            java.lang.String r0 = r1.getString(r2, r0)
            android.widget.TextView r1 = r8.mSummaryView
            r1.setText(r0)
        L24:
            java.lang.String r0 = "com.samsung.android.app.sharelive"
            java.lang.String r1 = "WifiQrCodeFragment"
            boolean r2 = r8.mIsSetupWizard
            if (r2 != 0) goto Le3
            android.widget.Button r2 = r8.mQuickShareButton
            if (r2 == 0) goto Le3
            java.lang.String r2 = "wifiQR"
            r3 = 128(0x80, float:1.794E-43)
            r4 = 0
            android.content.Context r5 = r8.mContext     // Catch: org.json.JSONException -> L58 android.content.pm.PackageManager.NameNotFoundException -> L5a
            android.content.pm.PackageManager r5 = r5.getPackageManager()     // Catch: org.json.JSONException -> L58 android.content.pm.PackageManager.NameNotFoundException -> L5a
            android.content.pm.ApplicationInfo r5 = r5.getApplicationInfo(r0, r3)     // Catch: org.json.JSONException -> L58 android.content.pm.PackageManager.NameNotFoundException -> L5a
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch: org.json.JSONException -> L58 android.content.pm.PackageManager.NameNotFoundException -> L5a
            android.os.Bundle r5 = r5.metaData     // Catch: org.json.JSONException -> L58 android.content.pm.PackageManager.NameNotFoundException -> L5a
            java.lang.String r7 = "com.samsung.android.app.sharelive.extend"
            java.lang.String r5 = r5.getString(r7)     // Catch: org.json.JSONException -> L58 android.content.pm.PackageManager.NameNotFoundException -> L5a
            r6.<init>(r5)     // Catch: org.json.JSONException -> L58 android.content.pm.PackageManager.NameNotFoundException -> L5a
            boolean r5 = r6.has(r2)     // Catch: org.json.JSONException -> L58 android.content.pm.PackageManager.NameNotFoundException -> L5a
            if (r5 == 0) goto L6b
            boolean r2 = r6.getBoolean(r2)     // Catch: org.json.JSONException -> L58 android.content.pm.PackageManager.NameNotFoundException -> L5a
            goto L71
        L58:
            r2 = move-exception
            goto L5c
        L5a:
            r2 = move-exception
            goto L64
        L5c:
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r1, r2)
            goto L6b
        L64:
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r1, r2)
        L6b:
            java.lang.String r2 = "Not support QUICK_SHARE_WIFI_QR"
            android.util.Log.d(r1, r2)
            r2 = r4
        L71:
            java.lang.String r5 = "ShareLive enabled? "
            android.content.Context r6 = r8.mContext     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L96
            android.content.pm.PackageManager r6 = r6.getPackageManager()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L96
            r7 = 1
            android.content.pm.PackageInfo r6 = r6.getPackageInfo(r0, r7)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L96
            boolean r7 = com.samsung.android.settings.wifi.QrCodeFragment.DBG     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L96
            if (r7 == 0) goto L98
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L96
            r7.<init>(r5)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L96
            android.content.pm.ApplicationInfo r5 = r6.applicationInfo     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L96
            boolean r5 = r5.enabled     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L96
            r7.append(r5)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L96
            java.lang.String r5 = r7.toString()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L96
            android.util.Log.d(r1, r5)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L96
            goto L98
        L96:
            r5 = move-exception
            goto L9d
        L98:
            android.content.pm.ApplicationInfo r5 = r6.applicationInfo     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L96
            boolean r5 = r5.enabled     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L96
            goto La1
        L9d:
            r5.printStackTrace()
            r5 = r4
        La1:
            if (r2 == 0) goto Ldc
            if (r5 == 0) goto Ldc
            android.content.Context r2 = r8.mContext     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lbc
            android.content.pm.PackageManager r2 = r2.getPackageManager()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lbc
            android.content.pm.ApplicationInfo r0 = r2.getApplicationInfo(r0, r3)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lbc
            if (r0 == 0) goto Lc1
            android.os.Bundle r0 = r0.metaData     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lbc
            if (r0 == 0) goto Lc1
            java.lang.String r2 = "com.samsung.android.app.sharelive.supportChinaP2p"
            boolean r0 = r0.getBoolean(r2, r4)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lbc
            goto Lc2
        Lbc:
            java.lang.String r0 = "PackageManager could not find QuickShare"
            android.util.Log.i(r1, r0)
        Lc1:
            r0 = r4
        Lc2:
            if (r0 == 0) goto Lcc
            android.widget.Button r0 = r8.mQuickShareButton
            r1 = 2132024768(0x7f141dc0, float:1.9688021E38)
            r0.setText(r1)
        Lcc:
            android.widget.Button r0 = r8.mQuickShareButton
            r0.setVisibility(r4)
            android.widget.Button r0 = r8.mQuickShareButton
            com.samsung.android.settings.wifi.WifiQrCodeFragment$$ExternalSyntheticLambda0 r1 = new com.samsung.android.settings.wifi.WifiQrCodeFragment$$ExternalSyntheticLambda0
            r1.<init>()
            r0.setOnClickListener(r1)
            goto Le3
        Ldc:
            java.lang.String r8 = "Not support QS - Supported/Enabled: "
            java.lang.String r0 = "/"
            com.android.settings.Utils$$ExternalSyntheticOutline0.m653m(r8, r2, r0, r5, r1)
        Le3:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.WifiQrCodeFragment.updateView():void");
    }
}

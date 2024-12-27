package com.android.settings.wifi.dpp;

import android.R;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settingslib.qrcode.QrCodeGenerator;

import com.google.zxing.WriterException;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiDppQrCodeGeneratorFragment extends WifiDppQrCodeBaseFragment {
    public String mQrCode;
    public ImageView mQrCodeView;

    /* JADX WARN: Removed duplicated region for block: B:21:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:5:0x0083 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0084  */
    @com.android.internal.annotations.VisibleForTesting
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.widget.Button createNearbyButton(
            android.content.Intent r11, android.view.View.OnClickListener r12) {
        /*
            r10 = this;
            android.content.ComponentName r0 = r10.getNearbySharingComponent()
            r1 = 0
            if (r0 != 0) goto La
        L7:
            r9 = r1
            goto L81
        La:
            android.content.Intent r7 = new android.content.Intent
            r7.<init>(r11)
            r7.setComponent(r0)
            android.content.Context r2 = r10.getContext()
            android.content.pm.PackageManager r2 = r2.getPackageManager()
            r3 = 128(0x80, float:1.794E-43)
            android.content.pm.ResolveInfo r4 = r2.resolveActivity(r7, r3)
            if (r4 == 0) goto L68
            android.content.pm.ActivityInfo r3 = r4.activityInfo
            if (r3 != 0) goto L27
            goto L68
        L27:
            android.os.Bundle r3 = r3.metaData
            if (r3 == 0) goto L47
            android.content.res.Resources r0 = r2.getResourcesForActivity(r0)     // Catch: java.lang.Throwable -> L44
            java.lang.String r5 = "android.service.chooser.chip_label"
            int r5 = r3.getInt(r5)     // Catch: java.lang.Throwable -> L44
            java.lang.String r5 = r0.getString(r5)     // Catch: java.lang.Throwable -> L44
            java.lang.String r6 = "android.service.chooser.chip_icon"
            int r3 = r3.getInt(r6)     // Catch: java.lang.Throwable -> L45
            android.graphics.drawable.Drawable r0 = r0.getDrawable(r3)     // Catch: java.lang.Throwable -> L45
            goto L49
        L44:
            r5 = r1
        L45:
            r0 = r1
            goto L49
        L47:
            r0 = r1
            r5 = r0
        L49:
            boolean r3 = android.text.TextUtils.isEmpty(r5)
            if (r3 == 0) goto L54
            java.lang.CharSequence r3 = r4.loadLabel(r2)
            r5 = r3
        L54:
            if (r0 != 0) goto L5a
            android.graphics.drawable.Drawable r0 = r4.loadIcon(r2)
        L5a:
            com.android.internal.app.chooser.DisplayResolveInfo r9 = new com.android.internal.app.chooser.DisplayResolveInfo
            java.lang.String r6 = ""
            r8 = 0
            r2 = r9
            r3 = r11
            r2.<init>(r3, r4, r5, r6, r7, r8)
            r9.setDisplayIcon(r0)
            goto L81
        L68:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r2 = "Device-specified nearby sharing component ("
            r11.<init>(r2)
            r11.append(r0)
            java.lang.String r0 = ") not available"
            r11.append(r0)
            java.lang.String r11 = r11.toString()
            java.lang.String r0 = "WifiDppQrCodeGeneratorFragment"
            android.util.Log.e(r0, r11)
            goto L7
        L81:
            if (r9 != 0) goto L84
            return r1
        L84:
            android.content.Context r11 = r10.getContext()
            android.graphics.drawable.Drawable r11 = r9.getDisplayIcon(r11)
            java.lang.CharSequence r0 = r9.getDisplayLabel()
            android.content.Context r2 = r10.getContext()
            android.view.LayoutInflater r2 = android.view.LayoutInflater.from(r2)
            r3 = 2131558455(0x7f0d0037, float:1.8742226E38)
            android.view.View r2 = r2.inflate(r3, r1)
            android.widget.Button r2 = (android.widget.Button) r2
            r3 = 0
            if (r11 == 0) goto Lb5
            android.content.res.Resources r10 = r10.getResources()
            r4 = 2131165247(0x7f07003f, float:1.7944706E38)
            int r10 = r10.getDimensionPixelSize(r4)
            r11.setBounds(r3, r3, r10, r10)
            r2.setCompoundDrawablesRelative(r11, r1, r1, r1)
        Lb5:
            r2.setText(r0)
            r2.setOnClickListener(r12)
            r2.setAllCaps(r3)
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.wifi.dpp.WifiDppQrCodeGeneratorFragment.createNearbyButton(android.content.Intent,"
                    + " android.view.View$OnClickListener):android.widget.Button");
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1595;
    }

    @VisibleForTesting
    public ComponentName getNearbySharingComponent() {
        String string =
                Settings.Secure.getString(
                        getContext().getContentResolver(), "nearby_sharing_component");
        if (TextUtils.isEmpty(string)) {
            string = getString(R.string.default_audio_route_name_dock_speakers);
        }
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return ComponentName.unflattenFromString(string);
    }

    @Override // com.android.settings.wifi.dpp.WifiDppQrCodeBaseFragment
    public final boolean isFooterAvailable() {
        return false;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        WifiNetworkConfig wifiNetworkConfig =
                ((WifiDppConfiguratorActivity) ((WifiNetworkConfig.Retriever) getActivity()))
                        .mWifiNetworkConfig;
        if (!WifiNetworkConfig.isValidConfig(wifiNetworkConfig)) {
            throw new IllegalStateException("Invalid Wi-Fi network for configuring");
        }
        if (getActivity() != null) {
            if (wifiNetworkConfig.mIsHotspot) {
                getActivity().setTitle(com.android.settings.R.string.wifi_dpp_share_hotspot);
            } else {
                getActivity().setTitle(com.android.settings.R.string.wifi_dpp_share_wifi);
            }
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        MenuItem findItem = menu.findItem(1);
        if (findItem != null) {
            findItem.setShowAsAction(0);
        }
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(
                com.android.settings.R.layout.wifi_dpp_qrcode_generator_fragment, viewGroup, false);
    }

    @Override // com.android.settings.wifi.dpp.WifiDppQrCodeBaseFragment,
              // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mQrCodeView = (ImageView) view.findViewById(com.android.settings.R.id.qrcode_view);
        final WifiNetworkConfig wifiNetworkConfig =
                ((WifiDppConfiguratorActivity) ((WifiNetworkConfig.Retriever) getActivity()))
                        .mWifiNetworkConfig;
        if (!WifiNetworkConfig.isValidConfig(wifiNetworkConfig)) {
            throw new IllegalStateException("Invalid Wi-Fi network for configuring");
        }
        if (wifiNetworkConfig.mIsHotspot) {
            setHeaderTitle(com.android.settings.R.string.wifi_dpp_share_hotspot, new Object[0]);
        } else {
            setHeaderTitle(com.android.settings.R.string.wifi_dpp_share_wifi, new Object[0]);
        }
        TextView textView = (TextView) view.findViewById(com.android.settings.R.id.password);
        String str = wifiNetworkConfig.mPreSharedKey;
        boolean isEmpty = TextUtils.isEmpty(str);
        String str2 = wifiNetworkConfig.mSsid;
        if (isEmpty) {
            this.mSummary.setText(
                    getString(
                            com.android.settings.R.string
                                    .wifi_dpp_scan_open_network_qr_code_with_another_device,
                            str2));
            textView.setVisibility(8);
        } else {
            this.mSummary.setText(
                    getString(
                            com.android.settings.R.string.wifi_dpp_scan_qr_code_with_another_device,
                            str2));
            if (wifiNetworkConfig.mIsHotspot) {
                textView.setText(
                        getString(com.android.settings.R.string.wifi_dpp_hotspot_password, str));
            } else {
                textView.setText(
                        getString(com.android.settings.R.string.wifi_dpp_wifi_password, str));
            }
        }
        final Intent component = new Intent().setComponent(getNearbySharingComponent());
        ViewGroup viewGroup =
                (ViewGroup) view.findViewById(com.android.settings.R.id.wifi_dpp_layout);
        Button createNearbyButton =
                createNearbyButton(
                        component,
                        new View
                                .OnClickListener() { // from class:
                                                     // com.android.settings.wifi.dpp.WifiDppQrCodeGeneratorFragment$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view2) {
                                WifiDppQrCodeGeneratorFragment wifiDppQrCodeGeneratorFragment =
                                        WifiDppQrCodeGeneratorFragment.this;
                                Intent intent = component;
                                WifiNetworkConfig wifiNetworkConfig2 = wifiNetworkConfig;
                                wifiDppQrCodeGeneratorFragment.getClass();
                                intent.setAction("android.intent.action.SEND");
                                intent.addFlags(268435456);
                                intent.addFlags(NetworkAnalyticsConstants.DataPoints.FLAG_UID);
                                Bundle bundle2 = new Bundle();
                                bundle2.putString(
                                        "android.intent.extra.SSID",
                                        WifiDppUtils.removeFirstAndLastDoubleQuotes(
                                                wifiNetworkConfig2.mSsid));
                                bundle2.putString(
                                        "android.intent.extra.PASSWORD",
                                        wifiNetworkConfig2.mPreSharedKey);
                                bundle2.putString(
                                        "android.intent.extra.SECURITY_TYPE",
                                        wifiNetworkConfig2.mSecurity);
                                bundle2.putBoolean(
                                        "android.intent.extra.HIDDEN_SSID",
                                        wifiNetworkConfig2.mHiddenSsid);
                                intent.putExtra(
                                        "android.intent.extra.WIFI_CREDENTIALS_BUNDLE", bundle2);
                                wifiDppQrCodeGeneratorFragment.startActivity(intent);
                            }
                        });
        if (createNearbyButton != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams =
                    new ViewGroup.MarginLayoutParams(-2, -2);
            int dimensionPixelSize = getResources().getDimensionPixelSize(17105803) / 2;
            marginLayoutParams.setMarginsRelative(dimensionPixelSize, 0, dimensionPixelSize, 0);
            viewGroup.addView(createNearbyButton, marginLayoutParams);
        }
        this.mQrCode = wifiNetworkConfig.getQrCode();
        try {
            int dimensionPixelSize2 =
                    getContext()
                            .getResources()
                            .getDimensionPixelSize(com.android.settings.R.dimen.qrcode_size);
            String contents = this.mQrCode;
            Intrinsics.checkNotNullParameter(contents, "contents");
            this.mQrCodeView.setImageBitmap(
                    QrCodeGenerator.encodeQrCode$default(dimensionPixelSize2, contents));
        } catch (WriterException e) {
            Log.e("WifiDppQrCodeGeneratorFragment", "Error generating QR code bitmap " + e);
        }
    }
}

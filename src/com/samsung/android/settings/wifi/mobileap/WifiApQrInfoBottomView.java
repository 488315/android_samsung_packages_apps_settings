package com.samsung.android.settings.wifi.mobileap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.SoftApConfiguration;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarMenu;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.WifiApUtils;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.WifiApQrCodeFragment;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFeatureUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.wifi.SemWifiManager;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiApQrInfoBottomView {
    public Activity mActivity;
    public AlertDialog mAlertDialog;
    public BottomNavigationView mBottomNavigationView;
    public RelativeLayout mButtonBar;
    public Context mContext;
    public MenuItem mInfoButton;
    public ArrayList mMHSCustomerList;
    public String mPrimaryTextColor;
    public SemWifiManager mSemWifiManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.WifiApQrInfoBottomView$2, reason: invalid class name */
    public final class AnonymousClass2 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            SALogging.insertSALog("TETH_010", "8028");
        }
    }

    public final void initBottomBar() {
        Log.i("WifiApQrInfoBottomView", "initBottomBar() - Start");
        Activity activity = this.mActivity;
        RelativeLayout relativeLayout = (RelativeLayout) activity.findViewById(R.id.button_bar);
        this.mButtonBar = relativeLayout;
        if (relativeLayout != null) {
            relativeLayout.removeAllViews();
            this.mButtonBar.addView(
                    (BottomNavigationView)
                            ((LayoutInflater) activity.getSystemService("layout_inflater"))
                                    .inflate(
                                            R.layout.sec_wifi_ap_qr_info_bottom_layout,
                                            (ViewGroup) this.mButtonBar,
                                            false));
        }
    }

    public final void initBottomNavigation() {
        Log.i("WifiApQrInfoBottomView", "initBottomNavigation() - Start");
        BottomNavigationView bottomNavigationView =
                (BottomNavigationView) this.mActivity.findViewById(R.id.bottom_navigation);
        this.mBottomNavigationView = bottomNavigationView;
        NavigationBarMenu navigationBarMenu = bottomNavigationView.menu;
        navigationBarMenu.findItem(R.id.qr_code_button);
        MenuItem findItem = navigationBarMenu.findItem(R.id.info_button);
        this.mInfoButton = findItem;
        this.mBottomNavigationView.selectedListener =
                new BottomNavigationView
                        .OnNavigationItemSelectedListener() { // from class:
                                                              // com.samsung.android.settings.wifi.mobileap.WifiApQrInfoBottomView.1
                    @Override // com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener
                    public final boolean onNavigationItemSelected(MenuItem menuItem) {
                        int itemId = menuItem.getItemId();
                        WifiApQrInfoBottomView wifiApQrInfoBottomView = WifiApQrInfoBottomView.this;
                        if (itemId != R.id.qr_code_button) {
                            if (itemId != R.id.info_button) {
                                return true;
                            }
                            SALogging.insertSALog("TETH_010", "8012");
                            wifiApQrInfoBottomView.showInfoDialog();
                            return true;
                        }
                        SALogging.insertSALog("TETH_010", "8011");
                        wifiApQrInfoBottomView.mButtonBar.setVisibility(8);
                        wifiApQrInfoBottomView.getClass();
                        Log.i("WifiApQrInfoBottomView", "launchQrCodeActivity() - Start");
                        SoftApConfiguration softApConfiguration =
                                wifiApQrInfoBottomView.mSemWifiManager.getSoftApConfiguration();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("key_config", softApConfiguration);
                        SubSettingLauncher subSettingLauncher =
                                new SubSettingLauncher(wifiApQrInfoBottomView.mContext);
                        SubSettingLauncher.LaunchRequest launchRequest =
                                subSettingLauncher.mLaunchRequest;
                        launchRequest.mExtras = bundle;
                        launchRequest.mArguments = bundle;
                        launchRequest.mSourceMetricsCategory = 3400;
                        launchRequest.mDestinationName =
                                WifiApQrCodeFragment.class.getCanonicalName();
                        subSettingLauncher.launch();
                        return true;
                    }
                };
        findItem.setVisible(!WifiApFeatureUtils.isMobileDataUsageSupported(this.mContext));
    }

    public final void showInfoDialog() {
        boolean z;
        MenuItem menuItem;
        Log.i("WifiApQrInfoBottomView", "showInfoDialog() - Start");
        SoftApConfiguration softApConfiguration = this.mSemWifiManager.getSoftApConfiguration();
        Context context = this.mContext;
        boolean z2 = WifiApSettingsUtils.DBG;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 0);
        View inflate =
                this.mActivity
                        .getLayoutInflater()
                        .inflate(R.layout.sec_wifi_ap_qr_info_dialog, (ViewGroup) null);
        TextView textView = (TextView) inflate.findViewById(R.id.wifi_ap_how_to_connect);
        TextView textView2 = (TextView) inflate.findViewById(R.id.title_instruction1);
        TextView textView3 = (TextView) inflate.findViewById(R.id.title_instruction2);
        TextView textView4 = (TextView) inflate.findViewById(R.id.title_instruction3);
        TextView textView5 = (TextView) inflate.findViewById(R.id.title_instruction4);
        TextView textView6 = (TextView) inflate.findViewById(R.id.title_num1);
        TextView textView7 = (TextView) inflate.findViewById(R.id.title_num2);
        TextView textView8 = (TextView) inflate.findViewById(R.id.title_num3);
        TextView textView9 = (TextView) inflate.findViewById(R.id.title_num4);
        TextView textView10 = (TextView) inflate.findViewById(R.id.qr_title_num_1);
        TextView textView11 = (TextView) inflate.findViewById(R.id.qr_title_num_2);
        TextView textView12 = (TextView) inflate.findViewById(R.id.wifi_ap_dun_fail_title_num_1);
        TextView textView13 = (TextView) inflate.findViewById(R.id.wifi_ap_dun_fail_title_num_2);
        String string = this.mContext.getString(R.string.wifi_ap_num1);
        String string2 = this.mContext.getString(R.string.wifi_ap_num2);
        String string3 = this.mContext.getString(R.string.wifi_ap_num3);
        String string4 = this.mContext.getString(R.string.wifi_ap_num4);
        String string5 = this.mContext.getString(R.string.wifi_ap_dot);
        String format =
                String.format(
                        this.mContext.getString(R.string.wifi_ap_instruction_num), string, string5);
        String format2 =
                String.format(
                        this.mContext.getString(R.string.wifi_ap_instruction_num),
                        string2,
                        string5);
        String format3 =
                String.format(
                        this.mContext.getString(R.string.wifi_ap_instruction_num),
                        string3,
                        string5);
        String format4 =
                String.format(
                        this.mContext.getString(R.string.wifi_ap_instruction_num),
                        string4,
                        string5);
        String format5 =
                String.format(
                        this.mContext.getString(R.string.wifi_ap_imp_instruction),
                        this.mContext.getString(R.string.wifi_ap_dun_fail_title));
        textView6.setText(format);
        textView7.setText(format2);
        textView8.setText(format3);
        textView9.setText(format4);
        textView10.setText(format);
        textView11.setText(format2);
        textView12.setText(format);
        textView13.setText(format2);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.title4);
        TextView textView14 =
                (TextView) inflate.findViewById(R.id.wifi_ap_how_to_connect_by_qrcode);
        TextView textView15 = (TextView) inflate.findViewById(R.id.qr_title_instruction1);
        TextView textView16 = (TextView) inflate.findViewById(R.id.qr_title_instruction2);
        LinearLayout linearLayout2 =
                (LinearLayout) inflate.findViewById(R.id.title_wifi_ap_dun_fail);
        LinearLayout linearLayout3 =
                (LinearLayout) inflate.findViewById(R.id.wifi_ap_dun_fail_title1);
        LinearLayout linearLayout4 =
                (LinearLayout) inflate.findViewById(R.id.wifi_ap_dun_fail_title2);
        LinearLayout linearLayout5 =
                (LinearLayout) inflate.findViewById(R.id.wifi_ap_dun_fail_title3);
        LinearLayout linearLayout6 =
                (LinearLayout) inflate.findViewById(R.id.wifi_ap_dun_fail_title4);
        TextView textView17 = (TextView) inflate.findViewById(R.id.wifi_ap_dun_fail);
        TextView textView18 =
                (TextView) inflate.findViewById(R.id.wifi_ap_dun_fail_title_instruction1);
        TextView textView19 =
                (TextView) inflate.findViewById(R.id.wifi_ap_dun_fail_title_instruction2);
        TextView textView20 =
                (TextView) inflate.findViewById(R.id.wifi_ap_dun_fail_title_instruction3);
        TextView textView21 =
                (TextView) inflate.findViewById(R.id.wifi_ap_dun_fail_title_instruction4);
        TextView textView22 = (TextView) inflate.findViewById(R.id.summary);
        if (softApConfiguration != null) {
            linearLayout.setVisibility(8);
            if (Rune.isDomesticModel()) {
                textView17.setText(format5);
                textView18.setText(this.mContext.getString(R.string.wifi_ap_dun_fail_msg1));
                textView19.setText(this.mContext.getString(R.string.wifi_ap_dun_fail_msg2));
                textView20.setText(this.mContext.getString(R.string.wifi_ap_dun_fail_msg3));
                textView21.setText(this.mContext.getString(R.string.wifi_ap_dun_fail_msg4));
            } else {
                linearLayout2.setVisibility(8);
                linearLayout3.setVisibility(8);
                linearLayout4.setVisibility(8);
                linearLayout5.setVisibility(8);
                linearLayout6.setVisibility(8);
            }
            int securityType = softApConfiguration.getSecurityType();
            String str = this.mPrimaryTextColor;
            if (securityType == 0 || softApConfiguration.getSecurityType() == 5) {
                if (softApConfiguration.isHiddenSsid()) {
                    textView.setText(
                            WifiApUtils.getString(
                                    this.mContext, R.string.wifi_ap_howto_with_password));
                    textView14.setText(
                            WifiApUtils.getString(
                                    this.mContext, R.string.wifi_ap_howto_with_qrcode));
                    textView2.setText(
                            WifiApUtils.getString(
                                    this.mContext,
                                    R.string.wifi_ap_instruction_open_activate_color_hidden1));
                    textView3.setText(
                            Html.fromHtml(
                                    this.mContext.getString(
                                            R.string
                                                    .wifi_ap_instruction_open_activate_color_hidden2,
                                            str,
                                            Html.escapeHtml(softApConfiguration.getSsid()))));
                    textView4.setText(
                            Html.fromHtml(
                                    this.mContext.getString(
                                            R.string
                                                    .wifi_ap_instruction_open_activate_color_hidden3,
                                            str,
                                            Html.escapeHtml(softApConfiguration.getSsid()))));
                    if ("TMO".equals(Utils.CONFIGOPBRANDINGFORMOBILEAP)) {
                        textView5.setText(
                                WifiApUtils.getString(
                                        this.mContext,
                                        R.string.wifi_ap_instruction_open_activate_color_hidden4));
                        linearLayout.setVisibility(0);
                    }
                    textView15.setText(
                            WifiApUtils.getString(
                                    this.mContext, R.string.wifi_ap_introduction_qrcode_enable));
                    textView16.setText(
                            WifiApUtils.getString(
                                    this.mContext, R.string.wifi_ap_introduction_qrcode_scan));
                } else {
                    textView.setText(
                            WifiApUtils.getString(
                                    this.mContext, R.string.wifi_ap_howto_with_password));
                    textView14.setText(
                            WifiApUtils.getString(
                                    this.mContext, R.string.wifi_ap_howto_with_qrcode));
                    textView2.setText(
                            WifiApUtils.getString(
                                    this.mContext,
                                    R.string.wifi_ap_instruction_open_activate_color1));
                    textView3.setText(
                            Html.fromHtml(
                                    this.mContext.getString(
                                            WifiApUtils.getStringID(
                                                    R.string
                                                            .wifi_ap_instruction_open_activate_color2),
                                            str,
                                            Html.escapeHtml(softApConfiguration.getSsid()))));
                    textView4.setText(
                            Html.fromHtml(
                                    this.mContext.getString(
                                            WifiApUtils.getStringID(
                                                    R.string
                                                            .wifi_ap_instruction_open_activate_color3),
                                            str,
                                            Html.escapeHtml(softApConfiguration.getSsid()))));
                    if (this.mMHSCustomerList.contains(Utils.CONFIGOPBRANDINGFORMOBILEAP)) {
                        textView5.setText(
                                WifiApUtils.getString(
                                        this.mContext,
                                        R.string.wifi_ap_instruction_open_activate_color4));
                        linearLayout.setVisibility(0);
                    }
                    textView15.setText(
                            WifiApUtils.getString(
                                    this.mContext, R.string.wifi_ap_introduction_qrcode_enable));
                    textView16.setText(
                            WifiApUtils.getString(
                                    this.mContext, R.string.wifi_ap_introduction_qrcode_scan));
                }
            } else if (softApConfiguration.isHiddenSsid()) {
                textView.setText(
                        WifiApUtils.getString(this.mContext, R.string.wifi_ap_howto_with_password));
                textView14.setText(
                        WifiApUtils.getString(this.mContext, R.string.wifi_ap_howto_with_qrcode));
                textView2.setText(
                        WifiApUtils.getString(
                                this.mContext,
                                R.string.wifi_ap_instruction_activate_color_hidden1));
                textView3.setText(
                        Html.fromHtml(
                                this.mContext.getString(
                                        R.string.wifi_ap_instruction_activate_color_hidden2,
                                        str,
                                        Html.escapeHtml(softApConfiguration.getSsid()))));
                textView4.setText(
                        Html.fromHtml(
                                this.mContext.getString(
                                        R.string.wifi_ap_instruction_activate_color_hidden3,
                                        str,
                                        Html.escapeHtml(softApConfiguration.getSsid()),
                                        Html.escapeHtml(softApConfiguration.getPassphrase()))));
                if ("TMO".equals(Utils.CONFIGOPBRANDINGFORMOBILEAP)) {
                    textView5.setText(
                            WifiApUtils.getString(
                                    this.mContext,
                                    R.string.wifi_ap_instruction_activate_color_hidden4));
                    linearLayout.setVisibility(0);
                }
                textView15.setText(
                        WifiApUtils.getString(
                                this.mContext, R.string.wifi_ap_introduction_qrcode_enable));
                textView16.setText(
                        WifiApUtils.getString(
                                this.mContext, R.string.wifi_ap_introduction_qrcode_scan));
            } else {
                textView.setText(
                        WifiApUtils.getString(this.mContext, R.string.wifi_ap_howto_with_password));
                textView14.setText(
                        WifiApUtils.getString(this.mContext, R.string.wifi_ap_howto_with_qrcode));
                textView2.setText(
                        WifiApUtils.getString(
                                this.mContext, R.string.wifi_ap_instruction_activate_color1));
                textView3.setText(
                        Html.fromHtml(
                                this.mContext.getString(
                                        WifiApUtils.getStringID(
                                                R.string.wifi_ap_instruction_activate_color2),
                                        str,
                                        Html.escapeHtml(softApConfiguration.getSsid()))));
                textView4.setText(
                        Html.fromHtml(
                                this.mContext.getString(
                                        WifiApUtils.getStringID(
                                                R.string.wifi_ap_instruction_activate_color3),
                                        str,
                                        Html.escapeHtml(softApConfiguration.getSsid()),
                                        Html.escapeHtml(softApConfiguration.getPassphrase()))));
                if (this.mMHSCustomerList.contains(Utils.CONFIGOPBRANDINGFORMOBILEAP)) {
                    textView5.setText(
                            WifiApUtils.getString(
                                    this.mContext,
                                    R.string.wifi_ap_instruction_open_activate_color4));
                    linearLayout.setVisibility(0);
                }
                textView15.setText(
                        WifiApUtils.getString(
                                this.mContext, R.string.wifi_ap_introduction_qrcode_enable));
                textView16.setText(
                        WifiApUtils.getString(
                                this.mContext, R.string.wifi_ap_introduction_qrcode_scan));
            }
            if ("VZW".equals(Utils.CONFIGOPBRANDINGFORMOBILEAP)) {
                textView22.setText(R.string.wifi_ap_instruction_battery_warning);
                z = false;
                textView22.setVisibility(0);
            } else {
                z = false;
                textView22.setVisibility(8);
            }
        } else {
            z = false;
        }
        builder.setTitle(R.string.wifi_ap_howto);
        builder.setPositiveButton(R.string.dlg_ok, new AnonymousClass2());
        builder.setView(inflate);
        this.mAlertDialog = builder.create();
        boolean z3 =
                this.mContext.getResources().getConfiguration().semDesktopModeEnabled == 1
                        ? true
                        : z;
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "showInfoDialog() - isDexMode:", "WifiApQrInfoBottomView", z3);
        if (z3 || (menuItem = this.mInfoButton) == null || menuItem.getActionView() == null) {
            AbsAdapter$$ExternalSyntheticOutline0.m(
                    "showInfoDialog() - isDexMode:", "WifiApQrInfoBottomView", z3);
        } else {
            this.mAlertDialog.semSetAnchor(this.mInfoButton.getActionView());
        }
        this.mAlertDialog.show();
    }
}

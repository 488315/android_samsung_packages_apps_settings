package com.samsung.android.settings.wifi;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.service.persistentdata.PersistentDataBlockManager;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.settings.ButtonBarHandler;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.network.SubscriptionUtil;
import com.android.settings.network.telephony.SubscriptionRepositoryKt;
import com.android.settings.wifi.WifiSettings;
import com.android.wifitrackerlib.WifiEntry;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.WifiIssueDetectorUtil;
import com.sec.android.vzwswlibrary.SetupWizardBaseActivity;
import com.sec.ims.extensions.WiFiManagerExt;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiSetupWizardActivityVzw extends SetupWizardBaseActivity
        implements ButtonBarHandler {
    public static final /* synthetic */ int $r8$clinit = 0;
    public WifiSetupWizardActivityVzw mActivity;
    public boolean mAllowCaptiveByKme;
    public ConnectivityManager mCm;
    public AnonymousClass4 mConnectedListAdapterListener;
    public WifiSetupWizardActivityVzw mContext;
    public AlertDialog mDialog;
    public int mDialogCode;
    public String mDialogLeftBtnText;
    public String mDialogMessage;
    public String mDialogRightBtnText;
    public String mDialogTitle;
    public final AnonymousClass1 mHandler;
    public boolean mIsAllowBack;
    public final boolean mIsEsimSupported;
    public boolean mIsFRPLocked;
    public boolean mIsWifiConnected;
    public int mLastShownDialogCode;
    public AnonymousClass5 mNetworkCallback;
    public final AnonymousClass2 mReceiver;
    public TelephonyManager mTelephonyManager;
    public boolean mUseSoftNavigation;
    public WifiEntry mWifiEntry;
    public WifiManager mWifiManager;
    public WifiSettings mWifiSettings;

    static {
        Color.parseColor("#000000");
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.settings.wifi.WifiSetupWizardActivityVzw$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.samsung.android.settings.wifi.WifiSetupWizardActivityVzw$2] */
    public WifiSetupWizardActivityVzw() {
        super.mContext = this;
        this.mIsEsimSupported =
                SemCscFeature.getInstance()
                        .getBoolean("CscFeature_SetupWizard_SupportEsimAsPrimary");
        this.mHandler =
                new Handler() { // from class:
                                // com.samsung.android.settings.wifi.WifiSetupWizardActivityVzw.1
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        int i = message.what;
                        WifiSetupWizardActivityVzw wifiSetupWizardActivityVzw =
                                WifiSetupWizardActivityVzw.this;
                        switch (i) {
                            case 10:
                                Boolean bool = Boolean.FALSE;
                                int i2 = WifiSetupWizardActivityVzw.$r8$clinit;
                                wifiSetupWizardActivityVzw.setMainActionButtonVisibility(bool);
                                wifiSetupWizardActivityVzw.setManualSetupButtonVisibility(
                                        Boolean.TRUE);
                                break;
                            case 11:
                            case 12:
                                if (wifiSetupWizardActivityVzw.mLastShownDialogCode != i) {
                                    AlertDialog alertDialog = wifiSetupWizardActivityVzw.mDialog;
                                    if (alertDialog != null && alertDialog.isShowing()) {
                                        wifiSetupWizardActivityVzw.mDialog.dismiss();
                                    }
                                    AlertDialog.Builder builder =
                                            new AlertDialog.Builder(
                                                    wifiSetupWizardActivityVzw.mActivity);
                                    builder.setCancelable(false);
                                    wifiSetupWizardActivityVzw.mLastShownDialogCode = i;
                                    if (i == 11) {
                                        builder.setTitle(
                                                wifiSetupWizardActivityVzw.getString(
                                                        R.string.no_internet_connection_title));
                                        builder.setMessage(
                                                wifiSetupWizardActivityVzw.getString(
                                                        R.string.no_internet_connection));
                                        builder.setPositiveButton(
                                                wifiSetupWizardActivityVzw.getString(
                                                        R.string.dlg_ok),
                                                new WifiSetupWizardActivityVzw$$ExternalSyntheticLambda2(
                                                        wifiSetupWizardActivityVzw, 0));
                                    } else if (i == 12) {
                                        if (!wifiSetupWizardActivityVzw.mIsFRPLocked
                                                || wifiSetupWizardActivityVzw.mAllowCaptiveByKme) {
                                            wifiSetupWizardActivityVzw.mWifiSettings.mListener =
                                                    wifiSetupWizardActivityVzw
                                                            .mConnectedListAdapterListener;
                                            builder.setTitle(
                                                    wifiSetupWizardActivityVzw.getString(
                                                            R.string
                                                                    .vzw_captive_portal_signin_dialog_title));
                                            builder.setMessage(
                                                    wifiSetupWizardActivityVzw.getString(
                                                            R.string
                                                                    .vzw_captive_portal_signin_dialog_desc));
                                            builder.setPositiveButton(
                                                    wifiSetupWizardActivityVzw.getString(
                                                            R.string
                                                                    .vzw_captive_portal_signin_right_btn),
                                                    new WifiSetupWizardActivityVzw$$ExternalSyntheticLambda2(
                                                            wifiSetupWizardActivityVzw, 1));
                                            builder.setNegativeButton(
                                                    wifiSetupWizardActivityVzw.getString(
                                                            R.string.wifi_cancel),
                                                    new WifiSetupWizardActivityVzw$$ExternalSyntheticLambda2(
                                                            wifiSetupWizardActivityVzw, 2));
                                        } else {
                                            builder.setMessage(
                                                    wifiSetupWizardActivityVzw.getString(
                                                            R.string
                                                                    .wifi_captive_portal_message_vzw));
                                            wifiSetupWizardActivityVzw.forgetCurrentNetwork$1();
                                            builder.setPositiveButton(
                                                    wifiSetupWizardActivityVzw.getString(
                                                            R.string.okay),
                                                    new WifiSetupWizardActivityVzw$$ExternalSyntheticLambda3(
                                                            0));
                                        }
                                    }
                                    AlertDialog create = builder.create();
                                    wifiSetupWizardActivityVzw.mDialog = create;
                                    create.setCanceledOnTouchOutside(false);
                                    if (wifiSetupWizardActivityVzw.mUseSoftNavigation) {
                                        wifiSetupWizardActivityVzw
                                                .mDialog
                                                .getWindow()
                                                .getDecorView()
                                                .setSystemUiVisibility(2050);
                                    }
                                    if (!wifiSetupWizardActivityVzw.mActivity.isFinishing()) {
                                        wifiSetupWizardActivityVzw.mDialog.show();
                                        break;
                                    }
                                }
                                break;
                            case 13:
                                Boolean bool2 = Boolean.TRUE;
                                int i3 = WifiSetupWizardActivityVzw.$r8$clinit;
                                wifiSetupWizardActivityVzw.setMainActionButtonVisibility(bool2);
                                wifiSetupWizardActivityVzw.setManualSetupButtonVisibility(
                                        Boolean.FALSE);
                                break;
                        }
                    }
                };
        this.mReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.samsung.android.settings.wifi.WifiSetupWizardActivityVzw.2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        String action = intent.getAction();
                        Log.d("WifiSetupWizardActivityVzw", "Broadcast " + action);
                        action.getClass();
                        if (action.equals("android.intent.action.SIM_STATE_CHANGED")) {
                            WifiSetupWizardActivityVzw wifiSetupWizardActivityVzw =
                                    WifiSetupWizardActivityVzw.this;
                            int i = WifiSetupWizardActivityVzw.$r8$clinit;
                            wifiSetupWizardActivityVzw.updateSimState$1();
                        }
                    }
                };
    }

    public final void forgetCurrentNetwork$1() {
        WifiInfo connectionInfo;
        WifiConfiguration wifiConfiguration;
        if (this.mActivity.isDestroyed()
                || (connectionInfo = this.mWifiManager.getConnectionInfo()) == null
                || connectionInfo.getNetworkId() == -1) {
            return;
        }
        int networkId = connectionInfo.getNetworkId();
        List<WifiConfiguration> configuredNetworks = this.mWifiManager.getConfiguredNetworks();
        if (configuredNetworks != null) {
            Iterator<WifiConfiguration> it = configuredNetworks.iterator();
            while (it.hasNext()) {
                wifiConfiguration = it.next();
                if (wifiConfiguration.networkId == networkId) {
                    break;
                }
            }
        }
        wifiConfiguration = null;
        if (wifiConfiguration != null) {
            if (wifiConfiguration.carrierId != -1) {
                WifiSetupWizardActivityVzw wifiSetupWizardActivityVzw = this.mActivity;
                ((SemWifiManager)
                                wifiSetupWizardActivityVzw.getSystemService(
                                        WiFiManagerExt.SEM_WIFI_SERVICE))
                        .reportIssue(
                                100,
                                WifiIssueDetectorUtil.ReportUtil.getReportDataForWifiManagerApi(
                                        -1,
                                        "disconnect",
                                        wifiSetupWizardActivityVzw
                                                .getPackageManager()
                                                .getNameForUid(
                                                        wifiSetupWizardActivityVzw.getUserId()),
                                        wifiSetupWizardActivityVzw.getPackageName()));
                this.mWifiManager.disconnect();
                return;
            }
            int networkId2 = connectionInfo.getNetworkId();
            WifiSetupWizardActivityVzw wifiSetupWizardActivityVzw2 = this.mActivity;
            ((SemWifiManager)
                            wifiSetupWizardActivityVzw2.getSystemService(
                                    WiFiManagerExt.SEM_WIFI_SERVICE))
                    .reportIssue(
                            102,
                            WifiIssueDetectorUtil.ReportUtil.getReportDataForWifiManagerApi(
                                    networkId2,
                                    "forget",
                                    wifiSetupWizardActivityVzw2
                                            .getPackageManager()
                                            .getNameForUid(wifiSetupWizardActivityVzw2.getUserId()),
                                    wifiSetupWizardActivityVzw2.getPackageName()));
            this.mWifiManager.forget(networkId2, null);
        }
    }

    @Override // com.android.settings.ButtonBarHandler
    public final Button getNextButton() {
        return null;
    }

    @Override // com.android.settings.ButtonBarHandler
    public final boolean hasNextButton() {
        return false;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onBackPressed() {
        if (this.mIsAllowBack) {
            setResult(0);
            finish();
        }
    }

    /* JADX WARN: Type inference failed for: r10v48, types: [com.samsung.android.settings.wifi.WifiSetupWizardActivityVzw$4] */
    @Override // com.sec.android.vzwswlibrary.SetupWizardBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        boolean z;
        super.onCreate(bundle);
        this.mActivity = this;
        this.mContext = this;
        PersistentDataBlockManager persistentDataBlockManager =
                (PersistentDataBlockManager) getSystemService(PersistentDataBlockManager.class);
        if (persistentDataBlockManager != null) {
            z = persistentDataBlockManager.isFactoryResetProtectionActive();
        } else {
            Log.e("WifiSetupWizardActivityVzw", "Failed to get PersistentDataBlockManager");
            z = false;
        }
        this.mIsFRPLocked = z;
        Log.d("WifiSetupWizardActivityVzw", "mIsFRPLocked = " + this.mIsFRPLocked);
        setContentView(R.layout.sec_wifi_setupwizard_vzw);
        this.mRootLayout.setIcon(
                getApplicationContext().getDrawable(R.drawable.sec_wifi_suw_header));
        this.mRootLayout.setHeaderText(R.string.wifi_suw_title_vzw);
        this.mRootLayout.getHeaderTextView().setVisibility(0);
        int dimensionPixelSize =
                getResources().getDimensionPixelSize(R.dimen.ssw_wifi_header_title_bottom_margin);
        TextView headerTextView = this.mRootLayout.getHeaderTextView();
        ViewGroup.MarginLayoutParams marginLayoutParams =
                (ViewGroup.MarginLayoutParams) headerTextView.getLayoutParams();
        marginLayoutParams.bottomMargin = dimensionPixelSize;
        headerTextView.setLayoutParams(marginLayoutParams);
        WifiSettings wifiSettings =
                (WifiSettings)
                        getSupportFragmentManager()
                                .findFragmentById(R.id.wifi_secsetup_activity_fragment_layout);
        this.mWifiSettings = wifiSettings;
        this.mWifiManager = wifiSettings.mWifiManager;
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService("phone");
        this.mTelephonyManager = telephonyManager;
        if (telephonyManager.getSimState() != 5
                && SemCscFeature.getInstance()
                        .getBoolean("CscFeature_SetupWizard_SupportEsimAsPrimary")) {
            this.mRootLayout.setHeaderText(R.string.wifi_suw_title);
            this.mRootLayout.getHeaderTextView().setVisibility(0);
            ((TextView) findViewById(R.id.header_content)).setVisibility(8);
            TextView textView =
                    (TextView) findViewById(R.id.wifi_secsetup_emergency_call_button_vzw);
            textView.setVisibility(0);
            textView.setPaintFlags(8 | textView.getPaintFlags());
            textView.setOnClickListener(
                    new View.OnClickListener() { // from class:
                        // com.samsung.android.settings.wifi.WifiSetupWizardActivityVzw.3
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            WifiSetupWizardActivityVzw wifiSetupWizardActivityVzw =
                                    WifiSetupWizardActivityVzw.this;
                            int i = WifiSetupWizardActivityVzw.$r8$clinit;
                            wifiSetupWizardActivityVzw.getClass();
                            Intent intent =
                                    new Intent(
                                            "com.samsung.android.app.telephonyui.action.OPEN_EMERGENCY_DIALER");
                            intent.putExtra("enable_home_key", false);
                            intent.putExtra("enable_recent_key", false);
                            intent.putExtra("enable_status_bar_expand", false);
                            intent.putExtra("enable_ice_contact_list", false);
                            intent.putExtra("enable_emergency_medical_info", false);
                            intent.setFlags(612368384);
                            try {
                                wifiSetupWizardActivityVzw.startActivityForResult(intent, 2020);
                            } catch (ActivityNotFoundException unused) {
                                Log.e(
                                        "WifiSetupWizardActivityVzw",
                                        "Emergency dialer activity not found");
                            }
                        }
                    });
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        registerReceiver(this.mReceiver, intentFilter);
        this.mDialogCode = getIntent().getIntExtra("dialog_code", -1);
        this.mDialogTitle = getIntent().getStringExtra("dialog_title");
        this.mDialogMessage = getIntent().getStringExtra("dialog_msg");
        this.mDialogLeftBtnText = getIntent().getStringExtra("dialog_left_btn");
        this.mDialogRightBtnText = getIntent().getStringExtra("dialog_right_btn");
        this.mIsAllowBack = getIntent().getBooleanExtra("allowBack", true);
        this.mUseSoftNavigation = getIntent().getBooleanExtra("usesSoftNavigationKeys", false);
        this.mAllowCaptiveByKme = getIntent().getBooleanExtra("allowCaptiveByKme", false);
        final int i = 0;
        this.mMainActionButton =
                new FooterButton(
                        super.mContext.getString(R.string.next_button_label),
                        new View.OnClickListener(this) { // from class:
                            // com.samsung.android.settings.wifi.WifiSetupWizardActivityVzw$$ExternalSyntheticLambda0
                            public final /* synthetic */ WifiSetupWizardActivityVzw f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i2 = i;
                                WifiSetupWizardActivityVzw wifiSetupWizardActivityVzw = this.f$0;
                                switch (i2) {
                                    case 0:
                                        int i3 = WifiSetupWizardActivityVzw.$r8$clinit;
                                        wifiSetupWizardActivityVzw.setFinishAction();
                                        break;
                                    default:
                                        if (wifiSetupWizardActivityVzw.mCm.getActiveNetworkInfo()
                                                        != null
                                                && wifiSetupWizardActivityVzw
                                                                .mCm
                                                                .getActiveNetworkInfo()
                                                                .getType()
                                                        == 9) {
                                            wifiSetupWizardActivityVzw.setFinishAction();
                                            break;
                                        } else {
                                            AlertDialog alertDialog =
                                                    wifiSetupWizardActivityVzw.mDialog;
                                            if (alertDialog != null && alertDialog.isShowing()) {
                                                wifiSetupWizardActivityVzw.mDialog.dismiss();
                                            }
                                            AlertDialog.Builder builder =
                                                    new AlertDialog.Builder(
                                                            wifiSetupWizardActivityVzw.mActivity);
                                            builder.setTitle(
                                                    wifiSetupWizardActivityVzw.mDialogTitle);
                                            builder.setMessage(
                                                    wifiSetupWizardActivityVzw.mDialogMessage);
                                            builder.setCancelable(false);
                                            int i4 = wifiSetupWizardActivityVzw.mDialogCode;
                                            if (i4 == 1) {
                                                if (wifiSetupWizardActivityVzw.mIsEsimSupported
                                                        && SemCscFeature.getInstance()
                                                                .getBoolean(
                                                                        "CscFeature_RIL_SupporteSimBootstrap",
                                                                        false)
                                                        && wifiSetupWizardActivityVzw
                                                                        .mTelephonyManager
                                                                        .getSimState()
                                                                != 5) {
                                                    builder.setTitle(
                                                            R.string
                                                                    .wifi_and_mobile_continue_title);
                                                    builder.setMessage(
                                                            Utils.isTablet()
                                                                    ? R.string
                                                                            .wifi_and_mobile_continue_message_tablet
                                                                    : R.string
                                                                            .wifi_and_mobile_continue_message_phone);
                                                    builder.setPositiveButton(
                                                            R.string.wifi_continue_button,
                                                            new WifiSetupWizardActivityVzw$$ExternalSyntheticLambda2(
                                                                    wifiSetupWizardActivityVzw, 3));
                                                } else {
                                                    builder.setPositiveButton(
                                                            wifiSetupWizardActivityVzw
                                                                    .mDialogLeftBtnText,
                                                            new WifiSetupWizardActivityVzw$$ExternalSyntheticLambda2(
                                                                    wifiSetupWizardActivityVzw, 4));
                                                }
                                                builder.setNegativeButton(
                                                        wifiSetupWizardActivityVzw
                                                                .mDialogRightBtnText,
                                                        new WifiSetupWizardActivityVzw$$ExternalSyntheticLambda3(
                                                                3));
                                            } else if (i4 == 2) {
                                                builder.setPositiveButton(
                                                        wifiSetupWizardActivityVzw
                                                                .mDialogRightBtnText,
                                                        new WifiSetupWizardActivityVzw$$ExternalSyntheticLambda2(
                                                                wifiSetupWizardActivityVzw, 5));
                                                builder.setNegativeButton(
                                                        wifiSetupWizardActivityVzw
                                                                .mDialogLeftBtnText,
                                                        new WifiSetupWizardActivityVzw$$ExternalSyntheticLambda3(
                                                                1));
                                            } else if (i4 == 3) {
                                                builder.setPositiveButton(
                                                        wifiSetupWizardActivityVzw
                                                                .mDialogRightBtnText,
                                                        new WifiSetupWizardActivityVzw$$ExternalSyntheticLambda3(
                                                                2));
                                            }
                                            AlertDialog create = builder.create();
                                            wifiSetupWizardActivityVzw.mDialog = create;
                                            create.setCanceledOnTouchOutside(false);
                                            if (wifiSetupWizardActivityVzw.mUseSoftNavigation) {
                                                wifiSetupWizardActivityVzw
                                                        .mDialog
                                                        .getWindow()
                                                        .getDecorView()
                                                        .setSystemUiVisibility(2050);
                                            }
                                            if (!wifiSetupWizardActivityVzw.mActivity
                                                    .isFinishing()) {
                                                wifiSetupWizardActivityVzw.mDialog.show();
                                                break;
                                            }
                                        }
                                        break;
                                }
                            }
                        },
                        5,
                        0);
        Boolean bool = Boolean.TRUE;
        setMainActionButtonVisibility(bool);
        FooterBarMixin footerBarMixin =
                (FooterBarMixin) this.mRootLayout.getMixin(FooterBarMixin.class);
        footerBarMixin.setPrimaryButton(this.mMainActionButton);
        footerBarMixin.getPrimaryButtonView().setTextAppearance(R.style.footer_button_style);
        setMainActionButtonVisibility(Boolean.FALSE);
        View findViewById = findViewById(R.id.sud_layout_content);
        findViewById.setPadding(
                findViewById.getPaddingStart(),
                this.mContext
                        .getResources()
                        .getDimensionPixelSize(R.dimen.ssw_wifi_header_title_bottom_margin),
                findViewById.getPaddingEnd(),
                findViewById.getPaddingBottom());
        final int i2 = 1;
        this.mManualSetupButton =
                new FooterButton(
                        super.mContext.getString(R.string.wifi_skip_button),
                        new View.OnClickListener(this) { // from class:
                            // com.samsung.android.settings.wifi.WifiSetupWizardActivityVzw$$ExternalSyntheticLambda0
                            public final /* synthetic */ WifiSetupWizardActivityVzw f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i22 = i2;
                                WifiSetupWizardActivityVzw wifiSetupWizardActivityVzw = this.f$0;
                                switch (i22) {
                                    case 0:
                                        int i3 = WifiSetupWizardActivityVzw.$r8$clinit;
                                        wifiSetupWizardActivityVzw.setFinishAction();
                                        break;
                                    default:
                                        if (wifiSetupWizardActivityVzw.mCm.getActiveNetworkInfo()
                                                        != null
                                                && wifiSetupWizardActivityVzw
                                                                .mCm
                                                                .getActiveNetworkInfo()
                                                                .getType()
                                                        == 9) {
                                            wifiSetupWizardActivityVzw.setFinishAction();
                                            break;
                                        } else {
                                            AlertDialog alertDialog =
                                                    wifiSetupWizardActivityVzw.mDialog;
                                            if (alertDialog != null && alertDialog.isShowing()) {
                                                wifiSetupWizardActivityVzw.mDialog.dismiss();
                                            }
                                            AlertDialog.Builder builder =
                                                    new AlertDialog.Builder(
                                                            wifiSetupWizardActivityVzw.mActivity);
                                            builder.setTitle(
                                                    wifiSetupWizardActivityVzw.mDialogTitle);
                                            builder.setMessage(
                                                    wifiSetupWizardActivityVzw.mDialogMessage);
                                            builder.setCancelable(false);
                                            int i4 = wifiSetupWizardActivityVzw.mDialogCode;
                                            if (i4 == 1) {
                                                if (wifiSetupWizardActivityVzw.mIsEsimSupported
                                                        && SemCscFeature.getInstance()
                                                                .getBoolean(
                                                                        "CscFeature_RIL_SupporteSimBootstrap",
                                                                        false)
                                                        && wifiSetupWizardActivityVzw
                                                                        .mTelephonyManager
                                                                        .getSimState()
                                                                != 5) {
                                                    builder.setTitle(
                                                            R.string
                                                                    .wifi_and_mobile_continue_title);
                                                    builder.setMessage(
                                                            Utils.isTablet()
                                                                    ? R.string
                                                                            .wifi_and_mobile_continue_message_tablet
                                                                    : R.string
                                                                            .wifi_and_mobile_continue_message_phone);
                                                    builder.setPositiveButton(
                                                            R.string.wifi_continue_button,
                                                            new WifiSetupWizardActivityVzw$$ExternalSyntheticLambda2(
                                                                    wifiSetupWizardActivityVzw, 3));
                                                } else {
                                                    builder.setPositiveButton(
                                                            wifiSetupWizardActivityVzw
                                                                    .mDialogLeftBtnText,
                                                            new WifiSetupWizardActivityVzw$$ExternalSyntheticLambda2(
                                                                    wifiSetupWizardActivityVzw, 4));
                                                }
                                                builder.setNegativeButton(
                                                        wifiSetupWizardActivityVzw
                                                                .mDialogRightBtnText,
                                                        new WifiSetupWizardActivityVzw$$ExternalSyntheticLambda3(
                                                                3));
                                            } else if (i4 == 2) {
                                                builder.setPositiveButton(
                                                        wifiSetupWizardActivityVzw
                                                                .mDialogRightBtnText,
                                                        new WifiSetupWizardActivityVzw$$ExternalSyntheticLambda2(
                                                                wifiSetupWizardActivityVzw, 5));
                                                builder.setNegativeButton(
                                                        wifiSetupWizardActivityVzw
                                                                .mDialogLeftBtnText,
                                                        new WifiSetupWizardActivityVzw$$ExternalSyntheticLambda3(
                                                                1));
                                            } else if (i4 == 3) {
                                                builder.setPositiveButton(
                                                        wifiSetupWizardActivityVzw
                                                                .mDialogRightBtnText,
                                                        new WifiSetupWizardActivityVzw$$ExternalSyntheticLambda3(
                                                                2));
                                            }
                                            AlertDialog create = builder.create();
                                            wifiSetupWizardActivityVzw.mDialog = create;
                                            create.setCanceledOnTouchOutside(false);
                                            if (wifiSetupWizardActivityVzw.mUseSoftNavigation) {
                                                wifiSetupWizardActivityVzw
                                                        .mDialog
                                                        .getWindow()
                                                        .getDecorView()
                                                        .setSystemUiVisibility(2050);
                                            }
                                            if (!wifiSetupWizardActivityVzw.mActivity
                                                    .isFinishing()) {
                                                wifiSetupWizardActivityVzw.mDialog.show();
                                                break;
                                            }
                                        }
                                        break;
                                }
                            }
                        },
                        0,
                        0);
        setManualSetupButtonVisibility(bool);
        FooterBarMixin footerBarMixin2 =
                (FooterBarMixin) this.mRootLayout.getMixin(FooterBarMixin.class);
        footerBarMixin2.setSecondaryButton(this.mManualSetupButton, false);
        footerBarMixin2.getSecondaryButtonView().setTextAppearance(R.style.footer_button_style);
        this.mConnectedListAdapterListener =
                new ConnectedListAdapter
                        .OnEventListener() { // from class:
                                             // com.samsung.android.settings.wifi.WifiSetupWizardActivityVzw.4
                    @Override // com.samsung.android.settings.wifi.ConnectedListAdapter.OnEventListener
                    public final void onItemClicked(WifiEntry wifiEntry) {
                        Log.d("WifiSetupWizardActivityVzw", "onItemClicked");
                        WifiSetupWizardActivityVzw.this.mWifiEntry = wifiEntry;
                    }
                };
        if ("1".equals(SystemProperties.get("wifi.test.off"))) {
            this.mWifiManager.setWifiEnabled(false);
        } else if (Settings.Secure.getIntForUser(
                        getContentResolver(), "first_enter_wifisetupwizard", 1, -2)
                == 1) {
            this.mWifiManager.setWifiEnabled(true);
            Settings.Secure.putIntForUser(
                    getContentResolver(), "first_enter_wifisetupwizard", 0, -2);
        }
        this.mIsWifiConnected = false;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onDestroy() {
        unregisterReceiver(this.mReceiver);
        unregisterNetworkCallback$2();
        super.onDestroy();
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.samsung.android.settings.wifi.WifiSetupWizardActivityVzw$5] */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        updateSimState$1();
        if (this.mCm == null) {
            this.mCm = (ConnectivityManager) getSystemService("connectivity");
        }
        unregisterNetworkCallback$2();
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        builder.addTransportType(1);
        if (this.mNetworkCallback == null) {
            this.mNetworkCallback =
                    new ConnectivityManager
                            .NetworkCallback() { // from class:
                                                 // com.samsung.android.settings.wifi.WifiSetupWizardActivityVzw.5
                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public final void onAvailable(Network network) {
                            WifiSetupWizardActivityVzw wifiSetupWizardActivityVzw =
                                    WifiSetupWizardActivityVzw.this;
                            wifiSetupWizardActivityVzw.mIsWifiConnected = false;
                            wifiSetupWizardActivityVzw.mLastShownDialogCode = -1;
                            wifiSetupWizardActivityVzw.mHandler.sendEmptyMessageDelayed(11, 8000L);
                        }

                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public final void onCapabilitiesChanged(
                                Network network, NetworkCapabilities networkCapabilities) {
                            if (networkCapabilities.hasCapability(16)) {
                                WifiSetupWizardActivityVzw wifiSetupWizardActivityVzw =
                                        WifiSetupWizardActivityVzw.this;
                                wifiSetupWizardActivityVzw.mIsWifiConnected = true;
                                wifiSetupWizardActivityVzw.mHandler.removeMessages(11);
                                sendEmptyMessage(13);
                                return;
                            }
                            if (networkCapabilities.hasCapability(17)) {
                                WifiSetupWizardActivityVzw wifiSetupWizardActivityVzw2 =
                                        WifiSetupWizardActivityVzw.this;
                                wifiSetupWizardActivityVzw2.mIsWifiConnected = false;
                                wifiSetupWizardActivityVzw2.mHandler.removeMessages(11);
                                sendEmptyMessage(12);
                            }
                        }

                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public final void onLost(Network network) {
                            super.onLost(network);
                            WifiSetupWizardActivityVzw wifiSetupWizardActivityVzw =
                                    WifiSetupWizardActivityVzw.this;
                            wifiSetupWizardActivityVzw.mIsWifiConnected = false;
                            wifiSetupWizardActivityVzw.mHandler.removeCallbacksAndMessages(null);
                            sendEmptyMessage(10);
                        }
                    };
        }
        this.mCm.registerNetworkCallback(builder.build(), this.mNetworkCallback);
    }

    public final void setFinishAction() {
        Intent intent = new Intent();
        intent.putExtra("is_wifi_connected", this.mIsWifiConnected);
        setResult(-1, intent);
        finish();
    }

    public final void unregisterNetworkCallback$2() {
        if (this.mCm == null) {
            this.mCm = (ConnectivityManager) getSystemService("connectivity");
        }
        try {
            AnonymousClass5 anonymousClass5 = this.mNetworkCallback;
            if (anonymousClass5 != null) {
                this.mCm.unregisterNetworkCallback(anonymousClass5);
            }
        } catch (IllegalArgumentException unused) {
            Log.d("WifiSetupWizardActivityVzw", "No need to unregister");
        }
    }

    public final void updateSimState$1() {
        int simState = this.mTelephonyManager.getSimState();
        if (simState == 0 || simState == 1 || simState == 7 || simState != 8) {}
        Pattern pattern = SubscriptionUtil.REGEX_DISPLAY_NAME_SUFFIX_PATTERN;
        List<SubscriptionInfo> selectableSubscriptionInfoList =
                SubscriptionRepositoryKt.getSelectableSubscriptionInfoList(this);
        if (selectableSubscriptionInfoList != null) {
            for (SubscriptionInfo subscriptionInfo : selectableSubscriptionInfoList) {
                if (subscriptionInfo != null && subscriptionInfo.isEmbedded()) {
                    subscriptionInfo.semGetProfileClass();
                }
            }
        }
    }
}

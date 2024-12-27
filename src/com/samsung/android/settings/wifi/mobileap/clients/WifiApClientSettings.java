package com.samsung.android.settings.wifi.mobileap.clients;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemProperties;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.BackStackRecord$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamMediaService$4$$ExternalSyntheticOutline0;
import com.android.settings.dashboard.DashboardFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarMenu;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApDataUsageConfig;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApConnectedDeviceUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.views.WifiApDialog;
import com.samsung.android.settings.wifi.mobileap.views.WifiApPreference;
import com.samsung.android.settings.wifi.mobileap.views.WifiApStackedProgressBarPreference;
import com.samsung.android.settings.wifi.mobileap.views.progressbar.StackedProgressBarEntry;
import com.samsung.android.util.SemLog;
import com.samsung.android.wifi.SemWifiApClientDetails;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApClientSettings extends DashboardFragment {
    public static final IntentFilter INTENT_FILTER =
            new IntentFilter("com.samsung.android.net.wifi.WIFI_AP_STA_STATE_CHANGED");
    public WifiApPreference mClientDataUsagePreference;
    public WifiApStackedProgressBarPreference mClientDataUsageProgressBar;
    public Context mContext;
    public String mDeviceName;
    public WifiApDialog mEditDeviceNameDialog;
    public boolean mIsOtpClient;
    public String mMacAddress;
    public PreferenceScreen mPreferenceScreen;
    public SemWifiApClientDetails mSemWifiApClientDetails;
    public SemWifiManager mSemWifiManager;
    public WifiApClientSetDataLimitPreferenceController mSetDataLimitPreferenceController;
    public WifiApClientSetTimeLimitPreferenceController mSetTimeLimitPreferenceController;
    public String mTempSsid = null;
    public String mSSID = null;
    public final String mErrSSID = "\tUSER#DEFINED#PWD#\n";
    public int mSSIDLen = 0;
    public String mIp = ApnSettings.MVNO_NONE;
    public Long mConnectedTime = 0L;
    public Long mConnectedDurationInMillis = 0L;
    public String dateFormat = null;
    public final AnonymousClass1 mSemWifiApClientUpdateCallback =
            new SemWifiManager
                    .SemWifiApClientUpdateCallback() { // from class:
                                                       // com.samsung.android.settings.wifi.mobileap.clients.WifiApClientSettings.1
                public final void onClientUpdated(SemWifiApClientDetails semWifiApClientDetails) {
                    IntentFilter intentFilter = WifiApClientSettings.INTENT_FILTER;
                    SemLog.i(
                            "WifiApClientSettings",
                            "onClientUpdated() : " + semWifiApClientDetails.toString());
                    if (!semWifiApClientDetails.isClientConnected()) {
                        SemLog.d(
                                "WifiApClientSettings",
                                "Client Disconnected callback. Closing activity");
                        WifiApClientSettings.this.finish();
                    }
                    WifiApClientSettings.this.refreshConnectedPreferences();
                }
            };
    public final AnonymousClass2 mBroadcastReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.wifi.mobileap.clients.WifiApClientSettings.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    IntentFilter intentFilter = WifiApClientSettings.INTENT_FILTER;
                    if (AudioStreamMediaService$4$$ExternalSyntheticOutline0.m(
                            "Broadcast Received: ",
                            action,
                            "WifiApClientSettings",
                            "com.samsung.android.net.wifi.WIFI_AP_STA_STATE_CHANGED")) {
                        WifiApClientSettings wifiApClientSettings = WifiApClientSettings.this;
                        if (WifiApConnectedDeviceUtils.getWifiApClientDetails(
                                        wifiApClientSettings.mContext,
                                        wifiApClientSettings.mMacAddress)
                                .isClientConnected()) {
                            return;
                        }
                        SemLog.d("WifiApClientSettings", "Client Disconnected. Closing activity");
                        WifiApClientSettings.this.finish();
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.clients.WifiApClientSettings$6, reason: invalid class name */
    public final class AnonymousClass6 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            SALogging.insertSALog("TETH_010", "8027");
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WifiApClientSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 3400;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_ap_connected_device_detail_view;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        final WifiApClientSettingsBottomView wifiApClientSettingsBottomView =
                new WifiApClientSettingsBottomView();
        Log.i("WifiApClientSettingsBottomView", "WifiApQrInfoBottomView() - Start");
        Context context = getContext();
        wifiApClientSettingsBottomView.mContext = context;
        Activity activity = (Activity) context;
        wifiApClientSettingsBottomView.mWifiApClientSettings = this;
        String str = this.mMacAddress;
        wifiApClientSettingsBottomView.mMacAddress = str;
        Log.i("WifiApClientSettingsBottomView", "initBottomBar() - Start");
        RelativeLayout relativeLayout = (RelativeLayout) activity.findViewById(R.id.button_bar);
        wifiApClientSettingsBottomView.mButtonBar = relativeLayout;
        if (relativeLayout != null) {
            relativeLayout.setVisibility(0);
            wifiApClientSettingsBottomView.mButtonBar.removeAllViews();
            wifiApClientSettingsBottomView.mButtonBar.addView(
                    (BottomNavigationView)
                            ((LayoutInflater) activity.getSystemService("layout_inflater"))
                                    .inflate(
                                            R.layout.sec_wifi_ap_client_settings_bottom_layout,
                                            (ViewGroup) wifiApClientSettingsBottomView.mButtonBar,
                                            false));
        }
        Log.i("WifiApClientSettingsBottomView", "initBottomNavigation() - Start");
        BottomNavigationView bottomNavigationView =
                (BottomNavigationView) activity.findViewById(R.id.bottom_navigation);
        wifiApClientSettingsBottomView.mBottomNavigationView = bottomNavigationView;
        NavigationBarMenu navigationBarMenu = bottomNavigationView.menu;
        wifiApClientSettingsBottomView.mPauseButton = navigationBarMenu.findItem(R.id.pause_button);
        wifiApClientSettingsBottomView.mResumeButton =
                navigationBarMenu.findItem(R.id.resume_button);
        if (WifiApConnectedDeviceUtils.getWifiApClientDetails(context, str)
                .isClientDataPausedByUser()) {
            wifiApClientSettingsBottomView.mPauseButton.setVisible(false);
            wifiApClientSettingsBottomView.mResumeButton.setVisible(true);
        } else {
            wifiApClientSettingsBottomView.mPauseButton.setVisible(true);
            wifiApClientSettingsBottomView.mResumeButton.setVisible(false);
        }
        wifiApClientSettingsBottomView.mBottomNavigationView.selectedListener =
                new BottomNavigationView
                        .OnNavigationItemSelectedListener() { // from class:
                                                              // com.samsung.android.settings.wifi.mobileap.clients.WifiApClientSettingsBottomView.1
                    @Override // com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener
                    public final boolean onNavigationItemSelected(MenuItem menuItem) {
                        int itemId = menuItem.getItemId();
                        WifiApClientSettingsBottomView wifiApClientSettingsBottomView2 =
                                WifiApClientSettingsBottomView.this;
                        if (itemId == R.id.pause_button) {
                            SALogging.insertSALog(0L, "TETH_014", "8085", (String) null);
                            wifiApClientSettingsBottomView2.mPauseButton.setVisible(false);
                            wifiApClientSettingsBottomView2.mResumeButton.setVisible(true);
                            Context context2 = wifiApClientSettingsBottomView2.mContext;
                            String str2 = wifiApClientSettingsBottomView2.mMacAddress;
                            Log.i("WifiApConnectedDeviceUtils", "setWifiApClientDataPaused: true");
                            WifiApFrameworkUtils.getSemWifiManager(context2)
                                    .setWifiApClientDataPaused(str2, true);
                        } else if (itemId == R.id.resume_button) {
                            SALogging.insertSALog(1L, "TETH_014", "8085", (String) null);
                            wifiApClientSettingsBottomView2.mPauseButton.setVisible(true);
                            wifiApClientSettingsBottomView2.mResumeButton.setVisible(false);
                            Context context3 = wifiApClientSettingsBottomView2.mContext;
                            String str3 = wifiApClientSettingsBottomView2.mMacAddress;
                            Log.i("WifiApConnectedDeviceUtils", "setWifiApClientDataPaused: false");
                            WifiApFrameworkUtils.getSemWifiManager(context3)
                                    .setWifiApClientDataPaused(str3, false);
                        }
                        wifiApClientSettingsBottomView2.mWifiApClientSettings
                                .refreshConnectedPreferences();
                        return true;
                    }
                };
        wifiApClientSettingsBottomView.mButtonBar.setVisibility(0);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        Bundle arguments = getArguments();
        if (arguments != null) {
            if (arguments.containsKey("intent_key_connected_client_mac_detail")) {
                this.mDeviceName = arguments.getString("intent_key_connected_client_name_detail");
            }
            if (arguments.containsKey("intent_key_connected_client_mac_detail")) {
                this.mMacAddress = arguments.getString("intent_key_connected_client_mac_detail");
            }
        } else {
            SemLog.e("WifiApClientSettings", "MAC details not found");
        }
        ((WifiApClientSetDataLimitPreferenceController)
                        use(WifiApClientSetDataLimitPreferenceController.class))
                .setMacDetail(this.mMacAddress);
        WifiApClientSetDataLimitPreferenceController wifiApClientSetDataLimitPreferenceController =
                (WifiApClientSetDataLimitPreferenceController)
                        use(WifiApClientSetDataLimitPreferenceController.class);
        this.mSetDataLimitPreferenceController = wifiApClientSetDataLimitPreferenceController;
        wifiApClientSetDataLimitPreferenceController.setHost(this);
        ((WifiApClientSetTimeLimitPreferenceController)
                        use(WifiApClientSetTimeLimitPreferenceController.class))
                .setMacDetail(this.mMacAddress);
        this.mSetTimeLimitPreferenceController =
                (WifiApClientSetTimeLimitPreferenceController)
                        use(WifiApClientSetTimeLimitPreferenceController.class);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext();
        this.mPreferenceScreen = getPreferenceScreen();
        this.mSemWifiManager =
                (SemWifiManager) this.mContext.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        SemLog.i(
                "WifiApClientSettings",
                "mDeviceName/mMacAddress "
                        + this.mDeviceName
                        + "/"
                        + WifiApSettingsUtils.hideSecondHalfOfString(this.mMacAddress));
        this.mSemWifiApClientDetails =
                WifiApConnectedDeviceUtils.getWifiApClientDetails(this.mContext, this.mMacAddress);
        this.mIsOtpClient =
                WifiApFrameworkUtils.isOtpConnectedClient(this.mContext, this.mMacAddress);
        SemWifiApClientDetails semWifiApClientDetails = this.mSemWifiApClientDetails;
        if (semWifiApClientDetails != null) {
            this.mConnectedTime =
                    Long.valueOf(semWifiApClientDetails.getClientRecentConnectionTimeStamp());
            this.mConnectedDurationInMillis =
                    Long.valueOf(this.mSemWifiApClientDetails.getClientActiveSessionDuration());
            this.mIp = this.mSemWifiApClientDetails.getClientIpAddress();
            this.mSemWifiApClientDetails.getDeviceType();
            this.mDeviceName = this.mSemWifiApClientDetails.getClientDeviceName();
        }
        getActivity().setTitle(this.mDeviceName);
        this.mClientDataUsagePreference =
                (WifiApPreference)
                        this.mPreferenceScreen.findPreference("client_data_usage_preference");
        this.mClientDataUsageProgressBar =
                (WifiApStackedProgressBarPreference)
                        this.mPreferenceScreen.findPreference("progress_bar_preference");
        WifiApPreference wifiApPreference = this.mClientDataUsagePreference;
        wifiApPreference.mPreferenceType = 1;
        wifiApPreference.notifyChanged();
        WifiApPreference wifiApPreference2 = this.mClientDataUsagePreference;
        wifiApPreference2.getClass();
        Log.i("WifiApPreference", "setTextSizeInSp() - : 16.0");
        wifiApPreference2.mTextSizeInSp = 16.0f;
        wifiApPreference2.notifyChanged();
        this.mClientDataUsagePreference.setTitleTextColor(
                this.mContext.getColor(R.color.wifi_ap_sub_text_color_2));
        this.mClientDataUsagePreference.setPreferenceColor(3);
        WifiApStackedProgressBarPreference wifiApStackedProgressBarPreference =
                this.mClientDataUsageProgressBar;
        wifiApStackedProgressBarPreference.mIsNonWhiteBackgroundRequired = true;
        wifiApStackedProgressBarPreference.notifyChanged();
        if (SystemProperties.getInt("ro.build.version.oneui", 0) < 60100 || !this.mIsOtpClient) {
            return;
        }
        WifiApPreference wifiApPreference3 = this.mClientDataUsagePreference;
        Drawable drawable =
                wifiApPreference3.mContext.getDrawable(R.drawable.sec_wifi_ap_guest_icon);
        if (drawable != null && wifiApPreference3.mSummaryIcon4 != drawable) {
            wifiApPreference3.mSummaryIcon4 = drawable;
            wifiApPreference3.notifyChanged();
        }
        WifiApPreference wifiApPreference4 = this.mClientDataUsagePreference;
        String string =
                wifiApPreference4.mContext.getString(R.string.wifi_ap_one_time_password_string);
        if (TextUtils.equals(wifiApPreference4.mSummary4Text, string)) {
            return;
        }
        wifiApPreference4.mSummary4Text = string;
        wifiApPreference4.notifyChanged();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        FragmentActivity activity = getActivity();
        if ((activity instanceof AppCompatActivity
                        ? ((AppCompatActivity) activity).getSupportActionBar()
                        : null)
                != null) {
            if (SystemProperties.getInt("ro.build.version.oneui", 0) >= 60100) {
                MenuItem add = menu.add(0, 1, 0, getString(R.string.common_edit));
                add.setShowAsAction(2);
                add.setIcon(R.drawable.sec_wifi_ap_edit_icon);
                add.setEnabled(true);
            }
            MenuItem add2 = menu.add(0, 2, 0, R.string.launch_wifi_text);
            add2.setShowAsAction(2);
            add2.setIcon(R.drawable.sec_ic_wifi_ap_info);
            add2.setEnabled(true);
        }
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 1) {
            SemLog.i("WifiApClientSettings", "Edit info Menu Button clicked");
            Context context = this.mContext;
            DialogInterface.OnClickListener onClickListener =
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.wifi.mobileap.clients.WifiApClientSettings.3
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            if (i == -1) {
                                String editable =
                                        WifiApClientSettings.this
                                                .mEditDeviceNameDialog
                                                .mEditText1
                                                .getText()
                                                .toString();
                                WifiApClientSettings wifiApClientSettings =
                                        WifiApClientSettings.this;
                                Context context2 = wifiApClientSettings.mContext;
                                WifiApFrameworkUtils.getSemWifiManager(context2)
                                        .setWifiApClientEditedName(
                                                wifiApClientSettings.mMacAddress, editable);
                            }
                        }
                    };
            String string = context.getString(R.string.wifi_ap_edit_device_name);
            String string2 = context.getString(R.string.save_button);
            String string3 = context.getString(R.string.cancel);
            WifiApDialog wifiApDialog = new WifiApDialog(context, 0);
            wifiApDialog.mContext = context;
            wifiApDialog.mTitle = string;
            wifiApDialog.mPositiveButtonText = string2;
            wifiApDialog.mNegativeButtonText = string3;
            wifiApDialog.mDialogButtonClickListener = onClickListener;
            this.mEditDeviceNameDialog = wifiApDialog;
            wifiApDialog.setOnShowListener(
                    new DialogInterface
                            .OnShowListener() { // from class:
                                                // com.samsung.android.settings.wifi.mobileap.clients.WifiApClientSettings.4
                        @Override // android.content.DialogInterface.OnShowListener
                        public final void onShow(DialogInterface dialogInterface) {
                            WifiApClientSettings wifiApClientSettings = WifiApClientSettings.this;
                            wifiApClientSettings.mEditDeviceNameDialog.setMessage(
                                    wifiApClientSettings.getString(R.string.wifi_ap_device_name));
                            WifiApClientSettings wifiApClientSettings2 = WifiApClientSettings.this;
                            wifiApClientSettings2.mEditDeviceNameDialog.setEditText(
                                    wifiApClientSettings2.mDeviceName);
                            WifiApDialog wifiApDialog2 =
                                    WifiApClientSettings.this.mEditDeviceNameDialog;
                            wifiApDialog2.mEditText1.addTextChangedListener(
                                    new TextWatcher() { // from class:
                                                        // com.samsung.android.settings.wifi.mobileap.clients.WifiApClientSettings.4.1
                                        @Override // android.text.TextWatcher
                                        public final void afterTextChanged(Editable editable) {
                                            WifiApClientSettings wifiApClientSettings3 =
                                                    WifiApClientSettings.this;
                                            wifiApClientSettings3.mSSID =
                                                    wifiApClientSettings3
                                                            .mEditDeviceNameDialog
                                                            .mEditText1
                                                            .getText()
                                                            .toString();
                                            int length =
                                                    WifiApClientSettings.this.mSSID.getBytes()
                                                            .length;
                                            WifiApClientSettings wifiApClientSettings4 =
                                                    WifiApClientSettings.this;
                                            int i = wifiApClientSettings4.mSSIDLen;
                                            if ((i < 1 && length >= 1)
                                                    || ((i >= 1 && length < 1)
                                                            || wifiApClientSettings4.mSSID.equals(
                                                                    wifiApClientSettings4
                                                                            .mErrSSID))) {
                                                IntentFilter intentFilter =
                                                        WifiApClientSettings.INTENT_FILTER;
                                                SemLog.i(
                                                        "WifiApClientSettings",
                                                        "mSSID:" + WifiApClientSettings.this.mSSID);
                                            }
                                            WifiApClientSettings.this.mSSIDLen = length;
                                        }

                                        @Override // android.text.TextWatcher
                                        public final void beforeTextChanged(
                                                CharSequence charSequence, int i, int i2, int i3) {
                                            if (charSequence == null
                                                    || charSequence.toString().getBytes().length
                                                            > 32) {
                                                return;
                                            }
                                            WifiApClientSettings.this.mTempSsid =
                                                    charSequence.toString();
                                            WifiApClientSettings.this.mSSIDLen =
                                                    charSequence.toString().getBytes().length;
                                        }

                                        @Override // android.text.TextWatcher
                                        public final void onTextChanged(
                                                CharSequence charSequence, int i, int i2, int i3) {
                                            if (charSequence.toString().getBytes().length > 32) {
                                                String str = WifiApClientSettings.this.mTempSsid;
                                                if (str == null || str.getBytes().length > 32) {
                                                    WifiApClientSettings.this.mEditDeviceNameDialog
                                                            .setEditText(ApnSettings.MVNO_NONE);
                                                } else if (charSequence.toString().length()
                                                                - WifiApClientSettings.this
                                                                        .mTempSsid.length()
                                                        > 1) {
                                                    String charSequence2 = charSequence.toString();
                                                    if (charSequence2.getBytes().length
                                                            > charSequence2.length()) {
                                                        int i4 = 0;
                                                        int i5 = 0;
                                                        int i6 = 0;
                                                        while (i4 <= 32) {
                                                            i6 =
                                                                    Character.charCount(
                                                                            charSequence2
                                                                                    .codePointAt(
                                                                                            i5));
                                                            int i7 = i5 + i6;
                                                            i4 +=
                                                                    charSequence2
                                                                            .substring(i5, i7)
                                                                            .getBytes()
                                                                            .length;
                                                            i5 = i7;
                                                        }
                                                        int i8 = i5 - i6;
                                                        int i9 = i8 - 1;
                                                        char charAt = charSequence2.charAt(i9);
                                                        if (charAt == 9770) {
                                                            WifiApClientSettings.this
                                                                    .mEditDeviceNameDialog
                                                                    .setEditText(
                                                                            charSequence2.substring(
                                                                                    0, i9));
                                                        } else if (charAt != 10013) {
                                                            WifiApClientSettings.this
                                                                    .mEditDeviceNameDialog
                                                                    .setEditText(
                                                                            charSequence2.substring(
                                                                                    0, i8));
                                                        } else {
                                                            WifiApClientSettings.this
                                                                    .mEditDeviceNameDialog
                                                                    .setEditText(
                                                                            charSequence2.substring(
                                                                                    0, i9));
                                                        }
                                                    } else {
                                                        WifiApClientSettings.this
                                                                .mEditDeviceNameDialog.setEditText(
                                                                charSequence2.substring(0, 32));
                                                    }
                                                } else {
                                                    WifiApClientSettings wifiApClientSettings3 =
                                                            WifiApClientSettings.this;
                                                    wifiApClientSettings3.mEditDeviceNameDialog
                                                            .setEditText(
                                                                    wifiApClientSettings3
                                                                            .mTempSsid);
                                                }
                                                WifiApDialog wifiApDialog3 =
                                                        WifiApClientSettings.this
                                                                .mEditDeviceNameDialog;
                                                wifiApDialog3.setEditTextErrorText(
                                                        wifiApDialog3.mContext.getString(
                                                                R.string.wifi_ssid_max_byte_error));
                                            } else if (charSequence.toString().getBytes().length
                                                    < 32) {
                                                WifiApClientSettings.this.mEditDeviceNameDialog
                                                        .setEditTextErrorText(null);
                                            }
                                            WifiApDialog wifiApDialog4 =
                                                    WifiApClientSettings.this.mEditDeviceNameDialog;
                                            boolean z = !TextUtils.isEmpty(charSequence);
                                            Button button = wifiApDialog4.mPositiveButton;
                                            if (button != null) {
                                                button.setEnabled(z);
                                            }
                                        }
                                    });
                        }
                    });
            this.mEditDeviceNameDialog.setOnDismissListener(
                    new DialogInterface
                            .OnDismissListener() { // from class:
                                                   // com.samsung.android.settings.wifi.mobileap.clients.WifiApClientSettings.5
                        @Override // android.content.DialogInterface.OnDismissListener
                        public final void onDismiss(DialogInterface dialogInterface) {
                            WifiApClientSettings wifiApClientSettings = WifiApClientSettings.this;
                            SemWifiApClientDetails wifiApClientDetails =
                                    WifiApConnectedDeviceUtils.getWifiApClientDetails(
                                            wifiApClientSettings.mContext,
                                            wifiApClientSettings.mMacAddress);
                            wifiApClientSettings.mSemWifiApClientDetails = wifiApClientDetails;
                            if (wifiApClientDetails != null) {
                                wifiApClientSettings.mConnectedTime =
                                        Long.valueOf(
                                                wifiApClientDetails
                                                        .getClientRecentConnectionTimeStamp());
                                wifiApClientSettings.mConnectedDurationInMillis =
                                        Long.valueOf(
                                                wifiApClientSettings.mSemWifiApClientDetails
                                                        .getClientActiveSessionDuration());
                                wifiApClientSettings.mIp =
                                        wifiApClientSettings.mSemWifiApClientDetails
                                                .getClientIpAddress();
                                wifiApClientSettings.mSemWifiApClientDetails.getDeviceType();
                                wifiApClientSettings.mDeviceName =
                                        wifiApClientSettings.mSemWifiApClientDetails
                                                .getClientDeviceName();
                            }
                            wifiApClientSettings
                                    .getActivity()
                                    .setTitle(wifiApClientSettings.mDeviceName);
                        }
                    });
            this.mEditDeviceNameDialog.show();
            SALogging.insertSALog("TETH_014", "8096");
            return true;
        }
        if (itemId != 2) {
            if (itemId != 16908332) {
                return super.onOptionsItemSelected(menuItem);
            }
            SemLog.i("WifiApClientSettings", "Navigate Up clicked");
            SALogging.insertSALog("TETH_014", "8101");
            return super.onOptionsItemSelected(menuItem);
        }
        SemLog.i("WifiApClientSettings", "More info Menu Button clicked");
        SALogging.insertSALog("TETH_014", "8082");
        Context context2 = this.mContext;
        boolean z = WifiApSettingsUtils.DBG;
        AlertDialog.Builder builder = new AlertDialog.Builder(context2, 0);
        View inflate =
                builder.create()
                        .getLayoutInflater()
                        .inflate(R.layout.sec_wifi_ap_deviceinfo_dialog, (ViewGroup) null);
        if (Utils.isRTL(this.mContext)) {
            ((TextView) inflate.findViewById(R.id.tv_ip_addr_text))
                    .setText(this.mContext.getString(R.string.wifi_ip_address) + "\u200f");
            ((TextView) inflate.findViewById(R.id.wifi_ap_ip)).setText("\u200e" + this.mIp);
            ((TextView) inflate.findViewById(R.id.tv_mac_addr_text))
                    .setText(this.mContext.getString(R.string.wifi_ap_mac) + "\u200f");
            ((TextView) inflate.findViewById(R.id.wifi_ap_mac))
                    .setText("\u200e" + this.mMacAddress.toUpperCase());
        } else {
            ((TextView) inflate.findViewById(R.id.tv_ip_addr_text))
                    .setText(this.mContext.getString(R.string.wifi_ip_address));
            ((TextView) inflate.findViewById(R.id.wifi_ap_ip)).setText(this.mIp);
            ((TextView) inflate.findViewById(R.id.tv_mac_addr_text))
                    .setText(this.mContext.getString(R.string.wifi_ap_mac));
            ((TextView) inflate.findViewById(R.id.wifi_ap_mac))
                    .setText(this.mMacAddress.toUpperCase());
        }
        String format =
                DateFormat.getTimeFormat(this.mContext)
                        .format(new Date(this.mConnectedTime.longValue()));
        this.dateFormat =
                DateFormat.getDateFormat(this.mContext)
                        .format(new Date(this.mConnectedTime.longValue()));
        String m =
                BackStackRecord$$ExternalSyntheticOutline0.m(
                        new StringBuilder(), this.dateFormat, " ", format);
        ((TextView) inflate.findViewById(R.id.wifi_ap_connection_time)).setText(m);
        if (Utils.isRTL(this.mContext)) {
            ((TextView) inflate.findViewById(R.id.tv_ip_addr_text)).setGravity(5);
            ((TextView) inflate.findViewById(R.id.tv_mac_addr_text)).setGravity(5);
            ((TextView) inflate.findViewById(R.id.wifi_ap_connection_time)).setText(m);
            ((TextView) inflate.findViewById(R.id.wifi_ap_connection_time)).setGravity(5);
        }
        TextView textView = (TextView) inflate.findViewById(R.id.wifi_ap_connection_duration);
        long longValue = this.mConnectedDurationInMillis.longValue() / 1000;
        long j = longValue / 3600;
        long j2 = longValue % 3600;
        textView.setText(
                ApnSettings.MVNO_NONE
                        + String.format("%02d", Long.valueOf(j))
                        + ":"
                        + String.format("%02d", Long.valueOf(j2 / 60))
                        + ":"
                        + String.format("%02d", Long.valueOf(j2 % 60)));
        builder.setTitle(this.mDeviceName);
        builder.setView(inflate);
        builder.setPositiveButton(R.string.dlg_ok, new AnonymousClass6()).show();
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        WifiApFrameworkUtils.setIsHotspotScreenOpened(this.mContext, false);
        this.mSemWifiManager.unregisterClientDataUsageCallback(this.mSemWifiApClientUpdateCallback);
        super.onPause();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        WifiApFrameworkUtils.setIsHotspotScreenOpened(this.mContext, true);
        refreshConnectedPreferences();
        this.mSemWifiManager.registerClientDataUsageCallback(
                this.mSemWifiApClientUpdateCallback,
                this.mContext.getMainExecutor(),
                this.mMacAddress);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        this.mContext.registerReceiver(this.mBroadcastReceiver, INTENT_FILTER, 2);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        this.mContext.unregisterReceiver(this.mBroadcastReceiver);
        super.onStop();
    }

    public final void refreshConnectedPreferences() {
        ArrayList arrayList = new ArrayList();
        WifiApDataUsageConfig wifiApClientMobileDataConsumedDetail =
                WifiApConnectedDeviceUtils.getWifiApClientMobileDataConsumedDetail(
                        this.mContext, this.mMacAddress);
        WifiApDataUsageConfig wifiApClientDataLimitDetail =
                WifiApConnectedDeviceUtils.getWifiApClientDataLimitDetail(
                        this.mContext, this.mMacAddress);
        float usageValueInMB = (float) wifiApClientMobileDataConsumedDetail.getUsageValueInMB();
        int i = WifiApStackedProgressBarPreference.PROGRESS_COLORS[1];
        arrayList.add(new StackedProgressBarEntry("mDeviceName", usageValueInMB));
        String usageValueInLocaleString =
                wifiApClientMobileDataConsumedDetail.getUsageValueInLocaleString(this.mContext);
        String usageValueInLocaleString2 =
                wifiApClientDataLimitDetail.getUsageValueInLocaleString(this.mContext);
        StringBuffer stringBuffer = new StringBuffer(usageValueInLocaleString);
        if (wifiApClientDataLimitDetail.mUsageValueInBytes > 0.0d) {
            stringBuffer.append("/");
            stringBuffer.append(usageValueInLocaleString2);
            arrayList.add(
                    new StackedProgressBarEntry(
                            "unused_data_name",
                            (float)
                                    (wifiApClientDataLimitDetail.getUsageValueInMB()
                                            - wifiApClientMobileDataConsumedDetail
                                                    .getUsageValueInMB())));
        }
        SpannableString spannableString = new SpannableString(stringBuffer.toString());
        int i2 = 0;
        spannableString.setSpan(
                new RelativeSizeSpan(1.5f), 0, usageValueInLocaleString.length(), 33);
        spannableString.setSpan(new StyleSpan(1), 0, usageValueInLocaleString.length(), 33);
        spannableString.setSpan(
                new RelativeSizeSpan(0.8f),
                usageValueInLocaleString.length(),
                spannableString.length(),
                33);
        this.mClientDataUsagePreference.setSpannableSummary(spannableString);
        WifiApStackedProgressBarPreference wifiApStackedProgressBarPreference =
                this.mClientDataUsageProgressBar;
        wifiApStackedProgressBarPreference.mStackedDataTotalSum = 0.0f;
        Log.i(WifiApStackedProgressBarPreference.TAG, "entry iteration start");
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            StackedProgressBarEntry stackedProgressBarEntry = (StackedProgressBarEntry) it.next();
            Log.i(
                    WifiApStackedProgressBarPreference.TAG,
                    "entry: " + stackedProgressBarEntry.mProgressEntryValue);
            float f = wifiApStackedProgressBarPreference.mStackedDataTotalSum;
            float f2 = stackedProgressBarEntry.mProgressEntryValue;
            wifiApStackedProgressBarPreference.mStackedDataTotalSum = f + f2;
            if (!TextUtils.equals(stackedProgressBarEntry.mProgressEntryName, "unused_data_name")) {
                if (i2 < 20) {
                    wifiApStackedProgressBarPreference.mStackedDataArray[i2] = f2;
                    i2++;
                } else {
                    float[] fArr = wifiApStackedProgressBarPreference.mStackedDataArray;
                    fArr[i2] = fArr[i2] + f2;
                }
            }
        }
        Log.i(WifiApStackedProgressBarPreference.TAG, "entry iteration end");
        wifiApStackedProgressBarPreference.notifyChanged();
        WifiApStackedProgressBarPreference wifiApStackedProgressBarPreference2 =
                this.mClientDataUsageProgressBar;
        wifiApStackedProgressBarPreference2.mIsDisabledProgressBar =
                WifiApConnectedDeviceUtils.getWifiApClientDetails(this.mContext, this.mMacAddress)
                        .isClientDataPausedByUser();
        wifiApStackedProgressBarPreference2.notifyChanged();
        this.mClientDataUsageProgressBar.notifyChanged();
        this.mSetDataLimitPreferenceController.updateState(getPreferenceScreen());
        this.mSetTimeLimitPreferenceController.updateState(getPreferenceScreen());
    }
}

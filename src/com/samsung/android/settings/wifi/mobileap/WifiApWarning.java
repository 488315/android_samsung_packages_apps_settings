package com.samsung.android.settings.wifi.mobileap;

import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.StatusBarManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.display.DisplayManager;
import android.hardware.display.SemWifiDisplayStatus;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.SoftApConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.apppickerview.features.applabel.AbstractAppLabelMapFactory$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;

import com.android.internal.app.AlertActivity;
import com.android.settings.MainClear$$ExternalSyntheticOutline0;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.fuelgauge.datasaver.DynamicDenylistManager$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.WifiApUtils;
import com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.intelligent.AutoConnectHotspotSettings;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApDataUsageConfig;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApConnectedDeviceUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSoftApUtils;
import com.samsung.android.wifi.SemWifiApBleScanResult;
import com.samsung.android.wifi.SemWifiApContentProviderHelper;
import com.samsung.android.wifi.SemWifiApCust;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifi.p2p.SemWifiP2pManager;
import com.samsung.android.wifitrackerlib.WifiIssueDetectorUtil;
import com.sec.ims.IMSParameter;
import com.sec.ims.extensions.WiFiManagerExt;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApWarning extends AlertActivity implements DialogInterface.OnClickListener {
    public static final boolean DBG = Utils.MHSDBG;
    public AlertDialog allowAutohotspotDiscoveryDialogBox;
    public AlertDialog autoHotspotConnectionDialogBox;
    public AlertDialog autoHotspotDisconnectedDialogBox;
    public int autohotspot_security;
    public int extra_type;
    public boolean isregisterFinishReceiver;
    public WifiManager mAOSPWifiManager;
    public int mBand;
    public boolean mChangeSecurity;
    public Context mContext;
    public CountDownTimer mCountdownTimer;
    public Button mDataLimitChangeLimitButton;
    public AlertDialog mDataLimitDialog;
    public TextView mDataLimitEditErrorTextView;
    public EditText mDataLimitEditText;
    public int mDialogTheme;
    public int mDialogType;
    public IntentFilter mIntentFilter;
    public final AnonymousClass4 mIntentReceiver;
    public AlertDialog mNaiMismatchDialog;
    public PowerManager.WakeLock mPartialWakeLock;
    public String[] mProvisionApp;
    public PowerManager.WakeLock mScreenWakeLock;
    public SemWifiManager mSemWifiManager;
    public SharedPreferences mSharedPref;
    public PowerManager.WakeLock mWakeLock;
    public final AnonymousClass4 mWifiApWarningRecv;
    public String m_ST_D2D_Devicename;
    public String m_ST_D2D_FamilyID;
    public long m_ST_D2D_FamilyIDHash;
    public String m_ST_D2D_WiFiMAC;
    public String m_ST_SSId;
    public int req_type;
    public boolean msendIntentToAutoHotspotD2DFamilyUpdate = false;
    public boolean waitingForConfigurationSet = false;
    public SemWifiApBleScanResult bleAp = null;
    public boolean isProvisionBRRegistered = false;
    public boolean isIntentFilterBRRegistered = false;
    public final AnonymousClass69 mReceiver = new AnonymousClass69();
    public final AnonymousClass81 mDataLimitEditTextWatcher =
            new TextWatcher() { // from class:
                                // com.samsung.android.settings.wifi.mobileap.WifiApWarning.81
                @Override // android.text.TextWatcher
                public final void afterTextChanged(Editable editable) {
                    WifiApWarning wifiApWarning = WifiApWarning.this;
                    boolean z = WifiApWarning.DBG;
                    wifiApWarning.buttonValidate();
                }

                @Override // android.text.TextWatcher
                public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    String charSequence2 = charSequence.toString();
                    WifiApWarning wifiApWarning = WifiApWarning.this;
                    if (wifiApWarning.mDataLimitEditErrorTextView == null
                            || wifiApWarning.mDataLimitEditText == null) {
                        return;
                    }
                    if (charSequence2.length() >= 10) {
                        WifiApWarning.this.mDataLimitEditErrorTextView.setVisibility(0);
                        WifiApWarning wifiApWarning2 = WifiApWarning.this;
                        SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0.m(
                                wifiApWarning2.mContext,
                                R.color.sec_wifi_dialog_error_color,
                                wifiApWarning2.mDataLimitEditText);
                        return;
                    }
                    WifiApWarning.this.mDataLimitEditErrorTextView.setVisibility(8);
                    WifiApWarning wifiApWarning3 = WifiApWarning.this;
                    SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0.m(
                            wifiApWarning3.mContext,
                            R.color.sec_wifi_ap_edit_text_background_color,
                            wifiApWarning3.mDataLimitEditText);
                }

                @Override // android.text.TextWatcher
                public final void beforeTextChanged(
                        CharSequence charSequence, int i, int i2, int i3) {}
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.WifiApWarning$1, reason: invalid class name */
    public final class AnonymousClass1 implements DialogInterface.OnClickListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WifiApWarning this$0;

        public /* synthetic */ AnonymousClass1(WifiApWarning wifiApWarning, int i) {
            this.$r8$classId = i;
            this.this$0 = wifiApWarning;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            int networkId;
            switch (this.$r8$classId) {
                case 0:
                    AlertDialog alertDialog = this.this$0.mNaiMismatchDialog;
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }
                    this.this$0.finish();
                    break;
                case 1:
                    WifiApWarning wifiApWarning = this.this$0;
                    boolean z = WifiApWarning.DBG;
                    wifiApWarning.sendBroadcastEnablingHotspotCancel();
                    this.this$0.finish();
                    break;
                case 2:
                    WifiApWarning wifiApWarning2 = this.this$0;
                    boolean z2 = WifiApWarning.DBG;
                    wifiApWarning2.checkFirstTimeWiFiSharingDialogAndProceedFurthur();
                    break;
                case 3:
                    WifiApWarning wifiApWarning3 = this.this$0;
                    boolean z3 = WifiApWarning.DBG;
                    wifiApWarning3.checkFirstTimeConfigureAndProceedFurther();
                    break;
                case 4:
                    WifiApWarning wifiApWarning4 = this.this$0;
                    boolean z4 = WifiApWarning.DBG;
                    wifiApWarning4.sendBroadcastEnablingHotspotCancel();
                    this.this$0.finish();
                    break;
                case 5:
                    WifiInfo connectionInfo = this.this$0.mAOSPWifiManager.getConnectionInfo();
                    if (connectionInfo != null
                            && (networkId = connectionInfo.getNetworkId()) != -1) {
                        Log.d("WifiApWarning", "disconneting because of low battery hotspot live");
                        Context context = this.this$0.mContext;
                        ((SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE))
                                .reportIssue(
                                        102,
                                        WifiIssueDetectorUtil.ReportUtil
                                                .getReportDataForWifiManagerApi(
                                                        networkId,
                                                        "removeNetwork",
                                                        context.getPackageManager()
                                                                .getNameForUid(context.getUserId()),
                                                        context.getPackageName()));
                        this.this$0.mAOSPWifiManager.removeNetwork(networkId);
                    }
                    this.this$0.finish();
                    break;
                case 6:
                    this.this$0.finish();
                    break;
                case 7:
                    String str =
                            SemWifiApContentProviderHelper.get(
                                    this.this$0.mContext, "smart_tethering_d2dfamilyid");
                    if (str == null || !str.equals(this.this$0.m_ST_D2D_FamilyID)) {
                        SemWifiApContentProviderHelper.insert(
                                this.this$0.mContext, "smart_tethering_d2d_Wifimac", (String) null);
                    }
                    WifiApWarning wifiApWarning5 = this.this$0;
                    SemWifiApContentProviderHelper.insert(
                            wifiApWarning5.mContext,
                            "smart_tethering_d2dfamilyid",
                            wifiApWarning5.m_ST_D2D_FamilyID);
                    SemWifiApContentProviderHelper.insert(
                            this.this$0.mContext,
                            "hash_value_based_on_d2dFamilyid",
                            ApnSettings.MVNO_NONE + this.this$0.m_ST_D2D_FamilyIDHash);
                    String str2 =
                            SemWifiApContentProviderHelper.get(
                                    this.this$0.mContext, "smart_tethering_d2d_Wifimac");
                    try {
                        WifiApWarning wifiApWarning6 = this.this$0;
                        wifiApWarning6.m_ST_D2D_WiFiMAC =
                                wifiApWarning6.m_ST_D2D_WiFiMAC.substring(9);
                    } catch (StringIndexOutOfBoundsException unused) {
                        MainClear$$ExternalSyntheticOutline0.m(
                                new StringBuilder(
                                        "java.lang.StringIndexOutOfBoundsException:m_ST_D2D_WiFiMAC:"),
                                this.this$0.m_ST_D2D_WiFiMAC,
                                "WifiApWarning");
                    }
                    if (str2 == null || str2.isEmpty()) {
                        WifiApWarning wifiApWarning7 = this.this$0;
                        SemWifiApContentProviderHelper.insert(
                                wifiApWarning7.mContext,
                                "smart_tethering_d2d_Wifimac",
                                wifiApWarning7.m_ST_D2D_WiFiMAC);
                    } else if (Arrays.asList(str2.split("\n"))
                            .contains(this.this$0.m_ST_D2D_WiFiMAC)) {
                        Log.d("WifiApWarning", "same D2D AutoHotspot MAC");
                    } else {
                        StringBuilder m =
                                PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                                        str2, "\n");
                        m.append(this.this$0.m_ST_D2D_WiFiMAC);
                        String sb = m.toString();
                        Log.d("WifiApWarning", "added D2D AutoHotspot MAC");
                        SemWifiApContentProviderHelper.insert(
                                this.this$0.mContext, "SMART_TETHERING_D2D_WIFIMAC", sb);
                    }
                    WifiApWarning.m1361$$Nest$msendIntentToAutoHotspotD2DFamilyUpdate(
                            this.this$0, true);
                    Intent intent = new Intent();
                    intent.setAction(
                            "com.samsung.android.server.wifi.softap.smarttethering.d2dfamilyid");
                    this.this$0.mContext.sendBroadcast(
                            intent, "android.permission.OVERRIDE_WIFI_CONFIG");
                    this.this$0.finish();
                    break;
                case 8:
                    WifiApWarning.m1361$$Nest$msendIntentToAutoHotspotD2DFamilyUpdate(
                            this.this$0, false);
                    this.this$0.finish();
                    break;
                case 9:
                    SALogging.insertSALog("TETH_010", "8055");
                    WifiApWarning wifiApWarning8 = this.this$0;
                    boolean z5 = WifiApWarning.DBG;
                    wifiApWarning8.sendBroadcastEnablingHotspotCancel();
                    this.this$0.finish();
                    break;
                case 10:
                    SALogging.insertSALog("TETH_010", "8054");
                    WifiApWarning wifiApWarning9 = this.this$0;
                    boolean z6 = WifiApWarning.DBG;
                    wifiApWarning9.getClass();
                    Log.i("WifiApWarning", "isJdmModel is false");
                    this.this$0.startProvisioningIfNecessary();
                    break;
                case 11:
                    SALogging.insertSALog("TETH_010", "8047");
                    WifiApWarning wifiApWarning10 = this.this$0;
                    boolean z7 = WifiApWarning.DBG;
                    wifiApWarning10.sendBroadcastEnablingHotspotCancel();
                    this.this$0.finish();
                    break;
                case 12:
                    AlertDialog alertDialog2 = this.this$0.mNaiMismatchDialog;
                    if (alertDialog2 != null) {
                        alertDialog2.dismiss();
                    }
                    if (this.this$0.mDialogType != 1) {
                        Intent intent2 = new Intent();
                        intent2.setFlags(268435456);
                        intent2.setAction("com.sprint.dsa.DSA_ACTIVITY");
                        intent2.setType("vnd.sprint.dsa/vnd.sprint.dsa.main");
                        intent2.putExtra("com.sprint.dsa.source", "hotspot");
                        try {
                            this.this$0.mContext.startActivity(intent2);
                        } catch (ActivityNotFoundException e) {
                            Log.i("WifiApWarning", "Error ActivityNotFoundException " + e);
                        }
                    }
                    this.this$0.finish();
                    break;
                case 13:
                    SALogging.insertSALog("TETH_010", "8046");
                    WifiApWarning wifiApWarning11 = this.this$0;
                    boolean z8 = WifiApWarning.DBG;
                    wifiApWarning11.startProvisioningIfNecessary();
                    break;
                case 14:
                    SALogging.insertSALog("TETH_010", "8059");
                    this.this$0.dismiss();
                    break;
                case 15:
                    SALogging.insertSALog("TETH_010", "8058");
                    this.this$0.mAOSPWifiManager.setWifiEnabled(false);
                    Settings.Secure.putInt(
                            this.this$0.mContext.getContentResolver(), "wifi_saved_state", 1);
                    new Handler().postDelayed(new WifiApWarning$33$1(this), 600L);
                    break;
                case 16:
                    SALogging.insertSALog("TETH_010", "8049");
                    this.this$0.dismiss();
                    break;
                case 17:
                    SALogging.insertSALog("TETH_010", "8048");
                    ((DisplayManager) this.this$0.mContext.getSystemService("display"))
                            .semDisconnectWifiDisplay();
                    new Handler().postDelayed(new WifiApWarning$33$1(this, (byte) 0), 600L);
                    break;
                case 18:
                    int wifiState = this.this$0.mAOSPWifiManager.getWifiState();
                    int wifiApState = this.this$0.mAOSPWifiManager.getWifiApState();
                    if (wifiState == 1 && wifiApState == 13) {
                        this.this$0.finish();
                    }
                    SALogging.insertSALog("TETH_010", "8056");
                    if (wifiState == 2 || wifiState == 3) {
                        this.this$0.mAOSPWifiManager.setWifiEnabled(false);
                        Settings.Secure.putInt(
                                this.this$0.mContext.getContentResolver(), "wifi_saved_state", 1);
                        new Handler().postDelayed(new WifiApWarning$33$1(this, (char) 0), 600L);
                        break;
                    }
                    break;
                case 19:
                    SALogging.insertSALog("TETH_010", "8057");
                    WifiApWarning wifiApWarning12 = this.this$0;
                    boolean z9 = WifiApWarning.DBG;
                    wifiApWarning12.sendBroadcastEnablingHotspotCancel();
                    this.this$0.finish();
                    break;
                case 20:
                    this.this$0.finish();
                    break;
                case 21:
                    SALogging.insertSALog(1L, "TETH_013", "8100");
                    CountDownTimer countDownTimer = this.this$0.mCountdownTimer;
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                        this.this$0.mCountdownTimer = null;
                    }
                    this.this$0.mSemWifiManager.isClientAcceptedWifiProfileSharing(true);
                    this.this$0.finish();
                    break;
                case 22:
                    CountDownTimer countDownTimer2 = this.this$0.mCountdownTimer;
                    if (countDownTimer2 != null) {
                        countDownTimer2.cancel();
                        this.this$0.mCountdownTimer = null;
                    }
                    SALogging.insertSALog(0L, "TETH_013", "8100");
                    this.this$0.mSemWifiManager.isClientAcceptedWifiProfileSharing(false);
                    this.this$0.finish();
                    break;
                case 23:
                    this.this$0.mSemWifiManager.requestStopAutohotspotAdvertisement(true);
                    this.this$0.finish();
                    break;
                case 24:
                    this.this$0.finish();
                    break;
                case 25:
                    this.this$0.finish();
                    break;
                case 26:
                    SALogging.insertSALog("TETH_010", "8051");
                    WifiApWarning wifiApWarning13 = this.this$0;
                    boolean z10 = WifiApWarning.DBG;
                    wifiApWarning13.startProvisioningIfNecessary();
                    break;
                case 27:
                    SALogging.insertSALog("TETH_010", "8050");
                    WifiApWarning wifiApWarning14 = this.this$0;
                    boolean z11 = WifiApWarning.DBG;
                    wifiApWarning14.sendBroadcastEnablingHotspotCancel();
                    this.this$0.finish();
                    break;
                case 28:
                    WifiApWarning wifiApWarning15 = this.this$0;
                    boolean z12 = WifiApWarning.DBG;
                    wifiApWarning15.sendBroadcastEnablingHotspotCancel();
                    this.this$0.finish();
                    break;
                default:
                    WifiApWarning wifiApWarning16 = this.this$0;
                    boolean z13 = WifiApWarning.DBG;
                    wifiApWarning16.checkFirstTimeConfigureAndProceedFurther();
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.WifiApWarning$3, reason: invalid class name */
    public final class AnonymousClass3 implements DialogInterface.OnCancelListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WifiApWarning this$0;

        public /* synthetic */ AnonymousClass3(WifiApWarning wifiApWarning, int i) {
            this.$r8$classId = i;
            this.this$0 = wifiApWarning;
        }

        @Override // android.content.DialogInterface.OnCancelListener
        public final void onCancel(DialogInterface dialogInterface) {
            switch (this.$r8$classId) {
                case 0:
                    AlertDialog alertDialog = this.this$0.mNaiMismatchDialog;
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }
                    this.this$0.finish();
                    break;
                case 1:
                    WifiApWarning wifiApWarning = this.this$0;
                    boolean z = WifiApWarning.DBG;
                    wifiApWarning.sendBroadcastEnablingHotspotCancel();
                    this.this$0.finish();
                    break;
                case 2:
                    WifiApWarning wifiApWarning2 = this.this$0;
                    boolean z2 = WifiApWarning.DBG;
                    wifiApWarning2.sendBroadcastEnablingHotspotCancel();
                    this.this$0.finish();
                    break;
                case 3:
                    WifiApWarning wifiApWarning3 = this.this$0;
                    boolean z3 = WifiApWarning.DBG;
                    wifiApWarning3.sendBroadcastEnablingHotspotCancel();
                    this.this$0.finish();
                    break;
                case 4:
                    this.this$0.finish();
                    break;
                case 5:
                    WifiApWarning.m1361$$Nest$msendIntentToAutoHotspotD2DFamilyUpdate(
                            this.this$0, false);
                    this.this$0.finish();
                    break;
                case 6:
                    WifiApWarning wifiApWarning4 = this.this$0;
                    boolean z4 = WifiApWarning.DBG;
                    wifiApWarning4.sendBroadcastEnablingHotspotCancel();
                    this.this$0.finish();
                    break;
                case 7:
                    WifiApWarning wifiApWarning5 = this.this$0;
                    boolean z5 = WifiApWarning.DBG;
                    wifiApWarning5.sendBroadcastEnablingHotspotCancel();
                    this.this$0.finish();
                    break;
                case 8:
                    this.this$0.dismiss();
                    break;
                case 9:
                    this.this$0.dismiss();
                    break;
                case 10:
                    WifiApWarning wifiApWarning6 = this.this$0;
                    boolean z6 = WifiApWarning.DBG;
                    wifiApWarning6.sendBroadcastEnablingHotspotCancel();
                    this.this$0.finish();
                    break;
                case 11:
                    this.this$0.finish();
                    break;
                case 12:
                    CountDownTimer countDownTimer = this.this$0.mCountdownTimer;
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                        this.this$0.mCountdownTimer = null;
                    }
                    this.this$0.mSemWifiManager.isClientAcceptedWifiProfileSharing(false);
                    this.this$0.finish();
                    break;
                case 13:
                    this.this$0.mSemWifiManager.requestStopAutohotspotAdvertisement(false);
                    this.this$0.finish();
                    break;
                case 14:
                    this.this$0.finish();
                    break;
                case 15:
                    this.this$0.finish();
                    break;
                case 16:
                    WifiApWarning wifiApWarning7 = this.this$0;
                    boolean z7 = WifiApWarning.DBG;
                    wifiApWarning7.sendBroadcastEnablingHotspotCancel();
                    this.this$0.finish();
                    break;
                case 17:
                    this.this$0.dismiss();
                    break;
                case 18:
                    WifiApWarning wifiApWarning8 = this.this$0;
                    boolean z8 = WifiApWarning.DBG;
                    wifiApWarning8.checkFirstTimeConfigureAndProceedFurther();
                    break;
                case 19:
                    this.this$0.finish();
                    break;
                case 20:
                    WifiApWarning wifiApWarning9 = this.this$0;
                    boolean z9 = WifiApWarning.DBG;
                    wifiApWarning9.sendBroadcastEnablingHotspotCancel();
                    this.this$0.finish();
                    break;
                case 21:
                    WifiApWarning wifiApWarning10 = this.this$0;
                    boolean z10 = WifiApWarning.DBG;
                    wifiApWarning10.sendBroadcastEnablingHotspotCancel();
                    this.this$0.finish();
                    break;
                case 22:
                    WifiApWarning wifiApWarning11 = this.this$0;
                    boolean z11 = WifiApWarning.DBG;
                    wifiApWarning11.sendBroadcastEnablingHotspotCancel();
                    this.this$0.finish();
                    break;
                case 23:
                    WifiApWarning wifiApWarning12 = this.this$0;
                    boolean z12 = WifiApWarning.DBG;
                    wifiApWarning12.sendBroadcastEnablingHotspotCancel();
                    this.this$0.finish();
                    break;
                default:
                    WifiApWarning wifiApWarning13 = this.this$0;
                    boolean z13 = WifiApWarning.DBG;
                    wifiApWarning13.sendBroadcastEnablingHotspotCancel();
                    this.this$0.finish();
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.WifiApWarning$40, reason: invalid class name */
    public final class AnonymousClass40 extends KeyguardManager.KeyguardDismissCallback {
        @Override // android.app.KeyguardManager.KeyguardDismissCallback
        public final void onDismissCancelled() {
            super.onDismissCancelled();
            Log.i("WifiApWarning", "onDismissCancelled: Dismiss cancelled");
        }

        @Override // android.app.KeyguardManager.KeyguardDismissCallback
        public final void onDismissError() {
            super.onDismissError();
            Log.e("WifiApWarning", "onDismissError: error");
        }

        @Override // android.app.KeyguardManager.KeyguardDismissCallback
        public final void onDismissSucceeded() {
            super.onDismissSucceeded();
            Log.i("WifiApWarning", "onDismissSucceeded: Dismiss success");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.WifiApWarning$47, reason: invalid class name */
    public final class AnonymousClass47 implements DialogInterface.OnClickListener {
        public final /* synthetic */ String val$mac_addr;
        public final /* synthetic */ String val$mhs_device;

        public AnonymousClass47(String str, String str2) {
            this.val$mac_addr = str;
            this.val$mhs_device = str2;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            String str = this.val$mac_addr;
            if (str == null || this.val$mhs_device == null) {
                Log.i("WifiApWarning", "Autohotspot values are null");
                WifiApWarning.this.finish();
                return;
            }
            WifiApWarning wifiApWarning = WifiApWarning.this;
            wifiApWarning.bleAp =
                    WifiApWarning.m1360$$Nest$mcheckIfBleScanResultValid(wifiApWarning, str);
            if (WifiApWarning.this.bleAp == null) {
                new Handler().postDelayed(new WifiApWarning$33$1(this), 3000L);
                return;
            }
            Log.i("WifiApWarning", "Autohotspot connect 2");
            WifiApWarning wifiApWarning2 = WifiApWarning.this;
            SemWifiManager semWifiManager = wifiApWarning2.mSemWifiManager;
            SemWifiApBleScanResult semWifiApBleScanResult = wifiApWarning2.bleAp;
            semWifiManager.connectToSmartMHS(
                    semWifiApBleScanResult.mDevice,
                    semWifiApBleScanResult.mMHSdeviceType,
                    semWifiApBleScanResult.mhidden,
                    semWifiApBleScanResult.mSecurity,
                    semWifiApBleScanResult.mWifiMac,
                    semWifiApBleScanResult.mUserName,
                    semWifiApBleScanResult.version,
                    semWifiApBleScanResult.isWifiProfileShareEnabled);
            WifiApWarning.this.finish();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.WifiApWarning$69, reason: invalid class name */
    public final class AnonymousClass69 extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i("WifiApWarning", "intent -: " + action);
            action.equals("com.samsung.unifiedtp.PROVISION_HOTSPOT_TRIGGER_ACTION");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.WifiApWarning$73, reason: invalid class name */
    public final class AnonymousClass73 extends SparseIntArray {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AutoHotspotConnectionClickable extends ClickableSpan {
        public final boolean focusOnDisconnectNotInUsePreference;
        public final AlertDialog mDialog;
        public final boolean shouldDismissDialogOnClick = false;
        public final boolean useSubSetting;

        public AutoHotspotConnectionClickable(AlertDialog alertDialog, boolean z, boolean z2) {
            this.mDialog = alertDialog;
            this.focusOnDisconnectNotInUsePreference = z;
            this.useSubSetting = z2;
        }

        @Override // android.text.style.ClickableSpan
        public final void onClick(View view) {
            if (this.useSubSetting) {
                Bundle m =
                        AbsAdapter$1$$ExternalSyntheticOutline0.m(
                                ":settings:fragment_args_key", "disconnect_when_not_in_use");
                SubSettingLauncher subSettingLauncher =
                        new SubSettingLauncher(WifiApWarning.this.mContext);
                String name = AutoConnectHotspotSettings.class.getName();
                SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
                launchRequest.mDestinationName = name;
                launchRequest.mSourceMetricsCategory = 0;
                if (this.focusOnDisconnectNotInUsePreference) {
                    launchRequest.mArguments = m;
                }
                Intent intent = subSettingLauncher.toIntent();
                intent.setFlags(268435456);
                WifiApWarning.this.mContext.startActivity(intent);
            } else {
                Intent intent2 = new Intent();
                intent2.setAction("android.setttings.WIFI_AUTO_HOTSPOT_CONNECTIONS_SETTINGS");
                intent2.setFlags(335544320);
                WifiApWarning.this.mContext.startActivity(intent2);
            }
            if (this.shouldDismissDialogOnClick) {
                this.mDialog.cancel();
            }
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public final void updateDrawState(TextPaint textPaint) {
            textPaint.linkColor = 0;
            textPaint.setUnderlineText(true);
        }
    }

    /* renamed from: -$$Nest$mcheckIfBleScanResultValid, reason: not valid java name */
    public static SemWifiApBleScanResult m1360$$Nest$mcheckIfBleScanResultValid(
            WifiApWarning wifiApWarning, String str) {
        List<SemWifiApBleScanResult> wifiApBleScanDetail =
                wifiApWarning.mSemWifiManager.getWifiApBleScanDetail();
        if (wifiApBleScanDetail == null) {
            Log.i("WifiApWarning", "Autohotspot checkIfBleScanResultValid null");
            return null;
        }
        for (SemWifiApBleScanResult semWifiApBleScanResult : wifiApBleScanDetail) {
            if (semWifiApBleScanResult.mWifiMac.equalsIgnoreCase(str)) {
                return semWifiApBleScanResult;
            }
        }
        return null;
    }

    /* renamed from: -$$Nest$msendIntentToAutoHotspotD2DFamilyUpdate, reason: not valid java name */
    public static void m1361$$Nest$msendIntentToAutoHotspotD2DFamilyUpdate(
            WifiApWarning wifiApWarning, boolean z) {
        if (wifiApWarning.msendIntentToAutoHotspotD2DFamilyUpdate) {
            wifiApWarning.msendIntentToAutoHotspotD2DFamilyUpdate = false;
            CountDownTimer countDownTimer = wifiApWarning.mCountdownTimer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
                wifiApWarning.mCountdownTimer = null;
            }
            wifiApWarning.getWindow().clearFlags(6815872);
            PowerManager.WakeLock wakeLock = wifiApWarning.mWakeLock;
            if (wakeLock != null && wakeLock.isHeld()) {
                wifiApWarning.mWakeLock.release();
            }
            PowerManager.WakeLock wakeLock2 = wifiApWarning.mPartialWakeLock;
            if (wakeLock2 != null && wakeLock2.isHeld()) {
                wifiApWarning.mPartialWakeLock.release();
            }
            PowerManager.WakeLock wakeLock3 = wifiApWarning.mScreenWakeLock;
            if (wakeLock3 != null && wakeLock3.isHeld()) {
                wifiApWarning.mScreenWakeLock.release();
            }
            Intent intent = new Intent();
            intent.setAction("com.samsung.android.server.wifi.softap.smarttethering.AcceptPopUp");
            intent.putExtra("accepted", z);
            wifiApWarning.mContext.sendBroadcast(intent, "android.permission.OVERRIDE_WIFI_CONFIG");
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.wifi.mobileap.WifiApWarning$4] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.samsung.android.settings.wifi.mobileap.WifiApWarning$4] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.samsung.android.settings.wifi.mobileap.WifiApWarning$81] */
    public WifiApWarning() {
        final int i = 0;
        this.mWifiApWarningRecv =
                new BroadcastReceiver(
                        this) { // from class:
                                // com.samsung.android.settings.wifi.mobileap.WifiApWarning.4
                    public final /* synthetic */ WifiApWarning this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        AlertDialog alertDialog;
                        switch (i) {
                            case 0:
                                Log.i("WifiApWarning", "onReceive , Finish RCV");
                                this.this$0.dismiss();
                                break;
                            default:
                                String action = intent.getAction();
                                Log.i("WifiApWarning", "intent -: " + action);
                                if (action.equals("android.net.wifi.STATE_CHANGE")) {
                                    NetworkInfo networkInfo =
                                            (NetworkInfo)
                                                    intent.getParcelableExtra(
                                                            IMSParameter.GENERAL.NETWORK_INFO);
                                    boolean z =
                                            networkInfo != null
                                                    && networkInfo.getDetailedState()
                                                            == NetworkInfo.DetailedState.CONNECTED;
                                    AbsAdapter$$ExternalSyntheticOutline0.m(
                                            "Wi-Fi isConnected -: ", "WifiApWarning", z);
                                    if (z
                                            && (alertDialog =
                                                            this.this$0
                                                                    .allowAutohotspotDiscoveryDialogBox)
                                                    != null
                                            && alertDialog.isShowing()) {
                                        this.this$0.allowAutohotspotDiscoveryDialogBox.dismiss();
                                        WifiApWarning wifiApWarning = this.this$0;
                                        wifiApWarning.allowAutohotspotDiscoveryDialogBox = null;
                                        wifiApWarning.dismiss();
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mIntentReceiver =
                new BroadcastReceiver(
                        this) { // from class:
                                // com.samsung.android.settings.wifi.mobileap.WifiApWarning.4
                    public final /* synthetic */ WifiApWarning this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        AlertDialog alertDialog;
                        switch (i2) {
                            case 0:
                                Log.i("WifiApWarning", "onReceive , Finish RCV");
                                this.this$0.dismiss();
                                break;
                            default:
                                String action = intent.getAction();
                                Log.i("WifiApWarning", "intent -: " + action);
                                if (action.equals("android.net.wifi.STATE_CHANGE")) {
                                    NetworkInfo networkInfo =
                                            (NetworkInfo)
                                                    intent.getParcelableExtra(
                                                            IMSParameter.GENERAL.NETWORK_INFO);
                                    boolean z =
                                            networkInfo != null
                                                    && networkInfo.getDetailedState()
                                                            == NetworkInfo.DetailedState.CONNECTED;
                                    AbsAdapter$$ExternalSyntheticOutline0.m(
                                            "Wi-Fi isConnected -: ", "WifiApWarning", z);
                                    if (z
                                            && (alertDialog =
                                                            this.this$0
                                                                    .allowAutohotspotDiscoveryDialogBox)
                                                    != null
                                            && alertDialog.isShowing()) {
                                        this.this$0.allowAutohotspotDiscoveryDialogBox.dismiss();
                                        WifiApWarning wifiApWarning = this.this$0;
                                        wifiApWarning.allowAutohotspotDiscoveryDialogBox = null;
                                        wifiApWarning.dismiss();
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
    }

    public final void CheckWiFiConcurrencyAndProceedFurther() {
        int wifiState = this.mAOSPWifiManager.getWifiState();
        boolean isP2pConnected =
                WifiApFrameworkUtils.getSemWifiManager(this.mContext).isP2pConnected();
        boolean isP2pSoftApConcurrencySupported =
                ((SemWifiP2pManager) this.mContext.getSystemService("sem_wifi_p2p"))
                        .isP2pSoftApConcurrencySupported();
        boolean isThisDualBand2GhzAnd5Ghz =
                WifiApSoftApUtils.getWifiApBandConfig(this.mContext).isThisDualBand2GhzAnd5Ghz();
        boolean z =
                isP2pConnected && (isThisDualBand2GhzAnd5Ghz || !isP2pSoftApConcurrencySupported);
        StringBuilder m =
                Utils$$ExternalSyntheticOutline0.m(
                        " isP2pCon: ",
                        isP2pConnected,
                        ", isDualBandCurSel: ",
                        isThisDualBand2GhzAnd5Ghz,
                        ", isP2pSoftApConcurrencySupported: ");
        m.append(isP2pSoftApConcurrencySupported);
        m.append(", isP2pDialogCanBeShown: ");
        m.append(z);
        String sb = m.toString();
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "CheckWiFiConcurrencyAndProceedFurther - ", sb, "WifiApWarning");
        this.mSemWifiManager.reportHotspotDumpLogs("WifiApWarning - " + sb);
        if (WifiApSoftApUtils.getBandArray(this.mContext).length > 1
                && this.mBand == -1
                && !Utils.supportBridgedApStaConcurrency()
                && (wifiState == 2 || wifiState == 3)) {
            if (this.mBand != -1) {
                this.mSemWifiManager.setAutohotspotToastMessage(12);
            }
            showNextHotspotDialog(54);
            return;
        }
        SemWifiDisplayStatus semGetWifiDisplayStatus =
                ((DisplayManager) this.mContext.getSystemService("display"))
                        .semGetWifiDisplayStatus();
        if (semGetWifiDisplayStatus != null
                && semGetWifiDisplayStatus.getActiveDisplayState() == 2
                && semGetWifiDisplayStatus.getConnectedState() == 2) {
            Log.d("WifiApWarning", "isWirelessDexEnabled:true");
            if (this.mBand != -1) {
                this.mSemWifiManager.setAutohotspotToastMessage(12);
            }
            Log.i("WifiApWarning", "WirelessDex is enabled. Create dailog");
            showNextHotspotDialog(52);
            return;
        }
        Log.d("WifiApWarning", "isWirelessDexEnabled:false");
        SemWifiDisplayStatus semGetWifiDisplayStatus2 =
                ((DisplayManager) this.mContext.getSystemService("display"))
                        .semGetWifiDisplayStatus();
        if (semGetWifiDisplayStatus2 != null
                && semGetWifiDisplayStatus2.getActiveDisplayState() == 2
                && semGetWifiDisplayStatus2.getConnectedState() == 0) {
            Log.d("WifiApWarning", "isSmartViewEnabled:true");
            if (z) {
                if (this.mBand != -1) {
                    this.mSemWifiManager.setAutohotspotToastMessage(12);
                }
                Log.i("WifiApWarning", "Smartview is enabled. Create dailog");
                showNextHotspotDialog(34);
                return;
            }
        } else {
            Log.d("WifiApWarning", "isSmartViewEnabled:false");
        }
        WifiApSettingsUtils.isNanEnabledDialogRequired(this.mContext);
        if (wifiState != 2 && wifiState != 3) {
            if (!z) {
                Log.i("WifiApWarning", " Wi-Fi is not enabled");
                startProvisioningIfNecessary();
                return;
            } else {
                Log.i("WifiApWarning", "WiFi P2p is connected. Create dailog");
                if (this.mBand != -1) {
                    this.mSemWifiManager.setAutohotspotToastMessage(12);
                }
                showNextHotspotDialog(31);
                return;
            }
        }
        if (Utils.SPF_SupportMobileApDualAp
                && Utils.SUPPORT_MOBILEAP_WIFISHARING
                && this.mBand != -1) {
            if (!z) {
                Log.i(
                        "WifiApWarning",
                        "Dualband supported model, so dont check for WifiSharing Switch if"
                            + " Autohotspot client in trying to enable in single band");
                startProvisioningIfNecessary();
                return;
            } else {
                Log.i(
                        "WifiApWarning",
                        "Dualband supported model,WiFi P2p is connected. Create dailog");
                if (this.mBand != -1) {
                    this.mSemWifiManager.setAutohotspotToastMessage(12);
                }
                showNextHotspotDialog(31);
                return;
            }
        }
        if (!Utils.SUPPORT_MOBILEAP_WIFISHARING || !isWifiSharingEnabled()) {
            if ("TMO".equals(Utils.CONFIGOPBRANDINGFORMOBILEAP)
                    || "NEWCO".equals(Utils.CONFIGOPBRANDINGFORMOBILEAP)) {
                if (getWifiConnectedFrequency() != -1) {
                    if (this.mBand != -1) {
                        this.mSemWifiManager.setAutohotspotToastMessage(12);
                    }
                    showNextHotspotDialog(30);
                    return;
                } else {
                    this.mAOSPWifiManager.setWifiEnabled(false);
                    final int i = 0;
                    new Handler()
                            .postDelayed(
                                    new Runnable(
                                            this) { // from class:
                                                    // com.samsung.android.settings.wifi.mobileap.WifiApWarning.71
                                        public final /* synthetic */ WifiApWarning this$0;

                                        {
                                            this.this$0 = this;
                                        }

                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            switch (i) {
                                                case 0:
                                                    WifiApWarning wifiApWarning = this.this$0;
                                                    boolean z2 = WifiApWarning.DBG;
                                                    wifiApWarning.startProvisioningIfNecessary();
                                                    break;
                                                default:
                                                    WifiApWarning wifiApWarning2 = this.this$0;
                                                    boolean z3 = WifiApWarning.DBG;
                                                    wifiApWarning2.startProvisioningIfNecessary();
                                                    break;
                                            }
                                        }
                                    },
                                    600L);
                    return;
                }
            }
            Log.i("WifiApWarning", "WiFi Sharing not supported/not enabled. Create dailog");
            if (this.mBand == -1) {
                showNextHotspotDialog(30);
                return;
            }
            Log.i(
                    "WifiApWarning",
                    "Autohotspot client is trying to enable,so dont show Wi-Fi disabble diaalog");
            this.mAOSPWifiManager.setWifiEnabled(false);
            Settings.Secure.putInt(this.mContext.getContentResolver(), "wifi_saved_state", 1);
            final int i2 = 1;
            new Handler()
                    .postDelayed(
                            new Runnable(
                                    this) { // from class:
                                            // com.samsung.android.settings.wifi.mobileap.WifiApWarning.71
                                public final /* synthetic */ WifiApWarning this$0;

                                {
                                    this.this$0 = this;
                                }

                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (i2) {
                                        case 0:
                                            WifiApWarning wifiApWarning = this.this$0;
                                            boolean z2 = WifiApWarning.DBG;
                                            wifiApWarning.startProvisioningIfNecessary();
                                            break;
                                        default:
                                            WifiApWarning wifiApWarning2 = this.this$0;
                                            boolean z3 = WifiApWarning.DBG;
                                            wifiApWarning2.startProvisioningIfNecessary();
                                            break;
                                    }
                                }
                            },
                            600L);
            return;
        }
        if (!Utils.SUPPORT_MOBILEAP_WIFISHARINGLITE || !isWifiSharingEnabled()) {
            if (!Utils.SUPPORT_MOBILEAP_WIFISHARING || !isWifiSharingEnabled()) {
                Log.e("WifiApWarning", "not handling in any case, it is an error");
                return;
            }
            Log.i("WifiApWarning", "WiFi Sharing  model.");
            if (!z) {
                startProvisioningIfNecessary();
                return;
            }
            Log.i("WifiApWarning", "WiFi P2p is connected. Create dailog");
            if (this.mBand != -1) {
                this.mSemWifiManager.setAutohotspotToastMessage(12);
            }
            showNextHotspotDialog(31);
            return;
        }
        if (z) {
            Log.i("WifiApWarning", "WiFi P2p is connected. Create dailog");
            if (this.mBand != -1) {
                this.mSemWifiManager.setAutohotspotToastMessage(12);
            }
            showNextHotspotDialog(31);
            return;
        }
        int wifiConnectedFrequency = getWifiConnectedFrequency();
        if (wifiConnectedFrequency == -1) {
            Log.i("WifiApWarning", "WiFi Sharing lite model. Wifi not connected");
            startProvisioningIfNecessary();
            return;
        }
        int indoorStatus = this.mSemWifiManager.getIndoorStatus();
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                wifiConnectedFrequency, "Frequency : ", "WifiApWarning");
        int i3 =
                wifiConnectedFrequency != -1
                        ? (wifiConnectedFrequency < 2412 || wifiConnectedFrequency > 2484) ? 5 : 2
                        : 0;
        Log.i("WifiApWarning", "Frequency band of connected AP:" + i3);
        Log.i(
                "WifiApWarning",
                "WiFi Sharing lite model. Wifi connected , indoor status:" + indoorStatus);
        if (indoorStatus == 1) {
            if (this.mBand != -1) {
                this.mSemWifiManager.setAutohotspotToastMessage(12);
            }
            showNextHotspotDialog(40);
        } else {
            if (i3 != 5) {
                startProvisioningIfNecessary();
                return;
            }
            if (this.mBand != -1) {
                this.mSemWifiManager.setAutohotspotToastMessage(12);
            }
            showNextHotspotDialog(41);
        }
    }

    public final void buttonValidate() {
        WifiApDataUsageConfig wifiApActiveSessionDataLimit =
                WifiApConnectedDeviceUtils.getWifiApActiveSessionDataLimit(this.mContext);
        if (this.mDataLimitChangeLimitButton != null) {
            if (this.mDataLimitEditText.getText().length() <= 0
                    || this.mDataLimitEditText.getText().toString().trim().length() <= 0) {
                this.mDataLimitChangeLimitButton.setEnabled(false);
                return;
            }
            if (wifiApActiveSessionDataLimit.getUsageValueInMB()
                    < getInputDataInDataUsageConfig().getUsageValueInMB()) {
                this.mDataLimitChangeLimitButton.setEnabled(true);
            } else {
                this.mDataLimitChangeLimitButton.setEnabled(false);
            }
        }
    }

    public final void checkFirstTimeConfigureAndProceedFurther() {
        if ((!"TMO".equals(Utils.CONFIGOPBRANDINGFORMOBILEAP)
                        && !"NEWCO".equals(Utils.CONFIGOPBRANDINGFORMOBILEAP))
                || !isUserDefinedPasswordSetForTMO()) {
            CheckWiFiConcurrencyAndProceedFurther();
            return;
        }
        if (this.mBand != -1) {
            this.mSemWifiManager.setAutohotspotToastMessage(12);
        }
        Log.i("WifiApWarning", "Dialog create during first time Mobile HotSpot at TMO");
        new Bundle().putInt("needresult", 1);
        Log.i(
                "WifiApWarning",
                "Launching WifiApEditSettings activity with title: "
                        + this.mContext.getString(R.string.wifi_ap_first_time_configuration));
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mSourceMetricsCategory = 3400;
        launchRequest.mDestinationName = WifiApEditSettings.class.getCanonicalName();
        subSettingLauncher.addFlags(536870912);
        subSettingLauncher.addFlags(67108864);
        subSettingLauncher.addFlags(268435456);
        subSettingLauncher.setTitleRes(R.string.wifi_ap_first_time_configuration, null);
        subSettingLauncher.launch();
        this.waitingForConfigurationSet = true;
    }

    public final void checkFirstTimeWiFiSharingDialogAndProceedFurthur() {
        if (Utils.SUPPORT_MOBILEAP_WIFISHARINGLITE
                && Settings.Secure.getInt(
                                this.mContext.getContentResolver(), "wifi_ap_wifi_sharing", 10)
                        == 10) {
            Settings.Secure.putInt(this.mContext.getContentResolver(), "wifi_ap_wifi_sharing", 0);
        }
        int wifiState = this.mAOSPWifiManager.getWifiState();
        if (!Utils.SUPPORT_MOBILEAP_WIFISHARING
                || Utils.SUPPORT_MOBILEAP_WIFISHARINGLITE
                || Settings.Secure.getInt(
                                this.mContext.getContentResolver(), "wifi_ap_wifi_sharing", 10)
                        != 1
                || Settings.Secure.getInt(
                                this.mContext.getContentResolver(),
                                "wifi_ap_first_time_wifi_sharing_dialog",
                                0)
                        != 0
                || (wifiState != 2 && wifiState != 3)) {
            checkFirstTimeConfigureAndProceedFurther();
            return;
        }
        Settings.Secure.putInt(
                this.mContext.getContentResolver(), "wifi_ap_first_time_wifi_sharing_dialog", 1);
        if (this.mBand != -1) {
            this.mSemWifiManager.setAutohotspotToastMessage(12);
        }
        showNextHotspotDialog(14);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0063  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean checkifProvisioningAPKPresent() {
        /*
            r7 = this;
            android.content.Context r0 = r7.mContext
            android.content.res.Resources r0 = r0.getResources()
            r1 = 17236257(0x1070121, float:2.4796394E-38)
            java.lang.String[] r0 = r0.getStringArray(r1)
            r7.mProvisionApp = r0
            r1 = 0
            r0 = r0[r1]
            java.lang.String r2 = ""
            boolean r0 = r0.equals(r2)
            r2 = 1
            if (r0 != 0) goto L8a
            android.content.Context r0 = r7.mContext
            java.lang.String[] r3 = r7.mProvisionApp
            r3 = r3[r1]
            java.lang.String r4 = "isPackageExists | package is enabled ? "
            java.lang.String r5 = "WifiApWarning"
            if (r0 != 0) goto L29
        L27:
            r6 = r1
            goto L61
        L29:
            android.content.pm.PackageManager r0 = r0.getPackageManager()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5b
            android.content.pm.PackageInfo r0 = r0.getPackageInfo(r3, r1)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5b
            if (r0 == 0) goto L3e
            android.content.pm.ApplicationInfo r6 = r0.applicationInfo     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5b
            if (r6 == 0) goto L3e
            int r6 = r6.flags     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5b
            r6 = r6 & r2
            if (r6 == 0) goto L3e
            r6 = r2
            goto L3f
        L3e:
            r6 = r1
        L3f:
            if (r6 == 0) goto L55
            android.content.pm.ApplicationInfo r0 = r0.applicationInfo     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5b
            boolean r6 = r0.enabled     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5b
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5b
            r0.<init>(r4)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5b
            r0.append(r6)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5b
            java.lang.String r0 = r0.toString()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5b
            android.util.Log.d(r5, r0)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5b
            goto L61
        L55:
            java.lang.String r0 = "isPackageExists | package is not system app or not available"
            android.util.Log.d(r5, r0)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5b
            goto L61
        L5b:
            java.lang.String r0 = "Package not found : "
            androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0.m(r0, r3, r5)
            goto L27
        L61:
            if (r6 != 0) goto L8a
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "package mprov : "
            r0.<init>(r2)
            java.lang.String[] r2 = r7.mProvisionApp
            r2 = r2[r1]
            r0.append(r2)
            java.lang.String r2 = " disabled."
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            android.util.Log.e(r5, r0)
            android.content.Context r7 = r7.mContext
            r0 = 2132023819(0x7f141a0b, float:1.9686097E38)
            android.widget.Toast r7 = android.widget.Toast.makeText(r7, r0, r1)
            r7.show()
            return r1
        L8a:
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.mobileap.WifiApWarning.checkifProvisioningAPKPresent():boolean");
    }

    public final void collapsePanels() {
        StatusBarManager statusBarManager =
                (StatusBarManager) this.mContext.getSystemService("statusbar");
        if (statusBarManager != null) {
            statusBarManager.collapsePanels();
        }
    }

    public final void enableTethering(boolean z) {
        int wifiApState = this.mSemWifiManager.getWifiApState();
        if (z && (wifiApState == 12 || wifiApState == 13)) {
            dismiss();
            return;
        }
        if (z && "LGT".equals(Utils.CONFIGOPBRANDINGFORMOBILEAP)) {
            Toast.makeText(this.mContext, R.string.wifi_ap_warn_toast_lgt, 0).show();
        }
        TooltipPopup$$ExternalSyntheticOutline0.m(
                RowView$$ExternalSyntheticOutline0.m("enableTethering: ", " mBand: ", z),
                this.mBand,
                "WifiApWarning");
        if (this.mBand == -1 && !this.mChangeSecurity && this.autohotspot_security == -1) {
            this.mSemWifiManager.setWifiApEnabled((SoftApConfiguration) null, z);
            return;
        }
        SoftApConfiguration softApConfiguration = this.mSemWifiManager.getSoftApConfiguration();
        SoftApConfiguration.Builder builder = new SoftApConfiguration.Builder(softApConfiguration);
        if (this.mBand != -1) {
            int channel = softApConfiguration.getChannel();
            Log.i("WifiApWarning", " mBand : " + this.mBand + ",mChannel:" + channel);
            int i = this.mBand;
            if ((i & 4) != 0) {
                AnonymousClass73 anonymousClass73 = new AnonymousClass73();
                anonymousClass73.put(4, 0);
                builder.setChannels(anonymousClass73);
            } else if ((i & 2) != 0) {
                AnonymousClass73 anonymousClass732 = new AnonymousClass73();
                anonymousClass732.put(2, 149);
                builder.setChannels(anonymousClass732);
            } else if (channel <= 0 || channel > 11) {
                AnonymousClass73 anonymousClass733 = new AnonymousClass73();
                anonymousClass733.put(1, 0);
                builder.setChannels(anonymousClass733);
            } else {
                AnonymousClass73 anonymousClass734 = new AnonymousClass73();
                anonymousClass734.put(1, channel);
                builder.setChannels(anonymousClass734);
            }
        }
        if (this.mChangeSecurity) {
            int i2 = this.autohotspot_security;
            if (i2 == 0) {
                builder.setPassphrase((String) null, 0);
            } else if (i2 < 1 || i2 > 3) {
                builder.setPassphrase(softApConfiguration.getPassphrase(), 2);
            } else {
                builder.setPassphrase(
                        softApConfiguration.getPassphrase(), this.autohotspot_security);
            }
        }
        this.mSemWifiManager.setWifiApEnabled(builder.build(), z);
    }

    public final WifiApDataUsageConfig getInputDataInDataUsageConfig() {
        return new WifiApDataUsageConfig(
                Long.parseLong(
                                (this.mDataLimitEditText.getText().length() <= 0
                                                || this.mDataLimitEditText
                                                                .getText()
                                                                .toString()
                                                                .trim()
                                                                .length()
                                                        <= 0)
                                        ? null
                                        : this.mDataLimitEditText.getText().toString())
                        * 1000000);
    }

    public final int getWifiConnectedFrequency() {
        WifiInfo connectionInfo = this.mAOSPWifiManager.getConnectionInfo();
        if (connectionInfo == null || connectionInfo.getNetworkId() == -1) {
            return -1;
        }
        Log.d("WifiApWarning", "Wifi Frequency is " + connectionInfo.getFrequency());
        return connectionInfo.getFrequency();
    }

    public final boolean isOperatorRequiresProvisioning() {
        if (DBG && SystemProperties.get("vendor.wifiap.provisioning.disable").equals("1")) {
            Log.d("WifiApWarning", "Skip isProvisioningCheck");
            return false;
        }
        if (!SemWifiApCust.isProvisioningNeeded()) {
            Utils$$ExternalSyntheticOutline0.m(
                    new StringBuilder(" provisioning is not required for this operator, operator:"),
                    Utils.CONFIGOPBRANDINGFORMOBILEAP,
                    "WifiApWarning");
            return false;
        }
        String[] stringArray = this.mContext.getResources().getStringArray(17236257);
        if (stringArray == null || stringArray.length == 0) {
            Utils$$ExternalSyntheticOutline0.m(
                    new StringBuilder(" provisioning app is not set in CSCfeature, operator:"),
                    Utils.CONFIGOPBRANDINGFORMOBILEAP,
                    "WifiApWarning");
            return false;
        }
        String[] stringArray2 = this.mContext.getResources().getStringArray(17236257);
        this.mProvisionApp = stringArray2;
        return stringArray2.length == 2;
    }

    public final boolean isUserDefinedPasswordSetForTMO() {
        SoftApConfiguration softApConfiguration = this.mSemWifiManager.getSoftApConfiguration();
        return (softApConfiguration.getPassphrase() == null
                        || !softApConfiguration.getPassphrase().equals("\tUSER#DEFINED#PWD#\n")
                        || softApConfiguration.getSecurityType() == 0)
                ? false
                : true;
    }

    public final boolean isWifiSharingEnabled() {
        return Settings.Secure.getInt(this.mContext.getContentResolver(), "wifi_ap_wifi_sharing", 0)
                == 1;
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        NetworkCapabilities networkCapabilities;
        super.onActivityResult(i, i2, intent);
        DynamicDenylistManager$$ExternalSyntheticOutline0.m(
                "onActivityResult requestCode", "resultCode ", i, i2, "WifiApWarning");
        if (1 == i) {
            enableTethering(true);
            dismiss();
            return;
        }
        if (i == 0) {
            if (i2 == -1) {
                this.mSemWifiManager.setProvisionSuccess(true);
                enableTethering(true);
                dismiss();
                return;
            }
            Log.i("WifiApWarning", "provision failed");
            int i3 =
                    Settings.Secure.getInt(
                            this.mContext.getContentResolver(), "wifi_saved_state", 0);
            if (Utils.SUPPORT_MOBILEAP_WIFISHARING && isWifiSharingEnabled()) {
                ConnectivityManager connectivityManager =
                        (ConnectivityManager) this.mContext.getSystemService("connectivity");
                Network activeNetwork = connectivityManager.getActiveNetwork();
                if ((activeNetwork == null
                                || (networkCapabilities =
                                                connectivityManager.getNetworkCapabilities(
                                                        activeNetwork))
                                        == null)
                        ? false
                        : networkCapabilities.hasTransport(1)) {
                    Log.i("WifiApWarning", "Wifi Sharing Provision failed but wifi connected");
                    enableTethering(true);
                    dismiss();
                    return;
                }
            }
            if (Utils.USE_BIXBY) {
                Utils.USE_BIXBY = false;
            }
            if (this.mSemWifiManager.getWifiApState() == 12
                    || this.mSemWifiManager.getWifiApState() == 13) {
                Log.i("WifiApWarning", "WifiAp is disabled: provisioning fail");
                this.mSemWifiManager.setWifiApEnabled((SoftApConfiguration) null, false);
                if (i3 == 1) {
                    try {
                        Thread.sleep(600L);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }
                }
            }
            if (this.mBand != -1) {
                this.mSemWifiManager.setAutohotspotToastMessage(11);
            }
            if (i3 == 1) {
                this.mAOSPWifiManager.setWifiEnabled(true);
                Settings.Secure.putInt(this.mContext.getContentResolver(), "wifi_saved_state", 0);
            }
            if (this.mDialogType != 6) {
                sendBroadcastEnablingHotspotCancel();
            }
            dismiss();
            return;
        }
        if (i == 3) {
            if (i2 == -1) {
                Log.i("WifiApWarning", "privision success, checking after softap enabled");
                this.mSemWifiManager.setProvisionSuccess(true);
                dismiss();
                return;
            }
            this.mSemWifiManager.setProvisionSuccess(false);
            if (this.mSemWifiManager.getWifiApState() == 13
                    || this.mSemWifiManager.getWifiApState() == 12) {
                Log.i(
                        "WifiApWarning",
                        "Disabling MHS as Provisioning failed after MHS enabled by startTethering");
                enableTethering(false);
                if (Settings.Secure.getInt(
                                this.mContext.getContentResolver(), "wifi_saved_state", 0)
                        == 1) {
                    try {
                        Thread.sleep(600L);
                    } catch (InterruptedException e2) {
                        Thread.currentThread().interrupt();
                        e2.printStackTrace();
                    }
                    Log.e("WifiApWarning", " provisioning failed, enabling Wifi ");
                    this.mAOSPWifiManager.setWifiEnabled(true);
                    Settings.Secure.putInt(
                            this.mContext.getContentResolver(), "wifi_saved_state", 0);
                }
            }
            dismiss();
            return;
        }
        if (i == 2) {
            if (i2 == -1) {
                Log.i(
                        "WifiApWarning",
                        "privision success, wifi disconnected ,restart provision case");
                this.mSemWifiManager.setProvisionSuccess(true);
                if ("VZW".equals(Utils.CONFIGOPBRANDINGFORMOBILEAP)) {
                    showNextHotspotDialog(7);
                    return;
                } else {
                    dismiss();
                    return;
                }
            }
            this.mSemWifiManager.setProvisionSuccess(false);
            if ("VZW".equals(Utils.CONFIGOPBRANDINGFORMOBILEAP) && isWifiSharingEnabled()) {
                if (!Utils.SPF_SupportMobileApEnhanced
                        || Settings.Secure.getInt(
                                        this.mContext.getContentResolver(),
                                        "wifi_ap_smart_tethering_settings",
                                        0)
                                == 0) {
                    Log.i("WifiApWarning", "for VZW show Wi-fiDisconnect ,disabling  wifi sharing");
                    Toast makeText =
                            Toast.makeText(
                                    this.mContext,
                                    R.string
                                            .wifi_ap_wifi_sharing_wifi_disconnected_mobileap_turnoff_toast,
                                    1);
                    makeText.setGravity(17, 0, 0);
                    makeText.show();
                    Settings.Secure.putInt(
                            this.mContext.getContentResolver(), "wifi_ap_wifi_sharing", 0);
                    Log.i("WifiApWarning", "WIFI_AP_WIFI_SHARING provider value after putting 0");
                } else {
                    Log.i(
                            "WifiApWarning",
                            "for VZW show Wi-fiDisconnect ,dont disabling  wifi sharing as"
                                + " Autohotspot case");
                }
            }
            if (this.mSemWifiManager.getWifiApState() == 13
                    || this.mSemWifiManager.getWifiApState() == 12) {
                Log.i(
                        "WifiApWarning",
                        "Disabling MHS as Provisioning failed in wifi disconneted case");
                enableTethering(false);
                if (Settings.Secure.getInt(
                                this.mContext.getContentResolver(), "wifi_saved_state", 0)
                        == 1) {
                    try {
                        Thread.sleep(600L);
                    } catch (InterruptedException e3) {
                        Thread.currentThread().interrupt();
                        e3.printStackTrace();
                    }
                    Log.e("WifiApWarning", " provisioning failed, enabling Wifi ");
                    this.mAOSPWifiManager.setWifiEnabled(true);
                    Settings.Secure.putInt(
                            this.mContext.getContentResolver(), "wifi_saved_state", 0);
                }
            }
            dismiss();
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        finish();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0431  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x0438  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreate(android.os.Bundle r14) {
        /*
            Method dump skipped, instructions count: 1168
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.mobileap.WifiApWarning.onCreate(android.os.Bundle):void");
    }

    public final void onDestroy() {
        if (this.isregisterFinishReceiver) {
            Log.i("WifiApWarning", "unregisterFinishReceiver");
            this.isregisterFinishReceiver = false;
            this.mContext.unregisterReceiver(this.mWifiApWarningRecv);
        }
        this.mSemWifiManager.setWifiApWarningActivityRunning(0);
        Context context = this.mContext;
        if (context != null && context.getContentResolver() != null) {
            Settings.Secure.putString(
                    this.mContext.getContentResolver(),
                    "wifi_ap_wifiapwarning_destroyed_history",
                    new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.US)
                            .format(Long.valueOf(System.currentTimeMillis())));
        }
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null && wakeLock.isHeld()) {
            this.mWakeLock.release();
        }
        PowerManager.WakeLock wakeLock2 = this.mPartialWakeLock;
        if (wakeLock2 != null && wakeLock2.isHeld()) {
            this.mPartialWakeLock.release();
        }
        PowerManager.WakeLock wakeLock3 = this.mScreenWakeLock;
        if (wakeLock3 != null && wakeLock3.isHeld()) {
            this.mScreenWakeLock.release();
        }
        Log.i("WifiApWarning", "onDestroy()");
        if (this.mContext != null
                && isOperatorRequiresProvisioning()
                && this.isProvisionBRRegistered) {
            this.mContext.unregisterReceiver(this.mReceiver);
            this.isProvisionBRRegistered = false;
        }
        Context context2 = this.mContext;
        if (context2 != null && this.isIntentFilterBRRegistered) {
            context2.unregisterReceiver(this.mIntentReceiver);
            this.isIntentFilterBRRegistered = false;
        }
        super.onDestroy();
    }

    public final void onPause() {
        Log.i("WifiApWarning", "onPause()");
        this.mSemWifiManager.setWifiApWarningActivityRunning(1);
        Context context = this.mContext;
        if (context != null
                && !((PowerManager) context.getSystemService("power")).isInteractive()) {
            Log.i("WifiApWarning", "onPause() sleep");
            AlertDialog alertDialog = this.allowAutohotspotDiscoveryDialogBox;
            if (alertDialog != null && alertDialog.isShowing()) {
                this.allowAutohotspotDiscoveryDialogBox.dismiss();
                this.allowAutohotspotDiscoveryDialogBox = null;
                dismiss();
            }
            AlertDialog alertDialog2 = this.autoHotspotDisconnectedDialogBox;
            if (alertDialog2 != null && alertDialog2.isShowing()) {
                this.autoHotspotDisconnectedDialogBox.dismiss();
                this.autoHotspotDisconnectedDialogBox = null;
                dismiss();
            }
            AlertDialog alertDialog3 = this.autoHotspotConnectionDialogBox;
            if (alertDialog3 != null && alertDialog3.isShowing()) {
                this.autoHotspotConnectionDialogBox.dismiss();
                this.autoHotspotConnectionDialogBox = null;
                dismiss();
            }
        }
        super.onPause();
    }

    public final void onResume() {
        super.onResume();
        Log.i("WifiApWarning", "onResume()");
        this.mSemWifiManager.setWifiApWarningActivityRunning(1);
        if (this.waitingForConfigurationSet) {
            Log.i("WifiApWarning", "waitingForConfigurationSet()");
            this.waitingForConfigurationSet = false;
            try {
                Thread.sleep(300L);
            } catch (InterruptedException e) {
                Thread.interrupted();
                e.printStackTrace();
            }
            if (isUserDefinedPasswordSetForTMO()) {
                dismiss();
            } else {
                CheckWiFiConcurrencyAndProceedFurther();
            }
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    public final void onStart() {
        super.onStart();
        Log.i("WifiApWarning", "onStart()");
    }

    public final void onStop() {
        super.onStop();
        Log.i("WifiApWarning", "onStop()");
    }

    public final void onUserLeaveHint() {
        super.onUserLeaveHint();
    }

    public final void sendBroadcastEnablingHotspotCancel() {
        Intent intent = new Intent("com.samsung.android.net.wifi.WIFI_DIALOG_CANCEL_ACTION");
        intent.putExtra("called_dialog", 2);
        this.mContext.sendBroadcast(intent, "android.permission.OVERRIDE_WIFI_CONFIG");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void showNextHotspotDialog(int i) {
        int i2;
        boolean z = (getResources().getConfiguration().uiMode & 48) == 32;
        if (z) {
            this.mDialogTheme = 4;
        } else {
            this.mDialogTheme = 5;
        }
        Preference$$ExternalSyntheticOutline0.m(
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "id :", " dialogtheme: "),
                this.mDialogTheme,
                "WifiApWarning");
        if (i == 7) {
            Log.i("WifiApWarning", "DIALOG_WIFI_DISCONNECTED_HOTSPOT_USAGE_WARNING");
            if (this.mSharedPref == null) {
                i2 = 0;
                this.mSharedPref = this.mContext.getSharedPreferences("SAMSUNG_HOTSPOT", 0);
            } else {
                i2 = 0;
            }
            int i3 = this.mSharedPref.getInt("wifi_disconnect_do_not_show", i2);
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    i3, "for VZW show Wi-fiDisconnect isDoNotShowAgain ", "WifiApWarning");
            if (i3 == 1) {
                Toast makeText =
                        Toast.makeText(
                                this.mContext,
                                R.string.wifi_ap_wifi_sharing_wifi_disconnected_toast,
                                1);
                makeText.setGravity(17, i2, i2);
                makeText.show();
                dismiss();
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this, this.mDialogTheme);
            View inflate =
                    View.inflate(
                            this.mContext,
                            R.layout.sec_wifi_ap_wifi_sharing_vzw_hotspot_usage_dialog,
                            null);
            final CheckBox checkBox =
                    (CheckBox)
                            inflate.findViewById(R.id.wifi_ap_wifi_disconnect_warning_do_not_show);
            builder.setTitle(R.string.wifi_ap_wifi_sharing_wifi_disconnected_dialog_title);
            builder.setView(inflate);
            builder.setPositiveButton(
                    R.string.dlg_ok,
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.wifi.mobileap.WifiApWarning.57
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i4) {
                            if (checkBox.isChecked()) {
                                WifiApWarning wifiApWarning = WifiApWarning.this;
                                if (wifiApWarning.mSharedPref == null) {
                                    wifiApWarning.mSharedPref =
                                            wifiApWarning.mContext.getSharedPreferences(
                                                    "SAMSUNG_HOTSPOT", 0);
                                }
                                SharedPreferences.Editor edit =
                                        WifiApWarning.this.mSharedPref.edit();
                                edit.putInt("wifi_disconnect_do_not_show", 1);
                                edit.commit();
                            }
                            WifiApWarning.this.dismiss();
                        }
                    });
            builder.setOnCancelListener(new AnonymousClass3(this, 17));
            builder.create();
            builder.show();
            collapsePanels();
        }
        if (i == 14) {
            Log.i("WifiApWarning", "DIALOG_WIFI_SHARING_FIRST_TIME");
            this.mContext.getContentResolver();
            AlertDialog.Builder builder2 = new AlertDialog.Builder(this, this.mDialogTheme);
            builder2.setTitle(
                            WifiApUtils.getString(
                                    this.mContext, R.string.wifi_sharing_first_time_dialog_title))
                    .setMessage(
                            WifiApUtils.getString(
                                    this.mContext, R.string.wifi_sharing_first_time_dialog_text))
                    .setPositiveButton(R.string.dlg_ok, new AnonymousClass1(this, 29))
                    .setOnCancelListener(new AnonymousClass3(this, 18));
            builder2.create().show();
            collapsePanels();
            return;
        }
        if (i == 21) {
            sendBroadcastEnablingHotspotCancel();
            finish();
            collapsePanels();
            return;
        }
        if (i == 28) {
            AlertDialog.Builder builder3 = new AlertDialog.Builder(this, this.mDialogTheme);
            builder3.setMessage(
                    WifiApUtils.getString(this.mContext, R.string.dialog_battery_data_warning));
            builder3.setPositiveButton(R.string.dlg_ok, new AnonymousClass1(this, 3));
            builder3.setNegativeButton(R.string.dlg_cancel, new AnonymousClass1(this, 4));
            builder3.setOnCancelListener(new AnonymousClass3(this, 3));
            builder3.setTitle(WifiApUtils.getString(this.mContext, R.string.mobileap));
            builder3.create();
            builder3.show();
            collapsePanels();
            return;
        }
        if (i == 34) {
            new AlertDialog.Builder(this, this.mDialogTheme)
                    .setMessage(WifiApUtils.getStringID(R.string.wifi_ap_smartview_off_warn))
                    .setPositiveButton(R.string.turn_off_button, new AnonymousClass1(this, 10))
                    .setNegativeButton(R.string.dlg_cancel, new AnonymousClass1(this, 9))
                    .setOnCancelListener(new AnonymousClass3(this, 6))
                    .setTitle(R.string.wifi_ap_smartview_off_warn_title)
                    .create()
                    .show();
            collapsePanels();
            return;
        }
        if (i == 35) {
            new AlertDialog.Builder(this, this.mDialogTheme)
                    .setMessage(R.string.wifi_ap_wifi_nan_off_warn)
                    .setPositiveButton(R.string.wifi_ap_button_stop, new AnonymousClass1(this, 13))
                    .setNegativeButton(R.string.dlg_cancel, new AnonymousClass1(this, 11))
                    .setOnCancelListener(new AnonymousClass3(this, 7))
                    .setTitle(R.string.wifi_ap_wifi_nan_off_warn_title)
                    .create()
                    .show();
            collapsePanels();
            return;
        }
        if (i == 51) {
            AlertDialog.Builder builder4 = new AlertDialog.Builder(this, this.mDialogTheme);
            builder4.setTitle(R.string.wifi_ap_wifi_sharinglite_turn_off);
            builder4.setMessage(
                    WifiApUtils.getString(
                            this.mContext,
                            R.string.wifi_ap_wifi_sharinglite_24ghz_client_during_conn_text));
            final int i4 = 1;
            builder4.setPositiveButton(
                    R.string.turn_off,
                    new DialogInterface.OnClickListener(this) { // from class:
                        // com.samsung.android.settings.wifi.mobileap.WifiApWarning.7
                        public final /* synthetic */ WifiApWarning this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i5) {
                            switch (i4) {
                                case 0:
                                    SALogging.insertSALog("TETH_010", "8035");
                                    WifiApWarning wifiApWarning = this.this$0;
                                    boolean z2 = WifiApWarning.DBG;
                                    wifiApWarning.sendBroadcastEnablingHotspotCancel();
                                    this.this$0.finish();
                                    break;
                                case 1:
                                    SALogging.insertSALog("TETH_010", "8063");
                                    Log.d("WifiApWarning", "AP sta disconnect");
                                    Settings.Secure.putInt(
                                            this.this$0.mContext.getContentResolver(),
                                            "wifi_ap_wifi_sharing",
                                            0);
                                    WifiManager wifiManager = this.this$0.mAOSPWifiManager;
                                    if (wifiManager != null) {
                                        wifiManager.setWifiEnabled(false);
                                        this.this$0.mSemWifiManager.resetSoftAp(new Message());
                                    }
                                    this.this$0.finish();
                                    break;
                                case 2:
                                    SALogging.insertSALog("TETH_010", "8062");
                                    this.this$0.finish();
                                    break;
                                case 3:
                                    SALogging.insertSALog("TETH_010", "8066");
                                    Context context = this.this$0.mContext;
                                    ((SemWifiManager)
                                                    context.getSystemService(
                                                            WiFiManagerExt.SEM_WIFI_SERVICE))
                                            .reportIssue(
                                                    100,
                                                    WifiIssueDetectorUtil.ReportUtil
                                                            .getReportDataForWifiManagerApi(
                                                                    -1,
                                                                    "disconnect",
                                                                    context.getPackageManager()
                                                                            .getNameForUid(
                                                                                    context
                                                                                            .getUserId()),
                                                                    context.getPackageName()));
                                    this.this$0.mAOSPWifiManager.disconnect();
                                    this.this$0.startProvisioningIfNecessary();
                                    break;
                                case 4:
                                    SALogging.insertSALog("TETH_010", "8065");
                                    WifiApWarning wifiApWarning2 = this.this$0;
                                    boolean z3 = WifiApWarning.DBG;
                                    wifiApWarning2.sendBroadcastEnablingHotspotCancel();
                                    this.this$0.finish();
                                    break;
                                case 5:
                                    SALogging.insertSALog("TETH_010", "8064");
                                    WifiApWarning wifiApWarning3 = this.this$0;
                                    boolean z4 = WifiApWarning.DBG;
                                    wifiApWarning3.startProvisioningIfNecessary();
                                    break;
                                case 6:
                                    WifiApDataUsageConfig inputDataInDataUsageConfig =
                                            this.this$0.getInputDataInDataUsageConfig();
                                    if (inputDataInDataUsageConfig.getUsageValueInMB()
                                            >= WifiApConnectedDeviceUtils
                                                    .getWifiApTodayTotalDataUsage(
                                                            this.this$0.mContext)
                                                    .getUsageValueInMB()) {
                                        Log.i(
                                                "WifiApWarning",
                                                "Settings Global data limit : "
                                                        + (inputDataInDataUsageConfig
                                                                        .mUsageValueInBytes
                                                                / 1000.0d));
                                        WifiApConnectedDeviceUtils.setWifiApActiveSessionDataLimit(
                                                this.this$0.mContext,
                                                (long)
                                                        ((double)
                                                                inputDataInDataUsageConfig
                                                                        .mUsageValueInBytes));
                                        try {
                                            Thread.sleep(100L);
                                        } catch (InterruptedException e) {
                                            Thread.currentThread().interrupt();
                                            e.printStackTrace();
                                        }
                                        this.this$0.startProvisioningIfNecessary();
                                        break;
                                    } else {
                                        Log.i(
                                                "WifiApWarning",
                                                "Error Settings Global data limit : "
                                                        + inputDataInDataUsageConfig
                                                                .getUsageValueInMB());
                                        this.this$0.sendBroadcastEnablingHotspotCancel();
                                        Toast.makeText(
                                                        this.this$0.mContext,
                                                        "You can'\t set less than the amount of"
                                                            + " data you used. Please enter again.",
                                                        0)
                                                .show();
                                        this.this$0.finish();
                                        break;
                                    }
                                case 7:
                                    WifiApConnectedDeviceUtils.setWifiApActiveSessionDataLimit(
                                            this.this$0.mContext, 0L);
                                    try {
                                        Thread.sleep(100L);
                                    } catch (InterruptedException e2) {
                                        Thread.currentThread().interrupt();
                                        e2.printStackTrace();
                                    }
                                    this.this$0.startProvisioningIfNecessary();
                                    break;
                                case 8:
                                    WifiApWarning wifiApWarning4 = this.this$0;
                                    boolean z5 = WifiApWarning.DBG;
                                    wifiApWarning4.sendBroadcastEnablingHotspotCancel();
                                    this.this$0.finish();
                                    break;
                                default:
                                    WifiApWarning wifiApWarning5 = this.this$0;
                                    boolean z6 = WifiApWarning.DBG;
                                    wifiApWarning5
                                            .checkFirstTimeWiFiSharingDialogAndProceedFurthur();
                                    break;
                            }
                        }
                    });
            final int i5 = 2;
            builder4.setNegativeButton(
                    R.string.dlg_cancel,
                    new DialogInterface.OnClickListener(this) { // from class:
                        // com.samsung.android.settings.wifi.mobileap.WifiApWarning.7
                        public final /* synthetic */ WifiApWarning this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i52) {
                            switch (i5) {
                                case 0:
                                    SALogging.insertSALog("TETH_010", "8035");
                                    WifiApWarning wifiApWarning = this.this$0;
                                    boolean z2 = WifiApWarning.DBG;
                                    wifiApWarning.sendBroadcastEnablingHotspotCancel();
                                    this.this$0.finish();
                                    break;
                                case 1:
                                    SALogging.insertSALog("TETH_010", "8063");
                                    Log.d("WifiApWarning", "AP sta disconnect");
                                    Settings.Secure.putInt(
                                            this.this$0.mContext.getContentResolver(),
                                            "wifi_ap_wifi_sharing",
                                            0);
                                    WifiManager wifiManager = this.this$0.mAOSPWifiManager;
                                    if (wifiManager != null) {
                                        wifiManager.setWifiEnabled(false);
                                        this.this$0.mSemWifiManager.resetSoftAp(new Message());
                                    }
                                    this.this$0.finish();
                                    break;
                                case 2:
                                    SALogging.insertSALog("TETH_010", "8062");
                                    this.this$0.finish();
                                    break;
                                case 3:
                                    SALogging.insertSALog("TETH_010", "8066");
                                    Context context = this.this$0.mContext;
                                    ((SemWifiManager)
                                                    context.getSystemService(
                                                            WiFiManagerExt.SEM_WIFI_SERVICE))
                                            .reportIssue(
                                                    100,
                                                    WifiIssueDetectorUtil.ReportUtil
                                                            .getReportDataForWifiManagerApi(
                                                                    -1,
                                                                    "disconnect",
                                                                    context.getPackageManager()
                                                                            .getNameForUid(
                                                                                    context
                                                                                            .getUserId()),
                                                                    context.getPackageName()));
                                    this.this$0.mAOSPWifiManager.disconnect();
                                    this.this$0.startProvisioningIfNecessary();
                                    break;
                                case 4:
                                    SALogging.insertSALog("TETH_010", "8065");
                                    WifiApWarning wifiApWarning2 = this.this$0;
                                    boolean z3 = WifiApWarning.DBG;
                                    wifiApWarning2.sendBroadcastEnablingHotspotCancel();
                                    this.this$0.finish();
                                    break;
                                case 5:
                                    SALogging.insertSALog("TETH_010", "8064");
                                    WifiApWarning wifiApWarning3 = this.this$0;
                                    boolean z4 = WifiApWarning.DBG;
                                    wifiApWarning3.startProvisioningIfNecessary();
                                    break;
                                case 6:
                                    WifiApDataUsageConfig inputDataInDataUsageConfig =
                                            this.this$0.getInputDataInDataUsageConfig();
                                    if (inputDataInDataUsageConfig.getUsageValueInMB()
                                            >= WifiApConnectedDeviceUtils
                                                    .getWifiApTodayTotalDataUsage(
                                                            this.this$0.mContext)
                                                    .getUsageValueInMB()) {
                                        Log.i(
                                                "WifiApWarning",
                                                "Settings Global data limit : "
                                                        + (inputDataInDataUsageConfig
                                                                        .mUsageValueInBytes
                                                                / 1000.0d));
                                        WifiApConnectedDeviceUtils.setWifiApActiveSessionDataLimit(
                                                this.this$0.mContext,
                                                (long)
                                                        ((double)
                                                                inputDataInDataUsageConfig
                                                                        .mUsageValueInBytes));
                                        try {
                                            Thread.sleep(100L);
                                        } catch (InterruptedException e) {
                                            Thread.currentThread().interrupt();
                                            e.printStackTrace();
                                        }
                                        this.this$0.startProvisioningIfNecessary();
                                        break;
                                    } else {
                                        Log.i(
                                                "WifiApWarning",
                                                "Error Settings Global data limit : "
                                                        + inputDataInDataUsageConfig
                                                                .getUsageValueInMB());
                                        this.this$0.sendBroadcastEnablingHotspotCancel();
                                        Toast.makeText(
                                                        this.this$0.mContext,
                                                        "You can'\t set less than the amount of"
                                                            + " data you used. Please enter again.",
                                                        0)
                                                .show();
                                        this.this$0.finish();
                                        break;
                                    }
                                case 7:
                                    WifiApConnectedDeviceUtils.setWifiApActiveSessionDataLimit(
                                            this.this$0.mContext, 0L);
                                    try {
                                        Thread.sleep(100L);
                                    } catch (InterruptedException e2) {
                                        Thread.currentThread().interrupt();
                                        e2.printStackTrace();
                                    }
                                    this.this$0.startProvisioningIfNecessary();
                                    break;
                                case 8:
                                    WifiApWarning wifiApWarning4 = this.this$0;
                                    boolean z5 = WifiApWarning.DBG;
                                    wifiApWarning4.sendBroadcastEnablingHotspotCancel();
                                    this.this$0.finish();
                                    break;
                                default:
                                    WifiApWarning wifiApWarning5 = this.this$0;
                                    boolean z6 = WifiApWarning.DBG;
                                    wifiApWarning5
                                            .checkFirstTimeWiFiSharingDialogAndProceedFurthur();
                                    break;
                            }
                        }
                    });
            builder4.setOnCancelListener(new AnonymousClass3(this, 19));
            builder4.create();
            builder4.show();
            collapsePanels();
            return;
        }
        if (i == 52) {
            Log.i("WifiApWarning", "CASE DIALOG_WARN_WIRELESS_DEX_DISABLE");
            new AlertDialog.Builder(this, this.mDialogTheme)
                    .setMessage(R.string.wifi_ap_wirelessdex_off_warn)
                    .setPositiveButton(
                            R.string.wifi_ap_disconnect_text, new AnonymousClass1(this, 17))
                    .setNegativeButton(R.string.dlg_cancel, new AnonymousClass1(this, 16))
                    .setOnCancelListener(new AnonymousClass3(this, 9))
                    .setTitle(R.string.wifi_ap_wirelessdex_off_warn_title)
                    .create()
                    .show();
            collapsePanels();
            return;
        }
        switch (i) {
            case 24:
                AlertDialog.Builder builder5 = new AlertDialog.Builder(this, this.mDialogTheme);
                builder5.setMessage(R.string.wifi_tether_dialog_nolte_warning);
                builder5.setPositiveButton(R.string.dlg_ok, new AnonymousClass1(this, 28));
                builder5.setOnCancelListener(new AnonymousClass3(this, 22));
                builder5.setTitle(WifiApUtils.getString(this.mContext, R.string.mobileap));
                builder5.create();
                builder5.show();
                collapsePanels();
                break;
            case 25:
                Log.d("WifiApWarning", "no sim");
                AlertDialog.Builder builder6 = new AlertDialog.Builder(this, this.mDialogTheme);
                if ("CTC".equals(Utils.getSalesCode())) {
                    builder6.setMessage(R.string.mobile_hotspot_dialog_nouim_or_nosim_warning);
                } else {
                    builder6.setMessage(
                            WifiApUtils.getString(
                                    this.mContext, R.string.wifi_tether_dialog_nosim_warning));
                }
                final int i6 = 0;
                builder6.setPositiveButton(
                        R.string.dlg_ok,
                        new DialogInterface.OnClickListener(this) { // from class:
                            // com.samsung.android.settings.wifi.mobileap.WifiApWarning.7
                            public final /* synthetic */ WifiApWarning this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i52) {
                                switch (i6) {
                                    case 0:
                                        SALogging.insertSALog("TETH_010", "8035");
                                        WifiApWarning wifiApWarning = this.this$0;
                                        boolean z2 = WifiApWarning.DBG;
                                        wifiApWarning.sendBroadcastEnablingHotspotCancel();
                                        this.this$0.finish();
                                        break;
                                    case 1:
                                        SALogging.insertSALog("TETH_010", "8063");
                                        Log.d("WifiApWarning", "AP sta disconnect");
                                        Settings.Secure.putInt(
                                                this.this$0.mContext.getContentResolver(),
                                                "wifi_ap_wifi_sharing",
                                                0);
                                        WifiManager wifiManager = this.this$0.mAOSPWifiManager;
                                        if (wifiManager != null) {
                                            wifiManager.setWifiEnabled(false);
                                            this.this$0.mSemWifiManager.resetSoftAp(new Message());
                                        }
                                        this.this$0.finish();
                                        break;
                                    case 2:
                                        SALogging.insertSALog("TETH_010", "8062");
                                        this.this$0.finish();
                                        break;
                                    case 3:
                                        SALogging.insertSALog("TETH_010", "8066");
                                        Context context = this.this$0.mContext;
                                        ((SemWifiManager)
                                                        context.getSystemService(
                                                                WiFiManagerExt.SEM_WIFI_SERVICE))
                                                .reportIssue(
                                                        100,
                                                        WifiIssueDetectorUtil.ReportUtil
                                                                .getReportDataForWifiManagerApi(
                                                                        -1,
                                                                        "disconnect",
                                                                        context.getPackageManager()
                                                                                .getNameForUid(
                                                                                        context
                                                                                                .getUserId()),
                                                                        context.getPackageName()));
                                        this.this$0.mAOSPWifiManager.disconnect();
                                        this.this$0.startProvisioningIfNecessary();
                                        break;
                                    case 4:
                                        SALogging.insertSALog("TETH_010", "8065");
                                        WifiApWarning wifiApWarning2 = this.this$0;
                                        boolean z3 = WifiApWarning.DBG;
                                        wifiApWarning2.sendBroadcastEnablingHotspotCancel();
                                        this.this$0.finish();
                                        break;
                                    case 5:
                                        SALogging.insertSALog("TETH_010", "8064");
                                        WifiApWarning wifiApWarning3 = this.this$0;
                                        boolean z4 = WifiApWarning.DBG;
                                        wifiApWarning3.startProvisioningIfNecessary();
                                        break;
                                    case 6:
                                        WifiApDataUsageConfig inputDataInDataUsageConfig =
                                                this.this$0.getInputDataInDataUsageConfig();
                                        if (inputDataInDataUsageConfig.getUsageValueInMB()
                                                >= WifiApConnectedDeviceUtils
                                                        .getWifiApTodayTotalDataUsage(
                                                                this.this$0.mContext)
                                                        .getUsageValueInMB()) {
                                            Log.i(
                                                    "WifiApWarning",
                                                    "Settings Global data limit : "
                                                            + (inputDataInDataUsageConfig
                                                                            .mUsageValueInBytes
                                                                    / 1000.0d));
                                            WifiApConnectedDeviceUtils
                                                    .setWifiApActiveSessionDataLimit(
                                                            this.this$0.mContext,
                                                            (long)
                                                                    ((double)
                                                                            inputDataInDataUsageConfig
                                                                                    .mUsageValueInBytes));
                                            try {
                                                Thread.sleep(100L);
                                            } catch (InterruptedException e) {
                                                Thread.currentThread().interrupt();
                                                e.printStackTrace();
                                            }
                                            this.this$0.startProvisioningIfNecessary();
                                            break;
                                        } else {
                                            Log.i(
                                                    "WifiApWarning",
                                                    "Error Settings Global data limit : "
                                                            + inputDataInDataUsageConfig
                                                                    .getUsageValueInMB());
                                            this.this$0.sendBroadcastEnablingHotspotCancel();
                                            Toast.makeText(
                                                            this.this$0.mContext,
                                                            "You can'\t set less than the amount of"
                                                                + " data you used. Please enter"
                                                                + " again.",
                                                            0)
                                                    .show();
                                            this.this$0.finish();
                                            break;
                                        }
                                    case 7:
                                        WifiApConnectedDeviceUtils.setWifiApActiveSessionDataLimit(
                                                this.this$0.mContext, 0L);
                                        try {
                                            Thread.sleep(100L);
                                        } catch (InterruptedException e2) {
                                            Thread.currentThread().interrupt();
                                            e2.printStackTrace();
                                        }
                                        this.this$0.startProvisioningIfNecessary();
                                        break;
                                    case 8:
                                        WifiApWarning wifiApWarning4 = this.this$0;
                                        boolean z5 = WifiApWarning.DBG;
                                        wifiApWarning4.sendBroadcastEnablingHotspotCancel();
                                        this.this$0.finish();
                                        break;
                                    default:
                                        WifiApWarning wifiApWarning5 = this.this$0;
                                        boolean z6 = WifiApWarning.DBG;
                                        wifiApWarning5
                                                .checkFirstTimeWiFiSharingDialogAndProceedFurthur();
                                        break;
                                }
                            }
                        });
                builder6.setOnCancelListener(new AnonymousClass3(this, 24));
                builder6.setTitle(R.string.wifi_tether_dialog_nosim_warning_title);
                builder6.create();
                builder6.show();
                collapsePanels();
                break;
            case 26:
                AlertDialog.Builder builder7 = new AlertDialog.Builder(this, this.mDialogTheme);
                if ("VZW".equals(Utils.CONFIGOPBRANDINGFORMOBILEAP)) {
                    builder7.setMessage(
                            WifiApUtils.getString(
                                    this.mContext, R.string.wifi_ap_warn_roaming_msg_beyond_vzw));
                    final int i7 = 9;
                    builder7.setPositiveButton(
                            R.string.lockpattern_continue_button_text,
                            new DialogInterface.OnClickListener(this) { // from class:
                                // com.samsung.android.settings.wifi.mobileap.WifiApWarning.7
                                public final /* synthetic */ WifiApWarning this$0;

                                {
                                    this.this$0 = this;
                                }

                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(
                                        DialogInterface dialogInterface, int i52) {
                                    switch (i7) {
                                        case 0:
                                            SALogging.insertSALog("TETH_010", "8035");
                                            WifiApWarning wifiApWarning = this.this$0;
                                            boolean z2 = WifiApWarning.DBG;
                                            wifiApWarning.sendBroadcastEnablingHotspotCancel();
                                            this.this$0.finish();
                                            break;
                                        case 1:
                                            SALogging.insertSALog("TETH_010", "8063");
                                            Log.d("WifiApWarning", "AP sta disconnect");
                                            Settings.Secure.putInt(
                                                    this.this$0.mContext.getContentResolver(),
                                                    "wifi_ap_wifi_sharing",
                                                    0);
                                            WifiManager wifiManager = this.this$0.mAOSPWifiManager;
                                            if (wifiManager != null) {
                                                wifiManager.setWifiEnabled(false);
                                                this.this$0.mSemWifiManager.resetSoftAp(
                                                        new Message());
                                            }
                                            this.this$0.finish();
                                            break;
                                        case 2:
                                            SALogging.insertSALog("TETH_010", "8062");
                                            this.this$0.finish();
                                            break;
                                        case 3:
                                            SALogging.insertSALog("TETH_010", "8066");
                                            Context context = this.this$0.mContext;
                                            ((SemWifiManager)
                                                            context.getSystemService(
                                                                    WiFiManagerExt
                                                                            .SEM_WIFI_SERVICE))
                                                    .reportIssue(
                                                            100,
                                                            WifiIssueDetectorUtil.ReportUtil
                                                                    .getReportDataForWifiManagerApi(
                                                                            -1,
                                                                            "disconnect",
                                                                            context.getPackageManager()
                                                                                    .getNameForUid(
                                                                                            context
                                                                                                    .getUserId()),
                                                                            context
                                                                                    .getPackageName()));
                                            this.this$0.mAOSPWifiManager.disconnect();
                                            this.this$0.startProvisioningIfNecessary();
                                            break;
                                        case 4:
                                            SALogging.insertSALog("TETH_010", "8065");
                                            WifiApWarning wifiApWarning2 = this.this$0;
                                            boolean z3 = WifiApWarning.DBG;
                                            wifiApWarning2.sendBroadcastEnablingHotspotCancel();
                                            this.this$0.finish();
                                            break;
                                        case 5:
                                            SALogging.insertSALog("TETH_010", "8064");
                                            WifiApWarning wifiApWarning3 = this.this$0;
                                            boolean z4 = WifiApWarning.DBG;
                                            wifiApWarning3.startProvisioningIfNecessary();
                                            break;
                                        case 6:
                                            WifiApDataUsageConfig inputDataInDataUsageConfig =
                                                    this.this$0.getInputDataInDataUsageConfig();
                                            if (inputDataInDataUsageConfig.getUsageValueInMB()
                                                    >= WifiApConnectedDeviceUtils
                                                            .getWifiApTodayTotalDataUsage(
                                                                    this.this$0.mContext)
                                                            .getUsageValueInMB()) {
                                                Log.i(
                                                        "WifiApWarning",
                                                        "Settings Global data limit : "
                                                                + (inputDataInDataUsageConfig
                                                                                .mUsageValueInBytes
                                                                        / 1000.0d));
                                                WifiApConnectedDeviceUtils
                                                        .setWifiApActiveSessionDataLimit(
                                                                this.this$0.mContext,
                                                                (long)
                                                                        ((double)
                                                                                inputDataInDataUsageConfig
                                                                                        .mUsageValueInBytes));
                                                try {
                                                    Thread.sleep(100L);
                                                } catch (InterruptedException e) {
                                                    Thread.currentThread().interrupt();
                                                    e.printStackTrace();
                                                }
                                                this.this$0.startProvisioningIfNecessary();
                                                break;
                                            } else {
                                                Log.i(
                                                        "WifiApWarning",
                                                        "Error Settings Global data limit : "
                                                                + inputDataInDataUsageConfig
                                                                        .getUsageValueInMB());
                                                this.this$0.sendBroadcastEnablingHotspotCancel();
                                                Toast.makeText(
                                                                this.this$0.mContext,
                                                                "You can'\t set less than the"
                                                                    + " amount of data you used."
                                                                    + " Please enter again.",
                                                                0)
                                                        .show();
                                                this.this$0.finish();
                                                break;
                                            }
                                        case 7:
                                            WifiApConnectedDeviceUtils
                                                    .setWifiApActiveSessionDataLimit(
                                                            this.this$0.mContext, 0L);
                                            try {
                                                Thread.sleep(100L);
                                            } catch (InterruptedException e2) {
                                                Thread.currentThread().interrupt();
                                                e2.printStackTrace();
                                            }
                                            this.this$0.startProvisioningIfNecessary();
                                            break;
                                        case 8:
                                            WifiApWarning wifiApWarning4 = this.this$0;
                                            boolean z5 = WifiApWarning.DBG;
                                            wifiApWarning4.sendBroadcastEnablingHotspotCancel();
                                            this.this$0.finish();
                                            break;
                                        default:
                                            WifiApWarning wifiApWarning5 = this.this$0;
                                            boolean z6 = WifiApWarning.DBG;
                                            wifiApWarning5
                                                    .checkFirstTimeWiFiSharingDialogAndProceedFurthur();
                                            break;
                                    }
                                }
                            });
                    int i8 = 1;
                    builder7.setNegativeButton(R.string.dlg_cancel, new AnonymousClass1(this, i8));
                    builder7.setOnCancelListener(new AnonymousClass3(this, i8));
                    builder7.setTitle(R.string.wifi_ap_warn_roaming_title_beyond_vzw);
                } else {
                    builder7.setMessage(
                            this.mContext.getString(R.string.wifi_ap_warn_roaming_msg, "$20.48"));
                    builder7.setPositiveButton(
                            R.string.lockpattern_continue_button_text,
                            new AnonymousClass1(this, 2));
                    builder7.setOnCancelListener(new AnonymousClass3(this, 2));
                    builder7.setTitle(R.string.wifi_ap_warn_roaming_title);
                }
                builder7.create();
                builder7.show();
                collapsePanels();
                break;
            default:
                switch (i) {
                    case 30:
                        if (z) {
                            this.mContext =
                                    new ContextThemeWrapper(
                                            this.mContext, android.R.style.Theme.DeviceDefault);
                        } else {
                            this.mContext =
                                    new ContextThemeWrapper(
                                            this.mContext,
                                            android.R.style.Theme.DeviceDefault.Light);
                        }
                        AlertDialog.Builder builder8 =
                                new AlertDialog.Builder(this, this.mDialogTheme);
                        Log.d(
                                "WifiApWarning",
                                "Setting the DB val :settings.Secure.WIFI_AP_DUAL_POPUP to 1");
                        Settings.Secure.putInt(
                                this.mContext.getContentResolver(), "wifi_ap_dual_popup", 1);
                        builder8.setMessage(
                                WifiApUtils.getString(
                                        this.mContext, R.string.wifi_ap_wifi_off_warn));
                        builder8.setPositiveButton(
                                WifiApUtils.getString(this.mContext, R.string.positive_button_off),
                                new AnonymousClass1(this, 18));
                        builder8.setNegativeButton(
                                R.string.dlg_cancel, new AnonymousClass1(this, 19));
                        builder8.setOnCancelListener(new AnonymousClass3(this, 10));
                        KeyguardManager keyguardManager =
                                (KeyguardManager) getSystemService("keyguard");
                        if (keyguardManager != null && keyguardManager.isKeyguardLocked()) {
                            Log.i("WifiApWarning", "showNextHotspotDialog: Keyguard is locked");
                            if (!keyguardManager.isKeyguardSecure()) {
                                Log.i("WifiApWarning", "showNextHotspotDialog: Non secure lock");
                                keyguardManager.requestDismissKeyguard(
                                        this, new AnonymousClass40());
                            }
                        }
                        collapsePanels();
                        builder8.setTitle(
                                WifiApUtils.getString(
                                        this.mContext, R.string.wifi_ap_wifi_off_warn_title));
                        builder8.create();
                        builder8.show();
                        break;
                    case 31:
                        AlertDialog.Builder builder9 =
                                new AlertDialog.Builder(this, this.mDialogTheme);
                        builder9.setMessage(
                                WifiApUtils.getString(
                                        this.mContext, R.string.wifi_ap_wifi_p2p_off_warn));
                        builder9.setPositiveButton(
                                R.string.turn_on_button, new AnonymousClass1(this, 26));
                        builder9.setNegativeButton(
                                R.string.dlg_cancel, new AnonymousClass1(this, 27));
                        builder9.setOnCancelListener(new AnonymousClass3(this, 16));
                        builder9.setTitle(
                                WifiApUtils.getStringID(R.string.wifi_ap_wifi_p2p_off_warn_title));
                        builder9.create();
                        builder9.show();
                        collapsePanels();
                        break;
                    case 32:
                        SemWifiManager semWifiManager = this.mSemWifiManager;
                        if (semWifiManager != null) {
                            semWifiManager.setWifiApEnabled((SoftApConfiguration) null, true);
                            break;
                        }
                        break;
                    default:
                        switch (i) {
                            case 40:
                                break;
                            case 41:
                                AlertDialog.Builder builder10 =
                                        new AlertDialog.Builder(this, this.mDialogTheme);
                                builder10.setMessage(
                                        WifiApUtils.getString(
                                                this.mContext,
                                                R.string
                                                        .wifi_ap_wifi_sharinglite_24ghz_client_mhs_on_text));
                                final int i9 = 5;
                                builder10.setPositiveButton(
                                        R.string.dlg_ok,
                                        new DialogInterface.OnClickListener(this) { // from class:
                                            // com.samsung.android.settings.wifi.mobileap.WifiApWarning.7
                                            public final /* synthetic */ WifiApWarning this$0;

                                            {
                                                this.this$0 = this;
                                            }

                                            @Override // android.content.DialogInterface.OnClickListener
                                            public final void onClick(
                                                    DialogInterface dialogInterface, int i52) {
                                                switch (i9) {
                                                    case 0:
                                                        SALogging.insertSALog("TETH_010", "8035");
                                                        WifiApWarning wifiApWarning = this.this$0;
                                                        boolean z2 = WifiApWarning.DBG;
                                                        wifiApWarning
                                                                .sendBroadcastEnablingHotspotCancel();
                                                        this.this$0.finish();
                                                        break;
                                                    case 1:
                                                        SALogging.insertSALog("TETH_010", "8063");
                                                        Log.d("WifiApWarning", "AP sta disconnect");
                                                        Settings.Secure.putInt(
                                                                this.this$0.mContext
                                                                        .getContentResolver(),
                                                                "wifi_ap_wifi_sharing",
                                                                0);
                                                        WifiManager wifiManager =
                                                                this.this$0.mAOSPWifiManager;
                                                        if (wifiManager != null) {
                                                            wifiManager.setWifiEnabled(false);
                                                            this.this$0.mSemWifiManager.resetSoftAp(
                                                                    new Message());
                                                        }
                                                        this.this$0.finish();
                                                        break;
                                                    case 2:
                                                        SALogging.insertSALog("TETH_010", "8062");
                                                        this.this$0.finish();
                                                        break;
                                                    case 3:
                                                        SALogging.insertSALog("TETH_010", "8066");
                                                        Context context = this.this$0.mContext;
                                                        ((SemWifiManager)
                                                                        context.getSystemService(
                                                                                WiFiManagerExt
                                                                                        .SEM_WIFI_SERVICE))
                                                                .reportIssue(
                                                                        100,
                                                                        WifiIssueDetectorUtil
                                                                                .ReportUtil
                                                                                .getReportDataForWifiManagerApi(
                                                                                        -1,
                                                                                        "disconnect",
                                                                                        context.getPackageManager()
                                                                                                .getNameForUid(
                                                                                                        context
                                                                                                                .getUserId()),
                                                                                        context
                                                                                                .getPackageName()));
                                                        this.this$0.mAOSPWifiManager.disconnect();
                                                        this.this$0.startProvisioningIfNecessary();
                                                        break;
                                                    case 4:
                                                        SALogging.insertSALog("TETH_010", "8065");
                                                        WifiApWarning wifiApWarning2 = this.this$0;
                                                        boolean z3 = WifiApWarning.DBG;
                                                        wifiApWarning2
                                                                .sendBroadcastEnablingHotspotCancel();
                                                        this.this$0.finish();
                                                        break;
                                                    case 5:
                                                        SALogging.insertSALog("TETH_010", "8064");
                                                        WifiApWarning wifiApWarning3 = this.this$0;
                                                        boolean z4 = WifiApWarning.DBG;
                                                        wifiApWarning3
                                                                .startProvisioningIfNecessary();
                                                        break;
                                                    case 6:
                                                        WifiApDataUsageConfig
                                                                inputDataInDataUsageConfig =
                                                                        this.this$0
                                                                                .getInputDataInDataUsageConfig();
                                                        if (inputDataInDataUsageConfig
                                                                        .getUsageValueInMB()
                                                                >= WifiApConnectedDeviceUtils
                                                                        .getWifiApTodayTotalDataUsage(
                                                                                this.this$0
                                                                                        .mContext)
                                                                        .getUsageValueInMB()) {
                                                            Log.i(
                                                                    "WifiApWarning",
                                                                    "Settings Global data limit : "
                                                                            + (inputDataInDataUsageConfig
                                                                                            .mUsageValueInBytes
                                                                                    / 1000.0d));
                                                            WifiApConnectedDeviceUtils
                                                                    .setWifiApActiveSessionDataLimit(
                                                                            this.this$0.mContext,
                                                                            (long)
                                                                                    ((double)
                                                                                            inputDataInDataUsageConfig
                                                                                                    .mUsageValueInBytes));
                                                            try {
                                                                Thread.sleep(100L);
                                                            } catch (InterruptedException e) {
                                                                Thread.currentThread().interrupt();
                                                                e.printStackTrace();
                                                            }
                                                            this.this$0
                                                                    .startProvisioningIfNecessary();
                                                            break;
                                                        } else {
                                                            Log.i(
                                                                    "WifiApWarning",
                                                                    "Error Settings Global data"
                                                                        + " limit : "
                                                                            + inputDataInDataUsageConfig
                                                                                    .getUsageValueInMB());
                                                            this.this$0
                                                                    .sendBroadcastEnablingHotspotCancel();
                                                            Toast.makeText(
                                                                            this.this$0.mContext,
                                                                            "You can'\t set less"
                                                                                + " than the amount"
                                                                                + " of data you"
                                                                                + " used. Please"
                                                                                + " enter again.",
                                                                            0)
                                                                    .show();
                                                            this.this$0.finish();
                                                            break;
                                                        }
                                                    case 7:
                                                        WifiApConnectedDeviceUtils
                                                                .setWifiApActiveSessionDataLimit(
                                                                        this.this$0.mContext, 0L);
                                                        try {
                                                            Thread.sleep(100L);
                                                        } catch (InterruptedException e2) {
                                                            Thread.currentThread().interrupt();
                                                            e2.printStackTrace();
                                                        }
                                                        this.this$0.startProvisioningIfNecessary();
                                                        break;
                                                    case 8:
                                                        WifiApWarning wifiApWarning4 = this.this$0;
                                                        boolean z5 = WifiApWarning.DBG;
                                                        wifiApWarning4
                                                                .sendBroadcastEnablingHotspotCancel();
                                                        this.this$0.finish();
                                                        break;
                                                    default:
                                                        WifiApWarning wifiApWarning5 = this.this$0;
                                                        boolean z6 = WifiApWarning.DBG;
                                                        wifiApWarning5
                                                                .checkFirstTimeWiFiSharingDialogAndProceedFurthur();
                                                        break;
                                                }
                                            }
                                        });
                                builder10.setOnCancelListener(new AnonymousClass3(this, 21));
                                builder10.setTitle(
                                        WifiApUtils.getString(
                                                this.mContext,
                                                R.string
                                                        .wifi_ap_wifi_sharinglite_mobile_hotspot_not_available));
                                builder10.create();
                                builder10.show();
                                collapsePanels();
                                break;
                            case 42:
                                Log.i(
                                        "WifiApWarning",
                                        "DIALOG_WARN_LOW_BATTERY_ST create " + this.m_ST_SSId);
                                AlertDialog.Builder builder11 =
                                        new AlertDialog.Builder(this, this.mDialogTheme);
                                builder11.setMessage(
                                        String.format(
                                                WifiApUtils.getString(
                                                        this.mContext,
                                                        R.string
                                                                .smart_tethering_connected_ap_summary),
                                                this.m_ST_SSId));
                                builder11.setPositiveButton(
                                        R.string
                                                .smart_tethering_low_battery_dialog_disconnect_device,
                                        new AnonymousClass1(this, 5));
                                builder11.setNegativeButton(
                                        R.string.smart_tethering_dialog_cancel,
                                        new AnonymousClass1(this, 6));
                                builder11.setOnCancelListener(new AnonymousClass3(this, 4));
                                builder11.setTitle(
                                        String.format(
                                                WifiApUtils.getString(
                                                        this.mContext,
                                                        R.string
                                                                .smart_tethering_connected_ap_title),
                                                this.m_ST_SSId));
                                builder11.create();
                                builder11.show();
                                collapsePanels();
                                break;
                            case 43:
                                this.msendIntentToAutoHotspotD2DFamilyUpdate = true;
                                Log.i(
                                        "WifiApWarning",
                                        "DIALOG_D2D_FAMILYID_ACCEPT create "
                                                + this.m_ST_D2D_Devicename
                                                + ",hash:"
                                                + this.m_ST_D2D_FamilyIDHash
                                                + ",m_ST_D2D_WiFiMAC:"
                                                + this.m_ST_D2D_WiFiMAC);
                                PowerManager powerManager =
                                        (PowerManager) getSystemService("power");
                                this.mWakeLock =
                                        powerManager.newWakeLock(805306394, "WifiApWarning");
                                this.mPartialWakeLock =
                                        powerManager.newWakeLock(1, "WifiApWarning");
                                this.mScreenWakeLock =
                                        powerManager.newWakeLock(10, "WifiApWarning");
                                this.mWakeLock.acquire();
                                if (this.mWakeLock.isHeld()) {
                                    this.mPartialWakeLock.acquire();
                                    this.mScreenWakeLock.acquire();
                                    getWindow().addFlags(6815872);
                                }
                                AlertDialog.Builder builder12 =
                                        new AlertDialog.Builder(this, this.mDialogTheme);
                                builder12.setMessage(R.string.wifi_ap_d2d_new_family_group_join);
                                builder12.setPositiveButton(
                                        R.string.wifi_ap_family_accept_dialog_ok,
                                        new AnonymousClass1(this, 7));
                                builder12.setNegativeButton(
                                        R.string.smart_tethering_dialog_cancel,
                                        new AnonymousClass1(this, 8));
                                builder12.setOnCancelListener(new AnonymousClass3(this, 5));
                                builder12.setOnDismissListener(
                                        new DialogInterface
                                                .OnDismissListener() { // from class:
                                                                       // com.samsung.android.settings.wifi.mobileap.WifiApWarning.23
                                            @Override // android.content.DialogInterface.OnDismissListener
                                            public final void onDismiss(
                                                    DialogInterface dialogInterface) {
                                                Log.d("WifiApWarning", "onDismiss");
                                                WifiApWarning
                                                        .m1361$$Nest$msendIntentToAutoHotspotD2DFamilyUpdate(
                                                                WifiApWarning.this, false);
                                                WifiApWarning.this.finish();
                                            }
                                        });
                                builder12.setTitle(R.string.wifi_ap_d2d_update_family);
                                builder12.create();
                                builder12.show();
                                final int i10 = 0;
                                this.mCountdownTimer =
                                        new CountDownTimer(this) { // from class:
                                            // com.samsung.android.settings.wifi.mobileap.WifiApWarning.24
                                            public final /* synthetic */ WifiApWarning this$0;

                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(30000L, 1000L);
                                                this.this$0 = this;
                                            }

                                            @Override // android.os.CountDownTimer
                                            public final void onFinish() {
                                                switch (i10) {
                                                    case 0:
                                                        Log.d("WifiApWarning", "onFinish");
                                                        WifiApWarning
                                                                .m1361$$Nest$msendIntentToAutoHotspotD2DFamilyUpdate(
                                                                        this.this$0, false);
                                                        this.this$0.finish();
                                                        break;
                                                    default:
                                                        Log.d(
                                                                "WifiApWarning",
                                                                "isClientAcceptedWifiProfileSharing"
                                                                    + " , onFinish");
                                                        this.this$0.mSemWifiManager
                                                                .isClientAcceptedWifiProfileSharing(
                                                                        false);
                                                        this.this$0.finish();
                                                        break;
                                                }
                                            }

                                            @Override // android.os.CountDownTimer
                                            public final void onTick(long j) {
                                                int i11 = i10;
                                            }

                                            private final void
                                                    onTick$com$samsung$android$settings$wifi$mobileap$WifiApWarning$24(
                                                            long j) {}

                                            private final void
                                                    onTick$com$samsung$android$settings$wifi$mobileap$WifiApWarning$46(
                                                            long j) {}
                                        }.start();
                                collapsePanels();
                                break;
                            default:
                                switch (i) {
                                    case 54:
                                        Log.i(
                                                "WifiApWarning",
                                                "CASE DIALOG_WARN_DUALAP_WIFI_DISABLE");
                                        new AlertDialog.Builder(this, this.mDialogTheme)
                                                .setMessage(R.string.wifi_ap_dualap_wifi_off_warn)
                                                .setPositiveButton(
                                                        WifiApUtils.getString(
                                                                this.mContext,
                                                                R.string.positive_button_off),
                                                        new AnonymousClass1(this, 15))
                                                .setNegativeButton(
                                                        R.string.dlg_cancel,
                                                        new AnonymousClass1(this, 14))
                                                .setOnCancelListener(new AnonymousClass3(this, 8))
                                                .setTitle(
                                                        WifiApUtils.getString(
                                                                this.mContext,
                                                                R.string
                                                                        .wifi_ap_wifi_off_warn_title))
                                                .create()
                                                .show();
                                        collapsePanels();
                                        break;
                                    case 55:
                                        Log.i(
                                                "WifiApWarning",
                                                "DIALOG_WARN_ALLOW_AUTOHOTSPOT_DISCOVERY 2");
                                        String stringExtra =
                                                getIntent()
                                                        .getStringExtra(
                                                                "wifiap_adv_autohotspot_ssid");
                                        String stringExtra2 =
                                                getIntent()
                                                        .getStringExtra(
                                                                "wifiap_adv_autohotspot_mac");
                                        this.mSemWifiManager =
                                                (SemWifiManager)
                                                        this.mContext.getSystemService(
                                                                WiFiManagerExt.SEM_WIFI_SERVICE);
                                        String string =
                                                this.mContext
                                                        .getResources()
                                                        .getString(
                                                                R.string
                                                                        .wifi_ap_advanced_hotspot_popup_message,
                                                                stringExtra);
                                        String string2 =
                                                WifiApUtils.getString(
                                                        this.mContext,
                                                        R.string
                                                                .wifi_ap_auto_hotspot_connection_settings_link_text);
                                        AlertDialog.Builder builder13 =
                                                new AlertDialog.Builder(this, this.mDialogTheme);
                                        builder13.setMessage(ApnSettings.MVNO_NONE);
                                        builder13.setPositiveButton(
                                                R.string.sec_bluetooth_connect,
                                                new AnonymousClass47(stringExtra2, stringExtra));
                                        builder13.setMessage(
                                                this.mContext
                                                        .getResources()
                                                        .getString(
                                                                R.string
                                                                        .wifi_ap_advanced_hotspot_popup_message,
                                                                stringExtra));
                                        builder13.setNegativeButton(
                                                R.string.dlg_cancel, new AnonymousClass1(this, 23));
                                        builder13.setOnCancelListener(
                                                new AnonymousClass3(this, 13));
                                        builder13.setTitle(
                                                this.mContext
                                                        .getResources()
                                                        .getString(
                                                                WifiApUtils.getStringID(
                                                                        R.string
                                                                                .wifi_ap_advanced_hotspot_popup_title),
                                                                stringExtra));
                                        AlertDialog create = builder13.create();
                                        this.allowAutohotspotDiscoveryDialogBox = create;
                                        create.show();
                                        SpannableString spannableString =
                                                new SpannableString(
                                                        AbstractAppLabelMapFactory$$ExternalSyntheticOutline0
                                                                .m(string, "\n", string2));
                                        spannableString.setSpan(
                                                new AutoHotspotConnectionClickable(
                                                        this.allowAutohotspotDiscoveryDialogBox,
                                                        false,
                                                        true),
                                                string.length() + 1,
                                                string2.length() + string.length() + 1,
                                                33);
                                        ((TextView)
                                                        this.allowAutohotspotDiscoveryDialogBox
                                                                .findViewById(android.R.id.message))
                                                .setText(spannableString);
                                        ((TextView)
                                                        this.allowAutohotspotDiscoveryDialogBox
                                                                .findViewById(android.R.id.message))
                                                .setMovementMethod(
                                                        LinkMovementMethod.getInstance());
                                        collapsePanels();
                                        break;
                                    case 56:
                                        Log.i(
                                                "WifiApWarning",
                                                "DIALOG_WARN_AUTO_HOTSPOT_DISCONNECTED 2");
                                        String string3 =
                                                this.mContext.getString(
                                                        WifiApUtils.getStringID(
                                                                Utils.isTablet()
                                                                        ? R.string
                                                                                .wifi_ap_auto_hotspot_disconnected_dialog_text_tablet
                                                                        : R.string
                                                                                .wifi_ap_auto_hotspot_disconnected_dialog_text_phone));
                                        String string4 =
                                                WifiApUtils.getString(
                                                        this.mContext,
                                                        R.string
                                                                .wifi_ap_auto_hotspot_connection_settings_link_text);
                                        String format = String.format(string3, string4);
                                        AlertDialog.Builder builder14 =
                                                new AlertDialog.Builder(this, this.mDialogTheme);
                                        builder14.setTitle(
                                                WifiApUtils.getStringID(
                                                        R.string
                                                                .wifi_ap_auto_hotspot_disconnected_dialog_title));
                                        builder14.setMessage(ApnSettings.MVNO_NONE);
                                        builder14.setCancelable(true);
                                        builder14.setPositiveButton(
                                                R.string.dlg_ok, new AnonymousClass1(this, 24));
                                        builder14.setOnCancelListener(
                                                new AnonymousClass3(this, 14));
                                        AlertDialog create2 = builder14.create();
                                        this.autoHotspotDisconnectedDialogBox = create2;
                                        create2.show();
                                        SpannableString spannableString2 =
                                                new SpannableString(format);
                                        int indexOf = string3.indexOf("%s");
                                        spannableString2.setSpan(
                                                new AutoHotspotConnectionClickable(
                                                        this.autoHotspotDisconnectedDialogBox,
                                                        true,
                                                        true),
                                                indexOf,
                                                string4.length() + indexOf,
                                                33);
                                        ((TextView)
                                                        this.autoHotspotDisconnectedDialogBox
                                                                .findViewById(android.R.id.message))
                                                .setText(spannableString2);
                                        ((TextView)
                                                        this.autoHotspotDisconnectedDialogBox
                                                                .findViewById(android.R.id.message))
                                                .setMovementMethod(
                                                        LinkMovementMethod.getInstance());
                                        collapsePanels();
                                        break;
                                    case 57:
                                        Log.i(
                                                "WifiApWarning",
                                                "DIALOG_WARN_AUTO_HOTSPOT_CONNECTION_FIRST_TIME 2");
                                        String string5 =
                                                WifiApUtils.getString(
                                                        this.mContext,
                                                        R.string
                                                                .wifi_ap_auto_hotspot_connection_settings_link_text);
                                        String string6 =
                                                WifiApUtils.getString(
                                                        this.mContext,
                                                        R.string
                                                                .wifi_ap_auto_hotspot_connection_dialog_body);
                                        AlertDialog.Builder builder15 =
                                                new AlertDialog.Builder(this, this.mDialogTheme);
                                        builder15.setTitle(
                                                WifiApUtils.getString(
                                                        this.mContext,
                                                        R.string.wifi_auto_connect_hotspot_title));
                                        builder15.setMessage(ApnSettings.MVNO_NONE);
                                        builder15.setCancelable(true);
                                        builder15.setPositiveButton(
                                                R.string.dlg_ok, new AnonymousClass1(this, 25));
                                        builder15.setOnCancelListener(
                                                new AnonymousClass3(this, 15));
                                        AlertDialog create3 = builder15.create();
                                        this.autoHotspotConnectionDialogBox = create3;
                                        create3.show();
                                        SpannableString spannableString3 =
                                                new SpannableString(
                                                        AbstractAppLabelMapFactory$$ExternalSyntheticOutline0
                                                                .m(string6, "\n", string5));
                                        spannableString3.setSpan(
                                                new AutoHotspotConnectionClickable(
                                                        this.autoHotspotConnectionDialogBox,
                                                        false,
                                                        false),
                                                string6.length() + 1,
                                                string5.length() + string6.length() + 1,
                                                33);
                                        ((TextView)
                                                        this.autoHotspotConnectionDialogBox
                                                                .findViewById(android.R.id.message))
                                                .setText(spannableString3);
                                        ((TextView)
                                                        this.autoHotspotConnectionDialogBox
                                                                .findViewById(android.R.id.message))
                                                .setMovementMethod(
                                                        LinkMovementMethod.getInstance());
                                        collapsePanels();
                                        break;
                                    case 58:
                                        String stringExtra3 =
                                                getIntent().getStringExtra("mhs_wifi_network_name");
                                        String str = stringExtra3 != null ? stringExtra3 : " ";
                                        AlertDialog.Builder builder16 =
                                                new AlertDialog.Builder(this, this.mDialogTheme);
                                        builder16.setMessage(
                                                String.format(
                                                        WifiApUtils.getString(
                                                                this.mContext,
                                                                R.string
                                                                        .wifi_ap_wifi_profile_share_network_available_msg),
                                                        str));
                                        builder16.setPositiveButton(
                                                R.string.sec_bluetooth_connect,
                                                new AnonymousClass1(this, 21));
                                        builder16.setNegativeButton(
                                                R.string.dlg_cancel, new AnonymousClass1(this, 22));
                                        builder16.setOnCancelListener(
                                                new AnonymousClass3(this, 12));
                                        builder16.setTitle(
                                                R.string
                                                        .wifi_ap_wifi_profile_share_network_available_title);
                                        builder16.create();
                                        builder16.show();
                                        collapsePanels();
                                        final int i11 = 1;
                                        this.mCountdownTimer =
                                                new CountDownTimer(this) { // from class:
                                                    // com.samsung.android.settings.wifi.mobileap.WifiApWarning.24
                                                    public final /* synthetic */ WifiApWarning
                                                            this$0;

                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    {
                                                        super(30000L, 1000L);
                                                        this.this$0 = this;
                                                    }

                                                    @Override // android.os.CountDownTimer
                                                    public final void onFinish() {
                                                        switch (i11) {
                                                            case 0:
                                                                Log.d("WifiApWarning", "onFinish");
                                                                WifiApWarning
                                                                        .m1361$$Nest$msendIntentToAutoHotspotD2DFamilyUpdate(
                                                                                this.this$0, false);
                                                                this.this$0.finish();
                                                                break;
                                                            default:
                                                                Log.d(
                                                                        "WifiApWarning",
                                                                        "isClientAcceptedWifiProfileSharing"
                                                                            + " , onFinish");
                                                                this.this$0.mSemWifiManager
                                                                        .isClientAcceptedWifiProfileSharing(
                                                                                false);
                                                                this.this$0.finish();
                                                                break;
                                                        }
                                                    }

                                                    @Override // android.os.CountDownTimer
                                                    public final void onTick(long j) {
                                                        int i112 = i11;
                                                    }

                                                    private final void
                                                            onTick$com$samsung$android$settings$wifi$mobileap$WifiApWarning$24(
                                                                    long j) {}

                                                    private final void
                                                            onTick$com$samsung$android$settings$wifi$mobileap$WifiApWarning$46(
                                                                    long j) {}
                                                }.start();
                                        break;
                                    case 59:
                                        AlertDialog.Builder builder17 =
                                                new AlertDialog.Builder(this, this.mDialogTheme);
                                        builder17.setTitle(R.string.wifi_ap_dun_fail_title);
                                        String string7 =
                                                WifiApUtils.getString(
                                                        this.mContext,
                                                        R.string.wifi_ap_dun_fail_msg1);
                                        String string8 =
                                                WifiApUtils.getString(
                                                        this.mContext,
                                                        R.string.wifi_ap_dun_fail_msg2);
                                        String string9 =
                                                WifiApUtils.getString(
                                                        this.mContext,
                                                        R.string.wifi_ap_dun_fail_msg3);
                                        String string10 =
                                                WifiApUtils.getString(
                                                        this.mContext,
                                                        R.string.wifi_ap_dun_fail_msg4);
                                        SpannableStringBuilder spannableStringBuilder =
                                                new SpannableStringBuilder();
                                        spannableStringBuilder.append(
                                                (CharSequence) new SpannableString(string7));
                                        spannableStringBuilder.append((CharSequence) " ");
                                        spannableStringBuilder.append(
                                                (CharSequence) new SpannableString(string8));
                                        spannableStringBuilder.append((CharSequence) " ");
                                        spannableStringBuilder.append(
                                                (CharSequence) new SpannableString(string9));
                                        spannableStringBuilder.append((CharSequence) " ");
                                        spannableStringBuilder.append(
                                                (CharSequence) new SpannableString(string10));
                                        builder17.setMessage(spannableStringBuilder);
                                        builder17.setPositiveButton(
                                                R.string.dlg_ok, new AnonymousClass1(this, 20));
                                        builder17.setOnCancelListener(
                                                new AnonymousClass3(this, 11));
                                        builder17.create();
                                        builder17.show();
                                        break;
                                    case 60:
                                        WifiApDataUsageConfig wifiApActiveSessionDataLimit =
                                                WifiApConnectedDeviceUtils
                                                        .getWifiApActiveSessionDataLimit(
                                                                this.mContext);
                                        int i12 = this.mDialogTheme;
                                        if ((getResources().getConfiguration().uiMode & 48) == 32) {
                                            this.mContext =
                                                    new ContextThemeWrapper(
                                                            this.mContext,
                                                            android.R.style.Theme.DeviceDefault);
                                        } else {
                                            this.mContext =
                                                    new ContextThemeWrapper(
                                                            this.mContext,
                                                            android.R.style
                                                                    .Theme
                                                                    .DeviceDefault
                                                                    .Light);
                                        }
                                        AlertDialog.Builder builder18 =
                                                new AlertDialog.Builder(this, i12);
                                        View inflate2 =
                                                View.inflate(
                                                        this.mContext,
                                                        R.layout.sec_wifi_ap_set_data_limit_dialog1,
                                                        null);
                                        builder18.setView(inflate2);
                                        ((TextView)
                                                        inflate2.findViewById(
                                                                R.id.data_limit_message_textview))
                                                .setText(
                                                        R.string
                                                                .wifi_ap_you_have_already_used_hotspot_data_today);
                                        EditText editText =
                                                (EditText)
                                                        inflate2.findViewById(
                                                                R.id.data_limit_edittext);
                                        this.mDataLimitEditText = editText;
                                        editText.addTextChangedListener(
                                                this.mDataLimitEditTextWatcher);
                                        this.mDataLimitEditText.setText(
                                                String.valueOf(
                                                        (int)
                                                                wifiApActiveSessionDataLimit
                                                                        .getUsageValueInMB()));
                                        this.mDataLimitEditText.selectAll();
                                        this.mDataLimitEditErrorTextView =
                                                (TextView)
                                                        inflate2.findViewById(
                                                                R.id
                                                                        .data_limit_edit_error_textview);
                                        builder18.setTitle(R.string.wifi_ap_data_limit_reached);
                                        final int i13 = 6;
                                        builder18.setPositiveButton(
                                                R.string.wifi_ap_change_limit,
                                                new DialogInterface.OnClickListener(
                                                        this) { // from class:
                                                    // com.samsung.android.settings.wifi.mobileap.WifiApWarning.7
                                                    public final /* synthetic */ WifiApWarning
                                                            this$0;

                                                    {
                                                        this.this$0 = this;
                                                    }

                                                    @Override // android.content.DialogInterface.OnClickListener
                                                    public final void onClick(
                                                            DialogInterface dialogInterface,
                                                            int i52) {
                                                        switch (i13) {
                                                            case 0:
                                                                SALogging.insertSALog(
                                                                        "TETH_010", "8035");
                                                                WifiApWarning wifiApWarning =
                                                                        this.this$0;
                                                                boolean z2 = WifiApWarning.DBG;
                                                                wifiApWarning
                                                                        .sendBroadcastEnablingHotspotCancel();
                                                                this.this$0.finish();
                                                                break;
                                                            case 1:
                                                                SALogging.insertSALog(
                                                                        "TETH_010", "8063");
                                                                Log.d(
                                                                        "WifiApWarning",
                                                                        "AP sta disconnect");
                                                                Settings.Secure.putInt(
                                                                        this.this$0.mContext
                                                                                .getContentResolver(),
                                                                        "wifi_ap_wifi_sharing",
                                                                        0);
                                                                WifiManager wifiManager =
                                                                        this.this$0
                                                                                .mAOSPWifiManager;
                                                                if (wifiManager != null) {
                                                                    wifiManager.setWifiEnabled(
                                                                            false);
                                                                    this.this$0.mSemWifiManager
                                                                            .resetSoftAp(
                                                                                    new Message());
                                                                }
                                                                this.this$0.finish();
                                                                break;
                                                            case 2:
                                                                SALogging.insertSALog(
                                                                        "TETH_010", "8062");
                                                                this.this$0.finish();
                                                                break;
                                                            case 3:
                                                                SALogging.insertSALog(
                                                                        "TETH_010", "8066");
                                                                Context context =
                                                                        this.this$0.mContext;
                                                                ((SemWifiManager)
                                                                                context
                                                                                        .getSystemService(
                                                                                                WiFiManagerExt
                                                                                                        .SEM_WIFI_SERVICE))
                                                                        .reportIssue(
                                                                                100,
                                                                                WifiIssueDetectorUtil
                                                                                        .ReportUtil
                                                                                        .getReportDataForWifiManagerApi(
                                                                                                -1,
                                                                                                "disconnect",
                                                                                                context.getPackageManager()
                                                                                                        .getNameForUid(
                                                                                                                context
                                                                                                                        .getUserId()),
                                                                                                context
                                                                                                        .getPackageName()));
                                                                this.this$0.mAOSPWifiManager
                                                                        .disconnect();
                                                                this.this$0
                                                                        .startProvisioningIfNecessary();
                                                                break;
                                                            case 4:
                                                                SALogging.insertSALog(
                                                                        "TETH_010", "8065");
                                                                WifiApWarning wifiApWarning2 =
                                                                        this.this$0;
                                                                boolean z3 = WifiApWarning.DBG;
                                                                wifiApWarning2
                                                                        .sendBroadcastEnablingHotspotCancel();
                                                                this.this$0.finish();
                                                                break;
                                                            case 5:
                                                                SALogging.insertSALog(
                                                                        "TETH_010", "8064");
                                                                WifiApWarning wifiApWarning3 =
                                                                        this.this$0;
                                                                boolean z4 = WifiApWarning.DBG;
                                                                wifiApWarning3
                                                                        .startProvisioningIfNecessary();
                                                                break;
                                                            case 6:
                                                                WifiApDataUsageConfig
                                                                        inputDataInDataUsageConfig =
                                                                                this.this$0
                                                                                        .getInputDataInDataUsageConfig();
                                                                if (inputDataInDataUsageConfig
                                                                                .getUsageValueInMB()
                                                                        >= WifiApConnectedDeviceUtils
                                                                                .getWifiApTodayTotalDataUsage(
                                                                                        this.this$0
                                                                                                .mContext)
                                                                                .getUsageValueInMB()) {
                                                                    Log.i(
                                                                            "WifiApWarning",
                                                                            "Settings Global data"
                                                                                + " limit : "
                                                                                    + (inputDataInDataUsageConfig
                                                                                                    .mUsageValueInBytes
                                                                                            / 1000.0d));
                                                                    WifiApConnectedDeviceUtils
                                                                            .setWifiApActiveSessionDataLimit(
                                                                                    this.this$0
                                                                                            .mContext,
                                                                                    (long)
                                                                                            ((double)
                                                                                                    inputDataInDataUsageConfig
                                                                                                            .mUsageValueInBytes));
                                                                    try {
                                                                        Thread.sleep(100L);
                                                                    } catch (
                                                                            InterruptedException
                                                                                    e) {
                                                                        Thread.currentThread()
                                                                                .interrupt();
                                                                        e.printStackTrace();
                                                                    }
                                                                    this.this$0
                                                                            .startProvisioningIfNecessary();
                                                                    break;
                                                                } else {
                                                                    Log.i(
                                                                            "WifiApWarning",
                                                                            "Error Settings Global"
                                                                                + " data limit : "
                                                                                    + inputDataInDataUsageConfig
                                                                                            .getUsageValueInMB());
                                                                    this.this$0
                                                                            .sendBroadcastEnablingHotspotCancel();
                                                                    Toast.makeText(
                                                                                    this.this$0
                                                                                            .mContext,
                                                                                    "You can'\t set"
                                                                                        + " less"
                                                                                        + " than"
                                                                                        + " the amount"
                                                                                        + " of data"
                                                                                        + " you used."
                                                                                        + " Please"
                                                                                        + " enter"
                                                                                        + " again.",
                                                                                    0)
                                                                            .show();
                                                                    this.this$0.finish();
                                                                    break;
                                                                }
                                                            case 7:
                                                                WifiApConnectedDeviceUtils
                                                                        .setWifiApActiveSessionDataLimit(
                                                                                this.this$0
                                                                                        .mContext,
                                                                                0L);
                                                                try {
                                                                    Thread.sleep(100L);
                                                                } catch (InterruptedException e2) {
                                                                    Thread.currentThread()
                                                                            .interrupt();
                                                                    e2.printStackTrace();
                                                                }
                                                                this.this$0
                                                                        .startProvisioningIfNecessary();
                                                                break;
                                                            case 8:
                                                                WifiApWarning wifiApWarning4 =
                                                                        this.this$0;
                                                                boolean z5 = WifiApWarning.DBG;
                                                                wifiApWarning4
                                                                        .sendBroadcastEnablingHotspotCancel();
                                                                this.this$0.finish();
                                                                break;
                                                            default:
                                                                WifiApWarning wifiApWarning5 =
                                                                        this.this$0;
                                                                boolean z6 = WifiApWarning.DBG;
                                                                wifiApWarning5
                                                                        .checkFirstTimeWiFiSharingDialogAndProceedFurthur();
                                                                break;
                                                        }
                                                    }
                                                });
                                        final int i14 = 7;
                                        builder18.setNegativeButton(
                                                R.string.data_limit_cancel,
                                                new DialogInterface.OnClickListener(
                                                        this) { // from class:
                                                    // com.samsung.android.settings.wifi.mobileap.WifiApWarning.7
                                                    public final /* synthetic */ WifiApWarning
                                                            this$0;

                                                    {
                                                        this.this$0 = this;
                                                    }

                                                    @Override // android.content.DialogInterface.OnClickListener
                                                    public final void onClick(
                                                            DialogInterface dialogInterface,
                                                            int i52) {
                                                        switch (i14) {
                                                            case 0:
                                                                SALogging.insertSALog(
                                                                        "TETH_010", "8035");
                                                                WifiApWarning wifiApWarning =
                                                                        this.this$0;
                                                                boolean z2 = WifiApWarning.DBG;
                                                                wifiApWarning
                                                                        .sendBroadcastEnablingHotspotCancel();
                                                                this.this$0.finish();
                                                                break;
                                                            case 1:
                                                                SALogging.insertSALog(
                                                                        "TETH_010", "8063");
                                                                Log.d(
                                                                        "WifiApWarning",
                                                                        "AP sta disconnect");
                                                                Settings.Secure.putInt(
                                                                        this.this$0.mContext
                                                                                .getContentResolver(),
                                                                        "wifi_ap_wifi_sharing",
                                                                        0);
                                                                WifiManager wifiManager =
                                                                        this.this$0
                                                                                .mAOSPWifiManager;
                                                                if (wifiManager != null) {
                                                                    wifiManager.setWifiEnabled(
                                                                            false);
                                                                    this.this$0.mSemWifiManager
                                                                            .resetSoftAp(
                                                                                    new Message());
                                                                }
                                                                this.this$0.finish();
                                                                break;
                                                            case 2:
                                                                SALogging.insertSALog(
                                                                        "TETH_010", "8062");
                                                                this.this$0.finish();
                                                                break;
                                                            case 3:
                                                                SALogging.insertSALog(
                                                                        "TETH_010", "8066");
                                                                Context context =
                                                                        this.this$0.mContext;
                                                                ((SemWifiManager)
                                                                                context
                                                                                        .getSystemService(
                                                                                                WiFiManagerExt
                                                                                                        .SEM_WIFI_SERVICE))
                                                                        .reportIssue(
                                                                                100,
                                                                                WifiIssueDetectorUtil
                                                                                        .ReportUtil
                                                                                        .getReportDataForWifiManagerApi(
                                                                                                -1,
                                                                                                "disconnect",
                                                                                                context.getPackageManager()
                                                                                                        .getNameForUid(
                                                                                                                context
                                                                                                                        .getUserId()),
                                                                                                context
                                                                                                        .getPackageName()));
                                                                this.this$0.mAOSPWifiManager
                                                                        .disconnect();
                                                                this.this$0
                                                                        .startProvisioningIfNecessary();
                                                                break;
                                                            case 4:
                                                                SALogging.insertSALog(
                                                                        "TETH_010", "8065");
                                                                WifiApWarning wifiApWarning2 =
                                                                        this.this$0;
                                                                boolean z3 = WifiApWarning.DBG;
                                                                wifiApWarning2
                                                                        .sendBroadcastEnablingHotspotCancel();
                                                                this.this$0.finish();
                                                                break;
                                                            case 5:
                                                                SALogging.insertSALog(
                                                                        "TETH_010", "8064");
                                                                WifiApWarning wifiApWarning3 =
                                                                        this.this$0;
                                                                boolean z4 = WifiApWarning.DBG;
                                                                wifiApWarning3
                                                                        .startProvisioningIfNecessary();
                                                                break;
                                                            case 6:
                                                                WifiApDataUsageConfig
                                                                        inputDataInDataUsageConfig =
                                                                                this.this$0
                                                                                        .getInputDataInDataUsageConfig();
                                                                if (inputDataInDataUsageConfig
                                                                                .getUsageValueInMB()
                                                                        >= WifiApConnectedDeviceUtils
                                                                                .getWifiApTodayTotalDataUsage(
                                                                                        this.this$0
                                                                                                .mContext)
                                                                                .getUsageValueInMB()) {
                                                                    Log.i(
                                                                            "WifiApWarning",
                                                                            "Settings Global data"
                                                                                + " limit : "
                                                                                    + (inputDataInDataUsageConfig
                                                                                                    .mUsageValueInBytes
                                                                                            / 1000.0d));
                                                                    WifiApConnectedDeviceUtils
                                                                            .setWifiApActiveSessionDataLimit(
                                                                                    this.this$0
                                                                                            .mContext,
                                                                                    (long)
                                                                                            ((double)
                                                                                                    inputDataInDataUsageConfig
                                                                                                            .mUsageValueInBytes));
                                                                    try {
                                                                        Thread.sleep(100L);
                                                                    } catch (
                                                                            InterruptedException
                                                                                    e) {
                                                                        Thread.currentThread()
                                                                                .interrupt();
                                                                        e.printStackTrace();
                                                                    }
                                                                    this.this$0
                                                                            .startProvisioningIfNecessary();
                                                                    break;
                                                                } else {
                                                                    Log.i(
                                                                            "WifiApWarning",
                                                                            "Error Settings Global"
                                                                                + " data limit : "
                                                                                    + inputDataInDataUsageConfig
                                                                                            .getUsageValueInMB());
                                                                    this.this$0
                                                                            .sendBroadcastEnablingHotspotCancel();
                                                                    Toast.makeText(
                                                                                    this.this$0
                                                                                            .mContext,
                                                                                    "You can'\t set"
                                                                                        + " less"
                                                                                        + " than"
                                                                                        + " the amount"
                                                                                        + " of data"
                                                                                        + " you used."
                                                                                        + " Please"
                                                                                        + " enter"
                                                                                        + " again.",
                                                                                    0)
                                                                            .show();
                                                                    this.this$0.finish();
                                                                    break;
                                                                }
                                                            case 7:
                                                                WifiApConnectedDeviceUtils
                                                                        .setWifiApActiveSessionDataLimit(
                                                                                this.this$0
                                                                                        .mContext,
                                                                                0L);
                                                                try {
                                                                    Thread.sleep(100L);
                                                                } catch (InterruptedException e2) {
                                                                    Thread.currentThread()
                                                                            .interrupt();
                                                                    e2.printStackTrace();
                                                                }
                                                                this.this$0
                                                                        .startProvisioningIfNecessary();
                                                                break;
                                                            case 8:
                                                                WifiApWarning wifiApWarning4 =
                                                                        this.this$0;
                                                                boolean z5 = WifiApWarning.DBG;
                                                                wifiApWarning4
                                                                        .sendBroadcastEnablingHotspotCancel();
                                                                this.this$0.finish();
                                                                break;
                                                            default:
                                                                WifiApWarning wifiApWarning5 =
                                                                        this.this$0;
                                                                boolean z6 = WifiApWarning.DBG;
                                                                wifiApWarning5
                                                                        .checkFirstTimeWiFiSharingDialogAndProceedFurthur();
                                                                break;
                                                        }
                                                    }
                                                });
                                        final int i15 = 8;
                                        builder18.setNeutralButton(
                                                R.string.dlg_cancel,
                                                new DialogInterface.OnClickListener(
                                                        this) { // from class:
                                                    // com.samsung.android.settings.wifi.mobileap.WifiApWarning.7
                                                    public final /* synthetic */ WifiApWarning
                                                            this$0;

                                                    {
                                                        this.this$0 = this;
                                                    }

                                                    @Override // android.content.DialogInterface.OnClickListener
                                                    public final void onClick(
                                                            DialogInterface dialogInterface,
                                                            int i52) {
                                                        switch (i15) {
                                                            case 0:
                                                                SALogging.insertSALog(
                                                                        "TETH_010", "8035");
                                                                WifiApWarning wifiApWarning =
                                                                        this.this$0;
                                                                boolean z2 = WifiApWarning.DBG;
                                                                wifiApWarning
                                                                        .sendBroadcastEnablingHotspotCancel();
                                                                this.this$0.finish();
                                                                break;
                                                            case 1:
                                                                SALogging.insertSALog(
                                                                        "TETH_010", "8063");
                                                                Log.d(
                                                                        "WifiApWarning",
                                                                        "AP sta disconnect");
                                                                Settings.Secure.putInt(
                                                                        this.this$0.mContext
                                                                                .getContentResolver(),
                                                                        "wifi_ap_wifi_sharing",
                                                                        0);
                                                                WifiManager wifiManager =
                                                                        this.this$0
                                                                                .mAOSPWifiManager;
                                                                if (wifiManager != null) {
                                                                    wifiManager.setWifiEnabled(
                                                                            false);
                                                                    this.this$0.mSemWifiManager
                                                                            .resetSoftAp(
                                                                                    new Message());
                                                                }
                                                                this.this$0.finish();
                                                                break;
                                                            case 2:
                                                                SALogging.insertSALog(
                                                                        "TETH_010", "8062");
                                                                this.this$0.finish();
                                                                break;
                                                            case 3:
                                                                SALogging.insertSALog(
                                                                        "TETH_010", "8066");
                                                                Context context =
                                                                        this.this$0.mContext;
                                                                ((SemWifiManager)
                                                                                context
                                                                                        .getSystemService(
                                                                                                WiFiManagerExt
                                                                                                        .SEM_WIFI_SERVICE))
                                                                        .reportIssue(
                                                                                100,
                                                                                WifiIssueDetectorUtil
                                                                                        .ReportUtil
                                                                                        .getReportDataForWifiManagerApi(
                                                                                                -1,
                                                                                                "disconnect",
                                                                                                context.getPackageManager()
                                                                                                        .getNameForUid(
                                                                                                                context
                                                                                                                        .getUserId()),
                                                                                                context
                                                                                                        .getPackageName()));
                                                                this.this$0.mAOSPWifiManager
                                                                        .disconnect();
                                                                this.this$0
                                                                        .startProvisioningIfNecessary();
                                                                break;
                                                            case 4:
                                                                SALogging.insertSALog(
                                                                        "TETH_010", "8065");
                                                                WifiApWarning wifiApWarning2 =
                                                                        this.this$0;
                                                                boolean z3 = WifiApWarning.DBG;
                                                                wifiApWarning2
                                                                        .sendBroadcastEnablingHotspotCancel();
                                                                this.this$0.finish();
                                                                break;
                                                            case 5:
                                                                SALogging.insertSALog(
                                                                        "TETH_010", "8064");
                                                                WifiApWarning wifiApWarning3 =
                                                                        this.this$0;
                                                                boolean z4 = WifiApWarning.DBG;
                                                                wifiApWarning3
                                                                        .startProvisioningIfNecessary();
                                                                break;
                                                            case 6:
                                                                WifiApDataUsageConfig
                                                                        inputDataInDataUsageConfig =
                                                                                this.this$0
                                                                                        .getInputDataInDataUsageConfig();
                                                                if (inputDataInDataUsageConfig
                                                                                .getUsageValueInMB()
                                                                        >= WifiApConnectedDeviceUtils
                                                                                .getWifiApTodayTotalDataUsage(
                                                                                        this.this$0
                                                                                                .mContext)
                                                                                .getUsageValueInMB()) {
                                                                    Log.i(
                                                                            "WifiApWarning",
                                                                            "Settings Global data"
                                                                                + " limit : "
                                                                                    + (inputDataInDataUsageConfig
                                                                                                    .mUsageValueInBytes
                                                                                            / 1000.0d));
                                                                    WifiApConnectedDeviceUtils
                                                                            .setWifiApActiveSessionDataLimit(
                                                                                    this.this$0
                                                                                            .mContext,
                                                                                    (long)
                                                                                            ((double)
                                                                                                    inputDataInDataUsageConfig
                                                                                                            .mUsageValueInBytes));
                                                                    try {
                                                                        Thread.sleep(100L);
                                                                    } catch (
                                                                            InterruptedException
                                                                                    e) {
                                                                        Thread.currentThread()
                                                                                .interrupt();
                                                                        e.printStackTrace();
                                                                    }
                                                                    this.this$0
                                                                            .startProvisioningIfNecessary();
                                                                    break;
                                                                } else {
                                                                    Log.i(
                                                                            "WifiApWarning",
                                                                            "Error Settings Global"
                                                                                + " data limit : "
                                                                                    + inputDataInDataUsageConfig
                                                                                            .getUsageValueInMB());
                                                                    this.this$0
                                                                            .sendBroadcastEnablingHotspotCancel();
                                                                    Toast.makeText(
                                                                                    this.this$0
                                                                                            .mContext,
                                                                                    "You can'\t set"
                                                                                        + " less"
                                                                                        + " than"
                                                                                        + " the amount"
                                                                                        + " of data"
                                                                                        + " you used."
                                                                                        + " Please"
                                                                                        + " enter"
                                                                                        + " again.",
                                                                                    0)
                                                                            .show();
                                                                    this.this$0.finish();
                                                                    break;
                                                                }
                                                            case 7:
                                                                WifiApConnectedDeviceUtils
                                                                        .setWifiApActiveSessionDataLimit(
                                                                                this.this$0
                                                                                        .mContext,
                                                                                0L);
                                                                try {
                                                                    Thread.sleep(100L);
                                                                } catch (InterruptedException e2) {
                                                                    Thread.currentThread()
                                                                            .interrupt();
                                                                    e2.printStackTrace();
                                                                }
                                                                this.this$0
                                                                        .startProvisioningIfNecessary();
                                                                break;
                                                            case 8:
                                                                WifiApWarning wifiApWarning4 =
                                                                        this.this$0;
                                                                boolean z5 = WifiApWarning.DBG;
                                                                wifiApWarning4
                                                                        .sendBroadcastEnablingHotspotCancel();
                                                                this.this$0.finish();
                                                                break;
                                                            default:
                                                                WifiApWarning wifiApWarning5 =
                                                                        this.this$0;
                                                                boolean z6 = WifiApWarning.DBG;
                                                                wifiApWarning5
                                                                        .checkFirstTimeWiFiSharingDialogAndProceedFurthur();
                                                                break;
                                                        }
                                                    }
                                                });
                                        builder18.setOnCancelListener(
                                                new AnonymousClass3(this, 23));
                                        AlertDialog create4 = builder18.create();
                                        this.mDataLimitDialog = create4;
                                        create4.show();
                                        this.mDataLimitChangeLimitButton =
                                                this.mDataLimitDialog.getButton(-1);
                                        buttonValidate();
                                        collapsePanels();
                                        break;
                                    default:
                                        finish();
                                        break;
                                }
                        }
                }
                AlertDialog.Builder builder19 = new AlertDialog.Builder(this, this.mDialogTheme);
                builder19.setMessage(
                        WifiApUtils.getString(
                                this.mContext, R.string.wifi_ap_wifi_sharinglite_indoor_text));
                final int i16 = 3;
                builder19.setPositiveButton(
                        R.string.dlg_ok,
                        new DialogInterface.OnClickListener(this) { // from class:
                            // com.samsung.android.settings.wifi.mobileap.WifiApWarning.7
                            public final /* synthetic */ WifiApWarning this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i52) {
                                switch (i16) {
                                    case 0:
                                        SALogging.insertSALog("TETH_010", "8035");
                                        WifiApWarning wifiApWarning = this.this$0;
                                        boolean z2 = WifiApWarning.DBG;
                                        wifiApWarning.sendBroadcastEnablingHotspotCancel();
                                        this.this$0.finish();
                                        break;
                                    case 1:
                                        SALogging.insertSALog("TETH_010", "8063");
                                        Log.d("WifiApWarning", "AP sta disconnect");
                                        Settings.Secure.putInt(
                                                this.this$0.mContext.getContentResolver(),
                                                "wifi_ap_wifi_sharing",
                                                0);
                                        WifiManager wifiManager = this.this$0.mAOSPWifiManager;
                                        if (wifiManager != null) {
                                            wifiManager.setWifiEnabled(false);
                                            this.this$0.mSemWifiManager.resetSoftAp(new Message());
                                        }
                                        this.this$0.finish();
                                        break;
                                    case 2:
                                        SALogging.insertSALog("TETH_010", "8062");
                                        this.this$0.finish();
                                        break;
                                    case 3:
                                        SALogging.insertSALog("TETH_010", "8066");
                                        Context context = this.this$0.mContext;
                                        ((SemWifiManager)
                                                        context.getSystemService(
                                                                WiFiManagerExt.SEM_WIFI_SERVICE))
                                                .reportIssue(
                                                        100,
                                                        WifiIssueDetectorUtil.ReportUtil
                                                                .getReportDataForWifiManagerApi(
                                                                        -1,
                                                                        "disconnect",
                                                                        context.getPackageManager()
                                                                                .getNameForUid(
                                                                                        context
                                                                                                .getUserId()),
                                                                        context.getPackageName()));
                                        this.this$0.mAOSPWifiManager.disconnect();
                                        this.this$0.startProvisioningIfNecessary();
                                        break;
                                    case 4:
                                        SALogging.insertSALog("TETH_010", "8065");
                                        WifiApWarning wifiApWarning2 = this.this$0;
                                        boolean z3 = WifiApWarning.DBG;
                                        wifiApWarning2.sendBroadcastEnablingHotspotCancel();
                                        this.this$0.finish();
                                        break;
                                    case 5:
                                        SALogging.insertSALog("TETH_010", "8064");
                                        WifiApWarning wifiApWarning3 = this.this$0;
                                        boolean z4 = WifiApWarning.DBG;
                                        wifiApWarning3.startProvisioningIfNecessary();
                                        break;
                                    case 6:
                                        WifiApDataUsageConfig inputDataInDataUsageConfig =
                                                this.this$0.getInputDataInDataUsageConfig();
                                        if (inputDataInDataUsageConfig.getUsageValueInMB()
                                                >= WifiApConnectedDeviceUtils
                                                        .getWifiApTodayTotalDataUsage(
                                                                this.this$0.mContext)
                                                        .getUsageValueInMB()) {
                                            Log.i(
                                                    "WifiApWarning",
                                                    "Settings Global data limit : "
                                                            + (inputDataInDataUsageConfig
                                                                            .mUsageValueInBytes
                                                                    / 1000.0d));
                                            WifiApConnectedDeviceUtils
                                                    .setWifiApActiveSessionDataLimit(
                                                            this.this$0.mContext,
                                                            (long)
                                                                    ((double)
                                                                            inputDataInDataUsageConfig
                                                                                    .mUsageValueInBytes));
                                            try {
                                                Thread.sleep(100L);
                                            } catch (InterruptedException e) {
                                                Thread.currentThread().interrupt();
                                                e.printStackTrace();
                                            }
                                            this.this$0.startProvisioningIfNecessary();
                                            break;
                                        } else {
                                            Log.i(
                                                    "WifiApWarning",
                                                    "Error Settings Global data limit : "
                                                            + inputDataInDataUsageConfig
                                                                    .getUsageValueInMB());
                                            this.this$0.sendBroadcastEnablingHotspotCancel();
                                            Toast.makeText(
                                                            this.this$0.mContext,
                                                            "You can'\t set less than the amount of"
                                                                + " data you used. Please enter"
                                                                + " again.",
                                                            0)
                                                    .show();
                                            this.this$0.finish();
                                            break;
                                        }
                                    case 7:
                                        WifiApConnectedDeviceUtils.setWifiApActiveSessionDataLimit(
                                                this.this$0.mContext, 0L);
                                        try {
                                            Thread.sleep(100L);
                                        } catch (InterruptedException e2) {
                                            Thread.currentThread().interrupt();
                                            e2.printStackTrace();
                                        }
                                        this.this$0.startProvisioningIfNecessary();
                                        break;
                                    case 8:
                                        WifiApWarning wifiApWarning4 = this.this$0;
                                        boolean z5 = WifiApWarning.DBG;
                                        wifiApWarning4.sendBroadcastEnablingHotspotCancel();
                                        this.this$0.finish();
                                        break;
                                    default:
                                        WifiApWarning wifiApWarning5 = this.this$0;
                                        boolean z6 = WifiApWarning.DBG;
                                        wifiApWarning5
                                                .checkFirstTimeWiFiSharingDialogAndProceedFurthur();
                                        break;
                                }
                            }
                        });
                final int i17 = 4;
                builder19.setNegativeButton(
                        R.string.dlg_cancel,
                        new DialogInterface.OnClickListener(this) { // from class:
                            // com.samsung.android.settings.wifi.mobileap.WifiApWarning.7
                            public final /* synthetic */ WifiApWarning this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i52) {
                                switch (i17) {
                                    case 0:
                                        SALogging.insertSALog("TETH_010", "8035");
                                        WifiApWarning wifiApWarning = this.this$0;
                                        boolean z2 = WifiApWarning.DBG;
                                        wifiApWarning.sendBroadcastEnablingHotspotCancel();
                                        this.this$0.finish();
                                        break;
                                    case 1:
                                        SALogging.insertSALog("TETH_010", "8063");
                                        Log.d("WifiApWarning", "AP sta disconnect");
                                        Settings.Secure.putInt(
                                                this.this$0.mContext.getContentResolver(),
                                                "wifi_ap_wifi_sharing",
                                                0);
                                        WifiManager wifiManager = this.this$0.mAOSPWifiManager;
                                        if (wifiManager != null) {
                                            wifiManager.setWifiEnabled(false);
                                            this.this$0.mSemWifiManager.resetSoftAp(new Message());
                                        }
                                        this.this$0.finish();
                                        break;
                                    case 2:
                                        SALogging.insertSALog("TETH_010", "8062");
                                        this.this$0.finish();
                                        break;
                                    case 3:
                                        SALogging.insertSALog("TETH_010", "8066");
                                        Context context = this.this$0.mContext;
                                        ((SemWifiManager)
                                                        context.getSystemService(
                                                                WiFiManagerExt.SEM_WIFI_SERVICE))
                                                .reportIssue(
                                                        100,
                                                        WifiIssueDetectorUtil.ReportUtil
                                                                .getReportDataForWifiManagerApi(
                                                                        -1,
                                                                        "disconnect",
                                                                        context.getPackageManager()
                                                                                .getNameForUid(
                                                                                        context
                                                                                                .getUserId()),
                                                                        context.getPackageName()));
                                        this.this$0.mAOSPWifiManager.disconnect();
                                        this.this$0.startProvisioningIfNecessary();
                                        break;
                                    case 4:
                                        SALogging.insertSALog("TETH_010", "8065");
                                        WifiApWarning wifiApWarning2 = this.this$0;
                                        boolean z3 = WifiApWarning.DBG;
                                        wifiApWarning2.sendBroadcastEnablingHotspotCancel();
                                        this.this$0.finish();
                                        break;
                                    case 5:
                                        SALogging.insertSALog("TETH_010", "8064");
                                        WifiApWarning wifiApWarning3 = this.this$0;
                                        boolean z4 = WifiApWarning.DBG;
                                        wifiApWarning3.startProvisioningIfNecessary();
                                        break;
                                    case 6:
                                        WifiApDataUsageConfig inputDataInDataUsageConfig =
                                                this.this$0.getInputDataInDataUsageConfig();
                                        if (inputDataInDataUsageConfig.getUsageValueInMB()
                                                >= WifiApConnectedDeviceUtils
                                                        .getWifiApTodayTotalDataUsage(
                                                                this.this$0.mContext)
                                                        .getUsageValueInMB()) {
                                            Log.i(
                                                    "WifiApWarning",
                                                    "Settings Global data limit : "
                                                            + (inputDataInDataUsageConfig
                                                                            .mUsageValueInBytes
                                                                    / 1000.0d));
                                            WifiApConnectedDeviceUtils
                                                    .setWifiApActiveSessionDataLimit(
                                                            this.this$0.mContext,
                                                            (long)
                                                                    ((double)
                                                                            inputDataInDataUsageConfig
                                                                                    .mUsageValueInBytes));
                                            try {
                                                Thread.sleep(100L);
                                            } catch (InterruptedException e) {
                                                Thread.currentThread().interrupt();
                                                e.printStackTrace();
                                            }
                                            this.this$0.startProvisioningIfNecessary();
                                            break;
                                        } else {
                                            Log.i(
                                                    "WifiApWarning",
                                                    "Error Settings Global data limit : "
                                                            + inputDataInDataUsageConfig
                                                                    .getUsageValueInMB());
                                            this.this$0.sendBroadcastEnablingHotspotCancel();
                                            Toast.makeText(
                                                            this.this$0.mContext,
                                                            "You can'\t set less than the amount of"
                                                                + " data you used. Please enter"
                                                                + " again.",
                                                            0)
                                                    .show();
                                            this.this$0.finish();
                                            break;
                                        }
                                    case 7:
                                        WifiApConnectedDeviceUtils.setWifiApActiveSessionDataLimit(
                                                this.this$0.mContext, 0L);
                                        try {
                                            Thread.sleep(100L);
                                        } catch (InterruptedException e2) {
                                            Thread.currentThread().interrupt();
                                            e2.printStackTrace();
                                        }
                                        this.this$0.startProvisioningIfNecessary();
                                        break;
                                    case 8:
                                        WifiApWarning wifiApWarning4 = this.this$0;
                                        boolean z5 = WifiApWarning.DBG;
                                        wifiApWarning4.sendBroadcastEnablingHotspotCancel();
                                        this.this$0.finish();
                                        break;
                                    default:
                                        WifiApWarning wifiApWarning5 = this.this$0;
                                        boolean z6 = WifiApWarning.DBG;
                                        wifiApWarning5
                                                .checkFirstTimeWiFiSharingDialogAndProceedFurthur();
                                        break;
                                }
                            }
                        });
                builder19.setOnCancelListener(new AnonymousClass3(this, 20));
                builder19.setTitle(
                        WifiApUtils.getString(
                                this.mContext,
                                R.string.wifi_ap_wifi_sharinglite_indoor_disconnect_text));
                builder19.create();
                builder19.show();
                collapsePanels();
                break;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00f3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startProvisioningIfNecessary() {
        /*
            Method dump skipped, instructions count: 299
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.mobileap.WifiApWarning.startProvisioningIfNecessary():void");
    }
}

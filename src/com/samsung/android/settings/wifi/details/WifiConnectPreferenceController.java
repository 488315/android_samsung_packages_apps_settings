package com.samsung.android.settings.wifi.details;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.picker3.widget.SeslColorPicker$16$$ExternalSyntheticOutline0;
import androidx.preference.DropDownPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settingslib.Utils;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnCreate;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.core.lifecycle.events.OnSaveInstanceState;
import com.android.settingslib.utils.ThreadUtils;
import com.android.settingslib.widget.LayoutPreference;
import com.android.wifitrackerlib.WifiEntry;

import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.textfield.TextInputLayout;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.keystore.CertificateProvisioning;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.widget.SecButtonPreference;
import com.samsung.android.settings.wifi.ContactHelper;
import com.samsung.android.settings.wifi.SecWifiButtonPreference;
import com.samsung.android.settings.wifi.SecWifiProfileShareButtonPreference;
import com.samsung.android.settings.wifi.SecWifiProgressButtonPreference;
import com.samsung.android.settings.wifi.SecWifiUnclickablePreference;
import com.samsung.android.settings.wifi.WifiDevicePolicyManager;
import com.samsung.android.wifi.ISemSharedPasswordCallback;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.LogUtils;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.sec.ims.configuration.DATA;
import com.sec.ims.extensions.WiFiManagerExt;

import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiConnectPreferenceController extends WifiConfigurePreferenceController
        implements LifecycleObserver,
                OnDestroy,
                OnResume,
                OnPause,
                OnCreate,
                OnSaveInstanceState,
                Preference.OnPreferenceChangeListener,
                View.OnKeyListener,
                SecWifiPreferenceControllerHelper.ValidationUpdater {
    public static final boolean DBG = Debug.semIsProductDev();
    public static SharedPasswordCallback mSharedPasswordCallback;
    public SwitchPreferenceCompat mAutoReconnectPref;
    public String mBssid;
    public SecWifiProgressButtonPreference mButtonPref;
    public final AnonymousClass6 mConnectListener;
    public boolean mConnectingState;
    public final Context mContext;
    public NetworkInfo.DetailedState mDetailedState;
    public SwitchPreferenceCompat mDhcpNameSharingPref;
    public int mDisableReason;
    public LayoutPreference mDns1Pref;
    public EditText mDns1View;
    public LayoutPreference mDns2Pref;
    public EditText mDns2View;
    public EditText mEapAnonymousView;
    public SecWifiUnclickablePreference mEapCaCertErrorPref;
    public SecWifiUnclickablePreference mEapErrorPref;
    public TextView mEapIdentityErrorText;
    public final ArrayList mEditTexts;
    public AlertDialog mErrorDialog;
    public boolean mErrorDialogDiplay;
    public boolean mExternalValidationResult;
    public LayoutPreference mGatewayPref;
    public EditText mGatewayView;
    public SwitchPreferenceCompat mHiddenPref;
    public final WifiImeHelper mImeHelper;
    public LayoutPreference mIpAddrPref;
    public EditText mIpAddressView;
    public DropDownPreference mIpSettingsPref;
    public boolean mIsPasswordShareDisplayed;
    public boolean mIsPasswordVisible;
    public boolean mIsRegistered;
    public final boolean mIsRetryPopup;
    public boolean mIsSaveButtonPressed;
    public int mLastNetworkId;
    public final AnonymousClass5 mListenerForHidingKeyboard;
    public final LogUtils mLog;
    public DropDownPreference mMeteredPref;
    public int mNetworkId;
    public LayoutPreference mNetworkPrefixLenPref;
    public EditText mNetworkPrefixLengthView;
    public CheckableImageButton mPasswordImageButton;
    public TextInputLayout mPasswordInput;
    public DropDownPreference mPhase2Pref;
    public DropDownPreference mPrivacyPref;
    public LayoutPreference mProxyExclusionListPref;
    public EditText mProxyExclusionlistView;
    public LayoutPreference mProxyHostNamePref;
    public EditText mProxyHostNameView;
    public LayoutPreference mProxyPacPref;
    public EditText mProxyPacView;
    public LayoutPreference mProxyPortPref;
    public EditText mProxyPortView;
    public DropDownPreference mProxySettingsPref;
    public final AnonymousClass1 mReceiver;
    public RecyclerView mRecyclerView;
    public int mRejectCount;
    public final String mSAScreenId;
    public SecButtonPreference mSaveButtonPref;
    public WifiConfiguration mSaveConfig;
    public final AnonymousClass6 mSaveListener;
    public PreferenceScreen mScreen;
    public boolean mSecurityFieldsInitialized;
    public final Integer[] mSecurityInPosition;
    public DropDownPreference mSecurityPref;
    public final SemWifiManager mSemWifiManager;
    public SecWifiProfileShareButtonPreference mSharedPasswordPref;
    public boolean mSharingCanceled;
    public boolean mSharingPassword;
    public boolean mShouldShowMessageForRetry;
    public TextView mSsidErrorText;
    public LayoutPreference mSsidPref;
    public TextView mSsidTitleView;
    public EditText mSsidView;
    public final AnonymousClass9 mSsidWatcher;
    public String mTempSsid;
    public final Timer mTimer;
    public boolean mUpdateSaveState;
    public final WifiManager mWifiManager;
    public long startTime;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.details.WifiConnectPreferenceController$2, reason: invalid class name */
    public final class AnonymousClass2 implements DialogInterface.OnClickListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WifiConnectPreferenceController this$0;

        public /* synthetic */ AnonymousClass2(
                WifiConnectPreferenceController wifiConnectPreferenceController, int i) {
            this.$r8$classId = i;
            this.this$0 = wifiConnectPreferenceController;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            switch (this.$r8$classId) {
                case 0:
                    WifiConnectPreferenceController wifiConnectPreferenceController = this.this$0;
                    wifiConnectPreferenceController.mErrorDialogDiplay = false;
                    wifiConnectPreferenceController.mConnectingState = false;
                    wifiConnectPreferenceController.mShouldShowMessageForRetry = false;
                    wifiConnectPreferenceController.resetProgressButtonAndPreference();
                    break;
                case 1:
                    WifiConnectPreferenceController wifiConnectPreferenceController2 = this.this$0;
                    if (wifiConnectPreferenceController2.mWifiConfig != null) {
                        wifiConnectPreferenceController2.loadCertificates(
                                wifiConnectPreferenceController2.mEapCaCertPref,
                                CertificateProvisioning.CA_CERTIFICATE,
                                wifiConnectPreferenceController2.mDoNotValidateEapServerString,
                                false,
                                true);
                        if (this.this$0.mWifiConfig.enterpriseConfig.getCaCertificateAliases()
                                        != null
                                || !TextUtils.isEmpty(
                                        this.this$0.mWifiConfig.enterpriseConfig.getCaPath())) {
                            WifiConnectPreferenceController wifiConnectPreferenceController3 =
                                    this.this$0;
                            int findIndexOfValue =
                                    WifiConfigurePreferenceController.findIndexOfValue(
                                            wifiConnectPreferenceController3.mEapCaCertPref,
                                            wifiConnectPreferenceController3.mTrustOnFirstUse);
                            if (findIndexOfValue >= 0) {
                                WifiConnectPreferenceController wifiConnectPreferenceController4 =
                                        this.this$0;
                                wifiConnectPreferenceController4.updateSummary$4(
                                        wifiConnectPreferenceController4.mEapCaCertPref,
                                        findIndexOfValue);
                                this.this$0.mEapCaCertPref.setValueIndex(findIndexOfValue);
                                this.this$0.setDomainVisible();
                                this.this$0.setOcspVisible();
                                this.this$0.setMinTlsVersionVisible();
                                this.this$0.enableSubmitIfAppropriate();
                                this.this$0.disableViewsIfAppropriate$4();
                                this.this$0.mImeHelper.updateImeOptions();
                                WifiConnectPreferenceController wifiConnectPreferenceController5 =
                                        this.this$0;
                                wifiConnectPreferenceController5.mErrorDialogDiplay = false;
                                wifiConnectPreferenceController5.mConnectingState = false;
                                wifiConnectPreferenceController5.mShouldShowMessageForRetry = false;
                                wifiConnectPreferenceController5.resetProgressButtonAndPreference();
                                break;
                            }
                        }
                    }
                    break;
                default:
                    WifiConnectPreferenceController wifiConnectPreferenceController6 = this.this$0;
                    wifiConnectPreferenceController6.mErrorDialogDiplay = false;
                    wifiConnectPreferenceController6.mConnectingState = false;
                    wifiConnectPreferenceController6.mShouldShowMessageForRetry = false;
                    wifiConnectPreferenceController6.resetProgressButtonAndPreference();
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SharedPasswordCallback extends ISemSharedPasswordCallback.Stub {
        public SharedPasswordCallback() {}

        public final void onAccepted(String str, String str2) {
            final String removeDoubleQuotes = SemWifiUtils.removeDoubleQuotes(str2);
            if (TextUtils.isEmpty(removeDoubleQuotes)) {
                return;
            }
            boolean z = WifiConnectPreferenceController.DBG;
            if (z) {
                Log.d(
                        "WifiConnectPrefController",
                        "mSharedPasswordCallback onAccept : "
                                + WifiConnectPreferenceController.this.mSharingPassword
                                + " , password : "
                                + removeDoubleQuotes);
            }
            if (str != null) {
                if (z) {
                    Log.d(
                            "WifiConnectPrefController",
                            "mSharedPasswordCallback onAccept : " + removeDoubleQuotes.length());
                }
                if (WifiConnectPreferenceController.this.mPasswordView == null) {
                    Log.d(
                            "WifiConnectPrefController",
                            "mSharedPasswordCallback mPasswordView is null");
                } else {
                    ThreadUtils.postOnMainThread(
                            new Runnable() { // from class:
                                             // com.samsung.android.settings.wifi.details.WifiConnectPreferenceController$SharedPasswordCallback$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    WifiConnectPreferenceController.SharedPasswordCallback
                                            sharedPasswordCallback =
                                                    WifiConnectPreferenceController
                                                            .SharedPasswordCallback.this;
                                    String str3 = removeDoubleQuotes;
                                    WifiConnectPreferenceController
                                            wifiConnectPreferenceController =
                                                    WifiConnectPreferenceController.this;
                                    if (WifiConnectPreferenceController.DBG) {
                                        wifiConnectPreferenceController.getClass();
                                        Log.d(
                                                "WifiConnectPrefController",
                                                "connectWithSaredPW : "
                                                        + str3
                                                        + ", "
                                                        + str3.length());
                                    }
                                    WifiConnectPreferenceController.Timer timer =
                                            wifiConnectPreferenceController.mTimer;
                                    timer.getClass();
                                    Log.d("WifiConnectPrefController", "Stop share timer");
                                    timer.removeMessages(3);
                                    if (!wifiConnectPreferenceController.mSharingPassword) {
                                        wifiConnectPreferenceController.mPasswordInput
                                                .setPasswordVisibilityToggleEnabled(false);
                                        wifiConnectPreferenceController.mPasswordImageButton
                                                .setEnabled(false);
                                    }
                                    wifiConnectPreferenceController.mPasswordView.setText(str3);
                                    wifiConnectPreferenceController.mPasswordView.setSelection(
                                            str3.length());
                                    new Handler()
                                            .postDelayed(
                                                    new WifiConnectPreferenceController$$ExternalSyntheticLambda5(
                                                            wifiConnectPreferenceController, 0),
                                                    500L);
                                    new Handler()
                                            .postDelayed(
                                                    new WifiConnectPreferenceController$$ExternalSyntheticLambda5(
                                                            wifiConnectPreferenceController, 1),
                                                    700L);
                                }
                            });
                }
            }
        }

        public final void onAvailable(boolean z) {
            Log.d("WifiConnectPrefController", "ProfileShare available");
            WifiConnectPreferenceController.this.mSharedPasswordPref.setVisible(true);
            WifiConnectPreferenceController.this.mIsPasswordShareDisplayed = true;
        }

        public final void onRejected(String str) {
            Log.d(
                    "WifiConnectPrefController",
                    "mSharedPasswordCallback onRejected : "
                            + WifiConnectPreferenceController.this.mLog.getPrintableLog(str));
            ThreadUtils.postOnMainThread(
                    new WifiConnectPreferenceController$$ExternalSyntheticLambda5(
                            WifiConnectPreferenceController.this, 2));
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Timer extends Handler {
        public Timer() {}

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            WifiConnectPreferenceController wifiConnectPreferenceController =
                    WifiConnectPreferenceController.this;
            if (i == 0) {
                wifiConnectPreferenceController.showErrorDialog(
                        wifiConnectPreferenceController.mContext.getString(
                                R.string.wifi_disabled_connection_failure));
                Log.d(
                        "WifiConnectPrefController",
                        "timer end took : "
                                + ((System.currentTimeMillis()
                                                - wifiConnectPreferenceController.startTime)
                                        / 1000));
                return;
            }
            if (i == 1) {
                wifiConnectPreferenceController.mWifiManager.startScan();
                sendEmptyMessageDelayed(1, 10000L);
            } else {
                if (i != 3) {
                    return;
                }
                Log.d("WifiConnectPrefController", "Share Password - time out");
                wifiConnectPreferenceController.insertSaLoggingForPasswordShare("time out");
                wifiConnectPreferenceController.mSemWifiManager.requestPassword(false);
                wifiConnectPreferenceController.setProfileSharingMode(false);
                Toast.makeText(
                                wifiConnectPreferenceController.mContext,
                                R.string.wifi_profile_share_subscribertimeout,
                                0)
                        .show();
                Log.d(
                        "WifiConnectPrefController",
                        "timer end took : "
                                + ((System.currentTimeMillis()
                                                - wifiConnectPreferenceController.startTime)
                                        / 1000));
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class WifiEapIdTextWatcher implements TextWatcher {
        public final EditText mEditText;
        public final TextView mErrorText;
        public String mPreviousString;

        public WifiEapIdTextWatcher(EditText editText, TextView textView) {
            this.mEditText = editText;
            this.mErrorText = textView;
        }

        @Override // android.text.TextWatcher
        public final void afterTextChanged(Editable editable) {
            WifiConnectPreferenceController wifiConnectPreferenceController =
                    WifiConnectPreferenceController.this;
            wifiConnectPreferenceController.mWifiConfig.enterpriseConfig.setIdentity(
                    wifiConnectPreferenceController.mEapIdentityView.getText().toString());
            WifiConnectPreferenceController.this.enableSubmitIfAppropriate();
        }

        @Override // android.text.TextWatcher
        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            int length;
            if (charSequence == null
                    || (length = charSequence.toString().getBytes().length) <= 0
                    || length > 200) {
                return;
            }
            this.mPreviousString = charSequence.toString();
        }

        @Override // android.text.TextWatcher
        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (charSequence.toString().getBytes().length <= 200) {
                WifiConnectPreferenceController wifiConnectPreferenceController =
                        WifiConnectPreferenceController.this;
                wifiConnectPreferenceController.clearErrorTextUnderEditText(
                        wifiConnectPreferenceController.mSsidTitleView,
                        this.mEditText,
                        this.mErrorText);
                return;
            }
            this.mEditText.setPrivateImeOptions(
                    "inputType=PredictionOff;disableEmoticonInput=true");
            String str = this.mPreviousString;
            if (str == null || str.getBytes().length > 200) {
                Log.e(
                        "WifiConnectPrefController",
                        "String: "
                                + ((Object) charSequence)
                                + " start: "
                                + i
                                + " before: "
                                + i2
                                + " count: "
                                + i3);
                this.mEditText.setText(ApnSettings.MVNO_NONE);
            } else {
                this.mEditText.setText(this.mPreviousString);
            }
            this.mEditText.setPrivateImeOptions(
                    "inputType=PredictionOff;disableEmoticonInput=true;defaultInputmode=english");
            WifiConnectPreferenceController wifiConnectPreferenceController2 =
                    WifiConnectPreferenceController.this;
            wifiConnectPreferenceController2.showErrorTextUnderEditText(
                    wifiConnectPreferenceController2.mSsidTitleView,
                    this.mEditText,
                    this.mErrorText,
                    R.string.max_char_reached);
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.settings.wifi.details.WifiConnectPreferenceController$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.samsung.android.settings.wifi.details.WifiConnectPreferenceController$5] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.samsung.android.settings.wifi.details.WifiConnectPreferenceController$6] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.samsung.android.settings.wifi.details.WifiConnectPreferenceController$6] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.samsung.android.settings.wifi.details.WifiConnectPreferenceController$9] */
    @VisibleForTesting
    public WifiConnectPreferenceController(
            WifiEntry wifiEntry,
            boolean z,
            Fragment fragment,
            Context context,
            Lifecycle lifecycle,
            WifiManager wifiManager,
            WifiImeHelper wifiImeHelper,
            String str) {
        super(context, fragment, wifiEntry);
        WifiEntry wifiEntry2;
        this.mNetworkId = -1;
        this.mLastNetworkId = -1;
        this.mBssid = ApnSettings.MVNO_NONE;
        this.mIsPasswordVisible = false;
        this.mUpdateSaveState = false;
        this.mEditTexts = new ArrayList();
        this.mRejectCount = 0;
        this.mExternalValidationResult = true;
        this.mSharingPassword = false;
        this.mReceiver =
                new BroadcastReceiver() { // from class:
                    // com.samsung.android.settings.wifi.details.WifiConnectPreferenceController.1
                    public final boolean isSelectedWifiEntryEvent() {
                        WifiInfo connectionInfo =
                                WifiConnectPreferenceController.this.mWifiManager
                                        .getConnectionInfo();
                        Log.d(
                                "WifiConnectPrefController",
                                "NETWORK_STATE_CHANGED mNetworkId "
                                        + WifiConnectPreferenceController.this.mNetworkId);
                        StringBuilder sb =
                                new StringBuilder("NETWORK_STATE_CHANGED info.networkId : ");
                        sb.append(
                                connectionInfo != null
                                        ? Integer.valueOf(connectionInfo.getNetworkId())
                                        : null);
                        Log.d("WifiConnectPrefController", sb.toString());
                        return connectionInfo != null
                                && connectionInfo.getNetworkId()
                                        == WifiConnectPreferenceController.this.mNetworkId;
                    }

                    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                    /* JADX WARN: Removed duplicated region for block: B:90:0x02c1  */
                    /* JADX WARN: Removed duplicated region for block: B:96:0x02ee  */
                    @Override // android.content.BroadcastReceiver
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void onReceive(
                            android.content.Context r10, android.content.Intent r11) {
                        /*
                            Method dump skipped, instructions count: 868
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.settings.wifi.details.WifiConnectPreferenceController.AnonymousClass1.onReceive(android.content.Context,"
                                    + " android.content.Intent):void");
                    }
                };
        this.mListenerForHidingKeyboard =
                new Preference
                        .OnPreferenceClickListener() { // from class:
                                                       // com.samsung.android.settings.wifi.details.WifiConnectPreferenceController.5
                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public final boolean onPreferenceClick(Preference preference) {
                        WifiConnectPreferenceController wifiConnectPreferenceController =
                                WifiConnectPreferenceController.this;
                        View currentFocus =
                                wifiConnectPreferenceController
                                        .mFragment
                                        .getActivity()
                                        .getCurrentFocus();
                        wifiConnectPreferenceController.mImeHelper.hideSoftKeyboard(currentFocus);
                        if (currentFocus == null) {
                            return false;
                        }
                        currentFocus.clearFocus();
                        return false;
                    }
                };
        final int i = 0;
        this.mConnectListener =
                new WifiManager.ActionListener(this) { // from class:
                    // com.samsung.android.settings.wifi.details.WifiConnectPreferenceController.6
                    public final /* synthetic */ WifiConnectPreferenceController this$0;

                    {
                        this.this$0 = this;
                    }

                    public final void onFailure(int i2) {
                        switch (i) {
                            case 0:
                                if (i2 != 4) {
                                    Toast.makeText(
                                                    this.this$0.mContext,
                                                    R.string.wifi_failed_connect_message,
                                                    0)
                                            .show();
                                    final int i3 = 1;
                                    ((Activity) this.this$0.mContext)
                                            .runOnUiThread(
                                                    new Runnable(this) { // from class:
                                                        // com.samsung.android.settings.wifi.details.WifiConnectPreferenceController.6.1
                                                        public final /* synthetic */ AnonymousClass6
                                                                this$1;

                                                        {
                                                            this.this$1 = this;
                                                        }

                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            switch (i3) {
                                                                case 0:
                                                                    WifiConnectPreferenceController
                                                                            wifiConnectPreferenceController =
                                                                                    this.this$1
                                                                                            .this$0;
                                                                    Timer timer =
                                                                            wifiConnectPreferenceController
                                                                                    .mTimer;
                                                                    String string =
                                                                            wifiConnectPreferenceController
                                                                                    .mContext
                                                                                    .getString(
                                                                                            android
                                                                                                    .R
                                                                                                    .string
                                                                                                    .importance_from_user);
                                                                    timer.getClass();
                                                                    Log.d(
                                                                            "WifiConnectPrefController",
                                                                            "timer"
                                                                                + " stopImmediately");
                                                                    timer.removeMessages(0);
                                                                    timer.removeMessages(1);
                                                                    timer.removeMessages(3);
                                                                    WifiConnectPreferenceController
                                                                            .this
                                                                            .showErrorDialog(
                                                                                    string);
                                                                    break;
                                                                default:
                                                                    WifiConnectPreferenceController
                                                                            wifiConnectPreferenceController2 =
                                                                                    this.this$1
                                                                                            .this$0;
                                                                    boolean z2 =
                                                                            WifiConnectPreferenceController
                                                                                    .DBG;
                                                                    wifiConnectPreferenceController2
                                                                            .resetProgressButtonAndPreference();
                                                                    break;
                                                            }
                                                        }
                                                    });
                                    break;
                                } else {
                                    final int i4 = 0;
                                    ((Activity) this.this$0.mContext)
                                            .runOnUiThread(
                                                    new Runnable(this) { // from class:
                                                        // com.samsung.android.settings.wifi.details.WifiConnectPreferenceController.6.1
                                                        public final /* synthetic */ AnonymousClass6
                                                                this$1;

                                                        {
                                                            this.this$1 = this;
                                                        }

                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            switch (i4) {
                                                                case 0:
                                                                    WifiConnectPreferenceController
                                                                            wifiConnectPreferenceController =
                                                                                    this.this$1
                                                                                            .this$0;
                                                                    Timer timer =
                                                                            wifiConnectPreferenceController
                                                                                    .mTimer;
                                                                    String string =
                                                                            wifiConnectPreferenceController
                                                                                    .mContext
                                                                                    .getString(
                                                                                            android
                                                                                                    .R
                                                                                                    .string
                                                                                                    .importance_from_user);
                                                                    timer.getClass();
                                                                    Log.d(
                                                                            "WifiConnectPrefController",
                                                                            "timer"
                                                                                + " stopImmediately");
                                                                    timer.removeMessages(0);
                                                                    timer.removeMessages(1);
                                                                    timer.removeMessages(3);
                                                                    WifiConnectPreferenceController
                                                                            .this
                                                                            .showErrorDialog(
                                                                                    string);
                                                                    break;
                                                                default:
                                                                    WifiConnectPreferenceController
                                                                            wifiConnectPreferenceController2 =
                                                                                    this.this$1
                                                                                            .this$0;
                                                                    boolean z2 =
                                                                            WifiConnectPreferenceController
                                                                                    .DBG;
                                                                    wifiConnectPreferenceController2
                                                                            .resetProgressButtonAndPreference();
                                                                    break;
                                                            }
                                                        }
                                                    });
                                    break;
                                }
                            default:
                                Log.d("WifiConnectPrefController", "mSaveListener - onFailure");
                                Toast.makeText(
                                                this.this$0.mContext,
                                                R.string.wifi_failed_save_message,
                                                0)
                                        .show();
                                break;
                        }
                    }

                    public final void onSuccess() {
                        switch (i) {
                            case 0:
                                break;
                            default:
                                Log.d("WifiConnectPrefController", "mSaveListener onSuccess ");
                                this.this$0.finishActivity$2();
                                break;
                        }
                    }

                    private final void
                            onSuccess$com$samsung$android$settings$wifi$details$WifiConnectPreferenceController$6() {}
                };
        final int i2 = 1;
        this.mSaveListener =
                new WifiManager.ActionListener(this) { // from class:
                    // com.samsung.android.settings.wifi.details.WifiConnectPreferenceController.6
                    public final /* synthetic */ WifiConnectPreferenceController this$0;

                    {
                        this.this$0 = this;
                    }

                    public final void onFailure(int i22) {
                        switch (i2) {
                            case 0:
                                if (i22 != 4) {
                                    Toast.makeText(
                                                    this.this$0.mContext,
                                                    R.string.wifi_failed_connect_message,
                                                    0)
                                            .show();
                                    final int i3 = 1;
                                    ((Activity) this.this$0.mContext)
                                            .runOnUiThread(
                                                    new Runnable(this) { // from class:
                                                        // com.samsung.android.settings.wifi.details.WifiConnectPreferenceController.6.1
                                                        public final /* synthetic */ AnonymousClass6
                                                                this$1;

                                                        {
                                                            this.this$1 = this;
                                                        }

                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            switch (i3) {
                                                                case 0:
                                                                    WifiConnectPreferenceController
                                                                            wifiConnectPreferenceController =
                                                                                    this.this$1
                                                                                            .this$0;
                                                                    Timer timer =
                                                                            wifiConnectPreferenceController
                                                                                    .mTimer;
                                                                    String string =
                                                                            wifiConnectPreferenceController
                                                                                    .mContext
                                                                                    .getString(
                                                                                            android
                                                                                                    .R
                                                                                                    .string
                                                                                                    .importance_from_user);
                                                                    timer.getClass();
                                                                    Log.d(
                                                                            "WifiConnectPrefController",
                                                                            "timer"
                                                                                + " stopImmediately");
                                                                    timer.removeMessages(0);
                                                                    timer.removeMessages(1);
                                                                    timer.removeMessages(3);
                                                                    WifiConnectPreferenceController
                                                                            .this
                                                                            .showErrorDialog(
                                                                                    string);
                                                                    break;
                                                                default:
                                                                    WifiConnectPreferenceController
                                                                            wifiConnectPreferenceController2 =
                                                                                    this.this$1
                                                                                            .this$0;
                                                                    boolean z2 =
                                                                            WifiConnectPreferenceController
                                                                                    .DBG;
                                                                    wifiConnectPreferenceController2
                                                                            .resetProgressButtonAndPreference();
                                                                    break;
                                                            }
                                                        }
                                                    });
                                    break;
                                } else {
                                    final int i4 = 0;
                                    ((Activity) this.this$0.mContext)
                                            .runOnUiThread(
                                                    new Runnable(this) { // from class:
                                                        // com.samsung.android.settings.wifi.details.WifiConnectPreferenceController.6.1
                                                        public final /* synthetic */ AnonymousClass6
                                                                this$1;

                                                        {
                                                            this.this$1 = this;
                                                        }

                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            switch (i4) {
                                                                case 0:
                                                                    WifiConnectPreferenceController
                                                                            wifiConnectPreferenceController =
                                                                                    this.this$1
                                                                                            .this$0;
                                                                    Timer timer =
                                                                            wifiConnectPreferenceController
                                                                                    .mTimer;
                                                                    String string =
                                                                            wifiConnectPreferenceController
                                                                                    .mContext
                                                                                    .getString(
                                                                                            android
                                                                                                    .R
                                                                                                    .string
                                                                                                    .importance_from_user);
                                                                    timer.getClass();
                                                                    Log.d(
                                                                            "WifiConnectPrefController",
                                                                            "timer"
                                                                                + " stopImmediately");
                                                                    timer.removeMessages(0);
                                                                    timer.removeMessages(1);
                                                                    timer.removeMessages(3);
                                                                    WifiConnectPreferenceController
                                                                            .this
                                                                            .showErrorDialog(
                                                                                    string);
                                                                    break;
                                                                default:
                                                                    WifiConnectPreferenceController
                                                                            wifiConnectPreferenceController2 =
                                                                                    this.this$1
                                                                                            .this$0;
                                                                    boolean z2 =
                                                                            WifiConnectPreferenceController
                                                                                    .DBG;
                                                                    wifiConnectPreferenceController2
                                                                            .resetProgressButtonAndPreference();
                                                                    break;
                                                            }
                                                        }
                                                    });
                                    break;
                                }
                            default:
                                Log.d("WifiConnectPrefController", "mSaveListener - onFailure");
                                Toast.makeText(
                                                this.this$0.mContext,
                                                R.string.wifi_failed_save_message,
                                                0)
                                        .show();
                                break;
                        }
                    }

                    public final void onSuccess() {
                        switch (i2) {
                            case 0:
                                break;
                            default:
                                Log.d("WifiConnectPrefController", "mSaveListener onSuccess ");
                                this.this$0.finishActivity$2();
                                break;
                        }
                    }

                    private final void
                            onSuccess$com$samsung$android$settings$wifi$details$WifiConnectPreferenceController$6() {}
                };
        this.mTempSsid = null;
        this.mSsidWatcher =
                new TextWatcher() { // from class:
                    // com.samsung.android.settings.wifi.details.WifiConnectPreferenceController.9
                    @Override // android.text.TextWatcher
                    public final void afterTextChanged(Editable editable) {
                        WifiConnectPreferenceController.this.enableSubmitIfAppropriate();
                    }

                    @Override // android.text.TextWatcher
                    public final void beforeTextChanged(
                            CharSequence charSequence, int i3, int i4, int i5) {
                        if (charSequence == null
                                || charSequence.toString().getBytes().length > 32) {
                            return;
                        }
                        WifiConnectPreferenceController.this.mTempSsid = charSequence.toString();
                    }

                    @Override // android.text.TextWatcher
                    public final void onTextChanged(
                            CharSequence charSequence, int i3, int i4, int i5) {
                        if (charSequence
                                .toString()
                                .equals(WifiConnectPreferenceController.this.mTempSsid)) {
                            return;
                        }
                        if (charSequence.toString().getBytes().length <= 32) {
                            WifiConnectPreferenceController wifiConnectPreferenceController =
                                    WifiConnectPreferenceController.this;
                            wifiConnectPreferenceController.clearErrorTextUnderEditText(
                                    wifiConnectPreferenceController.mSsidTitleView,
                                    wifiConnectPreferenceController.mSsidView,
                                    wifiConnectPreferenceController.mSsidErrorText);
                            return;
                        }
                        WifiConnectPreferenceController.this.mSsidView.setPrivateImeOptions(
                                "inputType=PredictionOff;disableEmoticonInput=true;disableGifKeyboard=true;disableSticker=true;disableLiveMessage=true;defaultInputmode=english");
                        String str2 = WifiConnectPreferenceController.this.mTempSsid;
                        if (str2 == null || str2.getBytes().length > 32) {
                            WifiConnectPreferenceController.this.mSsidView.setText(
                                    ApnSettings.MVNO_NONE);
                        } else if (charSequence.toString().length()
                                        - WifiConnectPreferenceController.this.mTempSsid.length()
                                > 1) {
                            String charSequence2 = charSequence.toString();
                            if (charSequence2.getBytes().length > charSequence2.length()) {
                                int i6 = 0;
                                int i7 = 0;
                                int i8 = 0;
                                while (i6 <= 32) {
                                    i8 = Character.charCount(charSequence2.codePointAt(i7));
                                    int i9 = i7 + i8;
                                    i6 += charSequence2.substring(i7, i9).getBytes().length;
                                    i7 = i9;
                                }
                                int i10 = i7 - i8;
                                int i11 = i10 - 1;
                                if (charSequence2.charAt(i11) != 9770) {
                                    WifiConnectPreferenceController.this.mSsidView.setText(
                                            charSequence2.substring(0, i10));
                                } else {
                                    WifiConnectPreferenceController.this.mSsidView.setText(
                                            charSequence2.substring(0, i11));
                                }
                            } else {
                                WifiConnectPreferenceController.this.mSsidView.setText(
                                        charSequence2.substring(0, 32));
                            }
                        } else {
                            WifiConnectPreferenceController wifiConnectPreferenceController2 =
                                    WifiConnectPreferenceController.this;
                            wifiConnectPreferenceController2.mSsidView.setText(
                                    wifiConnectPreferenceController2.mTempSsid);
                        }
                        WifiConnectPreferenceController wifiConnectPreferenceController3 =
                                WifiConnectPreferenceController.this;
                        wifiConnectPreferenceController3.showErrorTextUnderEditText(
                                wifiConnectPreferenceController3.mSsidTitleView,
                                wifiConnectPreferenceController3.mSsidView,
                                wifiConnectPreferenceController3.mSsidErrorText,
                                R.string.max_char_reached);
                    }
                };
        this.mContext = context;
        this.mWifiManager = wifiManager;
        this.mSemWifiManager =
                (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        this.mImeHelper = wifiImeHelper;
        this.mWifiEntrySecurity = wifiEntry == null ? 2 : wifiEntry.getSecurity$1();
        WifiEntry wifiEntry3 = this.mWifiEntry;
        if (wifiEntry3 != null) {
            this.mWifiConfig = wifiEntry3.getWifiConfiguration();
        }
        WifiConfiguration wifiConfiguration = this.mWifiConfig;
        if (wifiConfiguration == null) {
            this.mWifiConfig = new WifiConfiguration();
        } else {
            this.mBssid = wifiConfiguration.BSSID;
        }
        lifecycle.addObserver(this);
        this.mSAScreenId = str;
        this.mIsRetryPopup = z;
        this.mShouldShowMessageForRetry = z;
        this.mIsTrustOnFirstUseSupported = wifiManager.isTrustOnFirstUseSupported();
        this.mTimer = new Timer();
        this.mSecurityInPosition = new Integer[8];
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        this.mLastNetworkId = connectionInfo != null ? connectionInfo.getNetworkId() : -1;
        this.mLog = new LogUtils();
        if (!WifiConfigurePreferenceController.isEnterpriseNetwork(this.mWifiEntrySecurity)
                || (wifiEntry2 = this.mWifiEntry) == null
                || wifiEntry2.isSaved()
                || Utils.isWifiOnly(context)) {
            return;
        }
        setupEapMethodForCarrierSsid();
    }

    @Override // com.samsung.android.settings.wifi.details.WifiConfigurePreferenceController
    public final void afterShowSecurityFields() {
        CheckableImageButton checkableImageButton = this.mPasswordImageButton;
        if (checkableImageButton != null) {
            if (this.mIsPasswordVisible) {
                checkableImageButton.performClick();
                this.mIsPasswordVisible = false;
            }
            if (this.mPasswordImageButton.checked) {
                this.mPasswordView.setTransformationMethod(null);
                this.mPasswordImageButton.setVisibility(0);
            } else {
                this.mPasswordView.setTransformationMethod(
                        PasswordTransformationMethod.getInstance());
                this.mPasswordImageButton.setVisibility(0);
            }
            SeslColorPicker$16$$ExternalSyntheticOutline0.m(this.mPasswordView);
        }
        if (this.mPasswordInput.isPasswordVisibilityToggleEnabled()) {
            if (WifiDevicePolicyManager.isAllowedToShowPasswordHiddenView(this.mContext)) {
                this.mPasswordInput.setPasswordVisibilityToggleEnabled(true);
            } else {
                this.mPasswordInput.setPasswordVisibilityToggleEnabled(false);
                this.mPasswordView.setInputType(129);
            }
        }
        this.mSecurityFieldsInitialized = true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        int i;
        int i2;
        WifiConfiguration wifiConfiguration;
        this.mScreen = preferenceScreen;
        super.displayPreference(preferenceScreen);
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference("ssid");
        this.mSsidPref = layoutPreference;
        TextView textView = (TextView) layoutPreference.mRootView.findViewById(R.id.title);
        this.mSsidTitleView = textView;
        textView.setText(R.string.wifi_ssid);
        EditText editText = (EditText) this.mSsidPref.mRootView.findViewById(R.id.edittext);
        this.mSsidView = editText;
        editText.addTextChangedListener(this.mSsidWatcher);
        this.mSsidView.setHint(R.string.wifi_ssid_hint);
        TextView textView2 = (TextView) this.mSsidPref.mRootView.findViewById(R.id.wifi_error_text);
        this.mSsidErrorText = textView2;
        textView2.setVisibility(8);
        DropDownPreference dropDownPreference =
                (DropDownPreference) preferenceScreen.findPreference("security_settings");
        this.mSecurityPref = dropDownPreference;
        dropDownPreference.setTitle(R.string.wifi_security);
        this.mSecurityPref.setOnPreferenceChangeListener(this);
        this.mSecurityPref.setOnPreferenceClickListener(this.mListenerForHidingKeyboard);
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.mContext.getString(R.string.wifi_security_none));
        this.mSecurityInPosition[0] = 0;
        if (this.mWifiManager.isEnhancedOpenSupported()) {
            arrayList.add(this.mContext.getString(R.string.wifi_security_owe));
            i = this.mWifiEntrySecurity == 4 ? 1 : 0;
            this.mSecurityInPosition[1] = 4;
            i2 = 2;
        } else {
            i = 0;
            i2 = 1;
        }
        arrayList.add(this.mContext.getString(R.string.wifi_security_wep));
        if (this.mWifiEntrySecurity == 1) {
            i = i2;
        }
        int i3 = i2 + 1;
        this.mSecurityInPosition[i2] = 1;
        arrayList.add(this.mContext.getString(R.string.wifi_security_wpa_wpa2));
        if (this.mWifiEntrySecurity == 2) {
            i = i3;
        }
        int i4 = i2 + 2;
        this.mSecurityInPosition[i3] = 2;
        if (this.mWifiManager.isWpa3SaeSupported()) {
            arrayList.add(this.mContext.getString(R.string.wifi_security_wpa3));
            if (this.mWifiEntrySecurity == 5) {
                i = i4;
            }
            this.mSecurityInPosition[i4] = 5;
            i4 = i2 + 3;
        }
        arrayList.add(this.mContext.getString(R.string.wifi_security_eap_wpa_wpa2));
        if (this.mWifiEntrySecurity == 3) {
            i = i4;
        }
        int i5 = i4 + 1;
        this.mSecurityInPosition[i4] = 3;
        if (this.mWifiManager.isWpa3SaeSupported()) {
            arrayList.add(this.mContext.getString(R.string.wifi_security_eap_wpa3));
            if (this.mWifiEntrySecurity == 7) {
                i = i5;
            }
            this.mSecurityInPosition[i5] = 7;
            i5 = i4 + 2;
        }
        if (this.mWifiManager.isWpa3SuiteBSupported()) {
            arrayList.add(this.mContext.getString(R.string.wifi_security_eap_suiteb));
            if (this.mWifiEntrySecurity == 6) {
                i = i5;
            }
            this.mSecurityInPosition[i5] = 6;
        }
        this.mSecurityPref.setEntries(
                (CharSequence[]) arrayList.toArray(new String[arrayList.size()]));
        CharSequence[] charSequenceArr =
                (CharSequence[]) arrayList.toArray(new String[arrayList.size()]);
        for (int i6 = 0; i6 < arrayList.size(); i6++) {
            charSequenceArr[i6] = String.valueOf(i6);
        }
        DropDownPreference dropDownPreference2 = this.mSecurityPref;
        dropDownPreference2.mEntryValues = charSequenceArr;
        updateSummary$4(dropDownPreference2, i);
        DropDownPreference dropDownPreference3 =
                (DropDownPreference) preferenceScreen.findPreference("eap_method");
        this.mEapMethodPref = dropDownPreference3;
        dropDownPreference3.setOnPreferenceChangeListener(this);
        this.mEapMethodPref.setOnPreferenceClickListener(this.mListenerForHidingKeyboard);
        this.mEapMethodPref.setTitle(R.string.wifi_eap_method);
        setupEapItems();
        this.mEapMethodPref.setVisible(false);
        DropDownPreference dropDownPreference4 =
                (DropDownPreference) preferenceScreen.findPreference("eap_ca_cert");
        this.mEapCaCertPref = dropDownPreference4;
        dropDownPreference4.setOnPreferenceChangeListener(this);
        this.mEapCaCertPref.setOnPreferenceClickListener(this.mListenerForHidingKeyboard);
        this.mEapCaCertPref.setVisible(false);
        LayoutPreference layoutPreference2 =
                (LayoutPreference) preferenceScreen.findPreference("eap_domain");
        this.mEapDomainPref = layoutPreference2;
        ((TextView) layoutPreference2.mRootView.findViewById(R.id.title))
                .setText(R.string.wifi_eap_domain);
        EditText editText2 = (EditText) this.mEapDomainPref.mRootView.findViewById(R.id.edittext);
        this.mEapDomainView = editText2;
        editText2.setInputType(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        this.mEapDomainView.addTextChangedListener(this.mEapDomainViewWatcher);
        this.mEapDomainPref.setVisible(false);
        DropDownPreference dropDownPreference5 =
                (DropDownPreference) preferenceScreen.findPreference("eap_user_cert");
        this.mEapUserCertPref = dropDownPreference5;
        dropDownPreference5.setOnPreferenceChangeListener(this);
        this.mEapUserCertPref.setOnPreferenceClickListener(this.mListenerForHidingKeyboard);
        this.mEapUserCertPref.setVisible(false);
        DropDownPreference dropDownPreference6 =
                (DropDownPreference) preferenceScreen.findPreference("eap_min_tls_ver");
        this.mMinTlsVersionPref = dropDownPreference6;
        dropDownPreference6.setOnPreferenceChangeListener(this);
        setupEapMinTlsVer(this.mWifiManager.isTlsV13Supported());
        DropDownPreference dropDownPreference7 =
                (DropDownPreference) preferenceScreen.findPreference("eap_ocsp");
        this.mEapOcspPref = dropDownPreference7;
        dropDownPreference7.setVisible(false);
        this.mEapOcspPref.setTitle(R.string.wifi_eap_ocsp);
        this.mEapOcspPref.setOnPreferenceChangeListener(this);
        this.mEapOcspPref.setOnPreferenceClickListener(this.mListenerForHidingKeyboard);
        setupEapOcspItems();
        disableViewsIfAppropriate$4();
        SecWifiUnclickablePreference secWifiUnclickablePreference =
                (SecWifiUnclickablePreference)
                        preferenceScreen.findPreference("eap_ca_cert_error_text");
        this.mEapCaCertErrorPref = secWifiUnclickablePreference;
        secWifiUnclickablePreference.setVisible(false);
        SecWifiUnclickablePreference secWifiUnclickablePreference2 =
                (SecWifiUnclickablePreference)
                        preferenceScreen.findPreference("wifi_eap_ca_error_text");
        this.mEapErrorPref = secWifiUnclickablePreference2;
        secWifiUnclickablePreference2.setVisible(false);
        LayoutPreference layoutPreference3 =
                (LayoutPreference) preferenceScreen.findPreference("eap_identity");
        this.mEapIdPref = layoutPreference3;
        ((TextView) layoutPreference3.mRootView.findViewById(R.id.title))
                .setText(R.string.wifi_eap_identity);
        this.mEapIdentityView = (EditText) this.mEapIdPref.mRootView.findViewById(R.id.edittext);
        TextView textView3 =
                (TextView) this.mEapIdPref.mRootView.findViewById(R.id.wifi_error_text);
        this.mEapIdentityErrorText = textView3;
        EditText editText3 = this.mEapIdentityView;
        editText3.addTextChangedListener(new WifiEapIdTextWatcher(editText3, textView3));
        this.mEapIdentityView.setInputType(
                NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        this.mEapIdentityErrorText.setVisibility(8);
        if (!WifiConfigurePreferenceController.isEnterpriseNetwork(this.mWifiEntrySecurity)) {
            this.mEapIdPref.setVisible(false);
        }
        DropDownPreference dropDownPreference8 =
                (DropDownPreference) preferenceScreen.findPreference("eap_sim_select");
        this.mEapSimPref = dropDownPreference8;
        dropDownPreference8.setVisible(false);
        this.mEapSimPref.setOnPreferenceChangeListener(this);
        this.mEapSimPref.setOnPreferenceClickListener(this.mListenerForHidingKeyboard);
        updateEapSimPref();
        LayoutPreference layoutPreference4 =
                (LayoutPreference) preferenceScreen.findPreference("wifi_password");
        this.mPasswordPref = layoutPreference4;
        TextView textView4 = (TextView) layoutPreference4.mRootView.findViewById(R.id.title);
        this.mPaswordTitleView = textView4;
        textView4.setText(R.string.wifi_password);
        ((TextView) this.mPasswordPref.mRootView.findViewById(R.id.title))
                .setText(R.string.wifi_password);
        EditText editText4 = (EditText) this.mPasswordPref.mRootView.findViewById(R.id.edittext);
        this.mPasswordView = editText4;
        editText4.addTextChangedListener(this.mPasswordWatcher);
        this.mPasswordView.setOnKeyListener(this);
        this.mPasswordView.setOnEditorActionListener(this.mImeHelper);
        this.mPasswordView.setHint(R.string.wifi_enter_password);
        TextView textView5 =
                (TextView) this.mPasswordPref.mRootView.findViewById(R.id.wifi_error_text);
        this.mPasswordErrorText = textView5;
        textView5.setVisibility(8);
        TextInputLayout textInputLayout =
                (TextInputLayout) this.mPasswordPref.mRootView.findViewById(R.id.input_password);
        this.mPasswordInput = textInputLayout;
        textInputLayout.setPasswordVisibilityToggleEnabled(true);
        CheckableImageButton checkableImageButton =
                (CheckableImageButton)
                        this.mPasswordInput.findViewById(
                                this.mContext
                                        .getResources()
                                        .getIdentifier(
                                                "text_input_end_icon",
                                                "id",
                                                this.mContext.getPackageName()));
        this.mPasswordImageButton = checkableImageButton;
        if (checkableImageButton != null) {
            final int i7 = 0;
            checkableImageButton.setOnClickListener(
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.samsung.android.settings.wifi.details.WifiConnectPreferenceController$$ExternalSyntheticLambda0
                        public final /* synthetic */ WifiConnectPreferenceController f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            int i8 = i7;
                            WifiConnectPreferenceController wifiConnectPreferenceController =
                                    this.f$0;
                            switch (i8) {
                                case 0:
                                    SALogging.insertSALog(
                                            !wifiConnectPreferenceController
                                                            .mPasswordImageButton
                                                            .checked
                                                    ? 1
                                                    : 0,
                                            wifiConnectPreferenceController.mSAScreenId,
                                            "1021",
                                            (String) null);
                                    if (!wifiConnectPreferenceController
                                            .mPasswordImageButton
                                            .checked) {
                                        wifiConnectPreferenceController.mPasswordView
                                                .setTransformationMethod(null);
                                        SeslColorPicker$16$$ExternalSyntheticOutline0.m(
                                                wifiConnectPreferenceController.mPasswordView);
                                        wifiConnectPreferenceController.mPasswordImageButton
                                                .setChecked(true);
                                        break;
                                    } else {
                                        wifiConnectPreferenceController.mPasswordView
                                                .setTransformationMethod(
                                                        PasswordTransformationMethod.getInstance());
                                        SeslColorPicker$16$$ExternalSyntheticOutline0.m(
                                                wifiConnectPreferenceController.mPasswordView);
                                        break;
                                    }
                                case 1:
                                    wifiConnectPreferenceController.save(false, true);
                                    break;
                                case 2:
                                    wifiConnectPreferenceController.save(false, true);
                                    break;
                                default:
                                    WifiEntry wifiEntry =
                                            wifiConnectPreferenceController.mWifiEntry;
                                    if (wifiEntry != null) {
                                        if (!wifiConnectPreferenceController.mSharingPassword) {
                                            wifiConnectPreferenceController.startTime =
                                                    System.currentTimeMillis();
                                            WifiConnectPreferenceController.Timer timer =
                                                    wifiConnectPreferenceController.mTimer;
                                            if (!timer.hasMessages(3)) {
                                                Log.d(
                                                        "WifiConnectPrefController",
                                                        "Start share timer");
                                                timer.sendEmptyMessageDelayed(3, 20000L);
                                            }
                                            wifiConnectPreferenceController.setProfileSharingMode(
                                                    true);
                                            Log.d(
                                                    "WifiConnectPrefController",
                                                    "requestPassword : " + wifiEntry.getSsid());
                                            wifiConnectPreferenceController.mSemWifiManager
                                                    .requestPassword(true);
                                            break;
                                        } else {
                                            Log.d(
                                                    "WifiConnectPrefController",
                                                    "Request is already in progress");
                                            break;
                                        }
                                    }
                                    break;
                            }
                        }
                    });
        }
        this.mSsidView.setText(SemWifiUtils.removeDoubleQuotes(this.mWifiConfig.SSID));
        SeslColorPicker$16$$ExternalSyntheticOutline0.m(this.mSsidView);
        int dimensionPixelSize =
                this.mContext
                        .getResources()
                        .getDimensionPixelSize(R.dimen.device_info_top_space_height);
        WifiEntry wifiEntry = this.mWifiEntry;
        if (wifiEntry != null) {
            this.mSsidPref.setVisible(false);
            this.mSecurityPref.setVisible(false);
            if (WifiConfigurePreferenceController.isEnterpriseNetwork(this.mWifiEntrySecurity)) {
                TextView textView6 = this.mPaswordTitleView;
                textView6.setPadding(
                        textView6.getPaddingLeft(),
                        dimensionPixelSize,
                        textView6.getPaddingRight(),
                        textView6.getPaddingBottom());
            }
        } else {
            TextView textView7 = this.mSsidTitleView;
            textView7.setPadding(
                    textView7.getPaddingLeft(),
                    dimensionPixelSize,
                    textView7.getPaddingRight(),
                    textView7.getPaddingBottom());
        }
        if (wifiEntry != null && (wifiConfiguration = wifiEntry.getWifiConfiguration()) != null) {
            int i8 = this.mWifiEntrySecurity;
            String removeDoubleQuotes =
                    (i8 == 2 || i8 == 5)
                            ? SemWifiUtils.removeDoubleQuotes(wifiConfiguration.preSharedKey)
                            : i8 == 1
                                    ? SemWifiUtils.removeDoubleQuotes(wifiConfiguration.wepKeys[0])
                                    : null;
            String str =
                    (removeDoubleQuotes == null || removeDoubleQuotes.length() >= 8)
                            ? removeDoubleQuotes
                            : null;
            if (!TextUtils.isEmpty(str) && str.length() <= 64) {
                this.mPasswordView.setText(str);
                SeslColorPicker$16$$ExternalSyntheticOutline0.m(this.mPasswordView);
            }
        }
        this.mAutoReconnectPref =
                (SwitchPreferenceCompat) preferenceScreen.findPreference("auto_connect");
        SecButtonPreference secButtonPreference =
                (SecButtonPreference) preferenceScreen.findPreference("save_button");
        this.mSaveButtonPref = secButtonPreference;
        final int i9 = 1;
        secButtonPreference.mOnClickListener =
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.wifi.details.WifiConnectPreferenceController$$ExternalSyntheticLambda0
                    public final /* synthetic */ WifiConnectPreferenceController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i82 = i9;
                        WifiConnectPreferenceController wifiConnectPreferenceController = this.f$0;
                        switch (i82) {
                            case 0:
                                SALogging.insertSALog(
                                        !wifiConnectPreferenceController
                                                        .mPasswordImageButton
                                                        .checked
                                                ? 1
                                                : 0,
                                        wifiConnectPreferenceController.mSAScreenId,
                                        "1021",
                                        (String) null);
                                if (!wifiConnectPreferenceController.mPasswordImageButton.checked) {
                                    wifiConnectPreferenceController.mPasswordView
                                            .setTransformationMethod(null);
                                    SeslColorPicker$16$$ExternalSyntheticOutline0.m(
                                            wifiConnectPreferenceController.mPasswordView);
                                    wifiConnectPreferenceController.mPasswordImageButton.setChecked(
                                            true);
                                    break;
                                } else {
                                    wifiConnectPreferenceController.mPasswordView
                                            .setTransformationMethod(
                                                    PasswordTransformationMethod.getInstance());
                                    SeslColorPicker$16$$ExternalSyntheticOutline0.m(
                                            wifiConnectPreferenceController.mPasswordView);
                                    break;
                                }
                            case 1:
                                wifiConnectPreferenceController.save(false, true);
                                break;
                            case 2:
                                wifiConnectPreferenceController.save(false, true);
                                break;
                            default:
                                WifiEntry wifiEntry2 = wifiConnectPreferenceController.mWifiEntry;
                                if (wifiEntry2 != null) {
                                    if (!wifiConnectPreferenceController.mSharingPassword) {
                                        wifiConnectPreferenceController.startTime =
                                                System.currentTimeMillis();
                                        WifiConnectPreferenceController.Timer timer =
                                                wifiConnectPreferenceController.mTimer;
                                        if (!timer.hasMessages(3)) {
                                            Log.d("WifiConnectPrefController", "Start share timer");
                                            timer.sendEmptyMessageDelayed(3, 20000L);
                                        }
                                        wifiConnectPreferenceController.setProfileSharingMode(true);
                                        Log.d(
                                                "WifiConnectPrefController",
                                                "requestPassword : " + wifiEntry2.getSsid());
                                        wifiConnectPreferenceController.mSemWifiManager
                                                .requestPassword(true);
                                        break;
                                    } else {
                                        Log.d(
                                                "WifiConnectPrefController",
                                                "Request is already in progress");
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        secButtonPreference.mTitle = this.mContext.getResources().getString(R.string.wifi_connect);
        this.mSaveButtonPref.seslSetSubheaderRoundedBackground(12);
        SecWifiProgressButtonPreference secWifiProgressButtonPreference =
                (SecWifiProgressButtonPreference) preferenceScreen.findPreference("button");
        this.mButtonPref = secWifiProgressButtonPreference;
        secWifiProgressButtonPreference.seslSetSubheaderRoundedBackground(12);
        final int i10 = 2;
        ((SecWifiButtonPreference) this.mButtonPref).mOnClickListener =
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.wifi.details.WifiConnectPreferenceController$$ExternalSyntheticLambda0
                    public final /* synthetic */ WifiConnectPreferenceController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i82 = i10;
                        WifiConnectPreferenceController wifiConnectPreferenceController = this.f$0;
                        switch (i82) {
                            case 0:
                                SALogging.insertSALog(
                                        !wifiConnectPreferenceController
                                                        .mPasswordImageButton
                                                        .checked
                                                ? 1
                                                : 0,
                                        wifiConnectPreferenceController.mSAScreenId,
                                        "1021",
                                        (String) null);
                                if (!wifiConnectPreferenceController.mPasswordImageButton.checked) {
                                    wifiConnectPreferenceController.mPasswordView
                                            .setTransformationMethod(null);
                                    SeslColorPicker$16$$ExternalSyntheticOutline0.m(
                                            wifiConnectPreferenceController.mPasswordView);
                                    wifiConnectPreferenceController.mPasswordImageButton.setChecked(
                                            true);
                                    break;
                                } else {
                                    wifiConnectPreferenceController.mPasswordView
                                            .setTransformationMethod(
                                                    PasswordTransformationMethod.getInstance());
                                    SeslColorPicker$16$$ExternalSyntheticOutline0.m(
                                            wifiConnectPreferenceController.mPasswordView);
                                    break;
                                }
                            case 1:
                                wifiConnectPreferenceController.save(false, true);
                                break;
                            case 2:
                                wifiConnectPreferenceController.save(false, true);
                                break;
                            default:
                                WifiEntry wifiEntry2 = wifiConnectPreferenceController.mWifiEntry;
                                if (wifiEntry2 != null) {
                                    if (!wifiConnectPreferenceController.mSharingPassword) {
                                        wifiConnectPreferenceController.startTime =
                                                System.currentTimeMillis();
                                        WifiConnectPreferenceController.Timer timer =
                                                wifiConnectPreferenceController.mTimer;
                                        if (!timer.hasMessages(3)) {
                                            Log.d("WifiConnectPrefController", "Start share timer");
                                            timer.sendEmptyMessageDelayed(3, 20000L);
                                        }
                                        wifiConnectPreferenceController.setProfileSharingMode(true);
                                        Log.d(
                                                "WifiConnectPrefController",
                                                "requestPassword : " + wifiEntry2.getSsid());
                                        wifiConnectPreferenceController.mSemWifiManager
                                                .requestPassword(true);
                                        break;
                                    } else {
                                        Log.d(
                                                "WifiConnectPrefController",
                                                "Request is already in progress");
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        SecWifiProfileShareButtonPreference secWifiProfileShareButtonPreference =
                (SecWifiProfileShareButtonPreference)
                        preferenceScreen.findPreference("shared_password");
        this.mSharedPasswordPref = secWifiProfileShareButtonPreference;
        final int i11 = 3;
        ((SecWifiButtonPreference) secWifiProfileShareButtonPreference).mOnClickListener =
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.wifi.details.WifiConnectPreferenceController$$ExternalSyntheticLambda0
                    public final /* synthetic */ WifiConnectPreferenceController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i82 = i11;
                        WifiConnectPreferenceController wifiConnectPreferenceController = this.f$0;
                        switch (i82) {
                            case 0:
                                SALogging.insertSALog(
                                        !wifiConnectPreferenceController
                                                        .mPasswordImageButton
                                                        .checked
                                                ? 1
                                                : 0,
                                        wifiConnectPreferenceController.mSAScreenId,
                                        "1021",
                                        (String) null);
                                if (!wifiConnectPreferenceController.mPasswordImageButton.checked) {
                                    wifiConnectPreferenceController.mPasswordView
                                            .setTransformationMethod(null);
                                    SeslColorPicker$16$$ExternalSyntheticOutline0.m(
                                            wifiConnectPreferenceController.mPasswordView);
                                    wifiConnectPreferenceController.mPasswordImageButton.setChecked(
                                            true);
                                    break;
                                } else {
                                    wifiConnectPreferenceController.mPasswordView
                                            .setTransformationMethod(
                                                    PasswordTransformationMethod.getInstance());
                                    SeslColorPicker$16$$ExternalSyntheticOutline0.m(
                                            wifiConnectPreferenceController.mPasswordView);
                                    break;
                                }
                            case 1:
                                wifiConnectPreferenceController.save(false, true);
                                break;
                            case 2:
                                wifiConnectPreferenceController.save(false, true);
                                break;
                            default:
                                WifiEntry wifiEntry2 = wifiConnectPreferenceController.mWifiEntry;
                                if (wifiEntry2 != null) {
                                    if (!wifiConnectPreferenceController.mSharingPassword) {
                                        wifiConnectPreferenceController.startTime =
                                                System.currentTimeMillis();
                                        WifiConnectPreferenceController.Timer timer =
                                                wifiConnectPreferenceController.mTimer;
                                        if (!timer.hasMessages(3)) {
                                            Log.d("WifiConnectPrefController", "Start share timer");
                                            timer.sendEmptyMessageDelayed(3, 20000L);
                                        }
                                        wifiConnectPreferenceController.setProfileSharingMode(true);
                                        Log.d(
                                                "WifiConnectPrefController",
                                                "requestPassword : " + wifiEntry2.getSsid());
                                        wifiConnectPreferenceController.mSemWifiManager
                                                .requestPassword(true);
                                        break;
                                    } else {
                                        Log.d(
                                                "WifiConnectPrefController",
                                                "Request is already in progress");
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        secWifiProfileShareButtonPreference.setVisible(false);
        this.mPhase2Pref = (DropDownPreference) preferenceScreen.findPreference("eap_phase2");
        this.mEapAnonymousView =
                (EditText)
                        ((LayoutPreference) preferenceScreen.findPreference("eap_anonymous"))
                                .mRootView.findViewById(R.id.edittext);
        this.mIpSettingsPref = (DropDownPreference) preferenceScreen.findPreference("ip_settings");
        this.mIpAddrPref = (LayoutPreference) preferenceScreen.findPreference("wifi_ip_address");
        this.mGatewayPref = (LayoutPreference) preferenceScreen.findPreference("gateway");
        this.mNetworkPrefixLenPref =
                (LayoutPreference) preferenceScreen.findPreference("wifi_network_prefix_length");
        this.mDns1Pref = (LayoutPreference) preferenceScreen.findPreference("wifi_dns1");
        this.mDns2Pref = (LayoutPreference) preferenceScreen.findPreference("wifi_dns2");
        this.mIpAddressView = (EditText) this.mIpAddrPref.mRootView.findViewById(R.id.edittext);
        this.mGatewayView = (EditText) this.mGatewayPref.mRootView.findViewById(R.id.edittext);
        this.mNetworkPrefixLengthView =
                (EditText) this.mNetworkPrefixLenPref.mRootView.findViewById(R.id.edittext);
        this.mDns1View = (EditText) this.mDns1Pref.mRootView.findViewById(R.id.edittext);
        this.mDns2View = (EditText) this.mDns2Pref.mRootView.findViewById(R.id.edittext);
        this.mProxySettingsPref =
                (DropDownPreference) preferenceScreen.findPreference("proxy_settings");
        this.mProxyPacPref = (LayoutPreference) preferenceScreen.findPreference("proxy_pac");
        this.mProxyHostNamePref =
                (LayoutPreference) preferenceScreen.findPreference("proxy_hostname");
        this.mProxyPortPref = (LayoutPreference) preferenceScreen.findPreference("proxy_port");
        this.mProxyExclusionListPref =
                (LayoutPreference) preferenceScreen.findPreference("proxy_exclusionlist");
        this.mProxyPacView = (EditText) this.mProxyPacPref.mRootView.findViewById(R.id.edittext);
        this.mProxyHostNameView =
                (EditText) this.mProxyHostNamePref.mRootView.findViewById(R.id.edittext);
        this.mProxyPortView = (EditText) this.mProxyPortPref.mRootView.findViewById(R.id.edittext);
        this.mProxyExclusionlistView =
                (EditText) this.mProxyExclusionListPref.mRootView.findViewById(R.id.edittext);
        this.mMeteredPref = (DropDownPreference) preferenceScreen.findPreference("metered");
        this.mHiddenPref = (SwitchPreferenceCompat) preferenceScreen.findPreference("hidden");
        this.mDhcpNameSharingPref =
                (SwitchPreferenceCompat) preferenceScreen.findPreference("dhcp_name_sharing");
        this.mPrivacyPref = (DropDownPreference) preferenceScreen.findPreference("privacy");
        if (wifiEntry == null) {
            SecWifiProgressButtonPreference secWifiProgressButtonPreference2 = this.mButtonPref;
            secWifiProgressButtonPreference2.mButtonText =
                    this.mContext.getResources().getString(R.string.wifi_save);
            secWifiProgressButtonPreference2.notifyChanged();
        } else {
            SecWifiProgressButtonPreference secWifiProgressButtonPreference3 = this.mButtonPref;
            secWifiProgressButtonPreference3.mButtonText =
                    this.mContext.getResources().getString(R.string.wifi_connect);
            secWifiProgressButtonPreference3.notifyChanged();
        }
        this.mButtonPref.setVisible(true);
        this.mSaveButtonPref.setVisible(false);
        WifiImeHelper wifiImeHelper = this.mImeHelper;
        SecWifiProgressButtonPreference secWifiProgressButtonPreference4 = this.mButtonPref;
        if (WifiImeHelper.DBG) {
            wifiImeHelper.getClass();
            Log.v("WifiImeHelper", "setup action preference");
        }
        wifiImeHelper.mActionPreference = secWifiProgressButtonPreference4;
        updateSecurity();
        if (this.mConnectingState) {
            setButtonConnecting(true);
        }
        if (this.mUpdateSaveState) {
            if (this.mWifiConfig != null) {
                if (this.mSsidPref.isVisible()) {
                    String removeDoubleQuotes2 =
                            SemWifiUtils.removeDoubleQuotes(this.mWifiConfig.SSID);
                    if (removeDoubleQuotes2 != null) {
                        this.mSsidView.setText(removeDoubleQuotes2);
                        SeslColorPicker$16$$ExternalSyntheticOutline0.m(this.mSsidView);
                    } else {
                        this.mSsidView.setHint(R.string.wifi_ssid_hint);
                    }
                }
                if (this.mPasswordPref.isVisible()) {
                    int i12 = this.mWifiEntrySecurity;
                    if (i12 == 1) {
                        this.mTempPassword =
                                SemWifiUtils.removeDoubleQuotes(this.mWifiConfig.wepKeys[0]);
                    } else if (WifiConfigurePreferenceController.isEnterpriseNetwork(i12)) {
                        this.mTempPassword = this.mWifiConfig.enterpriseConfig.getPassword();
                    } else {
                        this.mTempPassword =
                                SemWifiUtils.removeDoubleQuotes(this.mWifiConfig.preSharedKey);
                    }
                }
            } else {
                Log.d("WifiConnectPrefController", "savedInstanceState getconfig is null");
            }
        }
        if (this.mIsRetryPopup && wifiEntry != null && wifiEntry.isSaved()) {
            this.mPasswordView.setHint(R.string.wifi_unchanged);
            this.mPasswordView.setText(ApnSettings.MVNO_NONE);
            this.mTempPassword = SemWifiUtils.removeDoubleQuotes(this.mWifiConfig.preSharedKey);
        }
    }

    @Override // com.samsung.android.settings.wifi.details.WifiConfigurePreferenceController
    public final void enableSubmitIfAppropriate() {
        if (this.mConnectingState) {
            return;
        }
        setButtonEnable(isSubmittable());
    }

    @Override // com.samsung.android.settings.wifi.details.WifiConfigurePreferenceController
    public final void fillPreviousPasswordIfNeeded() {
        if (this.mUpdateSaveState) {
            String str = this.mTempPassword;
            if (TextUtils.isEmpty(str)) {
                return;
            }
            this.mPasswordView.setText(str);
            this.mPasswordView.setSelection(str.length());
        }
    }

    public final void findEditTextFromPreference(Preference preference) {
        if (preference == null) {
            return;
        }
        if (preference instanceof LayoutPreference) {
            LayoutPreference layoutPreference = (LayoutPreference) preference;
            TextView textView = (TextView) layoutPreference.mRootView.findViewById(R.id.title);
            if (layoutPreference.isVisible()) {
                View findViewById = layoutPreference.mRootView.findViewById(R.id.edittext);
                if (findViewById instanceof EditText) {
                    this.mEditTexts.add((EditText) findViewById);
                    if (textView == null || !DBG) {
                        return;
                    }
                    Log.v(
                            "WifiConnectPrefController",
                            "findEditTextFromLayout add mEditTexts :" + layoutPreference.getKey());
                    return;
                }
                return;
            }
            return;
        }
        if (!(preference instanceof PreferenceCategory)) {
            return;
        }
        int i = 0;
        while (true) {
            PreferenceCategory preferenceCategory = (PreferenceCategory) preference;
            if (i >= preferenceCategory.getPreferenceCount()) {
                return;
            }
            findEditTextFromPreference(preferenceCategory.getPreference(i));
            i++;
        }
    }

    @Override // com.samsung.android.settings.wifi.details.WifiConfigurePreferenceController
    public final String getLogTag() {
        return "WifiConnectPrefController";
    }

    @Override // com.samsung.android.settings.wifi.details.WifiConfigurePreferenceController
    public final int getMode() {
        return 0;
    }

    public final void insertSaLoggingForPasswordShare(String str) {
        HashMap hashMap = new HashMap();
        hashMap.put("result", str);
        SALogging.insertSALog(this.mSAScreenId, "0125", hashMap, 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:115:0x006c, code lost:

       if ("PWD".equals(r0) == false) goto L35;
    */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0170  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0175  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x00cd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isSubmittable() {
        /*
            Method dump skipped, instructions count: 437
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.details.WifiConnectPreferenceController.isSubmittable():boolean");
    }

    @Override // com.samsung.android.settings.wifi.details.WifiConfigurePreferenceController
    public final boolean isUnchangedNeeded() {
        WifiEntry wifiEntry;
        return this.mIsRetryPopup && (wifiEntry = this.mWifiEntry) != null && wifiEntry.isSaved();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnCreate
    public final void onCreate(Bundle bundle) {
        if (bundle != null) {
            WifiConfiguration wifiConfiguration =
                    (WifiConfiguration) bundle.getParcelable("save_config_state");
            if (wifiConfiguration != null) {
                this.mWifiConfig = wifiConfiguration;
            }
            this.mWifiEntrySecurity = bundle.getInt("save_securitytype_state");
            this.mIsPasswordVisible = bundle.getBoolean("save_pw_button_state");
            this.mUpdateSaveState = true;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.CONFIGURED_NETWORKS_CHANGE");
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction("com.samsung.android.net.wifi.SHOW_INFO_MESSAGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("com.samsung.android.net.wifi.NETWORK_CONNECT_FAILED");
        intentFilter.addAction("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        this.mContext.registerReceiver(
                this.mReceiver, intentFilter, "android.permission.NETWORK_SETTINGS", null, 2);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public final void onDestroy() {
        Log.d("WifiConnectPrefController", "onDestroy clearall");
        HashMap hashMap = new HashMap();
        hashMap.put("not_save", this.mIsSaveButtonPressed ? DATA.DM_FIELD_INDEX.PCSCF_DOMAIN : "1");
        SALogging.insertSALog(this.mSAScreenId, "1027", hashMap, 0);
        HashMap hashMap2 = new HashMap();
        hashMap2.put(
                "password_share_displayed",
                this.mIsPasswordShareDisplayed ? "Displayed" : "Not Displayed");
        SALogging.insertSALog(this.mSAScreenId, "1027", hashMap2, 0);
        ((SpannableStringBuilder) this.mPasswordView.getText()).clear();
        AlertDialog alertDialog = this.mErrorDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        this.mContext.unregisterReceiver(this.mReceiver);
        Timer timer = this.mTimer;
        timer.getClass();
        Log.d("WifiConnectPrefController", "timer stop");
        timer.removeMessages(0);
        timer.removeMessages(1);
        timer.removeMessages(3);
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (view != this.mPasswordView || ((i != 66 && i != 160) || !isSubmittable())) {
            return false;
        }
        save(false, true);
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        Log.d("WifiConnectPrefController", "onPause");
        if (this.mSharingPassword) {
            this.mSemWifiManager.requestPassword(false);
            Timer timer = this.mTimer;
            timer.getClass();
            Log.d("WifiConnectPrefController", "Stop share timer");
            timer.removeMessages(3);
            this.mSharingCanceled = true;
        }
        if (this.mIsRegistered) {
            this.mSemWifiManager.unregisterPasswordCallback(mSharedPasswordCallback);
            mSharedPasswordCallback = null;
            this.mIsRegistered = false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:77:0x01b8, code lost:

       if (r1.length == (r9 + 1)) goto L81;
    */
    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onPreferenceChange(
            androidx.preference.Preference r8, java.lang.Object r9) {
        /*
            Method dump skipped, instructions count: 539
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.details.WifiConnectPreferenceController.onPreferenceChange(androidx.preference.Preference,"
                    + " java.lang.Object):boolean");
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        int i;
        int wifiState = this.mWifiManager.getWifiState();
        if (wifiState == 1 || wifiState == 0) {
            finishActivity$2();
        }
        Log.d(
                "WifiConnectPrefController",
                "onResume mWifiEntrySecurity : " + this.mWifiEntrySecurity);
        boolean z =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_MCF_SUPPORT_FRAMEWORK");
        WifiEntry wifiEntry = this.mWifiEntry;
        if (z) {
            if (SemWifiUtils.isEnabledUltraPowerSaving(this.mContext)) {
                Log.d("WifiConnectPrefController", "UPSM mode or EmergencyMode is turned on");
            } else if (!this.mSemWifiManager.isSupportedProfileRequest()) {
                Log.d("WifiConnectPrefController", "Device not support ProfileShare");
            } else if (Settings.System.getInt(
                            this.mFragment.getActivity().getContentResolver(),
                            "nearby_scanning_enabled",
                            0)
                    != 1) {
                Log.d("WifiConnectPrefController", "Nearby scanning is disabled");
            } else if (wifiEntry == null
                    || !((i = this.mWifiEntrySecurity) == 2 || i == 5 || i == 1)) {
                Log.d("WifiConnectPrefController", "ProfileShare not supported AP");
            } else if (TextUtils.isEmpty(this.mBssid)) {
                Log.d("WifiConnectPrefController", "BSSID is null");
            } else {
                if (!Rune.isChinaModel()) {
                    Context context = this.mContext;
                    Uri uri = ContactHelper.CONTENT_COUNT_URI;
                    if (context != null) {
                        try {
                            Cursor query =
                                    context.getContentResolver()
                                            .query(
                                                    ContactHelper.CONTENT_COUNT_URI,
                                                    null,
                                                    null,
                                                    null,
                                                    null);
                            try {
                                if (query == null) {
                                    Log.d("Wifi.ContactHelper", "cursor is null");
                                } else if (query.moveToNext()) {
                                    query.close();
                                }
                                if (query != null) {
                                    query.close();
                                }
                            } finally {
                            }
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.d("WifiConnectPrefController", "No contact");
                }
                if (!TextUtils.isEmpty(this.mBssid)) {
                    Log.d(
                            "WifiConnectPrefController",
                            "Prepare to share " + this.mLog.getPrintableLog(this.mBssid));
                    if (mSharedPasswordCallback == null) {
                        mSharedPasswordCallback = new SharedPasswordCallback();
                    }
                    this.mSemWifiManager.registerPasswordCallback(
                            this.mBssid, mSharedPasswordCallback);
                    this.mIsRegistered = true;
                }
            }
            Log.d("WifiConnectPrefController", "device not support ProfileShare");
        } else {
            Log.d(
                    "WifiConnectPrefController",
                    "SEC_FLOATING_FEATURE_MCF_SUPPORT_FRAMEWORK disabled");
        }
        if (this.mSharingCanceled) {
            setProfileSharingMode(false);
            this.mSharingCanceled = false;
        }
        if (!this.mSecurityFieldsInitialized) {
            showSecurityFields(this.mWifiEntrySecurity);
        }
        if (this.mPasswordView.hasFocus()) {
            SeslColorPicker$16$$ExternalSyntheticOutline0.m(this.mPasswordView);
        }
        if (this.mEapIdentityView.hasFocus()) {
            SeslColorPicker$16$$ExternalSyntheticOutline0.m(this.mEapIdentityView);
        }
        this.mUpdateSaveState = false;
        if (wifiEntry != null && wifiEntry.getWifiConfiguration() != null) {
            this.mNetworkId = wifiEntry.getWifiConfiguration().networkId;
        }
        if (this.mShouldShowMessageForRetry) {
            int i2 = this.mDisableReason;
            if (i2 == 2) {
                showErrorTextMsg(i2);
            } else if (i2 == -1) {
                showErrorTextMsg(-1);
            } else if (i2 == 11) {
                showErrorTextMsg(i2);
            }
        }
        enableSubmitIfAppropriate();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnSaveInstanceState
    public final void onSaveInstanceState(Bundle bundle) {
        save(true, true);
        bundle.putParcelable("save_config_state", this.mSaveConfig);
        bundle.putInt("save_securitytype_state", this.mWifiEntrySecurity);
        CheckableImageButton checkableImageButton = this.mPasswordImageButton;
        if (checkableImageButton != null) {
            bundle.putBoolean("save_pw_button_state", checkableImageButton.checked);
        }
    }

    public final void resetProgressButtonAndPreference() {
        if (DBG) {
            Log.d("WifiConnectPrefController", "resetProgressButtonAndPreference");
        }
        SecWifiProgressButtonPreference secWifiProgressButtonPreference = this.mButtonPref;
        secWifiProgressButtonPreference.mButtonText =
                this.mContext.getResources().getString(R.string.wifi_connect);
        secWifiProgressButtonPreference.notifyChanged();
        setPreferenceEnable(true);
        setButtonConnecting(false);
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0585  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x059e  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x05b9  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x05d6  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x05ef  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0626  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x062f  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0632  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x060c  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x05da  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x05d3  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0566  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x051e  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x03b2  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x03ad  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x03bd  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x03eb  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x041b  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x046a  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0483  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0508  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x051b  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0529  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void save(boolean r18, boolean r19) {
        /*
            Method dump skipped, instructions count: 1846
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.details.WifiConnectPreferenceController.save(boolean,"
                    + " boolean):void");
    }

    public final void setButtonConnecting(boolean z) {
        setButtonEnable(!z && isSubmittable());
        this.mButtonPref.setProgressVisibility(z);
    }

    public final void setButtonEnable(boolean z) {
        if (this.mButtonPref == null) {
            return;
        }
        if (z) {
            Timer timer = this.mTimer;
            timer.getClass();
            Log.d("WifiConnectPrefController", "timer stop");
            timer.removeMessages(0);
            timer.removeMessages(1);
            timer.removeMessages(3);
        }
        WifiEntry wifiEntry = this.mWifiEntry;
        if (wifiEntry != null
                && wifiEntry.getWifiConfiguration() != null
                && !WifiDevicePolicyManager.canModifyNetwork(
                        this.mContext, wifiEntry.getWifiConfiguration())) {
            this.mButtonPref.setEnabled(false);
        } else {
            this.mButtonPref.setEnabled(z);
            this.mButtonPref.setProgressVisibility(false);
        }
    }

    public final void setPreferenceEnable(boolean z) {
        this.mEditTexts.clear();
        for (int i = 0; i < this.mScreen.getPreferenceCount(); i++) {
            Preference preference = this.mScreen.getPreference(i);
            if (preference.isVisible()) {
                preference.setEnabled(z);
            }
            findEditTextFromPreference(preference);
        }
        for (int i2 = 0; i2 < this.mEditTexts.size(); i2++) {
            ((EditText) this.mEditTexts.get(i2)).setEnabled(z);
        }
        if (this.mPasswordInput.isPasswordVisibilityToggleEnabled()) {
            this.mPasswordInput.setPasswordVisibilityToggleEnabled(
                    WifiDevicePolicyManager.isAllowedToShowPasswordHiddenView(this.mContext));
        }
        if (this.mWifiEntrySecurity == 6 || !this.mCanChangeEapMethod) {
            this.mEapMethodPref.setEnabled(false);
        }
        this.mImeHelper.updateImeOptions();
    }

    public final void setProfileSharingMode(boolean z) {
        this.mSharingPassword = z;
        boolean z2 = !z;
        this.mSharedPasswordPref.setEnabled(z2);
        this.mPasswordImageButton.setEnabled(z2);
        this.mPasswordInput.setPasswordVisibilityToggleEnabled(z2);
        setPreferenceEnable(z2);
        if (z || isSubmittable()) {
            return;
        }
        this.mButtonPref.setEnabled(false);
    }

    public final void showErrorDialog(String str) {
        this.mErrorDialogDiplay = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setTitle(this.mWifiConfig.SSID);
        builder.setMessage(str);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.ok, new AnonymousClass2(this, 0));
        AlertDialog create = builder.create();
        this.mErrorDialog = create;
        create.show();
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x0074, code lost:

       if (r9 != 8) goto L38;
    */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x00c6, code lost:

       if (r9 == 2) goto L12;
    */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x00d3, code lost:

       if (r9 > 0) goto L12;
    */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0172  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void showErrorTextMsg(int r9) {
        /*
            Method dump skipped, instructions count: 386
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.details.WifiConnectPreferenceController.showErrorTextMsg(int):void");
    }

    @Override // com.samsung.android.settings.wifi.details.SecWifiPreferenceControllerHelper.ValidationUpdater
    public final void update(boolean z) {
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "update triggered by Validator ", "WifiConnectPrefController", z);
        this.mExternalValidationResult = z;
        setButtonEnable(isSubmittable());
    }

    @Override // com.samsung.android.settings.wifi.details.WifiConfigurePreferenceController
    public final void setupPhase2Method(int i) {}
}

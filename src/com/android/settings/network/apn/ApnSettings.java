package com.android.settings.network.apn;

import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothPan;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.provider.Telephony;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.DefaultRingtonePreference$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.RestrictedSettingsFragment;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.network.telephony.SubscriptionRepository;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.RestrictedLockUtils;

import com.google.android.material.tabs.TabLayout;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.connection.ConnectionsUtils;
import com.samsung.android.settings.connection.SecMultiSIMTabInterface;
import com.samsung.android.settings.connection.SecSimFeatureProvider;
import com.samsung.android.settings.connection.SecSimFeatureProviderImpl;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.network.apn.ApnUtils;
import com.samsung.android.settings.network.apn.OmaCpUtils;
import com.samsung.android.settings.widget.SecDividerItemDecorator;
import com.sec.ims.ImsManager;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ApnSettings extends RestrictedSettingsFragment
        implements Preference.OnPreferenceChangeListener, SecMultiSIMTabInterface {
    public static final String[] CARRIERS_PROJECTION;
    public static final boolean DEBUG;
    public static final Uri PREFERAPN_URI;
    public final int iconAlphaDisableValue;
    public final int iconAlphaEnableValue;
    public final AnonymousClass3 mAirplainModeEnabledObserver;
    public boolean mAllowAddingApns;
    public final AnonymousClass1 mApnRestoreReceiver;
    public final BluetoothAdapter mBluetoothAdapter;
    public final AnonymousClass4 mBluetoothListener;
    public final Object mBluetoothPanLock;
    public ContentResolver mContentResolver;
    public int mCurrentSIMSlot;
    public boolean mHidePresetApnDetails;
    public final AnonymousClass1 mHotSwapReceiver;
    public ImsManager mImsManager;
    public boolean mIsBtTetherOn;
    public boolean mIsEditableMode;
    public boolean mIsImsServiceConnected;
    public boolean mLockAPNWhenTetheringOn;
    public String mMccMnc;
    public String mMvnoCandidateMatchData;
    public String mMvnoCandidateType;
    public String mMvnoMatchData;
    public String mMvnoType;
    public PreferredApnRepository mPreferredApnRepositorySim1;
    public PreferredApnRepository mPreferredApnRepositorySim2;
    public BluetoothPan mProxy;
    public boolean mRestoreDefaultApnMode;
    public AlertDialog mRestoreDlg;
    public SecSimFeatureProvider mSecSimFeatureProvider;
    public boolean mSimTabChangeFlag;
    public int mSubId;
    public SubscriptionInfo mSubscriptionInfo;
    public Toast mTabToast;
    public TelephonyManager mTelephonyManager;
    public final AnonymousClass1 mTetherChangeReceiver;
    public IntentFilter mTetherStateFilter;
    public boolean mUnavailable;
    public UserManager mUserManager;
    public final List mVZWAPNs;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.network.apn.ApnSettings$5, reason: invalid class name */
    public final class AnonymousClass5 extends ProgressDialog {
        @Override // android.app.Dialog
        public final boolean onTouchEvent(MotionEvent motionEvent) {
            return true;
        }
    }

    static {
        String str = Build.TYPE;
        DEBUG = "eng".equals(str) || "userdebug".equals(str);
        CARRIERS_PROJECTION =
                new String[] {
                    "_id",
                    "name",
                    "apn",
                    "type",
                    "mvno_type",
                    "mvno_match_data",
                    "edited",
                    "user_visible"
                };
        PREFERAPN_URI = Uri.parse("content://telephony/carriers/preferapn");
    }

    /* JADX WARN: Type inference failed for: r0v10, types: [com.android.settings.network.apn.ApnSettings$1] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.settings.network.apn.ApnSettings$1] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.settings.network.apn.ApnSettings$1] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.settings.network.apn.ApnSettings$3] */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.settings.network.apn.ApnSettings$4] */
    public ApnSettings() {
        super("no_config_mobile_networks");
        this.mSimTabChangeFlag = false;
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mIsBtTetherOn = false;
        this.mBluetoothPanLock = new Object();
        this.mLockAPNWhenTetheringOn = false;
        this.mVZWAPNs =
                Arrays.asList(
                        "Verizon Internet",
                        "LTE - Verizon Internet",
                        "VZW Roaming Internet",
                        "Tracfone - Verizon",
                        "Tracfone",
                        "Xfinity Mobile",
                        "Visible Internet NEW");
        this.iconAlphaDisableValue = 66;
        this.iconAlphaEnableValue = 255;
        final int i = 0;
        this.mHotSwapReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.settings.network.apn.ApnSettings.1
                    public final /* synthetic */ ApnSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        switch (i) {
                            case 0:
                                if ("com.samsung.intent.action.SIMHOTSWAP"
                                        .equals(intent.getAction())) {
                                    Log.d("ApnSettings", "FINISH : HOTSWAP");
                                    this.this$0.finish();
                                    break;
                                }
                                break;
                            case 1:
                                if ("android.net.conn.TETHER_STATE_CHANGED"
                                        .equals(intent.getAction())) {
                                    Log.d("ApnSettings", "onReceive : ACTION_TETHER_STATE_CHANGED");
                                    ApnSettings apnSettings = this.this$0;
                                    boolean z = ApnSettings.DEBUG;
                                    apnSettings.fillList();
                                    break;
                                }
                                break;
                            default:
                                Log.d(
                                        "ApnSettings",
                                        "mRestoreDefaultApnMode : "
                                                + this.this$0.mRestoreDefaultApnMode
                                                + ", intent.getAction() : "
                                                + intent.getAction());
                                String action = intent.getAction();
                                if ("com.samsung.intent.action.CSC_CONNECTION_RESET_DONE"
                                                .equals(action)
                                        && this.this$0.mRestoreDefaultApnMode) {
                                    Log.i("ApnSettings", "restore apn done");
                                    ApnSettings apnSettings2 = this.this$0;
                                    FragmentActivity activity = apnSettings2.getActivity();
                                    if (activity == null) {
                                        apnSettings2.mRestoreDefaultApnMode = false;
                                    } else {
                                        apnSettings2.fillList();
                                        apnSettings2.getPreferenceScreen().setEnabled(true);
                                        apnSettings2.mRestoreDefaultApnMode = false;
                                        apnSettings2.removeDialog(1001);
                                        Toast.makeText(
                                                        activity,
                                                        apnSettings2
                                                                .getResources()
                                                                .getString(
                                                                        R.string
                                                                                .restore_default_apn_completed),
                                                        1)
                                                .show();
                                    }
                                }
                                if ("android.intent.action.SIM_PROFILE_UPDATE_DONE".equals(action)
                                        || "android.intent.action.UPDATE_CURRENT_CARRIER_DONE"
                                                .equals(action)
                                        || "com.samsung.intent.action.SET_PREFERREDAPN_UPDATED"
                                                .equals(action)) {
                                    ApnSettings apnSettings3 = this.this$0;
                                    if (!apnSettings3.mRestoreDefaultApnMode) {
                                        Log.i("ApnSettings", "Receive: Update intent");
                                        this.this$0.fillList();
                                        break;
                                    } else {
                                        apnSettings3.showDialog(1001);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mTetherChangeReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.settings.network.apn.ApnSettings.1
                    public final /* synthetic */ ApnSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        switch (i2) {
                            case 0:
                                if ("com.samsung.intent.action.SIMHOTSWAP"
                                        .equals(intent.getAction())) {
                                    Log.d("ApnSettings", "FINISH : HOTSWAP");
                                    this.this$0.finish();
                                    break;
                                }
                                break;
                            case 1:
                                if ("android.net.conn.TETHER_STATE_CHANGED"
                                        .equals(intent.getAction())) {
                                    Log.d("ApnSettings", "onReceive : ACTION_TETHER_STATE_CHANGED");
                                    ApnSettings apnSettings = this.this$0;
                                    boolean z = ApnSettings.DEBUG;
                                    apnSettings.fillList();
                                    break;
                                }
                                break;
                            default:
                                Log.d(
                                        "ApnSettings",
                                        "mRestoreDefaultApnMode : "
                                                + this.this$0.mRestoreDefaultApnMode
                                                + ", intent.getAction() : "
                                                + intent.getAction());
                                String action = intent.getAction();
                                if ("com.samsung.intent.action.CSC_CONNECTION_RESET_DONE"
                                                .equals(action)
                                        && this.this$0.mRestoreDefaultApnMode) {
                                    Log.i("ApnSettings", "restore apn done");
                                    ApnSettings apnSettings2 = this.this$0;
                                    FragmentActivity activity = apnSettings2.getActivity();
                                    if (activity == null) {
                                        apnSettings2.mRestoreDefaultApnMode = false;
                                    } else {
                                        apnSettings2.fillList();
                                        apnSettings2.getPreferenceScreen().setEnabled(true);
                                        apnSettings2.mRestoreDefaultApnMode = false;
                                        apnSettings2.removeDialog(1001);
                                        Toast.makeText(
                                                        activity,
                                                        apnSettings2
                                                                .getResources()
                                                                .getString(
                                                                        R.string
                                                                                .restore_default_apn_completed),
                                                        1)
                                                .show();
                                    }
                                }
                                if ("android.intent.action.SIM_PROFILE_UPDATE_DONE".equals(action)
                                        || "android.intent.action.UPDATE_CURRENT_CARRIER_DONE"
                                                .equals(action)
                                        || "com.samsung.intent.action.SET_PREFERREDAPN_UPDATED"
                                                .equals(action)) {
                                    ApnSettings apnSettings3 = this.this$0;
                                    if (!apnSettings3.mRestoreDefaultApnMode) {
                                        Log.i("ApnSettings", "Receive: Update intent");
                                        this.this$0.fillList();
                                        break;
                                    } else {
                                        apnSettings3.showDialog(1001);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        this.mAirplainModeEnabledObserver =
                new ContentObserver(
                        new Handler()) { // from class:
                                         // com.android.settings.network.apn.ApnSettings.3
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        super.onChange(z);
                        ApnSettings apnSettings = ApnSettings.this;
                        boolean z2 = ApnSettings.DEBUG;
                        if (Settings.Global.getInt(
                                        apnSettings.getContentResolver(), "airplane_mode_on", 0)
                                == 1) {
                            Log.d("ApnSettings", "FINISH : airplane mode on");
                            ApnSettings.this.finish();
                        }
                    }
                };
        this.mBluetoothListener =
                new BluetoothProfile
                        .ServiceListener() { // from class:
                                             // com.android.settings.network.apn.ApnSettings.4
                    @Override // android.bluetooth.BluetoothProfile.ServiceListener
                    public final void onServiceConnected(
                            int i3, BluetoothProfile bluetoothProfile) {
                        ApnSettings apnSettings;
                        BluetoothPan bluetoothPan;
                        Log.d("ApnSettings", "BluetoothListener onServiceConnected");
                        synchronized (ApnSettings.this.mBluetoothPanLock) {
                            apnSettings = ApnSettings.this;
                            bluetoothPan = (BluetoothPan) bluetoothProfile;
                            apnSettings.mProxy = bluetoothPan;
                        }
                        apnSettings.mIsBtTetherOn = bluetoothPan.isTetheringOn();
                        ApnSettings.this.fillList();
                    }

                    @Override // android.bluetooth.BluetoothProfile.ServiceListener
                    public final void onServiceDisconnected(int i3) {
                        ApnSettings apnSettings;
                        Log.d("ApnSettings", "BluetoothListener onServiceDisconnected");
                        synchronized (ApnSettings.this.mBluetoothPanLock) {
                            apnSettings = ApnSettings.this;
                            apnSettings.mProxy = null;
                        }
                        apnSettings.mIsBtTetherOn = false;
                    }
                };
        final int i3 = 2;
        this.mApnRestoreReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.settings.network.apn.ApnSettings.1
                    public final /* synthetic */ ApnSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        switch (i3) {
                            case 0:
                                if ("com.samsung.intent.action.SIMHOTSWAP"
                                        .equals(intent.getAction())) {
                                    Log.d("ApnSettings", "FINISH : HOTSWAP");
                                    this.this$0.finish();
                                    break;
                                }
                                break;
                            case 1:
                                if ("android.net.conn.TETHER_STATE_CHANGED"
                                        .equals(intent.getAction())) {
                                    Log.d("ApnSettings", "onReceive : ACTION_TETHER_STATE_CHANGED");
                                    ApnSettings apnSettings = this.this$0;
                                    boolean z = ApnSettings.DEBUG;
                                    apnSettings.fillList();
                                    break;
                                }
                                break;
                            default:
                                Log.d(
                                        "ApnSettings",
                                        "mRestoreDefaultApnMode : "
                                                + this.this$0.mRestoreDefaultApnMode
                                                + ", intent.getAction() : "
                                                + intent.getAction());
                                String action = intent.getAction();
                                if ("com.samsung.intent.action.CSC_CONNECTION_RESET_DONE"
                                                .equals(action)
                                        && this.this$0.mRestoreDefaultApnMode) {
                                    Log.i("ApnSettings", "restore apn done");
                                    ApnSettings apnSettings2 = this.this$0;
                                    FragmentActivity activity = apnSettings2.getActivity();
                                    if (activity == null) {
                                        apnSettings2.mRestoreDefaultApnMode = false;
                                    } else {
                                        apnSettings2.fillList();
                                        apnSettings2.getPreferenceScreen().setEnabled(true);
                                        apnSettings2.mRestoreDefaultApnMode = false;
                                        apnSettings2.removeDialog(1001);
                                        Toast.makeText(
                                                        activity,
                                                        apnSettings2
                                                                .getResources()
                                                                .getString(
                                                                        R.string
                                                                                .restore_default_apn_completed),
                                                        1)
                                                .show();
                                    }
                                }
                                if ("android.intent.action.SIM_PROFILE_UPDATE_DONE".equals(action)
                                        || "android.intent.action.UPDATE_CURRENT_CARRIER_DONE"
                                                .equals(action)
                                        || "com.samsung.intent.action.SET_PREFERREDAPN_UPDATED"
                                                .equals(action)) {
                                    ApnSettings apnSettings3 = this.this$0;
                                    if (!apnSettings3.mRestoreDefaultApnMode) {
                                        Log.i("ApnSettings", "Receive: Update intent");
                                        this.this$0.fillList();
                                        break;
                                    } else {
                                        apnSettings3.showDialog(1001);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
    }

    public final boolean dualSimMPSDataLock() {
        if (TelephonyManager.getDefault().getSupportedModemCount() > 1
                && "AIS"
                        .equals(
                                SystemProperties.get(
                                        "ro.boot.carrierid",
                                        com.samsung.android.knox.net.apn.ApnSettings.MVNO_NONE))
                && ("52001".equals(this.mMccMnc) || "52003".equals(this.mMccMnc))) {
            Log.d("ApnSettings", "DataLock operator");
            return true;
        }
        Log.d("ApnSettings", "DataLockFeature is disable");
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x0339, code lost:

       r3 = false;
    */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x038f, code lost:

       if (isVZWApnList(r14) == false) goto L151;
    */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x03ab, code lost:

       if (isVZWApnList(r14) == false) goto L151;
    */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x03ec, code lost:

       if ("ims,xcap".equals(r2) != false) goto L151;
    */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x0351, code lost:

       if (isVZWApnList(r14) != false) goto L125;
    */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x035e, code lost:

       if (((com.samsung.android.settings.connection.SecSimFeatureProviderImpl) r26.mSecSimFeatureProvider).isSPRSimInserted(r26.mCurrentSIMSlot) != false) goto L126;
    */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x0299, code lost:

       if (r11 == false) goto L102;
    */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0232, code lost:

       if (com.samsung.android.settings.network.apn.ApnUtils.isVZWConcept(r26.mCurrentSIMSlot) != false) goto L81;
    */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0297, code lost:

       if (r9 == false) goto L102;
    */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x0335, code lost:

       if (isVZWApnList(r14) == false) goto L126;
    */
    /* JADX WARN: Removed duplicated region for block: B:84:0x02ba  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x02ed  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void fillList() {
        /*
            Method dump skipped, instructions count: 1480
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.apn.ApnSettings.fillList():void");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final int getDialogMetricsCategory(int i) {
        return i == 1001 ? 579 : 0;
    }

    public final void getMccMnc() {
        SubscriptionInfo subscriptionInfo = this.mSubscriptionInfo;
        if (subscriptionInfo != null) {
            this.mMccMnc =
                    ((SecSimFeatureProviderImpl) this.mSecSimFeatureProvider)
                            .getSimOperator(subscriptionInfo.getSimSlotIndex());
            Utils$$ExternalSyntheticOutline0.m(
                    new StringBuilder("MccMnc : "), this.mMccMnc, "ApnSettings");
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 12;
    }

    @Override // com.android.settings.RestrictedSettingsFragment
    public final RestrictedLockUtils.EnforcedAdmin getRestrictionEnforcedAdmin() {
        UserHandle of = UserHandle.of(this.mUserManager.getProcessUserId());
        if (!this.mUserManager.hasUserRestriction("no_config_mobile_networks", of)
                || this.mUserManager.hasBaseUserRestriction("no_config_mobile_networks", of)) {
            return null;
        }
        return RestrictedLockUtils.EnforcedAdmin.MULTIPLE_ENFORCED_ADMIN;
    }

    public final void getSimSlotNum() {
        SubscriptionInfo subscriptionInfo = this.mSubscriptionInfo;
        if (subscriptionInfo != null) {
            this.mCurrentSIMSlot = subscriptionInfo.getSimSlotIndex();
            TooltipPopup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Current Sim Slot index : "),
                    this.mCurrentSIMSlot,
                    "ApnSettings");
        }
    }

    public final boolean isCallingState() {
        try {
            TelephonyManager telephonyManager =
                    (TelephonyManager) getContext().getSystemService("phone");
            Log.d("ApnSettings", "isCallingState : " + telephonyManager.getCallState());
            return telephonyManager.getCallState() != 0;
        } catch (NullPointerException unused) {
            Log.e("ApnSettings", "Call Settings fail");
            return false;
        }
    }

    public final boolean isEditableMode(Intent intent) {
        String stringExtra = intent.getStringExtra("vzw");
        String stringExtra2 = intent.getStringExtra("keyString");
        String stringExtra3 = intent.getStringExtra("LTE_TEST");
        StringBuilder m =
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                        "vzwExtra : ",
                        stringExtra,
                        ", sprExtra : ",
                        stringExtra2,
                        ", lteTestExtra : ");
        m.append(stringExtra3);
        Log.d("ApnSettings", m.toString());
        boolean z = true;
        if (TextUtils.isEmpty(stringExtra)
                && !"hidden".equals(stringExtra2)
                && !"editable".equals(stringExtra3)) {
            if (((SecSimFeatureProviderImpl) this.mSecSimFeatureProvider)
                            .isSPRSimInserted(this.mCurrentSIMSlot)
                    || !Rune.isSprModel()) {
                z = false;
            }
        }
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m("editableMode : ", "ApnSettings", z);
        return z;
    }

    public final boolean isVZWApnList(String str) {
        boolean z = false;
        if (!TextUtils.isEmpty(str)) {
            Iterator it = this.mVZWAPNs.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (str.equalsIgnoreCase((String) it.next())) {
                    z = true;
                    break;
                }
            }
        }
        Log.d("ApnSettings", "isVZWApnList : " + z + ", name : " + str);
        return z;
    }

    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mEmptyTextView.setText(R.string.apn_settings_not_available);
        boolean isUiRestricted = isUiRestricted();
        this.mUnavailable = isUiRestricted;
        setHasOptionsMenu(!isUiRestricted);
        if (this.mUnavailable) {
            Log.e("ApnSettings", "isUiRestricted removeAll!!!");
            setPreferenceScreen(new PreferenceScreen(getPrefContext(), null));
            getPreferenceScreen().removeAll();
        }
    }

    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mSubId = activity.getIntent().getIntExtra("sub_id", -1);
        if (Rune.isEnabledHidingByOpportunisticEsim(getContext())) {
            this.mPreferredApnRepositorySim1 = new PreferredApnRepository(activity, this.mSubId);
        } else {
            SubscriptionManager subscriptionManager =
                    (SubscriptionManager)
                            getContext().getSystemService("telephony_subscription_service");
            if (subscriptionManager != null) {
                List<SubscriptionInfo> completeActiveSubscriptionInfoList =
                        subscriptionManager.getCompleteActiveSubscriptionInfoList();
                if (completeActiveSubscriptionInfoList == null
                        || completeActiveSubscriptionInfoList.size() != 2) {
                    this.mPreferredApnRepositorySim1 =
                            new PreferredApnRepository(activity, this.mSubId);
                } else {
                    this.mPreferredApnRepositorySim1 =
                            new PreferredApnRepository(
                                    activity,
                                    completeActiveSubscriptionInfoList.get(0).getSubscriptionId());
                    this.mPreferredApnRepositorySim2 =
                            new PreferredApnRepository(
                                    activity,
                                    completeActiveSubscriptionInfoList.get(1).getSubscriptionId());
                }
            }
        }
        addPreferencesFromResource(R.xml.apn_settings);
        this.mSubscriptionInfo =
                SubscriptionManager.from(getActivity()).getActiveSubscriptionInfo(this.mSubId);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        SecSimFeatureProvider secSimFeatureProvider = featureFactoryImpl.getSecSimFeatureProvider();
        this.mSecSimFeatureProvider = secSimFeatureProvider;
        Context context = getContext();
        SecSimFeatureProviderImpl secSimFeatureProviderImpl =
                (SecSimFeatureProviderImpl) secSimFeatureProvider;
        secSimFeatureProviderImpl.getClass();
        secSimFeatureProviderImpl.mContext = context.getApplicationContext();
        getSimSlotNum();
        getMccMnc();
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("need_search_icon_in_action_bar", false);
        setArguments(bundle2);
        Log.d("ApnSettings", "SUB ID : " + this.mSubId);
        this.mLockAPNWhenTetheringOn = Rune.isJapanDCMModel();
        Log.d("ApnSettings", "mLockAPNWhenTetheringOn : " + this.mLockAPNWhenTetheringOn);
        this.mIsEditableMode = isEditableMode(getActivity().getIntent());
        this.mTetherStateFilter = new IntentFilter("android.net.conn.TETHER_STATE_CHANGED");
        getActivity()
                .registerReceiver(
                        this.mHotSwapReceiver,
                        new IntentFilter("com.samsung.intent.action.SIMHOTSWAP"),
                        4);
        this.mContentResolver = getContentResolver();
        this.mOnlyAvailableForAdmins = true;
        this.mTelephonyManager =
                (TelephonyManager) activity.getSystemService(TelephonyManager.class);
        PersistableBundle configForSubId =
                ((CarrierConfigManager) getSystemService("carrier_config"))
                        .getConfigForSubId(this.mSubId);
        this.mAllowAddingApns = true;
        if (Rune.isDomesticLGTModel()) {
            boolean equals =
                    "45006"
                            .equals(
                                    ConnectionsUtils.getSimOperator(
                                            getContext(),
                                            SubscriptionManager.getSlotIndex(
                                                    SubscriptionManager
                                                            .getDefaultDataSubscriptionId())));
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m("isLgSim : ", "ApnUtils", equals);
            if (equals) {
                this.mAllowAddingApns = false;
            }
        }
        this.mHidePresetApnDetails = configForSubId.getBoolean("hide_preset_apn_details_bool");
        this.mUserManager = UserManager.get(activity);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        if (i != 1001) {
            return null;
        }
        AnonymousClass5 anonymousClass5 = new AnonymousClass5(getActivity());
        anonymousClass5.setMessage(getResources().getString(R.string.restore_default_apn));
        anonymousClass5.setCancelable(false);
        return anonymousClass5;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if (!this.mUnavailable) {
            if (this.mAllowAddingApns) {
                menu.add(0, 1, 0, getResources().getString(R.string.common_add))
                        .setIcon(R.drawable.sec_ic_apn_add)
                        .setShowAsAction(1);
            }
            menu.add(0, 2, 0, R.string.configuration_messages);
            menu.add(0, 3, 0, getResources().getString(R.string.menu_restore))
                    .setIcon(android.R.drawable.ic_menu_upload);
        }
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return applyTabViewIfNeeded(
                getContext(), super.onCreateView(layoutInflater, viewGroup, bundle));
    }

    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        AlertDialog alertDialog = this.mRestoreDlg;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mRestoreDlg.dismiss();
        }
        getActivity().unregisterReceiver(this.mHotSwapReceiver);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        final int i = 1;
        boolean z = false;
        z = false;
        Log.d("ApnSettings", "Item select id : " + menuItem.getItemId());
        int itemId = menuItem.getItemId();
        if (itemId != 1) {
            if (itemId == 2) {
                Context context = getContext();
                int i2 = OmaCpUtils.sOmaCpCounts;
                Log.d("OmaCpUtils", "broadcastSetCp()");
                if (context != null) {
                    Intent intent = new Intent();
                    DefaultRingtonePreference$$ExternalSyntheticOutline0.m(
                            "com.wsomacp", "com.wsomacp.ui.FrontControllerActivity", intent);
                    try {
                        PendingIntent.getActivity(context, 0, intent, 167772160).send();
                    } catch (Exception e) {
                        Log.d("OmaCpUtils", e.getMessage());
                    }
                }
                return true;
            }
            if (itemId != 3) {
                if (itemId != 16908332) {
                    return super.onOptionsItemSelected(menuItem);
                }
                finish();
                return true;
            }
            LoggingHelper.insertEventLogging(12, 3623);
            if (!dualSimMPSDataLock()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.apn_restore_title);
                builder.setMessage(R.string.apn_restore_warning);
                final int i3 = z ? 1 : 0;
                builder.setPositiveButton(
                        R.string.common_reset,
                        new DialogInterface.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.network.apn.ApnSettings.6
                            public final /* synthetic */ ApnSettings this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i4) {
                                switch (i3) {
                                    case 0:
                                        ApnSettings apnSettings = this.this$0;
                                        boolean z2 = ApnSettings.DEBUG;
                                        apnSettings.getClass();
                                        Log.i("ApnSettings", "restoreDefaultApn");
                                        apnSettings.showDialog(1001);
                                        apnSettings.mRestoreDefaultApnMode = true;
                                        PreferredApnRepository preferredApnRepository =
                                                apnSettings.mPreferredApnRepositorySim1;
                                        if (preferredApnRepository.subId == apnSettings.mSubId) {
                                            LifecycleOwner lifecycleOwner =
                                                    apnSettings.getViewLifecycleOwner();
                                            ApnSettings$$ExternalSyntheticLambda3
                                                    apnSettings$$ExternalSyntheticLambda3 =
                                                            new ApnSettings$$ExternalSyntheticLambda3();
                                            Intrinsics.checkNotNullParameter(
                                                    lifecycleOwner, "lifecycleOwner");
                                            BuildersKt.launch$default(
                                                    LifecycleOwnerKt.getLifecycleScope(
                                                            lifecycleOwner),
                                                    null,
                                                    null,
                                                    new PreferredApnRepository$restorePreferredApn$1(
                                                            apnSettings$$ExternalSyntheticLambda3,
                                                            preferredApnRepository,
                                                            null),
                                                    3);
                                        } else {
                                            PreferredApnRepository preferredApnRepository2 =
                                                    apnSettings.mPreferredApnRepositorySim2;
                                            LifecycleOwner lifecycleOwner2 =
                                                    apnSettings.getViewLifecycleOwner();
                                            ApnSettings$$ExternalSyntheticLambda3
                                                    apnSettings$$ExternalSyntheticLambda32 =
                                                            new ApnSettings$$ExternalSyntheticLambda3();
                                            preferredApnRepository2.getClass();
                                            Intrinsics.checkNotNullParameter(
                                                    lifecycleOwner2, "lifecycleOwner");
                                            BuildersKt.launch$default(
                                                    LifecycleOwnerKt.getLifecycleScope(
                                                            lifecycleOwner2),
                                                    null,
                                                    null,
                                                    new PreferredApnRepository$restorePreferredApn$1(
                                                            apnSettings$$ExternalSyntheticLambda32,
                                                            preferredApnRepository2,
                                                            null),
                                                    3);
                                        }
                                        this.this$0.getClass();
                                        LoggingHelper.insertEventLogging(12, 3626);
                                        break;
                                    default:
                                        this.this$0.getClass();
                                        LoggingHelper.insertEventLogging(12, 3625);
                                        break;
                                }
                            }
                        });
                builder.setNegativeButton(
                        android.R.string.cancel,
                        new DialogInterface.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.network.apn.ApnSettings.6
                            public final /* synthetic */ ApnSettings this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i4) {
                                switch (i) {
                                    case 0:
                                        ApnSettings apnSettings = this.this$0;
                                        boolean z2 = ApnSettings.DEBUG;
                                        apnSettings.getClass();
                                        Log.i("ApnSettings", "restoreDefaultApn");
                                        apnSettings.showDialog(1001);
                                        apnSettings.mRestoreDefaultApnMode = true;
                                        PreferredApnRepository preferredApnRepository =
                                                apnSettings.mPreferredApnRepositorySim1;
                                        if (preferredApnRepository.subId == apnSettings.mSubId) {
                                            LifecycleOwner lifecycleOwner =
                                                    apnSettings.getViewLifecycleOwner();
                                            ApnSettings$$ExternalSyntheticLambda3
                                                    apnSettings$$ExternalSyntheticLambda3 =
                                                            new ApnSettings$$ExternalSyntheticLambda3();
                                            Intrinsics.checkNotNullParameter(
                                                    lifecycleOwner, "lifecycleOwner");
                                            BuildersKt.launch$default(
                                                    LifecycleOwnerKt.getLifecycleScope(
                                                            lifecycleOwner),
                                                    null,
                                                    null,
                                                    new PreferredApnRepository$restorePreferredApn$1(
                                                            apnSettings$$ExternalSyntheticLambda3,
                                                            preferredApnRepository,
                                                            null),
                                                    3);
                                        } else {
                                            PreferredApnRepository preferredApnRepository2 =
                                                    apnSettings.mPreferredApnRepositorySim2;
                                            LifecycleOwner lifecycleOwner2 =
                                                    apnSettings.getViewLifecycleOwner();
                                            ApnSettings$$ExternalSyntheticLambda3
                                                    apnSettings$$ExternalSyntheticLambda32 =
                                                            new ApnSettings$$ExternalSyntheticLambda3();
                                            preferredApnRepository2.getClass();
                                            Intrinsics.checkNotNullParameter(
                                                    lifecycleOwner2, "lifecycleOwner");
                                            BuildersKt.launch$default(
                                                    LifecycleOwnerKt.getLifecycleScope(
                                                            lifecycleOwner2),
                                                    null,
                                                    null,
                                                    new PreferredApnRepository$restorePreferredApn$1(
                                                            apnSettings$$ExternalSyntheticLambda32,
                                                            preferredApnRepository2,
                                                            null),
                                                    3);
                                        }
                                        this.this$0.getClass();
                                        LoggingHelper.insertEventLogging(12, 3626);
                                        break;
                                    default:
                                        this.this$0.getClass();
                                        LoggingHelper.insertEventLogging(12, 3625);
                                        break;
                                }
                            }
                        });
                this.mRestoreDlg = builder.show();
            }
            return true;
        }
        if (!dualSimMPSDataLock()) {
            Intent intent2 =
                    new Intent("android.intent.action.INSERT", Telephony.Carriers.CONTENT_URI);
            intent2.putExtra("sub_id", this.mSubId);
            intent2.addFlags(1);
            if (!TextUtils.isEmpty(this.mMvnoType) && !TextUtils.isEmpty(this.mMvnoMatchData)) {
                intent2.putExtra("mvno_type", this.mMvnoType);
                intent2.putExtra("mvno_match_data", this.mMvnoMatchData);
            }
            getContext();
            if (ApnUtils.isVZWConcept(this.mCurrentSIMSlot)) {
                z = this.mIsEditableMode;
            } else if (Rune.isSprModel()) {
                z = this.mIsEditableMode;
            } else if (Rune.isUsOpenModel()) {
                if (((SecSimFeatureProviderImpl) this.mSecSimFeatureProvider)
                        .isVZWSimInserted(this.mCurrentSIMSlot)) {
                    z = this.mIsEditableMode;
                } else {
                    if (((SecSimFeatureProviderImpl) this.mSecSimFeatureProvider)
                            .isSPRSimInserted(this.mCurrentSIMSlot)) {
                        z = this.mIsEditableMode;
                    }
                }
            }
            intent2.putExtra("editable_mode", z);
            Log.d("ApnSettings", " addNewApn start() ; Details " + intent2.toString());
            startActivity(intent2);
        }
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        if (this.mUnavailable) {
            Log.i("ApnSettings", "isUiRestricted return onPause");
            return;
        }
        getActivity().unregisterReceiver(this.mApnRestoreReceiver);
        getActivity().unregisterReceiver(this.mTetherChangeReceiver);
        ContentResolver contentResolver = OmaCpUtils.sActivity.getContentResolver();
        if (OmaCpUtils.sObserver == null) {
            OmaCpUtils.sObserver = new OmaCpUtils.AnonymousClass1(new Handler());
        }
        contentResolver.unregisterContentObserver(OmaCpUtils.sObserver);
        OmaCpUtils.sActivity = null;
        OmaCpUtils.sObserver = null;
        this.mContentResolver.unregisterContentObserver(this.mAirplainModeEnabledObserver);
        removeDialog(1001);
        this.mRestoreDefaultApnMode = false;
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter == null || !this.mLockAPNWhenTetheringOn) {
            return;
        }
        bluetoothAdapter.closeProfileProxy(5, this.mProxy);
        this.mProxy = null;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Log.d(
                "ApnSettings",
                "onPreferenceChange(): Preference - "
                        + preference
                        + ", newValue - "
                        + obj
                        + ", newValue type - "
                        + obj.getClass());
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("mSubid : "), this.mSubId, "ApnSettings");
        if (!(obj instanceof String)) {
            return true;
        }
        if (this.mPreferredApnRepositorySim1.subId == this.mSubId) {
            Log.d("ApnSettings", "mPreferredApnRepositorySim1 called");
            this.mPreferredApnRepositorySim1.setPreferredApn((String) obj);
            return true;
        }
        Log.d("ApnSettings", "mPreferredApnRepositorySim2 called");
        this.mPreferredApnRepositorySim2.setPreferredApn((String) obj);
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0039, code lost:

       if (((com.samsung.android.settings.connection.SecSimFeatureProviderImpl) r11.mSecSimFeatureProvider).isVZWSimInserted(r11.mCurrentSIMSlot) != false) goto L5;
    */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0059, code lost:

       if (((com.samsung.android.settings.connection.SecSimFeatureProviderImpl) r11.mSecSimFeatureProvider).isVZWSimInserted(r11.mCurrentSIMSlot) != false) goto L5;
    */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x006c, code lost:

       if (r11.mIsBtTetherOn != false) goto L5;
    */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00b6, code lost:

       if (((com.samsung.android.settings.connection.SecSimFeatureProviderImpl) r11.mSecSimFeatureProvider).isVZWSimInserted(r11.mCurrentSIMSlot) != false) goto L35;
    */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00cd, code lost:

       if (com.android.settings.network.apn.ApnSettings.DEBUG == false) goto L35;
    */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00f9, code lost:

       if (((com.samsung.android.settings.connection.SecSimFeatureProviderImpl) r11.mSecSimFeatureProvider).isSPRSimInserted(r11.mCurrentSIMSlot) != false) goto L35;
    */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0113, code lost:

       if (r11.mIsBtTetherOn != false) goto L35;
    */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x01b0, code lost:

       if (com.samsung.android.settings.network.apn.OmaCpUtils.sOmaCpCounts <= 0) goto L98;
    */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0091  */
    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onPrepareOptionsMenu(android.view.Menu r12) {
        /*
            Method dump skipped, instructions count: 449
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.apn.ApnSettings.onPrepareOptionsMenu(android.view.Menu):void");
    }

    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Log.i("ApnSettings", "onResume Start");
        if (Settings.Global.getInt(getContentResolver(), "airplane_mode_on", 0) == 1) {
            Log.i("ApnSettings", "onResume : Airplane mode ON");
            finish();
        }
        if (this.mUnavailable) {
            Log.i("ApnSettings", "isUiRestricted return onResume");
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.intent.action.CSC_CONNECTION_RESET_DONE");
        intentFilter.addAction("android.intent.action.SIM_PROFILE_UPDATE_DONE");
        intentFilter.addAction("android.intent.action.UPDATE_CURRENT_CARRIER_DONE");
        intentFilter.addAction("com.samsung.intent.action.SET_PREFERREDAPN_UPDATED");
        getActivity().registerReceiver(this.mApnRestoreReceiver, intentFilter, 2);
        getActivity().registerReceiver(this.mTetherChangeReceiver, this.mTetherStateFilter, 2);
        this.mContentResolver.registerContentObserver(
                Settings.Global.getUriFor("airplane_mode_on"),
                true,
                this.mAirplainModeEnabledObserver);
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter != null && this.mLockAPNWhenTetheringOn) {
            bluetoothAdapter.getProfileProxy(getActivity(), this.mBluetoothListener, 5);
        }
        FragmentActivity activity = getActivity();
        if (OmaCpUtils.sActivity == null) {
            OmaCpUtils.sActivity = activity;
        }
        try {
            ContentResolver contentResolver = OmaCpUtils.sActivity.getContentResolver();
            Uri uri = OmaCpUtils.CP_URI;
            if (OmaCpUtils.sObserver == null) {
                OmaCpUtils.sObserver = new OmaCpUtils.AnonymousClass1(new Handler());
            }
            contentResolver.registerContentObserver(uri, true, OmaCpUtils.sObserver);
        } catch (Exception unused) {
            Log.e("OmaCpUtils", "Can't register ContentObserver");
        }
        if (!this.mRestoreDefaultApnMode) {
            Log.i("ApnSettings", "onResume Filllist");
            fillList();
        }
        getActivity().invalidateOptionsMenu();
    }

    @Override // com.samsung.android.settings.connection.SecMultiSIMTabInterface
    public final void onTabChanged(TabLayout.Tab tab) {
        TabLayout tabLayout;
        int intValue = ((Integer) tab.tag).intValue();
        if (SubscriptionManager.getSubId(intValue) != null) {
            this.mSubId = SubscriptionManager.getSubId(intValue)[0];
        } else {
            Log.d("ApnSettings", "SubscriptionManager.getSubId(slotId) is NULL");
        }
        this.mSubscriptionInfo =
                SubscriptionManager.from(getActivity()).getActiveSubscriptionInfo(this.mSubId);
        getSimSlotNum();
        getMccMnc();
        SubscriptionInfo subscriptionInfo = this.mSubscriptionInfo;
        if (subscriptionInfo != null
                && this.mTelephonyManager.getSimState(subscriptionInfo.getSimSlotIndex()) == 4
                && (tabLayout = (TabLayout) getActivity().findViewById(R.id.sim_tabs)) != null) {
            String string =
                    getResources()
                            .getString(
                                    R.string.sim_not_available,
                                    tabLayout
                                            .getTabAt(tabLayout.getSelectedTabPosition())
                                            .text
                                            .toString());
            Toast toast = this.mTabToast;
            if (toast == null) {
                this.mTabToast = Toast.makeText(getContext(), string, 0);
            } else {
                toast.setText(string);
            }
            this.mTabToast.show();
            Log.d("ApnSettings", "simTabAvailable : false");
            return;
        }
        Log.d("ApnSettings", "simTabAvailable : true");
        this.mCurrentSIMSlot = intValue;
        this.mIsEditableMode = isEditableMode(getActivity().getIntent());
        Log.i("ApnSettings", "Call TabChanged fillList");
        if (!this.mSimTabChangeFlag) {
            LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
            PreferredApnRepository preferredApnRepository = this.mPreferredApnRepositorySim1;
            if (preferredApnRepository != null) {
                preferredApnRepository.collectPreferredApn(
                        viewLifecycleOwner, new ApnSettings$$ExternalSyntheticLambda0(this, 1));
            }
            PreferredApnRepository preferredApnRepository2 = this.mPreferredApnRepositorySim2;
            if (preferredApnRepository2 != null) {
                preferredApnRepository2.collectPreferredApn(
                        viewLifecycleOwner, new ApnSettings$$ExternalSyntheticLambda0(this, 2));
            }
            this.mSimTabChangeFlag = true;
        }
        fillList();
        StringBuilder sb = new StringBuilder("mTabListener mCurrentSIMSlot = ");
        sb.append(this.mCurrentSIMSlot);
        sb.append(" mSubId = ");
        RecyclerView$$ExternalSyntheticOutline0.m(
                sb, this.mSubId, " in TabListener", "ApnSettings");
        LoggingHelper.insertEventLogging(12, 3624);
        getActivity().invalidateOptionsMenu();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setDivider(null);
        getListView()
                .addItemDecoration(
                        new SecDividerItemDecorator(
                                (int)
                                        (getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_widget_list_with_checkbox_margin_end)
                                                + getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_widget_list_with_checkbox_size)
                                                + getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_widget_list_with_checkbox_margin_start)),
                                getContext(),
                                getResources().getDrawable(R.drawable.sec_top_level_list_divider)));
        new SubscriptionRepository(requireContext())
                .collectSubscriptionEnabled(
                        this.mSubId,
                        getViewLifecycleOwner(),
                        new ApnSettings$$ExternalSyntheticLambda0(this, 0));
        LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        PreferredApnRepository preferredApnRepository = this.mPreferredApnRepositorySim1;
        if (preferredApnRepository != null) {
            preferredApnRepository.collectPreferredApn(
                    viewLifecycleOwner, new ApnSettings$$ExternalSyntheticLambda0(this, 1));
        }
        PreferredApnRepository preferredApnRepository2 = this.mPreferredApnRepositorySim2;
        if (preferredApnRepository2 != null) {
            preferredApnRepository2.collectPreferredApn(
                    viewLifecycleOwner, new ApnSettings$$ExternalSyntheticLambda0(this, 2));
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final void showDialog(int i) {
        if (i == 1001 && this.mRestoreDefaultApnMode) {
            Log.d("ApnSettings", "ShowDialog : mRestoreDefaultApnMode");
        } else {
            super.showDialog(i);
        }
    }
}

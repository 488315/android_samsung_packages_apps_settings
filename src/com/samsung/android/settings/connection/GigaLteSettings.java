package com.samsung.android.settings.connection;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemProperties;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SeslSwitchBar;
import androidx.fragment.app.Fragment;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;
import com.android.settings.accounts.ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamMediaService$4$$ExternalSyntheticOutline0;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.search.SearchIndexableRaw;
import com.samsung.android.settings.Rune;
import com.sec.ims.settings.ImsProfile;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class GigaLteSettings extends Fragment implements CompoundButton.OnCheckedChangeListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass36();
    public AnonymousClass37 mAirplaneModeObserver;
    public Handler mHoldingHandler;
    public HotSwapReceiver mHotSwapReceiver;
    public boolean mIsKTtestOnly;
    public boolean mIsMobileDataEnabling;
    public boolean mIsNetworkEnabling;
    public boolean mIsWiFiEnabling;
    public AnonymousClass37 mMobileDataObserver;
    public HotSwapReceiver mMptcpStateReceiver;
    public AnonymousClass35 mRemoveProgress;
    public SettingsMainSwitchBar mSwitchBar;
    public TelephonyManager mTelephonyManager;
    public WifiManager mWifiManager;
    public ProgressDialog progressDialog;
    public AlertDialog.Builder mAlertDialog = null;
    public boolean mIsSupport5G = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.connection.GigaLteSettings$36, reason: invalid class name */
    public final class AnonymousClass36 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider, com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            Resources resources = context.getResources();
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            if (Rune.isDomesticSKTModel()) {
                searchIndexableRaw.title = resources.getString(R.string.band_lte_title);
                searchIndexableRaw.keywords = Utils.getKeywordForSearch(context, R.string.band_lte_title);
                searchIndexableRaw.screenTitle = resources.getString(R.string.band_lte_title);
            } else {
                searchIndexableRaw.title = resources.getString(R.string.giga_lte_title);
                searchIndexableRaw.keywords = Utils.getKeywordForSearch(context, R.string.giga_lte_title);
                searchIndexableRaw.screenTitle = resources.getString(R.string.giga_lte_title);
            }
            arrayList.add(searchIndexableRaw);
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider, com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.className = GigaLteSettings.class.getName();
            return Arrays.asList(searchIndexableResource);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class HotSwapReceiver extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ GigaLteSettings this$0;

        public /* synthetic */ HotSwapReceiver(GigaLteSettings gigaLteSettings, int i) {
            this.$r8$classId = i;
            this.this$0 = gigaLteSettings;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            int intExtra;
            switch (this.$r8$classId) {
                case 0:
                    String action = intent.getAction();
                    Log.d("mptcp", "HotSwapReceiver: onReceive action=" + action);
                    if ("com.samsung.intent.action.SIMHOTSWAP".equals(action)) {
                        GigaLteSettings gigaLteSettings = this.this$0;
                        BaseSearchIndexProvider baseSearchIndexProvider = GigaLteSettings.SEARCH_INDEX_DATA_PROVIDER;
                        gigaLteSettings.checkSwitchDisableStatus();
                        break;
                    }
                    break;
                default:
                    String action2 = intent.getAction();
                    if (!AudioStreamMediaService$4$$ExternalSyntheticOutline0.m("MPTCPStateReceiver: onReceive action=", action2, "GigaLteSettings", "com.samsung.android.mptcp.MPTCP_STATE")) {
                        if ("android.net.wifi.WIFI_STATE_CHANGED".equals(action2) && 2 != (intExtra = intent.getIntExtra("wifi_state", 4))) {
                            GigaLteSettings gigaLteSettings2 = this.this$0;
                            BaseSearchIndexProvider baseSearchIndexProvider2 = GigaLteSettings.SEARCH_INDEX_DATA_PROVIDER;
                            gigaLteSettings2.getClass();
                            boolean z = intExtra == 3;
                            GigaLteSettings gigaLteSettings3 = this.this$0;
                            if (!gigaLteSettings3.mIsNetworkEnabling || !gigaLteSettings3.mIsWiFiEnabling || !z) {
                                if (gigaLteSettings3.mIsWiFiEnabling) {
                                    gigaLteSettings3.mIsWiFiEnabling = false;
                                    if (!z) {
                                        if (gigaLteSettings3.progressDialog.isShowing()) {
                                            GigaLteSettings gigaLteSettings4 = this.this$0;
                                            gigaLteSettings4.mHoldingHandler.removeCallbacks(gigaLteSettings4.mRemoveProgress);
                                            this.this$0.progressDialog.dismiss();
                                        }
                                        this.this$0.mSwitchBar.setEnabled(true);
                                        GigaLteSettings gigaLteSettings5 = this.this$0;
                                        gigaLteSettings5.mSwitchBar.setChecked(Settings.System.getInt(gigaLteSettings5.getContext().getContentResolver(), "mptcp_value", 0) != 0);
                                        break;
                                    } else {
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                        break;
                                    }
                                }
                            } else {
                                gigaLteSettings3.mIsWiFiEnabling = false;
                                if (!gigaLteSettings3.mIsMobileDataEnabling) {
                                    gigaLteSettings3.mIsNetworkEnabling = false;
                                    gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    break;
                                }
                            }
                        }
                    } else {
                        ProgressDialog progressDialog = this.this$0.progressDialog;
                        if (progressDialog != null && progressDialog.isShowing()) {
                            GigaLteSettings gigaLteSettings6 = this.this$0;
                            gigaLteSettings6.mHoldingHandler.removeCallbacks(gigaLteSettings6.mRemoveProgress);
                            this.this$0.progressDialog.dismiss();
                        }
                        this.this$0.mSwitchBar.setEnabled(true);
                        Log.d("GigaLteSettings", "MPTCP State intent is received");
                        int intExtra2 = intent.getIntExtra("mptcp_state", 0);
                        if (intExtra2 != 0) {
                            if (intExtra2 != 1) {
                                Log.d("GigaLteSettings", "Cannot enable/disable MPTCP");
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            } else {
                                this.this$0.mSwitchBar.setChecked(true);
                                break;
                            }
                        } else {
                            this.this$0.mSwitchBar.setChecked(false);
                            break;
                        }
                    }
                    break;
            }
        }
    }

    public final void checkSwitchDisableStatus() {
        this.mSwitchBar.setEnabled(true);
        if (isRoaming() || ConnectionsUtils.isAirplaneModeEnabled(getContext()) || !isSimValid()) {
            this.mSwitchBar.setEnabled(false);
        }
    }

    public final String getChangedText(String str) {
        return str.replace(getContext().getString(R.string.wifi_watchdog_connectivity_check), getContext().getString(R.string.wifi_switch_to_mobile_data));
    }

    public final boolean isRoaming() {
        boolean isRoaming = ConnectionsUtils.isRoaming(getContext());
        if (!ConnectionsUtils.isAisSIMValid(getContext()) || !isRoaming) {
            return isRoaming;
        }
        Log.d("GigaLteSettings", "isRoaming() isAisSIMValid now and isRoaming");
        int phoneCount = this.mTelephonyManager.getPhoneCount();
        boolean z = true;
        for (int i = 0; i < phoneCount; i++) {
            String networkOperator = this.mTelephonyManager.getNetworkOperator(SubscriptionManager.getSubId(i)[0]);
            if (!TextUtils.isEmpty(networkOperator) && networkOperator.contains("52015")) {
                Log.d("GigaLteSettings", "isRoaming() current networkId = ".concat(networkOperator));
                z = false;
            }
        }
        return z;
    }

    public final boolean isSimPresent() {
        int phoneCount = this.mTelephonyManager.getPhoneCount();
        boolean z = false;
        for (int i = 0; i < phoneCount; i++) {
            if (this.mTelephonyManager.getSimState(i) != 1) {
                z = true;
            }
        }
        return z;
    }

    public final boolean isSimValid() {
        String subscriberId;
        if (Rune.isDomesticSKTModel() || ConnectionsUtils.isFastWebSIMValid(getContext()) || ConnectionsUtils.isAveaSIMValid(getContext()) || ConnectionsUtils.isVodafoneSIMValid(getContext())) {
            return true;
        }
        if (ConnectionsUtils.isAisSIMValid(getContext())) {
            Context context = getContext();
            int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
            int phoneId = SubscriptionManager.getPhoneId(defaultDataSubscriptionId);
            if (!SubscriptionManager.isValidSubscriptionId(defaultDataSubscriptionId)) {
                phoneId = 0;
            }
            String simOperator = ConnectionsUtils.getSimOperator(context, phoneId);
            boolean z = simOperator != null && simOperator.contains("52003");
            StringBuilder sb = new StringBuilder("slodId: ");
            sb.append(phoneId);
            sb.append("/operator: ");
            sb.append(simOperator);
            sb.append("/isAisSimValid: ");
            ActionBarContextView$$ExternalSyntheticOutline0.m(sb, z, "ConnectionsUtils");
            if (z) {
                return true;
            }
        }
        return isSimPresent() && (subscriberId = this.mTelephonyManager.getSubscriberId()) != null && Rune.isDomesticKTTModel() && (subscriberId.contains("45002") || subscriberId.contains("45008"));
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsMainSwitchBar settingsMainSwitchBar = ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.show();
        this.mSwitchBar.setChecked(Settings.System.getInt(getContext().getContentResolver(), "mptcp_value", 0) != 0);
        this.mSwitchBar.addOnSwitchChangeListener(this);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        WifiInfo wifiInfo;
        int i;
        int i2;
        int i3;
        int i4;
        String subscriberId;
        String string;
        if (z == (Settings.System.getInt(getContext().getContentResolver(), "mptcp_value", 0) != 0)) {
            return;
        }
        if (z && Rune.isDomesticKTTModel() && !this.mIsKTtestOnly) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            this.mAlertDialog = builder;
            if (this.mIsSupport5G) {
                builder.setTitle(R.string.giga_5g_turn_on_title);
            } else {
                builder.setTitle(R.string.giga_lte_popup);
            }
            if (Settings.System.getInt(getContext().getContentResolver(), "smart_bonding", 0) != 0) {
                if (ConnectionsUtils.isWifiEnabled(getActivity())) {
                    if (ConnectionsUtils.isMobileNetworkEnabled(getActivity())) {
                        string = this.mIsSupport5G ? getString(R.string.giga_5g_popup_turn_off_msg, getString(R.string.smart_bonding)) : getString(R.string.giga_lte_samrtbonding_off);
                    } else if (Settings.Global.getInt(getContext().getContentResolver(), "wifi_watchdog_poor_network_test_enabled", 0) != 0) {
                        string = this.mIsSupport5G ? getString(R.string.giga_5g_turn_on_together_msg_when_mobile_data_turn_off, getString(R.string.smart_bonding)) : getString(R.string.giga_lte_samrtbonding_mobile);
                    } else if (this.mIsSupport5G) {
                        string = getString(R.string.giga_5g_turn_on_together_msg_when_mobile_data_turn_off, getString(R.string.smart_bonding)) + getString(R.string.giga_5g_wifi_mobile_data_turn_on_msg, getString(R.string.smart_network));
                    } else {
                        string = getString(R.string.giga_lte_booster_smart_mobile);
                    }
                } else if (Settings.Global.getInt(getContext().getContentResolver(), "wifi_watchdog_poor_network_test_enabled", 0) != 0) {
                    string = this.mIsSupport5G ? getString(R.string.giga_5g_turn_on_together_msg_when_wifi_turn_off, getString(R.string.smart_bonding)) : getString(R.string.giga_lte_samrtbonding_wifi);
                } else if (this.mIsSupport5G) {
                    string = getString(R.string.giga_5g_turn_on_together_msg_when_wifi_turn_off, getString(R.string.smart_bonding)) + getString(R.string.giga_5g_wifi_turn_on_msg, getString(R.string.smart_network));
                } else {
                    string = getString(R.string.giga_lte_booster_smart_wifi);
                }
                final int i5 = 0;
                this.mAlertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i6) {
                        switch (i5) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager = this.this$0.mWifiManager;
                                    if (wifiManager != null && !wifiManager.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 == null || wifiManager2.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                final int i6 = 21;
                this.mAlertDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i6) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager = this.this$0.mWifiManager;
                                    if (wifiManager != null && !wifiManager.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 == null || wifiManager2.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                this.mAlertDialog.setMessage(getChangedText(string));
                this.mAlertDialog.setCancelable(false);
                this.mAlertDialog.show();
                return;
            }
            if (!ConnectionsUtils.isWifiEnabled(getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(getActivity())) {
                String changedText = getChangedText(getString(R.string.giga_lte_popup_msg));
                if (this.mIsSupport5G) {
                    changedText = getString(R.string.giga_5g_mobile_data_wifi_msg) + getString(R.string.giga_5g_wifi_mobile_data_turn_on_msg, getString(R.string.wifi_switch_to_mobile_data_exception_switch_to_mobile));
                }
                this.mAlertDialog.setMessage(changedText);
                final int i7 = 27;
                this.mAlertDialog.setPositiveButton(R.string.smart_bonding_popup_msg_wifi_data_btn, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i7) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager = this.this$0.mWifiManager;
                                    if (wifiManager != null && !wifiManager.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 == null || wifiManager2.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                final int i8 = 28;
                this.mAlertDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i8) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager = this.this$0.mWifiManager;
                                    if (wifiManager != null && !wifiManager.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 == null || wifiManager2.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                this.mAlertDialog.setCancelable(false);
                this.mAlertDialog.show();
                return;
            }
            if (!ConnectionsUtils.isWifiEnabled(getActivity())) {
                String changedText2 = getChangedText(getString(R.string.giga_lte_wifi_msg));
                if (this.mIsSupport5G) {
                    changedText2 = getString(R.string.giga_5g_wifi_msg) + getString(R.string.giga_5g_wifi_turn_on_msg, getString(R.string.wifi_switch_to_mobile_data_exception_switch_to_mobile));
                }
                this.mAlertDialog.setMessage(changedText2);
                final int i9 = 29;
                this.mAlertDialog.setPositiveButton(R.string.smart_bonding_popup_msg_wifi_btn, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i9) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager = this.this$0.mWifiManager;
                                    if (wifiManager != null && !wifiManager.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 == null || wifiManager2.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                final int i10 = 0;
                this.mAlertDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.6
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i11) {
                        switch (i10) {
                            case 0:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 1:
                                GigaLteSettings gigaLteSettings = this.this$0;
                                gigaLteSettings.mIsMobileDataEnabling = true;
                                gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 2:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                GigaLteSettings gigaLteSettings2 = this.this$0;
                                gigaLteSettings2.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings2.mSwitchBar).mSwitch.isChecked());
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                this.mAlertDialog.setCancelable(false);
                this.mAlertDialog.show();
                return;
            }
            if (!ConnectionsUtils.isMobileNetworkEnabled(getActivity())) {
                String changedText3 = getChangedText(getString(R.string.giga_lte_mobile_msg));
                if (this.mIsSupport5G) {
                    changedText3 = getString(R.string.giga_5g_mobile_data_msg) + getString(R.string.giga_5g_mobile_data_turn_on_msg, getString(R.string.wifi_switch_to_mobile_data_exception_switch_to_mobile));
                }
                this.mAlertDialog.setMessage(changedText3);
                final int i11 = 1;
                this.mAlertDialog.setPositiveButton(R.string.smart_bonding_popup_msg_data_btn, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.6
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i112) {
                        switch (i11) {
                            case 0:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 1:
                                GigaLteSettings gigaLteSettings = this.this$0;
                                gigaLteSettings.mIsMobileDataEnabling = true;
                                gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 2:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                GigaLteSettings gigaLteSettings2 = this.this$0;
                                gigaLteSettings2.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings2.mSwitchBar).mSwitch.isChecked());
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                final int i12 = 2;
                this.mAlertDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.6
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i112) {
                        switch (i12) {
                            case 0:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 1:
                                GigaLteSettings gigaLteSettings = this.this$0;
                                gigaLteSettings.mIsMobileDataEnabling = true;
                                gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 2:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                GigaLteSettings gigaLteSettings2 = this.this$0;
                                gigaLteSettings2.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings2.mSwitchBar).mSwitch.isChecked());
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                this.mAlertDialog.setCancelable(false);
                this.mAlertDialog.show();
                return;
            }
            if (Settings.Global.getInt(getContext().getContentResolver(), "wifi_watchdog_poor_network_test_enabled", 0) == 0) {
                String string2 = getString(R.string.giga_lte_switch_to_mobile);
                if (this.mIsSupport5G) {
                    string2 = getString(R.string.giga_5g_popup_turn_on_msg, getString(R.string.wifi_switch_to_mobile_data_exception_switch_to_mobile));
                }
                this.mAlertDialog.setMessage(string2);
                final int i13 = 3;
                this.mAlertDialog.setPositiveButton(R.string.sec_dlg_turn_on, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.6
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i112) {
                        switch (i13) {
                            case 0:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 1:
                                GigaLteSettings gigaLteSettings = this.this$0;
                                gigaLteSettings.mIsMobileDataEnabling = true;
                                gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 2:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                GigaLteSettings gigaLteSettings2 = this.this$0;
                                gigaLteSettings2.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings2.mSwitchBar).mSwitch.isChecked());
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                final int i14 = 1;
                this.mAlertDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i14) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager = this.this$0.mWifiManager;
                                    if (wifiManager != null && !wifiManager.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 == null || wifiManager2.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                this.mAlertDialog.setCancelable(false);
                this.mAlertDialog.show();
                return;
            }
        }
        if (z && ConnectionsUtils.isAisSIMValid(getContext())) {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
            this.mAlertDialog = builder2;
            builder2.setTitle(R.string.ais_mptcp_popup_title);
            if (Settings.System.getInt(getContext().getContentResolver(), "smart_bonding", 0) != 0) {
                this.mAlertDialog.setMessage(!ConnectionsUtils.isWifiEnabled(getActivity()) ? Settings.Global.getInt(getContext().getContentResolver(), "wifi_watchdog_poor_network_test_enabled", 0) != 0 ? getString(R.string.ais_mptcp_smartbonding_wifi, getString(R.string.smart_bonding)) : getString(R.string.ais_mptcp_booster_smart_wifi, getString(R.string.smart_bonding), getString(R.string.wifi_watchdog_connectivity_check)) : !ConnectionsUtils.isMobileNetworkEnabled(getActivity()) ? Settings.Global.getInt(getContext().getContentResolver(), "wifi_watchdog_poor_network_test_enabled", 0) != 0 ? getString(R.string.ais_mptcp_smartbonding_mobile, getString(R.string.smart_bonding)) : getString(R.string.ais_mptcp_booster_smart_mobile, getString(R.string.smart_bonding), getString(R.string.wifi_watchdog_connectivity_check)) : getString(R.string.ais_mptcp_smartbonding_off, getString(R.string.smart_bonding)));
                final int i15 = 2;
                this.mAlertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i15) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager = this.this$0.mWifiManager;
                                    if (wifiManager != null && !wifiManager.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 == null || wifiManager2.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                final int i16 = 3;
                this.mAlertDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i16) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager = this.this$0.mWifiManager;
                                    if (wifiManager != null && !wifiManager.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 == null || wifiManager2.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                this.mAlertDialog.setCancelable(false);
                this.mAlertDialog.show();
                return;
            }
            if (!ConnectionsUtils.isWifiEnabled(getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(getActivity())) {
                this.mAlertDialog.setMessage(getString(R.string.ais_mptcp_wifi_mobile_off_msg, getString(R.string.wifi_watchdog_connectivity_check)));
                final int i17 = 4;
                this.mAlertDialog.setPositiveButton(R.string.smart_bonding_popup_msg_wifi_data_btn, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i17) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager = this.this$0.mWifiManager;
                                    if (wifiManager != null && !wifiManager.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 == null || wifiManager2.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                final int i18 = 5;
                this.mAlertDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i18) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager = this.this$0.mWifiManager;
                                    if (wifiManager != null && !wifiManager.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 == null || wifiManager2.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                this.mAlertDialog.setCancelable(false);
                this.mAlertDialog.show();
                return;
            }
            if (!ConnectionsUtils.isWifiEnabled(getActivity())) {
                this.mAlertDialog.setMessage(getString(R.string.ais_mptcp_wifi_off_msg, getString(R.string.wifi_watchdog_connectivity_check)));
                final int i19 = 6;
                this.mAlertDialog.setPositiveButton(R.string.smart_bonding_popup_msg_wifi_btn, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i19) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager = this.this$0.mWifiManager;
                                    if (wifiManager != null && !wifiManager.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 == null || wifiManager2.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                final int i20 = 7;
                this.mAlertDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i20) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager = this.this$0.mWifiManager;
                                    if (wifiManager != null && !wifiManager.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 == null || wifiManager2.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                this.mAlertDialog.setCancelable(false);
                this.mAlertDialog.show();
                return;
            }
            if (!ConnectionsUtils.isMobileNetworkEnabled(getActivity())) {
                this.mAlertDialog.setMessage(getString(R.string.ais_mptcp_mobile_off_msg, getString(R.string.wifi_watchdog_connectivity_check)));
                final int i21 = 8;
                this.mAlertDialog.setPositiveButton(R.string.smart_bonding_popup_msg_data_btn, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i21) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager = this.this$0.mWifiManager;
                                    if (wifiManager != null && !wifiManager.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 == null || wifiManager2.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                final int i22 = 9;
                this.mAlertDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i22) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager = this.this$0.mWifiManager;
                                    if (wifiManager != null && !wifiManager.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 == null || wifiManager2.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                this.mAlertDialog.setCancelable(false);
                this.mAlertDialog.show();
                return;
            }
        }
        if (z && Rune.isDomesticSKTModel()) {
            AlertDialog.Builder builder3 = new AlertDialog.Builder(getActivity());
            this.mAlertDialog = builder3;
            if (this.mIsSupport5G) {
                builder3.setTitle(R.string.band_lte_title_5G);
            } else {
                builder3.setTitle(R.string.band_lte_title);
            }
            if (!isSimPresent()) {
                this.mAlertDialog.setMessage(R.string.band_lte_nosim_msg);
                final int i23 = 10;
                this.mAlertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i23) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager = this.this$0.mWifiManager;
                                    if (wifiManager != null && !wifiManager.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 == null || wifiManager2.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                this.mAlertDialog.setCancelable(false);
            } else if (!isSimPresent() || (subscriberId = this.mTelephonyManager.getSubscriberId()) == null || !subscriberId.contains("45005")) {
                this.mAlertDialog.setMessage(R.string.band_lte_noskt_sim);
                final int i24 = 11;
                this.mAlertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i24) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager = this.this$0.mWifiManager;
                                    if (wifiManager != null && !wifiManager.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 == null || wifiManager2.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                this.mAlertDialog.setCancelable(false);
            } else if (ConnectionsUtils.isRoaming(getContext()) || ConnectionsUtils.isAirplaneModeEnabled(getContext())) {
                if (this.mIsSupport5G) {
                    this.mAlertDialog.setMessage(R.string.hybrid_wifi_unavailable);
                } else {
                    this.mAlertDialog.setMessage(R.string.band_lte_unavaiable);
                }
                final int i25 = 12;
                this.mAlertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i25) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager = this.this$0.mWifiManager;
                                    if (wifiManager != null && !wifiManager.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 == null || wifiManager2.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                this.mAlertDialog.setCancelable(false);
            } else if (Settings.System.getInt(getContext().getContentResolver(), "smart_bonding", 0) != 0) {
                if (this.mIsSupport5G) {
                    this.mAlertDialog.setMessage(R.string.hybrid_wifi_samrtbonding_off);
                } else {
                    this.mAlertDialog.setMessage(R.string.band_lte_samrtbonding_off);
                }
                final int i26 = 13;
                this.mAlertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i26) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager = this.this$0.mWifiManager;
                                    if (wifiManager != null && !wifiManager.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 == null || wifiManager2.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                final int i27 = 14;
                this.mAlertDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i27) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager = this.this$0.mWifiManager;
                                    if (wifiManager != null && !wifiManager.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 == null || wifiManager2.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                this.mAlertDialog.setCancelable(false);
            } else if (ConnectionsUtils.isWifiEnabled(getActivity()) && ConnectionsUtils.isMobileNetworkEnabled(getActivity())) {
                if (this.mIsSupport5G) {
                    this.mAlertDialog.setMessage(R.string.hybrid_wifi_popup_msg);
                } else {
                    this.mAlertDialog.setMessage(R.string.band_lte_popup_msg);
                }
                final int i28 = 17;
                this.mAlertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i28) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager = this.this$0.mWifiManager;
                                    if (wifiManager != null && !wifiManager.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 == null || wifiManager2.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                final int i29 = 18;
                this.mAlertDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i29) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager = this.this$0.mWifiManager;
                                    if (wifiManager != null && !wifiManager.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 == null || wifiManager2.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                this.mAlertDialog.setCancelable(false);
            } else {
                if (this.mIsSupport5G) {
                    this.mAlertDialog.setMessage(R.string.hybrid_wifi_msg);
                } else {
                    this.mAlertDialog.setMessage(R.string.band_lte_wifi_msg);
                }
                final int i30 = 15;
                this.mAlertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i30) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager = this.this$0.mWifiManager;
                                    if (wifiManager != null && !wifiManager.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 == null || wifiManager2.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                final int i31 = 16;
                this.mAlertDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i31) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager = this.this$0.mWifiManager;
                                    if (wifiManager != null && !wifiManager.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 == null || wifiManager2.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                this.mAlertDialog.setCancelable(false);
            }
            this.mAlertDialog.show();
            return;
        }
        if (z && (ConnectionsUtils.isAveaSIMValid(getContext()) || ConnectionsUtils.isVodafoneSIMValid(getContext()))) {
            WifiManager wifiManager = this.mWifiManager;
            if (wifiManager != null) {
                wifiInfo = wifiManager.getConnectionInfo();
                if (wifiInfo != null) {
                    Log.d("GigaLteSettings", "Connected Wi-Fi SSID = " + wifiInfo.getSSID());
                } else {
                    Log.d("GigaLteSettings", "Connected Wi-Fi SSID = null");
                }
            } else {
                wifiInfo = null;
            }
            if (ConnectionsUtils.isAveaSIMValid(getContext())) {
                i = R.string.avea_giga_title;
                i2 = R.string.avea_giga_popup_msg;
                i3 = R.string.avea_giga_wifi_msg;
                i4 = R.string.avea_giga_mobile_msg;
            } else {
                i = R.string.vodafone_giga_title;
                i2 = R.string.vodafone_giga_popup_msg;
                i3 = R.string.vodafone_giga_wifi_msg;
                i4 = R.string.vodafone_giga_mobile_msg;
            }
            AlertDialog.Builder builder4 = new AlertDialog.Builder(getActivity());
            this.mAlertDialog = builder4;
            builder4.setTitle(i);
            if (!ConnectionsUtils.isWifiEnabled(getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(getActivity())) {
                this.mAlertDialog.setMessage(i2);
                final int i32 = 19;
                this.mAlertDialog.setPositiveButton(R.string.switch_on_text, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i32) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 != null && !wifiManager2.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager22 = this.this$0.mWifiManager;
                                    if (wifiManager22 == null || wifiManager22.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                final int i33 = 20;
                this.mAlertDialog.setNegativeButton(R.string.switch_off_text, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i33) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 != null && !wifiManager2.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager22 = this.this$0.mWifiManager;
                                    if (wifiManager22 == null || wifiManager22.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                this.mAlertDialog.setCancelable(false);
                this.mAlertDialog.show();
                return;
            }
            if (!ConnectionsUtils.isWifiEnabled(getActivity())) {
                this.mAlertDialog.setMessage(i3);
                final int i34 = 22;
                this.mAlertDialog.setPositiveButton(R.string.switch_on_text, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i34) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 != null && !wifiManager2.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager22 = this.this$0.mWifiManager;
                                    if (wifiManager22 == null || wifiManager22.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                final int i35 = 23;
                this.mAlertDialog.setNegativeButton(R.string.switch_off_text, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i35) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 != null && !wifiManager2.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager22 = this.this$0.mWifiManager;
                                    if (wifiManager22 == null || wifiManager22.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                this.mAlertDialog.setCancelable(false);
                this.mAlertDialog.show();
                return;
            }
            if (!ConnectionsUtils.isMobileNetworkEnabled(getActivity())) {
                this.mAlertDialog.setMessage(i4);
                final int i36 = 24;
                this.mAlertDialog.setPositiveButton(R.string.switch_on_text, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i36) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 != null && !wifiManager2.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager22 = this.this$0.mWifiManager;
                                    if (wifiManager22 == null || wifiManager22.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                final int i37 = 25;
                this.mAlertDialog.setNegativeButton(R.string.switch_off_text, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i37) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 != null && !wifiManager2.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager22 = this.this$0.mWifiManager;
                                    if (wifiManager22 == null || wifiManager22.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                this.mAlertDialog.setCancelable(false);
                this.mAlertDialog.show();
                return;
            }
            if (ConnectionsUtils.isVodafoneSIMValid(getContext()) && ConnectionsUtils.isWifiEnabled(getActivity()) && wifiInfo != null && !wifiInfo.getSSID().equals("\"Supernet-SIM\"")) {
                this.mAlertDialog.setMessage(R.string.vodafone_giga_supernet_msg);
                final int i38 = 26;
                this.mAlertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(this) { // from class: com.samsung.android.settings.connection.GigaLteSettings.1
                    public final /* synthetic */ GigaLteSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i62) {
                        switch (i38) {
                            case 0:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings = this.this$0;
                                    gigaLteSettings.mIsNetworkEnabling = true;
                                    gigaLteSettings.mIsMobileDataEnabling = true;
                                    gigaLteSettings.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager2 = this.this$0.mWifiManager;
                                    if (wifiManager2 != null && !wifiManager2.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings2 = this.this$0;
                                        gigaLteSettings2.mIsWiFiEnabling = true;
                                        gigaLteSettings2.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager22 = this.this$0.mWifiManager;
                                    if (wifiManager22 == null || wifiManager22.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings3 = this.this$0;
                                        gigaLteSettings3.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings3.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings4 = this.this$0;
                                        gigaLteSettings4.mIsWiFiEnabling = true;
                                        gigaLteSettings4.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings5 = this.this$0;
                                    gigaLteSettings5.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings5.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings6 = this.this$0;
                                    gigaLteSettings6.mIsMobileDataEnabling = true;
                                    gigaLteSettings6.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 1:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 2:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) && !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings7 = this.this$0;
                                    gigaLteSettings7.mIsNetworkEnabling = true;
                                    gigaLteSettings7.mIsMobileDataEnabling = true;
                                    gigaLteSettings7.mTelephonyManager.setDataEnabled(true);
                                    WifiManager wifiManager3 = this.this$0.mWifiManager;
                                    if (wifiManager3 != null && !wifiManager3.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings8 = this.this$0;
                                        gigaLteSettings8.mIsWiFiEnabling = true;
                                        gigaLteSettings8.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity())) {
                                    WifiManager wifiManager4 = this.this$0.mWifiManager;
                                    if (wifiManager4 == null || wifiManager4.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                        GigaLteSettings gigaLteSettings9 = this.this$0;
                                        gigaLteSettings9.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings9.mSwitchBar).mSwitch.isChecked());
                                    } else {
                                        GigaLteSettings gigaLteSettings10 = this.this$0;
                                        gigaLteSettings10.mIsWiFiEnabling = true;
                                        gigaLteSettings10.mWifiManager.setWifiEnabled(true);
                                    }
                                } else if (ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    GigaLteSettings gigaLteSettings11 = this.this$0;
                                    gigaLteSettings11.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings11.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings12 = this.this$0;
                                    gigaLteSettings12.mIsMobileDataEnabling = true;
                                    gigaLteSettings12.mTelephonyManager.setDataEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 3:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 4:
                                GigaLteSettings gigaLteSettings13 = this.this$0;
                                gigaLteSettings13.mIsNetworkEnabling = true;
                                gigaLteSettings13.mIsMobileDataEnabling = true;
                                gigaLteSettings13.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager5 = this.this$0.mWifiManager;
                                if (wifiManager5 != null && !wifiManager5.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings14 = this.this$0;
                                    gigaLteSettings14.mIsWiFiEnabling = true;
                                    gigaLteSettings14.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 5:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 6:
                                WifiManager wifiManager6 = this.this$0.mWifiManager;
                                if (wifiManager6 == null || wifiManager6.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings15 = this.this$0;
                                    gigaLteSettings15.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings15.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings16 = this.this$0;
                                    gigaLteSettings16.mIsWiFiEnabling = true;
                                    gigaLteSettings16.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 7:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 8:
                                GigaLteSettings gigaLteSettings17 = this.this$0;
                                gigaLteSettings17.mIsMobileDataEnabling = true;
                                gigaLteSettings17.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 9:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 10:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 11:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 12:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 13:
                                Settings.System.putInt(this.this$0.getContext().getContentResolver(), "smart_bonding", 0);
                                if (!ConnectionsUtils.isWifiEnabled(this.this$0.getActivity()) || !ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.onCheckedChanged(null, true);
                                    break;
                                } else {
                                    this.this$0.mSwitchBar.setEnabled(false);
                                    GigaLteSettings gigaLteSettings18 = this.this$0;
                                    gigaLteSettings18.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings18.mSwitchBar).mSwitch.isChecked());
                                    this.this$0.showProgressDialog$1();
                                    break;
                                }
                                break;
                            case 14:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 15:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                if (!ConnectionsUtils.isMobileNetworkEnabled(this.this$0.getActivity())) {
                                    this.this$0.mTelephonyManager.setDataEnabled(true);
                                }
                                WifiManager wifiManager7 = this.this$0.mWifiManager;
                                if (wifiManager7 != null && !wifiManager7.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    this.this$0.mWifiManager.setWifiEnabled(true);
                                }
                                GigaLteSettings gigaLteSettings19 = this.this$0;
                                gigaLteSettings19.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings19.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 16:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 17:
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                GigaLteSettings gigaLteSettings20 = this.this$0;
                                gigaLteSettings20.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings20.mSwitchBar).mSwitch.isChecked());
                                break;
                            case 18:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 19:
                                GigaLteSettings gigaLteSettings21 = this.this$0;
                                gigaLteSettings21.mIsNetworkEnabling = true;
                                gigaLteSettings21.mIsMobileDataEnabling = true;
                                gigaLteSettings21.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager8 = this.this$0.mWifiManager;
                                if (wifiManager8 != null && !wifiManager8.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings22 = this.this$0;
                                    gigaLteSettings22.mIsWiFiEnabling = true;
                                    gigaLteSettings22.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 20:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 21:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 22:
                                WifiManager wifiManager9 = this.this$0.mWifiManager;
                                if (wifiManager9 == null || wifiManager9.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings23 = this.this$0;
                                    gigaLteSettings23.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings23.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings24 = this.this$0;
                                    gigaLteSettings24.mIsWiFiEnabling = true;
                                    gigaLteSettings24.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 23:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 24:
                                GigaLteSettings gigaLteSettings25 = this.this$0;
                                gigaLteSettings25.mIsMobileDataEnabling = true;
                                gigaLteSettings25.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                            case 25:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 26:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            case 27:
                                GigaLteSettings gigaLteSettings26 = this.this$0;
                                gigaLteSettings26.mIsNetworkEnabling = true;
                                gigaLteSettings26.mIsMobileDataEnabling = true;
                                gigaLteSettings26.mTelephonyManager.setDataEnabled(true);
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                WifiManager wifiManager10 = this.this$0.mWifiManager;
                                if (wifiManager10 != null && !wifiManager10.isWifiEnabled() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings27 = this.this$0;
                                    gigaLteSettings27.mIsWiFiEnabling = true;
                                    gigaLteSettings27.mWifiManager.setWifiEnabled(true);
                                    break;
                                }
                                break;
                            case 28:
                                this.this$0.mSwitchBar.setChecked(false);
                                break;
                            default:
                                WifiManager wifiManager11 = this.this$0.mWifiManager;
                                if (wifiManager11 == null || wifiManager11.isWifiEnabled() || ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                    GigaLteSettings gigaLteSettings28 = this.this$0;
                                    gigaLteSettings28.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings28.mSwitchBar).mSwitch.isChecked());
                                } else {
                                    GigaLteSettings gigaLteSettings29 = this.this$0;
                                    gigaLteSettings29.mIsWiFiEnabling = true;
                                    gigaLteSettings29.mWifiManager.setWifiEnabled(true);
                                }
                                this.this$0.mSwitchBar.setEnabled(false);
                                this.this$0.showProgressDialog$1();
                                break;
                        }
                    }
                });
                this.mAlertDialog.setCancelable(false);
                this.mAlertDialog.show();
                return;
            }
        }
        this.mSwitchBar.setEnabled(false);
        showProgressDialog$1();
        sendMptcpStartBroadCast(z);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getContext();
        this.mIsSupport5G = ConnectionsUtils.isSupport5GConcept();
        this.mTelephonyManager = (TelephonyManager) getContext().getSystemService("phone");
        this.mWifiManager = (WifiManager) getContext().getSystemService(ImsProfile.PDN_WIFI);
        this.mIsKTtestOnly = SystemProperties.get("persist.mptcp.limitation", "false").equals("true");
        Log.d("GigaLteSettings", "Is Test mode for KTT = " + this.mIsKTtestOnly);
        Log.d("GigaLteSettings", "gigaltesettings create");
        if (Rune.isDomesticSKTModel() || ConnectionsUtils.isAveaSIMValid(getContext()) || ConnectionsUtils.isVodafoneSIMValid(getContext()) || this.mIsKTtestOnly || ConnectionsUtils.isAisSIMValid(getContext()) || ConnectionsUtils.isFastWebSIMValid(getContext()) || !Rune.isDomesticKTTModel()) {
            return;
        }
        if (!isSimPresent()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            this.mAlertDialog = builder;
            if (this.mIsSupport5G) {
                builder.setTitle(R.string.giga_5g_turn_on_title);
            } else {
                builder.setTitle(R.string.giga_lte_popup);
            }
            this.mAlertDialog.setMessage(R.string.giga_lte_nosim_msg);
            this.mAlertDialog.setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null);
            this.mAlertDialog.show();
            return;
        }
        if (isSimValid()) {
            return;
        }
        AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
        this.mAlertDialog = builder2;
        if (this.mIsSupport5G) {
            builder2.setTitle(R.string.giga_5g_turn_on_title);
            this.mAlertDialog.setMessage(R.string.giga_5g_nokt_sim);
        } else {
            builder2.setTitle(R.string.giga_lte_popup);
            this.mAlertDialog.setMessage(R.string.giga_lte_nokt_sim);
        }
        this.mAlertDialog.setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null);
        this.mAlertDialog.show();
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        String changedText;
        View inflate = layoutInflater.inflate(R.layout.sec_airplane_mode_settings, (ViewGroup) null);
        TextView textView = (TextView) inflate.findViewById(R.id.airplane_mode_desc_text_view);
        if (Rune.isDomesticKTTModel() && this.mIsSupport5G) {
            changedText = getString(R.string.giga_5g_body) + "\n" + getString(R.string.giga_5g_body_1) + "\n" + getString(R.string.giga_5g_body_2);
        } else {
            changedText = getChangedText(getString(Rune.isDomesticSKTModel() ? this.mIsSupport5G ? R.string.band_lte_body_5g : R.string.band_lte_body : ConnectionsUtils.isAveaSIMValid(getContext()) ? R.string.avea_giga_body : ConnectionsUtils.isVodafoneSIMValid(getContext()) ? R.string.vodafone_giga_body : ConnectionsUtils.isAisSIMValid(getContext()) ? R.string.ais_mptcp_body : ConnectionsUtils.isFastWebSIMValid(getContext()) ? R.string.fastweb_mptcp_body : R.string.giga_lte_body));
        }
        textView.setText(changedText);
        return inflate;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        this.mSwitchBar.removeOnSwitchChangeListener(this);
        this.mSwitchBar.hide();
        ProgressDialog progressDialog = this.progressDialog;
        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }
        this.mHoldingHandler.removeCallbacks(this.mRemoveProgress);
        this.progressDialog.dismiss();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        if (this.mMptcpStateReceiver != null) {
            getActivity().unregisterReceiver(this.mMptcpStateReceiver);
            this.mMptcpStateReceiver = null;
        }
        if (this.mHotSwapReceiver != null) {
            getActivity().unregisterReceiver(this.mHotSwapReceiver);
            this.mHotSwapReceiver = null;
        }
        getContext().getContentResolver().unregisterContentObserver(this.mAirplaneModeObserver);
        getContext().getContentResolver().unregisterContentObserver(this.mMobileDataObserver);
    }

    /* JADX WARN: Type inference failed for: r2v7, types: [com.samsung.android.settings.connection.GigaLteSettings$37] */
    /* JADX WARN: Type inference failed for: r2v8, types: [com.samsung.android.settings.connection.GigaLteSettings$37] */
    @Override // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Log.d("GigaLteSettings", "gigaltesettings resume");
        if (this.mMptcpStateReceiver == null) {
            this.mMptcpStateReceiver = new HotSwapReceiver(this, 1);
            getActivity().registerReceiver(this.mMptcpStateReceiver, ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0.m("com.samsung.android.mptcp.MPTCP_STATE", "android.net.wifi.WIFI_STATE_CHANGED"), 2);
        }
        if (this.mHotSwapReceiver == null) {
            this.mHotSwapReceiver = new HotSwapReceiver(this, 0);
            getActivity().registerReceiver(this.mHotSwapReceiver, new IntentFilter("com.samsung.intent.action.SIMHOTSWAP"), 4);
        }
        ContentResolver contentResolver = getContext().getContentResolver();
        Uri uriFor = Settings.System.getUriFor("airplane_mode_on");
        if (this.mAirplaneModeObserver == null) {
            final int i = 0;
            this.mAirplaneModeObserver = new ContentObserver(this, new Handler()) { // from class: com.samsung.android.settings.connection.GigaLteSettings.37
                public final /* synthetic */ GigaLteSettings this$0;

                {
                    this.this$0 = this;
                }

                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    switch (i) {
                        case 0:
                            GigaLteSettings gigaLteSettings = this.this$0;
                            BaseSearchIndexProvider baseSearchIndexProvider = GigaLteSettings.SEARCH_INDEX_DATA_PROVIDER;
                            if (!gigaLteSettings.isRoaming() && this.this$0.isSimValid() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                this.this$0.mSwitchBar.setEnabled(!ConnectionsUtils.isAirplaneModeEnabled(r1.getContext()));
                                break;
                            }
                            break;
                        default:
                            GigaLteSettings gigaLteSettings2 = this.this$0;
                            if (!gigaLteSettings2.mIsNetworkEnabling || !gigaLteSettings2.mIsMobileDataEnabling) {
                                if (gigaLteSettings2.mIsMobileDataEnabling) {
                                    gigaLteSettings2.mIsMobileDataEnabling = false;
                                    gigaLteSettings2.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings2.mSwitchBar).mSwitch.isChecked());
                                    break;
                                }
                            } else {
                                gigaLteSettings2.mIsMobileDataEnabling = false;
                                if (!gigaLteSettings2.mIsWiFiEnabling) {
                                    gigaLteSettings2.mIsNetworkEnabling = false;
                                    gigaLteSettings2.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings2.mSwitchBar).mSwitch.isChecked());
                                    break;
                                }
                            }
                            break;
                    }
                }
            };
        }
        contentResolver.registerContentObserver(uriFor, true, this.mAirplaneModeObserver);
        ContentResolver contentResolver2 = getContext().getContentResolver();
        Uri uriFor2 = Settings.Global.getUriFor("mobile_data");
        if (this.mMobileDataObserver == null) {
            final int i2 = 1;
            this.mMobileDataObserver = new ContentObserver(this, new Handler()) { // from class: com.samsung.android.settings.connection.GigaLteSettings.37
                public final /* synthetic */ GigaLteSettings this$0;

                {
                    this.this$0 = this;
                }

                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    switch (i2) {
                        case 0:
                            GigaLteSettings gigaLteSettings = this.this$0;
                            BaseSearchIndexProvider baseSearchIndexProvider = GigaLteSettings.SEARCH_INDEX_DATA_PROVIDER;
                            if (!gigaLteSettings.isRoaming() && this.this$0.isSimValid() && !ConnectionsUtils.isTetheringOn(this.this$0.getContext())) {
                                this.this$0.mSwitchBar.setEnabled(!ConnectionsUtils.isAirplaneModeEnabled(r1.getContext()));
                                break;
                            }
                            break;
                        default:
                            GigaLteSettings gigaLteSettings2 = this.this$0;
                            if (!gigaLteSettings2.mIsNetworkEnabling || !gigaLteSettings2.mIsMobileDataEnabling) {
                                if (gigaLteSettings2.mIsMobileDataEnabling) {
                                    gigaLteSettings2.mIsMobileDataEnabling = false;
                                    gigaLteSettings2.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings2.mSwitchBar).mSwitch.isChecked());
                                    break;
                                }
                            } else {
                                gigaLteSettings2.mIsMobileDataEnabling = false;
                                if (!gigaLteSettings2.mIsWiFiEnabling) {
                                    gigaLteSettings2.mIsNetworkEnabling = false;
                                    gigaLteSettings2.sendMptcpStartBroadCast(((SeslSwitchBar) gigaLteSettings2.mSwitchBar).mSwitch.isChecked());
                                    break;
                                }
                            }
                            break;
                    }
                }
            };
        }
        contentResolver2.registerContentObserver(uriFor2, true, this.mMobileDataObserver);
        if (!Rune.isDomesticSKTModel()) {
            checkSwitchDisableStatus();
        }
        boolean z = Settings.System.getInt(getContext().getContentResolver(), "mptcp_value", 0) != 0;
        if (this.mSwitchBar.isEnabled() && z != ((SeslSwitchBar) this.mSwitchBar).mSwitch.isChecked()) {
            this.mSwitchBar.setChecked(z);
        }
        ProgressDialog progressDialog = this.progressDialog;
        if (progressDialog != null && progressDialog.isShowing() && z == ((SeslSwitchBar) this.mSwitchBar).mSwitch.isChecked()) {
            this.mSwitchBar.setEnabled(true);
            this.mHoldingHandler.removeCallbacks(this.mRemoveProgress);
            this.progressDialog.dismiss();
        }
    }

    public final void sendMptcpStartBroadCast(boolean z) {
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.mptcp.MPTCP_START");
        intent.putExtra("mptcp_start", z ? 1 : 0);
        Log.d("GigaLteSettings", "Sending MPTCP Start Stop broadcast to MPTCP Service: " + intent);
        getContext().sendBroadcast(intent);
        if (Rune.isDomesticSKTModel() || !z) {
            return;
        }
        Settings.Global.putInt(getContext().getContentResolver(), "wifi_watchdog_poor_network_test_enabled", 1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.samsung.android.settings.connection.GigaLteSettings$35, java.lang.Runnable] */
    public final void showProgressDialog$1() {
        this.progressDialog = null;
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        this.progressDialog = progressDialog;
        progressDialog.setIndeterminate(true);
        this.progressDialog.setCancelable(false);
        this.progressDialog.getWindow().setType(VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_REG_403);
        this.progressDialog.setMessage(getText(R.string.mptcp_processing_dialog_message));
        this.progressDialog.show();
        Handler handler = new Handler();
        this.mHoldingHandler = handler;
        ?? r1 = new Runnable() { // from class: com.samsung.android.settings.connection.GigaLteSettings.35
            @Override // java.lang.Runnable
            public final void run() {
                ProgressDialog progressDialog2 = GigaLteSettings.this.progressDialog;
                if (progressDialog2 == null || !progressDialog2.isShowing()) {
                    return;
                }
                GigaLteSettings.this.mSwitchBar.setChecked(Settings.System.getInt(GigaLteSettings.this.getContext().getContentResolver(), "mptcp_value", 0) != 0);
                GigaLteSettings.this.mSwitchBar.setEnabled(true);
                GigaLteSettings.this.progressDialog.dismiss();
            }
        };
        this.mRemoveProgress = r1;
        handler.postDelayed(r1, 15000L);
    }
}

package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.room.util.DBUtil;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.datausage.DataUsageSummaryPreferenceController;
import com.android.settings.network.MobileNetworkListFragment;
import com.android.settings.network.MobileNetworkRepository;
import com.android.settings.network.SubscriptionUtil;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.mobile.dataservice.MobileNetworkInfoDao_Impl;
import com.android.settingslib.mobile.dataservice.MobileNetworkInfoDao_Impl$$ExternalSyntheticLambda0;
import com.android.settingslib.mobile.dataservice.MobileNetworkInfoEntity;
import com.android.settingslib.mobile.dataservice.SubscriptionInfoDao_Impl;
import com.android.settingslib.mobile.dataservice.SubscriptionInfoDao_Impl$$ExternalSyntheticLambda1;
import com.android.settingslib.mobile.dataservice.SubscriptionInfoEntity;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.settings.Rune;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class MobileNetworkSettings extends AbstractMobileNetworkSettings
        implements MobileNetworkRepository.MobileNetworkCallback {
    static final String KEY_CLICKED_PREF = "key_clicked_pref";
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.mobile_network_settings);
    public String mClickedPrefKey;
    public final ExecutorService mExecutor;
    public MobileNetworkInfoEntity mMobileNetworkInfoEntity;
    public MobileNetworkRepository mMobileNetworkRepository;
    public int mSubId;
    public SubscriptionInfoEntity mSubscriptionInfoEntity;
    public TelephonyManager mTelephonyManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.network.telephony.MobileNetworkSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            if (Rune.SUPPORT_SMARTMANAGER_CN
                    || Settings.Global.getInt(context.getContentResolver(), "airplane_mode_on", 0)
                            != 0) {
                return false;
            }
            Pattern pattern = SubscriptionUtil.REGEX_DISPLAY_NAME_SUFFIX_PATTERN;
            return context.getResources().getBoolean(R.bool.config_show_sim_info)
                    && ((UserManager) context.getSystemService(UserManager.class)).isAdminUser();
        }
    }

    public MobileNetworkSettings() {
        super("no_config_mobile_networks");
        this.mHiddenControllerList = new ArrayList();
        this.mExecutor = Executors.newSingleThreadExecutor();
        this.mSubId = -1;
        new ArrayList();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        if (!SubscriptionUtil.isSimHardwareVisible(context)) {
            finish();
            return Arrays.asList(new AbstractPreferenceController[0]);
        }
        if (getArguments() == null) {
            Intent intent = getIntent();
            if (intent != null) {
                int[] activeSubscriptionIdList =
                        MobileNetworkUtils.getActiveSubscriptionIdList(context);
                this.mSubId =
                        intent.getIntExtra(
                                "android.provider.extra.SUB_ID",
                                activeSubscriptionIdList.length >= 1
                                        ? activeSubscriptionIdList[0]
                                        : -1);
                Preference$$ExternalSyntheticOutline0.m(
                        new StringBuilder("display subId from intent: "),
                        this.mSubId,
                        "NetworkSettings");
            } else {
                RecyclerView$$ExternalSyntheticOutline0.m(
                        new StringBuilder("intent is null, can not get subId "),
                        this.mSubId,
                        " from intent.",
                        "NetworkSettings");
            }
        } else {
            Bundle arguments = getArguments();
            int[] activeSubscriptionIdList2 =
                    MobileNetworkUtils.getActiveSubscriptionIdList(context);
            this.mSubId =
                    arguments.getInt(
                            "android.provider.extra.SUB_ID",
                            activeSubscriptionIdList2.length >= 1
                                    ? activeSubscriptionIdList2[0]
                                    : -1);
            Preference$$ExternalSyntheticOutline0.m(
                    new StringBuilder("display subId from getArguments(): "),
                    this.mSubId,
                    "NetworkSettings");
        }
        this.mMobileNetworkRepository = MobileNetworkRepository.getInstance(context);
        this.mExecutor.execute(
                new Runnable() { // from class:
                                 // com.android.settings.network.telephony.MobileNetworkSettings$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MobileNetworkSettings mobileNetworkSettings = MobileNetworkSettings.this;
                        MobileNetworkRepository mobileNetworkRepository =
                                mobileNetworkSettings.mMobileNetworkRepository;
                        String valueOf = String.valueOf(mobileNetworkSettings.mSubId);
                        SubscriptionInfoDao_Impl subscriptionInfoDao_Impl =
                                mobileNetworkRepository.mSubscriptionInfoDao;
                        subscriptionInfoDao_Impl.getClass();
                        mobileNetworkSettings.mSubscriptionInfoEntity =
                                (SubscriptionInfoEntity)
                                        DBUtil.performBlocking(
                                                subscriptionInfoDao_Impl.__db,
                                                true,
                                                false,
                                                new SubscriptionInfoDao_Impl$$ExternalSyntheticLambda1(
                                                        valueOf, 0));
                        MobileNetworkRepository mobileNetworkRepository2 =
                                mobileNetworkSettings.mMobileNetworkRepository;
                        String valueOf2 = String.valueOf(mobileNetworkSettings.mSubId);
                        MobileNetworkInfoDao_Impl mobileNetworkInfoDao_Impl =
                                mobileNetworkRepository2.mMobileNetworkInfoDao;
                        mobileNetworkInfoDao_Impl.getClass();
                        mobileNetworkSettings.mMobileNetworkInfoEntity =
                                (MobileNetworkInfoEntity)
                                        DBUtil.performBlocking(
                                                mobileNetworkInfoDao_Impl.__db,
                                                true,
                                                false,
                                                new MobileNetworkInfoDao_Impl$$ExternalSyntheticLambda0(
                                                        valueOf2, 0));
                    }
                });
        MobileNetworkEidPreferenceController mobileNetworkEidPreferenceController =
                new MobileNetworkEidPreferenceController(context, "network_mode_eid_info");
        mobileNetworkEidPreferenceController.init(this, this.mSubId);
        return Arrays.asList(
                new DataUsageSummaryPreferenceController(context, this.mSubId),
                new RoamingPreferenceController(
                        context, "button_roaming_key", getSettingsLifecycle(), this, this.mSubId),
                new CallsDefaultSubscriptionController(
                        context, "calls_preference", getSettingsLifecycle(), this),
                new SmsDefaultSubscriptionController(
                        context, "sms_preference", getSettingsLifecycle(), this),
                new MobileDataPreferenceController(
                        context, "mobile_data_enable", getSettingsLifecycle(), this, this.mSubId),
                new ConvertToEsimPreferenceController(
                        context, "convert_to_esim", getSettingsLifecycle(), this, this.mSubId),
                mobileNetworkEidPreferenceController);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "NetworkSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1571;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.mobile_network_settings;
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        Preference findPreference;
        FragmentActivity activity;
        if (i == 17) {
            if (i2 == 0
                    || (findPreference = getPreferenceScreen().findPreference(this.mClickedPrefKey))
                            == null) {
                return;
            }
            findPreference.performClick();
            return;
        }
        if (i != 18 || i2 == 0 || (activity = getActivity()) == null || activity.isFinishing()) {
            return;
        }
        activity.finish();
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0058, code lost:

       if (android.text.TextUtils.equals(r0.getAction(), "android.telephony.ims.action.SHOW_CAPABILITY_DISCOVERY_OPT_IN") == false) goto L17;
    */
    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onAttach(android.content.Context r8) {
        /*
            Method dump skipped, instructions count: 910
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.telephony.MobileNetworkSettings.onAttach(android.content.Context):void");
    }

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public final void onAvailableSubInfoChanged(List list) {
        int i = 0;
        SubscriptionInfoEntity[] subscriptionInfoEntityArr =
                (SubscriptionInfoEntity[]) list.toArray(new SubscriptionInfoEntity[0]);
        this.mSubscriptionInfoEntity = null;
        int length = subscriptionInfoEntityArr.length;
        while (true) {
            if (i >= length) {
                break;
            }
            SubscriptionInfoEntity subscriptionInfoEntity = subscriptionInfoEntityArr[i];
            int parseInt = Integer.parseInt(subscriptionInfoEntity.subId);
            int i2 = this.mSubId;
            if (parseInt == i2) {
                this.mSubscriptionInfoEntity = subscriptionInfoEntity;
                Preference$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Set subInfo for subId "),
                        this.mSubId,
                        "NetworkSettings");
                break;
            } else {
                if (i2 == -1 && subscriptionInfoEntity.isDefaultSubscriptionSelection) {
                    this.mSubscriptionInfoEntity = subscriptionInfoEntity;
                    Log.d("NetworkSettings", "Set subInfo to default subInfo.");
                }
                i++;
            }
        }
        if (this.mSubscriptionInfoEntity == null && getActivity() != null) {
            finishFragment();
            return;
        }
        final SubscriptionInfoEntity subscriptionInfoEntity2 = this.mSubscriptionInfoEntity;
        if (subscriptionInfoEntity2 == null) {
            return;
        }
        ThreadUtils.postOnMainThread(
                new Runnable() { // from class:
                                 // com.android.settings.network.telephony.MobileNetworkSettings$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        MobileNetworkSettings mobileNetworkSettings = MobileNetworkSettings.this;
                        SubscriptionInfoEntity subscriptionInfoEntity3 = subscriptionInfoEntity2;
                        BaseSearchIndexProvider baseSearchIndexProvider =
                                MobileNetworkSettings.SEARCH_INDEX_DATA_PROVIDER;
                        FragmentActivity activity = mobileNetworkSettings.getActivity();
                        if (activity instanceof SettingsActivity) {
                            SettingsActivity settingsActivity = (SettingsActivity) activity;
                            if (!settingsActivity.isFinishing()) {
                                settingsActivity.setTitle(subscriptionInfoEntity3.uniqueName);
                            }
                        }
                        mobileNetworkSettings.redrawPreferenceControllers();
                    }
                });
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Log.i("NetworkSettings", "onCreate:+");
        TelephonyStatusControlSession telephonyStatusControlSession =
                new TelephonyStatusControlSession(getPreferenceControllersAsList(), getLifecycle());
        super.onCreate(bundle);
        Context context = getContext();
        this.mTelephonyManager =
                ((TelephonyManager) context.getSystemService(TelephonyManager.class))
                        .createForSubscriptionId(this.mSubId);
        telephonyStatusControlSession.close();
        onRestoreInstance(bundle);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if (this.mSubId != -1) {
            MenuItem add = menu.add(0, R.id.edit_sim_name, 0, R.string.mobile_network_sim_name);
            add.setIcon(android.R.drawable.ic_popup_sync_4);
            add.setShowAsAction(2);
        }
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (this.mSubId == -1 || menuItem.getItemId() != R.id.edit_sim_name) {
            return super.onOptionsItemSelected(menuItem);
        }
        int i = this.mSubId;
        Bundle bundle = new Bundle(1);
        bundle.putInt("subscription_id", i);
        RenameMobileNetworkDialogFragment renameMobileNetworkDialogFragment =
                new RenameMobileNetworkDialogFragment();
        renameMobileNetworkDialogFragment.setArguments(bundle);
        renameMobileNetworkDialogFragment.show(getFragmentManager(), "RenameMobileNetwork");
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        this.mMobileNetworkRepository.removeRegister(this);
        super.onPause();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (super.onPreferenceTreeClick(preference)) {
            return true;
        }
        String key = preference.getKey();
        if (!TextUtils.equals(key, "cdma_system_select_key")
                && !TextUtils.equals(key, "cdma_subscription_key")) {
            return false;
        }
        if (this.mTelephonyManager.getEmergencyCallbackMode()) {
            startActivityForResult(
                    new Intent("android.telephony.action.SHOW_NOTICE_ECM_BLOCK_OTHERS", (Uri) null)
                            .setPackage("com.android.phone"),
                    17);
            this.mClickedPrefKey = key;
        }
        return true;
    }

    public void onRestoreInstance(Bundle bundle) {
        if (bundle != null) {
            this.mClickedPrefKey = bundle.getString(KEY_CLICKED_PREF);
        }
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mMobileNetworkRepository.addRegister(this, this, this.mSubId);
        this.mMobileNetworkRepository.updateEntity();
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("onResume() subId="), this.mSubId, "NetworkSettings");
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString(KEY_CLICKED_PREF, this.mClickedPrefKey);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        MobileNetworkListFragment.Companion.SearchIndexProvider searchIndexProvider =
                MobileNetworkListFragment.SEARCH_INDEX_DATA_PROVIDER;
        MobileNetworkListFragment.Companion.collectAirplaneModeAndFinishIfOn(this);
    }
}

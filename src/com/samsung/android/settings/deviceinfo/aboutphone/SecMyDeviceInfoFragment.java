package com.samsung.android.settings.deviceinfo.aboutphone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.SearchIndexableData;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.SettingsPreferenceFragmentLinkData;
import com.samsung.android.settings.deviceinfo.aboutphone.deviceimage.DeviceImageFileUtils;
import com.samsung.android.settings.deviceinfo.aboutphone.deviceimage.DeviceImageManager;
import com.samsung.android.settings.dynamicmenu.SecDynamicFragment;
import com.samsung.android.settings.goodsettings.GoodSettingsProvider;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.softwareupdate.SoftwareUpdateLinkData;
import com.samsung.android.settings.widget.SecRelativeLinkView;
import com.samsung.scpm.odm.dos.common.LOG;
import com.samsung.scpm.odm.dos.common.ScpmDataSet;
import com.samsung.scpm.odm.dos.configuration.ConfigurationDataSet;
import com.samsung.scpm.odm.dos.product.ProductDataSet;
import com.samsung.scpm.odm.dos.product.ScpmProduct;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecMyDeviceInfoFragment extends SecDynamicFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.sec_my_device_info);
    public Context mContext;
    public DeviceImageManager mDeviceImageManager;
    public DeviceInfoHeaderPreferenceController mDeviceInfoHeaderPreferenceController;
    public SecRelativeLinkView mRelativeLinkView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.deviceinfo.aboutphone.SecMyDeviceInfoFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            BaseSearchIndexProvider baseSearchIndexProvider =
                    SecMyDeviceInfoFragment.SEARCH_INDEX_DATA_PROVIDER;
            ArrayList arrayList = new ArrayList();
            arrayList.add(new DeviceInfoHeaderPreferenceController(context, null, null, null));
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            searchIndexableRaw.title = context.getString(R.string.edit_device_name);
            searchIndexableRaw.screenTitle = context.getString(R.string.sec_about_settings);
            ((SearchIndexableData) searchIndexableRaw).key = "edit_device_name";
            searchIndexableRaw.keywords = context.getString(R.string.keywords_edit_device_name);
            arrayList.add(searchIndexableRaw);
            return arrayList;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        Lifecycle settingsLifecycle = getSettingsLifecycle();
        FragmentActivity activity = getActivity();
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                new DeviceInfoHeaderPreferenceController(
                        context, activity, this, settingsLifecycle));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SecMyDeviceInfoFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 40;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_my_device_info;
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mDeviceInfoHeaderPreferenceController =
                (DeviceInfoHeaderPreferenceController)
                        use(DeviceInfoHeaderPreferenceController.class);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.d("SecMyDeviceInfoFragment", "onConfigurationChanged");
        DeviceInfoHeaderPreferenceController deviceInfoHeaderPreferenceController =
                this.mDeviceInfoHeaderPreferenceController;
        if (deviceInfoHeaderPreferenceController != null) {
            DeviceInfoHeader deviceInfoHeader =
                    deviceInfoHeaderPreferenceController.mDeviceInfoHeader;
            if (deviceInfoHeader == null) {
                Log.w("DeviceInfoHeaderPreferenceController", "Fail to update UI");
            } else {
                deviceInfoHeader.initInfoChart();
            }
        }
    }

    /* JADX WARN: Type inference failed for: r2v3, types: [com.samsung.android.settings.deviceinfo.aboutphone.deviceimage.DeviceImageManager$2] */
    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getActivity().getBaseContext();
        KnoxUtils.removeKnoxCustomSettingsHiddenItems(this);
        if (DeviceImageFileUtils.isImageFileExist(this.mContext)) {
            File file = new File(DeviceImageFileUtils.getImageFilePath(this.mContext));
            if (file.exists() && file.lastModified() + 86400000 < System.currentTimeMillis()) {
                Log.d("DeviceImageFileUtils", "Image File is older than a day");
            }
            setAutoRemoveInsetCategory(false);
        }
        final DeviceImageManager deviceImageManager = new DeviceImageManager(this.mContext);
        this.mDeviceImageManager = deviceImageManager;
        new Thread(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.deviceinfo.aboutphone.deviceimage.DeviceImageManager.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                ProductDataSet create;
                                ConfigurationDataSet configurationDataSet;
                                ScpmProduct scpmProduct = DeviceImageManager.this.mScpmProduct;
                                Log.d("DeviceImageManager", "register");
                                try {
                                    if (scpmProduct
                                                    .context
                                                    .getPackageManager()
                                                    .resolveContentProvider(
                                                            "com.samsung.android.scpm.product", 0)
                                            != null) {
                                        Log.d(
                                                "DeviceImageManager",
                                                "scpm result : " + scpmProduct.register().result);
                                    } else {
                                        Log.d(
                                                "DeviceImageManager",
                                                "SCPM Product is not available");
                                    }
                                } catch (Throwable th) {
                                    Log.d(
                                            "DeviceImageManager",
                                            "resister failed --> error :" + th.getMessage());
                                }
                                DeviceImageManager deviceImageManager2 = DeviceImageManager.this;
                                deviceImageManager2.getClass();
                                Log.d("DeviceImageManager", GoodSettingsProvider.METHOD_INITIALIZE);
                                ScpmProduct scpmProduct2 = deviceImageManager2.mScpmProduct;
                                if (scpmProduct2
                                                .context
                                                .getPackageManager()
                                                .resolveContentProvider(
                                                        "com.samsung.android.scpm.product", 0)
                                        != null) {
                                    String packageName =
                                            deviceImageManager2.mContext.getPackageName();
                                    String str = "initialize : " + scpmProduct2.appId;
                                    boolean z = LOG.ENABLED;
                                    String str2 = scpmProduct2.TAG;
                                    if (str != null) {
                                        Log.i(str2, str);
                                    }
                                    try {
                                        Bundle bundle2 = new Bundle();
                                        bundle2.putString("receiverPackageName", packageName);
                                        Bundle call =
                                                scpmProduct2.call(
                                                        GoodSettingsProvider.METHOD_INITIALIZE,
                                                        scpmProduct2.context.getPackageName(),
                                                        bundle2);
                                        ScpmDataSet create2 = ScpmDataSet.create(call);
                                        if (call != null) {
                                            call.getString("filterId", null);
                                        }
                                        configurationDataSet =
                                                new ConfigurationDataSet(
                                                        create2.result,
                                                        create2.rcode,
                                                        create2.rmsg);
                                    } catch (Exception e) {
                                        LOG.e(str2, "cannot register package : " + e.getMessage());
                                        configurationDataSet =
                                                new ConfigurationDataSet(
                                                        2,
                                                        90000000,
                                                        "There is an exception, please check  { "
                                                                + e.getMessage()
                                                                + "}");
                                    }
                                    String str3 = configurationDataSet.rmsg;
                                    if (str3 != null) {
                                        Log.d(
                                                "DeviceImageManager",
                                                "initialize rcode = "
                                                        + configurationDataSet.rcode
                                                        + ", rmsg = "
                                                        + str3);
                                    }
                                } else {
                                    Log.d("DeviceImageManager", "SCPM Product is not available");
                                }
                                DeviceImageManager deviceImageManager3 = DeviceImageManager.this;
                                deviceImageManager3.getClass();
                                Log.d("DeviceImageManager", "requestPki");
                                ScpmProduct scpmProduct3 = deviceImageManager3.mScpmProduct;
                                if (scpmProduct3
                                                .context
                                                .getPackageManager()
                                                .resolveContentProvider(
                                                        "com.samsung.android.scpm.product", 0)
                                        == null) {
                                    Log.d("DeviceImageManager", "SCPM Product is not available");
                                    return;
                                }
                                String str4 = SystemProperties.get("ril.product_code");
                                DialogFragment$$ExternalSyntheticOutline0.m(
                                        "requestPki - model_code = ", str4, "DeviceImageManager");
                                String[] strArr = {str4};
                                boolean z2 = LOG.ENABLED;
                                String str5 = scpmProduct3.TAG;
                                Log.i(str5, "requestPki : 1");
                                try {
                                    Bundle bundle3 = new Bundle();
                                    bundle3.putStringArray("modelCodes", strArr);
                                    create =
                                            ProductDataSet.create(
                                                    scpmProduct3.call(
                                                            "requestPki",
                                                            scpmProduct3.context.getPackageName(),
                                                            bundle3),
                                                    null);
                                } catch (Exception e2) {
                                    LOG.e(str5, "cannot get pki from scpm : " + e2.getMessage());
                                    create = ProductDataSet.create(e2);
                                }
                                String str6 = create.rmsg;
                                if (str6 != null) {
                                    Log.d(
                                            "DeviceImageManager",
                                            "requestPki rcode = "
                                                    + create.rcode
                                                    + ", rmsg = "
                                                    + str6);
                                }
                            }
                        })
                .start();
        final DeviceImageManager deviceImageManager2 = this.mDeviceImageManager;
        SecMyDeviceInfoFragment$$ExternalSyntheticLambda0
                secMyDeviceInfoFragment$$ExternalSyntheticLambda0 =
                        new SecMyDeviceInfoFragment$$ExternalSyntheticLambda0(this);
        deviceImageManager2.getClass();
        Log.d("DeviceImageManager", "register Device Image Receiver");
        deviceImageManager2.mListener = secMyDeviceInfoFragment$$ExternalSyntheticLambda0;
        IntentFilter m =
                AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(
                        "com.samsung.android.scpm.product.UPDATE.s5d189ajvs");
        Context context = deviceImageManager2.mContext;
        if (deviceImageManager2.mDeviceImageReceiver == null) {
            deviceImageManager2.mDeviceImageReceiver =
                    new BroadcastReceiver() { // from class:
                        // com.samsung.android.settings.deviceinfo.aboutphone.deviceimage.DeviceImageManager.2
                        /* JADX WARN: Removed duplicated region for block: B:25:0x0116  */
                        /* JADX WARN: Removed duplicated region for block: B:39:0x0163  */
                        @Override // android.content.BroadcastReceiver
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final void onReceive(
                                android.content.Context r18, android.content.Intent r19) {
                            /*
                                Method dump skipped, instructions count: 472
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.samsung.android.settings.deviceinfo.aboutphone.deviceimage.DeviceImageManager.AnonymousClass2.onReceive(android.content.Context,"
                                        + " android.content.Intent):void");
                        }
                    };
        }
        context.registerReceiver(deviceImageManager2.mDeviceImageReceiver, m, 2);
        setAutoRemoveInsetCategory(false);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        DeviceImageManager.AnonymousClass2 anonymousClass2;
        super.onDestroy();
        DeviceImageManager deviceImageManager = this.mDeviceImageManager;
        if (deviceImageManager == null
                || (anonymousClass2 = deviceImageManager.mDeviceImageReceiver) == null) {
            return;
        }
        deviceImageManager.mContext.unregisterReceiver(anonymousClass2);
        deviceImageManager.mDeviceImageReceiver = null;
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        getActivity();
        if (Rune.supportRelativeLink() && this.mRelativeLinkView == null) {
            SecRelativeLinkView secRelativeLinkView = new SecRelativeLinkView(getActivity());
            this.mRelativeLinkView = secRelativeLinkView;
            SoftwareUpdateLinkData softwareUpdateLinkData =
                    new SoftwareUpdateLinkData(this.mContext);
            if (softwareUpdateLinkData.packageEnabled) {
                Context context = this.mContext;
                if (Utils.isNewDexMode(context)
                        || !Rune.isSamsungDexMode(context)
                        || Utils.isDesktopStandaloneMode(context)) {
                    SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData =
                            new SettingsPreferenceFragmentLinkData();
                    settingsPreferenceFragmentLinkData.flowId = "95020";
                    settingsPreferenceFragmentLinkData.callerMetric = 40;
                    Bundle m =
                            AbsAdapter$1$$ExternalSyntheticOutline0.m(
                                    ":settings:fragment_args_key", "key_update");
                    Intent intent = softwareUpdateLinkData.intent;
                    intent.putExtra(":settings:show_fragment_args", m);
                    settingsPreferenceFragmentLinkData.intent = intent;
                    settingsPreferenceFragmentLinkData.topLevelKey =
                            softwareUpdateLinkData.topLevelKey;
                    settingsPreferenceFragmentLinkData.titleRes =
                            R.string.sec_software_update_title;
                    secRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData);
                }
            }
            SecRelativeLinkView secRelativeLinkView2 = this.mRelativeLinkView;
            if (!Rune.isShopDemo(this.mContext)
                    && !Rune.isLDUModel()
                    && UserHandle.myUserId() == 0
                    && !KnoxUtils.checkKnoxCustomSettingsHiddenItem(4096)) {
                SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData2 =
                        new SettingsPreferenceFragmentLinkData();
                settingsPreferenceFragmentLinkData2.flowId = "9025";
                settingsPreferenceFragmentLinkData2.callerMetric = 40;
                Intent intent2 = new Intent();
                intent2.setClassName(
                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                        "com.android.settings.Settings$ResetDashboardFragmentActivity");
                intent2.putExtra("menu", UniversalCredentialManager.RESET_APPLET_FORM_FACTOR);
                settingsPreferenceFragmentLinkData2.intent = intent2;
                settingsPreferenceFragmentLinkData2.titleRes = R.string.reset;
                settingsPreferenceFragmentLinkData2.topLevelKey = "top_level_general";
                secRelativeLinkView2.pushLinkData(settingsPreferenceFragmentLinkData2);
            }
            SecRelativeLinkView secRelativeLinkView3 = this.mRelativeLinkView;
            if (Utils.isSupportContactUs(this.mContext, 170001000L)) {
                SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData3 =
                        new SettingsPreferenceFragmentLinkData();
                Intent contactUsIntent = Utils.getContactUsIntent(getActivity());
                settingsPreferenceFragmentLinkData3.flowId = "9026";
                settingsPreferenceFragmentLinkData3.callerMetric = 40;
                settingsPreferenceFragmentLinkData3.intent = contactUsIntent;
                settingsPreferenceFragmentLinkData3.topLevelKey = "top_level_general";
                settingsPreferenceFragmentLinkData3.titleRes = R.string.contact_us_title;
                secRelativeLinkView3.pushLinkData(settingsPreferenceFragmentLinkData3);
            }
            this.mRelativeLinkView.create(this);
        }
    }
}

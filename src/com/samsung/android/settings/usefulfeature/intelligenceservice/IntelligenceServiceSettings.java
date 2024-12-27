package com.samsung.android.settings.usefulfeature.intelligenceservice;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.SearchIndexableData;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.core.view.KeyEventDispatcher$Component;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.window.embedding.ActivityEmbeddingController;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;
import com.android.settings.biometrics.combination.CombinedBiometricSearchIndexProvider;
import com.android.settings.core.CategoryMixin;
import com.android.settings.core.OnActivityResultListener;
import com.android.settings.fuelgauge.datasaver.DynamicDenylistManager$$ExternalSyntheticOutline0;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.drawer.DashboardCategory;
import com.android.settingslib.drawer.Tile;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.account.AccountUtils;
import com.samsung.android.settings.dynamicmenu.SecDynamicFragment;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.settings.usefulfeature.intelligenceservice.packageInfo.PackageInfo;
import com.samsung.android.settings.voiceinput.Constants;
import com.samsung.android.settings.widget.SecCustomDividerItemDecorator;
import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class IntelligenceServiceSettings extends SecDynamicFragment
        implements SecIntelligenceServicePreference.OnClickListener, OnActivityResultListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass3();
    public Context mContext;
    public IntelligenceServiceAppsManager mISAManager;
    public int mUserId;
    public int mProfileType = 1;
    public final AnonymousClass1 mIntelligenceServiceReceiver = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public AnonymousClass1() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            action.getClass();
            switch (action) {
                case "android.intent.action.PACKAGE_REPLACED":
                case "android.intent.action.PACKAGE_CHANGED":
                case "android.intent.action.PACKAGE_REMOVED":
                case "android.intent.action.PACKAGE_ADDED":
                    new Handler()
                            .postDelayed(
                                    new Runnable() { // from class:
                                                     // com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceSettings$1$$ExternalSyntheticLambda0
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            IntelligenceServiceSettings
                                                    intelligenceServiceSettings =
                                                            IntelligenceServiceSettings.this;
                                            BaseSearchIndexProvider baseSearchIndexProvider =
                                                    IntelligenceServiceSettings
                                                            .SEARCH_INDEX_DATA_PROVIDER;
                                            intelligenceServiceSettings.updatePreference$6$1();
                                        }
                                    },
                                    100L);
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceSettings$3, reason: invalid class name */
    public final class AnonymousClass3 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(final Context context) {
            final List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (!isPageSearchEnabled(context)) {
                FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                if (featureFactoryImpl == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                DashboardCategory tilesForCategory =
                        featureFactoryImpl
                                .getDashboardFeatureProvider()
                                .getTilesForCategory(
                                        "com.samsung.android.settings.category.ia.intelligenceservice");
                if (tilesForCategory != null) {
                    ((ArrayList) tilesForCategory.getTiles())
                            .forEach(
                                    new Consumer() { // from class:
                                                     // com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceSettings$3$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            nonIndexableKeys.add(((Tile) obj).getKey(context));
                                        }
                                    });
                }
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            if (!UsefulfeatureUtils.isSupportedIntelligenceService(context)) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            context.getResources();
            new SearchIndexableRaw(context);
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            ((SearchIndexableData) searchIndexableRaw).key = "galaxy_ai_settings";
            searchIndexableRaw.screenTitle =
                    context.getString(R.string.sec_intelligence_service_title);
            searchIndexableRaw.title = String.valueOf(R.string.sec_intelligence_service_title);
            searchIndexableRaw.keywords =
                    context.getString(R.string.sec_intelligence_service_keyword)
                            + ", Galaxy AI, Samsung AI, Advanced AI, Generative AI, Artificial"
                            + " intelligence, Advanced intelligence";
            ((SearchIndexableData) searchIndexableRaw).intentTargetClass =
                    IntelligenceServiceSettings.class.getName();
            arrayList.add(searchIndexableRaw);
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.className = IntelligenceServiceSettings.class.getName();
            searchIndexableResource.xmlResId = R.xml.sec_intelligence_service;
            return List.of(searchIndexableResource);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            if (UsefulfeatureUtils.isSupportedIntelligenceService(context)) {
                return !(this instanceof CombinedBiometricSearchIndexProvider);
            }
            return false;
        }
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.dashboard.DashboardFragment
    public final Preference createPreference(Tile tile) {
        String string =
                tile.mMetaData.getString(
                        "com.android.settings.PREFERENCE_TYPE", ApnSettings.MVNO_NONE);
        StringBuilder m =
                ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                        "createPreference type : ", string, ", tile key : ");
        m.append(tile.getKey(getContext()));
        Log.d("IntelligenceServiceSettings", m.toString());
        string.getClass();
        return !string.equals("PreferenceCategory")
                ? new SecIntelligenceServicePreference(getContext())
                : super.createPreference(tile);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final boolean displayTile(Tile tile) {
        return super.displayTile(tile) && tile.userHandle.contains(UserHandle.of(this.mUserId));
    }

    public final void finishOrReturnToPlaceHolder$1() {
        FragmentActivity activity = getActivity();
        if (ActivityEmbeddingController.getInstance(activity).isActivityEmbedded(activity)) {
            Intent intent = new Intent("com.samsung.android.settings.CONNECTIONS");
            intent.addFlags(603979776);
            startActivity(intent);
        }
        getActivity().onBackPressed();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "IntelligenceServiceSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 80000;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_intelligence_service;
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment
    public final boolean isLoadingSupported() {
        return this.mUserId == 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        DynamicDenylistManager$$ExternalSyntheticOutline0.m(
                "onActivityResult: requestCode : ",
                " resultCode : ",
                i,
                i2,
                "IntelligenceServiceSettings");
        int i3 = 1002;
        if (i == 1002 || i == 2002) {
            if (i2 != -1) {
                Log.i("IntelligenceServiceSettings", "call finish 2");
                finishOrReturnToPlaceHolder$1();
                return;
            } else if (AccountUtils.isSamsungAccountExists(this.mContext)
                    && !AccountUtils.isChildAccount(this.mContext)) {
                Settings.System.putIntForUser(
                        this.mContext.getContentResolver(), "ai_info_confirmed", 1, this.mUserId);
                return;
            } else {
                Log.i(
                        "IntelligenceServiceSettings",
                        "Account is not exsits or child account call finish 1");
                finishOrReturnToPlaceHolder$1();
                return;
            }
        }
        if (i == 1001 || i == 2001) {
            if (i2 != -1) {
                Log.i("IntelligenceServiceSettings", "call finish 3");
                finishOrReturnToPlaceHolder$1();
                return;
            }
            if (AccountUtils.isSamsungAccountExistsAsUser(this.mContext, this.mUserId)) {
                Settings.System.putIntForUser(
                        this.mContext.getContentResolver(), "ai_info_confirmed", 1, this.mUserId);
                return;
            }
            try {
                Log.i("IntelligenceServiceSettings", "addSamsungAccount");
                getActivity().overridePendingTransition(0, 0);
                Context context = this.mContext;
                if (i != 1001) {
                    i3 = 2002;
                }
                AccountUtils.addSamsungAccount(context, this, i3, 1, this.mUserId);
            } catch (ActivityNotFoundException e) {
                Log.e("IntelligenceServiceSettings", "ActivityNotFoundException" + e.getMessage());
            }
        }
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        UserHandle managedProfile;
        super.onAttach(context);
        if (getArguments() != null) {
            this.mProfileType = getArguments().getInt(ImsProfile.SERVICE_PROFILE, 1);
        }
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        int myUserId = UserHandle.myUserId();
        int i = this.mProfileType;
        if (i == 1) {
            UserHandle userHandle = userManager.getPrimaryUser().getUserHandle();
            myUserId = userHandle.getIdentifier();
            context = context.createContextAsUser(userHandle, 0);
        } else if (i == 2) {
            if (myUserId == 10) {
                managedProfile = UserHandle.of(myUserId);
            } else {
                myUserId = Utils.getManagedProfileId(userManager, myUserId);
                managedProfile = Utils.getManagedProfile(userManager);
            }
            context = context.createContextAsUser(managedProfile, 0);
        }
        this.mUserId = myUserId;
        this.mContext = context;
        ((PreventOnlineProcessingPreferenceController)
                        use(PreventOnlineProcessingPreferenceController.class))
                .init(this.mUserId);
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.dashboard.DashboardFragment
    public final void onCategoriesChanged(Set set) {
        super.onCategoriesChanged(set);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null
                && IntelligenceServiceUtils.isInstalled(
                        this.mContext, Constants.GALAXY_STORE_PACKAGE_NAME)) {
            for (final PackageInfo packageInfo : this.mISAManager.aiSupportedAppList.values()) {
                if (((SecIntelligenceServicePreference)
                                preferenceScreen.findPreference(packageInfo.getKeyHint()))
                        == null) {
                    SecIntelligenceServicePreference secIntelligenceServicePreference =
                            new SecIntelligenceServicePreference(getContext());
                    secIntelligenceServicePreference.mPackageInfo = packageInfo;
                    secIntelligenceServicePreference.setKey(packageInfo.getKeyHint());
                    secIntelligenceServicePreference.setOrder(packageInfo.getOrder());
                    secIntelligenceServicePreference.setTitle(packageInfo.getTitle());
                    secIntelligenceServicePreference.setSummary(packageInfo.getSummary());
                    secIntelligenceServicePreference.setIcon(packageInfo.getIcon());
                    secIntelligenceServicePreference.mListener = this;
                    secIntelligenceServicePreference.setOnPreferenceClickListener(
                            new Preference
                                    .OnPreferenceClickListener() { // from class:
                                                                   // com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceSettings.2
                                @Override // androidx.preference.Preference.OnPreferenceClickListener
                                public final boolean onPreferenceClick(Preference preference) {
                                    PackageInfo packageInfo2 = packageInfo;
                                    if (!packageInfo2.isInstalled()
                                            || !packageInfo2.isSupportedAI()) {
                                        return true;
                                    }
                                    try {
                                        IntelligenceServiceSettings.this
                                                .getContext()
                                                .startActivity(packageInfo2.getLaunchIntent());
                                        return true;
                                    } catch (ActivityNotFoundException e) {
                                        Log.e(
                                                "IntelligenceServiceSettings",
                                                "ActivityNotFoundException " + e.getMessage());
                                        return true;
                                    }
                                }
                            });
                    preferenceScreen.addPreference(secIntelligenceServicePreference);
                }
                updatePreference$6$1();
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!UsefulfeatureUtils.isSupportedIntelligenceService(getContext())) {
            Log.i("IntelligenceServiceSettings", "not support IntelligenceService");
            finish();
            return;
        }
        enableAutoFlowLogging(false);
        SALogging.insertSALog("AI003");
        LoggingHelper.insertEventLogging("AI003", 9000);
        this.mISAManager = new IntelligenceServiceAppsManager(this.mContext, this.mUserId);
        if (UsefulfeatureUtils.isSupportedIntelligenceServiceForWork(this.mContext)
                || AccountUtils.isSamsungAccountExistsAsUser(this.mContext, UserHandle.myUserId())
                || Rune.isShopDemo(this.mContext)) {
            return;
        }
        Log.i("IntelligenceServiceSettings", "Samsung account is not exists.");
        getActivity()
                .startActivityForResultAsUser(
                        new Intent(
                                "com.samsung.android.settings.action.INTELLIGENCE_SERVICE_SETTINGS"),
                        1001,
                        UserHandle.CURRENT);
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (this.mUserId == UsefulfeatureUtils.getManagedProfileId(this.mContext)) {
            getActivity().setTitle(R.string.sec_intelligence_service_for_work_title);
        } else {
            getActivity().setTitle(R.string.sec_intelligence_service_title);
        }
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        KeyEventDispatcher$Component activity = getActivity();
        if (activity instanceof CategoryMixin.CategoryHandler) {
            ((ArrayList)
                            ((CategoryMixin.CategoryHandler) activity)
                                    .getCategoryMixin()
                                    .mCategoryListeners)
                    .add(this);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme("package");
        getActivity().registerReceiver(this.mIntelligenceServiceReceiver, intentFilter);
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        KeyEventDispatcher$Component activity = getActivity();
        if (activity instanceof CategoryMixin.CategoryHandler) {
            ((ArrayList)
                            ((CategoryMixin.CategoryHandler) activity)
                                    .getCategoryMixin()
                                    .mCategoryListeners)
                    .remove(this);
        }
        getActivity().unregisterReceiver(this.mIntelligenceServiceReceiver);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setDivider(null);
        getListView()
                .addItemDecoration(
                        new SecCustomDividerItemDecorator(
                                getResources().getDrawable(R.drawable.sec_top_level_list_divider),
                                this.mContext,
                                (int)
                                        (getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_intelligence_service_item_padding_end)
                                                + getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_intelligence_service_ic_size)
                                                + getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_intelligence_service_item_padding_start)),
                                R.id.icon_frame,
                                android.R.id.icon));
        if (this.mUserId == UsefulfeatureUtils.getManagedProfileId(getContext())) {
            getActivity().setTitle(R.string.sec_intelligence_service_for_work_title);
        } else {
            getActivity().setTitle(R.string.sec_intelligence_service_title);
        }
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment
    public final void setPreferenceIcon(Preference preference, Icon icon) {
        Drawable loadDrawable = icon.loadDrawable(preference.getContext());
        try {
            loadDrawable = getPackageManager().semGetDrawableForIconTray(loadDrawable, 1);
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Icon is not found : "), "IntelligenceServiceSettings");
        }
        preference.setIcon(loadDrawable);
    }

    public final void updatePreference$6$1() {
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen == null) {
            return;
        }
        for (PackageInfo packageInfo : this.mISAManager.aiSupportedAppList.values()) {
            SecIntelligenceServicePreference secIntelligenceServicePreference =
                    (SecIntelligenceServicePreference)
                            preferenceScreen.findPreference(packageInfo.getKeyHint());
            if (secIntelligenceServicePreference != null) {
                if (!packageInfo.isInstalled()) {
                    secIntelligenceServicePreference.mStatus = 1;
                    secIntelligenceServicePreference.updateStatusIcon(1);
                } else if (!packageInfo.isInstalled() || packageInfo.isSupportedAI()) {
                    secIntelligenceServicePreference.mStatus = 0;
                    secIntelligenceServicePreference.updateStatusIcon(0);
                } else {
                    secIntelligenceServicePreference.mStatus = 2;
                    secIntelligenceServicePreference.updateStatusIcon(2);
                }
            }
        }
    }
}

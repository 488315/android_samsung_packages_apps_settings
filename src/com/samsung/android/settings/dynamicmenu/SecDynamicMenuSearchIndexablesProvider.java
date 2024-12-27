package com.samsung.android.settings.dynamicmenu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFeatureProviderImpl;
import com.android.settings.development.DevelopmentSettingsDashboardFragment;
import com.android.settings.emergency.EmergencyDashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.privacy.PrivacyDashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.security.SecurityAdvancedSettings;
import com.android.settings.system.ResetDashboardFragment;
import com.android.settingslib.drawer.DashboardCategory;
import com.android.settingslib.drawer.Tile;
import com.android.settingslib.search.Indexable$SearchIndexProvider;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.accessories.DockSettings;
import com.samsung.android.settings.account.CloudAccountSettings;
import com.samsung.android.settings.connection.ConnectionsSettings;
import com.samsung.android.settings.connection.WirelessSettings;
import com.samsung.android.settings.deviceinfo.aboutphone.SecMyDeviceInfoFragment;
import com.samsung.android.settings.external.DynamicMenuData;
import com.samsung.android.settings.multidevices.MultiDevicesFragment;
import com.samsung.android.settings.privacy.OtherPrivacySettingsFragment;
import com.samsung.android.settings.privacy.SecurityAndPrivacySettings;
import com.samsung.android.settings.security.SecurityUtils;
import com.samsung.android.settings.usefulfeature.Usefulfeature;
import com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceSettings;
import com.samsung.android.settings.usefulfeature.labs.LabsSettings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SecDynamicMenuSearchIndexablesProvider {
    public static final Map SCREEN_TITLE_MAP;
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER;
    public static final List SKIP_ITEMS;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.dynamicmenu.SecDynamicMenuSearchIndexablesProvider$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        public DashboardFeatureProviderImpl mDashboardFeatureProvider;
        public SecDynamicMenuFeatureProviderImpl mSecDynamicMenuFeatureProvider;

        public static boolean isValidTile(Context context, Tile tile) {
            boolean isAdminUser;
            boolean isSupportFindMyMobileFeature;
            String key = tile.getKey(context);
            Bundle bundle = tile.mMetaData;
            String str = ApnSettings.MVNO_NONE;
            if (bundle != null) {
                str =
                        bundle.getString(
                                "com.android.settings.PREFERENCE_TYPE", ApnSettings.MVNO_NONE);
            }
            if (!TextUtils.isEmpty(key)
                    && !((ArrayList) SecDynamicMenuSearchIndexablesProvider.SKIP_ITEMS)
                            .contains(key)) {
                key.getClass();
                switch (key) {
                    case "security_status_security_patch_level":
                        isAdminUser = UserManager.get(context).isAdminUser();
                        break;
                    case "security_status_find_device":
                        isSupportFindMyMobileFeature =
                                SecurityUtils.isSupportFindMyMobileFeature(context);
                        isAdminUser = !isSupportFindMyMobileFeature;
                        break;
                    case "dashboard_tile_pref_com.android.healthconnect.controller.MainActivity":
                        isSupportFindMyMobileFeature = Rune.isChinaModel();
                        isAdminUser = !isSupportFindMyMobileFeature;
                        break;
                    case "key_find_my_mobile":
                        isAdminUser = SecurityUtils.isSupportFindMyMobileFeature(context);
                        break;
                    default:
                        isAdminUser = true;
                        break;
                }
            } else {
                isAdminUser = false;
            }
            if (isAdminUser) {
                return !("SecInsetCategoryPreference".equals(str)
                        || "PreferenceCategory".equals(str));
            }
            return false;
        }

        public final List collectionTiles() {
            ArrayList arrayList = new ArrayList();
            Iterator it =
                    ((ArrayMap) SecDynamicFragmentRegistry.PARENT_TO_CATEGORY_KEY_MAP)
                            .entrySet()
                            .iterator();
            while (it.hasNext()) {
                Iterator it2 = ((List) ((Map.Entry) it.next()).getValue()).iterator();
                while (it2.hasNext()) {
                    DashboardCategory tilesForCategory =
                            this.mDashboardFeatureProvider.getTilesForCategory((String) it2.next());
                    if (tilesForCategory != null) {
                        arrayList.addAll(tilesForCategory.getTiles());
                    }
                }
            }
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getDynamicRawDataToIndex(Context context) {
            List dynamicRawDataToIndex = super.getDynamicRawDataToIndex(context);
            if (Rune.isChinaModel()) {
                ((ArrayList) dynamicRawDataToIndex).addAll(getRawDataToDynamicMenu(context));
            }
            return dynamicRawDataToIndex;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(final Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (!Rune.isChinaModel()) {
                FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                if (featureFactoryImpl == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                this.mDashboardFeatureProvider = featureFactoryImpl.getDashboardFeatureProvider();
                FeatureFactoryImpl featureFactoryImpl2 = FeatureFactoryImpl._factory;
                if (featureFactoryImpl2 == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                this.mSecDynamicMenuFeatureProvider =
                        (SecDynamicMenuFeatureProviderImpl)
                                featureFactoryImpl2.secDynamicMenuFeatureProvider$delegate
                                        .getValue();
                List collectionTiles = collectionTiles();
                final ArrayList arrayList = new ArrayList();
                ((ArrayList) collectionTiles)
                        .forEach(
                                new Consumer() { // from class:
                                                 // com.samsung.android.settings.dynamicmenu.SecDynamicMenuSearchIndexablesProvider$1$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        Uri uriFromIntent;
                                        DynamicMenuData bundleFromKey;
                                        SecDynamicMenuSearchIndexablesProvider.AnonymousClass1
                                                anonymousClass1 =
                                                        SecDynamicMenuSearchIndexablesProvider
                                                                .AnonymousClass1.this;
                                        Context context2 = context;
                                        List list = arrayList;
                                        Tile tile = (Tile) obj;
                                        String dashboardKeyForTile =
                                                anonymousClass1.mDashboardFeatureProvider
                                                        .getDashboardKeyForTile(tile);
                                        if (!SecDynamicMenuSearchIndexablesProvider.AnonymousClass1
                                                        .isValidTile(context2, tile)
                                                || (uriFromIntent =
                                                                anonymousClass1
                                                                        .mSecDynamicMenuFeatureProvider
                                                                        .getUriFromIntent(
                                                                                tile.mIntent))
                                                        == null
                                                || (bundleFromKey =
                                                                anonymousClass1
                                                                        .mSecDynamicMenuFeatureProvider
                                                                        .getBundleFromKey(
                                                                                uriFromIntent,
                                                                                dashboardKeyForTile))
                                                        == null
                                                || bundleFromKey.mIsVisible) {
                                            return;
                                        }
                                        list.add(dashboardKeyForTile);
                                    }
                                });
                ((ArrayList) nonIndexableKeys).addAll(arrayList);
                Log.d(
                        "SecDynamicMenuSearchIndexablesProvider",
                        "NonIndexableKeys : " + nonIndexableKeys);
            }
            return nonIndexableKeys;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:128:0x0265  */
        /* JADX WARN: Removed duplicated region for block: B:132:0x01b7  */
        /* JADX WARN: Removed duplicated region for block: B:15:0x032b  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x032e A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:39:0x017e  */
        /* JADX WARN: Removed duplicated region for block: B:42:0x0189  */
        /* JADX WARN: Removed duplicated region for block: B:45:0x0194  */
        /* JADX WARN: Removed duplicated region for block: B:48:0x01a7  */
        /* JADX WARN: Removed duplicated region for block: B:51:0x01c5  */
        /* JADX WARN: Removed duplicated region for block: B:59:0x027b  */
        /* JADX WARN: Removed duplicated region for block: B:62:0x0282  */
        /* JADX WARN: Removed duplicated region for block: B:77:0x0310  */
        /* JADX WARN: Removed duplicated region for block: B:80:0x031f  */
        /* JADX WARN: Removed duplicated region for block: B:82:0x0323  */
        /* JADX WARN: Removed duplicated region for block: B:83:0x0314  */
        /* JADX WARN: Removed duplicated region for block: B:97:0x0260  */
        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.util.List getRawDataToDynamicMenu(android.content.Context r20) {
            /*
                Method dump skipped, instructions count: 831
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.settings.dynamicmenu.SecDynamicMenuSearchIndexablesProvider.AnonymousClass1.getRawDataToDynamicMenu(android.content.Context):java.util.List");
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            if (!Rune.isChinaModel()) {
                arrayList.addAll(getRawDataToDynamicMenu(context));
            }
            return arrayList;
        }
    }

    static {
        ArrayMap arrayMap = new ArrayMap();
        SCREEN_TITLE_MAP = arrayMap;
        arrayMap.put(Usefulfeature.class.getName(), Integer.valueOf(R.string.useful_feature_title));
        arrayMap.put(
                EmergencyDashboardFragment.class.getName(),
                Integer.valueOf(R.string.emergency_settings_preference_title));
        arrayMap.put(
                ConnectionsSettings.class.getName(),
                Integer.valueOf(R.string.tab_category_connections));
        arrayMap.put(
                WirelessSettings.class.getName(),
                Integer.valueOf(R.string.more_connection_settings_title));
        arrayMap.put(
                SecurityAndPrivacySettings.class.getName(),
                Integer.valueOf(R.string.security_and_privacy_title));
        arrayMap.put(
                OtherPrivacySettingsFragment.class.getName(),
                Integer.valueOf(R.string.other_privacy_settings_title));
        arrayMap.put(
                PrivacyDashboardFragment.class.getName(),
                Integer.valueOf(R.string.privacy_dashboard_title));
        arrayMap.put(
                DevelopmentSettingsDashboardFragment.class.getName(),
                Integer.valueOf(R.string.development_settings_title));
        arrayMap.put(
                MultiDevicesFragment.class.getName(),
                Integer.valueOf(R.string.sec_multi_devices_menu_name));
        arrayMap.put(
                SecurityAdvancedSettings.class.getName(),
                Integer.valueOf(R.string.security_advanced_settings));
        arrayMap.put(
                IntelligenceServiceSettings.class.getName(),
                Integer.valueOf(R.string.sec_intelligence_service_title));
        arrayMap.put(DockSettings.class.getName(), Integer.valueOf(R.string.accessory));
        arrayMap.put(
                CloudAccountSettings.class.getName(),
                Integer.valueOf(R.string.cloud_and_accounts_title));
        arrayMap.put(
                SecMyDeviceInfoFragment.class.getName(),
                Integer.valueOf(R.string.sec_about_settings));
        arrayMap.put(ResetDashboardFragment.class.getName(), Integer.valueOf(R.string.reset));
        arrayMap.put(
                LabsSettings.class.getName(), Integer.valueOf(R.string.sec_labs_settings_title));
        ArrayList arrayList = new ArrayList();
        SKIP_ITEMS = arrayList;
        arrayList.add("screenshots_and_screen_recorder");
        arrayList.add("wfc_settings_tile");
        arrayList.add("WifiCalling");
        arrayList.add("security_status_security_patch_level");
        arrayList.add("security_status_partial_system_updates");
        arrayList.add("security_status_package_verifier");
        SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();
    }
}

package com.samsung.android.settings.dynamicmenu;

import android.util.ArrayMap;

import com.android.settings.development.DevelopmentSettingsDashboardFragment;
import com.android.settings.emergency.EmergencyDashboardFragment;
import com.android.settings.security.SecurityAdvancedSettings;
import com.android.settings.system.ResetDashboardFragment;

import com.samsung.android.settings.accessories.DockSettings;
import com.samsung.android.settings.account.CloudAccountSettings;
import com.samsung.android.settings.connection.ConnectionsSettings;
import com.samsung.android.settings.connection.WirelessSettings;
import com.samsung.android.settings.deviceinfo.aboutphone.SecMyDeviceInfoFragment;
import com.samsung.android.settings.general.GeneralDeviceSettings;
import com.samsung.android.settings.multidevices.MultiDevicesFragment;
import com.samsung.android.settings.privacy.OtherPrivacySettingsFragment;
import com.samsung.android.settings.privacy.SecurityAndPrivacySettings;
import com.samsung.android.settings.usefulfeature.Usefulfeature;
import com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceSettings;
import com.samsung.android.settings.usefulfeature.labs.LabsSettings;

import java.util.ArrayList;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SecDynamicFragmentRegistry {
    public static final Map CATEGORY_KEY_TO_PARENT_MAP;
    public static final Map PARENT_TO_CATEGORY_KEY_MAP;

    static {
        ArrayMap arrayMap = new ArrayMap();
        PARENT_TO_CATEGORY_KEY_MAP = arrayMap;
        ArrayList arrayList = new ArrayList();
        arrayList.add("com.samsung.android.settings.category.ia.connections_settings");
        arrayMap.put(ConnectionsSettings.class.getName(), arrayList);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add("com.samsung.android.settings.category.ia.more_connections");
        arrayMap.put(WirelessSettings.class.getName(), arrayList2);
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add("com.android.settings.category.ia.security");
        arrayMap.put(SecurityAndPrivacySettings.class.getName(), arrayList3);
        ArrayList arrayList4 = new ArrayList();
        arrayList4.add("com.android.settings.category.ia.advanced_security");
        arrayMap.put(SecurityAdvancedSettings.class.getName(), arrayList4);
        ArrayList arrayList5 = new ArrayList();
        arrayList5.add("com.samsung.android.settings.category.ia.general");
        arrayMap.put(GeneralDeviceSettings.class.getName(), arrayList5);
        ArrayList arrayList6 = new ArrayList();
        arrayList6.add("com.android.settings.category.ia.privacy");
        arrayMap.put(OtherPrivacySettingsFragment.class.getName(), arrayList6);
        ArrayList arrayList7 = new ArrayList();
        arrayList7.add("com.samsung.android.settings.category.ia.dock_settings");
        arrayMap.put(DockSettings.class.getName(), arrayList7);
        ArrayList arrayList8 = new ArrayList();
        arrayList8.add("com.samsung.android.settings.category.ia.usefulfeatures");
        arrayMap.put(Usefulfeature.class.getName(), arrayList8);
        ArrayList arrayList9 = new ArrayList();
        arrayList9.add("com.samsung.android.settings.category.ia.intelligenceservice");
        arrayMap.put(IntelligenceServiceSettings.class.getName(), arrayList9);
        ArrayList arrayList10 = new ArrayList();
        arrayList10.add("com.samsung.android.settings.category.ia.labs");
        arrayMap.put(LabsSettings.class.getName(), arrayList10);
        ArrayList arrayList11 = new ArrayList();
        arrayList11.add("com.android.settings.category.ia.safety_emergency");
        arrayList11.add("com.android.settings.category.ia.emergency");
        arrayMap.put(EmergencyDashboardFragment.class.getName(), arrayList11);
        ArrayList arrayList12 = new ArrayList();
        arrayList12.add("com.samsung.android.settings.category.ia.autorestart");
        arrayList12.add("com.samsung.android.settings.category.ia.reset_settings");
        arrayMap.put(ResetDashboardFragment.class.getName(), arrayList12);
        ArrayList arrayList13 = new ArrayList();
        arrayList13.add("com.android.settings.category.ia.my_device_info");
        arrayMap.put(SecMyDeviceInfoFragment.class.getName(), arrayList13);
        ArrayList arrayList14 = new ArrayList();
        arrayList14.add("com.android.settings.category.ia.development");
        arrayMap.put(DevelopmentSettingsDashboardFragment.class.getName(), arrayList14);
        ArrayList arrayList15 = new ArrayList();
        arrayList15.add("com.samsung.android.settings.category.ia.multidevices");
        arrayMap.put(MultiDevicesFragment.class.getName(), arrayList15);
        ArrayList arrayList16 = new ArrayList();
        arrayList16.add("com.samsung.android.settings.category.ia.cloudaccount");
        arrayMap.put(CloudAccountSettings.class.getName(), arrayList16);
        CATEGORY_KEY_TO_PARENT_MAP = new ArrayMap(arrayMap.size());
        for (Map.Entry entry : arrayMap.entrySet()) {
            CATEGORY_KEY_TO_PARENT_MAP.put((ArrayList) entry.getValue(), (String) entry.getKey());
        }
    }
}

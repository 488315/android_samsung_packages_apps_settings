package com.android.settings.enterprise;

import android.content.Context;
import android.provider.SearchIndexableResource;

import com.android.settings.R;
import com.android.settings.widget.PreferenceCategoryController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PrivacySettingsEnterprisePreference implements PrivacySettingsPreference {
    public final Context mContext;

    public PrivacySettingsEnterprisePreference(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @Override // com.android.settings.enterprise.PrivacySettingsPreference
    public final List createPreferenceControllers(boolean z) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new NetworkLogsPreferenceController(this.mContext));
        arrayList.add(new BugReportsPreferenceController(this.mContext));
        arrayList.add(new SecurityLogsPreferenceController(this.mContext));
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new EnterpriseInstalledPackagesPreferenceController(this.mContext, z));
        arrayList2.add(
                new AdminGrantedLocationPermissionsPreferenceController(
                        this.mContext,
                        z,
                        new String[] {
                            "android.permission.ACCESS_COARSE_LOCATION",
                            "android.permission.ACCESS_FINE_LOCATION"
                        }));
        arrayList2.add(
                new AdminGrantedMicrophonePermissionPreferenceController(
                        this.mContext, z, new String[] {"android.permission.RECORD_AUDIO"}));
        arrayList2.add(
                new AdminGrantedCameraPermissionPreferenceController(
                        this.mContext, z, new String[] {"android.permission.CAMERA"}));
        arrayList2.add(new EnterpriseSetDefaultAppsPreferenceController(this.mContext));
        arrayList2.add(new AlwaysOnVpnCurrentUserPreferenceController(this.mContext));
        arrayList2.add(new AlwaysOnVpnManagedProfilePreferenceController(this.mContext));
        arrayList2.add(new ImePreferenceController(this.mContext));
        arrayList2.add(new GlobalHttpProxyPreferenceController(this.mContext));
        arrayList2.add(new CaCertsCurrentUserPreferenceController(this.mContext));
        arrayList2.add(new CaCertsManagedProfilePreferenceController(this.mContext));
        arrayList.addAll(arrayList2);
        arrayList.add(
                new PreferenceCategoryController(this.mContext, "exposure_changes_category")
                        .setChildren(arrayList2));
        arrayList.add(new FailedPasswordWipeCurrentUserPreferenceController(this.mContext));
        arrayList.add(new FailedPasswordWipeManagedProfilePreferenceController(this.mContext));
        return arrayList;
    }

    @Override // com.android.settings.enterprise.PrivacySettingsPreference
    public final int getPreferenceScreenResId() {
        return R.xml.enterprise_privacy_settings;
    }

    @Override // com.android.settings.enterprise.PrivacySettingsPreference
    public final List getXmlResourcesToIndex() {
        SearchIndexableResource searchIndexableResource =
                new SearchIndexableResource(this.mContext);
        searchIndexableResource.xmlResId = R.xml.enterprise_privacy_settings;
        return Collections.singletonList(searchIndexableResource);
    }
}

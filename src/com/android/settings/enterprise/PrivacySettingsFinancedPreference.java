package com.android.settings.enterprise;

import android.content.Context;
import android.provider.SearchIndexableResource;

import com.android.settings.R;

import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PrivacySettingsFinancedPreference implements PrivacySettingsPreference {
    public final Context mContext;

    public PrivacySettingsFinancedPreference(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @Override // com.android.settings.enterprise.PrivacySettingsPreference
    public final List createPreferenceControllers(boolean z) {
        return Collections.emptyList();
    }

    @Override // com.android.settings.enterprise.PrivacySettingsPreference
    public final int getPreferenceScreenResId() {
        return R.xml.financed_privacy_settings;
    }

    @Override // com.android.settings.enterprise.PrivacySettingsPreference
    public final List getXmlResourcesToIndex() {
        SearchIndexableResource searchIndexableResource =
                new SearchIndexableResource(this.mContext);
        searchIndexableResource.xmlResId = R.xml.financed_privacy_settings;
        return Collections.singletonList(searchIndexableResource);
    }
}

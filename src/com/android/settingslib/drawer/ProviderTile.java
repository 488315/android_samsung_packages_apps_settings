package com.android.settingslib.drawer;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ProviderTile extends Tile {
    public String mAuthority;
    public String mKey;

    public ProviderTile(ProviderInfo providerInfo, String str, Bundle bundle) {
        super(providerInfo, str, bundle);
        this.mAuthority = providerInfo.authority;
        this.mKey = bundle.getString("com.android.settings.keyhint");
    }

    @Override // com.android.settingslib.drawer.Tile
    public final ComponentInfo getComponentInfo(Context context) {
        if (this.mComponentInfo == null) {
            System.currentTimeMillis();
            PackageManager packageManager = context.getApplicationContext().getPackageManager();
            Intent intent = this.mIntent;
            List<ResolveInfo> queryIntentContentProviders =
                    packageManager.queryIntentContentProviders(intent, 0);
            if (queryIntentContentProviders == null || queryIntentContentProviders.isEmpty()) {
                Log.e(
                        "ProviderTile",
                        "Cannot find package info for " + intent.getComponent().flattenToString());
            } else {
                ProviderInfo providerInfo = queryIntentContentProviders.get(0).providerInfo;
                this.mComponentInfo = providerInfo;
                String str = providerInfo.authority;
                String str2 = this.mKey;
                ArrayMap arrayMap = new ArrayMap();
                Bundle bundleFromUri =
                        TileUtils.getBundleFromUri(
                                context,
                                TileUtils.buildUri(str, "getEntryData", str2),
                                arrayMap,
                                null);
                if (bundleFromUri == null) {
                    bundleFromUri =
                            TileUtils.getBundleFromUri(
                                    context,
                                    TileUtils.buildUri(str, "getSwitchData", str2),
                                    arrayMap,
                                    null);
                }
                this.mMetaData = bundleFromUri;
            }
        }
        return this.mComponentInfo;
    }

    @Override // com.android.settingslib.drawer.Tile
    public final CharSequence getComponentLabel(Context context) {
        return null;
    }

    @Override // com.android.settingslib.drawer.Tile
    public final String getDescription() {
        return this.mAuthority + "/" + this.mKey;
    }
}

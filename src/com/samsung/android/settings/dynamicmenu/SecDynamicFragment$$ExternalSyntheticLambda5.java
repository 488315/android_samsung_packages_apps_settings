package com.samsung.android.settings.dynamicmenu;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;

import androidx.preference.Preference;

import com.android.settingslib.drawer.Tile;
import com.android.settingslib.drawer.TileUtils;
import com.android.settingslib.utils.ThreadUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SecDynamicFragment$$ExternalSyntheticLambda5
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecDynamicFragment f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Preference f$2;

    public /* synthetic */ SecDynamicFragment$$ExternalSyntheticLambda5(
            SecDynamicFragment secDynamicFragment, Preference preference, Icon icon) {
        this.$r8$classId = 2;
        this.f$0 = secDynamicFragment;
        this.f$2 = preference;
        this.f$1 = icon;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SecDynamicFragment secDynamicFragment = this.f$0;
                Tile tile = (Tile) this.f$1;
                Preference preference = this.f$2;
                if (secDynamicFragment.getActivity() != null) {
                    Bundle bundleFromUri =
                            TileUtils.getBundleFromUri(
                                    secDynamicFragment.getActivity(),
                                    Uri.parse(
                                            tile.mMetaData.getString(
                                                    "com.android.settings.summary_uri")),
                                    new ArrayMap(),
                                    null);
                    ThreadUtils.postOnMainThread(
                            new SecDynamicFragment$$ExternalSyntheticLambda3(
                                    preference,
                                    bundleFromUri != null
                                            ? bundleFromUri.getString(
                                                    "com.android.settings.summary")
                                            : null));
                    break;
                }
                break;
            case 1:
                SecDynamicFragment secDynamicFragment2 = this.f$0;
                Tile tile2 = (Tile) this.f$1;
                Preference preference2 = this.f$2;
                secDynamicFragment2.getClass();
                Intent intent = tile2.mIntent;
                String str =
                        !TextUtils.isEmpty(intent.getPackage())
                                ? intent.getPackage()
                                : intent.getComponent() != null
                                        ? intent.getComponent().getPackageName()
                                        : null;
                ArrayMap arrayMap = new ArrayMap();
                Uri completeUri =
                        TileUtils.getCompleteUri(
                                tile2, "com.android.settings.icon_uri", "getProviderIcon");
                Pair iconFromUri =
                        TileUtils.getIconFromUri(
                                secDynamicFragment2.getContext(), str, completeUri, arrayMap);
                if (iconFromUri != null) {
                    ThreadUtils.postOnMainThread(
                            new SecDynamicFragment$$ExternalSyntheticLambda5(
                                    secDynamicFragment2,
                                    preference2,
                                    Icon.createWithResource(
                                            (String) iconFromUri.first,
                                            ((Integer) iconFromUri.second).intValue())));
                    break;
                } else {
                    Log.w("SecDynamicFragment", "Failed to get icon from uri " + completeUri);
                    break;
                }
            default:
                this.f$0.setPreferenceIcon(this.f$2, (Icon) this.f$1);
                break;
        }
    }

    public /* synthetic */ SecDynamicFragment$$ExternalSyntheticLambda5(
            SecDynamicFragment secDynamicFragment, Tile tile, Preference preference, int i) {
        this.$r8$classId = i;
        this.f$0 = secDynamicFragment;
        this.f$1 = tile;
        this.f$2 = preference;
    }
}

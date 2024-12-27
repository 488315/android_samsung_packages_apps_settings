package com.android.settings.dashboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settingslib.PrimarySwitchPreference;
import com.android.settingslib.drawer.Tile;
import com.android.settingslib.drawer.TileUtils;
import com.android.settingslib.utils.ThreadUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class DashboardFeatureProviderImpl$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 2;
    public final /* synthetic */ DashboardFeatureProviderImpl f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Preference f$2;
    public final /* synthetic */ boolean f$3;

    public /* synthetic */ DashboardFeatureProviderImpl$$ExternalSyntheticLambda0(
            DashboardFeatureProviderImpl dashboardFeatureProviderImpl,
            Uri uri,
            boolean z,
            Preference preference) {
        this.f$0 = dashboardFeatureProviderImpl;
        this.f$1 = uri;
        this.f$3 = z;
        this.f$2 = preference;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                final DashboardFeatureProviderImpl dashboardFeatureProviderImpl = this.f$0;
                final Tile tile = (Tile) this.f$1;
                final Preference preference = this.f$2;
                final boolean z = this.f$3;
                dashboardFeatureProviderImpl.getClass();
                Intent intent = tile.mIntent;
                String str =
                        !TextUtils.isEmpty(intent.getPackage())
                                ? intent.getPackage()
                                : intent.getComponent() != null
                                        ? intent.getComponent().getPackageName()
                                        : null;
                ArrayMap arrayMap = new ArrayMap();
                Uri completeUri =
                        TileUtils.getCompleteUri(
                                tile, "com.android.settings.icon_uri", "getProviderIcon");
                final Pair iconFromUri =
                        TileUtils.getIconFromUri(
                                dashboardFeatureProviderImpl.mContext, str, completeUri, arrayMap);
                if (iconFromUri != null) {
                    final Icon createWithResource =
                            Icon.createWithResource(
                                    (String) iconFromUri.first,
                                    ((Integer) iconFromUri.second).intValue());
                    ThreadUtils.postOnMainThread(
                            new Runnable(
                                    preference,
                                    tile,
                                    z,
                                    iconFromUri,
                                    createWithResource) { // from class:
                                                          // com.android.settings.dashboard.DashboardFeatureProviderImpl$$ExternalSyntheticLambda1
                                public final /* synthetic */ Preference f$1;
                                public final /* synthetic */ Tile f$2;
                                public final /* synthetic */ boolean f$3;
                                public final /* synthetic */ Pair f$4;
                                public final /* synthetic */ Icon f$5;

                                {
                                    this.f$4 = iconFromUri;
                                    this.f$5 = createWithResource;
                                }

                                @Override // java.lang.Runnable
                                public final void run() {
                                    DashboardFeatureProviderImpl dashboardFeatureProviderImpl2 =
                                            DashboardFeatureProviderImpl.this;
                                    Preference preference2 = this.f$1;
                                    Tile tile2 = this.f$2;
                                    Pair pair = this.f$4;
                                    Icon icon = this.f$5;
                                    dashboardFeatureProviderImpl2.getClass();
                                    DashboardFeatureProviderImpl.setPreferenceIcon(
                                            preference2, tile2, icon);
                                }
                            });
                    break;
                } else {
                    Log.w("DashboardFeatureImpl", "Failed to get icon from uri " + completeUri);
                    break;
                }
            case 1:
                DashboardFeatureProviderImpl dashboardFeatureProviderImpl2 = this.f$0;
                Preference preference2 = this.f$2;
                Bundle bundle = (Bundle) this.f$1;
                boolean z2 = this.f$3;
                dashboardFeatureProviderImpl2.getClass();
                DashboardFeatureProviderImpl.setSwitchEnabled(preference2, true);
                if (bundle.getBoolean("set_checked_error")) {
                    boolean z3 = !z2;
                    if (preference2 instanceof PrimarySwitchPreference) {
                        ((PrimarySwitchPreference) preference2).setChecked(z3);
                    } else if (preference2 instanceof TwoStatePreference) {
                        ((TwoStatePreference) preference2).setChecked(z3);
                    }
                    String string = bundle.getString("set_checked_error_message");
                    if (!TextUtils.isEmpty(string)) {
                        Toast.makeText(dashboardFeatureProviderImpl2.mContext, string, 0).show();
                        break;
                    }
                }
                break;
            default:
                DashboardFeatureProviderImpl dashboardFeatureProviderImpl3 = this.f$0;
                Uri uri = (Uri) this.f$1;
                boolean z4 = this.f$3;
                Preference preference3 = this.f$2;
                dashboardFeatureProviderImpl3.getClass();
                ArrayMap arrayMap2 = new ArrayMap();
                Context context = dashboardFeatureProviderImpl3.mContext;
                Bundle bundle2 = new Bundle();
                bundle2.putBoolean("checked_state", z4);
                ThreadUtils.postOnMainThread(
                        new DashboardFeatureProviderImpl$$ExternalSyntheticLambda0(
                                dashboardFeatureProviderImpl3,
                                preference3,
                                TileUtils.getBundleFromUri(context, uri, arrayMap2, bundle2),
                                z4));
                break;
        }
    }

    public /* synthetic */ DashboardFeatureProviderImpl$$ExternalSyntheticLambda0(
            DashboardFeatureProviderImpl dashboardFeatureProviderImpl,
            Preference preference,
            Bundle bundle,
            boolean z) {
        this.f$0 = dashboardFeatureProviderImpl;
        this.f$2 = preference;
        this.f$1 = bundle;
        this.f$3 = z;
    }

    public /* synthetic */ DashboardFeatureProviderImpl$$ExternalSyntheticLambda0(
            DashboardFeatureProviderImpl dashboardFeatureProviderImpl,
            Tile tile,
            Preference preference,
            boolean z) {
        this.f$0 = dashboardFeatureProviderImpl;
        this.f$1 = tile;
        this.f$2 = preference;
        this.f$3 = z;
    }
}

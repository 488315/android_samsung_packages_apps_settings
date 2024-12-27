package com.android.settings.dashboard;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settingslib.PrimarySwitchPreference;
import com.android.settingslib.drawer.TileUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class DashboardFeatureProviderImpl$$ExternalSyntheticLambda6
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DashboardFeatureProviderImpl f$0;
    public final /* synthetic */ Uri f$1;
    public final /* synthetic */ DashboardFeatureProviderImpl.AnonymousClass1 f$2;
    public final /* synthetic */ Preference f$3;

    public /* synthetic */ DashboardFeatureProviderImpl$$ExternalSyntheticLambda6(
            DashboardFeatureProviderImpl dashboardFeatureProviderImpl,
            Uri uri,
            Preference preference,
            DashboardFeatureProviderImpl.AnonymousClass1 anonymousClass1,
            int i) {
        this.$r8$classId = i;
        this.f$0 = dashboardFeatureProviderImpl;
        this.f$1 = uri;
        this.f$3 = preference;
        this.f$2 = anonymousClass1;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                final DashboardFeatureProviderImpl dashboardFeatureProviderImpl = this.f$0;
                Uri uri = this.f$1;
                DashboardFeatureProviderImpl.AnonymousClass1 anonymousClass1 = this.f$2;
                final Preference preference = this.f$3;
                dashboardFeatureProviderImpl.getClass();
                Bundle bundleFromUri =
                        TileUtils.getBundleFromUri(
                                dashboardFeatureProviderImpl.mContext, uri, new ArrayMap(), null);
                final boolean z =
                        bundleFromUri != null ? bundleFromUri.getBoolean("checked_state") : false;
                anonymousClass1.post(
                        new Runnable() { // from class:
                                         // com.android.settings.dashboard.DashboardFeatureProviderImpl$$ExternalSyntheticLambda9
                            @Override // java.lang.Runnable
                            public final void run() {
                                DashboardFeatureProviderImpl dashboardFeatureProviderImpl2 =
                                        DashboardFeatureProviderImpl.this;
                                Preference preference2 = preference;
                                boolean z2 = z;
                                dashboardFeatureProviderImpl2.getClass();
                                if (preference2 instanceof PrimarySwitchPreference) {
                                    ((PrimarySwitchPreference) preference2).setChecked(z2);
                                } else if (preference2 instanceof TwoStatePreference) {
                                    ((TwoStatePreference) preference2).setChecked(z2);
                                }
                                DashboardFeatureProviderImpl.setSwitchEnabled(preference2, true);
                            }
                        });
                break;
            case 1:
                DashboardFeatureProviderImpl dashboardFeatureProviderImpl2 = this.f$0;
                Uri uri2 = this.f$1;
                final Preference preference2 = this.f$3;
                DashboardFeatureProviderImpl.AnonymousClass1 anonymousClass12 = this.f$2;
                dashboardFeatureProviderImpl2.getClass();
                Bundle bundleFromUri2 =
                        TileUtils.getBundleFromUri(
                                dashboardFeatureProviderImpl2.mContext, uri2, new ArrayMap(), null);
                final String string =
                        bundleFromUri2 != null
                                ? bundleFromUri2.getString("com.android.settings.summary")
                                : null;
                if (!TextUtils.equals(string, preference2.getSummary())) {
                    final int i = 1;
                    anonymousClass12.post(
                            new Runnable() { // from class:
                                             // com.android.settings.dashboard.DashboardFeatureProviderImpl$$ExternalSyntheticLambda10
                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (i) {
                                        case 0:
                                            preference2.setTitle(string);
                                            break;
                                        default:
                                            preference2.setSummary(string);
                                            break;
                                    }
                                }
                            });
                    break;
                }
                break;
            default:
                DashboardFeatureProviderImpl dashboardFeatureProviderImpl3 = this.f$0;
                Uri uri3 = this.f$1;
                final Preference preference3 = this.f$3;
                DashboardFeatureProviderImpl.AnonymousClass1 anonymousClass13 = this.f$2;
                dashboardFeatureProviderImpl3.getClass();
                Bundle bundleFromUri3 =
                        TileUtils.getBundleFromUri(
                                dashboardFeatureProviderImpl3.mContext, uri3, new ArrayMap(), null);
                final String string2 =
                        bundleFromUri3 != null
                                ? bundleFromUri3.getString("com.android.settings.title")
                                : null;
                if (!TextUtils.equals(string2, preference3.getTitle())) {
                    final int i2 = 0;
                    anonymousClass13.post(
                            new Runnable() { // from class:
                                             // com.android.settings.dashboard.DashboardFeatureProviderImpl$$ExternalSyntheticLambda10
                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (i2) {
                                        case 0:
                                            preference3.setTitle(string2);
                                            break;
                                        default:
                                            preference3.setSummary(string2);
                                            break;
                                    }
                                }
                            });
                    break;
                }
                break;
        }
    }

    public /* synthetic */ DashboardFeatureProviderImpl$$ExternalSyntheticLambda6(
            DashboardFeatureProviderImpl dashboardFeatureProviderImpl,
            Uri uri,
            DashboardFeatureProviderImpl.AnonymousClass1 anonymousClass1,
            Preference preference) {
        this.$r8$classId = 0;
        this.f$0 = dashboardFeatureProviderImpl;
        this.f$1 = uri;
        this.f$2 = anonymousClass1;
        this.f$3 = preference;
    }
}

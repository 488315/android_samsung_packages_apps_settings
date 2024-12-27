package com.android.settings.dashboard;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.preference.Preference;

import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.PrimarySwitchPreference;
import com.android.settingslib.drawer.DashboardCategory;
import com.android.settingslib.drawer.Tile;
import com.android.settingslib.utils.ThreadUtils;

import com.google.android.setupcompat.util.WizardManagerHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DashboardFeatureProviderImpl implements DashboardFeatureProvider {
    public final CategoryManager mCategoryManager;
    public final Context mContext;
    public final SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    public final PackageManager mPackageManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.dashboard.DashboardFeatureProviderImpl$1, reason: invalid class name */
    public final class AnonymousClass1 extends ContentObserver {
        public final CountDownLatch mCountDownLatch;
        public boolean mUpdateDelegated;
        public Runnable mUpdateRunnable;
        public final /* synthetic */ String val$method;
        public final /* synthetic */ Preference val$pref;
        public final /* synthetic */ Uri val$uri;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Uri uri, String str, Preference preference) {
            super(new Handler(Looper.getMainLooper()));
            this.val$uri = uri;
            this.val$method = str;
            this.val$pref = preference;
            this.mCountDownLatch = new CountDownLatch(1);
            onDataChanged();
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            onDataChanged();
        }

        public final void onDataChanged() {
            String str = this.val$method;
            str.getClass();
            switch (str) {
                case "getDynamicTitle":
                    DashboardFeatureProviderImpl dashboardFeatureProviderImpl =
                            DashboardFeatureProviderImpl.this;
                    Uri uri = this.val$uri;
                    Preference preference = this.val$pref;
                    dashboardFeatureProviderImpl.getClass();
                    ThreadUtils.postOnBackgroundThread(
                            new DashboardFeatureProviderImpl$$ExternalSyntheticLambda6(
                                    dashboardFeatureProviderImpl, uri, preference, this, 2));
                    break;
                case "getDynamicSummary":
                    DashboardFeatureProviderImpl dashboardFeatureProviderImpl2 =
                            DashboardFeatureProviderImpl.this;
                    Uri uri2 = this.val$uri;
                    Preference preference2 = this.val$pref;
                    dashboardFeatureProviderImpl2.getClass();
                    ThreadUtils.postOnBackgroundThread(
                            new DashboardFeatureProviderImpl$$ExternalSyntheticLambda6(
                                    dashboardFeatureProviderImpl2, uri2, preference2, this, 1));
                    break;
                case "isChecked":
                    DashboardFeatureProviderImpl dashboardFeatureProviderImpl3 =
                            DashboardFeatureProviderImpl.this;
                    Uri uri3 = this.val$uri;
                    Preference preference3 = this.val$pref;
                    dashboardFeatureProviderImpl3.getClass();
                    ThreadUtils.postOnBackgroundThread(
                            new DashboardFeatureProviderImpl$$ExternalSyntheticLambda6(
                                    dashboardFeatureProviderImpl3, uri3, this, preference3));
                    break;
            }
        }

        public final synchronized void post(Runnable runnable) {
            try {
                if (this.mUpdateDelegated) {
                    ThreadUtils.postOnMainThread(runnable);
                } else {
                    this.mUpdateRunnable = runnable;
                    this.mCountDownLatch.countDown();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public DashboardFeatureProviderImpl(Context context) {
        this.mContext = context.getApplicationContext();
        this.mCategoryManager = CategoryManager.get(context);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        this.mPackageManager = context.getPackageManager();
    }

    public static void setPreferenceIcon(Preference preference, Tile tile, Icon icon) {
        Drawable loadDrawable = icon.loadDrawable(preference.getContext());
        if (tile.mCategory.contains("com.android.settings.category.ia.account_detail")) {
            preference.setIcon((Drawable) null);
        } else {
            preference.setIcon(loadDrawable);
        }
    }

    public static void setSwitchEnabled(Preference preference, boolean z) {
        if (preference instanceof PrimarySwitchPreference) {
            ((PrimarySwitchPreference) preference).setSwitchEnabled(z);
        } else {
            preference.setEnabled(z);
        }
    }

    public void bindIcon(Preference preference, Tile tile, boolean z) {
        if (tile.mIconResIdOverride != 0) {
            Icon icon = tile.getIcon(preference.getContext());
            if (icon != null) {
                setPreferenceIcon(preference, tile, icon);
                return;
            }
            return;
        }
        Bundle bundle = tile.mMetaData;
        if (bundle != null && bundle.containsKey("com.android.settings.icon_uri")) {
            preference.setIconSpaceReserved(true);
            ThreadUtils.postOnBackgroundThread(
                    new DashboardFeatureProviderImpl$$ExternalSyntheticLambda0(
                            this, tile, preference, z));
        } else {
            Icon icon2 = tile.getIcon(preference.getContext());
            if (icon2 == null) {
                return;
            }
            setPreferenceIcon(preference, tile, icon2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0164  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0190 A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x006d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List bindPreferenceToTileAndGetObservers(
            final androidx.fragment.app.FragmentActivity r17,
            final com.android.settings.dashboard.DashboardFragment r18,
            boolean r19,
            androidx.preference.Preference r20,
            final com.android.settingslib.drawer.Tile r21,
            final java.lang.String r22,
            int r23) {
        /*
            Method dump skipped, instructions count: 404
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.dashboard.DashboardFeatureProviderImpl.bindPreferenceToTileAndGetObservers(androidx.fragment.app.FragmentActivity,"
                    + " com.android.settings.dashboard.DashboardFragment, boolean,"
                    + " androidx.preference.Preference, com.android.settingslib.drawer.Tile,"
                    + " java.lang.String, int):java.util.List");
    }

    public final List getAllCategories() {
        List list;
        CategoryManager categoryManager = this.mCategoryManager;
        Context context = this.mContext;
        synchronized (categoryManager) {
            if (WizardManagerHelper.isUserSetupComplete(context)) {
                synchronized (categoryManager) {
                    categoryManager.tryInitCategories(context, false);
                    list = categoryManager.mCategories;
                }
            } else {
                list = new ArrayList();
            }
        }
        return list;
    }

    public final String getDashboardKeyForTile(Tile tile) {
        if (tile == null) {
            return null;
        }
        Bundle bundle = tile.mMetaData;
        if (bundle != null && bundle.containsKey("com.android.settings.keyhint")) {
            return tile.getKey(this.mContext);
        }
        return "dashboard_tile_pref_" + tile.mIntent.getComponent().getClassName();
    }

    public final DashboardCategory getTilesForCategory(String str) {
        return this.mCategoryManager.getTilesByCategory(this.mContext, str);
    }
}

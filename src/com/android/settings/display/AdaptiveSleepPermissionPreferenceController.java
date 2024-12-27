package com.android.settings.display;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settingslib.widget.BannerMessagePreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AdaptiveSleepPermissionPreferenceController {
    public final Context mContext;
    public final PackageManager mPackageManager;

    @VisibleForTesting BannerMessagePreference mPreference;

    public AdaptiveSleepPermissionPreferenceController(Context context) {
        this.mPackageManager = context.getPackageManager();
        this.mContext = context;
    }

    public final void initializePreference() {
        if (this.mPreference == null) {
            String attentionServicePackageName =
                    this.mContext.getPackageManager().getAttentionServicePackageName();
            final Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setPackage(this.mContext.getPackageName());
            intent.setData(Uri.parse("package:" + attentionServicePackageName));
            BannerMessagePreference bannerMessagePreference =
                    new BannerMessagePreference(this.mContext);
            this.mPreference = bannerMessagePreference;
            bannerMessagePreference.setTitle(R.string.adaptive_sleep_title_no_permission);
            this.mPreference.setSummary(R.string.adaptive_sleep_summary_no_permission);
            this.mPreference.setPositiveButtonText$1(
                    R.string.adaptive_sleep_manage_permission_button);
            this.mPreference.setPositiveButtonOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.display.AdaptiveSleepPermissionPreferenceController$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            AdaptiveSleepPermissionPreferenceController
                                    adaptiveSleepPermissionPreferenceController =
                                            AdaptiveSleepPermissionPreferenceController.this;
                            adaptiveSleepPermissionPreferenceController.mContext.startActivity(
                                    intent);
                        }
                    });
        }
    }
}

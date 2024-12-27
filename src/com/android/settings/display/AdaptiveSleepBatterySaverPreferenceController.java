package com.android.settings.display;

import android.content.Context;
import android.os.PowerManager;
import android.view.View;

import com.android.settings.R;
import com.android.settingslib.widget.BannerMessagePreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AdaptiveSleepBatterySaverPreferenceController {
    public final Context mContext;
    public final PowerManager mPowerManager;
    BannerMessagePreference mPreference;

    public AdaptiveSleepBatterySaverPreferenceController(Context context) {
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
        this.mContext = context;
    }

    public final void initializePreference() {
        if (this.mPreference == null) {
            BannerMessagePreference bannerMessagePreference =
                    new BannerMessagePreference(this.mContext);
            this.mPreference = bannerMessagePreference;
            bannerMessagePreference.setTitle(R.string.ambient_camera_summary_battery_saver_on);
            this.mPreference.setPositiveButtonText$1(R.string.disable_text);
            this.mPreference.setPositiveButtonOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.display.AdaptiveSleepBatterySaverPreferenceController$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            AdaptiveSleepBatterySaverPreferenceController.this.mPowerManager
                                    .setPowerSaveModeEnabled(false);
                        }
                    });
        }
    }

    public boolean isPowerSaveMode() {
        return this.mPowerManager.isPowerSaveMode();
    }
}

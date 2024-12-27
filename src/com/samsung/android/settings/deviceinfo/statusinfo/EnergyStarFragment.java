package com.samsung.android.settings.deviceinfo.statusinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EnergyStarFragment extends SettingsPreferenceFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 40;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.sec_energy_star, (ViewGroup) null);
        View findViewById = inflate.findViewById(R.id.container);
        if (findViewById != null) {
            findViewById.semSetRoundedCorners(15);
            findViewById.semSetRoundedCornerColor(
                    15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
        if (Utils.isTablet()) {
            ImageView imageView = (ImageView) inflate.findViewById(R.id.energy_star_img);
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.height =
                    getResources().getDimensionPixelOffset(R.dimen.energy_star_width_height_tablet);
            imageView.setLayoutParams(layoutParams);
        }
        return inflate;
    }
}

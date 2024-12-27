package com.android.settings.panel;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.SubSettings;
import com.android.settings.slices.CustomSliceRegistry;
import com.android.settings.slices.SliceBuilderUtils;
import com.android.settings.wifi.WifiSettings;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiPanel implements PanelContent {
    public final Context mContext;

    public WifiPanel(FragmentActivity fragmentActivity) {
        this.mContext = fragmentActivity.getApplicationContext();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1687;
    }

    @Override // com.android.settings.panel.PanelContent
    public final Intent getSeeMoreIntent() {
        Intent buildSearchResultPageIntent =
                SliceBuilderUtils.buildSearchResultPageIntent(
                        103,
                        R.string.menu_key_network,
                        this.mContext,
                        WifiSettings.class.getName(),
                        null,
                        this.mContext.getText(R.string.wifi_settings).toString());
        buildSearchResultPageIntent.setClassName(
                this.mContext.getPackageName(), SubSettings.class.getName());
        buildSearchResultPageIntent.addFlags(268435456);
        return buildSearchResultPageIntent;
    }

    @Override // com.android.settings.panel.PanelContent
    public final List getSlices() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(CustomSliceRegistry.WIFI_SLICE_URI);
        return arrayList;
    }

    @Override // com.android.settings.panel.PanelContent
    public final CharSequence getTitle() {
        return this.mContext.getText(R.string.wifi_settings);
    }
}

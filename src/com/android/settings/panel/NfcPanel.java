package com.android.settings.panel;

import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.SubSettings;
import com.android.settings.slices.CustomSliceRegistry;
import com.android.settings.slices.SliceBuilderUtils;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.nfc.NfcSettings;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NfcPanel implements PanelContent {
    public final boolean DBG = Debug.semIsProductDev();
    public final Context mContext;

    public NfcPanel(FragmentActivity fragmentActivity) {
        this.mContext = fragmentActivity.getApplicationContext();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1656;
    }

    @Override // com.android.settings.panel.PanelContent
    public final Intent getSeeMoreIntent() {
        Intent buildSearchResultPageIntent =
                SliceBuilderUtils.buildSearchResultPageIntent(
                        747,
                        R.string.menu_key_connected_devices,
                        this.mContext,
                        NfcSettings.class.getName(),
                        null,
                        this.mContext
                                .getText(
                                        !Rune.isJapanModel()
                                                ? R.string.nfcpayment_quick_toggle_title
                                                : R.string.nfc_quick_toggle_title)
                                .toString());
        buildSearchResultPageIntent.setClassName(
                this.mContext.getPackageName(), SubSettings.class.getName());
        buildSearchResultPageIntent.addFlags(268435456);
        return buildSearchResultPageIntent;
    }

    @Override // com.android.settings.panel.PanelContent
    public final List getSlices() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(CustomSliceRegistry.NFC_SLICE_URI);
        if (this.DBG) {
            Log.d("NfcPanel", "Nfc Panel URI = " + arrayList.toString());
        }
        return arrayList;
    }

    @Override // com.android.settings.panel.PanelContent
    public final CharSequence getTitle() {
        return this.mContext.getText(
                !Rune.isJapanModel()
                        ? R.string.nfcpayment_quick_toggle_title
                        : R.string.nfc_quick_toggle_title);
    }
}

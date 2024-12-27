package com.samsung.android.settings.wifi.details;

import android.view.View;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class WifiDetailPreferenceController$$ExternalSyntheticLambda0
        implements View.OnLongClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiDetailPreferenceController f$0;

    public /* synthetic */ WifiDetailPreferenceController$$ExternalSyntheticLambda0(
            WifiDetailPreferenceController wifiDetailPreferenceController, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiDetailPreferenceController;
    }

    @Override // android.view.View.OnLongClickListener
    public final boolean onLongClick(View view) {
        int i = this.$r8$classId;
        WifiDetailPreferenceController wifiDetailPreferenceController = this.f$0;
        switch (i) {
            case 0:
                wifiDetailPreferenceController.setLongClickListenerOnPasswordButton(view);
                break;
            default:
                wifiDetailPreferenceController.setLongClickListenerOnPasswordButton(view);
                break;
        }
        return true;
    }
}

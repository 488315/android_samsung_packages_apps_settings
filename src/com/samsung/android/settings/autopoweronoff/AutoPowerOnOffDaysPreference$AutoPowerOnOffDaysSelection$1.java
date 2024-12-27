package com.samsung.android.settings.autopoweronoff;

import android.view.View;
import android.widget.CheckBox;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AutoPowerOnOffDaysPreference$AutoPowerOnOffDaysSelection$1
        implements View.OnClickListener {
    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        ((CheckBox) view.findViewById(R.id.day_item_checkbox)).setChecked(!r0.isChecked());
    }
}

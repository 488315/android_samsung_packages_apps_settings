package com.android.settings.bluetooth;

import android.content.Context;
import android.util.AttributeSet;

import com.android.settings.ProgressCategory;
import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BluetoothProgressCategory extends ProgressCategory {
    public BluetoothProgressCategory(Context context) {
        super(context);
        this.mEmptyTextRes = R.string.bluetooth_no_devices_found;
    }

    public BluetoothProgressCategory(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mEmptyTextRes = R.string.bluetooth_no_devices_found;
    }

    public BluetoothProgressCategory(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mEmptyTextRes = R.string.bluetooth_no_devices_found;
    }

    public BluetoothProgressCategory(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mEmptyTextRes = R.string.bluetooth_no_devices_found;
    }
}

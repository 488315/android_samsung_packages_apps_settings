package com.samsung.android.settings.asbase.vibration;

import android.content.Intent;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface IVibSimType {
    Intent getIntent();

    ArrayList getSepIndexDbName();

    ArrayList getSoundType();

    ArrayList getSyncDbName();

    int getVibrationSimCount();
}

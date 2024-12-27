package com.samsung.android.settings.accessibility.shortcut;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class BaseShortcutActivity extends AppCompatActivity {
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        performShortcutOperation();
        finish();
    }

    public abstract void performShortcutOperation();
}

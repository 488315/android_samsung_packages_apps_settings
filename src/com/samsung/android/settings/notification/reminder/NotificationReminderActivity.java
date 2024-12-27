package com.samsung.android.settings.notification.reminder;

import android.content.Intent;

import com.android.settings.SettingsActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NotificationReminderActivity extends SettingsActivity {
    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.appcompat.app.AppCompatActivity
    public final boolean onSupportNavigateUp() {
        Intent intent = getIntent();
        if (intent != null && intent.getBooleanExtra("from_relative_link", false)) {
            startActivity(new Intent("com.samsung.settings.STATUS_BAR_SETTINGS"));
        }
        finish();
        return true;
    }
}

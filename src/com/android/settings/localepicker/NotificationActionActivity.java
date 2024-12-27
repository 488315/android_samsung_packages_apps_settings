package com.android.settings.localepicker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult;
import androidx.appcompat.app.AppCompatActivity;

import com.android.settings.overlay.FeatureFactoryImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NotificationActionActivity extends AppCompatActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityResultRegistry.AnonymousClass2 mStartForResult =
            (ActivityResultRegistry.AnonymousClass2)
                    registerForActivityResult(
                            new ActivityResultContracts$StartActivityForResult(0),
                            new NotificationActionActivity$$ExternalSyntheticLambda0());

    public ActivityResultLauncher getLauncher() {
        return this.mStartForResult;
    }

    public NotificationController getNotificationController(Context context) {
        return NotificationController.getInstance(context);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        int intExtra = intent.getIntExtra("notification_id", -1);
        String stringExtra = intent.getStringExtra("app_locale");
        if (TextUtils.isEmpty(stringExtra) || intExtra == -1) {
            finish();
            return;
        }
        NotificationInfo notificationInfo =
                getNotificationController(this).mDataManager.getNotificationInfo(stringExtra);
        if ((notificationInfo != null ? notificationInfo.mNotificationId : -1) == intExtra) {
            Intent intent2 = new Intent("android.settings.LOCALE_SETTINGS");
            intent2.putExtra("system_locale_dialog_type", "locale_suggestion");
            intent2.putExtra("app_locale", stringExtra);
            intent2.setFlags(603979776);
            getLauncher().launch(intent2);
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            featureFactoryImpl.getMetricsFeatureProvider().action(this, 1896, new Pair[0]);
            finish();
        }
    }
}

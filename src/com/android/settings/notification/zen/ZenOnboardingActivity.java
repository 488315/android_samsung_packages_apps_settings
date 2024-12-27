package com.android.settings.notification.zen;

import android.app.Activity;
import android.app.Flags;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.RadioButton;

import com.android.internal.logging.MetricsLogger;
import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenOnboardingActivity extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    static final long ALWAYS_SHOW_THRESHOLD = 1209600000;
    static final String PREF_KEY_SUGGESTION_FIRST_DISPLAY_TIME =
            "pref_zen_suggestion_first_display_time_ms";
    public View mKeepCurrentSetting;
    public RadioButton mKeepCurrentSettingButton;
    public MetricsLogger mMetrics;
    public View mNewSetting;
    public RadioButton mNewSettingButton;
    public NotificationManager mNm;

    public void launchSettings(View view) {
        this.mMetrics.action(1379);
        Intent intent =
                new Intent("android.settings.ZEN_MODE_SETTINGS").setPackage(getPackageName());
        intent.addFlags(268468224);
        startActivity(intent);
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setNotificationManager((NotificationManager) getSystemService(NotificationManager.class));
        setMetricsLogger(new MetricsLogger());
        Settings.Secure.putInt(
                getApplicationContext().getContentResolver(), "zen_settings_suggestion_viewed", 1);
        setupUI();
    }

    public void save(View view) {
        NotificationManager.Policy notificationPolicy = this.mNm.getNotificationPolicy();
        if (this.mNewSettingButton.isChecked()) {
            NotificationManager.Policy policy =
                    new NotificationManager.Policy(
                            notificationPolicy.priorityCategories | 16,
                            2,
                            notificationPolicy.priorityMessageSenders,
                            NotificationManager.Policy.getAllSuppressedVisualEffects());
            if (Flags.modesApi()) {
                this.mNm.setNotificationPolicy(policy, true);
            } else {
                this.mNm.setNotificationPolicy(policy);
            }
            this.mMetrics.action(1378);
        } else {
            this.mMetrics.action(1406);
        }
        Settings.Secure.putInt(
                getApplicationContext().getContentResolver(), "zen_settings_updated", 1);
        finishAndRemoveTask();
    }

    public void setMetricsLogger(MetricsLogger metricsLogger) {
        this.mMetrics = metricsLogger;
    }

    public void setNotificationManager(NotificationManager notificationManager) {
        this.mNm = notificationManager;
    }

    public void setupUI() {
        setContentView(R.layout.zen_onboarding);
        this.mNewSetting = findViewById(R.id.zen_onboarding_new_setting);
        this.mKeepCurrentSetting = findViewById(R.id.zen_onboarding_current_setting);
        this.mNewSettingButton = (RadioButton) findViewById(R.id.zen_onboarding_new_setting_button);
        this.mKeepCurrentSettingButton =
                (RadioButton) findViewById(R.id.zen_onboarding_current_setting_button);
        final int i = 0;
        View.OnClickListener onClickListener =
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.notification.zen.ZenOnboardingActivity.1
                    public final /* synthetic */ ZenOnboardingActivity this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i) {
                            case 0:
                                this.this$0.mKeepCurrentSettingButton.setChecked(false);
                                this.this$0.mNewSettingButton.setChecked(true);
                                break;
                            default:
                                this.this$0.mKeepCurrentSettingButton.setChecked(true);
                                this.this$0.mNewSettingButton.setChecked(false);
                                break;
                        }
                    }
                };
        final int i2 = 1;
        View.OnClickListener onClickListener2 =
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.notification.zen.ZenOnboardingActivity.1
                    public final /* synthetic */ ZenOnboardingActivity this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i2) {
                            case 0:
                                this.this$0.mKeepCurrentSettingButton.setChecked(false);
                                this.this$0.mNewSettingButton.setChecked(true);
                                break;
                            default:
                                this.this$0.mKeepCurrentSettingButton.setChecked(true);
                                this.this$0.mNewSettingButton.setChecked(false);
                                break;
                        }
                    }
                };
        this.mNewSetting.setOnClickListener(onClickListener);
        this.mNewSettingButton.setOnClickListener(onClickListener);
        this.mKeepCurrentSetting.setOnClickListener(onClickListener2);
        this.mKeepCurrentSettingButton.setOnClickListener(onClickListener2);
        this.mKeepCurrentSettingButton.setChecked(true);
        this.mMetrics.visible(1380);
    }
}

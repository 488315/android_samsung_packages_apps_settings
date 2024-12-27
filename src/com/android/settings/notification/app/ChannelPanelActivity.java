package com.android.settings.notification.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManagerImpl;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.core.lifecycle.HideNonSystemOverlayMixin;

import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ChannelPanelActivity extends FragmentActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Bundle mBundle = new Bundle();
    public NotificationSettings mPanelFragment;

    public final void createOrUpdatePanel() {
        final int i = 1;
        final int i2 = 0;
        Intent intent = getIntent();
        if (intent == null) {
            Log.e("ChannelPanelActivity", "Null intent, closing Panel Activity");
            finish();
            return;
        }
        FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
        setContentView(R.layout.notification_channel_panel);
        Window window = getWindow();
        window.setGravity(80);
        window.setLayout(-1, -2);
        findViewById(R.id.done)
                .setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.notification.app.ChannelPanelActivity$$ExternalSyntheticLambda0
                            public final /* synthetic */ ChannelPanelActivity f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i3 = i2;
                                ChannelPanelActivity channelPanelActivity = this.f$0;
                                switch (i3) {
                                    case 0:
                                        int i4 = ChannelPanelActivity.$r8$clinit;
                                        channelPanelActivity.finish();
                                        break;
                                    default:
                                        int i5 = ChannelPanelActivity.$r8$clinit;
                                        channelPanelActivity.launchFullSettings();
                                        break;
                                }
                            }
                        });
        findViewById(R.id.see_more)
                .setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.notification.app.ChannelPanelActivity$$ExternalSyntheticLambda0
                            public final /* synthetic */ ChannelPanelActivity f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i3 = i;
                                ChannelPanelActivity channelPanelActivity = this.f$0;
                                switch (i3) {
                                    case 0:
                                        int i4 = ChannelPanelActivity.$r8$clinit;
                                        channelPanelActivity.finish();
                                        break;
                                    default:
                                        int i5 = ChannelPanelActivity.$r8$clinit;
                                        channelPanelActivity.launchFullSettings();
                                        break;
                                }
                            }
                        });
        View decorView = getWindow().getDecorView();
        ChannelPanelActivity$$ExternalSyntheticLambda2
                channelPanelActivity$$ExternalSyntheticLambda2 =
                        new ChannelPanelActivity$$ExternalSyntheticLambda2();
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(
                decorView, channelPanelActivity$$ExternalSyntheticLambda2);
        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.Api30Impl.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController != null) {
            windowInsetsController.setAppearanceLightNavigationBars(true ^ Utils.isNightMode(this));
        }
        NotificationSettings conversationNotificationSettings =
                intent.hasExtra("android.provider.extra.CONVERSATION_ID")
                        ? new ConversationNotificationSettings()
                        : new ChannelNotificationSettings();
        this.mPanelFragment = conversationNotificationSettings;
        conversationNotificationSettings.setArguments(new Bundle(this.mBundle));
        supportFragmentManager.getClass();
        BackStackRecord backStackRecord = new BackStackRecord(supportFragmentManager);
        backStackRecord.replace(android.R.id.list_container, this.mPanelFragment, null);
        backStackRecord.commitInternal(false);
    }

    public final void launchFullSettings() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            extras.remove("android.provider.extra.CHANNEL_FILTER_LIST");
        }
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this);
        String name = ChannelNotificationSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mExtras = extras;
        launchRequest.mSourceMetricsCategory = 265;
        startActivity(subSettingLauncher.toIntent());
        finish();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!getIntent().hasExtra("android.provider.extra.CHANNEL_FILTER_LIST")) {
            launchFullSettings();
        }
        getApplicationContext().getTheme().rebase();
        createOrUpdatePanel();
        getLifecycle().addObserver(new HideNonSystemOverlayMixin(this));
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        createOrUpdatePanel();
    }
}

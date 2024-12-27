package com.samsung.android.settings.notification;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.Settings;
import com.android.settings.Utils;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecNotificationEditorPreference extends Preference {
    public final Context mContext;
    public View mLockScreenEditor;
    public View mStatusBarEditor;

    public SecNotificationEditorPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
    }

    public final void newActivityStart$1(
            final View view, PreferenceViewHolder preferenceViewHolder) {
        if (view == preferenceViewHolder.findViewById(R.id.status_bar_editor)) {
            try {
                Intent intent = new Intent();
                intent.setClass(this.mContext, Settings.StatusBarNotificationActivity.class);
                intent.putExtra("extra_from_search", true);
                this.mContext.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Log.d(
                        "SecLockScreenEditorPreference",
                        "ActivityNotFoundException in EditMainActivity");
                e.printStackTrace();
            }
        } else if (view == preferenceViewHolder.findViewById(R.id.lockscreen_editor)) {
            try {
                Intent intent2 = new Intent();
                intent2.setClass(this.mContext, Settings.LockscreenNotificationActivity.class);
                intent2.putExtra("extra_from_search", true);
                this.mContext.startActivity(intent2);
                LoggingHelper.insertEventLogging(4400, "4980W");
            } catch (ActivityNotFoundException e2) {
                Log.d(
                        "SecLockScreenEditorPreference",
                        "ActivityNotFoundException in FaceWidgetSettings");
                e2.printStackTrace();
            }
        }
        view.setEnabled(false);
        view.postDelayed(
                new Runnable() { // from class:
                                 // com.samsung.android.settings.notification.SecNotificationEditorPreference$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        view.setEnabled(true);
                    }
                },
                1000L);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(final PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mStatusBarEditor = preferenceViewHolder.findViewById(R.id.status_bar_editor);
        this.mLockScreenEditor = preferenceViewHolder.findViewById(R.id.lockscreen_editor);
        View findViewById = preferenceViewHolder.findViewById(R.id.lockscreen_editor_view);
        View findViewById2 = preferenceViewHolder.findViewById(R.id.widget_editor_view);
        int intForUser =
                Settings.System.getIntForUser(
                        this.mContext.getContentResolver(), "simple_status_bar", 1, -2);
        if (intForUser == 0) {
            ((ImageView) findViewById)
                    .setImageDrawable(this.mContext.getDrawable(R.drawable.ic_illust_status_dot));
        } else if (intForUser == 1) {
            ((ImageView) findViewById)
                    .setImageDrawable(this.mContext.getDrawable(R.drawable.ic_illust_status_icon));
        } else if (intForUser == 2) {
            ((ImageView) findViewById)
                    .setImageDrawable(this.mContext.getDrawable(R.drawable.ic_illust_status_none));
        }
        int intForUser2 =
                Settings.System.getIntForUser(
                        this.mContext.getContentResolver(),
                        "lockscreen_minimizing_notification",
                        1,
                        -2);
        if (intForUser2 == 0) {
            ((ImageView) findViewById2)
                    .setImageDrawable(this.mContext.getDrawable(R.drawable.ic_illust_lock_card));
        } else if (intForUser2 == 1) {
            ((ImageView) findViewById2)
                    .setImageDrawable(this.mContext.getDrawable(R.drawable.ic_illust_lock_icon));
        } else if (intForUser2 == 2) {
            ((ImageView) findViewById2)
                    .setImageDrawable(this.mContext.getDrawable(R.drawable.ic_illust_lock_status));
        }
        final boolean isMinimalBatteryUseEnabled = Utils.isMinimalBatteryUseEnabled(getContext());
        final boolean isSamsungDexMode = Rune.isSamsungDexMode(this.mContext);
        this.mStatusBarEditor.setAlpha(isMinimalBatteryUseEnabled ? 0.4f : 1.0f);
        this.mLockScreenEditor.setAlpha(
                (isMinimalBatteryUseEnabled || isSamsungDexMode) ? 0.4f : 1.0f);
        final String format =
                String.format(
                        getContext()
                                .getString(
                                        R.string
                                                .sec_lock_settings_menu_disabled_power_saving_mode_on),
                        getContext().getString(R.string.power_saving_mode));
        final String format2 =
                String.format(
                        getContext()
                                .getString(
                                        R.string
                                                .sec_lock_settings_menu_disabled_power_saving_mode_on),
                        getContext().getString(R.string.dex_mode));
        this.mStatusBarEditor.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.notification.SecNotificationEditorPreference$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        SecNotificationEditorPreference secNotificationEditorPreference =
                                SecNotificationEditorPreference.this;
                        boolean z = isMinimalBatteryUseEnabled;
                        String str = format;
                        PreferenceViewHolder preferenceViewHolder2 = preferenceViewHolder;
                        if (z) {
                            Toast.makeText(secNotificationEditorPreference.getContext(), str, 0)
                                    .show();
                            return;
                        }
                        secNotificationEditorPreference.getClass();
                        LoggingHelper.insertEventLogging(36011, "NSTE0039");
                        secNotificationEditorPreference.newActivityStart$1(
                                view, preferenceViewHolder2);
                    }
                });
        this.mLockScreenEditor.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.notification.SecNotificationEditorPreference$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        SecNotificationEditorPreference secNotificationEditorPreference =
                                SecNotificationEditorPreference.this;
                        boolean z = isMinimalBatteryUseEnabled;
                        boolean z2 = isSamsungDexMode;
                        String str = format;
                        String str2 = format2;
                        PreferenceViewHolder preferenceViewHolder2 = preferenceViewHolder;
                        secNotificationEditorPreference.getClass();
                        if (!z && !z2) {
                            LoggingHelper.insertEventLogging(36011, "NSTE0032");
                            secNotificationEditorPreference.newActivityStart$1(
                                    view, preferenceViewHolder2);
                        } else {
                            Context context = secNotificationEditorPreference.getContext();
                            if (!z) {
                                str = str2;
                            }
                            Toast.makeText(context, str, 0).show();
                        }
                    }
                });
        if (KnoxUtils.isApplicationRestricted(
                getContext(), "lock_screen_additional_info", "hide")) {
            this.mLockScreenEditor.setVisibility(8);
        } else if (KnoxUtils.isApplicationRestricted(
                getContext(), "lock_screen_additional_info", "grayout")) {
            this.mLockScreenEditor.setEnabled(false);
            this.mLockScreenEditor.setAlpha(0.4f);
        }
    }
}

package com.samsung.android.settings.lockscreen;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.glance.session.SessionManagerImpl$scope$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecLockScreenEditorPreference extends Preference {
    public final Context mContext;
    public View mLockScreenEditor;
    public View mWidgetEditor;

    public SecLockScreenEditorPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
    }

    public final void newActivityStart(final View view, PreferenceViewHolder preferenceViewHolder) {
        if (view == preferenceViewHolder.findViewById(R.id.lockscreen_editor)) {
            try {
                Intent intent = new Intent();
                intent.setAction("com.samsung.dressroom.intent.action.SHOW_LOCK_SETTINGS");
                intent.addFlags(335544352);
                int i = -1;
                try {
                    i =
                            getContext()
                                    .getContentResolver()
                                    .call(
                                            Uri.parse(
                                                    "content://com.android.systemui.pluginlock.provider"),
                                            "get_wallpaper_index",
                                            (String) null,
                                            (Bundle) null)
                                    .getInt("wallpaper_index", -1);
                } catch (Exception e) {
                    SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m(
                            "getWallPaperIndex() : ", e, "SecLockScreenEditorPreference");
                }
                intent.putExtra("wallpaper_index", i);
                this.mContext.startActivity(intent);
            } catch (ActivityNotFoundException e2) {
                Log.d(
                        "SecLockScreenEditorPreference",
                        "ActivityNotFoundException in EditMainActivity");
                e2.printStackTrace();
            }
        } else if (view == preferenceViewHolder.findViewById(R.id.widget_editor)) {
            try {
                this.mContext.startActivity(
                        new Intent().setAction("com.samsung.settings.FaceWidgetSettings"));
                LoggingHelper.insertEventLogging(4400, "4980W");
            } catch (ActivityNotFoundException e3) {
                Log.d(
                        "SecLockScreenEditorPreference",
                        "ActivityNotFoundException in FaceWidgetSettings");
                e3.printStackTrace();
            }
        }
        view.setEnabled(false);
        view.postDelayed(
                new Runnable() { // from class:
                                 // com.samsung.android.settings.lockscreen.SecLockScreenEditorPreference$$ExternalSyntheticLambda2
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
        this.mLockScreenEditor = preferenceViewHolder.findViewById(R.id.lockscreen_editor);
        this.mWidgetEditor = preferenceViewHolder.findViewById(R.id.widget_editor);
        final boolean isMinimalBatteryUseEnabled = Utils.isMinimalBatteryUseEnabled(getContext());
        final boolean isSamsungDexMode = Rune.isSamsungDexMode(this.mContext);
        this.mLockScreenEditor.setAlpha(isMinimalBatteryUseEnabled ? 0.4f : 1.0f);
        this.mWidgetEditor.setAlpha((isMinimalBatteryUseEnabled || isSamsungDexMode) ? 0.4f : 1.0f);
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
        this.mLockScreenEditor.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.lockscreen.SecLockScreenEditorPreference$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        SecLockScreenEditorPreference secLockScreenEditorPreference =
                                SecLockScreenEditorPreference.this;
                        boolean z = isMinimalBatteryUseEnabled;
                        String str = format;
                        PreferenceViewHolder preferenceViewHolder2 = preferenceViewHolder;
                        if (z) {
                            Toast.makeText(secLockScreenEditorPreference.getContext(), str, 0)
                                    .show();
                        } else {
                            secLockScreenEditorPreference.newActivityStart(
                                    view, preferenceViewHolder2);
                        }
                    }
                });
        this.mWidgetEditor.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.lockscreen.SecLockScreenEditorPreference$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        SecLockScreenEditorPreference secLockScreenEditorPreference =
                                SecLockScreenEditorPreference.this;
                        boolean z = isMinimalBatteryUseEnabled;
                        boolean z2 = isSamsungDexMode;
                        String str = format;
                        String str2 = format2;
                        PreferenceViewHolder preferenceViewHolder2 = preferenceViewHolder;
                        secLockScreenEditorPreference.getClass();
                        if (!z && !z2) {
                            secLockScreenEditorPreference.newActivityStart(
                                    view, preferenceViewHolder2);
                            return;
                        }
                        Context context = secLockScreenEditorPreference.getContext();
                        if (!z) {
                            str = str2;
                        }
                        Toast.makeText(context, str, 0).show();
                    }
                });
        if (KnoxUtils.isApplicationRestricted(
                getContext(), "lock_screen_additional_info", "hide")) {
            this.mWidgetEditor.setVisibility(8);
        } else if (KnoxUtils.isApplicationRestricted(
                getContext(), "lock_screen_additional_info", "grayout")) {
            this.mWidgetEditor.setEnabled(false);
            this.mWidgetEditor.setAlpha(0.4f);
        }
    }
}

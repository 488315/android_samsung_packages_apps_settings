package com.android.settings.display.darkmode;

import android.app.UiModeManager;
import android.content.Context;
import android.os.PowerManager;
import android.util.AttributeSet;
import com.android.settings.R;
import com.android.settingslib.PrimarySwitchPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DarkModePreference extends PrimarySwitchPreference {
    public final DarkModePreference$$ExternalSyntheticLambda0 mCallback;
    public final DarkModeObserver mDarkModeObserver;
    public final TimeFormatter mFormat;
    public final PowerManager mPowerManager;
    public final UiModeManager mUiModeManager;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.settings.display.darkmode.DarkModePreference$$ExternalSyntheticLambda0, java.lang.Runnable] */
    public DarkModePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        DarkModeObserver darkModeObserver = new DarkModeObserver(context);
        this.mDarkModeObserver = darkModeObserver;
        this.mUiModeManager = (UiModeManager) context.getSystemService(UiModeManager.class);
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
        this.mFormat = new TimeFormatter(context);
        ?? r2 = new Runnable() { // from class: com.android.settings.display.darkmode.DarkModePreference$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                String string;
                DarkModePreference darkModePreference = DarkModePreference.this;
                boolean isPowerSaveMode = darkModePreference.mPowerManager.isPowerSaveMode();
                boolean z = (darkModePreference.getContext().getResources().getConfiguration().uiMode & 32) != 0;
                darkModePreference.setSwitchEnabled(!isPowerSaveMode);
                if (isPowerSaveMode) {
                    darkModePreference.setSummary(darkModePreference.getContext().getString(z ? R.string.dark_ui_mode_disabled_summary_dark_theme_on : R.string.dark_ui_mode_disabled_summary_dark_theme_off));
                    return;
                }
                int nightMode = darkModePreference.mUiModeManager.getNightMode();
                if (nightMode == 0) {
                    string = darkModePreference.getContext().getString(z ? R.string.dark_ui_summary_on_auto_mode_auto : R.string.dark_ui_summary_off_auto_mode_auto);
                } else if (nightMode != 3) {
                    string = darkModePreference.getContext().getString(z ? R.string.dark_ui_summary_on_auto_mode_never : R.string.dark_ui_summary_off_auto_mode_never);
                } else if (darkModePreference.mUiModeManager.getNightModeCustomType() == 1) {
                    string = darkModePreference.getContext().getString(z ? R.string.dark_ui_summary_on_auto_mode_custom_bedtime : R.string.dark_ui_summary_off_auto_mode_custom_bedtime);
                } else {
                    string = darkModePreference.getContext().getString(z ? R.string.dark_ui_summary_on_auto_mode_custom : R.string.dark_ui_summary_off_auto_mode_custom, darkModePreference.mFormat.of(z ? darkModePreference.mUiModeManager.getCustomNightModeEnd() : darkModePreference.mUiModeManager.getCustomNightModeStart()));
                }
                darkModePreference.setSummary(string);
            }
        };
        this.mCallback = r2;
        darkModeObserver.subscribe(r2);
    }

    @Override // androidx.preference.Preference
    public final void onAttached() {
        registerDependency();
        this.mDarkModeObserver.subscribe(this.mCallback);
    }

    @Override // androidx.preference.Preference
    public final void onDetached() {
        super.onDetached();
        this.mDarkModeObserver.unsubscribe();
    }
}

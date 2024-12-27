package com.android.settings.development;

import android.app.IGameManagerService;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class GameDefaultFrameRatePreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    public final int mGameDefaultFrameRateValue;
    public final IGameManagerService mGameManagerService;
    public final Injector.AnonymousClass1 mSysProps;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    class Injector {

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.development.GameDefaultFrameRatePreferenceController$Injector$1, reason: invalid class name */
        public final class AnonymousClass1 {}
    }

    public GameDefaultFrameRatePreferenceController(Context context) {
        super(context);
        this.mGameManagerService =
                IGameManagerService.Stub.asInterface(ServiceManager.getService("game"));
        new Injector();
        this.mSysProps = new Injector.AnonymousClass1();
        this.mGameDefaultFrameRateValue =
                SystemProperties.getInt("ro.surface_flinger.game_default_frame_rate_override", 60);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "disable_game_default_frame_rate";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        TwoStatePreference twoStatePreference = (TwoStatePreference) this.mPreference;
        if (twoStatePreference.isChecked()) {
            try {
                this.mGameManagerService.toggleGameDefaultFrameRate(true);
            } catch (RemoteException unused) {
            }
        }
        twoStatePreference.setChecked(false);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        try {
            this.mGameManagerService.toggleGameDefaultFrameRate(!((Boolean) obj).booleanValue());
            updateGameDefaultPreferenceSetting();
        } catch (RemoteException unused) {
        }
        return true;
    }

    public final void updateGameDefaultPreferenceSetting() {
        this.mSysProps.getClass();
        ((TwoStatePreference) this.mPreference)
                .setChecked(
                        SystemProperties.getBoolean(
                                "debug.graphics.game_default_frame_rate.disabled", false));
        this.mPreference.setSummary(
                this.mContext.getString(
                        R.string.disable_game_default_frame_rate_summary,
                        Integer.valueOf(this.mGameDefaultFrameRateValue)));
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        updateGameDefaultPreferenceSetting();
    }

    public GameDefaultFrameRatePreferenceController(
            Context context, IGameManagerService iGameManagerService, Injector injector) {
        super(context);
        this.mGameManagerService = iGameManagerService;
        injector.getClass();
        this.mSysProps = new Injector.AnonymousClass1();
    }
}

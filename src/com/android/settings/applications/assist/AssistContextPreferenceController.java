package com.android.settings.applications.assist;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.internal.app.AssistUtils;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AssistContextPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin,
                Preference.OnPreferenceChangeListener,
                LifecycleObserver,
                OnResume,
                OnPause {
    public final AssistUtils mAssistUtils;
    public Preference mPreference;
    public PreferenceScreen mScreen;
    public final SettingObserver mSettingObserver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingObserver extends AssistSettingObserver {
        public final Uri URI = Settings.Secure.getUriFor("assist_structure_enabled");

        public SettingObserver() {}

        @Override // com.android.settings.applications.assist.AssistSettingObserver
        public final List getSettingUris() {
            return Arrays.asList(this.URI);
        }

        @Override // com.android.settings.applications.assist.AssistSettingObserver
        public final void onSettingChange() {
            AssistContextPreferenceController.this.updatePreference$1();
        }
    }

    public AssistContextPreferenceController(Context context, Lifecycle lifecycle) {
        super(context);
        this.mAssistUtils = new AssistUtils(context);
        this.mSettingObserver = new SettingObserver();
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        this.mScreen = preferenceScreen;
        this.mPreference = preferenceScreen.findPreference("context");
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "context";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mAssistUtils.getAssistComponentForUser(UserHandle.myUserId()) != null;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        SettingObserver settingObserver = this.mSettingObserver;
        settingObserver.getClass();
        contentResolver.unregisterContentObserver(settingObserver);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Settings.Secure.putInt(
                this.mContext.getContentResolver(),
                "assist_structure_enabled",
                ((Boolean) obj).booleanValue() ? 1 : 0);
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        this.mSettingObserver.register(this.mContext.getContentResolver());
        updatePreference$1();
    }

    public final void updatePreference$1() {
        Preference preference = this.mPreference;
        if (preference == null || !(preference instanceof TwoStatePreference)) {
            return;
        }
        if (!isAvailable()) {
            this.mScreen.removePreference(this.mPreference);
        } else if (this.mScreen.findPreference("context") == null) {
            this.mScreen.addPreference(this.mPreference);
            Preference preference2 = this.mPreference;
            if (preference2 != null) {
                preference2.setOnPreferenceChangeListener(this);
            }
        }
        ((TwoStatePreference) this.mPreference)
                .setChecked(
                        Settings.Secure.getInt(
                                        this.mContext.getContentResolver(),
                                        "assist_structure_enabled",
                                        1)
                                != 0);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        updatePreference$1();
    }
}

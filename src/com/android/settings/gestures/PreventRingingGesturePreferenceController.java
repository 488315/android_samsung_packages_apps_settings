package com.android.settings.gestures;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PreventRingingGesturePreferenceController extends AbstractPreferenceController
        implements SelectorWithWidgetPreference.OnClickListener,
                LifecycleObserver,
                OnResume,
                OnPause,
                PreferenceControllerMixin {

    @VisibleForTesting static final String KEY_MUTE = "prevent_ringing_option_mute";

    @VisibleForTesting static final String KEY_VIBRATE = "prevent_ringing_option_vibrate";
    public final Context mContext;

    @VisibleForTesting SelectorWithWidgetPreference mMutePref;

    @VisibleForTesting PreferenceCategory mPreferenceCategory;
    public SettingObserver mSettingObserver;

    @VisibleForTesting SelectorWithWidgetPreference mVibratePref;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingObserver extends ContentObserver {
        public final Uri VOLUME_HUSH_GESTURE;
        public final Preference mPreference;

        public SettingObserver(Preference preference) {
            super(new Handler());
            this.VOLUME_HUSH_GESTURE = Settings.Secure.getUriFor("volume_hush_gesture");
            this.mPreference = preference;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (uri == null || this.VOLUME_HUSH_GESTURE.equals(uri)) {
                PreventRingingGesturePreferenceController.this.updateState(this.mPreference);
            }
        }
    }

    public PreventRingingGesturePreferenceController(Context context, Lifecycle lifecycle) {
        super(context);
        this.mContext = context;
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        if (isAvailable()) {
            PreferenceCategory preferenceCategory =
                    (PreferenceCategory)
                            preferenceScreen.findPreference("gesture_prevent_ringing_category");
            this.mPreferenceCategory = preferenceCategory;
            SelectorWithWidgetPreference selectorWithWidgetPreference =
                    new SelectorWithWidgetPreference(preferenceCategory.getContext());
            selectorWithWidgetPreference.setKey(KEY_VIBRATE);
            selectorWithWidgetPreference.setTitle(R.string.prevent_ringing_option_vibrate);
            selectorWithWidgetPreference.mListener = this;
            this.mPreferenceCategory.addPreference(selectorWithWidgetPreference);
            this.mVibratePref = selectorWithWidgetPreference;
            SelectorWithWidgetPreference selectorWithWidgetPreference2 =
                    new SelectorWithWidgetPreference(this.mPreferenceCategory.getContext());
            selectorWithWidgetPreference2.setKey(KEY_MUTE);
            selectorWithWidgetPreference2.setTitle(R.string.prevent_ringing_option_mute);
            selectorWithWidgetPreference2.mListener = this;
            this.mPreferenceCategory.addPreference(selectorWithWidgetPreference2);
            this.mMutePref = selectorWithWidgetPreference2;
            if (this.mPreferenceCategory != null) {
                this.mSettingObserver = new SettingObserver(this.mPreferenceCategory);
            }
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "gesture_prevent_ringing_category";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mContext
                .getResources()
                .getBoolean(android.R.bool.config_wirelessConsentRequired);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        SettingObserver settingObserver = this.mSettingObserver;
        if (settingObserver != null) {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            settingObserver.getClass();
            contentResolver.unregisterContentObserver(settingObserver);
        }
    }

    @Override // com.android.settingslib.widget.SelectorWithWidgetPreference.OnClickListener
    public final void onRadioButtonClicked(
            SelectorWithWidgetPreference selectorWithWidgetPreference) {
        String key = selectorWithWidgetPreference.getKey();
        key.getClass();
        int i = !key.equals(KEY_MUTE) ? !key.equals(KEY_VIBRATE) ? 0 : 1 : 2;
        if (i
                != Settings.Secure.getInt(
                        this.mContext.getContentResolver(), "volume_hush_gesture", 1)) {
            Settings.Secure.putInt(this.mContext.getContentResolver(), "volume_hush_gesture", i);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        SettingObserver settingObserver = this.mSettingObserver;
        if (settingObserver != null) {
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(
                            settingObserver.VOLUME_HUSH_GESTURE, false, settingObserver);
            this.mSettingObserver.onChange(false, null);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        int i =
                Settings.Secure.getInt(
                        this.mContext.getContentResolver(), "volume_hush_gesture", 1);
        boolean z = i == 1;
        boolean z2 = i == 2;
        SelectorWithWidgetPreference selectorWithWidgetPreference = this.mVibratePref;
        if (selectorWithWidgetPreference != null && selectorWithWidgetPreference.mChecked != z) {
            selectorWithWidgetPreference.setChecked(z);
        }
        SelectorWithWidgetPreference selectorWithWidgetPreference2 = this.mMutePref;
        if (selectorWithWidgetPreference2 != null && selectorWithWidgetPreference2.mChecked != z2) {
            selectorWithWidgetPreference2.setChecked(z2);
        }
        if (i == 0) {
            this.mVibratePref.setEnabled(false);
            this.mMutePref.setEnabled(false);
        } else {
            this.mVibratePref.setEnabled(true);
            this.mMutePref.setEnabled(true);
        }
    }
}

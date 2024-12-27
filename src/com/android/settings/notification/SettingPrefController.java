package com.android.settings.notification;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.DropDownPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SettingPrefController extends AbstractPreferenceController
        implements PreferenceControllerMixin, LifecycleObserver, OnResume, OnPause {
    public final SettingsPreferenceFragment mParent;
    public SettingPref mPreference;
    public SettingsObserver mSettingsObserver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    final class SettingsObserver extends ContentObserver {
        public SettingsObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (SettingPrefController.this.mPreference.mUri.equals(uri)) {
                SettingPrefController settingPrefController = SettingPrefController.this;
                settingPrefController.mPreference.update(
                        ((AbstractPreferenceController) settingPrefController).mContext);
            }
        }
    }

    public SettingPrefController(
            Context context,
            SettingsPreferenceFragment settingsPreferenceFragment,
            Lifecycle lifecycle) {
        super(context);
        this.mParent = settingsPreferenceFragment;
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        SettingPref settingPref = this.mPreference;
        settingPref.getClass();
        SettingsPreferenceFragment settingsPreferenceFragment = this.mParent;
        FragmentActivity activity = settingsPreferenceFragment.getActivity();
        Preference findPreference =
                settingsPreferenceFragment.getPreferenceScreen().findPreference(settingPref.mKey);
        if (findPreference != null && !settingPref.isApplicable(activity)) {
            settingsPreferenceFragment.getPreferenceScreen().removePreference(findPreference);
            findPreference = null;
        }
        if (findPreference instanceof TwoStatePreference) {
            settingPref.mTwoState = (TwoStatePreference) findPreference;
        } else if (findPreference instanceof DropDownPreference) {
            settingPref.mDropDown = (DropDownPreference) findPreference;
            int[] iArr = settingPref.mValues;
            CharSequence[] charSequenceArr = new CharSequence[iArr.length];
            CharSequence[] charSequenceArr2 = new CharSequence[iArr.length];
            for (int i = 0; i < iArr.length; i++) {
                charSequenceArr[i] = settingPref.getCaption(activity.getResources(), iArr[i]);
                charSequenceArr2[i] = Integer.toString(iArr[i]);
            }
            settingPref.mDropDown.setEntries(charSequenceArr);
            settingPref.mDropDown.mEntryValues = charSequenceArr2;
        }
        settingPref.update(activity);
        if (settingPref.mTwoState != null) {
            findPreference.setOnPreferenceChangeListener(
                    new Preference
                            .OnPreferenceChangeListener() { // from class:
                                                            // com.android.settings.notification.SettingPref.1
                        public final /* synthetic */ int $r8$classId;
                        public final /* synthetic */ SettingPref this$0;
                        public final /* synthetic */ Context val$context;

                        public /* synthetic */ AnonymousClass1(
                                SettingPref settingPref2, FragmentActivity activity2, int i2) {
                            r3 = i2;
                            r1 = settingPref2;
                            r2 = activity2;
                        }

                        @Override // androidx.preference.Preference.OnPreferenceChangeListener
                        public final boolean onPreferenceChange(Preference preference, Object obj) {
                            switch (r3) {
                                case 0:
                                    r1.setSetting(r2, ((Boolean) obj).booleanValue() ? 1 : 0);
                                    return true;
                                default:
                                    return r1.setSetting(r2, Integer.parseInt((String) obj));
                            }
                        }
                    });
        } else if (settingPref2.mDropDown != null) {
            findPreference.setOnPreferenceChangeListener(
                    new Preference
                            .OnPreferenceChangeListener() { // from class:
                                                            // com.android.settings.notification.SettingPref.1
                        public final /* synthetic */ int $r8$classId;
                        public final /* synthetic */ SettingPref this$0;
                        public final /* synthetic */ Context val$context;

                        public /* synthetic */ AnonymousClass1(
                                SettingPref settingPref2, FragmentActivity activity2, int i2) {
                            r3 = i2;
                            r1 = settingPref2;
                            r2 = activity2;
                        }

                        @Override // androidx.preference.Preference.OnPreferenceChangeListener
                        public final boolean onPreferenceChange(Preference preference, Object obj) {
                            switch (r3) {
                                case 0:
                                    r1.setSetting(r2, ((Boolean) obj).booleanValue() ? 1 : 0);
                                    return true;
                                default:
                                    return r1.setSetting(r2, Integer.parseInt((String) obj));
                            }
                        }
                    });
        }
        super.displayPreference(preferenceScreen);
        if (isAvailable()) {
            this.mSettingsObserver = new SettingsObserver();
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return this.mPreference.mKey;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public boolean isAvailable() {
        return this.mPreference.isApplicable(this.mContext);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        SettingsObserver settingsObserver = this.mSettingsObserver;
        if (settingsObserver != null) {
            SettingPrefController.this
                    .mContext
                    .getContentResolver()
                    .unregisterContentObserver(settingsObserver);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        SettingsObserver settingsObserver = this.mSettingsObserver;
        if (settingsObserver != null) {
            SettingPrefController.this
                    .mContext
                    .getContentResolver()
                    .registerContentObserver(
                            SettingPrefController.this.mPreference.mUri, false, settingsObserver);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        this.mPreference.update(this.mContext);
    }
}

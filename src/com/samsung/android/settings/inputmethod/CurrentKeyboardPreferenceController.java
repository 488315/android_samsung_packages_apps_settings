package com.samsung.android.settings.inputmethod;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.inputmethod.InputMethodAndSubtypeUtil;
import com.android.settingslib.inputmethod.InputMethodSettingValuesWrapper;

import com.samsung.android.settings.Rune;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class CurrentKeyboardPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin, LifecycleObserver, OnStart, OnStop, OnDestroy {
    public InputMethodSettingValuesWrapper mInputMethodSettingValues;
    public SecPreference mPreference;
    public final SettingsObserver mSettingsObserver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsObserver extends ContentObserver {
        public final Context mContext;

        public SettingsObserver(Handler handler, Context context) {
            super(handler);
            this.mContext = context;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            CurrentKeyboardPreferenceController.this.updateCurrentImeName();
        }
    }

    public CurrentKeyboardPreferenceController(Context context, Lifecycle lifecycle) {
        super(context);
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
        this.mInputMethodSettingValues =
                InputMethodSettingValuesWrapper.getInstance(this.mContext.getApplicationContext());
        this.mSettingsObserver = new SettingsObserver(new Handler(Looper.getMainLooper()), context);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecPreference secPreference =
                (SecPreference) preferenceScreen.findPreference("current_input_method");
        this.mPreference = secPreference;
        if (secPreference != null) {
            InputMethodAndSubtypeUtil.removeUnnecessaryNonPersistentPreference(secPreference);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "current_input_method";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), "current_input_method")) {
            return false;
        }
        ((InputMethodManager) this.mContext.getSystemService("input_method"))
                .showInputMethodPickerFromSystem(false, this.mContext.getDisplayId());
        return false;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public final void onDestroy() {
        this.mInputMethodSettingValues = null;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public final void onStart() {
        SettingsObserver settingsObserver = this.mSettingsObserver;
        ContentResolver contentResolver = settingsObserver.mContext.getContentResolver();
        contentResolver.registerContentObserver(
                Settings.Secure.getUriFor("default_input_method"), false, settingsObserver);
        contentResolver.registerContentObserver(
                Settings.Secure.getUriFor("selected_input_method_subtype"),
                false,
                settingsObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public final void onStop() {
        SettingsObserver settingsObserver = this.mSettingsObserver;
        settingsObserver.mContext.getContentResolver().unregisterContentObserver(settingsObserver);
    }

    public final void updateCurrentImeName() {
        CharSequence charSequence;
        Context context = this.mContext;
        if (context == null) {
            return;
        }
        InputMethodSettingValuesWrapper inputMethodSettingValuesWrapper =
                this.mInputMethodSettingValues;
        inputMethodSettingValuesWrapper.getClass();
        String string =
                Settings.Secure.getString(context.getContentResolver(), "default_input_method");
        Iterator it = new ArrayList(inputMethodSettingValuesWrapper.mMethodList).iterator();
        while (true) {
            if (!it.hasNext()) {
                charSequence = null;
                break;
            }
            InputMethodInfo inputMethodInfo = (InputMethodInfo) it.next();
            if (TextUtils.equals(inputMethodInfo.getId(), string)) {
                charSequence = inputMethodInfo.loadLabel(context.getPackageManager());
                break;
            }
        }
        SecPreference secPreference = this.mPreference;
        secPreference.getClass();
        SecPreferenceUtils.applySummaryColor(secPreference, true);
        if (TextUtils.isEmpty(charSequence)) {
            return;
        }
        synchronized (this) {
            this.mPreference.setSummary(charSequence);
        }
    }

    @Override // com.android.settings.core.PreferenceControllerMixin
    public final void updateNonIndexableKeys(List list) {
        if (Rune.isSamsungDexMode(this.mContext)) {
            list.add("current_input_method");
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        if (this.mPreference == null) {
            return;
        }
        updateCurrentImeName();
        this.mPreference.setEnabled(!Rune.isSamsungDexMode(this.mContext));
    }
}

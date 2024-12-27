package com.android.settings.inputmethod;

import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.icu.text.ListFormatter;
import android.os.UserHandle;

import androidx.preference.Preference;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.knox.SemPersonaManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PhysicalKeyboardPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin,
                LifecycleObserver,
                OnResume,
                OnPause,
                InputManager.InputDeviceListener {
    public final InputManager mIm;
    public Preference mPreference;

    public PhysicalKeyboardPreferenceController(Context context, Lifecycle lifecycle) {
        super(context);
        this.mIm = (InputManager) context.getSystemService("input");
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    @VisibleForTesting
    public List<PhysicalKeyboardFragment.HardKeyboardDeviceInfo> getKeyboards() {
        return PhysicalKeyboardFragment.getHardKeyboards(this.mContext);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "physical_keyboard_pref";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        if (!"physical_keyboard_pref".equals(preference.getKey())) {
            return false;
        }
        Intent intent = new Intent("android.settings.HARD_KEYBOARD_SETTINGS");
        intent.setPackage(this.mContext.getPackageName());
        intent.putExtra("com.android.settings.inputmethod.EXTRA_ENTRYPOINT", 1);
        this.mContext.startActivity(intent);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (SemPersonaManager.isSecureFolderId(UserHandle.myUserId())) {
            return false;
        }
        return this.mContext.getResources().getBoolean(R.bool.config_show_physical_keyboard_pref);
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceAdded(int i) {
        updateEntry();
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceChanged(int i) {
        updateEntry();
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceRemoved(int i) {
        updateEntry();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        this.mIm.unregisterInputDeviceListener(this);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        this.mIm.registerInputDeviceListener(this, null);
    }

    public final void updateEntry() {
        if (this.mPreference == null) {
            return;
        }
        List<PhysicalKeyboardFragment.HardKeyboardDeviceInfo> keyboards = getKeyboards();
        if (keyboards.isEmpty()) {
            this.mPreference.setSummary(R.string.keyboard_disconnected);
            return;
        }
        this.mPreference.setVisible(true);
        ArrayList arrayList = new ArrayList();
        Iterator<PhysicalKeyboardFragment.HardKeyboardDeviceInfo> it = keyboards.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().mDeviceName);
        }
        this.mPreference.setSummary(ListFormatter.getInstance().format(arrayList));
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        this.mPreference = preference;
        updateEntry();
    }
}

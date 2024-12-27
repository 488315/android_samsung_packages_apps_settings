package com.samsung.android.settings.bluetooth.bluetoothcast;

import android.R;
import android.os.Debug;
import android.os.SystemProperties;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.Utils;

import com.samsung.android.settings.bluetooth.BluetoothListController;
import com.samsung.android.settings.bluetooth.BluetoothScanDialog;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.AudioCastProfile;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.CachedBluetoothCastDevice;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BluetoothCastDevicePreference extends Preference
        implements CachedBluetoothCastDevice.Callback, View.OnClickListener {
    public static int sDimAlpha;
    public final String TAG;
    public final CachedBluetoothCastDevice mCachedCastDevice;
    public final BluetoothListController mListController;

    static {
        Debug.semIsProductDev();
        sDimAlpha = Integer.MIN_VALUE;
    }

    public BluetoothCastDevicePreference(
            FragmentActivity fragmentActivity,
            CachedBluetoothCastDevice cachedBluetoothCastDevice) {
        super(fragmentActivity);
        this.TAG = "BluetoothCastDevicePreference";
        this.mCachedCastDevice = cachedBluetoothCastDevice;
        boolean z = getContext() instanceof BluetoothScanDialog;
        if (sDimAlpha == Integer.MIN_VALUE) {
            TypedValue typedValue = new TypedValue();
            fragmentActivity.getTheme().resolveAttribute(R.attr.disabledAlpha, typedValue, true);
            sDimAlpha = (int) (typedValue.getFloat() * 255.0f);
        }
        synchronized (cachedBluetoothCastDevice.mCallbacks) {
            ((ArrayList) cachedBluetoothCastDevice.mCallbacks).add(this);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == null || !(obj instanceof BluetoothCastDevicePreference)) {
            return false;
        }
        return this.mCachedCastDevice.equals(
                ((BluetoothCastDevicePreference) obj).mCachedCastDevice);
    }

    public final int hashCode() {
        return this.mCachedCastDevice.hashCode();
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        if (findPreferenceInHierarchy("bt_checkbox") != null) {
            setDependency("bt_checkbox");
        }
        super.onBindViewHolder(preferenceViewHolder);
        if (Utils.isTablet() && SystemProperties.get("ro.build.scafe.size").equals("tall")) {
            ((TextView) preferenceViewHolder.findViewById(R.id.title))
                    .setTextSize(
                            getContext()
                                    .getResources()
                                    .getInteger(
                                            com.android.settings.R.integer
                                                    .bluetooth_preference_text_size_tablet_tall));
        }
        CachedBluetoothCastDevice cachedBluetoothCastDevice = this.mCachedCastDevice;
        Iterator it = cachedBluetoothCastDevice.mCastProfiles.iterator();
        while (it.hasNext()) {
            if (cachedBluetoothCastDevice.getCastProfileConnectionState(
                            (AudioCastProfile) it.next())
                    == 2) {
                ((TextView) preferenceViewHolder.findViewById(R.id.title))
                        .setTextAppearance(
                                getContext(),
                                com.android.settings.R.style.BluetoothDeviceConnectedHighlight);
                return;
            }
        }
    }

    @Override // androidx.preference.Preference
    public final void onPrepareForRemoval() {
        unregisterDependency();
    }

    public final void reset$1() {
        CachedBluetoothCastDevice cachedBluetoothCastDevice = this.mCachedCastDevice;
        synchronized (cachedBluetoothCastDevice.mCallbacks) {
            ((ArrayList) cachedBluetoothCastDevice.mCallbacks).remove(this);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.preference.Preference, java.lang.Comparable
    public final int compareTo(Preference preference) {
        return !(preference instanceof BluetoothCastDevicePreference)
                ? super.compareTo(preference)
                : this.mCachedCastDevice.compareTo(
                        ((BluetoothCastDevicePreference) preference).mCachedCastDevice);
    }

    public BluetoothCastDevicePreference(
            FragmentActivity fragmentActivity,
            CachedBluetoothCastDevice cachedBluetoothCastDevice,
            BluetoothListController bluetoothListController) {
        super(fragmentActivity);
        this.TAG = "BluetoothCastDevicePreference";
        this.mListController = bluetoothListController;
        this.mCachedCastDevice = cachedBluetoothCastDevice;
        boolean z = getContext() instanceof BluetoothScanDialog;
        if (sDimAlpha == Integer.MIN_VALUE) {
            TypedValue typedValue = new TypedValue();
            fragmentActivity.getTheme().resolveAttribute(R.attr.disabledAlpha, typedValue, true);
            sDimAlpha = (int) (typedValue.getFloat() * 255.0f);
        }
        synchronized (cachedBluetoothCastDevice.mCallbacks) {
            ((ArrayList) cachedBluetoothCastDevice.mCallbacks).add(this);
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {}
}

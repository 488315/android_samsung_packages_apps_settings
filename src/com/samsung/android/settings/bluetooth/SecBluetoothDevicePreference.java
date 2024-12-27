package com.samsung.android.settings.bluetooth;

import android.R;
import android.os.Debug;
import android.os.SystemProperties;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.Utils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecBluetoothDevicePreference extends Preference
        implements CachedBluetoothDevice.SemCallback, View.OnClickListener {
    public static boolean mClickable;
    public static int sDimAlpha;
    public final CachedBluetoothDevice mCachedDevice;
    public final BluetoothListController mListController;
    public View.OnClickListener mOnSettingsClickListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.SecBluetoothDevicePreference$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        @Override // java.lang.Runnable
        public final void run() {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                Log.d("SecBluetoothDevicePreference", "InterruptedException" + e);
                Thread.currentThread().interrupt();
            }
            Log.e("SecBluetoothDevicePreference", "mClickable to true");
            SecBluetoothDevicePreference.mClickable = true;
        }
    }

    static {
        Debug.semIsProductDev();
        sDimAlpha = Integer.MIN_VALUE;
        mClickable = true;
    }

    public SecBluetoothDevicePreference(
            FragmentActivity fragmentActivity,
            CachedBluetoothDevice cachedBluetoothDevice,
            BluetoothListController bluetoothListController) {
        super(fragmentActivity);
        this.mListController = bluetoothListController;
        this.mCachedDevice = cachedBluetoothDevice;
        boolean z = getContext() instanceof BluetoothScanDialog;
        synchronized (cachedBluetoothDevice.mSemCallbacks) {
            ((ArrayList) cachedBluetoothDevice.mSemCallbacks).add(this);
        }
        if (sDimAlpha == Integer.MIN_VALUE) {
            TypedValue typedValue = new TypedValue();
            fragmentActivity.getTheme().resolveAttribute(R.attr.disabledAlpha, typedValue, true);
            sDimAlpha = (int) (typedValue.getFloat() * 255.0f);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == null || !(obj instanceof SecBluetoothDevicePreference)) {
            return false;
        }
        return this.mCachedDevice.equals(((SecBluetoothDevicePreference) obj).mCachedDevice);
    }

    public final String getName() {
        String str = "\u200e" + Html.escapeHtml(this.mCachedDevice.getName()) + "\u200e";
        String str2 = this.mCachedDevice.mPrefixName;
        if (str2 == null) {
            str2 = ApnSettings.MVNO_NONE;
        }
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, str);
    }

    public final int hashCode() {
        return this.mCachedDevice.hashCode();
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
        if (this.mCachedDevice.isConnected()) {
            ((TextView) preferenceViewHolder.findViewById(R.id.title))
                    .setTextAppearance(
                            getContext(),
                            com.android.settings.R.style.BluetoothDeviceConnectedHighlight);
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        View.OnClickListener onClickListener = this.mOnSettingsClickListener;
        if (onClickListener == null || !mClickable) {
            return;
        }
        mClickable = false;
        onClickListener.onClick(view);
        new Thread(new AnonymousClass1()).start();
    }

    @Override // androidx.preference.Preference
    public final void onPrepareForRemoval() {
        unregisterDependency();
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        synchronized (cachedBluetoothDevice.mSemCallbacks) {
            ((ArrayList) cachedBluetoothDevice.mSemCallbacks).remove(this);
        }
        this.mOnSettingsClickListener = null;
    }

    public final void semOnDeviceAttributesChanged() {
        StringBuilder sb = new StringBuilder("onDeviceAttributesChanged: Device = ");
        sb.append(this.mCachedDevice.getNameForLog());
        sb.append(", isBonded = ");
        TooltipPopup$$ExternalSyntheticOutline0.m(
                sb, this.mCachedDevice.mBondState, "SecBluetoothDevicePreference");
        this.mListController.getSelectedAdapter(this).setNeedUpdatePreference();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.preference.Preference, java.lang.Comparable
    public final int compareTo(Preference preference) {
        return !(preference instanceof SecBluetoothDevicePreference)
                ? super.compareTo(preference)
                : this.mCachedDevice.compareTo(
                        ((SecBluetoothDevicePreference) preference).mCachedDevice);
    }
}

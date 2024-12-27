package com.android.settings.bluetooth;

import android.R;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.UserManager;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.GearPreference;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.utils.ThreadUtils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothDevicePreference extends GearPreference {
    public static int sDimAlpha = Integer.MIN_VALUE;
    public static final AtomicInteger sNextId = new AtomicInteger();
    public String contentDescription;
    BluetoothAdapter mBluetoothAdapter;
    public Set mBluetoothDevices;
    public final CachedBluetoothDevice mCachedDevice;
    public final BluetoothDevicePreferenceCallback mCallback;
    public AlertDialog mDisconnectDialog;
    public boolean mHideSecondTarget;
    public final int mId;
    public boolean mIsCallbackRemoved;
    final BluetoothAdapter.OnMetadataChangedListener mMetadataListener;
    boolean mNeedNotifyHierarchyChanged;
    public final boolean mShowDevicesWithoutNames;
    public final int mType;
    public final UserManager mUserManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class BluetoothDevicePreferenceCallback implements CachedBluetoothDevice.Callback {
        public BluetoothDevicePreferenceCallback() {}

        @Override // com.android.settingslib.bluetooth.CachedBluetoothDevice.Callback
        public final void onDeviceAttributesChanged() {
            BluetoothDevicePreference.this.onPreferenceAttributesChanged();
        }
    }

    public BluetoothDevicePreference(
            Context context, CachedBluetoothDevice cachedBluetoothDevice, boolean z) {
        super(context, null);
        this.contentDescription = null;
        this.mHideSecondTarget = false;
        this.mIsCallbackRemoved = true;
        this.mNeedNotifyHierarchyChanged = false;
        this.mMetadataListener =
                new BluetoothAdapter
                        .OnMetadataChangedListener() { // from class:
                                                       // com.android.settings.bluetooth.BluetoothDevicePreference.1
                    public final void onMetadataChanged(
                            BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
                        Log.d(
                                "BluetoothDevicePref",
                                String.format(
                                        "Metadata updated in Device %s: %d = %s.",
                                        bluetoothDevice.getAnonymizedAddress(),
                                        Integer.valueOf(i),
                                        bArr == null ? null : new String(bArr)));
                        BluetoothDevicePreference.this.onPreferenceAttributesChanged();
                    }
                };
        getContext().getResources();
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mShowDevicesWithoutNames = z;
        if (sDimAlpha == Integer.MIN_VALUE) {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.disabledAlpha, typedValue, true);
            sDimAlpha = (int) (typedValue.getFloat() * 255.0f);
        }
        this.mCachedDevice = cachedBluetoothDevice;
        this.mCallback = new BluetoothDevicePreferenceCallback();
        this.mId = sNextId.getAndIncrement();
        this.mType = 2;
        setVisible(false);
        onPreferenceAttributesChanged();
    }

    public final boolean equals(Object obj) {
        if (obj == null || !(obj instanceof BluetoothDevicePreference)) {
            return false;
        }
        return this.mCachedDevice.equals(((BluetoothDevicePreference) obj).mCachedDevice);
    }

    @Override // com.android.settings.widget.GearPreference,
              // com.android.settingslib.widget.TwoTargetPreference
    public final int getSecondTargetResId() {
        return com.android.settings.R.layout.preference_widget_gear;
    }

    public final int hashCode() {
        return this.mCachedDevice.hashCode();
    }

    @Override // androidx.preference.Preference
    public final void onAttached() {
        registerDependency();
        if (this.mIsCallbackRemoved) {
            this.mCachedDevice.registerCallback(this.mCallback);
            if (this.mBluetoothAdapter == null) {
                Log.d("BluetoothDevicePref", "No mBluetoothAdapter");
            } else {
                if (this.mBluetoothDevices == null) {
                    this.mBluetoothDevices = new HashSet();
                }
                ((HashSet) this.mBluetoothDevices).clear();
                BluetoothDevice bluetoothDevice = this.mCachedDevice.mDevice;
                if (bluetoothDevice != null) {
                    ((HashSet) this.mBluetoothDevices).add(bluetoothDevice);
                }
                Iterator it = this.mCachedDevice.mMemberDevices.iterator();
                while (it.hasNext()) {
                    ((HashSet) this.mBluetoothDevices)
                            .add(((CachedBluetoothDevice) it.next()).mDevice);
                }
                if (((HashSet) this.mBluetoothDevices).isEmpty()) {
                    Log.d("BluetoothDevicePref", "No BT device to register.");
                } else {
                    final HashSet hashSet = new HashSet();
                    this.mBluetoothDevices.forEach(
                            new Consumer() { // from class:
                                             // com.android.settings.bluetooth.BluetoothDevicePreference$$ExternalSyntheticLambda2
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    BluetoothDevicePreference bluetoothDevicePreference =
                                            BluetoothDevicePreference.this;
                                    Set set = hashSet;
                                    BluetoothDevice bluetoothDevice2 = (BluetoothDevice) obj;
                                    int i = BluetoothDevicePreference.sDimAlpha;
                                    bluetoothDevicePreference.getClass();
                                    try {
                                        if (bluetoothDevicePreference.mBluetoothAdapter
                                                .addOnMetadataChangedListener(
                                                        bluetoothDevice2,
                                                        bluetoothDevicePreference
                                                                .getContext()
                                                                .getMainExecutor(),
                                                        bluetoothDevicePreference
                                                                .mMetadataListener)) {
                                            return;
                                        }
                                        Log.e(
                                                "BluetoothDevicePref",
                                                bluetoothDevice2.getAnonymizedAddress()
                                                        + ": add into Listener failed");
                                        set.add(bluetoothDevice2);
                                    } catch (IllegalArgumentException e) {
                                        set.add(bluetoothDevice2);
                                        Log.e(
                                                "BluetoothDevicePref",
                                                bluetoothDevice2.getAnonymizedAddress()
                                                        + ":"
                                                        + e.toString());
                                    } catch (NullPointerException e2) {
                                        set.add(bluetoothDevice2);
                                        Log.e(
                                                "BluetoothDevicePref",
                                                bluetoothDevice2.getAnonymizedAddress()
                                                        + ":"
                                                        + e2.toString());
                                    }
                                }
                            });
                    Iterator it2 = hashSet.iterator();
                    while (it2.hasNext()) {
                        BluetoothDevice bluetoothDevice2 = (BluetoothDevice) it2.next();
                        ((HashSet) this.mBluetoothDevices).remove(bluetoothDevice2);
                        Log.d(
                                "BluetoothDevicePref",
                                "mBluetoothDevices remove "
                                        + bluetoothDevice2.getAnonymizedAddress());
                    }
                }
            }
            this.mIsCallbackRemoved = false;
        }
        onPreferenceAttributesChanged();
    }

    @Override // com.android.settings.widget.GearPreference,
              // com.android.settingslib.RestrictedPreference,
              // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        ImageView imageView;
        if (findPreferenceInHierarchy("bt_checkbox") != null) {
            setDependency("bt_checkbox");
        }
        if (this.mCachedDevice.mBondState == 12
                && (imageView =
                                (ImageView)
                                        preferenceViewHolder.findViewById(
                                                com.android.settings.R.id.settings_button))
                        != null) {
            imageView.setOnClickListener(this);
        }
        ImageView imageView2 = (ImageView) preferenceViewHolder.findViewById(R.id.icon);
        if (imageView2 != null) {
            imageView2.setContentDescription(this.contentDescription);
            imageView2.setImportantForAccessibility(2);
            imageView2.setElevation(
                    getContext()
                            .getResources()
                            .getDimension(com.android.settings.R.dimen.bt_icon_elevation));
        }
        super.onBindViewHolder(preferenceViewHolder);
    }

    public final void onClicked() {
        Context context = getContext();
        int i = this.mCachedDevice.mBondState;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        SettingsMetricsFeatureProvider metricsFeatureProvider =
                featureFactoryImpl.getMetricsFeatureProvider();
        if (!this.mCachedDevice.isConnected()) {
            if (i == 12) {
                metricsFeatureProvider.action(context, 867, new Pair[0]);
                this.mCachedDevice.connect$1();
                return;
            }
            if (i == 10) {
                metricsFeatureProvider.action(context, 866, new Pair[0]);
                if (!(!TextUtils.isEmpty(this.mCachedDevice.mDevice.getAlias()))) {
                    metricsFeatureProvider.action(context, 1096, new Pair[0]);
                }
                if (this.mCachedDevice.startPairing()) {
                    return;
                }
                Context context2 = getContext();
                String name = this.mCachedDevice.getName();
                boolean z = Utils.DEBUG;
                Toast.makeText(
                                new ContextThemeWrapper(context2, 2132084353),
                                context2.getString(
                                        com.android.settings.R.string
                                                .bluetooth_pairing_error_message,
                                        ComposerKt$$ExternalSyntheticOutline0.m(
                                                "\u200e", name, "\u200e")),
                                0)
                        .show();
                return;
            }
            return;
        }
        metricsFeatureProvider.action(context, 868, new Pair[0]);
        Context context3 = getContext();
        String name2 = this.mCachedDevice.getName();
        if (TextUtils.isEmpty(name2)) {
            name2 = context3.getString(com.android.settings.R.string.bluetooth_device);
        }
        String string =
                context3.getString(
                        com.android.settings.R.string.bluetooth_disconnect_all_profiles, name2);
        String string2 =
                context3.getString(com.android.settings.R.string.bluetooth_disconnect_title);
        DialogInterface.OnClickListener onClickListener =
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.bluetooth.BluetoothDevicePreference.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        BluetoothDevicePreference.this.mCachedDevice.disconnect();
                    }
                };
        AlertDialog alertDialog = this.mDisconnectDialog;
        Spanned fromHtml = Html.fromHtml(string);
        boolean z2 = Utils.DEBUG;
        if (alertDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context3);
            builder.setPositiveButton(R.string.ok, onClickListener);
            builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
            alertDialog = builder.create();
        } else {
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
            alertDialog.setButton(-1, context3.getText(R.string.ok), onClickListener);
        }
        alertDialog.setTitle(string2);
        alertDialog.setMessage(fromHtml);
        alertDialog.show();
        this.mDisconnectDialog = alertDialog;
    }

    @Override // androidx.preference.Preference
    public final void onDetached() {
        super.onDetached();
        if (this.mIsCallbackRemoved) {
            return;
        }
        this.mCachedDevice.unregisterCallback(this.mCallback);
        unregisterMetadataChangedListener();
        this.mIsCallbackRemoved = true;
    }

    public final void onPreferenceAttributesChanged() {
        try {
            ThreadUtils.postOnBackgroundThread(
                    new Runnable() { // from class:
                                     // com.android.settings.bluetooth.BluetoothDevicePreference$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            final BluetoothDevicePreference bluetoothDevicePreference =
                                    BluetoothDevicePreference.this;
                            final String name = bluetoothDevicePreference.mCachedDevice.getName();
                            CachedBluetoothDevice cachedBluetoothDevice =
                                    bluetoothDevicePreference.mCachedDevice;
                            final String connectionSummary =
                                    cachedBluetoothDevice.mBondState != 10
                                            ? cachedBluetoothDevice.getConnectionSummary()
                                            : null;
                            final Pair drawableWithDescription =
                                    bluetoothDevicePreference.mCachedDevice
                                            .getDrawableWithDescription();
                            final boolean isBusy = bluetoothDevicePreference.mCachedDevice.isBusy();
                            final boolean z =
                                    bluetoothDevicePreference.mShowDevicesWithoutNames
                                            || (TextUtils.isEmpty(
                                                            bluetoothDevicePreference.mCachedDevice
                                                                    .mDevice.getAlias())
                                                    ^ true);
                            ThreadUtils.postOnMainThread(
                                    new Runnable() { // from class:
                                                     // com.android.settings.bluetooth.BluetoothDevicePreference$$ExternalSyntheticLambda3
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            BluetoothDevicePreference bluetoothDevicePreference2 =
                                                    BluetoothDevicePreference.this;
                                            String str = name;
                                            String str2 = connectionSummary;
                                            Pair pair = drawableWithDescription;
                                            boolean z2 = isBusy;
                                            boolean z3 = z;
                                            int i = BluetoothDevicePreference.sDimAlpha;
                                            bluetoothDevicePreference2.setTitle(str);
                                            bluetoothDevicePreference2.setSummary(str2);
                                            bluetoothDevicePreference2.setIcon(
                                                    (Drawable) pair.first);
                                            bluetoothDevicePreference2.contentDescription =
                                                    (String) pair.second;
                                            bluetoothDevicePreference2.setEnabled(!z2);
                                            bluetoothDevicePreference2.setVisible(z3);
                                            if (bluetoothDevicePreference2
                                                    .mNeedNotifyHierarchyChanged) {
                                                bluetoothDevicePreference2.notifyHierarchyChanged();
                                            }
                                        }
                                    });
                        }
                    });
        } catch (RejectedExecutionException unused) {
            Log.w(
                    "BluetoothDevicePref",
                    "Handler thread unavailable, skipping getConnectionSummary!");
        }
    }

    @Override // androidx.preference.Preference
    public final void onPrepareForRemoval() {
        unregisterDependency();
        if (!this.mIsCallbackRemoved) {
            this.mCachedDevice.unregisterCallback(this.mCallback);
            unregisterMetadataChangedListener();
            this.mIsCallbackRemoved = true;
        }
        AlertDialog alertDialog = this.mDisconnectDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.mDisconnectDialog = null;
        }
    }

    @Override // com.android.settings.widget.GearPreference,
              // com.android.settingslib.widget.TwoTargetPreference
    public final boolean shouldHideSecondTarget() {
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        return cachedBluetoothDevice == null
                || cachedBluetoothDevice.mBondState != 12
                || this.mUserManager.hasUserRestriction("no_config_bluetooth")
                || this.mHideSecondTarget;
    }

    public final void unregisterMetadataChangedListener() {
        if (this.mBluetoothAdapter == null) {
            Log.d("BluetoothDevicePref", "No mBluetoothAdapter");
            return;
        }
        Set set = this.mBluetoothDevices;
        if (set == null || ((HashSet) set).isEmpty()) {
            Log.d("BluetoothDevicePref", "No BT device to unregister.");
        } else {
            this.mBluetoothDevices.forEach(
                    new Consumer() { // from class:
                                     // com.android.settings.bluetooth.BluetoothDevicePreference$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            BluetoothDevicePreference bluetoothDevicePreference =
                                    BluetoothDevicePreference.this;
                            BluetoothDevice bluetoothDevice = (BluetoothDevice) obj;
                            int i = BluetoothDevicePreference.sDimAlpha;
                            bluetoothDevicePreference.getClass();
                            try {
                                bluetoothDevicePreference.mBluetoothAdapter
                                        .removeOnMetadataChangedListener(
                                                bluetoothDevice,
                                                bluetoothDevicePreference.mMetadataListener);
                            } catch (IllegalArgumentException e) {
                                Log.e(
                                        "BluetoothDevicePref",
                                        bluetoothDevice.getAnonymizedAddress()
                                                + ":"
                                                + e.toString());
                            } catch (NullPointerException e2) {
                                Log.e(
                                        "BluetoothDevicePref",
                                        bluetoothDevice.getAnonymizedAddress()
                                                + ":"
                                                + e2.toString());
                            }
                        }
                    });
            ((HashSet) this.mBluetoothDevices).clear();
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.preference.Preference, java.lang.Comparable
    public final int compareTo(Preference preference) {
        if (!(preference instanceof BluetoothDevicePreference)) {
            return super.compareTo(preference);
        }
        int i = this.mType;
        return i != 1
                ? i != 2
                        ? super.compareTo(preference)
                        : this.mId > ((BluetoothDevicePreference) preference).mId ? 1 : -1
                : this.mCachedDevice.compareTo(
                        ((BluetoothDevicePreference) preference).mCachedDevice);
    }
}

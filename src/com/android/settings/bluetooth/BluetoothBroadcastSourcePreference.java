package com.android.settings.bluetooth;

import android.bluetooth.BluetoothLeAudioContentMetadata;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothLeBroadcastReceiveState;
import android.bluetooth.BluetoothLeBroadcastSubgroup;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import java.util.List;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothBroadcastSourcePreference extends Preference {
    public BluetoothLeBroadcastMetadata mBluetoothLeBroadcastMetadata;
    public BluetoothLeBroadcastReceiveState mBluetoothLeBroadcastReceiveState;
    public ImageView mFrictionImageView;
    public boolean mIsEncrypted;
    public boolean mStatus;
    public String mTitle;

    public BluetoothBroadcastSourcePreference(Context context) {
        super(context);
        setLayoutResource(R.layout.preference_access_point);
        setWidgetLayoutResource(R.layout.access_point_friction_widget);
        this.mTitle = getContext().getString(R.string.device_info_default);
        this.mStatus = false;
        Drawable drawable = getContext().getDrawable(R.drawable.settings_input_antenna);
        if (drawable != null) {
            drawable.setTint(
                    com.android.settingslib.Utils.getColorAttrDefaultColor(
                            getContext(), android.R.attr.colorControlNormal));
            setIcon(drawable);
        }
    }

    public final String getProgramInfo() {
        BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState =
                this.mBluetoothLeBroadcastReceiveState;
        if (bluetoothLeBroadcastReceiveState != null) {
            List subgroupMetadata = bluetoothLeBroadcastReceiveState.getSubgroupMetadata();
            if (!subgroupMetadata.isEmpty()) {
                final int i = 0;
                return (String)
                        subgroupMetadata.stream()
                                .map(
                                        new Function() { // from class:
                                                         // com.android.settings.bluetooth.BluetoothBroadcastSourcePreference$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                switch (i) {
                                                    case 0:
                                                        return ((BluetoothLeAudioContentMetadata)
                                                                        obj)
                                                                .getProgramInfo();
                                                    default:
                                                        return ((BluetoothLeBroadcastSubgroup) obj)
                                                                .getContentMetadata()
                                                                .getProgramInfo();
                                                }
                                            }
                                        })
                                .findFirst()
                                .orElse(getContext().getString(R.string.device_info_default));
            }
        }
        BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata =
                this.mBluetoothLeBroadcastMetadata;
        if (bluetoothLeBroadcastMetadata == null) {
            return getContext().getString(R.string.device_info_default);
        }
        List subgroups = bluetoothLeBroadcastMetadata.getSubgroups();
        if (subgroups.isEmpty()) {
            return getContext().getString(R.string.device_info_default);
        }
        final int i2 = 1;
        return (String)
                subgroups.stream()
                        .map(
                                new Function() { // from class:
                                                 // com.android.settings.bluetooth.BluetoothBroadcastSourcePreference$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        switch (i2) {
                                            case 0:
                                                return ((BluetoothLeAudioContentMetadata) obj)
                                                        .getProgramInfo();
                                            default:
                                                return ((BluetoothLeBroadcastSubgroup) obj)
                                                        .getContentMetadata()
                                                        .getProgramInfo();
                                        }
                                    }
                                })
                        .filter(new BluetoothBroadcastSourcePreference$$ExternalSyntheticLambda2())
                        .findFirst()
                        .orElse(getContext().getString(R.string.device_info_default));
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.findViewById(R.id.two_target_divider).setVisibility(4);
        ((ImageButton) preferenceViewHolder.findViewById(R.id.icon_button)).setVisibility(8);
        this.mFrictionImageView = (ImageView) preferenceViewHolder.findViewById(R.id.friction_icon);
        updateStatusButton();
    }

    public final void updateMetadataAndRefreshUi(
            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata, boolean z) {
        this.mBluetoothLeBroadcastMetadata = bluetoothLeBroadcastMetadata;
        this.mTitle = getProgramInfo();
        this.mIsEncrypted = this.mBluetoothLeBroadcastMetadata.isEncrypted();
        this.mStatus = z || this.mBluetoothLeBroadcastReceiveState != null;
        setTitle(this.mTitle);
        updateStatusButton();
    }

    public final void updateStatusButton() {
        ImageView imageView = this.mFrictionImageView;
        if (imageView == null) {
            return;
        }
        boolean z = this.mStatus;
        if (!z && !this.mIsEncrypted) {
            imageView.setVisibility(8);
            return;
        }
        Drawable drawable =
                z
                        ? getContext().getDrawable(R.drawable.bluetooth_broadcast_dialog_done)
                        : getContext().getDrawable(R.drawable.ic_friction_lock_closed);
        if (drawable != null) {
            drawable.setTint(
                    com.android.settingslib.Utils.getColorAttrDefaultColor(
                            getContext(), android.R.attr.colorControlNormal));
            this.mFrictionImageView.setImageDrawable(drawable);
        }
        this.mFrictionImageView.setVisibility(0);
    }
}

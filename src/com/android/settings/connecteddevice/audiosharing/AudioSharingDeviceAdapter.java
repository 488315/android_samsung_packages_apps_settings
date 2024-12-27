package com.android.settings.connecteddevice.audiosharing;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AudioSharingDeviceAdapter extends RecyclerView.Adapter {
    public final Context mContext;
    public final List mDevices;
    public final OnClickListener mOnClickListener;
    public final ActionType mType;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ActionType {
        public static final /* synthetic */ ActionType[] $VALUES;
        public static final ActionType REMOVE;
        public static final ActionType SHARE;

        static {
            ActionType actionType = new ActionType("SHARE", 0);
            SHARE = actionType;
            ActionType actionType2 = new ActionType("REMOVE", 1);
            REMOVE = actionType2;
            $VALUES = new ActionType[] {actionType, actionType2};
        }

        public static ActionType valueOf(String str) {
            return (ActionType) Enum.valueOf(ActionType.class, str);
        }

        public static ActionType[] values() {
            return (ActionType[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AudioSharingDeviceViewHolder extends RecyclerView.ViewHolder {
        public final Button mButtonView;

        public AudioSharingDeviceViewHolder(View view) {
            super(view);
            this.mButtonView = (Button) view.findViewById(R.id.device_button);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnClickListener {
        void onClick();
    }

    public AudioSharingDeviceAdapter(
            Context context, List list, OnClickListener onClickListener, ActionType actionType) {
        this.mContext = context;
        this.mDevices = list;
        this.mOnClickListener = onClickListener;
        this.mType = actionType;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mDevices.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        String string;
        final AudioSharingDeviceViewHolder audioSharingDeviceViewHolder =
                (AudioSharingDeviceViewHolder) viewHolder;
        if (audioSharingDeviceViewHolder.mButtonView == null) {
            Log.w("AudioSharingDeviceAdapter", "bind view skipped due to button view is null");
            return;
        }
        AudioSharingDeviceAdapter audioSharingDeviceAdapter = AudioSharingDeviceAdapter.this;
        int ordinal = audioSharingDeviceAdapter.mType.ordinal();
        if (ordinal == 0) {
            string =
                    audioSharingDeviceAdapter.mContext.getString(
                            R.string.audio_sharing_share_with_button_label,
                            ((AudioSharingDeviceItem) audioSharingDeviceAdapter.mDevices.get(i))
                                    .mName);
        } else {
            if (ordinal != 1) {
                throw new IncompatibleClassChangeError();
            }
            string =
                    audioSharingDeviceAdapter.mContext.getString(
                            R.string.audio_sharing_disconnect_device_button_label,
                            ((AudioSharingDeviceItem) audioSharingDeviceAdapter.mDevices.get(i))
                                    .mName);
        }
        audioSharingDeviceViewHolder.mButtonView.setText(string);
        audioSharingDeviceViewHolder.mButtonView.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.connecteddevice.audiosharing.AudioSharingDeviceAdapter$AudioSharingDeviceViewHolder$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AudioSharingDeviceAdapter.AudioSharingDeviceViewHolder
                                audioSharingDeviceViewHolder2 =
                                        AudioSharingDeviceAdapter.AudioSharingDeviceViewHolder.this;
                        int i2 = i;
                        AudioSharingDeviceAdapter audioSharingDeviceAdapter2 =
                                AudioSharingDeviceAdapter.this;
                        AudioSharingDeviceAdapter.OnClickListener onClickListener =
                                audioSharingDeviceAdapter2.mOnClickListener;
                        onClickListener.onClick();
                    }
                });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new AudioSharingDeviceViewHolder(
                MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                        viewGroup, R.layout.audio_sharing_device_item, viewGroup, false));
    }
}

package com.samsung.android.settings.bluetooth.lebroadcast;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothDump;
import android.bluetooth.BluetoothLeAudio;
import android.bluetooth.BluetoothLeBroadcastReceiveState;
import android.content.Context;
import android.os.Debug;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.bluetooth.Utils;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LeAudioProfile;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant;

import com.samsung.android.settings.logging.SALogging;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecBluetoothLeBroadcastSourceDeviceAdapter extends RecyclerView.Adapter {
    public BluetoothDevice mActiveLeAudioDevice;
    public Map mClickedByUserMap;
    public Context mContext;
    public View mHolderView;
    public boolean mIsFirstTime;
    public SecBluetoothLeBroadcastSourceSetting.AnonymousClass2 mItemClickListener;
    public List mLeAudioConnectedDeviceLists;
    public LocalBluetoothLeBroadcastAssistant mLeBroadcastAssistant;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LeAudioDeviceBaseViewHolder extends RecyclerView.ViewHolder {
        public final ViewGroup mContainerLayout;
        public final ImageView mDeviceIcon;
        public final TextView mDeviceName;
        public final TextView mDeviceSummary;

        public LeAudioDeviceBaseViewHolder(View view) {
            super(view);
            this.mContainerLayout = (ViewGroup) view.requireViewById(R.id.device_container);
            this.mDeviceName = (TextView) view.requireViewById(R.id.title);
            this.mDeviceSummary = (TextView) view.requireViewById(R.id.summary);
            this.mDeviceIcon = (ImageView) view.requireViewById(R.id.icon);
        }
    }

    static {
        Debug.semIsProductDev();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        List list = this.mLeAudioConnectedDeviceLists;
        if (list != null) {
            return list.size();
        }
        Log.e("SecBluetoothLeBroadcastSourceDeviceAdapter", "mLeAudioConnectedDeviceLists NULL");
        return 0;
    }

    public final boolean isBisSync(CachedBluetoothDevice cachedBluetoothDevice) {
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                this.mLeBroadcastAssistant;
        if (localBluetoothLeBroadcastAssistant == null) {
            return false;
        }
        for (BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState :
                localBluetoothLeBroadcastAssistant.getAllSources(cachedBluetoothDevice.mDevice)) {
            if (bluetoothLeBroadcastReceiveState.getPaSyncState() == 2
                    || SecBluetoothLeBroadcastUtils.isBisSync(bluetoothLeBroadcastReceiveState)) {
                Log.d("SecBluetoothLeBroadcastSourceDeviceAdapter", "isBisSync");
                return true;
            }
        }
        Iterator it = cachedBluetoothDevice.mMemberDevices.iterator();
        while (it.hasNext()) {
            for (BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState2 :
                    localBluetoothLeBroadcastAssistant.getAllSources(
                            ((CachedBluetoothDevice) it.next()).mDevice)) {
                if (bluetoothLeBroadcastReceiveState2.getPaSyncState() == 2
                        || SecBluetoothLeBroadcastUtils.isBisSync(
                                bluetoothLeBroadcastReceiveState2)) {
                    Log.d("SecBluetoothLeBroadcastSourceDeviceAdapter", "isBisSync");
                    return true;
                }
            }
        }
        Log.i("SecBluetoothLeBroadcastSourceDeviceAdapter", "isBisSync false");
        return false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        LeAudioDeviceBaseViewHolder leAudioDeviceBaseViewHolder =
                (LeAudioDeviceBaseViewHolder) viewHolder;
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "onBindVieHolder - position: ", "SecBluetoothLeBroadcastSourceDeviceAdapter");
        CachedBluetoothDevice cachedBluetoothDevice =
                (CachedBluetoothDevice) this.mLeAudioConnectedDeviceLists.get(i);
        int size = this.mLeAudioConnectedDeviceLists.size();
        leAudioDeviceBaseViewHolder.mDeviceName.setText(cachedBluetoothDevice.mDeviceName);
        ImageView imageView = leAudioDeviceBaseViewHolder.mDeviceIcon;
        Context context = this.mContext;
        boolean z = Utils.DEBUG;
        imageView.setImageDrawable(
                BluetoothUtils.getHostOverlayIconDrawable(context, cachedBluetoothDevice));
        Map map = this.mClickedByUserMap;
        Boolean bool = Boolean.FALSE;
        boolean booleanValue =
                ((Boolean) ((HashMap) map).getOrDefault(cachedBluetoothDevice, bool))
                        .booleanValue();
        if (size > 1) {
            Log.d(
                    "SecBluetoothLeBroadcastSourceDeviceAdapter",
                    "onBindViewHolder: mIsFirstTime: "
                            + this.mIsFirstTime
                            + ", device: "
                            + cachedBluetoothDevice.mDevice.getAddressForLogging());
            if (this.mIsFirstTime) {
                if (!cachedBluetoothDevice.mDevice.equals(this.mActiveLeAudioDevice)) {
                    Iterator it = ((HashSet) cachedBluetoothDevice.mMemberDevices).iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            updateDeviceItem(leAudioDeviceBaseViewHolder, false);
                            break;
                        } else if (Objects.equals(
                                ((CachedBluetoothDevice) it.next()).mDevice,
                                this.mActiveLeAudioDevice)) {
                            updateDeviceItem(leAudioDeviceBaseViewHolder, true);
                            break;
                        }
                    }
                } else {
                    updateDeviceItem(leAudioDeviceBaseViewHolder, true);
                }
                if (i == size - 1) {
                    this.mIsFirstTime = false;
                }
            } else if (booleanValue) {
                updateDeviceItem(
                        leAudioDeviceBaseViewHolder,
                        leAudioDeviceBaseViewHolder.mDeviceSummary.getVisibility() == 0);
                ((HashMap) this.mClickedByUserMap).put(cachedBluetoothDevice, bool);
            } else if (isBisSync(cachedBluetoothDevice)) {
                updateDeviceItem(leAudioDeviceBaseViewHolder, true);
            } else {
                updateDeviceItem(leAudioDeviceBaseViewHolder, false);
            }
        }
        if (size == 1) {
            Log.d("SecBluetoothLeBroadcastSourceDeviceAdapter", "onBindViewHolder, size is 1");
            updateDeviceItem(leAudioDeviceBaseViewHolder, true);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        this.mContext = context;
        this.mHolderView =
                LayoutInflater.from(context)
                        .inflate(
                                R.layout.sec_bluetooth_auracast_device_list_item, viewGroup, false);
        final LeAudioDeviceBaseViewHolder leAudioDeviceBaseViewHolder =
                new LeAudioDeviceBaseViewHolder(this.mHolderView);
        this.mHolderView.setOnClickListener(
                new View.OnClickListener() { // from class:
                    // com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceDeviceAdapter.1
                    public long mLastClickTime;

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        boolean z;
                        int bindingAdapterPosition =
                                leAudioDeviceBaseViewHolder.getBindingAdapterPosition();
                        if (bindingAdapterPosition != -1) {
                            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                                    bindingAdapterPosition,
                                    "setOnClickListener - position: ",
                                    "SecBluetoothLeBroadcastSourceDeviceAdapter");
                            CachedBluetoothDevice cachedBluetoothDevice =
                                    (CachedBluetoothDevice)
                                            SecBluetoothLeBroadcastSourceDeviceAdapter.this
                                                    .mLeAudioConnectedDeviceLists.get(
                                                    bindingAdapterPosition);
                            SecBluetoothLeBroadcastSourceDeviceAdapter
                                    secBluetoothLeBroadcastSourceDeviceAdapter =
                                            SecBluetoothLeBroadcastSourceDeviceAdapter.this;
                            secBluetoothLeBroadcastSourceDeviceAdapter.getClass();
                            Log.i(
                                    "SecBluetoothLeBroadcastSourceDeviceAdapter",
                                    "getBisSyncDevices");
                            LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                                    secBluetoothLeBroadcastSourceDeviceAdapter
                                            .mLeBroadcastAssistant;
                            int i2 = 0;
                            if (localBluetoothLeBroadcastAssistant != null) {
                                int i3 = 0;
                                for (CachedBluetoothDevice cachedBluetoothDevice2 :
                                        secBluetoothLeBroadcastSourceDeviceAdapter
                                                .mLeAudioConnectedDeviceLists) {
                                    for (BluetoothLeBroadcastReceiveState
                                            bluetoothLeBroadcastReceiveState :
                                                    localBluetoothLeBroadcastAssistant
                                                            .getAllSources(
                                                                    cachedBluetoothDevice2
                                                                            .mDevice)) {
                                        if (bluetoothLeBroadcastReceiveState.getPaSyncState() == 2
                                                || SecBluetoothLeBroadcastUtils.isBisSync(
                                                        bluetoothLeBroadcastReceiveState)) {
                                            i3++;
                                            z = true;
                                            break;
                                        }
                                    }
                                    z = false;
                                    if (!z) {
                                        Iterator it =
                                                ((HashSet) cachedBluetoothDevice2.mMemberDevices)
                                                        .iterator();
                                        while (it.hasNext()) {
                                            for (BluetoothLeBroadcastReceiveState
                                                    bluetoothLeBroadcastReceiveState2 :
                                                            localBluetoothLeBroadcastAssistant
                                                                    .getAllSources(
                                                                            ((CachedBluetoothDevice)
                                                                                            it
                                                                                                    .next())
                                                                                    .mDevice)) {
                                                if (bluetoothLeBroadcastReceiveState2
                                                                        .getPaSyncState()
                                                                == 2
                                                        || SecBluetoothLeBroadcastUtils.isBisSync(
                                                                bluetoothLeBroadcastReceiveState2)) {
                                                    i3++;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                                        i3,
                                        "getBisSyncDevices, ",
                                        "SecBluetoothLeBroadcastSourceDeviceAdapter");
                                i2 = i3;
                            }
                            if (i2 == 1
                                    && SecBluetoothLeBroadcastSourceDeviceAdapter.this.isBisSync(
                                            cachedBluetoothDevice)) {
                                Log.i("SecBluetoothLeBroadcastSourceDeviceAdapter", "Ignore touch");
                                return;
                            }
                            long uptimeMillis = SystemClock.uptimeMillis();
                            if (uptimeMillis - this.mLastClickTime <= 600) {
                                Log.i("SecBluetoothLeBroadcastSourceDeviceAdapter", "Ignore click");
                                return;
                            }
                            this.mLastClickTime = uptimeMillis;
                            SecBluetoothLeBroadcastSourceDeviceAdapter
                                    secBluetoothLeBroadcastSourceDeviceAdapter2 =
                                            SecBluetoothLeBroadcastSourceDeviceAdapter.this;
                            SecBluetoothLeBroadcastSourceSetting.AnonymousClass2 anonymousClass2 =
                                    secBluetoothLeBroadcastSourceDeviceAdapter2.mItemClickListener;
                            boolean isBisSync =
                                    secBluetoothLeBroadcastSourceDeviceAdapter2.isBisSync(
                                            cachedBluetoothDevice);
                            anonymousClass2.getClass();
                            Log.d(
                                    "SecBluetoothLeBroadcastSourceSetting",
                                    "onItemClick: pos="
                                            + bindingAdapterPosition
                                            + " device="
                                            + cachedBluetoothDevice.getIdentityAddressForLog());
                            if (!isBisSync) {
                                Log.d("SecBluetoothLeBroadcastSourceSetting", "Call AddSource");
                                SecBluetoothLeBroadcastSourceSetting
                                        secBluetoothLeBroadcastSourceSetting =
                                                SecBluetoothLeBroadcastSourceSetting.this;
                                SALogging.insertSALog(
                                        secBluetoothLeBroadcastSourceSetting.mScreenId,
                                        secBluetoothLeBroadcastSourceSetting
                                                .getResources()
                                                .getString(
                                                        R.string
                                                                .event_broadcast_source_device_item));
                                if (secBluetoothLeBroadcastSourceSetting.mLocalLeBroadcast
                                        == null) {
                                    Log.e(
                                            "SecBluetoothLeBroadcastSourceSetting",
                                            "addSource, LocalLeBroadcast is null");
                                } else if (secBluetoothLeBroadcastSourceSetting.mLeAudioProfile
                                        == null) {
                                    Log.e(
                                            "SecBluetoothLeBroadcastSourceSetting",
                                            "addSource, mLeAudioProfile is null");
                                } else {
                                    BluetoothDump.BtLog("BRST-setActive");
                                    LeAudioProfile leAudioProfile =
                                            secBluetoothLeBroadcastSourceSetting.mLeAudioProfile;
                                    BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
                                    BluetoothLeAudio bluetoothLeAudio = leAudioProfile.mService;
                                    if (bluetoothLeAudio == null) {
                                        Log.d(
                                                "LeAudioProfile",
                                                "The BluetoothLeAudio is null when set active LE"
                                                    + " audio device");
                                    } else {
                                        bluetoothLeAudio.setActiveDevice(bluetoothDevice);
                                    }
                                }
                            }
                            SecBluetoothLeBroadcastSourceDeviceAdapter
                                    secBluetoothLeBroadcastSourceDeviceAdapter3 =
                                            SecBluetoothLeBroadcastSourceDeviceAdapter.this;
                            Map map = secBluetoothLeBroadcastSourceDeviceAdapter3.mClickedByUserMap;
                            CachedBluetoothDevice cachedBluetoothDevice3 =
                                    (CachedBluetoothDevice)
                                            secBluetoothLeBroadcastSourceDeviceAdapter3
                                                    .mLeAudioConnectedDeviceLists.get(
                                                    bindingAdapterPosition);
                            Boolean bool = Boolean.TRUE;
                            ((HashMap) map).put(cachedBluetoothDevice3, bool);
                            SecBluetoothLeBroadcastSourceDeviceAdapter.this.mObservable
                                    .notifyItemRangeChanged(bindingAdapterPosition, 1, bool);
                        }
                    }
                });
        return leAudioDeviceBaseViewHolder;
    }

    public final void setActiveLeAudioDevice(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice != null) {
            Log.d(
                    "SecBluetoothLeBroadcastSourceDeviceAdapter",
                    "setActiveLeAudioDevice : " + bluetoothDevice.getAddressForLogging());
        } else {
            Log.d("SecBluetoothLeBroadcastSourceDeviceAdapter", "setActiveLeAudioDevice is null");
        }
        this.mActiveLeAudioDevice = bluetoothDevice;
    }

    public final void updateDeviceItem(
            LeAudioDeviceBaseViewHolder leAudioDeviceBaseViewHolder, boolean z) {
        if (z) {
            leAudioDeviceBaseViewHolder.mDeviceName.setTextAppearance(
                    this.mContext, R.style.AuracastConnectedHighlight);
            leAudioDeviceBaseViewHolder.mDeviceName.setPadding(0, 0, 0, 0);
            leAudioDeviceBaseViewHolder.mDeviceSummary.setVisibility(0);
            leAudioDeviceBaseViewHolder.mContainerLayout.setClickable(false);
            return;
        }
        leAudioDeviceBaseViewHolder.mDeviceName.setTextAppearance(
                this.mContext, R.style.AuracastDeviceTitleTextStyle);
        leAudioDeviceBaseViewHolder.mDeviceName.setPadding(0, 3, 0, 3);
        leAudioDeviceBaseViewHolder.mDeviceSummary.setVisibility(8);
        leAudioDeviceBaseViewHolder.mContainerLayout.setClickable(true);
    }

    public final void updateDeviceList(List list) {
        this.mLeAudioConnectedDeviceLists = list;
        Log.d(
                "SecBluetoothLeBroadcastSourceDeviceAdapter",
                "updateDevicesList: " + this.mLeAudioConnectedDeviceLists.size());
        notifyDataSetChanged();
    }
}

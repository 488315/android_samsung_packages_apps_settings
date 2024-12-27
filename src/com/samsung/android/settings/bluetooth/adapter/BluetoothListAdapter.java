package com.samsung.android.settings.bluetooth.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;

import com.samsung.android.settings.bluetooth.BluetoothListController;
import com.samsung.android.settings.bluetooth.BluetoothScanDialog;
import com.samsung.android.settings.bluetooth.SecBluetoothDevicePreference;
import com.samsung.android.settings.bluetooth.SecDevicePickerDialog;
import com.samsung.android.settings.bluetooth.bluetoothcast.BluetoothCastDevicePreference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class BluetoothListAdapter extends RecyclerView.Adapter
        implements RecyclerView.ItemAnimator.ItemAnimatorFinishedListener {
    public final Context mContext;
    public final BluetoothListController mController;
    public final LocalBluetoothAdapter mLocalAdapter;
    public final RecyclerView mParentView;
    public final ArrayList mPreferenceList = new ArrayList();
    public boolean mNeedUpdatePreference = false;
    public boolean mIsNeedVi = true;
    public final MyHandler mHandler = new MyHandler();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MyHandler extends Handler
            implements RecyclerView.ItemAnimator.ItemAnimatorFinishedListener {
        public final List addList = new CopyOnWriteArrayList();
        public final List removeList = new CopyOnWriteArrayList();

        public MyHandler() {}

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i;
            if (message.what != 1) {
                return;
            }
            BluetoothListAdapter bluetoothListAdapter = BluetoothListAdapter.this;
            boolean isRunning = bluetoothListAdapter.mParentView.getItemAnimator().isRunning(this);
            ActionBarContextView$$ExternalSyntheticOutline0.m(
                    RowView$$ExternalSyntheticOutline0.m(
                            "MSG_UPDATE: isRunning = ", ", mNeed = ", isRunning),
                    bluetoothListAdapter.mNeedUpdatePreference,
                    bluetoothListAdapter.getLogTag());
            if (!isRunning && bluetoothListAdapter.mNeedUpdatePreference) {
                int size = ((CopyOnWriteArrayList) this.addList).size();
                int size2 = ((CopyOnWriteArrayList) this.removeList).size();
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        "MSG_UPDATE: addSize = ",
                        ", removeSize = ",
                        size,
                        size2,
                        bluetoothListAdapter.getLogTag());
                if (size == 0 && size2 == 0) {
                    bluetoothListAdapter.mNeedUpdatePreference = false;
                    Collections.sort(bluetoothListAdapter.mPreferenceList);
                    bluetoothListAdapter.notifyItemRangeChanged(
                            0, bluetoothListAdapter.mPreferenceList.size());
                } else {
                    if (size2 > 0) {
                        ArrayList arrayList = new ArrayList(this.removeList);
                        ((CopyOnWriteArrayList) this.removeList).clear();
                        ArrayList arrayList2 = bluetoothListAdapter.mPreferenceList;
                        if (arrayList2 == null || arrayList2.size() == 0) {
                            Log.e(
                                    bluetoothListAdapter.getLogTag(),
                                    "removeDeviceList: mCachedList size 0");
                        } else {
                            Iterator it = arrayList.iterator();
                            while (it.hasNext()) {
                                Preference preference = (Preference) it.next();
                                int indexOf =
                                        bluetoothListAdapter.mPreferenceList.indexOf(preference);
                                if (indexOf > -1) {
                                    if (preference instanceof SecBluetoothDevicePreference) {
                                        SecBluetoothDevicePreference secBluetoothDevicePreference =
                                                (SecBluetoothDevicePreference) preference;
                                        CachedBluetoothDevice cachedBluetoothDevice =
                                                secBluetoothDevicePreference.mCachedDevice;
                                        synchronized (cachedBluetoothDevice.mSemCallbacks) {
                                            ((ArrayList) cachedBluetoothDevice.mSemCallbacks)
                                                    .remove(secBluetoothDevicePreference);
                                        }
                                    } else if (preference
                                            instanceof BluetoothCastDevicePreference) {
                                        ((BluetoothCastDevicePreference) preference).reset$1();
                                    }
                                    bluetoothListAdapter.mPreferenceList.remove(preference);
                                    bluetoothListAdapter.notifyItemRemoved(indexOf);
                                } else {
                                    Log.d(
                                            bluetoothListAdapter.getLogTag(),
                                            "removeDeviceList: not contains");
                                }
                            }
                            bluetoothListAdapter.update();
                        }
                    }
                    if (size > 0) {
                        ArrayList arrayList3 = new ArrayList(this.addList);
                        ((CopyOnWriteArrayList) this.addList).clear();
                        if (bluetoothListAdapter.mPreferenceList == null) {
                            Log.e(
                                    bluetoothListAdapter.getLogTag(),
                                    "addDeviceList: mCachedList size is null");
                        } else {
                            Iterator it2 = arrayList3.iterator();
                            while (it2.hasNext()) {
                                Preference preference2 = (Preference) it2.next();
                                if (bluetoothListAdapter.mPreferenceList.contains(preference2)) {
                                    Log.e(
                                            bluetoothListAdapter.getLogTag(),
                                            "add: contains already");
                                } else {
                                    if (bluetoothListAdapter.mPreferenceList.size() > 0) {
                                        i =
                                                Collections.binarySearch(
                                                        bluetoothListAdapter.mPreferenceList,
                                                        preference2);
                                        if (i < 0) {
                                            i = (-i) - 1;
                                        }
                                    } else {
                                        i = 0;
                                    }
                                    String logTag = bluetoothListAdapter.getLogTag();
                                    StringBuilder m =
                                            ListPopupWindow$$ExternalSyntheticOutline0.m(
                                                    i, "add: index = ", ", size = ");
                                    m.append(bluetoothListAdapter.mPreferenceList.size());
                                    Log.d(logTag, m.toString());
                                    bluetoothListAdapter.mController.mListener
                                            .putDevicePreferenceMap(preference2);
                                    bluetoothListAdapter.mPreferenceList.add(i, preference2);
                                    bluetoothListAdapter.printAddLog(preference2);
                                    bluetoothListAdapter.notifyItemInserted(i);
                                }
                            }
                            bluetoothListAdapter.update();
                        }
                    }
                }
                update();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator.ItemAnimatorFinishedListener
        public final void onAnimationsFinished() {
            BluetoothListAdapter bluetoothListAdapter = BluetoothListAdapter.this;
            ActionBarContextView$$ExternalSyntheticOutline0.m(
                    new StringBuilder("onAnimationsFinished: mNeedUpdatePreference: "),
                    bluetoothListAdapter.mNeedUpdatePreference,
                    bluetoothListAdapter.getLogTag());
            if (bluetoothListAdapter.mNeedUpdatePreference) {
                update();
            }
        }

        public final void update() {
            Log.d(BluetoothListAdapter.this.getLogTag(), "update");
            removeMessages(1);
            sendMessage(obtainMessage(1));
        }
    }

    public BluetoothListAdapter(
            Context context,
            RecyclerView recyclerView,
            BluetoothListController bluetoothListController,
            LocalBluetoothAdapter localBluetoothAdapter) {
        this.mContext = context;
        this.mParentView = recyclerView;
        this.mController = bluetoothListController;
        this.mLocalAdapter = localBluetoothAdapter;
    }

    public final void add(Preference preference) {
        int i;
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("add: mIsNeedVi = "), this.mIsNeedVi, getLogTag());
        if (this.mIsNeedVi) {
            MyHandler myHandler = this.mHandler;
            ((CopyOnWriteArrayList) myHandler.addList).add(preference);
            BluetoothListAdapter.this.setNeedUpdatePreference();
        } else {
            if (this.mPreferenceList.contains(preference)) {
                return;
            }
            if (this.mPreferenceList.size() > 0) {
                i = Collections.binarySearch(this.mPreferenceList, preference);
                if (i < 0) {
                    i = (-i) - 1;
                }
            } else {
                i = 0;
            }
            this.mController.mListener.putDevicePreferenceMap(preference);
            this.mPreferenceList.add(i, preference);
        }
    }

    public final void delete(Preference preference) {
        MyHandler myHandler = this.mHandler;
        ((CopyOnWriteArrayList) myHandler.removeList).add(preference);
        BluetoothListAdapter.this.setNeedUpdatePreference();
    }

    public abstract String getLogTag();

    public final boolean isDialogType() {
        Context context = this.mContext;
        return (context instanceof BluetoothScanDialog)
                || (context instanceof SecDevicePickerDialog);
    }

    public abstract void printAddLog(Preference preference);

    public final void removeAll() {
        ArrayList arrayList = this.mPreferenceList;
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Preference preference = (Preference) it.next();
                if (preference instanceof SecBluetoothDevicePreference) {
                    SecBluetoothDevicePreference secBluetoothDevicePreference =
                            (SecBluetoothDevicePreference) preference;
                    CachedBluetoothDevice cachedBluetoothDevice =
                            secBluetoothDevicePreference.mCachedDevice;
                    synchronized (cachedBluetoothDevice.mSemCallbacks) {
                        ((ArrayList) cachedBluetoothDevice.mSemCallbacks)
                                .remove(secBluetoothDevicePreference);
                    }
                } else if (preference instanceof BluetoothCastDevicePreference) {
                    ((BluetoothCastDevicePreference) preference).reset$1();
                }
                it.remove();
            }
            update();
            notifyDataSetChanged();
        }
    }

    public final void setNeedUpdatePreference() {
        this.mNeedUpdatePreference = true;
        if (this.mParentView.getItemAnimator().isRunning()) {
            Log.d(getLogTag(), "setNeedUpdatePreference: itemAnimator isRunning");
        } else {
            this.mHandler.update();
        }
    }

    public final void setNeedVi(boolean z) {
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m("setNeedVi: needVi = ", getLogTag(), z);
        this.mIsNeedVi = z;
    }

    public abstract void update();
}

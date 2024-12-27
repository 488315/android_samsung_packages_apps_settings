package com.samsung.android.settings.wifi.managenetwork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.wifitrackerlib.WifiEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SavedWifiEntryListAdapter extends RecyclerView.Adapter {
    public final Context mContext;
    public SavedWifiEntrySettings mListener;
    public ArrayList mSavedWifiEntries = new ArrayList();
    public final ArrayList mWifiEntryCandidates = new ArrayList();
    public final Object mLock = new Object();
    public int mMode = 1;

    public SavedWifiEntryListAdapter(Context context, List list) {
        this.mContext = context;
        updateAllWifiEntries(list);
    }

    public final int getCheckedCount$1() {
        int count;
        synchronized (this.mLock) {
            count =
                    (int)
                            this.mSavedWifiEntries.stream()
                                    .filter(
                                            new SavedWifiEntryListAdapter$$ExternalSyntheticLambda0(
                                                    1))
                                    .count();
        }
        return count;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        int size;
        synchronized (this.mLock) {
            size = this.mSavedWifiEntries.size();
        }
        return size;
    }

    public final int getRemovableEntriesCount() {
        int count;
        synchronized (this.mLock) {
            count =
                    (int)
                            this.mSavedWifiEntries.stream()
                                    .filter(
                                            new SavedWifiEntryListAdapter$$ExternalSyntheticLambda0(
                                                    0))
                                    .count();
        }
        return count;
    }

    public final boolean isAutoSelectAllEnabled() {
        synchronized (this.mLock) {
            try {
                Iterator it = this.mSavedWifiEntries.iterator();
                int i = 0;
                while (it.hasNext()) {
                    if (((SavedWifiEntry) it.next()).isRemovableNetwork().booleanValue()
                            && (i = i + 1) > 1) {
                        return false;
                    }
                }
                return i == 1;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        SavedWifiEntryHolder savedWifiEntryHolder = (SavedWifiEntryHolder) viewHolder;
        synchronized (this.mLock) {
            try {
                if (this.mSavedWifiEntries.isEmpty()) {
                    return;
                }
                SavedWifiEntryItem savedWifiEntryItem = savedWifiEntryHolder.mEntry;
                SavedWifiEntry savedWifiEntry = (SavedWifiEntry) this.mSavedWifiEntries.get(i);
                savedWifiEntry.isRemovableNetwork();
                WifiEntry wifiEntry = savedWifiEntry.mWifiEntry;
                savedWifiEntryItem.setWifiEntry(savedWifiEntry, i, this.mListener);
                int connectedState = wifiEntry.getConnectedState();
                int i2 = 0;
                savedWifiEntryItem.setSummary(connectedState == 2);
                int i3 = this.mMode;
                savedWifiEntryItem.mMode = i3;
                if (i3 == 0) {
                    CheckBox checkBox = savedWifiEntryItem.mCheckbox;
                    if (!savedWifiEntryItem.mIsRemovable) {
                        i2 = 4;
                    }
                    checkBox.setVisibility(i2);
                } else {
                    savedWifiEntryItem.mCheckbox.setVisibility(8);
                }
                savedWifiEntryItem.setContentDescription(savedWifiEntryItem.getDescription());
            } finally {
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        SavedWifiEntryItem savedWifiEntryItem =
                (SavedWifiEntryItem)
                        LayoutInflater.from(this.mContext)
                                .inflate(R.layout.sec_saved_access_point_cell, viewGroup, false);
        SavedWifiEntryHolder savedWifiEntryHolder = new SavedWifiEntryHolder(savedWifiEntryItem);
        savedWifiEntryHolder.mEntry = savedWifiEntryItem;
        return savedWifiEntryHolder;
    }

    public final void setChecked(int i) {
        synchronized (this.mLock) {
            SavedWifiEntry savedWifiEntry = (SavedWifiEntry) this.mSavedWifiEntries.get(i);
            ((SavedWifiEntry) this.mSavedWifiEntries.get(i)).mIsChecked =
                    !savedWifiEntry.mIsChecked;
        }
        notifyItemChanged(i);
    }

    public final void setRemoveAllState(boolean z) {
        synchronized (this.mLock) {
            try {
                Iterator it = this.mSavedWifiEntries.iterator();
                while (it.hasNext()) {
                    ((SavedWifiEntry) it.next()).mIsChecked = z;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        notifyDataSetChanged();
    }

    public final void updateAllWifiEntries(Collection collection) {
        synchronized (this.mLock) {
            this.mSavedWifiEntries.clear();
            ArrayList arrayList = new ArrayList();
            Iterator it = ((ArrayList) collection).iterator();
            while (it.hasNext()) {
                arrayList.add(new SavedWifiEntry(this.mContext, (WifiEntry) it.next()));
            }
            this.mSavedWifiEntries = arrayList;
        }
        notifyDataSetChanged();
    }
}

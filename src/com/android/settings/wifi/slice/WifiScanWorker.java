package com.android.settings.wifi.slice;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

import com.android.settings.slices.SliceBackgroundWorker;
import com.android.settings.wifi.WifiPickerTrackerHelper;
import com.android.wifitrackerlib.WifiEntry;
import com.android.wifitrackerlib.WifiPickerTracker;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Deprecated(forRemoval = true)
/* loaded from: classes2.dex */
public class WifiScanWorker extends SliceBackgroundWorker
        implements WifiPickerTracker.WifiPickerTrackerCallback,
                LifecycleOwner,
                WifiEntry.WifiEntryCallback {
    final LifecycleRegistry mLifecycleRegistry;
    protected WifiPickerTracker mWifiPickerTracker;
    public final WifiPickerTrackerHelper mWifiPickerTrackerHelper;

    public WifiScanWorker(Context context, Uri uri) {
        super(context, uri);
        LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
        this.mLifecycleRegistry = lifecycleRegistry;
        WifiPickerTrackerHelper wifiPickerTrackerHelper =
                new WifiPickerTrackerHelper(lifecycleRegistry, context, this);
        this.mWifiPickerTrackerHelper = wifiPickerTrackerHelper;
        this.mWifiPickerTracker = wifiPickerTrackerHelper.mWifiPickerTracker;
        lifecycleRegistry.markState(Lifecycle.State.INITIALIZED);
        lifecycleRegistry.markState(Lifecycle.State.CREATED);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.mLifecycleRegistry.markState(Lifecycle.State.DESTROYED);
    }

    public int getApRowCount() {
        return 3;
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final Lifecycle getLifecycle() {
        return this.mLifecycleRegistry;
    }

    @Override // com.android.settings.slices.SliceBackgroundWorker
    public void onSlicePinned() {
        this.mLifecycleRegistry.markState(Lifecycle.State.STARTED);
        this.mLifecycleRegistry.markState(Lifecycle.State.RESUMED);
        updateResults();
    }

    @Override // com.android.settings.slices.SliceBackgroundWorker
    public void onSliceUnpinned() {
        this.mLifecycleRegistry.markState(Lifecycle.State.STARTED);
        this.mLifecycleRegistry.markState(Lifecycle.State.CREATED);
    }

    @Override // com.android.wifitrackerlib.WifiEntry.WifiEntryCallback
    public final void onUpdated() {
        updateResults();
    }

    @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
    public final void onWifiEntriesChanged() {
        updateResults();
    }

    public void updateResults() {
        if (this.mWifiPickerTracker.getWifiState() != 3
                || this.mLifecycleRegistry.state != Lifecycle.State.RESUMED) {
            if (this.mCachedResults != null) {
                this.mCachedResults = null;
                notifySliceChange();
                return;
            }
            return;
        }
        ArrayList arrayList = new ArrayList();
        WifiEntry wifiEntry = this.mWifiPickerTracker.mConnectedWifiEntry;
        if (wifiEntry != null) {
            wifiEntry.setListener(this);
            arrayList.add(new WifiSliceItem(this.mContext, wifiEntry));
        }
        for (WifiEntry wifiEntry2 : this.mWifiPickerTracker.getWifiEntries()) {
            if (arrayList.size() >= getApRowCount()) {
                break;
            } else if (wifiEntry2.getLevel() != -1) {
                wifiEntry2.setListener(this);
                arrayList.add(new WifiSliceItem(this.mContext, wifiEntry2));
            }
        }
        if (!arrayList.equals(this.mCachedResults)) {
            this.mCachedResults = arrayList;
            notifySliceChange();
        }
    }

    @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
    public final void onNumSavedNetworksChanged() {}

    @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
    public final void onNumSavedSubscriptionsChanged() {}
}

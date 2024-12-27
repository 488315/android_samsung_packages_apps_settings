package com.samsung.android.settings.wifi;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.wifi.WifiSettings;
import com.android.settings.wifi.WifiUtils;
import com.android.wifitrackerlib.WifiEntry;
import com.android.wifitrackerlib.WifiPickerTracker;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.SALogging;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiSettingsListController {
    public static final String[] PRIORITY_HINTCARD;
    public TextView mAutoHotspotCategory;
    public RecyclerView mAutoHotspotList;
    public AutoHotspotListAdapter mAutoHotspotListAdapter;
    public TextView mAvailableCategory;
    public RecyclerView mAvailableList;
    public View mBottomArea;
    public TextView mConnectedCategory;
    public RecyclerView mConnectedList;
    public ConnectedListAdapter mConnectedListAdapter;
    public ViewGroup mContainer;
    public final Context mContext;
    public TextView mEasySetupCategory;
    public RecyclerView mEasySetupList;
    public EasySetupListAdapter mEasySetupListAdapter;
    public TextView mEmptyView;
    public WifiEntriesFilter mFilter;
    public LinearLayout mFilterLayout;
    public final Fragment mFragment;
    public boolean mInPickerDialog;
    public boolean mInSetupWizardActivity;
    public boolean mInSetupWizardActivityVzw;
    public TextView mInstantHotspotCategory;
    public RecyclerView mInstantHotspotList;
    public InstantHotspotListAdapter mInstantHotspotListAdapter;
    public boolean mIsListVisible;
    public AvailableListAdapter mListAdapter;
    public TextView mMcfAutoHotspotCategory;
    public RecyclerView mMcfAutoHotspotList;
    public McfAutoHotspotListAdapter mMcfAutoHotspotListAdapter;
    public View mQrScannerLayout;
    public SwipeRefreshLayout mRefreshLayout;
    public View mRefreshListLayout;
    public final String mSAScreenId;
    public NestedScrollView mScrollView;
    public Drawable mSsidFilterClearDrawable;
    public EditText mSsidFilterEditText;
    public View mView;
    public final ArrayList mWifiEntries;
    public WifiPickerTracker mWifiTracker;
    public final boolean[] checked = new boolean[8];
    public final boolean[] checkedBackup = new boolean[8];
    public boolean mHasFilterMenuSetUp = false;
    public boolean mIsFilterMenuVisible = false;
    public final MyHandler mHandler = new MyHandler();
    public final AnonymousClass4 mSsidFilterEditTextOnTouchListener =
            new View
                    .OnTouchListener() { // from class:
                                         // com.samsung.android.settings.wifi.WifiSettingsListController.4
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    if (((int) motionEvent.getX())
                            <= (WifiSettingsListController.this.mSsidFilterEditText.getWidth()
                                            - WifiSettingsListController.this.mSsidFilterEditText
                                                    .getPaddingRight())
                                    - WifiSettingsListController.this.mSsidFilterClearDrawable
                                            .getIntrinsicWidth()) {
                        return false;
                    }
                    if (motionEvent.getAction() == 1) {
                        WifiSettingsListController.this.mSsidFilterEditText.setText(
                                ApnSettings.MVNO_NONE);
                        WifiSettingsListController.this.mSsidFilterEditText.performClick();
                    }
                    return true;
                }
            };
    public final AnonymousClass2 mMultiChoiceButtonOnClickListener = new AnonymousClass2(this, 2);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.WifiSettingsListController$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {}

        public final void onRefresh() {
            WifiSettingsListController wifiSettingsListController = WifiSettingsListController.this;
            if (wifiSettingsListController.mWifiTracker != null) {
                Log.d("WifiSettings.VI", "show refresh UI. force start scan manually");
                SALogging.insertSALog(
                        wifiSettingsListController
                                .mWifiTracker
                                .mScanResultUpdater
                                .mFilteredNetworkCount,
                        wifiSettingsListController.mSAScreenId,
                        "0103");
                wifiSettingsListController.mWifiTracker.semForceScan();
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.WifiSettingsListController$2, reason: invalid class name */
    public final class AnonymousClass2 implements View.OnClickListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WifiSettingsListController this$0;

        public /* synthetic */ AnonymousClass2(
                WifiSettingsListController wifiSettingsListController, int i) {
            this.$r8$classId = i;
            this.this$0 = wifiSettingsListController;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            switch (this.$r8$classId) {
                case 0:
                    WifiSettingsListController wifiSettingsListController = this.this$0;
                    wifiSettingsListController.getClass();
                    Intent secScannerIntent = WifiUtils.getSecScannerIntent();
                    SALogging.insertSALog(wifiSettingsListController.mSAScreenId, "0106");
                    try {
                        wifiSettingsListController.mFragment.startActivityForResult(
                                secScannerIntent, 5);
                        break;
                    } catch (ActivityNotFoundException e) {
                        Log.e("WifiSettings.VI", "No activity found : " + e.toString());
                        return;
                    }
                case 1:
                    Log.i("WifiSettings.VI", "onClick refresh icon ");
                    this.this$0.setEnableRefreshLayout(true);
                    this.this$0.mRefreshLayout.setRefreshing(true);
                    this.this$0.mWifiTracker.semForceScan();
                    break;
                default:
                    WifiSettingsListController wifiSettingsListController2 = this.this$0;
                    boolean[] zArr = wifiSettingsListController2.checked;
                    System.arraycopy(
                            zArr, 0, wifiSettingsListController2.checkedBackup, 0, zArr.length);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this.this$0.mContext);
                    builder.setMultiChoiceItems(
                            WifiMultiChoiceFilter.items,
                            this.this$0.checked,
                            new WifiSettingsListController$5$1());
                    builder.setTitle("Choose what to show");
                    final int i = 0;
                    builder.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener(
                                    this) { // from class:
                                            // com.samsung.android.settings.wifi.WifiSettingsListController$5$2
                                public final /* synthetic */ WifiSettingsListController
                                                .AnonymousClass2
                                        this$1;

                                {
                                    this.this$1 = this;
                                }

                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(DialogInterface dialogInterface, int i2) {
                                    switch (i) {
                                        case 0:
                                            WifiSettingsListController wifiSettingsListController3 =
                                                    this.this$1.this$0;
                                            WifiEntriesFilter wifiEntriesFilter =
                                                    wifiSettingsListController3.mFilter;
                                            wifiEntriesFilter.getClass();
                                            boolean[] isChecked =
                                                    wifiSettingsListController3.checked;
                                            Intrinsics.checkNotNullParameter(
                                                    isChecked, "isChecked");
                                            WifiMultiChoiceFilter wifiMultiChoiceFilter =
                                                    wifiEntriesFilter.multiChoiceFilter;
                                            wifiMultiChoiceFilter.getClass();
                                            ArrayList arrayList = new ArrayList();
                                            int length = isChecked.length;
                                            for (int i3 = 0; i3 < length; i3++) {
                                                if (isChecked[i3]) {
                                                    switch (i3) {
                                                        case 0:
                                                            arrayList.add(
                                                                    WifiMultiChoiceFilter
                                                                            .MultiChoiceFilter
                                                                            .OPEN);
                                                            break;
                                                        case 1:
                                                            arrayList.add(
                                                                    WifiMultiChoiceFilter
                                                                            .MultiChoiceFilter
                                                                            .SECURED);
                                                            break;
                                                        case 2:
                                                            arrayList.add(
                                                                    WifiMultiChoiceFilter
                                                                            .MultiChoiceFilter
                                                                            .FREQUENCY_2_4);
                                                            break;
                                                        case 3:
                                                            arrayList.add(
                                                                    WifiMultiChoiceFilter
                                                                            .MultiChoiceFilter
                                                                            .FREQUENCY_5);
                                                            break;
                                                        case 4:
                                                            arrayList.add(
                                                                    WifiMultiChoiceFilter
                                                                            .MultiChoiceFilter
                                                                            .FREQUENCY_6);
                                                            break;
                                                        case 5:
                                                            arrayList.add(
                                                                    WifiMultiChoiceFilter
                                                                            .MultiChoiceFilter
                                                                            .WIFI_5);
                                                            break;
                                                        case 6:
                                                            arrayList.add(
                                                                    WifiMultiChoiceFilter
                                                                            .MultiChoiceFilter
                                                                            .WIFI_6);
                                                            break;
                                                        case 7:
                                                            arrayList.add(
                                                                    WifiMultiChoiceFilter
                                                                            .MultiChoiceFilter
                                                                            .WIFI_7);
                                                            break;
                                                    }
                                                }
                                            }
                                            wifiMultiChoiceFilter.filters.clear();
                                            wifiMultiChoiceFilter.filters.addAll(arrayList);
                                            WifiEntriesFilter.OnFilterChangedListener
                                                    onFilterChangedListener =
                                                            wifiEntriesFilter.listener;
                                            if (onFilterChangedListener != null) {
                                                ((WifiSettings) onFilterChangedListener)
                                                        .updateWifiEntryPreferences(false);
                                                break;
                                            }
                                            break;
                                        default:
                                            WifiSettingsListController wifiSettingsListController4 =
                                                    this.this$1.this$0;
                                            boolean[] zArr2 =
                                                    wifiSettingsListController4.checkedBackup;
                                            System.arraycopy(
                                                    zArr2,
                                                    0,
                                                    wifiSettingsListController4.checked,
                                                    0,
                                                    zArr2.length);
                                            break;
                                    }
                                }
                            });
                    final int i2 = 1;
                    builder.setNegativeButton(
                            "Cancel",
                            new DialogInterface.OnClickListener(
                                    this) { // from class:
                                            // com.samsung.android.settings.wifi.WifiSettingsListController$5$2
                                public final /* synthetic */ WifiSettingsListController
                                                .AnonymousClass2
                                        this$1;

                                {
                                    this.this$1 = this;
                                }

                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(
                                        DialogInterface dialogInterface, int i22) {
                                    switch (i2) {
                                        case 0:
                                            WifiSettingsListController wifiSettingsListController3 =
                                                    this.this$1.this$0;
                                            WifiEntriesFilter wifiEntriesFilter =
                                                    wifiSettingsListController3.mFilter;
                                            wifiEntriesFilter.getClass();
                                            boolean[] isChecked =
                                                    wifiSettingsListController3.checked;
                                            Intrinsics.checkNotNullParameter(
                                                    isChecked, "isChecked");
                                            WifiMultiChoiceFilter wifiMultiChoiceFilter =
                                                    wifiEntriesFilter.multiChoiceFilter;
                                            wifiMultiChoiceFilter.getClass();
                                            ArrayList arrayList = new ArrayList();
                                            int length = isChecked.length;
                                            for (int i3 = 0; i3 < length; i3++) {
                                                if (isChecked[i3]) {
                                                    switch (i3) {
                                                        case 0:
                                                            arrayList.add(
                                                                    WifiMultiChoiceFilter
                                                                            .MultiChoiceFilter
                                                                            .OPEN);
                                                            break;
                                                        case 1:
                                                            arrayList.add(
                                                                    WifiMultiChoiceFilter
                                                                            .MultiChoiceFilter
                                                                            .SECURED);
                                                            break;
                                                        case 2:
                                                            arrayList.add(
                                                                    WifiMultiChoiceFilter
                                                                            .MultiChoiceFilter
                                                                            .FREQUENCY_2_4);
                                                            break;
                                                        case 3:
                                                            arrayList.add(
                                                                    WifiMultiChoiceFilter
                                                                            .MultiChoiceFilter
                                                                            .FREQUENCY_5);
                                                            break;
                                                        case 4:
                                                            arrayList.add(
                                                                    WifiMultiChoiceFilter
                                                                            .MultiChoiceFilter
                                                                            .FREQUENCY_6);
                                                            break;
                                                        case 5:
                                                            arrayList.add(
                                                                    WifiMultiChoiceFilter
                                                                            .MultiChoiceFilter
                                                                            .WIFI_5);
                                                            break;
                                                        case 6:
                                                            arrayList.add(
                                                                    WifiMultiChoiceFilter
                                                                            .MultiChoiceFilter
                                                                            .WIFI_6);
                                                            break;
                                                        case 7:
                                                            arrayList.add(
                                                                    WifiMultiChoiceFilter
                                                                            .MultiChoiceFilter
                                                                            .WIFI_7);
                                                            break;
                                                    }
                                                }
                                            }
                                            wifiMultiChoiceFilter.filters.clear();
                                            wifiMultiChoiceFilter.filters.addAll(arrayList);
                                            WifiEntriesFilter.OnFilterChangedListener
                                                    onFilterChangedListener =
                                                            wifiEntriesFilter.listener;
                                            if (onFilterChangedListener != null) {
                                                ((WifiSettings) onFilterChangedListener)
                                                        .updateWifiEntryPreferences(false);
                                                break;
                                            }
                                            break;
                                        default:
                                            WifiSettingsListController wifiSettingsListController4 =
                                                    this.this$1.this$0;
                                            boolean[] zArr2 =
                                                    wifiSettingsListController4.checkedBackup;
                                            System.arraycopy(
                                                    zArr2,
                                                    0,
                                                    wifiSettingsListController4.checked,
                                                    0,
                                                    zArr2.length);
                                            break;
                                    }
                                }
                            });
                    builder.setOnCancelListener(
                            new DialogInterface
                                    .OnCancelListener() { // from class:
                                                          // com.samsung.android.settings.wifi.WifiSettingsListController$5$4
                                @Override // android.content.DialogInterface.OnCancelListener
                                public final void onCancel(DialogInterface dialogInterface) {
                                    WifiSettingsListController wifiSettingsListController3 =
                                            WifiSettingsListController.AnonymousClass2.this.this$0;
                                    boolean[] zArr2 = wifiSettingsListController3.checkedBackup;
                                    System.arraycopy(
                                            zArr2,
                                            0,
                                            wifiSettingsListController3.checked,
                                            0,
                                            zArr2.length);
                                }
                            });
                    builder.create().show();
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MyHandler extends Handler
            implements RecyclerView.ItemAnimator.ItemAnimatorFinishedListener {
        public List mLastUpdatedPendingList;
        public boolean mScanUpdated = true;

        public MyHandler() {}

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            boolean z;
            int i = message.what;
            WifiSettingsListController wifiSettingsListController = WifiSettingsListController.this;
            if (i != 1) {
                if (i != 2) {
                    return;
                }
                wifiSettingsListController.mListAdapter.removeAll();
                return;
            }
            List<WifiEntry> list = (List) message.obj;
            if (list == null) {
                Log.e("WifiSettings.VI", "new list is null");
                return;
            }
            if (wifiSettingsListController.mAvailableList.getItemAnimator().isRunning(this)) {
                Log.d("WifiSettings.VI", "animation is running, pending CMD_UPDATE_LIST");
                this.mLastUpdatedPendingList = list;
                return;
            }
            if (this.mScanUpdated) {
                Log.i("WifiSettings.VI", "updateWithAnimation newList size:" + list.size());
                AvailableListAdapter availableListAdapter = wifiSettingsListController.mListAdapter;
                availableListAdapter.getClass();
                ArrayList arrayList = new ArrayList();
                HashMap hashMap = new HashMap();
                for (WifiEntry wifiEntry : list) {
                    hashMap.put(wifiEntry.getKey(), wifiEntry);
                }
                for (int size = availableListAdapter.mWifiEntries.size() - 1; size >= 0; size--) {
                    if (!hashMap.containsKey(
                            ((WifiEntry) availableListAdapter.mWifiEntries.get(size)).getKey())) {
                        arrayList.add(Integer.valueOf(size));
                    }
                }
                boolean isEmpty = arrayList.isEmpty();
                boolean z2 = WifiListAdapter.DBG;
                if (!isEmpty) {
                    if (z2) {
                        Log.i("WifiList.Available", "remove not scanned or changed items");
                    }
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        availableListAdapter.deleteItem(((Integer) it.next()).intValue());
                    }
                }
                if (z2) {
                    Log.i("WifiList.Available", "compare start");
                }
                z = false;
                for (int i2 = 0; i2 < list.size(); i2++) {
                    WifiEntry wifiEntry2 = (WifiEntry) list.get(i2);
                    int i3 = 0;
                    while (true) {
                        if (i3 >= availableListAdapter.mWifiEntries.size()) {
                            i3 = -1;
                            break;
                        } else if (((WifiEntry) availableListAdapter.mWifiEntries.get(i3))
                                .getKey()
                                .equals(wifiEntry2.getKey())) {
                            break;
                        } else {
                            i3++;
                        }
                    }
                    if (i3 < 0 || i3 < i2 || i2 >= availableListAdapter.mWifiEntries.size()) {
                        if (i3 > 0) {
                            Log.e(
                                    "WifiList.Available",
                                    wifiEntry2.getKey()
                                            + " is existed index: "
                                            + i3
                                            + " "
                                            + ((WifiEntry)
                                                            availableListAdapter.mWifiEntries.get(
                                                                    i3))
                                                    .getKey());
                        }
                        availableListAdapter.addItem(wifiEntry2, i2);
                    } else if (i3 != i2) {
                        WifiEntry wifiEntry3 =
                                (WifiEntry) availableListAdapter.mWifiEntries.get(i2);
                        if (z2) {
                            StringBuilder m =
                                    ListPopupWindow$$ExternalSyntheticOutline0.m(
                                            i2, "different_", " current ");
                            m.append(wifiEntry3.getKey());
                            m.append(" != new ");
                            m.append(wifiEntry2.getKey());
                            Log.d("WifiList.Available", m.toString());
                        }
                        availableListAdapter.deleteItem(i2);
                        if (i2 >= availableListAdapter.mWifiEntries.size()) {
                            availableListAdapter.addItem(wifiEntry2, i2);
                        } else if (wifiEntry2
                                != ((WifiEntry) availableListAdapter.mWifiEntries.get(i2))) {
                            int deleteItem = availableListAdapter.deleteItem(wifiEntry2);
                            if (z2) {
                                MainClearConfirm$$ExternalSyntheticOutline0.m(
                                        "move_", "_from_", i2, deleteItem, "WifiList.Available");
                            }
                            availableListAdapter.addItem(wifiEntry2, i2);
                        }
                    } else {
                        if ((TextUtils.isEmpty(wifiEntry2.getSummary(true))
                                        && TextUtils.isEmpty(
                                                wifiEntry2.mSemFlags.prevSummaryString))
                                ? false
                                : !TextUtils.equals(r9, r11.prevSummaryString)) {
                            if (i2 < availableListAdapter.mWifiEntries.size()) {
                                availableListAdapter.mWifiEntries.set(i2, wifiEntry2);
                                availableListAdapter.notifyItemChanged(i2);
                                if (z2) {
                                    StringBuilder m2 =
                                            ListPopupWindow$$ExternalSyntheticOutline0.m(
                                                    i2, "update_", ": ");
                                    m2.append(wifiEntry2.getKey());
                                    Log.d("WifiList.Available", m2.toString());
                                }
                            } else {
                                availableListAdapter.addItem(wifiEntry2, i2);
                            }
                        }
                    }
                    if (i2 >= availableListAdapter.mWifiEntries.size()) {
                        Log.e(
                                "WifiList.Available",
                                "error size:"
                                        + availableListAdapter.mWifiEntries.size()
                                        + " is under "
                                        + i2);
                    } else if (availableListAdapter.mWifiEntries.get(i2) != wifiEntry2) {
                        StringBuilder m3 =
                                ListPopupWindow$$ExternalSyntheticOutline0.m(
                                        i2, "error i:", " is different ");
                        m3.append(wifiEntry2.getKey());
                        Log.e("WifiList.Available", m3.toString());
                    }
                }
                if (availableListAdapter.mWifiEntries.size() > list.size()) {
                    if (z2) {
                        Log.i("WifiList.Available", "remove tail items");
                    }
                    for (int size2 = availableListAdapter.mWifiEntries.size() - 1;
                            size2 >= list.size();
                            size2--) {
                        availableListAdapter.deleteItem(size2);
                    }
                }
                List list2 = wifiSettingsListController.mListAdapter.mWifiEntries;
                if (list2.size() != list.size()) {
                    Log.e(
                            "WifiSettings.VI",
                            "dont match. size now:" + list2.size() + ", expect:" + list.size());
                } else {
                    boolean z3 = true;
                    for (int i4 = 0; i4 < list.size(); i4++) {
                        WifiEntry wifiEntry4 = (WifiEntry) list.get(i4);
                        WifiEntry wifiEntry5 = (WifiEntry) list2.get(i4);
                        if (wifiEntry4 != wifiEntry5
                                && !TextUtils.equals(wifiEntry4.getKey(), wifiEntry5.getKey())) {
                            Log.e(
                                    "WifiSettings.VI",
                                    "dont match "
                                            + wifiEntry4.getKey()
                                            + "/"
                                            + wifiEntry5.getKey());
                            z3 = false;
                        }
                    }
                    z = z3;
                }
            } else {
                Log.i("WifiSettings.VI", "update newList size:" + list.size());
                AvailableListAdapter availableListAdapter2 =
                        wifiSettingsListController.mListAdapter;
                availableListAdapter2.mWifiEntries = list;
                availableListAdapter2.notifyDataSetChanged();
                z = true;
            }
            if (!z) {
                Log.e("WifiSettings.VI", "list update failed, force update again.");
                sendMessage(obtainMessage(1, list));
            }
            this.mScanUpdated = z;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator.ItemAnimatorFinishedListener
        public final void onAnimationsFinished() {
            if (this.mLastUpdatedPendingList != null) {
                Log.d("WifiSettings.VI", "animation finished, need to update pending list");
                removeMessages(1);
                sendMessage(obtainMessage(1, this.mLastUpdatedPendingList));
                this.mLastUpdatedPendingList = null;
            }
        }
    }

    static {
        Debug.semIsProductDev();
        PRIORITY_HINTCARD = new String[] {"1"};
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.samsung.android.settings.wifi.WifiSettingsListController$4] */
    public WifiSettingsListController(
            Context context, ArrayList arrayList, String str, Fragment fragment) {
        this.mContext = context;
        this.mWifiEntries = arrayList;
        this.mSAScreenId = str;
        this.mFragment = fragment;
    }

    public final void destroyAutoHotspot() {
        Log.d("WifiSettings.VI.AutoHotspot", "destroyAutoHotspot() - Triggered");
        if (this.mAutoHotspotCategory.getVisibility() != 8
                || this.mAutoHotspotListAdapter.getItemCount() > 0) {
            this.mAutoHotspotCategory.setVisibility(8);
            AutoHotspotListAdapter autoHotspotListAdapter = this.mAutoHotspotListAdapter;
            autoHotspotListAdapter.getClass();
            Log.i("AutoHotspotListAdapter", "removeAll() - initiated");
            List list = autoHotspotListAdapter.mBleAccessPoints;
            if (list != null) {
                list.clear();
            }
            autoHotspotListAdapter.notifyDataSetChanged();
        }
    }

    public final void destroyInstantHotspot() {
        Log.d("WifiSettings.VI.InstantHotspot", "destroyInstantHotspot() - Triggered");
        if (this.mInstantHotspotCategory.getVisibility() != 8
                || this.mInstantHotspotListAdapter.getItemCount() > 0) {
            this.mInstantHotspotCategory.setVisibility(8);
            InstantHotspotListAdapter instantHotspotListAdapter = this.mInstantHotspotListAdapter;
            instantHotspotListAdapter.getClass();
            Log.i("InstantHotspotListAdapter", "removeAll() - initiated");
            List list = instantHotspotListAdapter.mBleAccessPoints;
            if (list != null) {
                list.clear();
            }
            instantHotspotListAdapter.notifyDataSetChanged();
        }
    }

    public final void destroyMcfAutoHotspot() {
        Log.d("WifiSettings.VI.MCFAutoHotspot", "destroyMcfAutoHotspot() - Triggered");
        if (this.mMcfAutoHotspotCategory.getVisibility() != 8) {
            this.mMcfAutoHotspotCategory.setVisibility(8);
            McfAutoHotspotListAdapter mcfAutoHotspotListAdapter = this.mMcfAutoHotspotListAdapter;
            mcfAutoHotspotListAdapter.getClass();
            Log.i("McfAutoHotspotListAdapter", "removeAll() - initiated");
            List list = mcfAutoHotspotListAdapter.mMcfAccessPoints;
            if (list != null) {
                list.clear();
            }
            mcfAutoHotspotListAdapter.notifyDataSetChanged();
        }
    }

    public final boolean isHideListSidePadding() {
        return this.mInSetupWizardActivity
                || this.mInPickerDialog
                || this.mInSetupWizardActivityVzw;
    }

    public final void setEnableRefreshLayout(boolean z) {
        SwipeRefreshLayout swipeRefreshLayout = this.mRefreshLayout;
        if (swipeRefreshLayout == null) {
            Log.d("WifiSettings.VI", "setEnableRefreshLayout - refresh layout is null");
        } else if (z) {
            swipeRefreshLayout.setEnabled(true);
        } else {
            swipeRefreshLayout.setEnabled(false);
        }
    }

    public final void setLayoutParams() {
        if (this.mView == null || isHideListSidePadding()) {
            return;
        }
        int listHorizontalPadding = Utils.getListHorizontalPadding(this.mContext);
        View findViewById =
                this.mView.findViewById(R.id.rounded_background).findViewById(R.id.rounded_frame);
        this.mScrollView.setPadding(listHorizontalPadding, 0, listHorizontalPadding, 0);
        FrameLayout.LayoutParams layoutParams =
                (FrameLayout.LayoutParams) findViewById.getLayoutParams();
        layoutParams.leftMargin = listHorizontalPadding;
        layoutParams.rightMargin = listHorizontalPadding;
        findViewById.setLayoutParams(layoutParams);
    }

    public final void setRoundedCorner(RecyclerView recyclerView) {
        if (this.mInSetupWizardActivity || this.mInPickerDialog) {
            recyclerView.semSetRoundedCorners(0);
        } else {
            recyclerView.semSetRoundedCorners(15);
            recyclerView.semSetRoundedCornerColor(
                    15,
                    this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x00ac  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setVisibleAvailableList(boolean r5) {
        /*
            r4 = this;
            r0 = 8
            r1 = 0
            if (r5 == 0) goto L1d
            androidx.recyclerview.widget.RecyclerView r2 = r4.mAvailableList
            r2.setVisibility(r1)
            boolean r2 = r4.mInSetupWizardActivity
            if (r2 != 0) goto L2c
            boolean r2 = r4.mInPickerDialog
            if (r2 != 0) goto L2c
            android.widget.TextView r2 = r4.mAvailableCategory
            r2.setVisibility(r1)
            android.view.View r2 = r4.mBottomArea
            r2.setVisibility(r1)
            goto L2c
        L1d:
            androidx.recyclerview.widget.RecyclerView r2 = r4.mAvailableList
            r2.setVisibility(r0)
            android.widget.TextView r2 = r4.mAvailableCategory
            r2.setVisibility(r0)
            android.view.View r2 = r4.mBottomArea
            r2.setVisibility(r0)
        L2c:
            r4.mIsListVisible = r5
            android.widget.TextView r5 = r4.mAvailableCategory
            int r5 = r5.getVisibility()
            r2 = 1
            if (r5 != 0) goto L4a
            android.content.Context r5 = r4.mContext
            android.telephony.SubscriptionManager r3 = com.samsung.android.wifitrackerlib.SemWifiUtils.mSubscriptionManager
            android.content.ContentResolver r5 = r5.getContentResolver()
            java.lang.String r3 = "sec_wifi_developer_use_filter"
            int r5 = android.provider.Settings.Global.getInt(r5, r3, r1)
            if (r5 != r2) goto L4a
            r5 = r2
            goto L4b
        L4a:
            r5 = r1
        L4b:
            r4.mIsFilterMenuVisible = r5
            boolean r3 = r4.mHasFilterMenuSetUp
            if (r3 != 0) goto La6
            if (r5 != 0) goto L54
            goto La6
        L54:
            android.widget.LinearLayout r5 = r4.mFilterLayout
            r3 = 2131364341(0x7f0a09f5, float:1.8348516E38)
            android.view.View r5 = r5.findViewById(r3)
            android.widget.ImageView r5 = (android.widget.ImageView) r5
            com.samsung.android.settings.wifi.WifiSettingsListController$2 r3 = r4.mMultiChoiceButtonOnClickListener
            r5.setOnClickListener(r3)
            android.widget.LinearLayout r5 = r4.mFilterLayout
            r3 = 2131365555(0x7f0a0eb3, float:1.8350979E38)
            android.view.View r5 = r5.findViewById(r3)
            android.widget.EditText r5 = (android.widget.EditText) r5
            r4.mSsidFilterEditText = r5
            com.samsung.android.settings.wifi.WifiSettingsListController$FilterInputTextWatcher r3 = new com.samsung.android.settings.wifi.WifiSettingsListController$FilterInputTextWatcher
            r3.<init>()
            r5.addTextChangedListener(r3)
            android.widget.LinearLayout r5 = r4.mFilterLayout
            r3 = 2131365554(0x7f0a0eb2, float:1.8350977E38)
            android.view.View r5 = r5.findViewById(r3)
            android.widget.ImageView r5 = (android.widget.ImageView) r5
            com.samsung.android.settings.wifi.WifiSettingsListController$$ExternalSyntheticLambda0 r3 = new com.samsung.android.settings.wifi.WifiSettingsListController$$ExternalSyntheticLambda0
            r3.<init>()
            r5.setOnClickListener(r3)
            android.widget.EditText r5 = r4.mSsidFilterEditText
            android.graphics.drawable.Drawable[] r5 = r5.getCompoundDrawables()
            r3 = 2
            r5 = r5[r3]
            r4.mSsidFilterClearDrawable = r5
            android.widget.EditText r5 = r4.mSsidFilterEditText
            r3 = 0
            r5.setCompoundDrawables(r3, r3, r3, r3)
            android.widget.EditText r5 = r4.mSsidFilterEditText
            com.samsung.android.settings.wifi.WifiSettingsListController$4 r3 = r4.mSsidFilterEditTextOnTouchListener
            r5.setOnTouchListener(r3)
            r4.mHasFilterMenuSetUp = r2
        La6:
            android.widget.LinearLayout r5 = r4.mFilterLayout
            boolean r4 = r4.mIsFilterMenuVisible
            if (r4 == 0) goto Lad
            r0 = r1
        Lad:
            r5.setVisibility(r0)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.WifiSettingsListController.setVisibleAvailableList(boolean):void");
    }

    public final void setVisibleEasySetupList(boolean z) {
        if (!z) {
            this.mEasySetupCategory.setVisibility(8);
            this.mEasySetupList.setVisibility(8);
        } else {
            if (this.mInSetupWizardActivity || this.mInPickerDialog) {
                return;
            }
            this.mEasySetupCategory.setVisibility(0);
            this.mEasySetupList.setVisibility(0);
        }
    }

    public final void updateEmptyView(boolean z) {
        View view;
        StringBuilder m =
                RowView$$ExternalSyntheticOutline0.m(
                        "updateEmptyView - ", ", AvailableAPCount : ", z);
        AvailableListAdapter availableListAdapter = this.mListAdapter;
        m.append(availableListAdapter == null ? 0 : availableListAdapter.mWifiEntries.size());
        m.append(", ConnectedAPCount:");
        ConnectedListAdapter connectedListAdapter = this.mConnectedListAdapter;
        m.append(connectedListAdapter == null ? 0 : connectedListAdapter.getItemCount());
        Log.d("WifiSettings.VI", m.toString());
        setEnableRefreshLayout(z);
        if (z) {
            this.mEmptyView.setVisibility(8);
            setVisibleAvailableList(true);
        } else {
            this.mEmptyView.setVisibility(0);
            Log.d("WifiSettings.VI", "removeAll() - Triggered");
            this.mConnectedCategory.setVisibility(8);
            this.mConnectedListAdapter.removeAll();
            this.mAvailableCategory.setVisibility(8);
            this.mListAdapter.removeAll();
            this.mAutoHotspotCategory.setVisibility(8);
            AutoHotspotListAdapter autoHotspotListAdapter = this.mAutoHotspotListAdapter;
            autoHotspotListAdapter.getClass();
            Log.i("AutoHotspotListAdapter", "removeAll() - initiated");
            List list = autoHotspotListAdapter.mBleAccessPoints;
            if (list != null) {
                list.clear();
            }
            autoHotspotListAdapter.notifyDataSetChanged();
            this.mMcfAutoHotspotCategory.setVisibility(8);
            McfAutoHotspotListAdapter mcfAutoHotspotListAdapter = this.mMcfAutoHotspotListAdapter;
            mcfAutoHotspotListAdapter.getClass();
            Log.i("McfAutoHotspotListAdapter", "removeAll() - initiated");
            List list2 = mcfAutoHotspotListAdapter.mMcfAccessPoints;
            if (list2 != null) {
                list2.clear();
            }
            mcfAutoHotspotListAdapter.notifyDataSetChanged();
            setVisibleAvailableList(false);
            setVisibleEasySetupList(false);
        }
        if (!this.mInSetupWizardActivity || (view = this.mQrScannerLayout) == null) {
            return;
        }
        if (z) {
            view.setVisibility(0);
        } else {
            view.setVisibility(8);
        }
    }

    public final void updateWithAnimation(WifiEntry wifiEntry, List list, boolean z) {
        MyHandler myHandler = this.mHandler;
        myHandler.mLastUpdatedPendingList = null;
        ArrayList arrayList = (ArrayList) list;
        if (arrayList.size() == 0) {
            Log.i("WifiSettings.VI", "update empty list");
            myHandler.sendMessage(myHandler.obtainMessage(2));
            return;
        }
        AvailableListAdapter availableListAdapter = this.mListAdapter;
        if ((availableListAdapter == null ? 0 : availableListAdapter.mWifiEntries.size()) == 0) {
            Log.i("WifiSettings.VI", "first update newList size:" + arrayList.size());
            AvailableListAdapter availableListAdapter2 = this.mListAdapter;
            availableListAdapter2.mWifiEntries = list;
            availableListAdapter2.notifyDataSetChanged();
        } else {
            if (myHandler.mScanUpdated) {
                myHandler.mScanUpdated = z;
            }
            if (WifiSettingsListController.this
                    .mAvailableList
                    .getItemAnimator()
                    .isRunning(myHandler)) {
                Log.d("WifiSettings.VI", "animation is running, pending it to update");
                myHandler.mLastUpdatedPendingList = list;
                if (wifiEntry != null) {
                    this.mListAdapter.deleteItem(wifiEntry);
                }
            } else {
                myHandler.removeMessages(1);
                myHandler.sendMessage(myHandler.obtainMessage(1, list));
            }
        }
        if (this.mIsListVisible) {
            return;
        }
        updateEmptyView(true);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class FilterInputTextWatcher implements TextWatcher {
        public FilterInputTextWatcher() {}

        @Override // android.text.TextWatcher
        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            WifiEntriesFilter wifiEntriesFilter = WifiSettingsListController.this.mFilter;
            wifiEntriesFilter.ssidFilter = charSequence.toString();
            WifiEntriesFilter.OnFilterChangedListener onFilterChangedListener =
                    wifiEntriesFilter.listener;
            if (onFilterChangedListener != null) {
                ((WifiSettings) onFilterChangedListener).updateWifiEntryPreferences(false);
            }
            if (WifiSettingsListController.this.mSsidFilterEditText.isFocused()) {
                WifiSettingsListController.this.mSsidFilterEditText.setCompoundDrawables(
                        null,
                        null,
                        charSequence.length() > 0
                                ? WifiSettingsListController.this.mSsidFilterClearDrawable
                                : null,
                        null);
            }
        }

        @Override // android.text.TextWatcher
        public final void afterTextChanged(Editable editable) {}

        @Override // android.text.TextWatcher
        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
    }
}

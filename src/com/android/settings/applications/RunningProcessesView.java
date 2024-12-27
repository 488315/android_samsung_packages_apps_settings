package com.android.settings.applications;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.UserHandle;
import android.text.BidiFormatter;
import android.text.format.DateUtils;
import android.text.format.Formatter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.internal.util.MemInfoReader;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RunningProcessesView extends FrameLayout
        implements AdapterView.OnItemClickListener,
                AbsListView.RecyclerListener,
                RunningState.OnRefreshUiListener {
    public final HashMap mActiveItems;
    public ServiceListAdapter mAdapter;
    public ActivityManager mAm;
    public TextView mAppsProcessPrefix;
    public TextView mAppsProcessText;
    public TextView mBackgroundProcessText;
    public final StringBuilder mBuilder;
    public ProgressBar mColorBar;
    public long mCurHighRam;
    public long mCurLowRam;
    public long mCurMedRam;
    public boolean mCurShowCached;
    public long mCurTotalRam;
    public Runnable mDataAvail;
    public TextView mForegroundProcessPrefix;
    public TextView mForegroundProcessText;
    public View mHeader;
    public ListView mListView;
    public final MemInfoReader mMemInfoReader;
    public SettingsPreferenceFragment mOwner;
    public RunningState mState;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ActiveItem {
        public long mFirstRunTime;
        public ViewHolder mHolder;
        public RunningState.BaseItem mItem;
        public View mRootView;
        public boolean mSetBackground;

        public final void updateTime(Context context, StringBuilder sb) {
            TextView textView;
            RunningState.BaseItem baseItem = this.mItem;
            if (baseItem instanceof RunningState.ServiceItem) {
                textView = this.mHolder.size;
            } else {
                String str = baseItem.mSizeStr;
                if (str == null) {
                    str = ApnSettings.MVNO_NONE;
                }
                if (!str.equals(baseItem.mCurSizeStr)) {
                    this.mItem.mCurSizeStr = str;
                    this.mHolder.size.setText(str);
                }
                RunningState.BaseItem baseItem2 = this.mItem;
                if (baseItem2.mBackground) {
                    if (!this.mSetBackground) {
                        this.mSetBackground = true;
                        this.mHolder.uptime.setText(ApnSettings.MVNO_NONE);
                    }
                } else if (baseItem2 instanceof RunningState.MergedItem) {
                    textView = this.mHolder.uptime;
                }
                textView = null;
            }
            if (textView != null) {
                this.mSetBackground = false;
                if (this.mFirstRunTime >= 0) {
                    textView.setText(
                            DateUtils.formatElapsedTime(
                                    sb,
                                    (SystemClock.elapsedRealtime() - this.mFirstRunTime) / 1000));
                    return;
                }
                RunningState.BaseItem baseItem3 = this.mItem;
                if (!(baseItem3 instanceof RunningState.MergedItem)
                        || ((RunningState.MergedItem) baseItem3).mServices.size() <= 0) {
                    textView.setText(ApnSettings.MVNO_NONE);
                } else {
                    textView.setText(context.getResources().getText(R.string.service_restarting));
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ServiceListAdapter extends BaseAdapter {
        public final LayoutInflater mInflater;
        public final ArrayList mItems = new ArrayList();
        public ArrayList mOrigItems;
        public boolean mShowBackground;
        public final RunningState mState;

        public ServiceListAdapter(RunningState runningState) {
            this.mState = runningState;
            this.mInflater =
                    (LayoutInflater)
                            RunningProcessesView.this
                                    .getContext()
                                    .getSystemService("layout_inflater");
            refreshItems();
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public final boolean areAllItemsEnabled() {
            return false;
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            return this.mItems.size();
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            return this.mItems.get(i);
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return ((RunningState.MergedItem) this.mItems.get(i)).hashCode();
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = this.mInflater.inflate(R.layout.running_processes_item, viewGroup, false);
                new ViewHolder(view);
            }
            synchronized (this.mState.mLock) {
                try {
                    if (i < this.mItems.size()) {
                        RunningProcessesView.this.mActiveItems.put(
                                view,
                                ((ViewHolder) view.getTag())
                                        .bind(
                                                this.mState,
                                                (RunningState.MergedItem) this.mItems.get(i),
                                                RunningProcessesView.this.mBuilder));
                    }
                } finally {
                }
            }
            return view;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public final boolean hasStableIds() {
            return true;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public final boolean isEmpty() {
            boolean z;
            RunningState runningState = this.mState;
            synchronized (runningState.mLock) {
                z = runningState.mHaveData;
            }
            return z && this.mItems.size() == 0;
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public final boolean isEnabled(int i) {
            return !((RunningState.MergedItem) this.mItems.get(i)).mIsProcess;
        }

        public final void refreshItems() {
            ArrayList arrayList;
            if (this.mShowBackground) {
                RunningState runningState = this.mState;
                synchronized (runningState.mLock) {
                    arrayList = runningState.mUserBackgroundItems;
                }
            } else {
                RunningState runningState2 = this.mState;
                synchronized (runningState2.mLock) {
                    arrayList = runningState2.mMergedItems;
                }
            }
            if (this.mOrigItems != arrayList) {
                this.mOrigItems = arrayList;
                if (arrayList == null) {
                    this.mItems.clear();
                    return;
                }
                this.mItems.clear();
                this.mItems.addAll(arrayList);
                if (this.mShowBackground) {
                    Collections.sort(this.mItems, this.mState.mBackgroundComparator);
                }
            }
        }

        public final void setShowBackground(boolean z) {
            if (this.mShowBackground != z) {
                this.mShowBackground = z;
                RunningState runningState = this.mState;
                synchronized (runningState.mLock) {
                    runningState.mWatchingBackgroundItems = z;
                }
                refreshItems();
                RunningProcessesView.this.refreshUi(true);
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ViewHolder {
        public final TextView description;
        public final ImageView icon;
        public final TextView name;
        public final View rootView;
        public final TextView size;
        public final TextView uptime;

        public ViewHolder(View view) {
            this.rootView = view;
            this.icon = (ImageView) view.findViewById(android.R.id.icon);
            this.name = (TextView) view.findViewById(android.R.id.title);
            this.description = (TextView) view.findViewById(android.R.id.summary);
            this.size = (TextView) view.findViewById(R.id.widget_summary1);
            this.uptime = (TextView) view.findViewById(R.id.widget_summary2);
            view.setTag(this);
        }

        public final ActiveItem bind(
                RunningState runningState, RunningState.BaseItem baseItem, StringBuilder sb) {
            ActiveItem activeItem;
            synchronized (runningState.mLock) {
                try {
                    PackageManager packageManager = this.rootView.getContext().getPackageManager();
                    if (baseItem.mPackageInfo == null
                            && (baseItem instanceof RunningState.MergedItem)
                            && ((RunningState.MergedItem) baseItem).mProcess != null) {
                        ((RunningState.MergedItem) baseItem).mProcess.ensureLabel(packageManager);
                        baseItem.mPackageInfo =
                                ((RunningState.MergedItem) baseItem).mProcess.mPackageInfo;
                        baseItem.mDisplayLabel =
                                ((RunningState.MergedItem) baseItem).mProcess.mDisplayLabel;
                    }
                    this.name.setText(baseItem.mDisplayLabel);
                    activeItem = new ActiveItem();
                    View view = this.rootView;
                    activeItem.mRootView = view;
                    activeItem.mItem = baseItem;
                    activeItem.mHolder = this;
                    activeItem.mFirstRunTime = baseItem.mActiveSince;
                    if (baseItem.mBackground) {
                        this.description.setText(view.getContext().getText(R.string.cached));
                    } else {
                        this.description.setText(baseItem.mDescription);
                    }
                    baseItem.mCurSizeStr = null;
                    this.icon.setImageDrawable(
                            baseItem.loadIcon(this.rootView.getContext(), runningState));
                    this.icon.setVisibility(0);
                    activeItem.updateTime(this.rootView.getContext(), sb);
                } catch (Throwable th) {
                    throw th;
                }
            }
            return activeItem;
        }
    }

    public RunningProcessesView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mActiveItems = new HashMap();
        this.mBuilder = new StringBuilder(128);
        this.mCurTotalRam = -1L;
        this.mCurHighRam = -1L;
        this.mCurMedRam = -1L;
        this.mCurLowRam = -1L;
        this.mCurShowCached = false;
        this.mMemInfoReader = new MemInfoReader();
        UserHandle.myUserId();
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
        RunningState.MergedItem mergedItem =
                (RunningState.MergedItem) ((ListView) adapterView).getAdapter().getItem(i);
        if (this.mOwner == null || mergedItem == null) {
            return;
        }
        Bundle bundle = new Bundle();
        RunningState.ProcessItem processItem = mergedItem.mProcess;
        if (processItem != null) {
            bundle.putInt(NetworkAnalyticsConstants.DataPoints.UID, processItem.mUid);
            bundle.putString("process", mergedItem.mProcess.mProcessName);
        }
        bundle.putInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, mergedItem.mUserId);
        bundle.putBoolean("background", this.mAdapter.mShowBackground);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getContext());
        String name = RunningServiceDetails.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = bundle;
        subSettingLauncher.setTitleRes(R.string.runningservicedetails_settings_title, null);
        launchRequest.mSourceMetricsCategory = this.mOwner.getMetricsCategory();
        subSettingLauncher.launch();
    }

    @Override // android.widget.AbsListView.RecyclerListener
    public final void onMovedToScrapHeap(View view) {
        this.mActiveItems.remove(view);
    }

    @Override // com.android.settings.applications.RunningState.OnRefreshUiListener
    public final void onRefreshUi(int i) {
        if (i == 0) {
            updateTimes$1();
            return;
        }
        if (i == 1) {
            refreshUi(false);
            updateTimes$1();
        } else {
            if (i != 2) {
                return;
            }
            refreshUi(true);
            updateTimes$1();
        }
    }

    public final void refreshUi(boolean z) {
        long j;
        long j2;
        if (z) {
            ServiceListAdapter serviceListAdapter = this.mAdapter;
            serviceListAdapter.refreshItems();
            serviceListAdapter.notifyDataSetChanged();
        }
        Runnable runnable = this.mDataAvail;
        if (runnable != null) {
            runnable.run();
            this.mDataAvail = null;
        }
        this.mMemInfoReader.readMemInfo();
        synchronized (this.mState.mLock) {
            try {
                boolean z2 = this.mCurShowCached;
                boolean z3 = this.mAdapter.mShowBackground;
                if (z2 != z3) {
                    this.mCurShowCached = z3;
                    if (z3) {
                        this.mForegroundProcessPrefix.setText(
                                getResources()
                                        .getText(R.string.running_processes_header_used_prefix));
                        this.mAppsProcessPrefix.setText(
                                getResources()
                                        .getText(R.string.running_processes_header_cached_prefix));
                    } else {
                        this.mForegroundProcessPrefix.setText(
                                getResources()
                                        .getText(R.string.running_processes_header_system_prefix));
                        this.mAppsProcessPrefix.setText(
                                getResources()
                                        .getText(R.string.running_processes_header_apps_prefix));
                    }
                }
                long totalSize = this.mMemInfoReader.getTotalSize();
                if (this.mCurShowCached) {
                    j = this.mMemInfoReader.getFreeSize() + this.mMemInfoReader.getCachedSize();
                    j2 = this.mState.mBackgroundProcessMemory;
                } else {
                    long freeSize =
                            this.mMemInfoReader.getFreeSize() + this.mMemInfoReader.getCachedSize();
                    RunningState runningState = this.mState;
                    j = freeSize + runningState.mBackgroundProcessMemory;
                    j2 = runningState.mServiceProcessMemory;
                }
                long j3 = (totalSize - j2) - j;
                if (this.mCurTotalRam != totalSize
                        || this.mCurHighRam != j3
                        || this.mCurMedRam != j2
                        || this.mCurLowRam != j) {
                    this.mCurTotalRam = totalSize;
                    this.mCurHighRam = j3;
                    this.mCurMedRam = j2;
                    this.mCurLowRam = j;
                    BidiFormatter bidiFormatter = BidiFormatter.getInstance();
                    this.mBackgroundProcessText.setText(
                            bidiFormatter.unicodeWrap(
                                    Formatter.formatShortFileSize(getContext(), j)));
                    this.mAppsProcessText.setText(
                            bidiFormatter.unicodeWrap(
                                    Formatter.formatShortFileSize(getContext(), j2)));
                    this.mForegroundProcessText.setText(
                            bidiFormatter.unicodeWrap(
                                    Formatter.formatShortFileSize(getContext(), j3)));
                    float f = totalSize;
                    int i = (int) ((j3 / f) * 100.0f);
                    this.mColorBar.setProgress(i);
                    this.mColorBar.setSecondaryProgress(i + ((int) ((j2 / f) * 100.0f)));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateTimes$1() {
        Iterator it = this.mActiveItems.values().iterator();
        while (it.hasNext()) {
            ActiveItem activeItem = (ActiveItem) it.next();
            if (activeItem.mRootView.getWindowToken() == null) {
                it.remove();
            } else {
                activeItem.updateTime(getContext(), this.mBuilder);
            }
        }
    }
}

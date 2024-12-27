package com.android.settings.applications.manageapplications;

import android.app.usage.StorageStats;
import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.UserHandle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.picker.widget.SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.notification.NotificationBackend;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.applications.ApplicationsState;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settingslib.applications.AppFileSizeFormatter;

import java.io.IOException;
import java.util.UUID;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ApplicationViewHolder extends RecyclerView.ViewHolder {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ImageView mAddIcon;
    public final ImageView mAppIcon;
    final TextView mAppName;
    public NotificationBackend mBackend;
    public Context mContext;
    public CharSequence mDisabledText;
    public final Button mHeaderButton;
    public final TextView mHeaderText;
    public int mLastSortMode;
    public final ProgressBar mProgressBar;
    public SizeSummaryLoaderTask mSizeSummaryLoaderTask;
    final TextView mSummary;
    public CharSequence mSummaryText;
    final SwitchCompat mSwitch;
    final ViewGroup mWidgetContainer;
    public final ImageView silentIcon;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SizeSummaryLoaderTask extends AsyncTask {
        public final ApplicationsState.AppEntry mAppEntry;
        public String mCurComputingSizePkg;
        public int mCurComputingSizeUserId;
        public UUID mCurComputingSizeUuid;
        public final CharSequence mInvalidSizeStr;
        public final StorageStatsManager mStats;
        public final int mWhich;

        public SizeSummaryLoaderTask(
                ApplicationsState.AppEntry appEntry, CharSequence charSequence, int i) {
            this.mAppEntry = appEntry;
            this.mWhich = i;
            this.mInvalidSizeStr = charSequence;
            this.mStats =
                    (StorageStatsManager)
                            ApplicationViewHolder.this.mContext.getSystemService(
                                    StorageStatsManager.class);
        }

        public static long getTotalExternalSize(PackageStats packageStats) {
            return packageStats.externalCodeSize
                    + packageStats.externalDataSize
                    + packageStats.externalCacheSize
                    + packageStats.externalMediaSize
                    + packageStats.externalObbSize;
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            PackageStats packageStats;
            long cacheBytes;
            ApplicationsState.AppEntry appEntry;
            long j;
            long j2;
            long j3;
            long j4;
            long j5;
            PackageStats packageStats2;
            long j6;
            SizeSummaryLoaderTask sizeSummaryLoaderTask = this;
            ApplicationInfo applicationInfo = sizeSummaryLoaderTask.mAppEntry.info;
            if ((applicationInfo.flags & 8388608) == 0
                    && !AppUtils.isArchived(
                            ApplicationViewHolder.this.mContext, applicationInfo.packageName)) {
                return null;
            }
            ApplicationsState.AppEntry appEntry2 = sizeSummaryLoaderTask.mAppEntry;
            if (appEntry2.size != -1 && !appEntry2.sizeStale) {
                return null;
            }
            ApplicationInfo applicationInfo2 = appEntry2.info;
            sizeSummaryLoaderTask.mCurComputingSizeUuid = applicationInfo2.storageUuid;
            sizeSummaryLoaderTask.mCurComputingSizePkg = applicationInfo2.packageName;
            int userId = UserHandle.getUserId(applicationInfo2.uid);
            sizeSummaryLoaderTask.mCurComputingSizeUserId = userId;
            try {
                StorageStats queryStatsForPackage =
                        sizeSummaryLoaderTask.mStats.queryStatsForPackage(
                                sizeSummaryLoaderTask.mCurComputingSizeUuid,
                                sizeSummaryLoaderTask.mCurComputingSizePkg,
                                UserHandle.of(userId));
                packageStats =
                        new PackageStats(
                                sizeSummaryLoaderTask.mCurComputingSizePkg,
                                sizeSummaryLoaderTask.mCurComputingSizeUserId);
                packageStats.codeSize = queryStatsForPackage.getAppBytes();
                packageStats.dataSize = queryStatsForPackage.getDataBytes();
                cacheBytes = queryStatsForPackage.getCacheBytes();
                packageStats.cacheSize = cacheBytes;
                appEntry = sizeSummaryLoaderTask.mAppEntry;
                appEntry.sizeStale = false;
                appEntry.sizeLoadStart = 0L;
                j = packageStats.externalCodeSize + packageStats.externalObbSize;
                j2 = packageStats.externalDataSize + packageStats.externalMediaSize;
                long j7 = j + j2;
                j3 = packageStats.codeSize;
                j4 = packageStats.dataSize;
                j5 = j7 + ((j3 + j4) - cacheBytes);
            } catch (PackageManager.NameNotFoundException | IOException | NullPointerException e) {
                e = e;
            }
            try {
                if (appEntry.size == j5
                        && appEntry.cacheSize == cacheBytes
                        && appEntry.codeSize == j3
                        && appEntry.dataSize == j4
                        && appEntry.externalCodeSize == j
                        && appEntry.externalDataSize == j2) {
                    packageStats2 = packageStats;
                    j6 = j2;
                    if (appEntry.externalCacheSize == packageStats2.externalCacheSize) {
                        return null;
                    }
                } else {
                    packageStats2 = packageStats;
                    j6 = j2;
                }
                appEntry.size = j5;
                appEntry.cacheSize = cacheBytes;
                appEntry.codeSize = j3;
                appEntry.dataSize = j4;
                appEntry.externalCodeSize = j;
                appEntry.externalDataSize = j6;
                appEntry.externalCacheSize = packageStats2.externalCacheSize;
                sizeSummaryLoaderTask = this;
                appEntry.sizeStr = sizeSummaryLoaderTask.getSizeStr(j5);
                ApplicationsState.AppEntry appEntry3 = sizeSummaryLoaderTask.mAppEntry;
                long j8 =
                        (packageStats2.codeSize + packageStats2.dataSize) - packageStats2.cacheSize;
                appEntry3.internalSize = j8;
                appEntry3.internalSizeStr = sizeSummaryLoaderTask.getSizeStr(j8);
                sizeSummaryLoaderTask.mAppEntry.externalSize = getTotalExternalSize(packageStats2);
                ApplicationsState.AppEntry appEntry4 = sizeSummaryLoaderTask.mAppEntry;
                appEntry4.externalSizeStr =
                        sizeSummaryLoaderTask.getSizeStr(appEntry4.externalSize);
                return null;
            } catch (PackageManager.NameNotFoundException | IOException | NullPointerException e2) {
                e = e2;
                sizeSummaryLoaderTask = this;
                sizeSummaryLoaderTask.mAppEntry.size = -2L;
                e.printStackTrace();
                return null;
            }
        }

        public final String getSizeStr(long j) {
            if (j < 0) {
                return null;
            }
            ApplicationViewHolder.this.getClass();
            return AppFileSizeFormatter.formatFileSize(0, j, ApplicationViewHolder.this.mContext);
        }

        @Override // android.os.AsyncTask
        public final void onCancelled() {
            ApplicationViewHolder.this.mSizeSummaryLoaderTask = null;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            String m;
            ApplicationViewHolder.this.updateSizeText(
                    this.mAppEntry, this.mInvalidSizeStr, this.mWhich);
            ApplicationViewHolder applicationViewHolder = ApplicationViewHolder.this;
            applicationViewHolder.mSizeSummaryLoaderTask = null;
            String str = this.mAppEntry.labelDescription;
            TextView textView = applicationViewHolder.mSummary;
            if (textView != null && textView.getText() != null) {
                StringBuilder m2 =
                        EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(
                                SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0
                                        .m(
                                                ApplicationViewHolder.this.mContext,
                                                R.string.comma,
                                                EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0
                                                        .m(str)));
                m2.append((Object) ApplicationViewHolder.this.mSummary.getText());
                str = m2.toString();
            }
            if (ApplicationViewHolder.this.mSwitch != null) {
                StringBuilder m3 =
                        EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(
                                str);
                ApplicationViewHolder applicationViewHolder2 = ApplicationViewHolder.this;
                boolean isChecked = applicationViewHolder2.mSwitch.isChecked();
                String m4 =
                        SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0
                                .m(
                                        applicationViewHolder2.mContext,
                                        R.string.comma,
                                        EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0
                                                .m(str));
                if (isChecked) {
                    m =
                            SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0
                                    .m(
                                            applicationViewHolder2.mContext,
                                            R.string.sec_enable_text_app,
                                            EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0
                                                    .m(m4));
                } else {
                    m =
                            SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0
                                    .m(
                                            applicationViewHolder2.mContext,
                                            R.string.sec_disable_text_app,
                                            EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0
                                                    .m(m4));
                }
                m3.append(m);
                str = m3.toString();
            }
            ApplicationViewHolder.this.itemView.setContentDescription(str);
        }
    }

    public ApplicationViewHolder(View view) {
        super(view);
        this.mAppName = (TextView) view.findViewById(android.R.id.title);
        this.mAppIcon = (ImageView) view.findViewById(android.R.id.icon);
        this.mSummary = (TextView) view.findViewById(android.R.id.summary);
        this.silentIcon = (ImageView) view.findViewById(R.id.silent_icon);
        this.mSwitch = (SwitchCompat) view.findViewById(android.R.id.switch_widget);
        this.mWidgetContainer = (ViewGroup) view.findViewById(android.R.id.widget_frame);
        this.mAddIcon = (ImageView) view.findViewById(R.id.add_preference_widget);
        this.mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar_cyclic);
        this.mHeaderText = (TextView) view.findViewById(R.id.function_desc);
        this.mHeaderButton = (Button) view.findViewById(R.id.button);
        this.mContext = view.getContext();
    }

    public static View newView(int i, ViewGroup viewGroup, boolean z, boolean z2) {
        LayoutInflater from = LayoutInflater.from(viewGroup.getContext());
        Configuration configuration = viewGroup.getContext().getResources().getConfiguration();
        StringBuilder sb = Utils.sBuilder;
        int i2 = configuration.screenWidthDp;
        ViewGroup viewGroup2 =
                (ViewGroup)
                        from.inflate(
                                ((i2 > 320 || configuration.fontScale < 1.1f)
                                                && (i2 >= 411 || configuration.fontScale < 1.3f))
                                        ? i == 1
                                                ? R.layout.sec_apps_notification_list_item_row
                                                : R.layout.sec_apps_list_item_row
                                        : R.layout.sec_apps_list_item_row_large,
                                viewGroup,
                                false);
        ViewGroup viewGroup3 = (ViewGroup) viewGroup2.findViewById(android.R.id.widget_frame);
        if (z) {
            if (viewGroup3 != null) {
                if (z2) {
                    LayoutInflater.from(viewGroup.getContext())
                            .inflate(
                                    R.layout.sec_widget_preference_switch_with_divider,
                                    viewGroup3,
                                    true);
                } else {
                    LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.sec_widget_preference_switch, viewGroup3, true);
                }
            }
        } else if (viewGroup3 != null) {
            viewGroup3.setVisibility(8);
        }
        return viewGroup2;
    }

    public final void setEnabled(boolean z) {
        this.mAppName.setEnabled(z);
        TextView textView = this.mSummary;
        if (textView != null) {
            textView.setEnabled(z);
        }
        SwitchCompat switchCompat = this.mSwitch;
        if (switchCompat != null) {
            switchCompat.setEnabled(z);
        }
    }

    public final void setHeaderText(CharSequence charSequence) {
        this.mHeaderText.setText(charSequence);
    }

    public final void setSummary(CharSequence charSequence) {
        this.mSummaryText = charSequence;
        updateDisableTextToSummary();
        updateSummaryVisibility();
    }

    public final void updateDisableTextToSummary() {
        if (this.mSummary == null) {
            return;
        }
        if (TextUtils.isEmpty(this.mSummaryText)) {
            this.mSummary.setText(this.mDisabledText);
            return;
        }
        if (TextUtils.isEmpty(this.mDisabledText)) {
            this.mSummary.setText(this.mSummaryText);
            return;
        }
        String str = this.mContext.getString(R.string.comma) + " ";
        this.mSummary.setText(((Object) this.mSummaryText) + str + ((Object) this.mDisabledText));
    }

    public final void updateDisableView(ApplicationInfo applicationInfo) {
        if (AppUtils.isArchived(this.mContext, applicationInfo.packageName)) {
            this.mDisabledText = ApnSettings.MVNO_NONE;
        } else if ((applicationInfo.flags & 8388608) == 0) {
            this.mDisabledText = this.mContext.getString(R.string.not_installed);
        } else if (AppUtils.isAutoDisabled(applicationInfo)
                || AppUtils.isManualDisabled(applicationInfo)) {
            this.mDisabledText = this.mContext.getString(R.string.sec_app_deep_sleeping);
        } else if (applicationInfo.enabled) {
            this.mDisabledText = ApnSettings.MVNO_NONE;
        } else {
            this.mDisabledText = this.mContext.getString(R.string.disabled);
        }
        updateDisableTextToSummary();
        updateSummaryVisibility();
    }

    public final void updateSizeText(
            ApplicationsState.AppEntry appEntry, CharSequence charSequence, int i) {
        String str = appEntry.sizeStr;
        if (str != null) {
            if (i == 1) {
                setSummary(appEntry.internalSizeStr);
                return;
            } else if (i != 2) {
                setSummary(str);
                return;
            } else {
                setSummary(appEntry.externalSizeStr);
                return;
            }
        }
        if (appEntry.size == -2) {
            setSummary(charSequence);
            return;
        }
        SizeSummaryLoaderTask sizeSummaryLoaderTask = this.mSizeSummaryLoaderTask;
        if (sizeSummaryLoaderTask != null) {
            sizeSummaryLoaderTask.cancel(true);
            this.mSizeSummaryLoaderTask = null;
        }
        SizeSummaryLoaderTask sizeSummaryLoaderTask2 =
                new SizeSummaryLoaderTask(appEntry, charSequence, i);
        this.mSizeSummaryLoaderTask = sizeSummaryLoaderTask2;
        sizeSummaryLoaderTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    public final void updateSummaryVisibility() {
        TextView textView = this.mSummary;
        if (textView != null) {
            textView.setVisibility(TextUtils.isEmpty(textView.getText()) ? 8 : 0);
        }
    }
}

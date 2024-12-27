package com.android.settings.applications;

import android.app.ActivityManager;
import android.app.ApplicationErrorReport;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.InstrumentedFragment;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;

import java.util.ArrayList;
import java.util.Collections;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RunningServiceDetails extends InstrumentedFragment
        implements RunningState.OnRefreshUiListener {
    public ViewGroup mAllDetails;
    public ActivityManager mAm;
    public boolean mHaveData;
    public LayoutInflater mInflater;
    public RunningState.MergedItem mMergedItem;
    public int mNumProcesses;
    public int mNumServices;
    public String mProcessName;
    public TextView mProcessesHeader;
    public View mRootView;
    public TextView mServicesHeader;
    public boolean mShowBackground;
    public ViewGroup mSnippet;
    public RunningProcessesView.ActiveItem mSnippetActiveItem;
    public RunningProcessesView.ViewHolder mSnippetViewHolder;
    public RunningState mState;
    public int mUid;
    public int mUserId;
    public final ArrayList mActiveDetails = new ArrayList();
    public final StringBuilder mBuilder = new StringBuilder(128);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ActiveDetail implements View.OnClickListener {
        public RunningProcessesView.ActiveItem mActiveItem;
        public ComponentName mInstaller;
        public PendingIntent mManageIntent;
        public Button mReportButton;
        public View mRootView;
        public RunningState.ServiceItem mServiceItem;
        public Button mStopButton;

        public ActiveDetail() {}

        /* JADX WARN: Code restructure failed: missing block: B:31:0x00e8, code lost:

           if (r1 == null) goto L42;
        */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x00b1, code lost:

           if (r7 == null) goto L89;
        */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:37:0x011a A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:55:0x0120 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r7v1 */
        /* JADX WARN: Type inference failed for: r7v11 */
        /* JADX WARN: Type inference failed for: r7v12 */
        /* JADX WARN: Type inference failed for: r7v13 */
        /* JADX WARN: Type inference failed for: r7v2 */
        /* JADX WARN: Type inference failed for: r7v4 */
        /* JADX WARN: Type inference failed for: r7v8, types: [java.io.FileOutputStream] */
        @Override // android.view.View.OnClickListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onClick(android.view.View r15) {
            /*
                Method dump skipped, instructions count: 386
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.settings.applications.RunningServiceDetails.ActiveDetail.onClick(android.view.View):void");
        }

        public final void stopActiveService(boolean z) {
            RunningState.ServiceItem serviceItem = this.mServiceItem;
            if (!z && (serviceItem.mServiceInfo.applicationInfo.flags & 1) != 0) {
                RunningServiceDetails runningServiceDetails = RunningServiceDetails.this;
                ComponentName componentName = serviceItem.mRunningService.service;
                runningServiceDetails.getClass();
                MyAlertDialogFragment myAlertDialogFragment = new MyAlertDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", 1);
                bundle.putParcelable("comp", componentName);
                myAlertDialogFragment.setArguments(bundle);
                myAlertDialogFragment.setTargetFragment(runningServiceDetails, 0);
                myAlertDialogFragment.show(
                        runningServiceDetails.getFragmentManager(), "confirmstop");
                return;
            }
            RunningServiceDetails.this
                    .getActivity()
                    .stopService(new Intent().setComponent(serviceItem.mRunningService.service));
            RunningServiceDetails runningServiceDetails2 = RunningServiceDetails.this;
            RunningState.MergedItem mergedItem = runningServiceDetails2.mMergedItem;
            if (mergedItem == null) {
                runningServiceDetails2.mState.updateNow();
                RunningServiceDetails.this.finish$2();
            } else if (runningServiceDetails2.mShowBackground || mergedItem.mServices.size() > 1) {
                RunningServiceDetails.this.mState.updateNow();
            } else {
                RunningServiceDetails.this.mState.updateNow();
                RunningServiceDetails.this.finish$2();
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class MyAlertDialogFragment extends InstrumentedDialogFragment {
        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 536;
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            int i = getArguments().getInt("id");
            if (i != 1) {
                throw new IllegalArgumentException(
                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "unknown id "));
            }
            final ComponentName componentName =
                    (ComponentName) getArguments().getParcelable("comp");
            if (((RunningServiceDetails) getTargetFragment()).activeDetailForService(componentName)
                    == null) {
                return null;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            String string = getActivity().getString(R.string.runningservicedetails_stop_dlg_title);
            AlertController.AlertParams alertParams = builder.P;
            alertParams.mTitle = string;
            alertParams.mMessage =
                    getActivity().getString(R.string.runningservicedetails_stop_dlg_text);
            builder.setPositiveButton(
                    R.string.dlg_ok,
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.android.settings.applications.RunningServiceDetails.MyAlertDialogFragment.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i2) {
                            ActiveDetail activeDetailForService =
                                    ((RunningServiceDetails)
                                                    MyAlertDialogFragment.this.getTargetFragment())
                                            .activeDetailForService(componentName);
                            if (activeDetailForService != null) {
                                activeDetailForService.stopActiveService(true);
                            }
                        }
                    });
            builder.setNegativeButton(R.string.dlg_cancel, (DialogInterface.OnClickListener) null);
            return builder.create();
        }
    }

    public final ActiveDetail activeDetailForService(ComponentName componentName) {
        ActivityManager.RunningServiceInfo runningServiceInfo;
        for (int i = 0; i < this.mActiveDetails.size(); i++) {
            ActiveDetail activeDetail = (ActiveDetail) this.mActiveDetails.get(i);
            RunningState.ServiceItem serviceItem = activeDetail.mServiceItem;
            if (serviceItem != null
                    && (runningServiceInfo = serviceItem.mRunningService) != null
                    && componentName.equals(runningServiceInfo.service)) {
                return activeDetail;
            }
        }
        return null;
    }

    public final void addDetailsViews(RunningState.MergedItem mergedItem, boolean z, boolean z2) {
        int i;
        CharSequence makeLabel;
        if (mergedItem != null) {
            if (z) {
                for (int i2 = 0; i2 < mergedItem.mServices.size(); i2++) {
                    addServiceDetailsView(
                            (RunningState.ServiceItem) mergedItem.mServices.get(i2),
                            mergedItem,
                            true,
                            true);
                }
            }
            if (z2) {
                if (mergedItem.mServices.size() <= 0) {
                    addServiceDetailsView(
                            null, mergedItem, false, mergedItem.mUserId != UserHandle.myUserId());
                    return;
                }
                int i3 = -1;
                while (i3 < mergedItem.mOtherProcesses.size()) {
                    RunningState.ProcessItem processItem =
                            i3 < 0
                                    ? mergedItem.mProcess
                                    : (RunningState.ProcessItem) mergedItem.mOtherProcesses.get(i3);
                    if (processItem == null || processItem.mPid > 0) {
                        boolean z3 = i3 < 0;
                        addProcessesHeader();
                        ActiveDetail activeDetail = new ActiveDetail();
                        View inflate =
                                this.mInflater.inflate(
                                        R.layout.running_service_details_process,
                                        this.mAllDetails,
                                        false);
                        this.mAllDetails.addView(inflate);
                        activeDetail.mRootView = inflate;
                        activeDetail.mActiveItem =
                                new RunningProcessesView.ViewHolder(inflate)
                                        .bind(this.mState, processItem, this.mBuilder);
                        TextView textView = (TextView) inflate.findViewById(R.id.comp_description);
                        if (processItem.mUserId != UserHandle.myUserId()) {
                            textView.setVisibility(8);
                        } else if (z3) {
                            textView.setText(R.string.main_running_process_description);
                        } else {
                            ActivityManager.RunningAppProcessInfo runningAppProcessInfo =
                                    processItem.mRunningProcessInfo;
                            ComponentName componentName =
                                    runningAppProcessInfo.importanceReasonComponent;
                            int i4 = runningAppProcessInfo.importanceReasonCode;
                            if (i4 == 1) {
                                i = R.string.process_provider_in_use_description;
                                if (componentName != null) {
                                    ProviderInfo providerInfo =
                                            getActivity()
                                                    .getPackageManager()
                                                    .getProviderInfo(
                                                            runningAppProcessInfo
                                                                    .importanceReasonComponent,
                                                            0);
                                    makeLabel =
                                            RunningState.makeLabel(
                                                    getActivity().getPackageManager(),
                                                    providerInfo.name,
                                                    providerInfo);
                                }
                                makeLabel = null;
                            } else if (i4 != 2) {
                                makeLabel = null;
                                i = 0;
                            } else {
                                i = R.string.process_service_in_use_description;
                                if (componentName != null) {
                                    try {
                                        ServiceInfo serviceInfo =
                                                getActivity()
                                                        .getPackageManager()
                                                        .getServiceInfo(
                                                                runningAppProcessInfo
                                                                        .importanceReasonComponent,
                                                                0);
                                        makeLabel =
                                                RunningState.makeLabel(
                                                        getActivity().getPackageManager(),
                                                        serviceInfo.name,
                                                        serviceInfo);
                                    } catch (PackageManager.NameNotFoundException unused) {
                                    }
                                }
                                makeLabel = null;
                            }
                            if (i != 0 && makeLabel != null) {
                                textView.setText(
                                        getActivity().getString(i, new Object[] {makeLabel}));
                            }
                        }
                        this.mActiveDetails.add(activeDetail);
                    }
                    i3++;
                }
            }
        }
    }

    public final void addProcessesHeader() {
        if (this.mNumProcesses == 0) {
            TextView textView =
                    (TextView)
                            this.mInflater.inflate(
                                    android.R.layout.preference_category, this.mAllDetails, false);
            this.mProcessesHeader = textView;
            textView.setText(R.string.runningservicedetails_processes_title);
            this.mAllDetails.addView(this.mProcessesHeader);
        }
        this.mNumProcesses++;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void addServiceDetailsView(
            RunningState.ServiceItem serviceItem,
            RunningState.MergedItem mergedItem,
            boolean z,
            boolean z2) {
        if (z) {
            if (this.mNumServices == 0) {
                TextView textView =
                        (TextView)
                                this.mInflater.inflate(
                                        android.R.layout.preference_category,
                                        this.mAllDetails,
                                        false);
                this.mServicesHeader = textView;
                textView.setText(R.string.runningservicedetails_services_title);
                this.mAllDetails.addView(this.mServicesHeader);
            }
            this.mNumServices++;
        } else if (mergedItem.mUserId != UserHandle.myUserId()) {
            addProcessesHeader();
        }
        RunningState.ServiceItem serviceItem2 = serviceItem != null ? serviceItem : mergedItem;
        ActiveDetail activeDetail = new ActiveDetail();
        View inflate =
                this.mInflater.inflate(
                        R.layout.running_service_details_service, this.mAllDetails, false);
        this.mAllDetails.addView(inflate);
        activeDetail.mRootView = inflate;
        activeDetail.mServiceItem = serviceItem;
        activeDetail.mActiveItem =
                new RunningProcessesView.ViewHolder(inflate)
                        .bind(this.mState, serviceItem2, this.mBuilder);
        if (!z2) {
            inflate.findViewById(R.id.service).setVisibility(8);
        }
        if (serviceItem != null) {
            ActivityManager.RunningServiceInfo runningServiceInfo = serviceItem.mRunningService;
            if (runningServiceInfo.clientLabel != 0) {
                activeDetail.mManageIntent =
                        this.mAm.getRunningServiceControlPanel(runningServiceInfo.service);
            }
        }
        TextView textView2 = (TextView) inflate.findViewById(R.id.comp_description);
        activeDetail.mStopButton = (Button) inflate.findViewById(R.id.left_button);
        activeDetail.mReportButton = (Button) inflate.findViewById(R.id.right_button);
        if (!z || mergedItem.mUserId == UserHandle.myUserId()) {
            if (serviceItem != null && serviceItem.mServiceInfo.descriptionRes != 0) {
                PackageManager packageManager = getActivity().getPackageManager();
                ServiceInfo serviceInfo = serviceItem.mServiceInfo;
                textView2.setText(
                        packageManager.getText(
                                serviceInfo.packageName,
                                serviceInfo.descriptionRes,
                                serviceInfo.applicationInfo));
            } else if (mergedItem.mBackground) {
                textView2.setText(R.string.background_process_stop_description);
            } else if (activeDetail.mManageIntent != null) {
                try {
                    textView2.setText(
                            getActivity()
                                    .getString(
                                            R.string.service_manage_description,
                                            new Object[] {
                                                getActivity()
                                                        .getPackageManager()
                                                        .getResourcesForApplication(
                                                                serviceItem
                                                                        .mRunningService
                                                                        .clientPackage)
                                                        .getString(
                                                                serviceItem
                                                                        .mRunningService
                                                                        .clientLabel)
                                            }));
                } catch (PackageManager.NameNotFoundException unused) {
                }
            } else {
                textView2.setText(
                        getActivity()
                                .getText(
                                        serviceItem != null
                                                ? R.string.service_stop_description
                                                : R.string.heavy_weight_stop_description));
            }
            activeDetail.mStopButton.setOnClickListener(activeDetail);
            activeDetail.mStopButton.setText(
                    getActivity()
                            .getText(
                                    activeDetail.mManageIntent != null
                                            ? R.string.service_manage
                                            : R.string.service_stop));
            activeDetail.mReportButton.setOnClickListener(activeDetail);
            activeDetail.mReportButton.setText(17042557);
            if (Settings.Global.getInt(
                                    getActivity().getContentResolver(), "send_action_app_error", 0)
                            == 0
                    || serviceItem == null) {
                activeDetail.mReportButton.setEnabled(false);
            } else {
                FragmentActivity activity = getActivity();
                ServiceInfo serviceInfo2 = serviceItem.mServiceInfo;
                ComponentName errorReportReceiver =
                        ApplicationErrorReport.getErrorReportReceiver(
                                activity,
                                serviceInfo2.packageName,
                                serviceInfo2.applicationInfo.flags);
                activeDetail.mInstaller = errorReportReceiver;
                activeDetail.mReportButton.setEnabled(errorReportReceiver != null);
            }
        } else {
            textView2.setVisibility(8);
            inflate.findViewById(R.id.control_buttons_panel).setVisibility(8);
        }
        this.mActiveDetails.add(activeDetail);
    }

    public final void ensureData() {
        if (this.mHaveData) {
            return;
        }
        this.mHaveData = true;
        this.mState.resume(this);
        RunningState runningState = this.mState;
        synchronized (runningState.mLock) {
            while (!runningState.mHaveData) {
                try {
                    runningState.mLock.wait(0L);
                } catch (InterruptedException unused) {
                }
            }
        }
        refreshUi(true);
    }

    public final void finish$2() {
        ThreadUtils.postOnMainThread(
                new Runnable() { // from class:
                                 // com.android.settings.applications.RunningServiceDetails$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        FragmentActivity activity = RunningServiceDetails.this.getActivity();
                        if (activity != null) {
                            activity.onBackPressed();
                        }
                    }
                });
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 85;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
        this.mUid = getArguments().getInt(NetworkAnalyticsConstants.DataPoints.UID, -1);
        this.mUserId =
                getArguments().getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, 0);
        this.mProcessName = getArguments().getString("process", null);
        this.mShowBackground = getArguments().getBoolean("background", false);
        this.mAm = (ActivityManager) getActivity().getSystemService("activity");
        this.mInflater = (LayoutInflater) getActivity().getSystemService("layout_inflater");
        this.mState = RunningState.getInstance(getActivity());
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.running_service_details, viewGroup, false);
        Utils.prepareCustomPreferencesList(viewGroup, inflate, inflate);
        this.mRootView = inflate;
        this.mAllDetails = (ViewGroup) inflate.findViewById(R.id.all_details);
        ViewGroup viewGroup2 = (ViewGroup) inflate.findViewById(R.id.snippet);
        this.mSnippet = viewGroup2;
        this.mSnippetViewHolder = new RunningProcessesView.ViewHolder(viewGroup2);
        ensureData();
        return inflate;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mHaveData = false;
        this.mState.pause();
    }

    @Override // com.android.settings.applications.RunningState.OnRefreshUiListener
    public final void onRefreshUi(int i) {
        if (getActivity() == null) {
            return;
        }
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

    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        ensureData();
    }

    public final void refreshUi(boolean z) {
        ArrayList arrayList;
        RunningState.MergedItem mergedItem;
        ArrayList arrayList2;
        int i;
        String str;
        RunningState.ProcessItem processItem;
        RunningState.ProcessItem processItem2;
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
        if (arrayList != null) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                mergedItem = (RunningState.MergedItem) arrayList.get(i2);
                if (mergedItem.mUserId == this.mUserId
                        && (((i = this.mUid) < 0
                                        || (processItem2 = mergedItem.mProcess) == null
                                        || processItem2.mUid == i)
                                && ((str = this.mProcessName) == null
                                        || ((processItem = mergedItem.mProcess) != null
                                                && str.equals(processItem.mProcessName))))) {
                    break;
                }
            }
        }
        mergedItem = null;
        if (this.mMergedItem != mergedItem) {
            this.mMergedItem = mergedItem;
            z = true;
        }
        if (z) {
            RunningState.MergedItem mergedItem2 = this.mMergedItem;
            if (mergedItem2 != null) {
                this.mSnippetActiveItem =
                        this.mSnippetViewHolder.bind(this.mState, mergedItem2, this.mBuilder);
            } else {
                RunningProcessesView.ActiveItem activeItem = this.mSnippetActiveItem;
                if (activeItem == null) {
                    finish$2();
                    return;
                } else {
                    activeItem.mHolder.size.setText(ApnSettings.MVNO_NONE);
                    this.mSnippetActiveItem.mHolder.uptime.setText(ApnSettings.MVNO_NONE);
                    this.mSnippetActiveItem.mHolder.description.setText(R.string.no_services);
                }
            }
            for (int size = this.mActiveDetails.size() - 1; size >= 0; size--) {
                this.mAllDetails.removeView(
                        ((ActiveDetail) this.mActiveDetails.get(size)).mRootView);
            }
            this.mActiveDetails.clear();
            TextView textView = this.mServicesHeader;
            if (textView != null) {
                this.mAllDetails.removeView(textView);
                this.mServicesHeader = null;
            }
            TextView textView2 = this.mProcessesHeader;
            if (textView2 != null) {
                this.mAllDetails.removeView(textView2);
                this.mProcessesHeader = null;
            }
            this.mNumProcesses = 0;
            this.mNumServices = 0;
            RunningState.MergedItem mergedItem3 = this.mMergedItem;
            if (mergedItem3 != null) {
                if (mergedItem3.mUser == null) {
                    addDetailsViews(mergedItem3, true, true);
                    return;
                }
                if (this.mShowBackground) {
                    arrayList2 = new ArrayList(this.mMergedItem.mChildren);
                    Collections.sort(arrayList2, this.mState.mBackgroundComparator);
                } else {
                    arrayList2 = mergedItem3.mChildren;
                }
                for (int i3 = 0; i3 < arrayList2.size(); i3++) {
                    addDetailsViews((RunningState.MergedItem) arrayList2.get(i3), true, false);
                }
                for (int i4 = 0; i4 < arrayList2.size(); i4++) {
                    addDetailsViews((RunningState.MergedItem) arrayList2.get(i4), false, true);
                }
            }
        }
    }

    public final void updateTimes$1() {
        RunningProcessesView.ActiveItem activeItem = this.mSnippetActiveItem;
        if (activeItem != null) {
            activeItem.updateTime(getActivity(), this.mBuilder);
        }
        for (int i = 0; i < this.mActiveDetails.size(); i++) {
            ((ActiveDetail) this.mActiveDetails.get(i))
                    .mActiveItem.updateTime(getActivity(), this.mBuilder);
        }
    }
}

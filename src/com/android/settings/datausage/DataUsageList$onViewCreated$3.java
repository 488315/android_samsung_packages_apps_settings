package com.android.settings.datausage;

import android.graphics.Typeface;
import android.os.UserHandle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.Log;

import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.datausage.lib.NetworkUsageData;
import com.android.settings.widget.LoadingViewController;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.datausage.DataUsageFeatureProviderImpl;
import com.samsung.android.settings.datausage.DataUsageUtilsCHN;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Job;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final /* synthetic */ class DataUsageList$onViewCreated$3 extends FunctionReferenceImpl
        implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((NetworkUsageData) obj);
        return Unit.INSTANCE;
    }

    public final void invoke(final NetworkUsageData p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        final DataUsageList dataUsageList = (DataUsageList) this.receiver;
        dataUsageList.getClass();
        Log.d(
                "DataUsageList",
                "showing cycle "
                        + p0
                        + " , start Time : "
                        + p0.startTime
                        + " , end Time : "
                        + p0.endTime);
        ((DataUsageListViewModel) dataUsageList.viewModel$delegate.getValue())
                .selectedCycleFlow.updateState(null, p0);
        DataUsageListAppsController dataUsageListAppsController =
                dataUsageList.dataUsageListAppsController;
        if (dataUsageListAppsController != null) {
            Job update = dataUsageListAppsController.update(0, p0.startTime, p0.endTime);
            if (update != null) {
                update.invokeOnCompletion(
                        new Function1() { // from class:
                                          // com.android.settings.datausage.DataUsageList$updateApps$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                CharSequence formatDataUsage;
                                DataUsageListAppsController dataUsageListAppsController2 =
                                        DataUsageList.this.dataUsageListAppsController;
                                Long valueOf =
                                        dataUsageListAppsController2 != null
                                                ? Long.valueOf(
                                                        dataUsageListAppsController2
                                                                .getSecureFolderUsage())
                                                : null;
                                if (SemPersonaManager.isSecureFolderId(UserHandle.myUserId())) {
                                    if (Rune.SUPPORT_SMARTMANAGER_CN) {
                                        FragmentActivity activity =
                                                DataUsageList.this.getActivity();
                                        Intrinsics.checkNotNull(valueOf);
                                        formatDataUsage =
                                                DataUsageUtilsCHN.formatDataUsage(
                                                        activity, valueOf.longValue());
                                    } else {
                                        DataUsageFeatureProviderImpl dataUsageFeatureProviderImpl =
                                                DataUsageList.this.mDataUsageFeatureProvider;
                                        Intrinsics.checkNotNull(dataUsageFeatureProviderImpl);
                                        FragmentActivity activity2 =
                                                DataUsageList.this.getActivity();
                                        Intrinsics.checkNotNull(valueOf);
                                        formatDataUsage =
                                                dataUsageFeatureProviderImpl.formatFileSize(
                                                        activity2, valueOf.longValue());
                                    }
                                    Intrinsics.checkNotNull(formatDataUsage);
                                } else {
                                    formatDataUsage =
                                            DataUsageUtils.formatDataUsage(
                                                    DataUsageList.this.getActivity(), p0.usage);
                                    Intrinsics.checkNotNull(formatDataUsage);
                                }
                                SpannableString spannableString =
                                        new SpannableString(
                                                DataUsageList.this
                                                        .requireContext()
                                                        .getString(
                                                                R.string.data_used_template,
                                                                DataUsageList.this
                                                                        .requireContext()
                                                                        .getString(
                                                                                R.string
                                                                                        .fileSizeSuffix,
                                                                                formatDataUsage,
                                                                                ApnSettings
                                                                                        .MVNO_NONE,
                                                                                ApnSettings
                                                                                        .MVNO_NONE)));
                                String string =
                                        DataUsageList.this
                                                .requireContext()
                                                .getString(
                                                        R.string.fileSizeSuffix,
                                                        formatDataUsage,
                                                        ApnSettings.MVNO_NONE,
                                                        ApnSettings.MVNO_NONE);
                                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                                int indexOf = TextUtils.indexOf(spannableString, string);
                                if (indexOf == -1) {
                                    spannableString.setSpan(
                                            new RelativeSizeSpan(0.5f),
                                            0,
                                            spannableString.length(),
                                            18);
                                } else {
                                    if (indexOf > 0) {
                                        spannableString.setSpan(
                                                new RelativeSizeSpan(0.5f), 0, indexOf, 18);
                                    }
                                    int length = string.length() + indexOf;
                                    if (length < spannableString.length()) {
                                        spannableString.setSpan(
                                                new RelativeSizeSpan(0.5f),
                                                length,
                                                spannableString.length(),
                                                18);
                                    }
                                    spannableString.setSpan(
                                            Typeface.create("sans-serif-medium", 0),
                                            indexOf,
                                            length,
                                            18);
                                }
                                Preference preference = DataUsageList.this.usageAmount;
                                if (preference != null) {
                                    preference.setTitle(spannableString);
                                }
                                NetworkUsageData networkUsageData = p0;
                                StringBuilder m =
                                        SnapshotStateObserver$$ExternalSyntheticOutline0.m(
                                                networkUsageData.startTime,
                                                "usage Start Time : ",
                                                ", End Time : ");
                                m.append(networkUsageData.endTime);
                                Log.i("DataUsageList", m.toString());
                                Log.i(
                                        "DataUsageList",
                                        "Total Usage : "
                                                + p0.usage
                                                + ", Secure Usage : "
                                                + valueOf);
                                LoadingViewController loadingViewController =
                                        DataUsageList.this.loadingViewController;
                                if (loadingViewController != null) {
                                    loadingViewController.showContent(false);
                                }
                                return Unit.INSTANCE;
                            }
                        });
            }
        }
    }
}

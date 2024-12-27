package com.android.settings.applications.intentpicker;

import android.os.SystemClock;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ProgressDialogFragment$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ProgressDialogFragment f$0;

    public /* synthetic */ ProgressDialogFragment$$ExternalSyntheticLambda1(
            ProgressDialogFragment progressDialogFragment, int i) {
        this.$r8$classId = i;
        this.f$0 = progressDialogFragment;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        final ProgressDialogFragment progressDialogFragment = this.f$0;
        switch (i) {
            case 0:
                progressDialogFragment.getClass();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                List linksList =
                        IntentPickerUtils.getLinksList(
                                progressDialogFragment.mDomainVerificationManager,
                                progressDialogFragment.mPackage,
                                0);
                int size = linksList.size();
                progressDialogFragment.mSupportedLinkWrapperList = new ArrayList();
                Iterator it = linksList.iterator();
                int i2 = 0;
                while (true) {
                    if (it.hasNext()) {
                        String str = (String) it.next();
                        SortedSet ownersForDomain =
                                progressDialogFragment.mDomainVerificationManager
                                        .getOwnersForDomain(str);
                        List list = progressDialogFragment.mSupportedLinkWrapperList;
                        FragmentActivity activity = progressDialogFragment.getActivity();
                        SupportedLinkWrapper supportedLinkWrapper = new SupportedLinkWrapper();
                        supportedLinkWrapper.mHost = str;
                        supportedLinkWrapper.mOwnerSet = ownersForDomain;
                        supportedLinkWrapper.mIsEnabled = true;
                        supportedLinkWrapper.mLastOwnerName = ApnSettings.MVNO_NONE;
                        supportedLinkWrapper.mIsChecked = false;
                        if (ownersForDomain.size() > 0) {
                            long count =
                                    ownersForDomain.stream()
                                            .filter(
                                                    new SupportedLinkWrapper$$ExternalSyntheticLambda0(
                                                            0))
                                            .count();
                            supportedLinkWrapper.mIsEnabled = count == 0;
                            if (count > 0) {
                                supportedLinkWrapper.mLastOwnerName =
                                        supportedLinkWrapper.getLastPackageLabel(activity, false);
                            } else {
                                supportedLinkWrapper.mLastOwnerName =
                                        supportedLinkWrapper.getLastPackageLabel(activity, true);
                            }
                        }
                        ((ArrayList) list).add(supportedLinkWrapper);
                        i2++;
                        if (progressDialogFragment.mProgressAlertDialog.isShowing()) {
                            final int i3 = (i2 * 100) / size;
                            progressDialogFragment.mHandle.post(
                                    new Runnable() { // from class:
                                                     // com.android.settings.applications.intentpicker.ProgressDialogFragment$$ExternalSyntheticLambda4
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            ProgressDialogFragment progressDialogFragment2 =
                                                    ProgressDialogFragment.this;
                                            int i4 = i3;
                                            synchronized (progressDialogFragment2.mHandle) {
                                                try {
                                                    if (progressDialogFragment2.mProgressAlertDialog
                                                            .isShowing()) {
                                                        progressDialogFragment2.mProgressAlertDialog
                                                                .mProgressBar.setProgress(i4);
                                                    } else {
                                                        Log.w(
                                                                "ProgressDialogFragment",
                                                                "Exit the UI thread");
                                                    }
                                                } finally {
                                                }
                                            }
                                        }
                                    });
                            if (ownersForDomain.size() == 0) {
                                SystemClock.sleep(20L);
                            }
                        } else {
                            Log.w("ProgressDialogFragment", "Exit the background thread!!!");
                            ((ArrayList) progressDialogFragment.mSupportedLinkWrapperList).clear();
                        }
                    }
                }
                IntentPickerUtils.logd(
                        "queryLinksInBackground : SupportedLinkWrapperList size="
                                + ((ArrayList) progressDialogFragment.mSupportedLinkWrapperList)
                                        .size());
                Collections.sort(progressDialogFragment.mSupportedLinkWrapperList);
                IntentPickerUtils.logd(
                        "queryLinksInBackground take time: "
                                + (SystemClock.elapsedRealtime() - elapsedRealtime));
                if (progressDialogFragment.mProgressAlertDialog.isShowing()) {
                    progressDialogFragment.mHandle.post(
                            new ProgressDialogFragment$$ExternalSyntheticLambda1(
                                    progressDialogFragment, 1));
                    return;
                }
                return;
            default:
                synchronized (progressDialogFragment.mHandle) {
                    try {
                        if (progressDialogFragment.mProgressAlertDialog.isShowing()) {
                            progressDialogFragment.mProgressAlertDialog.dismiss();
                            IntentPickerUtils.logd(
                                    "mProgressAlertDialog.dismiss() and isShowing: "
                                            + progressDialogFragment.mProgressAlertDialog
                                                    .isShowing());
                            progressDialogFragment.launchSupportedLinksDialogFragment();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
        }
    }
}

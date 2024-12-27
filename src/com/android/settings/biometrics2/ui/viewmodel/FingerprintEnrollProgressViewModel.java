package com.android.settings.biometrics2.ui.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.os.SystemClock;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.settings.R;
import com.android.settings.biometrics.fingerprint.FingerprintUpdater;
import com.android.settings.biometrics.fingerprint.MessageDisplayController;
import com.android.settings.biometrics2.ui.model.EnrollmentProgress;
import com.android.settings.biometrics2.ui.model.EnrollmentStatusMessage;

import java.util.LinkedList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FingerprintEnrollProgressViewModel extends AndroidViewModel {
    public final MutableLiveData mAcquireLiveData;
    public final MutableLiveData mCanceledSignalLiveData;
    public final LinkedList mCancelingSignalQueue;
    public CancellationSignal mCancellationSignal;
    public final AnonymousClass1 mEnrollmentCallback;
    public final MutableLiveData mErrorMessageLiveData;
    public final FingerprintUpdater mFingerprintUpdater;
    public final MutableLiveData mHelpMessageLiveData;
    public final MutableLiveData mPointerDownLiveData;
    public final MutableLiveData mPointerUpLiveData;
    public final MutableLiveData mProgressLiveData;
    public byte[] mToken;
    public final int mUserId;

    /* JADX WARN: Type inference failed for: r4v10, types: [com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollProgressViewModel$1] */
    public FingerprintEnrollProgressViewModel(
            Application application, FingerprintUpdater fingerprintUpdater, int i) {
        super(application);
        this.mProgressLiveData = new MutableLiveData(new EnrollmentProgress(-1, 0));
        this.mHelpMessageLiveData = new MutableLiveData();
        this.mErrorMessageLiveData = new MutableLiveData();
        this.mCanceledSignalLiveData = new MutableLiveData();
        this.mAcquireLiveData = new MutableLiveData();
        this.mPointerDownLiveData = new MutableLiveData();
        this.mPointerUpLiveData = new MutableLiveData();
        this.mToken = null;
        this.mCancellationSignal = null;
        this.mCancelingSignalQueue = new LinkedList();
        this.mEnrollmentCallback =
                new FingerprintManager
                        .EnrollmentCallback() { // from class:
                                                // com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollProgressViewModel.1
                    public final void onAcquired(boolean z) {
                        FingerprintEnrollProgressViewModel.this.mAcquireLiveData.postValue(
                                Boolean.valueOf(z));
                    }

                    public final void onEnrollmentError(int i2, CharSequence charSequence) {
                        Log.d(
                                "FingerprintEnrollProgressViewModel",
                                "onEnrollmentError("
                                        + i2
                                        + ", "
                                        + ((Object) charSequence)
                                        + "), cancelingQueueSize:"
                                        + FingerprintEnrollProgressViewModel.this
                                                .mCancelingSignalQueue.size());
                        if (5 != i2
                                || FingerprintEnrollProgressViewModel.this.mCancelingSignalQueue
                                                .size()
                                        <= 0) {
                            FingerprintEnrollProgressViewModel.this.mErrorMessageLiveData.postValue(
                                    new EnrollmentStatusMessage(i2, charSequence));
                        } else {
                            FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel =
                                    FingerprintEnrollProgressViewModel.this;
                            fingerprintEnrollProgressViewModel.mCanceledSignalLiveData.postValue(
                                    fingerprintEnrollProgressViewModel.mCancelingSignalQueue
                                            .poll());
                        }
                    }

                    public final void onEnrollmentHelp(int i2, CharSequence charSequence) {
                        FingerprintEnrollProgressViewModel.this.mHelpMessageLiveData.postValue(
                                new EnrollmentStatusMessage(i2, charSequence));
                    }

                    public final void onEnrollmentProgress(int i2) {
                        EnrollmentProgress enrollmentProgress =
                                new EnrollmentProgress(
                                        ((EnrollmentProgress)
                                                                        FingerprintEnrollProgressViewModel
                                                                                .this
                                                                                .mProgressLiveData
                                                                                .getValue())
                                                                .steps
                                                        == -1
                                                ? i2
                                                : ((EnrollmentProgress)
                                                                FingerprintEnrollProgressViewModel
                                                                        .this
                                                                        .mProgressLiveData
                                                                        .getValue())
                                                        .steps,
                                        i2);
                        FingerprintEnrollProgressViewModel.this.mHelpMessageLiveData.setValue(null);
                        FingerprintEnrollProgressViewModel.this.mProgressLiveData.postValue(
                                enrollmentProgress);
                    }

                    public final void onUdfpsPointerDown(int i2) {
                        FingerprintEnrollProgressViewModel.this.mPointerDownLiveData.postValue(
                                Integer.valueOf(i2));
                    }

                    public final void onUdfpsPointerUp(int i2) {
                        FingerprintEnrollProgressViewModel.this.mPointerUpLiveData.postValue(
                                Integer.valueOf(i2));
                    }
                };
        this.mFingerprintUpdater = fingerprintUpdater;
        this.mUserId = i;
    }

    public final boolean cancelEnrollment() {
        CancellationSignal cancellationSignal = this.mCancellationSignal;
        this.mCancellationSignal = null;
        if (cancellationSignal == null) {
            Log.e(
                    "FingerprintEnrollProgressViewModel",
                    "Fail to cancel enrollment, has cancelled or not start");
            return false;
        }
        Log.d("FingerprintEnrollProgressViewModel", "enrollment cancelled");
        this.mCancelingSignalQueue.add(cancellationSignal);
        cancellationSignal.cancel();
        return true;
    }

    public final void clearProgressLiveData() {
        this.mProgressLiveData.setValue(new EnrollmentProgress(-1, 0));
        this.mHelpMessageLiveData.setValue(null);
        this.mErrorMessageLiveData.setValue(null);
    }

    public final boolean isEnrolling() {
        return this.mCancellationSignal != null;
    }

    public final Object startEnrollment(int i) {
        if (this.mToken == null) {
            Log.e("FingerprintEnrollProgressViewModel", "Null hardware auth token for enroll");
            return null;
        }
        if (this.mCancellationSignal != null) {
            Log.w(
                    "FingerprintEnrollProgressViewModel",
                    "Enrolling is running, shall not start again");
            return this.mCancellationSignal;
        }
        this.mProgressLiveData.setValue(new EnrollmentProgress(-1, 0));
        this.mHelpMessageLiveData.setValue(null);
        this.mErrorMessageLiveData.setValue(null);
        this.mCancellationSignal = new CancellationSignal();
        Resources resources = getApplication().getResources();
        if (i == 2 && resources.getBoolean(R.bool.enrollment_message_display_controller_flag)) {
            MessageDisplayController messageDisplayController =
                    new MessageDisplayController(
                            getApplication().getMainThreadHandler(),
                            this.mEnrollmentCallback,
                            SystemClock.elapsedRealtimeClock(),
                            resources.getInteger(R.integer.enrollment_help_minimum_time_display),
                            resources.getInteger(
                                    R.integer.enrollment_progress_minimum_time_display),
                            resources.getBoolean(R.bool.enrollment_progress_priority_over_help),
                            resources.getBoolean(R.bool.enrollment_prioritize_acquire_messages),
                            resources.getInteger(R.integer.enrollment_collect_time));
            this.mFingerprintUpdater.enroll(
                    this.mToken,
                    this.mCancellationSignal,
                    this.mUserId,
                    messageDisplayController,
                    i,
                    new Intent());
        } else {
            this.mFingerprintUpdater.enroll(
                    this.mToken,
                    this.mCancellationSignal,
                    this.mUserId,
                    this.mEnrollmentCallback,
                    i,
                    new Intent());
        }
        return this.mCancellationSignal;
    }
}

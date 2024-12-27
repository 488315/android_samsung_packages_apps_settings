package com.android.settings.biometrics;

import android.app.Activity;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;

import com.android.settings.core.InstrumentedFragment;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BiometricEnrollSidecar extends InstrumentedFragment {
    public boolean mEnrolling;
    public CancellationSignal mEnrollmentCancel;
    public Listener mListener;
    public byte[] mToken;
    public int mUserId;
    public int mEnrollmentSteps = -1;
    public int mEnrollmentRemaining = 0;
    public final Handler mHandler = new Handler();
    public final AnonymousClass1 mTimeoutRunnable =
            new Runnable() { // from class: com.android.settings.biometrics.BiometricEnrollSidecar.1
                @Override // java.lang.Runnable
                public final void run() {
                    BiometricEnrollSidecar.this.cancelEnrollment();
                }
            };
    public final ArrayList mQueuedEvents = new ArrayList();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class QueuedAcquired extends QueuedEvent {
        public final boolean isAcquiredGood;

        public QueuedAcquired(boolean z) {
            this.isAcquiredGood = z;
        }

        @Override // com.android.settings.biometrics.BiometricEnrollSidecar.QueuedEvent
        public final void send(Listener listener) {
            listener.onAcquired(this.isAcquiredGood);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class QueuedEnrollmentHelp extends QueuedEvent {
        public final /* synthetic */ int $r8$classId;
        public int helpMsgId;
        public CharSequence helpString;

        public /* synthetic */ QueuedEnrollmentHelp(int i) {
            this.$r8$classId = i;
        }

        @Override // com.android.settings.biometrics.BiometricEnrollSidecar.QueuedEvent
        public final void send(Listener listener) {
            switch (this.$r8$classId) {
                case 0:
                    listener.onEnrollmentHelp(this.helpMsgId, this.helpString);
                    break;
                default:
                    listener.onEnrollmentError(this.helpMsgId, this.helpString);
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class QueuedEnrollmentProgress extends QueuedEvent {
        public int enrollmentSteps;
        public int remaining;

        @Override // com.android.settings.biometrics.BiometricEnrollSidecar.QueuedEvent
        public final void send(Listener listener) {
            listener.onEnrollmentProgressChange(this.enrollmentSteps, this.remaining);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class QueuedEvent {
        public abstract void send(Listener listener);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class QueuedUdfpsOverlayShown extends QueuedEvent {
        @Override // com.android.settings.biometrics.BiometricEnrollSidecar.QueuedEvent
        public final void send(Listener listener) {
            listener.onUdfpsOverlayShown();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class QueuedUdfpsPointerUp extends QueuedEvent {
        public final /* synthetic */ int $r8$classId;
        public final int sensorId;

        @Override // com.android.settings.biometrics.BiometricEnrollSidecar.QueuedEvent
        public final void send(Listener listener) {
            switch (this.$r8$classId) {
                case 0:
                    listener.onUdfpsPointerUp();
                    break;
                default:
                    listener.onUdfpsPointerDown();
                    break;
            }
        }
    }

    public final boolean cancelEnrollment() {
        this.mHandler.removeCallbacks(this.mTimeoutRunnable);
        if (!this.mEnrolling) {
            return false;
        }
        this.mEnrollmentCancel.cancel();
        this.mEnrolling = false;
        this.mEnrollmentSteps = -1;
        return true;
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mToken = activity.getIntent().getByteArrayExtra("hw_auth_token");
        this.mUserId = activity.getIntent().getIntExtra("android.intent.extra.USER_ID", -10000);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
    }

    public final void onEnrollmentError(int i, CharSequence charSequence) {
        Listener listener = this.mListener;
        if (listener != null) {
            listener.onEnrollmentError(i, charSequence);
        } else {
            ArrayList arrayList = this.mQueuedEvents;
            QueuedEnrollmentHelp queuedEnrollmentHelp = new QueuedEnrollmentHelp(1);
            queuedEnrollmentHelp.helpMsgId = i;
            queuedEnrollmentHelp.helpString = charSequence;
            arrayList.add(queuedEnrollmentHelp);
        }
        this.mEnrolling = false;
    }

    public final void onEnrollmentProgress(int i) {
        if (this.mEnrollmentSteps == -1) {
            this.mEnrollmentSteps = i;
        }
        this.mEnrollmentRemaining = i;
        Listener listener = this.mListener;
        if (listener != null) {
            listener.onEnrollmentProgressChange(this.mEnrollmentSteps, i);
            return;
        }
        ArrayList arrayList = this.mQueuedEvents;
        int i2 = this.mEnrollmentSteps;
        QueuedEnrollmentProgress queuedEnrollmentProgress = new QueuedEnrollmentProgress();
        queuedEnrollmentProgress.enrollmentSteps = i2;
        queuedEnrollmentProgress.remaining = i;
        arrayList.add(queuedEnrollmentProgress);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        if (this.mEnrolling) {
            return;
        }
        startEnrollment();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        if (getActivity().isChangingConfigurations()) {
            return;
        }
        cancelEnrollment();
    }

    public final void setListener(Listener listener) {
        this.mListener = listener;
        if (listener != null) {
            for (int i = 0; i < this.mQueuedEvents.size(); i++) {
                ((QueuedEvent) this.mQueuedEvents.get(i)).send(this.mListener);
            }
            this.mQueuedEvents.clear();
        }
    }

    public void startEnrollment() {
        this.mHandler.removeCallbacks(this.mTimeoutRunnable);
        this.mEnrollmentSteps = -1;
        this.mEnrollmentCancel = new CancellationSignal();
        this.mEnrolling = true;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Listener {
        void onEnrollmentError(int i, CharSequence charSequence);

        void onEnrollmentHelp(int i, CharSequence charSequence);

        void onEnrollmentProgressChange(int i, int i2);

        default void onUdfpsOverlayShown() {}

        default void onUdfpsPointerDown() {}

        default void onUdfpsPointerUp() {}

        default void onAcquired(boolean z) {}
    }
}

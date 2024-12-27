package com.android.settings.biometrics.fingerprint;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Handler;
import java.time.Clock;
import java.util.ArrayDeque;
import java.util.Deque;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class MessageDisplayController extends FingerprintManager.EnrollmentCallback {
    public final Clock mClock;
    public final int mCollectTime;
    public final MessageDisplayController$$ExternalSyntheticLambda0 mDisplayMessageRunnable;
    public final FingerprintManager.EnrollmentCallback mEnrollmentCallback;
    public final Handler mHandler;
    public final int mHelpMinimumDisplayTime;
    public ProgressMessage mLastProgressMessageDisplayed;
    public boolean mMustDisplayProgress;
    public final boolean mPrioritizeAcquireMessages;
    public final int mProgressMinimumDisplayTime;
    public final boolean mProgressPriorityOverHelp;
    public boolean mWaitingForMessage = false;
    public final Deque mHelpMessageList = new ArrayDeque();
    public final Deque mProgressMessageList = new ArrayDeque();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class HelpMessage extends Message {
        public final int mHelpMsgId;
        public final CharSequence mHelpString;

        public HelpMessage(int i, CharSequence charSequence) {
            this.mHelpMsgId = i;
            this.mHelpString = charSequence;
            this.mTimeStamp = MessageDisplayController.this.mClock.millis();
        }

        @Override // com.android.settings.biometrics.fingerprint.MessageDisplayController.Message
        public final void display() {
            MessageDisplayController messageDisplayController = MessageDisplayController.this;
            messageDisplayController.mEnrollmentCallback.onEnrollmentHelp(this.mHelpMsgId, this.mHelpString);
            messageDisplayController.mHandler.postDelayed(messageDisplayController.mDisplayMessageRunnable, messageDisplayController.mHelpMinimumDisplayTime);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class Message {
        public long mTimeStamp = 0;

        public abstract void display();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ProgressMessage extends Message {
        public final int mRemaining;

        public ProgressMessage(int i) {
            this.mRemaining = i;
            this.mTimeStamp = MessageDisplayController.this.mClock.millis();
        }

        @Override // com.android.settings.biometrics.fingerprint.MessageDisplayController.Message
        public final void display() {
            MessageDisplayController messageDisplayController = MessageDisplayController.this;
            messageDisplayController.mEnrollmentCallback.onEnrollmentProgress(this.mRemaining);
            messageDisplayController.mLastProgressMessageDisplayed = this;
            messageDisplayController.mHandler.postDelayed(messageDisplayController.mDisplayMessageRunnable, messageDisplayController.mProgressMinimumDisplayTime);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.settings.biometrics.fingerprint.MessageDisplayController$$ExternalSyntheticLambda0, java.lang.Runnable] */
    public MessageDisplayController(Handler handler, FingerprintManager.EnrollmentCallback enrollmentCallback, Clock clock, int i, int i2, boolean z, boolean z2, int i3) {
        this.mClock = clock;
        this.mHandler = handler;
        this.mEnrollmentCallback = enrollmentCallback;
        this.mHelpMinimumDisplayTime = i;
        this.mProgressMinimumDisplayTime = i2;
        this.mProgressPriorityOverHelp = z;
        this.mPrioritizeAcquireMessages = z2;
        this.mCollectTime = i3;
        ?? r2 = new Runnable() { // from class: com.android.settings.biometrics.fingerprint.MessageDisplayController$$ExternalSyntheticLambda0
            /* JADX WARN: Removed duplicated region for block: B:25:0x00e9  */
            /* JADX WARN: Removed duplicated region for block: B:28:0x00ed  */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void run() {
                /*
                    Method dump skipped, instructions count: 240
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.settings.biometrics.fingerprint.MessageDisplayController$$ExternalSyntheticLambda0.run():void");
            }
        };
        this.mDisplayMessageRunnable = r2;
        handler.postDelayed(r2, 0L);
    }

    public final void onAcquired(boolean z) {
        this.mEnrollmentCallback.onAcquired(z);
    }

    public final void onEnrollmentError(int i, CharSequence charSequence) {
        this.mEnrollmentCallback.onEnrollmentError(i, charSequence);
    }

    public final void onEnrollmentHelp(int i, CharSequence charSequence) {
        ((ArrayDeque) this.mHelpMessageList).add(new HelpMessage(i, charSequence));
        if (this.mWaitingForMessage) {
            this.mWaitingForMessage = false;
            this.mHandler.postDelayed(this.mDisplayMessageRunnable, this.mCollectTime);
        }
    }

    public final void onEnrollmentProgress(int i) {
        ((ArrayDeque) this.mProgressMessageList).add(new ProgressMessage(i));
        if (this.mWaitingForMessage) {
            this.mWaitingForMessage = false;
            this.mHandler.postDelayed(this.mDisplayMessageRunnable, this.mCollectTime);
        }
    }
}

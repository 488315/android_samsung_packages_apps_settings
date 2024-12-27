package com.android.settings.network;

import android.content.Context;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class MobileDataEnabledListener {
    public final Client mClient;
    public final Context mContext;
    public AnonymousClass1 mListener;
    public AnonymousClass1 mListenerForSubId;
    public int mSubId = -1;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.network.MobileDataEnabledListener$1, reason: invalid class name */
    public final class AnonymousClass1 extends GlobalSettingsChangeListener {
        public final /* synthetic */ int $r8$classId = 0;

        public AnonymousClass1(Context context) {
            super(context, "mobile_data");
        }

        @Override // com.android.settings.network.GlobalSettingsChangeListener
        public final void onChanged$1() {
            switch (this.$r8$classId) {
                case 0:
                    MobileDataEnabledListener.this.mClient.onMobileDataEnabledChange();
                    break;
                default:
                    MobileDataEnabledListener mobileDataEnabledListener =
                            MobileDataEnabledListener.this;
                    AnonymousClass1 anonymousClass1 = mobileDataEnabledListener.mListener;
                    if (anonymousClass1 != null) {
                        anonymousClass1.close();
                        mobileDataEnabledListener.mListener = null;
                    }
                    MobileDataEnabledListener.this.mClient.onMobileDataEnabledChange();
                    break;
            }
        }

        public AnonymousClass1(Context context, String str) {
            super(context, str);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Client {
        void onMobileDataEnabledChange();
    }

    public MobileDataEnabledListener(Context context, Client client) {
        this.mContext = context;
        this.mClient = client;
    }

    public final void start(int i) {
        this.mSubId = i;
        if (this.mListener == null) {
            this.mListener = new AnonymousClass1(this.mContext);
        }
        AnonymousClass1 anonymousClass1 = this.mListenerForSubId;
        if (anonymousClass1 != null) {
            anonymousClass1.close();
            this.mListenerForSubId = null;
        }
        if (this.mSubId == -1) {
            return;
        }
        this.mListenerForSubId = new AnonymousClass1(this.mContext, "mobile_data" + this.mSubId);
    }

    public final void stop() {
        AnonymousClass1 anonymousClass1 = this.mListener;
        if (anonymousClass1 != null) {
            anonymousClass1.close();
            this.mListener = null;
        }
        AnonymousClass1 anonymousClass12 = this.mListenerForSubId;
        if (anonymousClass12 != null) {
            anonymousClass12.close();
            this.mListenerForSubId = null;
        }
    }
}

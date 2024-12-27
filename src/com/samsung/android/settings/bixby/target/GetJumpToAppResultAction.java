package com.samsung.android.settings.bixby.target;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.bixby.target.actions.Action;
import com.samsung.android.util.SemLog;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class GetJumpToAppResultAction extends Action {
    public AnonymousClass1 mHandler;
    public CountDownLatch mLatch;
    public String mResult;

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGetAction() {
        if (TextUtils.isEmpty(getValue())) {
            return Action.createResult("fail");
        }
        Uri build =
                new Uri.Builder()
                        .scheme("content")
                        .authority(
                                "com.samsung.android.settings.intelligence.search.provider.SettingSearchProvider")
                        .build();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isDexMode", Rune.isSamsungDexMode(this.mContext));
        this.mContext
                .getContentResolver()
                .call(build, "startIndexing", ApnSettings.MVNO_NONE, bundle);
        AnonymousClass1 anonymousClass1 = this.mHandler;
        anonymousClass1.sendMessageDelayed(anonymousClass1.obtainMessage(1), 500L);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        this.mLatch = countDownLatch;
        try {
            countDownLatch.await(7, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            SemLog.d("GetJumpToAppResultAction", "fail to CountDownLatch.await : " + e);
        }
        anonymousClass1.removeMessages(1);
        return Action.createResult(this.mResult);
    }
}

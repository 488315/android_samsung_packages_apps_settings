package com.android.settings.network.helper;

import android.content.Context;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;

import androidx.annotation.Keep;

import com.android.settings.network.SubscriptionUtil;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SubscriptionAnnotation {
    public final boolean mIsActive;
    public final boolean mIsAllowToDisplay;
    public final boolean mIsExisted;
    public final SubscriptionInfo mSubInfo;
    public final int mType;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder {
        public final int mIndexWithinList;
        public final List mSubInfoList;

        public Builder(int i, List list) {
            this.mSubInfoList = list;
            this.mIndexWithinList = i;
        }
    }

    @Keep
    public SubscriptionAnnotation(
            List<SubscriptionInfo> list, int i, Context context, List<Integer> list2) {
        this.mType = 0;
        if (i < 0 || i >= list.size()) {
            return;
        }
        SubscriptionInfo subscriptionInfo = list.get(i);
        this.mSubInfo = subscriptionInfo;
        if (subscriptionInfo == null) {
            return;
        }
        int i2 = subscriptionInfo.isEmbedded() ? 2 : 1;
        this.mType = i2;
        this.mIsExisted = true;
        if (i2 == 2) {
            int cardId = subscriptionInfo.getCardId();
            this.mIsActive = list2.contains(Integer.valueOf(subscriptionInfo.getSimSlotIndex()));
            this.mIsAllowToDisplay =
                    cardId < 0
                            || SubscriptionUtil.isSubscriptionVisible(
                                    (SubscriptionManager)
                                            context.getSystemService(SubscriptionManager.class),
                                    context,
                                    this.mSubInfo);
        } else {
            if (subscriptionInfo.getSimSlotIndex() > -1
                    && list2.contains(Integer.valueOf(subscriptionInfo.getSimSlotIndex()))) {
                r0 = true;
            }
            this.mIsActive = r0;
            this.mIsAllowToDisplay =
                    SubscriptionUtil.isSubscriptionVisible(
                            (SubscriptionManager)
                                    context.getSystemService(SubscriptionManager.class),
                            context,
                            this.mSubInfo);
        }
    }

    @Keep
    public SubscriptionInfo getSubInfo() {
        return this.mSubInfo;
    }

    @Keep
    public int getSubscriptionId() {
        SubscriptionInfo subscriptionInfo = this.mSubInfo;
        if (subscriptionInfo == null) {
            return -1;
        }
        return subscriptionInfo.getSubscriptionId();
    }

    @Keep
    public int getType() {
        return this.mType;
    }

    @Keep
    public boolean isActive() {
        return this.mIsActive;
    }

    @Keep
    public boolean isDisplayAllowed() {
        return this.mIsAllowToDisplay;
    }

    @Keep
    public boolean isExisted() {
        return this.mIsExisted;
    }

    public final String toString() {
        return "SubscriptionAnnotation{subId="
                + getSubscriptionId()
                + ",type="
                + getType()
                + ",exist="
                + isExisted()
                + ",active="
                + isActive()
                + ",displayAllow="
                + isDisplayAllowed()
                + "}";
    }
}

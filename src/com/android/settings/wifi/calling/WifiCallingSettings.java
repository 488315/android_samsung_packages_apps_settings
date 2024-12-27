package com.android.settings.wifi.calling;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.ViewPager;

import com.android.internal.util.CollectionUtils;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.network.ActiveSubscriptionsListener;
import com.android.settings.network.SubscriptionUtil;
import com.android.settings.network.ims.WifiCallingQueryImsState;
import com.android.settings.network.telephony.MobileNetworkUtils;
import com.android.settings.network.telephony.SubscriptionRepositoryKt;
import com.android.settings.widget.RtlCompatibleViewPager;
import com.android.settings.widget.SlidingTabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiCallingSettings extends SettingsPreferenceFragment {
    public static final int[] EMPTY_SUB_ID_LIST = new int[0];
    public int mConstructionSubId;
    public List mSil;
    public ActiveSubscriptionsListener mSubscriptionChangeListener;
    public SlidingTabLayout mTabLayout;
    public RtlCompatibleViewPager mViewPager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class WifiCallingViewPagerAdapter extends FragmentPagerAdapter {
        public final RtlCompatibleViewPager mViewPager;

        public WifiCallingViewPagerAdapter(
                FragmentManager fragmentManager, RtlCompatibleViewPager rtlCompatibleViewPager) {
            super(fragmentManager);
            this.mViewPager = rtlCompatibleViewPager;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final int getCount() {
            WifiCallingSettings wifiCallingSettings = WifiCallingSettings.this;
            if (wifiCallingSettings.mSil == null) {
                Log.d("WifiCallingSettings", "Adapter getCount null mSil ");
                return 0;
            }
            Log.d("WifiCallingSettings", "Adapter getCount " + wifiCallingSettings.mSil.size());
            return wifiCallingSettings.mSil.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final CharSequence getPageTitle(int i) {
            WifiCallingSettings wifiCallingSettings = WifiCallingSettings.this;
            return String.valueOf(
                    SubscriptionUtil.getUniqueSubscriptionDisplayName(
                            wifiCallingSettings.getContext(),
                            (SubscriptionInfo) wifiCallingSettings.mSil.get(i)));
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final Object instantiateItem(ViewGroup viewGroup, int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i, "Adapter instantiateItem ", "WifiCallingSettings");
            int rtlAwareIndex = this.mViewPager.getRtlAwareIndex(i);
            BackStackRecord backStackRecord = this.mCurTransaction;
            FragmentManager fragmentManager = this.mFragmentManager;
            if (backStackRecord == null) {
                this.mCurTransaction =
                        DialogFragment$$ExternalSyntheticOutline0.m(
                                fragmentManager, fragmentManager);
            }
            long j = rtlAwareIndex;
            Fragment findFragmentByTag =
                    fragmentManager.findFragmentByTag(
                            "android:switcher:" + viewGroup.getId() + ":" + j);
            if (findFragmentByTag != null) {
                this.mCurTransaction.attach(findFragmentByTag);
            } else {
                int subscriptionId =
                        ((SubscriptionInfo) WifiCallingSettings.this.mSil.get(rtlAwareIndex))
                                .getSubscriptionId();
                Log.d(
                        "WifiCallingSettings",
                        "Adapter getItem " + rtlAwareIndex + " for subId=" + subscriptionId);
                Bundle bundle = new Bundle();
                bundle.putBoolean("need_search_icon_in_action_bar", false);
                bundle.putInt("subId", subscriptionId);
                findFragmentByTag = new WifiCallingSettingsForSub();
                findFragmentByTag.setArguments(bundle);
                this.mCurTransaction.doAddOp(
                        viewGroup.getId(),
                        findFragmentByTag,
                        "android:switcher:" + viewGroup.getId() + ":" + j,
                        1);
            }
            if (findFragmentByTag != this.mCurrentPrimaryItem) {
                findFragmentByTag.setMenuVisibility(false);
                if (this.mBehavior == 1) {
                    this.mCurTransaction.setMaxLifecycle(
                            findFragmentByTag, Lifecycle.State.STARTED);
                } else {
                    findFragmentByTag.setUserVisibleHint(false);
                }
            }
            return findFragmentByTag;
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 105;
    }

    public List<SubscriptionInfo> getSelectableSubscriptions(Context context) {
        Pattern pattern = SubscriptionUtil.REGEX_DISPLAY_NAME_SUFFIX_PATTERN;
        return SubscriptionRepositoryKt.getSelectableSubscriptionInfoList(context);
    }

    public ActiveSubscriptionsListener getSubscriptionChangeListener(Context context) {
        return new ActiveSubscriptionsListener(
                context.getMainLooper(),
                context,
                context) { // from class: com.android.settings.wifi.calling.WifiCallingSettings.1
            /* JADX WARN: Code restructure failed: missing block: B:21:0x0066, code lost:

               if (java.util.Arrays.stream(r1).anyMatch(new com.android.settings.wifi.calling.WifiCallingSettings$$ExternalSyntheticLambda1(r3)) != false) goto L24;
            */
            @Override // com.android.settings.network.ActiveSubscriptionsListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onChanged() {
                /*
                    r6 = this;
                    com.android.settings.wifi.calling.WifiCallingSettings r6 = com.android.settings.wifi.calling.WifiCallingSettings.this
                    com.android.settings.network.ActiveSubscriptionsListener r0 = r6.mSubscriptionChangeListener
                    if (r0 != 0) goto L8
                    goto La6
                L8:
                    java.util.List r0 = r6.mSil
                    int[] r1 = com.android.settings.wifi.calling.WifiCallingSettings.EMPTY_SUB_ID_LIST
                    if (r0 != 0) goto L10
                    r0 = r1
                    goto L21
                L10:
                    java.util.stream.Stream r0 = r0.stream()
                    com.android.settings.wifi.calling.WifiCallingSettings$$ExternalSyntheticLambda0 r2 = new com.android.settings.wifi.calling.WifiCallingSettings$$ExternalSyntheticLambda0
                    r2.<init>()
                    java.util.stream.IntStream r0 = r0.mapToInt(r2)
                    int[] r0 = r0.toArray()
                L21:
                    java.util.List r2 = r6.updateSubList()
                    if (r2 != 0) goto L28
                    goto L39
                L28:
                    java.util.stream.Stream r1 = r2.stream()
                    com.android.settings.wifi.calling.WifiCallingSettings$$ExternalSyntheticLambda0 r3 = new com.android.settings.wifi.calling.WifiCallingSettings$$ExternalSyntheticLambda0
                    r3.<init>()
                    java.util.stream.IntStream r1 = r1.mapToInt(r3)
                    int[] r1 = r1.toArray()
                L39:
                    int r3 = r1.length
                    if (r3 <= 0) goto L6b
                    int r3 = r0.length
                    if (r3 != 0) goto L42
                    r6.mSil = r2
                    goto La6
                L42:
                    int r3 = r0.length
                    int r4 = r1.length
                    if (r3 != r4) goto L6b
                    int r3 = r6.mConstructionSubId
                    java.util.stream.IntStream r4 = java.util.Arrays.stream(r0)
                    com.android.settings.wifi.calling.WifiCallingSettings$$ExternalSyntheticLambda1 r5 = new com.android.settings.wifi.calling.WifiCallingSettings$$ExternalSyntheticLambda1
                    r5.<init>()
                    boolean r3 = r4.anyMatch(r5)
                    if (r3 == 0) goto L68
                    int r3 = r6.mConstructionSubId
                    java.util.stream.IntStream r4 = java.util.Arrays.stream(r1)
                    com.android.settings.wifi.calling.WifiCallingSettings$$ExternalSyntheticLambda1 r5 = new com.android.settings.wifi.calling.WifiCallingSettings$$ExternalSyntheticLambda1
                    r5.<init>()
                    boolean r3 = r4.anyMatch(r5)
                    if (r3 == 0) goto L6b
                L68:
                    r6.mSil = r2
                    goto La6
                L6b:
                    java.lang.StringBuilder r2 = new java.lang.StringBuilder
                    java.lang.String r3 = "Closed subId="
                    r2.<init>(r3)
                    int r3 = r6.mConstructionSubId
                    r2.append(r3)
                    java.lang.String r3 = " due to subscription change: "
                    r2.append(r3)
                    java.lang.String r0 = java.util.Arrays.toString(r0)
                    r2.append(r0)
                    java.lang.String r0 = " -> "
                    r2.append(r0)
                    java.lang.String r0 = java.util.Arrays.toString(r1)
                    r2.append(r0)
                    java.lang.String r0 = r2.toString()
                    java.lang.String r1 = "WifiCallingSettings"
                    android.util.Log.d(r1, r0)
                    com.android.settings.network.ActiveSubscriptionsListener r0 = r6.mSubscriptionChangeListener
                    if (r0 == 0) goto La3
                    r1 = 0
                    r0.monitorSubscriptionsChange(r1)
                    r0 = 0
                    r6.mSubscriptionChangeListener = r0
                La3:
                    r6.finishFragment()
                La6:
                    return
                */
                throw new UnsupportedOperationException(
                        "Method not decompiled:"
                            + " com.android.settings.wifi.calling.WifiCallingSettings.AnonymousClass1.onChanged():void");
            }
        };
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Intent intent = getActivity().getIntent();
        int intExtra =
                intent != null ? intent.getIntExtra("android.provider.extra.SUB_ID", -1) : -1;
        if (intExtra == -1 && bundle != null) {
            intExtra = bundle.getInt("android.provider.extra.SUB_ID", -1);
        }
        this.mConstructionSubId = intExtra;
        super.onCreate(bundle);
        if (MobileNetworkUtils.isMobileNetworkUserRestricted(getActivity())) {
            finish();
            return;
        }
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("SubId="), this.mConstructionSubId, "WifiCallingSettings");
        if (this.mConstructionSubId != -1) {
            this.mSubscriptionChangeListener = getSubscriptionChangeListener(getContext());
        }
        this.mSil = updateSubList();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (MobileNetworkUtils.isMobileNetworkUserRestricted(getActivity())) {
            return new ViewStub(getActivity());
        }
        View inflate =
                layoutInflater.inflate(R.layout.wifi_calling_settings_tabs, viewGroup, false);
        this.mTabLayout = (SlidingTabLayout) inflate.findViewById(R.id.sliding_tabs);
        this.mViewPager = (RtlCompatibleViewPager) inflate.findViewById(R.id.view_pager);
        this.mViewPager.setAdapter(
                new WifiCallingViewPagerAdapter(getChildFragmentManager(), this.mViewPager));
        this.mViewPager.addOnPageChangeListener(new InternalViewPagerListener());
        if (this.mSil != null) {
            int i = this.mConstructionSubId;
            if (SubscriptionManager.isValidSubscriptionId(i)) {
                Iterator it = this.mSil.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    SubscriptionInfo subscriptionInfo = (SubscriptionInfo) it.next();
                    if (i == subscriptionInfo.getSubscriptionId()) {
                        this.mViewPager.setCurrentItem(this.mSil.indexOf(subscriptionInfo));
                        break;
                    }
                }
            }
        }
        return inflate;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("android.provider.extra.SUB_ID", this.mConstructionSubId);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        List list = this.mSil;
        if (list == null || list.size() <= 1) {
            this.mTabLayout.setVisibility(8);
        } else {
            this.mTabLayout.setViewPager(this.mViewPager);
        }
        updateTitleForCurrentSub();
        ActiveSubscriptionsListener activeSubscriptionsListener = this.mSubscriptionChangeListener;
        if (activeSubscriptionsListener != null) {
            activeSubscriptionsListener.monitorSubscriptionsChange(true);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        ActiveSubscriptionsListener activeSubscriptionsListener = this.mSubscriptionChangeListener;
        if (activeSubscriptionsListener != null) {
            activeSubscriptionsListener.monitorSubscriptionsChange(false);
        }
        super.onStop();
    }

    public WifiCallingQueryImsState queryImsState(int i) {
        return new WifiCallingQueryImsState(getContext(), i);
    }

    public final List updateSubList() {
        List<SubscriptionInfo> selectableSubscriptions = getSelectableSubscriptions(getContext());
        if (selectableSubscriptions == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (SubscriptionInfo subscriptionInfo : selectableSubscriptions) {
            int subscriptionId = subscriptionInfo.getSubscriptionId();
            try {
                Context context = getContext();
                WifiCallingQueryImsState queryImsState = queryImsState(subscriptionId);
                Drawable drawable = MobileNetworkUtils.EMPTY_DRAWABLE;
                if (queryImsState == null) {
                    queryImsState = new WifiCallingQueryImsState(context, subscriptionId);
                }
                if (queryImsState.isReadyToWifiCalling()) {
                    arrayList.add(subscriptionInfo);
                }
            } catch (Exception unused) {
            }
        }
        return arrayList;
    }

    public final void updateTitleForCurrentSub() {
        if (CollectionUtils.size(this.mSil) > 1) {
            getActivity()
                    .getActionBar()
                    .setTitle(
                            SubscriptionManager.getResourcesForSubId(
                                            getContext(),
                                            ((SubscriptionInfo)
                                                            this.mSil.get(
                                                                    this.mViewPager
                                                                            .getCurrentItem()))
                                                    .getSubscriptionId())
                                    .getString(R.string.wifi_calling_settings_title));
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class InternalViewPagerListener implements ViewPager.OnPageChangeListener {
        public InternalViewPagerListener() {}

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public final void onPageSelected(int i) {
            WifiCallingSettings.this.updateTitleForCurrentSub();
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public final void onPageScrollStateChanged(int i) {}

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public final void onPageScrolled(int i, int i2, float f) {}
    }
}

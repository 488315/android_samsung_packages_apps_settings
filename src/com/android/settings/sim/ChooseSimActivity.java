package com.android.settings.sim;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.icu.text.MessageFormat;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.SidecarFragment;
import com.android.settings.network.SubscriptionUtil;
import com.android.settings.network.SwitchToEuiccSubscriptionSidecar;
import com.android.settings.network.SwitchToRemovableSlotSidecar;
import com.android.settings.network.telephony.SubscriptionRepositoryKt;

import com.google.android.setupdesign.GlifLayout;
import com.google.android.setupdesign.GlifRecyclerLayout;
import com.google.android.setupdesign.items.RecyclerItemAdapter;
import com.google.android.setupdesign.view.HeaderRecyclerView;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ChooseSimActivity extends Activity
        implements RecyclerItemAdapter.OnItemSelectedListener, SidecarFragment.Listener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mHasPsim;
    public boolean mIsSwitching;
    public boolean mNoPsimContinueToSettings;
    public int mSelectedItemIndex;
    public SwitchToEuiccSubscriptionSidecar mSwitchToEuiccSubscriptionSidecar;
    public SwitchToRemovableSlotSidecar mSwitchToRemovableSlotSidecar;
    public final ArrayList mEmbeddedSubscriptions = new ArrayList();
    public SubscriptionInfo mRemovableSubscription = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class DisableableItem {
        public final boolean enabled;
        public final int id;
        public final int layoutRes;
        public final ArrayList observers;
        public CharSequence summary;
        public final CharSequence title;
        public final boolean visible;

        public abstract void notifyItemChanged();
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.choose_sim_activity);
        Intent intent = getIntent();
        this.mHasPsim = intent.getBooleanExtra("has_psim", false);
        this.mNoPsimContinueToSettings =
                intent.getBooleanExtra("no_psim_continue_to_settings", false);
        Pattern pattern = SubscriptionUtil.REGEX_DISPLAY_NAME_SUFFIX_PATTERN;
        List<SubscriptionInfo> selectableSubscriptionInfoList =
                SubscriptionRepositoryKt.getSelectableSubscriptionInfoList(this);
        if (selectableSubscriptionInfoList != null) {
            for (SubscriptionInfo subscriptionInfo : selectableSubscriptionInfoList) {
                if (subscriptionInfo != null) {
                    if (subscriptionInfo.isEmbedded()) {
                        this.mEmbeddedSubscriptions.add(subscriptionInfo);
                    } else {
                        this.mRemovableSubscription = subscriptionInfo;
                    }
                }
            }
        }
        if (this.mEmbeddedSubscriptions.size() == 0) {
            Log.e("ChooseSimActivity", "Unable to find available eSIM subscriptions.");
            finish();
            return;
        }
        if (bundle != null) {
            this.mSelectedItemIndex = bundle.getInt("selected_index");
            this.mIsSwitching = bundle.getBoolean("is_switching");
        }
        GlifLayout glifLayout = (GlifLayout) findViewById(R.id.glif_layout);
        int size = this.mEmbeddedSubscriptions.size();
        if (this.mHasPsim) {
            size++;
        }
        glifLayout.setHeaderText(getString(R.string.choose_sim_title));
        MessageFormat messageFormat =
                new MessageFormat(getString(R.string.choose_sim_text), Locale.getDefault());
        HashMap hashMap = new HashMap();
        hashMap.put("count", Integer.valueOf(size));
        glifLayout.setDescriptionText(messageFormat.format(hashMap));
        RecyclerView.Adapter adapter =
                ((GlifRecyclerLayout)
                                findViewById(android.R.id.content).findViewById(R.id.glif_layout))
                        .recyclerMixin.recyclerView.getAdapter();
        if (adapter instanceof HeaderRecyclerView.HeaderAdapter) {
            adapter = ((HeaderRecyclerView.HeaderAdapter) adapter).adapter;
        }
        RecyclerItemAdapter recyclerItemAdapter = (RecyclerItemAdapter) adapter;
        recyclerItemAdapter.listener = this;
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(recyclerItemAdapter.itemHierarchy);
        if (this.mHasPsim) {
            ArrayList arrayList = new ArrayList();
            SubscriptionInfo subscriptionInfo2 = this.mRemovableSubscription;
            if (TextUtils.isEmpty(
                    subscriptionInfo2 != null
                            ? SubscriptionUtil.getUniqueSubscriptionDisplayNames(this)
                                    .getOrDefault(
                                            Integer.valueOf(subscriptionInfo2.getSubscriptionId()),
                                            ApnSettings.MVNO_NONE)
                            : null)) {
                getString(R.string.sim_card_label);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((RecyclerItemAdapter) it.next()).notifyItemRangeChanged(0, 1);
            }
            if (this.mIsSwitching && this.mSelectedItemIndex == -1) {
                getString(R.string.choose_sim_activating);
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    ((RecyclerItemAdapter) it2.next()).notifyItemRangeChanged(0, 1);
                }
                throw null;
            }
            TextUtils.isEmpty(
                    SubscriptionUtil.getFormattedPhoneNumber(this, this.mRemovableSubscription));
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                ((RecyclerItemAdapter) it3.next()).notifyItemRangeChanged(0, 1);
            }
            throw null;
        }
        Iterator it4 = this.mEmbeddedSubscriptions.iterator();
        if (!it4.hasNext()) {
            FragmentManager fragmentManager = getFragmentManager();
            int i = SwitchToRemovableSlotSidecar.$r8$clinit;
            this.mSwitchToRemovableSlotSidecar =
                    (SwitchToRemovableSlotSidecar)
                            SidecarFragment.get(
                                    fragmentManager,
                                    "SwitchRemovableSidecar",
                                    SwitchToRemovableSlotSidecar.class);
            this.mSwitchToEuiccSubscriptionSidecar =
                    SwitchToEuiccSubscriptionSidecar.get(getFragmentManager());
            return;
        }
        SubscriptionInfo subscriptionInfo3 = (SubscriptionInfo) it4.next();
        ArrayList arrayList2 = new ArrayList();
        if (TextUtils.isEmpty(
                SubscriptionUtil.getUniqueSubscriptionDisplayNames(this)
                        .getOrDefault(
                                Integer.valueOf(subscriptionInfo3.getSubscriptionId()),
                                ApnSettings.MVNO_NONE))) {
            subscriptionInfo3.getDisplayName();
        }
        Iterator it5 = arrayList2.iterator();
        while (it5.hasNext()) {
            ((RecyclerItemAdapter) it5.next()).notifyItemRangeChanged(0, 1);
        }
        if (this.mIsSwitching && this.mSelectedItemIndex == 0) {
            getString(R.string.choose_sim_activating);
            Iterator it6 = arrayList2.iterator();
            while (it6.hasNext()) {
                ((RecyclerItemAdapter) it6.next()).notifyItemRangeChanged(0, 1);
            }
            throw null;
        }
        TextUtils.isEmpty(SubscriptionUtil.getFormattedPhoneNumber(this, subscriptionInfo3));
        Iterator it7 = arrayList2.iterator();
        while (it7.hasNext()) {
            ((RecyclerItemAdapter) it7.next()).notifyItemRangeChanged(0, 1);
        }
        throw null;
    }

    @Override // android.app.Activity
    public final void onPause() {
        this.mSwitchToEuiccSubscriptionSidecar.removeListener(this);
        this.mSwitchToRemovableSlotSidecar.removeListener(this);
        super.onPause();
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        this.mSwitchToRemovableSlotSidecar.addListener(this);
        this.mSwitchToEuiccSubscriptionSidecar.addListener(this);
    }

    @Override // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("selected_index", this.mSelectedItemIndex);
        bundle.putBoolean("is_switching", this.mIsSwitching);
        super.onSaveInstanceState(bundle);
    }

    @Override // com.android.settings.SidecarFragment.Listener
    public final void onStateChange(SidecarFragment sidecarFragment) {
        SubscriptionInfo firstRemovableSubscription;
        SwitchToRemovableSlotSidecar switchToRemovableSlotSidecar =
                this.mSwitchToRemovableSlotSidecar;
        if (sidecarFragment == switchToRemovableSlotSidecar) {
            int i = switchToRemovableSlotSidecar.mState;
            if (i != 2) {
                if (i != 3) {
                    return;
                }
                switchToRemovableSlotSidecar.setState(0, 0);
                Log.e("ChooseSimActivity", "Failed to switch slot in ChooseSubscriptionsActivity.");
                throw null;
            }
            switchToRemovableSlotSidecar.setState(0, 0);
            Log.i("ChooseSimActivity", "Switch slot successfully.");
            SubscriptionManager subscriptionManager =
                    (SubscriptionManager) getSystemService(SubscriptionManager.class);
            if (subscriptionManager.canDisablePhysicalSubscription()
                    && (firstRemovableSubscription =
                                    SubscriptionUtil.getFirstRemovableSubscription(this))
                            != null) {
                subscriptionManager.setUiccApplicationsEnabled(
                        firstRemovableSubscription.getSubscriptionId(), true);
            }
            finish();
            return;
        }
        SwitchToEuiccSubscriptionSidecar switchToEuiccSubscriptionSidecar =
                this.mSwitchToEuiccSubscriptionSidecar;
        if (sidecarFragment == switchToEuiccSubscriptionSidecar) {
            int i2 = switchToEuiccSubscriptionSidecar.mState;
            if (i2 != 2) {
                if (i2 != 3) {
                    return;
                }
                switchToEuiccSubscriptionSidecar.setState(0, 0);
                Log.e(
                        "ChooseSimActivity",
                        "Failed to switch subscription in ChooseSubscriptionsActivity.");
                throw null;
            }
            switchToEuiccSubscriptionSidecar.setState(0, 0);
            if (this.mNoPsimContinueToSettings) {
                Log.e(
                        "ChooseSimActivity",
                        "mNoPsimContinueToSettings is true which is not supported for now.");
            } else {
                Log.i("ChooseSimActivity", "User finished selecting eSIM profile.");
                finish();
            }
        }
    }
}

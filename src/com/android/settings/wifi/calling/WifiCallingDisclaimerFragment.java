package com.android.settings.wifi.calling;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.core.InstrumentedFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiCallingDisclaimerFragment extends InstrumentedFragment
        implements View.OnClickListener {
    public Button mAgreeButton;
    public Button mDisagreeButton;
    public List mDisclaimerItemList = new ArrayList();
    public boolean mScrollToBottom;

    @VisibleForTesting
    public void finish(int i) {
        FragmentActivity activity = getActivity();
        activity.setResult(i, null);
        activity.finish();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 105;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (view != this.mAgreeButton) {
            if (view == this.mDisagreeButton) {
                finish(0);
                return;
            }
            return;
        }
        Iterator it = ((ArrayList) this.mDisclaimerItemList).iterator();
        while (it.hasNext()) {
            DisclaimerItem disclaimerItem = (DisclaimerItem) it.next();
            String prefKey = disclaimerItem.getPrefKey();
            SharedPreferences.Editor edit =
                    disclaimerItem.mContext.getSharedPreferences("wfc_disclaimer_prefs", 0).edit();
            StringBuilder m =
                    EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(
                            prefKey);
            m.append(disclaimerItem.mSubId);
            edit.putBoolean(m.toString(), true).apply();
        }
        finish(-1);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        int i = arguments != null ? arguments.getInt("EXTRA_SUB_ID") : Preference.DEFAULT_ORDER;
        FragmentActivity activity = getActivity();
        ArrayList arrayList = new ArrayList();
        arrayList.add(new LocationPolicyDisclaimer(activity, i));
        arrayList.add(new EmergencyCallLimitationDisclaimer(activity, i));
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            if (!((DisclaimerItem) it.next()).shouldShow()) {
                it.remove();
            }
        }
        this.mDisclaimerItemList = arrayList;
        if (arrayList.isEmpty()) {
            finish(-1);
        } else if (bundle != null) {
            this.mScrollToBottom =
                    bundle.getBoolean("state_is_scroll_to_bottom", this.mScrollToBottom);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.wfc_disclaimer_fragment, viewGroup, false);
        Button button = (Button) inflate.findViewById(R.id.agree_button);
        this.mAgreeButton = button;
        button.setOnClickListener(this);
        Button button2 = (Button) inflate.findViewById(R.id.disagree_button);
        this.mDisagreeButton = button2;
        button2.setOnClickListener(this);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.disclaimer_item_list);
        getActivity();
        recyclerView.setLayoutManager(new LinearLayoutManager(1));
        List list = this.mDisclaimerItemList;
        DisclaimerItemListAdapter disclaimerItemListAdapter = new DisclaimerItemListAdapter();
        disclaimerItemListAdapter.mDisclaimerItemList = list;
        recyclerView.setAdapter(disclaimerItemListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), 1));
        recyclerView.addOnScrollListener(
                new RecyclerView
                        .OnScrollListener() { // from class:
                                              // com.android.settings.wifi.calling.WifiCallingDisclaimerFragment.1
                    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                    public final void onScrolled(RecyclerView recyclerView2, int i, int i2) {
                        if (recyclerView2.canScrollVertically(1)) {
                            return;
                        }
                        WifiCallingDisclaimerFragment wifiCallingDisclaimerFragment =
                                WifiCallingDisclaimerFragment.this;
                        wifiCallingDisclaimerFragment.mScrollToBottom = true;
                        wifiCallingDisclaimerFragment.mAgreeButton.setEnabled(true);
                        recyclerView2.removeOnScrollListener(this);
                    }
                });
        return inflate;
    }

    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mAgreeButton.setEnabled(this.mScrollToBottom);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("state_is_scroll_to_bottom", this.mScrollToBottom);
    }
}

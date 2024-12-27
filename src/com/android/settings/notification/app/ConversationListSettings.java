package com.android.settings.notification.app;

import android.app.people.IPeopleManager;
import android.content.Context;
import android.os.Bundle;
import android.os.ServiceManager;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.notification.NotificationBackend;

import com.samsung.android.settings.logging.SALogging;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ConversationListSettings extends DashboardFragment {
    public ViewGroup mContent;
    public View mEmptyView;
    public RecyclerView mRecyclerView;
    public final NotificationBackend mBackend = new NotificationBackend();
    public final List mControllers = new ArrayList();
    public boolean mUpdatedInOnCreate = false;

    public ConversationListSettings() {
        IPeopleManager.Stub.asInterface(ServiceManager.getService("people"));
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ((ArrayList) this.mControllers)
                .add(new PriorityConversationsPreferenceController(context, this.mBackend));
        ((ArrayList) this.mControllers)
                .add(new AllConversationsPreferenceController(context, this.mBackend));
        return new ArrayList(this.mControllers);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "ConvoListSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1834;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_conversation_list_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mRecyclerView = getListView();
        this.mContent = (ViewGroup) getListView().getParent();
        this.mEmptyView =
                getActivity()
                        .getLayoutInflater()
                        .inflate(R.layout.sec_conversation_empty_view, this.mContent, false);
        this.mRecyclerView.setFocusable(false);
        this.mRecyclerView.setImportantForAccessibility(2);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mUpdatedInOnCreate = true;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (this.mEmptyView != null) {
            this.mBackend.getClass();
            if (NotificationBackend.getConversations(false).getList().size() == 0) {
                this.mContent.removeView(this.mEmptyView);
                this.mContent.addView(this.mEmptyView);
            }
        }
        SALogging.insertSALog(Integer.toString(36043), "NSTE0403");
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        if (this.mUpdatedInOnCreate) {
            this.mUpdatedInOnCreate = false;
        }
    }
}

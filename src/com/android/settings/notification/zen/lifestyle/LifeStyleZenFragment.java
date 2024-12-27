package com.android.settings.notification.zen.lifestyle;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.notification.zen.LifeStyleZenModePeoplePreferenceController;
import com.android.settings.notification.zen.LifeStyleZenModeSettingsHeaderPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler;
import com.samsung.android.settings.widget.SecBottomBarButton;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LifeStyleZenFragment extends DashboardFragment implements View.OnClickListener {
    public static BixbyRoutineActionHandler SAHandler;
    public View mButtonBar;
    public SecBottomBarButton mCancelButton;
    public SecBottomBarButton mDoneButton;
    public boolean mIsPortrait;

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        FragmentActivity activity = getActivity();
        Lifecycle settingsLifecycle = getSettingsLifecycle();
        getFragmentManager();
        if (activity != null) {
            activity.getApplication();
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(new LifeStyleZenModePeoplePreferenceController(context, settingsLifecycle));
        LifeStyleZenModeBypassingAppsPreferenceController
                lifeStyleZenModeBypassingAppsPreferenceController =
                        new LifeStyleZenModeBypassingAppsPreferenceController(
                                context, "lifestyle_mode_add_apps", settingsLifecycle);
        BixbyRoutineActionHandler.getInstance();
        arrayList.add(lifeStyleZenModeBypassingAppsPreferenceController);
        arrayList.add(
                new LifeStyleZenModeSettingsHeaderPreferenceController(
                        context, "sec_lifestyle_zen_header_preference", settingsLifecycle));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "LifeStyleZenFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.lifestyle_dnd_index;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (view.getId() == R.id.save_button) {
            sendResults();
            getActivity().finish();
        } else if (view.getId() == R.id.cancel_button) {
            getActivity().finish();
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mIsPortrait = getResources().getConfiguration().orientation == 1;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        SAHandler = BixbyRoutineActionHandler.getInstance();
        this.mIsPortrait = getResources().getConfiguration().orientation == 1;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        if (this.mIsPortrait || Utils.isTablet()) {
            return;
        }
        getActivity().getMenuInflater().inflate(R.menu.menu_edit, menu);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        getActivity().setTitle(R.string.zen_mode_settings_title);
        View findViewById = getActivity().findViewById(R.id.button_bar);
        this.mButtonBar = findViewById;
        if (findViewById != null) {
            if (Utils.isTablet() || this.mIsPortrait) {
                this.mButtonBar.setVisibility(0);
            }
            View inflate =
                    getLayoutInflater()
                            .inflate(R.layout.sec_dnd_schedule_rule_settings, (ViewGroup) null);
            ((RelativeLayout) this.mButtonBar).removeAllViews();
            ((RelativeLayout) this.mButtonBar).addView(inflate);
            this.mCancelButton =
                    (SecBottomBarButton) getActivity().findViewById(R.id.cancel_button);
            SecBottomBarButton secBottomBarButton =
                    (SecBottomBarButton) getActivity().findViewById(R.id.save_button);
            this.mDoneButton = secBottomBarButton;
            if (secBottomBarButton != null) {
                secBottomBarButton.setClickable(true);
                this.mDoneButton.setOnClickListener(this);
                this.mDoneButton.setText(R.string.done);
                this.mDoneButton.setEnabled(true);
            }
            SecBottomBarButton secBottomBarButton2 = this.mCancelButton;
            if (secBottomBarButton2 != null) {
                secBottomBarButton2.setClickable(true);
                this.mCancelButton.setOnClickListener(this);
            }
        }
        return onCreateView;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_done) {
            sendResults();
            getActivity().finish();
            return true;
        }
        if (menuItem.getItemId() != R.id.action_cancel) {
            return true;
        }
        getActivity().finish();
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mButtonBar.setVisibility(8);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mDoneButton.setText(R.string.done);
        this.mDoneButton.setEnabled(true);
        if (Utils.isTablet() || this.mIsPortrait) {
            this.mButtonBar.setVisibility(0);
        }
    }

    public final void sendResults() {
        getActivity().setTitle(R.string.zen_mode_settings_title);
        ParameterValues parameterValues = new ParameterValues();
        SAHandler.getClass();
        parameterValues.put(
                "dnd_switch", Boolean.valueOf(BixbyRoutineActionHandler.LifeStyleZenState));
        SAHandler.getClass();
        parameterValues.put(
                "messages_options", Float.valueOf(BixbyRoutineActionHandler.getMessageOption()));
        SAHandler.getClass();
        parameterValues.put(
                "call_options", Float.valueOf(BixbyRoutineActionHandler.getCallOption()));
        parameterValues.put("app_options", BixbyRoutineActionHandler.app_exist_list);
        parameterValues.put(
                "repeat_options", Boolean.valueOf(BixbyRoutineActionHandler.repeatedCaller));
        parameterValues.put("contact_options", BixbyRoutineActionHandler.contact_exist_list);
        parameterValues.put(
                "exception_contactflag", Float.valueOf(BixbyRoutineActionHandler.getContactFlag()));
        parameterValues.put(
                "appBypass_flag", Float.valueOf(BixbyRoutineActionHandler.getAppFlag()));
        FragmentActivity activity = getActivity();
        Intent intent = new Intent();
        intent.putExtra("intent_params", parameterValues.toJsonString());
        activity.setResult(-1, intent);
    }
}

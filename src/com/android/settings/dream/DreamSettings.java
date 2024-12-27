package com.android.settings.dream;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.service.dreams.IDreamManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

import androidx.preference.Preference;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.dream.DreamBackend;
import com.android.settingslib.widget.MainSwitchPreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DreamSettings extends DashboardFragment
        implements CompoundButton.OnCheckedChangeListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new SearchIndexProvider(R.xml.dream_fragment_overview);
    public final DreamSettings$$ExternalSyntheticLambda3 mCallback =
            new DreamSettings$$ExternalSyntheticLambda3(this);
    public Preference mComplicationsTogglePreference;
    public DreamHomeControlsPreferenceController mDreamHomeControlsPreferenceController;
    public DreamPickerController mDreamPickerController;
    public Preference mHomeControllerTogglePreference;
    public Button mPreviewButton;
    public RecyclerView mRecyclerView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SearchIndexProvider extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return Utils.areDreamsAvailableToCurrentUser(context);
        }
    }

    public static CharSequence getSummaryTextFromBackend(
            DreamBackend dreamBackend, Context context) {
        if (!dreamBackend.isEnabled()) {
            return context.getString(R.string.screensaver_settings_summary_off);
        }
        ComponentName activeDream = dreamBackend.getActiveDream();
        CharSequence charSequence = null;
        if (activeDream != null) {
            PackageManager packageManager = dreamBackend.mContext.getPackageManager();
            try {
                ServiceInfo serviceInfo = packageManager.getServiceInfo(activeDream, 0);
                if (serviceInfo != null) {
                    charSequence = serviceInfo.loadLabel(packageManager);
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return context.getString(R.string.screensaver_settings_summary_on, charSequence);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        if (this.mDreamPickerController == null) {
            this.mDreamPickerController = new DreamPickerController(context);
        }
        if (this.mDreamHomeControlsPreferenceController == null) {
            this.mDreamHomeControlsPreferenceController =
                    new DreamHomeControlsPreferenceController(
                            context, DreamBackend.getInstance(getContext()));
        }
        arrayList.add(this.mDreamPickerController);
        arrayList.add(this.mDreamHomeControlsPreferenceController);
        arrayList.add(
                new WhenToDreamPreferenceController(
                        context,
                        context.getResources()
                                .getBoolean(android.R.bool.config_duplicate_port_omadm_wappush),
                        context.getResources()
                                .getBoolean(android.R.bool.config_earcEnabled_userConfigurable)));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "DreamSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 47;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.dream_fragment_overview;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        getPreferenceControllers().forEach(new DreamSettings$$ExternalSyntheticLambda1(this, z, 0));
        updateComplicationsToggleVisibility();
        this.mPreviewButton.setVisibility(z ? 0 : 8);
        this.mPreviewButton.post(new DreamSettings$$ExternalSyntheticLambda0(this));
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        DreamBackend dreamBackend = DreamBackend.getInstance(getContext());
        this.mComplicationsTogglePreference =
                findPreference(DreamComplicationPreferenceController.PREF_KEY);
        this.mHomeControllerTogglePreference =
                findPreference(DreamHomeControlsPreferenceController.PREF_KEY);
        MainSwitchPreference mainSwitchPreference =
                (MainSwitchPreference) findPreference("dream_main_settings_switch");
        if (mainSwitchPreference != null) {
            mainSwitchPreference.addOnSwitchChangeListener(this);
        }
        getPreferenceControllers()
                .forEach(
                        new DreamSettings$$ExternalSyntheticLambda1(
                                this, dreamBackend.isEnabled(), 0));
        updateComplicationsToggleVisibility();
        DreamPickerController dreamPickerController = this.mDreamPickerController;
        if (dreamPickerController != null) {
            dreamPickerController.addCallback(this.mCallback);
        }
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public final RecyclerView onCreateRecyclerView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        final DreamBackend dreamBackend = DreamBackend.getInstance(getContext());
        ViewGroup viewGroup2 = (ViewGroup) getActivity().findViewById(android.R.id.content);
        Button button =
                (Button)
                        getActivity()
                                .getLayoutInflater()
                                .inflate(R.layout.dream_preview_button, viewGroup2, false);
        this.mPreviewButton = button;
        button.setVisibility(dreamBackend.isEnabled() ? 0 : 8);
        viewGroup2.addView(this.mPreviewButton);
        this.mPreviewButton.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.dream.DreamSettings$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        DreamBackend dreamBackend2 = DreamBackend.this;
                        BaseSearchIndexProvider baseSearchIndexProvider =
                                DreamSettings.SEARCH_INDEX_DATA_PROVIDER;
                        ComponentName activeDream = dreamBackend2.getActiveDream();
                        IDreamManager iDreamManager = dreamBackend2.mDreamManager;
                        if (iDreamManager == null || activeDream == null) {
                            return;
                        }
                        try {
                            iDreamManager.testDream(
                                    dreamBackend2.mContext.getUserId(), activeDream);
                        } catch (RemoteException e) {
                            Log.w("DreamBackend", "Failed to preview " + activeDream, e);
                        }
                    }
                });
        RecyclerView onCreateRecyclerView =
                super.onCreateRecyclerView(layoutInflater, viewGroup, bundle);
        this.mRecyclerView = onCreateRecyclerView;
        onCreateRecyclerView.setFocusable(false);
        this.mPreviewButton.post(new DreamSettings$$ExternalSyntheticLambda0(this));
        return this.mRecyclerView;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        DreamPickerController dreamPickerController = this.mDreamPickerController;
        if (dreamPickerController != null) {
            dreamPickerController.removeCallback(this.mCallback);
        }
        super.onDestroy();
    }

    public void setDreamHomeControlsPreferenceController(
            DreamHomeControlsPreferenceController dreamHomeControlsPreferenceController) {
        this.mDreamHomeControlsPreferenceController = dreamHomeControlsPreferenceController;
    }

    public void setDreamPickerController(DreamPickerController dreamPickerController) {
        this.mDreamPickerController = dreamPickerController;
    }

    public final void updateComplicationsToggleVisibility() {
        DreamPickerController dreamPickerController = this.mDreamPickerController;
        if (dreamPickerController == null) {
            return;
        }
        DreamBackend.DreamInfo activeDreamInfo = dreamPickerController.getActiveDreamInfo();
        DreamBackend dreamBackend = DreamBackend.getInstance(getContext());
        Preference preference = this.mComplicationsTogglePreference;
        boolean z = false;
        if (preference != null) {
            preference.setVisible(activeDreamInfo != null && activeDreamInfo.supportsComplications);
        }
        if (this.mHomeControllerTogglePreference != null) {
            if (dreamBackend.isEnabled()
                    && ((activeDreamInfo == null || (activeDreamInfo.dreamCategory & 2) == 0)
                            && this.mDreamHomeControlsPreferenceController.getAvailabilityStatus()
                                    == 0)) {
                z = true;
            }
            this.mHomeControllerTogglePreference.setEnabled(z);
        }
    }
}

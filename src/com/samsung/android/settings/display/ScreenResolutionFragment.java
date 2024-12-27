package com.samsung.android.settings.display;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManagerGlobal;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.widget.LayoutPreference;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.display.controller.SecScreenResolutionSingleChoiceController;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.RadioItemDataInfo;
import com.samsung.android.settings.widget.SecButtonPreference;
import com.samsung.android.settings.widget.SecHorizontalRadioPreference;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ScreenResolutionFragment extends DashboardFragment {
    public SecButtonPreference mApplyButton;
    public Configuration mConfiguration;
    public Context mContext;
    public SecScreenResolutionSingleChoiceController mController;
    public AnonymousClass1 mDesktopModeListener;
    public SemDesktopModeManager mDesktopModeManager;
    public int mScreenResolution;
    public SecHorizontalRadioPreference mScreenResolutionButton;
    public String mStrBtnFHD;
    public String mStrBtnHD;
    public String mStrBtnWQHD;
    public final ArrayList mEntryValues = new ArrayList();
    public final ArrayList mMainTitleList = new ArrayList();
    public final ArrayList mSubTitleList = new ArrayList();
    public boolean mScreenChangeState = false;
    public final List mControllers = new ArrayList();

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        SecScreenResolutionSingleChoiceController secScreenResolutionSingleChoiceController = new SecScreenResolutionSingleChoiceController(context, "screen_resolution_seekbar");
        this.mController = secScreenResolutionSingleChoiceController;
        ((ArrayList) this.mControllers).add(secScreenResolutionSingleChoiceController);
        return this.mControllers;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "ScreenResolutionFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 7440;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_screen_resolution;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getListView().mDrawLastRoundedCorner = false;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        Preference preference;
        super.onConfigurationChanged(configuration);
        if (this.mConfiguration.orientation != configuration.orientation && (preference = getPreferenceScreen().getPreference(0)) != null) {
            getPreferenceScreen().removePreference(preference);
            LayoutPreference layoutPreference = new LayoutPreference(getContext(), R.layout.screen_resolution_preview_container);
            layoutPreference.setOrder(0);
            getPreferenceScreen().addPreference(layoutPreference);
            setPreview$1();
            if (getListView() != null && getListView().getAdapter() != null) {
                getListView().getAdapter().notifyDataSetChanged();
            }
        }
        this.mConfiguration.updateFrom(configuration);
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext();
        if (bundle == null || !bundle.containsKey("selected_value")) {
            this.mScreenResolution = SecDisplayUtils.getScreenResolution(this.mContext);
        } else {
            this.mScreenResolution = bundle.getInt("selected_value");
        }
        Configuration configuration = new Configuration();
        this.mConfiguration = configuration;
        configuration.updateFrom(this.mContext.getResources().getConfiguration());
        SecHorizontalRadioPreference secHorizontalRadioPreference = (SecHorizontalRadioPreference) findPreference("screen_resolution_seekbar");
        this.mScreenResolutionButton = secHorizontalRadioPreference;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        secHorizontalRadioPreference.setValue(Integer.toString(this.mScreenResolution));
        Point point = new Point();
        this.mStrBtnHD = this.mContext.getString(R.string.screen_resolution_hd);
        this.mStrBtnFHD = this.mContext.getString(R.string.screen_resolution_fhd);
        this.mStrBtnWQHD = this.mContext.getString(R.string.screen_resolution_wqhd);
        try {
            WindowManagerGlobal.getWindowManagerService().getInitialDisplaySize(0, point);
            int i = point.x;
            int i2 = point.y;
            boolean z = this.mContext.getResources().getConfiguration().getLayoutDirection() == 1;
            boolean equals = String.valueOf(1234).equals(String.format("%d", 1234));
            if (i2 / i > 1.7777778f) {
                this.mStrBtnHD = ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder(), this.mStrBtnHD, "+");
                this.mStrBtnFHD = ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder(), this.mStrBtnFHD, "+");
                this.mStrBtnWQHD = ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder(), this.mStrBtnWQHD, "+");
            }
            this.mMainTitleList.add(this.mStrBtnHD);
            this.mMainTitleList.add(this.mStrBtnFHD);
            this.mMainTitleList.add(this.mStrBtnWQHD);
            String string = this.mContext.getString(R.string.screen_resolution_hd_px);
            String string2 = this.mContext.getString(R.string.screen_resolution_fhd_px);
            String string3 = this.mContext.getString(R.string.screen_resolution_wqhd_px);
            NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
            numberFormat.setGroupingUsed(false);
            if (i == 1440) {
                int i3 = (!z || equals) ? i : i2;
                if (!z || equals) {
                    i = i2;
                }
                StringBuilder sb = new StringBuilder();
                double d = i;
                sb.append(numberFormat.format((int) (d * 0.5d)));
                sb.append(" x ");
                double d2 = i3;
                sb.append(numberFormat.format((int) (0.5d * d2)));
                String sb2 = sb.toString();
                string2 = numberFormat.format((int) (d * 0.75d)) + " x " + numberFormat.format((int) (d2 * 0.75d));
                string3 = numberFormat.format(i) + " x " + numberFormat.format(i3);
                string = sb2;
            }
            this.mSubTitleList.add(string);
            this.mSubTitleList.add(string2);
            this.mSubTitleList.add(string3);
            this.mEntryValues.add(Integer.toString(0));
            this.mEntryValues.add(Integer.toString(1));
            this.mEntryValues.add(Integer.toString(2));
        } catch (RemoteException unused) {
            Log.secD("ScreenResolutionFragment", "getInitialDisplaySize() exception!!!");
        }
        RadioItemDataInfo.Builder builder = new RadioItemDataInfo.Builder(this.mEntryValues, this.mMainTitleList);
        builder.mSubTitleList = this.mSubTitleList;
        this.mScreenResolutionButton.setRadioItemDataInfo(builder.build());
        SecHorizontalRadioPreference secHorizontalRadioPreference2 = this.mScreenResolutionButton;
        secHorizontalRadioPreference2.mIsColorFilterEnabled = true;
        secHorizontalRadioPreference2.mIsDividerEnabled = true;
        secHorizontalRadioPreference2.seslSetRoundedBg(15);
        SecButtonPreference secButtonPreference = new SecButtonPreference(this.mContext);
        this.mApplyButton = secButtonPreference;
        secButtonPreference.mTitle = getResources().getString(R.string.common_apply);
        this.mApplyButton.setEnabled(this.mScreenChangeState);
        this.mApplyButton.mOnClickListener = new View.OnClickListener() { // from class: com.samsung.android.settings.display.ScreenResolutionFragment.2

            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
            /* renamed from: com.samsung.android.settings.display.ScreenResolutionFragment$2$1, reason: invalid class name */
            public final class AnonymousClass1 implements DialogInterface.OnClickListener {
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ScreenResolutionFragment screenResolutionFragment = ScreenResolutionFragment.this;
                SecHorizontalRadioPreference secHorizontalRadioPreference3 = screenResolutionFragment.mScreenResolutionButton;
                if (secHorizontalRadioPreference3 != null) {
                    screenResolutionFragment.mScreenChangeState = Integer.parseInt(secHorizontalRadioPreference3.getCurrentValue$1()) != SecDisplayUtils.getScreenResolution(ScreenResolutionFragment.this.mContext);
                    if (ScreenResolutionFragment.this.mScreenChangeState) {
                        LoggingHelper.insertEventLogging(7440, 7450);
                        final int parseInt = Integer.parseInt(ScreenResolutionFragment.this.mScreenResolutionButton.getCurrentValue$1());
                        int intRefreshRate = SecDisplayUtils.getIntRefreshRate(ScreenResolutionFragment.this.mContext, 999);
                        Log.secD("ScreenResolutionFragment", "selected = " + parseInt);
                        Log.secD("ScreenResolutionFragment", "refreshRateMode = " + intRefreshRate);
                        if (parseInt != 2 || SecDisplayUtils.canSetHighRefreshRateAboveWQHD(ScreenResolutionFragment.this.mContext) || (intRefreshRate != 2 && intRefreshRate != 1)) {
                            ScreenResolutionFragment.this.mController.setSelectedValue(Integer.toString(parseInt));
                            ScreenResolutionFragment.this.finish();
                            return;
                        }
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(ScreenResolutionFragment.this.mContext);
                        String str2 = ApnSettings.MVNO_NONE;
                        AlertDialog.Builder title = builder2.setTitle(ApnSettings.MVNO_NONE);
                        ScreenResolutionFragment screenResolutionFragment2 = ScreenResolutionFragment.this;
                        Context context = screenResolutionFragment2.mContext;
                        int highRefreshRateSeamlessType = SecDisplayUtils.getHighRefreshRateSeamlessType(999);
                        if (highRefreshRateSeamlessType == 1) {
                            str2 = screenResolutionFragment2.mContext.getString(R.string.sec_high_refresh_rate_not_supported_in_wqhd);
                        } else if (highRefreshRateSeamlessType == 2 || highRefreshRateSeamlessType == 3 || highRefreshRateSeamlessType == 4) {
                            str2 = screenResolutionFragment2.mContext.getString(R.string.sec_high_refresh_rate_dynamic_not_supported_in_wqhd);
                        }
                        title.setMessage(str2).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.samsung.android.settings.display.ScreenResolutionFragment.2.2
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i4) {
                                ScreenResolutionFragment.this.mController.setSelectedValue(Integer.toString(parseInt));
                                SecDisplayUtils.putIntRefreshRate(ScreenResolutionFragment.this.mContext, 0, 999);
                                ScreenResolutionFragment.this.finish();
                            }
                        }).setNegativeButton(android.R.string.cancel, new AnonymousClass1());
                        builder2.create();
                        builder2.show();
                    }
                }
            }
        };
        getPreferenceScreen().addPreference(this.mApplyButton);
        final TextView textView = (TextView) ((LayoutPreference) findPreference("screen_resolution_help_description")).mRootView.findViewById(R.id.screen_resolution_help_description_text);
        this.mScreenResolutionButton.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: com.samsung.android.settings.display.ScreenResolutionFragment.3
            @Override // androidx.preference.Preference.OnPreferenceChangeListener
            public final boolean onPreferenceChange(Preference preference, Object obj) {
                ScreenResolutionFragment screenResolutionFragment = ScreenResolutionFragment.this;
                screenResolutionFragment.mScreenChangeState = Integer.parseInt(screenResolutionFragment.mScreenResolutionButton.getCurrentValue$1()) != SecDisplayUtils.getScreenResolution(screenResolutionFragment.mContext);
                try {
                    screenResolutionFragment.mApplyButton.setEnabled(screenResolutionFragment.mScreenChangeState);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
                int parseInt = Integer.parseInt((String) obj);
                if (parseInt == 0) {
                    screenResolutionFragment.getClass();
                    LoggingHelper.insertEventLogging(7440, 7443, parseInt);
                    textView.setText(R.string.screen_resolution_hd_description);
                } else if (parseInt == 1) {
                    screenResolutionFragment.getClass();
                    LoggingHelper.insertEventLogging(7440, 7443, parseInt);
                    textView.setText(R.string.screen_resolution_fhd_description);
                } else if (parseInt == 2) {
                    screenResolutionFragment.getClass();
                    LoggingHelper.insertEventLogging(7440, 7443, parseInt);
                    textView.setText(R.string.screen_resolution_wqhd_description);
                }
                return screenResolutionFragment.mScreenChangeState;
            }
        });
        setPreview$1();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        SemDesktopModeManager semDesktopModeManager = this.mDesktopModeManager;
        if (semDesktopModeManager != null) {
            semDesktopModeManager.unregisterListener(this.mDesktopModeListener);
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.samsung.android.desktopmode.SemDesktopModeManager$DesktopModeListener, com.samsung.android.settings.display.ScreenResolutionFragment$1] */
    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (Utils.isDesktopModeEnabled(this.mContext)) {
            finish();
        }
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        SecHorizontalRadioPreference secHorizontalRadioPreference = this.mScreenResolutionButton;
        if (secHorizontalRadioPreference != null && this.mApplyButton != null) {
            secHorizontalRadioPreference.setValue(Integer.toString(this.mScreenResolution));
            boolean z = !TextUtils.equals(this.mScreenResolutionButton.getCurrentValue$1(), Integer.toString(this.mScreenResolution));
            this.mScreenChangeState = z;
            this.mApplyButton.setEnabled(z);
        }
        SemDesktopModeManager semDesktopModeManager = (SemDesktopModeManager) this.mContext.getSystemService("desktopmode");
        this.mDesktopModeManager = semDesktopModeManager;
        if (semDesktopModeManager != null) {
            ?? r1 = new SemDesktopModeManager.DesktopModeListener() { // from class: com.samsung.android.settings.display.ScreenResolutionFragment.1
                public final void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
                    Preference$$ExternalSyntheticOutline0.m(new StringBuilder("changed desktopmode state = "), semDesktopModeState.enabled, "ScreenResolutionFragment");
                    if (semDesktopModeState.enabled == 4) {
                        ScreenResolutionFragment.this.getActivity().runOnUiThread(new Runnable() { // from class: com.samsung.android.settings.display.ScreenResolutionFragment.1.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                ScreenResolutionFragment.this.finish();
                            }
                        });
                    }
                }
            };
            this.mDesktopModeListener = r1;
            semDesktopModeManager.registerListener((SemDesktopModeManager.DesktopModeListener) r1);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("selected_value", Integer.parseInt(this.mScreenResolutionButton.getCurrentValue$1()));
        super.onSaveInstanceState(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewStateRestored(Bundle bundle) {
        if (bundle == null || !bundle.containsKey("selected_value")) {
            this.mScreenResolution = SecDisplayUtils.getScreenResolution(this.mContext);
        } else {
            this.mScreenResolution = bundle.getInt("selected_value");
        }
        super.onViewStateRestored(bundle);
    }

    public final void setPreview$1() {
        LinearLayout linearLayout = (LinearLayout) ((LayoutPreference) getPreferenceScreen().getPreference(0)).mRootView.findViewById(R.id.screen_resolution_image_container);
        if (linearLayout != null) {
            linearLayout.semSetRoundedCorners(15);
            linearLayout.semSetRoundedCornerColor(15, this.mContext.getColor(R.color.sec_widget_round_and_bgcolor));
            ((TextView) linearLayout.findViewById(R.id.hd_description)).setText(this.mStrBtnHD);
            ((TextView) linearLayout.findViewById(R.id.fhd_description)).setText(this.mStrBtnFHD);
            ((TextView) linearLayout.findViewById(R.id.wqhd_description)).setText(this.mStrBtnWQHD);
            if (this.mConfiguration.getLayoutDirection() == 1) {
                ((ImageView) linearLayout.findViewById(R.id.screen_resolution_desc_image)).setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.screen_resolution_preview_rtl, null));
            }
        }
    }
}

package com.samsung.android.settings.activekey;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.apppickerview.widget.AbsAdapter;
import androidx.apppickerview.widget.AppPickerView;
import androidx.apppickerview.widget.AppPickerView.AnonymousClass5;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.settings.usefulfeature.functionkey.FunctionKeySettings;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DedicatedAppView extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener {
    public AppPickerView mAppPickerView;
    public Context mContext;
    public LoadAppListTask mLoadAppListTask;
    public int mPressType;
    public ViewGroup mProgressBar;
    public SettingsMainSwitchBar mSwitchBar;
    public int mLoggingFlow = -1;
    public ArrayList mPackageList = new ArrayList();
    public final AnonymousClass1 mHandler =
            new Handler() { // from class: com.samsung.android.settings.activekey.DedicatedAppView.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    int i = message.what;
                    if (i == 0) {
                        AppPickerView appPickerView = DedicatedAppView.this.mAppPickerView;
                        if (appPickerView != null && appPickerView.getAppLabelInfo(0) != null) {
                            DedicatedAppView dedicatedAppView = DedicatedAppView.this;
                            DedicatedAppInfo.saveDedicatedApp(
                                    dedicatedAppView.mContext,
                                    dedicatedAppView.mPressType,
                                    dedicatedAppView.mAppPickerView.getAppLabelInfo(0)
                                            .mPackageName);
                            if (TextUtils.isEmpty(
                                    DedicatedAppView.this.mAppPickerView.getAppLabelInfo(0)
                                            .mLabel)) {
                                DedicatedAppView dedicatedAppView2 = DedicatedAppView.this;
                                DedicatedAppInfo.saveDedicatedAppLabel(
                                        dedicatedAppView2.mContext,
                                        dedicatedAppView2.mPressType,
                                        dedicatedAppView2.mAppPickerView.getAppLabelInfo(0)
                                                .mPackageName);
                            } else {
                                DedicatedAppView dedicatedAppView3 = DedicatedAppView.this;
                                DedicatedAppInfo.saveDedicatedAppLabel(
                                        dedicatedAppView3.mContext,
                                        dedicatedAppView3.mPressType,
                                        dedicatedAppView3.mAppPickerView.getAppLabelInfo(0).mLabel);
                            }
                            AppPickerView appPickerView2 = DedicatedAppView.this.mAppPickerView;
                            appPickerView2.getClass();
                            appPickerView2.post(appPickerView2.new AnonymousClass5(0));
                        }
                    } else if (i == 1) {
                        LoadAppListTask loadAppListTask = DedicatedAppView.this.mLoadAppListTask;
                        synchronized (loadAppListTask) {
                            DedicatedAppView.this.mProgressBar.setVisibility(8);
                            DedicatedAppView.this.mAppPickerView.setVisibility(0);
                            AppPickerView appPickerView3 = DedicatedAppView.this.mAppPickerView;
                            appPickerView3.getClass();
                            appPickerView3.post(
                                    new AppPickerView.AnonymousClass3(appPickerView3, 0));
                        }
                    }
                    super.handleMessage(message);
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LoadAppListTask extends AsyncTask {
        public LoadAppListTask() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            DedicatedAppView dedicatedAppView = DedicatedAppView.this;
            dedicatedAppView.mPackageList =
                    DedicatedAppInfo.loadAppList(
                            dedicatedAppView.mContext, dedicatedAppView.mPressType);
            return DedicatedAppView.this.mPackageList;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            ArrayList arrayList = (ArrayList) obj;
            DedicatedAppView.this.mProgressBar.setVisibility(8);
            DedicatedAppView.this.mAppPickerView.setVisibility(0);
            AppPickerView appPickerView = DedicatedAppView.this.mAppPickerView;
            appPickerView.getClass();
            AppPickerView.mIsDividerVisible = false;
            appPickerView.setAppPickerView(4, arrayList, 0);
            ((SimpleItemAnimator) DedicatedAppView.this.mAppPickerView.getItemAnimator())
                            .mSupportsChangeAnimations =
                    false;
            final DedicatedAppView dedicatedAppView = DedicatedAppView.this;
            final AppPickerView appPickerView2 = dedicatedAppView.mAppPickerView;
            AppPickerView.OnBindListener onBindListener =
                    new AppPickerView.OnBindListener() { // from class:
                        // com.samsung.android.settings.activekey.DedicatedAppView.2
                        @Override // androidx.apppickerview.widget.AppPickerView.OnBindListener
                        public final void onBindViewHolder(
                                AppPickerView.ViewHolder viewHolder,
                                final int i,
                                final String str) {
                            DedicatedAppView dedicatedAppView2 = DedicatedAppView.this;
                            if (i < dedicatedAppView2.mPackageList.size()) {
                                if (!dedicatedAppView2.mPackageList.contains(str)) {
                                    Log.e(
                                            "DedicatedAppView",
                                            "skipped unexpected package : " + str);
                                    return;
                                }
                                RadioButton radioButton = viewHolder.mRadioButton;
                                boolean z = false;
                                radioButton.setVisibility(0);
                                radioButton.setOnCheckedChangeListener(
                                        new CompoundButton
                                                .OnCheckedChangeListener() { // from class:
                                            // com.samsung.android.settings.activekey.DedicatedAppView.2.1
                                            /* JADX WARN: Code restructure failed: missing block: B:5:0x0026, code lost:

                                               if (com.samsung.android.settings.activekey.DedicatedAppInfo.getDedicatedApp(r3.mContext, r3.mPressType).equals(r2) == false) goto L7;
                                            */
                                            @Override // android.widget.CompoundButton.OnCheckedChangeListener
                                            /*
                                                Code decompiled incorrectly, please refer to instructions dump.
                                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                                            */
                                            public final void onCheckedChanged(
                                                    android.widget.CompoundButton r3, boolean r4) {
                                                /*
                                                    r2 = this;
                                                    if (r4 == 0) goto L64
                                                    com.samsung.android.settings.activekey.DedicatedAppView$2 r3 = com.samsung.android.settings.activekey.DedicatedAppView.AnonymousClass2.this
                                                    com.samsung.android.settings.activekey.DedicatedAppView r3 = com.samsung.android.settings.activekey.DedicatedAppView.this
                                                    android.content.Context r4 = r3.mContext
                                                    int r3 = r3.mPressType
                                                    java.lang.String r3 = com.samsung.android.settings.activekey.DedicatedAppInfo.getDedicatedApp(r4, r3)
                                                    boolean r3 = android.text.TextUtils.isEmpty(r3)
                                                    if (r3 != 0) goto L28
                                                    com.samsung.android.settings.activekey.DedicatedAppView$2 r3 = com.samsung.android.settings.activekey.DedicatedAppView.AnonymousClass2.this
                                                    com.samsung.android.settings.activekey.DedicatedAppView r3 = com.samsung.android.settings.activekey.DedicatedAppView.this
                                                    android.content.Context r4 = r3.mContext
                                                    int r3 = r3.mPressType
                                                    java.lang.String r3 = com.samsung.android.settings.activekey.DedicatedAppInfo.getDedicatedApp(r4, r3)
                                                    java.lang.String r4 = r2
                                                    boolean r3 = r3.equals(r4)
                                                    if (r3 != 0) goto L64
                                                L28:
                                                    com.samsung.android.settings.activekey.DedicatedAppView$2 r3 = com.samsung.android.settings.activekey.DedicatedAppView.AnonymousClass2.this
                                                    com.samsung.android.settings.activekey.DedicatedAppView r3 = com.samsung.android.settings.activekey.DedicatedAppView.this
                                                    android.content.Context r4 = r3.mContext
                                                    int r3 = r3.mPressType
                                                    java.lang.String r0 = r2
                                                    com.samsung.android.settings.activekey.DedicatedAppInfo.saveDedicatedApp(r4, r3, r0)
                                                    com.samsung.android.settings.activekey.DedicatedAppView$2 r3 = com.samsung.android.settings.activekey.DedicatedAppView.AnonymousClass2.this
                                                    androidx.apppickerview.widget.AppPickerView r3 = r2
                                                    int r4 = r3
                                                    androidx.apppickerview.widget.AppPickerView$AppLabelInfo r3 = r3.getAppLabelInfo(r4)
                                                    if (r3 == 0) goto L57
                                                    com.samsung.android.settings.activekey.DedicatedAppView$2 r3 = com.samsung.android.settings.activekey.DedicatedAppView.AnonymousClass2.this
                                                    com.samsung.android.settings.activekey.DedicatedAppView r4 = com.samsung.android.settings.activekey.DedicatedAppView.this
                                                    android.content.Context r0 = r4.mContext
                                                    int r4 = r4.mPressType
                                                    androidx.apppickerview.widget.AppPickerView r3 = r2
                                                    int r1 = r3
                                                    androidx.apppickerview.widget.AppPickerView$AppLabelInfo r3 = r3.getAppLabelInfo(r1)
                                                    java.lang.String r3 = r3.mLabel
                                                    com.samsung.android.settings.activekey.DedicatedAppInfo.saveDedicatedAppLabel(r0, r4, r3)
                                                    goto L64
                                                L57:
                                                    com.samsung.android.settings.activekey.DedicatedAppView$2 r3 = com.samsung.android.settings.activekey.DedicatedAppView.AnonymousClass2.this
                                                    com.samsung.android.settings.activekey.DedicatedAppView r3 = com.samsung.android.settings.activekey.DedicatedAppView.this
                                                    android.content.Context r4 = r3.mContext
                                                    int r3 = r3.mPressType
                                                    java.lang.String r0 = r2
                                                    com.samsung.android.settings.activekey.DedicatedAppInfo.saveDedicatedAppLabel(r4, r3, r0)
                                                L64:
                                                    com.samsung.android.settings.activekey.DedicatedAppView$2 r2 = com.samsung.android.settings.activekey.DedicatedAppView.AnonymousClass2.this
                                                    androidx.apppickerview.widget.AppPickerView r2 = r2
                                                    r2.getClass()
                                                    androidx.apppickerview.widget.AppPickerView$3 r3 = new androidx.apppickerview.widget.AppPickerView$3
                                                    r4 = 1
                                                    r3.<init>(r2, r4)
                                                    r2.post(r3)
                                                    return
                                                */
                                                throw new UnsupportedOperationException(
                                                        "Method not decompiled:"
                                                            + " com.samsung.android.settings.activekey.DedicatedAppView.AnonymousClass2.AnonymousClass1.onCheckedChanged(android.widget.CompoundButton,"
                                                            + " boolean):void");
                                            }
                                        });
                                if (!TextUtils.isEmpty(
                                                DedicatedAppInfo.getDedicatedApp(
                                                        dedicatedAppView2.mContext,
                                                        dedicatedAppView2.mPressType))
                                        && DedicatedAppInfo.getDedicatedApp(
                                                        dedicatedAppView2.mContext,
                                                        dedicatedAppView2.mPressType)
                                                .equals(str)) {
                                    z = true;
                                }
                                radioButton.setChecked(z);
                            }
                        }
                    };
            AbsAdapter absAdapter = appPickerView2.mAdapter;
            if (absAdapter != null) {
                absAdapter.mOnBindListener = onBindListener;
            }
            if (arrayList.isEmpty()) {
                DedicatedAppView.this.mAppPickerView.setVisibility(8);
            }
            DedicatedAppView dedicatedAppView2 = DedicatedAppView.this;
            if (TextUtils.isEmpty(
                    DedicatedAppInfo.getDedicatedApp(
                            dedicatedAppView2.mContext, dedicatedAppView2.mPressType))) {
                sendEmptyMessageDelayed(0, 100L);
            }
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            DedicatedAppView.this.mProgressBar.setVisibility(0);
            DedicatedAppView.this.mAppPickerView.setVisibility(8);
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        if (getArguments() == null) {
            return -1;
        }
        String string = getArguments().getString("pressed_type");
        if ("active".equals(string)) {
            return R.string.active_key_dedicated_app_title;
        }
        if ("xcover".equals(string)) {
            return R.string.xcover_key_dedicated_app_title;
        }
        if ("top".equals(string)) {
            return R.string.xcover_top_key_dedicated_app_title;
        }
        if ("sidekey".equals(string)) {
            return R.string.sec_short_press_dedicated_app;
        }
        return -1;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        if (getArguments() == null) {
            return null;
        }
        String string = getArguments().getString("pressed_type");
        if ("active".equals(string) || "xcover".equals(string)) {
            return ActiveKeySettings.class.getName();
        }
        if ("top".equals(string)) {
            return XcoverTopKeySettings.class.getName();
        }
        if ("sidekey".equals(string)) {
            return FunctionKeySettings.class.getName();
        }
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return this.mLoggingFlow;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.show();
        this.mSwitchBar.addOnSwitchChangeListener(this);
        this.mLoadAppListTask = new LoadAppListTask();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        String string = getArguments().getString("pressed_type");
        if ("active".equals(string)) {
            this.mPressType = 0;
            this.mLoggingFlow = 67209;
            getActivity().setTitle(R.string.active_key_dedicated_app_title);
            return;
        }
        if ("xcover".equals(string)) {
            this.mPressType = 1;
            this.mLoggingFlow = 67209;
            getActivity().setTitle(R.string.xcover_key_dedicated_app_title);
        } else if ("top".equals(string)) {
            this.mPressType = 2;
            this.mLoggingFlow = 67219;
            getActivity().setTitle(R.string.xcover_top_key_dedicated_app_title);
        } else if ("sidekey".equals(string)) {
            this.mPressType = 3;
            this.mLoggingFlow = 67220;
            getActivity().setTitle(R.string.sec_short_press_dedicated_app);
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        DedicatedAppInfo.setDedicatedAppSwitch(this.mContext, this.mPressType, z);
        if (DedicatedAppInfo.getDedicatedAppState(this.mContext, this.mPressType)) {
            DedicatedAppInfo.setB2BDeltaKeyCustomizationInfo(
                    this.mContext,
                    this.mPressType,
                    DedicatedAppInfo.getDedicatedApp(this.mContext, this.mPressType),
                    z);
        }
        setDetailView(Boolean.valueOf(z));
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getActivity().getApplicationContext();
        FragmentActivity activity = getActivity();
        if (this.mPressType != 0) {}
        if (UsefulfeatureUtils.hasDedicatedAppEnable$1(activity)
                || UsefulfeatureUtils.hasSideKeyDedicatedAppEnable(getActivity())) {
            return;
        }
        DedicatedAppInfo.setDedicatedAppSwitch(this.mContext, this.mPressType, false);
        DedicatedAppInfo.saveDedicatedApp(this.mContext, this.mPressType, ApnSettings.MVNO_NONE);
        DedicatedAppInfo.saveDedicatedAppLabel(
                this.mContext, this.mPressType, ApnSettings.MVNO_NONE);
        getActivity().finish();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate =
                layoutInflater.inflate(R.layout.sec_labs_app_picker_view_layout, (ViewGroup) null);
        AppPickerView appPickerView =
                (AppPickerView) inflate.findViewById(R.id.sec_labs_app_picker_view);
        this.mAppPickerView = appPickerView;
        appPickerView.setNestedScrollingEnabled(true);
        this.mAppPickerView.semSetRoundedCorners(15);
        this.mAppPickerView.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mAppPickerView.setVisibility(8);
        ViewGroup viewGroup2 = (ViewGroup) inflate.findViewById(R.id.loading_panel);
        this.mProgressBar = viewGroup2;
        viewGroup2.semSetRoundedCorners(3);
        this.mProgressBar.semSetRoundedCornerColor(
                3, getContext().getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        return inflate;
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
            this.mSwitchBar.hide();
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        if (this.mProgressBar.getVisibility() == 0) {
            this.mProgressBar.setVisibility(8);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        boolean dedicatedAppSwitch =
                DedicatedAppInfo.getDedicatedAppSwitch(this.mContext, this.mPressType);
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setChecked(dedicatedAppSwitch);
        }
        setDetailView(Boolean.valueOf(dedicatedAppSwitch));
    }

    public final void setDetailView(Boolean bool) {
        if (!bool.booleanValue()) {
            removeMessages(1);
            this.mProgressBar.setVisibility(8);
            this.mAppPickerView.setVisibility(8);
        } else {
            if (this.mLoadAppListTask.getStatus() == AsyncTask.Status.RUNNING) {
                return;
            }
            if (this.mLoadAppListTask.getStatus() == AsyncTask.Status.PENDING) {
                this.mLoadAppListTask.executeOnExecutor(
                        AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
                return;
            }
            this.mProgressBar.setVisibility(0);
            this.mAppPickerView.setVisibility(8);
            sendEmptyMessageDelayed(1, 100L);
        }
    }
}

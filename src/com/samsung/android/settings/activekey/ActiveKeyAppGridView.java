package com.samsung.android.settings.activekey;

import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.UserHandle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.picker.model.AppData;
import androidx.picker.model.AppInfo;
import androidx.picker.model.AppInfoData;
import androidx.picker.widget.AppPickerEvent$OnItemClickEventListener;
import androidx.picker.widget.SeslAppPickerGridView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.applications.AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ActiveKeyAppGridView extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener {
    public SeslAppPickerGridView mAppPickerGridView;
    public Context mContext;
    public TextView mEmptyText;
    public ViewGroup mEmptyView;
    public LoadAppListTask mLoadAppListTask;
    public ViewGroup mLoading;
    public int mPressType;
    public boolean mState;
    public SettingsMainSwitchBar mSwitchBar;
    public ArrayList mBlockList = null;
    public int mLoggingFlow = -1;
    public final AnonymousClass1 mHandler =
            new Handler() { // from class:
                            // com.samsung.android.settings.activekey.ActiveKeyAppGridView.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    ActiveKeyAppGridView activeKeyAppGridView;
                    ViewGroup viewGroup;
                    if (message.what == 1
                            && (viewGroup =
                                            (activeKeyAppGridView = ActiveKeyAppGridView.this)
                                                    .mLoading)
                                    != null) {
                        viewGroup.setVisibility(8);
                        activeKeyAppGridView.mAppPickerGridView.setVisibility(0);
                        activeKeyAppGridView.mEmptyText.setVisibility(8);
                    }
                    super.handleMessage(message);
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LoadAppListTask extends AsyncTask {

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class AlphaComparator implements Comparator {
            public final Collator sCollator = Collator.getInstance();

            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                AppInfoData appInfoData = (AppInfoData) obj;
                AppInfoData appInfoData2 = (AppInfoData) obj2;
                if (appInfoData == null || appInfoData.getLabel() == null) {
                    return -1;
                }
                if (appInfoData2 == null || appInfoData2.getLabel() == null) {
                    return 1;
                }
                return this.sCollator.compare(appInfoData.getLabel(), appInfoData2.getLabel());
            }
        }

        public LoadAppListTask() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            ArrayList arrayList;
            synchronized (this) {
                try {
                    HashMap hashMap = new HashMap();
                    ActiveKeyAppGridView activeKeyAppGridView = ActiveKeyAppGridView.this;
                    String string =
                            Settings.System.getString(
                                    activeKeyAppGridView.mContext.getContentResolver(),
                                    ActiveKeyInfo.getActiveKeyDBKey(
                                            activeKeyAppGridView.mPressType));
                    String str = ApnSettings.MVNO_NONE;
                    if (string != null) {
                        str = string.split("/")[0];
                    }
                    for (LauncherActivityInfo launcherActivityInfo :
                            ((LauncherApps)
                                            ActiveKeyAppGridView.this
                                                    .getContext()
                                                    .getSystemService("launcherapps"))
                                    .getActivityList(
                                            null, new UserHandle(UserHandle.getCallingUserId()))) {
                        String str2 = launcherActivityInfo.getApplicationInfo().packageName;
                        String name = launcherActivityInfo.getName();
                        ArrayList arrayList2 = ActiveKeyAppGridView.this.mBlockList;
                        if (arrayList2 != null) {
                            Iterator it = arrayList2.iterator();
                            while (it.hasNext()) {
                                String str3 = (String) it.next();
                                if (!name.contains(str3) && !str2.equals(str3)) {}
                            }
                        }
                        int appId =
                                UserHandle.getAppId(launcherActivityInfo.getApplicationInfo().uid);
                        AppInfo.Companion companion = AppInfo.Companion;
                        AppInfoData build =
                                new AppData.ListAppDataBuilder(
                                                AppInfo.Companion.obtain(appId, str2, name))
                                        .setIcon(launcherActivityInfo.getIcon(0))
                                        .setLabel(launcherActivityInfo.getLabel().toString())
                                        .build();
                        if (str2.equals(str)) {
                            build.setDimmed(true);
                        }
                        if (!hashMap.containsKey(build.getLabel())) {
                            hashMap.put(build.getLabel(), build);
                        }
                    }
                    arrayList = new ArrayList();
                    Iterator it2 = hashMap.keySet().iterator();
                    while (it2.hasNext()) {
                        arrayList.add((AppInfoData) hashMap.get((String) it2.next()));
                    }
                    arrayList.sort(new AlphaComparator());
                } catch (Throwable th) {
                    throw th;
                }
            }
            return arrayList;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            List list = (List) obj;
            ActiveKeyAppGridView activeKeyAppGridView = ActiveKeyAppGridView.this;
            ViewGroup viewGroup = activeKeyAppGridView.mLoading;
            if (viewGroup != null) {
                viewGroup.setVisibility(8);
                activeKeyAppGridView.mAppPickerGridView.setVisibility(0);
                activeKeyAppGridView.mEmptyText.setVisibility(8);
            }
            ArrayList arrayList = new ArrayList(list);
            ActiveKeyAppGridView activeKeyAppGridView2 = ActiveKeyAppGridView.this;
            String string =
                    Settings.System.getString(
                            activeKeyAppGridView2.mContext.getContentResolver(),
                            ActiveKeyInfo.getActiveKeyDBKey(activeKeyAppGridView2.mPressType));
            if (string != null) {
                String str = string.split("/")[0];
            }
            UsefulfeatureUtils.hasActiveKey();
            UsefulfeatureUtils.hasActiveKey();
            ((SimpleItemAnimator) ActiveKeyAppGridView.this.mAppPickerGridView.getItemAnimator())
                            .mSupportsChangeAnimations =
                    false;
            ActiveKeyAppGridView.this.mAppPickerGridView.submitList(arrayList);
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            ActiveKeyAppGridView activeKeyAppGridView = ActiveKeyAppGridView.this;
            ViewGroup viewGroup = activeKeyAppGridView.mLoading;
            if (viewGroup != null) {
                viewGroup.setVisibility(0);
                activeKeyAppGridView.mAppPickerGridView.setVisibility(8);
                activeKeyAppGridView.mEmptyText.setVisibility(8);
            }
        }
    }

    public final void doAppLoading(boolean z) {
        if (!z) {
            removeMessages(1);
            this.mLoading.setVisibility(8);
            this.mAppPickerGridView.setVisibility(8);
            this.mEmptyText.setVisibility(0);
            return;
        }
        if (this.mLoadAppListTask.getStatus() == AsyncTask.Status.PENDING) {
            this.mLoadAppListTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
            return;
        }
        ViewGroup viewGroup = this.mLoading;
        if (viewGroup != null) {
            viewGroup.setVisibility(0);
            this.mAppPickerGridView.setVisibility(8);
            this.mEmptyText.setVisibility(8);
        }
        sendEmptyMessageDelayed(1, 250L);
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
        this.mSwitchBar.setChecked(this.mState);
        this.mLoadAppListTask = new LoadAppListTask();
        doAppLoading(this.mState);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        String string = getArguments().getString("pressed_type");
        if ("long".equals(string)) {
            this.mPressType = 1;
            this.mLoggingFlow = 67204;
            return;
        }
        if ("double".equals(string)) {
            this.mPressType = 2;
            this.mLoggingFlow = 67203;
            return;
        }
        if ("short".equals(string)) {
            this.mPressType = 0;
            this.mLoggingFlow = 67203;
        } else if ("xcover_top_short".equals(string)) {
            this.mPressType = 3;
            this.mLoggingFlow = 67213;
        } else if ("xcover_top_long".equals(string)) {
            this.mPressType = 4;
            this.mLoggingFlow = 67214;
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (z) {
            Context context = this.mContext;
            int i = this.mPressType;
            String string =
                    Settings.System.getString(
                            context.getContentResolver(),
                            i != 1
                                    ? i != 2
                                            ? i != 3
                                                    ? i != 4
                                                            ? "short_press_app_backup"
                                                            : "xcover_top_long_press_app_backup"
                                                    : "xcover_top_short_press_app_backup"
                                            : "double_press_app_backup"
                                    : "long_press_app_backup");
            Settings.System.putString(
                    context.getContentResolver(), ActiveKeyInfo.getActiveKeyDBKey(i), string);
            StringBuilder sb = new StringBuilder("[restore] Press Type : ");
            sb.append(i);
            AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                    sb, "/ app : ", string, "ActiveKeyInfo");
            if (ActiveKeyInfo.getPressActionState(this.mContext, this.mPressType)) {
                ActiveKeyInfo.setExtraKeyCustomizationInfo(
                        this.mPressType,
                        Settings.System.getString(
                                this.mContext.getContentResolver(),
                                ActiveKeyInfo.getActiveKeyDBKey(this.mPressType)),
                        z);
            }
        } else {
            Context context2 = this.mContext;
            int i2 = this.mPressType;
            String string2 =
                    Settings.System.getString(
                            context2.getContentResolver(), ActiveKeyInfo.getActiveKeyDBKey(i2));
            Settings.System.putString(
                    context2.getContentResolver(),
                    i2 != 1
                            ? i2 != 2
                                    ? i2 != 3
                                            ? i2 != 4
                                                    ? "short_press_app_backup"
                                                    : "xcover_top_long_press_app_backup"
                                            : "xcover_top_short_press_app_backup"
                                    : "double_press_app_backup"
                            : "long_press_app_backup",
                    string2);
            Settings.System.putString(
                    context2.getContentResolver(),
                    ActiveKeyInfo.getActiveKeyDBKey(i2),
                    ApnSettings.MVNO_NONE);
            StringBuilder sb2 = new StringBuilder("[remove] Press Type : ");
            sb2.append(i2);
            AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                    sb2, "/ app : ", string2, "ActiveKeyInfo");
            ActiveKeyInfo.setExtraKeyCustomizationInfo(this.mPressType, ApnSettings.MVNO_NONE, z);
        }
        doAppLoading(z);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        ArrayList arrayList;
        super.onCreate(bundle);
        Context context = getContext();
        this.mContext = context;
        this.mState = ActiveKeyInfo.getPressActionState(context, this.mPressType);
        Context context2 = this.mContext;
        if (UsefulfeatureUtils.hasVzwPttEnable(context2)) {
            arrayList = new ArrayList(1);
            arrayList.add("com.verizon.pushtotalkplus");
        } else if (UsefulfeatureUtils.hasEPttEnable(context2)) {
            arrayList = new ArrayList(1);
            arrayList.add("com.att.eptt");
        } else if (UsefulfeatureUtils.hasBMCPttEnable(context2)) {
            arrayList = new ArrayList(1);
            arrayList.add("com.bell.ptt");
        } else if (UsefulfeatureUtils.hasTMOPttEnable(context2)) {
            arrayList = new ArrayList(1);
            arrayList.add("com.sprint.sdcplus");
        } else {
            arrayList = null;
        }
        this.mBlockList = arrayList;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.sec_app_shortcut_view, viewGroup, false);
        this.mAppPickerGridView = new SeslAppPickerGridView(this.mContext);
        SeslAppPickerGridView seslAppPickerGridView =
                (SeslAppPickerGridView) inflate.findViewById(R.id.appshortcutview);
        this.mAppPickerGridView = seslAppPickerGridView;
        seslAppPickerGridView.setNestedScrollingEnabled(true);
        this.mAppPickerGridView.semSetRoundedCorners(15);
        this.mAppPickerGridView.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mAppPickerGridView.setVisibility(8);
        this.mAppPickerGridView.setOnItemClickEventListener(
                new AppPickerEvent$OnItemClickEventListener() { // from class:
                                                                // com.samsung.android.settings.activekey.ActiveKeyAppGridView$$ExternalSyntheticLambda0
                    @Override // androidx.picker.widget.AppPickerEvent$OnItemClickEventListener
                    public final boolean onClick(View view, AppInfo appInfo) {
                        ActiveKeyAppGridView activeKeyAppGridView = ActiveKeyAppGridView.this;
                        String string =
                                Settings.Secure.getString(
                                        activeKeyAppGridView.mContext.getContentResolver(),
                                        "default_input_method");
                        if (!"quickMessageSender".equals(appInfo.packageName)
                                || "com.samsung.android.honeyboard/.service.HoneyBoardService"
                                        .equals(string)) {
                            String str = appInfo.packageName + "/" + appInfo.activityName;
                            Context context = activeKeyAppGridView.mContext;
                            int i = activeKeyAppGridView.mPressType;
                            Settings.System.putString(
                                    context.getContentResolver(),
                                    ActiveKeyInfo.getActiveKeyDBKey(i),
                                    str);
                            StringBuilder sb = new StringBuilder("[save] Press Type : ");
                            sb.append(i);
                            AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                                    sb, "/ app : ", str, "ActiveKeyInfo");
                            ActiveKeyInfo.setExtraKeyCustomizationInfo(i, str, true);
                            activeKeyAppGridView.getActivity().onBackPressed();
                        } else {
                            Toast.makeText(
                                            activeKeyAppGridView.mContext,
                                            R.string
                                                    .xcover_key_shortcut_quick_message_sender_warning,
                                            0)
                                    .show();
                        }
                        return true;
                    }
                });
        ViewGroup viewGroup2 = (ViewGroup) inflate.findViewById(R.id.loading_panel);
        this.mLoading = viewGroup2;
        viewGroup2.semSetRoundedCorners(3);
        this.mLoading.semSetRoundedCornerColor(
                3, this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mEmptyView = (ViewGroup) inflate.findViewById(R.id.empty_view);
        this.mEmptyText = (TextView) inflate.findViewById(android.R.id.empty);
        UsefulfeatureUtils.hasActiveKey();
        this.mEmptyText.setText(R.string.active_key_shortcut_off_message);
        this.mEmptyText.setTextSize(
                this.mContext
                        .getResources()
                        .getInteger(R.integer.daydream_settings_empty_view_text_size));
        this.mEmptyText.setPadding(
                this.mContext
                        .getResources()
                        .getDimensionPixelSize(R.dimen.sec_widget_body_text_padding_start_end),
                this.mEmptyText.getPaddingTop(),
                this.mEmptyText.getPaddingRight(),
                this.mEmptyText.getPaddingBottom());
        this.mEmptyText.setBackgroundColor(
                this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mEmptyText.setTextAppearance(this.mContext, R.style.description_text);
        return inflate;
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.hide();
        }
        if (this.mLoading != null) {
            this.mLoading = null;
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.addOnSwitchChangeListener(this);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
        }
    }
}

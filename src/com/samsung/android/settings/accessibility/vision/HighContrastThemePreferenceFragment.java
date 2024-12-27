package com.samsung.android.settings.accessibility.vision;

import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;

import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.widget.SingleChoicePreference;
import com.samsung.android.thememanager.IThemeManager;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class HighContrastThemePreferenceFragment extends DashboardFragment {
    public String mCurrentTheme;
    public Context mContext = null;
    public IThemeManager mThemePlatformManager = null;
    public final AnonymousClass5 mConnection =
            new ServiceConnection() { // from class:
                                      // com.samsung.android.settings.accessibility.vision.HighContrastThemePreferenceFragment.5
                @Override // android.content.ServiceConnection
                public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    IThemeManager iThemeManager;
                    Log.d("HighContrastThemePreferenceFragment", "theme manager service connected");
                    HighContrastThemePreferenceFragment highContrastThemePreferenceFragment =
                            HighContrastThemePreferenceFragment.this;
                    int i = IThemeManager.Stub.$r8$clinit;
                    if (iBinder == null) {
                        iThemeManager = null;
                    } else {
                        IInterface queryLocalInterface =
                                iBinder.queryLocalInterface(
                                        "com.samsung.android.thememanager.IThemeManager");
                        if (queryLocalInterface == null
                                || !(queryLocalInterface instanceof IThemeManager)) {
                            IThemeManager.Stub.Proxy proxy = new IThemeManager.Stub.Proxy();
                            proxy.mRemote = iBinder;
                            iThemeManager = proxy;
                        } else {
                            iThemeManager = (IThemeManager) queryLocalInterface;
                        }
                    }
                    highContrastThemePreferenceFragment.mThemePlatformManager = iThemeManager;
                    try {
                        HighContrastThemePreferenceFragment highContrastThemePreferenceFragment2 =
                                HighContrastThemePreferenceFragment.this;
                        IThemeManager iThemeManager2 =
                                highContrastThemePreferenceFragment2.mThemePlatformManager;
                        AnonymousClass6 anonymousClass6 =
                                highContrastThemePreferenceFragment2.applyThemeObserver;
                        IThemeManager.Stub.Proxy proxy2 = (IThemeManager.Stub.Proxy) iThemeManager2;
                        Parcel obtain = Parcel.obtain(proxy2.mRemote);
                        Parcel obtain2 = Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(
                                    "com.samsung.android.thememanager.IThemeManager");
                            obtain.writeStrongInterface(anonymousClass6);
                            proxy2.mRemote.transact(22, obtain, obtain2, 0);
                            obtain2.readException();
                            obtain2.recycle();
                            obtain.recycle();
                        } catch (Throwable th) {
                            obtain2.recycle();
                            obtain.recycle();
                            throw th;
                        }
                    } catch (Exception e) {
                        Log.e(
                                "HighContrastThemePreferenceFragment",
                                "ServiceConnection exception",
                                e);
                    }
                }

                @Override // android.content.ServiceConnection
                public final void onServiceDisconnected(ComponentName componentName) {
                    Log.d(
                            "HighContrastThemePreferenceFragment",
                            "themeManager service disconnected");
                    HighContrastThemePreferenceFragment.this.mThemePlatformManager = null;
                }
            };
    public final AnonymousClass6 applyThemeObserver = new AnonymousClass6();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessibility.vision.HighContrastThemePreferenceFragment$1, reason: invalid class name */
    public final class AnonymousClass1
            implements SingleChoicePreference.CheckItemDefaultKey,
                    SingleChoicePreference.OnItemSelectedListener {
        public /* synthetic */ AnonymousClass1() {}

        @Override // com.samsung.android.settings.accessibility.base.widget.SingleChoicePreference.CheckItemDefaultKey
        public String getDefaultKey() {
            HighContrastThemePreferenceFragment highContrastThemePreferenceFragment =
                    HighContrastThemePreferenceFragment.this;
            Context context = highContrastThemePreferenceFragment.mContext;
            HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
            String string =
                    Settings.System.getString(
                            context.getContentResolver(), "current_sec_active_themepackage");
            highContrastThemePreferenceFragment.mCurrentTheme = string;
            if (string == null) {
                return null;
            }
            if (string.equals("com.samsung.Dream_A11y_lowvision")
                    || highContrastThemePreferenceFragment.mCurrentTheme.equals(
                            "com.samsung.High_contrast_theme_I")) {
                return "com.samsung.High_contrast_theme_I";
            }
            if (highContrastThemePreferenceFragment.mCurrentTheme.equals(
                    "com.samsung.High_contrast_theme_II")) {
                return highContrastThemePreferenceFragment.mCurrentTheme;
            }
            return null;
        }

        @Override // com.samsung.android.settings.accessibility.base.widget.SingleChoicePreference.OnItemSelectedListener
        public void onItemSelected(final String str) {
            final HighContrastThemePreferenceFragment highContrastThemePreferenceFragment =
                    HighContrastThemePreferenceFragment.this;
            Context context = highContrastThemePreferenceFragment.mContext;
            HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
            if (!(ActivityManager.semGetCurrentUser() == 0
                    ? Utils.hasPackage(context, "com.samsung.android.themestore")
                    : false)) {
                String string =
                        highContrastThemePreferenceFragment.getString(R.string.galaxy_themes);
                String string2 =
                        highContrastThemePreferenceFragment.getString(
                                R.string.download_scloud_title, string);
                String string3 =
                        highContrastThemePreferenceFragment.getString(
                                R.string.download_desc, string);
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(highContrastThemePreferenceFragment.mContext);
                AlertController.AlertParams alertParams = builder.P;
                alertParams.mTitle = string2;
                alertParams.mMessage = string3;
                builder.setPositiveButton(
                        R.string.monotype_dialog_button,
                        new DialogInterface.OnClickListener() { // from class:
                            // com.samsung.android.settings.accessibility.vision.HighContrastThemePreferenceFragment.4
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent("android.intent.action.VIEW");
                                intent.setData(
                                        Uri.parse(
                                                "samsungapps://ProductDetail/com.samsung.android.themestore"));
                                intent.addFlags(335544352);
                                HighContrastThemePreferenceFragment.this.startActivity(intent);
                            }
                        });
                builder.setNegativeButton(
                        android.R.string.cancel, (DialogInterface.OnClickListener) null);
                builder.create().show();
                return;
            }
            if ("com.samsung.Dream_A11y_lowvision".equals(str)
                    || "com.samsung.High_contrast_theme_I".equals(str)
                    || "com.samsung.High_contrast_theme_II".equals(str)) {
                Intent intent = new Intent();
                intent.addFlags(335544352);
                if (highContrastThemePreferenceFragment
                        .mContext
                        .getPackageManager()
                        .getInstalledApplications(0)
                        .stream()
                        .anyMatch(
                                new Predicate() { // from class:
                                                  // com.samsung.android.settings.accessibility.vision.HighContrastThemePreferenceFragment$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return ((ApplicationInfo) obj).packageName.equals(str);
                                    }
                                })) {
                    intent.setData(
                            Uri.parse(
                                    "themestore://LocalProductDetail?appId="
                                            + str
                                            + "&from=accessibility&contentsType=THEMES&from=accessibility"));
                } else {
                    intent.setData(
                            Uri.parse(
                                    "themestore://ProductDetail?appId="
                                            + str
                                            + "&from=accessibility"));
                }
                try {
                    highContrastThemePreferenceFragment.startActivity(intent);
                    return;
                } catch (ActivityNotFoundException e) {
                    Log.w("HighContrastThemePreferenceFragment", "ActivityNotFoundException!", e);
                    return;
                }
            }
            String string4 =
                    Settings.System.getString(
                            highContrastThemePreferenceFragment.mContext.getContentResolver(),
                            "current_sec_active_themepackage");
            highContrastThemePreferenceFragment.mCurrentTheme = string4;
            if (string4 != null) {
                if (highContrastThemePreferenceFragment.mThemePlatformManager == null) {
                    Log.i(
                            "HighContrastThemePreferenceFragment",
                            "Theme Service disconnect. Start reconnect");
                    highContrastThemePreferenceFragment.mThemePlatformManager =
                            highContrastThemePreferenceFragment.getThemePlatformManager();
                    for (int i = 0;
                            highContrastThemePreferenceFragment.mThemePlatformManager == null
                                    && i < 1000;
                            i++) {
                        MainClearConfirm$$ExternalSyntheticOutline0.m(
                                i,
                                "onPreferenceChange fall asleep for ",
                                "HighContrastThemePreferenceFragment");
                        try {
                            Thread.sleep(10L);
                        } catch (Exception e2) {
                            Log.w(
                                    "HighContrastThemePreferenceFragment",
                                    "Thread sleep interrupted.",
                                    e2);
                        }
                    }
                }
                IThemeManager iThemeManager =
                        highContrastThemePreferenceFragment.mThemePlatformManager;
                if (iThemeManager == null) {
                    Log.i(
                            "HighContrastThemePreferenceFragment",
                            "Theme Service disconnect. Start reconnect");
                    return;
                }
                try {
                    ((IThemeManager.Stub.Proxy) iThemeManager).applyThemePackage();
                } catch (RemoteException e3) {
                    Log.w("HighContrastThemePreferenceFragment", "RemoteException!", e3);
                }
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "HighContrastThemePreferenceFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return VolteConstants.ErrorCode.CANCEL_CALL_COMPLETED_ELSEWHERE_FORKED;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.accessibility_settings_high_contrast_theme;
    }

    public final IThemeManager getThemePlatformManager() {
        if (this.mThemePlatformManager == null) {
            Log.d("HighContrastThemePreferenceFragment", "bindThemePlatformService()");
            Intent intent = new Intent();
            intent.setClassName(
                    "com.samsung.android.themecenter",
                    "com.samsung.android.thememanager.ThemeManagerService");
            this.mContext.startService(intent);
            try {
                if (this.mContext.bindService(intent, this.mConnection, 1)) {
                    Log.d("HighContrastThemePreferenceFragment", "theme manager service found!");
                } else {
                    Log.e(
                            "HighContrastThemePreferenceFragment",
                            "theme manager service not found!");
                    this.mContext.unbindService(this.mConnection);
                }
            } catch (Exception e) {
                Log.w("HighContrastThemePreferenceFragment", "Exception!", e);
            }
        }
        int i = 0;
        while (this.mThemePlatformManager == null && i < 100) {
            try {
                Thread.sleep(10L);
            } catch (Exception e2) {
                Log.w("HighContrastThemePreferenceFragment", "Thread sleep interrupted.", e2);
            }
            i++;
        }
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                i, "fall asleep for ", "HighContrastThemePreferenceFragment");
        return this.mThemePlatformManager;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = getContext();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        super.onCreatePreferences(bundle, str);
        SingleChoicePreference singleChoicePreference =
                (SingleChoicePreference) findPreference("single_choice_pref");
        if (singleChoicePreference != null) {
            ArrayList arrayList = new ArrayList();
            AnonymousClass1 anonymousClass1 = new AnonymousClass1();
            arrayList.add(
                    new SingleChoicePreference.SingleChoiceCandidateInfo(
                            SignalSeverity.NONE,
                            getString(R.string.app_list_preference_none),
                            this.mContext.getDrawable(R.drawable.high_contrast_theme_none),
                            anonymousClass1,
                            true));
            arrayList.add(
                    new SingleChoicePreference.SingleChoiceCandidateInfo(
                            "com.samsung.High_contrast_theme_I",
                            getString(R.string.color_yellow),
                            this.mContext.getDrawable(R.drawable.high_contrast_theme_yellow),
                            anonymousClass1,
                            false));
            arrayList.add(
                    new SingleChoicePreference.SingleChoiceCandidateInfo(
                            "com.samsung.High_contrast_theme_II",
                            getString(R.string.color_blue),
                            this.mContext.getDrawable(R.drawable.high_contrast_theme_blue),
                            anonymousClass1,
                            false));
            AnonymousClass1 anonymousClass12 = new AnonymousClass1();
            SingleChoicePreference.SingleChoiceAdapter singleChoiceAdapter =
                    singleChoicePreference.gridAdapter;
            singleChoiceAdapter.list = arrayList;
            singleChoiceAdapter.defaultKey = anonymousClass12;
            singleChoicePreference.notifyChanged();
        }
        Context context = this.mContext;
        HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
        String string =
                Settings.System.getString(
                        context.getContentResolver(), "current_sec_active_themepackage");
        this.mCurrentTheme = string;
        if (string == null || !string.equals("com.samsung.Dream_A11y_lowvision")) {
            removePreference("high_contrast_category");
            removePreference("high_contrast_tip");
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        try {
            if (this.mThemePlatformManager != null) {
                Log.d(
                        "HighContrastThemePreferenceFragment",
                        "onPause : unregisterStatusListener applyThemeObserver");
                IThemeManager iThemeManager = this.mThemePlatformManager;
                AnonymousClass6 anonymousClass6 = this.applyThemeObserver;
                IThemeManager.Stub.Proxy proxy = (IThemeManager.Stub.Proxy) iThemeManager;
                Parcel obtain = Parcel.obtain(proxy.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.thememanager.IThemeManager");
                    obtain.writeStrongInterface(anonymousClass6);
                    proxy.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    obtain2.recycle();
                    obtain.recycle();
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                    throw th;
                }
            }
        } catch (Exception e) {
            Log.e(
                    "HighContrastThemePreferenceFragment",
                    "onPause : unregisterStatusListener exception",
                    e);
        }
        if (this.mConnection == null || this.mThemePlatformManager == null) {
            return;
        }
        try {
            Log.d("HighContrastThemePreferenceFragment", "onPause : unbind service");
            this.mContext.unbindService(this.mConnection);
        } catch (Exception e2) {
            Log.w("HighContrastThemePreferenceFragment", "Exception during unbindService!", e2);
        }
        this.mThemePlatformManager = null;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        new Thread(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.accessibility.vision.HighContrastThemePreferenceFragment.3
                            @Override // java.lang.Runnable
                            public final void run() {
                                HighContrastThemePreferenceFragment
                                        highContrastThemePreferenceFragment =
                                                HighContrastThemePreferenceFragment.this;
                                if (highContrastThemePreferenceFragment.mThemePlatformManager
                                        == null) {
                                    highContrastThemePreferenceFragment.getThemePlatformManager();
                                }
                                try {
                                    Thread.sleep(1000L);
                                } catch (InterruptedException e) {
                                    Log.w(
                                            "HighContrastThemePreferenceFragment",
                                            "InterruptedException!",
                                            e);
                                }
                            }
                        })
                .start();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessibility.vision.HighContrastThemePreferenceFragment$6, reason: invalid class name */
    public final class AnonymousClass6 extends Binder implements IInterface {
        public AnonymousClass6() {
            attachInterface(this, "com.samsung.android.thememanager.IStatusListener");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.samsung.android.thememanager.IStatusListener");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.samsung.android.thememanager.IStatusListener");
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Log.d(
                            "HighContrastThemePreferenceFragment",
                            "onInstallProgress pkg = " + readString + ", progress = " + readInt);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Log.d(
                            "HighContrastThemePreferenceFragment",
                            "onInstallCompleted pkg = " + readString2 + ", result = " + readInt2);
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Log.d(
                            "HighContrastThemePreferenceFragment",
                            "onUninstallProgress pkg = "
                                    + readString3
                                    + ", progress = "
                                    + readInt3);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Log.d(
                            "HighContrastThemePreferenceFragment",
                            "onUninstallCompleted pkg = " + readString4 + ", result = " + readInt4);
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Log.d(
                            "HighContrastThemePreferenceFragment",
                            "onStateChangeProgress pkg = "
                                    + readString5
                                    + ", progress = "
                                    + readInt5);
                    if (readInt5 == 100) {
                        HighContrastThemePreferenceFragment.this.finishFragment();
                    }
                    return true;
                case 6:
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Log.d(
                            "HighContrastThemePreferenceFragment",
                            "onStateChangeCompleted pkg = "
                                    + readString6
                                    + ", type = "
                                    + readString7
                                    + ", result = "
                                    + readInt6);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}

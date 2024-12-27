package com.samsung.android.settings.usefulfeature.motionandgestures.fingersensorgesture;

import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.usefulfeature.motionandgestures.MotionAndGestureSettings;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class FingerSensorGestureSettings extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass4(R.xml.sec_finger_sensor_gesture);
    public Context mContext = null;
    public final AnonymousClass1 mFingerSensorGestureObserver;
    public final AnonymousClass1 mFingerSensorGestureSpayObserver;
    public SecSwitchPreference mNotificationPanel;
    public LayoutPreference mPreviewPreference;
    public SecSwitchPreference mSamsungPay;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.usefulfeature.motionandgestures.fingersensorgesture.FingerSensorGestureSettings$4, reason: invalid class name */
    public final class AnonymousClass4 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (!Rune.supportFingerPrint(context)
                    || !Rune.supportFingerSensorGestureSpay(context)
                    || !FingerSensorGestureSettings.isSamsungPayGestureVisible(context)) {
                ((ArrayList) nonIndexableKeys).add("open_samsung_pay");
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            Resources resources = context.getResources();
            if (Rune.supportFingerPrint(context)
                    && Rune.supportFingerSensorGestureSpay(context)
                    && FingerSensorGestureSettings.isSamsungPayGestureVisible(context)) {
                SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
                ((SearchIndexableData) searchIndexableRaw).key = "open_samsung_pay";
                searchIndexableRaw.screenTitle =
                        resources.getString(R.string.finger_sensor_gesture_title);
                String str = ApnSettings.MVNO_NONE;
                boolean z = true;
                try {
                    Bundle call =
                            context.getContentResolver()
                                    .call(
                                            Uri.parse(
                                                    "content://com.samsung.android.spay.common.share/global"),
                                            "GET_global",
                                            "samsungwallet_finger_sensor_gesture",
                                            (Bundle) null);
                    if (Integer.parseInt(call.getString("value")) != 1) {
                        z = false;
                    }
                    str = call.getString(UniversalCredentialUtil.AGENT_TITLE);
                } catch (IllegalArgumentException e) {
                    Log.e(
                            "FingerSensorGestureSettings",
                            "samsung pay title and summary is not read",
                            e);
                } catch (NullPointerException | NumberFormatException e2) {
                    Log.e(
                            "FingerSensorGestureSettings",
                            "samsung pay title and summary is not read",
                            e2);
                }
                if (TextUtils.isEmpty(str)) {
                    if ((Utils.hasPackage(context, "com.samsung.android.spay")
                                    ? "com.samsung.android.spay"
                                    : "com.samsung.android.spaymini")
                            .contains("spaymini")) {
                        searchIndexableRaw.title =
                                String.valueOf(R.string.open_samsung_pay_mini_title);
                        searchIndexableRaw.keywords =
                                Utils.getKeywordForSearch(
                                        context, R.string.open_samsung_pay_mini_title);
                    } else {
                        searchIndexableRaw.title = String.valueOf(R.string.open_samsung_pay_title);
                        searchIndexableRaw.keywords =
                                Utils.getKeywordForSearch(context, R.string.open_samsung_pay_title);
                    }
                } else {
                    searchIndexableRaw.title = str;
                }
                ((SearchIndexableData) searchIndexableRaw).enabled = z;
                arrayList.add(searchIndexableRaw);
            }
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return Rune.supportFingerPrint(context);
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.usefulfeature.motionandgestures.fingersensorgesture.FingerSensorGestureSettings$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.settings.usefulfeature.motionandgestures.fingersensorgesture.FingerSensorGestureSettings$1] */
    public FingerSensorGestureSettings() {
        final int i = 0;
        this.mFingerSensorGestureObserver =
                new ContentObserver(
                        this,
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.usefulfeature.motionandgestures.fingersensorgesture.FingerSensorGestureSettings.1
                    public final /* synthetic */ FingerSensorGestureSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i) {
                            case 0:
                                this.this$0.mNotificationPanel.setChecked(
                                        Settings.System.getInt(
                                                        this.this$0.mContext.getContentResolver(),
                                                        "fingerprint_gesture_quick",
                                                        0)
                                                > 0);
                                break;
                            default:
                                this.this$0.mSamsungPay.setChecked(
                                        Settings.System.getInt(
                                                        this.this$0.mContext.getContentResolver(),
                                                        "fingerprint_gesture_spay",
                                                        0)
                                                > 0);
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mFingerSensorGestureSpayObserver =
                new ContentObserver(
                        this,
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.usefulfeature.motionandgestures.fingersensorgesture.FingerSensorGestureSettings.1
                    public final /* synthetic */ FingerSensorGestureSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i2) {
                            case 0:
                                this.this$0.mNotificationPanel.setChecked(
                                        Settings.System.getInt(
                                                        this.this$0.mContext.getContentResolver(),
                                                        "fingerprint_gesture_quick",
                                                        0)
                                                > 0);
                                break;
                            default:
                                this.this$0.mSamsungPay.setChecked(
                                        Settings.System.getInt(
                                                        this.this$0.mContext.getContentResolver(),
                                                        "fingerprint_gesture_spay",
                                                        0)
                                                > 0);
                                break;
                        }
                    }
                };
    }

    public static boolean isSamsungPayGestureEnabled(Context context) {
        try {
            return Integer.parseInt(
                            context.getContentResolver()
                                    .call(
                                            Uri.parse(
                                                    "content://com.samsung.android.spay.common.share/global"),
                                            "GET_global",
                                            "samsung_pay_favorite_cards_on_home",
                                            (Bundle) null)
                                    .getString("value"))
                    == 1;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException e2) {
            e2.printStackTrace();
            return false;
        } catch (NumberFormatException e3) {
            e3.printStackTrace();
            return false;
        }
    }

    public static boolean isSamsungPayGestureVisible(Context context) {
        try {
            return Integer.parseInt(
                            context.getContentResolver()
                                    .call(
                                            Uri.parse(
                                                    "content://com.samsung.android.spay.common.share/global"),
                                            "GET_global",
                                            "samsung_pay_provisioning_status",
                                            (Bundle) null)
                                    .getString("value"))
                    == 1;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException e2) {
            e2.printStackTrace();
            return false;
        } catch (NumberFormatException e3) {
            e3.printStackTrace();
            return false;
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.finger_sensor_gesture_title;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return MotionAndGestureSettings.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 8257;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_advanced_features";
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getActivity().getApplicationContext();
        addPreferencesFromResource(R.xml.sec_finger_sensor_gesture);
        setAutoRemoveInsetCategory(false);
        this.mPreviewPreference =
                (LayoutPreference) findPreference("finger_sensor_gesture_preview");
        SecSwitchPreference secSwitchPreference =
                (SecSwitchPreference) findPreference("open_notification_panel");
        this.mNotificationPanel = secSwitchPreference;
        secSwitchPreference.setOnPreferenceChangeListener(this);
        SecSwitchPreference secSwitchPreference2 =
                (SecSwitchPreference) findPreference("open_samsung_pay");
        this.mSamsungPay = secSwitchPreference2;
        secSwitchPreference2.setOnPreferenceChangeListener(this);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        getContentResolver().unregisterContentObserver(this.mFingerSensorGestureObserver);
        getContentResolver().unregisterContentObserver(this.mFingerSensorGestureSpayObserver);
        super.onPause();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        if (preference.equals(this.mSamsungPay)) {
            Settings.System.putInt(
                    getContentResolver(), "fingerprint_gesture_spay", booleanValue ? 1 : 0);
            this.mSamsungPay.setChecked(booleanValue);
            LoggingHelper.insertEventLogging(8257, 8259, booleanValue);
            return false;
        }
        if (!preference.equals(this.mNotificationPanel)) {
            return false;
        }
        Settings.System.putInt(
                getContentResolver(), "fingerprint_gesture_quick", booleanValue ? 1 : 0);
        this.mNotificationPanel.setChecked(booleanValue);
        LoggingHelper.insertEventLogging(8257, 8258, booleanValue);
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x01c7  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0169  */
    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onResume() {
        /*
            Method dump skipped, instructions count: 498
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.usefulfeature.motionandgestures.fingersensorgesture.FingerSensorGestureSettings.onResume():void");
    }
}

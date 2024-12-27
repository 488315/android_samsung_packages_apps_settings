package com.samsung.android.settings.inputmethod;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecDropDownPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.core.SecCustomPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class PrimaryMouseButtonPreferenceController extends SecCustomPreferenceController
        implements Preference.OnPreferenceChangeListener, LifecycleObserver {
    private static final String KEY_PRIMARY_MOUSE_BUTTON = "key_primary_mouse_button";
    private static final String TAG = "PrimaryMouseButtonPreferenceController";
    private SecDropDownPreference mDropDownPref;

    public PrimaryMouseButtonPreferenceController(Context context, String str) {
        super(context, str);
    }

    private String setSummary(int i) {
        return i == 0
                ? this.mContext.getString(R.string.primary_mouse_button_left)
                : this.mContext.getString(R.string.primary_mouse_button_right);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecDropDownPreference secDropDownPreference =
                (SecDropDownPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mDropDownPref = secDropDownPreference;
        if (secDropDownPreference != null) {
            int i =
                    Settings.System.getInt(
                            this.mContext.getContentResolver(), "primary_mouse_button_option", 0);
            this.mDropDownPref.setEntries(
                    new CharSequence[] {
                        this.mContext.getString(R.string.primary_mouse_button_left),
                        this.mContext.getString(R.string.primary_mouse_button_right)
                    });
            SecDropDownPreference secDropDownPreference2 = this.mDropDownPref;
            secDropDownPreference2.mEntryValues =
                    new CharSequence[] {DATA.DM_FIELD_INDEX.PCSCF_DOMAIN, "1"};
            secDropDownPreference2.setValueIndex(i);
            this.mDropDownPref.setOnPreferenceChangeListener(this);
            SecDropDownPreference secDropDownPreference3 = this.mDropDownPref;
            secDropDownPreference3.getClass();
            SecPreferenceUtils.applySummaryColor(secDropDownPreference3, true);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return SemPersonaManager.isKnoxId(UserHandle.myUserId()) ? 3 : 0;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController
    public List<String> getBackupKeys() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("/Settings/General/PrimaryMouseKey");
        return arrayList;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_PRIMARY_MOUSE_BUTTON;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return setSummary(
                Settings.System.getInt(
                        this.mContext.getContentResolver(), "primary_mouse_button_option", 0));
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        try {
            int parseInt = Integer.parseInt((String) obj);
            if (!getPreferenceKey().equals(preference.getKey())) {
                return false;
            }
            Settings.System.putInt(
                    this.mContext.getContentResolver(), "primary_mouse_button_option", parseInt);
            SALogging.insertSALog("770100", String.valueOf(77010), (String) obj);
            SecDropDownPreference secDropDownPreference = this.mDropDownPref;
            if (secDropDownPreference == null) {
                return true;
            }
            secDropDownPreference.setSummary(setSummary(parseInt));
            return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}

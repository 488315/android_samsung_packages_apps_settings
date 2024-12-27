package com.samsung.android.settings.theftprotection;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.theftprotection.location.LocationData;
import com.samsung.android.settings.theftprotection.location.LocationStorage;
import com.samsung.android.settings.theftprotection.utils.NecessaryElementChecker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MandatoryBiometricLocationController extends BasePreferenceController {
    private static final int MAX_NAME_LENGTH = 240;
    private static final String TAG = "MandatoryBiometricLocationController";

    public MandatoryBiometricLocationController(Context context, String str) {
        super(context, str);
    }

    private String abbreviate(String str) {
        if (str.length() <= 240) {
            return str;
        }
        return str.substring(0, IKnoxCustomManager.Stub.TRANSACTION_setFavoriteApp) + "...";
    }

    private int safePlaceErrorTurnOffConditionSize() {
        return NecessaryElementChecker.Sequence.ON_AIRPLANE_MODE.check(this.mContext) ? 1 : 0;
    }

    private int safePlaceErrorTurnOnConditionSize() {
        Iterator it =
                List.of(
                                NecessaryElementChecker.Sequence.OFF_LOCATION,
                                NecessaryElementChecker.Sequence.OFF_LOCATION_ACCURACY)
                        .iterator();
        int i = 0;
        while (it.hasNext()) {
            if (((NecessaryElementChecker.Sequence) it.next()).check(this.mContext)) {
                i++;
            }
        }
        return i;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        int safePlaceErrorTurnOffConditionSize = safePlaceErrorTurnOffConditionSize();
        int safePlaceErrorTurnOnConditionSize = safePlaceErrorTurnOnConditionSize();
        if (safePlaceErrorTurnOnConditionSize > 0 || safePlaceErrorTurnOffConditionSize > 0) {
            return (safePlaceErrorTurnOnConditionSize <= 0
                            || safePlaceErrorTurnOffConditionSize <= 0)
                    ? safePlaceErrorTurnOnConditionSize > 0
                            ? safePlaceErrorTurnOnConditionSize == 1
                                    ? this.mContext.getString(
                                            R.string
                                                    .mandatory_biometric_safe_place_error_turn_on_one_text)
                                    : this.mContext.getString(
                                            R.string
                                                    .mandatory_biometric_safe_place_error_turn_on_many_text,
                                            Integer.valueOf(safePlaceErrorTurnOnConditionSize))
                            : this.mContext.getString(
                                    R.string.mandatory_biometric_safe_place_error_turn_off_one_text)
                    : safePlaceErrorTurnOnConditionSize == 1
                            ? this.mContext.getString(
                                    R.string
                                            .mandatory_biometric_safe_place_error_turn_on_and_turn_off_text)
                            : this.mContext.getString(
                                    R.string
                                            .mandatory_biometric_safe_place_error_turn_on_many_and_turn_off_one_text,
                                    Integer.valueOf(safePlaceErrorTurnOffConditionSize));
        }
        ArrayList arrayList =
                (ArrayList) LocationStorage.InstanceHolder.INSTANCE.loadLocationData();
        if (arrayList.isEmpty()) {
            return this.mContext.getString(R.string.mandatory_biometric_trusted_places_no_item);
        }
        int size = arrayList.size();
        String str = ((LocationData) arrayList.get(0)).mName;
        if (size == 1) {
            return abbreviate(str);
        }
        int i = size - 1;
        return this.mContext
                .getResources()
                .getQuantityString(
                        R.plurals.mandatory_biometric_trusted_places_summary,
                        i,
                        abbreviate(str),
                        Integer.valueOf(i));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        preference.setEnabled(TheftProtectionUtils.getMandatoryBiometricStatus(this.mContext) == 1);
        if (safePlaceErrorTurnOnConditionSize() > 0 || safePlaceErrorTurnOffConditionSize() > 0) {
            preference.seslSetSummaryColor(
                    this.mContext.getColor(R.color.security_dashboard_menu_icon_warning_color));
        } else {
            preference.seslSetSummaryColor(
                    this.mContext.getColor(R.color.security_dashboard_menu_subtext_default_color));
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}

package com.android.settings.privacy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.DeviceConfig;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.utils.SensorPrivacyManagerHelper;
import com.android.settingslib.RestrictedLockUtilsInternal;

import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecRestrictedSwitchPreference;

import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SensorToggleController extends TogglePreferenceController
        implements LifecycleObserver {
    private final Executor mCallbackExecutor;
    private boolean mFlag;
    private boolean mIgnoreDeviceConfig;
    private PreferenceScreen mScreen;
    protected final SensorPrivacyManagerHelper mSensorPrivacyManagerHelper;

    public SensorToggleController(Context context, String str) {
        this(context, str, SensorPrivacyManagerHelper.getInstance(context), false);
    }

    private void showSensorWarningDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        if (getSensor() == 1) {
            builder.setTitle(R.string.turn_off_microphone_dialog_title);
            builder.setMessage(R.string.turn_off_microphone_dialog_message);
        } else if (getSensor() == 2) {
            builder.setTitle(R.string.turn_off_camera_dialog_title);
            builder.setMessage(R.string.turn_off_camera_dialog_message);
        }
        builder.setPositiveButton(
                R.string.block_button,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.privacy.SensorToggleController.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        SensorToggleController.this.mFlag = true;
                    }
                });
        builder.setNegativeButton(R.string.cancel, new AnonymousClass2());
        builder.P.mOnDismissListener =
                new DialogInterface
                        .OnDismissListener() { // from class:
                                               // com.android.settings.privacy.SensorToggleController.3
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        if (SensorToggleController.this.mFlag) {
                            SensorToggleController.this.setChecked(false);
                        }
                    }
                };
        builder.show();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mScreen = preferenceScreen;
        SecRestrictedSwitchPreference secRestrictedSwitchPreference =
                (SecRestrictedSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
        if (secRestrictedSwitchPreference != null) {
            secRestrictedSwitchPreference.setDisabledByAdmin(
                    RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                            this.mContext, this.mContext.getUserId(), getRestriction()));
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (this.mSensorPrivacyManagerHelper.sensorPrivacyManager.supportsSensorToggle(
                                getSensor())
                        && (this.mIgnoreDeviceConfig
                                || DeviceConfig.getBoolean("privacy", getDeviceConfigKey(), true)))
                ? 0
                : 3;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    public abstract String getDeviceConfigKey();

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    public String getRestriction() {
        return null;
    }

    public abstract int getSensor();

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_privacy;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return !this.mSensorPrivacyManagerHelper.isSensorBlocked(-1, getSensor());
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    public void onSensorPrivacyChanged(int i, int i2, boolean z) {
        updateState(this.mScreen.findPreference(this.mPreferenceKey));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        this.mSensorPrivacyManagerHelper.addSensorBlockedListener(
                -1, getSensor(), this.mCallbackExecutor, this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        SensorPrivacyManagerHelper sensorPrivacyManagerHelper = this.mSensorPrivacyManagerHelper;
        sensorPrivacyManagerHelper.getClass();
        ArrayList arrayList = new ArrayList();
        synchronized (sensorPrivacyManagerHelper.lock) {
            try {
                for (Map.Entry entry :
                        ((LinkedHashMap) sensorPrivacyManagerHelper.callbacks).entrySet()) {
                    ((Set) entry.getValue())
                            .removeIf(
                                    new Predicate() { // from class:
                                                      // com.android.settings.utils.SensorPrivacyManagerHelper$removeSensorBlockedListener$1$1$1
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            Pair it = (Pair) obj;
                                            Intrinsics.checkNotNullParameter(it, "it");
                                            return Intrinsics.areEqual(
                                                    it.getFirst(), SensorToggleController.this);
                                        }
                                    });
                    if (((Set) entry.getValue()).isEmpty()) {
                        arrayList.add(entry.getKey());
                    }
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    sensorPrivacyManagerHelper.callbacks.remove((Pair) it.next());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        if (!z && !this.mFlag) {
            showSensorWarningDialog();
            return false;
        }
        this.mFlag = false;
        LoggingHelper.insertEventLogging(
                getMetricsCategory(), getSensor() == 1 ? 59201 : 59200, z ? 1L : 0L);
        this.mSensorPrivacyManagerHelper.sensorPrivacyManager.setSensorPrivacy(2, getSensor(), !z);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public SensorToggleController(
            Context context,
            String str,
            SensorPrivacyManagerHelper sensorPrivacyManagerHelper,
            boolean z) {
        super(context, str);
        this.mFlag = false;
        this.mIgnoreDeviceConfig = z;
        this.mSensorPrivacyManagerHelper = sensorPrivacyManagerHelper;
        this.mCallbackExecutor = context.getMainExecutor();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.privacy.SensorToggleController$2, reason: invalid class name */
    public final class AnonymousClass2 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {}
    }
}

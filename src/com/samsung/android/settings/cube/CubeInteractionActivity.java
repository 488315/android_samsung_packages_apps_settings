package com.samsung.android.settings.cube;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.HideNonSystemOverlayMixin;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CubeInteractionActivity extends AppCompatActivity {
    public final Object mLock = new Object();
    public final List mPendingControlData = new ArrayList();

    public static ControlValue getControlValue(Bundle bundle) {
        if (bundle == null) {
            Log.e("CubeInteractionActivity", "performControl() extra is null");
            return null;
        }
        if (TextUtils.isEmpty(bundle.getString("controllerName"))) {
            Log.e("CubeInteractionActivity", "controllerName is empty");
            return null;
        }
        String string = bundle.getString("controlData");
        if (TextUtils.isEmpty(string)) {
            Log.e("CubeInteractionActivity", "controlData is empty");
            return null;
        }
        String string2 = bundle.getString("controlId");
        if (TextUtils.isEmpty(string2)) {
            Log.e("CubeInteractionActivity", "controlId is empty");
            return null;
        }
        ControlValue.ControlValueWrapper controlValueWrapper =
                (ControlValue.ControlValueWrapper)
                        new Gson().fromJson(string, ControlValue.ControlValueWrapper.class);
        if (controlValueWrapper != null) {
            return controlValueWrapper.buildControlValue(string2);
        }
        Log.e("CubeInteractionActivity", "controlValueWrapper is null");
        return null;
    }

    public final void notifyInteractionFinished() {
        Bundle bundle;
        Log.i(
                "CubeInteractionActivity",
                "notifyInteractionFinished() " + ((ArrayList) this.mPendingControlData).size());
        synchronized (this.mLock) {
            try {
                bundle =
                        ((ArrayList) this.mPendingControlData).size() > 0
                                ? (Bundle) ((ArrayList) this.mPendingControlData).remove(0)
                                : null;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (bundle != null) {
            performControl(bundle);
        } else {
            finish();
        }
        ((ArrayList) this.mPendingControlData).size();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getApplicationContext().getTheme().rebase();
        getLifecycle().addObserver(new HideNonSystemOverlayMixin(this));
        CubeCallbackManager.LazyHolder.INSTANCE.mCubeInteractionActivity = this;
        performControl(getIntent().getExtras());
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        synchronized (this.mLock) {
            try {
                setIntent(intent);
                if (intent.getExtras() != null) {
                    ((ArrayList) this.mPendingControlData).add(intent.getExtras());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final synchronized void performControl(Bundle bundle) {
        BasePreferenceController basePreferenceController;
        ControlValue controlValue = getControlValue(bundle);
        if (controlValue == null) {
            Log.e("CubeInteractionActivity", "controlValue is null");
            notifyInteractionFinished();
            return;
        }
        String string = bundle.getString("controllerName");
        String str = controlValue.mKey;
        try {
            basePreferenceController = BasePreferenceController.createInstance(this, string);
        } catch (IllegalStateException unused) {
            basePreferenceController = null;
        }
        if (basePreferenceController == null) {
            basePreferenceController = BasePreferenceController.createInstance(this, string, str);
        }
        basePreferenceController.setControlId(controlValue);
        boolean z =
                basePreferenceController.needUserInteraction(controlValue.getTypedValue())
                        != Controllable$ControllableType.NO_INTERACTION;
        basePreferenceController.setValue(controlValue);
        if (!z) {
            notifyInteractionFinished();
        }
    }
}

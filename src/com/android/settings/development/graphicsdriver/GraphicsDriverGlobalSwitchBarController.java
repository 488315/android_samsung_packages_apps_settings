package com.android.settings.development.graphicsdriver;

import android.content.ContentResolver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import com.android.settings.SettingsActivity;
import com.android.settings.widget.MainSwitchBarController;
import com.android.settings.widget.SwitchWidgetController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.development.DevelopmentSettingsEnabler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class GraphicsDriverGlobalSwitchBarController
        implements SwitchWidgetController.OnSwitchChangeListener,
                GraphicsDriverContentObserver.OnGraphicsDriverContentChangedListener,
                LifecycleObserver,
                OnStart,
                OnStop {
    public final ContentResolver mContentResolver;
    GraphicsDriverContentObserver mGraphicsDriverContentObserver;
    public final SwitchWidgetController mSwitchWidgetController;

    public GraphicsDriverGlobalSwitchBarController(
            SettingsActivity settingsActivity, MainSwitchBarController mainSwitchBarController) {
        ContentResolver contentResolver = settingsActivity.getContentResolver();
        this.mContentResolver = contentResolver;
        this.mGraphicsDriverContentObserver =
                new GraphicsDriverContentObserver(new Handler(Looper.getMainLooper()), this);
        this.mSwitchWidgetController = mainSwitchBarController;
        mainSwitchBarController.setEnabled(
                DevelopmentSettingsEnabler.isDevelopmentSettingsEnabled(settingsActivity));
        mainSwitchBarController.setChecked(
                Settings.Global.getInt(contentResolver, "updatable_driver_all_apps", 0) != 3);
        mainSwitchBarController.mListener = this;
    }

    @Override // com.android.settings.development.graphicsdriver.GraphicsDriverContentObserver.OnGraphicsDriverContentChangedListener
    public final void onGraphicsDriverContentChanged() {
        this.mSwitchWidgetController.setChecked(
                Settings.Global.getInt(this.mContentResolver, "updatable_driver_all_apps", 0) != 3);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public final void onStart() {
        this.mSwitchWidgetController.startListening();
        GraphicsDriverContentObserver graphicsDriverContentObserver =
                this.mGraphicsDriverContentObserver;
        ContentResolver contentResolver = this.mContentResolver;
        graphicsDriverContentObserver.getClass();
        contentResolver.registerContentObserver(
                Settings.Global.getUriFor("updatable_driver_all_apps"),
                false,
                graphicsDriverContentObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public final void onStop() {
        this.mSwitchWidgetController.stopListening();
        GraphicsDriverContentObserver graphicsDriverContentObserver =
                this.mGraphicsDriverContentObserver;
        ContentResolver contentResolver = this.mContentResolver;
        graphicsDriverContentObserver.getClass();
        contentResolver.unregisterContentObserver(graphicsDriverContentObserver);
    }

    @Override // com.android.settings.widget.SwitchWidgetController.OnSwitchChangeListener
    public final boolean onSwitchToggled(boolean z) {
        int i = Settings.Global.getInt(this.mContentResolver, "updatable_driver_all_apps", 0);
        if (z && (i == 0 || i == 1 || i == 2)) {
            return true;
        }
        if (!z && i == 3) {
            return true;
        }
        Settings.Global.putInt(this.mContentResolver, "updatable_driver_all_apps", z ? 0 : 3);
        return true;
    }
}

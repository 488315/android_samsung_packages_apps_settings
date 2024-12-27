package com.android.settingslib.core.lifecycle;

import android.os.Bundle;
import android.os.SystemClock;
import android.os.Trace;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.OnLifecycleEvent;

import com.android.settingslib.core.instrumentation.VisibilityLoggerMixin;
import com.android.settingslib.core.lifecycle.events.OnCreate;
import com.android.settingslib.core.lifecycle.events.OnCreateOptionsMenu;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnOptionsItemSelected;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnPrepareOptionsMenu;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.core.lifecycle.events.OnSaveInstanceState;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class Lifecycle extends LifecycleRegistry {
    public final List mObservers;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.core.lifecycle.Lifecycle$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$androidx$lifecycle$Lifecycle$Event;

        static {
            int[] iArr = new int[Lifecycle.Event.values().length];
            $SwitchMap$androidx$lifecycle$Lifecycle$Event = iArr;
            try {
                iArr[Lifecycle.Event.ON_CREATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_START.ordinal()] =
                        2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_RESUME.ordinal()] =
                        3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_PAUSE.ordinal()] =
                        4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_STOP.ordinal()] =
                        5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[
                                Lifecycle.Event.ON_DESTROY.ordinal()] =
                        6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_ANY.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LifecycleProxy implements androidx.lifecycle.LifecycleObserver {
        public LifecycleProxy() {}

        @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
        public void onLifecycleEvent(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            int i = AnonymousClass1.$SwitchMap$androidx$lifecycle$Lifecycle$Event[event.ordinal()];
            int i2 = 0;
            Lifecycle lifecycle = Lifecycle.this;
            switch (i) {
                case 2:
                    int size = ((ArrayList) lifecycle.mObservers).size();
                    while (i2 < size) {
                        LifecycleObserver lifecycleObserver =
                                (LifecycleObserver) ((ArrayList) lifecycle.mObservers).get(i2);
                        if (lifecycleObserver instanceof OnStart) {
                            Trace.traceBegin(
                                    1L,
                                    lifecycleObserver
                                            .getClass()
                                            .getSimpleName()
                                            .concat("#OnStart"));
                            ((OnStart) lifecycleObserver).onStart();
                            Trace.traceEnd(1L);
                        }
                        i2++;
                    }
                    break;
                case 3:
                    int size2 = ((ArrayList) lifecycle.mObservers).size();
                    while (i2 < size2) {
                        LifecycleObserver lifecycleObserver2 =
                                (LifecycleObserver) ((ArrayList) lifecycle.mObservers).get(i2);
                        if (lifecycleObserver2 instanceof OnResume) {
                            Trace.traceBegin(
                                    1L,
                                    lifecycleObserver2
                                            .getClass()
                                            .getSimpleName()
                                            .concat("#OnResume"));
                            ((OnResume) lifecycleObserver2).onResume();
                            Trace.traceEnd(1L);
                        }
                        i2++;
                    }
                    break;
                case 4:
                    int size3 = ((ArrayList) lifecycle.mObservers).size();
                    while (i2 < size3) {
                        LifecycleObserver lifecycleObserver3 =
                                (LifecycleObserver) ((ArrayList) lifecycle.mObservers).get(i2);
                        if (lifecycleObserver3 instanceof OnPause) {
                            ((OnPause) lifecycleObserver3).onPause();
                        }
                        i2++;
                    }
                    break;
                case 5:
                    int size4 = ((ArrayList) lifecycle.mObservers).size();
                    while (i2 < size4) {
                        LifecycleObserver lifecycleObserver4 =
                                (LifecycleObserver) ((ArrayList) lifecycle.mObservers).get(i2);
                        if (lifecycleObserver4 instanceof OnStop) {
                            ((OnStop) lifecycleObserver4).onStop();
                        }
                        i2++;
                    }
                    break;
                case 6:
                    int size5 = ((ArrayList) lifecycle.mObservers).size();
                    while (i2 < size5) {
                        LifecycleObserver lifecycleObserver5 =
                                (LifecycleObserver) ((ArrayList) lifecycle.mObservers).get(i2);
                        if (lifecycleObserver5 instanceof OnDestroy) {
                            ((OnDestroy) lifecycleObserver5).onDestroy();
                        }
                        i2++;
                    }
                    break;
                case 7:
                    Log.wtf("LifecycleObserver", "Should not receive an 'ANY' event!");
                    break;
            }
        }
    }

    public Lifecycle(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
        this.mObservers = new ArrayList();
        addObserver(new LifecycleProxy());
    }

    @Override // androidx.lifecycle.LifecycleRegistry, androidx.lifecycle.Lifecycle
    public final void addObserver(androidx.lifecycle.LifecycleObserver lifecycleObserver) {
        ThreadUtils.ensureMainThread();
        super.addObserver(lifecycleObserver);
        if (lifecycleObserver instanceof LifecycleObserver) {
            ((ArrayList) this.mObservers).add((LifecycleObserver) lifecycleObserver);
        }
    }

    public final void onAttach() {
        int size = ((ArrayList) this.mObservers).size();
        for (int i = 0; i < size; i++) {
            LifecycleObserver lifecycleObserver =
                    (LifecycleObserver) ((ArrayList) this.mObservers).get(i);
            if (lifecycleObserver instanceof VisibilityLoggerMixin) {
                Trace.traceBegin(
                        1L, lifecycleObserver.getClass().getSimpleName().concat("#OnAttach"));
                VisibilityLoggerMixin visibilityLoggerMixin =
                        (VisibilityLoggerMixin) lifecycleObserver;
                visibilityLoggerMixin.getClass();
                visibilityLoggerMixin.mCreationTimestamp = SystemClock.elapsedRealtime();
                Trace.traceEnd(1L);
            }
        }
    }

    public final void onCreate(Bundle bundle) {
        int size = ((ArrayList) this.mObservers).size();
        for (int i = 0; i < size; i++) {
            LifecycleObserver lifecycleObserver =
                    (LifecycleObserver) ((ArrayList) this.mObservers).get(i);
            if (lifecycleObserver instanceof OnCreate) {
                ((OnCreate) lifecycleObserver).onCreate(bundle);
            }
        }
    }

    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        int size = ((ArrayList) this.mObservers).size();
        for (int i = 0; i < size; i++) {
            LifecycleObserver lifecycleObserver =
                    (LifecycleObserver) ((ArrayList) this.mObservers).get(i);
            if (lifecycleObserver instanceof OnCreateOptionsMenu) {
                ((OnCreateOptionsMenu) lifecycleObserver).onCreateOptionsMenu(menu, menuInflater);
            }
        }
    }

    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        int size = ((ArrayList) this.mObservers).size();
        for (int i = 0; i < size; i++) {
            LifecycleObserver lifecycleObserver =
                    (LifecycleObserver) ((ArrayList) this.mObservers).get(i);
            if ((lifecycleObserver instanceof OnOptionsItemSelected)
                    && ((OnOptionsItemSelected) lifecycleObserver)
                            .onOptionsItemSelected(menuItem)) {
                return true;
            }
        }
        return false;
    }

    public final void onPrepareOptionsMenu(Menu menu) {
        int size = ((ArrayList) this.mObservers).size();
        for (int i = 0; i < size; i++) {
            LifecycleObserver lifecycleObserver =
                    (LifecycleObserver) ((ArrayList) this.mObservers).get(i);
            if (lifecycleObserver instanceof OnPrepareOptionsMenu) {
                ((OnPrepareOptionsMenu) lifecycleObserver).onPrepareOptionsMenu(menu);
            }
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        int size = ((ArrayList) this.mObservers).size();
        for (int i = 0; i < size; i++) {
            LifecycleObserver lifecycleObserver =
                    (LifecycleObserver) ((ArrayList) this.mObservers).get(i);
            if (lifecycleObserver instanceof OnSaveInstanceState) {
                ((OnSaveInstanceState) lifecycleObserver).onSaveInstanceState(bundle);
            }
        }
    }

    @Override // androidx.lifecycle.LifecycleRegistry, androidx.lifecycle.Lifecycle
    public final void removeObserver(androidx.lifecycle.LifecycleObserver lifecycleObserver) {
        ThreadUtils.ensureMainThread();
        super.removeObserver(lifecycleObserver);
        if (lifecycleObserver instanceof LifecycleObserver) {
            ((ArrayList) this.mObservers).remove(lifecycleObserver);
        }
    }
}

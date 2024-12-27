package com.android.settings;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;

import com.android.settingslib.utils.ThreadUtils;

import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class SidecarFragment extends Fragment {
    public boolean mCreated;
    public final Set mListeners = new CopyOnWriteArraySet();
    public int mState;
    public int mSubstate;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Listener {
        void onStateChange(SidecarFragment sidecarFragment);
    }

    public static SidecarFragment get(FragmentManager fragmentManager, String str, Class cls) {
        SidecarFragment sidecarFragment = (SidecarFragment) fragmentManager.findFragmentByTag(str);
        if (sidecarFragment == null) {
            try {
                sidecarFragment = (SidecarFragment) cls.newInstance();
                fragmentManager.beginTransaction().add(sidecarFragment, str).commit();
                fragmentManager.executePendingTransactions();
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException("Unable to create fragment", e);
            } catch (InstantiationException e2) {
                throw new Fragment.InstantiationException("Unable to create fragment", e2);
            }
        }
        return sidecarFragment;
    }

    public final void addListener(Listener listener) {
        ThreadUtils.ensureMainThread();
        ((CopyOnWriteArraySet) this.mListeners).add(listener);
        if (this.mCreated) {
            listener.onStateChange(this);
        }
    }

    @Override // android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
        this.mCreated = true;
        setState(0, 0);
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        this.mCreated = false;
        super.onDestroy();
    }

    public final void removeListener(Listener listener) {
        ThreadUtils.ensureMainThread();
        ((CopyOnWriteArraySet) this.mListeners).remove(listener);
    }

    public final void setState(int i, int i2) {
        ThreadUtils.ensureMainThread();
        this.mState = i;
        this.mSubstate = i2;
        Iterator it = ((CopyOnWriteArraySet) this.mListeners).iterator();
        while (it.hasNext()) {
            ((Listener) it.next()).onStateChange(this);
        }
        StringBuilder sb = new StringBuilder("SidecarFragment.setState(): Sidecar Class: ");
        sb.append(getClass().getCanonicalName());
        sb.append(", State: ");
        int i3 = this.mState;
        if (i3 == 0) {
            sb.append("State.INIT");
        } else if (i3 == 1) {
            sb.append("State.RUNNING");
        } else if (i3 == 2) {
            sb.append("State.SUCCESS");
        } else if (i3 != 3) {
            sb.append(i3);
        } else {
            sb.append("State.ERROR");
        }
        if (this.mSubstate != 0) {
            sb.append(", ");
            sb.append(this.mSubstate);
        } else {
            sb.append(", Substate.UNUSED");
        }
        Log.v("SidecarFragment", sb.toString());
    }

    @Override // android.app.Fragment
    public final String toString() {
        Locale locale = Locale.US;
        int i = this.mState;
        int i2 = this.mSubstate;
        String fragment = super.toString();
        StringBuilder m =
                RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                        "SidecarFragment[mState=", ", mSubstate=", i, i2, "]: ");
        m.append(fragment);
        return m.toString();
    }
}

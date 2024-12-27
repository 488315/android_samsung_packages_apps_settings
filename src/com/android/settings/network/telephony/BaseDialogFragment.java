package com.android.settings.network.telephony;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BaseDialogFragment extends DialogFragment {
    public static void setListener(Activity activity, Class cls, int i, Bundle bundle) {
        if (!cls.isInstance(activity)) {
            throw new IllegalArgumentException(
                    "The caller activity should implement the callback function.");
        }
        bundle.putInt("in_caller_tag", i);
    }

    public final Object getListener(Class cls) {
        String string = getArguments().getString("listener_tag");
        Object activity =
                string == null
                        ? getActivity()
                        : getActivity().getFragmentManager().findFragmentByTag(string);
        if (cls.isInstance(activity)) {
            return activity;
        }
        throw new IllegalArgumentException("The caller should implement the callback function.");
    }
}

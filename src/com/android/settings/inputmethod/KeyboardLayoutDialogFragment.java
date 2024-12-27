package com.android.settings.inputmethod;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.input.InputDeviceIdentifier;
import android.hardware.input.InputManager;
import android.hardware.input.KeyboardLayout;
import android.os.Bundle;
import android.view.InputDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.window.reflection.WindowExtensionsConstants;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Collections;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class KeyboardLayoutDialogFragment extends InstrumentedDialogFragment
        implements InputManager.InputDeviceListener, LoaderManager.LoaderCallbacks {
    public KeyboardLayoutAdapter mAdapter;
    public InputManager mIm;
    public int mInputDeviceId = -1;
    public InputDeviceIdentifier mInputDeviceIdentifier;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class KeyboardLayoutAdapter extends ArrayAdapter {
        public int mCheckedItem;
        public final LayoutInflater mInflater;

        public KeyboardLayoutAdapter(Context context) {
            super(context, 17367436);
            this.mCheckedItem = -1;
            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            String string;
            String str;
            KeyboardLayout keyboardLayout = (KeyboardLayout) getItem(i);
            if (keyboardLayout != null) {
                string = keyboardLayout.getLabel();
                str = keyboardLayout.getCollection();
            } else {
                string = getContext().getString(R.string.keyboard_layout_default_label);
                str = ApnSettings.MVNO_NONE;
            }
            boolean z = i == this.mCheckedItem;
            if (str.isEmpty()) {
                if (view == null || view.getTag() == Boolean.TRUE) {
                    view =
                            this.mInflater.inflate(
                                    android.R.layout.simple_list_item_single_choice,
                                    viewGroup,
                                    false);
                    view.setTag(Boolean.FALSE);
                }
                CheckedTextView checkedTextView =
                        (CheckedTextView) view.findViewById(android.R.id.text1);
                checkedTextView.setText(string);
                checkedTextView.setChecked(z);
                return view;
            }
            if (view == null || view.getTag() != Boolean.TRUE) {
                view = this.mInflater.inflate(17367436, viewGroup, false);
                view.setTag(Boolean.TRUE);
            }
            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            TextView textView2 = (TextView) view.findViewById(android.R.id.text2);
            RadioButton radioButton =
                    (RadioButton) view.findViewById(android.R.id.sha1_fingerprint);
            textView.setText(string);
            textView2.setText(str);
            radioButton.setChecked(z);
            return view;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class KeyboardLayoutLoader extends AsyncTaskLoader {
        public final InputDeviceIdentifier mInputDeviceIdentifier;

        public KeyboardLayoutLoader(Context context, InputDeviceIdentifier inputDeviceIdentifier) {
            super(context);
            this.mInputDeviceIdentifier = inputDeviceIdentifier;
        }

        @Override // androidx.loader.content.AsyncTaskLoader
        public final Object loadInBackground() {
            Keyboards keyboards = new Keyboards();
            InputManager inputManager = (InputManager) this.mContext.getSystemService("input");
            InputDeviceIdentifier inputDeviceIdentifier = this.mInputDeviceIdentifier;
            if (inputDeviceIdentifier == null
                    || NewKeyboardSettingsUtils.getInputDevice(inputManager, inputDeviceIdentifier)
                            == null) {
                keyboards.keyboardLayouts.add(null);
                keyboards.current = 0;
            } else {
                for (String str :
                        inputManager.getEnabledKeyboardLayoutsForInputDevice(
                                this.mInputDeviceIdentifier)) {
                    KeyboardLayout keyboardLayout = inputManager.getKeyboardLayout(str);
                    if (keyboardLayout != null) {
                        keyboards.keyboardLayouts.add(keyboardLayout);
                    }
                }
                Collections.sort(keyboards.keyboardLayouts);
                String currentKeyboardLayoutForInputDevice =
                        inputManager.getCurrentKeyboardLayoutForInputDevice(
                                this.mInputDeviceIdentifier);
                if (currentKeyboardLayoutForInputDevice != null) {
                    int size = keyboards.keyboardLayouts.size();
                    int i = 0;
                    while (true) {
                        if (i >= size) {
                            break;
                        }
                        if (((KeyboardLayout) keyboards.keyboardLayouts.get(i))
                                .getDescriptor()
                                .equals(currentKeyboardLayoutForInputDevice)) {
                            keyboards.current = i;
                            break;
                        }
                        i++;
                    }
                }
                if (keyboards.keyboardLayouts.isEmpty()) {
                    keyboards.keyboardLayouts.add(null);
                    keyboards.current = 0;
                }
            }
            return keyboards;
        }

        @Override // androidx.loader.content.Loader
        public final void onStartLoading() {
            onForceLoad();
        }

        @Override // androidx.loader.content.Loader
        public final void onStopLoading() {
            onCancelLoad();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Keyboards {
        public final ArrayList keyboardLayouts = new ArrayList();
        public int current = -1;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnSetupKeyboardLayoutsListener {
        void onSetupKeyboardLayouts(InputDeviceIdentifier inputDeviceIdentifier);
    }

    public KeyboardLayoutDialogFragment() {}

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 541;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        show(getActivity().getSupportFragmentManager(), WindowExtensionsConstants.LAYOUT_PACKAGE);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onAttach(Activity activity) {
        super.onAttach(activity);
        Context baseContext = activity.getBaseContext();
        this.mIm = (InputManager) baseContext.getSystemService("input");
        this.mAdapter = new KeyboardLayoutAdapter(baseContext);
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        dismissInternal(false, false);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.mInputDeviceIdentifier = bundle.getParcelable("inputDeviceIdentifier");
        }
        getLoaderManager().initLoader(0, null, this);
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        FragmentActivity activity = getActivity();
        LayoutInflater from = LayoutInflater.from(activity);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.keyboard_layout_dialog_title);
        final int i = 1;
        builder.setPositiveButton(
                R.string.keyboard_layout_dialog_setup_button,
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.android.settings.inputmethod.KeyboardLayoutDialogFragment.1
                    public final /* synthetic */ KeyboardLayoutDialogFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        switch (i) {
                            case 0:
                                KeyboardLayoutDialogFragment keyboardLayoutDialogFragment =
                                        this.this$0;
                                if (i2 < 0) {
                                    keyboardLayoutDialogFragment.getClass();
                                    break;
                                } else if (i2 < keyboardLayoutDialogFragment.mAdapter.getCount()) {
                                    KeyboardLayout keyboardLayout =
                                            (KeyboardLayout)
                                                    keyboardLayoutDialogFragment.mAdapter.getItem(
                                                            i2);
                                    if (keyboardLayout != null) {
                                        keyboardLayoutDialogFragment.mIm
                                                .setCurrentKeyboardLayoutForInputDevice(
                                                        keyboardLayoutDialogFragment
                                                                .mInputDeviceIdentifier,
                                                        keyboardLayout.getDescriptor());
                                    }
                                    keyboardLayoutDialogFragment.dismissInternal(false, false);
                                    break;
                                }
                                break;
                            default:
                                KeyboardLayoutDialogFragment keyboardLayoutDialogFragment2 =
                                        this.this$0;
                                ((OnSetupKeyboardLayoutsListener)
                                                keyboardLayoutDialogFragment2.getTargetFragment())
                                        .onSetupKeyboardLayouts(
                                                keyboardLayoutDialogFragment2
                                                        .mInputDeviceIdentifier);
                                break;
                        }
                    }
                });
        final int i2 = 0;
        builder.setSingleChoiceItems(
                this.mAdapter,
                -1,
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.android.settings.inputmethod.KeyboardLayoutDialogFragment.1
                    public final /* synthetic */ KeyboardLayoutDialogFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i22) {
                        switch (i2) {
                            case 0:
                                KeyboardLayoutDialogFragment keyboardLayoutDialogFragment =
                                        this.this$0;
                                if (i22 < 0) {
                                    keyboardLayoutDialogFragment.getClass();
                                    break;
                                } else if (i22 < keyboardLayoutDialogFragment.mAdapter.getCount()) {
                                    KeyboardLayout keyboardLayout =
                                            (KeyboardLayout)
                                                    keyboardLayoutDialogFragment.mAdapter.getItem(
                                                            i22);
                                    if (keyboardLayout != null) {
                                        keyboardLayoutDialogFragment.mIm
                                                .setCurrentKeyboardLayoutForInputDevice(
                                                        keyboardLayoutDialogFragment
                                                                .mInputDeviceIdentifier,
                                                        keyboardLayout.getDescriptor());
                                    }
                                    keyboardLayoutDialogFragment.dismissInternal(false, false);
                                    break;
                                }
                                break;
                            default:
                                KeyboardLayoutDialogFragment keyboardLayoutDialogFragment2 =
                                        this.this$0;
                                ((OnSetupKeyboardLayoutsListener)
                                                keyboardLayoutDialogFragment2.getTargetFragment())
                                        .onSetupKeyboardLayouts(
                                                keyboardLayoutDialogFragment2
                                                        .mInputDeviceIdentifier);
                                break;
                        }
                    }
                });
        builder.setView(
                from.inflate(R.layout.keyboard_layout_dialog_switch_hint, (ViewGroup) null));
        updateSwitchHintVisibility();
        return builder.create();
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final Loader onCreateLoader(int i, Bundle bundle) {
        return new KeyboardLayoutLoader(
                getActivity().getBaseContext(), this.mInputDeviceIdentifier);
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceChanged(int i) {
        int i2 = this.mInputDeviceId;
        if (i2 < 0 || i != i2) {
            return;
        }
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceRemoved(int i) {
        int i2 = this.mInputDeviceId;
        if (i2 < 0 || i != i2) {
            return;
        }
        dismissInternal(false, false);
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final void onLoadFinished(Loader loader, Object obj) {
        Keyboards keyboards = (Keyboards) obj;
        this.mAdapter.clear();
        this.mAdapter.addAll(keyboards.keyboardLayouts);
        KeyboardLayoutAdapter keyboardLayoutAdapter = this.mAdapter;
        int i = keyboards.current;
        keyboardLayoutAdapter.mCheckedItem = i;
        AlertDialog alertDialog = (AlertDialog) this.mDialog;
        if (alertDialog != null) {
            alertDialog.mAlert.mListView.setItemChecked(i, true);
        }
        updateSwitchHintVisibility();
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final void onLoaderReset(Loader loader) {
        this.mAdapter.clear();
        updateSwitchHintVisibility();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        this.mIm.unregisterInputDeviceListener(this);
        this.mInputDeviceId = -1;
        super.onPause();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mIm.registerInputDeviceListener(this, null);
        InputDevice inputDeviceByDescriptor =
                this.mIm.getInputDeviceByDescriptor(this.mInputDeviceIdentifier.getDescriptor());
        if (inputDeviceByDescriptor == null) {
            dismissInternal(false, false);
        } else {
            this.mInputDeviceId = inputDeviceByDescriptor.getId();
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable("inputDeviceIdentifier", this.mInputDeviceIdentifier);
    }

    public final void updateSwitchHintVisibility() {
        AlertDialog alertDialog = (AlertDialog) this.mDialog;
        if (alertDialog != null) {
            alertDialog
                    .findViewById(R.id.customPanel)
                    .setVisibility(this.mAdapter.getCount() > 1 ? 0 : 8);
        }
    }

    public KeyboardLayoutDialogFragment(InputDeviceIdentifier inputDeviceIdentifier) {
        this.mInputDeviceIdentifier = inputDeviceIdentifier;
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceAdded(int i) {}
}

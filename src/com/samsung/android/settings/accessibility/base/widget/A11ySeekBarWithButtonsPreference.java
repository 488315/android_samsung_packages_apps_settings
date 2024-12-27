package com.samsung.android.settings.accessibility.base.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.SeslSeekBar;
import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.settings.accessibility.AccessibilityConstant;

import java.lang.ref.WeakReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class A11ySeekBarWithButtonsPreference extends A11ySeekBarPreference
        implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener {
    public boolean isLongKeyProcessing;
    public final LongPressHandler longPressHandler;
    public SeslSeekBar seekBar;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LongPressHandler extends Handler {
        public final WeakReference weakReference;

        public LongPressHandler(A11ySeekBarWithButtonsPreference a11ySeekBarWithButtonsPreference) {
            this.weakReference = new WeakReference(a11ySeekBarWithButtonsPreference);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            A11ySeekBarWithButtonsPreference a11ySeekBarWithButtonsPreference =
                    (A11ySeekBarWithButtonsPreference) this.weakReference.get();
            int i = message.what;
            if (i == 1) {
                a11ySeekBarWithButtonsPreference.onDeleteButtonClicked();
            } else if (i == 2) {
                a11ySeekBarWithButtonsPreference.onAddButtonClicked();
            }
        }
    }

    public A11ySeekBarWithButtonsPreference(Context context, AttributeSet attributeSet) {
        this(
                context,
                attributeSet,
                TypedArrayUtils.getAttr(
                        context,
                        R.attr.a11ySeekBarWithButtonsPreferenceStyle,
                        R.attr.seekBarPreferenceStyle));
    }

    public final void onAddButtonClicked() {
        int i = this.mSeekBarValue + 1;
        if (i > this.mMax || !callChangeListener(Integer.valueOf(i))) {
            return;
        }
        SeslSeekBar seslSeekBar = this.seekBar;
        if (seslSeekBar != null) {
            seslSeekBar.performHapticFeedback(AccessibilityConstant.HAPTIC_CONSTANT_CURSOR_MOVE);
        }
        setValueInternal(i, true);
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.A11ySeekBarPreference,
              // androidx.preference.SeekBarPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        Log.d("A11ySeekBarWithButtonsPreference", "onBindViewHolder:" + isEnabled());
        super.onBindViewHolder(preferenceViewHolder);
        View view = preferenceViewHolder.itemView;
        ImageView imageView = (ImageView) view.findViewById(R.id.add_button);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.delete_button);
        this.seekBar = (SeslSeekBar) view.findViewById(R.id.seekbar);
        imageView.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView.setOnLongClickListener(this);
        imageView2.setOnLongClickListener(this);
        imageView.setOnTouchListener(this);
        imageView2.setOnTouchListener(this);
        this.seekBar.setOnTouchListener(this);
        imageView.setEnabled(isEnabled());
        imageView2.setEnabled(isEnabled());
        imageView.setAlpha(isEnabled() ? 1.0f : 0.4f);
        imageView2.setAlpha(isEnabled() ? 1.0f : 0.4f);
        CharSequence title = getTitle();
        if (title != null) {
            imageView.setContentDescription(
                    ((Object) title)
                            + " "
                            + getContext()
                                    .getResources()
                                    .getString(R.string.increase_button_content_description));
            imageView2.setContentDescription(
                    ((Object) title)
                            + " "
                            + getContext()
                                    .getResources()
                                    .getString(R.string.decrease_button_content_description));
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int id = view.getId();
        if (id == R.id.delete_button) {
            onDeleteButtonClicked();
        } else if (id == R.id.add_button) {
            onAddButtonClicked();
        }
        view.announceForAccessibility(this.stateDescription);
    }

    public final void onDeleteButtonClicked() {
        int i = this.mSeekBarValue - 1;
        if (i < this.mMin || !callChangeListener(Integer.valueOf(i))) {
            return;
        }
        SeslSeekBar seslSeekBar = this.seekBar;
        if (seslSeekBar != null) {
            seslSeekBar.performHapticFeedback(AccessibilityConstant.HAPTIC_CONSTANT_CURSOR_MOVE);
        }
        setValueInternal(i, true);
    }

    @Override // android.view.View.OnLongClickListener
    public final boolean onLongClick(final View view) {
        this.isLongKeyProcessing = true;
        int id = view.getId();
        if (id != R.id.delete_button && id != R.id.add_button) {
            return false;
        }
        new Thread(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.accessibility.base.widget.A11ySeekBarWithButtonsPreference.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                while (true) {
                                    A11ySeekBarWithButtonsPreference
                                            a11ySeekBarWithButtonsPreference =
                                                    A11ySeekBarWithButtonsPreference.this;
                                    if (!a11ySeekBarWithButtonsPreference.isLongKeyProcessing) {
                                        return;
                                    }
                                    a11ySeekBarWithButtonsPreference.longPressHandler
                                            .sendEmptyMessage(
                                                    view.getId() == R.id.delete_button ? 1 : 2);
                                    try {
                                        Thread.sleep(300L);
                                    } catch (InterruptedException e) {
                                        Log.w(
                                                "A11ySeekBarWithButtonsPreference",
                                                "InterruptedException!",
                                                e);
                                    }
                                }
                            }
                        })
                .start();
        return false;
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        view.getId();
        if (motionEvent.getAction() == 1) {
            this.isLongKeyProcessing = false;
            this.longPressHandler.removeMessages(1);
            this.longPressHandler.removeMessages(2);
        }
        return false;
    }

    public A11ySeekBarWithButtonsPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public A11ySeekBarWithButtonsPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.isLongKeyProcessing = false;
        this.longPressHandler = new LongPressHandler(this);
    }
}

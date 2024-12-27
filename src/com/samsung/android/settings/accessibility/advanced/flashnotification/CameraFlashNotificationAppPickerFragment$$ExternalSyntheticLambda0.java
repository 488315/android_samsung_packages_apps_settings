package com.samsung.android.settings.accessibility.advanced.flashnotification;

import androidx.fragment.app.FragmentActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class CameraFlashNotificationAppPickerFragment$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CameraFlashNotificationAppPickerFragment f$0;

    public /* synthetic */ CameraFlashNotificationAppPickerFragment$$ExternalSyntheticLambda0(CameraFlashNotificationAppPickerFragment cameraFlashNotificationAppPickerFragment, int i) {
        this.$r8$classId = i;
        this.f$0 = cameraFlashNotificationAppPickerFragment;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        final CameraFlashNotificationAppPickerFragment cameraFlashNotificationAppPickerFragment = this.f$0;
        switch (i) {
            case 0:
                FragmentActivity activity = cameraFlashNotificationAppPickerFragment.getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new CameraFlashNotificationAppPickerFragment$$ExternalSyntheticLambda0(cameraFlashNotificationAppPickerFragment, 1));
                    break;
                }
                break;
            default:
                cameraFlashNotificationAppPickerFragment.appPickerListView.submitList(FlashNotificationUtil.getCameraFlashNotiInstalledAppDataList(cameraFlashNotificationAppPickerFragment.context, cameraFlashNotificationAppPickerFragment.installedAppSet));
                cameraFlashNotificationAppPickerFragment.loadingViewController.showContent(false);
                cameraFlashNotificationAppPickerFragment.appPickerListView.mOnStateChangeListener = 
                /*  JADX ERROR: Method code generation error
                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0021: IPUT 
                      (wrap:androidx.picker.widget.AppPickerState$OnStateChangeListener:0x001e: CONSTRUCTOR 
                      (r3v1 'cameraFlashNotificationAppPickerFragment' com.samsung.android.settings.accessibility.advanced.flashnotification.CameraFlashNotificationAppPickerFragment A[DONT_INLINE])
                     A[MD:(com.samsung.android.settings.accessibility.advanced.flashnotification.CameraFlashNotificationAppPickerFragment):void (m), WRAPPED] (LINE:31) call: com.samsung.android.settings.accessibility.advanced.flashnotification.CameraFlashNotificationAppPickerFragment.2.<init>(com.samsung.android.settings.accessibility.advanced.flashnotification.CameraFlashNotificationAppPickerFragment):void type: CONSTRUCTOR)
                      (wrap:androidx.picker.widget.SeslAppPickerListView:0x001a: IGET 
                      (r3v1 'cameraFlashNotificationAppPickerFragment' com.samsung.android.settings.accessibility.advanced.flashnotification.CameraFlashNotificationAppPickerFragment)
                     A[WRAPPED] (LINE:27) com.samsung.android.settings.accessibility.advanced.flashnotification.CameraFlashNotificationAppPickerFragment.appPickerListView androidx.picker.widget.SeslAppPickerListView)
                     (LINE:34) androidx.picker.widget.SeslAppPickerView.mOnStateChangeListener androidx.picker.widget.AppPickerState$OnStateChangeListener in method: com.samsung.android.settings.accessibility.advanced.flashnotification.CameraFlashNotificationAppPickerFragment$$ExternalSyntheticLambda0.run():void, file: classes4.dex
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                    	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                    	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:267)
                    	at jadx.core.dex.regions.SwitchRegion.generate(SwitchRegion.java:84)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
                    	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:276)
                    	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:406)
                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
                    	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                    	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                    Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.samsung.android.settings.accessibility.advanced.flashnotification.CameraFlashNotificationAppPickerFragment, state: NOT_LOADED
                    	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:305)
                    	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
                    	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                    	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:487)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                    	... 21 more
                    */
                /*
                    this = this;
                    int r0 = r3.$r8$classId
                    com.samsung.android.settings.accessibility.advanced.flashnotification.CameraFlashNotificationAppPickerFragment r3 = r3.f$0
                    switch(r0) {
                        case 0: goto L24;
                        default: goto L7;
                    }
                L7:
                    androidx.picker.widget.SeslAppPickerListView r0 = r3.appPickerListView
                    android.content.Context r1 = r3.context
                    java.util.Set r2 = r3.installedAppSet
                    java.util.List r1 = com.samsung.android.settings.accessibility.advanced.flashnotification.FlashNotificationUtil.getCameraFlashNotiInstalledAppDataList(r1, r2)
                    r0.submitList(r1)
                    com.android.settings.widget.LoadingViewController r0 = r3.loadingViewController
                    r1 = 0
                    r0.showContent(r1)
                    androidx.picker.widget.SeslAppPickerListView r0 = r3.appPickerListView
                    com.samsung.android.settings.accessibility.advanced.flashnotification.CameraFlashNotificationAppPickerFragment$2 r1 = new com.samsung.android.settings.accessibility.advanced.flashnotification.CameraFlashNotificationAppPickerFragment$2
                    r1.<init>(r3)
                    r0.mOnStateChangeListener = r1
                    return
                L24:
                    androidx.fragment.app.FragmentActivity r0 = r3.getActivity()
                    if (r0 == 0) goto L33
                    com.samsung.android.settings.accessibility.advanced.flashnotification.CameraFlashNotificationAppPickerFragment$$ExternalSyntheticLambda0 r1 = new com.samsung.android.settings.accessibility.advanced.flashnotification.CameraFlashNotificationAppPickerFragment$$ExternalSyntheticLambda0
                    r2 = 1
                    r1.<init>(r3, r2)
                    r0.runOnUiThread(r1)
                L33:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settings.accessibility.advanced.flashnotification.CameraFlashNotificationAppPickerFragment$$ExternalSyntheticLambda0.run():void");
            }
        }

package com.samsung.android.settings.bixby.control.commands;

import android.os.Bundle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SetAndLaunchCommand extends LaunchCommand {
    public SetCommand mSetCommand;

    @Override // com.samsung.android.settings.bixby.control.commands.LaunchCommand,
              // com.samsung.android.settings.bixby.control.commands.BaseCommand
    public final Bundle executeInternal(String str) {
        this.mSetCommand.execute(str);
        return super.executeInternal(str);
    }
}

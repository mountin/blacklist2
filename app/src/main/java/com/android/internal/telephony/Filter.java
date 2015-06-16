package com.android.internal.telephony;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by mountin on 16.06.2015.
 */
public class Filter {

    private final ComponentName componentName;
    private final PackageManager packageManager;
    private Context _context;

    private Filter(Context context)
    {
        _context = context;

        String packageName = context.getPackageName();
        String receiverComponent = packageName + ".SMSReceiver";
        componentName = new ComponentName(packageName, receiverComponent);
        packageManager = context.getPackageManager();
    }
}

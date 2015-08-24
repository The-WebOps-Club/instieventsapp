package org.saarang.instieventsapp.Services;

import android.content.Intent;

/**
 * Created by kevin selva prasanna on 24-Aug-15.
 */
public class IE_InstanceIDListenerService extends com.google.android.gms.iid.InstanceIDListenerService {
    private static final String TAG = "MyInstanceIDLS";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. This call is initiated by the
     * InstanceID provider.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        Intent intent = new Intent(this, IE_RegistrationIntentService.class);
        startService(intent);
    }
    // [END refresh_token]
}

package com.kalerkantho.Gcm;

import android.util.Log;

import com.aapbd.utils.storage.PersistData;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.kalerkantho.Utils.AppConstant;

import cz.msebera.android.httpclient.util.TextUtils;

/**
 * Created by wlaptop on 9/3/2016.
 */
public class FirebaseIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.

        if(!(TextUtils.isEmpty(token)))
        {
            PersistData.setStringData(getApplicationContext(), AppConstant.GCMID,token);
        }

    }
}

package com.example.soorinpark.beerbelly;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.net.URI;
import org.apache.http.client.methods.HttpGet;
import android.app.Fragment;


/**
 * Created by soorinpark on 11/8/16.
 */

public class MapFragment extends Fragment {
    private static final String TAG = "AATestFragment";

    // you'll want to call a REST service, but for basic network testing i use any url
    //private static final String TEST_URL                 = "http://jsonplaceholder.typicode.com/comments";
    private static final String TEST_URL                   = "http://denverpost.com/sports";
    private static final String ACTION_FOR_INTENT_CALLBACK = "THIS_IS_A_UNIQUE_KEY_WE_USE_TO_COMMUNICATE";

    ProgressDialog progress;
    private TextView ourTextView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    /**
     * Any code to access activity fields must be handled in this method.
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ourTextView = (TextView)getActivity().findViewById(R.id.myTextView);
        getContent();
    }

    private void getContent()
    {
        // the request
        try
        {
            HttpGet httpGet = new HttpGet(new URI(TEST_URL));
            RestTask task = new RestTask(getActivity(), ACTION_FOR_INTENT_CALLBACK);
            task.execute(httpGet);
            progress = ProgressDialog.show(getActivity(), "Getting Data ...", "Waiting For Results...", true);
        }
        catch (Exception e)
        {
            Log.e(TAG, e.getMessage());
        }

    }

    @Override
    public void onResume()
    {
        super.onResume();
        getActivity().registerReceiver(receiver, new IntentFilter(ACTION_FOR_INTENT_CALLBACK));
    }

    @Override
    public void onPause()
    {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }


    /**
     * Our Broadcast Receiver. We get notified that the data is ready this way.
     */
    private BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // clear the progress indicator
            if (progress != null)
            {
                progress.dismiss();
            }
            String response = intent.getStringExtra(RestTask.HTTP_RESPONSE);
            ourTextView.setText(response);
            Log.i(TAG, "RESPONSE = " + response);
            //
            // my old json code was here. this is where you will parse it.
            //
        }
    };
}

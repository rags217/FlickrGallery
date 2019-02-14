package com.rags.flickrgallery.util.home;

import android.content.Context;
import android.content.Intent;

import com.rags.flickrgallery.R;


public class ShareImage {

    //share image via email
    //@param: url to be shared and context to start intent activity
    public void shareViaEmail(String url, Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.email_subject));
        intent.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.mail_body) + "\n\n" + url);

        context.startActivity(Intent.createChooser(intent, context.getString(R.string.share_via_email)));
    }
}

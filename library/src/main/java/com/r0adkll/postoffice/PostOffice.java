package com.r0adkll.postoffice;

import android.content.Context;

import com.r0adkll.postoffice.model.Delivery;

/**
 * This static class will be used to generate new Delivery dialog interfaces
 *
 * Project: PostOffice
 * Package: com.r0adkll.postoffice
 * Created by drew.heavner on 8/20/14.
 */
public class PostOffice {


    /**
     * Start generating a new 'Mail' object to display a new
     * dialog.
     *
     * @param ctx       the application context
     * @return          the Delivery Builder
     */
    public static Delivery.Builder newMail(Context ctx){
        return new Delivery.Builder(ctx);
    }


}

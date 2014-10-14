package com.android.pxfifthtest;

import android.content.Context;

import com.fivehundredpx.api.PxApi;
import com.kiumiu.ca.api500px.response.photo.get_photos_response;

public class ApiHelper {

	public static final String CONSUMER_KEY =    "raiBkQlzAhfuN8FnC1CHJ1xukV4IcSXPxHQxIZl5";
	public static final String CONSUMER_SECRET = "XddxZV3fwglWxYf6eOOhoNBpQLncbsyVKNyJ4THA";
	
	private static PxApi getApi(Context context){      
            return new PxApi(CONSUMER_KEY, CONSUMER_SECRET);  
    }
	 public static get_photos_response getPhotoStream(Context context, String feature, int rpp, int size, int page){
	        PxApi pxapi = getApi(context);
	        get_photos_response photo = null;
	        try
	        {
	        	photo = pxapi.getPhotoInterface().get_photosEx(feature, null, null, null, null, page, rpp, size, true, true, true, null);
	        }
	        catch(Exception e)
	        {
	        	
	        }
	        return photo;
	    }

}

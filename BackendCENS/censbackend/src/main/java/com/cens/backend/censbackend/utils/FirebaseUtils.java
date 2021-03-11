package com.cens.backend.censbackend.utils;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;


public class FirebaseUtils {
	
	static String FIREBASE_URL_FCM = "https://fcm.googleapis.com/fcm/send";
	static String FIREBASE_SERVER_KEY = "AAAAPgDGbos:APA91bFBWjChDRfp5J0ek2qdDy5Io3fQoGqq1SD3M-EWeslF_aTSeGaUINVATD3UTza0o5GrC2qOmia8l_cRMcqEDfwKTwlb25-xxIYtNBTyFVxERikQOhNuK_mvbYiKF2lPmuX09r7N";

	//PROEPRTIES OF NOTIFICATION
	static String TITLE_NOTIFICATION = "CENS APP";
	static String TOPIC_NOTIFICATION = "/topics/CENS_APP";
	
	public static CompletableFuture<String> sendPushNotification(String message) {
		try {
		 RestTemplate restTemplate = new RestTemplate();
		 
			NotificationRequest requestNotification = new NotificationRequest(); 
			requestNotification.setTo(TOPIC_NOTIFICATION);
			Notification notification= new Notification();
			notification.setTitle(TITLE_NOTIFICATION);
			notification.setBody(message);
			requestNotification.setNotification(notification);
			
		    /**
		    https://fcm.googleapis.com/fcm/send
		    Content-Type:application/json
		    Authorization:key=FIREBASE_SERVER_KEY*/
		 
		    ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		    interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
		    interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
		    restTemplate.setInterceptors(interceptors);
		 
		    String firebaseResponse = restTemplate.postForObject(FIREBASE_URL_FCM, requestNotification, String.class);
		 
		    return CompletableFuture.completedFuture(firebaseResponse);
		    
		}catch(Exception ex) {
			
			ex.printStackTrace();
			return null;
			
		}
		
	}

}

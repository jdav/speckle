package com.malleamus.speckle;


public abstract interface View {
	
	public Context getContext();
	public void setContext(Context context);
	public boolean isAuthenticated();
	public boolean isAuthorized();

}

package com.malleamus.speckle;


public interface UseCase {
	
	public void setContext(Context context) throws SpeckleException;
	public Context getContext() throws SpeckleException;
	public boolean isAuthenticated() throws SpeckleException;
	public boolean isAuthorized() throws SpeckleException;
	public PromptingView getOpening() throws SpeckleException;
	public InvitationView getRebuff() throws SpeckleException;
	public void execute() throws SpeckleException;
	public InvitationView getClosing() throws SpeckleException;

}

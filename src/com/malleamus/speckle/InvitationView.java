package com.malleamus.speckle;

public interface InvitationView extends View {
	
	public UseCase show() throws SpeckleException; //MUST block and wait for user input

}

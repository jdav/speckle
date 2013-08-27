package com.malleamus.speckle;

public interface PromptingView extends View {

	public void show() throws SpeckleException; //MUST block and wait for user input
	
}

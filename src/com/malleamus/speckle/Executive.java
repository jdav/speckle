package com.malleamus.speckle;

public class Executive {

	public static void main(String[] args) throws SpeckleException {
		if (args.length != 1) {
			throw new SpeckleException(
					"ERROR: Command-line syntax should be java "
							+ "com.malleamus.speckle.Executive configfile");
		}
		
		Executive executive = new Executive(args[0]);
		executive.execute();
	}
	
	Context context = null;

	public Executive(String configName) {
		context = new Context();
		Configuration config = new Configuration(configName);
		context.setConfiguration(config);
		context.setUI(config.getObject("UI_CLASS"));
		context.put("STARTUP", config.getObject("STARTUP_CLASS"));
		context.put("SHUTDOWN", config.getClass("SHUTDOWN_CLASS"));
		context.put("AUTHENTICATION", config.getObject("AUTHENTICATION_CLASS"));
	}
	
	public void execute() throws SpeckleException {
		try {
			UseCase useCase = (UseCase) context.get("STARTUP");
			Class<? extends UseCase> shutdownClass = null;
			try {
				shutdownClass = (Class<? extends UseCase>) context.get("SHUTDOWN");
			} catch (Exception e) {
				throw new SpeckleException("ERROR: SHUTDOWN_CLASS "
						+ "property must be a class that "
						+ "implements the interface "
						+ "com.malleamus.speckle.UseCase.");
			}

			UseCase useCaseOnHold = null;
			useCase.setContext(context);
			while (!(shutdownClass.isInstance(useCase))) {
				if (useCase.isAuthenticated()) {
					if (useCase.isAuthorized()) {
						PromptingView openingView = useCase.getOpening();
						if (openingView.isAuthorized()) {
							openingView.show();
							useCase.execute();

							if (useCaseOnHold == null) {
								InvitationView closingView = useCase.getClosing();
								if (closingView.isAuthorized()) {
									useCase = closingView.show();
								} else {
									throw new SpeckleException(
											"ERROR: Closing view is not authorized");
								}
							} else {
								useCase = useCaseOnHold;
								useCaseOnHold = null;
							}
						} else {
							throw new SpeckleException(
									"ERROR: Opening view is not authorized");
						}
					} else {
						InvitationView rebuffView = useCase.getRebuff();
						if (rebuffView.isAuthorized()) {
							useCase = rebuffView.show();
						} else {
							throw new SpeckleException(
									"ERROR: Rebuff view is not authorized");
						}
					}
				} else {
					useCaseOnHold = useCase;
					useCase = (UseCase) context.get("AUTHENTICATION");
				}
				
				if (useCase == null) {
					useCase = shutdownClass.newInstance();
				}
			}
		} catch (Throwable t) {
			throw new SpeckleException(t);
		}
	}
}

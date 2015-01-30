package edu.carleton.COMP4601.a1.Main;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class TomcatContextListener implements ServletContextListener {

	// Stops the MongoDB client when the server is destroyed
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		DatabaseManager.getInstance().stopMongoClient();
	}

	// Initializes the MongoDB client when the server is initialized
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		DatabaseManager.getInstance();
	}

}

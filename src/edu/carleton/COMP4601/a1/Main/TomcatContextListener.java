package edu.carleton.COMP4601.a1.Main;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class TomcatContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		DatabaseManager.getInstance().stopMongoClient();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		DatabaseManager.getInstance();
	}

}

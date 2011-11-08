/* 
 * Copyright (C) 2010 Thorben Primke/Moon Monkey Labs <tprimke@moonmonkeylabs.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package org.moonmonkeylabs.bbmvc;

import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.util.IntHashtable;

/**
 * The Navigator class is used to handle all navigation for MVC
 * application. It keeps track of all Controller, the navigation
 * history and pops and pushes screen on the display stack
 * 
 */
public final class Navigator extends UiApplication
{
	private IntHashtable _controllerCache;
	private NavigationHistory _history;
	private static Navigator _instance;

	/**
	 * Default constructor for the Navigator class
	 * 
	 */
	public Navigator()
	{
		_controllerCache = new IntHashtable();
		_history = new NavigationHistory();
	}

	/**
	 * The getInstance static method returns a singleton instance of
	 * Navigator. The method is synchronized to ensure thread safety.
	 * 
	 */
	public static Navigator getInstance()
	{
		synchronized (Navigator.class)
		{
			if (_instance == null)
				_instance = new Navigator();
		}
		return _instance;
	}

	/**
	 * The addController method adds a Controller to the controller cache.
	 * 
	 * @param controller The Controller to be added
	 * @throws Exception It throws an exception if the Controller is null or already exists
	 * 
	 */
	public void addController(Controller controller) throws Exception
	{
		// Checks if controller is null
		if (controller != null)
		{
			int controllerId = controller.getControllerId();
			// Checks if a controller with the same controller id is already in the cache
			if (!_controllerCache.containsKey(controllerId))
				_controllerCache.put(controllerId, controller);
			else
				throw new Exception("Already contrains a conroller with this name");
		}
		else
			throw new Exception("Argument is null");
	}

	/**
	 * The removeController methods removes a controller from the controller cache.
	 * 
	 * @param controller The Controller to be removed
	 * @throws Exception Throws an exception if the Controller is null or not in the cache
	 */
	public void removeController(Controller controller) throws Exception
	{
		// Checks if the controller is null
		if (controller != null)
		{
			removeController(controller.getControllerId());
		}
		else
			throw new Exception("Argument is null");
	}

	/**
	 * The removeController methods removes a controller from the controller cache.
	 * 
	 * @param controllerId The id of the Controller to be removed.
	 * @throws Exception Throws an exception if the id is zero or the controller is not in the cache
	 */
	public void removeController(int controllerId) throws Exception
	{
		if (controllerId != 0)
		{
			// Checks if the controller is in the controller cache
			if (_controllerCache.containsKey(controllerId))
			{
				// Remove from History first
				_history.removeController((Controller) _controllerCache.get(controllerId));
				// Last remove the Controller from the cache
				_controllerCache.remove(controllerId);
			}
			else
				throw new Exception("Controller does not exist");
		}
		else
			throw new Exception("Argument is null");
	}

	/**
	 * The navigate method is responsible for controlling which screen is show.
	 * It uses the controller id to look up the Controller in the cache,
	 * updates the navigation history and pushes and pops screens of the display stack.
	 * 
	 * @param controllerId The id of the Controller to navigate to
	 * @throws Exception Throws an exception if the id is less than zero or the controller is not in the cache
	 */
	public void navigate(int controllerId) throws Exception
	{
		navigate(controllerId, null);
	}

	/**
	 * The navigate method is responsible for controlling which screen is show.
	 * It uses the Controller to look up the Controller in the cache,
	 * updates the navigation history and pushes and pops screens of the display stack.
	 * 
	 * @param controller The Controller to navigate to
	 * @param parameters Any initialization parameters that need to be passed to the Controller
	 * @throws Exception Throws an exception if the id is less than zero or the controller is not in the cache
	 */
	public void navigate(Controller controller) throws Exception
	{
		navigate(controller, null);
	}

	/**
	 * The navigate method is responsible for controlling which screen is show.
	 * It uses the Controller to look up the Controller in the cache,
	 * updates the navigation history and pushes and pops screens of the display stack.
	 * Additionally, it can pass initialization parameters to the Controller.
	 * 
	 * @param controllerId The Controller to navigate to
	 * @param parameters Any initialization parameters that need to be passed to the Controller
	 * @throws Exception Throws an exception if the id is less than zero or the controller is not in the cache
	 */
	public void navigate(Controller controller, Object[] parameters) throws Exception
	{
		if (controller != null)
		{
			navigate(controller.getControllerId(), parameters);
		}
		else
			throw new Exception("Argument is null");
	}

	/**
	 * The navigate method is responsible for controlling which screen is show.
	 * It uses the controller id to look up the Controller in the cache,
	 * updates the navigation history and pushes and pops screens of the display stack.
	 * Additionally, it can pass initialization parameters to the Controller.
	 * 
	 * @param controllerId The id of the Controller to navigate to
	 * @param parameters Any initialization parameters that need to be passed to the Controller
	 * @throws Exception Throws an exception if the id is less than zero or the controller is not in the cache
	 */
	public void navigate(int controllerId, Object[] parameters) throws Exception
	{
		if (controllerId > 0)
		{
			if (_controllerCache.containsKey(controllerId))
			{
				Controller cachedController = (Controller) _controllerCache.get(controllerId);
				Controller currentController = _history.getCurrent();

				if (cachedController != currentController)
				{
					// Checks to ensure that the parameters are not null
					if (parameters != null)
						cachedController.initialize(parameters);
					// Pushes the new screen on the display stack
					pushScreen(cachedController.getView().getViewScreen());

					// The previous screen is poped here because using the navigator, there is the case
					// where the screen is shown again and if it is already on the stack, this causes 
					// an exception since a screen cannot be pushed twice. 
					//if (currentController != null)
					//	popScreen(currentController.getView().getViewScreen());
					// *****TP: changed to not pop the screen - it was causing problems with popscreens
					// Have to look intot he exception with the double pushing. 
					// Posibly it just has to be pop and push
					// and the back has to be modified so that if the desired screen is not below a back action
					// then the screen has to be pushed
					
					// Add history
					_history.add(cachedController);
				}
			}
			else
				throw new Exception("Controller does not exist");
		}
		else
			throw new Exception("Argument is null");
	}

	/**
	 * The goBack method is used to easily navigate to the previous screen.
	 * 
	 */
	public void goBack()
	{
		goBack(null);
	}

	/**
	 * The goBack method is used to easily navigate to the previous screen.
	 * Additionally, it can pass a parameter to the Controller's initialization
	 * method.
	 * 
	 * @param parameters Any initialization parameters that need to be passed to the Controller
	 */
	public void goBack(Object[] parameters)
	{
		if (_history.canGoBack())
		{
			Controller currentController = _history.getCurrent();
			_history.goBack();
			Controller controller = _history.getCurrent();
			// Need to remove the previous screen
			if (parameters != null)
				_history.getCurrent().update(parameters);
			// Because the old/previous screen is always poped in the navigator method, it has 
			// to be pushed onto the display stack here again. 
			//pushScreen(controller.getView().getViewScreen());
			// Remove the old screen
			popScreen(currentController.getView().getViewScreen());
		}
		else
		{
			popScreen(_history.getCurrent().getView().getViewScreen());
			System.exit(0);
		}
	}

	/**
	 * The hasController method checks if a Controller with
	 * this id exists in the Controller cache
	 * 
	 * @param controllerId The id of the Controller is question
	 * @return True if the controller is already in the cache, false otherwise
	 */
	public boolean hasController(int controllerId)
	{
		return _controllerCache.containsKey(controllerId);
	}

	/**
	 * The getController accessor method returns the controller with
	 * the respective id
	 * 
	 * @param controllerId The id of the Controller to be retrieved
	 * @return A Controller instance if the id is valid, null otherwise
	 */
	public Controller getController(int controllerId)
	{
		return (Controller) _controllerCache.get(controllerId);
	}

	/**
	 * The getCurrentController accessor method returns the current
	 * controller.
	 * 
	 * @return The Controller instance of the current controller, null if not current controller exists
	 */
	public Controller getCurrentController()
	{
		return _history.getCurrent();
	}

	/**
	 * The removeFromHistory method completely removes a
	 * controller from the history and display stack
	 * 
	 * @param controller The Controller to be removed
	 */
	public void removeFromHistory(Controller controller)
	{
		// If a controller can be removed from the history, 
		if (_history.removeController(controller))
		{
			if (controller.getView().getViewScreen().isDisplayed())
				popScreen(controller.getView().getViewScreen());
		}
	}

	/**
	 * The isPreviousController method checks if the previous
	 * has a specific id.
	 * 
	 * @param controllerId The id of the potential previous Controller
	 * @return True if indeed the previous controller has the id, otherwise false
	 */
	public boolean isPreviousController(int controllerId)
	{
		Controller beforeCurrent = _history.getBeforeCurrent();
		// Check if there was even a previous controller to the current
		if (beforeCurrent != null)
			return beforeCurrent.getControllerId() == controllerId;
		return false;
	}
}

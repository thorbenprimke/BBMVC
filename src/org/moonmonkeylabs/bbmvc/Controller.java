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

/**
 * A Controller is the base for all controllers within an MVC based
 * application. It needs a View as well as an id to be instantiated.
 * The Controller subscribes to the View's ViewListener in order to be 
 * notified whenever new user input needs to be processed. The Controller 
 * has an initialize and update method that can be used to initialize and
 * update the Controller.
 * 
 * @author Thorben Primke
 * @version 1.0
 */
public abstract class Controller
{
	private int _controllerId;
	private ViewListener _viewListener;
	protected View _view;

	/**
	 * Default constructor for Controller
	 * 
	 * It sets the View and controllerId as well as adds a ViewListener
	 * to the View.
	 * 
	 * @param view View instance associated with this Controller
	 * @param controllerId The controller's id
	 */
	public Controller(View view, int controllerId)
	{
		_view = view;
		_controllerId = controllerId;

		// Create a new instance of ViewListner
		_viewListener = new ViewListener()
		{
			public void viewStateChanged(int key)
			{
				onViewStateChanged(key);
			}
		};
		_view.addViewListener(_viewListener);
	}

	/**
	 * The initialize method is used to pass data to a Controller
	 * 
	 * @param parameters Parameters for initialization of Controller
	 */
	public void initialize(Object[] parameters)
	{
		onInitialize(parameters);
	}

	/**
	 * The onInitilize method is only used internally by the 
	 * initialize method and each controller implements its own
	 * onInitilize. 
	 * 
	 * @param parameters Parameters for initialization of Controller
	 */
	abstract protected void onInitialize(Object[] parameters);

	/**
	 * The update method is used to updated data to a Controller. 
	 * This method might be used when a Controller is already 
	 * initialized and only specific data needs to updated.
	 * 
	 * @param parameters Parameters to update the Controller
	 */
	public void update(Object[] parameters)
	{
		onUpdate(parameters);
	}

	/**
	 * The onUpdate method is only used internally by the 
	 * updated method and each Controller implements its own
	 * onUpdate. 
	 * 
	 * @param parameters Parameters to update the Controller
	 */
	abstract protected void onUpdate(Object[] parameters);

	
	/**
	 * The onViewStateChanged method is used only within any 
	 * ViewListener. onViewStateChanged has to be implemented 
	 * by each controller. It is called whenever change occurs
	 * within a view. 
	 * 
	 * @param event The event is a unique id for the changed state 
	 */
	abstract protected void onViewStateChanged(int event);

	/**
	 * getViewData is an accessor method for the View's ViewData
	 * 
	 * @return The View's ViewData
	 */
	public ViewDataHashtable getViewData()
	{
		return _view.getViewData();
	}

	/**
	 * setViewData is a mutator method for the View's ViewData
	 * 
	 * @param viewData A ViewDataHastable instance - cannot be NULL
	 */
	public void setViewData(ViewDataHashtable viewData)
	{
		// Check that the new ViewData is not null
		if (viewData != null)
			_view.setViewData(viewData);
	}

	/**
	 * getModel is an accessor method for the View's Model
	 * 
	 * @return The View's Model
	 */
	public Object getModel()
	{
		return _view.getViewData().getModel();
	}

	/**
	 * setModel is a mutator method to set the View's Model
	 * 
	 * @param model A Model instance - cannot be NULL
	 */
	public void setModel(Object model)
	{
		// Check that the new model is not null
		if (model != null)
			_view.getViewData().setModel(model);
	}

	/**
	 * getControllerId is an accessor method for the Controller's id
	 * 
	 * @return  The Controller's id
	 */
	public int getControllerId()
	{
		return _controllerId;
	}

	/**
	 * setControllerId is a mutator method to set the Controller's id
	 * 
	 * @param controllerId An Id for the Controller
	 */
	public void setControllerId(int controllerId)
	{
		_controllerId = controllerId;
	}
	
	/**
	 * getView is an accessor method for the View associated with this
	 * Controller.
	 * 
	 * @return The View
	 */
	public View getView()
	{
		return _view;
	}
	
	/**
	 * getViewData is a mutator method for the Controller's View
	 * 
	 * @param view The new View that should be associated with this Controller - cannot be NULL
	 */
	public void setView(View view)
	{
		// Make sure that the new view is not null
		if (view == null)
			return;
		
		// Check if the current view is null - just in case so we don't remove a listener
		// that does not exist
		if (_view != null)
			_view.removeViewListener(_viewListener);
		
		_view = view;
		_view.addViewListener(_viewListener);
	}
}

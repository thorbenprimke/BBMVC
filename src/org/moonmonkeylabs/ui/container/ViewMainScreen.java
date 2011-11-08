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

package org.moonmonkeylabs.ui.container;

import java.util.Vector;
import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.container.MainScreen;
import org.moonmonkeylabs.bbmvc.View;
import org.moonmonkeylabs.bbmvc.ViewDataHashtable;
import org.moonmonkeylabs.bbmvc.ViewListener;

/**
 * The ViewMainScreen class is a simple implementation that satisfies the
 * View interface and can be used with the Controller. It extends
 * MainScreen.
 * 
 * @author Thorben Primke
 * @version 1.0
 */
public abstract class ViewMainScreen extends MainScreen implements View
{
	public static final int ScreenClose = 2001;
	public static final int ScreenSave = 2002;

	protected Vector _viewListeners;
	private ViewDataHashtable _viewData;

	/**
	 * Default constructor
	 */
	protected ViewMainScreen()
	{
		this(0);
	}
	
	protected ViewMainScreen(long style)
	{
		super(style);
		_viewListeners = new Vector();
		_viewData = new ViewDataHashtable();
	}

	/**
	 * The updateView method is used to inform the View from
	 * any other object (in most cases the Controller) that
	 * certain update action needs to be taken
	 * 
	 * @param key A numerical key for the update event
	 */
	public abstract void updateView(int key);

	/**
	 * The getViewScreen accessor method ensure that the Navigator has
	 * access through the Controller to the screen
	 * 
	 * @return An instance of the Screen
	 */
	public Screen getViewScreen()
	{
		return getScreen();
	}

	/**
	 * The getViewData accessor method provides a way for the Controller
	 * to access and set the ViewData
	 * 
	 * @return The View's ViewDataHashtable
	 */
	public ViewDataHashtable getViewData()
	{
		return _viewData;
	}

	/**
	 * The setViewData mutator sets ViewDataHashtable
	 * 
	 * @param viewData The ViewDataHashtable object
	 */
	public void setViewData(ViewDataHashtable viewData)
	{
		_viewData = viewData;
	}

	/**
	 * The getModel accessor method
	 * 
	 * @return The Model
	 */
	public Object getModel()
	{
		return _viewData.getModel();
	}

	/**
	 * The setModel mutator method
	 * 
	 * @param model The Model object
	 */
	public void setModel(Object model)
	{
		_viewData.setModel(model);
	}

	/**
	 * The addViewListener method is used to add a ViewListener
	 * 
	 * @param viewListener The ViewListener to be added
	 */
	public void addViewListener(final ViewListener viewListener)
	{
		if (!_viewListeners.contains(viewListener))
		{
			_viewListeners.addElement(viewListener);
		}
	}

	/**
	 * The removeViewListener method is used to remove a ViewListener
	 * 
	 * @param viewListener The ViewListener to be removed
	 */
	public void removeViewListener(final ViewListener viewListener)
	{
		_viewListeners.removeElement(viewListener);
	}

	/**
	 * The notifyViewListeners kicks of the notification process to 
	 * all subscribers. 
	 * 
	 * @param key A numerical key that identifying the event
	 */
	protected void notifyViewListeners(int key)
	{
		int numListener = _viewListeners.size();
		for (int i = 0; i < numListener; ++i)
			notifyViewListener(((ViewListener) _viewListeners.elementAt(i)), key);
	}
	
	/**
	 * The notifyViewListener is a generic method that can be used for a ViewListener. 
	 * Since the notification is often passed from a background thread, invokeAndWait is
	 * used. 
	 * 
	 * @param viewListener The ViewListener
	 * @param key A numerical key that identifying the event 
	 */
	private void notifyViewListener(ViewListener viewListener, int key)
	{
		viewListener.viewStateChanged(key);
	}

	/**
	 * Override the default close method. 
	 * Let the Navigator do the work. 
	 * Notify the controller that we are ready to close. 
	 * 
	 */
	public void close()
	{
		notifyViewListeners(ScreenClose);
	}
}

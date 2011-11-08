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

/**
 * The View interface is used for all Views within an MVC 
 * application.  The reason that it is an interface and not a class
 * is that it can be used with Screen, FullScren, MainScreen or any 
 * custom screen. 
 * 
 * @author Thorben Primke
 * @version 1.0
 */
public interface View
{
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
	public abstract Screen getViewScreen();

	/**
	 * The getViewData accessor method provides a way for the Controller
	 * to access and set the ViewData
	 * 
	 * @return The View's ViewDataHashtable
	 */
	public abstract ViewDataHashtable getViewData();
	
	/**
	 * The setViewData mutator sets ViewDataHashtable
	 * 
	 * @param viewData The ViewDataHashtable object
	 */
	public abstract void setViewData(ViewDataHashtable viewData);
	
	/**
	 * The getModel accessor method
	 * 
	 * @return The Model
	 */
	public abstract Object getModel();
	
	/**
	 * The setModel mutator method
	 * 
	 * @param model The Model object
	 */
	public abstract void setModel(Object model);
	
	/**
	 * The addViewListener method is used to add a ViewListener
	 * 
	 * @param viewListener The ViewListener to be added
	 */
	public abstract void addViewListener(final ViewListener viewListener);
	
	/**
	 * The removeViewListener method is used to remove a ViewListener
	 * 
	 * @param viewListener The ViewListener to be removed
	 */
	public abstract void removeViewListener(final ViewListener viewListener);
}

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

import java.util.Vector;
import net.rim.device.api.system.Application;

/**
 * The Model class is the base class for all Models/ViewModels. 
 * It allows the View to subscribe to its Model and thus be 
 * notified of any changes that occur within the Model
 * 
 * @author Thorben Primke
 * @version 1.0
 */
public abstract class Model
{
	private Vector _modelListener;

	/**
	 * Default constructor for Model
	 * 
	 * It creates a new Vector to keep track of all Views that 
	 * subscribe for notifications. 
	 */
	public Model()
	{
		_modelListener = new Vector();
	}

	/**
	 * The addModelListener method simply add a ModelListener 
	 * to the ModelListener vector if the listener is not already
	 * in it. 
	 * 
	 * @param modelListener ModelListener to be added
	 */
	public void addModelListener(final ModelListener modelListener)
	{
		// Checks that the listener is not already in the vector
		if (!_modelListener.contains(modelListener))
			_modelListener.addElement(modelListener);
	}

	/**
	 * The removeModelListener method removes a ModelListener from 
	 * the Model. 
	 * 
	 * @param modelListener ModelListener to be removed
	 */
	public void removeModelListener(final ModelListener modelListener)
	{
		_modelListener.removeElement(modelListener);
	}

	/**
	 * The notifyModelListeners kicks of the notification process to 
	 * all subscribers. 
	 * 
	 * @param key A numerical key that identifying the event
	 * @param args The data that is passed to the subscriber
	 */
	protected void notifyModelListeners(int key, Object[] args)
	{
		int numListener = _modelListener.size();
		for (int i = numListener -1; i >= 0; --i)
			notifyModelListener(((ModelListener) _modelListener.elementAt(i)), key, args);
	}

	/**
	 * The notifyModelListener is a generic method that can be used for a ModelListener. 
	 * Since the notification is often passed from a background thread, invokeAndWait is
	 * used. 
	 * 
	 * @param modelListener The ModelListener
	 * @param key A numerical key that identifying the event 
	 * @param args The data that is passed to the subscriber
	 */
	private void notifyModelListener(final ModelListener modelListener, final int key, final Object[] args)
	{
		Application.getApplication().invokeAndWait(new Runnable()
		{
			public void run()
			{
				modelListener.modelChanged(key, args);
			}
		});
	}
}

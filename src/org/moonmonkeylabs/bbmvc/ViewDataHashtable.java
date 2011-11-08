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

import java.util.Hashtable;

import net.rim.device.api.util.IntHashtable;

/**
 * The ViewDataHashtable class extends IntHashtable and additionally includes the Model object
 * It is used by the View and Controller to pass information between back and forth
 * 
 * @author Thorben Primke
 * @version 1.0
 * @see Hashtable
 */
final public class ViewDataHashtable extends IntHashtable
{
	Object _model;

	/**
	 * Default constructor for ViewDataHashtable
	 * 
	 */
	public ViewDataHashtable() 
	{
		this(null);
	}
	
	/**
	 * Constructor for ViewDataHashtable that takes a Model object
	 *  
	 * @param model An instance of a Model
	 */
	public ViewDataHashtable(Object model)
	{
		super();
		setModel(model);
	}
	
	/**
	 * The setModel mutator sets the ViewDataHashtable's Model object
	 * 
	 * @param model An instance of a Model
	 */
	public void setModel(Object model)
	{
		_model = model;
	}
	
	/**
	 * The getModel accessor method returns the ViewDataHashtable's Model object
	 * 
	 * @return The Model object
	 */
	public Object getModel( )
	{
		return _model;
	}
}

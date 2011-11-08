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
 * The NavigationHistoryItem class is wrapper that looks a
 * lot like a linked list in that it has a previous and next
 * reference for NavigationHistoryItems and an instance of
 * Controller
 * 
 * @author Thorben Primke
 * @version 1.0
 */
public final class NavigationHistoryItem
{
	private Controller _controller;
	private NavigationHistoryItem _next;
	private NavigationHistoryItem _previous;

	/**
	 * A constructor that takes a Controller instance
	 * 
	 * @param controller The Controller for this history item
	 */
	public NavigationHistoryItem(Controller controller)
	{
		_controller = controller;
	}

	/**
	 * A constructor that takes both a Controller and NavigationHistoryItem.
	 * The contoller as well as the previous reference as set. 
	 * 
	 * @param controller The Controller for this history item
	 * @param previous The previous NavigationHistoryItem object
	 */
	public NavigationHistoryItem(Controller controller, NavigationHistoryItem previous)
	{
		_controller = controller;
		_previous = previous;
	}

	/**
	 * The getController accessor returns the Controller object
	 * 
	 * @return The Controller object
	 */
	public Controller getController()
	{
		return _controller;
	}

	/**
	 * The setController mutator sets the Controller instance variable
	 * 
	 * @param controller A new Controller instance
	 */
	public void setController(Controller controller)
	{
		_controller = controller;
	}

	/**
	 * The getNext accessor returns the next NavigationHistoryItem object
	 * 
	 * @return The next NavigationHistoryItem object
	 */
	public NavigationHistoryItem getNext()
	{
		return _next;
	}

	/**
	 * The setNext mutator sets the next NavigationHistoryItem instance variable
	 * 
	 * @param _next
	 */
	public void setNext(NavigationHistoryItem next)
	{
		_next = next;
	}

	/**
	 * The getPrevious accessor method return the previous NavigationHistoryItem object
	 * 
	 * @return The previous NavigationHistoryItem object
	 */
	public NavigationHistoryItem getPrevious()
	{
		return _previous;
	}

	/**
	 * The setPrevious mutator sets the previous NavigationHistoryItem instance variable
	 * 
	 * @param _previous The previous NavigationHistoryItem object
	 */
	public void setPrevious(NavigationHistoryItem _previous)
	{
		this._previous = _previous;
	}
}

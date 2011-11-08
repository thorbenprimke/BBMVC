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

/**
 * The NavigationHistory class is used by the Navigator to keep 
 * of the navigation history. 
 * 
 * @author Thorben Primke
 * @version 1.0
 * 
 */
public final class NavigationHistory
{
	//TODO: Need to change the vector to a IntHashtable -> should be more efficient
	// when trying to look up if a Controller is already in the history. 
	private Vector _navigationHistory;
	private NavigationHistoryItem _currentHistoryItem;

	/**
	 * Default constructor for NavigationHistory
	 * 
	 */
	public NavigationHistory()
	{
		_navigationHistory = new Vector();
	}

	/**
	 * The add method adds a Controller to the history. 
	 * If that controller is already in the history, it is removed first. 
	 * 
	 * @param controller The Controller to be added to the history
	 */
	public void add(Controller controller)
	{
		// Check if the controller already exists in the history
		if (inHistory(controller))
			remove(controller);

		// Check if this is the first history item that is added to the list
		if (_currentHistoryItem == null)
		{
			_currentHistoryItem = new NavigationHistoryItem(controller);
			_navigationHistory.addElement(_currentHistoryItem);
		}
		else
		{
			NavigationHistoryItem newItem = new NavigationHistoryItem(controller, _currentHistoryItem);
			_navigationHistory.addElement(newItem);
			_currentHistoryItem.setNext(newItem);
			_currentHistoryItem = newItem;
		}
	}

	/**
	 * The inHistory method checks if a Controller is already in the
	 * history. 
	 * 
	 * @param controller The Controller that is checked for
	 * @return Return true if already in the history, otherwise false.
	 */
	private boolean inHistory(Controller controller)
	{
		int historySize = _navigationHistory.size();
		for (int i = historySize -1; i >= 0; --i)
		{
			if (((NavigationHistoryItem) _navigationHistory.elementAt(i)).getController() == controller)
				return true;
		}
		return false;
	}

	/**
	 * The removeController method removes a controller from the 
	 * history if that Controller is present in the history.
	 * 
	 * @param controller The Controller to be removed
	 * @return True if the Controller was removed, false if not
	 */
	public boolean removeController(Controller controller)
	{
		// Check if the Controller is in the history
		if (inHistory(controller))
		{
			remove(controller);
			return true;
		}
		return false;
	}

	/**
	 * The remove method is used internally to remove a Controller
	 * from the history. 
	 * 
	 * @param controller The Controller to be removed
	 */
	private void remove(Controller controller)
	{
		int historySize = _navigationHistory.size();
		for (int i = historySize - 1; i >= 0; --i)
		{
			NavigationHistoryItem item = (NavigationHistoryItem) _navigationHistory.elementAt(i);
			if (item.getController() == controller)
			{
				remove(item);
				return;
			}
		}
	}

	/**
	 * The remove method is used internally to remove a 
	 * history item and to patch up its previous and next
	 * references.
	 * 
	 * @param historyItem The NavigationHistoryItem to be removed
	 */
	private void remove(NavigationHistoryItem historyItem)
	{
		if (historyItem.getPrevious() != null)
			historyItem.getPrevious().setNext(historyItem.getNext());
		if (historyItem.getNext() != null)
			historyItem.getNext().setPrevious(historyItem.getPrevious());
		_navigationHistory.removeElement(historyItem);
		if (_currentHistoryItem == historyItem)
			_currentHistoryItem = null;
	}

	/**
	 * The clear method clears the NavigationHistory
	 * 
	 */
	public void clear()
	{
		this._navigationHistory.removeAllElements();
	}

	/**
	 * The getCurrent accessor method returns the 
	 * current Controller
	 * 
	 * @return The most recent Controller in the NavigationHistory
	 */
	public Controller getCurrent()
	{
		// Check if the current history item is set
		if (_currentHistoryItem != null)
			return _currentHistoryItem.getController();
		return null;
	}

	/**
	 * The getBeforeCurrent accessor method is used in special
	 * cases where one needs to know the Controller before the 
	 * current. 
	 * 
	 * @return The Controller before the current Controller
	 */
	public Controller getBeforeCurrent()
	{
		// Check if the current history item is set and if so if it has a 
		// previous item linked to it
		if (_currentHistoryItem != null)
			if (_currentHistoryItem.getPrevious() != null)
				return _currentHistoryItem.getPrevious().getController();
		return null;
	}

	/**
	 * The canGoBack method checks if the current Controller in the history
	 * has a previous Controller referenced
	 * 
	 * @return True if a previous Controller exists, false otherwise
	 */
	public boolean canGoBack()
	{
		return _currentHistoryItem.getPrevious() != null;
	}

	/**
	 * The goBack method moves the History one item back. 
	 * 
	 */
	public void goBack()
	{
		NavigationHistoryItem item = _currentHistoryItem.getPrevious();
		if (item != null)
		{
			remove(_currentHistoryItem);
			_currentHistoryItem = item;
		}
	}
}

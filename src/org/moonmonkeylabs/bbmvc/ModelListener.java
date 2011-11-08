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
 * The ModelListener interface is used to receive notification
 * from the Model. 
 * 
 * @author Thorben Primke
 * @version 1.0
 */
public interface ModelListener
{
	/**
	 * Invoked when an event about a Model change is sent. 
	 * 
	 * @param key A numerical key identifying the event
	 * @param args The data that is passed to the subscriber
	 */
	abstract void modelChanged(int key, Object[] args);
	 
}

/* PopupEnableWindowType.java
 * 
 * Programmed By:
 * 	   Vera Lukman
 *     
 * Change Log:
 *     2009-June-26, VL
 *         Initial write to clean up redundant code.
 *         
 * Known Issues:
 *     None
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package model.enumerations;

/**
 * Holds all popup windows that still enable the calling frame but do not allow
 * the user to call multiple windows of each of the popup window type. Provides a
 * standardized means of referencing them in a way that's easy for programmers
 * to understand.
 */
public enum PopupEnableWindowType {
	
	// Section
    // I - Enumerated Types
	// ABOUT is about ParrotIM window
	// CHATLOG is the chatlog window
    ABOUT, CHATLOG;

}

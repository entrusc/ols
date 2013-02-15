/*
 * OpenBench LogicSniffer / SUMP project
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at
 * your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA 02110, USA
 *
 * 
 * Copyright (C) 2010-2011 - J.W. Janssen, http://www.lxtreme.nl
 */
package nl.lxtreme.ols.client.ui.action;


import static nl.lxtreme.ols.client.ui.icons.IconFactory.*;

import java.awt.event.*;

import javax.swing.*;

import nl.lxtreme.ols.client.ui.*;
import nl.lxtreme.ols.client.ui.icons.*;
import nl.lxtreme.ols.client.ui.signaldisplay.*;
import nl.lxtreme.ols.common.*;
import nl.lxtreme.ols.common.acquisition.*;
import nl.lxtreme.ols.util.swing.*;


/**
 * Generic action to go to a specific set cursor in the diagram.
 * 
 * @author stefant
 */
public class GotoNthCursorAction extends AbstractAction implements IManagedAction
{
  // CONSTANTS

  private static final long serialVersionUID = 1L;

  private static final String ID_PREFIX = "GotoCursor";

  // VARIABLES

  private final int index;

  // METHODS

  /**
   * Creates a new {@link GotoNthCursorAction} instance.
   * 
   * @param aIndex
   *          the (zero-based) index of the cursor.
   */
  public GotoNthCursorAction( final int aIndex )
  {
    this.index = aIndex;

    final String cursorStr = String.valueOf( aIndex + 1 );

    putValue( NAME, "Go to cursor " + cursorStr );
    putValue( SHORT_DESCRIPTION, "Go to the " + getOrdinalNumber( aIndex + 1 ) + " cursor in the diagram" );
    putValue( Action.LARGE_ICON_KEY, createOverlayIcon( IconLocator.ICON_GOTO_CURSOR, cursorStr ) );

    int keyStroke = KeyEvent.VK_0 + ( ( aIndex + 1 ) % Ols.MAX_CURSORS );
    if ( keyStroke != KeyEvent.VK_0 )
    {
      // Avoid overwriting CTRL/CMD + 0 as accelerator...
      int mask = SwingComponentUtils.getMenuShortcutKeyMask();
      putValue( ACCELERATOR_KEY, SwingComponentUtils.createKeyMask( keyStroke, mask ) );
    }

    putValue( MNEMONIC_KEY, Integer.valueOf( keyStroke ) );
  }

  // METHODS

  /**
   * Creates an ID for the action to go the cursor with the given index.
   * 
   * @param aCursorIdx
   *          the cursor index to get the action-ID for, >= 0 && <
   *          {@value Ols#MAX_CURSORS}.
   * @return the action ID for the action, never <code>null</code>.
   */
  public static String getID( final int aCursorIdx )
  {
    if ( ( aCursorIdx < 0 ) || ( aCursorIdx >= Ols.MAX_CURSORS ) )
    {
      throw new IllegalArgumentException( "Invalid cursor index, should be between 0 and " + Ols.MAX_CURSORS );
    }
    return ID_PREFIX + aCursorIdx;
  }

  /**
   * Returns the ordinal representation (in English) of the given value.
   * 
   * @param aValue
   *          the value to get the ordinal value for, >= 0 && < 40.
   * @return a ordinal number representation of the given value, like "1st".
   */
  private static String getOrdinalNumber( final int aValue )
  {
    String suffix = "";
    if ( ( aValue == 1 ) || ( aValue == 21 ) || ( aValue == 31 ) )
    {
      suffix = "st";
    }
    else if ( ( aValue == 2 ) || ( aValue == 22 ) )
    {
      suffix = "nd";
    }
    else if ( ( aValue == 3 ) || ( aValue == 23 ) )
    {
      suffix = "rd";
    }
    else if ( ( aValue >= 0 ) && ( aValue < 40 ) )
    {
      suffix = "th";
    }
    return String.format( "%d%s", Integer.valueOf( aValue ), suffix );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void actionPerformed( final ActionEvent aEvent )
  {
    final Cursor[] definedCursors = getCursorController().getDefinedCursors();
    for ( Cursor cursor : definedCursors )
    {
      if ( ( this.index == cursor.getIndex() ) && cursor.isDefined() )
      {
        Long timestamp = Long.valueOf( cursor.getTimestamp() );
        getSignalDiagramController().scrollToTimestamp( timestamp );
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId()
  {
    return getID( this.index );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateState()
  {
    final Cursor cursor = getCursorController().getCursor( this.index );
    setEnabled( ( cursor != null ) && cursor.isDefined() );
  }

  /**
   * @return a {@link CursorController} instance, never <code>null</code>.
   */
  private CursorController getCursorController()
  {
    return Client.getInstance().getCursorController();
  }

  /**
   * @return a {@link SignalDiagramController} instance, never <code>null</code>
   *         .
   */
  private SignalDiagramController getSignalDiagramController()
  {
    return Client.getInstance().getSignalDiagramController();
  }
}
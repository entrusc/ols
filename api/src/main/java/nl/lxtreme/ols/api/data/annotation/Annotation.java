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
 * Copyright (C) 2006-2010 Michael Poppitz, www.sump.org
 * Copyright (C) 2010 J.W. Janssen, www.lxtreme.nl
 */
package nl.lxtreme.ols.api.data.annotation;


/**
 * Denotes an annotation.
 */
public interface Annotation<ANNOTATION_TYPE> extends Comparable<Annotation<ANNOTATION_TYPE>>
{
  // METHODS

  /**
   * Returns the actual annotation.
   * <p>
   * The given annotation type is generic, meaning it can be of <em>any</em>
   * type. One constraint that <b>all</b> annotation types must adhere is that
   * they have a proper {@link Object#toString()} implementation.
   * </p>
   * 
   * @return an annotation, never <code>null</code>.
   */
  ANNOTATION_TYPE getAnnotation();

  /**
   * Returns the channel index of the channel to annotate.
   * 
   * @return a channel index, >= 0 && < 32.
   */
  int getChannel();

}

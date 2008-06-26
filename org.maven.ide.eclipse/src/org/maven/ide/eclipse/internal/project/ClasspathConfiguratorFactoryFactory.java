/*******************************************************************************
 * Copyright (c) 2008 Sonatype, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.maven.ide.eclipse.internal.project;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.maven.ide.eclipse.internal.ExtensionReader;
import org.maven.ide.eclipse.project.configurator.AbstractClasspathConfiguratorFactory;

/**
 * ClasspathConfiguratorFactoryFactory
 *
 * @author igor
 */
public class ClasspathConfiguratorFactoryFactory {

  private static Set<AbstractClasspathConfiguratorFactory> factories;

  public static synchronized Set<AbstractClasspathConfiguratorFactory> getFactories() {
    if(factories==null) {
      Set<AbstractClasspathConfiguratorFactory> tmp = new TreeSet<AbstractClasspathConfiguratorFactory>(new FactoryComparator());
      tmp.addAll(ExtensionReader.readClasspathConfiguratorFactoryExtensions());
      factories = Collections.unmodifiableSet(tmp);
    }
    return factories;
  }

  static class FactoryComparator implements Comparator<AbstractClasspathConfiguratorFactory> {

    public int compare(AbstractClasspathConfiguratorFactory c1, AbstractClasspathConfiguratorFactory c2) {
      int res = c1.getPriority() - c2.getPriority();
      return res==0 ? c1.getId().compareTo(c2.getId()) : res;
    }
    
  }
}

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.sliew.scaleph.plugin.framework.core;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

public class PluginSPILoader<C extends Plugin> {

    private final Class<C> clazz;

    private volatile Map<PluginInfo, C> services = Collections.emptyMap();
    private volatile Set<PluginInfo> pluginInfos = Collections.emptySet();

    public PluginSPILoader(Class<C> clazz, ClassLoader classLoader) {
        this.clazz = clazz;
        final ClassLoader clazzClassloader = clazz.getClassLoader();
        if (classLoader == null) {
            classLoader = clazzClassloader;
        }
        load(classLoader);
    }

    public static <T extends Plugin> T newInstance(Class<T> clazz, Map<String, String> args) {
        try {
            return clazz.getConstructor(Map.class).newInstance(args);
        } catch (InvocationTargetException ite) {
            final Throwable cause = ite.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            if (cause instanceof Error) {
                throw (Error) cause;
            }
            throw new RuntimeException(
                "Unexpected checked exception while calling constructor of " + clazz.getName(),
                cause);
        } catch (ReflectiveOperationException e) {
            throw new UnsupportedOperationException(
                "Plugin "
                    + clazz.getName()
                    +
                    " cannot be instantiated. This is likely due to missing Map<String,String> constructor.",
                e);
        }
    }

    public void load(ClassLoader classLoader) {
        Objects.requireNonNull(classLoader, "classLoader must not be null");
        final LinkedHashMap<PluginInfo, C> services = new LinkedHashMap<>(this.services);
        final LinkedHashSet<PluginInfo> pluginInfos = new LinkedHashSet<>(this.pluginInfos);

        final Spliterator<C> spliterator = ServiceLoader.load(clazz, classLoader).spliterator();
        StreamSupport.stream(spliterator, false)
            .forEachOrdered(
                service -> {
                    final PluginInfo pluginInfo = service.getPluginInfo();
                    if (!services.containsKey(pluginInfo)) {
                        services.put(pluginInfo, service);
                        pluginInfos.add(pluginInfo);
                    }
                });
        this.services = new HashMap<>(services);
        this.pluginInfos = new HashSet<>(pluginInfos);
    }

    public Set<PluginInfo> availableServices() {
        return pluginInfos;
    }

    public Map<PluginInfo, C> getServices() {
        return Collections.unmodifiableMap(services);
    }

    public Optional<C> getPlugin(PluginInfo pluginInfo) {
        return Optional.ofNullable(services.get(pluginInfo));
    }


}

/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.frontend.taglib.clay.servlet.taglib.soy.base;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.taglib.clay.internal.js.loader.modules.extender.npm.NPMResolverProvider;
import com.liferay.frontend.taglib.soy.servlet.taglib.TemplateRendererTag;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Chema Balsas
 */
public abstract class BaseClayTag extends TemplateRendererTag {

	public BaseClayTag(String moduleBaseName, String componentBaseName) {
		this(moduleBaseName, componentBaseName, false);
	}

	public BaseClayTag(
		String moduleBaseName, String componentBaseName, boolean hydrate) {

		_moduleBaseName = moduleBaseName;
		_componentBaseName = componentBaseName;
		_hydrate = hydrate;
	}

	@Override
	public int doStartTag() {
		setHydrate(_hydrate);
		setTemplateNamespace(_componentBaseName + ".render");

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		NPMResolver npmResolver = NPMResolverProvider.getNPMResolver();

		if (npmResolver == null) {
			return StringPool.BLANK;
		}

		return npmResolver.resolveModuleName(
			StringBundler.concat(
				"clay-", _moduleBaseName, "/lib/", _componentBaseName));
	}

	private final String _componentBaseName;
	private final boolean _hydrate;
	private final String _moduleBaseName;

}
<%--
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
--%>

<%@ include file="/init.jsp" %>

<liferay-ui:tabs
	names="Alerts,Badges,Labels"
	refresh="<%= false %>"
	type="tabs nav-tabs-default"
>
	<liferay-ui:section>
		<div class="container-fluid-1280">
			<%@ include file="/partials/alerts.jsp" %>
		</div>
    </liferay-ui:section>

	<liferay-ui:section>
        <div class="container-fluid-1280">
            <%@ include file="/partials/badges.jsp" %>
        </div>
    </liferay-ui:section>

	<liferay-ui:section>
        <div class="container-fluid-1280">
            <%@ include file="/partials/labels.jsp" %>
        </div>
    </liferay-ui:section>
</liferay-ui:tabs>
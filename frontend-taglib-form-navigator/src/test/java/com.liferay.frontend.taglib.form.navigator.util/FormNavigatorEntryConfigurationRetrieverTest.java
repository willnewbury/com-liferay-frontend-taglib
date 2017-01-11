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

package com.liferay.frontend.taglib.form.navigator.util;

import com.liferay.frontend.taglib.form.navigator.configuration.FormNavigatorConfiguration;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Alejandro Tardín
 */
@RunWith(Enclosed.class)
public class FormNavigatorEntryConfigurationRetrieverTest {

	public static class WhenAConfigurationEntryHasSeveralLines {

		@Before
		public void setUp() throws InvalidSyntaxException, IOException {
			StringBuilder line1 = new StringBuilder();

			line1.append("add.general");
			line1.append(StringPool.EQUAL);
			line1.append("formNavigatorEntryKey1,");
			line1.append("formNavigatorEntryKey2,");
			line1.append("formNavigatorEntryKey3");

			StringBuilder line2 = new StringBuilder();

			line2.append("update.general");
			line2.append(StringPool.EQUAL);
			line2.append("formNavigatorEntryKey1,");
			line2.append("formNavigatorEntryKey4,");
			line2.append("formNavigatorEntryKey5");

			String config = line1.toString() + "\n" + line2.toString();

			_setMockConfigurations(
				_createMockConfig("form1", new String[] {config}));
		}

		@Test
		public void testContainsValuesForLine1() {
			List<String> formNavigatorEntryKeys =
				_formNavigatorEntryConfigurationHelper.
					getFormNavigatorEntryKeys("form1", "general", "add");

			Assert.assertEquals(3, formNavigatorEntryKeys.size());
			Assert.assertEquals(
				"formNavigatorEntryKey1", formNavigatorEntryKeys.get(0));
			Assert.assertEquals(
				"formNavigatorEntryKey2", formNavigatorEntryKeys.get(1));
			Assert.assertEquals(
				"formNavigatorEntryKey3", formNavigatorEntryKeys.get(2));
		}

		@Test
		public void testContainsValuesForLine2() {
			List<String> formNavigatorEntryKeys =
				_formNavigatorEntryConfigurationHelper.
					getFormNavigatorEntryKeys("form1", "general", "update");

			Assert.assertEquals(3, formNavigatorEntryKeys.size());
			Assert.assertEquals(
				"formNavigatorEntryKey1", formNavigatorEntryKeys.get(0));
			Assert.assertEquals(
				"formNavigatorEntryKey4", formNavigatorEntryKeys.get(1));
			Assert.assertEquals(
				"formNavigatorEntryKey5", formNavigatorEntryKeys.get(2));
		}

	}

	public static class WhenThereAreSeveralConfigurations {

		@Before
		public void setUp() throws InvalidSyntaxException, IOException {
			StringBuilder line1 = new StringBuilder();

			line1.append("add.general");
			line1.append(StringPool.EQUAL);
			line1.append("formNavigatorEntryKey1,");
			line1.append("formNavigatorEntryKey2,");
			line1.append("formNavigatorEntryKey3");

			StringBuilder line2 = new StringBuilder();

			line2.append("update.general");
			line2.append(StringPool.EQUAL);
			line2.append("formNavigatorEntryKey1,");
			line2.append("formNavigatorEntryKey4,");
			line2.append("formNavigatorEntryKey5");

			_setMockConfigurations(
				_createMockConfig("form1", new String[] {line1.toString()}),
				_createMockConfig("form1", new String[] {line2.toString()}));
		}

		@Test
		public void testContainsValuesForEntry1() {
			List<String> formNavigatorEntryKeys =
				_formNavigatorEntryConfigurationHelper.
					getFormNavigatorEntryKeys("form1", "general", "add");

			Assert.assertEquals(3, formNavigatorEntryKeys.size());
			Assert.assertEquals(
				"formNavigatorEntryKey1", formNavigatorEntryKeys.get(0));
			Assert.assertEquals(
				"formNavigatorEntryKey2", formNavigatorEntryKeys.get(1));
			Assert.assertEquals(
				"formNavigatorEntryKey3", formNavigatorEntryKeys.get(2));
		}

		@Test
		public void testContainsValuesForEntry2() {
			List<String> formNavigatorEntryKeys =
				_formNavigatorEntryConfigurationHelper.
					getFormNavigatorEntryKeys("form1", "general", "update");

			Assert.assertEquals(3, formNavigatorEntryKeys.size());
			Assert.assertEquals(
				"formNavigatorEntryKey1", formNavigatorEntryKeys.get(0));
			Assert.assertEquals(
				"formNavigatorEntryKey4", formNavigatorEntryKeys.get(1));
			Assert.assertEquals(
				"formNavigatorEntryKey5", formNavigatorEntryKeys.get(2));
		}

		@Test
		public void testReturnsEmptyListForAnUnknownCategory() {
			List<String> formNavigatorEntryKeys =
				_formNavigatorEntryConfigurationHelper.
					getFormNavigatorEntryKeys(
						"form1", "unknownCategory", "add");

			Assert.assertTrue(formNavigatorEntryKeys.isEmpty());
		}

		@Test
		public void testReturnsEmptyListForAnUnknownFormId() {
			List<String> formNavigatorEntryKeys =
				_formNavigatorEntryConfigurationHelper.
					getFormNavigatorEntryKeys("unknownForm", "general", "add");

			Assert.assertTrue(formNavigatorEntryKeys.isEmpty());
		}

		@Test
		public void testReturnsEmptyListForAnUnknownVariant() {
			List<String> formNavigatorEntryKeys =
				_formNavigatorEntryConfigurationHelper.
					getFormNavigatorEntryKeys(
						"form1", "general", "unknownVariant");

			Assert.assertTrue(formNavigatorEntryKeys.isEmpty());
		}

	}

	public static class WhenThereIsNoConfig {

		@Before
		public void setUp() throws InvalidSyntaxException, IOException {
			_setMockConfigurations();
		}

		@Test
		public void testGetFormNavigatorEntriesReturnsAnEmptyList()
			throws InvalidSyntaxException, IOException {

			List<String> formNavigatorEntryKeys =
				_formNavigatorEntryConfigurationHelper.
					getFormNavigatorEntryKeys(
						"formNavigatorId", "categoryKey", "variant");

			Assert.assertTrue(formNavigatorEntryKeys.isEmpty());
		}

	}

	public static class WhenThereIsOneConfigurationWithTwoLinesForSameTarget {

		@Before
		public void setUp() throws InvalidSyntaxException, IOException {
			StringBuilder line1 = new StringBuilder();

			line1.append("add.general");
			line1.append(StringPool.EQUAL);
			line1.append("formNavigatorEntryKey1,");
			line1.append("formNavigatorEntryKey2,");
			line1.append("formNavigatorEntryKey3");

			StringBuilder line2 = new StringBuilder();

			line2.append("add.general");
			line2.append(StringPool.EQUAL);
			line2.append("formNavigatorEntryKey1,");
			line2.append("formNavigatorEntryKey4,");
			line2.append("formNavigatorEntryKey5");

			_setMockConfigurations(
				_createMockConfig(
					"form1",
					new String[] {line1.toString(), line2.toString()}));
		}

		@Test
		public void testTheLastOneHasPrecedence() {
			List<String> formNavigatorEntryKeys =
				_formNavigatorEntryConfigurationHelper.
					getFormNavigatorEntryKeys("form1", "general", "add");

			Assert.assertEquals(3, formNavigatorEntryKeys.size());
			Assert.assertEquals(
				"formNavigatorEntryKey1", formNavigatorEntryKeys.get(0));
			Assert.assertEquals(
				"formNavigatorEntryKey4", formNavigatorEntryKeys.get(1));
			Assert.assertEquals(
				"formNavigatorEntryKey5", formNavigatorEntryKeys.get(2));
		}

	}

	private static Configuration _createMockConfig(
		String formNavigatorId, String[] hiddenFormNavigatorEntryQueries) {

		Configuration mock = Mockito.mock(Configuration.class);
		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("formNavigatorId", formNavigatorId);
		properties.put(
			"hiddenFormNavigatorEntryQueries", hiddenFormNavigatorEntryQueries);

		Mockito.when(mock.getProperties()).thenReturn(properties);

		return mock;
	}

	private static void _setMockConfigurations(Configuration... configurations)
		throws InvalidSyntaxException, IOException {

		Mockito.when(
			_configurationAdmin.listConfigurations(
				"(service.factoryPid=" +
					FormNavigatorConfiguration.class.getName() +
						")")).thenReturn(configurations);

		_formNavigatorEntryConfigurationHelper.setConfigurationAdmin(
			_configurationAdmin);
	}

	private static final ConfigurationAdmin _configurationAdmin = Mockito.mock(
		ConfigurationAdmin.class);
	private static final FormNavigatorEntryConfigurationRetriever
		_formNavigatorEntryConfigurationHelper =
			new FormNavigatorEntryConfigurationRetriever();

}
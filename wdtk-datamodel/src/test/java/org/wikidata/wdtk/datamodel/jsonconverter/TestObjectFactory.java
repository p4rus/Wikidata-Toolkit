package org.wikidata.wdtk.datamodel.jsonconverter;

/*
 * #%L
 * Wikidata Toolkit Data Model
 * %%
 * Copyright (C) 2014 Wikidata Toolkit Developers
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.wikidata.wdtk.datamodel.implementation.DataObjectFactoryImpl;
import org.wikidata.wdtk.datamodel.interfaces.Claim;
import org.wikidata.wdtk.datamodel.interfaces.DataObjectFactory;
import org.wikidata.wdtk.datamodel.interfaces.DatatypeIdValue;
import org.wikidata.wdtk.datamodel.interfaces.GlobeCoordinatesValue;
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.datamodel.interfaces.MonolingualTextValue;
import org.wikidata.wdtk.datamodel.interfaces.PropertyDocument;
import org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue;
import org.wikidata.wdtk.datamodel.interfaces.Reference;
import org.wikidata.wdtk.datamodel.interfaces.SiteLink;
import org.wikidata.wdtk.datamodel.interfaces.Snak;
import org.wikidata.wdtk.datamodel.interfaces.SomeValueSnak;
import org.wikidata.wdtk.datamodel.interfaces.Statement;
import org.wikidata.wdtk.datamodel.interfaces.StatementGroup;
import org.wikidata.wdtk.datamodel.interfaces.StatementRank;
import org.wikidata.wdtk.datamodel.interfaces.ValueSnak;

/**
 * This class provides functions to create objects from
 * {@link org.wikidata.wdtk.datamodel.interfaces} with certain predefined
 * parameters.
 * 
 * @author Michael Günther, Fredo Erxleben
 * 
 */
public class TestObjectFactory {

	private DataObjectFactory factory = new DataObjectFactoryImpl();
	private static String baseIri = "test";

	PropertyDocument createEmptyPropertyDocument() {

		PropertyIdValue propertyId = this.factory.getPropertyIdValue("P1",
				baseIri);
		List<MonolingualTextValue> labels = new LinkedList<>();
		List<MonolingualTextValue> descriptions = new LinkedList<>();
		List<MonolingualTextValue> aliases = new LinkedList<>();
		DatatypeIdValue datatypeId = this.factory
				.getDatatypeIdValue("globe-coordinate");
		PropertyDocument document = this.factory.getPropertyDocument(
				propertyId, labels, descriptions, aliases, datatypeId);
		return document;
	}

	Statement createStatement(String qId, String pId) {
		return factory.getStatement(
				createClaim(qId, createValueSnakStringValue(pId)),
				createReferences(), StatementRank.NORMAL, "id111");
	}

	StatementGroup createStatementGroup() {
		final String pId = "P122";
		final String qId = "Q10";
		List<Statement> statements = new ArrayList<Statement>();
		statements.add(createStatement(qId, pId));
		statements.add(factory.getStatement(
				createClaim(qId, createValueSnakQuantityValue(pId)),
				Collections.<Reference> emptyList(), StatementRank.NORMAL,
				"id112"));
		return factory.getStatementGroup(statements);
	}

	List<MonolingualTextValue> createLabels() {
		List<MonolingualTextValue> result = new LinkedList<>();
		result.add(factory.getMonolingualTextValue("foo", "lc"));
		result.add(factory.getMonolingualTextValue("bar", "lc2"));
		return result;
	}

	List<MonolingualTextValue> createAliases() {
		List<MonolingualTextValue> result = new LinkedList<>();
		result.add(factory.getMonolingualTextValue("foo", "lc"));
		result.add(factory.getMonolingualTextValue("bar", "lc"));
		return result;
	}

	List<MonolingualTextValue> createDescriptions() {
		List<MonolingualTextValue> result = new LinkedList<>();
		result.add(factory.getMonolingualTextValue("it's foo", "lc"));
		result.add(factory.getMonolingualTextValue("it's bar", "lc2"));
		return result;
	}

	Map<String, SiteLink> createSiteLinks() {
		Map<String, SiteLink> result = new HashMap<String, SiteLink>();
		result.put("enwiki", factory.getSiteLink("title_en", "siteKey",
				baseIri, new LinkedList<String>()));
		result.put("auwiki", factory.getSiteLink("title_au", "siteKey",
				baseIri, new LinkedList<String>()));
		return result;
	}

	List<? extends Snak> createQualifiers() {
		List<Snak> result = new ArrayList<Snak>();
		result.add(createValueSnakTimeValue(14, "P15"));
		return result;
	}

	List<? extends Reference> createReferences() {
		List<ValueSnak> snaks = new ArrayList<ValueSnak>();
		List<Reference> refs = new ArrayList<>();
		snaks.add(createValueSnakTimeValue(122, "P112"));
		refs.add(factory.getReference(snaks));
		return refs;
	}

	Reference createReference() {
		List<ValueSnak> snaks = new ArrayList<ValueSnak>();

		snaks.add(createValueSnakCoordinatesValue("P232"));
		snaks.add(createValueSnakQuantityValue("P211"));

		return factory.getReference(snaks);
	}

	Claim createClaim(String id, Snak snak) {
		return factory.getClaim(factory.getItemIdValue(id, baseIri), snak,
				Collections.<Snak> emptyList());
	}

	SomeValueSnak createSomeValueSnak(String pId) {
		return factory.getSomeValueSnak(factory
				.getPropertyIdValue(pId, baseIri));
	}

	ValueSnak createValueSnakItemIdValue(String pId, String qId) {
		return factory.getValueSnak(factory.getPropertyIdValue(pId, baseIri),
				factory.getItemIdValue(qId, baseIri));
	}

	ValueSnak createValueSnakStringValue(String pId) {
		return factory.getValueSnak(factory.getPropertyIdValue(pId, baseIri),
				factory.getStringValue("TestString"));
	}

	ValueSnak createValueSnakCoordinatesValue(String pId) {
		return factory.getValueSnak(factory.getPropertyIdValue(pId, baseIri),
				factory.getGlobeCoordinatesValue(213124, 21314,
						GlobeCoordinatesValue.PREC_ARCMINUTE,
						"http://www.wikidata.org/entity/Q2"));
	}

	ValueSnak createValueSnakQuantityValue(String pId) {
		return factory.getValueSnak(factory.getPropertyIdValue(pId, baseIri),
				factory.getQuantityValue(new BigDecimal(3), new BigDecimal(3),
						new BigDecimal(3)));
	}

	ValueSnak createValueSnakTimeValue(int random, String pId) {
		return factory.getValueSnak(factory.getPropertyIdValue(pId, baseIri),
				factory.getTimeValue(306, (byte) 11, (byte) 3, (byte) 13,
						(byte) 7, (byte) 6, (byte) 32, 17, 43, 0,
						"http://www.wikidata.org/entity/Q1985727"));
	}

	PropertyIdValue createPropertyIdValue(String id) {
		return factory.getPropertyIdValue(id, baseIri);
	}

	ItemIdValue createItemIdValue(String id) {
		return factory.getItemIdValue(id, baseIri);
	}

}

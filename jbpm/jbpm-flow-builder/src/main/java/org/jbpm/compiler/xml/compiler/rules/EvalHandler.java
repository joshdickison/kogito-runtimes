/*
 * Copyright 2010 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jbpm.compiler.xml.compiler.rules;

import org.drools.drl.ast.descr.ConditionalElementDescr;
import org.drools.drl.ast.descr.EvalDescr;
import org.jbpm.compiler.xml.Handler;
import org.jbpm.compiler.xml.Parser;
import org.jbpm.compiler.xml.core.BaseAbstractHandler;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class EvalHandler extends BaseAbstractHandler
        implements
        Handler {
    public EvalHandler() {
    }

    public Object start(final String uri,
            final String localName,
            final Attributes attrs,
            final Parser parser) throws SAXException {
        parser.startElementBuilder(localName,
                attrs);

        final EvalDescr evalDescr = new EvalDescr();

        return evalDescr;
    }

    public Object end(final String uri,
            final String localName,
            final Parser parser) throws SAXException {
        final Element element = parser.endElementBuilder();

        final EvalDescr evalDescr = (EvalDescr) parser.getCurrent();

        final String expression = ((org.w3c.dom.Text) element.getChildNodes().item(0)).getWholeText();

        emptyContentCheck(localName, expression, parser);

        evalDescr.setContent(expression);

        final ConditionalElementDescr parentDescr = (ConditionalElementDescr) parser.getParent();
        parentDescr.addDescr(evalDescr);

        return evalDescr;
    }

    public Class generateNodeFor() {
        return EvalDescr.class;
    }
}

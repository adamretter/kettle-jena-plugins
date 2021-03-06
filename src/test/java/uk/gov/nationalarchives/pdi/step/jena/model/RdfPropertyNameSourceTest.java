/*
 * The MIT License
 * Copyright © 2020 The National Archives
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package uk.gov.nationalarchives.pdi.step.jena.model;

import org.junit.jupiter.api.Test;
import uk.gov.nationalarchives.pdi.step.jena.model.JenaModelStepMeta.RdfPropertyNameSource;

import static org.junit.jupiter.api.Assertions.*;
import static uk.gov.nationalarchives.pdi.step.jena.Util.Entry;
import static uk.gov.nationalarchives.pdi.step.jena.Util.Map;

public class RdfPropertyNameSourceTest {

    @Test
    public void fromString() {
        assertNull(RdfPropertyNameSource.fromString(null, null));
        assertNull(RdfPropertyNameSource.fromString(null, ""));

        RdfPropertyNameSource source = RdfPropertyNameSource.fromString(null, "local-part");
        assertTrue(source instanceof JenaModelStepMeta.RdfPropertyNameLiteralSource);
        assertEquals(JenaModelStepMeta.SourceType.LITERAL, source.getSourceType());
        assertEquals("local-part", source.toString());

        source = RdfPropertyNameSource.fromString(Map(Entry("ns", "http://ns")), "ns:local-part");
        assertTrue(source instanceof JenaModelStepMeta.RdfPropertyNameLiteralSource);
        assertEquals(JenaModelStepMeta.SourceType.LITERAL, source.getSourceType());
        assertEquals("ns:local-part", source.toString());

        source = RdfPropertyNameSource.fromString(null, "#{field1}");
        assertTrue(source instanceof JenaModelStepMeta.RdfPropertyNameFieldSource);
        assertEquals(JenaModelStepMeta.SourceType.FIELD, source.getSourceType());
        assertEquals("#{field1}", source.toString());
        assertEquals("field1", ((JenaModelStepMeta.RdfPropertyNameFieldSource)source).getFieldName());

        source = RdfPropertyNameSource.fromString(null, "${var1}");
        assertTrue(source instanceof JenaModelStepMeta.RdfPropertyNameVariableSource);
        assertEquals(JenaModelStepMeta.SourceType.VARIABLE, source.getSourceType());
        assertEquals("${var1}", source.toString());
        assertEquals("var1", ((JenaModelStepMeta.RdfPropertyNameVariableSource)source).getVariableName());
    }
}

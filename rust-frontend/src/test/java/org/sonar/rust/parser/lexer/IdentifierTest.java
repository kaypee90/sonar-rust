/**
 *
 * Sonar Rust Plugin (Community)
 * Copyright (C) 2020 Eric Le Goff
 * http://github.com/elegoff/sonar-rust
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.rust.parser.lexer;

import org.junit.Test;
import org.sonar.rust.RustGrammar;
import org.sonar.sslr.tests.Assertions;

public class IdentifierTest {
    @Test
    public void checkidentifierOrKeyword() {
        Assertions.assertThat(RustGrammar.create().build().rule(RustGrammar.IDENTIFIER_OR_KEYWORD))
                .matches("a")
                .matches("abc")
                .matches("A")
                .matches("AbCD")
                .matches("U123")
                .matches("_a")
                .matches("_56")
                .matches("_AbC")
                .notMatches("_")
                .notMatches("42")
                .notMatches("(")
                .notMatches("a b")
                .notMatches("a self")
                .notMatches("\"hello\")")
                .matches("crate_type")

        ;
    }

    @Test
    public void checkRawIdentifier() {
        Assertions.assertThat(RustGrammar.create().build().rule(RustGrammar.RAW_IDENTIFIER))
                .notMatches("r#")
                .matches("r#a")
                .matches("r#_52")
                .matches("r#V123")
                .notMatches("s#52")
                //corner cases
                .notMatches("r#crate")
                .notMatches("r#self")
                .notMatches("r#super")
                .notMatches("r#Self");
    }

    @Test
    public void testNonKeywords() {
        Assertions.assertThat(RustGrammar.create().build().rule(RustGrammar.NON_KEYWORD_IDENTIFIER))
                .matches("a")
                .matches("bc")
                .matches("Abc")
                .notMatches("as")
                .notMatches("trait") //keyword
                .matches("prefix_trait")
                .matches("traitsuffix")
                .matches("foo_bar")
                .notMatches("a b")
                .notMatches("a self")
        ;
    }

    @Test
    public void testIdentifier() {
        Assertions.assertThat(RustGrammar.create().build().rule(RustGrammar.IDENTIFIER))
                .matches("a")
                .matches("bc")
                .matches("Abc")
                .notMatches("as")
                .notMatches("trait")
                .notMatches("foo ")
                .notMatches("r#")
                .matches("r#a")
                .matches("r#_52")
                .matches("r#V123")
                .notMatches("s#52")
                .matches("phenotype")
                .matches("crate_type")

        ;

    }
}

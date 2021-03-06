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
package org.sonar.rust.parser.statements;

import org.junit.Test;
import org.sonar.rust.RustGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class StatementTest {

    @Test
    public void testLetStatement() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.LET_STATEMENT))
                .matches("let x;")
                .matches("let x ;")
                .matches("let y=42;")
                .matches("#[test]\n" +
                        "let y:i32=42;")
        ;
    }


    @Test
    public void testStatement() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.STATEMENT))
                .matches(";")
                .matches("extern crate pcre;")
                .matches("let y=42;")
        ;
    }
}

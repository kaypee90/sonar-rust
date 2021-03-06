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
package org.sonar.rust.parser;

import org.junit.Test;
import org.sonar.rust.RustGrammar;
import org.sonar.sslr.parser.LexerlessGrammar;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class RustGrammarTest {
    @Test
    public void matchingEmpty() {
        LexerlessGrammar g = RustGrammar.create().build();
        RustGrammar[] rustGrammars = RustGrammar.values();

        Set<RustGrammar> couldMatch = new HashSet<RustGrammar>(Arrays.asList(
                RustGrammar.CALL_PARAMS_TERM,
                RustGrammar.COMPILATION_UNIT,
                RustGrammar.EOF,
                RustGrammar.FUNCTION_QUALIFIERS,
                RustGrammar.GENERIC_PARAMS,
                RustGrammar.LIFETIME_BOUNDS,
                RustGrammar.LIFETIME_PARAMS,
                RustGrammar.SPC,
                RustGrammar.TUPLE_STRUCT_ITEMS,
                RustGrammar.TYPE_PARAMS
        ));

        for (RustGrammar r : rustGrammars) {
                if (couldMatch.contains(r)) {
                    assertThat(RustGrammar.create().build().rule(r))
                            .matches("");
                } else {
                    assertThat(RustGrammar.create().build().rule(r))
                            .notMatches("");
                }



        }

    }


}

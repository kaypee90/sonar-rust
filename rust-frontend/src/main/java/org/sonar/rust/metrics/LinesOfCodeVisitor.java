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
package org.sonar.rust.metrics;

import com.google.common.collect.ImmutableSet;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import org.sonar.rust.RustGrammar;
import org.sonar.rust.RustVisitor;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.parser.ParserAdapter;

import java.util.HashSet;
import java.util.Set;

public class LinesOfCodeVisitor extends RustVisitor {

    private final ParserAdapter<LexerlessGrammar> lexer;
    private final Set<Integer> linesOfCode = new HashSet<>();

    public LinesOfCodeVisitor(ParserAdapter lexer) {
        this.lexer = lexer;
    }

    @Override
    public void visitFile(AstNode node) {
        linesOfCode.clear();
        for (AstNode token : lexer.parse(getContext().file().content()).getChildren(RustGrammar.ANY_TOKEN)) {
            String[] tokenLines = token.getTokenValue().split("(\r)?\n|\r", -1);

            for (int lineOffset = 0; lineOffset < tokenLines.length; lineOffset++) {
                linesOfCode.add(token.getTokenLine() + lineOffset);
            }
        }
    }

    public Set<Integer> linesOfCode() {
        return ImmutableSet.copyOf(linesOfCode);
    }
}

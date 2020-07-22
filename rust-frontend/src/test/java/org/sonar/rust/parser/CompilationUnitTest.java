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

import static org.sonar.sslr.tests.Assertions.assertThat;

public class CompilationUnitTest {

    @Test
    public void testCompilationUnit() {
        assertThat(RustGrammar.create().build().rule(RustGrammar.COMPILATION_UNIT))
                .matches("fn main() {\n" +
                        "    println!(\"Hello, world!\");\n" +
                        "}")
                .matches("/* comment */ fn main() {\n" +
                        "    println!(\"Hello, world!\");\n" +
                        "}")
                .matches(" fn main() {\n" +
                        " /* comment */\n" +
                        "    println!(\"Hello, world!\");\n" +
                        "}")
                .matches(" fn main() {\n" +
                        " // line comment \n" +
                        "    println!(\"Hello, world!\");\n" +
                        "}")
                /* FIXME
                .matches("fn main() {\n" +
                        "    println!(\"Checking issues\");\n" +
                        "    absurd_extreme_comparison();\n" +
                        "    println!(\"Done\");\n" +
                        "    as_conversions();\n" +
                        "}\n" +
                        "\n" +
                        "fn absurd_extreme_comparison(){\n" +
                        "    let vec: Vec<isize> = Vec::new();\n" +
                        "    if vec.len() <= 0 {}\n" +
                        "    if 100 > std::i32::MAX {}\n" +
                        "}\n" +
                        "\n" +
                        "fn as_conversions(){\n" +
                        "    let i = 0u32 as u64;\n" +
                        "\n" +
                        "    let _j = &i as *const u64 as *mut u64;\n" +
                        "}")

                 */


        ;
    }
}

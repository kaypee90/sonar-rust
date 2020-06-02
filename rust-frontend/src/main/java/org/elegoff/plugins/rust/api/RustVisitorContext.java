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
package org.elegoff.plugins.rust.api;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elegoff.plugins.rust.api.RustCheck.PreciseIssue;
import com.sonar.sslr.api.RecognitionException;
import org.elegoff.rust.semantic.SymbolTableBuilder;
import org.elegoff.plugins.rust.api.symbols.Symbol;
import org.elegoff.plugins.rust.api.tree.FileInput;



public class RustVisitorContext {

private final FileInput rootTree;
private final RustFile rustFile;
private File workingDirectory = null;
private final RecognitionException parsingException;
private List<RustCheck.PreciseIssue> issues = new ArrayList<>();


public RustVisitorContext(FileInput rootTree, RustFile rustFile, @Nullable File workingDirectory, @Nullable String packageName) {
        this.rootTree = rootTree;
        this.rustFile = rustFile;
        this.workingDirectory = workingDirectory;
        this.parsingException = null;
        SymbolTableBuilder symbolTableBuilder = packageName != null ? new SymbolTableBuilder(packageName, rustFile): new SymbolTableBuilder(rustFile);
        symbolTableBuilder.visitFileInput(rootTree);
        }

public RustVisitorContext(FileInput rootTree, RustFile rustFile, @Nullable File workingDirectory, String packageName, Map<String, Set<Symbol>> globalSymbols) {
        this.rootTree = rootTree;
        this.rustFile = rustFile;
        this.workingDirectory = workingDirectory;
        this.parsingException = null;
        new SymbolTableBuilder(packageName, rustFile, globalSymbols).visitFileInput(rootTree);
        }

public RustVisitorContext(RustFile rustFile, RecognitionException parsingException) {
        this.rootTree = null;
        this.rustFile = rustFile;
        this.parsingException = parsingException;
        }

public FileInput rootTree() {
        return rootTree;
        }

public RustFile pythonFile() {
        return rustFile;
        }

public RecognitionException parsingException() {
        return parsingException;
        }

public void addIssue(RustCheck.PreciseIssue issue) {
        issues.add(issue);
        }

public List<PreciseIssue> getIssues() {
        return issues;
        }

@CheckForNull
public File workingDirectory() {
        return workingDirectory;
        }
}
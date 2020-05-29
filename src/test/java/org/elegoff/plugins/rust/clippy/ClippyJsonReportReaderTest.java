/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.elegoff.plugins.rust.clippy;

import org.junit.Test;
import org.sonarsource.analyzer.commons.internal.json.simple.JSONArray;
import org.sonarsource.analyzer.commons.internal.json.simple.JSONObject;
import org.sonarsource.analyzer.commons.internal.json.simple.parser.JSONParser;
import org.sonarsource.analyzer.commons.internal.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fest.assertions.Assertions.assertThat;

public class ClippyJsonReportReaderTest {
    private final JSONParser jsonParser = new JSONParser();

    @Test
    public void noReportProvided(){
        assertThatThrownBy(() -> {
            InputStream in = ClippyJsonReportReader.toJSON(null);
        }).isInstanceOf(IOException.class);
    }

    @Test
    public void invalidReportPathProvided(){
        assertThatThrownBy(() -> {
            InputStream in = ClippyJsonReportReader.toJSON(new File("invalid.txt"));
        }).isInstanceOf(IOException.class);
    }

    @Test
    public void emptyReport() {
        File empty = this.getFileFromResources("org/elegoff/plugins/rust/clippy/empty-report.txt");
        InputStream in = null;
        try {
            in = ClippyJsonReportReader.toJSON(empty);
            assertThat(in).isNotNull();
            assertThat(isValidJsonReport(in)).isTrue();
            in = ClippyJsonReportReader.toJSON(empty);
            assertThat(issueCount(in)).isZero();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void validReport() {
        File report = this.getFileFromResources("org/elegoff/plugins/rust/clippy/myreport.txt");
        InputStream in = null;
        try {
            in = ClippyJsonReportReader.toJSON(report);
            assertThat(in).isNotNull();
            assertThat(isValidJsonReport(in)).isTrue();
            in = ClippyJsonReportReader.toJSON(report);
            assertThat(issueCount(in)).isEqualTo(2);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private File getFileFromResources(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }

    }

    private boolean isValidJsonReport(InputStream in){
        JSONObject rootObject = null;
        boolean res = false;
        try {
            rootObject = (JSONObject) jsonParser.parse(new InputStreamReader(in, UTF_8));

            JSONArray results = (JSONArray) rootObject.get("results");
            if (results != null) return true;

        } catch (ParseException |IOException e) {
            throw new IllegalStateException(e);
        }
        return res;
    }

    private int issueCount(InputStream in){
        JSONObject rootObject = null;
        int nbIssue = 0;
        try {
            rootObject = (JSONObject) jsonParser.parse(new InputStreamReader(in, UTF_8));

            JSONArray results = (JSONArray) rootObject.get("results");
            for (Object o: results){
                JSONObject jo = (JSONObject)o;
                JSONObject message = (JSONObject)jo.get("message");
                JSONArray spans = (JSONArray)message.get("spans");
                if ((spans != null)&& spans.size()>0){
                    nbIssue++;
                }

            }
        } catch (ParseException |IOException e) {
            throw new IllegalStateException(e);
        }
        return nbIssue;
    }

}
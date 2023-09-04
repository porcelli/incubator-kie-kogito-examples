/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
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
package org.acme;

import java.util.Map;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.kie.kogito.incubation.application.AppRoot;
import org.kie.kogito.incubation.common.DataContext;
import org.kie.kogito.incubation.common.MapDataContext;
import org.kie.kogito.incubation.predictions.PredictionIds;
import org.kie.kogito.incubation.predictions.services.PredictionService;

@Path("/custom-rest-prediction")
public class CustomRestPrediction {

    @Inject
    AppRoot appRoot;
    @Inject
    PredictionService svc;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DataContext linearRegression(Map<String, Object> payload) {
        // path: /predictions/Testregression/LinReg
        var id = appRoot
                .get(PredictionIds.class)
                .get("test_regression.pmml", "LinReg");

        MapDataContext ctx = MapDataContext.of(payload);
        return svc.evaluate(id, ctx).data();
    }

}

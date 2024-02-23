/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.pegaproofofconceptstubs.controllers

import play.api.Logging
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import play.mvc.Http.HeaderNames
import uk.gov.hmrc.pegaproofofconceptstubs.models.{GetCaseResponse, StartCaseRequest, StartCaseResponse}
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import java.util.Locale
import javax.inject.{Inject, Singleton}

@Singleton()
class PegaController @Inject() (cc: ControllerComponents)()
  extends BackendController(cc) with Logging {

  val startCase: Action[StartCaseRequest] = Action(parse.json[StartCaseRequest]) { implicit request =>
    logger.info("[OPS-11581] payload submitted with value: " + request.body.toString)
    logger.info("[OPS-11785] responding with: " + StartCaseResponse.defaultResponse.toString)
    if (request.headers.get(HeaderNames.AUTHORIZATION).getOrElse("").toLowerCase(Locale.ENGLISH).startsWith("basic")) {
      Ok(Json.toJson(StartCaseResponse.defaultResponse))
    } else {
      Forbidden
    }
  }

  def getCase(caseId: String): Action[AnyContent] = Action { implicit request =>
    logger.info("caseId was " + caseId)
    if (request.headers.get(HeaderNames.AUTHORIZATION).getOrElse("").toLowerCase(Locale.ENGLISH).startsWith("basic")) {
      Ok(GetCaseResponse(caseId).value)
    } else {
      Forbidden
    }
  }
}

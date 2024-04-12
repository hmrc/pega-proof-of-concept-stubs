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
import play.api.mvc.{Action, AnyContent, ControllerComponents, Request}
import play.mvc.Http.HeaderNames
import uk.gov.hmrc.pegaproofofconceptstubs.models.{GetCaseResponse, PegaToken, StartCaseRequest, StartCaseResponse}
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import java.util.Locale
import javax.inject.{Inject, Singleton}
import scala.util.Random

@Singleton()
class PegaController @Inject() (cc: ControllerComponents)()
  extends BackendController(cc) with Logging {

  val startCase: Action[StartCaseRequest] = Action(parse.json[StartCaseRequest]) { implicit request =>
    logger.info("Received access token: " + request.headers.get(HeaderNames.AUTHORIZATION).toString
      + "\n -> Responded with: " + StartCaseResponse.defaultResponse.toString
      + "\n and payload submitted with value: " + request.body.toString)

    if (hasCorrectAuth(request)) Created(Json.toJson(StartCaseResponse.defaultResponse))
    else Forbidden
  }

  def getCase(caseId: String): Action[AnyContent] = Action { implicit request =>
    logger.info("Received access token: " + request.headers.get(HeaderNames.AUTHORIZATION).toString + " and caseId was " + caseId)

    if (hasCorrectAuth(request)) Ok(GetCaseResponse(caseId).value)
    else Forbidden
  }

  val getToken: Action[AnyContent] = Action { request =>
    val randomToken: Int = Random.between(10000, 99999)
    logger.info(s"Responding with access token: ${randomToken.toString}")

    if (hasCorrectTokenAuth(request)) Ok(PegaToken(s"${randomToken.toString}").value)
    else Unauthorized
  }

  private def hasCorrectAuth(request: Request[_]): Boolean =
    request.headers.get(HeaderNames.AUTHORIZATION).getOrElse("").toLowerCase(Locale.ENGLISH).startsWith("bearer")

  private def hasCorrectTokenAuth(request: Request[_]): Boolean =
    request.headers.get(HeaderNames.AUTHORIZATION).getOrElse("").toLowerCase(Locale.ENGLISH).startsWith("basic")

}

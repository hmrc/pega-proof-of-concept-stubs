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
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController
import play.api.mvc.{Action, ControllerComponents}
import uk.gov.hmrc.pegaproofofconceptstubs.models.Payload

import javax.inject.{Inject, Singleton}

@Singleton()
class SubmitPayloadController @Inject() (cc: ControllerComponents)()
  extends BackendController(cc) with Logging {

  val submitPayload: Action[Payload] = Action(parse.json[Payload]) { implicit request =>
    logger.info("[OPS-11581] payload submitted with value: " + request.body.data)
    Ok
  }
}

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

package uk.gov.hmrc.pegaproofofconceptstubs.models

import play.api.libs.json.{Json, OFormat}

import scala.util.Random

final case class StartCaseResponse(ID: String, nextAssignmentID: String, nextPageID: String, pxObjClass: String)

object StartCaseResponse {

  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  implicit val format: OFormat[StartCaseResponse] = Json.format

  def defaultResponse: StartCaseResponse = {
    val sessionIdNum: Int = Random.between(10000, 99999)
    StartCaseResponse(s"HMRC-DEBT-WORK A-${sessionIdNum.toString}", "ASSIGN-WORKLIST HMRC-DEBT-WORK A-13002!STARTAFFORDABILITYASSESSMENT_FLOW", "Perform", "Pega-API-CaseManagement-Case")
  }
}

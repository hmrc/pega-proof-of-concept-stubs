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

import play.api.libs.json.{JsObject, Json}

final case class PegaToken(value: JsObject)

object PegaToken {
  def apply(access_token: String): PegaToken = PegaToken(Json.parse(
    s"""
       |{
       |  "access_token": "$access_token",
       |  "token_type": "bearer",
       |  "expires_in": 3600
       |}
       |""".stripMargin
  ).as[JsObject])

}

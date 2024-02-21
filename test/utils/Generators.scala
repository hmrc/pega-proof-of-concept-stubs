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

package uk.gov.hmrc.pegaproofofconceptstubs.utils

import org.scalacheck.Gen
import play.api.libs.json.Json
import uk.gov.hmrc.pegaproofofconceptstubs.models.StartCaseRequest

trait Generators {
  val nonEmptyStringGen: Gen[String] = for {
    length <- Gen.chooseNum(1, 50)
    str <- Gen.listOfN(length, Gen.alphaChar).map(_.mkString)
  } yield str
  val nonEmptyStartCaseRequest: Gen[StartCaseRequest] = for {
    value <- nonEmptyStringGen
  } yield StartCaseRequest(value, "", "", Json.obj())
}

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

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.{JsObject, Json}
import play.api.test.FakeRequest
import play.api.test.Helpers._
import play.mvc.Http.HeaderNames
import uk.gov.hmrc.pegaproofofconceptstubs.models.StartCaseRequest
import uk.gov.hmrc.pegaproofofconceptstubs.utils.Generators

class PegaControllerSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite with ScalaCheckDrivenPropertyChecks
  with Generators {

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .configure(
        "metrics.jvm" -> false,
        "metrics.enabled" -> false
      )
      .overrides()
      .build()

  private val controller = app.injector.instanceOf[PegaController]

  "Pega Controller" when {

    "submitting a StartCaseRequest in startCase" should {

      "return 201 for a valid request" in {

        val json = StartCaseRequest("", "", "", Json.obj())
        val result = controller.startCase()(FakeRequest().withHeaders(HeaderNames.AUTHORIZATION -> "basic").withBody(json))
        status(result) shouldBe 201
      }

      "return 403 when provided an incorrect auth header" in {

        val json = StartCaseRequest("", "", "", Json.obj())
        val result = controller.startCase()(FakeRequest().withHeaders(HeaderNames.AUTHORIZATION -> "beans").withBody(json))

        status(result) shouldBe 403
      }

      "return 403 when provided an empty auth header" in {

        val json = StartCaseRequest("", "", "", Json.obj())
        val result = controller.startCase()(FakeRequest().withHeaders(HeaderNames.AUTHORIZATION -> "").withBody(json))

        status(result) shouldBe 403
      }
    }

    "sending a GetCaseResponse in getCase" should {

      "return a 200 for a valid request" in {

        val result = controller.getCase("blah")(FakeRequest().withHeaders(HeaderNames.AUTHORIZATION -> "basic"))

        status(result) shouldBe 200
        contentAsJson(result) shouldBe Json.parse(
          """
            |{
            |  "caseTypeID": "HMRC-Debt-Work-AffordAssess",
            |  "createdBy": "TR929206A",
            |  "createTime": "2024-02-13T14:13:29.705Z",
            |  "ID": "blah",
            |  "lastUpdatedBy": "TR929206A",
            |  "lastUpdateTime": "2024-02-13T14:13:54.163Z",
            |  "name": "AffordAssess",
            |  "pxObjClass": "Pega-API-CaseManagement-Case",
            |  "stage": "PRIM3",
            |  "status": "Pending-PaymentPlans",
            |  "urgency": "10",
            |  "actions": [
            |    {
            |      "ID": "pyUpdateCaseDetails",
            |      "name": "Edit details",
            |      "pxObjClass": "Pega-API-CaseManagement-Action"
            |    },
            |    {
            |      "ID": "pyChangeStage",
            |      "name": "Change stage",
            |      "pxObjClass": "Pega-API-CaseManagement-Action"
            |    }
            |  ],
            |  "assignments": [
            |    {
            |      "ID": "ASSIGN-WORKLIST HMRC-DEBT-WORK A-11039!DISPLAYPAYMENTPLANOPTIONS_FLOW",
            |      "name": "Display link",
            |      "pxObjClass": "Pega-API-CaseManagement-Assignment",
            |      "routedTo": "TR929206A",
            |     "type": "Assignment",
            |      "urgency": "10",
            |      "actions": [
            |        {
            |          "ID": "DisplayLink",
            |          "name": "Display link",
            |          "pxObjClass": "Pega-API-CaseManagement-Action"
            |        }
            |      ]
            |    }
            |  ],
            |  "content": {
            |    "pxApplication": "Debt",
            |    "pxApplicationVersion": "01.01.01",
            |    "pxCommitDateTime": "2024-02-13T14:13:54.167Z",
            |    "pxCoveredCount": "0",
            |    "pxCoveredCountOpen": "0",
            |    "pxCoveredCountUnsatisfied": "0",
            |    "pxCreateDateTime": "2024-02-13T14:13:29.705Z",
            |    "pxCreatedFromChannel": "Web",
            |    "pxCreateOperator": "TR929206A",
            |    "pxCreateOpName": "Yasmine Owen",
            |    "pxCreateSystemID": "hmrc-adviserui-stg2",
            |    "pxCurrentStage": "PRIM3",
            |    "pxCurrentStageLabel": "Display Payment Plan Options",
            |    "pxCurrentStageSubscript": "PRIM3_5",
            |    "pxExternalSystemUpdateCount": "0",
            |    "pxInsName": "A-11039",
            |    "pxLockHandle": "HMRC-DEBT-WORK A-11039",
            |    "pxObjClass": "HMRC-Debt-Work-AffordAssess",
            |    "pxPreviousChannel": "Web",
            |    "pxSaveDateTime": "2024-02-13T14:13:54.163Z",
            |    "pxUpdateCounter": "12",
            |    "pxUpdateDateTime": "2024-02-13T14:13:54.163Z",
            |    "pxUpdateOperator": "TR929206A",
            |    "pxUpdateOpName": "Yasmine Owen",
            |    "pxUpdateOrgUnit": "Installation",
            |    "pxUpdateSystemID": "hmrc-adviserui-stg2",
            |    "pxUrgencyWork": "10",
            |    "pxUrgencyWorkClass": "10",
            |    "pxUrgencyWorkStageSLA": "0",
            |    "pyAgeFromDate": "2024-02-13T14:13:29.705Z",
            |    "pyAutomationErrorsRef": "pyAutomationErrors_Y9YOS",
            |    "pyConfirmationNote": "pyStepRoutedConfirmation",
            |    "pyElapsedCustomerUnsatisfied": "0.0",
            |    "pyElapsedStatusNew": "0",
            |    "pyElapsedStatusOpen": "4.0",
            |    "pyElapsedStatusPending": "16.0",
            |    "pyFolderType": "pyDefault",
            |    "pyID": "A-11039",
            |    "pyLabel": "AffordAssess",
            |    "pyLabelOld": "AffordAssess",
            |    "pyNextEmailThreadID": "1",
            |    "pyNotifyQuickStream": "QuestionAboutItem",
            |    "pyOrigDivision": "Administration",
            |    "pyOrigOrg": "pega.com",
            |    "pyOrigOrgUnit": "Installation",
            |    "pyOrigUserDivision": "Administration",
            |    "pyOrigUserID": "TR929206A",
            |    "pyOrigUserWorkgroup": "Default",
            |    "pyOwnerDivision": "Administration",
            |    "pyOwnerOrg": "pega.com",
            |    "pyOwnerOrgUnit": "Installation",
            |    "pyPushNotificationsEnabled": "false",
            |    "pySatisfactionChangeTimestamp": "2024-02-13T14:13:29.724Z",
            |    "pyStatusCustomerSat": "New",
            |    "pyStatusWork": "Pending-PaymentPlans",
            |    "pyStatusWorkOld": "Pending-PaymentPlans",
            |    "pyStatusWorkTimestamp": "2024-02-13T14:13:51.428Z",
            |    "pyTemporaryObject": "false",
            |    "pyWorkIDPrefix": "A-",
            |    "pyWorkPartiesRule": "pyCaseManagementDefault",
            |    "pzInsKey": "HMRC-DEBT-WORK A-11039",
            |    "Expenditure": {
            |      "pxObjClass": "HMRC-Debt-Data-AffordAssess",
            |      "TotalCAAIncome": "0",
            |      "TotalExpenditureCAA": "0"
            |    },
            |    "IncomeDetails": {
            |      "pxObjClass": "HMRC-Debt-Data-AffordAssess",
            |      "TotalIncomePAA": "0"
            |    },
            |    "PayOffDetails": {
            |      "ExpectedIncPer": "50%",
            |      "LegalEntityType": "Individual",
            |      "PayDebtOff": "No",
            |      "pxObjClass": "HMRC-Debt-Data-AffordAssess",
            |      "SetLegalType": "PAA"
            |    },
            |    "pxFlow": {
            |      "DisplayPaymentPlanOptions_Flow": {
            |        "pxAssignActivity": "WorkList",
            |        "pxAssignClass": "Assign-Worklist",
            |        "pxAssignmentKey": "ASSIGN-WORKLIST HMRC-DEBT-WORK A-11039!DISPLAYPAYMENTPLANOPTIONS_FLOW",
            |        "pxAssignStatus": "Pending-PaymentPlans",
            |        "pxFlowInsKey": "RULE-OBJ-FLOW HMRC-DEBT-WORK-AFFORDASSESS DISPLAYPAYMENTPLANOPTIONS_FLOW #20240117T150653.017 GMT",
            |        "pxIsInvestigative": "false",
            |        "pxLastActivityStatus": "DisplayAffordablePlans",
            |        "pxLastUpdateBy": "TR929206A",
            |        "pxObjClass": "Embed-PegaEPRO-FlowPage",
            |        "pxRouteTo": "TR929206A",
            |        "pxRouteToUserName": "Yasmine Owen",
            |        "pxStageFlowID": "FLOW0",
            |        "pxSubscript": "DisplayPaymentPlanOptions_Flow",
            |        "pxSystemFlow": "false",
            |        "pxTimeFlowStarted": "20240213T141351.424 GMT",
            |        "pyCategory": "FlowStandard",
            |        "pyConfirmationNote": "pyStepRoutedConfirmation",
            |        "pyDeferCommit": "false",
            |        "pyDeferErrors": "false",
            |        "pyDraftMode": "false",
            |        "pyFirstRun": "false",
            |        "pyFlowCalledCount": "1",
            |       "pyFlowInterestPageClass": "HMRC-Debt-Work-AffordAssess",
            |        "pyFlowType": "DisplayPaymentPlanOptions_Flow",
            |        "pyFlowTypeLabel": "Display Payment Plan Options",
            |        "pyIssuedFromStage": "PRIM3",
            |        "pyIssuedFromStageLabel": "Display Payment Plan Options",
            |        "pyIssuedFromStageSubscript": "PRIM3_5",
            |        "pyLastFlowStep": "Assignment2",
            |        "pyLastFlowStepLabel": "Display link",
            |        "pyContexts": {},
            |        "pyFlowParameters": {
            |          "AssignTo": "TR929206A",
            |          "AutomaticTransition": "false",
            |          "CurrentflowName": "DisplayPaymentPlanOptions_Flow",
            |          "CurrentStage": "PRIM3",
            |          "CurrentStageLabel": "Display Payment Plan Options",
            |          "CurrentStageSubscript": "PRIM3_5",
            |          "DoNotEvaluateWhens": "Yes",
            |          "flowClass": "HMRC-Debt-Work-AffordAssess",
            |          "FlowID": "FLOW0",
            |          "flowLabel": "Display Payment Plan Options",
            |          "flowName": "DisplayPaymentPlanOptions_Flow",
            |          "flowType": "DisplayPaymentPlanOptions_Flow",
            |          "isThisReEntry": "No",
            |          "pyCaseTypePurpose": "STAGE_STARTPROCESSES",
            |          "pyForEachCount": "5",
            |          "pyProcessPageName": "pySupportProcessList",
            |          "pySubCasesPageName": "pySubCasesList",
            |          "ReferencePageName": "pyWorkPage"
            |        },
            |        "pyFlowPath": [
            |          {
            |            "pxObjClass": "Embed-PegaEPRO-FlowPath",
            |            "pyDisplay": "true",
            |            "pyFAProcessOnJump": "false",
            |            "pyFlowStep": "Assignment1",
            |            "pyRouteTo": "TR929206A",
            |            "pyStepLabel": "Display affordable plans",
            |            "pyStepType": "ASSIGNMENT"
            |          },
            |          {
            |            "pxObjClass": "Embed-PegaEPRO-FlowPath",
            |            "pyDisplay": "true",
            |            "pyFAProcessOnJump": "false",
            |            "pyFlowStep": "Assignment2",
            |            "pyStepLabel": "Display link",
            |            "pyStepType": "ASSIGNMENT"
            |          }
            |        ]
            |      },
            |      "pzInternalCaseFlow": {
            |        "pxAssignActivity": "pzCreateInternalAssignment",
            |        "pxAssignClass": "Assign-Internal",
            |        "pxAssignIsVirtual": "true",
            |        "pxAssignmentKey": "ASSIGN-INTERNAL HMRC-DEBT-WORK A-11039!PZINTERNALCASEFLOW",
            |        "pxFlowInsKey": "RULE-OBJ-FLOW WORK- PZINTERNALCASEFLOW #20180713T133112.404 GMT",
            |        "pxIsInvestigative": "false",
            |        "pxLastUpdateBy": "TR929206A",
            |        "pxObjClass": "Embed-PegaEPRO-FlowPage",
            |        "pxRouteTo": "default@pega.com",
            |        "pxSubscript": "pzInternalCaseFlow",
            |        "pxSystemFlow": "true",
            |        "pxTimeFlowStarted": "20240213T141329.711 GMT",
            |        "pyCategory": "FlowStandard",
            |        "pyDeferCommit": "false",
            |        "pyDeferErrors": "false",
            |        "pyDraftMode": "false",
            |        "pyFirstRun": "false",
            |        "pyFlowCalledCount": "1",
            |        "pyFlowInterestPageClass": "HMRC-Debt-Work-AffordAssess",
            |        "pyFlowType": "pzInternalCaseFlow",
            |        "pyFlowTypeLabel": "Internal flow",
            |        "pyLastFlowStep": "ASSIGNMENT63",
            |        "pyLastFlowStepLabel": "Perform action",
            |        "pyContexts": {},
            |        "pyFlowParameters": {
            |          "AssignTo": "default@pega.com",
            |          "flowName": "pzInternalCaseFlow",
            |          "isNewCase": "true",
            |          "pyTempPlaceHolder": "TempPlaceHolder",
            |          "ReferencePageName": "pyWorkPage"
            |        },
            |        "pyFlowPath": [
            |          {
            |            "pxObjClass": "Embed-PegaEPRO-FlowPath",
            |            "pyDisplay": "false",
            |            "pyFAProcessOnJump": "false",
            |            "pyFlowStep": "ASSIGNMENT63",
            |            "pyStepLabel": "Perform action",
            |            "pyStepType": "ASSIGNMENT"
            |          }
            |        ]
            |      }
            |    },
            |    "pxStageHistory": [
            |      {
            |        "pxCompletedBy": "Yasmine Owen",
            |        "pxCompletedByOperator": "TR929206A",
            |        "pxCompletedStageTime": "2024-02-13T14:13:29.715Z",
            |        "pxEnterStageTime": "2024-02-13T14:13:29.713Z",
            |        "pxObjClass": "Embed-StageHistory",
            |        "pxStageID": "PRIM0",
            |        "pxStageName": "Create",
            |        "pxStageType": "Primary",
            |        "pxSubscript": "PRIM0_1",
            |        "pxWentTo": "PRIM6",
            |        "pyIsInitializationStage": "true",
            |        "pxProcesses": []
            |      },
            |      {
            |        "pxCameFrom": "PRIM0",
            |        "pxCompletedBy": "Yasmine Owen",
            |        "pxCompletedByOperator": "TR929206A",
            |        "pxCompletedStageTime": "2024-02-13T14:13:34.540Z",
            |        "pxEnterStageTime": "2024-02-13T14:13:29.715Z",
            |        "pxObjClass": "Embed-StageHistory",
            |        "pxStageID": "PRIM6",
            |        "pxStageName": "Start Affordability Assessment",
            |        "pxStageType": "Primary",
            |        "pxSubscript": "PRIM6_2",
            |        "pxWentTo": "PRIM1",
            |        "pxProcesses": [
            |          {
            |            "pxCompletedBy": "Yasmine Owen",
            |            "pxCompletedByOperator": "TR929206A",
            |            "pxCompletedTime": "2024-02-13T14:13:34.538Z",
            |            "pxFlowID": "FLOW0",
            |            "pxIsComplete": "True",
            |            "pxIsOptional": "false",
            |            "pxObjClass": "Embed-StageProcessHistory",
            |            "pxProcessName": "StartAffordabilityAssessment_Flow",
            |            "pxStartedBy": "Yasmine Owen",
            |            "pxStartedByOperator": "TR929206A",
            |            "pxStartTime": "2024-02-13T14:13:29.719Z",
            |            "pxSubscript": "StartAffordabilityAssessment_Flow",
            |            "pyLabel": "Start Affordability Assessment",
            |            "pxSteps": [
            |              {
            |                "pxObjClass": "Embed-StageStepHistory",
            |                "pxStepID": "Assignment1",
            |                "pxStepType": "ASSIGNMENT",
            |                "pyLabel": "Affordability Assessment"
            |              },
            |              {
            |                "pxObjClass": "Embed-StageStepHistory",
            |                "pxStepID": "Decision1",
            |                "pxStepType": "DECISION",
            |                "pyLabel": "[Decision]"
            |              }
            |            ]
            |          }
            |        ]
            |      },
            |      {
            |        "pxCameFrom": "PRIM6",
            |        "pxCompletedBy": "Yasmine Owen",
            |        "pxCompletedByOperator": "TR929206A",
            |        "pxCompletedStageTime": "2024-02-13T14:13:40.553Z",
            |        "pxEnterStageTime": "2024-02-13T14:13:34.539Z",
            |        "pxObjClass": "Embed-StageHistory",
            |        "pxStageID": "PRIM1",
            |        "pxStageName": "Determine Assessment Type",
            |        "pxStageType": "Primary",
            |        "pxSubscript": "PRIM1_3",
            |        "pxWentTo": "PRIM2",
            |        "pxProcesses": [
            |          {
            |            "pxCompletedBy": "Yasmine Owen",
            |            "pxCompletedByOperator": "TR929206A",
            |            "pxCompletedTime": "2024-02-13T14:13:40.551Z",
            |            "pxFlowID": "FLOW0",
            |            "pxIsComplete": "True",
            |            "pxIsOptional": "false",
            |            "pxObjClass": "Embed-StageProcessHistory",
            |            "pxProcessName": "DetermineAssessmentType_Flow",
            |            "pxStartedBy": "Yasmine Owen",
            |            "pxStartedByOperator": "TR929206A",
            |            "pxStartTime": "2024-02-13T14:13:34.545Z",
            |            "pxSubscript": "DetermineAssessmentType_Flow",
            |            "pyLabel": "Determine Assessment Type",
            |            "pxSteps": [
            |              {
            |                "pxObjClass": "Embed-StageStepHistory",
            |                "pxStepID": "Assignment1",
            |                "pxStepType": "ASSIGNMENT",
            |                "pyLabel": "Check Legal Type"
            |              },
            |              {
            |                "pxObjClass": "Embed-StageStepHistory",
            |                "pxStepID": "Decision1",
            |                "pxStepType": "DECISION",
            |                "pyLabel": "Check legal entity"
            |              },
            |              {
            |                "pxObjClass": "Embed-StageStepHistory",
            |                "pxStepID": "Assignment2",
            |                "pxStepType": "ASSIGNMENT",
            |                "pyLabel": "Display Landing Page"
            |              }
            |            ]
            |          }
            |        ]
            |      },
            |      {
            |        "pxCameFrom": "PRIM1",
            |        "pxCompletedBy": "Yasmine Owen",
            |        "pxCompletedByOperator": "TR929206A",
            |        "pxCompletedStageTime": "2024-02-13T14:13:51.420Z",
            |        "pxEnterStageTime": "2024-02-13T14:13:40.552Z",
            |        "pxObjClass": "Embed-StageHistory",
            |        "pxStageID": "PRIM2",
            |        "pxStageName": "Gather Income and Expenditure Details",
            |        "pxStageType": "Primary",
            |        "pxSubscript": "PRIM2_4",
            |        "pxWentTo": "PRIM3",
            |        "pxProcesses": [
            |          {
            |            "pxCompletedBy": "Yasmine Owen",
            |            "pxCompletedByOperator": "TR929206A",
            |            "pxCompletedTime": "2024-02-13T14:13:51.418Z",
            |            "pxFlowID": "FLOW1",
            |            "pxIsComplete": "True",
            |            "pxIsOptional": "false",
            |            "pxObjClass": "Embed-StageProcessHistory",
            |            "pxProcessName": "GatherIncomeAndExpenditureDetails_Flow_1",
            |            "pxStartedBy": "Yasmine Owen",
            |            "pxStartedByOperator": "TR929206A",
            |            "pxStartTime": "2024-02-13T14:13:40.559Z",
            |            "pxSubscript": "GatherIncomeAndExpenditureDetails_Flow_1",
            |            "pyLabel": "Gather Income and Expenditure Details (1)",
            |            "pxSteps": [
            |              {
            |                "pxObjClass": "Embed-StageStepHistory",
            |                "pxStepID": "Assignment6",
            |                "pxStepType": "ASSIGNMENT",
            |                "pyLabel": "Mothly Income"
            |              },
            |              {
            |                "pxObjClass": "Embed-StageStepHistory",
            |                "pxStepID": "Assignment1",
            |                "pxStepType": "ASSIGNMENT",
            |                "pyLabel": "Monthly Income Details"
            |              },
            |              {
            |                "pxObjClass": "Embed-StageStepHistory",
            |                "pxStepID": "Decision1",
            |                "pxStepType": "DECISION",
            |                "pyLabel": "[Decision]"
            |              },
            |              {
            |                "pxObjClass": "Embed-StageStepHistory",
            |                "pxStepID": "Assignment3",
            |                "pxStepType": "ASSIGNMENT",
            |                "pyLabel": " Monthly expenditure details"
            |              },
            |              {
            |                "pxObjClass": "Embed-StageStepHistory",
            |                "pxStepID": "Decision2",
            |                "pxStepType": "DECISION",
            |                "pyLabel": "[Decision]"
            |              },
            |              {
            |                "pxObjClass": "Embed-StageStepHistory",
            |                "pxStepID": "Assignment5",
            |                "pxStepType": "ASSIGNMENT",
            |                "pyLabel": "Display Disposable Income"
            |              }
            |            ]
            |          }
            |        ]
            |      },
            |      {
            |        "pxCameFrom": "PRIM2",
            |        "pxEnterStageTime": "2024-02-13T14:13:51.419Z",
            |        "pxObjClass": "Embed-StageHistory",
            |        "pxStageID": "PRIM3",
            |        "pxStageName": "Display Payment Plan Options",
            |       "pxStageType": "Primary",
            |        "pxSubscript": "PRIM3_5",
            |        "pxProcesses": [
            |          {
            |            "pxFlowID": "FLOW0",
            |            "pxIsComplete": "False",
            |            "pxIsOptional": "false",
            |            "pxObjClass": "Embed-StageProcessHistory",
            |            "pxProcessName": "DisplayPaymentPlanOptions_Flow",
            |            "pxStartedBy": "Yasmine Owen",
            |            "pxStartedByOperator": "TR929206A",
            |            "pxStartTime": "2024-02-13T14:13:51.424Z",
            |            "pxSubscript": "DisplayPaymentPlanOptions_Flow",
            |            "pyLabel": "Display Payment Plan Options",
            |            "pxSteps": [
            |              {
            |                "pxObjClass": "Embed-StageStepHistory",
            |                "pxStepID": "Assignment1",
            |                "pxStepType": "ASSIGNMENT",
            |                "pyLabel": "Display affordable plans"
            |              },
            |              {
            |                "pxObjClass": "Embed-StageStepHistory",
            |                "pxStepID": "Assignment2",
            |                "pxStepType": "ASSIGNMENT",
            |                "pyLabel": "Display link"
            |              }
            |            ]
            |          }
            |        ]
            |      }
            |    ],
            |    "pxSystemUpdateDetailsList": [],
            |    "pyAttachmentCategories": [],
            |    "Spending": {
            |      "DDIncomePAA": "0.00",
            |      "DiscountComapnyDSP": "0.00",
            |      "pxObjClass": "HMRC-Debt-Data-AffordAssess",
            |      "TotalDispIncomeCAA": "0",
            |      "TotalDisposableIncome": "0",
            |      "TotalExpenditure": "0"
            |    }
            |  },
            |  "stages": [
            |    {
            |      "ID": "PRIM0",
            |      "name": "Create",
            |      "pxObjClass": "Pega-API-CaseManagement-Stage"
            |    },
            |    {
            |      "ID": "PRIM6",
            |      "name": "Start Affordability Assessment",
            |      "pxObjClass": "Pega-API-CaseManagement-Stage"
            |    },
            |    {
            |      "ID": "PRIM1",
            |      "name": "Determine Assessment Type",
            |      "pxObjClass": "Pega-API-CaseManagement-Stage"
            |    },
            |    {
            |      "ID": "PRIM2",
            |      "name": "Gather Income and Expenditure Details",
            |      "pxObjClass": "Pega-API-CaseManagement-Stage"
            |    },
            |    {
            |      "ID": "PRIM3",
            |      "name": "Display Payment Plan Options",
            |      "pxObjClass": "Pega-API-CaseManagement-Stage"
            |    }
            |  ]
            |}
            |""".stripMargin
        ).as[JsObject]
      }

      "return a 403 when provided an incorrect auth header" in {

        val result = controller.getCase("blah")(FakeRequest().withHeaders(HeaderNames.AUTHORIZATION -> "acidic"))

        status(result) shouldBe 403
      }

      "return a 403 when provided an empty auth header" in {

        val result = controller.getCase("blah")(FakeRequest().withHeaders(HeaderNames.AUTHORIZATION -> ""))

        status(result) shouldBe 403
      }
    }
  }
}

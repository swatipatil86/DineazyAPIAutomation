Feature: Dineazy API Suite:Register
  @register
  Scenario Outline: Verify Register to Dineazy API
    Given user enters the <firstName>,<lastName>,<email>,<password> and <phone> details
    Then user should verify that User Resister successfully

    Examples:
      |firstName|lastName|email                   |password     |phone            |
      |"Swati"  |"Patil" |"swati.p@itprofoundGit add QA_Divya.com"|"Anish##0104"| "7633539319"    |
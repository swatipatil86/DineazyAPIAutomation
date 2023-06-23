Feature: Dineazy API Suite:Create Profile
  @createProfile
  Scenario Outline: Validate Create Profile API
    Given user login to dineazy
    And user enters <firstName>,<lastName>,<email>,<password> and <phone> details
    Then user should verify that profile created successfully
    Examples:
    |firstName|lastName|email                   |password     |phone            |
    |"Swati"  |"Patil" |"swati.p@itprofound.com"|"Anish##0104"| "7633539319"    |



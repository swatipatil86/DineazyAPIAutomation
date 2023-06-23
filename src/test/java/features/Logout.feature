Feature: Dineazy API Suite:Logout

  @logout
  Scenario: Verify Logout API
    Given user login to dineazy
    When user logout from application
    Then user should verify that user is logged out successfully

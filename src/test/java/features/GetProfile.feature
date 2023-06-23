Feature: Dineazy API Suite:GetProfile

@e2e
Scenario Outline: Validate Login to API:Verify profile details
Given user login to dineazy
When user gets the profile
Then user should verify that <profile> is found
  Examples:
    | profile |
    | "swati" |


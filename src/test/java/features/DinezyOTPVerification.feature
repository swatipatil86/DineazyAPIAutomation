Feature: Dineazy API Suite:OTP verificaiton
@otp
  Scenario: OTP sent and Verified
    Given user login to dineazy
    When user send OTP
    Then user should verify OTP sent is correct

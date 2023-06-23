Feature: Dineazy API Suite:Login

@login
 Scenario Outline: Validate Login to API
 Given user login with credentials <username> and <password>
 Examples:
    | username                 | password      |
    | "swati.p@itprofound.com" | "Anish##0104" |
    |"girish.pr@itprofound.com"|"Test@1234"    |




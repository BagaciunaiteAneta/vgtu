@ClaimPayoutFileTest
Feature: Check pay out file
  As a user I want to compare sums in claim pay out csv file with sums in application


  @Scenario1
  Scenario: Trauma claim registration
    Given I'm logged in BLIS system 
    And Open claim list
    When I filter claim in AP Approved status
    And Check pay out sum and press <Export for pay out>
    Then In csv file compare sum with "Payout amount in original currency" cell
   

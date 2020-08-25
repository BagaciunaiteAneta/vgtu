@ClaimRegisistrationBlis
Feature: Register BLIS claim
  As a user I want to register a new claim in BLIS application


  @Scenario1
  Scenario: Trauma claim registration
    Given I'm logged-in in BLIS
    And Open policy list
    When Choose policy with Trauma cover
    And Press create New Claim application
    And Fill all mandatory fields and press <Save>
    And Press <Edit/Investigate>
    Then I fill additional information and press <Approve>
    Then Check if the claim was registered and is in correct status

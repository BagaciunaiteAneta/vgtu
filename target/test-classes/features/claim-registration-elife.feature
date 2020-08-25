@ClaimRegistrationtest
Feature: Register new claim
  As a user I want to register a new claim in E-Life application

  @Scenario1
  Scenario: Trauma claim registration
    Given I'm logged-in in IBS
    And Opened policy list
    When Choose policy
    And Press create new claim
    And Fill all mandatory fields
    Then Press continue
    And Check if the claim was registered

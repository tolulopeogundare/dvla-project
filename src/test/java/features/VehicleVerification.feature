Feature: Vehicle Information Validation

  Scenario: Check that all vehicle information is as expected
    Given I load all supported files in configured directory
    When I retrieve all vehicles information in the files
    Then I verify each vehicle information matches what is on the website


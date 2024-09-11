Feature: River Test Cases

  Scenario: Save Draft
    Given the user is logged in
    When the user navigates to the 'File a form' page
    And the user selects 'Declaration for interpreted USCIS interview'
    And the user reviews the instructions
    And the user declares an interpreter
    And the user selects 'preferred language'
    Then the information entered should be saved to be completed later

  Scenario: Submit declaration with all fields
    Given the user is logged in
    When the user navigates to the 'File a form' page
    And the user selects 'Declaration for interpreted USCIS interview'
    And the user declares an interpreter with all fields filled
    Then the form should be submitted successfully

  # Add more scenarios based on the test cases provided in the document.

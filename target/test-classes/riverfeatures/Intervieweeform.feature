Feature: Interviewee form

  #Interviewee form.
  @JIRA-Story
  Scenario Outline: Language information is saved so the form can be completed and submitted later
    Given On Login Screen I logged in to River
    And User is on the Declaration for interpreted USCIS interview page
    When the user clicks "Declare an interpreter"
    Then the user is redirected to the "Personal information" page
